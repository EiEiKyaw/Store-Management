package org.android.project.storemanagement.data.remote

import org.android.project.storemanagement.data.remote.request.LoginRequest
import org.android.project.storemanagement.data.remote.request.RegisterRequest
import org.android.project.storemanagement.model.CustomerListResponse
import org.android.project.storemanagement.model.LoginResponse
import org.android.project.storemanagement.model.ProductListResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.HEAD
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("user/login")
    fun login(@Body req: LoginRequest): Call<LoginResponse>

    @POST("user/register")
    fun register(@Body req: RegisterRequest): Call<LoginResponse>

    @POST("customer/list")
    fun getCustomerList(@Header("userId") userId: Int): Call<CustomerListResponse>

    @POST("product/list")
    fun getProductList(@Header("userId") userId: Int): Call<ProductListResponse>
}