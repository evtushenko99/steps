package com.example.localdata.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.localdata.domain.repository.ProfileImageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileImageRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : ProfileImageRepository {

    override suspend fun getImageUrl(): Flow<String> = dataStore.data.map { it: Preferences ->
        it[PROFILE_IMAGE_URL_KEY] ?: ""
    }.distinctUntilChanged()

    override suspend fun upsertUrl(value: String) {
        dataStore.edit { it: MutablePreferences ->
            it[PROFILE_IMAGE_URL_KEY] = value
        }
    }

    companion object {
        val PROFILE_IMAGE_URL_KEY = stringPreferencesKey("profileImage")
    }
}