package io.github.ay012.playermail.hook

import ink.ptms.um.Mythic
import org.bukkit.entity.Player

object MythicMobsHook {

	private val mythic = Mythic.API

	/**
	 * 获取指定的mm物品库内的物品
	 *
	 * @param name 物品名
	 * @param player 目标玩家
	 * @param amount 数量
	 */
	fun getItems(name: String, player: Player, amount: Int) {
		val itemStack = mythic.getItemStack(name) ?: return
		itemStack.amount = amount
		player.inventory.addItem(itemStack)
	}
}