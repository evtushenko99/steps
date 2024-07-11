package com.example.localdata.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.test.platform.app.InstrumentationRegistry
import com.example.test.MainCoroutineRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ThemeRepositoryImplTest {

    @JvmField
    @Rule
    val coroutineRule = MainCoroutineRule()

    private val context: Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        scope = coroutineRule.testScope,
        produceFile = { context.preferencesDataStoreFile(TEST_DATASTORE_NAME) }
    )
    private val repository = ThemeRepositoryImpl(testDataStore, context)

    @After
    fun tearDown() = runTest {
        runBlocking { testDataStore.edit { it.clear() } }
        coroutineRule.testScope.cancel()
    }

    @Test
    fun isDarkThemDisabledDataStoreEmpty() = runTest {
        // Act
        val actual = repository.isDarkTheme()

        // Assert
        assertThat(actual.first()).isFalse()
    }

    @Test
    fun isDarkThemeEnabled() = runTest {
        // Assert
        repository.changeTheme(true)

        // Act
        val actual = repository.isDarkTheme()

        // Assert
        assertThat(actual.first()).isTrue()
    }

    private companion object {
        const val TEST_DATASTORE_NAME = "testDataStoreName"
    }
}