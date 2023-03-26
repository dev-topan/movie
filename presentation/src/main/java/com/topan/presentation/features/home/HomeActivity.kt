package com.topan.presentation.features.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import com.topan.domain.model.SourceItem
import com.topan.domain.utils.emptyString
import com.topan.presentation.R
import com.topan.presentation.databinding.ActivityHomeBinding
import com.topan.presentation.features.articles.ArticleListActivity
import com.topan.presentation.features.home.HomeViewModel.*
import com.topan.presentation.utils.Dialog
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val sourceListAdapter = SourceListAdapter()
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViwBinding()
        setupToolbar()
        setupInitialEvent()
        setupRecyclerView()
    }

    private fun setupViwBinding() {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        supportActionBar?.elevation = 0f
    }

    private fun setupInitialEvent() {
        viewModel.onEvent(Event.OnCreate)
        subscribeState()
    }

    private fun setupRecyclerView() {
        binding.sourceRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.sourceRecyclerView.adapter = sourceListAdapter
        binding.sourceRecyclerView.itemAnimator = null
        sourceListAdapter.onItemClickListener {
            val intent = Intent(this, ArticleListActivity::class.java)
            intent.putExtra(ArticleListActivity.SOURCE_KEY, it)
            startActivity(intent)
        }
    }

    private fun subscribeState() = viewModel.state.observe(this) {
        when (it) {
            is State.ShowCategory -> showCategory(it.categories)
            is State.ShowError -> showError(it.errorMessage)
            is State.ShowLoading -> showLoading(it.isLoading)
            is State.ShowSourceList -> showSourceList(it.sourceList)
        }
    }

    private fun showError(errorMessage: String) {
        Dialog.show(this, errorMessage)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) binding.sourceRecyclerView.isVisible = false
        binding.loadingView.isVisible = isLoading
    }

    private fun showSourceList(sourceList: List<SourceItem>) {
        binding.sourceRecyclerView.isVisible = true
        sourceListAdapter.sourceList = sourceList
    }

    private fun showCategory(category: List<String>) = with(binding.homeTabLayout) {
        category.forEachIndexed { position, title ->
            val tab = newTab()
            tab.text = title
            addTab(tab, position, position == 0)
        }
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                if (::searchView.isInitialized) searchView.setQuery(emptyString(), false)
                tab?.text?.let { viewModel.onEvent(Event.OnGetSourceList(it.toString())) }
            }

            override fun onTabUnselected(tab: Tab?) = Unit

            override fun onTabReselected(tab: Tab?) = Unit

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val menuItem = menu?.findItem(R.id.search_view)
        searchView = menuItem?.actionView as SearchView
        searchView.queryHint = getString(R.string.search_for_sources)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.onEvent(Event.OnSearch(p0 ?: emptyString()))
                return  false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 == emptyString()) viewModel.onEvent(Event.OnSearch(p0))
                return true
            }

        })
        return true
    }
}
