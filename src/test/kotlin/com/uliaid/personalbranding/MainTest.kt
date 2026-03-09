package com.uliaid.personalbranding

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MainTest {

    @Test
    fun `rendered page contains key sections`() {
        val html = renderLandingPage(PersonalBrandingProfile.default())

        assertTrue(html.contains("Tentang Saya"))
        assertTrue(html.contains("Kekuatan Utama"))
        assertTrue(html.contains("Fokus Saat Ini"))
        assertTrue(html.contains("Uli Aid"))
    }
}
