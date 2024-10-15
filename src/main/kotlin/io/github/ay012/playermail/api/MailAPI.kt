package io.github.ay012.playermail.api

import io.github.ay012.playermail.data.PlayerData
import java.util.UUID

abstract class MailAPI {

	/**
	 * 发送邮件
	 * @param uuid 收件人
	 * @param id 邮件id
	 * @param sender 发件人
	 * @param expireTime 过期时间
	 */
	abstract fun sendMail(uuid: UUID, id: String, sender: String = "", expireTime: Long = 0)

	/**
	 * 加载玩家目前邮件数据
	 *
	 * @param uuid 目标玩家
	 */
	internal abstract fun loadUser(uuid: UUID)

	/**
	 * 查询数据
	 *
	 * @param uuid 目标玩家
	 */
	internal abstract fun selectUser(uuid: UUID) : MutableList<PlayerData>

	/**
	 * 将缓存数据写入存储
	 */
	internal abstract fun saveCache()
}