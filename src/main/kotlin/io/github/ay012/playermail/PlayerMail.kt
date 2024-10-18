package io.github.ay012.playermail

import io.github.ay012.playermail.api.MailAPI
import io.github.ay012.playermail.config.SaveItemConfig
import io.github.ay012.playermail.config.SettingsConfig
import io.github.ay012.playermail.config.TemplateConfig
import io.github.ay012.playermail.data.PlayerDataManager
import io.github.ay012.playermail.storage.YAML
import taboolib.common.env.RuntimeDependencies
import taboolib.common.env.RuntimeDependency
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.console
import taboolib.module.chat.colored

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
    private lateinit var mailAPI : MailAPI

    fun getMailAPI(): MailAPI {
        return mailAPI
    }

    override fun onEnable() {
        init()
        mailAPI = YAML()
    }

    override fun onDisable() {
        // 将缓存数据写入存储
        mailAPI.saveCache()
    }

    fun say(string: String) = console().sendMessage("§8[§6Player§eMail§8]${string.colored()}")

    private fun init() {
        SettingsConfig.init()
        PlayerDataManager.init()
        SaveItemConfig.init()
        TemplateConfig.init()
    }
}