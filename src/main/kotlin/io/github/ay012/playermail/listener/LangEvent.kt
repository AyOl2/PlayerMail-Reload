package io.github.ay012.playermail.listener

import io.github.ay012.playermail.PlayerMail
import taboolib.common.platform.event.SubscribeEvent
import taboolib.module.lang.event.PlayerSelectLocaleEvent
import taboolib.module.lang.event.SystemSelectLocaleEvent

object LangEvent {

	@SubscribeEvent
	fun lang(event: PlayerSelectLocaleEvent) {
		event.locale = PlayerMail.settings.getString("lang", "zh_CN")!!
	}

	@SubscribeEvent
	fun lang(event: SystemSelectLocaleEvent) {
		event.locale = PlayerMail.settings.getString("lang", "zh_CN")!!
	}
}