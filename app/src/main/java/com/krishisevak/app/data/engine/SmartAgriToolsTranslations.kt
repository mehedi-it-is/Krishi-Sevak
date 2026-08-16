package com.krishisevak.app.data.engine

/**
 * Master Localization and Translation Engine for all 6 Smart Agri Tools:
 * 1. Soil & Fertilizer Dosage Engine
 * 2. Crop Recommendation & Suitability Ranker
 * 3. Crop Doctor & Pathogen Risk Forecaster
 * 4. Crop Almanac & 12-Month Seasonal Calendar
 * 5. Learn & Farming Best Practices Guides
 * 6. Farm Insights Analytics
 *
 * Supports all 11 languages:
 * Hindi (hi), Bengali (bn), Marathi (mr), Telugu (te), Tamil (ta),
 * Kannada (kn), Malayalam (ml), Gujarati (gu), Punjabi (pa), Odia (or), English (en).
 */
object SmartAgriToolsTranslations {

    // =========================================================================
    // 1. CROP NAMES & SOIL TYPES TRANSLATION
    // =========================================================================
    fun getCropName(englishName: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return englishName
        val lower = englishName.lowercase().trim()
        val map = cropNameMap[code] ?: return englishName
        return map.entries.firstOrNull { lower.contains(it.key.lowercase()) }?.value ?: englishName
    }

    fun getSoilTypeName(englishSoil: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return englishSoil
        return soilTypeMap[code]?.get(englishSoil) ?: englishSoil
    }

    fun getSeasonName(englishSeason: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return englishSeason
        return seasonMap[code]?.entries?.firstOrNull { englishSeason.contains(it.key, ignoreCase = true) }?.value ?: englishSeason
    }

    fun getWaterLevelName(englishWater: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return englishWater
        return waterLevelMap[code]?.entries?.firstOrNull { englishWater.contains(it.key, ignoreCase = true) }?.value ?: englishWater
    }

