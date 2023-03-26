package com.topan.presentation.features.webview

import android.annotation.SuppressLint
import android.os.Bundle
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
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun renderWebView() = with(binding.webView){
        webChromeClient = WebChromeClient()
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.mediaPlaybackRequiresUserGesture = false
        loadUrl(newsUrl)
    }

    companion object {
        const val URL_KEY = ".urlKey"
    }
}
