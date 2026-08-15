package com.kisaandost.app.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.kisaandost.app.utils.TtsManager

data class TutorialStep(
    val emoji: String,
    val titleEn: String,
    val titleHi: String,
    val descEn: String,
    val descHi: String,
    val tipEn: String,
    val tipHi: String,
    val color: Color
)

val tutorialSteps = listOf(
    TutorialStep(
        emoji = "🌦️",
        titleEn = "Agro-Weather & Sowing Window",
        titleHi = "कृषि मौसम एवं बुवाई सलाहकार",
        descEn = "Get 7-day hyper-local weather forecasts, rain probability, humidity alerts, and ideal spraying times calibrated for your farm.",
        descHi = "7-दिवसीय सटीक मौसम पूर्वानुमान, वर्षा की संभावना, आर्द्रता अलर्ट और कीटनाशक छिड़काव के सर्वोत्तम समय की जानकारी प्राप्त करें।",
        tipEn = "Tip: Tap the speaker icon on the weather card to listen to the audio forecast.",
        tipHi = "सुझाव: मौसम कार्ड पर स्पीकर आइकन दबाकर पूरी मौसम रिपोर्ट सुनें।",
        color = Color(0xFF0284C7)
    ),
    TutorialStep(
        emoji = "🏛️",
        titleEn = "Real-Time Mandi Bhav",
        titleHi = "ताज़ा मंडी भाव और लाइव कीमतें",
        descEn = "Explore live crop prices across 60+ commodities from your nearest APMC Mandi, with distance tracking and price trend indicators.",
        descHi = "अपने निकटतम APMC मंडी से 60 से अधिक फसलों के ताज़ा भाव, दूरी और मूल्य में तेजी/मंदी का रुझान देखें।",
        tipEn = "Tip: Use category filters (Vegetables, Fruits, Grains) or search bar to find any crop.",
        tipHi = "सुझाव: किसी भी फसल का भाव जानने के लिए सर्च बार या श्रेणी फिल्टर का उपयोग करें।",
        color = Color(0xFF16A34A)
    ),
    TutorialStep(
        emoji = "🩺",
        titleEn = "Multimodal Crop Doctor",
        titleHi = "एआई फसल डॉक्टर - पत्तों की जांच",
        descEn = "Snap a photo of diseased leaves or crops to instantly diagnose fungal, pest, or nutrient deficiencies with organic & chemical cure steps.",
        descHi = "फसल की बीमारी या पत्तों की फोटो लें और एआई द्वारा तुरंत बीमारी की पहचान एवं जैविक व रासायनिक उपचार पाएं।",
        tipEn = "Tip: Tap the Camera button in Crop Doctor to diagnose your leaf sample.",
        tipHi = "सुझाव: फसल डॉक्टर में कैमरा बटन दबाकर तुरंत पत्ते की जांच करें।",
        color = Color(0xFFEF4444)
    ),
    TutorialStep(
        emoji = "📜",
        titleEn = "Government Schemes & Subsidies",
        titleHi = "सरकारी योजनाएं एवं प्रत्यक्ष सब्सिडी",
        descEn = "Browse central and state-specific agricultural schemes (PM-Kisan, PMFBY, KCC, Solar Pumps) with direct links to official government portals.",
        descHi = "पीएम-किसान, फसल बीमा, केसीसी और सोलर पंप जैसी केंद्र व राज्य सरकार की योजनाओं की जानकारी और सीधे आधिकारिक वेबसाइट लिंक पाएं।",
        tipEn = "Tip: Tap any scheme card to expand eligibility, benefits, and the official apply link.",
        tipHi = "सुझाव: पात्रता और आवेदन लिंक देखने के लिए किसी भी योजना कार्ड पर टैप करें।",
        color = Color(0xFFF59E0B)
    ),
    TutorialStep(
        emoji = "🎙️",
        titleEn = "Voice AI in 11 Indian Languages",
        titleHi = "11 भारतीय भाषाओं में बोलकर पूछें",
        descEn = "Type or speak naturally in Hindi, English, Bengali, Telugu, Marathi, Tamil, Gujarati, Kannada, Punjabi, Malayalam, or Odia. The AI responds in your exact language with voice read-aloud.",
        descHi = "अपनी मातृभाषा में बोलकर खेती से जुड़ा कोई भी सवाल पूछें। किसान दोस्त आपकी ही भाषा में तुरंत उत्तर और बोलकर सलाह देगा।",
        tipEn = "Tip: Tap the Mic button at the bottom of the dashboard or chat screen to start talking.",
        tipHi = "सुझाव: बात करने के लिए डैशबोर्ड के नीचे माइक बटन दबाएं।",
        color = Color(0xFF8B5CF6)
    )
)

@Composable
fun AppTutorialDialog(
    userLanguageCode: String,
    ttsManager: TtsManager,
    onDismiss: () -> Unit
) {
    var currentStepIndex by remember { mutableIntStateOf(0) }
    val step = tutorialSteps[currentStepIndex]
    val isHindi = userLanguageCode != "en"

    val title = if (isHindi) step.titleHi else step.titleEn
    val desc = if (isHindi) step.descHi else step.descEn
    val tip = if (isHindi) step.tipHi else step.tipEn

    val currentlySpeakingId by ttsManager.currentlySpeakingId.collectAsState()
    val isSpeaking = currentlySpeakingId == "tutorial_step_${currentStepIndex}"

    Dialog(
        onDismissRequest = {
            ttsManager.stop()
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, step.color.copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with Step Indicator & Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = step.color.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "Step ${currentStepIndex + 1} of ${tutorialSteps.size}",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = step.color
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                val textToSpeak = "$title. $desc. $tip"
                                ttsManager.speak("tutorial_step_${currentStepIndex}", textToSpeak, userLanguageCode)
                            }
                        ) {
                            Icon(
                                imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Listen",
                                tint = step.color
                            )
                        }

                        IconButton(
                            onClick = {
                                ttsManager.stop()
                                onDismiss()
                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }

                // Step Emoji Badge
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(step.color.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = step.emoji, fontSize = 42.sp)
                }

                // Step Title
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                // Step Description
                Text(
                    text = desc,
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                // Tip Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("💡", fontSize = 18.sp)
                        Text(
                            text = tip,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Step Dots Progress Indicator
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    tutorialSteps.indices.forEach { index ->
                        val isCurrent = index == currentStepIndex
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(if (isCurrent) 24.dp else 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isCurrent) step.color else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                // Navigation Buttons (Back / Next / Finish)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentStepIndex > 0) {
                        OutlinedButton(
                            onClick = {
                                ttsManager.stop()
                                currentStepIndex--
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Back")
                        }
                    } else {
                        TextButton(
                            onClick = {
                                ttsManager.stop()
                                onDismiss()
                            }
                        ) {
                            Text("Skip Tour", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Button(
                        onClick = {
                            ttsManager.stop()
                            if (currentStepIndex < tutorialSteps.size - 1) {
                                currentStepIndex++
                            } else {
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = step.color)
                    ) {
                        Text(if (currentStepIndex < tutorialSteps.size - 1) "Next" else "Get Started 🎉", fontWeight = FontWeight.Bold)
                        if (currentStepIndex < tutorialSteps.size - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}
