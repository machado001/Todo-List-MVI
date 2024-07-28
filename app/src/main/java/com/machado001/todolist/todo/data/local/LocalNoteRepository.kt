package com.machado001.todolist.todo.data.local

import com.machado001.todolist.todo.domain.Note
import com.machado001.todolist.todo.domain.NoteDataSource
import com.machado001.todolist.todo.domain.NoteRepository
import kotlinx.coroutines.flow.Flow

class LocalNoteRepository(
    private val dataSource: NoteDataSource
) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> = dataSource.notes
    override suspend fun updateNote(note: Note): Flow<Note> = dataSource.updateNote(note)
    override suspend fun getNote(note: Note): Flow<Note> = dataSource.getNote(note)
    override suspend fun deleteNote(note: Note): Flow<Note> = dataSource.deleteNote(note)
}