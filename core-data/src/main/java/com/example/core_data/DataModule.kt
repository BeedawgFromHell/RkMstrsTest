package com.example.core_data

import com.example.core_data.db.entities.CameraObject
import com.example.core_data.db.entities.DoorObject
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