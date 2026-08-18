package com.krishisevak.app

import com.krishisevak.app.data.local.datastore.DataStoreManager
import com.krishisevak.app.utils.LocalSmartAiEngine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class AiQuotaLimiterTest {

    @Test
    fun testDailyLimitConstantIsTwo() {
        assertEquals(2, DataStoreManager.DAILY_AI_QUERY_LIMIT)
    }

    @Test
    fun testDailyLimitExhaustedMessage() {
        val enMsg = com.krishisevak.app.utils.AppStrings.get("daily_query_limit_exhausted", "en")
        assertEquals("You have used your daily query limit.", enMsg)

        val hiMsg = com.krishisevak.app.utils.AppStrings.get("daily_query_limit_exhausted", "hi")
        assertEquals("आपने अपनी दैनिक प्रश्न सीमा समाप्त कर ली है।", hiMsg)
    }

    @Test
    fun testSimulatedDailyRateLimiter() {
        // Simulating the atomic rate limiter logic implemented in DataStoreManager
        val maxLimit = DataStoreManager.DAILY_AI_QUERY_LIMIT
        var currentDate = "2026-08-18"
        var currentCount = 0

        fun recordUsage(today: String): Boolean {
            if (currentDate != today) {
                currentDate = today
                currentCount = 0
            }
            return if (currentCount < maxLimit) {
                currentCount++
                true
            } else {
                false
            }
        }

        // Day 1: 2026-08-18
        assertTrue("1st query should be allowed", recordUsage("2026-08-18"))
        assertEquals(1, currentCount)

        assertTrue("2nd query should be allowed", recordUsage("2026-08-18"))
        assertEquals(2, currentCount)

        assertFalse("3rd query on the same day should be blocked (limit reached)", recordUsage("2026-08-18"))
        assertEquals(2, currentCount)

        assertFalse("4th query on the same day should also be blocked", recordUsage("2026-08-18"))
        assertEquals(2, currentCount)

        // Day 2: 2026-08-19 (automatic reset)
        assertTrue("1st query on new day should be allowed after automatic reset", recordUsage("2026-08-19"))
        assertEquals(1, currentCount)

        assertTrue("2nd query on new day should be allowed", recordUsage("2026-08-19"))
        assertEquals(2, currentCount)

        assertFalse("3rd query on new day should be blocked", recordUsage("2026-08-19"))
    }
}
