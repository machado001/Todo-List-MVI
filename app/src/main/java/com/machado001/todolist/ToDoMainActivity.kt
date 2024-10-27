package com.machado001.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.machado001.todolist.core.presentation.designsystem.theme.ToDoListTheme
import com.machado001.todolist.todo.data.MockData
import com.machado001.todolist.todo.data.repository.LocalNoteRepository
import com.machado001.todolist.todo.domain.Note
import com.machado001.todolist.todo.domain.NoteState
import com.machado001.todolist.todo.presentation.ToDoUiState
import com.machado001.todolist.todo.presentation.ToDoViewModel

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val mockData = MockData()
        val repository = LocalNoteRepository(mockData)
        setContent {
            ToDoListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GreetingRoot(
                        modifier = Modifier.padding(innerPadding),
                        vm = ToDoViewModel(repository)
                    )
                }
            }
        }
    }
}

@Composable
fun GreetingRoot(modifier: Modifier = Modifier, vm: ToDoViewModel) {
    val state by vm.uiState.collectAsState()
    Greeting(state)
}

@Composable
fun Greeting(state: ToDoUiState) {
    LazyColumn {
        items(state.notes.size) { note ->
            NoteItem(state.notes[note])
        }
    }
}

@Composable
fun NoteItem(note: Note, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Column(modifier = modifier.padding(8.dp)) {
            Row {
                Text(note.title, modifier = modifier.weight(1.1f))
                Text(note.date, fontWeight = FontWeight.Light)
            }
            Row {
                Text(
                    note.description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = modifier.weight(1.1f)
                )
                Text(
                    note.state.toString(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoListTheme {
        Greeting(
            ToDoUiState(
                notes = listOf(
                    Note(
                        1,
                        "Title",
                        "Descriptikkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkon",
                        "23/03/2000",
                        NoteState.Todo
                    )
                )
            )
        )
    }
}