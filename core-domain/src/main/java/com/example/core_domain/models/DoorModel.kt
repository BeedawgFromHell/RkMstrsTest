package com.example.core_domain.models

data class DoorModel(
    val id: Int,
    val name: String,
    val room: String,
    val favorites: Boolean,
)