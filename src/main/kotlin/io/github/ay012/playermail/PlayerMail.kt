package io.github.ay012.playermail

import io.github.ay012.playermail.api.MailAPI
import io.github.ay012.playermail.data.MailDataManager
import io.github.ay012.playermail.data.PlayerDataManager
import io.github.ay012.playermail.storage.YAML
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.chat.colored
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

@RuntimeDependencies(
    RuntimeDependency(
        value = "org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.2",
        test = "!kotlinx.serialization.Serializer",
        relocate = ["!kotlin.", "!kotlin1922."],
    ),
    RuntimeDependency(
        value = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2",
        relocate = ["!kotlin.", "!kotlin1922."],
    )
)
object PlayerMail : Plugin() {

    @Config("settings.yml")
    lateinit var settings: ConfigFile

    @Config("template/示例模板邮件.yml")
    lateinit var template: ConfigFile

    private lateinit var mailAPI : MailAPI

    val getMailAPI
        get() = mailAPI

    override fun onEnable() {
        mailAPI = YAML() // 载入存储方式
        this.init() // 载入初始化
    }

    override fun onDisable() {
        mailAPI.saveCache() // 将缓存数据写入存储
    }

    fun say(string: String) = console().sendMessage("§8[§6Player§eMail§8]${string.colored()}")

    private fun init() {
        PlayerDataManager.init()
        MailDataManager.init()
    }
}