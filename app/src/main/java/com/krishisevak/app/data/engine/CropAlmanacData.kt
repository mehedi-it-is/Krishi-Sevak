package com.krishisevak.app.data.engine

enum class AlmanacActivityType(val label: String, val icon: String, val badgeColorHex: Long) {
    SOWING("Sowing", "🌱", 0xFF10B981),
    FERTILIZING("Fertilizing", "🌿", 0xFF16A34A),
    WATERING("Watering", "💧", 0xFF0284C7),
    PEST_CONTROL("Pest Control", "🐛", 0xFFDC2626),
    HARVESTING("Harvesting", "✂️", 0xFFD97706);

    fun getLocalizedLabel(langCode: String): String {
        return when (langCode.lowercase()) {
            "hi" -> when (this) {
                SOWING -> "बुवाई"
                FERTILIZING -> "उर्वरक / खाद"
                WATERING -> "सिंचाई"
                PEST_CONTROL -> "कीट नियंत्रण"
                HARVESTING -> "कटाई"
            }
            "mr" -> when (this) {
                SOWING -> "पेरणी"
                FERTILIZING -> "खत व्यवस्थापन"
                WATERING -> "पाणी व्यवस्थापन"
                PEST_CONTROL -> "कीड नियंत्रण"
                HARVESTING -> "कापणी / काढणी"
            }
            "bn" -> when (this) {
                SOWING -> "বপন / রোপণ"
                FERTILIZING -> "সার প্রয়োগ"
                WATERING -> "সেচ প্রদান"
                PEST_CONTROL -> "বালাই দমন"
                HARVESTING -> "ফসল কাটা"
            }
            "te" -> when (this) {
                SOWING -> "విత్తడం / నాట్లు"
                FERTILIZING -> "ఎరువుల యాజమాన్యం"
                WATERING -> "నీటిపారుదల"
                PEST_CONTROL -> "సస్యరక్షణ"
                HARVESTING -> "కోత"
            }
            "ta" -> when (this) {
                SOWING -> "விதைப்பு"
                FERTILIZING -> "உரமிடுதல்"
                WATERING -> "நீர்ப்பாசனம்"
                PEST_CONTROL -> "பூச்சி கட்டுப்பாடு"
                HARVESTING -> "அறுவடை"
            }
            "kn" -> when (this) {
                SOWING -> "ಬಿತ್ತನೆ"
                FERTILIZING -> "ಗೊಬ್ಬರ ನಿರ್ವಹಣೆ"
                WATERING -> "ನೀರಾವರಿ"
                PEST_CONTROL -> "ಕೀಟ ನಿಯಂತ್ರಣ"
                HARVESTING -> "ಕೊಯ್ಲು"
            }
            "gu" -> when (this) {
                SOWING -> "વાવણી"
                FERTILIZING -> "ખાતર વ્યવસ્થાપન"
                WATERING -> "પિયત"
                PEST_CONTROL -> "જીવાત નિયંત્રણ"
                HARVESTING -> "લણણી"
            }
            "pa" -> when (this) {
                SOWING -> "ਬਿਜਾਈ"
                FERTILIZING -> "ਖਾਦ ਪ੍ਰਬੰਧਨ"
                WATERING -> "ਸਿੰਚਾਈ"
                PEST_CONTROL -> "ਕੀੜੇ-ਮਕੌੜੇ ਕੰਟਰੋਲ"
                HARVESTING -> "ਵਾਢੀ"
            }
            "or" -> when (this) {
                SOWING -> "ବୁଣା / ରୋପଣ"
                FERTILIZING -> "ଖତ ଓ ସାର"
                WATERING -> "ଜଳସେଚନ"
                PEST_CONTROL -> "କୀଟ ନିୟନ୍ତ୍ରଣ"
                HARVESTING -> "ଅମଳ"
            }
            "ml" -> when (this) {
                SOWING -> "വിത്ത് നടൽ"
                FERTILIZING -> "വളപ്രയോഗം"
                WATERING -> "നനയ്ക്കൽ"
                PEST_CONTROL -> "കീടനിയന്ത്രണം"
                HARVESTING -> "വിളവെടുപ്പ്"
            }
            else -> label
        }
    }
}

data class AlmanacActivityItem(
    val type: AlmanacActivityType,
    val crop: String,
    val title: String,
    val description: String,
    val optimalTiming: String
)

