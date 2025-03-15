package com.bonial.data.model

data class ExternalTracking(
    val impression: List<String> = emptyList(),
    val click: List<String> = emptyList()
)