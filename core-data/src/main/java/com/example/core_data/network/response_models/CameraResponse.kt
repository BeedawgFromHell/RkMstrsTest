package com.example.core_data.network.response_models

import com.example.core_domain.models.CameraModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CamerasResponseModel(
    @SerialName("room")
    val room: List<String>,
    @SerialName("cameras")
    val cameras: List<CameraResponseModel>
)

@Serializable
data class CameraResponseModel(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("snapshot")
    val snapshot: String,
    @SerialName("room")
    val room: String?,
    @SerialName("favorites")
    val favorites: Boolean,
    @SerialName("rec")
    val rec: Boolean
) {
    fun toModel() = CameraModel(
        id, name, snapshot, room, favorites, rec
    )
}