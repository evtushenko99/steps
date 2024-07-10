package com.example.steps.di

import android.app.Application
import android.content.Context
import android.hardware.SensorManager
import com.example.steps.StepsApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class StepsModule {

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.Main + SupervisorJob())

    @Provides
    @Singleton
    fun providesStateFlowLocaleDate(
        app: Application
    ): StateFlow<LocalDate> = (app as StepsApplication).currentDate.asStateFlow()


    @Provides
    @Singleton
    fun provideSensorManager(@ApplicationContext context: Context): SensorManager {
        return context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }
}