package com.example.core_data.datasources.db.entities

import com.example.core_domain.models.CameraModel
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class CameraObject(): RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var id: Int = 0
    var name: String = ""
    var snapshotUrl: String = ""
    var room: String? = null
    var favorites: Boolean = false
    var rec: Boolean = false

    constructor(model: CameraModel) : this() {
        this.id = model.id
        this.name = model.name
        this.snapshotUrl = model.snapshotUrl
        this.room = model.room
        this.favorites = model.favorites
        this.rec = model.rec
    }

    fun toModel() = CameraModel(
        id,name,snapshotUrl, room, favorites, rec
    )
}

