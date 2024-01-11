package com.example.core_data.repositories

import com.example.core_data.db.entities.CameraObject
import com.example.core_domain.models.CameraModel
import com.example.core_domain.repositories.ICamerasRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CamerasRepository @Inject constructor(
    private val realm: Realm
): ICamerasRepository {
    override suspend fun isEmpty(): Boolean {
        return realm.query<CameraObject>().find().isEmpty()
    }

    override suspend fun update() {
        TODO("Not yet implemented")
    }

    override suspend fun getCameras(): List<CameraModel> {
        return realm.query<CameraObject>().find().map(CameraObject::toModel)
    }

    override suspend fun getCamerasLive(): Flow<List<CameraModel>> {
        return realm.query<CameraObject>().find().asFlow().map {
            it.list.map(CameraObject::toModel)
        }
    }

    override suspend fun setIsFavorite(camerasModel: CameraModel, value: Boolean) {
        realm.writeBlocking {
            findLatest(CameraObject(camerasModel))?.favorites = value
        }
    }
}