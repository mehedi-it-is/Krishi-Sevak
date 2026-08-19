package com.krishisevak.app.data.engine

data class CropRecommendInput(
    val season: String = "Kharif (Monsoon)",
    val waterAvailability: String = "Medium (Rainfed / Borewell)",
    val soilType: String = "Alluvial Soil",
    val previousCrop: String? = null,
    val ph: Float? = null
)

data class RecommendedCropItem(
    val cropName: String,
    val iconEmoji: String,
    val suitabilityScore: Int, // 0 to 100
    val reason: String,
    val waterRequirement: String,
    val growingDuration: String,
    val expectedYield: String,
    val considerations: List<String>,
    val fertilizerTip: String
)

object CropRecommendationEngine {

    private data class CropProfile(
        val name: String,
        val emoji: String,
        val seasons: List<String>,
        val waterNeed: String, // "High", "Medium", "Low"
        val soilTypes: List<String>,
        val phRange: Pair<Float, Float>,
        val growingDays: String,
        val yield: String,
        val defaultReason: String,
        val tips: List<String>,
        val fertilizer: String
    )

    private val cropKnowledgeBase = listOf(
        CropProfile(
            name = "Rice / Paddy",
            emoji = "🌾",
            seasons = listOf("kharif", "monsoon"),
            waterNeed = "High",
            soilTypes = listOf("alluvial / loamy", "clayey", "black cotton"),
            phRange = 5.5f to 7.5f,
            growingDays = "120 - 150 days",
            yield = "45 - 60 quintals/ha",
            defaultReason = "Ideal for kharif monsoon season with standing water and heavy clayey or loamy soils.",
            tips = listOf("Maintain 3-5 cm standing water during tillering.", "Adopt System of Rice Intensification (SRI) to save 30% water."),
            fertilizer = "NPK 120:60:40 kg/ha with Zinc Sulfate @ 25 kg/ha basal."
        ),
        CropProfile(
            name = "Wheat",
            emoji = "🌾",
            seasons = listOf("rabi", "winter"),
            waterNeed = "Medium",
            soilTypes = listOf("alluvial / loamy", "black cotton", "clayey"),
            phRange = 6.0f to 7.8f,
            growingDays = "115 - 135 days",
            yield = "40 - 55 quintals/ha",
            defaultReason = "Top winter staple. High productivity in cool temperatures with well-drained loamy soils.",
            tips = listOf("First irrigation at Crown Root Initiation (CRI) 21 days after sowing is vital.", "Opt for HD-2967 or PBW-550 high yielding varieties."),
            fertilizer = "NPK 120:60:40 kg/ha with two split top dressings of Urea."
        ),
        CropProfile(
            name = "Cotton",
            emoji = "🌿",
            seasons = listOf("kharif", "monsoon"),
            waterNeed = "Medium",
            soilTypes = listOf("black cotton", "alluvial / loamy"),
            phRange = 6.0f to 8.0f,
            growingDays = "150 - 180 days",
            yield = "20 - 30 quintals/ha (seed cotton)",
            defaultReason = "Thrives in deep black cotton soils with good moisture retention capacity during warm summers.",
            tips = listOf("Use Bt-Cotton certified hybrids for bollworm resistance.", "Install pheromone traps @ 5 traps/acre for pest surveillance."),
            fertilizer = "NPK 100:50:50 kg/ha with split nitrogen applications."
        ),
        CropProfile(
            name = "Maize (Corn)",
            emoji = "🌽",
            seasons = listOf("kharif", "rabi", "summer"),
            waterNeed = "Medium",
            soilTypes = listOf("alluvial / loamy", "red / laterite", "black cotton"),
            phRange = 5.8f to 7.5f,
            growingDays = "85 - 105 days",
            yield = "50 - 65 quintals/ha",
            defaultReason = "Versatile high-yielding cereal with fast growth and excellent fodder plus grain value.",
            tips = listOf("Sensitive to waterlogging; ensure ridges and furrow sowing.", "Scout for Fall Armyworm (FAW) in early whorl stage."),
            fertilizer = "NPK 120:60:50 kg/ha with Zinc application."
        ),
        CropProfile(
            name = "Chickpea (Chana)",
            emoji = "🫘",
            seasons = listOf("rabi", "winter"),
            waterNeed = "Low",
            soilTypes = listOf("black cotton", "alluvial / loamy", "sandy loam"),
            phRange = 6.0f to 8.0f,
            growingDays = "90 - 110 days",
            yield = "18 - 25 quintals/ha",
            defaultReason = "Drought-tolerant leguminous crop that fixes atmospheric nitrogen, restoring soil health.",
            tips = listOf("Seed treatment with Rhizobium + Trichoderma prevents wilt.", "Ideal rotation crop after rice or maize."),
            fertilizer = "NPK 20:50:20 kg/ha + 20 kg Sulfur/ha."
        ),
        CropProfile(
            name = "Mustard (Sarson)",
            emoji = "🌱",
            seasons = listOf("rabi", "winter"),
            waterNeed = "Low",
            soilTypes = listOf("alluvial / loamy", "sandy loam", "clayey"),
            phRange = 6.0f to 7.5f,
            growingDays = "100 - 120 days",
            yield = "18 - 24 quintals/ha",
            defaultReason = "Low water requiring oilseed crop suited for winter with high market oil value.",
            tips = listOf("Give 2-3 irrigations: at flowering and siliqua development.", "Sulfur application increases oil content by 2-3%."),
            fertilizer = "NPK 80:40:40 kg/ha + 30 kg/ha elemental Sulfur."
        ),
        CropProfile(
            name = "Pearl Millet (Bajra)",
            emoji = "🌾",
            seasons = listOf("kharif", "summer"),
            waterNeed = "Low",
            soilTypes = listOf("sandy loam", "red / laterite", "alluvial / loamy"),
            phRange = 5.5f to 8.5f,
            growingDays = "75 - 90 days",
            yield = "25 - 35 quintals/ha",
            defaultReason = "Extremely hardy drought-tolerant millet suited for arid & semi-arid dryland farming.",
            tips = listOf("Tolerates high temperatures and sandy marginal soils.", "Nutrient-rich grain high in iron & zinc."),
            fertilizer = "NPK 60:30:20 kg/ha."
        ),
        CropProfile(
            name = "Soybean",
            emoji = "🌱",
            seasons = listOf("kharif", "monsoon"),
            waterNeed = "Medium",
            soilTypes = listOf("black cotton", "alluvial / loamy"),
            phRange = 6.0f to 7.5f,
            growingDays = "90 - 105 days",
            yield = "22 - 30 quintals/ha",
            defaultReason = "High-protein oilseed and legume that excels in well-drained medium to heavy soils.",
            tips = listOf("Inoculate seeds with Bradyrhizobium culture.", "Avoid sowing deeper than 3-4 cm."),
            fertilizer = "NPK 30:60:40 kg/ha + 20 kg Sulfur/ha."
        ),
        CropProfile(
            name = "Watermelon / Muskmelon",
            emoji = "🍉",
            seasons = listOf("zaid", "summer"),
            waterNeed = "Low",
            soilTypes = listOf("sandy loam", "alluvial / loamy"),
            phRange = 6.0f to 7.2f,
            growingDays = "70 - 85 days",
            yield = "25 - 35 tonnes/ha",
            defaultReason = "Fast-growing high-margin summer crop ideal for warm weather and sandy loam riverbeds.",
            tips = listOf("Use plastic mulching and drip irrigation to maximize sweetness & size.", "Pollination by bees is critical during early morning."),
            fertilizer = "NPK 80:50:50 kg/ha through fertigation."
        ),
        CropProfile(
            name = "Sugarcane",
            emoji = "🎋",
            seasons = listOf("kharif", "rabi", "spring"),
            waterNeed = "High",
            soilTypes = listOf("alluvial / loamy", "black cotton", "clayey"),
            phRange = 6.5f to 7.8f,
            growingDays = "300 - 360 days",
            yield = "80 - 120 tonnes/ha",
            defaultReason = "Commercial cash crop with high gross returns in regions with guaranteed year-round irrigation.",
            tips = listOf("Adopt trench planting with single bud setts.", "Intercrop with short duration pulses or vegetables."),
            fertilizer = "NPK 250:100:120 kg/ha in multiple splits."
        )
    )

