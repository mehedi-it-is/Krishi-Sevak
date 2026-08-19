package com.krishisevak.app.utils

data class ChatRecommendation(
    val icon: String,
    val category: String,
    val prompt: String
)

object ChatRecommendations {

    fun getTitle(langCode: String): String {
        return when (langCode.lowercase().trim()) {
            "hi" -> "💡 सुझाए गए महत्वपूर्ण प्रश्न"
            "bn" -> "💡 প্রস্তাবিত গুরুত্বপূর্ণ প্রশ্ন"
            "mr" -> "💡 सुचवलेले महत्त्वाचे प्रश्न"
            "te" -> "💡 సిఫార్సు చేసిన ముఖ్యమైన ప్రశ్నలు"
            "ta" -> "💡 பரிந்துரைக்கப்பட்ட முக்கிய கேள்விகள்"
            "kn" -> "💡 ಶಿಫಾರಸು ಮಾಡಲಾದ ಪ್ರಮುಖ ಪ್ರಶ್ನೆಗಳು"
            "ml" -> "💡 നിർദ്ദേശിച്ച പ്രധാന ചോദ്യങ്ങൾ"
            "gu" -> "💡 ભલામણ કરેલ મહત્વપૂર્ણ પ્રશ્નો"
            "pa" -> "💡 ਸਿਫ਼ਾਰਸ਼ ਕੀਤੇ ਅਹਿਮ ਸਵਾਲ"
            "or", "od" -> "💡 ସୁପାରିଶ କରାଯାଇଥିବା ଗୁରୁତ୍ୱପୂର୍ଣ୍ଣ ପ୍ରଶ୍ନ"
            else -> "💡 Recommended Farming Questions"
        }
    }

    fun getSubtitle(langCode: String): String {
        return when (langCode.lowercase().trim()) {
            "hi" -> "किसी भी प्रश्न पर टैप करें या अपना प्रश्न बोलें / लिखें"
            "bn" -> "যেকোনো প্রশ্নে ট্যাপ করুন অথবা আপনার প্রশ্ন বলুন / লিখুন"
            "mr" -> "कोणत्याही प्रश्नावर टॅप करा किंवा आपला प्रश्न बोला / लिहा"
            "te" -> "ఏదైనా ప్రశ్నపై నొక్కండి లేదా మీ ప్రశ్నను మాట్లాడండి / వ్రాయండి"
            "ta" -> "ஏதேனும் கேள்வியைத் தட்டவும் அல்லது உங்கள் கேள்வியைப் பேசவும் / தட்டச்சு செய்யவும்"
            "kn" -> "ಯಾವುದೇ ಪ್ರಶ್ನೆಯ ಮೇಲೆ ಟ್ಯಾಪ್ ಮಾಡಿ ಅಥವಾ ನಿಮ್ಮ ಪ್ರಶ್ನೆಯನ್ನು ಮಾತನಾಡಿ / ಬರೆಯಿರಿ"
            "ml" -> "ഏതെങ്കിലും ചോദ്യത്തിൽ ടാപ്പ് ചെയ്യുക അല്ലെങ്കിൽ നിങ്ങളുടെ ചോദ്യം സംസാരിക്കുക / എഴുതുക"
            "gu" -> "કોઈપણ પ્રશ્ન પર ટેપ કરો અથવા તમારો પ્રશ્ન બોલો / લખો"
            "pa" -> "ਕਿਸੇ ਵੀ ਸਵਾਲ 'ਤੇ ਟੈਪ ਕਰੋ ਜਾਂ ਆਪਣਾ ਸਵਾਲ ਬੋਲੋ / ਲਿਖੋ"
            "or", "od" -> "ଯେକୌଣସି ପ୍ରଶ୍ନ ଉପରେ ଟ୍ୟାପ୍ କରନ୍ତୁ କିମ୍ବା ଆପଣଙ୍କ ପ୍ରଶ୍ନ କୁହନ୍ତୁ / ଲେଖନ୍ତୁ"
            else -> "Tap any question below or type/speak your query"
        }
    }

