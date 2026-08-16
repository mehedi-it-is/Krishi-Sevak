package com.krishisevak.app.ui.onboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.krishisevak.app.utils.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onOnboardingComplete: () -> Unit
) {
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val userName by viewModel.userName.collectAsStateWithLifecycle()

    var showOtpDialog by remember { mutableStateOf(false) }
    var showQuickStartDialog by remember { mutableStateOf(false) }
    var inputName by remember { mutableStateOf(userName) }
    var phoneInput by remember { mutableStateOf("") }
    var otpInput by remember { mutableStateOf("") }
    var otpSent by remember { mutableStateOf(false) }

    // Automatically start line-by-line speech in Hindi on screen launch
    DisposableEffect(Unit) {
        viewModel.startAutoSpeech()
        onDispose {
            viewModel.stopAutoSpeech()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0B0F0C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 28.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Circular Illustration matching screenshot
            Box(
                modifier = Modifier
                    .size(220.dp)
                    .background(Color(0xFF13462D), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(190.dp)) {
                    val w = size.width
                    val h = size.height

                    // Subtle ground shadow mound
                    drawOval(
                        color = Color(0xFF0A2E1C),
                        topLeft = Offset(w * 0.32f, h * 0.69f),
                        size = Size(w * 0.36f, h * 0.08f)
                    )

                    // Sun with rays on top-left
                    val sunCenter = Offset(w * 0.33f, h * 0.32f)
                    val sunRadius = w * 0.07f
                    drawCircle(
                        color = Color(0xFFD4E157),
                        radius = sunRadius,
                        center = sunCenter
                    )

                    // Sun rays
                    val rayColor = Color(0xFFD4E157)
                    val rayLen = w * 0.035f
                    val rayDist = sunRadius + 6f
                    val rayAngles = listOf(45.0, 90.0, 135.0, 180.0, 225.0, 270.0, 315.0, 360.0)
                    for (ang in rayAngles) {
                        val rad = Math.toRadians(ang)
                        val startX = (sunCenter.x + Math.cos(rad) * rayDist).toFloat()
                        val startY = (sunCenter.y + Math.sin(rad) * rayDist).toFloat()
                        val endX = (sunCenter.x + Math.cos(rad) * (rayDist + rayLen)).toFloat()
                        val endY = (sunCenter.y + Math.sin(rad) * (rayDist + rayLen)).toFloat()
                        drawLine(
                            color = rayColor,
                            start = Offset(startX, startY),
                            end = Offset(endX, endY),
                            strokeWidth = 2.5f,
                            cap = StrokeCap.Round
                        )
                    }

                    // Background darker leaf (reaching upwards)
                    val backLeaf = Path().apply {
                        moveTo(w * 0.50f, h * 0.44f)
                        cubicTo(
                            w * 0.50f, h * 0.30f,
                            w * 0.68f, h * 0.28f,
                            w * 0.68f, h * 0.32f
                        )
                        cubicTo(
                            w * 0.68f, h * 0.40f,
                            w * 0.54f, h * 0.48f,
                            w * 0.50f, h * 0.52f
                        )
                        close()
                    }
                    drawPath(path = backLeaf, color = Color(0xFF1E5C3B))

                    // Left leaf (light mint green)
                    val leftLeaf = Path().apply {
                        moveTo(w * 0.48f, h * 0.48f)
                        cubicTo(
                            w * 0.32f, h * 0.42f,
                            w * 0.32f, h * 0.35f,
                            w * 0.46f, h * 0.44f
                        )
                        close()
                    }
                    drawPath(path = leftLeaf, color = Color(0xFF86EFAC))

                    // Right leaf (pale mint white)
                    val rightLeaf = Path().apply {
                        moveTo(w * 0.52f, h * 0.46f)
                        cubicTo(
                            w * 0.66f, h * 0.40f,
                            w * 0.66f, h * 0.34f,
                            w * 0.50f, h * 0.42f
                        )
                        close()
                    }
                    drawPath(path = rightLeaf, color = Color(0xFFD1FAE5))

                    // Center white vertical sprout stem
                    drawLine(
                        color = Color.White,
                        start = Offset(w * 0.50f, h * 0.72f),
                        end = Offset(w * 0.50f, h * 0.40f),
                        strokeWidth = 5.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Main Title (Elegant Serif)
            Text(
                text = "Welcome to Krishi\nSevak",
                fontSize = 28.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Subtitle
            Text(
                text = "How would you like to proceed?",
                fontSize = 14.sp,
                color = Color(0xFFA0B2A6),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(44.dp))

            // Primary Action Button: "Login with OTP"
            Button(
                onClick = {
                    showOtpDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF165A36)),
                border = BorderStroke(1.5.dp, Color(0xFF2E8C56))
            ) {
                Text(
                    text = "Login with OTP",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Secondary Action: "Continue without login"
            TextButton(
                onClick = {
                    showQuickStartDialog = true
                }
            ) {
                Text(
                    text = "Continue without login",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFA3E7BD)
                )
            }
        }

        // OTP Dialog
        if (showOtpDialog) {
            AlertDialog(
                onDismissRequest = { showOtpDialog = false },
                title = { 
                    Text(
                        text = if (otpSent) "OTP Verification" else "Login with OTP",
                        fontWeight = FontWeight.Bold
                    ) 
                },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        if (!otpSent) {
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = "Select Language / भाषा चुनें:",
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                                LazyRow(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    items(viewModel.supportedLanguages) { lang ->
                                        val isSelected = lang.code == selectedLanguage.code
                                        Surface(
                                            onClick = { viewModel.selectLanguage(lang) },
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isSelected) Color(0xFF2E8C56) else Color(0xFFE8F5E9),
                                            border = BorderStroke(
                                                1.dp,
                                                if (isSelected) Color(0xFF2E8C56) else Color(0xFFC8E6C9)
                                            )
                                        ) {
                                            Text(
                                                text = lang.nativeName,
                                                fontSize = 12.sp,
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                color = if (isSelected) Color.White else Color(0xFF1B5E20),
                                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                }
                            }

                            Text("Enter your mobile number:", fontSize = 13.sp)
                            OutlinedTextField(
                                value = phoneInput,
                                onValueChange = { if (it.length <= 10) phoneInput = it },
                                label = { Text("Mobile Number (10 digits)") },
                                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            OutlinedTextField(
                                value = inputName,
                                onValueChange = { inputName = it },
                                label = { Text("Your Name / आपका नाम") },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        } else {
                            Text("OTP sent to +91 $phoneInput (Demo OTP: 1234)", fontSize = 13.sp)
                            OutlinedTextField(
                                value = otpInput,
                                onValueChange = { otpInput = it },
                                label = { Text("4-Digit OTP") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (!otpSent) {
                                if (phoneInput.length >= 10) otpSent = true
                            } else {
                                viewModel.saveOnboarding(inputName, onOnboardingComplete)
                                showOtpDialog = false
                            }
                        }
                    ) {
                        Text(if (!otpSent) "Send OTP" else "Verify & Login")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showOtpDialog = false }) { Text("Cancel") }
                }
            )
        }

        // Quick Continue / Language Dialog
        if (showQuickStartDialog) {
            AlertDialog(
                onDismissRequest = { showQuickStartDialog = false },
                title = { Text("Choose Language / भाषा", fontWeight = FontWeight.Bold) },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = "Select your preferred language / अपनी भाषा चुनें:",
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(viewModel.supportedLanguages) { lang ->
                                    val isSelected = lang.code == selectedLanguage.code
                                    Surface(
                                        onClick = { viewModel.selectLanguage(lang) },
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (isSelected) Color(0xFF2E8C56) else Color(0xFFE8F5E9),
                                        border = BorderStroke(
                                            1.dp,
                                            if (isSelected) Color(0xFF2E8C56) else Color(0xFFC8E6C9)
                                        )
                                    ) {
                                        Text(
                                            text = lang.nativeName,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isSelected) Color.White else Color(0xFF1B5E20),
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                        )
                                    }
                                }
                            }
                        }
                    
                        OutlinedTextField(
                            value = inputName,
                            onValueChange = { inputName = it },
                            label = { Text("Your Name / आपका नाम (Optional)") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.saveOnboarding(inputName, onOnboardingComplete)
                            showQuickStartDialog = false
                        }
                    ) {
                        Text("Start App / शुरू करें")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showQuickStartDialog = false }) { Text("Cancel") }
                }
            )
        }
    }
}
