package io.github.ay012.playermail.util

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.*


object ItemStackUtils {

	/**
	 * 将 ItemStack 序列化为 Base64 字符串
	 * @param itemStack 要序列化的物品堆
	 * @return 序列化后的 Base64 字符串
	 * @throws Exception 如果序列化过程中出现错误
	 */
	@Throws(Exception::class)
	fun itemStackToBase64(itemStack: ItemStack): String {
		// 创建字节数组输出流
		val byteArrayOutputStream = ByteArrayOutputStream()
		// 使用 BukkitObjectOutputStream 将 ItemStack 序列化为字节数组
		BukkitObjectOutputStream(byteArrayOutputStream).use { it.writeObject(itemStack) }
		// 将字节数组编码为 Base64 字符串
		return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray())
	}

	/**
	 * 将 Base64 字符串反序列化为 ItemStack
	 * @param data 要反序列化的 Base64 字符串
	 * @return 反序列化后的物品堆
	 * @throws Exception 如果反序列化过程中出现错误
	 */
	@Throws(Exception::class)
	fun base64ToItemStack(data: String): ItemStack {
		// 将 Base64 字符串解码为字节数组
		val bytes = Base64.getDecoder().decode(data)
		// 使用 ByteArrayInputStream 创建字节数组输入流
		ByteArrayInputStream(bytes).use { byteArrayInputStream ->
			// 使用 BukkitObjectInputStream 将字节数组反序列化为 ItemStack
			return BukkitObjectInputStream(byteArrayInputStream).use { it.readObject() as ItemStack }
		}
	}
}