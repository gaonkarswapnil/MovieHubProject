package com.example.movie.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movie.R
import com.example.movie.databinding.ActivityWebBinding

class WebActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        setupWebView()

        val requestToken = intent.getStringExtra("requestToken")

        requestToken?.let {
            val authUrl = "https://www.themoviedb.org/authenticate/$requestToken"
            binding.webView.loadUrl(authUrl)
        }


    }

    private fun setupWebView() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url.toString()
                Log.d("WebActivity", "URL: $url")
                if (url.contains("allow")) {
                    val grantedToken = extractTokenFromUrl(url)
                    val resultIntent = Intent().apply {
                        putExtra("grantedToken", grantedToken)
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                    return true
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }

        binding.webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }
    }

    private fun extractTokenFromUrl(url: String): String {
        val tokenStart = url.substringAfter("/authenticate/")
        return tokenStart.substringBefore("/allow")
    }
}