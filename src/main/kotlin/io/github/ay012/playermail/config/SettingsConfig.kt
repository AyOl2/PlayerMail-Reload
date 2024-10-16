package io.github.ay012.playermail.config

import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile

object SettingsConfig {

	@Config("settings.yml", autoReload = true)
	private lateinit var config: ConfigFile

	lateinit var lang: String
	lateinit var yaml_path: String
	var autoSaveCacheTime: Long = 20 * 1200

	fun init() {
		yaml_path = config.getString("storage.yaml.path") ?: "users"
		lang = config.getString("lang") ?: "zh_CN"
		autoSaveCacheTime = config.getLong("auto-save-cache-time", 20) * 1200
	}
}