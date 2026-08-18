package com.krishisevak.app.ui.dashboard

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.krishisevak.app.data.engine.AgroWeatherAdvisoryEngine
import com.krishisevak.app.data.engine.AgroWeatherInput
import com.krishisevak.app.data.engine.MandiTranslations
import com.krishisevak.app.data.remote.mandi.MandiRecord
import com.krishisevak.app.ui.components.AudioWaveformVisualizer
import com.krishisevak.app.ui.theme.*
import com.krishisevak.app.utils.AppStrings
import com.krishisevak.app.utils.LocationHelper
import com.krishisevak.app.utils.UserLocationDetails
import kotlinx.coroutines.launch
import coil.compose.AsyncImage

enum class DashboardTab {
    MANDI, WEATHER, SCHEMES, ALERTS
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    schemesViewModel: com.krishisevak.app.ui.schemes.SchemesViewModel,
    onNavigateToChat: (chatId: String?, initialText: String?, initialImageUri: String?) -> Unit,
    onNavigateToSchemes: () -> Unit,
    onNavigateToSoil: () -> Unit,
    onNavigateToCropRecommend: () -> Unit,
    onNavigateToCropDoctor: () -> Unit,
    onNavigateToAlmanac: () -> Unit,
    onNavigateToInsights: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToKvk: () -> Unit,
    onLogoutRestart: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val userName by viewModel.userName.collectAsStateWithLifecycle()
    val userLanguageCode by viewModel.userLanguageCode.collectAsStateWithLifecycle()
    val userLanguageName by viewModel.userLanguageName.collectAsStateWithLifecycle()
    val userLocation by viewModel.userLocation.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val recentChats by viewModel.recentChats.collectAsStateWithLifecycle()
    val mandiPrices by viewModel.mandiPrices.collectAsStateWithLifecycle()
    val mandiSearchQuery by viewModel.mandiSearchQuery.collectAsStateWithLifecycle()
    val mandiCategoryFilter by viewModel.mandiCategoryFilter.collectAsStateWithLifecycle()
    val mandiMarketName by viewModel.mandiMarketName.collectAsStateWithLifecycle()
    val weatherSummary by viewModel.weatherSummary.collectAsStateWithLifecycle()
    val currentlySpeakingId by viewModel.ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()
    val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    var activeTab by remember { mutableStateOf(DashboardTab.MANDI) }
    var chatInputText by remember { mutableStateOf("") }
    var isVoiceRecording by remember { mutableStateOf(false) }
    var showSettingsSheet by remember { mutableStateOf(false) }
    var showLocationModal by remember { mutableStateOf(false) }
    var showLogoutConfirm by remember { mutableStateOf(false) }
    var showAttachMenu by remember { mutableStateOf(false) }
    var showAppTutorial by remember { mutableStateOf(false) }
    var selectedImageUri by remember { mutableStateOf<android.net.Uri?>(null) }

    var tempNameInput by remember { mutableStateOf(userName) }

    val context = LocalContext.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val pullToRefreshState = rememberPullToRefreshState()
    val snackbarHostState = remember { SnackbarHostState() }

