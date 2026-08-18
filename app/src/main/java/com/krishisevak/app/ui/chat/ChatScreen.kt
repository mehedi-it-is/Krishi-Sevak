package com.krishisevak.app.ui.chat

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.krishisevak.app.utils.AppStrings

private val supportedLanguages = listOf(
    Triple("en", "English", "English"),
    Triple("hi", "Hindi", "हिन्दी"),
    Triple("bn", "Bengali", "বাংলা"),
    Triple("mr", "Marathi", "मराठी"),
    Triple("te", "Telugu", "తెలుగు"),
    Triple("ta", "Tamil", "தமிழ்"),
    Triple("kn", "Kannada", "ಕನ್ನಡ"),
    Triple("ml", "Malayalam", "മലയാളം"),
    Triple("gu", "Gujarati", "ગુજરાતી"),
    Triple("pa", "Punjabi", "ਪੰਜਾਬੀ"),
    Triple("or", "Odia", "ଓଡ଼ିଆ")
)

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
    val isTranscribingVoice by viewModel.isTranscribingVoice.collectAsStateWithLifecycle()
    val userLangCode by viewModel.userLanguageCode.collectAsStateWithLifecycle()
    val translatingMessageIds by viewModel.translatingMessageIds.collectAsStateWithLifecycle()
    val translatedMessages by viewModel.translatedMessages.collectAsStateWithLifecycle()

    var textInput by remember { mutableStateOf("") }
    var isVoiceRecording by remember { mutableStateOf(false) }
    var recordingSeconds by remember { mutableIntStateOf(0) }
    var showAttachmentDialog by remember { mutableStateOf(false) }
    var showTopLanguageMenu by remember { mutableStateOf(false) }

    var attachedImageUri by remember { mutableStateOf<Uri?>(null) }
    var attachedImageBase64 by remember { mutableStateOf<String?>(null) }

    val voiceRecorder = remember { com.krishisevak.app.utils.VoiceRecorderHelper(context) }

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
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && tempCameraUri != null) {
            attachedImageUri = tempCameraUri
            attachedImageBase64 = com.krishisevak.app.utils.ImageHelper.compressAndEncodeToBase64(context, tempCameraUri!!)
        }
    }

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

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = com.krishisevak.app.utils.ImageHelper.createTempPictureUri(context)
            if (uri != null) {
                tempCameraUri = uri
                try { takePictureLauncher.launch(uri) } catch (e: Exception) { cameraLauncher.launch(null) }
            } else { cameraLauncher.launch(null) }
        } else {
            Toast.makeText(context, "Camera permission required", Toast.LENGTH_SHORT).show()
        }
    }

    val triggerCamera = {
        val hasCameraPerm = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPerm) {
            val uri = com.krishisevak.app.utils.ImageHelper.createTempPictureUri(context)
            if (uri != null) {
                tempCameraUri = uri
                try { takePictureLauncher.launch(uri) } catch (e: Exception) { cameraLauncher.launch(null) }
            } else { cameraLauncher.launch(null) }
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            attachedImageUri = uri
            attachedImageBase64 = com.krishisevak.app.utils.ImageHelper.compressAndEncodeToBase64(context, uri)
        }
    }

    val recordAudioPermission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val file = voiceRecorder.startRecording()
            if (file != null) isVoiceRecording = true
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.size - 1)
    }

    var initialDataProcessed by remember { mutableStateOf(false) }
    LaunchedEffect(initialText, initialImageUri) {
        if (!initialDataProcessed) {
            val text = initialText ?: ""
            val imgUri = initialImageUri
            if (imgUri != null) {
                val parsedUri = Uri.parse(imgUri)
                val base64 = com.krishisevak.app.utils.ImageHelper.compressAndEncodeToBase64(context, parsedUri)
                if (base64 != null) viewModel.sendImageQuery(base64Image = base64, captionText = text)
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
                            text = AppStrings.get("app_title", userLangCode),
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                            Box(modifier = Modifier.size(6.dp).background(Color(0xFF22C55E), CircleShape))
                            Text(
                                text = "AI Agricultural Assistant",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                            )
                        }
                    }
                },
                actions = {
                    Box {
                        Surface(
                            onClick = { showTopLanguageMenu = true },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF165231).copy(alpha = 0.12f),
                            border = BorderStroke(1.dp, Color(0xFF165231).copy(alpha = 0.35f)),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.Language, contentDescription = null, tint = Color(0xFF165231), modifier = Modifier.size(16.dp))
                                val currentLang = supportedLanguages.firstOrNull { it.first == userLangCode }
                                Text(
                                    text = currentLang?.third ?: "हिन्दी",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF165231)
                                )
                                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0xFF165231), modifier = Modifier.size(18.dp))
                            }
                        }
                        DropdownMenu(expanded = showTopLanguageMenu, onDismissRequest = { showTopLanguageMenu = false }, modifier = Modifier.heightIn(max = 380.dp)) {
                            supportedLanguages.forEach { (code, name, nativeName) ->
                                val isSelected = code == userLangCode
                                DropdownMenuItem(
                                    text = {
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                            Text(text = "$nativeName ($name)", fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
                                            if (isSelected) Icon(Icons.Default.Check, contentDescription = null, tint = Color(0xFF165231), modifier = Modifier.size(18.dp))
                                        }
                                    },
                                    onClick = {
                                        showTopLanguageMenu = false
                                        viewModel.setLanguage(code, name)
                                    }
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth().navigationBarsPadding().padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (attachedImageUri != null) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                    coil.compose.AsyncImage(model = attachedImageUri, contentDescription = null, modifier = Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)), contentScale = ContentScale.Crop)
                                    Column {
                                        Text(text = "📷 " + AppStrings.get("choose_image_source", userLangCode), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text(text = "Ready for diagnosis", fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }
                                IconButton(onClick = { attachedImageUri = null; attachedImageBase64 = null }) {
                                    Icon(Icons.Default.Close, contentDescription = "Remove", tint = MaterialTheme.colorScheme.error)
                                }
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        IconButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); showAttachmentDialog = true }, modifier = Modifier.size(44.dp).background(Color(0xFF165231), CircleShape)) {
                            Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Attach", tint = Color.White)
                        }
                        if (isVoiceRecording) {
                            Surface(shape = RoundedCornerShape(24.dp), color = Color(0xFFEF4444).copy(alpha = 0.12f), border = BorderStroke(1.dp, Color(0xFFEF4444)), modifier = Modifier.weight(1f).height(48.dp)) {
                                Row(modifier = Modifier.padding(horizontal = 14.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Box(modifier = Modifier.size(10.dp).background(Color(0xFFEF4444), CircleShape))
                                        Text(text = "🎙️ Listening... (${recordingSeconds}s)", fontSize = 13.sp, color = Color(0xFFDC2626))
                                    }
                                    IconButton(onClick = { isVoiceRecording = false; try { voiceRecorder.stopRecording()?.delete() } catch (_: Exception) {} }) {
                                        Icon(Icons.Default.Close, contentDescription = "Cancel", tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                                    }
                                }
                            }
                        } else {
                            OutlinedTextField(
                                value = textInput,
                                onValueChange = { textInput = it },
                                placeholder = { Text(AppStrings.get("ask_anything", userLangCode), fontSize = 13.sp) },
                                modifier = Modifier.weight(1f).heightIn(max = 120.dp),
                                shape = RoundedCornerShape(24.dp),
                                maxLines = 4,
                                trailingIcon = {
                                    IconButton(onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        val hasPerm = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
                                        if (hasPerm) { val file = voiceRecorder.startRecording(); if (file != null) isVoiceRecording = true }
                                        else recordAudioPermission.launch(Manifest.permission.RECORD_AUDIO)
                                    }) {
                                        Icon(Icons.Default.Mic, contentDescription = "Voice Input", tint = Color(0xFF165231))
                                    }
                                }
                            )
                        }
                        val isSendEnabled = isVoiceRecording || textInput.isNotBlank() || attachedImageBase64 != null
                        IconButton(
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                if (isVoiceRecording) {
                                    isVoiceRecording = false
                                    val file = voiceRecorder.stopRecording()
                                    if (file != null) viewModel.transcribeAndSendVoice(file) { textInput = it }
                                } else {
                                    val query = textInput; val img = attachedImageBase64
                                    textInput = ""; attachedImageBase64 = null; attachedImageUri = null
                                    if (img != null) viewModel.sendImageQuery(img, query)
                                    else if (query.isNotBlank()) viewModel.sendTextMessage(query)
                                }
                            },
                            enabled = isSendEnabled && uiState !is ChatUiState.Loading,
                            modifier = Modifier.size(44.dp).background(if (isSendEnabled) Color(0xFF165231) else MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = if (isSendEnabled) Color.White else MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }
            }
            if (showAttachmentDialog) {
                AlertDialog(
                    onDismissRequest = { showAttachmentDialog = false },
                    title = { Text(AppStrings.get("choose_image_source", userLangCode), fontWeight = FontWeight.Bold) },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            Button(onClick = { showAttachmentDialog = false; triggerCamera() }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF165231))) {
                                Icon(Icons.Default.PhotoCamera, null); Spacer(Modifier.width(8.dp)); Text(AppStrings.get("camera_take_photo", userLangCode))
                            }
                            OutlinedButton(onClick = { showAttachmentDialog = false; imagePickerLauncher.launch("image/*") }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
                                Icon(Icons.Default.PhotoLibrary, null); Spacer(Modifier.width(8.dp)); Text(AppStrings.get("gallery_choose_photo", userLangCode))
                            }
                        }
                    },
                    confirmButton = {}, dismissButton = { TextButton(onClick = { showAttachmentDialog = false }) { Text("Cancel") } }
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues).background(MaterialTheme.colorScheme.background)) {
            if (messages.isEmpty() && uiState !is ChatUiState.Loading) {
                Column(modifier = Modifier.fillMaxSize().padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = "🌾 " + AppStrings.get("app_title", userLangCode), fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF165231))
                    Spacer(Modifier.height(8.dp))
                    Text(text = "Speak or ask anything in any Indian language.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            } else {
                LazyColumn(state = listState, modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(messages) { message ->
                        val isUser = message.sender == "USER"
                        val isSpeaking = currentlySpeakingId == message.id
                        val isTranslating = translatingMessageIds.contains(message.id)
                        val translatedText = translatedMessages[message.id]
                        var showTranslateMenu by remember { mutableStateOf(false) }
                        var viewOriginal by remember { mutableStateOf(false) }
                        val displayText = if (translatedText != null && !viewOriginal) translatedText else message.text
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start) {
                            Card(shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = if (isUser) 16.dp else 4.dp, bottomEnd = if (isUser) 4.dp else 16.dp), colors = CardDefaults.cardColors(containerColor = if (isUser) Color(0xFF165231) else MaterialTheme.colorScheme.surfaceVariant), modifier = Modifier.widthIn(max = 320.dp)) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    if (message.isImageAttached) { Text("📷 Crop Image Attached", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = if (isUser) Color(0xFFC8F5DC) else MaterialTheme.colorScheme.primary); Spacer(Modifier.height(4.dp)) }
                                    if (translatedText != null && !viewOriginal) {
                                        Surface(shape = RoundedCornerShape(6.dp), color = Color(0xFF165231).copy(alpha = 0.15f), modifier = Modifier.padding(bottom = 6.dp)) {
                                            Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                                                Icon(Icons.Default.Translate, null, tint = Color(0xFF165231), modifier = Modifier.size(12.dp)); Text(" Translated", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF165231))
                                            }
                                        }
                                    }
                                    Text(text = displayText, style = MaterialTheme.typography.bodyMedium.copy(color = if (isUser) Color.White else MaterialTheme.colorScheme.onSurface))
                                    if (!isUser) {
                                        Spacer(Modifier.height(6.dp))
                                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                            if (translatedText != null) { TextButton(onClick = { viewOriginal = !viewOriginal }) { Text(if (viewOriginal) "View Translation" else "View Original", fontSize = 11.sp, color = Color(0xFF165231)) } } else Spacer(Modifier.width(1.dp))
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Box {
                                                    IconButton(onClick = { showTranslateMenu = true }, modifier = Modifier.size(32.dp)) {
                                                        if (isTranslating) CircularProgressIndicator(modifier = Modifier.size(14.dp), strokeWidth = 2.dp, color = Color(0xFF165231))
                                                        else Icon(Icons.Default.Translate, "Translate", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
                                                    }
                                                    DropdownMenu(expanded = showTranslateMenu, onDismissRequest = { showTranslateMenu = false }) {
                                                        supportedLanguages.forEach { (code, name, native) -> DropdownMenuItem(text = { Text("$native ($name)") }, onClick = { showTranslateMenu = false; viewOriginal = false; viewModel.translateMessage(message.id, message.text, code, name) }) }
                                                    }
                                                }
                                                IconButton(onClick = { haptic.performHapticFeedback(HapticFeedbackType.LongPress); viewModel.toggleTts(message.id, displayText) }, modifier = Modifier.size(32.dp)) {
                                                    Icon(if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp, "Speak", tint = if (isSpeaking) Color(0xFF165231) else MaterialTheme.colorScheme.onSurfaceVariant)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (uiState is ChatUiState.Loading) {
                        item {
                            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)) {
                                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = Color(0xFF165231)); Text(" Analyzing...")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
