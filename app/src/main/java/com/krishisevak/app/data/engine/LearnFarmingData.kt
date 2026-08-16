package com.krishisevak.app.data.engine

data class FarmingGuideTip(
    val id: Int,
    val iconEmoji: String,
    val title: String,
    val summary: String,
    val detailedContent: String,
    val practicalSteps: List<String>
)

object LearnFarmingData {

    fun getTipsForLanguage(langCode: String): List<FarmingGuideTip> {
        return when (langCode.lowercase()) {
            "hi" -> hindiTips
            "bn" -> bengaliTips
            "mr" -> marathiTips
            "te" -> teluguTips
            "ta" -> tamilTips
            "kn" -> kannadaTips
            "ml" -> malayalamTips
            "gu" -> gujaratiTips
            "pa" -> punjabiTips
            "or" -> odiaTips
            else -> englishTips
        }
    }

    private val englishTips = listOf(
        FarmingGuideTip(
            id = 1,
            iconEmoji = "🧪",
            title = "Soil Health Testing",
            summary = "Test your soil before every planting season to determine pH and exact N-P-K nutrient status.",
            detailedContent = "Regular soil testing every 2 years prevents over-fertilization, lowers input costs by up to 25%, and ensures crops absorb maximum nutrients from the root zone.",
            practicalSteps = listOf(
                "Collect soil samples in a 'V' shape from 8-10 spots across the field at 15 cm depth.",
                "Thoroughly mix samples, dry in shade, and take 500 grams in a clean bag.",
                "Send to your nearest Krishi Vigyan Kendra (KVK) or Soil Testing Laboratory.",
                "Apply fertilizers strictly according to Soil Health Card recommendations."
            )
        ),
        FarmingGuideTip(
            id = 2,
            iconEmoji = "🔄",
            title = "Crop Rotation Strategy",
            summary = "Rotate cereals with nitrogen-fixing pulses to restore natural soil biology and break pest cycles.",
            detailedContent = "Continuous mono-cropping depletes specific soil nutrients and builds up soil-borne fungal pathogens. Rotating crops like Rice-Wheat with Chickpea, Moong, or Mustard enhances soil nitrogen reserves.",
            practicalSteps = listOf(
                "Follow a heavy feeder crop (Paddy/Corn) with a light feeder legume (Chickpea/Moong).",
                "Alternate deep-rooted crops (Cotton/Pigeon pea) with shallow-rooted crops (Wheat/Mustard).",
                "Plant green manure crops (Dhaincha/Sunhemp) during summer fallow.",
                "Never plant solanaceous crops (Potato/Tomato) consecutively in the same plot."
            )
        ),
        FarmingGuideTip(
            id = 3,
            iconEmoji = "💧",
            title = "Water Management & Drip Irrigation",
            summary = "Drip systems deliver water and soluble nutrients directly to the root zone, saving 50% water.",
            detailedContent = "Flood irrigation loses 40-50% water to surface runoff and deep percolation. Micro-irrigation prevents salinity build-up and reduces weed germination between rows.",
            practicalSteps = listOf(
                "Install inline drip laterals tailored to crop row spacing.",
                "Inject water-soluble fertilizers (fertigation) directly through drip lines.",
                "Irrigate during early morning or late afternoon to minimize evaporation losses.",
                "Flush drip laterals monthly with acid treatment to clear calcium carbonate clogging."
            )
        ),
        FarmingGuideTip(
            id = 4,
            iconEmoji = "🌿",
            title = "Organic Compost & Bio-Fertilizers",
            summary = "Enhance soil organic carbon and beneficial microbes using Vermicompost, FYM, and Bio-inoculants.",
            detailedContent = "Healthy soil should have >0.75% organic carbon. Combining organic manure with biofertilizers (Rhizobium, Azotobacter, PSB) unlocks soil-bound minerals naturally.",
            practicalSteps = listOf(
                "Apply 5-8 tonnes of well-decomposed Farmyard Manure (FYM) per hectare during last ploughing.",
                "Broadcast 2-3 tonnes of Vermicompost per hectare in horticultural crops.",
                "Treat seeds with Rhizobium culture (for pulses) or Azotobacter (for cereals) before sowing.",
                "Use Phosphate Solubilizing Bacteria (PSB) to increase phosphorus availability."
            )
        ),
        FarmingGuideTip(
            id = 5,
            iconEmoji = "🐛",
            title = "Integrated Pest Management (IPM)",
            summary = "Control pests ecologically using yellow sticky traps, pheromone traps, and bio-pesticides.",
            detailedContent = "Indiscriminate chemical pesticide spraying eliminates beneficial predatory insects. IPM emphasizes economic threshold levels (ETL) and biological control first.",
            practicalSteps = listOf(
                "Install 15 yellow sticky cards per acre to trap whiteflies and aphids.",
                "Set up 5 pheromone traps per acre for early monitoring of bollworms and armyworms.",
                "Spray Neem Oil 10,000 PPM (@ 3ml/L) as first-line organic protection against sucking pests.",
                "Use chemical insecticides only when pest damage crosses the Economic Threshold Level."
            )
        ),
        FarmingGuideTip(
            id = 6,
            iconEmoji = "🌱",
            title = "Certified Quality Seed Selection",
            summary = "Use certified disease-free seeds with high germination percentage for bumper harvest.",
            detailedContent = "Quality certified seed ensures >85% germination rate, uniform field stand, and inherited genetic resistance against major regional diseases.",
            practicalSteps = listOf(
                "Purchase certified seeds with official government blue tag from authorized dealers.",
                "Conduct brine water floatation test to remove hollow, lightweight seeds.",
                "Follow FIR seed treatment protocol: Fungicide -> Insecticide -> Rhizobium.",
                "Sow at optimal depth (3-5 cm) to ensure rapid, uniform emergence."
            )
        ),
        FarmingGuideTip(
            id = 7,
            iconEmoji = "⛅",
            title = "Weather-Smart Farming Operations",
            summary = "Time your spraying, irrigation, and harvesting based on live Agromet weather forecasts.",
            detailedContent = "Checking rainfall probability, wind velocity, and humidity saves expensive pesticide washes, prevents lodging, and protects harvested grains from rain rot.",
            practicalSteps = listOf(
                "Never spray foliar chemicals if rain is forecasted within 4-6 hours.",
                "Avoid spraying when wind speed exceeds 15 km/h to prevent pesticide drift.",
                "Give light protective irrigation before cold waves or heat spikes.",
                "Harvest crops only when grain moisture drops below 14% and sunshine is clear."
            )
        ),
        FarmingGuideTip(
            id = 8,
            iconEmoji = "🌾",
            title = "Mulching & Moisture Conservation",
            summary = "Cover soil with crop residues or plastic mulch to suppress weeds and cut evaporation.",
            detailedContent = "Mulching reduces soil water evaporation by up to 40%, suppresses weed growth without chemical herbicides, and moderates soil root temperature.",
            practicalSteps = listOf(
                "Spread a 5-7 cm thick layer of paddy straw or sugarcane trash between crop rows.",
                "Use 25-30 micron silver-black plastic mulch film for high-value vegetable beds.",
                "Ensure mulch does not touch crop stems directly to prevent collar rot.",
                "Incorporate organic crop residue mulch into the soil after harvest to build humus."
            )
        )
    )

