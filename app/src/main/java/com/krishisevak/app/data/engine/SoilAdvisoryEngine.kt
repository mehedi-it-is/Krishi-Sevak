package com.krishisevak.app.data.engine

data class SoilAnalysisInput(
    val soilType: String = "Alluvial Soil",
    val ph: Float = 6.8f,
    val nitrogenPpm: Float = 140f,
    val phosphorusPpm: Float = 24f,
    val potassiumPpm: Float = 180f
)

data class SoilAnalysisResult(
    val summary: String,
    val soilHealthIndex: Int,
    val deficiencies: List<String>,
    val fertilizerRecommendations: List<String>,
    val organicAmendments: List<String>,
    val precautions: List<String>,
    val confidence: String = "High"
)

data class FertilizerAcreageResult(
    val cropName: String,
    val acreage: Float,
    val unit: String,
    val soilType: String,
    val ureaKg: Float,
    val ureaBags45kg: Float,
    val dapKg: Float,
    val dapBags50kg: Float,
    val mopKg: Float,
    val mopBags50kg: Float,
    val sspAlternativeKg: Float,
    val sspAlternativeBags50kg: Float,
    val basalSchedule: String,
    val firstTopDressingSchedule: String,
    val secondTopDressingSchedule: String,
    val micronutrientTip: String
)

object SoilAdvisoryEngine {

