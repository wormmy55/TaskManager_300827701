package com.example.taskmanager.data

//These are the routes for the navigation
sealed class TaskRoutes(val route: String){
    data object Home : TaskRoutes("home")
    data object ViewEdit : TaskRoutes("viewEdit")
    data object CreateTask : TaskRoutes("createTask")
}