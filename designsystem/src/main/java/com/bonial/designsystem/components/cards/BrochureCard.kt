package com.bonial.designsystem.components.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bonial.designsystem.R
import com.bonial.designsystem.components.labels.PremiumBadge
import com.bonial.designsystem.tokens.AspectRatios
import com.bonial.designsystem.tokens.Elevation
import com.bonial.designsystem.tokens.Spacing

/**
 * Standardized brochure card component
 *
 * @param retailerName Name of the retailer
 * @param imageUrl URL of the brochure image, null if no image available
 * @param isPremium Whether this is a premium brochure
 * @param distance Distance to the retailer in km, null if not available
 * @param onClick Callback when the card is clicked
 * @param modifier Optional modifier for customization
 */
@Composable
fun BrochureCard(
    retailerName: String,
    imageUrl: String?,
    isPremium: Boolean = false,
    distance: Double? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(Spacing.small)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = Elevation.medium),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Column {
            // Brochure image section with aspect ratio based on premium status
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(if (isPremium) AspectRatios.premium else AspectRatios.standard)
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Brochure for $retailerName",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        error = painterResource(id = R.drawable.placeholder_brochure)
                    )
                } else {
                    // Placeholder when no image is available
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.placeholder_brochure),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(48.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                // Premium badge if applicable
                if (isPremium) {
                    PremiumBadge(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(Spacing.small)
                    )
                }
            }

            // Retailer name
            Text(
                text = retailerName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.medium),
                style = MaterialTheme.typography.titleMedium
            )

            // Distance information (if available)
            distance?.let { dist ->
                Text(
                    text = "%.1f km".format(dist),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.medium, vertical = Spacing.xsmall),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(Spacing.small))
        }
    }
}