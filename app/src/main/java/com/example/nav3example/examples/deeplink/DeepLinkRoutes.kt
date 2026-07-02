package com.example.nav3example.examples.deeplink

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface DeepLinkRoute : NavKey {
    @Serializable
    data object Home : DeepLinkRoute

    @Serializable
    data object Catalog : DeepLinkRoute

    @Serializable
    data class Product(val id: Int) : DeepLinkRoute

    @Serializable
    data class Search(val query: String) : DeepLinkRoute
}

fun routesForDeepLink(rawLink: String): List<DeepLinkRoute> {
    val parts = rawLink
        .removePrefix("nav3://")
        .split("/")
        .filter { it.isNotBlank() }

    return when (parts.firstOrNull()) {
        "product" -> {
            val id = parts.getOrNull(1)?.toIntOrNull() ?: return listOf(DeepLinkRoute.Home)
            listOf(DeepLinkRoute.Home, DeepLinkRoute.Catalog, DeepLinkRoute.Product(id))
        }

        "search" -> {
            val query = parts.getOrNull(1) ?: return listOf(DeepLinkRoute.Home)
            listOf(DeepLinkRoute.Home, DeepLinkRoute.Search(query))
        }

        else -> listOf(DeepLinkRoute.Home)
    }
}
