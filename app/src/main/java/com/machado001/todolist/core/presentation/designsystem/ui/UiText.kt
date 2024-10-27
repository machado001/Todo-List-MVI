package com.machado001.todolist.core.presentation.designsystem.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


/**
 * Class to handle gracefully string resources in ViewModel.
 * The instances [StringResource] and [DynamicString] would be
 * sent by the ViewModel, for example in an error case.
 *
 * The [asString] methods would be used in UI layer, in the instances sent
 * by ViewModel.
 */
sealed interface UiText {
    class StringResource(
        @StringRes val id: Int,
        val args: Array<Any> = arrayOf()
    ) : UiText

    data class DynamicString(val value: String) : UiText

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> stringResource(id, args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(id, args)
        }
    }
}