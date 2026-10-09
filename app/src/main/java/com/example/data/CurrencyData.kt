package com.example.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

data class CurrencyItem(
    val code: String,
    val name: String,
    val flag: String,
    val symbol: String
)

object CurrencyData {
    val currencies = listOf(
        CurrencyItem("USD", "United States Dollar", "🇺🇸", "$"),
        CurrencyItem("INR", "Indian Rupee", "🇮🇳", "₹"),
        CurrencyItem("EUR", "Euro", "🇪🇺", "€"),
        CurrencyItem("GBP", "British Pound", "🇬🇧", "£"),
        CurrencyItem("AED", "UAE Dirham", "🇦🇪", "د.إ"),
        CurrencyItem("AUD", "Australian Dollar", "🇦🇺", "A$"),
        CurrencyItem("CAD", "Canadian Dollar", "🇨🇦", "C$"),
        CurrencyItem("SGD", "Singapore Dollar", "🇸🇬", "S$"),
        CurrencyItem("JPY", "Japanese Yen", "🇯🇵", "¥"),
        CurrencyItem("CNY", "Chinese Yuan", "🇨🇳", "¥"),
        CurrencyItem("SAR", "Saudi Riyal", "🇸🇦", "﷼"),
        CurrencyItem("KRW", "South Korean Won", "🇰🇷", "₩"),
        CurrencyItem("RUB", "Russian Ruble", "🇷🇺", "₽"),
        CurrencyItem("BRL", "Brazilian Real", "🇧🇷", "R$"),
        CurrencyItem("ZAR", "South African Rand", "🇿🇦", "R")
    )

    val defaultFallbackRates = mapOf(
        "USD" to 1.0,
        "INR" to 86.5,
        "EUR" to 0.92,
        "GBP" to 0.79,
        "AED" to 3.67,
        "AUD" to 1.55,
        "CAD" to 1.41,
        "SGD" to 1.35,
        "JPY" to 154.2,
        "CNY" to 7.24,
        "SAR" to 3.75,
        "KRW" to 1420.0,
        "RUB" to 97.0,
        "BRL" to 5.75,
        "ZAR" to 18.2
    )

    private val client = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .build()

    suspend fun fetchRates(): Pair<Map<String, Double>, String> = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder()
                .url("https://open.er-api.com/v6/latest/USD")
                .build()
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string() ?: ""
                val json = JSONObject(body)
                val ratesObj = json.getJSONObject("rates")
                val ratesMap = mutableMapOf<String, Double>()
                currencies.forEach { curr ->
                    if (ratesObj.has(curr.code)) {
                        ratesMap[curr.code] = ratesObj.getDouble(curr.code)
                    }
                }
                val now = SimpleDateFormat("dd MMM HH:mm", Locale.getDefault()).format(Date())
                ratesMap to now
            } else {
                defaultFallbackRates to "Offline Cached"
            }
        } catch (_: Exception) {
            defaultFallbackRates to "Offline Cached"
        }
    }
}
