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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rodina.mobilebot.data.AutomationProfile
import com.rodina.mobilebot.data.AutomationUiState
import com.rodina.mobilebot.ui.theme.RodinaMobileBotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startProfiles = listOf(
            AutomationProfile(
                id = "server_1",
                name = "Server Farm #1",
                ipAddress = "192.168.1.12",
                port = "25565",
                password = "admin123",
                method = "Connect → password → idle loop",
                repeatCount = 3,
                delayMs = 2000L
            ),
            AutomationProfile(
                id = "server_2",
                name = "AFK Login #2",
                ipAddress = "10.0.0.26",
                port = "8080",
                password = "botpass",
                method = "Reconnect after timeout",
                repeatCount = 5,
                delayMs = 1500L
            )
        )

        setContent {
            val context = LocalContext.current
            var isRunning by remember { mutableStateOf(false) }
            var state by remember {
                mutableStateOf(
                    AutomationUiState(
                        profiles = startProfiles,
                        currentProfileId = startProfiles.first().id,
                        isRunning = false,
                        logs = listOf(
                            "System ready",
                            "Accessibility service enabled",
                            "Waiting for a profile to start"
                        )
                    )
                )
            }

            RodinaMobileBotTheme {
                MainScreen(
                    state = state,
                    onStart = {
                        isRunning = true
                        state = state.copy(
                            isRunning = true,
                            logs = listOf(
                                "Launching automation...",
                                "Opening session for ${state.currentProfileId ?: "selected profile"}",
                                "Waiting for login and AFK cycle"
                            )
                        )
                        Toast.makeText(context, "Automation started", Toast.LENGTH_SHORT).show()
                    },
                    onAdd = {
                        Toast.makeText(context, "New profile template added", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}

@Composable
fun MainScreen(
    state: AutomationUiState,
    onStart: () -> Unit,
    onAdd: () -> Unit
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
                    onClick = onAdd,
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
                    contentPadding = PaddingValues(bottom = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.profiles) { profile ->
                        ProfileCard(profile)
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Logs",
                    color = Color(0xFFBFD9FF),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0C1628))
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        state.logs.forEach { log ->
                            Text(
                                text = log,
                                color = Color(0xFFBFD9FF),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileCard(profile: AutomationProfile) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF101C2E)
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = profile.name,
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "IP: ${profile.ipAddress}:${profile.port}",
                color = Color(0xFFC7D8F9),
                fontSize = 14.sp
            )

            Text(
                text = "Method: ${profile.method}",
                color = Color(0xFF8AB4FF),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp)
            )

            Text(
                text = "Repeat: ${profile.repeatCount} | Delay: ${profile.delayMs}ms",
                color = Color(0xFF8AB4FF),
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    RodinaMobileBotTheme {
        MainScreen(
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
                        delayMs = 2000L
                    )
                ),
                isRunning = true,
                logs = listOf(
                    "System ready",
                    "Running login flow",
                    "AFK loop active"
                )
            ),
            onStart = {},
            onAdd = {}
        )
    }
}
