package com.example.student01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.student01.ui.theme.Student01Theme

data class Student(val name: String, val id: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Student01Theme {
                StudentApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentApp() {
    var viewModel = StudentViewModel()
    var data = remember { mutableStateListOf<Student>() }
    var StudentViewModel = remember {
        mutableStateOf(false)
    }
    var newStudentName by remember {
        mutableStateOf("")
    }
    var newStudentId by remember {
        mutableStateOf("")
    }
    if (StudentViewModel.value) {
        InputDialog(
            itemName = newStudentName,
            itemId = newStudentId,
            onCancel = {
                StudentViewModel.value = false
            },
            onAddButtonClick = { newItem, newItemId ->
                val newStudent = Student(newItem, newItemId)
                data.add(newStudent)
                StudentViewModel.value = false
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student App") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.DarkGray,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { StudentViewModel.value = true },
                containerColor = Color.DarkGray,
                contentColor = Color.White) {
                Icon(Icons.Filled.Add, "Add New Student")
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            items(data) { student ->
                Text("Student Name: ${student.name}, ID: ${student.id}")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InputDialog(
    itemName: String,
    itemId: String,
    onCancel: () -> Unit,
    onAddButtonClick: (String, String) -> Unit
) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        var textValue by remember {
            mutableStateOf(itemName)
        }
        var studentIdValue by remember {
            mutableStateOf(itemId)
        }
        Card(
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                TextField(
                    value = textValue,
                    onValueChange = { textValue = it },
                    label = { Text("Student Name") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = studentIdValue,
                    onValueChange = { studentIdValue = it },
                    label = { Text("Student ID") }
                )
                TextButton(onClick = { onAddButtonClick(textValue, studentIdValue) }) {
                    Text("Add")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Student01Theme {
        StudentApp()
    }
}