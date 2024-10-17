package io.github.ay012.playermail.storage

import io.github.ay012.playermail.PlayerMail.say
import io.github.ay012.playermail.api.MailAPI
import io.github.ay012.playermail.config.SettingsConfig
import io.github.ay012.playermail.config.TemplateConfig
import io.github.ay012.playermail.data.PlayerData
import io.github.ay012.playermail.data.PlayerDataManager
import io.github.ay012.playermail.util.ItemStackUtils
import io.github.ay012.playermail.util.SerializationUtils
import io.github.ay012.playermail.util.TimeUtils
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.io.newFile
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.submitAsync
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import taboolib.module.configuration.createLocal
import taboolib.platform.util.onlinePlayers
import java.io.IOException
import java.util.*

class YAML : MailAPI() {

	private fun database(uuid: UUID): Configuration {
		val file = newFile(getDataFolder(), "save/${SettingsConfig.yaml_path}/$uuid.yml", create = true)
		return Configuration.loadFromFile(file, Type.YAML)
	}

	private val items by lazy {
		createLocal("save/items.yml", type = Type.YAML, saveTime = 12000)
	}

	init {
		submitAsync(period = SettingsConfig.autoSaveCacheTime) {
			TimeUtils.measureTimeSeconds {
				saveCache()
			}.also {
				say("&8[&fINFO&8] &3自动将缓存保存到YAML... 共处理邮件数: ${PlayerDataManager.getPlayerMailCache.values.sumOf { mail -> mail.size }} 封 &8(总耗时 $it s)")
			}
		}
	}

	override fun saveCache() {
		if (PlayerDataManager.getPlayerMailCache.isEmpty()) return

		val onlineUUIDs = onlinePlayers.map { it.uniqueId }.toSet()

		PlayerDataManager.getPlayerMailCache.forEach { (uuid, mails) ->
			val config = database(uuid)

			when(uuid in onlineUUIDs) {
				true -> {
					mails.forEach { mail ->
						config["mails.${mail.uuid}"] = SerializationUtils.serializeMailList(mutableListOf(mail))
					}
				}
				false -> {
					val currentMails = selectUser(uuid).apply { addAll(mails) }
					currentMails.forEach { mail ->
						config["mails.${mail.uuid}"] = SerializationUtils.serializeMailList(mutableListOf(mail))
					}
					PlayerDataManager.getPlayerMailCache.remove(uuid)
				}
			}

			try {
				config.saveToFile()
			} catch (e: IOException) {
				say("&8[&cWARN&8] &c缓存写入YAML时出现错误! ${e.message}")
			}
		}
	}

	override fun sendMail(uuid: UUID, id: String, sender: String) {
		val configTemplate = TemplateConfig.getTemplateCache[id] ?: return

		// 反序列化邮件模板
		val mail = SerializationUtils.deserializeConfiguration(configTemplate).apply {
			this.uuid = UUID.randomUUID().toString()
			this.sender = sender
		}

		// 将邮件存入缓存
		PlayerDataManager.getPlayerMailCache.computeIfAbsent(uuid) { mutableListOf() }.add(mail)
	}

	override fun loadUser(uuid: UUID) {
		PlayerDataManager.getPlayerMailCache[uuid] = selectUser(uuid)
	}

	/**
	 * 从指定 UUID 的 YAML 文件中选择用户的邮件数据。
	 * @param uuid 要选择邮件数据的玩家 UUID。
	 * @return 包含所有邮件数据的可变列表。
	 */
	override fun selectUser(uuid: UUID): MutableList<PlayerData> {
		// 获取指定 UUID 的 YAML 配置文件
		val mailsMap = database(uuid).getConfigurationSection("mails") ?: return mutableListOf()

		// 遍历 YAML 文件中所有邮件的键
		return mailsMap.getKeys(false).flatMap { key ->
			// 获取邮件数据字符串
			mailsMap.getString(key)?.let {
				// 反序列化邮件数据并将其转换为 PlayerData 对象的流
				SerializationUtils.deserializeMailList(it)
			} ?: emptyList()
		}.toMutableList()
	}

	override fun saveItems(name: String, item: ItemStack) {
		items[name] = ItemStackUtils.itemStackToBase64(item)
	}

	override fun getItems(name: String, player: Player) {
		player.inventory.addItem(ItemStackUtils.base64ToItemStack(items.getString(name) ?: return))
	}
}
