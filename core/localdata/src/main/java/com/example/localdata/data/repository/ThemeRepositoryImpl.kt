package com.example.localdata.data.repository

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.localdata.domain.repository.ThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ThemeRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val context: Context
) : ThemeRepository {

    override fun isDarkTheme(): Flow<Boolean> = dataStore.data.map {
        it[IS_DARK_THEME]
            ?: (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES)
    }.distinctUntilChanged()


    override suspend fun changeTheme(isDark: Boolean) {
        dataStore.edit {
            it[IS_DARK_THEME] = isDark
        }
    }

    companion object {
        //App settings
        val IS_DARK_THEME = booleanPreferencesKey("IsDarkTheme")
    }
}