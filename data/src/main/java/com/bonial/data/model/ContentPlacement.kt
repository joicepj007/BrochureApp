package com.bonial.data.model

data class ContentPlacement(
    val placement: String? = null,
    val adFormat: String? = null,
    val contentFormatSource: String? = null,
    val contentType: String? = null,
    // This can be either a single object or an array of objects
    val content: Any? = null,
    val externalTracking: ExternalTracking? = null
)