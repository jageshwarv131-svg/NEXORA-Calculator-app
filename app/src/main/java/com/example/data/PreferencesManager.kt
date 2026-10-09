package com.example.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("nexora_calc_prefs", Context.MODE_PRIVATE)

    var wallpaperIndex: Int
        get() = prefs.getInt("wallpaper_idx", 0)
        set(value) = prefs.edit().putInt("wallpaper_idx", value).apply()

    var isDarkMode: Boolean
        get() = prefs.getBoolean("is_dark_mode", false)
        set(value) = prefs.edit().putBoolean("is_dark_mode", value).apply()

    var isRadMode: Boolean
        get() = prefs.getBoolean("is_rad_mode", true)
        set(value) = prefs.edit().putBoolean("is_rad_mode", value).apply()

    var standardMemory: Double
        get() = java.lang.Double.longBitsToDouble(prefs.getLong("std_memory", java.lang.Double.doubleToLongBits(0.0)))
        set(value) = prefs.edit().putLong("std_memory", java.lang.Double.doubleToLongBits(value)).apply()

    var tipNoTipOnTax: Boolean
        get() = prefs.getBoolean("tip_no_tax", false)
        set(value) = prefs.edit().putBoolean("tip_no_tax", value).apply()
}
