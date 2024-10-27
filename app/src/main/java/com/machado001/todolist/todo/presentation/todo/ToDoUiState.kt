package com.machado001.todolist.todo.presentation.todo

import com.machado001.todolist.todo.domain.Note

data class ToDoUiState(
    val isLoading: Boolean = true,
    val notes: List<Note> = emptyList(),
)