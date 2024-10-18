package io.github.ay012.playermail.command.impl

import io.github.ay012.playermail.command.CommandExpression
import io.github.ay012.playermail.config.SaveItemConfig
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.player
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang

object CommandItem : CommandExpression {
	override val command = subCommand {

		literal("save") {
			dynamic("物品名") {
				suggestion<CommandSender>(uncheck = true) { _, _ ->
					listOf("输入保存的物品名")
				}
				execute<CommandSender> { sender, context, _ ->
					if (sender !is Player) return@execute

					val saveName = if (context["物品名"] == "输入保存的物品名") return@execute else context["物品名"]

					if (sender.inventory.itemInMainHand.type == Material.AIR) {
						sender.sendLang("物品-保存-未手持")
						return@execute
					}
					try {
						SaveItemConfig.saveItem(saveName, sender.inventory.itemInMainHand)
						sender.sendLang("物品-保存-成功", saveName)
					} catch (ex: Exception) {
						sender.sendLang("物品-保存-失败", saveName)
						ex.printStackTrace()
					}
				}
			}
		}

		literal("list") {
			execute<CommandSender> { sender, _, _ ->
				sender.sendLang("物品-展示-列表", SaveItemConfig.listItems().joinToString())
			}
		}

		literal("remove") {
			dynamic("输入保存的物品名") {
				suggestion<CommandSender>(uncheck = true) { _, _ ->
					SaveItemConfig.listItems()
				}
				execute<CommandSender> { sender, context, _ ->

					val saveName = context["输入保存的物品名"]

					if (SaveItemConfig.getItemsCache()[saveName] != null) {
						SaveItemConfig.removeItem(saveName)
						sender.sendLang("物品-删除-成功", saveName)
					} else {
						sender.sendLang("物品-错误-不存在", saveName)
					}
				}
			}
		}

		literal("get") {
			dynamic("输入保存的物品名") {
				suggestion<CommandSender>(uncheck = true) { _, _ ->
					SaveItemConfig.listItems()
				}

				player("发送目标") {
					execute<CommandSender> { sender, context, _ ->
						val saveName = context["输入保存的物品名"]
						if (SaveItemConfig.getItemsCache()[saveName] != null) {
							Bukkit.getPlayer(context.player("发送目标").uniqueId)?.let { SaveItemConfig.getItem(saveName, it) }
							sender.sendLang("物品-给予-玩家", saveName)
						} else {
							sender.sendLang("物品-错误-不存在", saveName)
						}
					}
				}
			}
		}
	}
}