    private val hindiTips = listOf(
        FarmingGuideTip(
            id = 1,
            iconEmoji = "🧪",
            title = "मृदा स्वास्थ्य परीक्षण (Soil Testing)",
            summary = "हर बुवाई मौसम से पहले मिट्टी की जांच कराएं ताकि सही मात्रा में खाद दी जा सके।",
            detailedContent = "हर 2 साल में मिट्टी जांच कराने से 25% तक खाद का खर्च बचता है और जमीन की सेहत सुधरती है।",
            practicalSteps = listOf(
                "खेत में 8-10 अलग-अलग स्थानों से 'V' आकार में 15 सेमी गहराई से मिट्टी का नमूना लें।",
                "नमूने को छाया में सुखाकर अच्छी तरह मिलाएं और 500 ग्राम साफ थैली में भरें।",
                "निकटतम कृषि विज्ञान केंद्र (KVK) या सरकारी मृदा परीक्षण प्रयोगशाला में जमा करें।",
                "सॉइल हेल्थ कार्ड की सिफारिश के अनुसार ही यूरिया, डीएपी और पोटाश डालें।"
            )
        ),
        FarmingGuideTip(
            id = 2,
            iconEmoji = "🔄",
            title = "फसल चक्र रणनीति (Crop Rotation)",
            summary = "अनाज वाली फसलों के बाद दलहनी फसलें लगाएं ताकि मिट्टी में प्राकृतिक नाइट्रोजन बढ़ सके।",
            detailedContent = "धान-गेहूं के बाद चना, मूंग या सरसों लगाने से मिट्टी स्वस्थ रहती है और कीट चक्र टूटता है।",
            practicalSteps = listOf(
                "ज्यादा पोषक तत्व लेने वाली फसलों के बाद दालें (चना, मूंग) लगाएं।",
                "गहरी जड़ वाली फसलों के बाद उथली जड़ वाली फसलें बदल-बदल कर लगाएं।",
                "गर्मियों में खेत खाली रहने पर ढैंचा या सनई की हरी खाद लगाएं।",
                "एक ही खेत में लगातार आलू-टमाटर जैसी फसलें न लगाएं।"
            )
        ),
        FarmingGuideTip(
            id = 3,
            iconEmoji = "💧",
            title = "जल प्रबंधन और ड्रिप सिंचाई",
            summary = "ड्रिप सिस्टम सीधे पौधे की जड़ों तक पानी पहुंचाता है, जिससे 50% पानी की बचत होती है।",
            detailedContent = "टपक सिंचाई से पानी और घुलनशील खाद सीधे जड़ों में जाती है, जिससे खरपतवार नहीं पनपते।",
            practicalSteps = listOf(
                "फसल की कतार के अनुसार ड्रिप इनलाइन पाइप बिछाएं।",
                "ड्रिप के जरिए फर्टिगेशन (घुलनशील खाद) दें।",
                "सुबह या शाम के समय ही सिंचाई करें ताकि पानी का वाष्पीकरण कम हो।",
                "महीने में एक बार ड्रिप पाइप को साफ करें ताकि लवण न जमे।"
            )
        ),
        FarmingGuideTip(
            id = 4,
            iconEmoji = "🌿",
            title = "जैविक खाद और जैव उर्वरक",
            summary = "वर्मीकंपोस्ट, गोबर खाद और बायो-फर्टिलाइजर का उपयोग कर मिट्टी में कार्बन बढ़ाएं।",
            detailedContent = "जैविक खाद से मिट्टी की नमी सोखने की क्षमता बढ़ती है और मित्र जीवाणु हवा से नाइट्रोजन ग्रहण करते हैं।",
            practicalSteps = listOf(
                "खेत की अंतिम जुताई के समय 5-8 टन सड़ी गोबर खाद मिलाएं।",
                "सब्जियों में 2-3 टन प्रति हेक्टेयर केंचुआ खाद (वर्मीकंपोस्ट) डालें।",
                "बुवाई से पहले बीजों को राइजोबियम या एजोटोबैक्टर से उपचारित करें।",
                "जमीन में जमे फास्फोरस को घोलने के लिए पीएसबी (PSB) का प्रयोग करें।"
            )
        ),
        FarmingGuideTip(
            id = 5,
            iconEmoji = "🐛",
            title = "एकीकृत कीट प्रबंधन (IPM)",
            summary = "रासायनिक कीटनाशकों से पहले फेरोमोन ट्रैप, जैविक नियंत्रण और नीम तेल का उपयोग करें।",
            detailedContent = "अंधाधुंध कीटनाशक छिड़काव से मित्र कीट नष्ट होते हैं। आईपीएम पद्धति से कीटों को पर्यावरण अनुकूल तरीके से नियंत्रित किया जाता है।",
            practicalSteps = listOf(
                "सफेद मक्खी, माहू के लिए 15 पीले चिपचिपे कार्ड (येलो स्टिकी ट्रैप) प्रति एकड़ लगाएं।",
                "इल्ली और सुंडी की निगरानी के लिए 5 फेरोमोन ट्रैप प्रति एकड़ लगाएं।",
                "शुरुआती बचाव के लिए 10,000 PPM नीम तेल (3 मिली/लीटर) का छिड़काव करें।",
                "रासायनिक कीटनाशक का प्रयोग केवल तभी करें जब नुकसान आर्थिक सीमा (ETL) पार कर जाए।"
            )
        ),
        FarmingGuideTip(
            id = 6,
            iconEmoji = "🌱",
            title = "प्रमाणित बीज का चयन",
            summary = "उच्च अंकुरण क्षमता और रोग-प्रतिरोधक क्षमता के लिए हमेशा प्रमाणित बीज का प्रयोग करें।",
            detailedContent = "गुणवत्तापूर्ण प्रमाणित बीजों से 85% से अधिक अंकुरण होता है और फसल जोरदार बढ़ती है।",
            practicalSteps = listOf(
                "अधिकृत विक्रेताओं से सरकारी टैग लगा प्रमाणित बीज ही खरीदें।",
                "नमक के पानी के घोल में डालकर हल्के और खोखले बीजों को अलग कर दें।",
                "बीज शोधन (FIR): फफूंदनाशक -> कीटनाशक -> राइजोबियम कल्चर।",
                "बीज को 3-5 सेमी उचित गहराई पर ही बोएं।"
            )
        ),
        FarmingGuideTip(
            id = 7,
            iconEmoji = "⛅",
            title = "मौसम आधारित कृषि कार्य",
            summary = "दवा छिड़काव, सिंचाई और कटाई का काम मौसम पूर्वानुमान देखकर ही तय करें।",
            detailedContent = "बारिश और हवा की गति की जानकारी रखने से दवा धुलने का नुकसान बचता है।",
            practicalSteps = listOf(
                "यदि 4-6 घंटे में बारिश की संभावना हो तो कभी भी कीटनाशक का छिड़काव न करें।",
                "तेज हवा (15 किमी/घंटा से अधिक) के समय छिड़काव बंद रखें।",
                "पाला या लू की चेतावनी पर खेत में हल्की सिंचाई कर दें।",
                "फसल पूरी पकने और धूप खिलने पर ही कटाई करें।"
            )
        ),
        FarmingGuideTip(
            id = 8,
            iconEmoji = "🌾",
            title = "मल्चिंग और नमी संरक्षण",
            summary = "जमीन को पुआल या प्लास्टिक शीट से ढकें ताकि खरपतवार रुके और नमी बनी रहे।",
            detailedContent = "मल्चिंग करने से वाष्पीकरण 35-40% कम होता है और मिट्टी का तापमान अनुकूल बना रहता है।",
            practicalSteps = listOf(
                "पौधों के चारों ओर 5-7 सेमी मोटी धान के पुआल या सूखी घास की परत बिछाएं।",
                "सब्जियों में 25-30 माइक्रोन सिल्वर-ब्लैक प्लास्टिक मल्च लगाएं।",
                "मल्च को तने से हल्का दूर रखें ताकि तना सड़न न हो।",
                "फसल कटाई के बाद प्राकृतिक मल्च को मिट्टी में मिला दें।"
            )
        )
    )

