package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.BlinkingCursor
import com.example.ui.components.GlassButtonStyle
import com.example.ui.components.GlassCalcButton
import com.example.ui.components.GlassCard
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CoralRedSubtle
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

@Composable
fun MassScreen(
    massValues: Map<String, String>,
    activeUnit: String,
    isDarkMode: Boolean,
    onUnitSelected: (String) -> Unit,
    onNumberClick: (String) -> Unit,
    onDotClick: () -> Unit,
    onDeleteLast: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val units = listOf(
        "kg" to "Kilogram (kg)",
        "lb" to "Pound (lb)",
        "g" to "Gram (g)",
        "oz" to "Ounce (oz)"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Mass Boxes Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            units.forEach { (key, label) ->
                val isActive = activeUnit == key
                val value = massValues[key] ?: "0"

                GlassCard(
                    modifier = Modifier.fillMaxWidth(),
                    isDarkMode = isDarkMode,
                    isActive = isActive,
                    onClick = { onUnitSelected(key) }
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = label,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDarkMode) Slate400 else Slate700
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = value,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDarkMode) Color.White else Slate900,
                                textAlign = TextAlign.End
                            )
                            if (isActive) {
                                Spacer(modifier = Modifier.width(3.dp))
                                BlinkingCursor(color = if (isDarkMode) CoralRedSubtle else CoralRed)
                            }
                        }
                    }
                }
            }
        }

        // Standard Keypad (4 columns, exactly matching HTML)
        val gap = 10.dp
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(gap)
        ) {
            // Row 1: 7, 8, 9, ⌫
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "7", onClick = { onNumberClick("7") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "8", onClick = { onNumberClick("8") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "9", onClick = { onNumberClick("9") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "⌫", onClick = onDeleteLast, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 22.sp)
            }

            // Row 2: 4, 5, 6, AC
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "4", onClick = { onNumberClick("4") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "5", onClick = { onNumberClick("5") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "6", onClick = { onNumberClick("6") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "AC", onClick = onClearAll, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            // Row 3: 1, 2, 3, 00
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "1", onClick = { onNumberClick("1") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "2", onClick = { onNumberClick("2") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "3", onClick = { onNumberClick("3") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "00", onClick = { onNumberClick("00") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 20.sp)
            }

            // Row 4: 0, ., ✓ (span 2 with matching intrinsic height)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                GlassCalcButton(text = "0", onClick = { onNumberClick("0") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = ".", onClick = onDotClick, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                GlassCalcButton(
                    text = "✓",
                    onClick = {},
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    style = GlassButtonStyle.EQUALS,
                    shape = RoundedCornerShape(28.dp),
                    isDarkMode = isDarkMode,
                    fontSize = 24.sp
                )
            }
        }
    }
}
