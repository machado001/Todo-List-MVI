package com.machado001.todolist.todo.presentation

sealed interface ToDoEvent {
    data class Error(val text: String) : ToDoEvent
    data object Success : ToDoEvent
    data object SuggestUpdateUserName : ToDoEvent

}