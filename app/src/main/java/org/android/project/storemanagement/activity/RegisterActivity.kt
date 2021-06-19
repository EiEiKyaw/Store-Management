package org.android.project.storemanagement.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.containerName
import kotlinx.android.synthetic.main.activity_login.containerPassword
import kotlinx.android.synthetic.main.activity_login.etName
import kotlinx.android.synthetic.main.activity_login.etPassword
import kotlinx.android.synthetic.main.activity_register.*
import org.android.project.storemanagement.R
import org.android.project.storemanagement.data.remote.request.RegisterRequest
import org.android.project.storemanagement.util.Constants
import org.android.project.storemanagement.viewmodel.RegisterViewModel

class RegisterActivity : BaseActivity() {
    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        tvLogin.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
            finish()
        }

        btnRegister.setOnClickListener {
            val name = etName.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            when {
                name.isBlank() -> {
                    containerName.error = "User Name is required"
                }
                password
                    .isBlank() -> {
                    containerPassword.error = "Password is required"
                }
                password.isNotBlank() -> {
                    if (password.length < 6)
                        containerPassword.error = "Must be 6 chars"
                }
                confirmPassword.isBlank() -> {
                    containerPassword.error = "Password is required"
                }
                confirmPassword.isNotBlank() -> {
                    if (confirmPassword.length < 6)
                        containerPassword.error = "Must be 6 chars"
                }
                confirmPassword.isNotBlank() && password.isNotBlank() -> {
                    if (password != confirmPassword)
                        containerPassword.error = "Passwords do not match"
                }
                else -> {
                    val req = RegisterRequest(
                        name = name,
                        password = password,
                        deviceToken = "sample-token"
                    )
                    registerViewModel.register(req)
                }
            }
        }

        registerViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        registerViewModel.error.observe(this, { error ->
            if (error.contains("duplicate"))
                showError(error)
            else
                Log.d("error", error)
        })

        registerViewModel.user.observe(this, { user ->
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
        registerProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        registerProgressBar.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        registerProgressBar.visibility = View.GONE
        containerName.error = errorMessage
    }
}
