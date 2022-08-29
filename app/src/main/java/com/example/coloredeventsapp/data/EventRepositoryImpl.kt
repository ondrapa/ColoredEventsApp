package com.example.coloredeventsapp.data

import kotlinx.coroutines.flow.Flow

class EventRepositoryImpl(
    private val dao: EventDao
): EventRepository {

    override suspend fun insertEvent(event: Event) {
        dao.insertEvent(event)
    }

    override suspend fun deleteEvent(event: Event) {
        dao.deleteEvent(event)
    }

    override suspend fun getEventById(id: Int): Event? {
        return dao.getEventById(id)
    }

    override fun getEvents(): Flow<List<Event>> {
        return dao.getEvents()
    }
}