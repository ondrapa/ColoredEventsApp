package com.example.coloredeventsapp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    @Delete
    suspend fun deleteEvent(event: Event)

    @Query("SELECT * FROM event WHERE id = :id")
    suspend fun getEventById(id: Int): Event?

    @Query("SELECT * FROM event")
    fun getEvents(): Flow<List<Event>>
}