package com.example.core_data.datasources.network

import com.example.core_data.datasources.network.response_models.CamerasResponseModel
import com.example.core_data.datasources.network.response_models.DoorResponseModel
import javax.inject.Inject

interface INetworkDatasource {
    suspend fun getCameras(): CamerasResponseModel?

    suspend fun getDoors(): List<DoorResponseModel>?
}

class NetworkDatasource @Inject constructor(
    private val httpClient: HttpClient
): INetworkDatasource {
    override suspend fun getCameras(): CamerasResponseModel? {
        return httpClient.getCameras()
    }
    override suspend fun getDoors(): List<DoorResponseModel>? {
        return httpClient.getDoors()
    }
}