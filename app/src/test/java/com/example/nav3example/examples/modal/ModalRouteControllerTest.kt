package com.example.nav3example.examples.modal

import org.junit.Assert.assertEquals
import org.junit.Test

class ModalRouteControllerTest {
    @Test
    fun openingFilterAddsFilterRouteAboveGallery() {
        val backStack = mutableListOf<ModalRoute>(ModalRoute.Gallery)
        val controller = ModalRouteController(backStack)

        controller.openFilter()

        assertEquals(listOf(ModalRoute.Gallery, ModalRoute.Filter), backStack)
    }

    @Test
    fun confirmingDeleteRemovesDialogAndSelectedPhoto() {
        val backStack = mutableListOf<ModalRoute>(
            ModalRoute.Gallery,
            ModalRoute.PhotoPreview(7),
            ModalRoute.DeleteConfirm(7)
        )
        val controller = ModalRouteController(backStack)

        controller.confirmDelete()

        assertEquals(listOf(ModalRoute.Gallery), backStack)
    }

    @Test
    fun cancellingModalKeepsGalleryRoot() {
        val backStack = mutableListOf<ModalRoute>(ModalRoute.Gallery)
        val controller = ModalRouteController(backStack)

        controller.closeTop()

        assertEquals(listOf(ModalRoute.Gallery), backStack)
    }
}
