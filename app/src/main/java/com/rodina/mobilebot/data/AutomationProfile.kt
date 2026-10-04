package com.rodina.mobilebot.data

import java.util.UUID

data class AutomationProfile(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val ipAddress: String,
    val port: String,
    val password: String,
    val method: String = "Connect and auto keep alive",
    val repeatCount: Int = 1,
    val delayMs: Long = 2000L,
    val enabled: Boolean = true
)

enum class AutomationType {
    CONNECT,
    TYPE_PASSWORD,
    WAIT,
    TAP,
    SWIPE,
    RECONNECT,
    AFK_LOOP
}

data class AutomationStep(
    val id: String = UUID.randomUUID().toString(),
    val type: AutomationType,
    val label: String,
    val delayMs: Long = 1000L,
    val x: Int? = null,
    val y: Int? = null,
    val text: String? = null
)
