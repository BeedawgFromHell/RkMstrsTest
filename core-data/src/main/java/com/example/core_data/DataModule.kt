package com.example.core_data

import com.example.core_data.datasources.db.ILocalDatasource
import com.example.core_data.datasources.db.LocalDatasource
import com.example.core_data.datasources.db.entities.CameraObject
import com.example.core_data.datasources.db.entities.DoorObject
import com.example.core_data.datasources.network.INetworkDatasource
import com.example.core_data.datasources.network.NetworkDatasource
import com.example.core_data.repositories.CamerasRepository
import com.example.core_data.repositories.DoorsRepository
import com.example.core_domain.repositories.ICamerasRepository
import com.example.core_domain.repositories.IDoorsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideRealm(): Realm {
        val config = RealmConfiguration.create(
            setOf(CameraObject::class, DoorObject::class)
        )
        return Realm.open(config)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataBindModule {
    @Binds
    abstract fun bindCamerasRepository(impl: CamerasRepository): ICamerasRepository

    @Binds
    abstract fun bindDoorsRepository(impl: DoorsRepository): IDoorsRepository

    @Binds
    abstract fun bindLocalDatasource(impl: LocalDatasource): ILocalDatasource

    @Binds
    abstract fun bindNetworkDatasource(impl: NetworkDatasource): INetworkDatasource
}