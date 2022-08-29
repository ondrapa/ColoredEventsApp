package com.example.coloredeventsapp.ui.eventediting

sealed class EventEditingEvent {

    data class OnTitleChange(val title: String): EventEditingEvent()

    data class OnDescriptionChange(val description: String): EventEditingEvent()

    data class OnColorChange(val color: Int): EventEditingEvent()

    object OnSaveEventClick: EventEditingEvent()

}