    private val cropNameMap: Map<String, Map<String, String>> = mapOf(
        "hi" to mapOf(
            "rice" to "धान / चावल", "paddy" to "धान", "wheat" to "गेहूं", "cotton" to "कपास",
            "sugarcane" to "गन्ना", "maize" to "मक्का", "corn" to "मक्का", "potato" to "आलू",
            "tomato" to "टमाटर", "mustard" to "सरसों", "soyabean" to "सोयाबीन", "gram" to "चना",
            "chickpea" to "चना", "groundnut" to "मूंगफली", "bajra" to "बाजरा", "jowar" to "ज्वार",
            "onion" to "प्याज", "garlic" to "लहसुन", "ginger" to "अदरक", "turmeric" to "हल्दी"
        ),
        "bn" to mapOf(
            "rice" to "ধান / চাল", "paddy" to "ধান", "wheat" to "গম", "cotton" to "তুলা",
            "sugarcane" to "আখ", "maize" to "ভুট্টা", "corn" to "ভুট্টা", "potato" to "আলু",
            "tomato" to "টমেটো", "mustard" to "সরিষা", "soyabean" to "সয়াবিন", "gram" to "ছোলা",
            "chickpea" to "ছোলা", "groundnut" to "চিনাবাদাম", "bajra" to "বাজরা", "jowar" to "জোয়ার",
            "onion" to "পেঁয়াজ", "garlic" to "রসুন", "ginger" to "আদা", "turmeric" to "হলুদ"
        ),
        "mr" to mapOf(
            "rice" to "भात / तांदूळ", "paddy" to "भात", "wheat" to "गहू", "cotton" to "कापूस",
            "sugarcane" to "ऊस", "maize" to "मका", "corn" to "मका", "potato" to "बटाटा",
            "tomato" to "टोमॅटो", "mustard" to "मोहरी", "soyabean" to "सोयाबीन", "gram" to "हरभरा / चना",
            "chickpea" to "हरभरा", "groundnut" to "भुईमूग", "bajra" to "बाजरी", "jowar" to "ज्वारी",
            "onion" to "कांदा", "garlic" to "लसूण", "ginger" to "आले", "turmeric" to "हळद"
        ),
        "te" to mapOf(
            "rice" to "వరి / బియ్యం", "paddy" to "వరి", "wheat" to "గోధుమలు", "cotton" to "ప్రత్తి",
            "sugarcane" to "చెరకు", "maize" to "మొక్కజొన్న", "corn" to "మొక్కజొన్న", "potato" to "బంగాళాదుంప",
            "tomato" to "టమోటా", "mustard" to "ఆవాలు", "soyabean" to "సోయాబీన్", "gram" to "శనగలు",
            "chickpea" to "శనగలు", "groundnut" to "వేరుశనగ", "bajra" to "సజ్జలు", "jowar" to "జొన్నలు",
            "onion" to "ఉల్లిపాయ", "garlic" to "వెల్లుల్లి", "ginger" to "అల్లం", "turmeric" to "పసుపు"
        ),
        "ta" to mapOf(
            "rice" to "நெல் / அரிசி", "paddy" to "நெல்", "wheat" to "கோதுமை", "cotton" to "பருத்தி",
            "sugarcane" to "கரும்பு", "maize" to "மக்காச்சோளம்", "corn" to "மக்காச்சோளம்", "potato" to "உருளைக்கிழங்கு",
            "tomato" to "தக்காளி", "mustard" to "கடுகு", "soyabean" to "சோயாபீன்", "gram" to "கொண்டைக்கடலை",
            "chickpea" to "கொண்டைக்கடலை", "groundnut" to "நிலக்கடலை", "bajra" to "கம்பு", "jowar" to "சோளம்",
            "onion" to "வெங்காயம்", "garlic" to "பூண்டு", "ginger" to "இஞ்சி", "turmeric" to "மஞ்சள்"
        ),
        "kn" to mapOf(
            "rice" to "ಭತ್ತ / ಅಕ್ಕಿ", "paddy" to "ಭತ್ತ", "wheat" to "ಗೋಧಿ", "cotton" to "ಹತ್ತಿ",
            "sugarcane" to "ಕಬ್ಬು", "maize" to "ಮೆಕ್ಕೆಜೋಳ", "corn" to "ಮೆಕ್ಕೆಜೋಳ", "potato" to "ಆಲೂಗಡ್ಡೆ",
            "tomato" to "ಟೊಮ್ಯಾಟೊ", "mustard" to "ಸಾಸಿವೆ", "soyabean" to "ಸೋಯಾಬೀನ್", "gram" to "ಕಡಲೆ",
            "chickpea" to "ಕಡಲೆ", "groundnut" to "ಕಡಲೆಕಾಯಿ", "bajra" to "ಸಜ್ಜೆ", "jowar" to "ಜೋಳ",
            "onion" to "ಈರುಳ್ಳಿ", "garlic" to "ಬೆಳ್ಳುಳ್ಳಿ", "ginger" to "ಶುಂಠಿ", "turmeric" to "ಅರಿಶಿನ"
        ),
        "ml" to mapOf(
            "rice" to "നെല്ല് / അരി", "paddy" to "നെല്ല്", "wheat" to "ഗോതമ്പ്", "cotton" to "പരുത്തി",
            "sugarcane" to "കരിമ്പ്", "maize" to "ചോളം", "corn" to "ചോളം", "potato" to "ഉരുളക്കിഴങ്ങ്",
            "tomato" to "തക്കാളി", "mustard" to "കടുക്", "soyabean" to "സോയാബീൻ", "gram" to "കടല",
            "chickpea" to "കടല", "groundnut" to "നിലക്കടല", "bajra" to "കമ്പം", "jowar" to "ചോളം",
            "onion" to "സവാള", "garlic" to "വെളുത്തുള്ളി", "ginger" to "ഇഞ്ചി", "turmeric" to "മഞ്ഞൾ"
        ),
        "gu" to mapOf(
            "rice" to "ડાંગર / ચોખા", "paddy" to "ડાંગર", "wheat" to "ઘઉં", "cotton" to "કપાસ",
            "sugarcane" to "શેરડી", "maize" to "મકાઈ", "corn" to "મકાઈ", "potato" to "બટાકા",
            "tomato" to "ટામેટા", "mustard" to "રાઈ", "soyabean" to "સોયાબીન", "gram" to "ચણા",
            "chickpea" to "ચણા", "groundnut" to "મગફળી", "bajra" to "બાજરી", "jowar" to "જુવાર",
            "onion" to "ડુંગળી", "garlic" to "લસણ", "ginger" to "આદુ", "turmeric" to "હળદર"
        ),
        "pa" to mapOf(
            "rice" to "ਝੋਨਾ / ਚੌਲ", "paddy" to "ਝੋਨਾ", "wheat" to "ਕਣਕ", "cotton" to "ਨਰਮਾ / ਕਪਾਹ",
            "sugarcane" to "ਕਮਾਦ / ਗੰਨਾ", "maize" to "ਮੱਕੀ", "corn" to "ਮੱਕੀ", "potato" to "ਆਲੂ",
            "tomato" to "ਟਮਾਟਰ", "mustard" to "ਸਰ੍ਹੋਂ", "soyabean" to "ਸੋਇਆਬੀਨ", "gram" to "ਛੋਲੇ",
            "chickpea" to "ਛੋਲੇ", "groundnut" to "ਮੂੰਗਫਲੀ", "bajra" to "ਬਾਜਰਾ", "jowar" to "ਜਵਾਰ",
            "onion" to "ਗੰਢਾ / ਪਿਆਜ਼", "garlic" to "ਲਸਣ", "ginger" to "ਅਦਰਕ", "turmeric" to "ਹਲਦੀ"
        ),
        "or" to mapOf(
            "rice" to "ଧାନ / ଚାଉଳ", "paddy" to "ଧାନ", "wheat" to "ଗହମ", "cotton" to "କପା",
            "sugarcane" to "ଆଖୁ", "maize" to "ମକା", "corn" to "ମକା", "potato" to "ଆଳୁ",
            "tomato" to "ଟମାଟୋ", "mustard" to "ସୋରିଷ", "soyabean" to "ସୋୟାବିନ", "gram" to "ବୁଟ / ଚଣା",
            "chickpea" to "ବୁଟ", "groundnut" to "ଚିନାବାଦାମ", "bajra" to "ବାଜରା", "jowar" to "ଜୁଆର",
            "onion" to "ପିଆଜ", "garlic" to "ରସୁଣ", "ginger" to "ଅଦା", "turmeric" to "ହଳଦୀ"
        )
    )

