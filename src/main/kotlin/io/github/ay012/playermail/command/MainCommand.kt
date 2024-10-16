package io.github.ay012.playermail.command

import io.github.ay012.playermail.command.impl.CommandSaveItem
import io.github.ay012.playermail.command.impl.CommandSend
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader


@CommandHeader(
	name = "PlayerMail",
	aliases = ["mail"],
	permission = "PlayerMail.use"
)
object MainCommand {

	@CommandBody(permission = "playermail.admin.send")
	val send = CommandSend.command

	@CommandBody(permission = "playermail.admin.saveItem")
	val saveItem = CommandSaveItem.command
}