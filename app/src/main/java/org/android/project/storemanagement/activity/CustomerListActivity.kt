package org.android.project.storemanagement.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_customer_list.*
import kotlinx.android.synthetic.main.activity_login.*
import org.android.project.storemanagement.R
import org.android.project.storemanagement.model.User
import org.android.project.storemanagement.util.Constants
import org.android.project.storemanagement.viewmodel.CustomerListViewModel

class CustomerListActivity: BaseActivity() {
    private lateinit var customerListViewModel: CustomerListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_list)
        customerListViewModel = ViewModelProvider(this)[CustomerListViewModel::class.java]

        val pref = getSharedPreferences(Constants.SHARE_PREF_NAME, Context.MODE_PRIVATE)
        val user = User(
            userId = pref.getInt(Constants.KEY_USER_ID, -1)
        )

        customerListViewModel.getCustomerList(user)

        customerListViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        customerListViewModel.error.observe(this, { error ->
            if (error.contains("incorrect"))
                showError(error)
            else
                Log.d("error", error)
        })

        customerListViewModel.customerList.observe(this, { user ->
            // todo: bind with adapter
        })
    }

    private fun showLoading() {
        customerListProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        customerListProgressBar.visibility = View.GONE
    }

    private fun showError(errorMessage: String) {
        customerListProgressBar.visibility = View.GONE
        containerName.error = errorMessage
    }
}