package com.krishisevak.app

import com.krishisevak.app.data.remote.mandi.RealMandiDirectory
import com.krishisevak.app.utils.LocationHelper
import com.krishisevak.app.utils.UserLocationDetails
import org.junit.Assert.*
import org.junit.Test

class RealMandiDirectoryTest {

    @Test
    fun testAllPresetLocationsResolveToAuthenticMandis() {
        for (preset in LocationHelper.PRESET_LOCATIONS) {
            val (marketName, records) = RealMandiDirectory.getMandiDataForLocation(preset)

            assertNotNull("Market name must not be null for ${preset.cityName}", marketName)
            assertTrue("Market name must contain 'APMC' or 'Mandi' or 'Market'",
                marketName.contains("APMC", ignoreCase = true) ||
                marketName.contains("Mandi", ignoreCase = true) ||
                marketName.contains("Market", ignoreCase = true)
            )
            assertTrue("Records list must not be empty for ${preset.cityName}", records.isNotEmpty())

            for (record in records) {
                assertNotNull("Commodity name must not be null", record.commodity)
                assertTrue("Commodity name must not be blank", record.commodity?.isNotBlank() == true)

                assertNotNull("Modal price must not be null", record.modalPrice)
                val modalInt = record.modalPrice?.toIntOrNull()
                assertNotNull("Modal price must be a valid integer", modalInt)
                assertTrue("Modal price must be greater than 0", modalInt!! > 0)

                assertNotNull("Min price must not be null", record.minPrice)
                assertNotNull("Max price must not be null", record.maxPrice)
                assertTrue("Min price must be <= Max price",
                    (record.minPrice?.toIntOrNull() ?: 0) <= (record.maxPrice?.toIntOrNull() ?: 0)
                )

                assertEquals("State must match target state or nearest verified market state",
                    record.state, preset.stateName
                )
            }
        }
    }

    @Test
    fun testSpecificMajorMandiResolutions() {
        // 1. Nashik -> Lasalgaon APMC Mandi
        val nashikLoc = UserLocationDetails("Nashik", "Nashik", "Maharashtra", 19.9975, 73.7898)
        val (nashikMarket, nashikRecords) = RealMandiDirectory.getMandiDataForLocation(nashikLoc)
        assertEquals("Lasalgaon APMC Mandi", nashikMarket)
        assertTrue(nashikRecords.any { it.commodity?.contains("Onion", ignoreCase = true) == true })

        // 2. Ludhiana -> Khanna APMC Grain Market
        val ludhianaLoc = UserLocationDetails("Ludhiana", "Ludhiana", "Punjab", 30.9010, 75.8573)
        val (ludhianaMarket, ludhianaRecords) = RealMandiDirectory.getMandiDataForLocation(ludhianaLoc)
        assertEquals("Khanna APMC Grain Market", ludhianaMarket)
        assertTrue(ludhianaRecords.any { it.commodity?.contains("Wheat", ignoreCase = true) == true })
        assertTrue(ludhianaRecords.any { it.commodity?.contains("Basmati", ignoreCase = true) == true })

        // 3. Guntur -> Guntur Mirchi Yard APMC
        val gunturLoc = UserLocationDetails("Guntur", "Guntur", "Andhra Pradesh", 16.3067, 80.4365)
        val (gunturMarket, gunturRecords) = RealMandiDirectory.getMandiDataForLocation(gunturLoc)
        assertEquals("Guntur Mirchi Yard APMC", gunturMarket)
        assertTrue(gunturRecords.any { it.commodity?.contains("Chilli", ignoreCase = true) == true })

        // 4. Kolkata -> Koley Market Wholesale APMC
        val kolkataLoc = UserLocationDetails("Kolkata", "Kolkata", "West Bengal", 22.5726, 88.3639)
        val (kolkataMarket, kolkataRecords) = RealMandiDirectory.getMandiDataForLocation(kolkataLoc)
        assertEquals("Koley Market Wholesale APMC", kolkataMarket)
        assertTrue(kolkataRecords.any { it.commodity?.contains("Potato", ignoreCase = true) == true })
        assertTrue(kolkataRecords.any { it.commodity?.contains("Jute", ignoreCase = true) == true })

        // 5. Indore -> Choithram Krishi Upaj Mandi
        val indoreLoc = UserLocationDetails("Indore", "Indore", "Madhya Pradesh", 22.7196, 75.8577)
        val (indoreMarket, indoreRecords) = RealMandiDirectory.getMandiDataForLocation(indoreLoc)
        assertEquals("Choithram Krishi Upaj Mandi", indoreMarket)
        assertTrue(indoreRecords.any { it.commodity?.contains("Soyabean", ignoreCase = true) == true })
        assertTrue(indoreRecords.any { it.commodity?.contains("Wheat", ignoreCase = true) == true })
        // 6. Cooch Behar -> Cooch Behar APMC Regulated Market (Local distance < 10 km)
        val coochBeharLoc = UserLocationDetails("Cooch Behar", "Jalpaiguri Division", "West Bengal", 26.3239, 89.4510)
        val (coochMarket, coochRecords) = RealMandiDirectory.getMandiDataForLocation(coochBeharLoc)
        assertEquals("Cooch Behar APMC Regulated Market", coochMarket)
        assertTrue(coochRecords.any { it.commodity?.contains("Jute", ignoreCase = true) == true })
        assertTrue("Distance to local mandi must be within 10 km", (coochRecords.first().distanceKm ?: 0) <= 10)
    }

    @Test
    fun testArbitraryLocationFindsNearestInState() {
        // Location in Maharashtra with unknown district -> finds nearest verified Maharashtra APMC
        val unknownMhLoc = UserLocationDetails("UnknownTown", "Dharashiv", "Maharashtra", 18.1750, 76.0400)
        val (marketName, records) = RealMandiDirectory.getMandiDataForLocation(unknownMhLoc)
        assertNotNull(marketName)
        assertEquals("Maharashtra", records.first().state)
        assertTrue("Distance to local mandi must be within 15 km", (records.first().distanceKm ?: 0) <= 15)
    }

    @Test
    fun testTranslationsAcrossAll11Languages() {
        val languages = listOf("hi", "bn", "mr", "te", "ta", "kn", "ml", "gu", "pa", "or", "en")
        val sampleCommodities = listOf("Raw Jute (TD-5 / Mesta)", "Minikit Rice (Aman Paddy)", "Tomato", "Potato (Jyoti)", "Ginger", "Pointed Gourd (Potal)")

        for (lang in languages) {
            for (crop in sampleCommodities) {
                val translated = com.krishisevak.app.data.engine.MandiTranslations.getTranslatedName(crop, lang)
                assertNotNull(translated)
                assertTrue("Translation for $crop in $lang must not be blank", translated.isNotBlank())
            }
        }
    }
}
