package com.example.rkmstrstest

import com.example.core_domain.models.DoorModel
import com.example.core_domain.repositories.ICamerasRepository
import com.example.core_domain.repositories.IDoorsRepository
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @Mock
    private lateinit var camerasRepository: ICamerasRepository

    @Mock
    private lateinit var doorsRepository: IDoorsRepository

    @InjectMocks
    private lateinit var viewModel: MainViewModel

    private val doors = listOf(
        DoorModel(
            id = 1,
            name = "name 1",
            room = null,
            snapshotUrl = null,
            favorites = false
        ),
        DoorModel(
            id = 2,
            name = "name 2",
            room = null,
            snapshotUrl = null,
            favorites = false
        ),
        DoorModel(
            id = 3,
            name = "name 3",
            room = null,
            snapshotUrl = null,
            favorites = false
        )
    )

}