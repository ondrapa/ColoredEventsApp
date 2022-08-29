package com.example.coloredeventsapp.data

import kotlinx.coroutines.flow.Flow

interface EventRepository {

    suspend fun insertEvent(event: Event)

    suspend fun deleteEvent(event: Event)

    suspend fun getEventById(id: Int): Event?

    fun getEvents(): Flow<List<Event>>

}