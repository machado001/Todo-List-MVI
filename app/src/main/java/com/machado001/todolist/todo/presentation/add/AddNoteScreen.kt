package com.machado001.todolist.todo.presentation.add

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.machado001.todolist.core.presentation.designsystem.theme.ToDoListTheme

@Composable
fun AddNoteScreenRoot() {

}

@Composable
fun AddNoteScreen(state: AddNoteScreenState, action: (AddNoteScreenAction) -> Unit) {
    Dialog(onDismissRequest = { action(AddNoteScreenAction.OnCancelNoteCreationClick) }) {
    }
}

@Preview
@Composable
private fun AddNoteScreenPreview() {
    ToDoListTheme {
        AddNoteScreen(state = AddNoteScreenState()) { AddNoteScreenAction.OnAddClickButton }
    }
}