    fun analyzeSoil(input: SoilAnalysisInput, langCode: String = "en"): SoilAnalysisResult {
        val deficiencies = mutableListOf<String>()
        val recommendations = mutableListOf<String>()
        val organicAmendments = mutableListOf<String>()
        val precautions = mutableListOf<String>()

        var healthScore = 85
        val code = langCode.lowercase()

        // 1. pH Evaluation
        when {
            input.ph < 6.0f -> {
                healthScore -= 15
                when (code) {
                    "hi" -> {
                        deficiencies.add("अम्लीय मिट्टी (pH ${input.ph}): उच्च अम्लता से फास्फोरस और कैल्शियम का अवशोषण रुकता है।")
                        recommendations.add("मिट्टी की अम्लता दूर करने के लिए 250-400 किग्रा/एकड़ कृषि चूना (कैल्शियम कार्बोनेट) डालें।")
                        organicAmendments.add("खेत में अच्छी सड़ी लकड़ी की राख और गोबर की खाद 3 टन/एकड़ की दर से मिलाएं।")
                    }
                    "mr" -> {
                        deficiencies.add("आम्लयुक्त जमीन (pH ${input.ph}): जास्त आम्लतेमुळे फॉस्फरस व कॅल्शियम शोषण कमी होते.")
                        recommendations.add("जमिनीची आम्लता कमी करण्यासाठी २५०-४०० किलो/एकर कृषी चुना मिसळा.")
                        organicAmendments.add("चांगले कुजलेले शेणखत किंवा लाकडाची राख ३ टन/एकर वापरा.")
                    }
                    "bn" -> {
                        deficiencies.add("অম্লীয় মাটি (pH ${input.ph}): অতিরিক্ত অম্লতার কারণে ফসফরাস ও ক্যালসিয়াম শোষণ ব্যাহত হয়।")
                        recommendations.add("মাটির অম্লতা দূর করতে ২৫০-৪০০ কেজি/একর হারে কৃষি চুন প্রয়োগ করুন।")
                        organicAmendments.add("৩ টন/একর হারে পচা গোবর সার বা কাঠের ছাই মাটিতে মিশিয়ে দিন।")
                    }
                    "te" -> {
                        deficiencies.add("ఆమ్ల నేల (pH ${input.ph}): అధిక ఆమ్లత్వం వల్ల భాస్వరం మరియు కాల్షియం శోషణ తగ్గుతుంది.")
                        recommendations.add("నేల ఆమ్లత్వాన్ని తగ్గించడానికి ఎకరాకు 250-400 కిలోల వ్యవసాయ సున్నం వేయండి.")
                        organicAmendments.add("ఎకరాకు 3 టన్నుల బాగా కుళ్ళిన పశువుల ఎరువును కలపండి.")
                    }
                    "ta" -> {
                        deficiencies.add("அமில மண் (pH ${input.ph}): பாஸ்பரஸ் மற்றும் கால்சியம் சத்துக்களை பயிர்கள் உறிஞ்சுவது குறைகிறது.")
                        recommendations.add("மண் அமிலத்தன்மையை சரிசெய்ய ஏக்கருக்கு 250-400 கிலோ விவசாய சுண்ணாம்பு இடவும்.")
                        organicAmendments.add("ஏக்கருக்கு 3 டன் நன்கு மக்கிய தொழுவுரம் இடவும்.")
                    }
                    "kn", "ml", "gu", "pa", "or" -> {
                        deficiencies.add("Acidic Soil (pH ${input.ph}): High acidity restricts Phosphorus and Calcium absorption.")
                        recommendations.add("Apply Agricultural Lime @ 250-400 kg/acre to neutralize soil acidity.")
                        organicAmendments.add("Apply Well-decomposed Farmyard Manure (FYM) @ 3 tonnes/acre.")
                    }
                    else -> {
                        deficiencies.add("Acidic Soil (pH ${input.ph}): High acidity restricts Phosphorus and Calcium absorption.")
                        recommendations.add("Apply Agricultural Lime (Calcium Carbonate) @ 250-400 kg/acre to neutralize soil acidity.")
                        organicAmendments.add("Apply Well-decomposed Wood Ash and Farmyard Manure (FYM) @ 3 tonnes/acre.")
                    }
                }
            }
            input.ph > 8.0f -> {
                healthScore -= 15
                when (code) {
                    "hi" -> {
                        deficiencies.add("क्षारीय / लवणीय मिट्टी (pH ${input.ph}): जिंक और आयरन की भारी कमी हो सकती है।")
                        recommendations.add("300 किग्रा/एकड़ कृषि जिप्सम (कैल्शियम सल्फेट) डालें और गहरी सिंचाई करें।")
                        organicAmendments.add("कार्बनिक अम्ल छोड़ने के लिए हरी खाद (ढैंचा / सनई) को फूल आने से पहले मिट्टी में दबाएं।")
                    }
                    "mr" -> {
                        deficiencies.add("क्षारयुक्त / चोपण जमीन (pH ${input.ph}): जस्त व लोहाची कमतरता निर्माण होते.")
                        recommendations.add("३०० किलो/एकर कृषी जिप्सम टाकून पाण्याचा चांगला निचरा करावा.")
                        organicAmendments.add("सेंद्रिय कर्ब वाढवण्यासाठी ताग किंवा धैंच्याचे हिरवळीचे खत जमिनीत गाडा.")
                    }
                    "bn" -> {
                        deficiencies.add("ক্ষারীয় / লবণাক্ত মাটি (pH ${input.ph}): জিঙ্ক ও আয়রনের ঘাটতি দেখা দেয়।")
                        recommendations.add("৩০০ কেজি/একর কৃষি জিপসাম প্রয়োগ করে গভীর সেচ দিন।")
                        organicAmendments.add("সবুজ সার (ধৈঞ্চা) ফুল আসার আগেই মাটিতে মিশিয়ে দিন।")
                    }
                    "te" -> {
                        deficiencies.add("క్షార నేల (pH ${input.ph}): జింక్ మరియు ఐరన్ లోపం ఏర్పడుతుంది.")
                        recommendations.add("ఎకరాకు 300 కిలోల జిప్సం వేసి సమృద్ధిగా నీరు పెట్టండి.")
                        organicAmendments.add("జీలుగ లేదా జనుము పచ్చిరొట్ట ఎరువును నేలలో కలియదున్నండి.")
                    }
                    "ta" -> {
                        deficiencies.add("கார மண் (pH ${input.ph}): துத்தநாகம் மற்றும் இரும்புச் சத்து பற்றாக்குறை ஏற்படும்.")
                        recommendations.add("ஏக்கருக்கு 300 கிலோ ஜிப்சம் இட்டு நீர் பாய்ச்சவும்.")
                        organicAmendments.add("தக்கைப்பூண்டு அல்லது சணப்பை போன்ற பசுந்தாள் உரங்களை மண்ணில் மடக்கவும்.")
                    }
                    else -> {
                        deficiencies.add("Alkaline / Saline Soil (pH ${input.ph}): High alkalinity induces Zinc and Iron chlorosis.")
                        recommendations.add("Apply Agricultural Gypsum (Calcium Sulphate) @ 300 kg/acre followed by deep leaching irrigation.")
                        organicAmendments.add("Incorporate Green Manure (Dhaincha / Sunhemp) before flowering to release organic acids.")
                    }
                }
            }
            else -> {
                when (code) {
                    "hi" -> recommendations.add("मिट्टी का pH बिल्कुल संतुलित है (${input.ph})। यह अधिकांश फसलों के लिए उत्तम है।")
                    "mr" -> recommendations.add("जमिनीचा सामू (pH ${input.ph}) अत्यंत योग्य आहे. सर्व पिकांसाठी उत्तम.")
                    "bn" -> recommendations.add("মাটির pH মান উপযুক্ত (${input.ph})। সব ধরণের ফসলের জন্য আদর্শ।")
                    "te" -> recommendations.add("నేల pH విలువ సమతుల్యంగా ఉంది (${input.ph}). పంటల సాగుకు అనుకూలం.")
                    "ta" -> recommendations.add("மண்ணின் pH அளவு சிறந்தது (${input.ph}). பயிர் வளர்ச்சிக்கு உகந்தது.")
                    "kn" -> recommendations.add("ಮಣ್ಣಿನ pH ಮಟ್ಟ ಸಮತೋಲನದಲ್ಲಿದೆ (${input.ph}). ಉತ್ತಮ ಬೆಳೆ ಇಳುವರಿಗೆ ಸೂಕ್ತ.")
                    "gu" -> recommendations.add("જમીનનો pH સંતુલિત છે (${input.ph}). પાકના સારા વિકાસ માટે ઉત્તમ.")
                    "pa" -> recommendations.add("ਮਿੱਟੀ ਦਾ pH ਬਿਲਕੁਲ ਸਹੀ ਹੈ (${input.ph})। ਫ਼ਸਲਾਂ ਲਈ ਢੁਕਵਾਂ।")
                    "or" -> recommendations.add("ମାଟିର pH ସନ୍ତୁଳିତ ଅଛି (${input.ph})। ଫସଲ ଚାଷ ପାଇଁ ଉତ୍ତମ।")
                    else -> recommendations.add("Soil pH is optimal (${input.ph}). Suitable for most cereal, pulse, and horticultural crops.")
                }
            }
        }

        // 2. Nitrogen Evaluation
        when {
            input.nitrogenPpm < 100f -> {
                healthScore -= 20
                when (code) {
                    "hi" -> {
                        deficiencies.add("गंभीर नाइट्रोजन की कमी (${input.nitrogenPpm.toInt()} ppm): पौधों की वृद्धि रुकना और निचली पत्तियों का पीला पड़ना।")
                        recommendations.add("यूरिया (46% N) को 3 भागों में दें: 50% बुवाई पर, 25% कल्ले फूटते समय (21 दिन), 25% फूल आने से पहले।")
                        organicAmendments.add("2 टन/एकड़ केंचुआ खाद (वर्मीकंपोस्ट) या 100 किग्रा नीम खली डालें।")
                    }
                    "mr" -> {
                        deficiencies.add("नायट्रोजनची तीव्र कमतरता (${input.nitrogenPpm.toInt()} ppm): पिकाची वाढ खुंटणे व पाने पिवळी पडणे.")
                        recommendations.add("युरिया खत ३ हप्त्यांत द्या: ५०% पेरणीवेळी, २५% फुटवे फुटताना (२१ दिवस), २५% पोटरी अवस्थेत.")
                        organicAmendments.add("२ टन/एकर गांडूळ खत किंवा १०० किलो निंबोळी पेंड वापरा.")
                    }
                    "bn" -> {
                        deficiencies.add("নাইট্রোজেনের তীব্র ঘাটতি (${input.nitrogenPpm.toInt()} ppm): গাছের বৃদ্ধি কমে যাওয়া ও নিচের পাতা হলুদ হওয়া।")
                        recommendations.add("ইউরিয়া সার ৩ কিস্তিতে দিন: ৫০% রোপণের সময়, ২৫% কুশি অবস্থায়, ২৫% থোড় আসার আগে।")
                        organicAmendments.add("২ টন/একর কেঁচো সার (ভার্মিকম্পোস্ট) বা নিম খোল প্রয়োগ করুন।")
                    }
                    "te" -> {
                        deficiencies.add("నత్రజని లోపం (${input.nitrogenPpm.toInt()} ppm): మొక్కల పెరుగుదల తగ్గడం మరియు ఆకులు పసుపు రంగులోకి మారడం.")
                        recommendations.add("యూరియాను 3 దఫాలుగా వేయండి: 50% నాట్లు వేసేటప్పుడు, 25% పిలకల దశలో, 25% చిరుపొట్ట దశలో.")
                        organicAmendments.add("ఎకరాకు 2 టన్నుల వర్మీకంపోస్ట్ లేదా వేపపిండి వేయండి.")
                    }
                    "ta" -> {
                        deficiencies.add("நைட்ரஜன் பற்றாக்குறை (${input.nitrogenPpm.toInt()} ppm): பயிர் வளர்ச்சி குன்றி கீழ் இலைகள் மஞ்சளாதல்.")
                        recommendations.add("யூரியாவை 3 தவணைகளாக இடவும்: 50% அடியுரமாக, 25% தூர் கட்டும் போது, 25% பூக்கும் முன்.")
                        organicAmendments.add("ஏக்கருக்கு 2 டன் மண்புழு உரம் அல்லது வேப்பம் புண்ணாக்கு இடவும்.")
                    }
                    else -> {
                        deficiencies.add("Severe Nitrogen Deficiency (${input.nitrogenPpm.toInt()} ppm): Stunted crop growth and leaf yellowing.")
                        recommendations.add("Apply Urea (46% N) in 3 split doses: 50% basal, 25% at tillering (21 days), 25% before panicle emergence.")
                        organicAmendments.add("Apply Vermicompost @ 2 tonnes/acre or Neem Cake @ 100 kg/acre.")
                    }
                }
            }
            input.nitrogenPpm > 240f -> {
                when (code) {
                    "hi" -> precautions.add("अत्यधिक नाइट्रोजन: फसल गिरने (Lodging) और कीटों का खतरा बढ़ सकता है। यूरिया की खुराक 30% घटाएं।")
                    "mr" -> precautions.add("जास्त नायट्रोजन: पीक लोळण्याची व कीड वाढण्याची शक्यता. युरियाचे प्रमाण ३०% कमी करा.")
                    "bn" -> precautions.add("অতিরিক্ত নাইট্রোজেন: গাছ হেলে পড়ার ঝুঁকি বাড়ে। ইউরিয়ার মাত্রা ৩০% কমান।")
                    "te" -> precautions.add("అధిక నత్రజని: తెగుళ్ల వ్యాప్తి పెరుగుతుంది. యూరియా వాడకాన్ని 30% తగ్గించండి.")
                    "ta" -> precautions.add("அதிக நைட்ரஜன்: பூச்சி தாக்குதல் அதிகரிக்கும். யூரியா அளவை 30% குறைக்கவும்.")
                    else -> precautions.add("Excess Nitrogen detected: Increases vegetative softness and lodging risk. Reduce Urea dosage by 30%.")
                }
            }
            else -> {
                when (code) {
                    "hi" -> recommendations.add("नाइट्रोजन का स्तर संतुलित है (${input.nitrogenPpm.toInt()} ppm)।")
                    "mr" -> recommendations.add("नायट्रोजनची पातळी संतुलित आहे (${input.nitrogenPpm.toInt()} ppm).")
                    "bn" -> recommendations.add("নাইট্রোজেনের মাত্রা স্বাভাবিক (${input.nitrogenPpm.toInt()} ppm)।")
                    "te" -> recommendations.add("నత్రజని సమతుల్యంగా ఉంది (${input.nitrogenPpm.toInt()} ppm).")
                    "ta" -> recommendations.add("நைட்ரஜன் அளவு சீராக உள்ளது (${input.nitrogenPpm.toInt()} ppm).")
                    else -> recommendations.add("Nitrogen level is balanced (${input.nitrogenPpm.toInt()} ppm). Follow standard maintenance dosage.")
                }
            }
        }

        // 3. Phosphorus Evaluation
        when {
            input.phosphorusPpm < 18f -> {
                healthScore -= 15
                when (code) {
                    "hi" -> {
                        deficiencies.add("फास्फोरस की कमी (${input.phosphorusPpm.toInt()} ppm): जड़ों का कमजोर विकास और देरी से फूल आना।")
                        recommendations.add("बुवाई के समय डीएपी (DAP 18:46:0) 50 किग्रा/एकड़ या एसएसपी (SSP) 150 किग्रा/एकड़ जड़ के पास दें।")
                        organicAmendments.add("फास्फोरस घोलक जीवाणु (PSB जैव उर्वरक) 2 किग्रा/एकड़ गोबर खाद में मिलाकर डालें।")
                    }
                    "mr" -> {
                        deficiencies.add("फॉस्फरसची कमतरता (${input.phosphorusPpm.toInt()} ppm): मुळांची वाढ न होणे व फुले उशिरा येणे.")
                        recommendations.add("पेरणीवेळी डीएपी (DAP) ५० किलो/एकर किंवा सिंगल सुपर फॉस्फेट (SSP) १५० किलो/एकर द्या.")
                        organicAmendments.add("पीएसबी (PSB) जिवाणू खत २ किलो/एकर शेणखतात मिसळून वापरा.")
                    }
                    "bn" -> {
                        deficiencies.add("ফসফরাসের ঘাটতি (${input.phosphorusPpm.toInt()} ppm): শিকড়ের দুর্বল বিকাশ।")
                        recommendations.add("বপনের সময় ডিএপি (DAP) ৫০ কেজি/একর বা এসএসপি (SSP) ১৫০ কেজি/একর প্রয়োগ করুন।")
                        organicAmendments.add("পিএসবি (PSB) জৈব সার ২ কেজি/একর গোবরের সাথে মিশিয়ে প্রয়োগ করুন।")
                    }
                    "te" -> {
                        deficiencies.add("భాస్వరం లోపం (${input.phosphorusPpm.toInt()} ppm): వేరు వ్యవస్థ బలహీనపడటం.")
                        recommendations.add("నాట్లు వేసే సమయంలో ఎకరాకు 50 కిలోల డీఏపీ (DAP) లేదా 150 కిలోల ఎస్ఎస్‌పీ వేయండి.")
                        organicAmendments.add("పీఎస్‌బీ (PSB) బయో-ఫెర్టిలైజర్ 2 కిలోలు పశువుల ఎరువుతో కలిపి వేయండి.")
                    }
                    "ta" -> {
                        deficiencies.add("பாஸ்பரஸ் பற்றாக்குறை (${input.phosphorusPpm.toInt()} ppm): வேர் வளர்ச்சி பாதிப்பு.")
                        recommendations.add("அடியுரமாக ஏக்கருக்கு 50 கிலோ டிஏபி (DAP) அல்லது 150 கிலோ எஸ்எஸ்பி இடவும்.")
                        organicAmendments.add("பிஎஸ்பி (PSB) உயிர் உரத்தை தொழுவுரத்துடன் கலந்து இடவும்.")
                    }
                    else -> {
                        deficiencies.add("Low Phosphorus (${input.phosphorusPpm.toInt()} ppm): Poor root development and delayed flowering.")
                        recommendations.add("Apply DAP (18:46:0) @ 50 kg/acre or SSP @ 150 kg/acre as basal dose.")
                        organicAmendments.add("Inoculate soil with Phosphate Solubilizing Bacteria (PSB) @ 2 kg/acre.")
                    }
                }
            }
            else -> {
                when (code) {
                    "hi" -> recommendations.add("फास्फोरस की उपलब्धता अच्छी है (${input.phosphorusPpm.toInt()} ppm)।")
                    "mr" -> recommendations.add("फॉस्फरसचे प्रमाण समाधानकारक आहे (${input.phosphorusPpm.toInt()} ppm).")
                    "bn" -> recommendations.add("ফসফরাসের মাত্রা সন্তোষজনক (${input.phosphorusPpm.toInt()} ppm)।")
                    "te" -> recommendations.add("భాస్వరం లభ్యత బాగుంది (${input.phosphorusPpm.toInt()} ppm).")
                    "ta" -> recommendations.add("பாஸ்பரஸ் அளவு போதுமானதாக உள்ளது (${input.phosphorusPpm.toInt()} ppm).")
                    else -> recommendations.add("Phosphorus availability is good (${input.phosphorusPpm.toInt()} ppm).")
                }
            }
        }

        // 4. Potassium Evaluation
        when {
            input.potassiumPpm < 120f -> {
                healthScore -= 10
                when (code) {
                    "hi" -> {
                        deficiencies.add("पोटाश की कमी (${input.potassiumPpm.toInt()} ppm): दाना हल्का रहना, तना कमजोर होना और पत्तियों के किनारे सूखना।")
                        recommendations.add("खेत की तैयारी के समय म्यूटरेट ऑफ पोटाश (MOP 0:0:60) 25-35 किग्रा/एकड़ डालें।")
                    }
                    "mr" -> {
                        deficiencies.add("पोटॅशची कमतरता (${input.potassiumPpm.toInt()} ppm): दाण्यांचे वजन कमी होणे व खोड कमकुवत होणे.")
                        recommendations.add("जमीन तयार करताना एमओपी (MOP) २५-३५ किलो/एकर द्या.")
                    }
                    "bn" -> {
                        deficiencies.add("পটাশিয়ামের ঘাটতি (${input.potassiumPpm.toInt()} ppm): দানা অপুষ্ট হওয়া ও কান্ড দুর্বল হওয়া।")
                        recommendations.add("জমি তৈরির সময় এমওপি (MOP) ২৫-৩৫ কেজি/একর প্রয়োগ করুন।")
                    }
                    "te" -> {
                        deficiencies.add("పొటాషియం లోపం (${input.potassiumPpm.toInt()} ppm): గింజ బరువు తగ్గడం మరియు కాండం బలహీనపడటం.")
                        recommendations.add("దుక్కి సమయంలో ఎకరాకు 25-35 కిలోల ఎంఓపీ (MOP) వేయండి.")
                    }
                    "ta" -> {
                        deficiencies.add("பொட்டாசியம் பற்றாக்குறை (${input.potassiumPpm.toInt()} ppm): மணி எடை குறைதல் மற்றும் தண்டு பலவீனமடைதல்.")
                        recommendations.add("நிலம் தயாரிக்கும் போது ஏக்கருக்கு 25-35 கிலோ எம்ஓபி (MOP) இடவும்.")
                    }
                    else -> {
                        deficiencies.add("Potassium Deficiency (${input.potassiumPpm.toInt()} ppm): Reduced grain weight and leaf edge scorching.")
                        recommendations.add("Apply Muriate of Potash (MOP 0:0:60) @ 25-35 kg/acre during field preparation.")
                    }
                }
            }
            else -> {
                when (code) {
                    "hi" -> recommendations.add("पोटाश की स्थिति उत्तम है (${input.potassiumPpm.toInt()} ppm)। यह सूखे और रोगों से लड़ने में मदद करता है।")
                    "mr" -> recommendations.add("पोटॅशचे प्रमाण उत्तम आहे (${input.potassiumPpm.toInt()} ppm). कीड व रोगप्रतिकारशक्ती वाढवते.")
                    "bn" -> recommendations.add("পটাশিয়ামের মাত্রা চমৎকার (${input.potassiumPpm.toInt()} ppm)।")
                    "te" -> recommendations.add("పొటాషియం లభ్యత బాగుంది (${input.potassiumPpm.toInt()} ppm).")
                    "ta" -> recommendations.add("பொட்டாசியம் அளவு திருப்திகரமாக உள்ளது (${input.potassiumPpm.toInt()} ppm).")
                    else -> recommendations.add("Potassium status is healthy (${input.potassiumPpm.toInt()} ppm). Enhances drought and pest resistance.")
                }
            }
        }

        val soilLabel = SmartAgriToolsTranslations.getSoilTypeName(input.soilType, code)
        val finalHealth = healthScore.coerceIn(30, 100)

        val summary = when (code) {
            "hi" -> "$soilLabel (pH ${input.ph}) के लिए मृदा रिपोर्ट: समग्र स्वास्थ्य सूचकांक $finalHealth/100 है। ${deficiencies.size} कमियों की पहचान कर खाद की सिफारिश तैयार की गई है।"
            "mr" -> "$soilLabel (pH ${input.ph}) साठी माती अहवाल: आरोग्य निर्देशांक $finalHealth/100 आहे. ${deficiencies.size} घटकांची कमतरता आढळली आहे."
            "bn" -> "$soilLabel (pH ${input.ph}) এর মৃত্তিকা রিপোর্ট: সামগ্রিক স্বাস্থ্য সূচক $finalHealth/100। ${deficiencies.size}টি ঘাটতি শনাক্ত করা হয়েছে।"
            "te" -> "$soilLabel (pH ${input.ph}) నేల పరీక్ష నివేదిక: సాయిల్ హెల్త్ ఇండెక్స్ $finalHealth/100. ${deficiencies.size} లోపాలు గుర్తించబడ్డాయి."
            "ta" -> "$soilLabel (pH ${input.ph}) மண் பரிசோதனை அறிக்கை: மண் வள குறியீடு $finalHealth/100. ${deficiencies.size} சத்து பற்றாக்குறைகள் கண்டறியப்பட்டுள்ளன."
            "kn" -> "$soilLabel (pH ${input.ph}) ಮಣ್ಣು ಪರೀಕ್ಷೆ ವರದಿ: ಮಣ್ಣಿನ ಆರೋಗ್ಯ ಸೂಚ್ಯಂಕ $finalHealth/100. ${deficiencies.size} ಕೊರತೆಗಳನ್ನು ಗುರುತಿಸಲಾಗಿದೆ."
            "gu" -> "$soilLabel (pH ${input.ph}) જમીન રિપોર્ટ: એકંદર સ્વાસ્થ્ય ઇન્ડેક્સ $finalHealth/100 છે. ${deficiencies.size} ખામીઓ ઓળખવામાં આવી છે."
            "pa" -> "$soilLabel (pH ${input.ph}) ਮਿੱਟੀ ਰਿਪੋਰਟ: ਸਿਹਤ ਸੂਚਕਾਂਕ $finalHealth/100 ਹੈ। ${deficiencies.size} ਕਮੀਆਂ ਦੀ ਪਛਾਣ ਕੀਤੀ ਗਈ ਹੈ।"
            "or" -> "$soilLabel (pH ${input.ph}) ମାଟି ପରୀକ୍ଷା ରିପୋର୍ଟ: ମାଟି ସ୍ୱାସ୍ଥ୍ୟ ସୂଚକାଙ୍କ $finalHealth/100 ଅଟେ। ${deficiencies.size}ଟି ଅଭାବ ଚିହ୍ନଟ ହୋଇଛି।"
            else -> "Soil Diagnostic Report for ${input.soilType} (pH ${input.ph}): Overall Soil Health Index is $finalHealth/100. ${deficiencies.size} deficiencies diagnosed with customized fertilizer advisory."
        }

        if (organicAmendments.isEmpty()) {
            organicAmendments.add(when (code) {
                "hi" -> "प्रति वर्ष 2-3 टन सड़ी गोबर खाद (FYM) खेत में अवश्य मिलाएं।"
                "mr" -> "दरवर्षी २-३ टन चांगले कुजलेले शेणखत जमिनीत मिसळा."
                "bn" -> "প্রতি বছর ২-৩ টন পচা গোবর সার মাটিতে মেশান।"
                "te" -> "ప్రతి సంవత్సరం ఎకరాకు 2-3 టన్నుల పశువుల ఎరువు వేయండి."
                "ta" -> "வருடத்திற்கு 2-3 டன் தொழுவுரம் இடவும்."
                else -> "Apply standard enriched Farm Yard Manure (FYM) @ 2-3 tonnes/acre annually."
            })
        }
        if (precautions.isEmpty()) {
            precautions.add(when (code) {
                "hi" -> "यूरिया को डीएपी या बिना बुझे चूने के साथ मिलाकर न रखें ताकि गैस बनकर उड़ने से नुकसान न हो।"
                "mr" -> "युरिया आणि डीएपी एकत्र साठवून ठेवू नका."
                "bn" -> "ইউরিয়া ও ডিএপি সার একসাথে মিশিয়ে সংরক্ষণ করবেন না।"
                "te" -> "యూరియా మరియు డీఏపీని కలిపి నిల్వ చేయవద్దు."
                "ta" -> "யூரியா மற்றும் டிஏபி உரங்களை ஒன்றாக கலந்து வைக்கக் கூடாது."
                else -> "Do not mix Urea directly with DAP during storage to prevent ammonia volatilization."
            })
        }

        return SoilAnalysisResult(
            summary = summary,
            soilHealthIndex = finalHealth,
            deficiencies = deficiencies.ifEmpty {
                listOf(when (code) {
                    "hi" -> "कोई गंभीर पोषक तत्व की कमी नहीं पाई गई। मिट्टी उत्तम उर्वरता में है।"
                    "mr" -> "जमिनीत कोणतीही गंभीर कमतरता नाही. जमीन सुपीक आहे."
                    "bn" -> "কোনো গুরুতর ঘাটতি নেই। মাটি উপযুক্ত উর্বর।"
                    "te" -> "ఎలాంటి తీవ్రమైన లోపాలు లేవు. నేల సారవంతంగా ఉంది."
                    "ta" -> "குறிப்பிடத்தக்க சத்து பற்றாக்குறை இல்லை. மண் வளமாக உள்ளது."
                    else -> "No critical nutrient deficiency detected. Soil is in optimal fertility condition."
                })
            },
            fertilizerRecommendations = recommendations,
            organicAmendments = organicAmendments,
            precautions = precautions,
            confidence = "High"
        )
    }

