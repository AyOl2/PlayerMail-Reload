package io.github.ay012.playermail.util

import io.github.ay012.playermail.data.PlayerData
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import taboolib.module.configuration.Configuration

object SerializationUtils {

	// 序列化工具类入口
	private val jsonUtils = Json {
		prettyPrint = true
		isLenient = true
		ignoreUnknownKeys = true
		coerceInputValues = true
		encodeDefaults = true
		allowStructuredMapKeys = true
		allowSpecialFloatingPointValues = true
	}

	/**
	 * 将 PlayerData 对象列表序列化为 JSON 字符串。
	 * @param data PlayerData 对象的列表。
	 * @return 序列化后的 JSON 字符串。
	 */
	fun serializeMailList(data: MutableList<PlayerData>): String {
		return jsonUtils.encodeToString(ListSerializer(PlayerData.serializer()), data)
	}

	/**
	 * 将 JSON 字符串反序列化为 PlayerData 对象列表。
	 * @param serializedMailList JSON 字符串。
	 * @return 反序列化后的 PlayerData 对象的列表。
	 */
	fun deserializeMailList(serializedMailList: String): MutableList<PlayerData> {
		return jsonUtils.decodeFromString(ListSerializer(PlayerData.serializer()), serializedMailList).toMutableList()
	}

	/**
	 * 将 Configuration 对象转换为 PlayerData 对象。
	 * @param config Configuration 对象。
	 * @return 反序列化后的 PlayerData 对象。
	 */
	fun deserializeConfiguration(config: Configuration): PlayerData {
		return Configuration.deserialize(config, ignoreConstructor = true)
	}
}