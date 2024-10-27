package com.machado001.todolist.todo.domain

import com.machado001.todolist.core.domain.EmptyResult
import com.machado001.todolist.core.domain.LocalError
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    val notes: Flow<List<Note>>
    suspend fun updateNote(note: Note): EmptyResult<LocalError>
    suspend fun createNote(note: Note): EmptyResult<LocalError>
    suspend fun deleteNote(noteId: Int): EmptyResult<LocalError>
}