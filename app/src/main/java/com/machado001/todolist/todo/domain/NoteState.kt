package com.machado001.todolist.todo.domain

sealed interface NoteState {
    data object Todo : NoteState
    data object InProgress : NoteState
    data object Done : NoteState
}

