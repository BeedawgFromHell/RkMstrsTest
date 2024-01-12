package com.example.core_data.network.response_models

import com.example.core_domain.models.DoorModel
import kotlinx.serialization.Serializable

@Serializable
data class DoorResponseModel(
    val name: String,
    val room: String?,
    val snapshot: String? = null,
    val id: Int,
    val favorites: Boolean,
) {
    fun toModel() = DoorModel(
        id,name,room, snapshot,favorites
    )
}