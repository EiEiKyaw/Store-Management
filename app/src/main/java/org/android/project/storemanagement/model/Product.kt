package org.android.project.storemanagement.model

import com.google.gson.annotations.SerializedName

class Product (
    @SerializedName("id") val id: Int? = -1,
    @SerializedName("name") val name: String?,
    @SerializedName("price") val price: String?,
)