package com.bonial.data.model

data class ContentItem(
    val id: String = "",
    val contentId: String? = null,
    val title: String? = null,
    val validFrom: String? = null,
    val validUntil: String? = null,
    val publishedFrom: String? = null,
    val publishedUntil: String? = null,
    val type: String? = null,
    val brochureImage: String? = null,
    val pageCount: Int? = null,
    val publisher: Publisher? = null,
    val contentBadges: List<Badge>? = null,
    val distance: Double? = null,
    val hideValidityDate: Boolean? = null,
    val closestStore: Store? = null,
    val clickUrl: String? = null,
    val imageUrl: String? = null
)