package io.github.ay012.playermail.config

import io.github.ay012.playermail.PlayerMail.say
import io.github.ay012.playermail.util.TimeUtils
import taboolib.common.platform.function.getDataFolder
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.module.configuration.Configuration
import taboolib.module.configuration.Type
import java.io.File
import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

object TemplateConfig {

	@Config("template/示例模板邮件.yml")
	private lateinit var config: ConfigFile

	private val templateCache = ConcurrentHashMap<String, Configuration>() // 所有的邮件数据

	val getTemplateCache = templateCache

	// 初始化方法，加载所有模板文件
	fun init() {
		this.template()
	}

	/**
	 * 加载文件夹下所有的模板文件
	 */
	private fun template() {
		TimeUtils.measureTimeSeconds {
			templateCache.clear()

			val loadedIds = ConcurrentHashMap.newKeySet<String>()
			val templateFolder = File(getDataFolder(), "template")

			templateFolder.listFiles { file -> file.extension == "yml" }?.toList()?.parallelStream()?.forEach { file ->
				val config = Configuration.loadFromFile(file, Type.YAML)

				// 判断是否存在唯一标识符
				val id = config.getString("id") ?: run {
					say("&8[&cWARN&8] &c${file.name} 加载失败 缺少唯一标识符")
					return@forEach
				}

				//检查是否存在重复的ID
				if (!loadedIds.add(id)) {
					say("&8[&cWARN&8] &c${file.name} 加载失败 重复的唯一标识符")
					return@forEach
				}
				templateCache[id] = config
			}
		}.also {
			say("&8[&fINFO&8] &a${templateCache.size} &3个模板被载入至缓存... &8(总耗时 $it s)")
		}
	}
}