package com.rodina.mobilebot

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodina.mobilebot.data.AutomationProfile
import com.rodina.mobilebot.data.AutomationStep
import com.rodina.mobilebot.data.AutomationType
import com.rodina.mobilebot.data.AutomationUiState
import com.rodina.mobilebot.engine.AutomationEngine
import com.rodina.mobilebot.ui.theme.RodinaMobileBotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val defaultProfiles = listOf(
            AutomationProfile(
                id = "server_1",
                name = "Server Farm #1",
                ipAddress = "192.168.1.12",
                port = "25565",
                password = "admin123",
                method = "Connect → password → idle loop",
                repeatCount = 3,
                delayMs = 2000L,
                steps = AutomationEngine.buildDefaultScenario(
                    AutomationProfile(
                        name = "Server Farm #1",
                        ipAddress = "192.168.1.12",
                        port = "25565",
                        password = "admin123"
                    )
                )
            ),
            AutomationProfile(
                id = "server_2",
                name = "AFK Login #2",
                ipAddress = "10.0.0.26",
                port = "8080",
                password = "botpass",
                method = "Reconnect after timeout",
                repeatCount = 5,
                delayMs = 1500L,
                steps = AutomationEngine.buildDefaultScenario(
                    AutomationProfile(
                        name = "AFK Login #2",
                        ipAddress = "10.0.0.26",
                        port = "8080",
                        password = "botpass"
                    )
                )
            )
        )

        setContent {
            val context = LocalContext.current
            var state by remember { mutableStateOf(AutomationUiState(profiles = defaultProfiles, currentProfileId = defaultProfiles.first().id, logs = listOf("System ready", "Accessibility service enabled", "Waiting for a profile to start"))) }
            var editorOpen by remember { mutableStateOf(false) }
            var draft by remember { mutableStateOf(defaultProfiles.first()) }

            RodinaMobileBotTheme {
                AppScreen(
                    state = state,
                    editorOpen = editorOpen,
                    draft = draft,
                    onStart = {
                        val profile = state.profiles.find { it.id == state.currentProfileId } ?: state.profiles.firstOrNull()
                        if (profile == null) {
                            Toast.makeText(context, "No profile selected", Toast.LENGTH_SHORT).show()
                            return@AppScreen
                        }

                        state = state.copy(isRunning = true, logs = listOf("Launching automation...", "Opening session for ${profile.name}", "Running scenario..."))
                        Toast.makeText(context, "Automation started", Toast.LENGTH_SHORT).show()

                        Thread {
                            AutomationEngine.execute(profile) { log ->
                                state = state.copy(logs = listOf(log) + state.logs.take(6))
                            }
                            state = state.copy(isRunning = false, logs = listOf("Scenario completed for ${profile.name}") + state.logs.take(6))
                        }.start()
                    },
                    onAddProfile = {
                        val newProfile = AutomationProfile(
                            name = "New profile",
                            ipAddress = "127.0.0.1",
                            port = "25565",
                            password = "",
                            method = "Custom loop",
                            repeatCount = 1,
                            delayMs = 1000L,
                            steps = listOf(
                                AutomationStep(
                                    type = AutomationType.CONNECT,
                                    label = "Connect",
                                    delayMs = 1000L
                                )
                            )
                        )
                        draft = newProfile
                        editorOpen = true
                    },
                    onEditProfile = { profile ->
                        draft = profile
                        editorOpen = true
                    },
                    onSaveProfile = { updatedProfile ->
                        val current = state.profiles.toMutableList()
                        val existingIndex = current.indexOfFirst { it.id == updatedProfile.id }
                        if (existingIndex >= 0) {
                            current[existingIndex] = updatedProfile
                        } else {
                            current.add(0, updatedProfile)
                        }
                        state = state.copy(profiles = current, currentProfileId = updatedProfile.id)
                        editorOpen = false
                    },
                    onDismissEditor = { editorOpen = false }
                )
            }
        }
    }
}