    private val soilTypeMap: Map<String, Map<String, String>> = mapOf(
        "hi" to mapOf(
            "Alluvial Soil" to "जलोढ़ मिट्टी (Alluvial)", "Black Cotton Soil" to "काली मिट्टी (Black Soil)",
            "Red Soil" to "लाल मिट्टी (Red Soil)", "Laterite Soil" to "लैटेराइट मिट्टी",
            "Sandy Loam" to "बलुई दोमट (Sandy Loam)", "Clayey Soil" to "चिकनी मिट्टी (Clayey)"
        ),
        "mr" to mapOf(
            "Alluvial Soil" to "गाळाची जमीन (Alluvial)", "Black Cotton Soil" to "काळी कसदार जमीन (कापसाची)",
            "Red Soil" to "तांबडी जमीन (Red Soil)", "Laterite Soil" to "जांभी जमीन (Laterite)",
            "Sandy Loam" to "रेतीयुक्त पोयटा जमीन", "Clayey Soil" to "चिकनमातीची जमीन"
        ),
        "bn" to mapOf(
            "Alluvial Soil" to "পলি মাটি (Alluvial)", "Black Cotton Soil" to "কালো মাটি (Black Soil)",
            "Red Soil" to "লাল মাটি (Red Soil)", "Laterite Soil" to "ল্যাটেরাইট মাটি",
            "Sandy Loam" to "বেলে দোআঁশ মাটি", "Clayey Soil" to "এঁটেল মাটি"
        ),
        "te" to mapOf(
            "Alluvial Soil" to "ఒండ్రు నేలలు (Alluvial)", "Black Cotton Soil" to "నల్లరేగడి నేలలు",
            "Red Soil" to "ఎర్ర నేలలు (Red Soil)", "Laterite Soil" to "లేటరైట్ నేలలు",
            "Sandy Loam" to "ఇసుక నేలలు", "Clayey Soil" to "బంకమట్టి నేలలు"
        ),
        "ta" to mapOf(
            "Alluvial Soil" to "வண்டல் மண் (Alluvial)", "Black Cotton Soil" to "கரிசல் மண் (Black Soil)",
            "Red Soil" to "செம்மண் (Red Soil)", "Laterite Soil" to "சரளை மண்",
            "Sandy Loam" to "மணல் கலந்த வண்டல் மண்", "Clayey Soil" to "களிமண்"
        ),
        "kn" to mapOf(
            "Alluvial Soil" to "ಮೆಕ್ಕಲು ಮಣ್ಣು (Alluvial)", "Black Cotton Soil" to "ಕಪ್ಪು ಹತ್ತಿ ಮಣ್ಣು",
            "Red Soil" to "ಕೆಂಪು ಮಣ್ಣು (Red Soil)", "Laterite Soil" to "ಲ್ಯಾಟರೈಟ್ ಮಣ್ಣು",
            "Sandy Loam" to "ಮರಳು ಮಿಶ್ರಿತ ಗೋಡು ಮಣ್ಣು", "Clayey Soil" to "ಜೇಡಿ ಮಣ್ಣು"
        ),
        "gu" to mapOf(
            "Alluvial Soil" to "કાંપની જમીન (Alluvial)", "Black Cotton Soil" to "કાળી કપાસની જમીન",
            "Red Soil" to "રાતી જમીન (Red Soil)", "Laterite Soil" to "લેટેરાઇટ જમીન",
            "Sandy Loam" to "ગોરાડુ / રેતાળ જમીન", "Clayey Soil" to "ચીકણી જમીન"
        ),
        "pa" to mapOf(
            "Alluvial Soil" to "ਜਲੋਢ ਮਿੱਟੀ (Alluvial)", "Black Cotton Soil" to "ਕਾਲੀ ਮਿੱਟੀ",
            "Red Soil" to "ਲਾਲ ਮਿੱਟੀ", "Laterite Soil" to "ਲੈਟਰਾਈਟ ਮਿੱਟੀ",
            "Sandy Loam" to "ਰੇਤਲੀ ਦੋਮਟ ਮਿੱਟੀ", "Clayey Soil" to "ਚੀਕਣੀ ਮਿੱਟੀ"
        ),
        "ml" to mapOf(
            "Alluvial Soil" to "എക്കൽ മണ്ണ് (Alluvial)", "Black Cotton Soil" to "കരിമണ്ണ്",
            "Red Soil" to "ചെമ്മണ്ണ്", "Laterite Soil" to "ലാറ്ററൈറ്റ് മണ്ണ്",
            "Sandy Loam" to "മണൽ കലർന്ന മണ്ണ്", "Clayey Soil" to "കളിമണ്ണ്"
        ),
        "or" to mapOf(
            "Alluvial Soil" to "ପଟୁ ମାଟି (Alluvial)", "Black Cotton Soil" to "କଳା କପା ମାଟି",
            "Red Soil" to "ନାଲି ମାଟି", "Laterite Soil" to "ଲେଟେରାଇଟ ମାଟି",
            "Sandy Loam" to "ବାଲିଆ ଦୋରସା ମାଟି", "Clayey Soil" to "ଚିକିଟା ମାଟି"
        )
    )

