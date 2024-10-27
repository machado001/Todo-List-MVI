package com.machado001.todolist.todo.data.repository

import com.machado001.todolist.core.domain.EmptyResult
import com.machado001.todolist.core.domain.LocalError
import com.machado001.todolist.core.domain.Result
import com.machado001.todolist.core.domain.asEmptyDataResult
import com.machado001.todolist.todo.domain.Note
import com.machado001.todolist.todo.domain.NoteDataSource
import com.machado001.todolist.todo.domain.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class LocalNoteRepository(
    private val dataSource: NoteDataSource
) : NoteRepository {

    override val notes: Flow<List<Note>> = dataSource.notes.flowOn(Dispatchers.IO)

    override suspend fun updateNote(note: Note): EmptyResult<LocalError> =
        withContext(Dispatchers.IO) {
            when (val result = dataSource.updateNote(note)) {
                is Result.Success -> result.asEmptyDataResult()
                is Result.Error -> {
                    when (result.error) {
                        LocalError.UNKNOWN_ERROR -> result.asEmptyDataResult()
                    }
                }
            }
        }

    override suspend fun createNote(note: Note): EmptyResult<LocalError> =
        withContext(Dispatchers.IO) {
            when (val result = dataSource.createNote(note)) {
                is Result.Error -> {
                    when (result.error) {
                        LocalError.UNKNOWN_ERROR -> result.asEmptyDataResult()
                    }
                }

                is Result.Success -> {
                    result.asEmptyDataResult()
                }
            }
        }

    override suspend fun deleteNote(noteId: Int): EmptyResult<LocalError> =
        withContext(Dispatchers.IO) {
            dataSource.deleteNote(noteId).asEmptyDataResult()
        }

}
