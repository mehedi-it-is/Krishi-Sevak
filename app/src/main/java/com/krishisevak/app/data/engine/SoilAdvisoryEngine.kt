package com.krishisevak.app.data.engine

typealias SoilAnalysisInput = SoilAdvisoryInput
typealias SoilAnalysisResult = SoilAdvisoryResult


data class SoilAdvisoryInput(
    val soilType: String = "Alluvial Soil",
    val ph: Float = 6.8f,
    val nitrogenPpm: Float = 180f,
    val phosphorusPpm: Float = 22f,
    val potassiumPpm: Float = 140f
)

data class SoilAdvisoryResult(
    val healthScore: Int, // 0 to 100
    val summary: String,
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

    fun analyzeSoil(input: SoilAdvisoryInput, langCode: String = "en"): SoilAdvisoryResult {
        val code = langCode.lowercase()
        var healthScore = 88
        val deficiencies = mutableListOf<String>()
        val recommendations = mutableListOf<String>()
        val organicAmendments = mutableListOf<String>()
        val precautions = mutableListOf<String>()

        // 1. pH Evaluation
        when {
            input.ph < 6.0f -> {
                healthScore -= 15
                when (code) {
                    "hi" -> {
                        deficiencies.add("अम्लीय मिट्टी (pH ${input.ph}): उच्च अम्लता फास्फोरस और कैल्शियम के अवशोषण को बाधित करती है।")
                        recommendations.add("मिट्टी की अम्लता उदासीन करने के लिए 250-400 किग्रा/एकड़ कृषि चूना (कैल्शियम कार्बोनेट) डालें।")
                        organicAmendments.add("सड़ी हुई गोबर की खाद (FYM) @ 3 टन/एकड़ या लकड़ी की राख का प्रयोग करें।")
                    }
                    "mr" -> {
                        deficiencies.add("आम्लधर्मी जमीन (pH ${input.ph}): स्फुरद व कॅल्शियम अन्नद्रव्यांची उपलब्धता कमी होते.")
                        recommendations.add("जमिनीचा सामू सुधारण्यासाठी २५०-४०० किलो/एकर कृषी चुना मिसळावा.")
                        organicAmendments.add("३ टन/एकर चांगले कुजलेले शेणखत किंवा लाकडी राख वापरा.")
                    }
                    "bn" -> {
                        deficiencies.add("অম্লীয় মাটি (pH ${input.ph}): ফসফরাস এবং ক্যালসিয়াম শোষণে বাধা সৃষ্টি হয়।")
                        recommendations.add("মাটির অম্লতা কমাতে ২৫০-৪০০ কেজি/একর কৃষি চুন প্রয়োগ করুন।")
                        organicAmendments.add("৩ টন/একর ভালো পচা গোবর সার বা কাঠের ছাই ব্যবহার করুন।")
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
                    "kn" -> {
                        deficiencies.add("ಆಮ್ಲೀಯ ಮಣ್ಣು (pH ${input.ph}): ರಂಜಕ ಮತ್ತು ಕ್ಯಾಲ್ಸಿಯಂ ಹೀರಿಕೊಳ್ಳುವಿಕೆ ಕಡಿಮೆಯಾಗುತ್ತದೆ.")
                        recommendations.add("ಮಣ್ಣಿನ ಆಮ್ಲೀಯತೆ ಸರಿಪಡಿಸಲು ಎಕರೆಗೆ 250-400 ಕೆಜಿ ಕೃಷಿ ಸುಣ್ಣ ಹಾಕಿ.")
                        organicAmendments.add("ಎಕರೆಗೆ 3 ಟನ್ ಚೆನ್ನಾಗಿ ಕಳಿತ ಕೊಟ್ಟಿಗೆ ಗೊಬ್ಬರ ಬಳಸಿ.")
                    }
                    "gu" -> {
                        deficiencies.add("એસિડિક જમીન (pH ${input.ph}): ફોસ્ફરસ અને કેલ્શિયમનું શોષણ ઘટે છે.")
                        recommendations.add("જમીનનો એસિડિક ગુણ સુધારવા એકરે 250-400 કિલો કૃષિ ચૂનો ઉમેરો.")
                        organicAmendments.add("એકરે 3 ટન સારું દેશી છાણીયું ખાતર આપો.")
                    }
                    "pa" -> {
                        deficiencies.add("ਤੇਜ਼ਾਬੀ ਮਿੱਟੀ (pH ${input.ph}): ਫਾਸਫੋਰਸ ਅਤੇ ਕੈਲਸ਼ੀਅਮ ਦੀ ਪ੍ਰਾਪਤੀ ਘਟਦੀ ਹੈ।")
                        recommendations.add("ਜ਼ਮੀਨ ਦਾ ਤੇਜ਼ਾਬੀਪਨ ਘਟਾਉਣ ਲਈ 250-400 ਕਿਲੋ/ਏਕੜ ਖੇਤੀ ਚੂਨਾ ਪਾਓ।")
                        organicAmendments.add("3 ਟਨ/ਏਕੜ ਵਧੀਆ ਗਲੀ-ਸੜੀ ਰੂੜੀ ਖਾਦ ਪਾਓ।")
                    }
                    "ml" -> {
                        deficiencies.add("അമ്ലഗുണമുള്ള മണ്ണ് (pH ${input.ph}): ഫോസ്ഫറസ്, കാൽസ്യം ആഗിരണം കുറയുന്നു.")
                        recommendations.add("മണ്ണിലെ അമ്ലത കുറയ്ക്കാൻ ഏക്കറിന് 250-400 കിലോഗ്രാം കുമ്മായം ചേർക്കുക.")
                        organicAmendments.add("ഏക്കറിന് 3 ടൺ ഉണങ്ങിപ്പൊടിഞ്ഞ കാലിവളം ചേർക്കുക.")
                    }
                    "or" -> {
                        deficiencies.add("ଅମ୍ଳୀୟ ମାଟି (pH ${input.ph}): ଫସଫରସ ଓ କ୍ୟାଲସିୟମ ଶୋଷଣ ବାଧାପ୍ରାପ୍ତ ହୁଏ।")
                        recommendations.add("ମାଟିର ଅମ୍ଳତା ଦୂର କରିବା ପାଇଁ ଏକର ପିଛା 250-400 କିଲୋ ଚୂନ ପ୍ରୟୋଗ କରନ୍ତୁ।")
                        organicAmendments.add("ଏକର ପିଛା 3 ଟନ ସଢ଼ା ଗୋବର ଖତ ପ୍ରୟୋଗ କରନ୍ତୁ।")
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
                    "kn" -> {
                        deficiencies.add("ಕ್ಷಾರೀಯ ಮಣ್ಣು (pH ${input.ph}): ಸತು ಮತ್ತು ಕಬ್ಬಿಣದ ಕೊರತೆ ಉಂಟಾಗುತ್ತದೆ.")
                        recommendations.add("ಎಕರೆಗೆ 300 ಕೆಜಿ ಕೃಷಿ ಜಿಪ್ಸಮ್ ಹಾಕಿ ನೀರು ಹಾಯಿಸಿ.")
                        organicAmendments.add("ಹಸಿರೆಲೆ ಗೊಬ್ಬರವನ್ನು (ಡೈಂಚಾ/ಸಣಬು) ಭೂಮಿಗೆ ಸೇರಿಸಿ.")
                    }
                    "gu" -> {
                        deficiencies.add("ક્ષારયુક્ત જમીન (pH ${input.ph}): ઝીંક અને લોહ તત્વની ઉણપ સર્જાય છે.")
                        recommendations.add("એકરે 300 કિલો જીપ્સમ આપીને ઊંડું પિયત આપો.")
                        organicAmendments.add("ઇકકડ અથવા શણનું લીલું ખાતર જમીનમાં દાટો.")
                    }
                    "pa" -> {
                        deficiencies.add("ਖਾਰੀ / ਕੱਲਰ ਵਾਲੀ ਜ਼ਮੀਨ (pH ${input.ph}): ਜਿੰਕ ਅਤੇ ਲੋਹੇ ਦੀ ਘਾਟ ਹੁੰਦੀ ਹੈ।")
                        recommendations.add("300 ਕਿਲੋ/ਏਕੜ ਜਿਪਸਮ ਪਾ ਕੇ ਭਾਰੀ ਸਿੰਚਾਈ ਕਰੋ।")
                        organicAmendments.add("ਜੰਤਰ ਜਾਂ ਸਣ ਦੀ ਹਰੀ ਖਾਦ ਖੇਤ ਵਿੱਚ ਵਾਹੋ।")
                    }
                    "ml" -> {
                        deficiencies.add("ക്ഷാരഗുണമുള്ള മണ്ണ് (pH ${input.ph}): സിങ്ക്, ഇരുമ്പ് എന്നിവയുടെ കുറവ് അനുഭവപ്പെടുന്നു.")
                        recommendations.add("ഏക്കറിന് 300 കിലോഗ്രാം ജിപ്സം ഇട്ട് നനയ്ക്കുക.")
                        organicAmendments.add("പച്ചിലവളങ്ങൾ മണ്ണിൽ ഉഴുതുചേർക്കുക.")
                    }
                    "or" -> {
                        deficiencies.add("କ୍ଷାରୀୟ ମାଟି (pH ${input.ph}): ଜିଙ୍କ ଓ ଲୌହ ଅଂଶର ଅଭାବ ଦେଖାଦିଏ।")
                        recommendations.add("ଏକର ପିଛା 300 କିଲୋ ଜିପସମ ପ୍ରୟୋଗ କରନ୍ତୁ।")
                        organicAmendments.add("ଧନଞ୍ଚା କିମ୍ବା ଛଣପଟ ସବୁଜ ଖତ ଜମିରେ ମିଶାନ୍ତୁ।")
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
                    "ml" -> recommendations.add("മണ്ണിന്റെ pH നില അനുയോജ്യമാണ് (${input.ph}). വിളവെടുപ്പിന് ഉത്തമം.")
                    "or" -> recommendations.add("ମାଟିର pH ସନ୍ତୁଳିତ ଅଛି (${input.ph})। ଫସଲ ଚାଷ ପାଇଁ ଉତ୍ତମ।")
                    else -> recommendations.add("Soil pH is optimal (${input.ph}). Suitable for most cereal, pulse, and horticultural crops.")
                }
            }
        }

        // 2. Nitrogen Evaluation
        when {
            input.nitrogenPpm < 120f -> {
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
                    "kn" -> {
                        deficiencies.add("ಸಾರಜನಕದ ಕೊರತೆ (${input.nitrogenPpm.toInt()} ppm): ಗಿಡಗಳ ಬೆಳವಣಿಗೆ ಕುಂಠಿತ ಮತ್ತು ಎಲೆಗಳು ಹಳದಿಯಾಗುವುದು.")
                        recommendations.add("ಯೂರಿಯಾ ಗೊಬ್ಬರವನ್ನು 3 ಕಂತುಗಳಲ್ಲಿ ನೀಡಿ.")
                        organicAmendments.add("ಎಕರೆಗೆ 2 ಟನ್ ಎರೆಹುಳು ಗೊಬ್ಬರ ಅಥವಾ ಬೇವಿನ ಹಿಂಡಿ ಬಳಸಿ.")
                    }
                    "gu" -> {
                        deficiencies.add("નાઇટ્રોજનની ઉણપ (${input.nitrogenPpm.toInt()} ppm): છોડનો વિકાસ અટકવો અને પાન પીળા પડવા.")
                        recommendations.add("યુરિયા ખાતર 3 હપ્તામાં આપો.")
                        organicAmendments.add("એકરે 2 ટન અળસિયાનું ખાતર અથવા લીંબોળીનો ખોળ આપો.")
                    }
                    "pa" -> {
                        deficiencies.add("ਨਾਈਟ੍ਰੋਜਨ ਦੀ ਭਾਰੀ ਘਾਟ (${input.nitrogenPpm.toInt()} ppm): ਬੂਟਿਆਂ ਦਾ ਵਾਧਾ ਰੁਕਣਾ।")
                        recommendations.add("ਯੂਰੀਆ ਖਾਦ 3 ਕਿਸ਼ਤਾਂ ਵਿੱਚ ਪਾਓ।")
                        organicAmendments.add("2 ਟਨ/ਏਕੜ ਵਰਮੀਕੰਪੋਸਟ ਜਾਂ ਨਿੰਮ ਦੀ ਖਲ ਪਾਓ।")
                    }
                    "ml" -> {
                        deficiencies.add("നൈട്രജന്റെ കുറവ് (${input.nitrogenPpm.toInt()} ppm): ഇലകൾ മഞ്ഞളിക്കുകയും വളർച്ച മുരടിക്കുകയും ചെയ്യുന്നു.")
                        recommendations.add("യൂറിയ 3 തവണകളായി നൽകുക.")
                        organicAmendments.add("ഏക്കറിന് 2 ടൺ മണ്ണിര വളം പ്രയോഗിക്കുക.")
                    }
                    "or" -> {
                        deficiencies.add("ଯବକ୍ଷାରଜାନର ଅଭାବ (${input.nitrogenPpm.toInt()} ppm): ଗଛର ବୃଦ୍ଧି ବାଧାପ୍ରାପ୍ତ ହେବା ଓ ପତ୍ର ହଳଦିଆ ପଡ଼ିବା।")
                        recommendations.add("ୟୁରିଆ ସାରକୁ 3ଟି କିସ୍ତିରେ ପ୍ରୟୋଗ କରନ୍ତୁ।")
                        organicAmendments.add("ଏକର ପିଛା 2 ଟନ ଜିଆ ଖତ ବ୍ୟବହାର କରନ୍ତୁ।")
                    }
                    else -> {
                        deficiencies.add("Severe Nitrogen Deficiency (${input.nitrogenPpm.toInt()} ppm): Stunted crop growth and leaf yellowing.")
                        recommendations.add("Apply Urea (46% N) in 3 split doses: 50% basal, 25% at tillering (21 days), 25% before panicle emergence.")
                        organicAmendments.add("Apply Vermicompost @ 2 tonnes/acre or Neem Cake @ 100 kg/acre.")
                    }
                }
            }
            else -> {
                when (code) {
                    "hi" -> recommendations.add("मिट्टी में उपलब्ध नाइट्रोजन स्तर संतोषजनक है (${input.nitrogenPpm.toInt()} ppm)।")
                    "mr" -> recommendations.add("जमिनीतील नायट्रोजनचे प्रमाण समाधानकारक आहे (${input.nitrogenPpm.toInt()} ppm).")
                    "bn" -> recommendations.add("মাটিতে নাইট্রোজেনের মাত্রা সন্তোষজনক (${input.nitrogenPpm.toInt()} ppm)।")
                    "te" -> recommendations.add("నేలలో నత్రజని పరిమాణం సరిపడా ఉంది (${input.nitrogenPpm.toInt()} ppm).")
                    "ta" -> recommendations.add("மண்ணில் நைட்ரஜன் அளவு போதுமானதாக உள்ளது (${input.nitrogenPpm.toInt()} ppm).")
                    "kn" -> recommendations.add("ಮಣ್ಣಿನಲ್ಲಿ ಸಾರಜನಕದ ಪ್ರಮಾಣ ಸಮರ್ಪಕವಾಗಿದೆ (${input.nitrogenPpm.toInt()} ppm).")
                    "gu" -> recommendations.add("જમીનમાં નાઇટ્રોજનનું પ્રમાણ સંતોષકારક છે (${input.nitrogenPpm.toInt()} ppm).")
                    "pa" -> recommendations.add("ਮਿੱਟੀ ਵਿੱਚ ਨਾਈਟ੍ਰੋਜਨ ਦਾ ਪੱਧਰ ਸੰਤੋਸ਼ਜਨਕ ਹੈ (${input.nitrogenPpm.toInt()} ppm)।")
                    "ml" -> recommendations.add("മണ്ണിൽ നൈട്രജൻ ആവശ്യത്തിന് ഉണ്ട് (${input.nitrogenPpm.toInt()} ppm).")
                    "or" -> recommendations.add("ମାଟିରେ ଯବକ୍ଷାରଜାନ ମାତ୍ରା ସନ୍ତୋଷଜନକ ଅଛି (${input.nitrogenPpm.toInt()} ppm)।")
                    else -> recommendations.add("Available Nitrogen status is satisfactory (${input.nitrogenPpm.toInt()} ppm).")
                }
            }
        }

        // Summary Line
        val summary = when (code) {
            "hi" -> "मृदा स्वास्थ्य स्कोर: $healthScore/100। संतुलित उर्वरक व जैविक खाद प्रबंधन से उत्पादकता में 25% तक वृद्धि संभव है।"
            "mr" -> "जमीन आरोग्य निर्देशांक: $healthScore/१००. योग्य खत व्यवस्थापनाने उत्पादनात २५% पर्यंत वाढ शक्य आहे."
            "bn" -> "মাটির স্বাস্থ্য স্কোর: $healthScore/১০০। সুষম সার প্রয়োগে ফলন ২৫% পর্যন্ত বৃদ্ধি পাবে।"
            "te" -> "భూసార స్కోరు: $healthScore/100. సరైన ఎరువుల యాజమాన్యంతో 25% అధిక దిగుబడి సాధ్యం."
            "ta" -> "மண் வள மதிப்பீடு: $healthScore/100. சீரான உர நிர்வாகம் மூலம் 25% வரை கூடுதல் மகசூல் பெறலாம்."
            "kn" -> "ಮಣ್ಣಿನ ಆರೋಗ್ಯ ಸ್ಕೋರ್: $healthScore/100. ಸಮತೋಲಿತ ಗೊಬ್ಬರ ಬಳಕೆಯಿಂದ 25% ಹೆಚ್ಚಿನ ಇಳುವರಿ ಪಡೆಯಬಹುದು."
            "gu" -> "જમીન ફળદ્રુપતા સ્કોર: $healthScore/100. સંતુલિત ખાતર વ્યવસ્થાપનથી 25% વધુ ઉત્પાદન શક્ય છે."
            "pa" -> "ਜ਼ਮੀਨ ਦੀ ਸਿਹਤ ਸਕੋਰ: $healthScore/100। ਸੰਤੁਲਿਤ ਖਾਦਾਂ ਨਾਲ 25% ਵੱਧ ਝਾੜ ਮਿਲ ਸਕਦਾ ਹੈ।"
            "ml" -> "മണ്ണിന്റെ ആരോഗ്യ സ്കോർ: $healthScore/100. ശരിയായ വളപ്രയോഗത്തിലൂടെ 25% അധിക വിളവ് നേടാം."
            "or" -> "ମାଟି ସ୍ୱାସ୍ଥ୍ୟ ସ୍କୋର: $healthScore/100। ସନ୍ତୁଳିତ ସାର ପ୍ରୟୋଗ ଦ୍ୱାରା 25% ଅଧିକ ଅମଳ ସମ୍ଭବ।"
            else -> "Soil Health Index: $healthScore/100. Applying balanced N-P-K nutrition can boost crop productivity by up to 25%."
        }

        val precautionsList = when (code) {
            "hi" -> listOf(
                "खड़ी फसल में तेज धूप के समय यूरिया का बुरकाव न करें।",
                "उर्वरक डालने के तुरंत बाद या साथ में हल्की सिंचाई अवश्य करें।",
                "डीएपी और जिंक सल्फेट को कभी एक साथ मिलाकर न डालें।"
            )
            "mr" -> listOf(
                "कडक उन्हामध्ये उभ्या पिकात युरिया टाकू नका.",
                "खते दिल्यानंतर शेतात हलके पाणी नक्की द्यावे.",
                "डीएपी आणि झिंक सल्फेट कधीही एकत्र मिसळून टाकू नका."
            )
            "bn" -> listOf(
                "তীব্র রোদে ফসলে ইউরিয়া ছিটাবেন না।",
                "সার প্রয়োগের সাথে সাথে হালকা সেচ দিন।",
                "ডিএপি এবং জিংক সালফেট কখনো একসাথে মেশাবেন না।"
            )
            "te" -> listOf(
                "ఎండ తీవ్రత ఎక్కువగా ఉన్నప్పుడు యూరియా చల్లవద్దు.",
                "ఎరువులు వేసిన వెంటనే తేలికపాటి తడి ఇవ్వండి.",
                "డీఏపీ మరియు జింక్ సల్ఫేట్‌లను కలిపి వేయవద్దు."
            )
            "ta" -> listOf(
                "கடும் வெயிலில் யூரியாவை பயிரில் தூவ வேண்டாம்.",
                "உரமிட்டவுடன் லேசான பாசனம் செய்யவும்.",
                "டிஏபி மற்றும் ஜிங்க் சல்பேட்டை ஒன்றாக கலக்கக் கூடாது."
            )
            "kn" -> listOf(
                "ಕಡು ಬಿಸಿಲಿನಲ್ಲಿ ಯೂರಿಯಾ ಗೊಬ್ಬರ ಚೆಲ್ಲಬೇಡಿ.",
                "ಗೊಬ್ಬರ ಹಾಕಿದ ತಕ್ಷಣ ಲಘು ನೀರಾವರಿ ನೀಡಿ.",
                "ಡಿಎಪಿ ಮತ್ತು ಜಿಂಕ್ ಸಲ್ಫೇಟ್ ಒಟ್ಟಿಗೆ ಬೆರೆಸಿ ಹಾಕಬೇಡಿ."
            )
            "gu" -> listOf(
                "બપોરના તડકામાં યુરિયાનો છંટકાવ ન કરવો.",
                "ખાતર આપ્યા પછી તરત હળવું પિયત આપવું.",
                "ડીએપી અને ઝીંક સલ્ફેટને ક્યારેય ભેગા ન આપવા."
            )
            "pa" -> listOf(
                "ਤੇਜ਼ ਧੁੱਪ ਵਿੱਚ ਯੂਰੀਆ ਦਾ ਛਿੜਕਾਅ ਨਾ ਕਰੋ।",
                "ਖਾਦ ਪਾਉਣ ਤੋਂ ਬਾਅਦ ਹਲਕਾ ਪਾਣੀ ਜ਼ਰੂਰ ਲਗਾਓ।",
                "ਡੀਏਪੀ ਅਤੇ ਜਿੰਕ ਸਲਫੇਟ ਨੂੰ ਕਦੇ ਵੀ ਇਕੱਠੇ ਨਾ ਮਿਲਾਓ।"
            )
            "ml" -> listOf(
                "കടുത്ത വെയിലുള്ളപ്പോൾ യൂറിയ വിതറരുത്.",
                "വളപ്രയോഗത്തിന് ശേഷം ഉടൻ നനയ്ക്കുക.",
                "ഡിഎപിയും സിങ്ക് സൾഫേറ്റും ഒരുമിച്ച് ചേർക്കരുത്."
            )
            "or" -> listOf(
                "ଖରା ବେଳେ ୟୁରିଆ ପ୍ରୟୋଗ କରନ୍ତୁ ନାହିଁ।",
                "ସାର ପ୍ରୟୋଗ ପରେ ହାଲୁକା ଜଳସେଚନ କରନ୍ତୁ।",
                "ଡିଏପି ଏବଂ ଜିଙ୍କ ସଲଫେଟ ଏକାଠି ମିଶାନ୍ତୁ ନାହିଁ।"
            )
            else -> listOf(
                "Never broadcast Urea under intense direct sunlight to prevent volatile ammonia loss.",
                "Provide light irrigation immediately following granular fertilizer application.",
                "Never mix DAP and Zinc Sulphate together — it creates insoluble Zinc Phosphate precipitate."
            )
        }

        return SoilAdvisoryResult(
            healthScore = healthScore.coerceIn(20, 100),
            summary = summary,
            deficiencies = deficiencies,
            fertilizerRecommendations = recommendations,
            organicAmendments = organicAmendments,
            precautions = precautionsList
        )
    }

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
            "bigha", "guntha" -> acreage * 0.40f
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
            "black cotton soil", "black soil" -> 0.95f
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
            "kn" -> "ಬಿತ್ತನೆ ಸಮಯದಲ್ಲಿ: 100% ಡಿಎಪಿ (${dapKg.toInt()} ಕೆಜಿ / ${dapBags} ಚೀಲ) + 100% ಪೊಟ್ಯಾಷ್ (${mopKg.toInt()} ಕೆಜಿ) + 33% ಯೂರಿಯಾ ಹಾಕಿ."
            "gu" -> "વાવણી સમયે (પાયામાં): 100% ડીએપી (${dapKg.toInt()} કિલો / ${dapBags} થેલી) + 100% પોટાશ (${mopKg.toInt()} કિલો) + 33% યુરિયા આપો."
            "pa" -> "ਬਿਜਾਈ ਵੇਲੇ: 100% ਡੀਏਪੀ (${dapKg.toInt()} ਕਿਲੋ / ${dapBags} ਥੈਲੇ) + 100% ਪੋਟਾਸ਼ (${mopKg.toInt()} ਕਿਲੋ) + 33% ਯੂਰੀਆ ਪਾਓ।"
            "ml" -> "അടിവളമായി: 100% ഡിഎപി (${dapKg.toInt()} കിലോ / ${dapBags} ചാക്ക്) + 100% പൊട്ടാഷ് (${mopKg.toInt()} കിലോ) + 33% യൂറിയ ചേർക്കുക."
            "or" -> "ବୁଣିବା ସମୟରେ: 100% ଡିଏପି (${dapKg.toInt()} କିଲୋ / ${dapBags} ବସ୍ତା) + 100% ପଟାସ (${mopKg.toInt()} କିଲୋ) + 33% ୟୁରିଆ ପ୍ରୟୋଗ କରନ୍ତୁ।"
            else -> "At Sowing / Field Preparation (Basal): Apply 100% of DAP (${dapKg.toInt()} kg / ${dapBags} bags) + 100% of MOP (${mopKg.toInt()} kg / ${mopBags} bags) + 33% of Urea (${(ureaKg * 0.33f).toInt()} kg)."
        }

        val firstTopDressingSchedule = when (code) {
            "hi" -> "20-25 दिन बाद (पहला पानी / कल्ले फूटने पर): 33% यूरिया (${(ureaKg * 0.33f).toInt()} किग्रा / ${(ureaBags * 0.33f).toInt()} बोरी) पर्याप्त नमी में छिड़कें।"
            "mr" -> "२०-२५ दिवसांनी (पहिले पाणी / फुटवे फुटताना): ३३% युरिया (${(ureaKg * 0.33f).toInt()} किलो) जमिनीत ओलावा असताना द्या."
            "bn" -> "২০-২৫ দিন পর (প্রথম সেচ / কুশি অবস্থায়): ৩৩% ইউরিয়া (${(ureaKg * 0.33f).toInt()} কেজি) জমিতে পর্যাপ্ত আর্দ্রতায় দিন।"
            "te" -> "20-25 రోజుల తర్వాత (మొదటి తడి / పిలకల దశ): 33% యూరియా (${(ureaKg * 0.33f).toInt()} కిలోలు) వేయండి."
            "ta" -> "20-25 நாட்களில் (முதல் பாசனம் / தூர் கட்டும் பருவம்): 33% யூரியா (${(ureaKg * 0.33f).toInt()} கிலோ) இடவும்."
            "kn" -> "20-25 ದಿನಗಳ ನಂತರ (ಮೊದಲ ನೀರಾವರಿ / ಕವಲು ಒಡೆಯುವ ಹಂತ): 33% ಯೂರಿಯಾ (${(ureaKg * 0.33f).toInt()} ಕೆಜಿ) ಹಾಕಿ."
            "gu" -> "20-25 દિવસ પછી (પ્રથમ પિયત વખતે): 33% યુરિયા (${(ureaKg * 0.33f).toInt()} કિલો) આપો."
            "pa" -> "20-25 ਦਿਨਾਂ ਬਾਅਦ (ਪਹਿਲੇ ਪਾਣੀ ਵੇਲੇ): 33% ਯੂਰੀਆ (${(ureaKg * 0.33f).toInt()} ਕਿਲੋ) ਪਾਓ।"
            "ml" -> "20-25 ദിവസത്തിന് ശേഷം (ആദ്യ നന): 33% യൂറിയ (${(ureaKg * 0.33f).toInt()} കിലോ) നൽകുക."
            "or" -> "20-25 ଦିନ ପରେ (ପ୍ରଥମ ଜଳସେଚନ ବେଳେ): 33% ୟୁରିଆ (${(ureaKg * 0.33f).toInt()} କିଲୋ) ପ୍ରୟୋଗ କରନ୍ତୁ।"
            else -> "At 20-25 Days (1st Irrigation / Tillering): Apply 33% of Urea (${(ureaKg * 0.33f).toInt()} kg / ${(ureaBags * 0.33f).toInt()} bags) with optimal soil moisture."
        }

        val secondTopDressingSchedule = when (code) {
            "hi" -> "40-45 दिन बाद (फूल / बाली आने से पहले): शेष 34% यूरिया (${(ureaKg * 0.34f).toInt()} किग्रा / ${(ureaBags * 0.34f).toInt()} बोरी) का छिड़काव करें।"
            "mr" -> "४०-४५ दिवसांनी (पोटरी / फुलोरा अवस्था): उर्वरित ३४% युरिया (${(ureaKg * 0.34f).toInt()} किलो) द्या."
            "bn" -> "৪০-৪৫ দিন পর (থোড় আসার আগে): অবশিষ্ট ৩৪% ইউরিয়া (${(ureaKg * 0.34f).toInt()} কেজি) জমিতে ছিটিয়ে দিন।"
            "te" -> "40-45 రోజుల తర్వాత (పూతకు ముందు): మిగిలిన 34% యూరియా (${(ureaKg * 0.34f).toInt()} కిలోలు) వేయండి."
            "ta" -> "40-45 நாட்களில் (பூக்கும் முன்): மீதமுள்ள 34% யூரியா (${(ureaKg * 0.34f).toInt()} கிலோ) இடவும்."
            "kn" -> "40-45 ದಿನಗಳ ನಂತರ (ಹೂಬಿಡುವ ಮುನ್ನ): ಉಳಿದ 34% ಯೂರಿಯಾ (${(ureaKg * 0.34f).toInt()} ಕೆಜಿ) ಹಾಕಿ."
            "gu" -> "40-45 દિવસ પછી (ફૂલ આવતા પહેલા): બાકીનું 34% યુરિયા (${(ureaKg * 0.34f).toInt()} કિલો) આપો."
            "pa" -> "40-45 ਦਿਨਾਂ ਬਾਅਦ (ਫੁੱਲ ਪੈਣ ਤੋਂ ਪਹਿਲਾਂ): ਬਾਕੀ 34% ਯੂਰੀਆ (${(ureaKg * 0.34f).toInt()} ਕਿਲੋ) ਪਾਓ।"
            "ml" -> "40-45 ദിവസത്തിന് ശേഷം (പൂവിടുന്നതിന് മുൻപ്): ബാക്കി 34% യൂറിയ (${(ureaKg * 0.34f).toInt()} കിലോ) നൽകുക."
            "or" -> "40-45 ଦିନ ପରେ (ଫୁଲ ଆସିବା ପୂର୍ବରୁ): ବାକି 34% ୟୁରିଆ (${(ureaKg * 0.34f).toInt()} କିଲୋ) ପ୍ରୟୋଗ କରନ୍ତୁ।"
            else -> "At 40-45 Days (Flowering / Booting stage): Broadcast remaining 34% of Urea (${(ureaKg * 0.34f).toInt()} kg) before flowering."
        }

        val micronutrientTip = when (code) {
            "hi" -> "⚡ सूक्ष्म पोषक तत्व सुझाव: $transCrop की अच्छी पैदावार के लिए बुवाई पर जिंक सल्फेट 21% @ 10 किग्रा/एकड़ + सल्फर 90% @ 3 किग्रा/एकड़ मिलाएं।"
            "mr" -> "⚡ सूक्ष्म अन्नद्रव्ये सल्ला: $transCrop च्या चांगल्या उत्पादनासाठी पेरणीवेळी झिंक सल्फेट १० किलो/एकर व सल्फर ३ किलो/एकर वापरा."
            "bn" -> "⚡ অনুখাদ্য পরামর্শ: $transCrop ফসলের ভালো ফলনের জন্য জিংক সালফেট ১০ কেজি/একর + সালফার ৩ কেজি/একর প্রয়োগ করুন।"
            "te" -> "⚡ సూక్ష్మ పోషకాల చిట్కా: $transCrop పంటలో అధిక దిగుబడికి జింక్ సల్ఫేట్ 10 కిలోలు/ఎకరా + సల్ఫర్ 3 కిలోలు వేయండి."
            "ta" -> "⚡ நுண்ணூட்டச்சத்து ஆலோசனை: $transCrop பயிரில் அதிக விளைச்சலுக்கு ஜிங்க் சல்பேட் 10 கிலோ/ஏக்கர் + சல்பர் 3 கிலோ இடவும்."
            "kn" -> "⚡ ಸೂಕ್ಷ್ಮ ಪೋಷಕಾಂಶ ಸಲಹೆ: $transCrop ಬೆಳೆಯ ಉತ್ತಮ ಇಳುವರಿಗೆ ಜಿಂಕ್ ಸಲ್ಫೇಟ್ 10 ಕೆಜಿ/ಎಕರೆ + ಗಂಧಕ 3 ಕೆಜಿ/ಎಕರೆ ಬಳಸಿ."
            "gu" -> "⚡ સૂક્ષ્મ પોષકતત્ત્વ ટીપ: $transCrop ના વધુ ઉત્પાદન માટે ઝીંક સલ્ફેટ 10 કિલો/એકર + સલ્ફર 3 કિલો/એકર આપો."
            "pa" -> "⚡ ਸੂਖਮ ਤੱਤ ਸੁਝਾਅ: $transCrop ਦੇ ਵੱਧ ਝਾੜ ਲਈ ਜਿੰਕ ਸਲਫੇਟ 10 ਕਿਲੋ/ਏਕੜ + ਸਲਫਰ 3 ਕਿਲੋ/ਏਕੜ ਪਾਓ।"
            "ml" -> "⚡ സൂക്ഷ്മ മൂലക നിർദ്ദേശം: $transCrop വിളവിന് സിങ്ക് സൾഫേറ്റ് 10 കി/ഏക്കർ + സൾഫർ 3 കി/ഏക്കർ പ്രയോഗിക്കുക."
            "or" -> "⚡ ଅଣୁ ପୋଷକ ତତ୍ତ୍ୱ ପରାମର୍ଶ: $transCrop ଫସଲର ଉତ୍ତମ ଅମଳ ପାଇଁ ଜିଙ୍କ ସଲଫେଟ 10 କିଲୋ/ଏକର + ସଲଫର 3 କିଲୋ/ଏକର ପ୍ରୟୋଗ କରନ୍ତୁ।"
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
