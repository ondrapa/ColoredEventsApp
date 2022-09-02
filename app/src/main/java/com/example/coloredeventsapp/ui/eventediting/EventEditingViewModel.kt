package com.example.coloredeventsapp.ui.eventediting

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.WorkSource
import android.provider.MediaStore
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.bumptech.glide.Glide
import com.example.coloredeventsapp.data.Event
import com.example.coloredeventsapp.data.EventRepository
import com.example.coloredeventsapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.security.Provider
import java.security.interfaces.DSAKey
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class EventEditingViewModel @Inject constructor(
    private val repository: EventRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var eventA by mutableStateOf<Event?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var color by mutableStateOf<Int>(Event.eventColors.random().toArgb())
        private set

    var description by mutableStateOf("")
        private set

    var image by mutableStateOf<Uri?>(null)
        private set

    /*
    fun onImageChange(imageChange: Uri) {
        image = imageChange
    }
    */

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val eventId = savedStateHandle.get<Int>("eventId")!!
        if (eventId != -1) {
            viewModelScope.launch {
                repository.getEventById(eventId)?.let { event ->
                    title = event.title
                    color = event.color
                    description = event.description ?: ""
                    // image = event.image.toUri()
                    this@EventEditingViewModel.eventA = event
                }
            }
        }
    }

    fun onEvent(event: EventEditingEvent) {
        when(event) {
            is EventEditingEvent.OnTitleChange -> {
                title = event.title
            }
            is EventEditingEvent.OnColorChange -> {
                color = event.color
            }
            is EventEditingEvent.OnDescriptionChange -> {
                description = event.description
            }
            is EventEditingEvent.OnSaveEventClick -> {
                viewModelScope.launch {
                    if (title.isBlank()) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "The title cant be empty"
                        ))
                        return@launch
                    }
                    /*
                    if (image == null) {
                        sendUiEvent(UiEvent.ShowSnackbar(
                            message = "Chose Image"
                        ))
                        return@launch
                    }
                     */
                    repository.insertEvent(
                        Event(
                            title = title,
                            color = color,
                            description = description,
                            id = eventA?.id
                            // image = image.toString()
                        )
                    )
                    sendUiEvent(UiEvent.PopBackStack)
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