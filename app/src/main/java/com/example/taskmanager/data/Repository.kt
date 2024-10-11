package com.example.taskmanager.data

import androidx.compose.runtime.mutableStateListOf
import java.util.Calendar

//These are the variables for the Calendar and the date and time
var cal = Calendar.getInstance()
var year = cal.get(Calendar.YEAR)
var month = cal.get(Calendar.MONTH)
var day = cal.get(Calendar.DAY_OF_MONTH)
var hour = cal.get(Calendar.HOUR_OF_DAY)
var minute = cal.get(Calendar.MINUTE)

//This is the variables that sets the date and time
var setDueDate = "$day/${month+1}/$year"
var setDueTime = "%02d:%02d".format(hour, minute)


//This is where all the tasks will be located
data class Task(var title: String, var dueDate: String, var dueTime: String, var content: String)
var allTasks = mutableStateListOf(
    Task(title="Task 1", dueDate="10/15/2024", dueTime="13:25", content="This is due soon"),
    Task(title="Task 2", dueDate="10/16/2024", dueTime="08:15", content="This is due later"),
    Task(title="Task 3", dueDate="10/17/2024", dueTime="17:30", content="This is due after a long time"),
    Task(title="Task 4", dueDate="10/18/2024", dueTime="04:45", content="This is a very long task that is " +
            " bothersome and needs to be truncated")
)