package com.krishisevak.app.utils

object LocalSmartAiEngine {

    private val cropKeywords = listOf(
        "wheat", "गेहूं", "गहू", "గోధుమ", "గోధుమలు", "ಗೋಧಿ", "கோதுமை", "ഗോതമ്പ്", "ਕਣਕ", "ઘઉં", "ଗହମ",
        "paddy", "rice", "धान", "तांदूळ", "వరి", "ধান", "ಭತ್ತ", "நெல்", "നെല്ല്", "ਝੋਨਾ", "ચોખા", "ଧାନ",
        "cotton", "कपास", "कापूस", "ప్రత్తి", "পটি", "ಹತ್ತಿ", "பருத்தி", "പരുത്തി", "ਕਪਾਹ", "કપાસ", "କପା",
        "onion", "प्याज", "कांदा", "ఉల్లిపాయ", "পেঁয়াজ", "ಈರುಳ್ಳಿ", "வெங்காயம்", "ഉള്ളി", "ਪਿਆਜ਼", "ડુંગળી", "ପିଆଜ",
        "tomato", "टमाटर", "टोमॅटो", "టామాటో", "টমেটো", "ಟೊಮೆಟೊ", "தக்காளி", "തக்காளி", "ਟਮਾਟਰ", "ટામેટાં", "ଟମାଟୋ",
        "soybean", "सोयाबीन", "సోయాబీన్", "ସୋୟାବିନ", "ಸೋಯಾಬೀನ್",
        "sugarcane", "गन्ना", "ऊस", "చెరకు", "আখ", "ಕಬ್ಬು", "கரும்பு", "കരിമ്പ്", "ਗੰਨਾ", "શેਰડી", "ଆଖୁ",
        "mustard", "सरसों", "मोहरी", "ఆవాలు", "ಸಾಸಿವೆ", "கடுகு", "കടുക്", "ਸਰ੍ਹੋਂ", "રાઈ", "ସୋରିଷ",
        "potato", "आलू", "बटाटा", "బంగాళాదుంప", "আলু", "ಆಲೂಗಡ್ಡೆ", "உருளைக்கிழங்கு", "ഉരുളക്കിഴങ്ങ്", "ਆਲੂ", "બટાટા", "ଆଳୁ"
    )

    private val diseaseKeywords = listOf(
        "rust", "blight", "rot", "wilt", "mildew", "yellow", "spot", "pest", "fungus",
        "कीट", "रोग", "ब्लाइट", "झुलसा", "फफूंद", "किड", "तांबेरा", "తెగులు",
        "ಕೀಟ", "ரோகம்", "കീടം", "ਕੀੜਾ", "જીવાત", "କୀଟ"
    )

    fun generateSmartTitle(prompt: String): String {
        val clean = prompt.trim()
        if (clean.isBlank()) return "New Farming Query"

        val lower = clean.lowercase()
        val foundCrop = cropKeywords.firstOrNull { lower.contains(it) }?.replaceFirstChar { it.uppercase() }
        val foundDisease = diseaseKeywords.firstOrNull { lower.contains(it) }?.replaceFirstChar { it.uppercase() }

        return when {
            foundCrop != null && foundDisease != null -> "$foundCrop $foundDisease Advisory"
            foundCrop != null -> "$foundCrop Crop Care"
            foundDisease != null -> "$foundDisease Disease Solution"
            lower.contains("mandi") || lower.contains("price") || lower.contains("भाव") || lower.contains("दाम") || lower.contains("ధర") || lower.contains("ভাভ") || lower.contains("ಬೆಲೆ") || lower.contains("விலை") || lower.contains("വില") || lower.contains("ਭਾਅ") || lower.contains("ભાવ") || lower.contains("ଦାମ") -> "Mandi Price Advisory"
            lower.contains("weather") || lower.contains("rain") || lower.contains("मौसम") || lower.contains("पाऊस") || lower.contains("వాతావరణం") || lower.contains("আবহাওয়া") || lower.contains("ಹವಾಮಾನ") || lower.contains("வானிலை") || lower.contains("കാലാവസ്ഥ") || lower.contains("ਮੌਸਮ") || lower.contains("હવામાન") || lower.contains("ପାଣିପାଗ") -> "Weather Forecast Advisory"
            lower.contains("scheme") || lower.contains("pm-kisan") || lower.contains("योजना") || lower.contains("పథకం") || lower.contains("প্রকল্প") || lower.contains("ಯೋಜನೆ") || lower.contains("திட்டம்") || lower.contains("പദ്ധതി") || lower.contains("ਯੋਜਨਾ") || lower.contains("યોજના") || lower.contains("ଯୋଜନା") -> "Govt Scheme Details"
            else -> {
                val words = clean.split("\\s+".toRegex()).take(3)
                words.joinToString(" ").replaceFirstChar { it.uppercase() }
            }
        }
    }

