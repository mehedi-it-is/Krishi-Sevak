package com.krishisevak.app.ui.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.speech.RecognizerIntent
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    initialText: String? = null,
    initialImageUri: String? = null,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    val listState = rememberLazyListState()

    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val currentlySpeakingId by viewModel.ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()
    val lastDetectedLanguage by viewModel.lastDetectedLanguage.collectAsStateWithLifecycle()
    val isTranscribingVoice by viewModel.isTranscribingVoice.collectAsStateWithLifecycle()
    val sarvamQueriesUsed by viewModel.sarvamQueriesUsed.collectAsStateWithLifecycle()
    val kindwiseQueriesUsed by viewModel.kindwiseQueriesUsed.collectAsStateWithLifecycle()

    var textInput by remember { mutableStateOf("") }
    var isVoiceRecording by remember { mutableStateOf(false) }
    var recordingSeconds by remember { mutableIntStateOf(0) }
    var showAttachmentDialog by remember { mutableStateOf(false) }

    var attachedImageUri by remember { mutableStateOf<Uri?>(null) }
    var attachedImageBase64 by remember { mutableStateOf<String?>(null) }

    val voiceRecorder = remember { com.krishisevak.app.utils.VoiceRecorderHelper(context) }

    // Recording timer
    LaunchedEffect(isVoiceRecording) {
        if (isVoiceRecording) {
            recordingSeconds = 0
            while (isVoiceRecording) {
                kotlinx.coroutines.delay(1000)
                recordingSeconds++
            }
        }
    }

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Full-resolution camera launcher using FileProvider
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && tempCameraUri != null) {
            attachedImageUri = tempCameraUri
            attachedImageBase64 = com.krishisevak.app.utils.ImageHelper.compressAndEncodeToBase64(context, tempCameraUri!!)
        }
    }

    // Fallback camera preview launcher
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        if (bitmap != null) {
            val base64 = com.krishisevak.app.utils.ImageHelper.compressBitmapToBase64(bitmap)
            val uri = com.krishisevak.app.utils.ImageHelper.bitmapToCacheUri(context, bitmap)
            attachedImageBase64 = base64
            attachedImageUri = uri
        }
    }

    // Camera runtime permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = com.krishisevak.app.utils.ImageHelper.createTempPictureUri(context)
            if (uri != null) {
                tempCameraUri = uri
                try {
                    takePictureLauncher.launch(uri)
                } catch (e: Exception) {
                    cameraLauncher.launch(null)
                }
            } else {
                cameraLauncher.launch(null)
            }
        } else {
            Toast.makeText(context, "Camera permission is required to take crop photos", Toast.LENGTH_SHORT).show()
        }
    }

    val triggerCamera = {
        val hasCameraPerm = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPerm) {
            val uri = com.krishisevak.app.utils.ImageHelper.createTempPictureUri(context)
            if (uri != null) {
                tempCameraUri = uri
                try {
                    takePictureLauncher.launch(uri)
                } catch (e: Exception) {
                    cameraLauncher.launch(null)
                }
            } else {
                cameraLauncher.launch(null)
            }
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    // Photo gallery launcher
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            attachedImageUri = uri
            attachedImageBase64 = com.krishisevak.app.utils.ImageHelper.compressAndEncodeToBase64(context, uri)
        }
    }

    // Record audio permission launcher
    val recordAudioPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val file = voiceRecorder.startRecording()
            if (file != null) {
                isVoiceRecording = true
            }
        }
    }

    // Auto scroll on new message
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    var initialDataProcessed by remember { mutableStateOf(false) }
    LaunchedEffect(initialText, initialImageUri) {
        if (!initialDataProcessed) {
            val text = initialText ?: ""
            val imgUri = initialImageUri
            if (imgUri != null) {
                val parsedUri = android.net.Uri.parse(imgUri)
                val base64 = com.krishisevak.app.utils.ImageHelper.compressAndEncodeToBase64(context, parsedUri)
                if (base64 != null) {
                    viewModel.sendImageQuery(base64Image = base64, captionText = text)
                }
            } else if (text.isNotBlank()) {
                viewModel.sendTextMessage(text)
            }
            initialDataProcessed = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "🌱 Krishi Sevak AI",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = if (sarvamQueriesUsed >= 2) "Offline AI Engine Active" else "Sarvam Indic Multi-Language AI",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (sarvamQueriesUsed >= 2) Color(0xFFEAB308) else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                },
                actions = {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (sarvamQueriesUsed >= 2) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                        modifier = Modifier.padding(end = 12.dp)
                    ) {
                        val remaining = (2 - sarvamQueriesUsed).coerceAtLeast(0)
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = if (sarvamQueriesUsed >= 2) "⚡ 2/2 used" else "⚡ $remaining/2 left",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (sarvamQueriesUsed >= 2) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    navigationIconContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Attached photo preview banner
                    if (attachedImageUri != null) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    coil.compose.AsyncImage(
                                        model = attachedImageUri,
                                        contentDescription = "Attached Crop Photo",
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(RoundedCornerShape(8.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    Column {
                                        Text(
                                            text = "📷 Crop Photo Attached",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "Ready for disease diagnosis",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        attachedImageUri = null
                                        attachedImageBase64 = null
                                    },
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Remove Photo",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }

                    // Voice Recording Banner (Sarvam AI)
                    if (isVoiceRecording) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = Color(0xFFEF4444).copy(alpha = 0.15f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(Color(0xFFEF4444), CircleShape)
                                    )
                                    Text(
                                        text = "🎙️ Recording with Sarvam Indic AI (${recordingSeconds}s)...",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFFDC2626)
                                    )
                                }
                                TextButton(
                                    onClick = {
                                        isVoiceRecording = false
                                        val file = voiceRecorder.stopRecording()
                                        if (file != null) {
                                            viewModel.transcribeAndSendVoice(file) { textInput = it }
                                        }
                                    }
                                ) {
                                    Text("DONE", fontWeight = FontWeight.Bold, color = Color(0xFFDC2626))
                                }
                            }
                        }
                    }

                    // Voice Transcribing State
                    if (isTranscribingVoice) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp)
                                Text(
                                    text = "Sarvam AI is transcribing your voice...",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Image Picker (+) Button
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showAttachmentDialog = true
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .background(Color(0xFF165231), CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = "Attach Photo",
                                tint = Color.White
                            )
                        }

                        // Mic Voice Query STT Button (Sarvam AI)
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                if (isVoiceRecording) {
                                    isVoiceRecording = false
                                    val audioFile = voiceRecorder.stopRecording()
                                    if (audioFile != null) {
                                        viewModel.transcribeAndSendVoice(audioFile) { transcript ->
                                            textInput = transcript
                                        }
                                    }
                                } else {
                                    val hasPermission = androidx.core.content.ContextCompat.checkSelfPermission(
                                        context, android.Manifest.permission.RECORD_AUDIO
                                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                                    if (hasPermission) {
                                        val file = voiceRecorder.startRecording()
                                        if (file != null) {
                                            isVoiceRecording = true
                                        }
                                    } else {
                                        recordAudioPermission.launch(android.Manifest.permission.RECORD_AUDIO)
                                    }
                                }
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .background(if (isVoiceRecording) Color(0xFFEF4444) else Color(0xFF165231), CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isVoiceRecording) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = "Voice Input",
                                tint = Color.White
                            )
                        }

                        // Message Text Field
                        OutlinedTextField(
                            value = textInput,
                            onValueChange = { textInput = it },
                            placeholder = { Text("Ask in any language (Voice or Text)...", fontSize = 13.sp) },
                            modifier = Modifier
                                .weight(1f)
                                .heightIn(max = 120.dp),
                            shape = RoundedCornerShape(24.dp),
                            maxLines = 4
                        )

                        // Send Button
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                val query = textInput
                                val imgBase64 = attachedImageBase64
                                textInput = ""
                                attachedImageBase64 = null
                                attachedImageUri = null
                                if (imgBase64 != null) {
                                    viewModel.sendImageQuery(base64Image = imgBase64, captionText = query)
                                } else if (query.isNotBlank()) {
                                    viewModel.sendTextMessage(query)
                                }
                            },
                            enabled = (textInput.isNotBlank() || attachedImageBase64 != null) && uiState !is ChatUiState.Loading,
                            modifier = Modifier
                                .size(44.dp)
                                .background(if (textInput.isNotBlank() || attachedImageBase64 != null) Color(0xFF165231) else MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Send,
                                contentDescription = "Send",
                                tint = if (textInput.isNotBlank() || attachedImageBase64 != null) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            if (showAttachmentDialog) {
                AlertDialog(
                    onDismissRequest = { showAttachmentDialog = false },
                    title = { Text("Attach Crop Photo", fontWeight = FontWeight.Bold) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text("Choose photo source for leaf & crop diagnosis:")
                            Button(
                                onClick = {
                                    showAttachmentDialog = false
                                    triggerCamera()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(Icons.Default.PhotoCamera, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("📷 Take Photo with Camera", fontWeight = FontWeight.Bold)
                            }

                            OutlinedButton(
                                onClick = {
                                    showAttachmentDialog = false
                                    imagePickerLauncher.launch("image/*")
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.PhotoLibrary, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("🖼️ Choose from Gallery", fontWeight = FontWeight.Bold)
                            }
                        }
                    },
                    confirmButton = {},
                    dismissButton = {
                        TextButton(onClick = { showAttachmentDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (messages.isEmpty() && uiState !is ChatUiState.Loading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "🌾 Krishi Sevak AI Companion",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Speak or ask anything in any Indian language. The AI will understand and respond in that exact same language.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(messages) { message ->
                        val isUser = message.sender == "USER"
                        val isSpeaking = currentlySpeakingId == message.id

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
                        ) {
                            Card(
                                shape = RoundedCornerShape(
                                    topStart = 16.dp,
                                    topEnd = 16.dp,
                                    bottomStart = if (isUser) 16.dp else 4.dp,
                                    bottomEnd = if (isUser) 4.dp else 16.dp
                                ),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isUser) Color(0xFF165231) else MaterialTheme.colorScheme.surfaceVariant
                                ),
                                modifier = Modifier.widthIn(max = 300.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    if (message.isImageAttached) {
                                        Text(
                                            text = "📷 Crop Image Attached",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (isUser) Color(0xFFC8F5DC) else MaterialTheme.colorScheme.primary
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }

                                    Text(
                                        text = message.text,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    )

                                    if (!isUser) {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.End
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                    viewModel.toggleTts(message.id, message.text)
                                                },
                                                modifier = Modifier.size(32.dp)
                                            ) {
                                                Icon(
                                                    imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                                    contentDescription = "Read Aloud",
                                                    tint = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (uiState is ChatUiState.Loading) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Card(
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                                        Text("Analyzing crop health with Sarvam Indic AI...", fontSize = 13.sp)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
