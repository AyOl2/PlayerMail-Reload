package io.github.ay012.playermail.data

import kotlinx.serialization.Serializable

@Serializable
data class PlayerData(
	var uuid: String, // 邮件唯一标识符
	val id: String, // 邮件发送唯一标识符
	var sender: String, // 发件人
	val title: String, // 邮件标题
	val icon: String, // 邮件展示物品
	val text: List<String> // 邮件文本
)




