package com.example.core_data.datasources.network.response_models

import kotlinx.serialization.Serializable

@Serializable
data class DefaultResponse<T>(
    val success: Boolean,
    val data: T
)
