package io.github.ay012.playermail.command.impl

import io.github.ay012.playermail.PlayerMail
import io.github.ay012.playermail.command.CommandExpression
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

object CommandSaveItem : CommandExpression{

	override val command = subCommand {

		dynamic("物品名") {
			execute<CommandSender> { sender, context, _ ->
				if (sender !is Player) return@execute

				val saveName = context["物品名"]
				val itemStack = sender.inventory.itemInMainHand

				if (itemStack.type == Material.AIR) {
					sender.sendLang("保存物品-未手持")
					return@execute
				}
				try {
					PlayerMail.getMailAPI.saveItems(saveName, itemStack)
					sender.sendLang("保存物品-成功", saveName)
				} catch (ex: Exception) {
					sender.sendLang("保存物品-失败", saveName)
					ex.printStackTrace()
				}
			}
		}
	}
}