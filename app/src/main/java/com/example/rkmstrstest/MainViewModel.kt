package com.example.rkmstrstest

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core_domain.models.CameraModel
import com.example.core_domain.models.DoorModel
import com.example.core_domain.repositories.ICamerasRepository
import com.example.core_domain.repositories.IDoorsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val camerasRepository: ICamerasRepository,
    private val doorsRepository: IDoorsRepository
) : ViewModel() {
    val errorMessage = mutableStateOf("")
    val isRefreshing = mutableStateOf(false)

    val mappedCamerasState = mutableStateMapOf<String, MutableList<CameraModel>>()
    val doorsState = mutableStateListOf<DoorModel>()

    init {
        viewModelScope.launch {
            if (camerasRepository.isEmpty() || doorsRepository.isEmpty()) {
                refreshData()
            }
            updateLiveData()
        }
    }

    private fun updateLiveData() {
        viewModelScope.launch {
            val cameras = camerasRepository.getCameras()
            cameras.forEach { camera ->
                if (camera.room != null) {
                    if (mappedCamerasState[camera.room] == null) {
                        mappedCamerasState[camera.room!!] = mutableListOf(camera)
                    } else {
                        mappedCamerasState[camera.room!!]?.add(camera)
                    }
                }
            }
        }

        viewModelScope.launch {
            val doors = doorsRepository.getAllDoors()
            doorsState.clear()
            doorsState.addAll(doors)
        }
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            isRefreshing.value = true
            val cameraUpdate = async {
                camerasRepository.update()
            }
            val doorUpdate = async {
                doorsRepository.update()
            }

            if (!cameraUpdate.await() || !doorUpdate.await()) {
                errorMessage.value = "Ошибка в загрузке данных"
            }

            isRefreshing.value = false
        }
    }

    fun onFavoriteCamera(cameraId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            camerasRepository.toggleFavorite(cameraId)
            updateLiveData()
        }
    }

    fun changeDoorName(doorModel: DoorModel, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            doorsRepository.renameDoor(doorModel, newName)
            updateLiveData()
        }
    }

    fun onFavoriteDoor(doorId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            doorsRepository.toggleFavorite(doorId)
            updateLiveData()
        }
    }
}