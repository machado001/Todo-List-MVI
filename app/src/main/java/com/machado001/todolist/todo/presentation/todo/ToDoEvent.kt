package com.machado001.todolist.todo.presentation.todo

import com.machado001.todolist.core.presentation.designsystem.ui.UiText

sealed interface ToDoEvent {
    data class Error(val uiText: UiText) : ToDoEvent
    data object Success : ToDoEvent
    data object OnGoingToAddNoteScreen : ToDoEvent
}