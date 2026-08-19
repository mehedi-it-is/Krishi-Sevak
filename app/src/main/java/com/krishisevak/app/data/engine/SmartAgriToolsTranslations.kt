package com.krishisevak.app.data.engine

object SmartAgriToolsTranslations {

    private val cropNameMap = mapOf(
        "wheat" to mapOf(
            "hi" to "गेहूं", "mr" to "गहू", "bn" to "গম", "te" to "గోధుమలు", "ta" to "கோதுமை",
            "kn" to "ಗೋಧಿ", "gu" to "ઘઉં", "pa" to "ਕਣਕ", "ml" to "ഗോതമ്പ്", "or" to "ଗହମ"
        ),
        "rice" to mapOf(
            "hi" to "धान / चावल", "mr" to "भात / धान", "bn" to "ধান / চাল", "te" to "వరి / ధాన్యం", "ta" to "நெல் / அரிசி",
            "kn" to "ಭತ್ತ / ಅಕ್ಕಿ", "gu" to "ડાંગર / ચોખા", "pa" to "ਝੋਨਾ / ਚਾਵਲ", "ml" to "നെല്ല് / അരി", "or" to "ଧାନ"
        ),
        "paddy" to mapOf(
            "hi" to "धान", "mr" to "भात", "bn" to "ধান", "te" to "వరి", "ta" to "நெல்",
            "kn" to "ಭತ್ತ", "gu" to "ડાંગર", "pa" to "ਝੋਨਾ", "ml" to "നെല്ല്", "or" to "ଧାନ"
        ),
        "cotton" to mapOf(
            "hi" to "कपास", "mr" to "कापूस", "bn" to "তুলা", "te" to "ప్రత్తి", "ta" to "பருத்தி",
            "kn" to "ಹತ್ತಿ", "gu" to "કપાસ", "pa" to "ਨਰਮਾ / ਕਪਾਹ", "ml" to "പരുത്തി", "or" to "କପା"
        ),
        "sugarcane" to mapOf(
            "hi" to "गन्ना", "mr" to "ऊस", "bn" to "আখ", "te" to "చెరకు", "ta" to "கரும்பு",
            "kn" to "ಕಬ್ಬು", "gu" to "શેરડી", "pa" to "ਕਮਾਦ / ਗੰਨਾ", "ml" to "കരിമ്പ്", "or" to "ଆଖୁ"
        ),
        "maize" to mapOf(
            "hi" to "मक्का", "mr" to "मका", "bn" to "ভুট্টা", "te" to "మొక్కజొన్న", "ta" to "மக்காச்சோளம்",
            "kn" to "ಮೆಕ್ಕೆಜೋಳ", "gu" to "મકાઈ", "pa" to "ਮੱਕੀ", "ml" to "മക്കച്ചോളം", "or" to "ମକା"
        ),
        "potato" to mapOf(
            "hi" to "आलू", "mr" to "बटाटा", "bn" to "আলু", "te" to "బంగాళాదుంప", "ta" to "உருளைக்கிழங்கு",
            "kn" to "ಆಲೂಗಡ್ಡೆ", "gu" to "બટાકા", "pa" to "ਆਲੂ", "ml" to "ഉരുളക്കിഴങ്ങ്", "or" to "ଆଳୁ"
        ),
        "tomato" to mapOf(
            "hi" to "टमाटर", "mr" to "टोमॅटो", "bn" to "টমেটো", "te" to "టమోటా", "ta" to "தக்காளி",
            "kn" to "ಟೊಮ್ಯಾಟೊ", "gu" to "ટામેટા", "pa" to "ਟਮਾਟਰ", "ml" to "തക്കാളി", "or" to "ଟମାଟୋ"
        ),
        "mustard" to mapOf(
            "hi" to "सरसों", "mr" to "मोहरी", "bn" to "সরিষা", "te" to "ఆవాలు", "ta" to "கடுகு",
            "kn" to "ಸಾಸಿವೆ", "gu" to "રાઈ", "pa" to "ਸਰ੍ਹੋਂ", "ml" to "കടുക്", "or" to "ସୋରିଷ"
        ),
        "soyabean" to mapOf(
            "hi" to "सोयाबीन", "mr" to "सोयाबीन", "bn" to "সয়াবিন", "te" to "సోయాబీన్", "ta" to "சோயாபீன்",
            "kn" to "ಸೋಯಾಬೀನ್", "gu" to "સોયાબીન", "pa" to "ਸੋਇਆਬੀਨ", "ml" to "സോയാബീൻ", "or" to "ସୋୟାବିନ"
        ),
        "gram" to mapOf(
            "hi" to "चना", "mr" to "हरभरा / चना", "bn" to "ছোলা", "te" to "శనగలు", "ta" to "கொண்டைக்கடலை",
            "kn" to "ಕಡಲೆ", "gu" to "ચણા", "pa" to "ਛੋਲੇ", "ml" to "കടല", "or" to "ବୁଟ"
        ),
        "chickpea" to mapOf(
            "hi" to "चना", "mr" to "हरभरा", "bn" to "ছোলা", "te" to "శనగలు", "ta" to "கொண்டைக்கடலை",
            "kn" to "ಕಡಲೆ", "gu" to "ચણા", "pa" to "ਛੋਲੇ", "ml" to "കടല", "or" to "ବୁଟ"
        ),
        "onion" to mapOf(
            "hi" to "प्याज", "mr" to "कांदा", "bn" to "পেঁয়াজ", "te" to "ఉల్లిపాయ", "ta" to "வெங்காயம்",
            "kn" to "ಈರುಳ್ಳಿ", "gu" to "ડુંગળી", "pa" to "ਗੰਢੇ / ਪਿਆਜ਼", "ml" to "സവാള", "or" to "ପିଆଜ"
        ),
        "garlic" to mapOf(
            "hi" to "लहसुन", "mr" to "लसूण", "bn" to "রসুন", "te" to "వెల్లుల్లి", "ta" to "பூண்டு",
            "kn" to "ಬೆಳ್ಳುಳ್ಳಿ", "gu" to "લસણ", "pa" to "ਲਸਣ", "ml" to "വെളുത്തുള്ളി", "or" to "ରସୁଣ"
        ),
        "groundnut" to mapOf(
            "hi" to "मूंगफली", "mr" to "भुईमूग", "bn" to "চিনাবাদাম", "te" to "వేరుశనగ", "ta" to "வேர்க்கடலை",
            "kn" to "ಕಡಲೆಕಾಯಿ", "gu" to "મગફળી", "pa" to "ਮੂੰਗਫਲੀ", "ml" to "നിലക്കടല", "or" to "ଚିନାବାଦାମ"
        ),
        "moong" to mapOf(
            "hi" to "मूंग दाल", "mr" to "मूग", "bn" to "মুগ ডাল", "te" to "పెసలు", "ta" to "பாசிப்பயறு",
            "kn" to "ಹೆಸರುಕಾಳು", "gu" to "મગ", "pa" to "ਮੂੰਗੀ", "ml" to "ചെറുപയർ", "or" to "ମୁଗ"
        ),
        "urad" to mapOf(
            "hi" to "उड़द दाल", "mr" to "उडीद", "bn" to "মাষকলাই", "te" to "మినుములు", "ta" to "உளுந்து",
            "kn" to "ಉದ್ದಿನಕಾಳು", "gu" to "અડદ", "pa" to "ਮਾਂਹ", "ml" to "ഉഴുന്ന്", "or" to "ବିରି"
        ),
        "jowar" to mapOf(
            "hi" to "ज्वार", "mr" to "ज्वारी", "bn" to "জোয়ার", "te" to "జొన్నలు", "ta" to "சோளம்",
            "kn" to "ಜೋಳ", "gu" to "જુવાર", "pa" to "ਜਵਾਰ", "ml" to "ചോളം", "or" to "ଜୁଆର"
        ),
        "bajra" to mapOf(
            "hi" to "बाजरा", "mr" to "बाजरी", "bn" to "বাজরা", "te" to "సజ్జలు", "ta" to "கம்பு",
            "kn" to "ಸಜ್ಜೆ", "gu" to "બાજરી", "pa" to "ਬਾਜਰਾ", "ml" to "കമ്പം", "or" to "ବାଜରା"
        ),
        "pigeon pea" to mapOf(
            "hi" to "अरहर / तुअर", "mr" to "तूर", "bn" to "অড়হর", "te" to "కందులు", "ta" to "துவரை",
            "kn" to "ತೊಗರಿ", "gu" to "તુવેર", "pa" to "ਅਰਹਰ", "ml" to "തുവര", "or" to "ହରଡ଼"
        ),
        "barley" to mapOf(
            "hi" to "जौ", "mr" to "जव", "bn" to "যব", "te" to "యావలు", "ta" to "பார்லி",
            "kn" to "ಜವೆಗೋಧಿ", "gu" to "જવ", "pa" to "ਜੌਂ", "ml" to "ബാർലി", "or" to "ଯବ"
        ),
        "sunflower" to mapOf(
            "hi" to "सूरजमुखी", "mr" to "सूर्यफूल", "bn" to "সূর্যমুখী", "te" to "పొద్దుతిరుగుడు", "ta" to "சூரியகாந்தி",
            "kn" to "ಸೂರ್ಯಕಾಂತಿ", "gu" to "સૂર્યમુખી", "pa" to "ਸੂਰਜਮੁਖੀ", "ml" to "സൂര്യകാന്തി", "or" to "ସୂର୍ଯ୍ୟମୁଖୀ"
        )
    )

