package com.example.localdata.domain.repository

import kotlinx.coroutines.flow.Flow

interface ProfileImageRepository {

    suspend fun getImageUrl(): Flow<String>
    suspend fun upsertUrl(value: String)
}