    fun recommend(input: CropRecommendInput, langCode: String = "en"): List<RecommendedCropItem> {
        val code = langCode.lowercase()
        val seasonClean = input.season.lowercase()
        val waterMap = mapOf(
            "high (canal / tube well)" to 3,
            "medium (rainfed / borewell)" to 2,
            "low (dryland / drip)" to 1
        )
        val userWaterScore = waterMap[input.waterAvailability.lowercase()] ?: 2
        val soilClean = input.soilType.lowercase()

        val scored = cropKnowledgeBase.map { crop ->
            var score = 75f

            // 1. Season Matching
            val seasonMatch = crop.seasons.any { seasonClean.contains(it) }
            if (seasonMatch) {
                score += 15f
            } else {
                score -= 35f
            }

            // 2. Water Availability Matching
            val cropWaterScore = when (crop.waterNeed) {
                "High" -> 3
                "Medium" -> 2
                else -> 1
            }
            when {
                userWaterScore >= cropWaterScore -> score += 10f
                userWaterScore == cropWaterScore - 1 -> score -= 15f
                else -> score -= 35f
            }

            // 3. Soil Type Matching
            val soilMatch = crop.soilTypes.any { soilClean.contains(it) }
            if (soilMatch) {
                score += 10f
            } else {
                score -= 5f
            }

            // 4. pH Matching
            input.ph?.let { ph ->
                if (ph in crop.phRange.first..crop.phRange.second) {
                    score += 5f
                } else {
                    score -= 10f
                }
            }

            // 5. Crop Rotation Bonus
            input.previousCrop?.let { prev ->
                val prevLower = prev.lowercase()
                if (crop.name.contains("Chickpea") || crop.name.contains("Mustard")) {
                    if (prevLower.contains("rice") || prevLower.contains("paddy") || prevLower.contains("cotton") || prevLower.contains("maize")) {
                        score += 8f
                    }
                }
                if (crop.name.contains("Wheat") && (prevLower.contains("rice") || prevLower.contains("soybean"))) {
                    score += 5f
                }
            }

            val finalScore = score.coerceIn(15f, 98f).toInt()
            val localizedName = SmartAgriToolsTranslations.getCropName(crop.name, code)

            val localizedReason = when (code) {
                "hi" -> when (crop.name) {
                    "Rice / Paddy" -> "खरीफ मानसून और दोमट/चिकनी मिट्टी के लिए सबसे उपयुक्त। जलभराव सहन करने की उत्तम क्षमता।"
                    "Wheat" -> "सर्दियों की प्रमुख फसल। ठंडे मौसम और अच्छी जल निकासी वाली दोमट मिट्टी में बंपर पैदावार।"
                    "Cotton" -> "काली कपास वाली भारी मिट्टी और गर्म मौसम में उच्च पैदावार देने वाली प्रमुख नकदी फसल।"
                    "Maize (Corn)" -> "तेजी से बढ़ने वाला बहुउपयोगी अनाज। दाना और चारा दोनों के लिए उच्च बाजार मांग।"
                    "Chickpea (Chana)" -> "कम पानी की आवश्यकता वाली दलहनी फसल। हवा से नाइट्रोजन खींचकर जमीन की उर्वरता बढ़ाती है।"
                    "Mustard (Sarson)" -> "कम सिंचाई में सर्दियों की सर्वश्रेष्ठ तिलहनी फसल। तेल की अधिक मात्रा और उच्च बाजार भाव।"
                    "Pearl Millet (Bajra)" -> "सूखे और गर्मी को आसानी से झेलने वाला मोटा अनाज। रेतीली व कम उपजाऊ जमीन के लिए आदर्श।"
                    "Soybean" -> "प्रोटीन और तेल से भरपूर फसल। मध्य और भारी मिट्टी में नाइट्रोजन जोड़कर पैदावार बढ़ाती है।"
                    "Watermelon / Muskmelon" -> "गर्मी (जायद) के मौसम की तेजी से तैयार होने वाली उच्च मुनाफा देने वाली फल फसल।"
                    "Sugarcane" -> "वर्षभर सिंचाई वाले क्षेत्रों के लिए सबसे लाभदायक और सुरक्षित वाणिज्यिक नकदी फसल।"
                    else -> crop.defaultReason
                }
                "mr" -> when (crop.name) {
                    "Rice / Paddy" -> "खरीप पावसाळी हंगाम आणि चिकणमातीसाठी अत्यंत योग्य. पाणी धरून ठेवणाऱ्या जमिनीत भरपूर उत्पादन."
                    "Wheat" -> "रब्बी हंगामातील मुख्य पीक. थंड हवामान आणि पाण्याचा निचरा होणाऱ्या जमिनीत उत्तम उत्पादन."
                    "Cotton" -> "काळ्या कसदार जमिनीत भरपूर नफा देणारे प्रमुख नगदी पीक."
                    "Maize (Corn)" -> "कमी कालावधीत जास्त उत्पादन देणारे पीक. धान्य व जनावरांच्या चाऱ्यासाठी उत्तम."
                    "Chickpea (Chana)" -> "कमी पाण्यात येणारे कडधान्य पीक. जमिनीत नत्र स्थिरीकरण करून सुपीकता वाढवते."
                    "Mustard (Sarson)" -> "कमी पाण्यावर येणारे हिवाळी गळित धान्य. बाजारात चांगला भाव मिळतो."
                    "Pearl Millet (Bajra)" -> "कमी पावसाच्या आणि कोरडवाहू भागात हमखास उत्पादन देणारे पौष्टिक तृणधान्य."
                    "Soybean" -> "काळ्या जमिनीत उत्तम पोषण देणारे गळित धान्य पीक. जमिनीचा पोत सुधारते."
                    "Watermelon / Muskmelon" -> "उन्हाळी हंगामात कमी दिवसांत जास्त नफा मिळवून देणारे फळ पीक."
                    "Sugarcane" -> "बारमाही पाण्याची सोय असल्यास सर्वाधिक खात्रीशीर व नफा मिळवून देणारे पीक."
                    else -> crop.defaultReason
                }
                "bn" -> when (crop.name) {
                    "Rice / Paddy" -> "খরিফ বর্ষা মৌসুম ও দোআঁশ/এঁটেল মাটির জন্য সবচেয়ে উপযুক্ত।"
                    "Wheat" -> "শীতকালীন প্রধান ফসল। ঠান্ডা আবহাওয়া ও সুনিষ্কাশিত জমিতে ভালো ফলন।"
                    "Cotton" -> "কালো মাটিতে উচ্চ উৎপাদনশীল বাণিজ্যিক অর্থকরী ফসল।"
                    "Maize (Corn)" -> "দ্রুত বর্ধনশীল উচ্চ ফলনশীল শস্য ও গোখাদ্যের জন্য চমৎকার।"
                    "Chickpea (Chana)" -> "কম জলের ডাল জাতীয় ফসল। মাটিতে নাইট্রোজেন যোগ করে উর্বরতা বাড়ায়।"
                    "Mustard (Sarson)" -> "শীতের স্বল্প জলের প্রধান তৈলবীজ ফসল। ভালো বাজার মূল্য।"
                    "Pearl Millet (Bajra)" -> "খরা সহনশীল পুষ্টিকর দানাশস্য। শুষ্ক অঞ্চলের জন্য আদর্শ।"
                    "Soybean" -> "প্রোটিন ও তেলে সমৃদ্ধ ফসল। মাটির স্বাস্থ্য ভালো রাখে।"
                    "Watermelon / Muskmelon" -> "গ্রীষ্মকালের স্বল্পমেয়াদী অত্যন্ত লাভজনক ফল চাষ।"
                    "Sugarcane" -> "বার্ষিক সেচযুক্ত অঞ্চলের সর্বোচ্চ লাভজনক বাণিজ্যিক অর্থকরী ফসল।"
                    else -> crop.defaultReason
                }
                "te" -> when (crop.name) {
                    "Rice / Paddy" -> "ఖరీఫ్ వర్షాకాలం మరియు బంకమట్టి నేలలకు అత్యంత అనుకూలం."
                    "Wheat" -> "శీతాకాలపు ప్రధాన ఆహార పంట. చల్లని వాతావరణంలో అధిక దిగుబడి."
                    "Cotton" -> "నల్లరేగడి నేలల్లో అధిక ఆదాయం ఇచ్చే ప్రధాన వాణిజ్య పంట."
                    "Maize (Corn)" -> "త్వరగా ఎదిగే అధిక దిగుబడి నిచ్చే ధాన్యపు పంట."
                    "Chickpea (Chana)" -> "తక్కువ నీటితో సాగయ్యే పప్పుధాన్యపు పంట. నేల సారాన్ని పెంచుతుంది."
                    "Mustard (Sarson)" -> "శీతాకాలంలో తక్కువ నీటితో సాగయ్యే ఆవాల నూనెగింజల పంట."
                    "Pearl Millet (Bajra)" -> "ఎండలను, కరువును తట్టుకునే బలవర్ధకమైన చిరుధాన్యం."
                    "Soybean" -> "ప్రోటీన్ సమృద్ధిగా ఉండే నూనెగింజల పంట. నేల ఆరోగ్యాన్ని మెరుగుపరుస్తుంది."
                    "Watermelon / Muskmelon" -> "వేసవిలో స్వల్ప కాలంలో మంచి లాభాలనిచ్చే పండ్ల తోట."
                    "Sugarcane" -> "ఏడాది పొడవునా నీటి వసతి ఉన్న ప్రాంతాలలో అత్యధిక లాభదాయక పంట."
                    else -> crop.defaultReason
                }
                "ta" -> when (crop.name) {
                    "Rice / Paddy" -> "மழைக்காலத்திற்கும் களிமண் நிலங்களுக்கும் மிகவும் ஏற்ற முக்கிய பயிர்."
                    "Wheat" -> "குளிர்காலத்திற்கு ஏற்ற தானியப் பயிர். நல்ல விளைச்சல் தரும்."
                    "Cotton" -> "கரிசல் மண்ணில் அதிக லாபம் ஈட்டித் தரும் பணப்பயிர்."
                    "Maize (Corn)" -> "குறுகிய காலத்தில் அதிக மகசூல் தரும் மக்காச்சோளப் பயிர்."
                    "Chickpea (Chana)" -> "குறைந்த நீரில் விளையும் பயறு வகை. மண் வளத்தை அதிகரிக்கும்."
                    "Mustard (Sarson)" -> "குளிர்காலத்தில் குறைந்த நீரில் விளையும் எண்ணெய் வித்து பயிர்."
                    "Pearl Millet (Bajra)" -> "வறட்சியைத் தாங்கி வளரும் சத்தான சிறுதானியப் பயிர்."
                    "Soybean" -> "மண்ணில் நைட்ரஜனை நிலைநிறுத்தும் சத்தான சோயாபீன் பயிர்."
                    "Watermelon / Muskmelon" -> "கோடை பருவத்தில் குறுகிய காலத்தில் அதிக லாபம் தரும் பழப்பயிர்."
                    "Sugarcane" -> "ஆண்டு முழுவதும் பாசன வசதி உள்ள நிலங்களுக்கு ஏற்ற கரும்புப் பயிர்."
                    else -> crop.defaultReason
                }
                "kn" -> when (crop.name) {
                    "Rice / Paddy" -> "ಮುಂಗಾರು ಹಂಗಾಮು ಮತ್ತು ಜೇಡಿ ಮಣ್ಣಿಗೆ ಅತ್ಯಂತ ಸೂಕ್ತವಾದ ಬೆಳೆ."
                    "Wheat" -> "ಹಿಂಗಾರು ಹಂಗಾಮಿನ ಪ್ರಮುಖ ಆಹಾರ ಬೆಳೆ. ತಂಪಾದ ಹವಾಮಾನದಲ್ಲಿ ಉತ್ತಮ ಇಳುವರಿ."
                    "Cotton" -> "ಕಪ್ಪು ಹತ್ತಿ ಮಣ್ಣಿನಲ್ಲಿ ಅಧಿಕ ಲಾಭ ನೀಡುವ ಪ್ರಮುಖ ವಾಣಿಜ್ಯ ಬೆಳೆ."
                    "Maize (Corn)" -> "ವೇಗವಾಗಿ ಬೆಳೆಯುವ ಮತ್ತು ಹೆಚ್ಚು ಇಳುವರಿ ಕೊಡುವ ಧಾನ್ಯದ ಬೆಳೆ."
                    "Chickpea (Chana)" -> "ಕಡಿಮೆ ನೀರಿನಲ್ಲಿ ಬರುವ ಕಾಳು ಬೆಳೆ. ಮಣ್ಣಿನ ಫಲವತ್ತತೆ ಹೆಚ್ಚಿಸುತ್ತದೆ."
                    "Mustard (Sarson)" -> "ಚಳಿಗಾಲದಲ್ಲಿ ಕಡಿಮೆ ನೀರಿನಲ್ಲಿ ಬೆಳೆಯುವ ಉತ್ತಮ ಎಣ್ಣೆಕಾಳು ಬೆಳೆ."
                    "Pearl Millet (Bajra)" -> "ಬರವನ್ನು ತಡೆದುಕೊಳ್ಳುವ ಪೌಷ್ಟಿಕ ಸಿರಿಧಾನ್ಯ ಬೆಳೆ."
                    "Soybean" -> "ಪ್ರೋಟೀನ್ ಭರಿತ ಎಣ್ಣೆಕಾಳು ಬೆಳೆ. ಮಣ್ಣಿನ ಆರೋಗ್ಯ ಸುಧಾರಿಸುತ್ತದೆ."
                    "Watermelon / Muskmelon" -> "ಬೇಸಿಗೆ ಹಂಗಾಮಿನಲ್ಲಿ ಕಡಿಮೆ ಅವಧಿಯಲ್ಲಿ ಅಧಿಕ ಲಾಭ ನೀಡುವ ಹಣ್ಣಿನ ಬೆಳೆ."
                    "Sugarcane" -> "ವರ್ಷವಿಡೀ ನೀರಿನ ಸೌಲಭ್ಯವಿರುವ ಪ್ರದೇಶಗಳಿಗೆ ಅತಿ ಹೆಚ್ಚು ಲಾಭದಾಯಕ ಬೆಳೆ."
                    else -> crop.defaultReason
                }
                "gu" -> when (crop.name) {
                    "Rice / Paddy" -> "ચોમાસુ ઋતુ અને ચીકણી જમીન માટે સૌથી ઉત્તમ પાક."
                    "Wheat" -> "શિયાળુ ઋતુનો મુખ્ય ધાન્ય પાક. ઠંડા હવામાનમાં બમ્પર ઉત્પાદન."
                    "Cotton" -> "કાળી જમીનમાં પુષ્કળ નફો આપતો મુખ્ય રોકડિયો પાક."
                    "Maize (Corn)" -> "ઝડપથી વધતો અને દાણા તેમજ ઘાસચારા માટે ઉપયોગી પાક."
                    "Chickpea (Chana)" -> "ઓછા પાણીમાં થતો કઠોળ પાક. જમીનની ફળદ્રુપતા વધારે છે."
                    "Mustard (Sarson)" -> "શિયાળામાં ઓછા પિયતમાં વધુ ઉત્પાદન આપતો તેલીબિયાં પાક."
                    "Pearl Millet (Bajra)" -> "દુષ્કાળ સહન કરતું પૌષ્ટિક ધાન્ય. ઓછી ઉપજાઉ જમીન માટે આદર્શ."
                    "Soybean" -> "પ્રોટીન અને તેલથી ભરપૂર પાક. જમીનમાં નાઇટ્રોજન વધારે છે."
                    "Watermelon / Muskmelon" -> "ઉનાળુ ઋતુમાં ટૂંકા ગાળામાં મોટો નફો આપતો ફળ પાક."
                    "Sugarcane" -> "બારેમાસ પાણીની સગવડવાળા વિસ્તારો માટે ખૂબ જ નફાકારક પાક."
                    else -> crop.defaultReason
                }
                "pa" -> when (crop.name) {
                    "Rice / Paddy" -> "ਸਾਉਣੀ ਦੇ ਮੌਸਮ ਅਤੇ ਚੀਕਣੀ ਜ਼ਮੀਨ ਲਈ ਸਭ ਤੋਂ ਵਧੀਆ ਫ਼ਸਲ।"
                    "Wheat" -> "ਹਾੜ੍ਹੀ ਰੁੱਤ ਦੀ ਮੁੱਖ ਫ਼ਸਲ। ਠੰਢੇ ਮੌਸਮ ਵਿੱਚ ਭਰਪੂਰ ਝਾੜ ਦਿੰਦੀ ਹੈ।"
                    "Cotton" -> "ਕਾਲੀ ਕਪਾਹ ਵਾਲੀ ਜ਼ਮੀਨ ਵਿੱਚ ਵੱਧ ਮੁਨਾਫ਼ਾ ਦੇਣ ਵਾਲੀ ਨਕਦੀ ਫ਼ਸਲ।"
                    "Maize (Corn)" -> "ਤੇਜ਼ੀ ਨਾਲ ਵਧਣ ਵਾਲੀ ਅਨਾਜ ਅਤੇ ਚਾਰੇ ਦੀ ਬਹੁਮੁੱਲੀ ਫ਼ਸਲ।"
                    "Chickpea (Chana)" -> "ਘੱਟ ਪਾਣੀ ਵਾਲੀ ਦਾਲ ਦੀ ਫ਼ਸਲ, ਜੋ ਜ਼ਮੀਨ ਦੀ ਉਪਜਾਊ ਸ਼ਕਤੀ ਵਧਾਉਂਦੀ ਹੈ।"
                    "Mustard (Sarson)" -> "ਸਰਦੀਆਂ ਵਿੱਚ ਘੱਟ ਪਾਣੀ ਨਾਲ ਤਿਆਰ ਹੋਣ ਵਾਲੀ ਤੇਲ ਬੀਜ ਫ਼ਸਲ।"
                    "Pearl Millet (Bajra)" -> "ਸੋਕਾ ਸਹਾਰਨ ਵਾਲਾ ਪੌਸ਼ਟਿਕ ਮੋਟਾ ਅਨਾਜ।"
                    "Soybean" -> "ਪ੍ਰੋਟੀਨ ਅਤੇ ਤੇਲ ਨਾਲ ਭਰਪੂਰ ਫ਼ਸਲ। ਜ਼ਮੀਨ ਦੀ ਸਿਹਤ ਸੁਧਾਰਦੀ ਹੈ।"
                    "Watermelon / Muskmelon" -> "ਗਰਮੀਆਂ ਵਿੱਚ ਥੋੜ੍ਹੇ ਸਮੇਂ ਵਿੱਚ ਵੱਧ ਮੁਨਾਫ਼ਾ ਦੇਣ ਵਾਲਾ ਫਲ।"
                    "Sugarcane" -> "ਸਾਰਾ ਸਾਲ ਪਾਣੀ ਵਾਲੇ ਇਲਾਕਿਆਂ ਲਈ ਸਭ ਤੋਂ ਲਾਹੇਵੰਦ ਨਕਦੀ ਫ਼ਸਲ।"
                    else -> crop.defaultReason
                }
                "ml" -> when (crop.name) {
                    "Rice / Paddy" -> "മഴക്കാലത്തിനും കളിമൺ നിലങ്ങൾക്കും അനുയോജ്യമായ പ്രധാന വിള."
                    "Wheat" -> "ശീതകാലത്ത് നല്ല വിളവ് തരുന്ന ധാന്യവിള."
                    "Cotton" -> "കരിമണ്ണിൽ കൂടുതൽ ലാഭം തരുന്ന നാണ്യവിള."
                    "Maize (Corn)" -> "വേഗത്തിൽ വളരുന്നതും ഉയർന്ന വിളവ് തരുന്നതുമായ ധാന്യം."
                    "Chickpea (Chana)" -> "കുറഞ്ഞ വെള്ളത്തിൽ വളരുന്ന പയറുവർഗ്ഗം. മണ്ണിന്റെ ഫലഭൂയിഷ്ഠത കൂട്ടുന്നു."
                    "Mustard (Sarson)" -> "ശൈത്യകാലത്ത് കുറഞ്ഞ നനവുള്ള എണ്ണക്കുരു വിള."
                    "Pearl Millet (Bajra)" -> "വരൾച്ചയെ അതിജീവിക്കുന്ന പോഷകഗുണമുള്ള ചെറുധാന്യം."
                    "Soybean" -> "പ്രോട്ടീൻ സമൃദ്ധമായ വിള. മണ്ണിന്റെ ആരോഗ്യം മെച്ചപ്പെടുത്തുന്നു."
                    "Watermelon / Muskmelon" -> "വേനൽക്കാലത്ത് ചുരുങ്ങിയ ദിവസങ്ങൾക്കുള്ളിൽ ലാഭം തരുന്ന പഴവർഗ്ഗം."
                    "Sugarcane" -> "വർഷം മുഴുവൻ ജലലഭ്യതയുള്ള സ്ഥലങ്ങൾക്ക് അനുയോജ്യമായ കരിമ്പ് കൃഷി."
                    else -> crop.defaultReason
                }
                "or" -> when (crop.name) {
                    "Rice / Paddy" -> "ଖରିଫ ବର୍ଷାଋତୁ ଏବଂ ପଟୁ/ମଟାଳ ମାଟି ପାଇଁ ସବୁଠାରୁ ଉପଯୁକ୍ତ ଫସଲ।"
                    "Wheat" -> "ଶୀତଋତୁର ପ୍ରମୁଖ ଫସଲ। ଥଣ୍ଡା ପାଗରେ ଉଚ୍ଚ ଅମଳ ମିଳିଥାଏ।"
                    "Cotton" -> "କଳା ମାଟିରେ ଅଧିକ ଲାଭ ଦେଉଥିବା ପ୍ରମୁଖ ଅର୍ଥକରୀ ଫସଲ।"
                    "Maize (Corn)" -> "ଶୀଘ୍ର ବଢ଼ୁଥିବା ଉଚ୍ଚ ଅମଳକ୍ଷମ ଦାନାଶସ୍ୟ ଓ ଗୋଖାଦ୍ୟ ଫସଲ।"
                    "Chickpea (Chana)" -> "କମ ପାଣି ଆବଶ୍ୟକ କରୁଥିବା ଡାଲି ଜାତୀୟ ଫସଲ। ମାଟିର ଉର୍ବରତା ବଢ଼ାଏ।"
                    "Mustard (Sarson)" -> "ଶୀତଦିନିଆ କମ ଜଳସେଚନରେ ଅଧିକ ତୈଳବୀଜ ଅମଳ ଦେଉଥିବା ଫସଲ।"
                    "Pearl Millet (Bajra)" -> "ଖରା ଓ ମରୁଡ଼ି ସହ୍ୟ କରିପାରୁଥିବା ପୌଷ୍ଟିକ ମିଲେଟ ଶସ୍ୟ।"
                    "Soybean" -> "ପ୍ରୋଟିନ ଓ ତେଲରେ ଭରପୂର ଫସଲ। ମାଟିର ସ୍ୱାସ୍ଥ୍ୟ ଭଲ ରଖେ।"
                    "Watermelon / Muskmelon" -> "ଗ୍ରୀଷ୍ମ ଋତୁରେ କମ ସମୟରେ ଅଧିକ ଲାଭ ଦେଉଥିବା ଫଳ ଫସଲ।"
                    "Sugarcane" -> "ସାରା ବର୍ଷ ଜଳସେଚନ ଥିବା ଜମି ପାଇଁ ଅତ୍ୟନ୍ତ ଲାଭଦାୟକ ଆଖୁ ଫସଲ।"
                    else -> crop.defaultReason
                }
                else -> crop.defaultReason
            }

            RecommendedCropItem(
                cropName = localizedName,
                iconEmoji = crop.emoji,
                suitabilityScore = finalScore,
                reason = localizedReason,
                waterRequirement = when (code) {
                    "hi" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} मांग"
                    "mr" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} गरज"
                    "bn" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} চাহিদা"
                    "te" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} అవసరం"
                    "ta" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} தேவை"
                    "kn" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} ಅಗತ್ಯ"
                    "gu" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} જરૂરિયાત"
                    "pa" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} ਲੋੜ"
                    "ml" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} ആവശ്യം"
                    "or" -> "${SmartAgriToolsTranslations.getWaterLevelName(crop.waterNeed, code)} ଆବଶ୍ୟକତା"
                    else -> "${crop.waterNeed} (${crop.waterNeed.lowercase()} water demand)"
                },
                growingDuration = crop.growingDays,
                expectedYield = crop.yield,
                considerations = crop.tips,
                fertilizerTip = crop.fertilizer
            )
        }

        return scored.sortedByDescending { it.suitabilityScore }.take(5)
    }
}
