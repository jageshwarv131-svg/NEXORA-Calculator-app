package com.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.CalculationHistoryEntity
import com.example.data.CurrencyData
import com.example.data.PreferencesManager
import com.example.engine.MathEvaluator
import com.example.model.CalcMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.roundToInt

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val historyDao = db.calculationHistoryDao()
    private val prefs = PreferencesManager(application)

    // Current Mode
    private val _currentMode = MutableStateFlow(CalcMode.STANDARD)
    val currentMode: StateFlow<CalcMode> = _currentMode.asStateFlow()

    // Preferences & Theme
    private val _isDarkMode = MutableStateFlow(prefs.isDarkMode)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _selectedWallpaperIndex = MutableStateFlow(prefs.wallpaperIndex)
    val selectedWallpaperIndex: StateFlow<Int> = _selectedWallpaperIndex.asStateFlow()

    // History (Room database flows)
    val standardHistory = historyDao.getHistoryByMode("standard")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val scientificHistory = historyDao.getHistoryByMode("scientific")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Standard Calculator State
    val stdDisplayValue = MutableStateFlow("0")
    val stdExpression = MutableStateFlow("")
    private var stdShouldReset = false
    private var stdMemory = prefs.standardMemory

    // Scientific Calculator State
    val sciDisplayValue = MutableStateFlow("0")
    val sciExpression = MutableStateFlow("")
    val isRadMode = MutableStateFlow(prefs.isRadMode)
    private var sciShouldReset = false

    // Currency Converter State
    val curr1 = MutableStateFlow("USD")
    val curr2 = MutableStateFlow("EUR")
    val currVal1 = MutableStateFlow("100")
    val currVal2 = MutableStateFlow("0")
    val currActiveBox = MutableStateFlow(1) // 1 or 2
    val ratePairText = MutableStateFlow("1 USD ≈ 0.92 EUR")
    val rateUpdateTime = MutableStateFlow("Loading...")
    private var exchangeRates = CurrencyData.defaultFallbackRates

    // Mass Converter State
    val massActiveUnit = MutableStateFlow("kg")
    val massValues = MutableStateFlow(
        mapOf("kg" to "1", "lb" to "2.2046", "g" to "1000", "oz" to "35.274")
    )
    private val massFactors = mapOf("kg" to 1.0, "lb" to 2.20462262, "g" to 1000.0, "oz" to 35.27396195)

    // Tip Calculator State
    val tipBill = MutableStateFlow("150")
    val tipPeople = MutableStateFlow("3")
    val tipTip = MutableStateFlow("20.00")
    val tipTax = MutableStateFlow("4.00")
    val tipIsPercent = MutableStateFlow(true)
    val taxIsPercent = MutableStateFlow(true)
    val noTipOnTax = MutableStateFlow(prefs.tipNoTipOnTax)
    val tipActiveField = MutableStateFlow("bill") // "bill", "people", "tip", "tax"
    val tipTotalResult = MutableStateFlow("187.20")
    val tipPerPersonResult = MutableStateFlow("31.20")

    // Discount Calculator State
    val discPrice = MutableStateFlow("159.99")
    val discDiscount = MutableStateFlow("20")
    val discTax = MutableStateFlow("10")
    val discActiveField = MutableStateFlow("price") // "price", "disc", "tax"
    val discSavedResult = MutableStateFlow("32.00")
    val discTotalResult = MutableStateFlow("140.79")

    // Modals / Drawers
    val isMenuDrawerOpen = MutableStateFlow(false)
    val isHistoryDrawerOpen = MutableStateFlow(false)
    val isSettingsViewOpen = MutableStateFlow(false)
    val isWallpaperModalOpen = MutableStateFlow(false)
    val isCurrencyPickerModalOpen = MutableStateFlow(false)
    val currencyPickerTargetBox = MutableStateFlow(1)

    init {
        fetchCurrencyRates()
        calculateCurrency()
        calculateMass()
        calculateTip()
        calculateDiscount()
    }

    // Navigation / Mode Switch
    fun setMode(mode: CalcMode) {
        _currentMode.value = mode
        isMenuDrawerOpen.value = false
        if (mode == CalcMode.CURRENCY) calculateCurrency()
        if (mode == CalcMode.MASS) calculateMass()
        if (mode == CalcMode.TIP) calculateTip()
        if (mode == CalcMode.DISCOUNT) calculateDiscount()
    }

    // Settings
    fun toggleDarkMode(enable: Boolean) {
        _isDarkMode.value = enable
        prefs.isDarkMode = enable
    }

    fun setWallpaperIndex(index: Int) {
        _selectedWallpaperIndex.value = index
        prefs.wallpaperIndex = index
    }

    // History
    fun clearCurrentHistory() {
        viewModelScope.launch {
            val modeStr = if (_currentMode.value == CalcMode.STANDARD) "standard" else "scientific"
            historyDao.clearHistoryByMode(modeStr)
        }
    }

    // Standard Calculator Logic
    fun onStdNumber(num: String) {
        if (stdDisplayValue.value == "0" || stdShouldReset) {
            stdDisplayValue.value = num
            stdShouldReset = false
        } else if (stdDisplayValue.value.length < 14) {
            stdDisplayValue.value += num
        }
    }

    fun onStdDot() {
        if (stdShouldReset) {
            stdDisplayValue.value = "0"
            stdShouldReset = false
        }
        if (!stdDisplayValue.value.contains(".")) {
            stdDisplayValue.value += "."
        }
    }

    fun onStdOperator(op: String) {
        stdShouldReset = false
        stdExpression.value += "${stdDisplayValue.value} $op "
        stdDisplayValue.value = "0"
    }

    fun onStdClearAll() {
        stdDisplayValue.value = "0"
        stdExpression.value = ""
        stdShouldReset = false
    }

    fun onStdDeleteLast() {
        stdDisplayValue.value = if (stdDisplayValue.value.length > 1) {
            stdDisplayValue.value.dropLast(1)
        } else "0"
    }

    fun onStdToggleSign() {
        if (stdDisplayValue.value != "0") {
            stdDisplayValue.value = if (stdDisplayValue.value.startsWith("-")) {
                stdDisplayValue.value.removePrefix("-")
            } else {
                "-${stdDisplayValue.value}"
            }
        }
    }

    fun onStdPercent() {
        val d = stdDisplayValue.value.toDoubleOrNull()
        if (d != null) {
            stdDisplayValue.value = (d / 100.0).toString()
        }
    }

    fun onStdEquals() {
        if (stdExpression.value.isEmpty()) return
        val fullExpr = "${stdExpression.value}${stdDisplayValue.value}"
        val result = MathEvaluator.evaluate(fullExpr)
        if (result != "Error") {
            viewModelScope.launch {
                historyDao.insert(
                    CalculationHistoryEntity(
                        mode = "standard",
                        expression = fullExpr,
                        result = result
                    )
                )
            }
        }
        stdDisplayValue.value = result
        stdExpression.value = ""
        stdShouldReset = true
    }

    fun onStdMemoryClear() {
        stdMemory = 0.0
        prefs.standardMemory = 0.0
    }

    fun onStdMemoryAdd() {
        stdMemory += (stdDisplayValue.value.toDoubleOrNull() ?: 0.0)
        prefs.standardMemory = stdMemory
        stdShouldReset = true
    }

    fun onStdMemorySubtract() {
        stdMemory -= (stdDisplayValue.value.toDoubleOrNull() ?: 0.0)
        prefs.standardMemory = stdMemory
        stdShouldReset = true
    }

    fun onStdMemoryRecall() {
        stdDisplayValue.value = stdMemory.toString().removeSuffix(".0")
        stdShouldReset = true
    }

    // Scientific Calculator Logic
    fun onSciNumber(num: String) {
        if (sciDisplayValue.value == "0" || sciShouldReset) {
            sciDisplayValue.value = num
            sciShouldReset = false
        } else {
            sciDisplayValue.value += num
        }
    }

    fun onSciDot() {
        if (sciShouldReset) {
            sciDisplayValue.value = "0"
            sciShouldReset = false
        }
        if (!sciDisplayValue.value.contains(".")) {
            sciDisplayValue.value += "."
        }
    }

    fun onSciOperator(op: String) {
        sciShouldReset = false
        sciDisplayValue.value = if (sciDisplayValue.value == "0" && op != "-") {
            op
        } else {
            "${sciDisplayValue.value} $op "
        }
    }

    fun onSciFunction(func: String) {
        sciDisplayValue.value = if (sciDisplayValue.value == "0" || sciShouldReset) {
            func
        } else {
            "${sciDisplayValue.value}$func"
        }
        sciShouldReset = false
    }

    fun onSciConstant(constStr: String) {
        sciDisplayValue.value = if (sciDisplayValue.value == "0" || sciShouldReset) {
            constStr
        } else {
            "${sciDisplayValue.value}$constStr"
        }
        sciShouldReset = false
    }

    fun onSciParenthesis() {
        val curr = sciDisplayValue.value
        sciDisplayValue.value = if (curr == "0" || sciShouldReset) "(" else "$curr("
        sciShouldReset = false
    }

    fun onSciClearAll() {
        sciDisplayValue.value = "0"
        sciExpression.value = ""
        sciShouldReset = false
    }

    fun onSciDeleteLast() {
        sciDisplayValue.value = if (sciDisplayValue.value.length > 1) {
            sciDisplayValue.value.trimEnd().dropLast(1).trimEnd().ifEmpty { "0" }
        } else "0"
    }

    fun onSciToggleRadDeg() {
        val updated = !isRadMode.value
        isRadMode.value = updated
        prefs.isRadMode = updated
    }

    fun onSciEquals() {
        val expr = sciDisplayValue.value
        val result = MathEvaluator.evaluate(expr, isRad = isRadMode.value)
        if (result != "Error") {
            viewModelScope.launch {
                historyDao.insert(
                    CalculationHistoryEntity(
                        mode = "scientific",
                        expression = expr,
                        result = result
                    )
                )
            }
            sciExpression.value = "$expr ="
        }
        sciDisplayValue.value = result
        sciShouldReset = true
    }

    // Currency Logic
    private fun fetchCurrencyRates() {
        viewModelScope.launch {
            val (rates, timestamp) = CurrencyData.fetchRates()
            exchangeRates = rates
            rateUpdateTime.value = timestamp
            calculateCurrency()
        }
    }

    fun selectCurrBox(box: Int) {
        currActiveBox.value = box
    }

    fun swapCurrencies() {
        val tempCode = curr1.value
        curr1.value = curr2.value
        curr2.value = tempCode

        val tempVal = currVal1.value
        currVal1.value = currVal2.value
        currVal2.value = tempVal

        calculateCurrency()
    }

    fun setCurrency(code: String) {
        if (currencyPickerTargetBox.value == 1) {
            curr1.value = code
        } else {
            curr2.value = code
        }
        calculateCurrency()
    }

    fun onCurrNumber(num: String) {
        if (currActiveBox.value == 1) {
            currVal1.value = if (currVal1.value == "0") num else currVal1.value + num
        } else {
            currVal2.value = if (currVal2.value == "0") num else currVal2.value + num
        }
        calculateCurrency()
    }

    fun onCurrDot() {
        if (currActiveBox.value == 1 && !currVal1.value.contains(".")) {
            currVal1.value += "."
        } else if (currActiveBox.value == 2 && !currVal2.value.contains(".")) {
            currVal2.value += "."
        }
        calculateCurrency()
    }

    fun onCurrDeleteLast() {
        if (currActiveBox.value == 1) {
            currVal1.value = if (currVal1.value.length > 1) currVal1.value.dropLast(1) else "0"
        } else {
            currVal2.value = if (currVal2.value.length > 1) currVal2.value.dropLast(1) else "0"
        }
        calculateCurrency()
    }

    fun onCurrClearAll() {
        if (currActiveBox.value == 1) currVal1.value = "0"
        else currVal2.value = "0"
        calculateCurrency()
    }

    private fun calculateCurrency() {
        val r1 = exchangeRates[curr1.value] ?: 1.0
        val r2 = exchangeRates[curr2.value] ?: 1.0
        val factor = r2 / r1

        if (currActiveBox.value == 1) {
            val v1 = currVal1.value.toDoubleOrNull() ?: 0.0
            val converted = (v1 * factor * 100.0).roundToInt() / 100.0
            currVal2.value = converted.toString().removeSuffix(".0")
        } else {
            val v2 = currVal2.value.toDoubleOrNull() ?: 0.0
            val converted = (v2 / factor * 100.0).roundToInt() / 100.0
            currVal1.value = converted.toString().removeSuffix(".0")
        }

        val pairFactor = (factor * 100.0).roundToInt() / 100.0
        ratePairText.value = "1 ${curr1.value} ≈ $pairFactor ${curr2.value}"
    }

    // Mass Logic
    fun selectMassUnit(unit: String) {
        massActiveUnit.value = unit
    }

    fun onMassNumber(num: String) {
        val current = massValues.value[massActiveUnit.value] ?: "0"
        val updated = if (current == "0") num else current + num
        massValues.value = massValues.value.toMutableMap().apply { put(massActiveUnit.value, updated) }
        calculateMass()
    }

    fun onMassDot() {
        val current = massValues.value[massActiveUnit.value] ?: "0"
        if (!current.contains(".")) {
            massValues.value = massValues.value.toMutableMap().apply { put(massActiveUnit.value, "$current.") }
        }
    }

    fun onMassDeleteLast() {
        val current = massValues.value[massActiveUnit.value] ?: "0"
        val updated = if (current.length > 1) current.dropLast(1) else "0"
        massValues.value = massValues.value.toMutableMap().apply { put(massActiveUnit.value, updated) }
        calculateMass()
    }

    fun onMassClearAll() {
        massValues.value = massValues.value.toMutableMap().apply { put(massActiveUnit.value, "0") }
        calculateMass()
    }

    private fun calculateMass() {
        val active = massActiveUnit.value
        val raw = massValues.value[active]?.toDoubleOrNull() ?: 0.0
        val baseKg = raw / (massFactors[active] ?: 1.0)

        val updatedMap = massValues.value.toMutableMap()
        massFactors.forEach { (unit, factor) ->
            if (unit != active) {
                val converted = (baseKg * factor * 10000.0).roundToInt() / 10000.0
                updatedMap[unit] = converted.toString().removeSuffix(".0")
            }
        }
        massValues.value = updatedMap
    }

    // Tip Logic
    fun selectTipField(field: String) {
        tipActiveField.value = field
    }

    fun setTipType(isPercent: Boolean) {
        tipIsPercent.value = isPercent
        calculateTip()
    }

    fun setTaxType(isPercent: Boolean) {
        taxIsPercent.value = isPercent
        calculateTip()
    }

    fun setNoTipOnTax(enable: Boolean) {
        noTipOnTax.value = enable
        prefs.tipNoTipOnTax = enable
        calculateTip()
    }

    fun onTipNumber(num: String) {
        when (tipActiveField.value) {
            "bill" -> tipBill.value = if (tipBill.value == "0") num else tipBill.value + num
            "people" -> tipPeople.value = if (tipPeople.value == "0") num else tipPeople.value + num
            "tip" -> tipTip.value = if (tipTip.value == "0") num else tipTip.value + num
            "tax" -> tipTax.value = if (tipTax.value == "0") num else tipTax.value + num
        }
        calculateTip()
    }

    fun onTipDot() {
        when (tipActiveField.value) {
            "bill" -> if (!tipBill.value.contains(".")) tipBill.value += "."
            "people" -> {} // Integer count
            "tip" -> if (!tipTip.value.contains(".")) tipTip.value += "."
            "tax" -> if (!tipTax.value.contains(".")) tipTax.value += "."
        }
        calculateTip()
    }

    fun onTipDeleteLast() {
        when (tipActiveField.value) {
            "bill" -> tipBill.value = if (tipBill.value.length > 1) tipBill.value.dropLast(1) else "0"
            "people" -> tipPeople.value = if (tipPeople.value.length > 1) tipPeople.value.dropLast(1) else "1"
            "tip" -> tipTip.value = if (tipTip.value.length > 1) tipTip.value.dropLast(1) else "0"
            "tax" -> tipTax.value = if (tipTax.value.length > 1) tipTax.value.dropLast(1) else "0"
        }
        calculateTip()
    }

    fun onTipClearAll() {
        when (tipActiveField.value) {
            "bill" -> tipBill.value = "0"
            "people" -> tipPeople.value = "1"
            "tip" -> tipTip.value = "0"
            "tax" -> tipTax.value = "0"
        }
        calculateTip()
    }

    private fun calculateTip() {
        val bill = tipBill.value.toDoubleOrNull() ?: 0.0
        var people = tipPeople.value.toDoubleOrNull() ?: 1.0
        if (people <= 0.0) people = 1.0

        val rawTip = tipTip.value.toDoubleOrNull() ?: 0.0
        val rawTax = tipTax.value.toDoubleOrNull() ?: 0.0

        val taxAmount = if (taxIsPercent.value) bill * rawTax / 100.0 else rawTax
        val tipBase = if (noTipOnTax.value) bill else bill + taxAmount
        val tipAmount = if (tipIsPercent.value) tipBase * rawTip / 100.0 else rawTip

        val total = bill + taxAmount + tipAmount
        val perPerson = total / people

        tipTotalResult.value = BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toPlainString()
        tipPerPersonResult.value = BigDecimal(perPerson).setScale(2, RoundingMode.HALF_UP).toPlainString()
    }

    // Discount Logic
    fun selectDiscField(field: String) {
        discActiveField.value = field
    }

    fun onDiscNumber(num: String) {
        when (discActiveField.value) {
            "price" -> discPrice.value = if (discPrice.value == "0") num else discPrice.value + num
            "disc" -> discDiscount.value = if (discDiscount.value == "0") num else discDiscount.value + num
            "tax" -> discTax.value = if (discTax.value == "0") num else discTax.value + num
        }
        calculateDiscount()
    }

    fun onDiscDot() {
        when (discActiveField.value) {
            "price" -> if (!discPrice.value.contains(".")) discPrice.value += "."
            "disc" -> if (!discDiscount.value.contains(".")) discDiscount.value += "."
            "tax" -> if (!discTax.value.contains(".")) discTax.value += "."
        }
        calculateDiscount()
    }

    fun onDiscDeleteLast() {
        when (discActiveField.value) {
            "price" -> discPrice.value = if (discPrice.value.length > 1) discPrice.value.dropLast(1) else "0"
            "disc" -> discDiscount.value = if (discDiscount.value.length > 1) discDiscount.value.dropLast(1) else "0"
            "tax" -> discTax.value = if (discTax.value.length > 1) discTax.value.dropLast(1) else "0"
        }
        calculateDiscount()
    }

    fun onDiscClearAll() {
        when (discActiveField.value) {
            "price" -> discPrice.value = "0"
            "disc" -> discDiscount.value = "0"
            "tax" -> discTax.value = "0"
        }
        calculateDiscount()
    }

    private fun calculateDiscount() {
        val price = discPrice.value.toDoubleOrNull() ?: 0.0
        val discPct = discDiscount.value.toDoubleOrNull() ?: 0.0
        val taxPct = discTax.value.toDoubleOrNull() ?: 0.0

        val saved = price * discPct / 100.0
        val discountedPrice = price - saved
        val taxAmount = discountedPrice * taxPct / 100.0
        val total = discountedPrice + taxAmount

        discSavedResult.value = BigDecimal(saved).setScale(2, RoundingMode.HALF_UP).toPlainString()
        discTotalResult.value = BigDecimal(total).setScale(2, RoundingMode.HALF_UP).toPlainString()
    }
}