    private val seasonMap: Map<String, Map<String, String>> = mapOf(
        "hi" to mapOf("kharif" to "खरीफ (मानसून)", "rabi" to "रबी (सर्दियां)", "zaid" to "जायद (गर्मी)"),
        "mr" to mapOf("kharif" to "खरीप (पावसाळी)", "rabi" to "रब्बी (हिवाळी)", "zaid" to "उन्हाळी (जायद)"),
        "bn" to mapOf("kharif" to "খরিফ (বর্ষাকাল)", "rabi" to "রবি (শীতকাল)", "zaid" to "জায়েদ (গ্রীষ্মকাল)"),
        "te" to mapOf("kharif" to "ఖరీఫ్ (వర్షాకాలం)", "rabi" to "రబీ (శీతాకాలం)", "zaid" to "జాయెద్ (వేసవి)"),
        "ta" to mapOf("kharif" to "காரீப் (மழைக்காலம்)", "rabi" to "ரபி (குளிர்காலம்)", "zaid" to "ஜாயித் (கோடை)"),
        "kn" to mapOf("kharif" to "ಮುಂಗಾರು (ಖಾರಿಫ್)", "rabi" to "ಹಿಂಗಾರು (ರಬಿ)", "zaid" to "ಬೇಸಿಗೆ (ಜಾಯೆದ್)"),
        "gu" to mapOf("kharif" to "ખરીફ (ચોમાસુ)", "rabi" to "રવિ (શિયાળુ)", "zaid" to "જાયદ (ઉનાળુ)"),
        "pa" to mapOf("kharif" to "ਸਾਉਣੀ (ਖ਼ਰੀਫ਼)", "rabi" to "ਹਾੜ੍ਹੀ (ਰਬੀ)", "zaid" to "ਜ਼ਾਇਦ (ਗਰਮੀਆਂ)"),
        "ml" to mapOf("kharif" to "ഖാരിഫ് (മഴക്കാലം)", "rabi" to "റാബി (ശീതകാലം)", "zaid" to "സായിദ് (വേനൽക്കാലം)"),
        "or" to mapOf("kharif" to "ଖରିଫ (ବର୍ଷା ଦିନ)", "rabi" to "ରବି (ଶୀତ ଦିନ)", "zaid" to "ଜାଏଦ (ଖରା ଦିନ)")
    )

