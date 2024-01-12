package com.example.rkmstrstest

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val cameras
        get() = camerasRepository.getCamerasLive()
    val doors
        get() = doorsRepository.getAllDoorsLive()

    init {
        viewModelScope.launch {
            if (camerasRepository.isEmpty() || doorsRepository.isEmpty()) refreshData()
        }
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            val cameraUpdate = async {
                camerasRepository.update()
            }
            val doorUpdate = async {
                doorsRepository.update()
            }

            if (!cameraUpdate.await() || !doorUpdate.await()) {
                errorMessage.value = "Ошибка в загрузке данных"
            }
        }
    }
}