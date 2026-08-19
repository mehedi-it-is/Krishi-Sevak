package com.krishisevak.app.data.engine

enum class AlmanacActivityType(val label: String, val icon: String, val badgeColorHex: Long) {
    SOWING("Sowing & Planting", "🌱", 0xFF22C55E),
    WATERING("Irrigation", "💧", 0xFF3B82F6),
    FERTILIZING("Fertilization", "⚡", 0xFFF59E0B),
    PEST_CONTROL("Pest & Disease Control", "🛡️", 0xFFEF4444),
    HARVESTING("Harvesting & Post-Harvest", "🌾", 0xFF8B5CF6);

    fun getLocalizedLabel(langCode: String): String {
        return when (langCode.lowercase()) {
            "hi" -> when (this) {
                SOWING -> "बुवाई व रोपाई"
                WATERING -> "सिंचाई प्रबंधन"
                FERTILIZING -> "उर्वरक व खाद"
                PEST_CONTROL -> "कीट व रोग नियंत्रण"
                HARVESTING -> "कटाई व भंडारण"
            }
            "bn" -> when (this) {
                SOWING -> "বপন ও রোপণ"
                WATERING -> "সেচ ব্যবস্থাপনা"
                FERTILIZING -> "সার প্রয়োগ"
                PEST_CONTROL -> "বালাই দমন"
                HARVESTING -> "ফসল কাটা ও মাড়াই"
            }
            "mr" -> when (this) {
                SOWING -> "पेरणी व लागवड"
                WATERING -> "पाणी व्यवस्थापन"
                FERTILIZING -> "खत व्यवस्थापन"
                PEST_CONTROL -> "कीड व रोग नियंत्रण"
                HARVESTING -> "काढणी व साठवणूक"
            }
            "te" -> when (this) {
                SOWING -> "విత్తడం మరియు నాటడం"
                WATERING -> "నీటి యాజమాన్యం"
                FERTILIZING -> "ఎరువుల యాజమాన్యం"
                PEST_CONTROL -> "సస్యరక్షణ"
                HARVESTING -> "కోత మరియు నిల్వ"
            }
            "ta" -> when (this) {
                SOWING -> "விதைப்பு மற்றும் நடுவு"
                WATERING -> "நீர்ப்பாசன மேலாண்மை"
                FERTILIZING -> "உர மேலாண்மை"
                PEST_CONTROL -> "பூச்சி நோய் கட்டுப்பாடு"
                HARVESTING -> "அறுவடை மற்றும் சேமிப்பு"
            }
            "kn" -> when (this) {
                SOWING -> "ಬಿತ್ತನೆ ಮತ್ತು ನಾಟಿ"
                WATERING -> "ನೀರಾವರಿ ನಿರ್ವಹಣೆ"
                FERTILIZING -> "ಗೊಬ್ಬರ ನಿರ್ವಹಣೆ"
                PEST_CONTROL -> "ಕೀಟ ಮತ್ತು ರೋಗ ನಿಯಂತ್ರಣ"
                HARVESTING -> "ಕೊಯ್ಲು ಮತ್ತು ಸಂಸ್ಕರಣೆ"
            }
            "gu" -> when (this) {
                SOWING -> "વાવણી અને રોપણી"
                WATERING -> "પિયત વ્યવસ્થાપન"
                FERTILIZING -> "ખાતર વ્યવસ્થાપન"
                PEST_CONTROL -> "રોગ-જીવાત નિયંત્રણ"
                HARVESTING -> "લણણી અને સંગ્રહ"
            }
            "pa" -> when (this) {
                SOWING -> "ਬਿਜਾਈ ਅਤੇ ਲੁਆਈ"
                WATERING -> "ਸਿੰਚਾਈ ਪ੍ਰਬੰਧ"
                FERTILIZING -> "ਖਾਦ ਪ੍ਰਬੰਧ"
                PEST_CONTROL -> "ਕੀੜੇ-ਮਕੌੜੇ ਅਤੇ ਬਿਮਾਰੀਆਂ ਦੀ ਰੋਕਥਾਮ"
                HARVESTING -> "ਵਾਢੀ ਅਤੇ ਸਾਂਭ-ਸੰਭਾਲ"
            }
            "ml" -> when (this) {
                SOWING -> "വിത്തുവിതയ്ക്കലും നടീലും"
                WATERING -> "ജലസേചന ക്രമീകരണം"
                FERTILIZING -> "വളപ്രയോഗം"
                PEST_CONTROL -> "കീടരോഗ നിയന്ത്രണം"
                HARVESTING -> "വിളവെടുപ്പും സംഭരണവും"
            }
            "or" -> when (this) {
                SOWING -> "ବୁଣା ଓ ରୋପଣ"
                WATERING -> "ଜଳସେଚନ ପରିଚାଳନା"
                FERTILIZING -> "ଖତ ଓ ସାର ପ୍ରୟୋଗ"
                PEST_CONTROL -> "କୀଟ ଓ ରୋଗ ନିୟନ୍ତ୍ରଣ"
                HARVESTING -> "ଅମଳ ଓ ସଂରକ୍ଷଣ"
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
    val seasonName: String,
    val seasonEmoji: String,
    val weatherTip: String,
    val activities: List<AlmanacActivityItem>
)

object CropAlmanacData {

    private val monthNamesMap = mapOf(
        "hi" to listOf("जनवरी", "फरवरी", "मार्च", "अप्रैल", "मई", "जून", "जुलाई", "अगस्त", "सितंबर", "अक्टूबर", "नवंबर", "दिसंबर"),
        "mr" to listOf("जानेवारी", "फेब्रुवारी", "मार्च", "एप्रिल", "मे", "जून", "जुलै", "ऑगस्ट", "सप्टेंबर", "ऑक्टोबर", "नोव्हेंबर", "डिसेंबर"),
        "bn" to listOf("জানুয়ারী", "ফেব্রুয়ারী", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর"),
        "te" to listOf("జనవరి", "ఫిబ్రవరి", "మార్చి", "ఏప్రిల్", "మే", "జూన్", "జూలై", "ఆగస్టు", "సెప్టెంబర్", "అక్టోబర్", "నవంబర్", "డిసెంబర్"),
        "ta" to listOf("ஜனவரி", "பிப்ரவரி", "மார்ச்", "ஏப்ரல்", "மே", "ஜூன்", "ஜூலை", "ஆகஸ்ட்", "செப்டம்பர்", "அக்டோபர்", "நவம்பர்", "டிசம்பர்"),
        "kn" to listOf("ಜನವರಿ", "ಫೆಬ್ರವರಿ", "ಮಾರ್ಚ್", "ಏಪ್ರಿಲ್", "ಮೇ", "ಜೂನ್", "ಜುಲೈ", "ಆಗಸ್ಟ್", "ಸೆಪ್ಟೆಂಬರ್", "ಅಕ್ಟೋಬರ್", "ನವೆಂಬರ್", "ಡಿಸೆಂಬರ್"),
        "gu" to listOf("જાન્યુઆરી", "ફેબ્રુઆરી", "માર્ચ", "એપ્રિલ", "મે", "જૂન", "જુલાઈ", "ઓગસ્ટ", "સપ્ટેમ્બર", "ઓક્ટોબર", "નવેમ્બર", "ડિસેમ્બર"),
        "pa" to listOf("ਜਨਵਰੀ", "ਫ਼ਰਵਰੀ", "ਮਾਰਚ", "ਅਪ੍ਰੈਲ", "ਮਈ", "ਜੂਨ", "ਜੁਲਾਈ", "ਅਗਸਤ", "ਸਤੰਬਰ", "ਅਕਤੂਬਰ", "ਨਵੰਬਰ", "ਦਸੰਬਰ"),
        "ml" to listOf("ജനുവരി", "ഫെബ്രുവരി", "മാർച്ച്", "ഏപ്രിൽ", "മേയ്", "ജൂൺ", "ജൂലൈ", "ഓഗസ്റ്റ്", "സെപ്റ്റംബർ", "ഒക്ടോബർ", "നവംബർ", "ഡിസംബർ"),
        "or" to listOf("ଜାନୁଆରୀ", "ଫେବୃଆରୀ", "ମାର୍ଚ୍ଚ", "ଏପ୍ରିଲ", "ମେ", "ଜୁନ", "ଜୁଲାଇ", "ଅଗଷ୍ଟ", "ସେପ୍ଟେମ୍ବର", "ଅକ୍ଟୋବର", "ନଭେମ୍ବର", "ଡିସେମ୍ବର")
    )

    private val englishMonths = listOf(
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
                    7 -> "ভারী বৃষ্টিপাত। জমিতে যাতে জল না জমে সেদিকে খেয়াল রাখুন।"
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
                "kn" -> when (m.monthIndex) {
                    1 -> "ಚಳಿಗಾಲದ ಮಂಜು ಮತ್ತು ಶೀತಗಾಳಿ. ಚಳಿಗಾಲದ ತರಕಾರಿಗಳನ್ನು ರಕ್ಷಿಸಿ."
                    2 -> "ತಾಪಮಾನ ಏರಿಕೆಯಿಂದ ಕಾಳು ಕಟ್ಟುವ ಹಂತ. ಮಣ್ಣಿನಲ್ಲಿ ತೇವಾಂಶ ಕಾಪಾಡಿ."
                    3 -> "ಬೇಸಿಗೆಯ ಆರಂಭ. ಆಗಾಗ್ಗೆ ಲಘು ನೀರಾವರಿ ಒದಗಿಸಿ."
                    4 -> "ಕಡು ಬೇಸಿಗೆ. ಮುಂಜಾನೆ ಅಥವಾ ಸಂಜೆ ವೇಳೆ ನೀರು ಹಾಯಿಸಿ."
                    5 -> "ಬೇಸಿಗೆಯ ಆಳವಾದ ಉಳುಮೆಗೆ ಸೂಕ್ತವಾದ ಸಮಯ."
                    6 -> "ಮುಂಗಾರು ಮಳೆ ಆಗಮನ! ಮುಂಗಾರು ಬಿತ್ತನೆಗೆ ಹೊಲ ಸಿದ್ಧಪಡಿಸಿ."
                    7 -> "ಉತ್ತಮ ಮಳೆ. ಜಮೀನಿನಲ್ಲಿ ನೀರು ನಿಲ್ಲದಂತೆ ನೋಡಿಕೊಳ್ಳಿ."
                    8 -> "ಹೆಚ್ಚಿನ ತೇವಾಂಶ. ಶಿಲೀಂಧ್ರ ರೋಗಗಳ ಬಗ್ಗೆ ಎಚ್ಚರವಿರಲಿ."
                    9 -> "ಮುಂಗಾರು ಬೆಳೆಗಳ ಕೊಯ್ಲು ಮತ್ತು ಒಣಗಿಸುವಿಕೆ."
                    10 -> "ಹಿಂಗಾರು ಬೆಳೆಗಳ ಬಿತ್ತನೆಗೆ ಹೊಲ ಸಿದ್ಧತೆ ಮಾಡಿ."
                    11 -> "ಗೋಧಿ ಮತ್ತು ಸಾಸಿವೆ ಬಿತ್ತನೆಗೆ ಪ್ರಶಸ್ತ ಕಾಲ."
                    12 -> "ತೀವ್ರ ಚಳಿ. ರಾತ್ರಿ ವೇಳೆ ಲಘು ನೀರಾವರಿ ನೀಡಿ."
                    else -> m.weatherTip
                }
                "gu" -> when (m.monthIndex) {
                    1 -> "ઠંડીનું મોજું અને ઝાકળ. શિયાળુ શાકભાજીનું રક્ષણ કરો."
                    2 -> "ગરમી વધતાં દાણા ભરાવાની પ્રક્રિયા. જમીનમાં પૂરતો ભેજ રાખો."
                    3 -> "ઉનાળાની શરૂઆત. વારંવાર હળવું પિયત આપો."
                    4 -> "તીવ્ર ગરમી. સવાર અથવા સાંજે પિયત આપવું."
                    5 -> "ઉનાળુ ઊંડી ખેડ કરવા માટે શ્રેષ્ઠ સમય."
                    6 -> "ચોમાસાનું આગમન! ખરીફ વાવણીની તૈયારી કરો."
                    7 -> "સારો વરસાદ. ખેતરમાંથી પાણીના નિકાલની વ્યવસ્થા રાખો."
                    8 -> "વધુ ભેજ. ફૂગ અને જીવાત પર નજર રાખો."
                    9 -> "ખરીફ પાકની લણણી અને સુકવણી."
                    10 -> "રવિ પાકની વાવણી માટે જમીન તૈયાર કરો."
                    11 -> "ઘઉં અને રાઈની વાવણી માટે ઉત્તમ સમય."
                    12 -> "કડકડતી ઠંડી. રાત્રે હળવું પિયત આપો."
                    else -> m.weatherTip
                }
                "pa" -> when (m.monthIndex) {
                    1 -> "ਸੀਤ ਲਹਿਰ ਅਤੇ ਕੋਹਰੇ ਦੀ ਚਿਤਾਵਨੀ। ਸਬਜ਼ੀਆਂ ਨੂੰ ਕੋਰੇ ਤੋਂ ਬਚਾਓ।"
                    2 -> "ਤਾਪਮਾਨ ਵਧਣ ਨਾਲ ਦਾਣਾ ਭਰਨ ਦਾ ਸਮਾਂ। ਨਮੀ ਬਣਾਈ ਰੱਖੋ।"
                    3 -> "ਗਰਮੀ ਦੀ ਸ਼ੁਰੂਆਤ। ਹਲਕੀ ਸਿੰਚਾਈ ਜ਼ਰੂਰੀ ਹੈ।"
                    4 -> "ਭਾਰੀ ਗਰਮੀ ਦਾ ਦੌਰ। ਸਵੇਰੇ ਜਾਂ ਸ਼ਾਮ ਨੂੰ ਪਾਣੀ ਦਿਓ।"
                    5 -> "ਡੂੰਘੀ ਵਾਹੀ ਕਰਨ ਦਾ ਵਧੀਆ ਸਮਾਂ।"
                    6 -> "ਮਾਨਸੂਨ ਦੀ ਆਮਦ! ਸਾਉਣੀ ਦੀ ਬਿਜਾਈ ਦੀ ਤਿਆਰੀ ਕਰੋ।"
                    7 -> "ਭਾਰੀ ਮੀਂਹ। ਪਾਣੀ ਦੀ ਨਿਕਾਸੀ ਦਾ ਪ੍ਰਬੰਧ ਰੱਖੋ।"
                    8 -> "ਜ਼ਿਆਦਾ ਨਮੀ। ਉੱਲੀ ਰੋਗਾਂ ਤੋਂ ਸਾਵਧਾਨ ਰਹੋ।"
                    9 -> "ਸਾਉਣੀ ਦੀਆਂ ਫ਼ਸਲਾਂ ਦੀ ਵਾਢੀ ਸ਼ੁਰੂ ਕਰੋ।"
                    10 -> "ਹਾੜ੍ਹੀ ਦੀਆਂ ਫ਼ਸਲਾਂ ਦੀ ਬਿਜਾਈ ਲਈ ਜ਼ਮੀਨ ਤਿਆਰ ਕਰੋ।"
                    11 -> "ਕਣਕ ਦੀ ਸਮੇਂ ਸਿਰ ਬਿਜਾਈ ਦਾ ਮੁੱਖ ਸਮਾਂ।"
                    12 -> "ਕੜਾਕੇ ਦੀ ਠੰਢ। ਰਾਤ ਵੇਲੇ ਹਲਕਾ ਪਾਣੀ ਲਗਾਓ।"
                    else -> m.weatherTip
                }
                "ml" -> when (m.monthIndex) {
                    1 -> "തണുപ്പുകാലം. ശീതകാല പച്ചക്കറികൾ സംരക്ഷിക്കുക."
                    2 -> "ചൂട് കൂടുന്നതിനാൽ ഈർപ്പം നിലനിർത്തുക."
                    3 -> "വേനൽക്കാല ആരംഭം. ഇടയ്ക്കിടെ നേരിയ നന നൽകുക."
                    4 -> "കടുത്ത വേനൽ. രാവിലെയും വൈകുന്നേരവും നനയ്ക്കുക."
                    5 -> "വേനൽക്കാല ഉഴവിന് അനുയോജ്യമായ സമയം."
                    6 -> "മഴക്കാലം ആരംഭിക്കുന്നു! വിത്ത് നടാൻ പാടമൊരുക്കുക."
                    7 -> "കനത്ത മഴ. വെള്ളക്കെട്ട് ഒഴിവാക്കുക."
                    8 -> "ഉയർന്ന അന്തരീക്ഷ ഈർപ്പം. കീടബാധ ശ്രദ്ധിക്കുക."
                    9 -> "വിളവെടുപ്പിനുള്ള തയ്യാറെടുപ്പ് നടത്തുക."
                    10 -> "ശീതകാല കൃഷിക്കായി നിലമൊരുക്കുക."
                    11 -> "വിളകൾക്ക് ആവശ്യാനുസരണം വളം നൽകുക."
                    12 -> "തണുപ്പുള്ള കാലാവസ്ഥ. കൃത്യമായ നന നൽകുക."
                    else -> m.weatherTip
                }
                "or" -> when (m.monthIndex) {
                    1 -> "ଶୀତ ଲହରୀ ଓ କୁହୁଡ଼ି। ପନିପରିବା ଫସଲକୁ ଶୀତରୁ ରକ୍ଷା କରନ୍ତୁ।"
                    2 -> "ତାପମାତ୍ରା ବୃଦ୍ଧି ସହ ଦାନା ଭରିବା ସମୟ। ଜମିରେ ଆର୍ଦ୍ରତା ରଖନ୍ତୁ।"
                    3 -> "ଗ୍ରୀଷ୍ମ ଋତୁର ଆରମ୍ଭ। ହାଲୁକା ଜଳସେଚନ କରନ୍ତୁ।"
                    4 -> "ପ୍ରବଳ ଗ୍ରୀଷ୍ମ ପ୍ରବାହ। ସକାଳେ ବା ସନ୍ଧ୍ୟାରେ ପାଣି ଦିଅନ୍ତୁ।"
                    5 -> "ଗ୍ରୀଷ୍ମକାଳୀନ ଗଭୀର ଚାଷ ପାଇଁ ଉପଯୁକ୍ତ ସମୟ।"
                    6 -> "ମୌସୁମୀର ଆଗମନ! ଖରିଫ ଚାଷ ପାଇଁ ଜମି ପ୍ରସ୍ତୁତ କରନ୍ତୁ।"
                    7 -> "ପ୍ରବଳ ବର୍ଷା। ଜଳ ନିଷ୍କାସନ ବ୍ୟବସ୍ଥା ଠିକ ରଖନ୍ତୁ।"
                    8 -> "ଅଧିକ ଆର୍ଦ୍ରତା। କବକ ଓ କୀଟ ରୋଗ ଉପରେ ନଜର ରଖନ୍ତୁ।"
                    9 -> "ଖରିଫ ଫସଲ ଅମଳ ପାଇଁ ପ୍ରସ୍ତୁତ ହୁଅନ୍ତୁ।"
                    10 -> "ରବି ଚାଷ ପାଇଁ ଜମି ପ୍ରସ୍ତୁତ କରନ୍ତୁ।"
                    11 -> "ଗହମ ଓ ସୋରିଷ ବୁଣିବାର ପ୍ରକୃଷ୍ଟ ସମୟ।"
                    12 -> "ପ୍ରବଳ ଥଣ୍ଡା। ସନ୍ଧ୍ୟା ସମୟରେ ହାଲୁକା ଜଳସେଚନ କରନ୍ତୁ।"
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
            weatherTip = "Rising daytime temperatures accelerate grain filling. Ensure adequate soil moisture.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Wheat", "Flowering Stage Irrigation", "Ensure irrigation at flowering/milking stage; avoid water stress during grain formation.", "Calm Wind Hours"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Gram", "Pod Borer Surveillance", "Install pheromone traps (5/ha) to monitor Helicoverpa armigera pod borer.", "Evening"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Watermelon", "Summer Melon Sowing", "Sow watermelon, muskmelon, and summer squash on raised beds with drip.", "Mid to Late Feb")
            )
        ),
        MonthAlmanac(
            monthIndex = 3,
            monthName = "March",
            seasonName = "Zaid (Summer)",
            seasonEmoji = "☀️",
            weatherTip = "Onset of summer heat. High evaporation requires frequent light irrigations.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Mustard", "Mustard & Chickpea Harvest", "Harvest when siliquae turn yellowish-brown; thresh early to minimize seed shattering.", "Dry Sunny Days"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Moong", "Summer Moong Sowing", "Sow early maturing green gram (Samrat, IPM 205-7) after mustard harvest.", "First Fortnight"),
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Sugarcane", "Spring Sugarcane Irrigation", "Maintain regular 10-day irrigation cycle for germinating sugarcane setts.", "Early Morning")
            )
        ),
        MonthAlmanac(
            monthIndex = 4,
            monthName = "April",
            seasonName = "Zaid (Summer)",
            seasonEmoji = "☀️",
            weatherTip = "Peak summer heat and dry winds. Irrigate crops during early morning or late evening.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Wheat", "Wheat Harvest & Combine", "Harvest when grain moisture drops below 12%. Store grains in sanitized, dry bins.", "Sunny Dry Days"),
                AlmanacActivityItem(AlmanacActivityType.WATERING, "Moong", "Moong Flowering Irrigation", "Provide critical irrigation at flowering stage in summer moong & urad crops.", "Cool Evenings"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Tomato", "Whitefly & Mite Management", "Spray yellow sticky traps (10/acre) to prevent leaf curl viral infections.", "Early Morning")
            )
        ),
        MonthAlmanac(
            monthIndex = 5,
            monthName = "May",
            seasonName = "Zaid (Summer)",
            seasonEmoji = "☀️",
            weatherTip = "Intense solar radiation. Best period for deep summer ploughing to eradicate soil-borne pests.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Field Prep", "Deep Summer Ploughing", "Deep plough with MB plough to expose weed seeds and soil grubs to sun heat.", "Mid May"),
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Soil Health", "Green Manuring Sowing", "Sow Dhaincha (Sesbania) or Sunhemp @ 20 kg/acre as green manure crop.", "Pre-Monsoon"),
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Moong", "Summer Moong Pod Picking", "First picking of mature moong pods; second flush to follow in 10 days.", "Morning Hours")
            )
        ),
        MonthAlmanac(
            monthIndex = 6,
            monthName = "June",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "Southwest Monsoon onset! Inspect field bunds and prepare nursery beds for Paddy.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Paddy", "Paddy Nursery Sowing", "Sow nursery beds with treated seeds (Carbendazim @ 2g/kg) for long-duration rice.", "June 01 - June 20"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Cotton", "Kharif Cotton & Maize Sowing", "Sow Bt-Cotton and hybrid maize after receiving 75-100 mm pre-monsoon rain.", "Post First Rains"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Soybean", "Soybean Sowing with BBF", "Adopt Broad Bed Furrow (BBF) seed drill for soybean to manage drainage.", "Mid to Late June")
            )
        ),
        MonthAlmanac(
            monthIndex = 7,
            monthName = "July",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "Active monsoon rains. Ensure field drainage to prevent waterlogging in Maize and Pulses.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Paddy", "Paddy Main Field Transplanting", "Transplant 21-25 day old seedlings @ 2-3 seedlings/hill with 20x15 cm spacing.", "Active Monsoon"),
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Paddy", "Basal Fertilization", "Apply 50% Urea, 100% DAP and 100% MOP before final puddling in paddy.", "At Transplanting"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Cotton", "Sucking Pest Scouting", "Monitor for jassids and thrips; spray Neem seed kernel extract (NSKE 5%).", "Clear Rain Gaps")
            )
        ),
        MonthAlmanac(
            monthIndex = 8,
            monthName = "August",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "High humidity (>80%). Vigilant scouting for fungal blast, sheath blight, and stem borers.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Paddy", "Tillering Urea Top Dressing", "Broadcast 30 kg Urea/ha at active tillering (25-30 days post transplanting).", "Drain Excess Water"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Paddy", "Stem Borer & Leaf Folder", "Apply Cartap Hydrochloride 4G @ 7.5 kg/acre if dead hearts exceed 5%.", "Morning"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Soybean", "Semilooper & Girdle Beetle", "Spray Chlorantraniliprole 18.5 SC @ 0.3 ml/L for chewing caterpillars.", "Rainless Window")
            )
        ),
        MonthAlmanac(
            monthIndex = 9,
            monthName = "September",
            seasonName = "Kharif (Monsoon)",
            seasonEmoji = "🌧️",
            weatherTip = "Monsoon withdrawal phase. Maintain light moisture during grain filling in early Kharif crops.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.FERTILIZING, "Paddy", "Panicle Initiation Top Dressing", "Final Urea top dressing (25 kg/ha) + Potash (15 kg/ha) at booting stage.", "Before Flag Leaf"),
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Maize", "Kharif Maize & Soybean Harvest", "Harvest when outer husks dry and grain black layer appears; dry below 14% moisture.", "Dry Days"),
                AlmanacActivityItem(AlmanacActivityType.PEST_CONTROL, "Cotton", "Pink Bollworm Pheromone Traps", "Install PBW lures @ 8 traps/acre; spray Emamectin Benzoate if rosette flowers seen.", "Mid September")
            )
        ),
        MonthAlmanac(
            monthIndex = 10,
            monthName = "October",
            seasonName = "Rabi (Winter)",
            seasonEmoji = "❄️",
            weatherTip = "Pleasant autumn weather. Optimal window for land prep and sowing of winter Rabi crops.",
            activities = listOf(
                AlmanacActivityItem(AlmanacActivityType.HARVESTING, "Paddy", "Kharif Paddy Harvest", "Drain water 10 days before harvest; use combine harvester at 80% grain maturity.", "Full Sunlight"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Mustard", "Mustard & Chickpea Sowing", "Sow treated mustard (Pusa Mustard 30, Giriraj) and desi chickpea (JG-11, JAKI-9218).", "Oct 10 - Oct 30"),
                AlmanacActivityItem(AlmanacActivityType.SOWING, "Potato", "Autumn Potato Planting", "Plant sprouted seed tubers on ridges @ 60x20 cm with balanced NPK + FYM.", "Mid October")
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