    // Speech-to-Text STT Launcher
    val sttLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        isVoiceRecording = false
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
            spokenText?.let {
                chatInputText = it
            }
        }
    }

    val voiceRecorder = remember { com.krishisevak.app.utils.VoiceRecorderHelper(context) }

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success && tempCameraUri != null) {
            selectedImageUri = tempCameraUri
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            val uri = com.krishisevak.app.utils.ImageHelper.bitmapToCacheUri(context, bitmap)
            selectedImageUri = uri
        }
    }

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
            Toast.makeText(context, "Camera permission is required to take photos", Toast.LENGTH_SHORT).show()
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

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

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

    // Location Permission
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                if (locationPermissionState.allPermissionsGranted) {
                    viewModel.fetchCurrentGpsLocation()
                } else {
                    locationPermissionState.launchMultiplePermissionRequest()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(20.dp)
                ) {
                    Text(
                        text = AppStrings.get("app_title", userLanguageCode),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = String.format(AppStrings.get("greeting", userLanguageCode), userName),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Add, contentDescription = null) },
                        label = { Text(AppStrings.get("new_chat", userLanguageCode)) },
                        selected = false,
                        onClick = {
                            coroutineScope.launch { drawerState.close() }
                            onNavigateToChat(null, null, null)
                        }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = AppStrings.get("saved_chats", userLanguageCode),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 6.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (recentChats.isEmpty()) {
                        Text(
                            text = AppStrings.get("no_saved_chats", userLanguageCode),
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 12.dp, bottom = 4.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 160.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            recentChats.forEach { chat ->
                                NavigationDrawerItem(
                                    icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = null) },
                                    label = { Text(chat.title, maxLines = 1) },
                                    selected = false,
                                    onClick = {
                                        coroutineScope.launch { drawerState.close() }
                                        onNavigateToChat(chat.id, null, null)
                                    }
                                )
                            }
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Smart Agri Tools Section
                    Text(
                        text = AppStrings.get("smart_tools_title", userLanguageCode),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(bottom = 6.dp),
                        color = MaterialTheme.colorScheme.primary
                    )

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        NavigationDrawerItem(
                            icon = { Text("📞", fontSize = 18.sp) },
                            label = { Text("Kisan Call Center & KVKs") },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToKvk()
                            }
                        )

                        NavigationDrawerItem(
                            icon = { Text("⚖️", fontSize = 18.sp) },
                            label = { Text("Fertilizer & Soil Advisory") },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToSoil()
                            }
                        )

                        NavigationDrawerItem(
                            icon = { Text("🩺", fontSize = 18.sp) },
                            label = { Text(AppStrings.get("tool_doctor_title", userLanguageCode)) },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToCropDoctor()
                            }
                        )

                        NavigationDrawerItem(
                            icon = { Text("🌾", fontSize = 18.sp) },
                            label = { Text(AppStrings.get("tool_crop_title", userLanguageCode)) },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToCropRecommend()
                            }
                        )

                        NavigationDrawerItem(
                            icon = { Text("📅", fontSize = 18.sp) },
                            label = { Text(AppStrings.get("tool_almanac_title", userLanguageCode)) },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToAlmanac()
                            }
                        )

                        NavigationDrawerItem(
                            icon = { Text("📊", fontSize = 18.sp) },
                            label = { Text(AppStrings.get("tool_insights_title", userLanguageCode)) },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToInsights()
                            }
                        )

                        NavigationDrawerItem(
                            icon = { Text("📖", fontSize = 18.sp) },
                            label = { Text(AppStrings.get("tool_learn_title", userLanguageCode)) },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToLearn()
                            }
                        )

                        NavigationDrawerItem(
                            icon = { Text("🎓", fontSize = 18.sp) },
                            label = { Text("App Tour & Guide / ऐप गाइड") },
                            selected = false,
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                showAppTutorial = true
                            }
                        )
                    }

                    // Bottom Section
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                showSettingsSheet = true
                            },
                            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant, CircleShape)
                        ) {
                            Icon(Icons.Default.Settings, contentDescription = AppStrings.get("settings", userLanguageCode), tint = MaterialTheme.colorScheme.primary)
                        }

                        Surface(
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                                onNavigateToKvk()
                            },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFF165231),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25A25A))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Phone,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "1800-180-1551",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Left Section: Hamburger Menu
                            IconButton(
                                onClick = { coroutineScope.launch { drawerState.open() } }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Center Interactive Location Selector Button
                            Surface(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                    showLocationModal = true
                                },
                                shape = RoundedCornerShape(20.dp),
                                color = MaterialTheme.colorScheme.primaryContainer,
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Select Location",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = "${userLocation.cityName}, ${userLocation.stateName} ▾",
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }

                            // Center-Right Language Selector Dropdown
                            var isLanguageMenuExpanded by remember { mutableStateOf(false) }
                            Box {
                                Surface(
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        isLanguageMenuExpanded = true
                                    },
                                    shape = RoundedCornerShape(20.dp),
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(
                                            text = "$userLanguageName ▾",
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                                DropdownMenu(
                                    expanded = isLanguageMenuExpanded,
                                    onDismissRequest = { isLanguageMenuExpanded = false }
                                ) {
                                    viewModel.supportedLanguages.forEach { lang ->
                                        DropdownMenuItem(
                                            text = { Text("${lang.name} (${lang.nativeName})") },
                                            onClick = {
                                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                                viewModel.updateLanguage(lang.code, lang.name)
                                                isLanguageMenuExpanded = false
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Category Nav Bar Chips: Mandi | Weather | Schemes | Alerts
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            item {
                                TabChip(
                                    title = AppStrings.get("tab_mandi", userLanguageCode),
                                    isSelected = activeTab == DashboardTab.MANDI,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        activeTab = DashboardTab.MANDI
                                    }
                                )
                            }
                            item {
                                TabChip(
                                    title = AppStrings.get("tab_weather", userLanguageCode),
                                    isSelected = activeTab == DashboardTab.WEATHER,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        activeTab = DashboardTab.WEATHER
                                    }
                                )
                            }
                            item {
                                TabChip(
                                    title = AppStrings.get("tab_schemes", userLanguageCode),
                                    isSelected = activeTab == DashboardTab.SCHEMES,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        activeTab = DashboardTab.SCHEMES
                                    }
                                )
                            }
                            item {
                                TabChip(
                                    title = AppStrings.get("tab_alerts", userLanguageCode),
                                    isSelected = activeTab == DashboardTab.ALERTS,
                                    onClick = {
                                        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                        activeTab = DashboardTab.ALERTS
                                    }
                                )
                            }
                        }
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                Surface(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onNavigateToChat(null, null, null)
                    },
                    shape = RoundedCornerShape(28.dp),
                    color = if (isDarkMode) Color(0xFF132A1C) else Color.White,
                    border = BorderStroke(
                        1.dp,
                        if (isDarkMode) Color(0xFF25A25A).copy(alpha = 0.6f) else Color(0xFFE2E8F0)
                    ),
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .navigationBarsPadding()
                        .padding(bottom = 16.dp, end = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // AI Sparkle icon with vibrant gradient matching app aesthetic
                        Box(
                            modifier = Modifier.size(22.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                val brush = Brush.linearGradient(
                                    colors = listOf(Color(0xFFE11D48), Color(0xFF9333EA), Color(0xFF3B82F6)),
                                    start = Offset(0f, 0f),
                                    end = Offset(size.width, size.height)
                                )
                                // Main 4-point sparkle
                                val cx = size.width * 0.40f
                                val cy = size.height * 0.40f
                                val r = size.width * 0.38f
                                val starPath = Path().apply {
                                    moveTo(cx, cy - r)
                                    quadraticTo(cx, cy, cx + r, cy)
                                    quadraticTo(cx, cy, cx, cy + r)
                                    quadraticTo(cx, cy, cx - r, cy)
                                    quadraticTo(cx, cy, cx, cy - r)
                                    close()
                                }
                                drawPath(starPath, brush = brush)

                                // Secondary smaller sparkle
                                val sBrush = Brush.linearGradient(
                                    colors = listOf(Color(0xFF9333EA), Color(0xFF3B82F6))
                                )
                                val scx = size.width * 0.82f
                                val scy = size.height * 0.82f
                                val sr = size.width * 0.20f
                                val smallStar = Path().apply {
                                    moveTo(scx, scy - sr)
                                    quadraticTo(scx, scy, scx + sr, scy)
                                    quadraticTo(scx, scy, scx, scy + sr)
                                    quadraticTo(scx, scy, scx - sr, scy)
                                    quadraticTo(scx, scy, scx, scy - sr)
                                    close()
                                }
                                drawPath(smallStar, brush = sBrush)
                            }
                        }

                        Text(
                            text = AppStrings.get("ask_anything", userLanguageCode),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 15.sp,
                                color = if (isDarkMode) Color.White else Color(0xFF1E293B)
                            )
                        )
                    }
                }
            }
        ) { paddingValues ->
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    viewModel.refreshData()
                    schemesViewModel.refreshSchemes(force = true)
                },
                state = pullToRefreshState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background),
                indicator = {
                    PullToRefreshDefaults.Indicator(
                        state = pullToRefreshState,
                        isRefreshing = isRefreshing,
                        modifier = Modifier.align(Alignment.TopCenter),
                        containerColor = Color(0xFF165231),
                        color = Color(0xFF22C55E)
                    )
                }
            ) {
                when (activeTab) {
                    DashboardTab.MANDI -> MandiTabView(
                        mandiPrices = mandiPrices,
                        searchQuery = mandiSearchQuery,
                        selectedCategory = mandiCategoryFilter,
                        mandiMarketName = mandiMarketName,
                        viewModel = viewModel,
                        currentlySpeakingId = currentlySpeakingId,
                        userLanguageCode = userLanguageCode,
                        userLocation = userLocation,
                        onNavigateToSoil = onNavigateToSoil,
                        onNavigateToCropRecommend = onNavigateToCropRecommend,
                        onNavigateToCropDoctor = onNavigateToCropDoctor,
                        onNavigateToAlmanac = onNavigateToAlmanac,
                        onNavigateToInsights = onNavigateToInsights,
                        onNavigateToLearn = onNavigateToLearn,
                        onNavigateToKvk = onNavigateToKvk
                    )
                    DashboardTab.SCHEMES -> SchemesTabView(viewModel, schemesViewModel, currentlySpeakingId, userLanguageCode, userLocation, onNavigateToSchemes)
                    DashboardTab.WEATHER -> WeatherTabView(weatherSummary, viewModel, currentlySpeakingId, userLanguageCode, userLocation)
                    DashboardTab.ALERTS -> AlertsTabView(viewModel, currentlySpeakingId, userLanguageCode, onNavigateToKvk)
                }
            }
        }
    }

    // Location Dialog Modal
    if (showLocationModal) {
        AlertDialog(
            onDismissRequest = { showLocationModal = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(Icons.Default.MyLocation, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                    Text(AppStrings.get("select_location_title", userLanguageCode), style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 400.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = {
                            showLocationModal = false
                            if (!locationPermissionState.allPermissionsGranted) {
                                locationPermissionState.launchMultiplePermissionRequest()
                            }
                            viewModel.fetchCurrentGpsLocation()
                            Toast.makeText(context, "Fetching Live GPS Coordinates...", Toast.LENGTH_SHORT).show()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(Icons.Default.GpsFixed, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(AppStrings.get("detect_gps_btn", userLanguageCode), fontWeight = FontWeight.Bold)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                    Text(AppStrings.get("or_select_region", userLanguageCode), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(LocationHelper.PRESET_LOCATIONS) { preset ->
                            val isSelected = userLocation.cityName.equals(preset.cityName, ignoreCase = true)
                            OutlinedCard(
                                onClick = {
                                    viewModel.selectManualLocation(preset)
                                    showLocationModal = false
                                    Toast.makeText(context, "Location updated to ${preset.cityName}, ${preset.stateName}", Toast.LENGTH_SHORT).show()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.outlinedCardColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("${preset.cityName}, ${preset.districtName}", fontWeight = FontWeight.Bold)
                                        Text("State: ${preset.stateName}", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                    if (isSelected) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLocationModal = false }) {
                    Text(AppStrings.get("close", userLanguageCode))
                }
            }
        )
    }

    // Settings Bottom Sheet
    if (showSettingsSheet) {
        AlertDialog(
            onDismissRequest = { showSettingsSheet = false },
            title = {
                Text(
                    text = AppStrings.get("settings_title", userLanguageCode),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(AppStrings.get("change_name", userLanguageCode), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    OutlinedTextField(
                        value = tempNameInput,
                        onValueChange = { tempNameInput = it },
                        label = { Text(AppStrings.get("user_name_label", userLanguageCode)) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Button(
                        onClick = {
                            if (tempNameInput.isNotBlank()) {
                                viewModel.updateUserName(tempNameInput)
                                Toast.makeText(context, "Name updated!", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(AppStrings.get("save_name", userLanguageCode))
                    }

                    HorizontalDivider()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = if (isDarkMode) AppStrings.get("dark_mode", userLanguageCode) else AppStrings.get("light_mode", userLanguageCode),
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = AppStrings.get("theme_desc", userLanguageCode),
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { viewModel.setDarkMode(it) }
                        )
                    }

                    HorizontalDivider()

                    Button(
                        onClick = { showLogoutConfirm = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(AppStrings.get("logout_btn", userLanguageCode), fontWeight = FontWeight.Bold)
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showSettingsSheet = false }) {
                    Text(AppStrings.get("close", userLanguageCode))
                }
            }
        )
    }

    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text(AppStrings.get("logout_confirm_title", userLanguageCode)) },
            text = { Text(AppStrings.get("logout_confirm_desc", userLanguageCode)) },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        showSettingsSheet = false
                        viewModel.logoutAndClearAll {
                            onLogoutRestart()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(AppStrings.get("logout_yes", userLanguageCode))
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text(AppStrings.get("cancel", userLanguageCode))
                }
            }
        )
    }

    if (showAppTutorial) {
        com.krishisevak.app.ui.components.AppTutorialDialog(
            userLanguageCode = userLanguageCode,
            ttsManager = viewModel.ttsManager,
            onDismiss = { showAppTutorial = false }
        )
    }
}

@Composable
fun TabChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0xFF165231) else MaterialTheme.colorScheme.surfaceVariant,
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) Color(0xFF25A25A) else MaterialTheme.colorScheme.outline
        )
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            fontSize = 14.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// -------------------------------------------------------------------------
// Smart Tools Quick Action Card Item
// -------------------------------------------------------------------------
@Composable
fun SmartToolQuickCard(
    emoji: String,
    title: String,
    subtitle: String,
    badgeColor: Color,
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        modifier = Modifier.width(160.dp)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(emoji, fontSize = 22.sp)
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Text(
                text = subtitle,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
        }
    }
}

// -------------------------------------------------------------------------
// Tab 1: Comprehensive Mandi Prices View
// -------------------------------------------------------------------------
@Composable
fun MandiTabView(
    mandiPrices: List<MandiRecord>,
    searchQuery: String,
    selectedCategory: String,
    mandiMarketName: String,
    viewModel: DashboardViewModel,
    currentlySpeakingId: String?,
    userLanguageCode: String,
    userLocation: UserLocationDetails,
    onNavigateToSoil: () -> Unit,
    onNavigateToCropRecommend: () -> Unit,
    onNavigateToCropDoctor: () -> Unit,
    onNavigateToAlmanac: () -> Unit,
    onNavigateToInsights: () -> Unit,
    onNavigateToLearn: () -> Unit,
    onNavigateToKvk: () -> Unit
) {
    val isSpeaking = currentlySpeakingId == "mandi_tab"
    val mandiSummaryText = AppStrings.get("mandi_tts", userLanguageCode)

    val dynamicCategories = listOf("All") + mandiPrices.map { it.displayCategory }.distinct().filter { it != "All" }
    val categories = dynamicCategories.map { cat ->
        val key = when(cat) {
            "All" -> "mandi_cat_all"
            "Vegetables" -> "mandi_cat_veg"
            "Fruits" -> "mandi_cat_fruit"
            "Grains & Crops" -> "mandi_cat_grains"
            "Pulses & Legumes" -> "mandi_cat_pulses"
            "Spices & Cash Crops" -> "mandi_cat_spices"
            else -> ""
        }
        val localized = if (key.isNotEmpty()) AppStrings.get(key, userLanguageCode) else cat
        cat to localized
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Agri Smart Tools Horizontal Carousel
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = AppStrings.get("smart_tools_title", userLanguageCode),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        SmartToolQuickCard("📞", "Kisan Helpline", "1800-180-1551 & KVK", Color(0xFF22C55E), onNavigateToKvk)
                    }
                    item {
                        SmartToolQuickCard("⚖️", "Fertilizer Dosage", "Exact NPK Bags", Color(0xFF3B82F6), onNavigateToSoil)
                    }
                    item {
                        SmartToolQuickCard("🩺", AppStrings.get("tool_doctor_title", userLanguageCode), AppStrings.get("tool_doctor_sub", userLanguageCode), Color(0xFFEF4444), onNavigateToCropDoctor)
                    }
                    item {
                        SmartToolQuickCard("🌾", AppStrings.get("tool_crop_title", userLanguageCode), AppStrings.get("tool_crop_sub", userLanguageCode), Color(0xFFF59E0B), onNavigateToCropRecommend)
                    }
                    item {
                        SmartToolQuickCard("📅", AppStrings.get("tool_almanac_title", userLanguageCode), AppStrings.get("tool_almanac_sub", userLanguageCode), Color(0xFF0284C7), onNavigateToAlmanac)
                    }
                    item {
                        SmartToolQuickCard("📊", AppStrings.get("tool_insights_title", userLanguageCode), AppStrings.get("tool_insights_sub", userLanguageCode), Color(0xFF8B5CF6), onNavigateToInsights)
                    }
                    item {
                        SmartToolQuickCard("📖", AppStrings.get("tool_learn_title", userLanguageCode), AppStrings.get("tool_learn_sub", userLanguageCode), Color(0xFF16A34A), onNavigateToLearn)
                    }
                }
            }
        }

        // Mandi Header + Audio Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = AppStrings.get("mandi_title", userLanguageCode),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                    )
                    Text(
                        text = "${AppStrings.get("mandi_nearest_market", userLanguageCode)}: ${mandiMarketName.ifBlank { "${userLocation.districtName} Mandi" }} (${userLocation.stateName})",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

                IconButton(
                    onClick = { viewModel.toggleTts("mandi_tab", mandiSummaryText) },
                    modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                ) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Read Aloud",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        // Live Commodity Search Bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setMandiSearchQuery(it) },
                placeholder = { Text(AppStrings.get("mandi_search_hint", userLanguageCode), fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setMandiSearchQuery("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            )
        }

        // Category Filter Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { (key, label) ->
                    val isSelected = selectedCategory == key
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setMandiCategoryFilter(key) },
                        label = { Text(label, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal, fontSize = 13.sp) }
                    )
                }
            }
        }

        // Items Count Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${mandiPrices.size} Commodities Available",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Unit: ₹ / Quintal (100 kg)",
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        if (mandiPrices.isEmpty()) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Box(modifier = Modifier.padding(32.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = AppStrings.get("mandi_no_data", userLanguageCode),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        } else {
            items(mandiPrices) { record ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(record.displayEmoji, fontSize = 28.sp)
                                Column {
                                    Text(
                                        text = MandiTranslations.getTranslatedName(record.commodity ?: "Unknown", userLanguageCode),
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (userLanguageCode != "en") {
                                        Text(
                                            text = record.commodity ?: "",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                        )
                                    }
                                    Text(
                                        text = MandiTranslations.getTranslatedCategory(record.displayCategory, userLanguageCode),
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            // Price Section + Audio Speak Button
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                val isRecordSpeaking = currentlySpeakingId == "mandi_${record.commodity}_${record.market}"
                                IconButton(
                                    onClick = { viewModel.speakMandiRecord(record, userLanguageCode) },
                                    modifier = Modifier.size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isRecordSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                        contentDescription = "Listen Price",
                                        tint = if (isRecordSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "₹ ${record.modalPrice ?: "N/A"}",
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "/ Quintal",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

                        // Nearest Market & Distance Details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(Icons.Default.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(14.dp))
                                Text(
                                    text = "${record.market ?: "${userLocation.cityName} Mandi"} (${record.displayDistance} km)",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }

                            // Price Trend Badge
                            val (trendColor, trendLabel) = when (record.displayPriceTrend) {
                                "Rising" -> Color(0xFF16A34A) to "📈 " + AppStrings.get("mandi_trend_rising", userLanguageCode)
                                "Falling" -> Color(0xFFDC2626) to "📉 " + AppStrings.get("mandi_trend_falling", userLanguageCode)
                                else -> Color(0xFFD97706) to "⚖️ " + AppStrings.get("mandi_trend_stable", userLanguageCode)
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = trendColor.copy(alpha = 0.15f),
                                border = androidx.compose.foundation.BorderStroke(1.dp, trendColor.copy(alpha = 0.5f))
                            ) {
                                Text(
                                    text = trendLabel,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = trendColor
                                )
                            }
                        }

                        // Min / Max Range + Retail Estimate
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Range: ₹${record.minPrice ?: "---"} - ₹${record.maxPrice ?: "---"}",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            record.retailPrice?.let {
                                Text(
                                    text = "Est. Retail: ~₹$it/kg",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = AppStrings.get("mandi_footer", userLanguageCode),
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

// -------------------------------------------------------------------------
// Tab 2: Schemes View
// -------------------------------------------------------------------------
@Composable
fun SchemesTabView(
    viewModel: DashboardViewModel,
    schemesViewModel: com.krishisevak.app.ui.schemes.SchemesViewModel,
    currentlySpeakingId: String?,
    userLanguageCode: String,
    userLocation: UserLocationDetails,
    onNavigateToSchemes: () -> Unit
) {
    val isSpeaking = currentlySpeakingId == "schemes_tab"
    val textToSpeak = AppStrings.get("schemes_tts", userLanguageCode)
    val filteredSchemes by schemesViewModel.filteredSchemes.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${AppStrings.get("schemes_title", userLanguageCode)} (${userLocation.stateName.uppercase()} & CENTRAL)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(onClick = { viewModel.toggleTts("schemes_tab", textToSpeak) }) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Read Aloud",
                        tint = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        val previewSchemes = filteredSchemes.take(4)
        items(previewSchemes, key = { it.id }) { scheme ->
            val context = androidx.compose.ui.platform.LocalContext.current
            val isSchemeSpeaking = currentlySpeakingId == "scheme_${scheme.id}"
            DashboardSchemeCardItem(
                icon = "🏛",
                title = scheme.title,
                badgeText = scheme.amount.take(20),
                description = scheme.description,
                isSpeaking = isSchemeSpeaking,
                onSpeakClick = {
                    val text = "${scheme.title}. ${scheme.description}. ${scheme.benefits}."
                    viewModel.ttsManager.speak("scheme_${scheme.id}", text, userLanguageCode)
                },
                onClick = {
                    val intent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(scheme.officialUrl))
                    context.startActivity(intent)
                }
            )
        }

        item {
            Button(
                onClick = onNavigateToSchemes,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(AppStrings.get("schemes_view_all", userLanguageCode), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun DashboardSchemeCardItem(
    icon: String,
    title: String,
    badgeText: String,
    description: String,
    isSpeaking: Boolean = false,
    onSpeakClick: (() -> Unit)? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(icon, fontSize = 20.sp)
                    Text(title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    onSpeakClick?.let { speak ->
                        IconButton(onClick = speak, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Read Aloud",
                                tint = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primary
                    ) {
                        Text(
                            text = badgeText,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

// -------------------------------------------------------------------------
// Tab 3: Actionable Agro-Weather View
// -------------------------------------------------------------------------
@Composable
fun WeatherTabView(
    weatherSummary: String,
    viewModel: DashboardViewModel,
    currentlySpeakingId: String?,
    userLanguageCode: String,
    userLocation: UserLocationDetails
) {
    val isSpeaking = currentlySpeakingId == "weather_tab"

    // Actionable Agro-Weather Engine Evaluation
    val agroAdvisory = remember(userLocation, userLanguageCode) {
        AgroWeatherAdvisoryEngine.generateAdvisory(
            AgroWeatherInput(
                temperature = 29f,
                humidity = 78,
                windSpeedKmH = 14f,
                rainProbability = 60,
                condition = "Partly Cloudy"
            ),
            langCode = userLanguageCode
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Current Weather Conditions Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "LOCATION: ${userLocation.cityName.uppercase()}, ${userLocation.stateName.uppercase()}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = AppStrings.get("weather_cond", userLanguageCode),
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                            )
                        }

                        IconButton(
                            onClick = { viewModel.toggleTts("weather_tab", agroAdvisory.summaryAudioText) },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        ) {
                            Icon(
                                imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Read Aloud Agro-Weather Advisory",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "29°C",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = AppStrings.get("weather_details", userLanguageCode),
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        WeatherMetricItem("💧", AppStrings.get("metric_humidity", userLanguageCode))
                        WeatherMetricItem("🌬", AppStrings.get("metric_wind", userLanguageCode))
                        WeatherMetricItem("🌧", AppStrings.get("metric_rain", userLanguageCode))
                        WeatherMetricItem("☀️", AppStrings.get("metric_uv", userLanguageCode))
                    }
                }
            }
        }

        // =========================================================================
        // HIGH-IMPACT SIH FEATURE: Actionable Agro-Weather Decision Cards
        // =========================================================================
        item {
            Text(
                text = AppStrings.get("agro_advisory_title", userLanguageCode),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Spraying Window Card
        item {
            val sprayColor = Color(agroAdvisory.sprayingStatus.badgeColorHex)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, sprayColor.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("🧪", fontSize = 20.sp)
                            Text(AppStrings.get("agro_spray_window", userLanguageCode), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = sprayColor.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, sprayColor)
                        ) {
                            Text(
                                text = "${agroAdvisory.sprayingStatus.icon} ${agroAdvisory.sprayingStatusLabel}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = sprayColor
                            )
                        }
                    }

                    Text(
                        text = agroAdvisory.sprayingReason,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Irrigation Decision Card
        item {
            val irrColor = Color(agroAdvisory.irrigationStatus.badgeColorHex)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, irrColor.copy(alpha = 0.6f))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("💧", fontSize = 20.sp)
                            Text(AppStrings.get("agro_irri_title", userLanguageCode), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }

                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = irrColor.copy(alpha = 0.2f),
                            border = androidx.compose.foundation.BorderStroke(1.dp, irrColor)
                        ) {
                            Text(
                                text = "${agroAdvisory.irrigationStatus.icon} ${agroAdvisory.irrigationStatusLabel}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = irrColor
                            )
                        }
                    }

                    Text(
                        text = agroAdvisory.irrigationReason,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // Harvesting & Microclimate Disease Warning Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1B3828)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25A25A))
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = agroAdvisory.harvestAdvisory,
                        fontSize = 13.sp,
                        color = Color.White,
                        lineHeight = 18.sp
                    )
                    HorizontalDivider(color = Color(0xFF25A25A).copy(alpha = 0.4f))
                    Text(
                        text = agroAdvisory.pestDiseaseThreat,
                        fontSize = 13.sp,
                        color = Color(0xFF86EFAC),
                        lineHeight = 18.sp
                    )
                }
            }
        }

        // 5-Day Forecast Breakdown Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = AppStrings.get("4day_title", userLanguageCode),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ForecastDayColumn("Today", "⛅ 29°C", "60% Rain", modifier = Modifier.weight(1f))
                        ForecastDayColumn("Tomorrow", "🌧 27°C", "65% Rain", modifier = Modifier.weight(1f))
                        ForecastDayColumn("Wed 13", "🌧 26°C", "70% Rain", modifier = Modifier.weight(1f))
                        ForecastDayColumn("Thu 14", "⛅ 28°C", "20% Rain", modifier = Modifier.weight(1f))
                        ForecastDayColumn("Fri 15", "☀️ 30°C", "10% Rain", modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun ForecastDayColumn(
    day: String,
    temp: String,
    rain: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Text(day, fontSize = 11.sp, fontWeight = FontWeight.Bold, maxLines = 1)
        Text(temp, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
        Text(rain, fontSize = 10.sp, color = Color(0xFF60A5FA), maxLines = 1)
    }
}

@Composable
fun WeatherMetricItem(icon: String, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = icon, fontSize = 16.sp)
        Text(text = text, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

// -------------------------------------------------------------------------
// Tab 4: Alerts & Awareness View
// -------------------------------------------------------------------------
@Composable
fun AlertsTabView(
    viewModel: DashboardViewModel,
    currentlySpeakingId: String?,
    userLanguageCode: String,
    onNavigateToKvk: () -> Unit
) {
    val isSpeaking = currentlySpeakingId == "alerts_tab"
    val textToSpeak = AppStrings.get("alerts_tts", userLanguageCode)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = AppStrings.get("alerts_title", userLanguageCode),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                IconButton(onClick = { viewModel.toggleTts("alerts_tab", textToSpeak) }) {
                    Icon(
                        imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                        contentDescription = "Read Aloud",
                        tint = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        // Kisan Call Center Helpline Emergency Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToKvk() },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF165231)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25A25A))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("📞 EMERGENCY KISAN CALL CENTER", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF86EFAC))
                        Text("Toll-Free: 1800-180-1551", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("Tap to call or browse your local District KVK directory.", fontSize = 11.sp, color = Color(0xFFC8F5DC))
                    }
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.White)
                }
            }
        }

        item {
            val isAlert1Speaking = currentlySpeakingId == "alert_1"
            val title1 = AppStrings.get("alert1_title", userLanguageCode)
            val desc1 = AppStrings.get("alert1_desc", userLanguageCode)
            AlertCardItem(
                id = "1",
                title = title1,
                description = desc1,
                isHighAlert = true,
                isSpeaking = isAlert1Speaking,
                onSpeakClick = { viewModel.speakAlert("1", title1, desc1, userLanguageCode) }
            )
        }

        item {
            val isAlert2Speaking = currentlySpeakingId == "alert_2"
            val title2 = AppStrings.get("alert2_title", userLanguageCode)
            val desc2 = AppStrings.get("alert2_desc", userLanguageCode)
            AlertCardItem(
                id = "2",
                title = title2,
                description = desc2,
                isHighAlert = true,
                isSpeaking = isAlert2Speaking,
                onSpeakClick = { viewModel.speakAlert("2", title2, desc2, userLanguageCode) }
            )
        }

        item {
            val isAlert3Speaking = currentlySpeakingId == "alert_3"
            val title3 = AppStrings.get("alert3_title", userLanguageCode)
            val desc3 = AppStrings.get("alert3_desc", userLanguageCode)
            AlertCardItem(
                id = "3",
                title = title3,
                description = desc3,
                isHighAlert = false,
                isSpeaking = isAlert3Speaking,
                onSpeakClick = { viewModel.speakAlert("3", title3, desc3, userLanguageCode) }
            )
        }

        item {
            val isAlert4Speaking = currentlySpeakingId == "alert_4"
            val title4 = AppStrings.get("alert4_title", userLanguageCode)
            val desc4 = AppStrings.get("alert4_desc", userLanguageCode)
            AlertCardItem(
                id = "4",
                title = title4,
                description = desc4,
                isHighAlert = true,
                isSpeaking = isAlert4Speaking,
                onSpeakClick = { viewModel.speakAlert("4", title4, desc4, userLanguageCode) }
            )
        }
    }
}

@Composable
fun AlertCardItem(
    id: String,
    title: String,
    description: String,
    isHighAlert: Boolean,
    isSpeaking: Boolean = false,
    onSpeakClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isHighAlert) MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isHighAlert) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = (if (isHighAlert) "🚨 " else "ℹ️ ") + title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isHighAlert) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.weight(1f)
                )

                onSpeakClick?.let { speak ->
                    IconButton(onClick = speak, modifier = Modifier.size(36.dp)) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Read Aloud",
                            tint = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}
