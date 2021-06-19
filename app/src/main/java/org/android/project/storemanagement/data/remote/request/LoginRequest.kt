package org.android.project.storemanagement.data.remote.request

import com.google.gson.annotations.SerializedName

class LoginRequest (
    @SerializedName("name") val name: String?,
    @SerializedName("password") val password: String?
)