package io.github.ay012.playermail.data

import io.github.ay012.playermail.PlayerMail.say
import io.github.ay012.playermail.util.TimeUtils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object PlayerDataManager {

	/**
	 * 玩家邮件数据缓存
	 */
	private val playerMailCache = ConcurrentHashMap<UUID, MutableList<PlayerData>>()

	/**
	 * 获取玩家邮件数据缓存
	 */

	fun getPlayerMailCache(): ConcurrentHashMap<UUID, MutableList<PlayerData>> {
		return playerMailCache
	}

	/**
	 * 全部玩家数据缓存
	 */
	private val playerALLCache= ConcurrentHashMap.newKeySet<OfflinePlayer>()

	/**
	 * 缓存全部玩家数据缓存
	 */
	fun getPlayerALLCache(): ConcurrentHashMap.KeySetView<OfflinePlayer, Boolean> {
		return playerALLCache
	}

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
		TimeUtils.measureTimeSeconds {
			Bukkit.getOfflinePlayers().forEach { player ->
				playerALLCache.add(player)
			}
		}.also {
			say("&8[&fINFO&8] &a${playerALLCache.size} &3个玩家被载入至缓存... &8(总耗时 $it s)")
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
}
