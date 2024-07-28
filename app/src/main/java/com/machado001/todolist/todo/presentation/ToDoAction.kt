package com.machado001.todolist.todo.presentation

sealed interface ToDoAction {
    data object OnUpdateNoteClick : ToDoAction
    data object OnDeleteNoteClick : ToDoAction
    data object OnAddNoteClick : ToDoAction
    data object OnUpdateUserNameClick : ToDoAction
    data object OnRefreshPush : ToDoAction
}