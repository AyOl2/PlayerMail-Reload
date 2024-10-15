package io.github.ay012.playermail.data

import io.github.ay012.playermail.PlayerMail.say
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import taboolib.module.configuration.Configuration
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

object PlayerDataManager {

	/**
	 * 玩家邮件数据缓存
	 */
	private val playerMailCache = ConcurrentHashMap<UUID, MutableList<PlayerData>>()

	/**
	 * 获取玩家邮件数据缓存
	 */
	val getPlayerMailCache= playerMailCache

	/**
	 * 全部玩家数据缓存
	 */
	private val playerALLCache= ConcurrentHashMap.newKeySet<OfflinePlayer>()

	/**
	 * 缓存全部玩家数据缓存
	 */
	val getPlayerALLCache: ConcurrentHashMap.KeySetView<OfflinePlayer, Boolean> = playerALLCache

	/**
	 * 初始化
	 */
	fun init() {
		loadedPlayers()
	}

	/**
	 * 加载全部玩家数据至缓存
	 */
	private fun loadedPlayers() {
		measureTimeMillis {
			Bukkit.getOfflinePlayers().forEach { player ->
				playerALLCache.add(player)
			}
		}.also {
			say("&8[&fINFO&8] &a${playerALLCache.size} &3个玩家被载入至缓存... &8(总耗时 $it ms)")
		}
	}

	/**
	 * 判断缓存中是否存在此玩家
	 */
	fun addPlayer(player: Player) {
		if (playerALLCache.add(player)) {
			say("&8[&fINFO&8] &a${player.name} &3已载入至缓存...")
		}
	}

	/**
	 * 序列化工具类入口
	 */
	private val jsonUtils = Json {
		prettyPrint = true
		isLenient = true
		ignoreUnknownKeys = true
		coerceInputValues = true
		encodeDefaults = true
		allowStructuredMapKeys = true
		allowSpecialFloatingPointValues = true
	}

	/**
	 * 将 PlayerData 对象列表序列化为 JSON 字符串。
	 * @param data PlayerData 对象的列表。
	 * @return 序列化后的 JSON 字符串。
	 */
	fun serializeMailList(data: MutableList<PlayerData>): String {
		return jsonUtils.encodeToString(ListSerializer(PlayerData.serializer()), data)
	}

	/**
	 * 将 JSON 字符串反序列化为 PlayerData 对象列表。
	 * @param serializedMailList JSON 字符串。
	 * @return 反序列化后的 PlayerData 对象的列表。
	 */
	fun deserializeMailList(serializedMailList: String): MutableList<PlayerData> {
		return jsonUtils.decodeFromString(ListSerializer(PlayerData.serializer()), serializedMailList).toMutableList()
	}

	/**
	 * 将 Configuration 对象转换为 PlayerData 对象。
	 * @param config Configuration 对象。
	 * @return 反序列化后的 PlayerData 对象。
	 */
	fun deserializeConfiguration(config: Configuration): PlayerData {
		return Configuration.deserialize(config, ignoreConstructor = true)
	}
}
