package com.kisaandost.app.ui.learn

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kisaandost.app.utils.AppStrings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearnScreen(
    viewModel: LearnViewModel,
    onBackClick: () -> Unit
) {
    val userLanguageCode by viewModel.userLanguageCode.collectAsStateWithLifecycle()
    val tips by viewModel.tipsList.collectAsStateWithLifecycle()
    val currentlySpeakingId by viewModel.ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()

    var expandedTipId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = AppStrings.get("learn_title", userLanguageCode),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = AppStrings.get("learn_subtitle", userLanguageCode),
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text(
                    text = "${AppStrings.get("learn_guides_title", userLanguageCode)} (${tips.size})",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(tips) { tip ->
                val isExpanded = expandedTipId == tip.id
                val isSpeaking = currentlySpeakingId == "learn_tip_${tip.id}"
                val speakText = "${tip.title}. ${tip.summary}. ${tip.detailedContent}. Key steps: ${tip.practicalSteps.joinToString(". ")}"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            expandedTipId = if (isExpanded) null else tip.id
                        },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSpeaking) Color(0xFF1B3828) else MaterialTheme.colorScheme.surfaceVariant
                    ),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isSpeaking) Color(0xFF25A25A) else MaterialTheme.colorScheme.outline
                    )
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
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(tip.iconEmoji, fontSize = 24.sp)
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = tip.title,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        color = if (isSpeaking) Color.White else MaterialTheme.colorScheme.onSurface,
                                        maxLines = 1,
                                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                    )
                                    Text(
                                        text = "Audio Guide Enabled 🔊",
                                        fontSize = 10.sp,
                                        color = if (isSpeaking) Color(0xFF86EFAC) else MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            IconButton(
                                onClick = { viewModel.toggleTts("learn_tip_${tip.id}", speakText) },
                                modifier = Modifier.background(
                                    if (isSpeaking) Color(0xFF22C55E) else MaterialTheme.colorScheme.primaryContainer,
                                    CircleShape
                                )
                            ) {
                                Icon(
                                    imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                    contentDescription = "Read Aloud Guide",
                                    tint = if (isSpeaking) Color.Black else MaterialTheme.colorScheme.primary
                                )
                            }
                        }

                        Text(
                            text = tip.summary,
                            fontSize = 13.sp,
                            color = if (isSpeaking) Color(0xFFE2E8F0) else MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 18.sp
                        )

                        if (isExpanded) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))

                            Text(
                                text = tip.detailedContent,
                                fontSize = 13.sp,
                                color = if (isSpeaking) Color(0xFFF1F5F9) else MaterialTheme.colorScheme.onSurface,
                                lineHeight = 19.sp
                            )

                            Text(
                                text = AppStrings.get("learn_checklist", userLanguageCode),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = if (isSpeaking) Color(0xFF4ADE80) else MaterialTheme.colorScheme.primary
                            )

                            tip.practicalSteps.forEachIndexed { index, step ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text(
                                        text = "${index + 1}.",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (isSpeaking) Color(0xFF4ADE80) else MaterialTheme.colorScheme.primary
                                    )
                                    Text(
                                        text = step,
                                        fontSize = 13.sp,
                                        color = if (isSpeaking) Color(0xFFF1F5F9) else MaterialTheme.colorScheme.onSurfaceVariant,
                                        lineHeight = 18.sp
                                    )
                                }
                            }
                        } else {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = AppStrings.get("learn_tap_view", userLanguageCode),
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
