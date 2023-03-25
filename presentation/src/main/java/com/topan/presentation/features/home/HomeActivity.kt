package com.topan.presentation.features.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import com.topan.domain.model.SourceItem
import com.topan.presentation.R
import com.topan.presentation.databinding.ActivityHomeBinding
import com.topan.presentation.features.home.HomeViewModel.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()
    private val sourceListAdapter = SourceListAdapter()

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
        //TODO: add error dialog
        Log.e("M-NEWS", "showError: $errorMessage", )
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
                tab?.text?.let { viewModel.onEvent(Event.OnGetSourceList(it.toString())) }
            }

            override fun onTabUnselected(tab: Tab?) = Unit

            override fun onTabReselected(tab: Tab?) = Unit

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
}
