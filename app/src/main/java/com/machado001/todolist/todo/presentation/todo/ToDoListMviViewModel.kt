package com.machado001.todolist.todo.presentation.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.machado001.todolist.R
import com.machado001.todolist.core.domain.Result
import com.machado001.todolist.core.presentation.designsystem.ui.UiText
import com.machado001.todolist.todo.domain.Note
import com.machado001.todolist.todo.domain.NoteRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ToDoListMviViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ToDoUiState(isLoading = true))
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<ToDoEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.notes
                .collectLatest { notesList ->
                    _uiState.update { state ->
                        state.copy(notes = notesList, isLoading = false)
                    }
                }
        }
    }

    fun onAction(action: ToDoAction) = when (action) {
        is ToDoAction.OnCreateNoteClick -> goToAddNoteScreen()
        is ToDoAction.OnDeleteNoteClick -> deleteNote(action.noteId)
        is ToDoAction.OnUpdateNoteClick -> updateNote(action.note)
    }

    private fun updateNote(note: Note) = viewModelScope.launch {
        when (repository.updateNote(note)) {
            is Result.Error -> ToDoEvent.Error(UiText.StringResource(R.string.error_updating_note))
            is Result.Success -> ToDoEvent.Success
        }.also { event ->
            eventChannel.send(event)
        }
    }

    private fun deleteNote(noteId: Int) = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (repository.deleteNote(noteId)) {
            is Result.Error -> ToDoEvent.Error(UiText.StringResource(R.string.error_deleting_note))
            is Result.Success -> ToDoEvent.Success
        }.also { event ->
            eventChannel.send(event)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun goToAddNoteScreen() = viewModelScope.launch {
        eventChannel.send(ToDoEvent.OnGoingToAddNoteScreen)
    }
}