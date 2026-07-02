package com.example.nav3example.examples.adaptive

import org.junit.Assert.assertEquals
import org.junit.Test

class AdaptiveMailStateTest {
    @Test
    fun narrowWidthUsesSinglePaneEvenWhenDetailIsSelected() {
        val layout = mailLayoutFor(widthDp = 420, hasSelectedDetail = true)

        assertEquals(MailLayout.SinglePane, layout)
    }

    @Test
    fun wideWidthUsesListDetailWhenDetailIsSelected() {
        val layout = mailLayoutFor(widthDp = 900, hasSelectedDetail = true)

        assertEquals(MailLayout.ListDetail, layout)
    }

    @Test
    fun wideWidthStillUsesSinglePaneBeforeDetailIsSelected() {
        val layout = mailLayoutFor(widthDp = 900, hasSelectedDetail = false)

        assertEquals(MailLayout.SinglePane, layout)
    }
}