@Composable
fun AppScreen(
    state: AutomationUiState,
    editorOpen: Boolean,
    draft: AutomationProfile,
    onStart: () -> Unit,
    onAddProfile: () -> Unit,
    onEditProfile: (AutomationProfile) -> Unit,
    onSaveProfile: (AutomationProfile) -> Unit,
    onDismissEditor: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF07111F),
                            Color(0xFF0B1830),
                            Color(0xFF050A12)
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Rodina Mobile Bot",
                    color = Color(0xFFEAF4FF),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Android automation for server login and AFK farming",
                    color = Color(0xFF9CB5DC),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 18.dp)
                )

                Button(
                    onClick = onStart,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF0F4CFF),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = if (state.isRunning) "Running..." else "Start automation")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = onAddProfile,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF112541),
                        contentColor = Color(0xFFEAF4FF)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Add profile")
                }

                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "Profiles",
                    color = Color(0xFFBFD9FF),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))

                LazyColumn(
                    contentPadding = PaddingValues(bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.profiles) { profile ->
                        ProfileCard(profile = profile, onEdit = { onEditProfile(profile) })
                    }
                }
            }

            if (editorOpen) {
                ProfileEditorDialog(
                    profile = draft,
                    onDismiss = onDismissEditor,
                    onSave = onSaveProfile
                )
            }
        }
    }
}

@Composable
fun ProfileCard(profile: AutomationProfile, onEdit: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF101C2E))
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = profile.name,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Button(
                    onClick = onEdit,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F4CFF))
                ) {
                    Text("Edit")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text("IP: ${profile.ipAddress}:${profile.port}", color = Color(0xFFC7D8F9), fontSize = 14.sp)
            Text("Method: ${profile.method}", color = Color(0xFF8AB4FF), fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp))
            Text("Repeat: ${profile.repeatCount} | Delay: ${profile.delayMs}ms", color = Color(0xFF8AB4FF), fontSize = 12.sp, modifier = Modifier.padding(top = 6.dp))
        }
    }
}

@Composable
fun ProfileEditorDialog(
    profile: AutomationProfile,
    onDismiss: () -> Unit,
    onSave: (AutomationProfile) -> Unit
) {
    var localName by remember(profile.id) { mutableStateOf(profile.name) }
    var localIp by remember(profile.id) { mutableStateOf(profile.ipAddress) }
    var localPort by remember(profile.id) { mutableStateOf(profile.port) }
    var localPassword by remember(profile.id) { mutableStateOf(profile.password) }
    var localMethod by remember(profile.id) { mutableStateOf(profile.method) }
    var localRepeat by remember(profile.id) { mutableStateOf(profile.repeatCount.toString()) }
    var localDelay by remember(profile.id) { mutableStateOf(profile.delayMs.toString()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99070B14)),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF101C2E))
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Edit profile", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)

                OutlinedTextField(value = localName, onValueChange = { localName = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = localIp, onValueChange = { localIp = it }, label = { Text("IP address") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = localPort, onValueChange = { localPort = it }, label = { Text("Port") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(value = localPassword, onValueChange = { localPassword = it }, label = { Text("Password") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = localMethod, onValueChange = { localMethod = it }, label = { Text("Method") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = localRepeat, onValueChange = { localRepeat = it }, label = { Text("Repeat count") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                OutlinedTextField(value = localDelay, onValueChange = { localDelay = it }, label = { Text("Delay ms") }, modifier = Modifier.fillMaxWidth(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2A3A51))
                    ) { Text("Cancel") }

                    Button(
                        onClick = {
                            val updated = profile.copy(
                                name = localName,
                                ipAddress = localIp,
                                port = localPort,
                                password = localPassword,
                                method = localMethod,
                                repeatCount = localRepeat.toIntOrNull() ?: profile.repeatCount,
                                delayMs = localDelay.toLongOrNull() ?: profile.delayMs
                            )
                            onSave(updated)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0F4CFF))
                    ) { Text("Save") }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    RodinaMobileBotTheme {
        AppScreen(
            state = AutomationUiState(
                profiles = listOf(
                    AutomationProfile(
                        id = "preview_1",
                        name = "Preview Server",
                        ipAddress = "192.168.0.42",
                        port = "25565",
                        password = "preview",
                        method = "Connect and idle",
                        repeatCount = 2,
                        delayMs = 2000L,
                        steps = AutomationEngine.buildDefaultScenario(
                            AutomationProfile(
                                name = "Preview Server",
                                ipAddress = "192.168.0.42",
                                port = "25565",
                                password = "preview"
                            )
                        )
                    )
                ),
                currentProfileId = "preview_1",
                isRunning = true,
                logs = listOf("System ready", "Running login flow", "AFK loop active")
            ),
            editorOpen = false,
            draft = AutomationProfile(
                name = "Preview Server",
                ipAddress = "192.168.0.42",
                port = "25565",
                password = "preview"
            ),
            onStart = {},
            onAddProfile = {},
            onEditProfile = {},
            onSaveProfile = {},
            onDismissEditor = {}
        )
    }
}
