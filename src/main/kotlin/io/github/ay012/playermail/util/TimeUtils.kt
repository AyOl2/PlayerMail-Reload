package io.github.ay012.playermail.util

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

object TimeUtils {

	/**
	 * 测量执行块的时间（秒）
	 * @param block 要执行的代码块
	 * @return 执行时间（秒）
	 */
	@OptIn(ExperimentalContracts::class)
	inline fun measureTimeSeconds(block: () -> Unit): Double {
		contract {
			callsInPlace(block, InvocationKind.EXACTLY_ONCE)
		}
		val start = System.currentTimeMillis()
		block()
		return (System.currentTimeMillis() - start) / 1000.0
	}
}