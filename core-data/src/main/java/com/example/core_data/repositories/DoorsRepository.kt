package com.example.core_data.repositories

import com.example.core_data.datasources.db.ILocalDatasource
import com.example.core_data.datasources.db.entities.DoorObject
import com.example.core_data.datasources.network.INetworkDatasource
import com.example.core_data.datasources.network.response_models.DoorResponseModel
import com.example.core_domain.models.DoorModel
import com.example.core_domain.repositories.IDoorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DoorsRepository @Inject constructor(
    private val localDatasource: ILocalDatasource,
    private val networkDatasource: INetworkDatasource
) : IDoorsRepository {

    override suspend fun isEmpty(): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext localDatasource.doorsIsEmpty()
        }
    }

    override suspend fun update(): Boolean {
        return withContext(Dispatchers.IO) {
            val response = networkDatasource.getDoors() ?: return@withContext false
            response.map { it }
            val doorObjects = response.map(DoorResponseModel::toModel).map { DoorObject(it) }
            localDatasource.insertDoors(doorObjects)
            return@withContext true
        }
    }

    override suspend fun getAllDoors(): List<DoorModel> {
        return withContext(Dispatchers.IO) {
            return@withContext localDatasource.getDoors().map(DoorObject::toModel)
        }
    }

    override suspend fun renameDoor(door: DoorModel, newName: String) {
        withContext(Dispatchers.IO) {
            localDatasource.getDoors().find { it.id == door.id }?.let { doorObject ->
                doorObject.name = newName
                localDatasource.update(doorObject)
            }
        }
    }

    override suspend fun toggleFavorite(doorId: Int) {
        withContext(Dispatchers.IO) {
            localDatasource.getDoors().find { it.id == doorId }?.let { doorObject ->
                doorObject.favorites = !doorObject.favorites
                localDatasource.update(doorObject)
            }
        }
    }
}