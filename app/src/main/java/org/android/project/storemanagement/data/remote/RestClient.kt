package org.android.project.storemanagement.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestClient {
    companion object{
        fun getApiService():ApiService{
            return getRetrofit().create(ApiService::class.java)
        }

        fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://talentnest.com.mm/product-api/")
                .client(getClient())
                .addConverterFactory(getConverter())
                .build()
        }

        fun getConverter(): GsonConverterFactory {
            val gson = GsonBuilder().create()
            return GsonConverterFactory.create(gson)
        }

        fun getClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }
    }
}