    fun getRecommendations(langCode: String): List<ChatRecommendation> {
        return recommendations[langCode.lowercase().trim()] ?: recommendations["en"] ?: emptyList()
    }

    private val recommendations = mapOf(
        // 1. ENGLISH (en)
        "en" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "Crop Health",
                prompt = "How to treat yellowing leaves, rust, and pest damage in crops?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "Fertilizers",
                prompt = "What is the best fertilizer and NPK dosage for flowering and fruiting stage?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "Mandi Prices",
                prompt = "What are today's mandi market rates and best selling tips?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "Weather & Spray",
                prompt = "How should I plan field irrigation and spraying according to weather?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "Govt Schemes",
                prompt = "How to apply for PM-Kisan subsidy and solar pump schemes?"
            )
        ),

        // 2. HINDI (hi)
        "hi" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "फसल स्वास्थ्य",
                prompt = "गेहूं और धान में पत्तियों के पीलेपन और कीटों की रोकथाम कैसे करें?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "खाद एवं उर्वरक",
                prompt = "फूल और फल आते समय कौन सा खाद और NPK छिड़कना सबसे अच्छा है?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "मंडी भाव",
                prompt = "आज की मंडी में फसलों के ताजा भाव और बेचने का सही समय क्या है?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "मौसम व सिंचाई",
                prompt = "आने वाले मौसम और बारिश को देखते हुए सिंचाई और दवा का छिड़काव कब करें?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "सरकारी योजनाएं",
                prompt = "पीएम किसान सम्मान निधि और सोलर पंप सब्सिडी के लिए कैसे आवेदन करें?"
            )
        ),

        // 3. BENGALI (bn)
        "bn" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "ফসলের রোগ ও পোকা",
                prompt = "ধান ও গমের পাতা হলুদ হওয়া এবং মাজরা পোকা কীভাবে দমন করব?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "সার ও পুষ্টি",
                prompt = "ফুল ও ফল আসার সময় কোন সার এবং এনপিকে স্প্রে করা সবচেয়ে ভালো?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "মান্ডি দর",
                prompt = "আজকের মান্ডিতে ফসলের তাজা দর এবং বিক্রির সঠিক সময় কী?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "আবহাওয়া ও সেচ",
                prompt = "আবহাওয়ার পূর্বাভাস অনুযায়ী সেচ এবং কীটনাশক স্প্রে কখন করব?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "সরকারি প্রকল্প",
                prompt = "পিএম কিষাণ এবং সৌর পাম্প ভর্তুকির জন্য কীভাবে আবেদন করবেন?"
            )
        ),

        // 4. MARATHI (mr)
        "mr" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "पीक संरक्षण",
                prompt = "पिकांच्या पानांचा पिवळेपणा, तांबेरा व अळी नियंत्रणासाठी काय करावे?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "खत व्यवस्थापन",
                prompt = "फुलधारणा व फळधारणेच्या वेळी कोणते खत व NPK डोस द्यावा?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "बाजार भाव",
                prompt = "आजच्या बाजार समितीमध्ये पिकांचे ताजे भाव आणि विक्रीचा सल्ला काय आहे?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "हवामान व पाणी",
                prompt = "हवामानाचा अंदाज पाहून शेतात पाणी व्यवस्थापन व फवारणी कशी करावी?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "शासकीय योजना",
                prompt = "पीएम किसान योजना आणि सौर कृषी पंप अनुदानासाठी कसा अर्ज करावा?"
            )
        ),

        // 5. TELUGU (te)
        "te" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "పంట సంరక్షణ",
                prompt = "వరి మరియు పత్తిలో ఆకులు పసుపు రంగులోకి మారడం, పురుగుల నివారణ ఎలా?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "ఎరువుల మోతాదు",
                prompt = "పూత మరియు కాత సమయంలో ఏ ఎరువు మరియు NPK మోతాదు వేయాలి?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "మార్కెట్ ధరలు",
                prompt = "నేటి మార్కెట్ యార్డులో తాజా పంట ధరలు మరియు విక్రయ సలహాలు ఏమిటి?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "వాతావరణం & తడులు",
                prompt = "వాతావరణ అంచనా ప్రకారం పొలానికి నీటి తడులు మరియు మందుల పిచಿಕారీ ఎప్పుడు చేయాలి?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "ప్రభుత్వ పథకాలు",
                prompt = "పీఎం కిసాన్ మరియు సోలార్ పంపు సబ్సిడీ కోసం ఎలా దరఖాస్తు చేసుకోవాలి?"
            )
        ),

        // 6. TAMIL (ta)
        "ta" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "பயிர் பாதுகாப்பு",
                prompt = "நெற்பயிரில் இலைகள் மஞ்சள் நிறமாதல் மற்றும் பூச்சி தாக்குதலை கட்டுப்படுத்துவது எப்படி?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "உர மேலாண்மை",
                prompt = "பூக்கும் மற்றும் காய்க்கும் தருணத்தில் எந்த உரம் மற்றும் NPK தெளிப்பது நல்லது?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "சந்தை நிலவரம்",
                prompt = "இன்றைய சந்தையில் பயிர்களின் விலை நிலவரம் மற்றும் விற்பனை ஆலோசனை என்ன?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "வானிலை & பாசனம்",
                prompt = "வானிலை முன்னறிவிப்பின்படி பாசனம் மற்றும் பூச்சிக்கொல்லி தெளிப்பை எவ்வாறு திட்டமிடுவது?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "அரசு திட்டங்கள்",
                prompt = "பிஎம் கிசான் மற்றும் சூரிய சக்தி பம்பு மானியத்திற்கு எவ்வாறு விண்ணப்பிப்பது?"
            )
        ),

        // 7. KANNADA (kn)
        "kn" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "ಬೆಳೆ ರಕ್ಷಣೆ",
                prompt = "ಭತ್ತ ಮತ್ತು ಇತರ ಬೆಳೆಗಳಲ್ಲಿ ಎಲೆ ಹಳದಿಯಾಗುವುದು ಮತ್ತು ಕೀಟ ನಿಯಂತ್ರಣ ಹೇಗೆ?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "ಗೊಬ್ಬರ ಪ್ರಮಾಣ",
                prompt = "ಹೂವು ಮತ್ತು ಕಾಯಿ ಬಿಡುವ ಹಂತದಲ್ಲಿ ಯಾವ ಗೊಬ್ಬರ ಮತ್ತು NPK ಸಿಂಪಡಿಸಬೇಕು?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "ಮಾರುಕಟ್ಟೆ ದರ",
                prompt = "ಇಂದಿನ ಮಾರುಕಟ್ಟೆಯಲ್ಲಿ ಬೆಳೆಗಳ ಪ್ರಸ್ತುತ ದರಗಳು ಮತ್ತು ಮಾರಾಟದ ಸಲಹೆಗಳೇನು?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "ಹವಾಮಾನ & ನೀರಾವರಿ",
                prompt = "ಹವಾಮಾನ ಮುನ್ಸೂಚನೆ ನೋಡಿ ಬೆಳೆಗೆ ನೀರಾವರಿ ಮತ್ತು ಸಿಂಪಡಣೆ ಯಾವಾಗ ಮಾಡಬೇಕು?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "ಸರ್ಕಾರಿ ಯೋಜನೆಗಳು",
                prompt = "ಪಿಎಂ ಕಿಸಾನ್ ಮತ್ತು ಸೋಲಾರ್ ಪಂಪ್ ಸಬ್ಸಿಡಿಗೆ ಅರ್ಜಿ ಸಲ್ಲಿಸುವುದು ಹೇಗೆ?"
            )
        ),

        // 8. MALAYALAM (ml)
        "ml" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "വിള സംരക്ഷണം",
                prompt = "നെല്ലിലെയും മറ്റ് വിളകളിലെയും ഇല മഞ്ഞളിപ്പും കീടബാധയും എങ്ങനെ തടയാം?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "വളപ്രയോഗം",
                prompt = "പൂവിടുന്ന സമയത്ത് ഏറ്റവും അനുയോജ്യമായ വളവും NPK അളവും എന്താണ്?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "വിപണി വില",
                prompt = "ഇന്നത്തെ മാർക്കറ്റിലെ വിളകളുടെ വിലനിലവാരവും വിൽപന നിർദ്ദേശങ്ങളും എന്തൊക്കെയാണ്?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "കാലാവസ്ഥയും നനയും",
                prompt = "കാലാവസ്ഥാ പ്രവചനമനുസരിച്ച് നനയ്ക്കലും മരുന്ന് തളിക്കലും എങ്ങനെ ക്രമീകരിക്കണം?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "സർക്കാർ പദ്ധതികൾ",
                prompt = "പിഎം കിസാനും സോളാർ പമ്പ് സബ്‌സിഡിക്കും എങ്ങനെ അപേക്ഷിക്കാം?"
            )
        ),

        // 9. GUJARATI (gu)
        "gu" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "પાક સંરક્ષણ",
                prompt = "ઘઉં અને કપાસમાં પાંદડા પીળા પડવા અને જીવાતોનું નિયંત્રણ કેવી રીતે કરવું?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "ખાતર વ્યવસ્થાપન",
                prompt = "ફૂલ અને ફળ આવવાના સમયે કયું ખાતર અને NPK છંટકાવ શ્રેષ્ઠ છે?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "બજાર ભાવ",
                prompt = "આજના માર્કેટ યાર્ડમાં પાકોના તાજા ભાવ અને વેચાણ માટેની સલાહ શું છે?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "હવામાન અને પિયત",
                prompt = "હવામાનની આગાહી મુજબ પિયત અને દવાનો છંટકાવ ક્યારે કરવો જોઈએ?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "સરકારી યોજનાઓ",
                prompt = "પીએમ કિસાન અને સોલર પંપ સબસિડી માટે કેવી રીતે અરજી કરવી?"
            )
        ),

        // 10. PUNJABI (pa)
        "pa" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "ਫ਼ਸਲ ਦੀ ਸੰਭਾਲ",
                prompt = "ਕਣਕ ਦੇ ਪੀਲੇ ਪੱਤਿਆਂ, ਕੁੰਗੀ ਅਤੇ ਕੀੜਿਆਂ ਦੀ ਰੋਕਥਾਮ ਕਿਵੇਂ ਕਰੀਏ?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "ਖਾਦ ਪ੍ਰਬੰਧਨ",
                prompt = "ਫੁੱਲ ਅਤੇ ਫਲ ਬਣਨ ਵੇਲੇ ਕਿਹੜੀ ਖਾਦ ਅਤੇ NPK ਸਪਰੇਅ ਸਭ ਤੋਂ ਵਧੀਆ ਹੈ?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "ਮੰਡੀ ਭਾਅ",
                prompt = "ਅੱਜ ਮੰਡੀ ਵਿੱਚ ਫ਼ਸਲਾਂ ਦੇ ਤਾਜ਼ਾ ਭਾਅ ਅਤੇ ਵੇਚਣ ਦੀ ਸਹੀ ਸਲਾਹ ਕੀ ਹੈ?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "ਮੌਸਮ ਤੇ ਸਿੰਚਾਈ",
                prompt = "ਮੌਸਮ ਦੇ ਹਾਲਾਤ ਦੇਖ ਕੇ ਸਿੰਚਾਈ ਅਤੇ ਕੀਟਨਾਸ਼ਕ ਸਪਰੇਅ ਕਦੋਂ ਕਰਨੀ ਚਾਹੀਦੀ ਹੈ?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "ਸਰਕਾਰੀ ਸਕੀਮਾਂ",
                prompt = "ਪੀਐਮ ਕਿਸਾਨ ਅਤੇ ਸੋਲਰ ਪੰਪ ਸਬਸਿਡੀ ਲਈ ਅਰਜ਼ੀ ਕਿਵੇਂ ਦੇਣੀ ਹੈ?"
            )
        ),

        // 11. ODIA (or / od)
        "or" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "ଫସଲ ସୁରକ୍ଷା",
                prompt = "ଧାନ ଏବଂ ଗହମରେ ପତ୍ର ହଳଦିଆ ପଡ଼ିବା ଓ ପୋକ ନିୟନ୍ତ୍ରଣ କିପରି କରିବେ?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "ଖତ ଓ ପୋଷକ ତତ୍ତ୍ୱ",
                prompt = "ଫୁଲ ଏବଂ ଫଳ ଆସିବା ସମୟରେ କେଉଁ ଖତ ଏବଂ NPK ସ୍ପ୍ରେ କରିବା ଉତ୍ତମ?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "ମଣ୍ଡି ଦର",
                prompt = "ଆଜି ମଣ୍ଡିରେ ଫସଲର ତାଜା ଦର ଏବଂ ବିକ୍ରୟ ପାଇଁ ପରାମର୍ଶ କ'ଣ?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "ପାଣିପାଗ ଓ ଜଳସେଚନ",
                prompt = "ପାଣିପାଗ ପୂର୍ବାନୁମାନ ଅନୁଯାୟୀ ଜଳସେଚନ ଏବଂ କୀଟନାଶକ ସ୍ପ୍ରେ କେବେ କରିବା ଉଚିତ?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "ସରକାରୀ ଯୋଜନା",
                prompt = "ପିଏମ୍ କିଷାନ୍ ଏବଂ ସୌର ପମ୍ପ ସବସିଡି ପାଇଁ କିପରି ଆବେଦନ କରିବେ?"
            )
        ),
        "od" to listOf(
            ChatRecommendation(
                icon = "🌾",
                category = "ଫସଲ ସୁରକ୍ଷା",
                prompt = "ଧାନ ଏବଂ ଗହମରେ ପତ୍ର ହଳଦିଆ ପଡ଼ିବା ଓ ପୋକ ନିୟନ୍ତ୍ରଣ କିପରି କରିବେ?"
            ),
            ChatRecommendation(
                icon = "🧪",
                category = "ଖତ ଓ ପୋଷକ ତତ୍ତ୍ୱ",
                prompt = "ଫୁଲ ଏବଂ ଫଳ ଆସିବା ସମୟରେ କେଉଁ ଖତ ଏବଂ NPK ସ୍ପ୍ରେ କରିବା ଉତ୍ତମ?"
            ),
            ChatRecommendation(
                icon = "📊",
                category = "ମଣ୍ଡି ଦର",
                prompt = "ଆଜି ମଣ୍ଡିରେ ଫସଲର ତାଜା ଦର ଏବଂ ବିକ୍ରୟ ପାଇଁ ପରାମର୍ଶ କ'ଣ?"
            ),
            ChatRecommendation(
                icon = "🌧️",
                category = "ପାଣିପାଗ ଓ ଜଳସେଚନ",
                prompt = "ପାଣିପାଗ ପୂର୍ବାନୁମାନ ଅନୁଯାୟୀ ଜଳସେଚନ ଏବଂ କୀଟନାଶକ ସ୍ପ୍ରେ କେବେ କରିବା ଉଚିତ?"
            ),
            ChatRecommendation(
                icon = "🏛️",
                category = "ସରକାରୀ ଯୋଜନା",
                prompt = "ପିଏମ୍ କିଷାନ୍ ଏବଂ ସୌର ପମ୍ପ ସବସିଡି ପାଇଁ କିପରି ଆବେଦନ କରିବେ?"
            )
        )
    )
}
