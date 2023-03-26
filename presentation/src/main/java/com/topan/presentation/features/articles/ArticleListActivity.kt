package com.topan.presentation.features.articles

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.topan.domain.model.ArticleItem
import com.topan.domain.utils.emptyString
import com.topan.presentation.databinding.ActivityArticlesBinding
import com.topan.presentation.features.articles.ArticleListViewModel.*
import com.topan.presentation.features.webview.NewsWebView
import com.topan.presentation.features.webview.NewsWebView.Companion.URL_KEY
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Topan E on 26/03/23.
 */
class ArticleListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticlesBinding
    private val viewModel: ArticleListViewModel by viewModel()
    private val articleListAdapter = ArticleListAdapter()
    private val source by lazy {
        intent.getStringExtra(SOURCE_KEY) ?: emptyString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViwBinding()
        setupToolbar()
        setupInitialEvent()
        setupRecyclerView()
    }

    private fun setupViwBinding() {
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0f
    }

    private fun setupInitialEvent() {
        viewModel.onEvent(Event.OnCreate(source))
        subscribeState()
    }

    private fun setupRecyclerView() {
        binding.articlesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.articlesRecyclerView.adapter = articleListAdapter
        binding.articlesRecyclerView.itemAnimator = null
        articleListAdapter.onItemClickListener {
            val intent = Intent(this, NewsWebView::class.java)
            intent.putExtra(URL_KEY, it)
            startActivity(intent)
        }
    }

    private fun subscribeState() = viewModel.state.observe(this) {
        when (it) {
            is State.ShowArticleList -> showArticleList(it.articleList)
            is State.ShowError -> showError(it.errorMessage)
            is State.ShowLoading -> showLoading(it.isLoading)
        }
    }

    private fun showArticleList(articleList: List<ArticleItem>) {
        binding.articlesRecyclerView.isVisible = true
        articleListAdapter.articleList = articleList
    }

    private fun showError(errorMessage: String) {
        //TODO: add error dialog
        Log.e("M-NEWS", "showError: $errorMessage")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.articlesRecyclerView.isVisible = false
        binding.loadingView.isVisible = isLoading
    }

    companion object {
        const val SOURCE_KEY = ".sourceKey"
    }
}
