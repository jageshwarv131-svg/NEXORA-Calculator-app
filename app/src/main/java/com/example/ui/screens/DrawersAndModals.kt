package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.CalculationHistoryEntity
import com.example.data.CurrencyData
import com.example.data.CurrencyItem
import com.example.data.WallpaperData
import com.example.model.CalcMode
import com.example.ui.theme.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HistoryDrawerContent(
    historyList: List<CalculationHistoryEntity>,
    currentModeTitle: String,
    isDarkMode: Boolean,
    onClearHistory: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = remember { SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()) }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(if (isDarkMode) Color(0xF20F172A) else Color(0xF7FFFFFF))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$currentModeTitle History",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isDarkMode) Color.White else Slate900
                )
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = if (isDarkMode) Slate400 else Slate700)
                }
            }

            HorizontalDivider(color = if (isDarkMode) Color(0x33FFFFFF) else Color(0x1F000000))

            if (historyList.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No history yet",
                        color = if (isDarkMode) Slate400 else Slate700,
                        fontSize = 15.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(historyList) { item ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isDarkMode) Color(0x1AFFFFFF) else Color(0x0A000000))
                                .padding(12.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = dateFormat.format(Date(item.timestamp)),
                                fontSize = 11.sp,
                                color = if (isDarkMode) Slate400 else Slate700
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = item.expression,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = if (isDarkMode) Slate200 else Slate700,
                                textAlign = TextAlign.End
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "= ${item.result}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isDarkMode) CoralRedSubtle else CoralRed,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }

        // Clear History Button
        Button(
            onClick = onClearHistory,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .testTag("btn_clear_history"),
            colors = ButtonDefaults.buttonColors(
                containerColor = CoralRed.copy(alpha = 0.15f),
                contentColor = CoralRed
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Clear History", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }
    }
}

@Composable
fun MainMenuDrawerContent(
    currentMode: CalcMode,
    isDarkMode: Boolean,
    onSelectMode: (CalcMode) -> Unit,
    onOpenSettings: () -> Unit,
    onShareApp: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(if (isDarkMode) Color(0xF20F172A) else Color(0xF7FFFFFF))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("NEXORA", fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = CoralRed, letterSpacing = 1.sp)
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = if (isDarkMode) Slate400 else Slate700)
            }
        }

        HorizontalDivider(color = if (isDarkMode) Color(0x33FFFFFF) else Color(0x1F000000))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp)
        ) {
            val modes = listOf(
                CalcMode.STANDARD to Icons.Default.Calculate,
                CalcMode.SCIENTIFIC to Icons.Default.Functions,
                CalcMode.CURRENCY to Icons.Default.AttachMoney,
                CalcMode.MASS to Icons.Default.Scale,
                CalcMode.TIP to Icons.Default.Percent,
                CalcMode.DISCOUNT to Icons.Default.Discount
            )

            items(modes) { (mode, icon) ->
                val isSelected = currentMode == mode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) CoralRed.copy(alpha = 0.15f) else Color.Transparent)
                        .clickable { onSelectMode(mode) }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = mode.title,
                        tint = if (isSelected) CoralRed else (if (isDarkMode) Color.White else Slate900)
                    )
                    Text(
                        text = mode.title,
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) CoralRed else (if (isDarkMode) Color.White else Slate900)
                    )
                }
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = if (isDarkMode) Color(0x33FFFFFF) else Color(0x1F000000)
                )
            }

            // Share App
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onShareApp() }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share", tint = if (isDarkMode) Color.White else Slate900)
                    Text("Share App", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = if (isDarkMode) Color.White else Slate900)
                }
            }

            // Settings
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onOpenSettings() }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings", tint = if (isDarkMode) Color.White else Slate900)
                        Text("Settings", fontSize = 15.sp, fontWeight = FontWeight.Medium, color = if (isDarkMode) Color.White else Slate900)
                    }
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Go to settings", tint = Slate400)
                }
            }
        }
    }
}

