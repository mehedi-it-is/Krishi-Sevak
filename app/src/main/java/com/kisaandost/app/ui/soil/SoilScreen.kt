package com.kisaandost.app.ui.soil

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kisaandost.app.utils.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SoilScreen(
    viewModel: SoilViewModel,
    onBackClick: () -> Unit
) {
    val haptic = LocalHapticFeedback.current
    val userLanguageCode by viewModel.userLanguageCode.collectAsStateWithLifecycle(initialValue = "en")
    val screenMode by viewModel.screenMode.collectAsStateWithLifecycle()

    // Mode 1 Diagnosis States
    val soilType by viewModel.soilType.collectAsStateWithLifecycle()
    val phInput by viewModel.phInput.collectAsStateWithLifecycle()
    val nitrogenInput by viewModel.nitrogenInput.collectAsStateWithLifecycle()
    val phosphorusInput by viewModel.phosphorusInput.collectAsStateWithLifecycle()
    val potassiumInput by viewModel.potassiumInput.collectAsStateWithLifecycle()
    val result by viewModel.analysisResult.collectAsStateWithLifecycle()

    // Mode 2 Calculator States
    val calcCrop by viewModel.calcCrop.collectAsStateWithLifecycle()
    val calcAcreage by viewModel.calcAcreage.collectAsStateWithLifecycle()
    val calcUnit by viewModel.calcUnit.collectAsStateWithLifecycle()
    val calcSoilType by viewModel.calcSoilType.collectAsStateWithLifecycle()
    val fertResult by viewModel.fertilizerResult.collectAsStateWithLifecycle()

    val currentlySpeakingId by viewModel.ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()
    val isSpeaking = currentlySpeakingId == "soil_screen"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "⚖️ Fertilizer & Soil Advisory",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "NPK Dosage & Soil Diagnosis Engine",
                            fontSize = 12.sp,
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
                    val ttsText = if (screenMode == SoilScreenMode.CALCULATOR) {
                        fertResult?.let {
                            "Fertilizer Dosage for ${it.acreage} ${it.unit} of ${it.cropName}: Apply ${it.ureaBags45kg} bags of Urea, ${it.dapBags50kg} bags of DAP, and ${it.mopBags50kg} bags of MOP. ${it.basalSchedule}. ${it.firstTopDressingSchedule}."
                        } ?: "Fertilizer dosage calculator."
                    } else {
                        result?.let { res ->
                            "${res.summary}. Deficiencies: ${res.deficiencies.joinToString(". ")}. Recommendations: ${res.fertilizerRecommendations.joinToString(". ")}"
                        } ?: "Soil test diagnosis."
                    }

                    IconButton(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            viewModel.toggleTts("soil_screen", ttsText)
                        }
                    ) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Listen Advisory",
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Mode Selector Switcher Tabs
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Surface(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.setScreenMode(SoilScreenMode.CALCULATOR)
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (screenMode == SoilScreenMode.CALCULATOR) Color(0xFF165231) else Color.Transparent,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "⚖️ Dosage Calculator",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (screenMode == SoilScreenMode.CALCULATOR) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Surface(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            viewModel.setScreenMode(SoilScreenMode.DIAGNOSIS)
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (screenMode == SoilScreenMode.DIAGNOSIS) Color(0xFF165231) else Color.Transparent,
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "🧪 Soil Test Diagnosis",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (screenMode == SoilScreenMode.DIAGNOSIS) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // TAB 1: FERTILIZER DOSAGE CALCULATOR
            // =========================================================================
            if (screenMode == SoilScreenMode.CALCULATOR) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = "🌾 1. Select Crop",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(viewModel.cropsList) { crop ->
                                    val isSelected = calcCrop == crop
                                    val localizedCrop = com.kisaandost.app.data.engine.SmartAgriToolsTranslations.getCropName(crop, userLanguageCode)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            viewModel.setCalcCrop(crop)
                                        },
                                        label = { Text(localizedCrop, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                                    )
                                }
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                            Text(
                                text = "📐 2. Field Size (Acreage)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                OutlinedTextField(
                                    value = calcAcreage,
                                    onValueChange = { viewModel.setCalcAcreage(it) },
                                    label = { Text("Land Area") },
                                    singleLine = true,
                                    modifier = Modifier.weight(1.2f),
                                    shape = RoundedCornerShape(12.dp)
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    viewModel.unitsList.forEach { unit ->
                                        val isSelected = calcUnit == unit
                                        Surface(
                                            onClick = {
                                                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                                viewModel.setCalcUnit(unit)
                                            },
                                            shape = RoundedCornerShape(10.dp),
                                            color = if (isSelected) Color(0xFF165231) else MaterialTheme.colorScheme.surface,
                                            border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) Color(0xFF25A25A) else MaterialTheme.colorScheme.outline)
                                        ) {
                                            Text(
                                                text = unit,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 12.dp),
                                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                fontSize = 12.sp,
                                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }

                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                            Text(
                                text = "🌱 3. Soil Type",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(viewModel.soilTypesList) { soil ->
                                    val isSelected = calcSoilType == soil
                                    val localizedSoil = com.kisaandost.app.data.engine.SmartAgriToolsTranslations.getSoilTypeName(soil, userLanguageCode)
                                    FilterChip(
                                        selected = isSelected,
                                        onClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            viewModel.setCalcSoilType(soil)
                                        },
                                        label = { Text(localizedSoil, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                                    )
                                }
                            }
                        }
                    }
                }

                // Bag Requirement Results
                fertResult?.let { fert ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25A25A))
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(
                                            text = "REQUIRED FERTILIZER BAGS",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "${fert.cropName.uppercase()} · ${fert.acreage} ${fert.unit.uppercase()}",
                                            fontWeight = FontWeight.ExtraBold,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }

                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = Color(0xFF165231)
                                    ) {
                                        Text(
                                            text = "ICAR Standard",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }

                                // 3 Bag Badges Row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Urea
                                    Card(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E3A8A).copy(alpha = 0.2f)),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF3B82F6))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Text("UREA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF60A5FA), maxLines = 1)
                                            Text("${fert.ureaBags45kg}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                                            Text("Bags (45kg)", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                            Text("(${fert.ureaKg.toInt()} kg)", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                                        }
                                    }

                                    // DAP
                                    Card(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF78350F).copy(alpha = 0.2f)),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF59E0B))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Text("DAP", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFBBF24), maxLines = 1)
                                            Text("${fert.dapBags50kg}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                                            Text("Bags (50kg)", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                            Text("(${fert.dapKg.toInt()} kg)", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                                        }
                                    }

                                    // MOP
                                    Card(
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(14.dp),
                                        colors = CardDefaults.cardColors(containerColor = Color(0xFF831843).copy(alpha = 0.2f)),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEC4899))
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(2.dp)
                                        ) {
                                            Text("POTASH", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFFF472B6), maxLines = 1)
                                            Text("${fert.mopBags50kg}", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = Color.White)
                                            Text("Bags (50kg)", fontSize = 9.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, maxLines = 1)
                                            Text("(${fert.mopKg.toInt()} kg)", fontSize = 9.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                                        }
                                    }
                                }

                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

                                // Split Application Schedule
                                Text(
                                    text = "📅 Split Application Schedule",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = MaterialTheme.colorScheme.surface,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Text("1️⃣ " + fert.basalSchedule, fontSize = 12.sp, lineHeight = 16.sp)
                                        Text("2️⃣ " + fert.firstTopDressingSchedule, fontSize = 12.sp, lineHeight = 16.sp)
                                        Text("3️⃣ " + fert.secondTopDressingSchedule, fontSize = 12.sp, lineHeight = 16.sp)
                                    }
                                }

                                // Booster Tip
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = Color(0xFF165231).copy(alpha = 0.3f),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25A25A))
                                ) {
                                    Text(
                                        text = fert.micronutrientTip,
                                        modifier = Modifier.padding(10.dp),
                                        fontSize = 12.sp,
                                        color = Color(0xFF86EFAC),
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // =========================================================================
            // TAB 2: SOIL TEST DIAGNOSIS
            // =========================================================================
            if (screenMode == SoilScreenMode.DIAGNOSIS) {
                // Soil Type Selector
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = AppStrings.get("soil_select_type", userLanguageCode),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                items(viewModel.soilTypesList) { type ->
                                    val isSelected = soilType == type
                                    Surface(
                                        onClick = {
                                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                                            viewModel.setSoilType(type)
                                        },
                                        shape = RoundedCornerShape(12.dp),
                                        color = if (isSelected) Color(0xFF165231) else MaterialTheme.colorScheme.surface,
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (isSelected) Color(0xFF25A25A) else MaterialTheme.colorScheme.outline
                                        )
                                    ) {
                                        Text(
                                            text = type,
                                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Parameter Inputs (pH, N, P, K)
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = AppStrings.get("soil_test_params", userLanguageCode),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedTextField(
                                    value = phInput,
                                    onValueChange = { viewModel.setPh(it) },
                                    label = { Text(AppStrings.get("soil_ph", userLanguageCode)) },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = nitrogenInput,
                                    onValueChange = { viewModel.setNitrogen(it) },
                                    label = { Text(AppStrings.get("soil_nitrogen", userLanguageCode)) },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                OutlinedTextField(
                                    value = phosphorusInput,
                                    onValueChange = { viewModel.setPhosphorus(it) },
                                    label = { Text(AppStrings.get("soil_phosphorus", userLanguageCode)) },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f)
                                )
                                OutlinedTextField(
                                    value = potassiumInput,
                                    onValueChange = { viewModel.setPotassium(it) },
                                    label = { Text(AppStrings.get("soil_potassium", userLanguageCode)) },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f)
                                )
                            }

                            Button(
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.calculateAdvisory()
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Icon(Icons.Default.Calculate, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(AppStrings.get("soil_analyze_btn", userLanguageCode), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }

                // Results Card
                result?.let { res ->
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF25A25A))
                        ) {
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(14.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text("📋", fontSize = 22.sp)
                                        Text(
                                            text = AppStrings.get("soil_report_title", userLanguageCode),
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(8.dp),
                                        color = if (res.confidence == "High") Color(0xFF165231) else Color(0xFF854D0E)
                                    ) {
                                        Text(
                                            text = "Confidence: ${res.confidence}",
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )
                                    }
                                }

                                Text(
                                    text = res.summary,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

                                // Deficiencies
                                Text(
                                    text = AppStrings.get("soil_deficiencies", userLanguageCode),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.error
                                )
                                res.deficiencies.forEach { def ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("•", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                                        Text(def, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    }
                                }

                                HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))

                                // Fertilizer Recommendations
                                Text(
                                    text = AppStrings.get("soil_recommendations", userLanguageCode),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                res.fertilizerRecommendations.forEach { rec ->
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("✓", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                        Text(rec, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
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
