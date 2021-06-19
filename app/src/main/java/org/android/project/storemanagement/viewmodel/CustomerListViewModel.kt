package org.android.project.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import org.android.project.storemanagement.data.remote.RestClient
import org.android.project.storemanagement.model.Customer
import org.android.project.storemanagement.model.CustomerListResponse
import org.android.project.storemanagement.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerListViewModel : BaseViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val customerList = MutableLiveData<List<Customer>>()

    fun getCustomerList(user: User) {
        RestClient.getApiService()
            .getCustomerList(user.userId ?: -1)
            .enqueue(object : Callback<CustomerListResponse> {
                override fun onResponse(
                    call: Call<CustomerListResponse>,
                    response: Response<CustomerListResponse>
                ) {
                    isLoading.postValue(false)
                    if(response.isSuccessful){
                        response.body()?.let{body ->
                            error.postValue(body.error?.firstOrNull()?.errorMessage ?: "Unknown error")
                            customerList.postValue(body.data ?: listOf())
                        }
                    }
                }

                override fun onFailure(call: Call<CustomerListResponse>, t: Throwable) {
                    isLoading.postValue(false)
                    error.postValue(t.message ?: "Unknown error")
                }

            })
    }
}