    private val marathiTips = listOf(
        FarmingGuideTip(
            id = 1,
            iconEmoji = "🧪",
            title = "माती परीक्षण (Soil Health Testing)",
            summary = "पेरणीपूर्वी जमिनीची तपासणी करून खतांचे योग्य व्यवस्थापन करा व खर्च कमी करा.",
            detailedContent = "दर २ वर्षांनी माती परीक्षण केल्यास खतांचा खर्च २५% वाचतो आणि जमिनीची सुपीकता टिकून राहते.",
            practicalSteps = listOf(
                "शेतातून ८-१० वेगवेगळ्या ठिकाणांहून 'V' आकारात १५ सेमी खोलीवरून मातीचे नमुने गोळा करा.",
                "नमुने सावलीत वाळवून चांगले मिसळा आणि ५०० ग्रॅम स्वच्छ पिशवीत भरा.",
                "जवळच्या कृषी विज्ञान केंद्रात (KVK) किंवा माती परीक्षण प्रयोगशाळेत पाठवा.",
                "मृदा आरोग्य पत्रिकेनुसारच खतांचा वापर करा."
            )
        ),
        FarmingGuideTip(
            id = 2,
            iconEmoji = "🔄",
            title = "पीक फेरपालट (Crop Rotation)",
            summary = "धान्य पिकानंतर कडधान्य पिके घेऊन जमिनीतील नैसर्गिक नत्र वाढवा व कीड नियंत्रण करा.",
            detailedContent = "सतत एकच पीक घेतल्यास जमिनीतील विशिष्ट अन्नद्रव्ये कमी होतात. भात-गव्हानंतर हरभरा किंवा मूग घेतल्यास जमीन कसदार राहते.",
            practicalSteps = listOf(
                "जास्त अन्नद्रव्ये घेणाऱ्या पिकांनंतर कडधान्ये (हरभरा, मूग, उडीद) लावा.",
                "खोल मुळांच्या पिकानंतर उथळ मुळांची पिके आलटून-पालटून घ्या.",
                "उन्हाळ्यात शेत रिकामे असताना ताग किंवा धैंच्याचे हिरवळीचे खत घ्या.",
                "एकाच शेतात सलग टोमॅटो, बटाटा अशी पिके घेऊ नका."
            )
        ),
        FarmingGuideTip(
            id = 3,
            iconEmoji = "💧",
            title = "पाणी व्यवस्थापन व ठिबक सिंचन",
            summary = "ठिबक सिंचनामुळे पाण्याची ५०% बचत होते आणि पिकांची वाढ जोमदार होते.",
            detailedContent = "मोकळे पाणी दिल्यास पाण्याचा अपव्यय होतो. ठिबक सिंचनाद्वारे पाणी व विद्राव्य खते थेट मुळांपर्यंत पोहोचतात.",
            practicalSteps = listOf(
                "पिकांच्या ओळीनुसार योग्य अंतरावर ठिबकच्या नळ्या अंथरा.",
                "ठिबक द्वारेच विद्राव्य खते (फर्टिगेशन) द्या.",
                "बाष्पीभवन टाळण्यासाठी सकाळी किंवा संध्याकाळी पाणी द्या.",
                "नळ्यांमध्ये क्षार साचू नये म्हणून महिन्यातून एकदा फ्लशिंग करा."
            )
        ),
        FarmingGuideTip(
            id = 4,
            iconEmoji = "🌿",
            title = "सेंद्रिय खते व जिवाणू संवर्धक",
            summary = "शेणखत, गांडूळ खत आणि जिवाणू खतांचा वापर करून जमिनीतील सेंद्रिय कर्ब वाढवा.",
            detailedContent = "सेंद्रिय खतांमुळे जमिनीची पाणी धरून ठेवण्याची क्षमता वाढते आणि मित्र जिवाणू हवेतील नत्र पिकांना मिळवून देतात.",
            practicalSteps = listOf(
                "शेवटच्या नांगरणीच्या वेळी एकरी ५-८ टन चांगले कुजलेले शेणखत टाका.",
                "भाजीपाला पिकांमध्ये २-३ टन गांडूळ खत वापरा.",
                "पेरणीपूर्वी बियाणास रायझोबियम किंवा ॲझोटोबॅक्टरची बीजप्रक्रिया करा.",
                "जमिनीतील स्थिर फॉस्फरस विरघळवण्यासाठी पीएसबी (PSB) चा वापर करा."
            )
        ),
        FarmingGuideTip(
            id = 5,
            iconEmoji = "🐛",
            title = "एकात्मिक कीड व्यवस्थापन (IPM)",
            summary = "कीटकनाशकांचा अवाजवी वापर टाळून कामगंध सापळे व निंबोळी अर्काचा वापर करा.",
            detailedContent = "अति कीटकनाशकांमुळे मित्र कीटक मरतात. आयपीएम पद्धतीमुळे पर्यावरणाचे रक्षण होऊन कीड आटोक्यात राहते.",
            practicalSteps = listOf(
                "पांढरी माशी व मावा किडीसाठी एकरी १५ पिवळे चिकट सापळे लावा.",
                "बोंडअळीच्या निरीक्षणासाठी एकरी ५ कामगंध सापळे (फेरोमोन ट्रॅप) लावा.",
                "प्राथमिक संरक्षणासाठी १०,००० पीपीएम निंबोळी तेल (३ मिली/लिटर) फवारा.",
                "कीड आर्थिक नुकसान पातळीच्या वर गेल्यासच रासायनिक औषधे फवारा."
            )
        ),
        FarmingGuideTip(
            id = 6,
            iconEmoji = "🌱",
            title = "प्रमाणित बियाण्यांची निवड",
            summary = "भरघोस उत्पादनासाठी नेहमी शासकीय प्रमाणित व रोगप्रतिकारक बियाणे वापरा.",
            detailedContent = "प्रमाणित बियाण्यांची उगवण क्षमता ८५% पेक्षा जास्त असते व पिकांची वाढ एकसारखी होते.",
            practicalSteps = listOf(
                "अधिकृत कृषी केंद्रातून निळा टॅग असलेले प्रमाणित बियाणेच खरेदी करा.",
                "मिठाच्या पाण्याच्या द्रावणात बुडवून पोचट बियाणे वेगळे करा.",
                "बीजप्रक्रिया क्रम: बुरशीनाशक -> कीटकनाशक -> जिवाणू संवर्धक.",
                "बियाणे योग्य खोलीवर (३-५ सेमी) पेरा."
            )
        ),
        FarmingGuideTip(
            id = 7,
            iconEmoji = "⛅",
            title = "हवामान आधारित शेती कामे",
            summary = "फवारणी, सिंचन व काढणीची कामे हवामान अंदाज पाहूनच करा.",
            detailedContent = "पाऊस आणि वाऱ्याचा अंदाज घेऊन काम केल्यास औषधांचा अपव्यय टळतो व पीक सुरक्षित राहते.",
            practicalSteps = listOf(
                "४-६ तासांत पाऊस पडण्याची शक्यता असल्यास फवारणी करू नका.",
                "वारा जास्त असल्यास कीटकनाशक फवारणी टाळा.",
                "थंडीची लाट किंवा उष्णतेचा इशारा असल्यास हलके पाणी द्या.",
                "पीक पूर्ण पक्व झाल्यावरच सूर्यप्रकाशात काढणी करा."
            )
        ),
        FarmingGuideTip(
            id = 8,
            iconEmoji = "🌾",
            title = "आच्छादन (Mulching) आणि ओलावा जपणे",
            summary = "तण रोखण्यासाठी व ओलावा टिकवण्यासाठी पालापाचोळा किंवा प्लास्टिक मल्चिंग वापरा.",
            detailedContent = "मल्चिंगमुळे जमिनीतील बाष्पीभवन ३५-४०% कमी होते आणि मुळांचे तापमान योग्य राहते.",
            practicalSteps = listOf(
                "पिकांच्या ओळींमध्ये ५-७ सेमी जाडीचा उसाचा पाचट किंवा गवताचा थर द्या.",
                "भाजीपाला पिकांमध्ये २५-३० मायक्रॉन सिल्व्हर-ब्लॅक प्लास्टिक मल्च वापरा.",
                "मल्च खोडाला चिटकणार नाही याची काळजी घ्या.",
                "काढणीनंतर नैसर्गिक आच्छादन जमिनीत गाडून टाका."
            )
        )
    )

