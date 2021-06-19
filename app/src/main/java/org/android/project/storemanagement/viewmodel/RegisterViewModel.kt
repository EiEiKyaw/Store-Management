package org.android.project.storemanagement.viewmodel

import androidx.lifecycle.MutableLiveData
import org.android.project.storemanagement.data.remote.RestClient
import org.android.project.storemanagement.data.remote.request.RegisterRequest
import org.android.project.storemanagement.model.LoginResponse
import org.android.project.storemanagement.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : BaseViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()
    val user = MutableLiveData<User>()

    fun register(req: RegisterRequest) {
        isLoading.postValue(true)
        RestClient.getApiService().register(req).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isLoading.postValue(false)
                error.postValue(t.message ?: "Unknown error")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                isLoading.postValue(false)
                if(response.isSuccessful){
                    response.body()?.let{body ->
                        error.postValue(body.error?.firstOrNull()?.errorMessage ?: "Unknown error")
                        user.postValue(body.data ?: User())
                    }
                }
            }

        })
    }
}