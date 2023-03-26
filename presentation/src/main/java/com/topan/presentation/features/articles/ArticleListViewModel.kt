package com.topan.presentation.features.articles

import androidx.lifecycle.MutableLiveData
import com.topan.domain.model.ArticleItem
import com.topan.domain.model.ArticleList
import com.topan.domain.usecases.GetArticleListUseCase
import com.topan.domain.utils.ResultCall
import com.topan.domain.utils.emptyInt
import com.topan.domain.utils.emptyString
import com.topan.presentation.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Topan E on 26/03/23.
 */
class ArticleListViewModel(
    private val getArticleListUseCase: GetArticleListUseCase
) : BaseViewModel() {
    val state = MutableLiveData<State>()
    private var source: String = emptyString()
    private var totalResult: Int = emptyInt()
    private var page: Int = DEFAULT_PAGE

    sealed class Event {
        data class OnCreate(val source: String) : Event()
        object OnLoadMore : Event()
    }

    sealed class State {
        data class ShowLoading(val isLoading: Boolean) : State()
        data class ShowArticleList(val articleList: List<ArticleItem>) : State()
        data class ShowError(val errorMessage: String) : State()
    }

    fun onEvent(event: Event) = when (event) {
        is Event.OnCreate -> onCreate(event.source)
        Event.OnLoadMore -> onLoadMore()
    }

    private fun onCreate(_source: String) {
        source = _source
        getArticleList(source, page)
    }

    private fun onLoadMore() {
        getArticleList(source, page)
    }

    private fun getArticleList(source: String, page: Int) = launch {
        setState(State.ShowLoading(true))
        when (val result = getArticleListUseCase.invoke(
            source = source,
            pageSize = DEFAULT_PAGE_SIZE,
            page = page
        )) {
            is ResultCall.Failed -> setState(State.ShowError(result.errorMessage))
            is ResultCall.Success -> onSuccessGetArticles(result.data)
        }
        setState(State.ShowLoading(false))
    }

    private fun onSuccessGetArticles(response: ArticleList) {
        if (response.articles.isNullOrEmpty()) return setState(
            State.ShowError("No articles available")
        )
        if (response.status != "ok") return setState(
            State.ShowError("Internal server error")
        )
        page += page
        totalResult = response.totalResults ?: emptyInt()
        setState(State.ShowArticleList(response.articles ?: emptyList()))
    }

    private fun setState(_state: State) {
        state.value = _state
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
        private const val DEFAULT_PAGE = 1
    }
}
