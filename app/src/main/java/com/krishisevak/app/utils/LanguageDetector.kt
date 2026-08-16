package com.krishisevak.app.utils

object LanguageDetector {

    data class DetectedLanguage(
        val code: String,
        val name: String,
        val nativeName: String
    )

    fun detectLanguage(text: String): DetectedLanguage {
        if (text.isBlank()) {
            return DetectedLanguage("en", "English", "English")
        }

        var devanagariCount = 0
        var bengaliCount = 0
        var teluguCount = 0
        var tamilCount = 0
        var kannadaCount = 0
        var malayalamCount = 0
        var gujaratiCount = 0
        var gurmukhiCount = 0
        var odiaCount = 0

        for (ch in text) {
            when (ch.code) {
                in 0x0900..0x097F -> devanagariCount++ // Hindi / Marathi
                in 0x0980..0x09FF -> bengaliCount++   // Bengali
                in 0x0C00..0x0C7F -> teluguCount++    // Telugu
                in 0x0B80..0x0BFF -> tamilCount++     // Tamil
                in 0x0C80..0x0CFF -> kannadaCount++   // Kannada
                in 0x0D00..0x0D7F -> malayalamCount++ // Malayalam
                in 0x0A80..0x0AFF -> gujaratiCount++  // Gujarati
                in 0x0A00..0x0A7F -> gurmukhiCount++  // Punjabi
                in 0x0B00..0x0B7F -> odiaCount++      // Odia
            }
        }

        val maxIndic = maxOf(
            devanagariCount, bengaliCount, teluguCount, tamilCount,
            kannadaCount, malayalamCount, gujaratiCount, gurmukhiCount, odiaCount
        )

        if (maxIndic > 0) {
            return when (maxIndic) {
                bengaliCount -> DetectedLanguage("bn", "Bengali", "বাংলা")
                teluguCount -> DetectedLanguage("te", "Telugu", "తెలుగు")
                tamilCount -> DetectedLanguage("ta", "Tamil", "தமிழ்")
                kannadaCount -> DetectedLanguage("kn", "Kannada", "ಕನ್ನಡ")
                malayalamCount -> DetectedLanguage("ml", "Malayalam", "മലയാളം")
                gujaratiCount -> DetectedLanguage("gu", "Gujarati", "ગુજરાતી")
                gurmukhiCount -> DetectedLanguage("pa", "Punjabi", "ਪੰਜਾਬੀ")
                odiaCount -> DetectedLanguage("or", "Odia", "ଓଡ଼ିଆ")
                devanagariCount -> {
                    val lower = text.lowercase()
                    // Check for typical Marathi marker words
                    if (lower.contains("आहे") || lower.contains("करा") || lower.contains("शेती") ||
                        lower.contains("पिका") || lower.contains("पाणी") || lower.contains("खत") ||
                        lower.contains("नाही") || lower.contains("होते") || lower.contains("झाले")) {
                        DetectedLanguage("mr", "Marathi", "मराठी")
                    } else {
                        DetectedLanguage("hi", "Hindi", "हिन्दी")
                    }
                }
                else -> DetectedLanguage("hi", "Hindi", "हिन्दी")
            }
        }

        // Check for phonetic transliterated queries or English
        val lower = text.lowercase()
        return when {
            lower.contains("kaise") || lower.contains("kya") || lower.contains("kisan") || lower.contains("fasal") || lower.contains("pani") ->
                DetectedLanguage("hi", "Hindi", "हिन्दी")
            lower.contains("kasa") || lower.contains("sheti") || lower.contains("pik") || lower.contains("ahe") ->
                DetectedLanguage("mr", "Marathi", "मराठी")
            lower.contains("kemon") || lower.contains("chash") || lower.contains("fashol") ->
                DetectedLanguage("bn", "Bengali", "বাংলা")
            lower.contains("ela") || lower.contains("pantalu") || lower.contains("vyavasayam") ->
                DetectedLanguage("te", "Telugu", "తెలుగు")
            lower.contains("eppadi") || lower.contains("vivisayam") || lower.contains("payir") ->
                DetectedLanguage("ta", "Tamil", "தமிழ்")
            else ->
                DetectedLanguage("en", "English", "English")
        }
    }
}
