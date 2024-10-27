package com.machado001.todolist.todo.presentation.todo

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.machado001.todolist.core.presentation.designsystem.theme.ToDoListTheme
import com.machado001.todolist.core.presentation.designsystem.ui.ObserveAsEvents
import com.machado001.todolist.core.presentation.designsystem.ui.mapColorToState
import com.machado001.todolist.todo.domain.Note
import com.machado001.todolist.todo.domain.NoteState

@Composable
fun GreetingRoot(modifier: Modifier = Modifier, vm: ToDoViewModel) {
    val context = LocalContext.current
    val state by vm.uiState.collectAsStateWithLifecycle()
    ObserveAsEvents(vm.events) { event ->
        when (event) {
            is ToDoEvent.Error -> Toast.makeText(context, "Error adding note", Toast.LENGTH_SHORT)
                .show()

            is ToDoEvent.Success -> Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    Greeting(state = state,
        onAction = { action ->
            vm.onAction(action)
        }
    )
}

@Composable
fun Greeting(state: ToDoUiState, onAction: (ToDoAction) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(androidx.compose.foundation.layout.WindowInsets.systemBars)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center)
                .size(30.dp))
        } else {
            LazyColumn {
                items(state.notes.size) { i ->
                    NoteItem(
                        note = state.notes[i],
                        isLoading = false,
                        onDelete = { onAction(ToDoAction.OnDeleteNoteClick(i)) })
                    if (i != state.notes.size - 1) {
                        HorizontalDivider()
                    }
                }
            }
            FloatingActionButton(
                onClick = { },
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Icon(Icons.Default.Add, "Add note")
            }

        }
    }
}

@Composable
fun NoteItem(
    modifier: Modifier = Modifier,
    note: Note,
    isLoading: Boolean = false,
    onDelete: () -> Unit
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(),
        onClick = { onDelete() }
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Column(modifier = modifier.padding(8.dp)) {
                Row {
                    Text(note.title, modifier = modifier.weight(1.1f))
                    Text(
                        note.state.toString(),
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Light,
                        modifier = modifier.background(mapColorToState(note.state)),
                        color = Color.Black
                    )
                }
                Text(
                    note.description,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light,
                    maxLines = 2,
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
                    ),
                    Note(
                        2,
                        "Title",
                        "Descriptikkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkon",
                        "23/03/2000",
                        NoteState.Todo
                    )
                )
            )
        ) {}
    }
}