    private val soilTypeMap = mapOf(
        "alluvial soil" to mapOf(
            "hi" to "जलोढ़ मिट्टी", "mr" to "गाळाची जमीन", "bn" to "পলি মাটি", "te" to "ఒండ్రు నేల", "ta" to "வண்டல் மண்",
            "kn" to "ಮೆಕ್ಕಲು ಮಣ್ಣು", "gu" to "કાંપવાળી જમીન", "pa" to "ਜਲੋਢ ਮਿੱਟੀ", "ml" to "എക്കൽ മണ്ണ്", "or" to "ପଟୁ ମାଟି"
        ),
        "black soil" to mapOf(
            "hi" to "काली मिट्टी", "mr" to "काळी जमीन / रेगूर", "bn" to "কালো মাটি", "te" to "నల్లరేగడి నేల", "ta" to "கரிசல் மண்",
            "kn" to "ಕಪ್ಪು ಮಣ್ಣು", "gu" to "કાળી જમીન", "pa" to "ਕਾਲੀ ਮਿੱਟੀ", "ml" to "കറുത്ത മണ്ണ്", "or" to "କଳା ମାଟି"
        ),
        "black cotton soil" to mapOf(
            "hi" to "काली कपास मिट्टी", "mr" to "काळी कापसाची जमीन", "bn" to "কালো তুলা মাটি", "te" to "నల్లరేగడి నేల", "ta" to "கரிசல் மண்",
            "kn" to "ಕಪ್ಪು ಹತ್ತಿ ಮಣ್ಣು", "gu" to "કાળી કપાસની જમીન", "pa" to "ਕਾਲੀ ਕਪਾਹ ਮਿੱਟੀ", "ml" to "കരിമണ്ണ്", "or" to "କଳା କପା ମାଟି"
        ),
        "red soil" to mapOf(
            "hi" to "लाल मिट्टी", "mr" to "तांबडी जमीन", "bn" to "লাল মাটি", "te" to "ఎర్ర నేల", "ta" to "செம்மண்",
            "kn" to "ಕೆಂಪು ಮಣ್ಣು", "gu" to "રાતી / લાલ જમીન", "pa" to "ਲਾਲ ਮਿੱਟੀ", "ml" to "ചെമ്മണ്ണ്", "or" to "ନାଲି ମାଟି"
        ),
        "sandy loam" to mapOf(
            "hi" to "बलुई दोमट मिट्टी", "mr" to "वालୁमય पोयटा जमीन", "bn" to "বেলে দোআঁশ মাটি", "te" to "ఇసుక నేల", "ta" to "மணல் கலந்த வண்டல் மண்",
            "kn" to "ಮರಳು ಮಿಶ್ರಿತ ಗೋಡು ಮಣ್ಣು", "gu" to "ગોરાડુ / રેતાળ જમીન", "pa" to "ਰੇਤਲੀ ਦੋਮਟ ਮਿੱਟੀ", "ml" to "മണൽ കലർന്ന മണ്ണ്", "or" to "ବାଲିଆ ଦୋରସା ମାଟି"
        ),
        "sandy soil" to mapOf(
            "hi" to "बलुई / रेतीली मिट्टी", "mr" to "वालୁमय जमीन", "bn" to "বেলে মাটি", "te" to "ఇసుక నేల", "ta" to "மணல் மண்",
            "kn" to "ಮರಳು ಮಣ್ಣು", "gu" to "રેતાળ જમીન", "pa" to "ਰੇਤਲੀ ਮਿੱਟੀ", "ml" to "മണൽ മണ്ണ്", "or" to "ବାଲି ମାଟି"
        ),
        "clay loam" to mapOf(
            "hi" to "चिकनी दोमट मिट्टी", "mr" to "चिकણ पोयटा जमीन", "bn" to "এঁটেল দোআঁশ মাটি", "te" to "బంకమట్టి నేల", "ta" to "களிமண்",
            "kn" to "ಜೇಡಿ ಗೋಡು ಮಣ್ಣು", "gu" to "ચીકણી જમીન", "pa" to "ਚੀਕਣੀ ਮਿੱਟੀ", "ml" to "കളിമണ്ണ്", "or" to "ମଟାଳ ଦୋରସା ମାଟି"
        ),
        "laterite soil" to mapOf(
            "hi" to "लैटेराइट मिट्टी", "mr" to "जांभी जमीन", "bn" to "ল্যাটেরাইট মাটি", "te" to "లేటరైట్ నేల", "ta" to "லேட்டரைட் மண்",
            "kn" to "ಲ್ಯಾಟರೈಟ್ ಮಣ್ಣು", "gu" to "લેટેરાઇટ જમીન", "pa" to "ਲੈਟਰਾਈਟ ਮਿੱਟੀ", "ml" to "ലാറ്ററൈറ്റ് മണ്ണ്", "or" to "ଲାଟେରାଇଟ ମାଟି"
        )
    )

