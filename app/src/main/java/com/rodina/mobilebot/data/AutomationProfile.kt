package com.rodina.mobilebot.data

data class AutomationProfile(
    val id: String,
    val name: String,
    val ipAddress: String,
    val port: String,
    val password: String,
    val method: String
)
