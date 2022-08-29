package com.example.coloredeventsapp.ui.eventlist

import com.example.coloredeventsapp.data.Event

sealed class EventListEvent {

    data class OnDeleteEvent(val event: Event): EventListEvent()

    data class OnEventClick(val event: Event): EventListEvent()

    object OnAddEventClick: EventListEvent()

    object OnUndoDeleteClick: EventListEvent()
}