data class MonthAlmanac(
    val monthIndex: Int, // 1 to 12
    val monthName: String,
    val seasonName: String, // "Kharif", "Rabi", "Zaid"
    val seasonEmoji: String,
    val weatherTip: String,
    val activities: List<AlmanacActivityItem>
)

object CropAlmanacData {

    private val monthNamesMap = mapOf(
        "hi" to listOf("जनवरी", "फरवरी", "मार्च", "अप्रैल", "मई", "जून", "जुलाई", "अगस्त", "सितंबर", "अक्टूबर", "नवंबर", "दिसंबर"),
        "mr" to listOf("जानेवारी", "फेब्रुवारी", "मार्च", "एप्रिल", "मे", "जून", "जुलै", "ऑगस्ट", "सप्टेंबर", "ऑक्टोबर", "नोव्हेंबर", "डिसेंबर"),
        "bn" to listOf("জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"),
        "te" to listOf("జనవరి", "ఫిబ్రవరి", "మార్చి", "ఏప్రిల్", "మే", "జూన్", "జూలై", "ఆగస్టు", "సెప్టెంబర్", "అక్టోబర్", "నవంబర్", "డిసెంబర్"),
        "ta" to listOf("ஜனவரி", "பிப்ரவரி", "மார்ச்", "ஏப்ரல்", "மே", "ஜூன்", "ஜூலை", "ஆகஸ்ட்", "செப்டம்பர்", "அக்டோபர்", "நவம்பர்", "டிசம்பர்"),
        "kn" to listOf("ಜನವರಿ", "ಫೆಬ್ರವರಿ", "ಮಾರ್ಚ್", "ಏಪ್ರಿಲ್", "ಮೇ", "ಜೂನ್", "ಜುಲೈ", "ಆಗಸ್ಟ್", "ಸೆಪ್ಟೆಂಬರ್", "ಅಕ್ಟೋಬರ್", "ನವೆಂಬರ್", "ಡಿಸೆಂಬರ್"),
        "gu" to listOf("જાન્યુઆરી", "ફેબ્રુઆરી", "માર્ચ", "એપ્રિલ", "મે", "જૂન", "જુલાઈ", "ઓગસ્ટ", "સપ્ટેમ્બર", "ઓક્ટોબર", "નવેમ્બર", "ડિસેમ્બર"),
        "pa" to listOf("ਜਨਵਰੀ", "ਫ਼ਰਵਰੀ", "ਮਾਰਚ", "ਅਪ੍ਰੈਲ", "ਮਈ", "ਜੂਨ", "ਜੁਲਾਈ", "ਅਗਸਤ", "ਸਤੰਬਰ", "ਅਕਤੂਬਰ", "ਨਵੰਬਰ", "ਦਸੰਬਰ"),
        "or" to listOf("ଜାନୁଆରୀ", "ଫେବୃଆରୀ", "ମାର୍ଚ୍ଚ", "ଏପ୍ରିଲ", "ମେ", "ଜୁନ", "ଜୁଲାଇ", "ଅଗଷ୍ଟ", "ସେପ୍ଟେମ୍ବର", "ଅକ୍ଟୋବର", "ନଭେମ୍ବର", "ଡିସେମ୍ବର"),
        "ml" to listOf("ജനുവരി", "ഫെബ്രുവരി", "മാർച്ച്", "ഏപ്രിൽ", "മേയ്", "ജൂൺ", "ജൂലൈ", "ഓഗസ്റ്റ്", "സെപ്റ്റംബർ", "ഒക്ടോബർ", "നവംബർ", "ഡിസംബർ")
    )

