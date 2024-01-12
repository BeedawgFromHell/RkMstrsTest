package com.example.core_data.repositories

import com.example.core_data.db.entities.DoorObject
import com.example.core_data.network.HttpClient
import com.example.core_data.network.response_models.DoorResponseModel
import com.example.core_domain.models.DoorModel
import com.example.core_domain.repositories.IDoorsRepository
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DoorsRepository @Inject constructor(
    private val realm: Realm,
    private val http: HttpClient
) : IDoorsRepository {
    override suspend fun isEmpty(): Boolean {
        return realm.query<DoorObject>().find().isEmpty()
    }

    override suspend fun update(): Boolean {
        val response = http.getDoors() ?: return false
        response.map { it }
        val doorModels = response.map(DoorResponseModel::toModel)

        realm.writeBlocking {
            this.delete(DoorObject::class)
            doorModels.map {
                DoorObject(it)
            }.forEach {
                this.copyToRealm(it, UpdatePolicy.ALL)
            }
        }

        return true
    }

    override suspend fun getAllDoors(): List<DoorModel> {
        return realm.query<DoorObject>().find().map(DoorObject::toModel)
    }

    override fun getAllDoorsLive(): Flow<List<DoorModel>> {
        return realm.query<DoorObject>().find().asFlow().map {
            it.list.map(DoorObject::toModel)
        }
    }

    override suspend fun renameDoor(door: DoorModel, newName: String) {
        realm.writeBlocking {
            val realmObject = DoorObject(door)
            findLatest(realmObject)?.name = newName
        }
    }

    override suspend fun setIsFavorite(door: DoorModel, value: Boolean) {
        realm.writeBlocking {
            val realmObject = DoorObject(door)
            findLatest(realmObject)?.favorites = value
        }
    }
}