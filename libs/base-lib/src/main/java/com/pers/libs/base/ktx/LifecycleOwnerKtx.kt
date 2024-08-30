package com.pers.libs.base.ktx

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

inline fun <T> LifecycleOwner.liveDataObserve(
    liveData: LiveData<T>, crossinline action: (t: T) -> Unit
) {
    liveData.observe(this) { it?.let { t -> action(t) } }
}

fun ViewModel.launch(context: CoroutineContext = Dispatchers.Main, block: suspend CoroutineScope.() -> Unit) {
    viewModelScope.launch(context) {
        block()
    }
}