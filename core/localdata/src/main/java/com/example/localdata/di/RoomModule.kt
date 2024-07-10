package com.example.localdata.di

import android.content.Context
import android.preference.PreferenceManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.localdata.data.repository.ProfileImageRepositoryImpl
import com.example.localdata.data.repository.SettingsRepositoryImpl
import com.example.localdata.data.repository.ThemeRepositoryImpl
import com.example.localdata.data.source.ForestDatabase
import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.domain.repository.ProfileImageRepository
import com.example.localdata.domain.repository.SettingsRepository
import com.example.localdata.domain.repository.ThemeRepository
import com.example.localdata.domain.usecase.GetDayUseCase
import com.example.localdata.domain.usecase.GetDayUseCaseImpl
import com.example.localdata.domain.usecase.IncrementStepCountUseCase
import com.example.localdata.domain.usecase.IncrementStepCountUseCaseImpl
import com.example.localdata.domain.usecase.UpdateDaySettingsUseCase
import com.example.localdata.domain.usecase.UpdateDaySettingsUseCaseImpl
import com.example.localdata.model.DbConstants.SETTINGS_DATA_STORAGE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideForestDatabase(
        @ApplicationContext context: Context
    ): ForestDatabase = Room.databaseBuilder(
        context,
        ForestDatabase::class.java,
        ForestDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigrationOnDowngrade()
        .fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideDataSource(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = context.dataStore

    @Singleton
    @Provides
    fun provideThemeRepository(
        @ApplicationContext context: Context,
        dataStore: DataStore<Preferences>
    ): ThemeRepository = ThemeRepositoryImpl(
        dataStore = dataStore,
        context = context
    )

    @Singleton
    @Provides
    fun provideDayRepository(forestDatabase: ForestDatabase): DaysRepository {
        return com.example.localdata.data.repository.DaysRepositoryImpl(forestDatabase.daysDao)
    }

    @Singleton
    @Provides
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImpl(PreferenceManager.getDefaultSharedPreferences(context))
    }

    @Singleton
    @Provides
    fun provideGetDay(getDayImpl: GetDayUseCaseImpl): GetDayUseCase = getDayImpl

    @Singleton
    @Provides
    fun provideIncrementStepCount(incrementStepCountImpl: IncrementStepCountUseCaseImpl): IncrementStepCountUseCase =
        incrementStepCountImpl

    @Singleton
    @Provides
    fun provideUpdateDaySettingsUseCase(updateDaySettingsUseCaseImpl: UpdateDaySettingsUseCaseImpl): UpdateDaySettingsUseCase =
        updateDaySettingsUseCaseImpl

    @Singleton
    @Provides
    fun provideProfileImageRepository(dataStore: DataStore<Preferences>): ProfileImageRepository {
        return ProfileImageRepositoryImpl(dataStore)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_DATA_STORAGE_NAME)
}