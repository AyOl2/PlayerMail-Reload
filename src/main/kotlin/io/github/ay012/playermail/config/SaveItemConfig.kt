package io.github.ay012.playermail.config

import io.github.ay012.playermail.PlayerMail.say
import io.github.ay012.playermail.util.ItemStackUtils
import io.github.ay012.playermail.util.TimeUtils
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.module.configuration.Type
import taboolib.module.configuration.createLocal
import java.util.concurrent.ConcurrentHashMap

object SaveItemConfig {

	private val items by lazy {
		createLocal("save/items.yml", type = Type.YAML, saveTime = 12000)
	}

	private val itemsCache = ConcurrentHashMap<String, String>()

	fun getItemsCache():ConcurrentHashMap<String, String> {
		return itemsCache
	}

	fun init() {
		TimeUtils.measureTimeSeconds {
			items.getKeys(false).forEach { key ->
				itemsCache[key] = items.getString(key) ?: ""
			}
		}.also {
			say("&8[&fINFO&8] &a${itemsCache.size} &3个物品被载入至缓存... &8(总耗时 $it s)")
		}
	}

	/**
	 * 将物品写入存储
	 *
	 * @param name 保存的物品名
	 * @param item 需要操作的物品
	 */
	fun saveItem(name: String, item: ItemStack) {
		val itemData = ItemStackUtils.itemStackToBase64(item)
		items[name] = itemData
		itemsCache[name] = itemData
	}

	/**
	 * 将保存的物品给予给玩家
	 *
	 * @param name 保存的物品名
	 * @param player 需要操作的玩家
	 */
	fun getItem(name: String, player: Player){
		player.inventory.addItem(ItemStackUtils.base64ToItemStack(itemsCache[name] ?: return))
	}

	/**
	 * 移除存储的物品
	 *
	 * @param name 保存的物品名
	 */
	fun removeItem(name: String) {
		itemsCache.remove(name)
		items[name] = null
	}

	/**
	 * 获取所有存储物品的名字
	 *
	 * @return 存储物品名字列表
	 */
	fun listItems(): List<String> {
		return itemsCache.keys.toList()
	}
}