    val englishMonths = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    fun getMonthsData(langCode: String = "en"): List<MonthAlmanac> {
        val code = langCode.lowercase()
        return baseMonthsData.map { m ->
            val localizedMonth = monthNamesMap[code]?.getOrNull(m.monthIndex - 1) ?: englishMonths[m.monthIndex - 1]
            val localizedSeason = SmartAgriToolsTranslations.getSeasonName(m.seasonName, code)
            val localizedWeather = when (code) {
                "hi" -> when (m.monthIndex) {
                    1 -> "शीतलहर और सुबह के कोहरे की चेतावनी। सर्दियों की सब्जियों में पाले से सुरक्षा करें।"
                    2 -> "तापमान में वृद्धि से दाना भराव तेज होता है। खेत में पर्याप्त नमी बनाए रखें।"
                    3 -> "गर्मी की शुरुआत। तेज वाष्पीकरण के कारण हल्की सिंचाई आवश्यक है।"
                    4 -> "भीषण गर्मी का दौर। पौधों को झुलसने से बचाने के लिए सुबह या शाम को पानी दें।"
                    5 -> "कड़ी धूप। मिट्टी के हानिकारक जीवाणु नष्ट करने हेतु गहरी जुताई का सबसे अच्छा समय।"
                    6 -> "दक्षिण-पश्चिम मानसून का आगमन! मेड़ों और जल निकासी नालियों को दुरुस्त रखें।"
                    7 -> "सक्रिय मानसून बारिश। सोयाबीन, कपास और मक्का के खेतों में पानी न रुकने दें।"
                    8 -> "उच्च आर्द्रता (>80%)। फफूंद रोग और तना छेदक कीटों पर सतर्क निगरानी रखें।"
                    9 -> "मानसून वापसी चरण। अगेती खरीफ फसलों में दाना पकने के समय हल्की नमी रखें।"
                    10 -> "सुहावना मौसम। रबी फसलों की बुवाई और खेत की तैयारी का सर्वोत्तम समय।"
                    11 -> "ठंडी सुबह। अधिक उपज देने वाले गेहूं और जौ की समय पर बुवाई का मुख्य समय।"
                    12 -> "कड़ाके की ठंड। पाले से बचाव के लिए शाम के समय हल्की सिंचाई करें।"
                    else -> m.weatherTip
                }
                "mr" -> when (m.monthIndex) {
                    1 -> "थंडीची लाट व धुके. हिवाळी भाजीपाल्याचे तुषार सिंचनाने संरक्षण करा."
                    2 -> "दिवसा तापमान वाढत असल्याने दाणे भरण्याच्या अवस्थेत पुरेसा ओलावा ठेवा."
                    3 -> "उन्हाळ्याची सुरुवात. बाष्पीभवन वाढल्यामुळे वारंवार हलके पाणी द्यावे."
                    4 -> "कडक उन्हाळा. ठिबक सिंचन सकाळी किंवा संध्याकाळी चालू ठेवावे."
                    5 -> "जमीन तापवून निर्जंतुकीकरण करण्यासाठी खोल उन्हाळी नांगरट करा."
                    6 -> "मान्सूनचे आगमन! खरीप पेरणीसाठी शेताची बांधबंदिस्ती तपासा."
                    7 -> "मुसळधार पाऊस. पिकात पाणी साचू न देता पाण्याचा निचरा करा."
                    8 -> "जास्त दमट हवामान. बुरशी व कीड नियंत्रणासाठी पिकाचे सतत निरीक्षण करा."
                    9 -> "मान्सून परतीचा पाऊस. लवकर पेरलेल्या खरीप पिकांची काढणी वेळेवर करा."
                    10 -> "रब्बी हंगामाची पेरणी सुरू करण्याची उत्तम वेळ. जमिनीचा ओलावा जपा."
                    11 -> "थंड हवामान. गव्हाची वेळेवर पेरणी करण्यासाठी सर्वोत्तम महिना."
                    12 -> "तीव्र थंडी. रब्बी पिकांना गरजेनुसार रात्री हलके पाणी द्या."
                    else -> m.weatherTip
                }
                "bn" -> when (m.monthIndex) {
                    1 -> "শৈত্যপ্রবাহ ও কুয়াশা। শীতকালীন শাকসবজির যত্ন নিন।"
                    2 -> "দিনের তাপমাত্রা বাড়ছে। গম ও রবি ফসলে সেচ দিন।"
                    3 -> "গ্রীষ্মের শুরু। ঘন ঘন হালকা সেচ প্রয়োজন।"
                    4 -> "তীব্র রোদ। জমিতে আর্দ্রতা ধরে রাখতে ড্রিপ সেচ দিন।"
                    5 -> "গ্রীষ্মকালীন গভীর চাষের সেরা সময়।"
                    6 -> "বর্ষা মৌসুমের শুরু! খরিফ ফসলের বীজতলা তৈরি করুন।"
                    7 -> "ভারী বৃষ্টিপাত। জমিতে যাতে পানি না জমে সেদিকে খেয়াল রাখুন।"
                    8 -> "অতিরিক্ত আর্দ্রতা। ছত্রাক ও পোকার আক্রমণ থেকে সাবধান।"
                    9 -> "আশ্বিনের রোদ ও হালকা বৃষ্টি। খরিফ ফসল কাটার প্রস্তুতি নিন।"
                    10 -> "রবি মৌসুমের চাষের জন্য জমি প্রস্তুত করুন।"
                    11 -> "গম ও সরিষা বপনের উপযুক্ত সময়।"
                    12 -> "তীব্র শীত। ঠাণ্ডা থেকে সবজি রক্ষা করতে সেচ দিন।"
                    else -> m.weatherTip
                }
                "te" -> when (m.monthIndex) {
                    1 -> "చలిగాలుల తీవ్రత. శీతాకాలపు కూరగాయల పంటలను రక్షించండి."
                    2 -> "ఉష్ణోగ్రతలు పెరుగుతాయి. తగినంత తేమను అందించండి."
                    3 -> "ఎండలు ప్రారంభం. తరచుగా తేలికపాటి నీరు ఇవ్వండి."
                    4 -> "ఎండ తీవ్రత ఎక్కువ. ఉదయం లేదా సాయంత్రం నీరు పెట్టండి."
                    5 -> "వేసవి లోతు దుక్కులకు అనుకూల సమయం."
                    6 -> "ఖరీఫ్ వర్షాకాలం ప్రారంభం! విత్తనాలు వేయడానికి సిద్ధం చేయండి."
                    7 -> "వర్షాలు సమృద్ధిగా కురుస్తాయి. మురుగు నీటిని తీసివేయండి."
                    8 -> "అధిక తేమ. తెగుళ్లు మరియు పురుగుల పట్ల జాగ్రత్త వహించండి."
                    9 -> "ఖరీఫ్ పంటల కోత మరియు ఆరబెట్టడం."
                    10 -> "రబీ పంటల విత్తన శుద్ధి మరియు సాగు ప్రారంభం."
                    11 -> "గోధుమలు, ఆవాలు విత్తడానికి అనుకూల సమయం."
                    12 -> "చలి తీవ్రత. రాత్రి వేళల్లో తేలికపాటి తడులు ఇవ్వండి."
                    else -> m.weatherTip
                }
                "ta" -> when (m.monthIndex) {
                    1 -> "குளிர் காலம். பயிர்களை பனிப்பொழிவிலிருந்து பாதுகாக்கவும்."
                    2 -> "தானியங்கள் முதிரும் பருவம். சீரான பாசனம் தேவை."
                    3 -> "கோடை வெயில் தொடக்கம். அடிக்கடி நீர் பாய்ச்சவும்."
                    4 -> "கடும் வெயில். சொட்டுநீர் பாசனத்தை காலையில் இயக்கவும்."
                    5 -> "கோடை உழவு செய்ய சிறந்த காலம்."
                    6 -> "தென்மேற்கு பருவமழை தொடக்கம். விதைப்புக்கு உகந்தது."
                    7 -> "மழைக்காலம். வடிகால் வசதியை உறுதி செய்யவும்."
                    8 -> "அதிக ஈரப்பதம். பூச்சி, பூஞ்சான தாக்குதலை கண்காணிக்கவும்."
                    9 -> "பயிர்களை அறுவடை செய்ய தயாராகுங்கள்."
                    10 -> "குளிர்கால ரபி பயிர்களை விதைக்க சிறந்த பருவம்."
                    11 -> "கோதுமை மற்றும் பயறு வகைகளை விதைக்கவும்."
                    12 -> "பனிப்பொழிவு அதிகம். மாலை நேர பாசனம் செய்யவும்."
                    else -> m.weatherTip
                }
                else -> m.weatherTip
            }

            val localizedActivities = m.activities.map { act ->
                val transCrop = SmartAgriToolsTranslations.getCropName(act.crop, code)
                act.copy(crop = transCrop)
            }

            m.copy(
                monthName = localizedMonth,
                seasonName = localizedSeason,
                weatherTip = localizedWeather,
                activities = localizedActivities
            )
        }
    }

