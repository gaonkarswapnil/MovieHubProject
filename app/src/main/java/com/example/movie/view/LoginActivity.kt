package com.example.movie.view

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movie.databinding.ActivityLoginBinding
import com.example.movie.model.CreateSessionLoginRequest
import com.example.movie.model.CreateSessionRequest
import com.example.movie.repository.apiimplementation.AuthenticationRepositoryImplementation
import com.example.movie.utils.broadcastreciever.InternetReciever
import com.example.movie.utils.sharedpreference.SessionManager
import com.example.movie.viewmodel.AuthenticationViewModel
import com.example.movie.viewmodel.AuthenticationViewModelFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var internetReceiver: InternetReciever

    private val authenticationViewModel: AuthenticationViewModel by viewModels {
        AuthenticationViewModelFactory(application, AuthenticationRepositoryImplementation())
    }

    private lateinit var userName: String
    private lateinit var userPassword: String

    private var requestToken: String = ""
    private lateinit var newRequestToken: String


    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val grantedToken = result.data?.getStringExtra("grantedToken")
                if (!grantedToken.isNullOrEmpty()) {
                    requestToken = grantedToken
                    Log.d("LoginActivity", "Granted Token: $requestToken")
                    Toast.makeText(this, "Token permission granted. Proceed to login.", Toast.LENGTH_SHORT).show()
                    userName = binding.etLoginScreenUserName.text.toString()
                    userPassword = binding.etLoginScreenPassword.text.toString()
                    proceedWithLogin(userName, userPassword)
                } else {
                    Toast.makeText(this, "Failed to retrieve granted token.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Token permission not granted.", Toast.LENGTH_SHORT).show()
            }
        }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        userName = binding.etLoginScreenUserName.text.toString()
        userPassword = binding.etLoginScreenPassword.text.toString()


        binding.btnLoginScreenButton.setOnClickListener {
            authenticationViewModel.getRequestToken().observe(this, Observer { token ->
                grantPermission(token.request_token)
            })
        }
    }

    private fun grantPermission(requestToken: String) {
        Intent(this@LoginActivity, WebActivity::class.java).also{
            Log.d("requestToken", "${requestToken}")
            it.putExtra("requestToken", requestToken)
            resultLauncher.launch(it)
        }
    }

    private fun proceedWithLogin(userName: String, userPassword: String) {
        if(userName!=null && userPassword!=null && requestToken!=null){
            val request = CreateSessionLoginRequest(
                password = userPassword,
                request_token = requestToken,
                username = userName
            )

            authenticationViewModel.createSessionViaLogin(request).observe(this, Observer { reqRes ->
                newRequestToken = reqRes.request_token
                authenticationViewModel.createSessionResponse(CreateSessionRequest(requestToken)).observe(this, Observer{
                    val sessionManager = SessionManager(this)
                    sessionManager.saveSessionId(it.session_id)

                    Intent(this, MovieActivity::class.java).also {
                        startActivity(it)
                        finish()
                    }
                })
            })
        }else{
            Toast.makeText(this@LoginActivity, "Enter field", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        internetReceiver = InternetReciever()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(internetReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(internetReceiver)
    }
}