package org.android.project.storemanagement.model

import com.google.gson.annotations.SerializedName

class ProductListResponse (
    @SerializedName("responseCode") val responseCode: Int? = -1,
    @SerializedName("responseMessage") val responseMessage: String?,
    @SerializedName("data") val data: List<Product>?,
    @SerializedName("error") val error: List<ApiError>?,
)