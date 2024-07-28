package com.machado001.todolist.todo.domain

import com.machado001.todolist.core.domain.LocalError
import com.machado001.todolist.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface NoteDataSource {
    val notes: Flow<List<Note>>
    suspend fun updateNote(note: Note): Result<Flow<Note>, LocalError>
    suspend fun getNote(note: Note): Result<Flow<Note>, LocalError>
    suspend fun deleteNote(note: Note): Result<Flow<Note>, LocalError>
}
