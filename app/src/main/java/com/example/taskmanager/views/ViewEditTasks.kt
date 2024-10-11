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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskmanager.DatePicker
import com.example.taskmanager.TimePicker
import com.example.taskmanager.data.allTasks
import com.example.taskmanager.data.setDueTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEditScreen(
    onBackButtonClick: () -> Unit = {},
    onSaveButtonClick: () -> Unit = {},
    listIndex: Int,
   //windowSizeClass : WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
){
    //val showBottomAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT

    //Variables for the text fields
    var textTitle by remember { mutableStateOf(allTasks[listIndex].title) }
    var calendarDueDate by remember { mutableStateOf(allTasks[listIndex].dueDate) }
    var calendarDueTime by remember { mutableStateOf(allTasks[listIndex].dueTime) }
    var textContent by remember { mutableStateOf(allTasks[listIndex].content) }

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
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        //This is the back button to exit without saving the task
                        Button(
                            onClick = { onBackButtonClick() }
                        ) {
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, "Return")
                        }
                        //This is the text field for the title of the task
                        TextField(
                            value = textTitle,
                            onValueChange = {textTitle = it},
                            modifier = Modifier
                                .fillMaxWidth()
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
                //This calls the SaveViewEdit button composable function
                SaveViewEditButton(
                    textTitle = textTitle,
                    calendarDueDate = calendarDueDate,
                    calendarDueTime = calendarDueTime,
                    textContent = textContent,
                    onClick = { onSaveButtonClick() },
                )
            }
        },
    ){innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            //This calls the date picker composable function
            DatePicker(
                value = calendarDueDate,
                onValueChange = {calendarDueDate = it}
            )
            //This calls the time picker composable function
            TimePicker(
                value = calendarDueTime,
                onValueChange = {calendarDueTime = it}
            )
            //This is the textField for the content of the task
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


//This is the SaveViewEditButton composable function
@Composable
fun SaveViewEditButton(
    textTitle: String,
    calendarDueDate: String,
    calendarDueTime: String,
    textContent: String,
    onClick: () -> Unit,
){
    Button(
        onClick = { onClick()
            allTasks[globalIndex].title = textTitle
            allTasks[globalIndex].dueDate = calendarDueDate
            allTasks[globalIndex].dueTime = calendarDueTime
            allTasks[globalIndex].content = textContent
        },
    ) {
        Icon(Icons.Sharp.Done, "Save")
    }
}

//This is a preview of the ViewEditTaskScreen
@Preview
@Composable
fun ViewEditTaskScreenPreview(){
    ViewEditScreen(
        onBackButtonClick = {},
        onSaveButtonClick = {},
        listIndex = globalIndex,
    )
}