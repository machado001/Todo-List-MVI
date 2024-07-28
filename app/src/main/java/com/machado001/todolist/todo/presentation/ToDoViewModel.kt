package com.machado001.todolist.todo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val _uiState = MutableStateFlow(ToDoState())
    val uiState = _uiState.asStateFlow()


    private val eventChannel = Channel<ToDoEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.getAllNotes()
                .collectLatest { notes -> _uiState.update { it.copy(notes = notes) } }
        }
    }

    fun onAction(action: ToDoAction) {
        when (action) {
            ToDoAction.OnAddNoteClick -> addNote()
            ToDoAction.OnDeleteNoteClick -> deleteNote()
            ToDoAction.OnRefreshPush -> getNotesOnRefresh()
            ToDoAction.OnUpdateNoteClick -> updateNote()
            ToDoAction.OnUpdateUserNameClick -> Unit
        }
    }

    private fun updateNote() = Unit
    private fun deleteNote() = Unit
    private fun addNote() = Unit
    private fun getNotesOnRefresh() = repository.getAllNotes()
    private fun updateUserName() = viewModelScope.launch(coroutineName) {
        takeIf { _uiState.value.userName.isEmpty() }
            ?.let { eventChannel.send(ToDoEvent.SuggestUpdateUserName) }
    }

    companion object {
        private const val UPDATE_USER_NAME_COROUTINE_NAME = "UpdateNameSc"
        val coroutineName = CoroutineName(UPDATE_USER_NAME_COROUTINE_NAME)
    }
}