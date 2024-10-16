package io.github.ay012.playermail.command.impl

import io.github.ay012.playermail.PlayerMail
import io.github.ay012.playermail.command.CommandExpression
import io.github.ay012.playermail.config.TemplateConfig
import io.github.ay012.playermail.data.PlayerDataManager
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.submitAsync
import taboolib.platform.util.onlinePlayers
import taboolib.platform.util.sendLang

/**
 * 实现发送邮件命令
 */
object CommandSendMail : CommandExpression {

	override val command = subCommand {

		dynamic("邮件唯一标识符") {
			suggestion<CommandSender>(uncheck = true) { _, _ ->
				TemplateConfig.getTemplateCache().keys().toList()
			}
			dynamic("发送目标") {
				suggestion<CommandSender>(uncheck = true) { _, _ ->
					listOf("在线玩家", "所有玩家") + PlayerDataManager.getPlayerALLCache().mapNotNull { it.name }
				}
				dynamic("指定发件人") {
					suggestion<CommandSender>(uncheck = true) { _, _ ->
						listOf("指定发件人(输入me则为自己)")
					}
					execute<CommandSender> { sender, context, _ ->
						val mail = context["邮件唯一标识符"]
						val user = context["发送目标"]
						val senderName = when (context["指定发件人"]) {
							"me" -> sender.name
							"指定发件人(输入me则为自己)" -> return@execute
							else -> context["指定发件人"]
						}
						submitAsync {
							when (user) {
								"所有玩家" -> {
									PlayerDataManager.getPlayerALLCache().forEach { player ->
										PlayerMail.getMailAPI().sendMail(player.uniqueId, mail, senderName)
									}
								}

								"在线玩家" -> {
									onlinePlayers.forEach { player ->
										PlayerMail.getMailAPI().sendMail(player.uniqueId, mail, senderName)
									}
								}

								else -> {
									val player = PlayerDataManager.getPlayerALLCache().find { it.name == user } ?: run {
										sender.sendLang("命令错误-无效玩家", user)
										return@submitAsync
									}
									PlayerMail.getMailAPI().sendMail(player.uniqueId, mail, senderName)
								}
							}
							sender.sendLang("邮件发送-玩家", user, mail)
						}
					}
				}
			}
		}
	}
}
