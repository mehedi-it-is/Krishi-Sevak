package com.krishisevak.app.data.engine

/**
 * Comprehensive commodity name translations for all 11 supported languages.
 * Covers all Indian agricultural commodities across Vegetables, Fruits,
 * Grains & Crops, Pulses & Legumes, and Spices & Cash Crops.
 */
object MandiTranslations {

    fun getTranslatedName(englishName: String?, langCode: String): String {
        if (englishName.isNullOrBlank()) return "Crop"
        if (langCode == "en") return englishName
        val cleanKey = englishName.lowercase()
            .replace(Regex("\\(.*\\)"), "") // Remove bracketed varieties for cleaner matching
            .trim()

        val dict = commodityTranslations[langCode] ?: return englishName

        // Direct match or partial contains match
        val match = dict.entries.firstOrNull { (k, _) ->
            cleanKey.contains(k.lowercase()) || englishName.lowercase().contains(k.lowercase())
        }
        return match?.value ?: englishName
    }

    fun getTranslatedCategory(englishCategory: String?, langCode: String): String {
        if (englishCategory.isNullOrBlank()) return "Vegetables"
        if (langCode == "en") return englishCategory
        return categoryTranslations[langCode]?.get(englishCategory) ?: englishCategory
    }

    private val categoryTranslations = mapOf(
        "hi" to mapOf(
            "Vegetables" to "सब्ज़ियाँ", "Fruits" to "फल",
            "Grains & Crops" to "अनाज और फसलें", "Pulses & Legumes" to "दालें और दलहन",
            "Spices & Cash Crops" to "मसाले और नकदी फसलें", "All" to "सभी"
        ),
        "bn" to mapOf(
            "Vegetables" to "শাকসবজি", "Fruits" to "ফল",
            "Grains & Crops" to "শস্য ও ফসল", "Pulses & Legumes" to "ডাল ও শিম",
            "Spices & Cash Crops" to "মসলা ও অর্থকরী ফসল", "All" to "সব"
        ),
        "mr" to mapOf(
            "Vegetables" to "भाजीपाला", "Fruits" to "फळे",
            "Grains & Crops" to "धान्य आणि पिके", "Pulses & Legumes" to "कडधान्ये",
            "Spices & Cash Crops" to "मसाले आणि नगदी पिके", "All" to "सर्व"
        ),
        "te" to mapOf(
            "Vegetables" to "కూరగాయలు", "Fruits" to "పండ్లు",
            "Grains & Crops" to "ధాన్యాలు మరియు పంటలు", "Pulses & Legumes" to "పప్పులు",
            "Spices & Cash Crops" to "సుగంధ ద్రవ్యాలు మరియు వాణిజ్య పంటలు", "All" to "అన్నీ"
        ),
        "ta" to mapOf(
            "Vegetables" to "காய்கறிகள்", "Fruits" to "பழங்கள்",
            "Grains & Crops" to "தானியங்கள் மற்றும் பயிர்கள்", "Pulses & Legumes" to "பருப்பு வகைகள்",
            "Spices & Cash Crops" to "மசாலா மற்றும் பணப்பயிர்கள்", "All" to "அனைத்தும்"
        ),
        "kn" to mapOf(
            "Vegetables" to "ತರಕಾರಿಗಳು", "Fruits" to "ಹಣ್ಣುಗಳು",
            "Grains & Crops" to "ಧಾನ್ಯಗಳು ಮತ್ತು ಬೆಳೆಗಳು", "Pulses & Legumes" to "ಬೇಳೆಕಾಳುಗಳು",
            "Spices & Cash Crops" to "ಮಸಾಲೆ ಮತ್ತು ವಾಣಿಜ್ಯ ಬೆಳೆಗಳು", "All" to "ಎಲ್ಲಾ"
        ),
        "ml" to mapOf(
            "Vegetables" to "പച്ചക്കറികൾ", "Fruits" to "പഴങ്ങൾ",
            "Grains & Crops" to "ധാന്യങ്ങളും വിളകളും", "Pulses & Legumes" to "പയറുവർഗങ്ങൾ",
            "Spices & Cash Crops" to "സുഗന്ധവ്യഞ്ജനങ്ങളും നാണ്യവിളകളും", "All" to "എല്ലാം"
        ),
        "gu" to mapOf(
            "Vegetables" to "શાકભાજી", "Fruits" to "ફળો",
            "Grains & Crops" to "અનાજ અને પાક", "Pulses & Legumes" to "કઠોળ",
            "Spices & Cash Crops" to "મસાલા અને રોકડિયા પાક", "All" to "બધા"
        ),
        "pa" to mapOf(
            "Vegetables" to "ਸਬਜ਼ੀਆਂ", "Fruits" to "ਫਲ",
            "Grains & Crops" to "ਅਨਾਜ ਅਤੇ ਫ਼ਸਲਾਂ", "Pulses & Legumes" to "ਦਾਲਾਂ",
            "Spices & Cash Crops" to "ਮਸਾਲੇ ਅਤੇ ਨਕਦ ਫ਼ਸਲਾਂ", "All" to "ਸਾਰੇ"
        ),
        "or" to mapOf(
            "Vegetables" to "ପରିବା", "Fruits" to "ଫଳ",
            "Grains & Crops" to "ଶସ୍ୟ ଏବଂ ଫସଲ", "Pulses & Legumes" to "ଡାଲି",
            "Spices & Cash Crops" to "ମସଲା ଏବଂ ନଗଦ ଫସଲ", "All" to "ସବୁ"
        )
    )