    private val baseMonthsData: List<MonthAlmanac> = listOf(
        MonthAlmanac(
            monthIndex = 1,
            monthName = "January",
            seasonName = "Rabi (Winter)",
            seasonEmoji = "❄️",
            weatherTip = "Cold wave & morning fog alert. Guard against frost damage in winter vegetables.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Wheat", "Second & Third Irrigation", "Provide irrigation at late tillering / jointing stage (40-45 days after sowing).", "Morning / Afternoon"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Mustard", "Aphid Scouting & Spray", "Inspect for aphid colonies on mustard inflorescence; spray Neem oil if noticed.", "Early Morning"),
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Wheat", "Second Urea Top Dressing", "Apply 35 kg Urea/ha before second irrigation in wheat fields.", "With Irrigation")
            )
        ),
        MonthAlmanac(
            monthIndex = 2,
            monthName = "February",
            seasonName = "Rabi (Winter)",
            seasonEmoji = "❄️",
            weatherTip = "Rising daytime temperatures accelerate grain filling. Maintain adequate soil moisture.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Wheat", "Flowering Stage Irrigation", "Critical irrigation during boot leaf & flowering stage to ensure plump grain formation.", "Calm Wind Hours"),
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Mustard", "Early Rabi Harvest", "Harvest mustard when 75% of siliquae turn golden yellow to avoid shattering losses.", "Sunny Morning"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Watermelon", "Early Zaid Nursery Sowing", "Prepare nursery beds for watermelon, cucumber, and gourd under plastic mulch.", "Mid February")
            )
        ),
        MonthAlmanac(
            monthIndex = 3,
            monthName = "March",
            seasonName = "Zaid (Summer)",
            seasonEmoji = "☀️",
            weatherTip = "Onset of summer heat. Rapid evaporation requires frequent light water applications.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Wheat", "Main Rabi Harvest", "Harvest chickpea and wheat when pods turn brownish-straw colored.", "Dry Sunny Days"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Gram", "Zaid Pulse Sowing", "Sow short-duration Green Gram (Moong bean) and summer vegetables.", "Early March"),
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Maize", "Basal Organic Application", "Mix vermicompost and Trichoderma in furrows before planting summer crops.", "At Bed Preparation")
            )
        ),
        MonthAlmanac(
            monthIndex = 4,
            monthName = "April",
            seasonName = "Zaid (Summer)",
            seasonEmoji = "☀️",
            weatherTip = "Peak dry heat. Irrigate early morning or late evening to prevent thermal shock.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Tomato", "Drip Irrigation Management", "Operate drip systems for 2-3 hours daily; check dripper nozzles.", "06:00 - 08:00 AM"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Tomato", "Sucking Pest Management", "Hang yellow sticky traps (@ 15/acre) to control whiteflies and thrips.", "Weekly Inspection"),
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Wheat", "Threshing & Storage", "Dry harvested grain to <12% moisture before hermetic bag storage.", "Afternoon Drying")
            )
        ),
        MonthAlmanac(
            monthIndex = 5,
            monthName = "May",
            seasonName = "Zaid (Summer)",
            seasonEmoji = "☀️",
            weatherTip = "Intense summer heat. Ideal time for deep summer ploughing to solarize soil pathogens.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Wheat", "Deep Summer Ploughing & Solarization", "Plough fields 25-30 cm deep to expose soil-borne fungi and weed seeds to sunlight.", "Full Sun"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Cotton", "Green Manuring Sowing", "Sow green manure seeds with pre-monsoon showers to enrich organic carbon.", "Mid to Late May"),
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Watermelon", "Peak Fruit Harvest", "Harvest melons early morning when the bottom ground spot turns creamy yellow.", "Dawn")
            )
        ),
        MonthAlmanac(
            monthIndex = 6,
            monthName = "June",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "Arrival of Southwest Monsoon! Ensure field bunds and drainage channels are intact.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Rice", "Kharif Main Sowing", "Raise paddy nursery and sow Bt-Cotton, Soybean, Maize with monsoon arrival.", "After 50mm Rain"),
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Cotton", "Basal Fertilizer Dressing", "Apply Single Super Phosphate + Potash + 25% Nitrogen during final land preparation.", "Basal Placement"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Rice", "Seed Treatment Protocol", "Treat seeds with Fungicide -> Insecticide -> Rhizobium bio-culture.", "Before Sowing")
            )
        ),
        MonthAlmanac(
            monthIndex = 7,
            monthName = "July",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "Active monsoon showers. Prevent water stagnation in soybean, cotton, and maize fields.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Rice", "Mainfield Transplantation", "Transplant 21-25 day old paddy seedlings (2-3 seedlings per hill) at 20x15 cm spacing.", "Standing Water"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Cotton", "Weed Management & Interculture", "Perform manual weeding or spray recommended post-emergence herbicides within 20 days.", "Weed 2-3 Leaf Stage"),
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Maize", "Drainage Trench Clearing", "Open field drainage trenches to drain excess rainwater and prevent root asphyxiation.", "After Heavy Showers")
            )
        ),
        MonthAlmanac(
            monthIndex = 8,
            monthName = "August",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "High humidity (>80%). High vigilance needed against fungal blast, blight & stem borers.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Rice", "First Top Dressing of Nitrogen", "Broadcast Neem-coated Urea @ 35 kg/ha at active tillering stage.", "Drained Field"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Rice", "Stem Borer & Leaf Blast Watch", "Install pheromone traps and spray Chlorantraniliprole if pest ETL exceeded.", "Calm Morning"),
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Cotton", "Dry Spell Supplemental Irrigation", "If monsoon takes a 10-day dry break, give protective life-saving irrigation.", "Evening")
            )
        ),
        MonthAlmanac(
            monthIndex = 9,
            monthName = "September",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "Monsoon retreat phase. Grain filling in early kharif crops. Maintain moist soil.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Rice", "Panicle Initiation Top Dressing", "Final split of Urea + MOP potash application to support bold grain formation.", "Panicle Stage"),
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Soybean", "Early Kharif Harvest", "Harvest soybean when leaves turn yellow and drop; pods turn golden brown.", "Dry Weather"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Cotton", "Pink Bollworm Scouting", "Inspect green cotton bolls for internal rosetting or entry pinholes.", "Weekly")
            )
        ),
        MonthAlmanac(
            monthIndex = 10,
            monthName = "October",
            seasonName = "Rabi (Winter)",
            seasonEmoji = "❄️",
            weatherTip = "Pleasant autumn days with cool nights. Best time for Rabi seedbed preparation.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Rice", "Main Kharif Harvest", "Harvest paddy when 80-85% grains turn straw golden; dry paddy on tarpaulin.", "Bright Sunshine"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Mustard", "Early Rabi Sowing", "Sow Mustard, Chickpea (Chana), Lentil (Masoor) and Rabi vegetables.", "Mid October"),
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Gram", "Basal Manuring & PSB Inoculation", "Incorporate 8-10 tonnes FYM/ha and DAP @ 50 kg/ha in seed furrows.", "Before Sowing")
            )
        ),
        MonthAlmanac(
            monthIndex = 11,
            monthName = "November",
            seasonName = "Rabi (Winter)",
            seasonEmoji = "❄️",
            weatherTip = "Crisp cool mornings. Optimum window for sowing high-yield Wheat and Barley.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Wheat", "Timely Wheat Sowing", "Sow certified wheat varieties (HD-3086, DBW-187, GW-322) using seed drill.", "Nov 01 - Nov 25"),
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Mustard", "Pre-flowering Irrigation", "Light irrigation before flowering in mustard and branching stage in chickpea.", "Morning Hours"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Potato", "Late Blight Preventive Spray", "Foliar spray of Mancozeb @ 2.5g/L as preventive barrier against late blight.", "Clear Morning")
            )
        ),
        MonthAlmanac(
            monthIndex = 12,
            monthName = "December",
            seasonName = "Rabi (Winter)",
            seasonEmoji = "❄️",
            weatherTip = "Peak winter chill. Protect tender vegetables with straw mulching and windbreaks.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Wheat", "CRI Stage First Irrigation", "Crown Root Initiation (21 days after sowing) irrigation is compulsory for tillering.", "21-25 Days Post Sowing"),
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Wheat", "First Nitrogen Top Dressing", "Broadcast 35-40 kg Urea/ha immediately following first CRI irrigation.", "Post Irrigation"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Gram", "Cutworm & Weed Control", "Hoeing and weeding in chickpea, mustard, and garlic beds to keep rows clean.", "Mid December")
            )
        )
    )

    val monthsData: List<MonthAlmanac> get() = getMonthsData("en")
}
