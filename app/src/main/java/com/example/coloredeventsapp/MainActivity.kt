package com.example.coloredeventsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coloredeventsapp.ui.eventediting.EventEditingScreen
import com.example.coloredeventsapp.ui.eventlist.EventListScreen
import com.example.coloredeventsapp.ui.theme.ColoredEventsAppTheme
import com.example.coloredeventsapp.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColoredEventsAppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.EVENT_LIST
                ) {
                    composable(Routes.EVENT_LIST) {
                        EventListScreen(onNavigate = {
                            navController.navigate((it.route))
                        })
                    }
                    composable(
                        route = Routes.ADD_EDIT_EVENT +
                                "?eventId={eventId}&eventColor={eventColor}",
                        arguments = listOf(
                            navArgument(name = "eventId") {
                                type = NavType.IntType
                                defaultValue = -1
                            },
                            navArgument(name = "eventColor") {
                                type = NavType.IntType
                                defaultValue = -1
                            }
                        )
                    ) {
                        val color = it.arguments?.getInt("eventColor") ?: -1
                        EventEditingScreen(
                            onPopBackStack = {
                                navController.popBackStack()
                            },
                            eventColor = color
                        )
                    }
                }
            }
        }
    }
}
