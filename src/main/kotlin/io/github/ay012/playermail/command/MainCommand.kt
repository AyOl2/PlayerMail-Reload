package io.github.ay012.playermail.command

import io.github.ay012.playermail.command.impl.CommandReload
import io.github.ay012.playermail.command.impl.CommandSaveItem
import io.github.ay012.playermail.command.impl.CommandSendMail
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.mainCommand
import taboolib.platform.util.sendLang


@CommandHeader(
	name = "PlayerMail",
	aliases = ["mail"],
	permission = "PlayerMail.use"
)
object MainCommand {

	@CommandBody
	val main = mainCommand {
		execute<CommandSender> { sender, _, _ ->
			if (sender.isOp) {
				sender.sendLang("管理员指令帮助")
				return@execute
			}
			sender.sendLang("玩家指令帮助")
		}
	}

	@CommandBody(permission = "playermail.admin.reload")
	val reload = CommandReload.command

	@CommandBody(permission = "playermail.admin.sendMail")
	val sendmail = CommandSendMail.command

	@CommandBody(permission = "playermail.admin.saveItem")
	val saveItem = CommandSaveItem.command
}