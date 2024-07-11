package com.example.glonav

import androidx.lifecycle.ViewModel
import com.example.localdata.domain.repository.DaysRepository
import com.example.localdata.domain.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class StepsViewModel @Inject constructor(
    themeRepository: ThemeRepository,
    val daysRepository: DaysRepository
) : ViewModel() {


    val isDarkThemeEnabled: Flow<Boolean> = themeRepository.isDarkTheme()
}