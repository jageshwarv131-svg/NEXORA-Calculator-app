package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
fun TipScreen(
    bill: String,
    people: String,
    tip: String,
    tax: String,
    tipIsPercent: Boolean,
    taxIsPercent: Boolean,
    noTipOnTax: Boolean,
    totalResult: String,
    perPersonResult: String,
    activeField: String, // "bill", "people", "tip", "tax"
    isDarkMode: Boolean,
    onFieldSelected: (String) -> Unit,
    onTipTypeChanged: (Boolean) -> Unit,
    onTaxTypeChanged: (Boolean) -> Unit,
    onNoTipOnTaxChanged: (Boolean) -> Unit,
    onNumberClick: (String) -> Unit,
    onDotClick: () -> Unit,
    onDeleteLast: () -> Unit,
    onClearAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Scrollable Input & Result area (Tool container)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 6.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Bill Box
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                isDarkMode = isDarkMode,
                isActive = activeField == "bill",
                onClick = { onFieldSelected("bill") }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Bill",
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
                            text = bill,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else Slate900,
                            textAlign = TextAlign.End
                        )
                        if (activeField == "bill") {
                            Spacer(modifier = Modifier.width(3.dp))
                            BlinkingCursor(color = if (isDarkMode) CoralRedSubtle else CoralRed)
                        }
                    }
                }
            }

            // People Box
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                isDarkMode = isDarkMode,
                isActive = activeField == "people",
                onClick = { onFieldSelected("people") }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "People",
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
                            text = people,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else Slate900,
                            textAlign = TextAlign.End
                        )
                        if (activeField == "people") {
                            Spacer(modifier = Modifier.width(3.dp))
                            BlinkingCursor(color = if (isDarkMode) CoralRedSubtle else CoralRed)
                        }
                    }
                }
            }

            // Tip Rate Box
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                isDarkMode = isDarkMode,
                isActive = activeField == "tip",
                onClick = { onFieldSelected("tip") }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tip",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDarkMode) Slate400 else Slate700
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { onTipTypeChanged(true) }
                            ) {
                                RadioButton(
                                    selected = tipIsPercent,
                                    onClick = { onTipTypeChanged(true) },
                                    colors = RadioButtonDefaults.colors(selectedColor = CoralRed)
                                )
                                Text("%", fontSize = 13.sp, color = if (isDarkMode) Color.White else Slate900)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { onTipTypeChanged(false) }
                            ) {
                                RadioButton(
                                    selected = !tipIsPercent,
                                    onClick = { onTipTypeChanged(false) },
                                    colors = RadioButtonDefaults.colors(selectedColor = CoralRed)
                                )
                                Text("Val", fontSize = 13.sp, color = if (isDarkMode) Color.White else Slate900)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tip,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else Slate900,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = if (tipIsPercent) "%" else "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (isDarkMode) Slate400 else Slate700,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                        if (activeField == "tip") {
                            Spacer(modifier = Modifier.width(3.dp))
                            BlinkingCursor(color = if (isDarkMode) CoralRedSubtle else CoralRed)
                        }
                    }
                }
            }

            // Tax Rate Box
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                isDarkMode = isDarkMode,
                isActive = activeField == "tax",
                onClick = { onFieldSelected("tax") }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tax Rate",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDarkMode) Slate400 else Slate700
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { onTaxTypeChanged(true) }
                            ) {
                                RadioButton(
                                    selected = taxIsPercent,
                                    onClick = { onTaxTypeChanged(true) },
                                    colors = RadioButtonDefaults.colors(selectedColor = CoralRed)
                                )
                                Text("%", fontSize = 13.sp, color = if (isDarkMode) Color.White else Slate900)
                            }
                            Spacer(modifier = Modifier.width(4.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { onTaxTypeChanged(false) }
                            ) {
                                RadioButton(
                                    selected = !taxIsPercent,
                                    onClick = { onTaxTypeChanged(false) },
                                    colors = RadioButtonDefaults.colors(selectedColor = CoralRed)
                                )
                                Text("Val", fontSize = 13.sp, color = if (isDarkMode) Color.White else Slate900)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = tax,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else Slate900,
                            textAlign = TextAlign.End
                        )
                        Text(
                            text = if (taxIsPercent) "%" else "",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Normal,
                            color = if (isDarkMode) Slate400 else Slate700,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                        if (activeField == "tax") {
                            Spacer(modifier = Modifier.width(3.dp))
                            BlinkingCursor(color = if (isDarkMode) CoralRedSubtle else CoralRed)
                        }
                    }
                }
            }

            // Do not tip on tax Toggle Box
            GlassCard(modifier = Modifier.fillMaxWidth(), isDarkMode = isDarkMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Do not tip on tax",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDarkMode) Color.White else Slate900
                    )
                    Switch(
                        checked = noTipOnTax,
                        onCheckedChange = onNoTipOnTaxChanged,
                        colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CoralRed)
                    )
                }
            }

            // RESULTS Summary Box
            GlassCard(modifier = Modifier.fillMaxWidth(), isDarkMode = isDarkMode) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "RESULTS",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isDarkMode) Slate400 else Slate700,
                        letterSpacing = 0.5.sp
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Total", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (isDarkMode) Slate400 else Slate700)
                        Text(totalResult, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) CoralRedSubtle else CoralRed)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Per person", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = if (isDarkMode) Slate400 else Slate700)
                        Text(perPersonResult, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) CoralRedSubtle else CoralRed)
                    }
                }
            }
        }

        // Standard Keypad for Tip (4 columns, exactly matching HTML)
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
