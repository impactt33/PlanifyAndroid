package com.example.planify.main.common.network.ktor.plugins

import android.util.Log
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request

object KtorResponseLoggerPlugin {
    val plugin = createClientPlugin("ktorResponseLoggerPlugin") {
        onResponse { response ->
            val body = response.bodyAsText()
            val url = response.request.url

            Log.d("ktorResponseLoggerPlugin", "RESPONSE $url; Body: $body")
        }
    }
}
