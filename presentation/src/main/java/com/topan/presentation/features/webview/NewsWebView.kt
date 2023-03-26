package com.topan.presentation.features.webview

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import androidx.appcompat.app.AppCompatActivity
import com.topan.domain.utils.emptyString
import com.topan.presentation.databinding.ActivityWebViewBinding

/**
 * Created by Topan E on 26/03/23.
 */
class NewsWebView: AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
    private val newsUrl by lazy {
        intent.getStringExtra(URL_KEY) ?: emptyString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViwBinding()
        renderWebView()
        setupToolbar()
    }

    private fun setupViwBinding() {
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupToolbar() {
        supportActionBar?.title = newsUrl
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.elevation = 0f
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderWebView() = with(binding.webView){
        webChromeClient = WebChromeClient()
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        loadUrl(newsUrl.validateUrl())
    }

    private fun String.validateUrl(): String {
        if (startsWith("http://")) return replace("http://", "https://")
        return this
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

    companion object {
        const val URL_KEY = ".urlKey"
    }
}
