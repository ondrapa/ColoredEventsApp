package com.example.coloredeventsapp.ui.eventediting

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.coloredeventsapp.R
import com.example.coloredeventsapp.data.Event
import com.example.coloredeventsapp.util.UiEvent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun EventEditingScreen(
    onPopBackStack: () -> Unit,
    eventColor: Int,
    viewModel: EventEditingViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    val eventBackgroundAnimatable = remember {
        Animatable(if (eventColor != -1) Color(eventColor) else Color(viewModel.color))
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(EventEditingEvent.OnSaveEventClick)
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "save"
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = eventBackgroundAnimatable.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ) { /*
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(20.dp)
                    .clickable { launcher.launch("image/*") }
            ) {
                if (viewModel.image != null) {
                    Image(
                        contentScale = ContentScale.Crop,
                        painter = rememberImagePainter(data = viewModel.image),
                        contentDescription = "selectedImage",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    Image(
                        painter = rememberImagePainter(R.drawable.ic_launcher_foreground), 
                        contentDescription = "default image"
                    )
                }
            } */
            */
            Spacer(modifier = Modifier.height(40.dp))
            TextField(
                value = viewModel.title,
                onValueChange = {
                    viewModel.onEvent(EventEditingEvent.OnTitleChange(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
            TextField(
                value = viewModel.description,
                onValueChange = {
                    viewModel.onEvent(EventEditingEvent.OnDescriptionChange(it))
                },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Event.eventColors.forEach { color ->
                    val colorInt = color.toArgb()
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .padding(5.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color)
                            .border(
                                width = 2.5.dp,
                                color = if (viewModel.color == colorInt) {
                                    Color.Black
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                            .clickable {
                                scope.launch {
                                    eventBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 550
                                        )
                                    )
                                }
                                viewModel.onEvent(EventEditingEvent.OnColorChange(colorInt))
                            }
                    )
                }
            }
        }
    }
}