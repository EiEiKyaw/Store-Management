package org.android.project.storemanagement.data.remote.request

import com.google.gson.annotations.SerializedName

class RegisterRequest (
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?,
    @SerializedName("deviceToken") val deviceToken: String?,
)