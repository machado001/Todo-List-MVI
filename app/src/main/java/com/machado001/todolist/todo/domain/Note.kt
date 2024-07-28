package com.machado001.todolist.todo.domain

data class Note(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val state: NoteState
)
