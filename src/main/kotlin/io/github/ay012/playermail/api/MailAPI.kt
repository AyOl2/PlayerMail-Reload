package io.github.ay012.playermail.api

import io.github.ay012.playermail.data.PlayerData
import java.util.*

interface MailAPI {

	/**
	 * 发送邮件
	 * @param uuid 收件人
	 * @param id 邮件id
	 * @param sender 发件人
	 */
	fun sendMail(uuid: UUID, id: String, sender: String)

	/**
	 * 加载玩家目前邮件数据
	 *
	 * @param uuid 目标玩家
	 */
	fun loadUser(uuid: UUID)

	/**
	 * 查询邮件数据
	 *
	 * @param uuid 目标玩家
	 */
	fun selectUser(uuid: UUID) : MutableList<PlayerData>

	/**
	 * 将缓存数据写入存储
	 */
	fun saveCache()
}