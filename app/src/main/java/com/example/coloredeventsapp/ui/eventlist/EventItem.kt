package com.example.coloredeventsapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.coloredeventsapp.data.Event
import com.example.coloredeventsapp.ui.eventlist.EventListEvent
import kotlin.math.E

@Composable
fun EventItem(
    event: Event,
    onEvent: (EventListEvent) -> Unit,
    modifier: Modifier
) {
    Card(
        Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { onEvent(EventListEvent.OnEventClick(event)) },
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            /*
            Box(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth()
            ) {

                Image(
                    painter = rememberImagePainter(data = event.image),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .padding(top = 115.dp)
                        .fillMaxSize()
                        .background(Color(event.color))
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(event.color)
                                )
                            )
                        )
                )
            */
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(event.color))
                ) {
                    Text(
                        text = event.title,
                        textAlign = TextAlign.Left,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp)
                            .padding(5.dp)
                    )
                }
            if (event.description != "") {
                Text(
                    text = event.description!!,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Left,
                    maxLines = 5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(event.color))
                        .padding(horizontal = 10.dp)
                        .padding(5.dp)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(event.color))
                    .height(40.dp)
            ) {
                Text(
                    text = "",
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(start = 0.dp)
                )
                IconButton(
                    onClick = {
                        onEvent(EventListEvent.OnDeleteEvent(event))
                    }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}