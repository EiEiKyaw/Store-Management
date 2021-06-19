package org.android.project.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import org.android.project.storemanagement.R
import org.android.project.storemanagement.data.remote.request.LoginRequest
import org.android.project.storemanagement.util.Constants
import org.android.project.storemanagement.viewmodel.LoginViewModel

class LoginActivity : BaseActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        tvRegister.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
            finish()
        }

        btnLogin.setOnClickListener {
            val name = etName.text.toString()
            val password = etPassword.text.toString()

            when {
                name.isBlank() -> {
                    containerName.error = "User Name is required"
                }
                password.isBlank() -> {
                    containerPassword.error = "Password is required"
                }
                password.isNotBlank() -> {
                    if (password.length < 6)
                        containerPassword.error = "Must be 6 chars"
                }
                else -> {
                    val req = LoginRequest(
                        name = name,
                        password = password
                    )
                    loginViewModel.login(req)
                }
            }
        }

        loginViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        loginViewModel.error.observe(this, { error ->
            if (error.contains("incorrect"))
                showError(error)
            else
                Log.d("error", error)
        })

        loginViewModel.user.observe(this, { user ->
            val userId = user.userId ?: -1
            if (userId > 0) {
                val pref = getSharedPreferences(Constants.SHARE_PREF_NAME, MODE_PRIVATE)
                pref.edit()
                    .putInt(Constants.KEY_USER_ID, userId)
                    .apply()

                startActivity(MainActivity.newIntent(this))
                finish()
            }
        })
    }

    private fun showLoading() {
        loginProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loginProgressBar.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        loginProgressBar.visibility = View.GONE
        containerName.error = errorMessage
    }
}
