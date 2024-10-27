package com.machado001.todolist.todo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.machado001.todolist.todo.data.MockData
import com.machado001.todolist.todo.data.repository.LocalNoteRepository
import com.machado001.todolist.todo.presentation.add.AddNoteScreenRoot
import com.machado001.todolist.todo.presentation.todo.ToDoListMviViewModel
import com.machado001.todolist.todo.presentation.todo.TodoListMviRoot
import kotlinx.serialization.Serializable


object NavigationManager {
    @Composable
    fun AppNav(modifier: Modifier = Modifier) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Routes.TodoListMVI) {
            composable<Routes.AddNote> {
                AddNoteScreenRoot()
            }
            composable<Routes.TodoListMVI> {
                val mockData = MockData()
                val repository = LocalNoteRepository(mockData)
                TodoListMviRoot(
                    vm = ToDoListMviViewModel(repository),
                    onGoingToAddNoteScreen = { navController.navigate(Routes.AddNote) })
            }
        }
    }

    object Routes {
        @Serializable
        object AddNote

        @Serializable
        object TodoListMVI
    }
}


