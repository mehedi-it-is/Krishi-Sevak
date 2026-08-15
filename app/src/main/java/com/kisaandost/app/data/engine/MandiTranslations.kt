package com.kisaandost.app.data.engine

/**
 * Comprehensive commodity name translations for all 11 supported languages.
 * Covers 60+ Indian agricultural commodities across Vegetables, Fruits,
 * Grains & Crops, Pulses & Legumes, and Spices & Cash Crops.
 */
object MandiTranslations {

    fun getTranslatedName(englishName: String?, langCode: String): String {
        if (englishName.isNullOrBlank()) return "Crop"
        if (langCode == "en") return englishName
        val key = englishName.lowercase().trim()
        return commodityTranslations[langCode]?.entries?.firstOrNull {
            key.contains(it.key.lowercase())
        }?.value ?: englishName
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
            "tomato" to "टमाटर", "onion" to "प्याज", "potato" to "आलू",
            "green chilli" to "हरी मिर्च", "garlic" to "लहसुन", "ginger" to "अदरक",
            "brinjal" to "बैंगन", "cauliflower" to "फूलगोभी", "cabbage" to "पत्तागोभी",
            "lady finger" to "भिंडी", "bitter gourd" to "करेला", "bottle gourd" to "लौकी",
            "ridge gourd" to "तोरई", "spinach" to "पालक", "fenugreek" to "मेथी",
            "carrot" to "गाजर", "radish" to "मूली", "capsicum" to "शिमला मिर्च",
            "green peas" to "मटर", "beetroot" to "चुकंदर", "coriander leaves" to "धनिया पत्ती",
            "cucumber" to "खीरा",
            "mango" to "आम", "banana" to "केला", "apple" to "सेब",
            "pomegranate" to "अनार", "grapes" to "अंगूर", "papaya" to "पपीता",
            "guava" to "अमरूद", "orange" to "संतरा / मौसम्बी", "watermelon" to "तरबूज",
            "muskmelon" to "खरबूजा", "pineapple" to "अनानास", "custard apple" to "सीताफल",
            "strawberry" to "स्ट्रॉबेरी", "lemon" to "नींबू", "sapota" to "चीकू",
            "dragon fruit" to "ड्रैगन फ्रूट",
            "wheat" to "गेहूँ", "paddy" to "धान / चावल", "maize" to "मक्का",
            "bajra" to "बाजरा", "jowar" to "ज्वार", "ragi" to "रागी",
            "barley" to "जौ", "foxtail millet" to "कँगनी", "kodo millet" to "कोदो",
            "chana" to "चना", "tur" to "तूर / अरहर", "moong" to "मूँग",
            "urad" to "उड़द", "masoor" to "मसूर", "kabuli chana" to "काबुली चना",
            "rajma" to "राजमा", "cowpea" to "लोबिया",
            "mustard" to "सरसों", "soyabean" to "सोयाबीन", "groundnut" to "मूँगफली",
            "cotton" to "कपास", "sugarcane" to "गन्ना", "turmeric" to "हल्दी",
            "cumin" to "जीरा", "coriander seed" to "धनिया बीज", "sesame" to "तिल",
            "black pepper" to "काली मिर्च", "cardamom" to "इलायची", "red chilli" to "लाल मिर्च"
        ),
        "bn" to mapOf(
            "tomato" to "টমেটো", "onion" to "পেঁয়াজ", "potato" to "আলু",
            "green chilli" to "কাঁচা লঙ্কা", "garlic" to "রসুন", "ginger" to "আদা",
            "brinjal" to "বেগুন", "cauliflower" to "ফুলকপি", "cabbage" to "বাঁধাকপি",
            "lady finger" to "ঢেঁড়স", "bitter gourd" to "করলা", "bottle gourd" to "লাউ",
            "ridge gourd" to "ঝিঙ্গে", "spinach" to "পালং শাক", "fenugreek" to "মেথি",
            "carrot" to "গাজর", "radish" to "মুলো", "capsicum" to "ক্যাপসিকাম",
            "green peas" to "মটরশুঁটি", "beetroot" to "বিট", "coriander leaves" to "ধনে পাতা",
            "cucumber" to "শসা",
            "mango" to "আম", "banana" to "কলা", "apple" to "আপেল",
            "pomegranate" to "ডালিম", "grapes" to "আঙুর", "papaya" to "পেঁপে",
            "guava" to "পেয়ারা", "orange" to "কমলা", "watermelon" to "তরমুজ",
            "muskmelon" to "খরমুজ", "pineapple" to "আনারস", "custard apple" to "আতা ফল",
            "strawberry" to "স্ট্রবেরি", "lemon" to "লেবু", "sapota" to "সবেদা",
            "dragon fruit" to "ড্রাগন ফল",
            "wheat" to "গম", "paddy" to "ধান", "maize" to "ভুট্টা",
            "bajra" to "বাজরা", "jowar" to "জোয়ার", "ragi" to "রাগি",
            "barley" to "যব", "foxtail millet" to "কাউন", "kodo millet" to "কোদো",
            "chana" to "ছোলা", "tur" to "অড়হর", "moong" to "মুগ ডাল",
            "urad" to "মাষকলাই", "masoor" to "মসুর", "kabuli chana" to "কাবুলি ছোলা",
            "rajma" to "রাজমা", "cowpea" to "বরবটি",
            "mustard" to "সরিষা", "soyabean" to "সয়াবিন", "groundnut" to "চিনাবাদাম",
            "cotton" to "তুলা", "sugarcane" to "আখ", "turmeric" to "হলুদ",
            "cumin" to "জিরা", "coriander seed" to "ধনে বীজ", "sesame" to "তিল",
            "black pepper" to "গোলমরিচ", "cardamom" to "এলাচ", "red chilli" to "শুকনো লঙ্কা"
        ),
        "mr" to mapOf(
            "tomato" to "टोमॅटो", "onion" to "कांदा", "potato" to "बटाटा",
            "green chilli" to "हिरवी मिरची", "garlic" to "लसूण", "ginger" to "आले",
            "brinjal" to "वांगे", "cauliflower" to "फुलकोबी", "cabbage" to "कोबी",
            "lady finger" to "भेंडी", "bitter gourd" to "कारले", "bottle gourd" to "दुधी भोपळा",
            "ridge gourd" to "दोडका", "spinach" to "पालक", "fenugreek" to "मेथी",
            "carrot" to "गाजर", "radish" to "मुळा", "capsicum" to "ढोबळी मिरची",
            "green peas" to "वाटाणे", "beetroot" to "बीट", "coriander leaves" to "कोथिंबीर",
            "cucumber" to "काकडी",
            "mango" to "आंबा", "banana" to "केळे", "apple" to "सफरचंद",
            "pomegranate" to "डाळिंब", "grapes" to "द्राक्षे", "papaya" to "पपई",
            "guava" to "पेरू", "orange" to "संत्री / मोसंबी", "watermelon" to "कलिंगड",
            "muskmelon" to "खरबूज", "pineapple" to "अननस", "custard apple" to "सीताफळ",
            "strawberry" to "स्ट्रॉबेरी", "lemon" to "लिंबू", "sapota" to "चिक्कू",
            "dragon fruit" to "ड्रॅगन फ्रुट",
            "wheat" to "गहू", "paddy" to "भात / तांदूळ", "maize" to "मका",
            "bajra" to "बाजरी", "jowar" to "ज्वारी", "ragi" to "नाचणी",
            "barley" to "जव", "foxtail millet" to "राळा", "kodo millet" to "कोद्रा",
            "chana" to "हरभरा", "tur" to "तूर", "moong" to "मूग",
            "urad" to "उडीद", "masoor" to "मसूर", "kabuli chana" to "काबुली चणे",
            "rajma" to "राजमा", "cowpea" to "चवळी",
            "mustard" to "मोहरी", "soyabean" to "सोयाबीन", "groundnut" to "भुईमूग",
            "cotton" to "कापूस", "sugarcane" to "ऊस", "turmeric" to "हळद",
            "cumin" to "जिरे", "coriander seed" to "धणे", "sesame" to "तीळ",
            "black pepper" to "काळी मिरी", "cardamom" to "वेलची", "red chilli" to "लाल मिरची"
        ),
        "te" to mapOf(
            "tomato" to "టమాటా", "onion" to "ఉల్లిపాయ", "potato" to "బంగాళాదుంప",
            "green chilli" to "పచ్చిమిర్చి", "garlic" to "వెల్లుల్లి", "ginger" to "అల్లం",
            "brinjal" to "వంకాయ", "cauliflower" to "గోబీపువ్వు", "cabbage" to "క్యాబేజి",
            "lady finger" to "బెండకాయ", "bitter gourd" to "కాకరకాయ", "bottle gourd" to "సొరకాయ",
            "ridge gourd" to "బీరకాయ", "spinach" to "పాలకూర", "fenugreek" to "మెంతికూర",
            "carrot" to "క్యారెట్", "radish" to "ముల్లంగి", "capsicum" to "క్యాప్సికం",
            "green peas" to "బఠాణీ", "beetroot" to "బీట్‌రూట్", "coriander leaves" to "కొత్తిమీర",
            "cucumber" to "దోసకాయ",
            "mango" to "మామిడి", "banana" to "అరటి", "apple" to "ఆపిల్",
            "pomegranate" to "దానిమ్మ", "grapes" to "ద్రాక్ష", "papaya" to "బొప్పాయి",
            "guava" to "జామ", "orange" to "నారింజ", "watermelon" to "పుచ్చకాయ",
            "muskmelon" to "ఖర్బూజ", "pineapple" to "అనాస", "custard apple" to "సీతాఫలం",
            "strawberry" to "స్ట్రాబెర్రీ", "lemon" to "నిమ్మకాయ", "sapota" to "సపోటా",
            "dragon fruit" to "డ్రాగన్ ఫ్రూట్",
            "wheat" to "గోధుమ", "paddy" to "వరి / బియ్యం", "maize" to "మొక్కజొన్న",
            "bajra" to "సజ్జ", "jowar" to "జొన్న", "ragi" to "రాగి",
            "barley" to "బార్లీ", "foxtail millet" to "కొర్ర", "kodo millet" to "అరికెలు",
            "chana" to "శనగలు", "tur" to "కందులు", "moong" to "పెసలు",
            "urad" to "మినుములు", "masoor" to "మసూర్", "kabuli chana" to "కాబూలీ శనగలు",
            "rajma" to "రాజ్మా", "cowpea" to "అలసందలు",
            "mustard" to "ఆవాలు", "soyabean" to "సోయాబీన్", "groundnut" to "వేరుశనగ",
            "cotton" to "ప్రత్తి", "sugarcane" to "చెరకు", "turmeric" to "పసుపు",
            "cumin" to "జీలకర్ర", "coriander seed" to "ధనియాలు", "sesame" to "నువ్వులు",
            "black pepper" to "మిరియాలు", "cardamom" to "ఏలకులు", "red chilli" to "ఎండుమిర్చి"
        ),
        "ta" to mapOf(
            "tomato" to "தக்காளி", "onion" to "வெங்காயம்", "potato" to "உருளைக்கிழங்கு",
            "green chilli" to "பச்சை மிளகாய்", "garlic" to "பூண்டு", "ginger" to "இஞ்சி",
            "brinjal" to "கத்திரிக்காய்", "cauliflower" to "காலிஃபிளவர்", "cabbage" to "முட்டைக்கோஸ்",
            "lady finger" to "வெண்டைக்காய்", "bitter gourd" to "பாகற்காய்", "bottle gourd" to "சுரைக்காய்",
            "ridge gourd" to "பீர்க்கங்காய்", "spinach" to "கீரை", "fenugreek" to "வெந்தயக்கீரை",
            "carrot" to "கேரட்", "radish" to "முள்ளங்கி", "capsicum" to "குடைமிளகாய்",
            "green peas" to "பட்டாணி", "beetroot" to "பீட்ரூட்", "coriander leaves" to "கொத்தமல்லி",
            "cucumber" to "வெள்ளரிக்காய்",
            "mango" to "மாம்பழம்", "banana" to "வாழைப்பழம்", "apple" to "ஆப்பிள்",
            "pomegranate" to "மாதுளை", "grapes" to "திராட்சை", "papaya" to "பப்பாளி",
            "guava" to "கொய்யா", "orange" to "ஆரஞ்சு", "watermelon" to "தர்பூசணி",
            "muskmelon" to "முலாம்பழம்", "pineapple" to "அன்னாசி", "custard apple" to "சீதாப்பழம்",
            "strawberry" to "ஸ்ட்ராபெர்ரி", "lemon" to "எலுமிச்சை", "sapota" to "சப்போட்டா",
            "dragon fruit" to "டிராகன் ஃப்ரூட்",
            "wheat" to "கோதுமை", "paddy" to "நெல் / அரிசி", "maize" to "மக்காச்சோளம்",
            "bajra" to "கம்பு", "jowar" to "சோளம்", "ragi" to "கேழ்வரகு",
            "barley" to "வாற்கோதுமை", "foxtail millet" to "தினை", "kodo millet" to "வரகு",
            "chana" to "கொண்டைக்கடலை", "tur" to "துவரை", "moong" to "பாசிப்பயறு",
            "urad" to "உளுந்து", "masoor" to "மசூர்", "kabuli chana" to "காபூலி கடலை",
            "rajma" to "ராஜ்மா", "cowpea" to "தட்டைப்பயறு",
            "mustard" to "கடுகு", "soyabean" to "சோயாபீன்", "groundnut" to "நிலக்கடலை",
            "cotton" to "பருத்தி", "sugarcane" to "கரும்பு", "turmeric" to "மஞ்சள்",
            "cumin" to "சீரகம்", "coriander seed" to "தனியா", "sesame" to "எள்",
            "black pepper" to "மிளகு", "cardamom" to "ஏலக்காய்", "red chilli" to "காய்ந்த மிளகாய்"
        ),
        "kn" to mapOf(
            "tomato" to "ಟೊಮೆಟೊ", "onion" to "ಈರುಳ್ಳಿ", "potato" to "ಆಲೂಗಡ್ಡೆ",
            "green chilli" to "ಹಸಿಮೆಣಸಿನಕಾಯಿ", "garlic" to "ಬೆಳ್ಳುಳ್ಳಿ", "ginger" to "ಶುಂಠಿ",
            "brinjal" to "ಬದನೆಕಾಯಿ", "cauliflower" to "ಹೂಕೋಸು", "cabbage" to "ಎಲೆಕೋಸು",
            "lady finger" to "ಬೆಂಡೆಕಾಯಿ", "bitter gourd" to "ಹಾಗಲಕಾಯಿ", "bottle gourd" to "ಸೋರೆಕಾಯಿ",
            "ridge gourd" to "ಹೀರೆಕಾಯಿ", "spinach" to "ಪಾಲಕ್", "fenugreek" to "ಮೆಂತ್ಯ",
            "carrot" to "ಕ್ಯಾರೆಟ್", "radish" to "ಮೂಲಂಗಿ", "capsicum" to "ದೊಣ್ಣೆಮೆಣಸಿನಕಾಯಿ",
            "green peas" to "ಹಸಿರು ಬಟಾಣಿ", "beetroot" to "ಬೀಟ್‌ರೂಟ್", "coriander leaves" to "ಕೊತ್ತಂಬರಿ",
            "cucumber" to "ಸೌತೆಕಾಯಿ",
            "mango" to "ಮಾವಿನಹಣ್ಣು", "banana" to "ಬಾಳೆಹಣ್ಣು", "apple" to "ಸೇಬು",
            "pomegranate" to "ದಾಳಿಂಬೆ", "grapes" to "ದ್ರಾಕ್ಷಿ", "papaya" to "ಪಪ್ಪಾಯಿ",
            "guava" to "ಪೇರಲ", "orange" to "ಕಿತ್ತಳೆ", "watermelon" to "ಕಲ್ಲಂಗಡಿ",
            "muskmelon" to "ಖರ್ಬೂಜ", "pineapple" to "ಅನಾನಸ್", "custard apple" to "ಸೀತಾಫಲ",
            "strawberry" to "ಸ್ಟ್ರಾಬೆರಿ", "lemon" to "ನಿಂಬೆಹಣ್ಣು", "sapota" to "ಚಿಕ್ಕು",
            "dragon fruit" to "ಡ್ರ್ಯಾಗನ್ ಫ್ರೂಟ್",
            "wheat" to "ಗೋಧಿ", "paddy" to "ಭತ್ತ / ಅಕ್ಕಿ", "maize" to "ಮೆಕ್ಕೆಜೋಳ",
            "bajra" to "ಸಜ್ಜೆ", "jowar" to "ಜೋಳ", "ragi" to "ರಾಗಿ",
            "barley" to "ಬಾರ್ಲಿ", "foxtail millet" to "ನವಣೆ", "kodo millet" to "ಹಾರಕ",
            "chana" to "ಕಡಲೆ", "tur" to "ತೊಗರಿ", "moong" to "ಹೆಸರುಕಾಳು",
            "urad" to "ಉದ್ದು", "masoor" to "ಮಸೂರ", "kabuli chana" to "ಕಾಬೂಲಿ ಕಡಲೆ",
            "rajma" to "ರಾಜ್ಮಾ", "cowpea" to "ಅಲಸಂದೆ",
            "mustard" to "ಸಾಸಿವೆ", "soyabean" to "ಸೋಯಾಬೀನ್", "groundnut" to "ಶೇಂಗಾ",
            "cotton" to "ಹತ್ತಿ", "sugarcane" to "ಕಬ್ಬು", "turmeric" to "ಅರಿಶಿನ",
            "cumin" to "ಜೀರಿಗೆ", "coriander seed" to "ಕೊತ್ತಂಬರಿ ಬೀಜ", "sesame" to "ಎಳ್ಳು",
            "black pepper" to "ಕಾಳುಮೆಣಸು", "cardamom" to "ಯಾಲಕ್ಕಿ", "red chilli" to "ಒಣಮೆಣಸಿನಕಾಯಿ"
        ),
        "ml" to mapOf(
            "tomato" to "തക്കാളി", "onion" to "ഉള്ളി", "potato" to "ഉരുളക്കിഴങ്ങ്",
            "green chilli" to "പച്ചമുളക്", "garlic" to "വെളുത്തുള്ളി", "ginger" to "ഇഞ്ചി",
            "brinjal" to "വഴുതനങ്ങ", "cauliflower" to "കോളിഫ്ലവർ", "cabbage" to "കാബേജ്",
            "lady finger" to "വെണ്ട", "bitter gourd" to "പാവയ്ക്ക", "bottle gourd" to "ചുരക്ക",
            "ridge gourd" to "പീച്ചിങ്ങ", "spinach" to "ചീര", "fenugreek" to "ഉലുവ",
            "carrot" to "കാരറ്റ്", "radish" to "മുള്ളങ്കി", "capsicum" to "കുടമുളക്",
            "green peas" to "പട്ടാണി", "beetroot" to "ബീറ്റ്‌റൂട്ട്", "coriander leaves" to "മല്ലി",
            "cucumber" to "വെള്ളരിക്ക",
            "mango" to "മാങ്ങ", "banana" to "വാഴപ്പഴം", "apple" to "ആപ്പിൾ",
            "pomegranate" to "മാതളനാരങ്ങ", "grapes" to "മുന്തിരി", "papaya" to "പപ്പായ",
            "guava" to "പേരയ്ക്ക", "orange" to "ഓറഞ്ച്", "watermelon" to "തണ്ണിമത്തൻ",
            "muskmelon" to "കസ്തൂരി മത്തൻ", "pineapple" to "കൈതച്ചക്ക", "custard apple" to "ആത്ത",
            "strawberry" to "സ്ട്രോബെറി", "lemon" to "ചെറുനാരങ്ങ", "sapota" to "സപ്പോട്ട",
            "dragon fruit" to "ഡ്രാഗൺ ഫ്രൂട്ട്",
            "wheat" to "ഗോതമ്പ്", "paddy" to "നെല്ല് / അരി", "maize" to "ചോളം",
            "bajra" to "കമ്പം", "jowar" to "ചോളം", "ragi" to "റാഗി",
            "barley" to "ബാർലി", "foxtail millet" to "തിന", "kodo millet" to "വരഗ്",
            "chana" to "കടല", "tur" to "തുവര", "moong" to "ചെറുപയർ",
            "urad" to "ഉഴുന്ന്", "masoor" to "മസൂർ", "kabuli chana" to "കാബൂലി കടല",
            "rajma" to "രാജ്മ", "cowpea" to "പയർ",
            "mustard" to "കടുക്", "soyabean" to "സോയാബീൻ", "groundnut" to "നിലക്കടല",
            "cotton" to "പരുത്തി", "sugarcane" to "കരിമ്പ്", "turmeric" to "മഞ്ഞൾ",
            "cumin" to "ജീരകം", "coriander seed" to "മല്ലി വിത്ത്", "sesame" to "എള്ള്",
            "black pepper" to "കുരുമുളക്", "cardamom" to "ഏലക്ക", "red chilli" to "ഉണക്കമുളക്"
        ),
        "gu" to mapOf(
            "tomato" to "ટામેટાં", "onion" to "ડુંગળી", "potato" to "બટાટા",
            "green chilli" to "લીલા મરચાં", "garlic" to "લસણ", "ginger" to "આદું",
            "brinjal" to "રીંગણ", "cauliflower" to "ફૂલકોબી", "cabbage" to "કોબીજ",
            "lady finger" to "ભીંડા", "bitter gourd" to "કારેલાં", "bottle gourd" to "દૂધી",
            "ridge gourd" to "તૂરિયા", "spinach" to "પાલક", "fenugreek" to "મેથી",
            "carrot" to "ગાજર", "radish" to "મૂળા", "capsicum" to "શિમલા મરચાં",
            "green peas" to "વટાણા", "beetroot" to "બીટ", "coriander leaves" to "ધાણા",
            "cucumber" to "કાકડી",
            "mango" to "કેરી", "banana" to "કેળાં", "apple" to "સફરજન",
            "pomegranate" to "દાડમ", "grapes" to "દ્રાક્ષ", "papaya" to "પપૈયું",
            "guava" to "જામફળ", "orange" to "સંતરાં / મોસંબી", "watermelon" to "તરબૂચ",
            "muskmelon" to "શક્કરટેટી", "pineapple" to "અનાનસ", "custard apple" to "સીતાફળ",
            "strawberry" to "સ્ટ્રોબેરી", "lemon" to "લીંબુ", "sapota" to "ચીકુ",
            "dragon fruit" to "ડ્રેગન ફ્રૂટ",
            "wheat" to "ઘઉં", "paddy" to "ડાંગર / ચોખા", "maize" to "મકાઈ",
            "bajra" to "બાજરી", "jowar" to "જુવાર", "ragi" to "રાગી / નાગલી",
            "barley" to "જવ", "foxtail millet" to "કાંગ", "kodo millet" to "કોદરા",
            "chana" to "ચણા", "tur" to "તુવેર", "moong" to "મગ",
            "urad" to "અડદ", "masoor" to "મસૂર", "kabuli chana" to "કાબુલી ચણા",
            "rajma" to "રાજમા", "cowpea" to "ચોળા",
            "mustard" to "રાઈ", "soyabean" to "સોયાબીન", "groundnut" to "મગફળી",
            "cotton" to "કપાસ", "sugarcane" to "શેરડી", "turmeric" to "હળદર",
            "cumin" to "જીરું", "coriander seed" to "ધાણા બીજ", "sesame" to "તલ",
            "black pepper" to "કાળા મરી", "cardamom" to "એલચી", "red chilli" to "સૂકા મરચાં"
        ),
        "pa" to mapOf(
            "tomato" to "ਟਮਾਟਰ", "onion" to "ਪਿਆਜ਼", "potato" to "ਆਲੂ",
            "green chilli" to "ਹਰੀ ਮਿਰਚ", "garlic" to "ਲਸਣ", "ginger" to "ਅਦਰਕ",
            "brinjal" to "ਬੈਂਗਣ", "cauliflower" to "ਫੁੱਲ ਗੋਭੀ", "cabbage" to "ਬੰਦ ਗੋਭੀ",
            "lady finger" to "ਭਿੰਡੀ", "bitter gourd" to "ਕਰੇਲਾ", "bottle gourd" to "ਘੀਆ",
            "ridge gourd" to "ਤੋਰੀ", "spinach" to "ਪਾਲਕ", "fenugreek" to "ਮੇਥੀ",
            "carrot" to "ਗਾਜਰ", "radish" to "ਮੂਲੀ", "capsicum" to "ਸ਼ਿਮਲਾ ਮਿਰਚ",
            "green peas" to "ਮਟਰ", "beetroot" to "ਚੁਕੰਦਰ", "coriander leaves" to "ਧਨੀਆ ਪੱਤਾ",
            "cucumber" to "ਖੀਰਾ",
            "mango" to "ਅੰਬ", "banana" to "ਕੇਲਾ", "apple" to "ਸੇਬ",
            "pomegranate" to "ਅਨਾਰ", "grapes" to "ਅੰਗੂਰ", "papaya" to "ਪਪੀਤਾ",
            "guava" to "ਅਮਰੂਦ", "orange" to "ਸੰਤਰਾ", "watermelon" to "ਤਰਬੂਜ਼",
            "muskmelon" to "ਖਰਬੂਜ਼ਾ", "pineapple" to "ਅਨਾਨਾਸ", "custard apple" to "ਸੀਤਾਫਲ",
            "strawberry" to "ਸਟ੍ਰਾਬੈਰੀ", "lemon" to "ਨਿੰਬੂ", "sapota" to "ਚੀਕੂ",
            "dragon fruit" to "ਡ੍ਰੈਗਨ ਫਰੂਟ",
            "wheat" to "ਕਣਕ", "paddy" to "ਝੋਨਾ / ਚੌਲ", "maize" to "ਮੱਕੀ",
            "bajra" to "ਬਾਜਰਾ", "jowar" to "ਜੁਆਰ", "ragi" to "ਰਾਗੀ",
            "barley" to "ਜੌਂ", "foxtail millet" to "ਕੰਗਣੀ", "kodo millet" to "ਕੋਦੋ",
            "chana" to "ਛੋਲੇ", "tur" to "ਅਰਹਰ", "moong" to "ਮੂੰਗ",
            "urad" to "ਉੜਦ", "masoor" to "ਮਸੂਰ", "kabuli chana" to "ਕਾਬੁਲੀ ਛੋਲੇ",
            "rajma" to "ਰਾਜਮਾਂਹ", "cowpea" to "ਲੋਬੀਆ",
            "mustard" to "ਸਰ੍ਹੋਂ", "soyabean" to "ਸੋਇਆਬੀਨ", "groundnut" to "ਮੂੰਗਫਲੀ",
            "cotton" to "ਕਪਾਹ", "sugarcane" to "ਗੰਨਾ", "turmeric" to "ਹਲਦੀ",
            "cumin" to "ਜੀਰਾ", "coriander seed" to "ਧਨੀਆ ਬੀਜ", "sesame" to "ਤਿਲ",
            "black pepper" to "ਕਾਲੀ ਮਿਰਚ", "cardamom" to "ਇਲਾਇਚੀ", "red chilli" to "ਲਾਲ ਮਿਰਚ"
        ),
        "or" to mapOf(
            "tomato" to "ଟମାଟୋ", "onion" to "ପିଆଜ", "potato" to "ଆଳୁ",
            "green chilli" to "ଲଙ୍କା", "garlic" to "ରସୁଣ", "ginger" to "ଅଦା",
            "brinjal" to "ବାଇଗଣ", "cauliflower" to "ଫୁଲକୋବି", "cabbage" to "ବନ୍ଧାକୋବି",
            "lady finger" to "ଭେଣ୍ଡି", "bitter gourd" to "କଲରା", "bottle gourd" to "ଲାଉ",
            "ridge gourd" to "ଜହ୍ନି", "spinach" to "ପାଳଙ୍ଗ", "fenugreek" to "ମେଥି",
            "carrot" to "ଗାଜର", "radish" to "ମୂଳା", "capsicum" to "କ୍ୟାପସିକମ୍",
            "green peas" to "ମଟର", "beetroot" to "ବିଟ୍", "coriander leaves" to "ଧନିଆ ପତ୍ର",
            "cucumber" to "କାକୁଡ଼ି",
            "mango" to "ଆମ୍ବ", "banana" to "କଦଳୀ", "apple" to "ସେଓ",
            "pomegranate" to "ଡାଳିମ୍ବ", "grapes" to "ଅଙ୍ଗୁର", "papaya" to "ଅମୃତଭଣ୍ଡା",
            "guava" to "ପିଜୁଳି", "orange" to "କମଳା", "watermelon" to "ତରଭୂଜ",
            "muskmelon" to "ଖରଭୂଜ", "pineapple" to "ସପୁରି", "custard apple" to "ଆତ",
            "strawberry" to "ଷ୍ଟ୍ରବେରୀ", "lemon" to "ଲେମ୍ବୁ", "sapota" to "ସପେଟା",
            "dragon fruit" to "ଡ୍ରାଗନ ଫଳ",
            "wheat" to "ଗହମ", "paddy" to "ଧାନ / ଚାଉଳ", "maize" to "ମକା",
            "bajra" to "ବାଜରା", "jowar" to "ଜୁଆର", "ragi" to "ମାଣ୍ଡିଆ",
            "barley" to "ଯବ", "foxtail millet" to "କଙ୍ଗୁ", "kodo millet" to "କୋଦୋ",
            "chana" to "ଚଣା", "tur" to "ଅରହର", "moong" to "ମୁଗ",
            "urad" to "ବିରି", "masoor" to "ମସୁର", "kabuli chana" to "କାବୁଲି ଚଣା",
            "rajma" to "ରାଜମା", "cowpea" to "ଲୋବିଆ",
            "mustard" to "ସୋରିଷ", "soyabean" to "ସୋୟାବିନ", "groundnut" to "ବାଦାମ",
            "cotton" to "କପା", "sugarcane" to "ଆଖୁ", "turmeric" to "ହଳଦୀ",
            "cumin" to "ଜୀରା", "coriander seed" to "ଧନିଆ ବିହନ", "sesame" to "ରାଶି",
            "black pepper" to "ଗୋଲମରିଚ", "cardamom" to "ଏଲାଚ", "red chilli" to "ଶୁଖିଲା ଲଙ୍କା"
        )
    )
}
