package com.krishisevak.app.ui.kvk

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.krishisevak.app.data.engine.HelplineContact
import com.krishisevak.app.data.engine.KvkCenter
import com.krishisevak.app.data.engine.KvkDirectory
import com.krishisevak.app.utils.AppStrings
import com.krishisevak.app.utils.TtsManager
import com.krishisevak.app.utils.UserLocationDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KvkScreen(
    userLanguageCode: String,
    userLocation: UserLocationDetails,
    ttsManager: TtsManager,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current
    var searchQuery by remember { mutableStateOf("") }

    // Strictly default state to user's current location state
    var selectedState by remember { mutableStateOf(userLocation.stateName.ifBlank { "Maharashtra" }) }

    // Auto-update when GPS location changes
    LaunchedEffect(userLocation.stateName) {
        if (userLocation.stateName.isNotBlank()) {
            selectedState = userLocation.stateName
        }
    }

    val currentlySpeakingId by ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()
    val isSpeaking = currentlySpeakingId == "kvk_screen"

    val allStates = remember {
        val statesList = KvkDirectory.districtKvkList.map { it.state }.distinct().toMutableList()
        if (!statesList.contains(userLocation.stateName) && userLocation.stateName.isNotBlank()) {
            statesList.add(0, userLocation.stateName)
        }
        statesList
    }

    // Keep only nearest KVK numbers within the selected state
    val stateKvks = remember(selectedState, searchQuery, userLocation) {
        val listInState = KvkDirectory.districtKvkList.filter {
            it.state.equals(selectedState, ignoreCase = true)
        }

        // Sort to put user's home district KVK at the top
        val sorted = listInState.sortedByDescending {
            it.district.equals(userLocation.districtName, ignoreCase = true) ||
            it.district.equals(userLocation.cityName, ignoreCase = true)
        }

        if (searchQuery.isBlank()) {
            sorted
        } else {
            sorted.filter {
                it.district.contains(searchQuery, ignoreCase = true) ||
                it.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = AppStrings.get("kvk_screen_title", userLanguageCode),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "${AppStrings.get("kvk_screen_sub", userLanguageCode)} · ${userLocation.stateName.uppercase()}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val ttsText = "${AppStrings.get("kvk_screen_title", userLanguageCode)}. 1800-180-1551. ${userLocation.stateName}."
                    IconButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (isSpeaking) {
                                ttsManager.stop()
                            } else {
                                ttsManager.speak("kvk_screen", ttsText, userLanguageCode)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Listen",
                            tint = if (isSpeaking) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // National Kisan Call Center Banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF165231)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25A25A))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = AppStrings.get("kvk_kcc_title", userLanguageCode),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF86EFAC)
                                )
                                Text(
                                    text = "1800-180-1551",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White
                                )
                                Text(
                                    text = AppStrings.get("kvk_kcc_hours", userLanguageCode),
                                    fontSize = 11.sp,
                                    color = Color(0xFFC8F5DC)
                                )
                            }

                            Button(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:18001801551"))
                                    context.startActivity(intent)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E)),
                                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.Phone, contentDescription = null, tint = Color.Black, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(AppStrings.get("kvk_call_btn", userLanguageCode), fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 13.sp)
                            }
                        }
                    }
                }
            }

            // Quick Helplines
            item {
                Text(
                    text = AppStrings.get("kvk_emergency_title", userLanguageCode),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            items(KvkDirectory.nationalHelplines.drop(1)) { helpline ->
                HelplineCardItem(helpline = helpline) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${helpline.number}"))
                    context.startActivity(intent)
                }
            }

            // =========================================================================
            // REQUIREMENT 2: STATE-BOUND NEAREST KVK NUMBERS ONLY
            // =========================================================================
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = AppStrings.get("kvk_nearest_title", userLanguageCode).replace("%s", selectedState.uppercase()),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = AppStrings.get("kvk_nearest_sub", userLanguageCode),
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = AppStrings.get("kvk_centers_count", userLanguageCode).replace("%d", stateKvks.size.toString()),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // State Selector Switcher Chips (Allows changing state if desired)
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(allStates) { state ->
                        val isSelected = selectedState.equals(state, ignoreCase = true)
                        FilterChip(
                            selected = isSelected,
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                selectedState = state
                            },
                            label = {
                                Text(
                                    text = if (state.equals(userLocation.stateName, ignoreCase = true)) "📍 $state (Current)" else state,
                                    fontSize = 12.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        )
                    }
                }
            }

            // District Search within State
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(AppStrings.get("kvk_search_hint", userLanguageCode), fontSize = 13.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            if (stateKvks.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Box(modifier = Modifier.padding(24.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                text = "No local KVK listed for this district query. Call Kisan Call Center (1800-180-1551) for immediate contact.",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                items(stateKvks) { kvk ->
                    val isUserDistrict = kvk.district.equals(userLocation.districtName, ignoreCase = true) ||
                                         kvk.district.equals(userLocation.cityName, ignoreCase = true)

                    KvkCenterCardItem(
                        kvk = kvk,
                        isHomeDistrict = isUserDistrict,
                        userLanguageCode = userLanguageCode
                    ) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${kvk.phone}"))
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun HelplineCardItem(
    helpline: HelplineContact,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(helpline.iconEmoji, fontSize = 22.sp)
                Column {
                    Text(
                        text = helpline.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Dial: ${helpline.number} · ${helpline.timing}",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            IconButton(
                onClick = onCallClick,
                modifier = Modifier
                    .size(38.dp)
                    .background(Color(0xFF165231), CircleShape)
            ) {
                Icon(Icons.Default.Phone, contentDescription = "Call", tint = Color.White, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun KvkCenterCardItem(
    kvk: KvkCenter,
    isHomeDistrict: Boolean,
    userLanguageCode: String,
    onCallClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isHomeDistrict) Color(0xFF165231).copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isHomeDistrict) Color(0xFF22C55E) else MaterialTheme.colorScheme.outline
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    if (isHomeDistrict) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = Color(0xFF165231),
                            modifier = Modifier.padding(bottom = 4.dp)
                        ) {
                            Text(
                                text = AppStrings.get("kvk_nearest_tag", userLanguageCode),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF86EFAC)
                            )
                        }
                    }

                    Text(
                        text = kvk.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${AppStrings.get("kvk_district", userLanguageCode)}: ${kvk.district} (${kvk.state})",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Button(
                    onClick = onCallClick,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF165231)),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = Color.White, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(AppStrings.get("kvk_call_btn", userLanguageCode), fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            Text(
                text = "Head Scientist: ${kvk.headScientist}",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = kvk.address,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 14.sp,
                maxLines = 2
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "☎️ ${kvk.phone}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "✉️ ${kvk.email}",
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
