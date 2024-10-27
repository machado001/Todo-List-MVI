package com.machado001.todolist.todo.presentation.add

sealed interface AddNoteScreenAction {
    data object OnAddClickButton : AddNoteScreenAction
    data object OnCancelNoteCreationClick : AddNoteScreenAction {
    }
}
