package com.machado001.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.machado001.todolist.core.presentation.designsystem.theme.ToDoListTheme
import com.machado001.todolist.todo.data.MockData
import com.machado001.todolist.todo.data.repository.LocalNoteRepository
import com.machado001.todolist.todo.presentation.todo.GreetingRoot
import com.machado001.todolist.todo.presentation.todo.ToDoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoListTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val mockData = MockData()
                    val repository = LocalNoteRepository(mockData)
                    GreetingRoot(
                        modifier = Modifier.padding(innerPadding),
                        vm = ToDoViewModel(repository)
                    )
                }
            }
        }
    }
}