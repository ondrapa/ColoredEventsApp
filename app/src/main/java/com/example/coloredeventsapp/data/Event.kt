package com.example.coloredeventsapp.data

import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.compose.ui.graphics.painter.Painter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.coloredeventsapp.ui.theme.*

@Entity
data class Event(
    val title: String,
    val color: Int,
    val description: String?,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val eventColors = listOf(BackgroundYellow, BackgroundOrange, BackgroundRed, BackgroundPurple, BackgroundBlue, BackgroundGreen)
    }
}