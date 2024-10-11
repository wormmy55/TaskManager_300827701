package com.example.taskmanager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskmanager.data.allTasks

var globalIndex = 0

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCreateButtonClick: () -> Unit,
    onViewEditButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    //windowSizeClass : WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
){
    //val showBottomAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        modifier = modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Task Manager"
                    )
                    FloatingActionButton(
                        onClick = { onCreateButtonClick() },
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary,
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add",
                        )
                    }
                },
            )
        },/*
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ){
                FloatingActionButton(
                    onClick = { onCreateButtonClick() },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.primary,
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add",
                    )
                }
            }
        }*/
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.primaryContainer),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ){
            //This runs the LazyTaskColumn
            LazyTaskCol(onClick = {onViewEditButtonClick()})
        }
    }
}

//This is the Lazy column
@Composable
fun LazyTaskCol(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    var selectedTask by remember { mutableStateOf("") }
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(12.dp),
    ){
        items(allTasks){task ->
            @Composable
            fun selectedTaskLine():String {
                return task.title + "\n" + task.dueDate + "\n" + task.dueTime
            }
            Text(
                text = if(selectedTask == task.title){
                    ViewEditButton(
                        onClick = { onClick()
                        globalIndex = allTasks.indexOf(task)})
                    selectedTaskLine()
                } else selectedTaskLine(),
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(1.dp, Color.Black)
                    .clickable { selectedTask = task.title }
                    .background(if (selectedTask == task.title) Color.Magenta
                    else MaterialTheme.colorScheme.tertiary),
                fontSize = 20.sp,
                maxLines = if (selectedTask == task.title) 3 else 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
            )
        }
    }
}

//This is the ViewEdit Button Function
@Composable
fun ViewEditButton(
    onClick: () -> Unit
){
    Button(
        onClick = { onClick() },
        modifier = Modifier.border(1.dp, Color.Black, shape = MaterialTheme.shapes.medium)
    ){
        Icon(
            Icons.Filled.Edit,
            contentDescription = "ViewEdit",
        )
    }
}

//This is a preview of the lazy Column
@Preview(showBackground = true)
@Composable
fun LazyTaskColPreview(){
    LazyTaskCol(onClick = {})
}

//This is a preview of the ViewEdit button
@Preview(showBackground = true)
@Composable
fun ViewEditButtonPreview(){
    ViewEditButton(onClick = {  })
}