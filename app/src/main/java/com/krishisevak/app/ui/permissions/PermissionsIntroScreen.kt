package com.krishisevak.app.ui.permissions

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.krishisevak.app.utils.AppStrings
import com.krishisevak.app.utils.LocationHelper
import com.krishisevak.app.utils.TtsManager
import kotlinx.coroutines.delay

@Composable
fun PermissionsIntroScreen(
    userLanguageCode: String,
    ttsManager: TtsManager,
    locationHelper: LocationHelper,
    onPermissionsComplete: () -> Unit
) {
    val scrollState = rememberScrollState()
    val speechText = AppStrings.get("perm_tts_speech", userLanguageCode)
    val currentlySpeakingId by ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()
    val isSpeaking = currentlySpeakingId == "perm_intro_speech"

    // Launcher for Android runtime permissions
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        ttsManager.stop()
        onPermissionsComplete()
    }

    // Auto-start speaking in the user's selected language upon screen launch
    LaunchedEffect(userLanguageCode) {
        delay(600)
        ttsManager.speak("perm_intro_speech", speechText, userLanguageCode)
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsManager.stop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF09140C),
                        Color(0xFF0D1C12),
                        Color(0xFF060D08)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Shield / Security Header Icon
            Surface(
                modifier = Modifier.size(72.dp),
                shape = CircleShape,
                color = Color(0xFF16A34A).copy(alpha = 0.15f),
                border = BorderStroke(2.dp, Color(0xFF16A34A).copy(alpha = 0.4f))
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Security Shield",
                        tint = Color(0xFF4ADE80),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Main Title in Selected Language
            Text(
                text = AppStrings.get("perm_intro_title", userLanguageCode),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 28.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = AppStrings.get("perm_intro_subtitle", userLanguageCode),
                fontSize = 13.sp,
                color = Color(0xFFA0B2A6),
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Audio Speak / Pause Button Chip
            Surface(
                onClick = {
                    if (isSpeaking) {
                        ttsManager.stop()
                    } else {
                        ttsManager.speak("perm_intro_speech", speechText, userLanguageCode)
                    }
                },
                shape = RoundedCornerShape(20.dp),
                color = if (isSpeaking) Color(0xFF165A36) else Color(0xFF1F2E23),
                border = BorderStroke(1.dp, Color(0xFF2E8C56).copy(alpha = 0.6f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Voice Guide",
                        tint = Color(0xFF4ADE80),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = if (isSpeaking) "Playing Voice Guide..." else AppStrings.get("perm_audio_guide", userLanguageCode),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 1. Location Permission Card
            PermissionCard(
                icon = Icons.Default.LocationOn,
                iconTint = Color(0xFF38BDF8),
                iconBg = Color(0xFF0369A1).copy(alpha = 0.2f),
                title = AppStrings.get("perm_loc_title", userLanguageCode),
                description = AppStrings.get("perm_loc_desc", userLanguageCode),
                borderColor = Color(0xFF0284C7).copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 2. Camera Permission Card
            PermissionCard(
                icon = Icons.Default.CameraAlt,
                iconTint = Color(0xFFF87171),
                iconBg = Color(0xFF991B1B).copy(alpha = 0.2f),
                title = AppStrings.get("perm_cam_title", userLanguageCode),
                description = AppStrings.get("perm_cam_desc", userLanguageCode),
                borderColor = Color(0xFFDC2626).copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 3. Microphone Permission Card
            PermissionCard(
                icon = Icons.Default.Mic,
                iconTint = Color(0xFFA78BFA),
                iconBg = Color(0xFF5B21B6).copy(alpha = 0.2f),
                title = AppStrings.get("perm_mic_title", userLanguageCode),
                description = AppStrings.get("perm_mic_desc", userLanguageCode),
                borderColor = Color(0xFF7C3AED).copy(alpha = 0.3f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Tip Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF142418),
                border = BorderStroke(1.dp, Color(0xFF1E3D26))
            ) {
                Text(
                    text = AppStrings.get("perm_how_to_allow_tip", userLanguageCode),
                    fontSize = 12.sp,
                    color = Color(0xFF86EFAC),
                    modifier = Modifier.padding(12.dp),
                    lineHeight = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Continue & Allow Button
            Button(
                onClick = {
                    ttsManager.stop()
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A)),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Text(
                    text = AppStrings.get("perm_continue_btn", userLanguageCode),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}

@Composable
private fun PermissionCard(
    icon: ImageVector,
    iconTint: Color,
    iconBg: Color,
    title: String,
    description: String,
    borderColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF111E16)),
        border = BorderStroke(1.2.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                modifier = Modifier.size(44.dp),
                shape = CircleShape,
                color = iconBg
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconTint,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = description,
                    fontSize = 12.sp,
                    color = Color(0xFFA8B8AE),
                    lineHeight = 16.sp
                )
            }
        }
    }
}
