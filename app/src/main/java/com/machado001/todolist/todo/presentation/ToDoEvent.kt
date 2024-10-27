package com.machado001.todolist.todo.presentation

import com.machado001.todolist.core.presentation.designsystem.ui.UiText

sealed interface ToDoEvent {
    data class Error(val uiText: UiText) : ToDoEvent
    data object Success : ToDoEvent
}