package com.example.utils

enum class Navigation(val route: String, val imageRes: Int, val screenTitle: String) {
    DAILY_COUNTER("Tree", R.drawable.ic_tree_24, "Сегодня"),
    STATISTICS("Stats", R.drawable.ic_statistics_24, "Отчет"),
    MORE("More", R.drawable.ic_settings_24, "Еще"),
    MORE_SETTINGS("MoreSettings",  R.drawable.ic_settings_24, "Больше Настроек")
}