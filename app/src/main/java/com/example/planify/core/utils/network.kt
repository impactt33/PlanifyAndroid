package com.example.planify.core.utils

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

class RetryScope(
    val currentAttempt: Int,
)

suspend fun <T> retrying(
    attempts: Int,
    initialDelayMs: Long = 100,
    backoffFactor: Double = 2.0,
    shouldRetry: (Exception) -> Boolean = { true },
    tag: String = "Retry",
    block: suspend RetryScope.() -> T
): T {
    require(attempts >= 1) { "attempts must be >= 1" }

    var delayMs = initialDelayMs
    var lastException: Exception? = null

    repeat(attempts) { idx ->
        val scope = RetryScope(currentAttempt = idx)

        try {
            return block.invoke(scope)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            lastException = e
            if (!shouldRetry(e) || idx == attempts - 1) throw e

            Log.e(tag, "Attempt ${idx + 1}/$attempts failed: ${e::class.simpleName}: ${e.message}")

            delay(delayMs)
            delayMs = (delayMs * backoffFactor).toLong()
        }
    }

    throw lastException ?: IllegalStateException("retrying ended unexpectedly")
}