@Composable
fun SettingsDrawerContent(
    isDarkMode: Boolean,
    onToggleDarkMode: (Boolean) -> Unit,
    onOpenWallpaperPicker: () -> Unit,
    onBackToMenu: () -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .width(320.dp)
            .background(if (isDarkMode) Color(0xF20F172A) else Color(0xF7FFFFFF))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { onBackToMenu() }
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = if (isDarkMode) Color.White else Slate900)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Settings", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = if (isDarkMode) Color.White else Slate900)
            }
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = if (isDarkMode) Slate400 else Slate700)
            }
        }

        HorizontalDivider(color = if (isDarkMode) Color(0x33FFFFFF) else Color(0x1F000000))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dark Mode
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Dark Mode", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = if (isDarkMode) Color.White else Slate900)
                    Text("Switch between light & dark", fontSize = 12.sp, color = if (isDarkMode) Slate400 else Slate700)
                }
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onToggleDarkMode,
                    colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = CoralRed)
                )
            }

            HorizontalDivider(color = if (isDarkMode) Color(0x22FFFFFF) else Color(0x11000000))

            // App Version
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("App Version", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = if (isDarkMode) Color.White else Slate900)
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CoralRed.copy(alpha = 0.15f))
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("1.0.0", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = CoralRed)
                }
            }

            HorizontalDivider(color = if (isDarkMode) Color(0x22FFFFFF) else Color(0x11000000))

            // Wallpaper Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onOpenWallpaperPicker() }
                    .padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Wallpaper", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = if (isDarkMode) Color.White else Slate900)
                    Text("20 glass themes available", fontSize = 12.sp, color = if (isDarkMode) Slate400 else Slate700)
                }
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Choose wallpaper", tint = Slate400)
            }
        }
    }
}

@Composable
fun WallpaperPickerModal(
    selectedWallpaperIndex: Int,
    onWallpaperSelected: (Int) -> Unit,
    onClose: () -> Unit
) {
    var pendingIndex by remember { mutableIntStateOf(selectedWallpaperIndex) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xE60F172A))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate700)
                }
                Text("Wallpaper Themes", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Slate900)
                Spacer(modifier = Modifier.size(48.dp))
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                itemsIndexed(WallpaperData.items) { index, wallpaper ->
                    val isSelected = pendingIndex == index
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .then(
                                if (isSelected) {
                                    Modifier.border(3.dp, CyanAccent, RoundedCornerShape(12.dp))
                                } else Modifier
                            )
                            .clickable { pendingIndex = index }
                    ) {
                        // Fallback gradient
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Brush.linearGradient(wallpaper.gradientColors))
                        )
                        // Online image load with Coil
                        AsyncImage(
                            model = wallpaper.url,
                            contentDescription = wallpaper.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            // Set button
            Button(
                onClick = {
                    onWallpaperSelected(pendingIndex)
                    onClose()
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .shadow(6.dp, RoundedCornerShape(30.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CyanAccent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(30.dp),
                contentPadding = PaddingValues(horizontal = 36.dp, vertical = 12.dp)
            ) {
                Text("SET", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun CurrencyPickerModal(
    currencies: List<CurrencyItem>,
    onSelectCurrency: (CurrencyItem) -> Unit,
    onClose: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredList = remember(searchQuery) {
        val q = searchQuery.trim().lowercase()
        if (q.isEmpty()) currencies
        else currencies.filter { it.code.lowercase().contains(q) || it.name.lowercase().contains(q) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x99000000)),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                .background(Color(0xFFF8FAFC))
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Select Currency", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Slate900)
                IconButton(onClick = onClose) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Slate700)
                }
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search currency...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                shape = RoundedCornerShape(14.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Slate400)
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(filteredList) { currency ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSelectCurrency(currency)
                                onClose()
                            }
                            .padding(vertical = 12.dp, horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(currency.flag, fontSize = 28.sp)
                        Column {
                            Text(currency.code, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Slate900)
                            Text(currency.name, fontSize = 13.sp, color = Slate700)
                        }
                    }
                    HorizontalDivider(color = Color(0x1F000000))
                }
            }
        }
    }
}
