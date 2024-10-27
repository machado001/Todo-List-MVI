package com.machado001.todolist.todo.domain

import com.machado001.todolist.core.domain.EmptyResult
import com.machado001.todolist.core.domain.LocalError
import com.machado001.todolist.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    val notes: Flow<List<Note>>
    suspend fun createNote(note: Note): Result<Note, LocalError>
    suspend fun updateNote(note: Note): Result<Note, LocalError>
    suspend fun deleteNote(noteId: Int): EmptyResult<LocalError>
}
