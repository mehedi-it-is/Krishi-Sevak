package com.krishisevak.app.data.engine

import com.krishisevak.app.data.local.db.PlantScanEntity

data class CropDoctorInput(
    val temperature: Float = 29f,
    val humidity: Int = 78,
    val rainProbability: Int = 60,
    val conditionDescription: String = "Partly Cloudy",
    val pastScans: List<PlantScanEntity> = emptyList()
)

data class CropDoctorResult(
    val riskLevel: String, // "LOW", "MEDIUM", "HIGH"
    val riskScore: Int, // 0 to 100%
    val weatherAlert: String?,
    val potentialRisks: List<String>,
    val recommendations: List<String>,
    val preventivePlan7Day: List<String>
)

object CropDoctorEngine {

    fun evaluate(input: CropDoctorInput, langCode: String = "en"): CropDoctorResult {
        var baseRisk = 20f
        val potentialRisks = mutableListOf<String>()
        val recommendations = mutableListOf<String>()
        val plan7Day = mutableListOf<String>()
        var weatherAlert: String? = null
        val code = langCode.lowercase()

        // 1. Weather Humidity Risk (>80% is prime breeding ground for fungal spores)
        if (input.humidity >= 80) {
            baseRisk += 28f
            when (code) {
                "hi" -> {
                    potentialRisks.add("उच्च फफूंद संक्रमण का खतरा: झुलसा रोग (Blight), चूर्णिल आसिता (Powdery Mildew) और पर्ण रतुआ (Leaf Rust)।")
                    recommendations.add("बचाव हेतु फफूंदनाशक छिड़कें (मैंकोजेब 75% WP @ 2 ग्राम/लीटर या कॉपर ऑक्सीक्लोराइड @ 3 ग्राम/लीटर)।")
                    recommendations.add("पौधों के बीच उचित दूरी रखें ताकि हवा का आवागमन बना रहे और पत्तियां जल्दी सूखें।")
                    weatherAlert = "⚠️ अत्यधिक आर्द्रता (${input.humidity}%): फफूंद और कवक बीजाणुओं के तेजी से पनपने की चेतावनी।"
                }
                "mr" -> {
                    potentialRisks.add("बुरशीजन्य रोगांचा मोठा धोका: करपा (Blight), भुरी (Powdery Mildew) आणि तांबेरा (Rust).")
                    recommendations.add("प्रतिबंधात्मक उपाय म्हणून मॅन्कोझेब २ ग्रॅम/लिटर किंवा कॉपर ऑक्सिक्लोराईड ३ ग्रॅम/लिटर फवारा.")
                    recommendations.add("हवा खेळती राहण्यासाठी पिकात योग्य अंतर ठेवा.")
                    weatherAlert = "⚠️ जास्त आर्द्रता (${input.humidity}%): बुरशीची वाढ वेगाने होण्याचा धोका."
                }
                "bn" -> {
                    potentialRisks.add("ছত্রাক সংক্রমণের উচ্চ ঝুঁকি: ব্লাইট, পাউডারি মিলডিউ ও পাতার মরিচা রোগ।")
                    recommendations.add("প্রতিরোধক ছত্রাকনাশক স্প্রে করুন (ম্যানকোজেব ২ গ্রাম/লিটার বা কপার অক্সিক্লোরাইড ৩ গ্রাম/লিটার)।")
                    weatherAlert = "⚠️ অতিরিক্ত আর্দ্রতা (${input.humidity}%): ছত্রাকের দ্রুত বিস্তারের আশঙ্কা।"
                }
                "te" -> {
                    potentialRisks.add("శిలీంధ్ర వ్యాధుల తీవ్ర ముప్పు: ఆకుమచ్చ తెగులు, బూడిద తెగులు మరియు తుప్పు తెగులు.")
                    recommendations.add("నివారణకు మాంకోజెబ్ 2 గ్రా/లీటరు లేదా కాపర్ ఆక్సిక్లోరైడ్ 3 గ్రా/లీటరు పిచికారీ చేయండి.")
                    weatherAlert = "⚠️ అధిక తేమ (${input.humidity}%): శిలీంధ్రాలు వేగంగా వృద్ధి చెందే ప్రమాదం."
                }
                "ta" -> {
                    potentialRisks.add("பூஞ்சான நோய் தாக்குதல் அபாயம்: இலைக்கருகல், சாம்பல் நோய் மற்றும் துரு நோய்.")
                    recommendations.add("முன்னெச்சரிக்கையாக மான்கோசெப் 2 கிராம்/லிட்டர் தெளிக்கவும்.")
                    weatherAlert = "⚠️ அதிக ஈரப்பதம் (${input.humidity}%): பூஞ்சான வித்துக்கள் பரவும் அபாயம்."
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
                    potentialRisks.add("অতিরিক্ত পানিতে শিকড় পচা ও ব্যাকটেরিয়াল ব্লাইট হতে পারে।")
                    recommendations.add("জমির পানি নিষ্কাশন ব্যবস্থা পরিষ্কার রাখুন।")
                }
                "te" -> {
                    potentialRisks.add("నీరు నిలవడం వల్ల వేరుకుళ్లు మరియు బాక్టీరియా తెగులు వచ్చే అవకాశం.")
                    recommendations.add("పొలంలో నీరు నిల్వ ఉండకుండా మురుగు కాలువలు తీయండి.")
                }
                "ta" -> {
                    potentialRisks.add("தேங்கி நிற்கும் நீரால் வேரழுகல் நோய் ஏற்படும் அபாயம்.")
                    recommendations.add("வடிகால் வசதியை சரிசெய்யவும்.")
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
                plan7Day.add("రోజు 4-5: గట్లపై ఉన్న కలుపు మొక్కలను తొలగించండి.")
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
                    else -> "Crop disease risk is currently low under normal weather."
                })
            },
            recommendations = recommendations,
            preventivePlan7Day = plan7Day
        )
    }
}
