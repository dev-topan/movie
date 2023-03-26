package com.topan.presentation.features.articles

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.topan.domain.model.ArticleItem
import com.topan.domain.utils.emptyString
import com.topan.presentation.R
import com.topan.presentation.databinding.ActivityArticlesBinding
import com.topan.presentation.features.articles.ArticleListViewModel.*
import com.topan.presentation.features.webview.NewsWebView
import com.topan.presentation.features.webview.NewsWebView.Companion.URL_KEY
import com.topan.presentation.utils.Dialog
import com.topan.presentation.utils.onLoadMore
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
        supportActionBar?.setHomeButtonEnabled(true)
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
        binding.articlesRecyclerView.onLoadMore { viewModel.onEvent(Event.OnLoadMore) }
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
            is State.ShowMiniLoading -> binding.miniLoadingView.isVisible = it.isLoading
        }
    }

    private fun showArticleList(articleList: List<ArticleItem>) {
        binding.articlesRecyclerView.isVisible = true
        articleListAdapter.submitList(articleList)
    }

    private fun showError(errorMessage: String) {
        Dialog.show(this, errorMessage)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.articlesRecyclerView.isVisible = false
        binding.loadingView.isVisible = isLoading
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val menuItem = menu?.findItem(R.id.search_view)
        val searchView = menuItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_for_sources)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.onEvent(Event.OnSearch(p0 ?: emptyString()))
                return  false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == emptyString()) viewModel.onEvent(Event.OnCreate(source))
                return true
            }

        })
        return true
    }

    companion object {
        const val SOURCE_KEY = ".sourceKey"
    }
}
