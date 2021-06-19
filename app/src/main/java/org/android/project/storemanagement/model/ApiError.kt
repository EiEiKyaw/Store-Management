package org.android.project.storemanagement.model

import com.google.gson.annotations.SerializedName

class ApiError (
    @SerializedName("fieldCode") val fieldCode: Int? = -1,
    @SerializedName("errorMessage") val errorMessage: String?,
)