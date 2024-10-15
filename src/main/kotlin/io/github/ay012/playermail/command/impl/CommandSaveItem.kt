package io.github.ay012.playermail.command.impl

import io.github.ay012.playermail.command.CommandExpression
import io.github.ay012.playermail.util.ItemStackUtils
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

object CommandSaveItem : CommandExpression{

	@Config("save/items.yml")
	lateinit var items: ConfigFile

	override val command = subCommand {

		dynamic("物品名") {
			execute<CommandSender> { sender, context, _ ->

				if (sender !is Player) return@execute

				val itemStack = sender.inventory.itemInMainHand

				if (itemStack.type == Material.AIR) {
					sender.sendMessage("§c你没有手持物品!")
					return@execute
				}
				try {
					// 将 ItemStack 序列化为 Base64 字符串
					items[context["物品名"]] = ItemStackUtils.itemStackToBase64(itemStack)
					items.saveToFile()
					sender.sendMessage("保存成功")
				} catch (ex: Exception) {
					ex.printStackTrace()
					sender.sendMessage("§c保存物品时出错")
				}
			}
		}

		execute<CommandSender> { sender, _, _ ->
			if (sender !is Player) return@execute

			val serializedItemString = items.getString("木头")

			if (serializedItemString == null) {
				sender.sendMessage("§c未找到该物品!")
				return@execute
			}
			try {
				// 将 Base64 字符串反序列化为 ItemStack
				sender.inventory.addItem(ItemStackUtils.base64ToItemStack(serializedItemString))
				sender.sendMessage("物品已放入背包")
			} catch (ex: Exception) {
				ex.printStackTrace()
				sender.sendMessage("§c加载物品时出错")
			}
		}
	}
}