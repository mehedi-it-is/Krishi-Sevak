package com.krishisevak.app.ui.onboarding

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel,
    onOnboardingComplete: (selectedLangCode: String) -> Unit
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
            // App Logo in Login / Onboarding Screen
            Surface(
                modifier = Modifier.size(200.dp),
                shape = CircleShape,
                color = Color(0xFFF4F7F4),
                border = BorderStroke(3.dp, Color(0xFF16A34A).copy(alpha = 0.4f)),
                shadowElevation = 16.dp
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.foundation.Image(
                        painter = androidx.compose.ui.res.painterResource(id = com.krishisevak.app.R.drawable.ic_app_logo),
                        contentDescription = "Krishi Sevak App Logo",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(10.dp)
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
