package com.example.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ripple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

enum class GlassButtonStyle {
    NUMBER,
    OPERATOR,
    EQUALS,
    ACCENT_TEXT,
    MEMORY,
    BADGE
}

@Composable
fun GlassCalcButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: GlassButtonStyle = GlassButtonStyle.NUMBER,
    isDarkMode: Boolean = false,
    fontSize: TextUnit = 24.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    shape: Shape = CircleShape,
    testTag: String = "btn_$text",
    content: (@Composable () -> Unit)? = null
) {
    val interactionSource = remember { MutableInteractionSource() }

    val backgroundBrush: Brush
    val borderColor: Color
    val textColor: Color

    when (style) {
        GlassButtonStyle.NUMBER -> {
            backgroundBrush = Brush.linearGradient(
                if (isDarkMode) listOf(GlassBtnDarkBgStart, GlassBtnDarkBgEnd)
                else listOf(GlassBtnLightBgStart, GlassBtnLightBgEnd)
            )
            borderColor = if (isDarkMode) GlassBtnDarkBorder else GlassBtnLightBorder
            textColor = if (isDarkMode) Color.White else Slate900
        }
        GlassButtonStyle.OPERATOR -> {
            backgroundBrush = Brush.linearGradient(
                if (isDarkMode) listOf(GlassOpDarkBgStart, GlassOpDarkBgEnd)
                else listOf(GlassOpLightBgStart, GlassOpLightBgEnd)
            )
            borderColor = if (isDarkMode) Color(0x66F87171) else Color(0xCCFFCDD2)
            textColor = if (isDarkMode) CoralRedSubtle else CoralRed
        }
        GlassButtonStyle.EQUALS -> {
            backgroundBrush = Brush.linearGradient(
                if (isDarkMode) listOf(Color(0xFFF87171), Color(0xFFDC2626))
                else listOf(Color(0xFFFB7185), Color(0xFFE11D48))
            )
            borderColor = Color(0x99FFFFFF)
            textColor = Color.White
        }
        GlassButtonStyle.ACCENT_TEXT -> {
            backgroundBrush = Brush.linearGradient(
                if (isDarkMode) listOf(GlassBtnDarkBgStart, GlassBtnDarkBgEnd)
                else listOf(GlassBtnLightBgStart, GlassBtnLightBgEnd)
            )
            borderColor = if (isDarkMode) GlassBtnDarkBorder else GlassBtnLightBorder
            textColor = if (isDarkMode) Color(0xFFF87171) else CoralRed
        }
        GlassButtonStyle.MEMORY -> {
            backgroundBrush = Brush.linearGradient(
                if (isDarkMode) listOf(GlassBtnDarkBgStart, GlassBtnDarkBgEnd)
                else listOf(GlassBtnLightBgStart, GlassBtnLightBgEnd)
            )
            borderColor = if (isDarkMode) GlassBtnDarkBorder else GlassBtnLightBorder
            textColor = if (isDarkMode) Slate400 else Slate700
        }
        GlassButtonStyle.BADGE -> {
            backgroundBrush = Brush.linearGradient(
                if (isDarkMode) listOf(Color(0x40EF4444), Color(0x26EF4444))
                else listOf(Color(0x33EF4444), Color(0x1AEF4444))
            )
            borderColor = CoralRed
            textColor = if (isDarkMode) CoralRedSubtle else CoralRed
        }
    }

    val shadowElevation = if (style == GlassButtonStyle.EQUALS) 8.dp else 4.dp

    Box(
        modifier = modifier
            .testTag(testTag)
            .shadow(shadowElevation, shape, clip = false)
            .clip(shape)
            .background(backgroundBrush)
            .border(1.dp, borderColor, shape)
            .clickable(
                interactionSource = interactionSource,
                indication = ripple(bounded = true, color = if (style == GlassButtonStyle.EQUALS) Color.White else CoralRed),
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        // Subtle top reflection shine
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(28.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0x40FFFFFF), Color.Transparent)
                    )
                )
        )

        if (content != null) {
            content()
        } else {
            Text(
                text = text,
                color = textColor,
                fontSize = fontSize,
                fontWeight = fontWeight,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    isDarkMode: Boolean = false,
    isActive: Boolean = false,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val borderColor = when {
        isActive -> if (isDarkMode) CoralRedSubtle else CoralRed
        isDarkMode -> Color(0x33FFFFFF)
        else -> Color(0x80FFFFFF)
    }

    val bgBrush = Brush.linearGradient(
        if (isDarkMode) listOf(Color(0x330F172A), Color(0x1A0F172A))
        else listOf(Color(0xB3FFFFFF), Color(0x80FFFFFF))
    )

    val boxModifier = modifier
        .shadow(if (isActive) 8.dp else 3.dp, shape)
        .clip(shape)
        .background(bgBrush)
        .border(if (isActive) 2.dp else 1.dp, borderColor, shape)
        .then(
            if (onClick != null) {
                Modifier.clickable(onClick = onClick)
            } else Modifier
        )
        .padding(14.dp)

    Box(modifier = boxModifier) {
        content()
    }
}

@Composable
fun BlinkingCursor(
    color: Color = CoralRed,
    height: Dp = 26.dp,
    width: Dp = 2.dp
) {
    val transition = rememberInfiniteTransition(label = "cursor")
    val alpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(500),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cursorAlpha"
    )

    Box(
        modifier = Modifier
            .width(width)
            .height(height)
            .background(color.copy(alpha = alpha))
    )
}
