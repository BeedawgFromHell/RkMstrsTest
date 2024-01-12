package com.example.core_data.db.entities

import com.example.core_domain.models.DoorModel
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class DoorObject(): RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var id: Int = 0
    var name: String = ""
    var favorites: Boolean = false
    var room: String? = null
    var snapshotUrl: String? = null

    constructor(model: DoorModel): this() {
        id = model.id
        name = model.name
        favorites = model.favorites
        room = model.room
        snapshotUrl = model.snapshotUrl
    }

    fun toModel() = DoorModel(
        id,
        name,
        room,
        snapshotUrl,
        favorites
    )
}