    fun generateExecutiveSummary(userQuery: String, aiResponseText: String): String {
        val crop = cropKeywords.firstOrNull { userQuery.lowercase().contains(it) }?.replaceFirstChar { it.uppercase() } ?: "Crop"
        val snippet = aiResponseText.take(120).replace("\n", " ").trim()
        return "$crop Query Summary: $snippet..."
    }

    fun generateLocalAdvisory(userQuery: String, langCode: String): String {
        val lower = userQuery.lowercase()
        return when (langCode.lowercase()) {
            "hi" -> when {
                lower.contains("कीट") || lower.contains("रोग") || lower.contains("दवा") || lower.contains("spray") ->
                    "कृषि विशेषज्ञ सलाह: फसल में कीट/फफूंद नियंत्रण के लिए 10,000 PPM नीम तेल (@ 3ml/लीटर) या कॉपर ऑक्सीक्लोराइड 50 WP (@ 2.5g/लीटर) का छिड़काव करें। स्प्रे हमेशा शाम को करें।"
                lower.contains("खाद") || lower.contains("यूरिया") || lower.contains("fertilizer") ->
                    "उर्वरक सलाह: प्रति एकड़ संतुलित मात्रा में गोबर खाद (FYM) और मिट्टी परीक्षण के अनुसार यूरिया व डीएपी डालें। फूल आते समय घुलनशील NPK 19:19:19 (@ 5g/लीटर) का छिड़काव करें।"
                lower.contains("मंडी") || lower.contains("भाव") || lower.contains("दाम") || lower.contains("price") ->
                    "मंडी भाव अपडेट: निकटतम APMC मंडी में आपकी फसल की गुणवत्ता के आधार पर अच्छे दाम मिल रहे हैं। उपज को सुखाकर और श्रेणीबद्ध (grading) करके ही मंडी ले जाएं।"
                else ->
                    "कृषि सेवक सलाह: आपकी फसल की अच्छी पैदावार के लिए नियमित जल प्रबंधन रखें, खरपतवार निकालें और मौसम का पूर्वानुमान देखकर ही कृषि कार्य करें।"
            }
            "mr" -> when {
                lower.contains("कीड") || lower.contains("रोग") || lower.contains("औषध") || lower.contains("spray") ->
                    "कृषी तज्ज्ञ सल्ला: पिकावरील कीड व बुरशी नियंत्रणासाठी निंबोळी अर्क (१०,००० पीपीएम @ ३ मिली/लिटर) किंवा कॉपर ऑक्सिक्लोराईड (@ २.५ ग्रॅम/लिटर) फवारावे. फवारणी संध्याकाळी करावी."
                lower.contains("खत") || lower.contains("युरिया") || lower.contains("fertilizer") ->
                    "खत व्यवस्थापन: माती परीक्षणानुसार संतुलित खतांचा वापर करा. पीक वाढीच्या अवस्थेत १९:१९:१९ व फुलधारणेच्या वेळी १२:६१:०० विद्राव्य खतांची फवारणी करावी."
                lower.contains("भाव") || lower.contains("बाजार") || lower.contains("मंडी") ->
                    "बाजारभाव सल्ला: जवळच्या कृषी उत्पन्न बाजार समितीत (APMC) शेतमालाची प्रतवारी (Grading) करून विक्रीस नेल्यास उत्तम दर मिळतील."
                else ->
                    "कृषि सेवक सल्ला: पिकांच्या जोमदार वाढीसाठी ठिबक सिंचनाचा वापर करा, तण नियंत्रण ठेवा आणि हवामान अंदाज पाहून फवारणीचे नियोजन करा."
            }
            "bn" -> when {
                lower.contains("পোকা") || lower.contains("রোগ") || lower.contains("ওষুধ") ->
                    "কৃষি বিশেষজ্ঞের পরামর্শ: ফসলের পোকা ও ছত্রাক দমনে নিম তেল (১০,০০০ পিপিএম @ ৩ মিলি/লিটার) বা কপার অক্সিক্লোরাইড স্প্রে করুন। বিকেলে স্প্রে করা উত্তম।"
                lower.contains("সার") || lower.contains("ইউরিয়া") ->
                    "সার প্রয়োগের পরামর্শ: মাটি পরীক্ষা করে সুষম মাত্রায় ইউরিয়া, ডিএপি ও পটাশ প্রয়োগ করুন। ফুল আসার সময় এনপিকে ১৯:১৯:১৯ স্প্রে করুন।"
                else ->
                    "কৃষি সেবক পরামর্শ: ফসলের ভালো ফলনের জন্য সঠিক সেচ দিন, আগাছা মুক্ত রাখুন এবং আবহাওয়ার পূর্বাভাস দেখে ওষুধ স্প্রে করুন।"
            }
            "te" -> when {
                lower.contains("పురుగు") || lower.contains("తెగులు") || lower.contains("మందు") ->
                    "వ్యవసాయ నిపుణుల సలహా: పంటలో పురుగులు మరియు తెగుళ్ల నివారణకు వేప నూనె (3 మి.లీ/లీటరు) లేదా కాపర్ ఆక్సిక్లోరైడ్ పిచికారీ చేయండి. సాయంత్రం వేళల్లో పిచికారీ చేయడం మంచిది."
                lower.contains("ఎరువు") || lower.contains("యూరియా") ->
                    "ఎరువుల యాజమాన్యం: నేల పరీక్ష ఆధారంగా సమతుల్య ఎరువులను వాడండి. పూత దశలో 19:19:19 ఎరువును పిచికారీ చేయండి."
                else ->
                    "కృషి సేవక్ సలహా: అధిక దిగుబడి కోసం సరైన నీటి యాజమాన్యం పాటించండి మరియు వాతావరణ సమాచారం ఆధారంగా వ్యవసాయ పనులు చేపట్టండి."
            }
            "ta" -> when {
                lower.contains("பூச்சி") || lower.contains("நோய்") || lower.contains("மருந்து") ->
                    "வேளாண் ஆலோசகர்: பயிரில் பூச்சி மற்றும் நோய் தாக்கத்தை கட்டுப்படுத்த வேப்ப எண்ணெய் (3 மி.லி/லி) அல்லது காப்பர் ஆக்ஸிகுளோரைடு தெளிக்கவும். மாலையில் தெளிப்பது சிறந்தது."
                lower.contains("உரம்") || lower.contains("யூரியா") ->
                    "உர மேலாண்மை: மண் பரிசோதனை பரிந்துரைப்படி சமச்சீர் உரம் இடவும். பூக்கும் பருவத்தில் NPK 19:19:19 இலைவழி தெளிக்கவும்."
                else ->
                    "கிருஷி சேவக்: அதிக மகசூலுக்கு சொட்டுநீர் பாசனம் அமைக்கவும் மற்றும் வானிலை முன்னறிவிப்பு பார்த்து பூச்சிக்கொல்லி தெளிக்கவும்."
            }
            "kn" ->
                "ಕೃಷಿ ಸೇವಕ್ ಸಲಹೆ: ಬೆಳೆ ಸಂರಕ್ಷಣೆಗಾಗಿ ಬೇವಿನ ಎಣ್ಣೆ (3 ಮಿಲಿ/ಲೀಟರ್) ಸಿಂಪಡಿಸಿ. ಮಣ್ಣು ಪರೀಕ್ಷೆ ಆಧಾರದ ಮೇಲೆ ಸಮತೋಲಿತ ರಸಗೊಬ್ಬರ ಮತ್ತು ಹನಿ ನೀರಾವರಿ ಬಳಸಿ."
            "ml" ->
                "കൃഷി സേവക് ഉപദേശം: കീടങ്ങളെ നിയന്ത്രിക്കാൻ വേപ്പെണ്ണ മിശ്രിതം തളിക്കുക. മണ്ണുപരിശോധന അടിസ്ഥാനമാക്കി വളപ്രയോഗം നടത്തുക."
            "gu" ->
                "કૃષિ સેવક સલાહ: પાકમાં જીવાત નિયંત્રણ માટે લીમડાનું તેલ (@ ૩ મિલી/લિટર) છાંટો. જમીન ચકાસણી મુજબ સંતુલિત ખાતર આપો."
            "pa" ->
                "ਕ੍ਰਿਸ਼ੀ ਸੇਵਕ ਸਲਾਹ: ਕੀੜਿਆਂ ਦੀ ਰੋਕਥਾਮ ਲਈ ਨਿੰਮ ਦੇ ਤੇਲ ਦਾ ਛਿੜਕਾਅ ਕਰੋ। ਮਿੱਟੀ ਦੀ ਪਰਖ ਅਨੁਸਾਰ ਸੰਤੁਲਿਤ ਖਾਦਾਂ ਦੀ ਵਰਤੋਂ ਕਰੋ।"
            "or" ->
                "କୃଷି ସେବକ ପରାମର୍ଶ: ଫସଲରେ ପୋକ ନିୟନ୍ତ୍ରଣ ପାଇଁ ନିମ୍ବ ତେଲ (3 ମି.ଲି./ଲିଟର) ସ୍ପ୍ରେ କରନ୍ତୁ। ମାଟି ପରୀକ୍ଷା ଅନୁଯାୟୀ ସନ୍ତୁଳିତ ସାର ପ୍ରୟୋଗ କରନ୍ତୁ।"
            else ->
                "Agri Expert Advisory: For organic pest control, apply Cold-Pressed Neem Oil (10,000 PPM @ 3ml/L) or Copper Oxychloride. Ensure proper root zone drainage and follow balanced NPK fertilization."
        }
    }

