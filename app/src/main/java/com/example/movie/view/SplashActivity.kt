package com.example.movie.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movie.R
import com.example.movie.databinding.ActivitySplashBinding
import com.example.movie.utils.sharedpreference.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch {
            delay(500)

            val sessionManager = SessionManager(this@SplashActivity)
            val sessionID = sessionManager.getSessionId()

            if(sessionID!=null){
                Intent(this@SplashActivity, MovieActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }else{
                Intent(this@SplashActivity, LoginActivity::class.java).also {
                    startActivity(it)
                    finish()
                }
            }
        }



    }
}