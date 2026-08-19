package com.krishisevak.app.data.engine

import com.krishisevak.app.data.local.db.PlantScanEntity

data class CropDoctorInput(
    val temperature: Float = 28f,
    val humidity: Int = 65,
    val rainProbability: Int = 20,
    val conditionDescription: String = "Partly Cloudy",
    val pastScans: List<PlantScanEntity> = emptyList()
)

data class CropDoctorResult(
    val riskLevel: String, // "HIGH", "MEDIUM", "LOW"
    val riskScore: Int, // 0 to 100
    val weatherAlert: String?,
    val potentialRisks: List<String>,
    val recommendations: List<String>,
    val preventivePlan7Day: List<String>
)

object CropDoctorEngine {

    fun evaluate(input: CropDoctorInput, langCode: String = "en"): CropDoctorResult {
        val code = langCode.lowercase()
        var baseRisk = 20f
        val potentialRisks = mutableListOf<String>()
        val recommendations = mutableListOf<String>()
        val plan7Day = mutableListOf<String>()
        var weatherAlert: String? = null

        // 1. High Humidity (>75%) - Fungal Blight Hazard
        if (input.humidity >= 78) {
            baseRisk += 35f
            when (code) {
                "hi" -> {
                    potentialRisks.add("उच्च फफूंद रोग जोखिम: अगेती/पछेती झुलसा (Blight), चूर्णिल आसिता (Powdery Mildew) और पर्ण रतुआ (Leaf Rust)।")
                    recommendations.add("निवारक कवकनाशी का छिड़काव करें (मैंकोजेब 75% WP @ 2 ग्राम/लीटर या कॉपर ऑक्सीक्लोराइड @ 3 ग्राम/लीटर)।")
                    recommendations.add("पौधों के बीच उचित दूरी रखें ताकि हवा का संचार हो सके और पत्तियां जल्दी सूखें।")
                    weatherAlert = "⚠️ उच्च आर्द्रता (${input.humidity}%): फफूंद जीवाणुओं के तेजी से पनपने की अनुकूल परिस्थिति।"
                }
                "mr" -> {
                    potentialRisks.add("जास्त बुरशीजन्य रोगांचा धोका: करपा (Blight), भुरी (Powdery Mildew), तांबेरा (Leaf Rust) व केवडा.")
                    recommendations.add("प्रतिबंधक बुरशीनाशकाची फवारणी करा (मँकोझेब २ ग्रॅम/लिटर किंवा कॉपर ऑक्सिक्लोराईड ३ ग्रॅम/लिटर).")
                    recommendations.add("पिकात हवा खेळती राहण्यासाठी आवश्यक अंतर ठेवा.")
                    weatherAlert = "⚠️ जास्त दमट हवामान (${input.humidity}%): बुरशीची झपाट्याने वाढ होण्याचा धोका."
                }
                "bn" -> {
                    potentialRisks.add("মারাত্মক ছত্রাকজনিত রোগের ঝুঁকি: আগাম/নাবী ব্লাইট, পাউডারি মিলডিউ ও মরিচা রোগ।")
                    recommendations.add("প্রতিরোধক ছত্রাকনাশক স্প্রে করুন (ম্যানকোজেব ২ গ্রাম/লিটার বা কপার অক্সিক্লোরাইড ৩ গ্রাম/লিটার)।")
                    recommendations.add("বাতাস চলাচলের জন্য গাছের মাঝে পর্যাপ্ত দূরত্ব রাখুন।")
                    weatherAlert = "⚠️ অতিরিক্ত আর্দ্রতা (${input.humidity}%): ছত্রাকের দ্রুত বিস্তারের আশঙ্কা।"
                }
                "te" -> {
                    potentialRisks.add("శిలీంధ్ర వ్యాధుల తీవ్ర ముప్పు: ఆకుమచ్చ తెగులు, బూడిద తెగులు మరియు తుప్పు తెగులు.")
                    recommendations.add("నివారణకు మాంకోజెబ్ 2 గ్రా/లీటరు లేదా కాపర్ ఆక్సిక్లోరైడ్ 3 గ్రా/లీటరు పిచికారీ చేయండి.")
                    recommendations.add("గాలి ప్రసరణ బాగుండేలా మొక్కల మధ్య సరైన దూరం పాటించండి.")
                    weatherAlert = "⚠️ అధిక తేమ (${input.humidity}%): శిలీంధ్రాలు వేగంగా వృద్ధి చెందే ప్రమాదం."
                }
                "ta" -> {
                    potentialRisks.add("பூஞ்சான நோய் தாக்குதல் அபாயம்: இலைக்கருகல், சாம்பல் நோய் மற்றும் துரு நோய்.")
                    recommendations.add("முன்னெச்சரிக்கையாக மான்கோசெப் 2 கிராம்/லிட்டர் அல்லது காப்பர் ஆக்ஸிகுளோரைடு 3 கிராம்/லிட்டர் தெளிக்கவும்.")
                    recommendations.add("காற்றோட்டத்தை உறுதிப்படுத்த பயிர்களுக்கு இடையே போதிய இடைவெளி விடவும்.")
                    weatherAlert = "⚠️ அதிக ஈரப்பதம் (${input.humidity}%): பூஞ்சான வித்துக்கள் பரவும் அபாயம்."
                }
                "kn" -> {
                    potentialRisks.add("ಶಿಲೀಂಧ್ರ ರೋಗಗಳ ತೀವ್ರ ಅಪಾಯ: ಎಲೆ ಚುಕ್ಕೆ ರೋಗ, ಬೂದಿ ರೋಗ ಮತ್ತು ತುಕ್ಕು ರೋಗ.")
                    recommendations.add("ಮುನ್ನೆಚ್ಚರಿಕೆಯಾಗಿ ಮ್ಯಾಂಕೋಜೆಬ್ 2 ಗ್ರಾಂ/ಲೀಟರ್ ಅಥವಾ ಕಾಪರ್ ಆಕ್ಸಿಕ್ಲೋರೈಡ್ 3 ಗ್ರಾಂ/ಲೀಟರ್ ಸಿಂಪಡಿಸಿ.")
                    recommendations.add("ಗಾಳಿ ಬೆಳಕು ಚೆನ್ನಾಗಿ ಆಡುವಂತೆ ಸಾಲುಗಳ ನಡುವೆ ಅಂತರ ಕಾಪಾಡಿ.")
                    weatherAlert = "⚠️ ಅತಿ ಹೆಚ್ಚು ತೇವಾಂಶ (${input.humidity}%): ಶಿಲೀಂಧ್ರ ಹರಡುವ ಸಾಧ್ಯತೆ ಹೆಚ್ಚು."
                }
                "gu" -> {
                    potentialRisks.add("ફૂગજન્ય રોગોનું ઊંચું જોખમ: સુકારો (Blight), છારો (Powdery Mildew) અને ગેરુ (Rust).")
                    recommendations.add("સાવચેતી રૂપે મેન્કોઝેબ 2 ગ્રામ/લીટર અથવા કોપર ઓક્સીક્લોરાઇડ 3 ગ્રામ/લીટરનો છંટકાવ કરો.")
                    recommendations.add("હવા ઉજાસ માટે છોડ વચ્ચે યોગ્ય અંતર જાળવો.")
                    weatherAlert = "⚠️ વધુ પડતો ભેજ (${input.humidity}%): ફૂગના ઝડપી ફેલાવાની શક્યતા."
                }
                "pa" -> {
                    potentialRisks.add("ਉੱਲੀ ਰੋਗਾਂ ਦਾ ਵੱਡਾ ਖ਼ਤਰਾ: ਝੁਲਸ ਰੋਗ (Blight), ਚਿੱਟਾ ਰੋਗ (Mildew) ਅਤੇ ਰੱਤੂਆ (Rust)।")
                    recommendations.add("ਬਚਾਅ ਵਜੋਂ ਮੈਨਕੋਜ਼ੇਬ 2 ਗ੍ਰਾਮ/ਲੀਟਰ ਜਾਂ ਕਾਪਰ ਆਕਸੀਕਲੋਰਾਈਡ 3 ਗ੍ਰਾਮ/ਲੀਟਰ ਦਾ ਛਿੜਕਾਅ ਕਰੋ।")
                    recommendations.add("ਹਵਾ ਦੇ ਗੇੜ ਲਈ ਬੂਟਿਆਂ ਵਿਚਕਾਰ ਸਹੀ ਵਿੱਥ ਰੱਖੋ।")
                    weatherAlert = "⚠️ ਜ਼ਿਆਦਾ ਨਮੀ (${input.humidity}%): ਉੱਲੀ ਦੇ ਤੇਜ਼ੀ ਨਾਲ ਵਧਣ ਦਾ ਖ਼ਤਰਾ।"
                }
                "ml" -> {
                    potentialRisks.add("ഫംഗസ് രോഗ സാധ്യത: ഇലപ്പുള്ളി രോഗം, ചാരപ്പൂപ്പ്, തുരുമ്പ് രോഗം.")
                    recommendations.add("പ്രതിരോധത്തിനായി മാങ്കോസെബ് 2 ഗ്രാം/ലിറ്റർ അല്ലെങ്കിൽ കോപ്പർ ഓക്സിക്ലോറൈഡ് 3 ഗ്രാം/ലിറ്റർ തളിക്കുക.")
                    recommendations.add("കാറ്റ് കടക്കാനായി ചെടികൾ തമ്മിൽ ആവശ്യത്തിന് അകലം പാലിക്കുക.")
                    weatherAlert = "⚠️ ഉയർന്ന അന്തരീക്ഷ ഈർപ്പം (${input.humidity}%): കുമിൾ രോഗ സാധ്യത കൂടുതൽ."
                }
                "or" -> {
                    potentialRisks.add("କବକ ଜନିତ ରୋଗ ଆଶଙ୍କା: ପତ୍ରପୋଡ଼ା (Blight), ପାଉଁଶିଆ ରୋଗ ଓ କଳଙ୍କୀ ରୋଗ।")
                    recommendations.add("ପ୍ରତିଷେଧକ ଭାବେ ମାଙ୍କୋଜେବ 2 ଗ୍ରାମ/ଲିଟର କିମ୍ବା କପର ଅକ୍ସିକ୍ଲୋରାଇଡ 3 ଗ୍ରାମ/ଲିଟର ସ୍ପ୍ରେ କରନ୍ତୁ।")
                    recommendations.add("ପବନ ଚଳାଚଳ ପାଇଁ ଫସଲ ଧାଡ଼ି ମଧ୍ୟରେ ଉପଯୁକ୍ତ ବ୍ୟବଧାନ ରଖନ୍ତୁ।")
                    weatherAlert = "⚠️ ଅତ୍ୟଧିକ ଆର୍ଦ୍ରତା (${input.humidity}%): କବକ ସଂକ୍ରମଣର ତୀବ୍ର ଆଶଙ୍କା।"
                }
                else -> {
                    potentialRisks.add("High Fungal Infection Risk: Early/Late Blight, Powdery Mildew, Leaf Rust & Downy Mildew.")
                    recommendations.add("Apply prophylactic contact fungicide spray (Mancozeb 75% WP @ 2g/L or Copper Oxychloride @ 3g/L).")
                    recommendations.add("Ensure proper plant canopy spacing to facilitate air circulation and rapid leaf drying.")
                    weatherAlert = "⚠️ High Humidity (${input.humidity}%): Rapid spore germination danger for fungal pathogens."
                }
            }
        } else if (input.humidity >= 70) {
            baseRisk += 14f
            when (code) {
                "hi" -> {
                    potentialRisks.add("घनी फसल में पत्ती धब्बा और फफूंद का मध्यम खतरा।")
                    recommendations.add("निचली पत्तियों पर भूरे धब्बों की नियमित जांच करते रहें।")
                }
                "mr" -> {
                    potentialRisks.add("पानांवरील ठिपके आणि बुरशीचा मध्यम धोका.")
                    recommendations.add("खालच्या पानांची नियमित पाहणी करा.")
                }
                "bn" -> {
                    potentialRisks.add("পাতায় দাগ ও ছত্রাকের মাঝারি ঝুঁকি।")
                    recommendations.add("নিয়মিত গাছের পাতা পর্যবেক্ষণ করুন।")
                }
                "te" -> {
                    potentialRisks.add("ఆకులపై మచ్చలు మరియు తేలికపాటి తెగులు ముప్పు.")
                    recommendations.add("కింది ఆకులను తరచూ గమనిస్తూ ఉండండి.")
                }
                "ta" -> {
                    potentialRisks.add("இலைப்புள்ளி நோய் தாக்கும் மிதமான அபாயம்.")
                    recommendations.add("கீழ் இலைகளை தொடர்ந்து கண்காணிக்கவும்.")
                }
                "kn" -> {
                    potentialRisks.add("ಎಲೆಗಳ ಮೇಲೆ ಚುಕ್ಕೆ ಮತ್ತು ಶಿಲೀಂಧ್ರದ ಸಾಧಾರಣ ಅಪಾಯ.")
                    recommendations.add("ಕೆಳಗಿನ ಎಲೆಗಳನ್ನು ನಿಯಮಿತವಾಗಿ ಪರಿಶೀಲಿಸಿ.")
                }
                "gu" -> {
                    potentialRisks.add("પાંદડા પર ટપકા અને સામાન્ય ફૂગનું જોખમ.")
                    recommendations.add("નીચલા પાંદડાઓનું નિયમિત નિરીક્ષણ કરો.")
                }
                "pa" -> {
                    potentialRisks.add("ਪੱਤਿਆਂ 'ਤੇ ਧੱਬੇ ਅਤੇ ਹਲਕੀ ਉੱਲੀ ਦਾ ਖ਼ਤਰਾ।")
                    recommendations.add("ਹੇਠਲੇ ਪੱਤਿਆਂ ਦੀ ਨਿਯਮਤ ਜਾਂਚ ਕਰੋ।")
                }
                "ml" -> {
                    potentialRisks.add("ഇലപ്പുള്ളി രോഗത്തിന്റെ മിതമായ സാധ്യത.")
                    recommendations.add("അടിഭാഗത്തെ ഇലകൾ പതിവായി പരിശോധിക്കുക.")
                }
                "or" -> {
                    potentialRisks.add("ପତ୍ରରେ ଦାଗ ଏବଂ କବକର ମଧ୍ୟମ ଧରଣର ଆଶଙ୍କା।")
                    recommendations.add("ତଳ ପତ୍ରଗୁଡ଼ିକୁ ନିୟମିତ ଯାଞ୍ଚ କରନ୍ତୁ।")
                }
                else -> {
                    potentialRisks.add("Moderate Leaf Spot & Mildew Risk in dense crop canopies.")
                    recommendations.add("Inspect lower leaves regularly for early discoloration or brown necrotic spots.")
                }
            }
        }

        // 2. High Temperature Heat Stress (>35°C)
        if (input.temperature >= 35) {
            baseRisk += 20f
            when (code) {
                "hi" -> {
                    potentialRisks.add("तापमान तनाव और फूल झड़ना: अधिक वाष्पीकरण से पराग कणों का सूखना।")
                    potentialRisks.add("कीट प्रकोप: शुष्क गर्मी में लाल मकड़ी (Mites), थ्रिप्स और सफेद मक्खी का प्रसार।")
                    recommendations.add("सुबह या शाम के समय हल्की सिंचाई का अंतराल बढ़ाएं।")
                    recommendations.add("गर्मी से बचाव के लिए पोटेशियम नाइट्रेट (13:0:45 @ 10 ग्राम/लीटर) का छिड़काव करें।")
                }
                "mr" -> {
                    potentialRisks.add("उष्णतेचा ताण व फूलगळ: परागीभवनावर परिणाम.")
                    potentialRisks.add("कीड प्रादुर्भाव: उष्ण व कोरड्या हवामानात लाल कोळी, थ्रिप्स व पांढरी माशीचा धोका.")
                    recommendations.add("सकाळी किंवा संध्याकाळी हलके पाणी द्या.")
                }
                "bn" -> {
                    potentialRisks.add("তাপপ্রবাহ ও ফুল ঝরে পড়া: লাল মাকড় ও থ্রিপস পোকার আক্রমণ।")
                    recommendations.add("সকাল বা বিকেলে হালকা সেচ দিন।")
                }
                "te" -> {
                    potentialRisks.add("ఎండ వేడిమి వల్ల పూత రాలడం: ఎర్ర నల్లి, తామర పురుగుల దాడి.")
                    recommendations.add("ఉదయం లేదా సాయంత్రం వేళల్లో తేలికపాటి తడులు ఇవ్వండి.")
                }
                "ta" -> {
                    potentialRisks.add("வெப்ப அழுத்தம் மற்றும் பூ உதிர்தல்: அசுவினி, இலைப்பேன் தாக்குதல்.")
                    recommendations.add("காலை அல்லது மாலை வேளையில் பாசனம் செய்யவும்.")
                }
                "kn" -> {
                    potentialRisks.add("ಬಿಸಿಲಿನ ತಾಪ ಮತ್ತು ಹೂವು ಉದುರುವುದು: ರಸ ಹೀರುವ ಕೀಟಗಳ ಹಾವಳಿ.")
                    recommendations.add("ಬೆಳಿಗ್ಗೆ ಅಥವಾ ಸಂಜೆ ವೇಳೆ ಲಘು ನೀರಾವರಿ ಒದಗಿಸಿ.")
                }
                "gu" -> {
                    potentialRisks.add("ગરમીનો તણાવ અને ફૂલ ખરી પડવા: થ્રિપ્સ અને સફેદ માખીનો ઉપદ્રવ.")
                    recommendations.add("સવાર અથવા સાંજના સમયે હળવું પિયત આપો.")
                }
                "pa" -> {
                    potentialRisks.add("ਗਰਮੀ ਦਾ ਤਣਾਅ ਅਤੇ ਫੁੱਲ ਝੜਨਾ: ਚੂਸਕ ਕੀੜਿਆਂ ਦਾ ਹਮਲਾ।")
                    recommendations.add("ਸਵੇਰੇ ਜਾਂ ਸ਼ਾਮ ਨੂੰ ਹਲਕੀ ਸਿੰਚਾਈ ਕਰੋ।")
                }
                "ml" -> {
                    potentialRisks.add("കടുത്ത ചൂടും പൂക്കൾ കൊഴിയലും: കീടങ്ങളുടെ ആക്രമണ സാധ്യത.")
                    recommendations.add("രാവിലെയോ വൈകുന്നേരമോ നേരിയ നന നൽകുക.")
                }
                "or" -> {
                    potentialRisks.add("ତାପମାତ୍ରା ବୃଦ୍ଧି ଓ ଫୁଲ ଝଡ଼ିବା: ଶୋଷକ କୀଟଙ୍କ ପ୍ରାଦୁର୍ଭାବ।")
                    recommendations.add("ସକାଳେ କିମ୍ବା ସନ୍ଧ୍ୟାରେ ହାଲୁକା ଜଳସେଚନ କରନ୍ତୁ।")
                }
                else -> {
                    potentialRisks.add("Heat Stress & Blossom Drop: Accelerated transpiration and pollen sterility.")
                    potentialRisks.add("Pest Outbreak Risk: Red Spider Mites, Thrips, and Whiteflies thrive in dry heat.")
                    recommendations.add("Increase light irrigation frequency during early morning or evening hours.")
                    recommendations.add("Spray Potassium Nitrate (13:0:45 @ 10g/L) to enhance plant heat tolerance.")
                }
            }
            val heatMsg = when (code) {
                "hi" -> "☀️ अत्यधिक गर्मी की चेतावनी (${input.temperature}°C): नमी संरक्षण का विशेष ध्यान रखें।"
                "mr" -> "☀️ तीव्र उष्णतेचा इशारा (${input.temperature}°C): पाण्याचा ताण पडू देऊ नका."
                "bn" -> "☀️ তীব্র গরমের সতর্কতা (${input.temperature}°C)।"
                "te" -> "☀️ తీవ్రమైన ఎండ హెచ్చరిక (${input.temperature}°C)."
                "ta" -> "☀️ அதிக வெப்ப எச்சரிக்கை (${input.temperature}°C)."
                "kn" -> "☀️ ತೀವ್ರ ಶಾಖದ ಎಚ್ಚರಿಕೆ (${input.temperature}°C)."
                "gu" -> "☀️ કાળઝાળ ગરમીની ચેતવણી (${input.temperature}°C)."
                "pa" -> "☀️ ਭਾਰੀ ਗਰਮੀ ਦੀ ਚਿਤਾਵਨੀ (${input.temperature}°C)।"
                "ml" -> "☀️ കഠിനമായ ചൂട് മുന്നറിയിപ്പ് (${input.temperature}°C)."
                "or" -> "☀️ ପ୍ରବଳ ଗ୍ରୀଷ୍ମ ପ୍ରବାହ ସତର୍କତା (${input.temperature}°C)।"
                else -> "☀️ Extreme Heat Alert (${input.temperature}°C): Guard against moisture stress."
            }
            weatherAlert = (weatherAlert?.let { "$it\n" } ?: "") + heatMsg
        }

        // 3. Rain / Moisture Risk
        if (input.rainProbability >= 65 || input.conditionDescription.lowercase().contains("rain")) {
            baseRisk += 15f
            when (code) {
                "hi" -> {
                    potentialRisks.add("जलभराव से जीवाणु झुलसा (Bacterial Blight) और जड़ गलन (Root Rot) की आशंका।")
                    recommendations.add("खेत से अतिरिक्त पानी निकालने के लिए नालियों को साफ रखें।")
                    recommendations.add("बारिश रुकने तक किसी भी कीटनाशक का छिड़काव स्थगित रखें।")
                }
                "mr" -> {
                    potentialRisks.add("जास्त पाण्यामुळे मूळकुज आणि जिवाणूजन्य करपा होण्याचा धोका.")
                    recommendations.add("पाण्याचा निचरा होण्यासाठी शेतात चर काढा.")
                }
                "bn" -> {
                    potentialRisks.add("অতিরিক্ত জলে শিকড় পচা ও ব্যাকটেরিয়াল ব্লাইট হতে পারে।")
                    recommendations.add("জমির জল নিকাশি ব্যবস্থা পরিষ্কার রাখুন।")
                }
                "te" -> {
                    potentialRisks.add("నీరు నిలవడం వల్ల వేరుకుళ్లు మరియు బాక్టీరియా తెగులు వచ్చే అవకాశం.")
                    recommendations.add("పొలంలో నీరు నిల్వ ఉండకుండా మురుగు కాలువలు తీయండి.")
                }
                "ta" -> {
                    potentialRisks.add("தேங்கி நிற்கும் நீரால் வேரழுகல் நோய் ஏற்படும் அபாயம்.")
                    recommendations.add("வடிகால் வசதியை சரிசெய்யவும்.")
                }
                "kn" -> {
                    potentialRisks.add("ನೀರು ನಿಲ್ಲುವುದರಿಂದ ಬೇರು ಕೊಳೆತ ಮತ್ತು ಬ್ಯಾಕ್ಟೀರಿಯಾ ರೋಗದ ಅಪಾಯ.")
                    recommendations.add("ಜಮೀನಿನಲ್ಲಿ ನೀರು ನಿಲ್ಲದಂತೆ ಬಸಿದು ಹೋಗಲು ಕಾಲುವೆ ಮಾಡಿ.")
                }
                "gu" -> {
                    potentialRisks.add("પાણી ભરાવાથી મૂળનો કોહવારો અને બેક્ટેરિયલ સુકારો થવાનું જોખમ.")
                    recommendations.add("ખેતરમાંથી વધારાના પાણીના નિકાલની વ્યવસ્થા કરો.")
                }
                "pa" -> {
                    potentialRisks.add("ਪਾਣੀ ਖੜ੍ਹਨ ਨਾਲ ਜੜ੍ਹ ਗਲਣ ਅਤੇ ਬੈਕਟੀਰੀਆ ਰੋਗ ਦਾ ਖ਼ਤਰਾ।")
                    recommendations.add("ਖੇਤ ਵਿੱਚੋਂ ਪਾਣੀ ਦੇ ਨਿਕਾਸ ਦਾ ਪ੍ਰਬੰਧ ਕਰੋ।")
                }
                "ml" -> {
                    potentialRisks.add("വെള്ളക്കെട്ട് കാരണം വേരുചീയൽ രോഗ സാധ്യത.")
                    recommendations.add("പാടത്തുനിന്ന് വെള്ളം വാർന്നുപോകാൻ ചാലുകൾ കീറുക.")
                }
                "or" -> {
                    potentialRisks.add("ଜଳବନ୍ଦୀ ଯୋଗୁଁ ଚେର ପଚା ଏବଂ ଜୀବାଣୁ ରୋଗ ହେବାର ଆଶଙ୍କା।")
                    recommendations.add("ଜମିରୁ ଅତିରିକ୍ତ ଜଳ ନିଷ୍କାସନ ପାଇଁ ନାଳି ସଫା ରଖନ୍ତୁ।")
                }
                else -> {
                    potentialRisks.add("Bacterial Leaf Blight & Root Rot due to prolonged leaf wetness & waterlogging.")
                    recommendations.add("Ensure field drainage channels are unobstructed to avoid root submergence.")
                    recommendations.add("Postpone foliar pesticide/fertilizer spraying until rainfall subsides.")
                }
            }
        }

        // 4. 7-Day Preventive Action Plan
        when (code) {
            "hi" -> {
                plan7Day.add("दिन 1-2: खेत के किनारों पर पत्तियों की निचली सतह और तने पर कीटों के अंडों की निगरानी करें।")
                plan7Day.add("दिन 3: रस चूसक कीटों से बचाव के लिए 10,000 PPM नीम तेल (@ 3 मिली/लीटर) का छिड़काव करें।")
                plan7Day.add("दिन 4-5: मेड़ों से खरपतवार साफ करें जो विषाणु फैलाने वाले कीटों का बसेरा बनते हैं।")
                plan7Day.add("दिन 6: मिट्टी की नमी जांचें; ऊपरी 3 इंच सूखने पर ही हल्की सिंचाई करें।")
                plan7Day.add("दिन 7: फसल की वृद्धि परखें; किसी भी पीले पत्ते को कृषि सेवक AI से स्कैन करें।")
            }
            "mr" -> {
                plan7Day.add("दिवस १-२: पानांच्या खालच्या बाजूला किडींच्या अंड्यांचे निरीक्षण करा.")
                plan7Day.add("दिवस ३: रस शोषणाऱ्या किडींसाठी निंबोळी अर्क किंवा नीम तेल (३ मिली/लिटर) फवारा.")
                plan7Day.add("दिवस ४-५: बांधावरील तण काढून स्वच्छता ठेवा.")
                plan7Day.add("दिवस ६: जमिनीतील ओलावा तपासूनच पाणी द्या.")
                plan7Day.add("दिवस ७: पिकाची पाहणी करा; संशयित पानाचा कृषि सेवक AI द्वारे फोटो काढून निदान करा.")
            }
            "bn" -> {
                plan7Day.add("দিন ১-২: পাতার নিচে ও ডগায় পোকার উপস্থিতি পরীক্ষা করুন।")
                plan7Day.add("দিন ৩: নিম তেল (৩ মিলি/লিটার) স্প্রে করে প্রাথমিক পোকা দমন করুন।")
                plan7Day.add("দিন ৪-৫: জমির আইলের আগাছা পরিষ্কার রাখুন।")
                plan7Day.add("দিন ৬: মাটির আর্দ্রতা দেখে সেচ দিন।")
                plan7Day.add("দিন ৭: ফসলের স্বাস্থ্য পরীক্ষা করে কৃষি সেবক AI তে স্ক্যান করুন।")
            }
            "te" -> {
                plan7Day.add("రోజు 1-2: ఆకుల అడుగు భాగాన రసం పీల్చే పురుగుల గుడ్లను గమనించండి.")
                plan7Day.add("రోజు 3: వేప నూనె (3 మి.లీ/లీటరు) పిచికారీ చేయండి.")
                plan7Day.add("రోజు 4-5: గట్లపై ఉన్న కలుపు మొక్కలను తొಲగించండి.")
                plan7Day.add("రోజు 6: నేలలో తేమను బట్టి నీరు పెట్టండి.")
                plan7Day.add("రోజు 7: కృషి సేవక్ AI ద్వారా అనుమానాస్పద ఆకులను స్కాన్ చేయండి.")
            }
            "ta" -> {
                plan7Day.add("நாள் 1-2: இலைகளின் அடிப்பகுதியில் பூச்சிகள் உள்ளதா என கண்காணிக்கவும்.")
                plan7Day.add("நாள் 3: வேப்பெண்ணெய் (3 மி.லி/லிட்டர்) தெளிக்கவும்.")
                plan7Day.add("நாள் 4-5: வரப்புகளில் உள்ள களைகளை அகற்றவும்.")
                plan7Day.add("நாள் 6: மண்ணின் ஈரப்பதத்திற்கு ஏற்ப பாசனம் செய்யவும்.")
                plan7Day.add("நாள் 7: கிருஷி சேவக் AI மூலம் இலைகளை ஸ்கேன் செய்து கண்காணிக்கவும்.")
            }
            "kn" -> {
                plan7Day.add("ದಿನ 1-2: ಎಲೆಗಳ ಕೆಳಭಾಗದಲ್ಲಿ ಕೀಟಗಳ ಮೊಟ್ಟೆಗಳಿವೆಯೇ ಎಂದು ಪರೀಕ್ಷಿಸಿ.")
                plan7Day.add("ದಿನ 3: ಬೇವಿನ ಎಣ್ಣೆ (3 ಮಿಲಿ/ಲೀಟರ್) ಸಿಂಪಡಿಸಿ ರೋಗ ನಿಯಂತ್ರಿಸಿ.")
                plan7Day.add("ದಿನ 4-5: ಬದುಗಳ ಮೇಲಿನ ಕಳೆಗಳನ್ನು ತೆಗೆದು ಸ್ವಚ್ಛವಾಗಿಡಿ.")
                plan7Day.add("ದಿನ 6: ಮಣ್ಣಿನಲ್ಲಿ ತೇವಾಂಶ ನೋಡಿಕೊಂಡು ನೀರು ಹಾಯಿಸಿ.")
                plan7Day.add("ದಿನ 7: ಕೃಷಿ ಸೇವಕ್ AI ಮೂಲಕ ಶಂಕಿತ ಎಲೆಗಳನ್ನು ಸ್ಕ್ಯಾನ್ ಮಾಡಿ.")
            }
            "gu" -> {
                plan7Day.add("દિવસ 1-2: પાંદડાની નીચે અને થડ પર જીવાતનું નિરીક્ષણ કરો.")
                plan7Day.add("દિવસ 3: લીમડાનું તેલ (3 મિલી/લીટર) છાંટીને જીવાત નિયંત્રણ કરો.")
                plan7Day.add("દિવસ 4-5: શેઢા-પાળા પરથી નીંદણ દૂર કરો.")
                plan7Day.add("દિવસ 6: જમીનમાં ભેજ જોઈને જ પિયત આપો.")
                plan7Day.add("દિવસ 7: કૃષિ સેવક AI દ્વારા શંકાસ્પદ પાન સ્કેન કરો.")
            }
            "pa" -> {
                plan7Day.add("ਦਿਨ 1-2: ਪੱਤਿਆਂ ਦੇ ਹੇਠਲੇ ਪਾਸੇ ਕੀੜਿਆਂ ਦੇ ਆਂਡਿਆਂ ਦੀ ਜਾਂਚ ਕਰੋ।")
                plan7Day.add("ਦਿਨ 3: ਨਿੰਮ ਦਾ ਤੇਲ (3 ਮਿਲੀ/ਲੀਟਰ) ਛਿੜਕ ਕੇ ਕੀੜੇ ਰੋਕੋ।")
                plan7Day.add("ਦਿਨ 4-5: ਵੱਟਾਂ ਤੋਂ ਨਦੀਨ ਸਾਫ਼ ਕਰੋ।")
                plan7Day.add("ਦਿਨ 6: ਜ਼ਮੀਨ ਦੀ ਨਮੀ ਅਨੁਸਾਰ ਸਿੰਚਾਈ ਕਰੋ।")
                plan7Day.add("ਦਿਨ 7: ਕ੍ਰਿਸ਼ੀ ਸੇਵਕ AI ਨਾਲ ਸ਼ੱਕੀ ਪੱਤਿਆਂ ਨੂੰ ਸਕੈਨ ਕਰੋ।")
            }
            "ml" -> {
                plan7Day.add("ദിവസം 1-2: ഇലകളുടെ അടിഭാഗത്ത് കീടങ്ങളുടെ സാന്നിധ്യം പരിശോധിക്കുക.")
                plan7Day.add("ദിവസം 3: വേപ്പെണ്ണ (3 മി.ലി/ലിറ്റർ) തളിച്ച് കീടനിയന്ത്രണം നടത്തുക.")
                plan7Day.add("ദിവസം 4-5: വരമ്പുകളിലെ കളകൾ പറിച്ച് വൃത്തിയാക്കുക.")
                plan7Day.add("ദിവസം 6: മണ്ണിലെ ഈർപ്പം നോക്കി നനയ്ക്കുക.")
                plan7Day.add("ദിവസം 7: കൃഷി സേവക് AI ഉപയോഗിച്ച് സംശയമുള്ള ഇലകൾ സ്കാൻ ചെയ്യുക.")
            }
            "or" -> {
                plan7Day.add("ଦିନ 1-2: ପତ୍ରର ତଳ ଭାଗରେ କୀଟ ମାନଙ୍କ ଉପସ୍ଥିତି ଯାଞ୍ଚ କରନ୍ତୁ।")
                plan7Day.add("ଦିନ 3: ନିମ୍ବ ତେଲ (3 ମିଲି/ଲିଟର) ସ୍ପ୍ରେ କରି ପ୍ରାଥମିକ କୀଟ ଦମନ କରନ୍ତୁ।")
                plan7Day.add("ଦିନ 4-5: ହିଡ଼ରେ ଥିବା ଅନାବନା ଘାସ ସଫା କରନ୍ତୁ।")
                plan7Day.add("ଦିନ 6: ମାଟିରେ ଆର୍ଦ୍ରତା ଦେଖି ଜଳସେଚନ କରନ୍ତୁ।")
                plan7Day.add("ଦିନ 7: କୃଷି ସେବକ AI ଦ୍ୱାରା ସନ୍ଦିଗ୍ଧ ପତ୍ରକୁ ସ୍କାନ କରନ୍ତୁ।")
            }
            else -> {
                plan7Day.add("Day 1-2: Inspect underside of leaves & stem joints across field borders for pest egg masses.")
                plan7Day.add("Day 3: Apply Neem Oil 10,000 PPM (@ 3ml/L) as an organic repellent against sucking pests.")
                plan7Day.add("Day 4-5: Clear weeds from field bunds which act as alternative hosts for viral vectors.")
                plan7Day.add("Day 6: Monitor soil moisture; irrigate if top 3 inches feel dry.")
                plan7Day.add("Day 7: Re-evaluate crop vigour; click and scan any suspicious leaf with Krishi Sevak AI.")
            }
        }

        if (recommendations.isEmpty()) {
            recommendations.add(when (code) {
                "hi" -> "नियमित रूप से फसल की निगरानी जारी रखें और खेत में स्वच्छता बनाए रखें।"
                "mr" -> "पिकाचे नियमित निरीक्षण चालू ठेवा आणि शेतात स्वच्छता राखा."
                "bn" -> "নিয়মিত ফসল পর্যবেক্ষণ করুন ও জমি পরিষ্কার রাখুন।"
                "te" -> "పంటను క్రమం తప్పకుండా పర్యవేక్షించండి."
                "ta" -> "பயிரை தொடர்ந்து கண்காணித்து சுத்தமாக பராமரிக்கவும்."
                "kn" -> "ಬೆಳೆಯನ್ನು ನಿರಂತರವಾಗಿ ಪರಿಶೀಲಿಸಿ ಸ್ವಚ್ಛತೆ ಕಾಪಾಡಿ."
                "gu" -> "પાકનું નિયમિત નિરીક્ષણ ચાલુ રાખો અને ખેતરમાં સ્વચ્છતા જાળવો."
                "pa" -> "ਫ਼ਸਲ ਦੀ ਲਗਾਤਾਰ ਦੇਖਭਾਲ ਰੱਖੋ ਅਤੇ ਖੇਤ ਸਾਫ਼ ਰੱਖੋ।"
                "ml" -> "വിളകൾ പതിവായി നിരീക്ഷിക്കുകയും തോട്ടം വൃത്തിയായി സൂക്ഷിക്കുകയും ചെയ്യുക."
                "or" -> "ଫସଲର ନିୟମିତ ଯତ୍ନ ନିଅନ୍ତୁ ଏବଂ ଜମି ସଫା ରଖନ୍ତୁ।"
                else -> "Continue regular crop scouting and maintain clean field sanitation."
            })
        }

        val finalScore = baseRisk.coerceIn(10f, 96f).toInt()
        val riskLevel = when {
            finalScore >= 60 -> "HIGH"
            finalScore >= 35 -> "MEDIUM"
            else -> "LOW"
        }

        return CropDoctorResult(
            riskLevel = riskLevel,
            riskScore = finalScore,
            weatherAlert = weatherAlert,
            potentialRisks = potentialRisks.ifEmpty {
                listOf(when (code) {
                    "hi" -> "वर्तमान मौसम स्थिति में फसल रोग का जोखिम न्यूनतम है।"
                    "mr" -> "सध्याच्या हवामानात रोगाचा धोका अत्यंत कमी आहे."
                    "bn" -> "বর্তমান আবহাওয়ায় ফসলের ঝুঁকি কম।"
                    "te" -> "ప్రస్తుత వాతావరణంలో తెగుళ్ల ముప్పు తక్కువగా ఉంది."
                    "ta" -> "தற்போதைய வானிலையில் நோய் அபாயம் குறைவு."
                    "kn" -> "ಪ್ರಸ್ತುತ ಹವಾಮಾನದಲ್ಲಿ ಬೆಳೆ ರೋಗದ ಅಪಾಯ ಕಡಿಮೆ ಇದೆ."
                    "gu" -> "હાલના હવામાનમાં પાકના રોગનું જોખમ ઓછું છે."
                    "pa" -> "ਮੌਜੂਦਾ ਮੌਸਮ ਵਿੱਚ ਫ਼ਸਲੀ ਬਿਮਾਰੀਆਂ ਦਾ ਖ਼ਤਰਾ ਘੱਟ ਹੈ।"
                    "ml" -> "നിലവിലെ കാലാവസ്ഥയിൽ വിള രോഗ സാധ്യത കുറവാണ്."
                    "or" -> "ବର୍ତ୍ତମାନ ପାଣିପାଗରେ ଫସଲ ରୋଗର ଆଶଙ୍କା କମ ଅଛି।"
                    else -> "Crop disease risk is currently low under normal weather."
                })
            },
            recommendations = recommendations,
            preventivePlan7Day = plan7Day
        )
    }
}