    /**
     * NPK Fertilizer Dosage & Bag Calculator with Full Multi-Language Scheduling
     */
    fun calculateFertilizerAcreage(
        cropName: String,
        acreage: Float,
        unit: String = "Acres",
        soilType: String = "Alluvial Soil",
        langCode: String = "en"
    ): FertilizerAcreageResult {
        val code = langCode.lowercase()
        val acres = when (unit.lowercase()) {
            "hectares", "ha" -> acreage * 2.471f
            "bigha" -> acreage * 0.40f
            else -> acreage
        }.coerceAtLeast(0.1f)

        val (baseN, baseP, baseK) = when (cropName.lowercase()) {
            "wheat", "गेहूं", "गहू", "গম", "గోధుమలు", "கோதுமை", "ಗೋಧಿ", "ઘઉં", "ਕਣਕ", "ଗହମ" -> Triple(50f, 25f, 20f)
            "rice", "paddy", "धान", "भात", "ধান", "వరి", "நெல்", "ಭತ್ತ", "ડાંગર", "ਝੋਨਾ", "ଧାନ" -> Triple(48f, 24f, 20f)
            "cotton", "कपास", "कापूस", "তুলা", "ప్రత్తి", "பருத்தி", "ಹತ್ತಿ", "કપાસ", "ਨਰਮਾ", "କପା" -> Triple(60f, 30f, 30f)
            "sugarcane", "गन्ना", "ऊस", "আখ", "చెరకు", "கரும்பு", "ಕಬ್ಬು", "શેરડી", "ਕਮਾਦ", "ଆଖୁ" -> Triple(100f, 40f, 50f)
            "maize", "corn", "मक्का", "मका", "ভুট্টা", "మొక్కజొన్న", "மக்காச்சோளம்", "ಮೆಕ್ಕೆಜೋಳ", "મકાઈ", "ਮੱਕੀ", "ମକା" -> Triple(48f, 24f, 20f)
            "potato", "आलू", "बटाटा", "আলু", "బంగాళాదుంప", "உருளைக்கிழங்கு", "ಆಲೂಗಡ್ಡೆ", "બટાકા", "ਆਲੂ", "ଆଳୁ" -> Triple(60f, 40f, 50f)
            "tomato", "टमाटर", "टोमॅटो", "টমেটো", "టమోటా", "தக்காளி", "ಟೊಮ್ಯಾಟೊ", "ટામેટા", "ਟਮਾਟਰ", "ଟମାଟୋ" -> Triple(55f, 35f, 40f)
            "mustard", "sarson", "सरसों", "मोहरी", "সরিষা", "ఆవాలు", "கடுகு", "ಸಾಸಿವೆ", "રાઈ", "ਸਰ੍ਹੋਂ", "ସୋରିଷ" -> Triple(32f, 16f, 15f)
            "soyabean", "सोयाबीन", "সয়াবিন", "సోయాబీన్", "சோயாபீன்", "ಸೋಯಾಬೀನ್", "સોયાબીન", "ਸੋਇਆਬੀਨ", "ସୋୟାବିନ" -> Triple(12f, 24f, 16f)
            "gram", "chana", "चना", "हरभरा", "ছোলা", "శనగలు", "கொண்டைக்கடலை", "ಕಡಲೆ", "ચણા", "ਛੋਲੇ", "ବୁଟ" -> Triple(10f, 20f, 10f)
            else -> Triple(45f, 25f, 20f)
        }

        val soilFactor = when (soilType.lowercase()) {
            "sandy soil", "sandy loam" -> 1.15f
            "black cotton soil" -> 0.95f
            "red soil" -> 1.05f
            else -> 1.0f
        }

        val totalReqN = baseN * acres * soilFactor
        val totalReqP = baseP * acres * soilFactor
        val totalReqK = baseK * acres * soilFactor

        val dapKg = (totalReqP / 0.46f)
        val nFromDap = dapKg * 0.18f
        val remainingN = (totalReqN - nFromDap).coerceAtLeast(0f)
        val ureaKg = (remainingN / 0.46f)
        val mopKg = (totalReqK / 0.60f)
        val sspKg = (totalReqP / 0.16f)

        val ureaBags = Math.round((ureaKg / 45f) * 10f) / 10f
        val dapBags = Math.round((dapKg / 50f) * 10f) / 10f
        val mopBags = Math.round((mopKg / 50f) * 10f) / 10f
        val sspBags = Math.round((sspKg / 50f) * 10f) / 10f

        val transCrop = SmartAgriToolsTranslations.getCropName(cropName, code)

        val basalSchedule = when (code) {
            "hi" -> "बुवाई / रोपाई के समय (बेसल डोज): 100% डीएपी (${dapKg.toInt()} किग्रा / ${dapBags} बोरी) + 100% पोटाश (${mopKg.toInt()} किग्रा / ${mopBags} बोरी) + 33% यूरिया (${(ureaKg * 0.33f).toInt()} किग्रा) डालें।"
            "mr" -> "पेरणीवेळी (बेसल डोस): १००% डीएपी (${dapKg.toInt()} किलो / ${dapBags} पोती) + १००% पोटॅश (${mopKg.toInt()} किलो / ${mopBags} पोती) + ३३% युरिया (${(ureaKg * 0.33f).toInt()} किलो) द्या."
            "bn" -> "রোপণের সময় (বেসাল ডোজ): ১০০% ডিএপি (${dapKg.toInt()} কেজি / ${dapBags} বস্তা) + ১০০% পটাশ (${mopKg.toInt()} কেজি / ${mopBags} বস্তা) + ৩৩% ইউরিয়া দিন।"
            "te" -> "నాట్లు వేసే సమయంలో: 100% డీఏపీ (${dapKg.toInt()} కిలోలు / ${dapBags} బస్తాలు) + 100% పొటాష్ (${mopKg.toInt()} కిలోలు) + 33% యూరియా వేయండి."
            "ta" -> "அடியுரமாக: 100% டிஏபி (${dapKg.toInt()} கிலோ / ${dapBags} மூட்டை) + 100% பொட்டாஷ் (${mopKg.toInt()} கிலோ) + 33% யூரியா இடவும்."
            else -> "At Sowing / Field Preparation (Basal): Apply 100% of DAP (${dapKg.toInt()} kg / ${dapBags} bags) + 100% of MOP (${mopKg.toInt()} kg / ${mopBags} bags) + 33% of Urea (${(ureaKg * 0.33f).toInt()} kg)."
        }

        val firstTopDressingSchedule = when (code) {
            "hi" -> "20-25 दिन बाद (पहला पानी / कल्ले फूटने पर): 33% यूरिया (${(ureaKg * 0.33f).toInt()} किग्रा / ${(ureaBags * 0.33f).toInt()} बोरी) पर्याप्त नमी में छिड़कें।"
            "mr" -> "२०-२५ दिवसांनी (पहिले पाणी / फुटवे फुटताना): ३३% युरिया (${(ureaKg * 0.33f).toInt()} किलो) जमिनीत ओलावा असताना द्या."
            "bn" -> "২০-২৫ দিন পর (প্রথম সেচ / কুশি অবস্থায়): ৩৩% ইউরিয়া (${(ureaKg * 0.33f).toInt()} কেজি) জমিতে পর্যাপ্ত আর্দ্রতায় দিন।"
            "te" -> "20-25 రోజుల తర్వాత (మొదటి తడి / పిలకల దశ): 33% యూరియా (${(ureaKg * 0.33f).toInt()} కిలోలు) వేయండి."
            "ta" -> "20-25 நாட்களில் (முதல் பாசனம் / தூர் கட்டும் பருவம்): 33% யூரியா (${(ureaKg * 0.33f).toInt()} கிலோ) இடவும்."
            else -> "At 20-25 Days (1st Irrigation / Tillering): Apply 33% of Urea (${(ureaKg * 0.33f).toInt()} kg / ${(ureaBags * 0.33f).toInt()} bags) with optimal soil moisture."
        }

        val secondTopDressingSchedule = when (code) {
            "hi" -> "40-45 दिन बाद (फूल / बाली आने से पहले): शेष 34% यूरिया (${(ureaKg * 0.34f).toInt()} किग्रा / ${(ureaBags * 0.34f).toInt()} बोरी) का छिड़काव करें।"
            "mr" -> "४०-४५ दिवसांनी (पोटरी / फुलोरा अवस्था): उर्वरित ३४% युरिया (${(ureaKg * 0.34f).toInt()} किलो) द्या."
            "bn" -> "৪০-৪৫ দিন পর (থোড় আসার আগে): অবশিষ্ট ৩৪% ইউরিয়া (${(ureaKg * 0.34f).toInt()} কেজি) জমিতে ছিটিয়ে দিন।"
            "te" -> "40-45 రోజుల తర్వాత (పూతకు ముందు): మిగిలిన 34% యూరియా (${(ureaKg * 0.34f).toInt()} కిలోలు) వేయండి."
            "ta" -> "40-45 நாட்களில் (பூக்கும் முன்): மீதமுள்ள 34% யூரியா (${(ureaKg * 0.34f).toInt()} கிலோ) இடவும்."
            else -> "At 40-45 Days (Flowering / Booting stage): Broadcast remaining 34% of Urea (${(ureaKg * 0.34f).toInt()} kg) before flowering."
        }

        val micronutrientTip = when (code) {
            "hi" -> "⚡ सूक्ष्म पोषक तत्व सुझाव: $transCrop की अच्छी पैदावार के लिए बुवाई पर जिंक सल्फेट 21% @ 10 किग्रा/एकड़ + सल्फर 90% @ 3 किग्रा/एकड़ मिलाएं।"
            "mr" -> "⚡ सूक्ष्म अन्नद्रव्ये सल्ला: $transCrop च्या चांगल्या उत्पादनासाठी पेरणीवेळी झिंक सल्फेट १० किलो/एकर व सल्फर ३ किलो/एकर वापरा."
            "bn" -> "⚡ অনুখাদ্য পরামর্শ: $transCrop ফসলের ভালো ফলনের জন্য জিংক সালফেট ১০ কেজি/একর + সালফার ৩ কেজি/একর প্রয়োগ করুন।"
            "te" -> "⚡ సూక్ష్మ పోషకాల చిట్కా: $transCrop పంటలో అధిక దిగుబడికి జింక్ సల్ఫేట్ 10 కిలోలు/ఎకరా + సల్ఫర్ 3 కిలోలు వేయండి."
            "ta" -> "⚡ நுண்ணூட்டச்சத்து ஆலோசனை: $transCrop பயிரில் அதிக விளைச்சலுக்கு ஜிங்க் சல்பேட் 10 கிலோ/ஏக்கர் + சல்பர் 3 கிலோ இடவும்."
            else -> "⚡ Micronutrient Booster: For $cropName, add Zinc Sulphate 21% @ 10 kg/acre + Sulphur 90% @ 3 kg/acre during basal application for enhanced yield."
        }

        return FertilizerAcreageResult(
            cropName = cropName,
            acreage = acreage,
            unit = unit,
            soilType = soilType,
            ureaKg = ureaKg,
            ureaBags45kg = ureaBags,
            dapKg = dapKg,
            dapBags50kg = dapBags,
            mopKg = mopKg,
            mopBags50kg = mopBags,
            sspAlternativeKg = sspKg,
            sspAlternativeBags50kg = sspBags,
            basalSchedule = basalSchedule,
            firstTopDressingSchedule = firstTopDressingSchedule,
            secondTopDressingSchedule = secondTopDressingSchedule,
            micronutrientTip = micronutrientTip
        )
    }
}
