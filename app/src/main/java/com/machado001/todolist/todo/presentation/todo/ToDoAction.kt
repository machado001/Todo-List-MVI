package com.machado001.todolist.todo.presentation.todo

import com.machado001.todolist.todo.domain.Note

sealed interface ToDoAction {
    data class OnUpdateNoteClick(val note: Note) : ToDoAction
    data class OnDeleteNoteClick(val noteId: Int) : ToDoAction
    data class OnCreateNoteClick(val note: Note) : ToDoAction
}