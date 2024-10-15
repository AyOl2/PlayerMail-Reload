package io.github.ay012.playermail.command.impl

import io.github.ay012.playermail.command.CommandExpression
import io.github.ay012.playermail.data.MailDataManager
import io.github.ay012.playermail.data.PlayerDataManager
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand

/**
 * 实现发送邮件命令
 */
object CommandSend : CommandExpression {

	override val command = subCommand {

		dynamic("邮件唯一标识符") {
			suggestion<CommandSender>(uncheck = true) { _, _ ->
				MailDataManager.getTemplateCache.keys().toList()
			}
			dynamic("发送目标") {
				suggestion<CommandSender>(uncheck = true) { _, _ ->
					listOf("online", "all") + PlayerDataManager.getPlayerALLCache.mapNotNull { it.name }
				}
				dynamic("指定发件人") {
					suggestion<CommandSender>(uncheck = true) { _, _ ->
						listOf("me")
					}
					dynamic("到期时间") {
						suggestion<CommandSender>(uncheck = true) { _, _ ->
							listOf("none")
						}
						execute<CommandSender> { sender, context, _ ->
							val mail = context["邮件唯一标识符"]
							val user = context["发送目标"]
							val senderName = if (context["指定发件人"] == "me") sender.name else context["指定发件人"]


						}
					}
				}
			}
		}
	}
}