    private val seasonMap = mapOf(
        "kharif (monsoon)" to mapOf(
            "hi" to "खरीफ (मानसून)", "mr" to "खरीप (पावसाळी)", "bn" to "খরিফ (বর্ষাকাল)", "te" to "ఖరీఫ్ (వర్షాకాలం)", "ta" to "காரீப் (பருவமழை)",
            "kn" to "ಖಾರೀಫ್ (ಮುಂಗಾರು)", "gu" to "ચોમાસુ (ખરીફ)", "pa" to "ਸਾਉਣੀ (ਖਰੀਫ਼)", "ml" to "ഖാരിഫ് (മഴക്കാലം)", "or" to "ଖରିଫ (ବର୍ଷାଋତୁ)"
        ),
        "kharif" to mapOf(
            "hi" to "खरीफ", "mr" to "खरीप", "bn" to "খরিফ", "te" to "ఖరీఫ్", "ta" to "காரீப்",
            "kn" to "ಖಾರೀಫ್", "gu" to "ખરીફ", "pa" to "ਸਾਉਣੀ", "ml" to "ഖാരിഫ്", "or" to "ଖରିଫ"
        ),
        "rabi (winter)" to mapOf(
            "hi" to "रबी (सर्दियां)", "mr" to "रब्बी (हिवाळी)", "bn" to "রবি (শীতকাল)", "te" to "రబీ (శీతాకాలం)", "ta" to "ரபி (குளிர்காலம்)",
            "kn" to "ರಬಿ (ಹಿಂಗಾರು)", "gu" to "શિયાળુ (રવિ)", "pa" to "ਹਾੜ੍ਹੀ (ਰਬੀ)", "ml" to "റബി (ശീതകാലം)", "or" to "ରବି (ଶୀତଋତୁ)"
        ),
        "rabi" to mapOf(
            "hi" to "रबी", "mr" to "रब्बी", "bn" to "রবি", "te" to "రబీ", "ta" to "ரபி",
            "kn" to "ರಬಿ", "gu" to "રવિ", "pa" to "ਹਾੜ੍ਹੀ", "ml" to "റബി", "or" to "ରବି"
        ),
        "zaid (summer)" to mapOf(
            "hi" to "जायद (गर्मी)", "mr" to "उन्हाळी (झायेद)", "bn" to "জায়েদ (গ্রীষ্মকাল)", "te" to "జాయెద్ (వేసవి)", "ta" to "சையத் (கோடைக்காலம்)",
            "kn" to "ಬೇಸಿಗೆ ಬೆಳೆ (ಜಾಯೆದ್)", "gu" to "ઉનાળુ (ઝાયદ)", "pa" to "ਜ਼ਾਇਦ (ਗਰਮੀਆਂ)", "ml" to "സെയ്ദ് (വേനൽക്കാലം)", "or" to "ଜାଏଦ (ଗ୍ରୀଷ୍ମଋତୁ)"
        ),
        "zaid" to mapOf(
            "hi" to "जायद", "mr" to "उन्हाळी", "bn" to "জায়েদ", "te" to "జాయెద్", "ta" to "சையத்",
            "kn" to "ಜಾಯೆದ್", "gu" to "ઝાયદ", "pa" to "ਜ਼ਾਇਦ", "ml" to "സെയ്ദ്", "or" to "ଜାଏଦ"
        )
    )

