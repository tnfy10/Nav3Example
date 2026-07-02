package com.example.nav3example.examples.deeplink

import org.junit.Assert.assertEquals
import org.junit.Test

class DeepLinkRouteParserTest {
    @Test
    fun productLinkBuildsCatalogToProductBackStack() {
        val routes = routesForDeepLink("nav3://product/42")

        assertEquals(
            listOf(
                DeepLinkRoute.Home,
                DeepLinkRoute.Catalog,
                DeepLinkRoute.Product(id = 42)
            ),
            routes
        )
    }

    @Test
    fun searchLinkKeepsQueryAsRouteArgument() {
        val routes = routesForDeepLink("nav3://search/navigation")

        assertEquals(
            listOf(
                DeepLinkRoute.Home,
                DeepLinkRoute.Search(query = "navigation")
            ),
            routes
        )
    }

    @Test
    fun unknownLinkFallsBackToHome() {
        val routes = routesForDeepLink("nav3://unknown/value")

        assertEquals(listOf(DeepLinkRoute.Home), routes)
    }
}
