package com.example.core_data.datasources.db

import com.example.core_data.datasources.db.entities.CameraObject
import com.example.core_data.datasources.db.entities.DoorObject
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import javax.inject.Inject

interface ILocalDatasource {
    suspend fun camerasIsEmpty(): Boolean

    suspend fun doorsIsEmpty(): Boolean

    suspend fun insertDoors(items: List<DoorObject>)

    suspend fun insertCameras(items: List<CameraObject>)

    suspend fun getDoors(): List<DoorObject>

    suspend fun getCameras(): List<CameraObject>

    suspend fun update(cameraObject: CameraObject)

    suspend fun update(doorObject: DoorObject)
}

class LocalDatasource @Inject constructor(
    private val realm: Realm
): ILocalDatasource {
    override suspend fun camerasIsEmpty(): Boolean {
        return realm.query<CameraObject>().find().isEmpty()
    }

    override suspend fun doorsIsEmpty(): Boolean {
        return realm.query<DoorObject>().find().isEmpty()
    }

    override suspend fun insertDoors(items: List<DoorObject>) {
        realm.writeBlocking {
            this.delete(CameraObject::class)
            items.forEach {
                this.copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun insertCameras(items: List<CameraObject>) {
        realm.writeBlocking {
            this.delete(CameraObject::class)
            items.forEach {
                this.copyToRealm(it, UpdatePolicy.ALL)
            }
        }
    }

    override suspend fun getDoors(): List<DoorObject> {
        return realm.query<DoorObject>().find()
    }

    override suspend fun getCameras(): List<CameraObject> {
        return realm.query<CameraObject>().find()
    }

    override suspend fun update(cameraObject: CameraObject) {
        realm.writeBlocking {
            query<CameraObject>()
                .find()
                .find {
                    it.id == cameraObject.id
                }?.apply {
                    favorites = !this.favorites
                }
        }
    }

    override suspend fun update(doorObject: DoorObject) {
        realm.writeBlocking {
            query<CameraObject>()
                .find()
                .find {
                    it.id == doorObject.id
                }?.apply {
                    favorites = !this.favorites
                }
        }
    }
}