    private val waterLevelMap: Map<String, Map<String, String>> = mapOf(
        "hi" to mapOf("high" to "अधिक (नहर / नलकूप)", "medium" to "मध्यम (वर्षा / बोरवेल)", "low" to "कम (असिंचित / ड्रिप)"),
        "mr" to mapOf("high" to "भरपूर (कालवा / विहीर)", "medium" to "मध्यम (पावसावर / बोअरवेल)", "low" to "कमी (कोरडवाहू / ठिबक)"),
        "bn" to mapOf("high" to "উচ্চ (খাল / নলকূপ)", "medium" to "মাঝারি (বৃষ্টি / বোরওয়েল)", "low" to "কম (বৃষ্টিহীন / ড্রিপ)"),
        "te" to mapOf("high" to "ఎక్కువ (కాలువ / బోరు)", "medium" to "మధ్యస్థం (వర్షాధారం)", "low" to "తక్కువ (ఆరుతడి / డ్రిప్)"),
        "ta" to mapOf("high" to "அதிகம் (கால்வாய் / ஆழ்துளை கிணறு)", "medium" to "மிதமானது (மழை / போர்வெல்)", "low" to "குறைவு (மானாவாரி / சொட்டுநீர்)"),
        "kn" to mapOf("high" to "ಉತ್ತಮ ನೀರಾವರಿ (ಕಾಲುವೆ / ಬೋರ್‌ವೆಲ್)", "medium" to "ಮಧ್ಯಮ (ಮಳೆ ಆಶ್ರಿತ)", "low" to "ಕಡಿಮೆ (ಒಣಭೂಮಿ / ಹನಿ ನೀರಾವರಿ)"),
        "gu" to mapOf("high" to "વધુ (કેનાલ / બોરવેલ)", "medium" to "મધ્યમ (વરસાદ આધારિત)", "low" to "ઓછું (સૂકી ખેતી / ટપક)"),
        "pa" to mapOf("high" to "ਵੱਧ (ਨਹਿਰੀ / ਟਿਊਬਵੈੱਲ)", "medium" to "ਦਰਮਿਆਨਾ (ਮੀਂਹ / ਬੋਰਵੈੱਲ)", "low" to "ਘੱਟ (ਬਾਰਾਨੀ / ਤੁਪਕਾ)"),
        "ml" to mapOf("high" to "ധാരാളം (കനാൽ / കുഴൽക്കിണർ)", "medium" to "മിതമായത് (മഴയെ ആശ്രയിച്ച്)", "low" to "കുറഞ്ഞത് (തുള്ളി നന)"),
        "or" to mapOf("high" to "ପ୍ରଚୁର (କେନାଲ / ନଳକୂପ)", "medium" to "ମଧ୍ୟମ (ବର୍ଷା ନିର୍ଭର)", "low" to "କମ (ଶୁଷ୍କ / ଡ୍ରିପ)")
    )
}
