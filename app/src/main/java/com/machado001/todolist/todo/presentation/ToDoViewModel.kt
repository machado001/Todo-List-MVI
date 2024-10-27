package com.machado001.todolist.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machado001.todolist.todo.domain.Note
import com.machado001.todolist.todo.domain.NoteRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ToDoUiState())
    val uiState = _uiState.asStateFlow()


    private val eventChannel = Channel<ToDoEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            repository.notes
                .collectLatest { notes ->
                    _uiState.update {
                        it.copy(
                            notes = notes,
                            isLoading = false
                        )
                    }
                }
        }
    }

    fun onAction(action: ToDoAction) = when (action) {
        is ToDoAction.OnCreateNoteClick -> addNote(action.note)
        is ToDoAction.OnDeleteNoteClick -> deleteNote(action.note)
        is ToDoAction.OnUpdateNoteClick -> updateNote(action.note)
    }

    private fun updateNote(note: Note) = viewModelScope.launch {
        repository.updateNote(note)
        eventChannel.send(ToDoEvent.Success)
    }

    private fun deleteNote(note: Note) = viewModelScope.launch {
        repository.deleteNote(note)
        eventChannel.send(ToDoEvent.Success)
    }

    private fun addNote(note: Note) = viewModelScope.launch {
        repository.createNote(note)
        eventChannel.send(ToDoEvent.Success)
    }
}