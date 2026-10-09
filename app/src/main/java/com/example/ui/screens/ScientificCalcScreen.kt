package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

@Composable
fun ScientificCalcScreen(
    displayValue: String,
    expression: String,
    isRadMode: Boolean,
    isDarkMode: Boolean,
    onNumberClick: (String) -> Unit,
    onOperatorClick: (String) -> Unit,
    onFunctionClick: (String) -> Unit,
    onConstantClick: (String) -> Unit,
    onParenthesisClick: () -> Unit,
    onDotClick: () -> Unit,
    onEqualsClick: () -> Unit,
    onClearAll: () -> Unit,
    onDeleteLast: () -> Unit,
    onToggleRadDeg: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Display Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = expression,
                color = if (isDarkMode) Slate400 else Slate700,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))

            val fontSize = when {
                displayValue.length > 13 -> 28.sp
                displayValue.length > 9 -> 36.sp
                else -> 46.sp
            }

            Text(
                text = displayValue,
                color = if (isDarkMode) Color.White else Slate900,
                fontSize = fontSize,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Scientific Keypad (5 columns, 7 rows matching HTML .sci-keypad)
        val gap = 6.dp
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(gap)
        ) {
            // Row 1: ⇄, Rad/Deg, sin, cos, tan
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "⇄", onClick = {}, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 14.sp)
                GlassCalcButton(
                    text = if (isRadMode) "Rad" else "Deg",
                    onClick = onToggleRadDeg,
                    modifier = Modifier.weight(1f).aspectRatio(1f),
                    style = GlassButtonStyle.BADGE,
                    isDarkMode = isDarkMode,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                GlassCalcButton(text = "sin", onClick = { onFunctionClick("sin(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 13.sp)
                GlassCalcButton(text = "cos", onClick = { onFunctionClick("cos(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 13.sp)
                GlassCalcButton(text = "tan", onClick = { onFunctionClick("tan(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 13.sp)
            }

            // Row 2: e, π, sinh, cosh, tanh
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "e", onClick = { onConstantClick("e") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 15.sp)
                GlassCalcButton(text = "π", onClick = { onConstantClick("π") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 15.sp)
                GlassCalcButton(text = "sinh", onClick = { onFunctionClick("sinh(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 12.sp)
                GlassCalcButton(text = "cosh", onClick = { onFunctionClick("cosh(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 12.sp)
                GlassCalcButton(text = "tanh", onClick = { onFunctionClick("tanh(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 12.sp)
            }

            // Row 3: %, AC, ÷, ×, ⌫
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "%", onClick = { onOperatorClick("%") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 15.sp)
                GlassCalcButton(text = "AC", onClick = onClearAll, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                GlassCalcButton(text = "÷", onClick = { onOperatorClick("÷") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 22.sp)
                GlassCalcButton(text = "×", onClick = { onOperatorClick("×") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 22.sp)
                GlassCalcButton(text = "⌫", onClick = onDeleteLast, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.ACCENT_TEXT, isDarkMode = isDarkMode, fontSize = 16.sp)
            }

            // Row 4: ( ), 7, 8, 9, -
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "( )", onClick = onParenthesisClick, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 14.sp)
                GlassCalcButton(text = "7", onClick = { onNumberClick("7") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                GlassCalcButton(text = "8", onClick = { onNumberClick("8") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                GlassCalcButton(text = "9", onClick = { onNumberClick("9") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                GlassCalcButton(text = "-", onClick = { onOperatorClick("-") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 22.sp)
            }

            // Row 5: 1/x, 4, 5, 6, +
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(gap)) {
                GlassCalcButton(text = "1/x", onClick = { onFunctionClick("1/(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 13.sp)
                GlassCalcButton(text = "4", onClick = { onNumberClick("4") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                GlassCalcButton(text = "5", onClick = { onNumberClick("5") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                GlassCalcButton(text = "6", onClick = { onNumberClick("6") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                GlassCalcButton(text = "+", onClick = { onOperatorClick("+") }, modifier = Modifier.weight(1f).aspectRatio(1f), style = GlassButtonStyle.OPERATOR, isDarkMode = isDarkMode, fontSize = 22.sp)
            }

            // Rows 6 & 7: Columns 1-4 with Row 6 (√x, 1, 2, 3) and Row 7 (lg, 00, 0, .), and Column 5 is = spanning both rows!
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.spacedBy(gap)
            ) {
                Column(
                    modifier = Modifier.weight(4f),
                    verticalArrangement = Arrangement.spacedBy(gap)
                ) {
                    // Row 6
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gap)
                    ) {
                        GlassCalcButton(text = "√x", onClick = { onFunctionClick("√(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 14.sp)
                        GlassCalcButton(text = "1", onClick = { onNumberClick("1") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                        GlassCalcButton(text = "2", onClick = { onNumberClick("2") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                        GlassCalcButton(text = "3", onClick = { onNumberClick("3") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                    }
                    // Row 7
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(gap)
                    ) {
                        GlassCalcButton(text = "lg", onClick = { onFunctionClick("lg(") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 14.sp)
                        GlassCalcButton(text = "00", onClick = { onNumberClick("00") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                        GlassCalcButton(text = "0", onClick = { onNumberClick("0") }, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 19.sp, fontWeight = FontWeight.SemiBold)
                        GlassCalcButton(text = ".", onClick = onDotClick, modifier = Modifier.weight(1f).aspectRatio(1f), isDarkMode = isDarkMode, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    }
                }

                // Column 5: Equals button spanning both rows
                GlassCalcButton(
                    text = "=",
                    onClick = onEqualsClick,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    style = GlassButtonStyle.EQUALS,
                    shape = RoundedCornerShape(26.dp),
                    isDarkMode = isDarkMode,
                    fontSize = 24.sp
                )
            }
        }
    }
}