    private val commodityTranslations = mapOf(
        "hi" to mapOf(
            "jute" to "कच्चा पटसन / जूट", "raw jute" to "कच्चा पटसन (जूट)", "rice" to "चावल (धान)", "minikit" to "मिनीकिट चावल",
            "paddy" to "धान / चावल", "wheat" to "गेहूँ", "tomato" to "टमाटर", "potato" to "आलू", "onion" to "प्याज",
            "green chilli" to "हरी मिर्च", "red chilli" to "लाल मिर्च", "chilli" to "मिर्च", "garlic" to "लहसुन",
            "ginger" to "अदरक", "mustard" to "सरसों / तोरी", "pointed gourd" to "परवल / पटल", "potal" to "परवल",
            "cauliflower" to "फूलगोभी", "cabbage" to "पत्तागोभी", "brinjal" to "बैंगन", "tea" to "चाय पत्ती",
            "cardamom" to "इलायची", "large cardamom" to "बड़ी इलायची", "pineapple" to "अनानास", "mango" to "आम",
            "banana" to "केला", "apple" to "सेब", "pomegranate" to "अनार", "grapes" to "अंगूर", "papaya" to "पपीता",
            "guava" to "अमरूद", "orange" to "संतरा", "watermelon" to "तरबूज", "muskmelon" to "खरबूजा", "lemon" to "नींबू",
            "sapota" to "चीकू", "maize" to "मक्का", "bajra" to "बाजरा", "jowar" to "ज्वार", "ragi" to "रागी",
            "chana" to "चना", "kabuli" to "काबुली चना", "tur" to "तूर / अरहर", "moong" to "मूँग", "urad" to "उड़द",
            "masoor" to "मसूर", "soyabean" to "सोयाबीन", "groundnut" to "मूँगफली", "cotton" to "कपास", "sugarcane" to "गन्ना",
            "turmeric" to "हल्दी", "cumin" to "जीरा", "sesame" to "तिल", "black pepper" to "काली मिर्च",
            "lady finger" to "भिंडी", "bitter gourd" to "करेला", "bottle gourd" to "लौकी", "ridge gourd" to "तोरई",
            "spinach" to "पालक", "fenugreek" to "मेथी", "carrot" to "गाजर", "radish" to "मूली", "capsicum" to "शिमला मिर्च",
            "green peas" to "हरी मटर", "beetroot" to "चुकंदर", "coriander" to "धनिया", "cucumber" to "खीरा",
            "drumstick" to "सहजन", "tapioca" to "कसावा / साबूदाना", "rubber" to "रबर", "coconut" to "नारियल",
            "sunflower" to "सूरजमुखी", "castor" to "अरंडी"
        ),
        "bn" to mapOf(
            "jute" to "পাট (কাঁচা পাট)", "raw jute" to "কাঁচা পাট (মেস্তা)", "rice" to "চাল (ধান)", "minikit" to "মিনিকিট ধান/চাল",
            "paddy" to "ধান (আমন/বোরো)", "wheat" to "গম", "tomato" to "টমেটো", "potato" to "আলু (জ্যোতি)", "onion" to "পেঁয়াজ",
            "green chilli" to "কাঁচা লঙ্কা", "red chilli" to "শুকনো লঙ্কা", "chilli" to "লঙ্কা", "garlic" to "রসুন",
            "ginger" to "আদা", "mustard" to "সরিষা (তোড়ি)", "pointed gourd" to "পটল", "potal" to "পটল",
            "cauliflower" to "ফুলকপি", "cabbage" to "বাঁধাকপি", "brinjal" to "বেগুন (মুক্তকেশী)", "tea" to "চা পাতা (ডুয়ার্স)",
            "cardamom" to "এলাচ", "large cardamom" to "বড় এলাচ", "pineapple" to "আনারস", "mango" to "আম (হিমসাগর/ফজলি)",
            "banana" to "কলা", "apple" to "আপেল", "pomegranate" to "ডালিম / বেদানা", "grapes" to "আঙুর", "papaya" to "পেঁপে",
            "guava" to "পেয়ারা", "orange" to "কমলালেবু", "watermelon" to "তরমুজ", "muskmelon" to "খরমুজ", "lemon" to "পাতিলেবু",
            "sapota" to "সবেদা", "maize" to "ভুট্টা", "bajra" to "বাজরা", "jowar" to "জোয়ার", "ragi" to "রাগি",
            "chana" to "ছোলা", "kabuli" to "কাবুলি ছোলা", "tur" to "অড়হর ডাল", "moong" to "মুগ ডাল", "urad" to "মাষকলাই ডাল",
            "masoor" to "মসুর ডাল", "soyabean" to "সয়াবিন", "groundnut" to "চিনাবাদাম", "cotton" to "তুলা", "sugarcane" to "আখ",
            "turmeric" to "হলুদ", "cumin" to "জিরা", "sesame" to "তিল", "black pepper" to "গোলমরিচ",
            "lady finger" to "ঢেঁড়স", "bitter gourd" to "করলা / উচ্ছে", "bottle gourd" to "লাউ", "ridge gourd" to "ঝিঙে",
            "spinach" to "পালং শাক", "fenugreek" to "মেথি শাক", "carrot" to "গাজর", "radish" to "মুলো", "capsicum" to "ক্যাপসিকাম",
            "green peas" to "মটরশুঁটি", "beetroot" to "বিট", "coriander" to "ধনে পাতা", "cucumber" to "শসা",
            "drumstick" to "সজনে ডাঁটা", "tapioca" to "শিমুল আলু", "rubber" to "রাবার", "coconut" to "নারকেল",
            "sunflower" to "সূর্যমুখী", "castor" to "রেড়ী বীজ"
        ),
        "mr" to mapOf(
            "jute" to "ताग", "raw jute" to "कच्चा ताग", "rice" to "तांदूळ (भात)", "minikit" to "मिनिकिट तांदूळ",
            "paddy" to "धान / भात", "wheat" to "गहू (लोकवन/शरबती)", "tomato" to "टोमॅटो", "potato" to "बटाटा", "onion" to "कांदा (गरवा)",
            "green chilli" to "हिरवी मिरची", "red chilli" to "लाल मिरची", "chilli" to "मिरची", "garlic" to "लसूण",
            "ginger" to "आले", "mustard" to "मोहरी", "pointed gourd" to "परवळ", "potal" to "परवळ",
            "cauliflower" to "फुलकोबी", "cabbage" to "कोबी", "brinjal" to "वांगे", "tea" to "चहा पत्ती",
            "cardamom" to "वेलची", "large cardamom" to "मोठी वेलची", "pineapple" to "अननस", "mango" to "आंबा (हापूस)",
            "banana" to "केळी", "apple" to "सफरचंद", "pomegranate" to "डाळिंब (भगवा)", "grapes" to "द्राक्षे", "papaya" to "पपई",
            "guava" to "पेरू", "orange" to "संत्री (नागपूर)", "watermelon" to "कलिंगड", "muskmelon" to "खरबूज", "lemon" to "लिंबू",
            "sapota" to "चिक्कू", "maize" to "मका", "bajra" to "बाजरी", "jowar" to "ज्वारी (मालदांडी)", "ragi" to "नाचणी",
            "chana" to "हरभरा", "kabuli" to "काबुली चणा", "tur" to "तूर (लाल)", "moong" to "मूग", "urad" to "उडीद",
            "masoor" to "मसूर", "soyabean" to "सोयाबीन (पिवळा)", "groundnut" to "भुईमूग (शेंगदाणा)", "cotton" to "कापूस (कपाशी)", "sugarcane" to "ऊस",
            "turmeric" to "हळद", "cumin" to "जिरे", "sesame" to "तीळ", "black pepper" to "काळी मिरी",
            "lady finger" to "भेंडी", "bitter gourd" to "कारले", "bottle gourd" to "दुधी भोपळा", "ridge gourd" to "दोडका",
            "spinach" to "पालक", "fenugreek" to "मेथी", "carrot" to "गाजर", "radish" to "मुळा", "capsicum" to "ढोबळी मिरची",
            "green peas" to "हिरवे वाटाणे", "beetroot" to "बीट", "coriander" to "कोथिंबीर", "cucumber" to "काकडी",
            "drumstick" to "शेवगा", "tapioca" to "कसावा", "rubber" to "रबर", "coconut" to "नारळ",
            "sunflower" to "सूर्यफूल", "castor" to "एरंडी"
        ),
        "te" to mapOf(
            "jute" to "జనపనార", "raw jute" to "జనపనార", "rice" to "బియ్యం / వరి", "minikit" to "మినీకిట్ బియ్యం",
            "paddy" to "వరి (ధాన్యం)", "wheat" to "గోధుమలు", "tomato" to "టమాటా", "potato" to "బంగాళాదుంప", "onion" to "ఉల్లిపాయ",
            "green chilli" to "పచ్చిమిర్చి", "red chilli" to "ఎండుమిర్చి (తేజా)", "chilli" to "మిర్చి", "garlic" to "వెల్లుల్లి",
            "ginger" to "అల్లం", "mustard" to "ఆవాలు", "pointed gourd" to "పొటల్స్", "potal" to "పొటల్స్",
            "cauliflower" to "క్యాలీఫ్లవర్", "cabbage" to "క్యాబేజీ", "brinjal" to "వంకాయ", "tea" to "టీ ఆకులు",
            "cardamom" to "యాలకులు", "large cardamom" to "పెద్ద యాలకులు", "pineapple" to "అనాసపండు", "mango" to "మామిడిపండు",
            "banana" to "అరటిపండు", "apple" to "ఆపిల్", "pomegranate" to "దానిమ్మ", "grapes" to "ద్రాక్ష", "papaya" to "బొప్పాయి",
            "guava" to "జామపండు", "orange" to "నారింజ", "watermelon" to "పుచ్చకాయ", "muskmelon" to "ఖర్బూజ", "lemon" to "నిమ్మకాయ",
            "sapota" to "సపోటా", "maize" to "మొక్కజొన్న", "bajra" to "సజ్జలు", "jowar" to "జొన్నలు", "ragi" to "రాగులు",
            "chana" to "శనగలు", "kabuli" to "కాబూలీ శనగలు", "tur" to "కందులు", "moong" to "పెసలు", "urad" to "మినుములు",
            "masoor" to "మసూర్ పప్పు", "soyabean" to "సోయాబీన్", "groundnut" to "వేరుశనగ", "cotton" to "పత్తి", "sugarcane" to "చెరకు",
            "turmeric" to "పసుపు", "cumin" to "జీలకర్ర", "sesame" to "నువ్వులు", "black pepper" to "మిరియాలు",
            "lady finger" to "బెండకాయ", "bitter gourd" to "కాకరకాయ", "bottle gourd" to "సొరకాయ", "ridge gourd" to "బీరకాయ",
            "spinach" to "పాలకూర", "fenugreek" to "మెంతికూర", "carrot" to "క్యారెట్", "radish" to "ముల్లంగి", "capsicum" to "క్యాప్సికమ్",
            "green peas" to "పచ్చి బఠానీలు", "beetroot" to "బీట్‌రూట్", "coriander" to "కొత్తిమీర", "cucumber" to "దోసకాయ",
            "drumstick" to "మునగకాయ", "tapioca" to "కర్రపెండలం", "rubber" to "రబ్బరు", "coconut" to "కొబ్బరికాయ",
            "sunflower" to "పొద్దుతిరుగుడు", "castor" to "ఆముదాలు"
        ),
        "ta" to mapOf(
            "jute" to "சணல்", "raw jute" to "பச்சை சணல்", "rice" to "அரிசி / நெல்", "minikit" to "மினிகிட் அரிசி",
            "paddy" to "நெல் (சாம்பா)", "wheat" to "கோதுமை", "tomato" to "தக்காளி", "potato" to "உருளைக்கிழங்கு", "onion" to "வெங்காயம்",
            "green chilli" to "பச்சை மிளகாய்", "red chilli" to "காய்ந்த மிளகாய்", "chilli" to "மிளகாய்", "garlic" to "பூண்டு",
            "ginger" to "இஞ்சி", "mustard" to "கடுகு", "pointed gourd" to "பரவல்", "potal" to "பரவல்",
            "cauliflower" to "காலிஃபிளவர்", "cabbage" to "முட்டைகோஸ்", "brinjal" to "கத்திரிக்காய்", "tea" to "தேயிலை",
            "cardamom" to "ஏலக்காய்", "large cardamom" to "பெரிய ஏலக்காய்", "pineapple" to "அன்னாசிப்பழம்", "mango" to "மாம்பழம்",
            "banana" to "வாழைப்பழம்", "apple" to "ஆப்பிள்", "pomegranate" to "மாதுளை", "grapes" to "திராட்சை", "papaya" to "பப்பாளி",
            "guava" to "கொய்யாப்பழம்", "orange" to "ஆரஞ்சு", "watermelon" to "தர்பூசணி", "muskmelon" to "முலாம்பழம்", "lemon" to "எலுமிச்சை",
            "sapota" to "சப்போட்டா", "maize" to "மக்காச்சோளம்", "bajra" to "கம்பு", "jowar" to "சோளம்", "ragi" to "கேழ்வரகு",
            "chana" to "கொண்டைக்கடலை", "kabuli" to "காபூலி கொண்டைக்கடலை", "tur" to "துவரம்பருப்பு", "moong" to "பாசிப்பயறு", "urad" to "உளுந்து",
            "masoor" to "மசூர் பருப்பு", "soyabean" to "சோயாபீன்", "groundnut" to "நிலக்கடலை", "cotton" to "பருத்தி", "sugarcane" to "கரும்பு",
            "turmeric" to "மஞ்சள்", "cumin" to "சீரகம்", "sesame" to "எள்", "black pepper" to "மிளகு",
            "lady finger" to "வெண்டைக்காய்", "bitter gourd" to "பாகற்காய்", "bottle gourd" to "சுரைக்காய்", "ridge gourd" to "பீர்க்கங்காய்",
            "spinach" to "கீரை", "fenugreek" to "வெந்தயக்கீரை", "carrot" to "கேரட்", "radish" to "முள்ளங்கி", "capsicum" to "குடைமிளகாய்",
            "green peas" to "பச்சை பட்டாணி", "beetroot" to "பீட்ரூட்", "coriander" to "கொத்தமல்லி", "cucumber" to "வெள்ளரிக்காய்",
            "drumstick" to "முருங்கைக்காய்", "tapioca" to "மரவள்ளிக்கிழங்கு", "rubber" to "ரப்பர்", "coconut" to "தேங்காய்",
            "sunflower" to "சூரியகாந்தி", "castor" to "ஆமணக்கு"
        ),
        "kn" to mapOf(
            "jute" to "ಸೆಣಬು", "raw jute" to "ಹಸಿ ಸೆಣಬು", "rice" to "ಅಕ್ಕಿ / ಭತ್ತ", "minikit" to "ಮಿನಿಕಿಟ್ ಅಕ್ಕಿ",
            "paddy" to "ಭತ್ತ (ಧಾನ್ಯ)", "wheat" to "ಗೋಧಿ", "tomato" to "ಟೊಮೆಟೊ", "potato" to "ಆಲೂಗಡ್ಡೆ", "onion" to "ಈರುಳ್ಳಿ",
            "green chilli" to "ಹಸಿಮೆಣಸಿನಕಾಯಿ", "red chilli" to "ಒಣಮೆಣಸಿನಕಾಯಿ", "chilli" to "ಮೆಣಸಿನಕಾಯಿ", "garlic" to "ಬೆಳ್ಳುಳ್ಳಿ",
            "ginger" to "ಶುಂಠಿ", "mustard" to "ಸಾಸಿವೆ", "pointed gourd" to "ಪರ್ವಲ್", "potal" to "ಪರ್ವಲ್",
            "cauliflower" to "ಹೂಕೋಸು", "cabbage" to "ಎಲೆಕೋಸು", "brinjal" to "ಬದನೆಕಾಯಿ", "tea" to "ಚಹಾ ಎಲೆ",
            "cardamom" to "ಏಲಕ್ಕಿ", "large cardamom" to "ದೊಡ್ಡ ಏಲಕ್ಕಿ", "pineapple" to "ಅನಾನಸ್", "mango" to "ಮಾವಿನಹಣ್ಣು",
            "banana" to "ಬಾಳೆಹಣ್ಣು", "apple" to "ಸೇಬು", "pomegranate" to "ದಾಳಿಂಬೆ", "grapes" to "ದ್ರಾಕ್ಷಿ", "papaya" to "ಪಪ್ಪಾಯಿ",
            "guava" to "ಸೀಬೆಹಣ್ಣು", "orange" to "ಕಿತ್ತಳೆ", "watermelon" to "ಕಲ್ಲಂಗಡಿ", "muskmelon" to "ಖರ್ಬೂಜ", "lemon" to "ನಿಂಬೆಹಣ್ಣು",
            "sapota" to "ಚಿಕ್ಕು", "maize" to "ಮೆಕ್ಕೆಜೋಳ", "bajra" to "ಸಜ್ಜೆ", "jowar" to "ಜೋಳ", "ragi" to "ರಾಗಿ",
            "chana" to "ಕಡಲೆಕಾಳು", "kabuli" to "ಕಾಬೂಲಿ ಕಡಲೆ", "tur" to "ತೊಗರಿಬೇಳೆ", "moong" to "ಹೆಸರುಕಾಳು", "urad" to "ಉದ್ದಿನಬೇಳೆ",
            "masoor" to "ಮಸೂರ ಬೇಳೆ", "soyabean" to "ಸೋಯಾಬೀನ್", "groundnut" to "ಶೇಂಗಾ (ಕಡಲೆಕಾಯಿ)", "cotton" to "ಹತ್ತಿ", "sugarcane" to "ಕಬ್ಬು",
            "turmeric" to "ಅರಿಶಿನ", "cumin" to "ಜೀರಿಗೆ", "sesame" to "ಎಳ್ಳು", "black pepper" to "ಕಾಳುಮೆಣಸು",
            "lady finger" to "ಬೆಂಡೆಕಾಯಿ", "bitter gourd" to "ಹಾಗಲಕಾಯಿ", "bottle gourd" to "ಸೋರೆಕಾಯಿ", "ridge gourd" to "ಹೀರೆಕಾಯಿ",
            "spinach" to "ಪಾಲಕ್ ಸೊಪ್ಪು", "fenugreek" to "ಮೆಂತ್ಯ ಸೊಪ್ಪು", "carrot" to "ಕ್ಯಾರೆಟ್", "radish" to "ಮೂಲಂಗಿ", "capsicum" to "ದೊಣ್ಣೆ ಮೆಣಸಿನಕಾಯಿ",
            "green peas" to "ಹಸಿರು ಬಟಾಣಿ", "beetroot" to "ಬೀಟ್‌ರೂಟ್", "coriander" to "ಕೊತ್ತಂಬರಿ", "cucumber" to "ಸೌತೆಕಾಯಿ",
            "drumstick" to "ನುಗ್ಗೆಕಾಯಿ", "tapioca" to "ಮರಗೆಣಸು", "rubber" to "ರಬ್ಬರ್", "coconut" to "ತೆಂಗಿನಕಾಯಿ",
            "sunflower" to "ಸೂರ್ಯಕಾಂತಿ", "castor" to "ಹರಳೆಣ್ಣೆ ಬೀಜ"
        ),
        "ml" to mapOf(
            "jute" to "ചണ", "raw jute" to "പച്ചച്ചണ", "rice" to "അരി / നെല്ല്", "minikit" to "മിനിക്കിറ്റ് അരി",
            "paddy" to "നെല്ല്", "wheat" to "ഗോതമ്പ്", "tomato" to "തക്കാളി", "potato" to "ഉരുളക്കിഴങ്ങ്", "onion" to "സവാള / ഉള്ളി",
            "green chilli" to "പച്ചമുളക്", "red chilli" to "വറ്റൽമുളക്", "chilli" to "മുളക്", "garlic" to "വെളുത്തുള്ളി",
            "ginger" to "ഇഞ്ചി", "mustard" to "കടുക്", "pointed gourd" to "പടവലം", "potal" to "പടവലം",
            "cauliflower" to "കോളിഫ്ലവർ", "cabbage" to "കാബേജ്", "brinjal" to "വഴുതനങ്ങ", "tea" to "തേയില",
            "cardamom" to "ഏലക്ക", "large cardamom" to "വലിയ ഏലക്ക", "pineapple" to "കൈതച്ചക്ക", "mango" to "മാങ്ങ",
            "banana" to "ഏത്തപ്പഴം / വാഴപ്പഴം", "apple" to "ആപ്പിൾ", "pomegranate" to "മാതളനാരങ്ങ", "grapes" to "മുന്തിരി", "papaya" to "പപ്പായ",
            "guava" to "പേരയ്ക്ക", "orange" to "ഓറഞ്ച്", "watermelon" to "തണ്ണിമത്തൻ", "muskmelon" to "കസ്തൂരിമത്തൻ", "lemon" to "ചെറുനാരങ്ങ",
            "sapota" to "സപ്പോട്ട", "maize" to "ചോളം", "bajra" to "കമ്പ്", "jowar" to "ചോളം", "ragi" to "റാഗി / കൂവരക്",
            "chana" to "കടല", "kabuli" to "കാബൂളി കടല", "tur" to "തുവരപ്പരിപ്പ്", "moong" to "ചെറുപയർ", "urad" to "ഉഴുന്ന്",
            "masoor" to "മസൂർ പരിപ്പ്", "soyabean" to "സോയാബീൻ", "groundnut" to "നിലക്കടല / കപ്പലണ്ടി", "cotton" to "പരുത്തി", "sugarcane" to "കരിമ്പ്",
            "turmeric" to "മഞ്ഞൾ", "cumin" to "ജീരകം", "sesame" to "എള്ള്", "black pepper" to "കുരുമുളക്",
            "lady finger" to "വെണ്ടയ്ക്ക", "bitter gourd" to "പാവയ്ക്ക", "bottle gourd" to "ചുരയ്ക്ക", "ridge gourd" to "പീച്ചിങ്ങ",
            "spinach" to "ചീര", "fenugreek" to "ഉലുവയില", "carrot" to "കാരറ്റ്", "radish" to "മുള്ളങ്കി", "capsicum" to "കുടമുളക്",
            "green peas" to "പച്ചപ്പട്ടാണി", "beetroot" to "ബീറ്റ്റൂട്ട്", "coriander" to "മല്ലിയില", "cucumber" to "വെള്ളരിക്ക",
            "drumstick" to "മുരിങ്ങക്കായ", "tapioca" to "കപ്പ / മരച്ചീനി", "rubber" to "റബ്ബർ ഷീറ്റ്", "coconut" to "തേങ്ങ",
            "sunflower" to "സൂര്യകാന്തി", "castor" to "ആവണക്ക്"
        ),
        "gu" to mapOf(
            "jute" to "શણ", "raw jute" to "કાચું શણ", "rice" to "ચોખા / ડાંગર", "minikit" to "મિનિકિટ ચોખા",
            "paddy" to "ડાંગર", "wheat" to "ઘઉં (ટુકડી/લોકવન)", "tomato" to "ટામેટાં", "potato" to "બટાટા", "onion" to "ડુંગળી",
            "green chilli" to "લીલાં મરચાં", "red chilli" to "લાલ મરચાં", "chilli" to "મરચાં", "garlic" to "લસણ",
            "ginger" to "આદું", "mustard" to "રાઈ / સરસવ", "pointed gourd" to "પરવળ", "potal" to "પરવળ",
            "cauliflower" to "ફૂલેવર", "cabbage" to "કોબીજ", "brinjal" to "રીંગણ", "tea" to "ચા પત્તી",
            "cardamom" to "એલચી", "large cardamom" to "મોટી એલચી", "pineapple" to "અનાનસ", "mango" to "કેરી (કેસર/હાફૂસ)",
            "banana" to "કેળાં", "apple" to "સફરજન", "pomegranate" to "દાડમ", "grapes" to "દ્રાક્ષ", "papaya" to "પપૈયું",
            "guava" to "જામફળ", "orange" to "સંતરાં", "watermelon" to "તરબૂચ", "muskmelon" to "ટેટી / શક્કરટેટી", "lemon" to "લીંબુ",
            "sapota" to "ચીકુ", "maize" to "મકાઈ", "bajra" to "બાજરી", "jowar" to "જુવાર", "ragi" to "રાગી / નાગલી",
            "chana" to "ચણા", "kabuli" to "કાબુલી ચણા", "tur" to "તુવેર દાળ", "moong" to "મગ", "urad" to "અડદ",
            "masoor" to "મસૂર", "soyabean" to "સોયાબીન", "groundnut" to "મગફળી (સીંગદાણા)", "cotton" to "કપાસ (શંકર-૬)", "sugarcane" to "શેરડી",
            "turmeric" to "હળદર", "cumin" to "જીરું", "sesame" to "તલ", "black pepper" to "કાળા મરી",
            "lady finger" to "ભીંડા", "bitter gourd" to "કારેલાં", "bottle gourd" to "દૂધી", "ridge gourd" to "તૂરિયા",
            "spinach" to "પાલક", "fenugreek" to "મેથી", "carrot" to "ગાજર", "radish" to "મૂળા", "capsicum" to "શિમલા મરચાં",
            "green peas" to "લીલા વટાણા", "beetroot" to "બીટ", "coriander" to "કોથમરી", "cucumber" to "કાકડી",
            "drumstick" to "સરગવો", "tapioca" to "સાબુદાણા / કસાવા", "rubber" to "રબર", "coconut" to "નાળિયેર",
            "sunflower" to "સૂર્યમુખી", "castor" to "દિવેલા (એરંડા)"
        ),
        "pa" to mapOf(
            "jute" to "ਪਟਸਨ / ਪਟ", "raw jute" to "ਕੱਚਾ ਪਟਸਨ", "rice" to "ਚੌਲ (ਝੋਨਾ)", "minikit" to "ਮਿਨੀਕਿਟ ਚੌਲ",
            "paddy" to "ਝੋਨਾ (ਬਾਸਮਤੀ)", "wheat" to "ਕਣਕ", "tomato" to "ਟਮਾਟਰ", "potato" to "ਆਲੂ", "onion" to "ਪਿਆਜ਼",
            "green chilli" to "ਹਰੀ ਮਿਰਚ", "red chilli" to "ਲਾਲ ਮਿਰਚ", "chilli" to "ਮਿਰਚ", "garlic" to "ਲਸਣ",
            "ginger" to "ਅਦਰਕ", "mustard" to "ਸਰ੍ਹੋਂ / ਰਾਇਆ", "pointed gourd" to "ਪਰਵਲ", "potal" to "ਪਰਵਲ",
            "cauliflower" to "ਫੁੱਲ ਗੋਭੀ", "cabbage" to "ਬੰਦ ਗੋਭੀ", "brinjal" to "ਬੈਂਗਣ", "tea" to "ਚਾਹ ਪੱਤੀ",
            "cardamom" to "ਇਲਾਇਚੀ", "large cardamom" to "ਵੱਡੀ ਇਲਾਇਚੀ", "pineapple" to "ਅਨਾਨਾਸ", "mango" to "ਅੰਬ",
            "banana" to "ਕੇਲਾ", "apple" to "ਸੇਬ", "pomegranate" to "ਅਨਾਰ", "grapes" to "ਅੰਗੂਰ", "papaya" to "ਪਪੀਤਾ",
            "guava" to "ਅਮਰੂਦ", "orange" to "ਸੰਤਰਾ / ਕਿੰਨੂ", "watermelon" to "ਤਰਬੂਜ਼", "muskmelon" to "ਖਰਬੂਜ਼ਾ", "lemon" to "ਨਿੰਬੂ",
            "sapota" to "ਚੀਕੂ", "maize" to "ਮੱਕੀ", "bajra" to "ਬਾਜਰਾ", "jowar" to "ਜੁਆਰ", "ragi" to "ਰਾਗੀ",
            "chana" to "ਛੋਲੇ", "kabuli" to "ਕਾਬੁਲੀ ਛੋਲੇ", "tur" to "ਅਰਹਰ ਦਾਲ", "moong" to "ਮੂੰਗੀ", "urad" to "ਮਾਂਹ / ਉੜਦ",
            "masoor" to "ਮਸਰ", "soyabean" to "ਸੋਇਆਬੀਨ", "groundnut" to "ਮੂੰਗਫਲੀ", "cotton" to "ਨਰਮਾ / ਕਪਾਹ", "sugarcane" to "ਗੰਨਾ",
            "turmeric" to "ਹਲਦੀ", "cumin" to "ਜੀਰਾ", "sesame" to "ਤਿਲ", "black pepper" to "ਕਾਲੀ ਮਿਰਚ",
            "lady finger" to "ਭਿੰਡੀ", "bitter gourd" to "ਕਰੇਲਾ", "bottle gourd" to "ਘੀਆ / ਕੱਦੂ", "ridge gourd" to "ਤੋਰੀ",
            "spinach" to "ਪਾਲਕ", "fenugreek" to "ਮੇਥੀ", "carrot" to "ਗਾਜਰ", "radish" to "ਮੂਲੀ", "capsicum" to "ਸ਼ਿਮਲਾ ਮਿਰਚ",
            "green peas" to "ਹਰੇ ਮਟਰ", "beetroot" to "ਚੁਕੰਦਰ", "coriander" to "ਧਨੀਆ", "cucumber" to "ਖੀਰਾ",
            "drumstick" to "ਸੁਹਾਂਜਣਾ", "tapioca" to "ਸਾਬੂਦਾਣਾ", "rubber" to "ਰਬੜ", "coconut" to "ਨਾਰੀਅਲ",
            "sunflower" to "ਸੂਰਜਮੁਖੀ", "castor" to "ਅਰੰਡੀ"
        ),
        "or" to mapOf(
            "jute" to "ଝୋଟ", "raw jute" to "କଞ୍ଚା ଝୋଟ", "rice" to "ଚାଉଳ / ଧାନ", "minikit" to "ମିନିକିଟ୍ ଚାଉଳ",
            "paddy" to "ଧାନ (ସ୍ୱର୍ଣ୍ଣା/ପୂଜା)", "wheat" to "ଗହମ", "tomato" to "ଟମାଟୋ (ବିଲାତି)", "potato" to "ଆଳୁ", "onion" to "ପିଆଜ",
            "green chilli" to "କଞ୍ଚା ଲଙ୍କା", "red chilli" to "ଶୁଖିଲା ଲଙ୍କା", "chilli" to "ଲଙ୍କା", "garlic" to "ରସୁଣ",
            "ginger" to "ଅଦା", "mustard" to "ସୋରିଷ", "pointed gourd" to "ପୋଟଳ", "potal" to "ପୋଟଳ",
            "cauliflower" to "ଫୁଲକୋବି", "cabbage" to "ବନ୍ଧାକୋବି", "brinjal" to "ବାଇଗଣ", "tea" to "ଚାହା ପତି",
            "cardamom" to "ଗୁଜୁରାତି / ଏଲାଚ", "large cardamom" to "ବଡ଼ ଏଲାଚ", "pineapple" to "ସପୁରି", "mango" to "ଆମ୍ବ",
            "banana" to "କଦଳୀ", "apple" to "ସେଓ", "pomegranate" to "ଡାଳିମ୍ବ / ବେଦନା", "grapes" to "ଅଙ୍ଗୁର", "papaya" to "ଅମୃତଭଣ୍ଡା",
            "guava" to "ପିଜୁଳି", "orange" to "କମଳା", "watermelon" to "ତରଭୁଜ", "muskmelon" to "ଖରଭୁଜ", "lemon" to "ଲେମ୍ବୁ",
            "sapota" to "ସପେଟା", "maize" to "ମକା", "bajra" to "ବାଜରା", "jowar" to "ଜୁଆର", "ragi" to "ମାଣ୍ଡିଆ",
            "chana" to "ବୁଟ / ଚଣା", "kabuli" to "କାବୁଲି ଚଣା", "tur" to "ହରଡ଼ ଡାଲି", "moong" to "ମୁଗ ଡାଲି", "urad" to "ବିରି ଡାଲି",
            "masoor" to "ମସୁର ଡାଲି", "soyabean" to "ସୋୟାବିନ୍", "groundnut" to "ଚିନାବାଦାମ", "cotton" to "କପା", "sugarcane" to "ଆଖୁ",
            "turmeric" to "ହଳଦୀ", "cumin" to "ଜୀରା", "sesame" to "ରାଶି", "black pepper" to "ଗୋଲମରିଚ",
            "lady finger" to "ଭେଣ୍ଡି", "bitter gourd" to "କଲରା", "bottle gourd" to "ଲାଉ", "ridge gourd" to "ଜହ୍ନି",
            "spinach" to "ପାଳଙ୍ଗ ଶାଗ", "fenugreek" to "ମେଥି ଶାଗ", "carrot" to "ଗାଜର", "radish" to "ମୂଳା", "capsicum" to "କ୍ୟାପସିକମ୍",
            "green peas" to "ମଟର", "beetroot" to "ବିଟ୍", "coriander" to "ଧନିଆ ପତ୍ର", "cucumber" to "କାକୁଡ଼ି",
            "drumstick" to "ସଜନା ଛୁଇଁ", "tapioca" to "କନ୍ଦମୂଳ / ଶାଗୁ", "rubber" to "ରବର", "coconut" to "ନଡ଼ିଆ",
            "sunflower" to "ସୂର୍ଯ୍ୟମୁଖୀ", "castor" to "ଜଡ଼ା ବିହନ"
        )
    )
}