    fun build7DayWeatherAudioScript(
        cityName: String,
        stateName: String,
        tempToday: String,
        conditionToday: String,
        humidityToday: String,
        tempTomorrow: String,
        conditionTomorrow: String,
        rainProbTomorrow: String,
        outlook5Day: String,
        farmingTip: String,
        langCode: String
    ): String {
        return when (langCode.lowercase()) {
            "hi" -> """
                $cityName, $stateName के लिए मौसम का सारांश।
                आज का मौसम: तापमान $tempToday डिग्री, $conditionToday, नमी $humidityToday प्रतिशत।
                कल का मौसम: तापमान $tempTomorrow डिग्री, $conditionTomorrow, बारिश की संभावना $rainProbTomorrow प्रतिशत।
                आगामी 5 दिनों का पूर्वानुमान: $outlook5Day।
                किसान सलाह: $farmingTip।
            """.trimIndent()

            "mr" -> """
                $cityName, $stateName साठी हवामान अंदाज.
                आजचे हवामान: तापमान $tempToday अंश, $conditionToday, आद्रता $humidityToday टक्के.
                उद्याचे हवामान: तापमान $tempTomorrow अंश, $conditionTomorrow, पावसाची शक्यता $rainProbTomorrow टक्के.
                पुढील 5 दिवसांचा अंदाज: $outlook5Day.
                शेती सल्ला: $farmingTip.
            """.trimIndent()

            "te" -> """
                $cityName, $stateName వాతావరణ నివేదిక.
                ఈరోజు వాతావరణం: ఉష్ణోగ్రత $tempToday డిగ్రీలు, $conditionToday.
                రేపటి వాతావరణం: ఉష్ణోగ్రత $tempTomorrow డిగ్రీలు, వర్ష సూచన $rainProbTomorrow శాతం.
                రాబోయే 5 రోజుల వాతావరణ అంచనా: $outlook5Day.
                రైతులకు సలహా: $farmingTip.
            """.trimIndent()

            "bn" -> """
                $cityName, $stateName এর আবহাওয়া প্রতিবেদন।
                আজকের আবহাওয়া: তাপমাত্রা $tempToday ডিগ্রি, $conditionToday, আর্দ্রতা $humidityToday শতাংশ।
                আগামীকালের আবহাওয়া: তাপমাত্রা $tempTomorrow ডিগ্রি, $conditionTomorrow, বৃষ্টির সম্ভাবনা $rainProbTomorrow শতাংশ।
                আগামী ৫ দিনের পূর্বাভাস: $outlook5Day।
                কৃষকদের পরামর্শ: $farmingTip।
            """.trimIndent()

            "ta" -> """
                $cityName, $stateName வானிலை அறிக்கை.
                இன்றைய வானிலை: வெப்பநிலை $tempToday டிகிரி, $conditionToday, ஈரப்பதம் $humidityToday சதவீதம்.
                நாளைய வானிலை: வெப்பநிலை $tempTomorrow டிகிரி, $conditionTomorrow, மழை வாய்ப்பு $rainProbTomorrow சதவீதம்.
                அடுத்த 5 நாட்கள் பூர்வானுமானம்: $outlook5Day.
                விவசாயிகளுக்கு ஆலோசனை: $farmingTip.
            """.trimIndent()

            "kn" -> """
                $cityName, $stateName ಹವಾಮಾನ ವರದಿ.
                ಇಂದಿನ ಹವಾಮಾನ: ಉಷ್ಣಾಂಶ $tempToday ಡಿಗ್ರಿ, $conditionToday, ತೇವಾಂಶ $humidityToday ಶೇಕಡಾ.
                ನಾಳಿನ ಹವಾಮಾನ: ಉಷ್ಣಾಂಶ $tempTomorrow ಡಿಗ್ರಿ, $conditionTomorrow, ಮಳೆ ಸಾಧ್ಯತೆ $rainProbTomorrow ಶೇಕಡಾ.
                ಮುಂಬರುವ 5 ದಿನಗಳ ಮುನ್ಸೂಚನೆ: $outlook5Day.
                ರೈತರಿಗೆ ಸಲಹೆ: $farmingTip.
            """.trimIndent()

            "ml" -> """
                $cityName, $stateName കാലാവസ്ഥ റിപ്പോർട്ട്.
                ഇന്നത്തെ കാലാവസ്ഥ: താപനില $tempToday ഡിഗ്രി, $conditionToday, ഈർപ്പം $humidityToday ശതമാനം.
                നാളത്തെ കാലാവസ്ഥ: താപനില $tempTomorrow ഡിഗ്രി, $conditionTomorrow, മഴ സാധ്യത $rainProbTomorrow ശതമാനം.
                അടുത്ത 5 ദിവസത്തെ പ്രവചനം: $outlook5Day.
                കർഷകർക്ക് ഉപദേശം: $farmingTip.
            """.trimIndent()

            "gu" -> """
                $cityName, $stateName હવામાન અહેવાલ.
                આજનું હવામાન: તાપમાન $tempToday ડિગ્રી, $conditionToday, ભેજ $humidityToday ટકા.
                આવતીકાલનું હવામાન: તાપમાન $tempTomorrow ડિગ્રી, $conditionTomorrow, વરસાદની શક્યતા $rainProbTomorrow ટકા.
                આવનારા 5 દિવસની આગાહી: $outlook5Day.
                ખેડૂતોને સલાહ: $farmingTip.
            """.trimIndent()

            "pa" -> """
                $cityName, $stateName ਮੌਸਮ ਰਿਪੋਰਟ।
                ਅੱਜ ਦਾ ਮੌਸਮ: ਤਾਪਮਾਨ $tempToday ਡਿਗਰੀ, $conditionToday, ਨਮੀ $humidityToday ਫ਼ੀਸਦੀ।
                ਕੱਲ੍ਹ ਦਾ ਮੌਸਮ: ਤਾਪਮਾਨ $tempTomorrow ਡਿਗਰੀ, $conditionTomorrow, ਮੀਂਹ ਦੀ ਸੰਭਾਵਨਾ $rainProbTomorrow ਫ਼ੀਸਦੀ।
                ਅਗਲੇ 5 ਦਿਨਾਂ ਦਾ ਅਨੁਮਾਨ: $outlook5Day।
                ਕਿਸਾਨਾਂ ਲਈ ਸਲਾਹ: $farmingTip।
            """.trimIndent()

            "or" -> """
                $cityName, $stateName ପାଣିପାଗ ରିପୋର୍ଟ।
                ଆଜିର ପାଣିପାଗ: ତାପମାତ୍ରା $tempToday ଡିଗ୍ରୀ, $conditionToday, ଆର୍ଦ୍ରତା $humidityToday ପ୍ରତିଶତ।
                ଆସନ୍ତାକାଲି ପାଣିପାଗ: ତାପମାତ୍ରା $tempTomorrow ଡିଗ୍ରୀ, $conditionTomorrow, ବର୍ଷା ସମ୍ଭାବନା $rainProbTomorrow ପ୍ରତିଶତ।
                ଆଗାମୀ 5 ଦିନର ପୂର୍ବାନୁମାନ: $outlook5Day।
                ଚାଷୀଙ୍କୁ ପରାମର୍ଶ: $farmingTip।
            """.trimIndent()

            else -> """
                Weather forecast summary for $cityName, $stateName.
                Today's weather: Temperature $tempToday degrees celsius, $conditionToday, with $humidityToday percent humidity.
                Tomorrow's forecast: Expecting $tempTomorrow degrees celsius with $conditionTomorrow and $rainProbTomorrow percent rain probability.
                5-day outlook: $outlook5Day.
                Farmer advisory tip: $farmingTip.
            """.trimIndent()
        }
    }
}
