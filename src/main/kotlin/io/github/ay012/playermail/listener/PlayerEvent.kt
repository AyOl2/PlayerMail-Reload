package io.github.ay012.playermail.listener

import io.github.ay012.playermail.PlayerMail
import io.github.ay012.playermail.data.PlayerDataManager
import org.bukkit.event.player.PlayerJoinEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submitAsync

object PlayerEvent {

	@SubscribeEvent
	private fun onPlayerJoinServer(event: PlayerJoinEvent) {
		val player = event.player

		submitAsync {
			// 载入玩家邮件数据
			PlayerMail.getMailAPI().loadUser(player.uniqueId)
			// 判断玩家缓存中是否存在此玩家
			PlayerDataManager.addPlayer(player)
		}
	}
}