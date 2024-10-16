package io.github.ay012.playermail.api

import io.github.ay012.playermail.data.PlayerData
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

abstract class MailAPI {

	/**
	 * 发送邮件
	 * @param uuid 收件人
	 * @param id 邮件id
	 * @param sender 发件人
	 */
	abstract fun sendMail(uuid: UUID, id: String, sender: String = "")

	/**
	 * 加载玩家目前邮件数据
	 *
	 * @param uuid 目标玩家
	 */
	internal abstract fun loadUser(uuid: UUID)

	/**
	 * 查询邮件数据
	 *
	 * @param uuid 目标玩家
	 */
	internal abstract fun selectUser(uuid: UUID) : MutableList<PlayerData>

	/**
	 * 将缓存数据写入存储
	 */
	internal abstract fun saveCache()

	/**
	 * 将物品写入存储
	 *
	 * @param name 保存的物品名
	 * @param item 需要操作的物品
	 */
	internal abstract fun saveItems(name: String, item: ItemStack)

	/**
	 * 将保存的物品给予给玩家
	 *
	 * @param name 保存的物品名
	 * @param player 需要操作的玩家
	 */
	internal abstract fun getItems(name: String, player: Player)
}