package com.topan.presentation.features.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayout.Tab
import com.topan.presentation.R
import com.topan.presentation.databinding.ActivityHomeBinding
import com.topan.presentation.features.home.HomeViewModel.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViwBinding()
        setupToolbar()
        setupInitialEvent()
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

    private fun subscribeState() = viewModel.state.observe(this) {
        when (it) {
            is State.ShowCategory -> showCategory(it.categories)
        }
    }

    private fun showCategory(category: List<String>) = with(binding.homeTabLayout) {
        category.forEachIndexed { position, title ->
            val tab = newTab()
            tab.text = title
            addTab(tab, position, position == 0)
        }
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: Tab?) {
                binding.textView.text = tab?.text
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
