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
    private var query: String = emptyString()
    private val articleList = mutableListOf<ArticleItem>()
    private var maximumTotalResult = emptyInt()
    private var totalResult: Int = emptyInt()
    private var page: Int = DEFAULT_PAGE
    private var isLoading = false

    sealed class Event {
        data class OnCreate(val source: String) : Event()
        object OnLoadMore : Event()
        data class OnSearch(val query: String) : Event()
    }

    sealed class State {
        data class ShowLoading(val isLoading: Boolean) : State()
        data class ShowMiniLoading(val isLoading: Boolean) : State()
        data class ShowArticleList(val articleList: List<ArticleItem>) : State()
        data class ShowError(val errorMessage: String) : State()
    }

    fun onEvent(event: Event) {
        when (event) {
            is Event.OnCreate -> onCreate(event.source)
            Event.OnLoadMore -> onLoadMore()
            is Event.OnSearch -> onSearch(event.query)
        }
    }

    private fun onCreate(_source: String) = launch {
        page = DEFAULT_PAGE
        source = _source
        totalResult = emptyInt()
        maximumTotalResult = emptyInt()
        query = emptyString()
        articleList.clear()
        setState(State.ShowArticleList(articleList.toList()))
        getArticleList()
    }

    private fun onLoadMore() {
        if (totalResult >= maximumTotalResult) return
        if (isLoading) return
        getArticleList()
    }

    private fun onSearch(_query: String) {
        page = DEFAULT_PAGE
        totalResult = emptyInt()
        maximumTotalResult = emptyInt()
        query = _query
        articleList.clear()
        searchArticle()
    }

    private fun searchArticle() = launch {
        setState(State.ShowLoading(true))
        when (val result = getArticleListUseCase.invoke(
            source = source,
            query = query,
            pageSize = DEFAULT_PAGE_SIZE,
            page = page
        )) {
            is ResultCall.Failed -> setState(State.ShowError(result.errorMessage))
            is ResultCall.Success -> onSuccessGetArticles(result.data)
        }
        setState(State.ShowLoading(false))
    }

    private fun getArticleList() = launch {
        isLoading = true
        setState(State.ShowMiniLoading(isLoading))
        when (val result = getArticleListUseCase.invoke(
            source = source,
            query = query,
            pageSize = DEFAULT_PAGE_SIZE,
            page = page
        )) {
            is ResultCall.Failed -> setState(State.ShowError(result.errorMessage))
            is ResultCall.Success -> onSuccessGetArticles(result.data)
        }
        isLoading = false
        setState(State.ShowMiniLoading(isLoading))
    }

    private fun onSuccessGetArticles(response: ArticleList) {
        if (response.status != "ok") return setState(
            State.ShowError("Internal server error")
        )
        page++
        maximumTotalResult = response.totalResults ?: emptyInt()
        totalResult += response.articles?.size ?: emptyInt()
        articleList.addAll(response.articles ?: emptyList())
        setState(State.ShowArticleList(articleList.toList()))
    }

    private fun setState(_state: State) {
        state.value = _state
    }

    companion object {
        private const val DEFAULT_PAGE_SIZE = 10
        private const val DEFAULT_PAGE = 1
    }
}
