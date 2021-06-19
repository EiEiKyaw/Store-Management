package org.android.project.storemanagement.model

import com.google.gson.annotations.SerializedName

class CustomerListResponse (
    @SerializedName("responseCode") val responseCode: Int? = -1,
    @SerializedName("responseMessage") val responseMessage: String?,
    @SerializedName("data") val data: List<Customer>?,
    @SerializedName("error") val error: List<ApiError>?,
)