package com.example.coloredeventsapp.ui.eventlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coloredeventsapp.data.Event
import com.example.coloredeventsapp.data.EventRepository
import com.example.coloredeventsapp.util.Routes
import com.example.coloredeventsapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventListViewModel @Inject constructor(
    private val repository: EventRepository
): ViewModel() {
    val events = repository.getEvents()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var lastDeletedEvent: Event? = null

    fun onEvent(event: EventListEvent) {
        when(event) {
            is EventListEvent.OnAddEventClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_EVENT))
            }
            is EventListEvent.OnEventClick -> {
                sendUiEvent(UiEvent.Navigate(Routes.ADD_EDIT_EVENT + "?eventId=${event.event.id}&eventColor=${event.event.color}"))
            }
            is EventListEvent.OnDeleteEvent -> {
                viewModelScope.launch {
                    lastDeletedEvent = event.event
                    repository.deleteEvent(event.event)
                    sendUiEvent(UiEvent.ShowSnackbar(
                        message = "Event deleted",
                        action = "Undo"
                    ))
                }
            }
            is EventListEvent.OnUndoDeleteClick -> {
                lastDeletedEvent?.let { event ->
                    viewModelScope.launch {
                        repository.insertEvent(event)
                    }
                }
            }
        }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}