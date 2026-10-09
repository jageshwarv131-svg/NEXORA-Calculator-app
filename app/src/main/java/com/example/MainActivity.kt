package com.example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.data.CurrencyData
import com.example.data.WallpaperData
import com.example.model.CalcMode
import com.example.ui.components.GlassCalcButton
import com.example.ui.screens.*
import com.example.ui.theme.CoralRed
import com.example.ui.theme.NexoraCalculatorTheme
import com.example.ui.theme.Slate400
import com.example.ui.theme.Slate700
import com.example.ui.theme.Slate900

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

            NexoraCalculatorTheme(darkTheme = isDarkMode) {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val currentMode by viewModel.currentMode.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val wallpaperIdx by viewModel.selectedWallpaperIndex.collectAsStateWithLifecycle()

    val isMenuOpen by viewModel.isMenuDrawerOpen.collectAsStateWithLifecycle()
    val isHistoryOpen by viewModel.isHistoryDrawerOpen.collectAsStateWithLifecycle()
    val isSettingsOpen by viewModel.isSettingsViewOpen.collectAsStateWithLifecycle()
    val isWallpaperModalOpen by viewModel.isWallpaperModalOpen.collectAsStateWithLifecycle()
    val isCurrencyPickerOpen by viewModel.isCurrencyPickerModalOpen.collectAsStateWithLifecycle()

    val stdHistory by viewModel.standardHistory.collectAsStateWithLifecycle()
    val sciHistory by viewModel.scientificHistory.collectAsStateWithLifecycle()

    // Handle Back Press
    BackHandler(
        enabled = isMenuOpen || isHistoryOpen || isSettingsOpen || isWallpaperModalOpen || isCurrencyPickerOpen || currentMode != CalcMode.STANDARD
    ) {
        when {
            isWallpaperModalOpen -> viewModel.isWallpaperModalOpen.value = false
            isCurrencyPickerOpen -> viewModel.isCurrencyPickerModalOpen.value = false
            isSettingsOpen -> viewModel.isSettingsViewOpen.value = false
            isHistoryOpen -> viewModel.isHistoryDrawerOpen.value = false
            isMenuOpen -> viewModel.isMenuDrawerOpen.value = false
            currentMode != CalcMode.STANDARD -> viewModel.setMode(CalcMode.STANDARD)
        }
    }

    val currentWallpaper = WallpaperData.items.getOrElse(wallpaperIdx) { WallpaperData.items[0] }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Dynamic Wallpaper Background (Fallback Gradient + Online Coil Image)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(currentWallpaper.gradientColors))
        )
        AsyncImage(
            model = currentWallpaper.url,
            contentDescription = "Wallpaper",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Frosted Glass App Layer
        val overlayColor = if (isDarkMode) Color(0x330F172A) else Color(0x1AFFFFFF)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(overlayColor)
        )

        // Main App Content Container (Constrained on Tablets / Desktops for optimal ergonomics)
        Scaffold(
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets.safeDrawing,
            modifier = Modifier.fillMaxSize()
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 480.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Top Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Menu Button
                        IconButton(
                            onClick = {
                                viewModel.isSettingsViewOpen.value = false
                                viewModel.isMenuDrawerOpen.value = true
                            },
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(if (isDarkMode) Color(0x33FFFFFF) else Color(0x66FFFFFF))
                                .testTag("btn_menu")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Menu",
                                tint = if (isDarkMode) Color.White else Slate900
                            )
                        }

                        // App Title / Mode Title
                        Text(
                            text = if (currentMode == CalcMode.STANDARD) "NEXORA" else currentMode.title.uppercase(),
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isDarkMode) Color.White else Slate900,
                            letterSpacing = 1.sp
                        )

                        // History Button (Only for Standard & Scientific)
                        if (currentMode == CalcMode.STANDARD || currentMode == CalcMode.SCIENTIFIC) {
                            IconButton(
                                onClick = { viewModel.isHistoryDrawerOpen.value = true },
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(if (isDarkMode) Color(0x33FFFFFF) else Color(0x66FFFFFF))
                                    .testTag("btn_history")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.History,
                                    contentDescription = "View History",
                                    tint = if (isDarkMode) Color.White else Slate900
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(44.dp))
                        }
                    }

                    // Active Calculator Mode Screen
                    Box(modifier = Modifier.fillMaxSize()) {
                        when (currentMode) {
                            CalcMode.STANDARD -> {
                                val displayVal by viewModel.stdDisplayValue.collectAsStateWithLifecycle()
                                val expression by viewModel.stdExpression.collectAsStateWithLifecycle()

                                StandardCalcScreen(
                                    displayValue = displayVal,
                                    expression = expression,
                                    isDarkMode = isDarkMode,
                                    onNumberClick = viewModel::onStdNumber,
                                    onOperatorClick = viewModel::onStdOperator,
                                    onDotClick = viewModel::onStdDot,
                                    onEqualsClick = viewModel::onStdEquals,
                                    onClearAll = viewModel::onStdClearAll,
                                    onDeleteLast = viewModel::onStdDeleteLast,
                                    onToggleSign = viewModel::onStdToggleSign,
                                    onPercentageClick = viewModel::onStdPercent,
                                    onMemoryClear = viewModel::onStdMemoryClear,
                                    onMemoryAdd = viewModel::onStdMemoryAdd,
                                    onMemorySubtract = viewModel::onStdMemorySubtract,
                                    onMemoryRecall = viewModel::onStdMemoryRecall
                                )
                            }
                            CalcMode.SCIENTIFIC -> {
                                val displayVal by viewModel.sciDisplayValue.collectAsStateWithLifecycle()
                                val expression by viewModel.sciExpression.collectAsStateWithLifecycle()
                                val isRad by viewModel.isRadMode.collectAsStateWithLifecycle()

                                ScientificCalcScreen(
                                    displayValue = displayVal,
                                    expression = expression,
                                    isRadMode = isRad,
                                    isDarkMode = isDarkMode,
                                    onNumberClick = viewModel::onSciNumber,
                                    onOperatorClick = viewModel::onSciOperator,
                                    onFunctionClick = viewModel::onSciFunction,
                                    onConstantClick = viewModel::onSciConstant,
                                    onParenthesisClick = viewModel::onSciParenthesis,
                                    onDotClick = viewModel::onSciDot,
                                    onEqualsClick = viewModel::onSciEquals,
                                    onClearAll = viewModel::onSciClearAll,
                                    onDeleteLast = viewModel::onSciDeleteLast,
                                    onToggleRadDeg = viewModel::onSciToggleRadDeg
                                )
                            }
                            CalcMode.CURRENCY -> {
                                val c1 by viewModel.curr1.collectAsStateWithLifecycle()
                                val c2 by viewModel.curr2.collectAsStateWithLifecycle()
                                val v1 by viewModel.currVal1.collectAsStateWithLifecycle()
                                val v2 by viewModel.currVal2.collectAsStateWithLifecycle()
                                val box by viewModel.currActiveBox.collectAsStateWithLifecycle()
                                val pairText by viewModel.ratePairText.collectAsStateWithLifecycle()
                                val updateTime by viewModel.rateUpdateTime.collectAsStateWithLifecycle()

                                CurrencyScreen(
                                    curr1 = c1,
                                    curr2 = c2,
                                    val1 = v1,
                                    val2 = v2,
                                    activeBox = box,
                                    ratePairText = pairText,
                                    rateUpdateTime = updateTime,
                                    isDarkMode = isDarkMode,
                                    onBoxSelected = viewModel::selectCurrBox,
                                    onOpenPicker = { target ->
                                        viewModel.currencyPickerTargetBox.value = target
                                        viewModel.isCurrencyPickerModalOpen.value = true
                                    },
                                    onSwapCurrencies = viewModel::swapCurrencies,
                                    onNumberClick = viewModel::onCurrNumber,
                                    onDotClick = viewModel::onCurrDot,
                                    onDeleteLast = viewModel::onCurrDeleteLast,
                                    onClearAll = viewModel::onCurrClearAll
                                )
                            }
                            CalcMode.MASS -> {
                                val massVals by viewModel.massValues.collectAsStateWithLifecycle()
                                val activeUnit by viewModel.massActiveUnit.collectAsStateWithLifecycle()

                                MassScreen(
                                    massValues = massVals,
                                    activeUnit = activeUnit,
                                    isDarkMode = isDarkMode,
                                    onUnitSelected = viewModel::selectMassUnit,
                                    onNumberClick = viewModel::onMassNumber,
                                    onDotClick = viewModel::onMassDot,
                                    onDeleteLast = viewModel::onMassDeleteLast,
                                    onClearAll = viewModel::onMassClearAll
                                )
                            }
                            CalcMode.TIP -> {
                                val bill by viewModel.tipBill.collectAsStateWithLifecycle()
                                val people by viewModel.tipPeople.collectAsStateWithLifecycle()
                                val tip by viewModel.tipTip.collectAsStateWithLifecycle()
                                val tax by viewModel.tipTax.collectAsStateWithLifecycle()
                                val tipPct by viewModel.tipIsPercent.collectAsStateWithLifecycle()
                                val taxPct by viewModel.taxIsPercent.collectAsStateWithLifecycle()
                                val noTaxTip by viewModel.noTipOnTax.collectAsStateWithLifecycle()
                                val activeField by viewModel.tipActiveField.collectAsStateWithLifecycle()
                                val total by viewModel.tipTotalResult.collectAsStateWithLifecycle()
                                val perPerson by viewModel.tipPerPersonResult.collectAsStateWithLifecycle()

                                TipScreen(
                                    bill = bill,
                                    people = people,
                                    tip = tip,
                                    tax = tax,
                                    tipIsPercent = tipPct,
                                    taxIsPercent = taxPct,
                                    noTipOnTax = noTaxTip,
                                    totalResult = total,
                                    perPersonResult = perPerson,
                                    activeField = activeField,
                                    isDarkMode = isDarkMode,
                                    onFieldSelected = viewModel::selectTipField,
                                    onTipTypeChanged = viewModel::setTipType,
                                    onTaxTypeChanged = viewModel::setTaxType,
                                    onNoTipOnTaxChanged = viewModel::setNoTipOnTax,
                                    onNumberClick = viewModel::onTipNumber,
                                    onDotClick = viewModel::onTipDot,
                                    onDeleteLast = viewModel::onTipDeleteLast,
                                    onClearAll = viewModel::onTipClearAll
                                )
                            }
                            CalcMode.DISCOUNT -> {
                                val price by viewModel.discPrice.collectAsStateWithLifecycle()
                                val disc by viewModel.discDiscount.collectAsStateWithLifecycle()
                                val tax by viewModel.discTax.collectAsStateWithLifecycle()
                                val activeField by viewModel.discActiveField.collectAsStateWithLifecycle()
                                val saved by viewModel.discSavedResult.collectAsStateWithLifecycle()
                                val total by viewModel.discTotalResult.collectAsStateWithLifecycle()

                                DiscountScreen(
                                    price = price,
                                    discount = disc,
                                    tax = tax,
                                    savedResult = saved,
                                    totalResult = total,
                                    activeField = activeField,
                                    isDarkMode = isDarkMode,
                                    onFieldSelected = viewModel::selectDiscField,
                                    onNumberClick = viewModel::onDiscNumber,
                                    onDotClick = viewModel::onDiscDot,
                                    onDeleteLast = viewModel::onDiscDeleteLast,
                                    onClearAll = viewModel::onDiscClearAll
                                )
                            }
                        }
                    }
                }
            }
        }

        // Overlays & Drawers

        // Dark dim backdrop for drawers
        if (isMenuOpen || isHistoryOpen) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x66000000))
                    .testTag("overlay_backdrop")
            )
        }

        // Menu Drawer (Slides from Left)
        AnimatedVisibility(
            visible = isMenuOpen,
            enter = slideInHorizontally(initialOffsetX = { -it }),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            if (isSettingsOpen) {
                SettingsDrawerContent(
                    isDarkMode = isDarkMode,
                    onToggleDarkMode = viewModel::toggleDarkMode,
                    onOpenWallpaperPicker = { viewModel.isWallpaperModalOpen.value = true },
                    onBackToMenu = { viewModel.isSettingsViewOpen.value = false },
                    onClose = { viewModel.isMenuDrawerOpen.value = false }
                )
            } else {
                MainMenuDrawerContent(
                    currentMode = currentMode,
                    isDarkMode = isDarkMode,
                    onSelectMode = viewModel::setMode,
                    onOpenSettings = { viewModel.isSettingsViewOpen.value = true },
                    onShareApp = {
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(
                                Intent.EXTRA_TEXT,
                                "Check out NEXORA Calculator - a beautiful glassmorphism calculator with standard, scientific, currency, mass, tip & discount tools!"
                            )
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(sendIntent, "Share NEXORA Calculator"))
                        viewModel.isMenuDrawerOpen.value = false
                    },
                    onClose = { viewModel.isMenuDrawerOpen.value = false }
                )
            }
        }

        // History Drawer (Slides from Right)
        AnimatedVisibility(
            visible = isHistoryOpen,
            enter = slideInHorizontally(initialOffsetX = { it }),
            exit = slideOutHorizontally(targetOffsetX = { it }),
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            HistoryDrawerContent(
                historyList = if (currentMode == CalcMode.STANDARD) stdHistory else sciHistory,
                currentModeTitle = if (currentMode == CalcMode.STANDARD) "Standard" else "Scientific",
                isDarkMode = isDarkMode,
                onClearHistory = viewModel::clearCurrentHistory,
                onClose = { viewModel.isHistoryDrawerOpen.value = false }
            )
        }

        // Wallpaper Picker Modal
        if (isWallpaperModalOpen) {
            WallpaperPickerModal(
                selectedWallpaperIndex = wallpaperIdx,
                onWallpaperSelected = viewModel::setWallpaperIndex,
                onClose = { viewModel.isWallpaperModalOpen.value = false }
            )
        }

        // Currency Picker Modal
        if (isCurrencyPickerOpen) {
            CurrencyPickerModal(
                currencies = CurrencyData.currencies,
                onSelectCurrency = { selected ->
                    viewModel.setCurrency(selected.code)
                    viewModel.isCurrencyPickerModalOpen.value = false
                },
                onClose = { viewModel.isCurrencyPickerModalOpen.value = false }
            )
        }
    }
}