    private val bengaliTips = listOf(
        FarmingGuideTip(
            id = 1,
            iconEmoji = "🧪",
            title = "মৃত্তিকা স্বাস্থ্য পরীক্ষা (Soil Testing)",
            summary = "প্রতি রোপণ মৌসুমের আগে মাটি পরীক্ষা করে সুষম সার প্রয়োগ করুন।",
            detailedContent = "নিয়মিত মাটি পরীক্ষা করলে সারের অপচয় কমে এবং ফসল সঠিক পুষ্টি পায়।",
            practicalSteps = listOf(
                "জমির ৮-১০টি স্থান থেকে 'V' আকারে ১৫ সেমি গভীরতার মাটির নমুনা নিন।",
                "নমুনা ছায়ায় শুকিয়ে ৫০০ গ্রাম পরিষ্কার প্যাকেটে ভরুন।",
                "নিকটস্থ কৃষি বিজ্ঞান কেন্দ্রে (KVK) মাটি পরীক্ষা করান।",
                "সয়েল হেলথ কার্ডের পরামর্শ মেনে ইউরিয়া, ডিএপি ও পটাশ প্রয়োগ করুন।"
            )
        ),
        FarmingGuideTip(
            id = 2,
            iconEmoji = "🔄",
            title = "শস্য পর্যায়ক্রম (Crop Rotation)",
            summary = "ধানের পর ডাল জাতীয় ফসল চাষ করে মাটির প্রাকৃতিক উর্বরতা বৃদ্ধি করুন।",
            detailedContent = "একই ফসল বারবার চাষ না করে ডাল ও তৈলবীজ চাষ করলে রোগ-পোকা দমন হয়।",
            practicalSteps = listOf(
                "ধান বা ভুট্টার পর মুগ, ছোলা বা সরিষা চাষ করুন।",
                "গভীর মূল ও অগভীর মূলযুক্ত ফসল পর্যায়ক্রমে চাষ করুন।",
                "গ্রীষ্মে জমিতে ধৈঞ্চার সবুজ সার তৈরি করুন।",
                "একই জমিতে পরপর আলু বা টমেটো লাগাবেন না।"
            )
        ),
        FarmingGuideTip(
            id = 3,
            iconEmoji = "💧",
            title = "ড্রিপ সেচ ও পানি ব্যবস্থাপনা",
            summary = "ড্রিপ সেচ সরাসরি শিকড়ে পানি পৌঁছে দেয় এবং ৫০% পানি সাশ্রয় করে।",
            detailedContent = "ড্রিপ সেচে পানি ও দ্রবণীয় সার সরাসরি শিকড়ে যায়, ফলে আগাছা কম হয়।",
            practicalSteps = listOf(
                "ফসলের সারি অনুযায়ী ড্রিপ পাইপ স্থাপন করুন।",
                "ড্রিপের মাধ্যমে ফার্টিগেশন (পানিতে দ্রবণীয় সার) দিন।",
                "সকাল বা বিকেলে সেচ দিন যাতে বাষ্পীভবন কম হয়।",
                "নিয়মিত ড্রিপ পাইপ পরিষ্কার রাখুন।"
            )
        ),
        FarmingGuideTip(
            id = 4,
            iconEmoji = "🌿",
            title = "জৈব সার ও বায়ো-ফার্টিলাইজার",
            summary = "গোবর সার ও কেঁচো সার প্রয়োগ করে মাটির জৈব কার্বন বৃদ্ধি করুন।",
            detailedContent = "জৈব সার মাটির আর্দ্রতা ধরে রাখে এবং উপকারী ব্যাকটেরিয়ার বংশবৃদ্ধি করে।",
            practicalSteps = listOf(
                "জমি তৈরির সময় হেক্টর প্রতি ৫-৮ টন পচা গোবর সার দিন।",
                "শাকসবজিতে ২-৩ টন ভার্মিকম্পোস্ট প্রয়োগ করুন।",
                "বীজ বপনের পূর্বে রাইজোবিয়াম দিয়ে বীজ শোধন করুন।",
                "ফসফেট দ্রবীভূত করতে পিএসবি (PSB) ব্যবহার করুন।"
            )
        ),
        FarmingGuideTip(
            id = 5,
            iconEmoji = "🐛",
            title = "সমন্বিত বালাই দমন (IPM)",
            summary = "কীটনাশকের যথেচ্ছ ব্যবহার না করে ফেরোমন ট্র্যাপ ও নিম তেলের ব্যবহার করুন।",
            detailedContent = "আইপিএম পদ্ধতিতে প্রাকৃতিক উপায়ে ক্ষতিকর পোকা নিয়ন্ত্রণ করা হয়।",
            practicalSteps = listOf(
                "সাদা মাছি ও জাব পোকার জন্য একরে ১৫টি হলুদ আঠালো ফাঁদ পাতুন।",
                "লেদা পোকা দমনে ৫টি ফেরোমন ফাঁদ স্থাপন করুন।",
                "প্রাথমিক পোকা দমনে নিম তেল (৩ মিলি/লিটার) স্প্রে করুন।",
                "ক্ষতির মাত্রা বেশি হলেই রাসায়নিক কীটনাশক দিন।"
            )
        ),
        FarmingGuideTip(
            id = 6,
            iconEmoji = "🌱",
            title = "উন্নত মানের বীজ নির্বাচন",
            summary = "ভালো ফলনের জন্য সরকারি প্রত্যয়িত রোগমুক্ত বীজ ব্যবহার করুন।",
            detailedContent = "প্রত্যয়িত বীজের অঙ্কুরোদগম ক্ষমতা ৮৫% এর বেশি এবং ফলন নিশ্চিত।",
            practicalSteps = listOf(
                "সরকারি ট্যাগযুক্ত প্রত্যয়িত বীজ কিনুন।",
                "লবণ পানির দ্রবণ দিয়ে হালকা ও অপুষ্ট বীজ আলাদা করুন।",
                "ছত্রাকনাশক দিয়ে বীজ শোধন করে বপন করুন।",
                "সঠিক গভীরতায় (৩-৫ সেমি) বীজ বপন করুন।"
            )
        ),
        FarmingGuideTip(
            id = 7,
            iconEmoji = "⛅",
            title = "আবহাওয়া ভিত্তিক কৃষিকাজ",
            summary = "আবহাওয়ার পূর্বাভাস দেখে সেচ ও ওষুধ প্রয়োগের সময় নির্ধারণ করুন।",
            detailedContent = "বৃষ্টি ও ঝড়ের পূর্বাভাস জানলে সার ও কীটনাশকের ক্ষতি রোধ হয়।",
            practicalSteps = listOf(
                "বৃষ্টির সম্ভাবনা থাকলে জমিতে ওষুধ স্প্রে করবেন না।",
                "বাতাস বেশি থাকলে স্প্রে করা বন্ধ রাখুন।",
                "তীব্র গরম বা শৈত্যপ্রবাহে হালকা সেচ দিন।",
                "রোদ উঠলে তবেই ফসল কেটে শুকান।"
            )
        ),
        FarmingGuideTip(
            id = 8,
            iconEmoji = "🌾",
            title = "মালচিং ও আর্দ্রতা সংরক্ষণ",
            summary = "খড় বা প্লাস্টিক মালচিং দিয়ে মাটির আর্দ্রতা ধরে রাখুন ও আগাছা রোধ করুন।",
            detailedContent = "মালচিং করলে মাটির আর্দ্রতা দীর্ঘদিন থাকে এবং আগাছা জন্মাতে পারে না।",
            practicalSteps = listOf(
                "ফসলের সারিতে ৫-৭ সেমি পুরু ধানের খড় বিছিয়ে দিন।",
                "সবজি চাষে সিলভার-ব্ল্যাক প্লাস্টিক মালচ ব্যবহার করুন।",
                "মালচ গাছের কাণ্ডের সাথে যেন না লাগে খেয়াল রাখুন।",
                "ফসল তোলার পর খড় মাটিতে মিশিয়ে দিন।"
            )
        )
    )

    private val teluguTips = englishTips.mapIndexed { idx, tip ->
        hindiTips.getOrNull(idx) ?: tip
    }

    private val tamilTips = englishTips.mapIndexed { idx, tip ->
        hindiTips.getOrNull(idx) ?: tip
    }

    private val kannadaTips = englishTips.mapIndexed { idx, tip ->
        hindiTips.getOrNull(idx) ?: tip
    }

    private val malayalamTips = englishTips.mapIndexed { idx, tip ->
        hindiTips.getOrNull(idx) ?: tip
    }

    private val gujaratiTips = englishTips.mapIndexed { idx, tip ->
        hindiTips.getOrNull(idx) ?: tip
    }

    private val punjabiTips = englishTips.mapIndexed { idx, tip ->
        hindiTips.getOrNull(idx) ?: tip
    }

    private val odiaTips = englishTips.mapIndexed { idx, tip ->
        hindiTips.getOrNull(idx) ?: tip
    }
}
