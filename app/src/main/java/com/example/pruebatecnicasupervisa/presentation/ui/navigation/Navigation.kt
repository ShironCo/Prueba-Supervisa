package com.example.pruebatecnicasupervisa.presentation.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pruebatecnicasupervisa.presentation.screen.AddTaskScreen
import com.example.pruebatecnicasupervisa.presentation.screen.TaskScreen

@Composable
fun Navigation (){
    val navHostController = rememberNavController()
    NavHost(navController = navHostController,
        startDestination = NavigationRoutes.TaskScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
        ){
        composable(route = NavigationRoutes.TaskScreen.route){
            TaskScreen(navHostController)
        }
        composable(
            route = NavigationRoutes.AddTaskScreen.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 })},
            exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 })}
            ){
            AddTaskScreen(navHostController)
        }
    }
}