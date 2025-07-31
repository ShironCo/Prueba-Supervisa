package com.example.pruebatecnicasupervisa.presentation.ui.navigation

sealed class NavigationRoutes(
    val route: String
) {
    data object TaskScreen: NavigationRoutes ("taskScreen")
    data object AddTaskScreen: NavigationRoutes("addTaskScreen")
    data object PokemonScreen: NavigationRoutes("pokemonScreen")
}