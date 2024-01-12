package com.example.core_domain.models

data class CameraModel(
    val id: Int,
    val name: String,
    val snapshotUrl: String,
    val room: String?,
    val favorites: Boolean,
    val rec: Boolean
)
