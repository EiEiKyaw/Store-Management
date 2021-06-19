package org.android.project.storemanagement.model

import com.google.gson.annotations.SerializedName

class Customer (
    @SerializedName("id") val id: Int? = -1,
    @SerializedName("name") val name: String?,
    @SerializedName("phoneNo") val phoneNo: String?,
)