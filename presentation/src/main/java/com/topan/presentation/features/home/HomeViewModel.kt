package com.topan.presentation.features.home

import androidx.lifecycle.MutableLiveData
import com.topan.domain.model.SourceItem
import com.topan.domain.model.SourceList
import com.topan.domain.usecases.GetSourceListUseCase
import com.topan.domain.utils.ResultCall
import com.topan.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Topan E on 25/03/23.
 */
class HomeViewModel(
    private val getSourcesUseCase: GetSourceListUseCase
) : BaseViewModel() {
    val state = MutableLiveData<State>()
    private var sourceList = listOf<SourceItem>()

    sealed class Event {
        object OnCreate : Event()
        data class OnGetSourceList(val category: String) : Event()
        data class OnSearch(val query: String) : Event()
    }

    sealed class State {
        data class ShowCategory(val categories: List<String>) : State()
        data class ShowLoading(val isLoading: Boolean) : State()
        data class ShowError(val errorMessage: String) : State()
        data class ShowSourceList(val sourceList: List<SourceItem>) : State()
    }

    fun onEvent(event: Event) {
        when (event) {
            Event.OnCreate -> onCreate()
            is Event.OnGetSourceList -> getSourceList(event.category)
            is Event.OnSearch -> onSearch(event.query)
        }
    }

    private fun onCreate() = launch {
        setupCategory()
        getSourceList(NEWS_CATEGORY.first())
    }

    private fun setupCategory() {
        setState(State.ShowCategory(NEWS_CATEGORY))
    }

    private fun getSourceList(category: String) = launch {
        setState(State.ShowLoading(true))
        when (val result = getSourcesUseCase.invoke(category)) {
            is ResultCall.Failed -> setState(State.ShowError(result.errorMessage))
            is ResultCall.Success -> onSuccessGetSourceList(result.data)
        }
        setState(State.ShowLoading(false))
    }

    private fun onSuccessGetSourceList(response: SourceList) {
        if (response.status != "ok") return setState(State.ShowError("Error Occurred"))
        sourceList = response.sources ?: emptyList()
        setState(State.ShowSourceList(sourceList))
    }

    private fun onSearch(query: String) = launch {
        if (query.isEmpty()) return@launch setState(State.ShowSourceList(sourceList))
        val result = sourceList.filter {
            it.name?.contains(query, true) == true || it.description?.contains(query, true) == true
        }
        setState(State.ShowSourceList(result))
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
