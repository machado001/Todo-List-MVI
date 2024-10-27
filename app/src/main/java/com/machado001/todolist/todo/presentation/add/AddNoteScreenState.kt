package com.machado001.todolist.todo.presentation.add

import com.machado001.todolist.todo.domain.NoteState

data class AddNoteScreenState(
    val title: String = "Oi",
    val description: String = "Oi",
    val noteState: NoteState = NoteState.Todo
)
