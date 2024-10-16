package io.github.ay012.playermail.command.impl

import io.github.ay012.playermail.command.CommandExpression
import io.github.ay012.playermail.config.SettingsConfig
import io.github.ay012.playermail.config.TemplateConfig
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.platform.util.sendLang
import kotlin.system.measureTimeMillis

object CommandReload : CommandExpression {

	override val command = subCommand {
		execute<CommandSender> { sender, _, _ ->
			measureTimeMillis {
				SettingsConfig.init()
				TemplateConfig.init()
			}.also {
				sender.sendLang("插件配置-重载", it)
			}
		}
	}
}