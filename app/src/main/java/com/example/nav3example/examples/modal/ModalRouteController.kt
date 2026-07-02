package com.example.nav3example.examples.modal

import androidx.navigation3.runtime.NavKey
import com.example.nav3example.examples.common.popIfNotRoot
import kotlinx.serialization.Serializable

@Serializable
sealed interface ModalRoute : NavKey {
    @Serializable
    data object Gallery : ModalRoute

    @Serializable
    data object Filter : ModalRoute

    @Serializable
    data class PhotoPreview(val id: Int) : ModalRoute

    @Serializable
    data class DeleteConfirm(val id: Int) : ModalRoute
}

class ModalRouteController(
    private val backStack: MutableList<ModalRoute>
) {
    fun openFilter() {
        backStack.add(ModalRoute.Filter)
    }

    fun openPhoto(id: Int) {
        backStack.add(ModalRoute.PhotoPreview(id))
    }

    fun requestDelete(id: Int) {
        backStack.add(ModalRoute.DeleteConfirm(id))
    }

    fun confirmDelete() {
        val confirm = backStack.lastOrNull() as? ModalRoute.DeleteConfirm ?: return
        backStack.removeAll { route ->
            route == ModalRoute.PhotoPreview(confirm.id) || route == confirm
        }
    }

    fun closeTop() {
        backStack.popIfNotRoot()
    }
}
