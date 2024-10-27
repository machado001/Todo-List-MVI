package com.machado001.todolist.core.presentation.designsystem.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.machado001.todolist.todo.domain.NoteState

@Composable
fun mapColorToState(state: NoteState): Color = when (state) {
    NoteState.Done -> Color.Green
    NoteState.InProgress -> Color.Yellow
    NoteState.Todo -> Color.Magenta
}