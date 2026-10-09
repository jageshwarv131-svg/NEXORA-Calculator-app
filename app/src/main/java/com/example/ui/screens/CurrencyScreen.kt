package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CurrencyData
import com.example.data.CurrencyItem
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
fun CurrencyScreen(
    curr1: String,
    curr2: String,
    val1: String,
    val2: String,
    activeBox: Int, // 1 or 2
    ratePairText: String,
    rateUpdateTime: String,
    isDarkMode: Boolean,
    onBoxSelected: (Int) -> Unit,
    onOpenPicker: (Int) -> Unit,
    onSwapCurrencies: () -> Unit,
    onNumberClick: (String) -> Unit,
    onDotClick: () -> Unit,
    onDeleteLast: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currency1Item = CurrencyData.currencies.find { it.code == curr1 }
        ?: CurrencyItem(curr1, curr1, "🌐", "$")
    val currency2Item = CurrencyData.currencies.find { it.code == curr2 }
        ?: CurrencyItem(curr2, curr2, "🌐", "€")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Upper conversion boxes area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Box 1
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                isDarkMode = isDarkMode,
                isActive = activeBox == 1,
                onClick = { onBoxSelected(1) }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenPicker(1) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = currency1Item.flag, fontSize = 24.sp)
                            Text(
                                text = "${currency1Item.code} - ${currency1Item.name}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isDarkMode) Slate400 else Slate700
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select currency 1",
                            tint = if (isDarkMode) Slate400 else Slate700
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = val1,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else Slate900,
                            textAlign = TextAlign.End
                        )
                        if (activeBox == 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                            BlinkingCursor(color = if (isDarkMode) CoralRedSubtle else CoralRed)
                        }
                    }
                }
            }

            // Box 2
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                isDarkMode = isDarkMode,
                isActive = activeBox == 2,
                onClick = { onBoxSelected(2) }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onOpenPicker(2) },
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = currency2Item.flag, fontSize = 24.sp)
                            Text(
                                text = "${currency2Item.code} - ${currency2Item.name}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isDarkMode) Slate400 else Slate700
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select currency 2",
                            tint = if (isDarkMode) Slate400 else Slate700
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = val2,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else Slate900,
                            textAlign = TextAlign.End
                        )
                        if (activeBox == 2) {
                            Spacer(modifier = Modifier.width(4.dp))
                            BlinkingCursor(color = if (isDarkMode) CoralRedSubtle else CoralRed)
                        }
                    }
                }
            }

            // Rate info pill
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "💰 $ratePairText",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDarkMode) Color.White else Slate900
                )
                Text(
                    text = "Rates Updated: $rateUpdateTime",
                    fontSize = 12.sp,
                    color = if (isDarkMode) Slate400 else Slate700
                )
            }
        }

        // Numeric Keypad
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlassCalcButton(text = "7", onClick = { onNumberClick("7") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "8", onClick = { onNumberClick("8") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "9", onClick = { onNumberClick("9") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "⌫", onClick = onDeleteLast, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 22.sp)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlassCalcButton(text = "4", onClick = { onNumberClick("4") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "5", onClick = { onNumberClick("5") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "6", onClick = { onNumberClick("6") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(
                    text = "⇅",
                    onClick = onSwapCurrencies,
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    style = GlassButtonStyle.OPERATOR,
                    isDarkMode = isDarkMode,
                    fontSize = 26.sp
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlassCalcButton(text = "1", onClick = { onNumberClick("1") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "2", onClick = { onNumberClick("2") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "3", onClick = { onNumberClick("3") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = "AC", onClick = onClearAll, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                GlassCalcButton(text = "00", onClick = { onNumberClick("00") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 20.sp)
                GlassCalcButton(text = "0", onClick = { onNumberClick("0") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode)
                GlassCalcButton(text = ".", onClick = onDotClick, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                GlassCalcButton(text = "✓", onClick = {}, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.EQUALS, isDarkMode = isDarkMode, fontSize = 24.sp)
            }
        }
    }
}
