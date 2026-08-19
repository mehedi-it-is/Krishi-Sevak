package com.krishisevak.app.ui.crop

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.krishisevak.app.utils.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CropRecommendScreen(
    viewModel: CropRecommendViewModel,
    onBackClick: () -> Unit
) {
    val userLanguageCode by viewModel.userLanguageCode.collectAsStateWithLifecycle()
    val selectedSeason by viewModel.selectedSeason.collectAsStateWithLifecycle()
    val selectedWater by viewModel.selectedWater.collectAsStateWithLifecycle()
    val selectedSoil by viewModel.selectedSoil.collectAsStateWithLifecycle()
    val previousCrop by viewModel.previousCrop.collectAsStateWithLifecycle()
    val recommendations by viewModel.recommendations.collectAsStateWithLifecycle()
    val currentlySpeakingId by viewModel.ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()

    val isSpeaking = currentlySpeakingId == "crop_recommendations"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = AppStrings.get("crop_rec_title", userLanguageCode),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = AppStrings.get("crop_rec_subtitle", userLanguageCode),
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
                    val summaryTts = "Recommended crops for your farm: " + recommendations.joinToString(". ") {
                        "${it.cropName} with ${it.suitabilityScore} percent match. ${it.reason}"
                    }
                    IconButton(onClick = { viewModel.toggleTts("crop_recommendations", summaryTts) }) {
                        Icon(
                            imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = "Listen Recommendations",
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
            // Filter Controls Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        // Season Selector
                        Text(AppStrings.get("crop_season", userLanguageCode), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(viewModel.seasonsList) { season ->
                                val isSelected = selectedSeason == season
                                val localizedSeason = com.krishisevak.app.data.engine.SmartAgriToolsTranslations.getSeasonName(season, userLanguageCode)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setSeason(season) },
                                    label = { Text(localizedSeason, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                                )
                            }
                        }

                        // Water Availability
                        Text(AppStrings.get("crop_water", userLanguageCode), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(viewModel.waterList) { water ->
                                val isSelected = selectedWater == water
                                val localizedWater = com.krishisevak.app.data.engine.SmartAgriToolsTranslations.getWaterLevelName(water, userLanguageCode)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setWater(water) },
                                    label = { Text(localizedWater, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                                )
                            }
                        }

                        // Soil Type
                        Text(AppStrings.get("crop_soil", userLanguageCode), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(viewModel.soilList) { soil ->
                                val isSelected = selectedSoil == soil
                                val localizedSoil = com.krishisevak.app.data.engine.SmartAgriToolsTranslations.getSoilTypeName(soil, userLanguageCode)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setSoil(soil) },
                                    label = { Text(localizedSoil, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                                )
                            }
                        }

                        // Previous Crop
                        Text(AppStrings.get("crop_prev", userLanguageCode), fontWeight = FontWeight.Bold, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(viewModel.previousCropList) { crop ->
                                val isSelected = previousCrop == crop
                                val localizedPrev = com.krishisevak.app.data.engine.SmartAgriToolsTranslations.getCropName(crop, userLanguageCode)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { viewModel.setPreviousCrop(crop) },
                                    label = { Text(localizedPrev, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) }
                                )
                            }
                        }
                    }
                }
            }

            // Results Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = AppStrings.get("crop_ranked_title", userLanguageCode),
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${recommendations.size} Options",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Crop Items List
            items(recommendations) { crop ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
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
                                Text(crop.iconEmoji, fontSize = 24.sp)
                                Column {
                                    Text(
                                        text = crop.cropName,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "${AppStrings.get("crop_duration", userLanguageCode)}: ${crop.growingDuration}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                }
                            }

                            // Match Score Badge
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = if (crop.suitabilityScore >= 80) Color(0xFF165231) else Color(0xFF854D0E)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Text("⭐", fontSize = 10.sp)
                                    Text(
                                        text = "${crop.suitabilityScore}% ${AppStrings.get("crop_match", userLanguageCode)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = Color.White,
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        Text(
                            text = crop.reason,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 17.sp
                        )

                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(AppStrings.get("crop_water_need", userLanguageCode), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(crop.waterRequirement, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(AppStrings.get("crop_expected_yield", userLanguageCode), fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(crop.expectedYield, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, maxLines = 1)
                            }
                        }

                        // Fertilizer Tip
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text("💡", fontSize = 14.sp)
                                Text(
                                    text = crop.fertilizerTip,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
