package com.machado001.todolist.todo.data

import com.machado001.todolist.core.domain.EmptyResult
import com.machado001.todolist.core.domain.LocalError
import com.machado001.todolist.core.domain.Result
import com.machado001.todolist.todo.domain.Note
import com.machado001.todolist.todo.domain.NoteDataSource
import com.machado001.todolist.todo.domain.NoteState
import io.bloco.faker.Faker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.coroutines.cancellation.CancellationException

//Simulating a database while i don't have a real one.
class MockData : NoteDataSource {
    override val notes: Flow<List<Note>> = flow {
        emit(noteMockDatabase)
    }

    override suspend fun createNote(note: Note): Result<Note, LocalError> = try {
        faker.run {
            val n = Note(
                id = note.id,
                title = note.title,
                description = note.description,
                date = note.date,
                state = NoteState.Todo
            )
            noteMockDatabase.add(n)
            Result.Success(n)
        }
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.Error(LocalError.UNKNOWN_ERROR)
    }

    override suspend fun updateNote(note: Note): Result<Note, LocalError> = try {
        noteMockDatabase.first { it.id == note.id }
            .run {
                Result.Success(copy(id = id, title = title, description = description, date = date))
            }
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.Error(LocalError.UNKNOWN_ERROR)
    }

    override suspend fun deleteNote(note: Note): EmptyResult<LocalError> = try {
        noteMockDatabase.remove(note)
        Result.Success(Unit)
    } catch (e: Exception) {
        if (e is CancellationException) throw e
        Result.Error(LocalError.UNKNOWN_ERROR)
    }
}

private val faker = Faker()

private val noteMockDatabase = buildList {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    repeat(10) {
        val possibleNoteStates = listOf(NoteState.Todo, NoteState.Done, NoteState.InProgress)
        with(faker) {
            add(
                Note(
                    id = number.digit().toInt(),
                    title = lorem.characters(7),
                    description = lorem.characters(30),
                    date = dateFormat.format(date.forward()),
                    state = possibleNoteStates.random()
                )
            )
        }
    }

}.toMutableList()