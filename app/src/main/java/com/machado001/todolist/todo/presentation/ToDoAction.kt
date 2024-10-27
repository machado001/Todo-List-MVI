package com.machado001.todolist.todo.presentation

import com.machado001.todolist.todo.domain.Note

sealed interface ToDoAction {
    data class OnUpdateNoteClick(val note: Note) : ToDoAction
    data class OnDeleteNoteClick(val note: Note) : ToDoAction
    data class OnCreateNoteClick(val note: Note) : ToDoAction
}