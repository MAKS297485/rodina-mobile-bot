package com.rodina.mobilebot.data

import androidx.compose.runtime.Immutable

@Immutable
data class AutomationUiState(
    val profiles: List<AutomationProfile> = emptyList(),
    val currentProfileId: String? = null,
    val isRunning: Boolean = false,
    val logs: List<String> = emptyList()
)
