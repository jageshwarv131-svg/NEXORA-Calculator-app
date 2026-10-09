package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.GlassButtonStyle
import com.example.ui.components.GlassCalcButton
import com.example.ui.theme.CoralRed
import com.example.ui.theme.CoralRedSubtle
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

@Composable
fun StandardCalcScreen(
    displayValue: String,
    expression: String,
    isDarkMode: Boolean,
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onDotClick: () -> Unit,
    onEqualsClick: () -> Unit,
    onClearAll: () -> Unit,
    onDeleteLast: () -> Unit,
    onToggleSign: () -> Unit,
    onPercentageClick: () -> Unit,
    onMemoryClear: () -> Unit,
    onMemoryAdd: () -> Unit,
    onMemorySubtract: () -> Unit,
    onMemoryRecall: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = expression,
                color = if (isDarkMode) Slate400 else Slate700,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(6.dp))

            val fontSize = when {
                displayValue.length > 11 -> 36.sp
                displayValue.length > 8 -> 46.sp
                else -> 60.sp
            }

            Text(
                text = displayValue,
                color = if (isDarkMode) Color.White else Slate900,
                fontSize = fontSize,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Keypad Grid (Standard: 4 columns x 6 rows)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Row 1: Memory
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCalcButton(text = "mc", onClick = onMemoryClear, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.MEMORY, isDarkMode = isDarkMode, fontSize = 18.sp)
                GlassCalcButton(text = "m+", onClick = onMemoryAdd, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.MEMORY, isDarkMode = isDarkMode, fontSize = 18.sp)
                GlassCalcButton(text = "m-", onClick = onMemorySubtract, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.MEMORY, isDarkMode = isDarkMode, fontSize = 18.sp)
                GlassCalcButton(text = "mr", onClick = onMemoryRecall, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 18.sp)
            }

            // Row 2: AC, Delete, +/-, ÷
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCalcButton(text = "AC", onClick = onClearAll, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                GlassCalcButton(
                    text = "del",
                    onClick = onDeleteLast,
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    style = GlassButtonStyle.ACCENT_TEXT,
                    isDarkMode = isDarkMode
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Backspace,
                        contentDescription = "Delete",
                        tint = if (isDarkMode) CoralRedSubtle else CoralRed,
                        modifier = Modifier.size(24.dp)
                    )
                }
                GlassCalcButton(text = "+/-", onClick = onToggleSign, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 22.sp)
                GlassCalcButton(text = "÷", onClick = { onOperatorClick("÷") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 28.sp)
            }

            // Row 3: 7, 8, 9, ×
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCalcButton(text = "7", onClick = { onNumberClick("7") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "8", onClick = { onNumberClick("8") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "9", onClick = { onNumberClick("9") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "×", onClick = { onOperatorClick("×") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 28.sp)
            }

            // Row 4: 4, 5, 6, -
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCalcButton(text = "4", onClick = { onNumberClick("4") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "5", onClick = { onNumberClick("5") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "6", onClick = { onNumberClick("6") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "-", onClick = { onOperatorClick("-") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 28.sp)
            }

            // Row 5: 1, 2, 3, +
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCalcButton(text = "1", onClick = { onNumberClick("1") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "2", onClick = { onNumberClick("2") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "3", onClick = { onNumberClick("3") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "+", onClick = { onOperatorClick("+") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 28.sp)
            }

            // Row 6: %, 0, ., =
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                GlassCalcButton(text = "%", onClick = onPercentageClick, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 22.sp)
                GlassCalcButton(text = "0", onClick = { onNumberClick("0") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = ".", onClick = onDotClick, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                GlassCalcButton(text = "=", onClick = onEqualsClick, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.EQUALS, isDarkMode = isDarkMode, fontSize = 28.sp)
            }
        }
    }
}
