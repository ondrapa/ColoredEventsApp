package com.example.coloredeventsapp.di

import android.app.Application
import androidx.room.Room
import com.example.coloredeventsapp.data.EventDatabase
import com.example.coloredeventsapp.data.EventRepository
import com.example.coloredeventsapp.data.EventRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideEventDatabase(app: Application): EventDatabase {
        return Room.databaseBuilder(
            app,
            EventDatabase::class.java,
            "event_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEventRepository(database: EventDatabase): EventRepository {
        return EventRepositoryImpl(database.dao)
    }
}