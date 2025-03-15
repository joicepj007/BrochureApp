package com.bonial.domain.model

data class BrochureItem(
    val id: String,
    val retailer: Retailer,
    val content: Content,
    val distance: Double? = null
)

data class Retailer(
    val id: String,
    val name: String
)

data class Content(
    val contentType: String,
    val brochureImage: BrochureImage? = null
)

data class BrochureImage(
    val url: String
)