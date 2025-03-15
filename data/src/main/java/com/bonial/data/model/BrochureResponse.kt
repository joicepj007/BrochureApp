package com.bonial.data.model

import com.google.gson.annotations.SerializedName

data class BrochureResponse(
    val id: String? = null,
    @SerializedName("_embedded")
    val embedded: EmbeddedData? = null
)
