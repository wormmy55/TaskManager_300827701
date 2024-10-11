package com.example.taskmanager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.DatePicker
import com.example.taskmanager.TimePicker
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.allTasks
import com.example.taskmanager.data.setDueDate
import com.example.taskmanager.data.setDueTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTaskScreen(
    onBackButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
    //modifier: Modifier = Modifier,
    //windowSizeClass : WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
){
    //val showBottomAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT

    //Variables for the text fields
    var textTitle by remember { mutableStateOf("New Task") }
    var calendarDueDate by remember { mutableStateOf(setDueDate) }
    var calendarDueTime by remember { mutableStateOf(setDueTime) }
    var textContent by remember { mutableStateOf("Lorem Ipsum") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.inversePrimary)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        //This is the button to return without saving the task
                        Button(
                            onClick = { onBackButtonClick() }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                "Return",)
                        }
                        //This is the text field for the title of the task
                        TextField(
                            value = textTitle,
                            onValueChange = {textTitle = it},
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                //This calls the save task button composable function
                SaveTaskButton(
                    textTitle = textTitle,
                    calendarDueDate = calendarDueDate,
                    calendarDueTime = calendarDueTime,
                    textContent = textContent,
                    onClick = { onSaveButtonClick() },
                )
            }
        },
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            //This calls the date picker composable function
            DatePicker(
                value = calendarDueDate,
                onValueChange = { calendarDueDate = it })
            //This calls the time picker composable function
            TimePicker(
                value = calendarDueTime,
                onValueChange = { calendarDueTime = it}
            )
            //This is the text field for the content of the task
            TextField(
                value = textContent,
                onValueChange = {textContent = it},
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            )
        }
    }
}

//This is the function which will save the task
@Composable
fun SaveTaskButton(
    textTitle: String,
    calendarDueDate: String,
    calendarDueTime: String,
    textContent: String,
    onClick: () -> Unit,
){
    Button(
        onClick = { onClick()
            //This will add the new task to the allTasks list
            allTasks.add(Task(title = textTitle, dueDate = calendarDueDate, dueTime = calendarDueTime, content = textContent))},
    ) {
        Icon(Icons.Sharp.Done, "Save")
    }
}

//This is a preview of the CreateTaskScreen
@Preview
@Composable
fun NewTaskScreenPreview(){
    CreateTaskScreen(
        onBackButtonClick = {},
        onSaveButtonClick = {},
    )
}
