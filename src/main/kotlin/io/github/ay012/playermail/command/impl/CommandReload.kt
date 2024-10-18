package io.github.ay012.playermail.command.impl

import io.github.ay012.playermail.command.CommandExpression
import io.github.ay012.playermail.config.SettingsConfig
import io.github.ay012.playermail.config.TemplateConfig
import io.github.ay012.playermail.util.TimeUtils
import org.bukkit.command.CommandSender
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.submitAsync
import taboolib.platform.util.sendLang

object CommandReload : CommandExpression {

	override val command = subCommand {
		execute<CommandSender> { sender, _, _ ->
			TimeUtils.measureTimeSeconds{
				submitAsync {
					SettingsConfig.init()
					TemplateConfig.init()
				}
			}.also {
				sender.sendLang("插件配置-重载", it)
			}
		}
	}
}