    private val waterLevelMap = mapOf(
        "high / assured irrigation" to mapOf(
            "hi" to "पर्याप्त सिंचाई सुविधा", "mr" to "खात्रीशीर पाणीपुरवठा", "bn" to "পর্যাপ্ত সেচ সুবিধা", "te" to "సమృద్ధిగా నీటి సదుపాయం", "ta" to "நிறைவான நீர்ப்பாசனம்",
            "kn" to "ಉತ್ತಮ ನೀರಾವರಿ ಸೌಲಭ್ಯ", "gu" to "પૂરતી પિયત સુવિધા", "pa" to "ਪੱਕਾ ਸਿੰਚਾਈ ਪ੍ਰਬੰਧ", "ml" to "ഉറപ്പുള്ള ജലസേചനം", "or" to "ପର୍ଯ୍ୟାପ୍ତ ଜଳସେଚନ ସୁବିଧା"
        ),
        "moderate irrigation" to mapOf(
            "hi" to "मध्यम सिंचाई", "mr" to "मध्यम पाणीपुरवठा", "bn" to "মাঝারি সেচ", "te" to "మితమైన నీటి వనరులు", "ta" to "மிதமான பாசனம்",
            "kn" to "ಸಾಧಾರಣ ನೀರಾವರಿ", "gu" to "મધ્યમ પિયત", "pa" to "ਦਰਮਿਆਨੀ ਸਿੰਚਾਈ", "ml" to "മിതമായ ജലസേചനം", "or" to "ମଧ୍ୟମ ଧରଣର ଜଳସେଚନ"
        ),
        "rainfed / low water" to mapOf(
            "hi" to "वर्षा आधारित / कम पानी", "mr" to "कोरडवाहू / कमी पाणी", "bn" to "বৃষ্টি নির্ভর / কম জল", "te" to "వర్షాధారం / తక్కువ నీరు", "ta" to "மானாவாரி / குறைந்த நீர்",
            "kn" to "ಮಳೆಯಾಶ್ರಿತ / ಕಡಿಮೆ ನೀರು", "gu" to "વરસાદ આધારિત / ઓછું પાણી", "pa" to "ਮੀਂਹ 'ਤੇ ਨਿਰਭਰ / ਘੱਟ ਪਾਣੀ", "ml" to "മഴയെ ആശ്രയിച്ച് / കുറഞ്ഞ വെള്ളം", "or" to "ବର୍ଷା ନିର୍ଭର / କମ ପାଣି"
        )
    )

    fun getCropName(crop: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return crop
        val key = crop.trim().lowercase()
        return cropNameMap[key]?.get(code) ?: cropNameMap.entries.find { key.contains(it.key) }?.value?.get(code) ?: crop
    }

    fun getSoilTypeName(soil: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return soil
        val key = soil.trim().lowercase()
        return soilTypeMap[key]?.get(code) ?: soilTypeMap.entries.find { key.contains(it.key) }?.value?.get(code) ?: soil
    }

    fun getSeasonName(season: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return season
        val key = season.trim().lowercase()
        return seasonMap[key]?.get(code) ?: seasonMap.entries.find { key.contains(it.key) }?.value?.get(code) ?: season
    }

    fun getWaterLevelName(water: String, langCode: String): String {
        val code = langCode.lowercase()
        if (code == "en") return water
        val key = water.trim().lowercase()
        return waterLevelMap[key]?.get(code) ?: waterLevelMap.entries.find { key.contains(it.key) }?.value?.get(code) ?: water
    }
}
