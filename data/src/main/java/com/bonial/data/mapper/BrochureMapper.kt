package com.bonial.data.mapper

import com.bonial.common.constants.AppConstants.BROCHURE
import com.bonial.common.constants.AppConstants.BROCHURE_PREMIUM
import com.bonial.common.constants.AppConstants.SUPER_BANNER_CAROUSEL
import com.bonial.common.constants.AppConstants.UNKNOWN_RETAILER
import com.bonial.data.model.BrochureResponse
import com.bonial.data.model.ContentItem
import com.bonial.data.model.ContentPlacement
import com.bonial.data.model.ContentWrapper
import com.bonial.domain.model.BrochureImage
import com.bonial.domain.model.BrochureItem
import com.bonial.domain.model.Content
import com.bonial.domain.model.Retailer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class BrochureMapper @Inject constructor() {

    private val gson = Gson()

    fun mapToDomainModel(response: BrochureResponse): List<BrochureItem> {
        val brochureItems = mutableListOf<BrochureItem>()

        // Process all content placements in the embedded contents
        response.embedded?.contents?.forEach { contentPlacement ->
            // Extract content items from the placement
            val contentItems = extractContentItems(contentPlacement)

            // Process all extracted content items
            contentItems.forEach { contentItem ->
                // Create a brochure item from this content
                val brochureItem = mapContentItemToBrochureItem(
                    contentItem = contentItem,
                    contentType = contentPlacement.contentType ?: "unknown"
                )
                brochureItems.add(brochureItem)
            }
        }

        return brochureItems
    }

    private fun extractContentItems(contentPlacement: ContentPlacement): List<ContentItem> {
        return when (val content = contentPlacement.content) {
            // Case 1: Content is a direct ContentItem object (like in "brochure" type)
            is Map<*, *> -> {
                val contentItemJson = gson.toJson(content)
                listOf(gson.fromJson(contentItemJson, ContentItem::class.java))
            }
            // Case 2: Content is an array of ContentWrapper objects (like in "superBannerCarousel" type)
            is List<*> -> {
                val contentJson = gson.toJson(content)
                val typeToken = object : TypeToken<List<ContentWrapper>>() {}.type
                val contentWrappers: List<ContentWrapper> = gson.fromJson(contentJson, typeToken)
                contentWrappers.mapNotNull { it.content }
            }
            // Handle other cases
            else -> emptyList()
        }
    }

    private fun mapContentItemToBrochureItem(contentItem: ContentItem, contentType: String): BrochureItem {
        // Get or infer the ID
        val id = contentItem.id.toString()

        // Determine image URL (different fields based on content type)
        val imageUrl = contentItem.brochureImage ?: contentItem.imageUrl

        //domain model
        return BrochureItem(
            id = id,
            retailer = Retailer(
                id = contentItem.publisher?.id ?: "",
                name = contentItem.publisher?.name ?: UNKNOWN_RETAILER
            ),
            content = Content(
                contentType = contentType,
                brochureImage = imageUrl?.let { BrochureImage(url = it) }
            ),
            distance = contentItem.distance
        )
    }

    fun isBrochureContentType(contentType: String?): Boolean {
        return contentType == BROCHURE ||
                contentType == BROCHURE_PREMIUM ||
                contentType == SUPER_BANNER_CAROUSEL
    }
}