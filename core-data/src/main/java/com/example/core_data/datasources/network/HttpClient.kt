package com.example.core_data.datasources.network

import android.util.Log
import com.example.core_data.datasources.network.response_models.CamerasResponseModel
import com.example.core_data.datasources.network.response_models.DefaultResponse
import com.example.core_data.datasources.network.response_models.DoorResponseModel
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class HttpClient @Inject constructor() {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("KTOR => ", message)
                }
            }
            level = LogLevel.ALL
        }
    }

    suspend fun getCameras(): CamerasResponseModel? {
        return try {
            val response: HttpResponse = client.get("$BASE_URL/api/rubetek/cameras/")
            response.receive<DefaultResponse<CamerasResponseModel>>().data
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    suspend fun getDoors(): List<DoorResponseModel>? {
        return try {
            val response: HttpResponse = client.get("$BASE_URL/api/rubetek/doors/")
            response.receive<DefaultResponse<List<DoorResponseModel>>>().data
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    companion object {
        private const val BASE_URL = "https://cars.cprogroup.ru"
    }
}