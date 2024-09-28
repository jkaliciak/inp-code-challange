package pl.inpost.recruitmenttask.ui.extensions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

fun <T> MutableStateFlow<T>.setState(newState: T.() -> T) {
    update { newState(requireNotNull(value) { "Missing initial data" }) }
}
