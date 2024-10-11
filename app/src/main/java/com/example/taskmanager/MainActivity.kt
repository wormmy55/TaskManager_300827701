package com.example.taskmanager

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign.Companion.Center
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskmanager.data.Task
import com.example.taskmanager.data.TaskRoutes
import com.example.taskmanager.data.allTasks
import com.example.taskmanager.data.setDueDate
import com.example.taskmanager.data.setDueTime
import com.example.taskmanager.ui.theme.TaskManagerTheme
import com.example.taskmanager.views.CreateTaskScreen
import com.example.taskmanager.views.HomeScreen
import com.example.taskmanager.views.ViewEditScreen
import com.example.taskmanager.views.globalIndex
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskManagerTheme {
                MainScaffold()
            }
        }
    }
/*
    private fun computeWindowSizeClasses(){
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val width = metrics.bounds.width()
        val height = metrics.bounds.height()
        val density = resources.displayMetrics.density
        val windowSizeClass = WindowSizeClass.compute(width/density, height/density)

        val widthWindowSizeClass = windowSizeClass.windowWidthSizeClass
        val heightWindowSizeClass = windowSizeClass.windowHeightSizeClass
    }*/
}
/*
@Composable
fun AppWindow(
    windowSizeClass : WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
){
    val showTopAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT
}*/

//This is a test for adding tasks
fun test(){allTasks.add(Task(title = "New Task", dueDate= setDueDate, dueTime=setDueTime, content="This is a new Task"))}

@Composable
fun MainScaffold(
    //windowSizeClass : WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
){
    //val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    //val showTopAppBar = windowSizeClass.windowHeightSizeClass != WindowHeightSizeClass.COMPACT
    Scaffold{innerPadding ->
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = TaskRoutes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            //this Route leeds to the Home Screen
            composable(TaskRoutes.Home.route){
                HomeScreen(
                    //This is where the routes are set
                    onCreateButtonClick = { navController.navigate(TaskRoutes.CreateTask.route) },
                    onViewEditButtonClick = { navController.navigate(TaskRoutes.ViewEdit.route) },
                )
            }
            //This Route leeds to the ViewEdit Screen
            composable(TaskRoutes.ViewEdit.route){
                ViewEditScreen(
                    onBackButtonClick = { navController.navigate(TaskRoutes.Home.route) },
                    onSaveButtonClick = { navController.navigate(TaskRoutes.Home.route) },
                    //This sets the listIndex to the global index
                    // more about this in ViewEditTasks.kt
                    listIndex = globalIndex,
                )
            }
            //This Route leads to the CreateTask Screen
            composable(TaskRoutes.CreateTask.route) {
                CreateTaskScreen(
                    onBackButtonClick = { navController.navigate(TaskRoutes.Home.route) },
                    onSaveButtonClick = { navController.navigate(TaskRoutes.Home.route) },
                )
            }
        }
    }
}
//This is a function for the date picker
@Composable
fun DatePicker(
    value: String,
    onValueChange: (String) -> Unit
){
    val context = LocalContext.current

    val year: Int
    val month: Int
    val day: Int

    val cal = Calendar.getInstance()
    year = cal.get(Calendar.YEAR)
    month = cal.get(Calendar.MONTH)
    day = cal.get(Calendar.DAY_OF_MONTH)

    var date by remember { mutableStateOf(value) }

    val datePickerDialog = DatePickerDialog(
        context,
        {_, year : Int, month : Int, day : Int ->
            date = "$day/${month+1}/$year"
        }, year, month, day
    )
    onValueChange(date)
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally
    ){
        Button(
            onClick = { datePickerDialog.show() },
            //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ){
            Text(
                text = "Date: $date",
                fontSize = 30.sp,
                textAlign = Center
            )
        }
    }
}
//This is a function for the time picker
@Composable
fun TimePicker(
    value: String,
    onValueChange: (String) -> Unit
){
    //This is where the context is set
    val context = LocalContext.current
    //This is where the calendar variables are set
    val cal = Calendar.getInstance()
    val hour = cal.get(Calendar.HOUR_OF_DAY)
    val minute = cal.get(Calendar.MINUTE)
    //This is where the time is set
    var time by remember { mutableStateOf(value) }
    //This is where the time picker dialog is set
    val timePickerDialog = TimePickerDialog(
        context,
        {_, hour : Int, minute : Int ->
            time = "%02d:%02d".format(hour, minute)
        }, hour, minute, true
    )
    //This is where the time is updated
    onValueChange(time)
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally,
    ){
        Button(
            onClick = { timePickerDialog.show() },
            //colors = ButtonDefaults.buttonColors(backgroundColor = Color.Blue)
        ){
            Text(
                text = "Time: $time",
                fontSize = 30.sp
            )
        }

    }
}
//This previews the Date Picker function
@Preview
@Composable
fun DatePickerPreview(){
    DatePicker (
        value = setDueDate,
        onValueChange = { setDueDate = it})
}
//This previews the Time Picker function
@Preview
@Composable
fun TimePickerPreview(){
    TimePicker (
        value = setDueTime,
        onValueChange = { setDueTime = it}
    )
}
//This is a preview of the main scaffold
@Preview (showBackground = true)
@Composable
fun MainScaffoldPreview(){
    TaskManagerTheme{
        MainScaffold()
    }
}