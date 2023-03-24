package com.topan.presentation.features.home

import androidx.lifecycle.MutableLiveData
import com.topan.presentation.base.BaseViewModel

/**
 * Created by Topan E on 25/03/23.
 */
class HomeViewModel : BaseViewModel() {
    val state = MutableLiveData<State>()

    sealed class Event {
        object OnCreate : Event()
    }

    sealed class State {
        data class ShowCategory(val categories: List<String>) : State()
    }

    fun onEvent(event: Event) = when (event) {
        Event.OnCreate -> onCreate()
    }

    private fun onCreate() {
        setState(State.ShowCategory(NEWS_CATEGORY))
    }

    private fun setState(_state: State) {
        state.value = _state
    }

    companion object {
        private val NEWS_CATEGORY = listOf(
            "General",
            "Business",
            "Entertainment",
            "Science",
            "Technology",
            "Health",
            "Sports"
        )
    }
}
