package com.rodina.mobilebot.engine

import com.rodina.mobilebot.data.AutomationProfile
import com.rodina.mobilebot.data.AutomationStep
import com.rodina.mobilebot.data.AutomationType

object AutomationEngine {
    fun buildDefaultScenario(profile: AutomationProfile): List<AutomationStep> = listOf(
        AutomationStep(
            type = AutomationType.CONNECT,
            label = "Connect to ${profile.ipAddress}:${profile.port}",
            delayMs = profile.delayMs
        ),
        AutomationStep(
            type = AutomationType.TYPE_PASSWORD,
            label = "Type saved password",
            text = profile.password,
            delayMs = 800L
        ),
        AutomationStep(
            type = AutomationType.WAIT,
            label = "Wait for login screen",
            delayMs = 2500L
        ),
        AutomationStep(
            type = AutomationType.AFK_LOOP,
            label = "Run AFK circle loop",
            delayMs = 4000L
        )
    )

    fun execute(profile: AutomationProfile, onLog: (String) -> Unit) {
        val steps = if (profile.steps.isEmpty()) buildDefaultScenario(profile) else profile.steps
        onLog("Profile selected: ${profile.name}")
        steps.forEachIndexed { index, step ->
            onLog("Step ${index + 1}: ${step.label} [${step.type.name}]")
            if (step.delayMs > 0L) {
                Thread.sleep(step.delayMs)
            }
        }
        onLog("Scenario finished for ${profile.name}")
    }
}
