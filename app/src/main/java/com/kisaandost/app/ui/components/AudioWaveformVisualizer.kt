package com.kisaandost.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AudioWaveformVisualizer(
    isRecording: Boolean,
    modifier: Modifier = Modifier
) {
    if (!isRecording) return

    val infiniteTransition = rememberInfiniteTransition(label = "WaveformAnimation")

    val bar1Height by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 28f,
        animationSpec = infiniteRepeatable(
            animation = tween(400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bar1"
    )

    val bar2Height by infiniteTransition.animateFloat(
        initialValue = 12f,
        targetValue = 34f,
        animationSpec = infiniteRepeatable(
            animation = tween(550, delayMillis = 100, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bar2"
    )

    val bar3Height by infiniteTransition.animateFloat(
        initialValue = 6f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(350, delayMillis = 200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bar3"
    )

    val bar4Height by infiniteTransition.animateFloat(
        initialValue = 14f,
        targetValue = 38f,
        animationSpec = infiniteRepeatable(
            animation = tween(480, delayMillis = 50, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bar4"
    )

    val bar5Height by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 26f,
        animationSpec = infiniteRepeatable(
            animation = tween(420, delayMillis = 150, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Bar5"
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF165231).copy(alpha = 0.9f))
            .padding(horizontal = 14.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text("🎙️ Listening...", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(6.dp))

        Box(modifier = Modifier.width(4.dp).height(bar1Height.dp).clip(RoundedCornerShape(2.dp)).background(Color(0xFF22C55E)))
        Box(modifier = Modifier.width(4.dp).height(bar2Height.dp).clip(RoundedCornerShape(2.dp)).background(Color(0xFF86EFAC)))
        Box(modifier = Modifier.width(4.dp).height(bar3Height.dp).clip(RoundedCornerShape(2.dp)).background(Color(0xFF22C55E)))
        Box(modifier = Modifier.width(4.dp).height(bar4Height.dp).clip(RoundedCornerShape(2.dp)).background(Color(0xFF4ADE80)))
        Box(modifier = Modifier.width(4.dp).height(bar5Height.dp).clip(RoundedCornerShape(2.dp)).background(Color(0xFF22C55E)))
    }
}
