package com.machado001.todolist.todo.presentation

import com.machado001.todolist.todo.domain.Note

data class ToDoUiState(
    val isLoading: Boolean = false,
    val notes: List<Note> = emptyList(),
)