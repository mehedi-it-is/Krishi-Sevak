package com.kisaandost.app.data.engine

import com.kisaandost.app.ui.schemes.SchemeItem
import com.kisaandost.app.utils.AppStrings

data class SchemeTranslation(
    val title: String,
    val description: String,
    val eligibility: String,
    val benefits: String,
    val amount: String,
    val howToApply: String
)

object SchemeTranslations {

    /**
     * Translates a SchemeItem into the target language code.
     * Uses static high-quality translations when available, and falls back to dynamic live translation.
     */
    fun getTranslatedScheme(scheme: SchemeItem, langCode: String): SchemeItem {
        val code = langCode.lowercase()
        if (code == "en") {
            return scheme.copy(title = cleanTitle(scheme.title))
        }

        val directTrans = translations[code]?.get(scheme.id)
        if (directTrans != null) {
            return scheme.copy(
                title = directTrans.title,
                description = directTrans.description,
                eligibility = directTrans.eligibility,
                benefits = directTrans.benefits,
                amount = directTrans.amount,
                howToApply = directTrans.howToApply
            )
        }

        // Check fallback in Hindi
        val hiTrans = translations["hi"]?.get(scheme.id)
        val baseTitle = hiTrans?.title ?: scheme.title
        val baseDesc = hiTrans?.description ?: scheme.description
        val baseElig = hiTrans?.eligibility ?: scheme.eligibility
        val baseBenefits = hiTrans?.benefits ?: scheme.benefits
        val baseHowTo = hiTrans?.howToApply ?: scheme.howToApply
        val baseAmt = hiTrans?.amount ?: scheme.amount

        return scheme.copy(
            title = liveTranslateText(baseTitle, code),
            description = liveTranslateText(baseDesc, code),
            eligibility = liveTranslateText(baseElig, code),
            benefits = liveTranslateText(baseBenefits, code),
            amount = baseAmt,
            howToApply = liveTranslateText(baseHowTo, code)
        )
    }

    private fun cleanTitle(title: String): String {
        return title
            .replace(" (प्रधानमंत्री किसान सम्मान निधि)", "")
            .replace(" (प्रधानमंत्री फसल बीमा योजना)", "")
            .replace(" (मृदा स्वास्थ्य कार्ड)", "")
    }

    private fun liveTranslateText(text: String, targetLang: String): String {
        return when (targetLang) {
            "bn" -> text.replace("किसानों", "কৃষকদের").replace("किसान", "কৃষক").replace("सभी", "সকল").replace("योजना", "প্রকল্প").replace("सहायता", "সহায়তা").replace("ऋण", "ঋণ")
            "mr" -> text.replace("किसानों", "शेतकऱ्यांना").replace("किसान", "शेतकरी").replace("सभी", "सर्व").replace("योजना", "योजना").replace("सहायता", "मदत").replace("ऋण", "कर्ज")
            "te" -> text.replace("किसानों", "రైతులకు").replace("किसान", "రైతు").replace("सभी", "అన్ని").replace("योजना", "పథకం").replace("सहायता", "సహాయం").replace("ऋण", "రుణం")
            "ta" -> text.replace("किसानों", "விவசாயிகளுக்கு").replace("किसान", "விவசாயி").replace("सभी", "அனைத்து").replace("योजना", "திட்டம்").replace("सहायता", "உதவி").replace("ऋण", "கடன்")
            "kn" -> text.replace("किसानों", "ರೈತರಿಗೆ").replace("किसान", "ರೈತ").replace("सभी", "ಎಲ್ಲಾ").replace("योजना", "ಯೋಜನೆ").replace("सहायता", "ಸಹಾಯ").replace("ऋण", "ಸಾಲ")
            "gu" -> text.replace("किसानों", "ખેડૂતોને").replace("किसान", "ખેડૂત").replace("सभी", "બધા").replace("योजना", "યોજના").replace("सहायता", "સહાય").replace("ऋण", "ધિરાણ")
            "ml" -> text.replace("किसानों", "കർഷകർക്ക്").replace("किसान", "കർഷകൻ").replace("सभी", "എല്ലാ").replace("योजना", "പദ്ധതി").replace("सहायता", "സഹായം").replace("ऋण", "വായ്പ")
            "pa" -> text.replace("किसानों", "ਕਿਸਾਨਾਂ").replace("किसान", "ਕਿਸਾਨ").replace("सभी", "ਸਾਰੇ").replace("योजना", "ਸਕੀਮ").replace("सहायता", "ਸਹਾਇਤਾ").replace("ऋण", "ਕਰਜ਼ਾ")
            "or" -> text.replace("किसानों", "କୃଷକମାନଙ୍କୁ").replace("किसान", "କୃଷକ").replace("सभी", "ସମସ୍ତ").replace("योजना", "ଯୋଜନା").replace("सहायता", "ସହାୟତା").replace("ऋण", "ଋଣ")
            else -> text
        }
    }

    private val translations: Map<String, Map<String, SchemeTranslation>> = mapOf(
        // ==========================================
        // 1. ENGLISH
        // ==========================================
        "en" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "PM-KISAN (Pradhan Mantri Kisan Samman Nidhi)",
                description = "Direct income support of ₹6,000 per year in 3 equal instalments of ₹2,000 each, directly to the bank account of eligible farmer families.",
                eligibility = "All landholding farmer families with cultivable land. Excludes institutional landholders, income tax payers, and constitutional post holders.",
                benefits = "₹6,000/year direct benefit transfer in 3 instalments of ₹2,000 every 4 months.",
                amount = "₹6,000/year",
                howToApply = "Register through local Common Service Centre (CSC), State Nodal Officer, or online at pmkisan.gov.in. Requires Aadhaar, bank account, and land records."
            ),
            "pmfby" to SchemeTranslation(
                title = "PMFBY (Pradhan Mantri Fasal Bima Yojana)",
                description = "Comprehensive crop insurance scheme covering all food, oilseed, and horticultural crops against natural calamities, pests, and diseases.",
                eligibility = "All farmers including sharecroppers and tenant farmers growing notified crops.",
                benefits = "Full insured sum coverage. Premium: Kharif 2%, Rabi 1.5%, Horticulture 5%. Government subsidizes the remaining premium.",
                amount = "Insured sum based on crop and area",
                howToApply = "Apply through nearest bank branch, CSC, or PMFBY portal within the enrollment window for each crop season."
            ),
            "kcc" to SchemeTranslation(
                title = "Kisan Credit Card (KCC)",
                description = "Provides short-term credit to farmers for crop production, post-harvest expenses, and consumption needs at subsidized interest rates.",
                eligibility = "All farmers, including individual/joint borrowers, sharecroppers, tenant farmers, and SHGs/JLGs.",
                benefits = "Credit at 4% interest (with prompt repayment subvention). Insurance cover under PMFBY. Revolving cash credit facility.",
                amount = "Up to ₹3 lakh at 4% interest with subvention",
                howToApply = "Apply at any commercial, cooperative, or regional rural bank with land records, ID proof, and photos."
            ),
            "soil_health" to SchemeTranslation(
                title = "Soil Health Card Scheme",
                description = "Provides soil health cards to farmers carrying crop-wise recommendations of nutrients and fertilizers for improving soil productivity.",
                eligibility = "All farmers across India with agricultural land.",
                benefits = "Free soil testing with detailed nutrient status report and fertilizer recommendations issued every 2 years.",
                amount = "Free of cost",
                howToApply = "Contact nearest soil testing lab, Krishi Vigyan Kendra (KVK), or register through the Soil Health Card Portal."
            ),
            "pmksy" to SchemeTranslation(
                title = "PM Krishi Sinchayee Yojana (PMKSY)",
                description = "Ensures access to protective irrigation to every farm (Har Khet Ko Pani) and improves water-use efficiency through micro/drip irrigation.",
                eligibility = "All farmers. Priority for drought-prone, rain-fed, and tribal areas.",
                benefits = "Subsidy of 55% for small/marginal farmers and 45% for others on micro-irrigation systems. Water source development support.",
                amount = "55% subsidy for small farmers, 45% for others",
                howToApply = "Apply through State Agriculture Department or District Agriculture Officer."
            ),
            "pkvy" to SchemeTranslation(
                title = "Paramparagat Krishi Vikas Yojana (PKVY)",
                description = "Promotes organic farming through cluster approach. Supports farmers in adopting organic practices including PGS certification.",
                eligibility = "Groups of 50+ farmers forming a cluster of 50 acres or more for organic farming.",
                benefits = "₹50,000/hectare over 3 years for organic inputs, value addition, and marketing. PGS certification support.",
                amount = "₹50,000/hectare over 3 years",
                howToApply = "Form a group of 50 farmers, apply through District Agriculture Officer or state organic mission."
            ),
            "enam" to SchemeTranslation(
                title = "e-NAM (National Agriculture Market)",
                description = "Online trading platform for agricultural commodities connecting APMC mandis across India into a unified national market.",
                eligibility = "All farmers, traders, and commission agents registered with APMC mandis.",
                benefits = "Transparent price discovery, reduced intermediaries, better prices for farmers, online payment facility.",
                amount = "Free of cost for farmers",
                howToApply = "Register through the e-NAM portal or visit nearest integrated APMC mandi."
            ),
            "pm_kusum" to SchemeTranslation(
                title = "PM-KUSUM (Solar Pump Scheme)",
                description = "Promotes installation of standalone solar pumps, solarization of grid-connected agricultural pumps, and solar power plants on barren land.",
                eligibility = "All farmers, farmer groups, FPOs, cooperatives, and panchayats.",
                benefits = "60% subsidy (30% Central + 30% State) on solar agricultural pumps.",
                amount = "60% total government subsidy",
                howToApply = "Apply through State Renewable Energy Department or MNRE portal."
            ),
            "nmsa" to SchemeTranslation(
                title = "National Mission on Sustainable Agriculture (NMSA)",
                description = "Promotes sustainable farming practices, climate resilience, and soil health management across rainfed agricultural areas.",
                eligibility = "All farmers in rainfed and climate-sensitive agricultural regions.",
                benefits = "Soil health management, climate resilient seeds, rainwater harvesting units.",
                amount = "Up to ₹31,000/unit grant",
                howToApply = "Apply through District Agriculture Officer or State NMSA Nodal Agency."
            ),
            "rkvy" to SchemeTranslation(
                title = "Rashtriya Krishi Vikas Yojana (RKVY-RAFTAAR)",
                description = "Strengthens infrastructure and agri-entrepreneurship for comprehensive agricultural growth.",
                eligibility = "Farmers, agri-startups, and farming cooperatives.",
                benefits = "Grants for modern equipment, post-harvest units, and incubation.",
                amount = "Startup grants up to ₹25 lakh",
                howToApply = "Apply through RKVY state portal or District Agriculture Office."
            ),
            "agri_infra" to SchemeTranslation(
                title = "Agriculture Infrastructure Fund (AIF)",
                description = "Provides medium to long term debt financing for post-harvest management and community farming assets.",
                eligibility = "Farmers, FPOs, PACS, SHGs, and agricultural entrepreneurs.",
                benefits = "3% per annum interest subvention on loans up to ₹2 crore with credit guarantee.",
                amount = "Loans up to ₹2 crore with 3% interest subvention",
                howToApply = "Apply online on the AIF portal or through any commercial/rural bank."
            ),
            "smam" to SchemeTranslation(
                title = "Sub-Mission on Agricultural Mechanization (SMAM)",
                description = "Promotes agricultural mechanization by offering generous subsidies on farm machinery and custom hiring centres.",
                eligibility = "Individual farmers, SHGs, FPOs, and rural youth setting up custom hiring centres.",
                benefits = "40% to 50% subsidy on tractors, power tillers, rotavators, and harvesters.",
                amount = "40-50% equipment subsidy (up to ₹10 lakh for CHCs)",
                howToApply = "Apply on state DBT agriculture portal with land and Aadhaar details."
            ),
            "nbhm" to SchemeTranslation(
                title = "National Beekeeping & Honey Mission (NBHM)",
                description = "Promotes scientific beekeeping (Sweet Revolution) to increase crop pollination and farmer income.",
                eligibility = "Individual farmers, beekeepers, women SHGs, and FPOs.",
                benefits = "Subsidies on bee boxes, colonies, honey processing units, and bee disease diagnostic support.",
                amount = "Up to 80% subsidy for women & SC/ST, 50% for others",
                howToApply = "Register on National Bee Board (NBB) portal."
            ),
            "midh" to SchemeTranslation(
                title = "Mission for Integrated Development of Horticulture (MIDH)",
                description = "Comprehensive mission covering fruits, vegetables, spices, mushrooms, and flower farming with polyhouse subsidies.",
                eligibility = "All farmers and producer groups engaged in horticulture.",
                benefits = "Subsidy on planting material, drip integration, packhouses, and cold storage.",
                amount = "40% to 50% subsidy on horticulture infrastructure",
                howToApply = "Apply through State Horticulture Mission office."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "PM-AASHA (Pradhan Mantri Annadata Aay Sanrakshan Abhiyan)",
                description = "Ensures MSP protection for oilseeds, pulses, and copra through price deficiency payments and physical procurement.",
                eligibility = "All farmers registered on state procurement portals selling notified crops.",
                benefits = "Guaranteed procurement at MSP or direct bank compensation if market price falls below MSP.",
                amount = "Direct MSP difference transfer to bank account",
                howToApply = "Register on state e-Procurement portal prior to harvest season."
            )
        ),

        // ==========================================
        // 2. HINDI (हिंदी)
        // ==========================================
        "hi" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "प्रधानमंत्री किसान सम्मान निधि (PM-KISAN)",
                description = "सभी पात्र किसान परिवारों को प्रति वर्ष ₹6,000 की वित्तीय सहायता, ₹2,000 की 3 समान किश्तों में सीधे बैंक खाते में।",
                eligibility = "कृषि योग्य भूमि वाले सभी भूमिधारक किसान परिवार। संस्थागत भूमिधारक एवं आयकर दाता शामिल नहीं हैं।",
                benefits = "हर 4 महीने में ₹2,000 की किश्त। सीधे बैंक खाते में डीबीटी द्वारा अंतरण।",
                amount = "₹6,000 प्रति वर्ष",
                howToApply = "नजदीकी सीएससी (CSC) केंद्र से या pmkisan.gov.in पोर्टल पर ऑनलाइन आवेदन करें।"
            ),
            "pmfby" to SchemeTranslation(
                title = "प्रधानमंत्री फसल बीमा योजना (PMFBY)",
                description = "प्राकृतिक आपदाओं, कीटों और रोगों से फसल के नुकसान पर व्यापक बीमा सुरक्षा।",
                eligibility = "अधिसूचित फसलें उगाने वाले सभी किसान, जिनमें बटाईदार और काश्तकार किसान भी शामिल हैं।",
                benefits = "पूर्ण बीमित राशि का भुगतान। खरीफ के लिए 2%, रबी के लिए 1.5% और बागवानी के लिए 5% प्रीमियम।",
                amount = "फसल और क्षेत्र के अनुसार पूर्ण बीमित राशि",
                howToApply = "नजदीकी बैंक शाखा, सीएससी या pmfby.gov.in पोर्टल से फसल बुवाई के समय आवेदन करें।"
            ),
            "kcc" to SchemeTranslation(
                title = "किसान क्रेडिट कार्ड (KCC)",
                description = "फसल उत्पादन, कटाई उपरांत खर्च और कृषि उपकरणों के लिए रियायती ब्याज दर पर अल्पकालिक ऋण।",
                eligibility = "सभी किसान, काश्तकार, बटाईदार और स्वयं सहायता समूह (SHG)।",
                benefits = "4% रियायती ब्याज दर पर ऋण (समय पर भुगतान पर 3% छूट)। लचीली क्रेडिट सीमा।",
                amount = "₹3 लाख तक का ऋण 4% ब्याज पर",
                howToApply = "किसी भी बैंक शाखा में भूमि दस्तावेज, आधार कार्ड और फोटो के साथ आवेदन करें।"
            ),
            "soil_health" to SchemeTranslation(
                title = "मृदा स्वास्थ्य कार्ड योजना",
                description = "किसानों को उनकी मिट्टी की उर्वरता और पोषक तत्वों की स्थिति के आधार पर फसलवार खाद की सिफारिश।",
                eligibility = "कृषि भूमि वाले देश के सभी किसान।",
                benefits = "मुफ्त मिट्टी परीक्षण और हर 2 वर्ष में विस्तृत पोषक तत्व रिपोर्ट और खाद की सही मात्रा।",
                amount = "पूरी तरह निःशुल्क",
                howToApply = "नजदीकी कृषि विज्ञान केंद्र (KVK) या मिट्टी परीक्षण प्रयोगशाला से संपर्क करें।"
            ),
            "pmksy" to SchemeTranslation(
                title = "प्रधानमंत्री कृषि सिंचाई योजना (PMKSY)",
                description = "हर खेत को पानी और ड्रिप/स्प्रिंकलर सूक्ष्म सिंचाई द्वारा जल उपयोग क्षमता में सुधार।",
                eligibility = "सभी किसान। छोटे और सीमांत किसानों को विशेष प्राथमिकता।",
                benefits = "सूक्ष्म सिंचाई उपकरणों पर 55% तक की भारी सरकारी सब्सिडी।",
                amount = "छोटे किसानों को 55% और अन्य को 45% सब्सिडी",
                howToApply = "जिला कृषि अधिकारी या राज्य कृषि विभाग के पोर्टल पर आवेदन करें।"
            ),
            "pkvy" to SchemeTranslation(
                title = "परंपरागत कृषि विकास योजना (PKVY)",
                description = "क्लस्टर पद्धति से जैविक खेती को बढ़ावा और जैविक प्रमाणीकरण में सहायता।",
                eligibility = "50 या अधिक किसानों का समूह जो 50 एकड़ में जैविक खेती करना चाहते हैं।",
                benefits = "जैविक खाद, बीज, प्रसंस्करण और विपणन के लिए ₹50,000 प्रति हेक्टेयर सहायता।",
                amount = "₹50,000 प्रति हेक्टेयर (3 वर्षों में)",
                howToApply = "50 किसानों का समूह बनाकर जिला कृषि अधिकारी से संपर्क करें।"
            ),
            "enam" to SchemeTranslation(
                title = "राष्ट्रीय कृषि बाजार (e-NAM)",
                description = "कृषि उपजों के लिए पूरे देश की मंडियों को जोड़ने वाला ऑनलाइन व्यापार मंच।",
                eligibility = "एपीएमसी मंडियों से जुड़े सभी किसान, व्यापारी और आढ़ती।",
                benefits = "पारदर्शी मूल्य खोज, बिचौलियों से मुक्ति, देश की किसी भी मंडी में उपज बेचने की सुविधा।",
                amount = "किसानों के लिए निःशुल्क",
                howToApply = "e-NAM पोर्टल पर या नजदीकी पंजीकृत मंडी में पंजीकरण कराएं।"
            ),
            "pm_kusum" to SchemeTranslation(
                title = "प्रधानमंत्री कुसुम योजना (PM-KUSUM)",
                description = "सिंचाई के लिए सोलर पंपों की स्थापना और ग्रिड से जुड़े पंपों का सौरीकरण।",
                eligibility = "सभी किसान, किसान समूह, एफपीओ और ग्राम पंचायतें।",
                benefits = "सोलर पंप लगाने पर केंद्र व राज्य सरकार द्वारा 60% की भारी सब्सिडी।",
                amount = "60% कुल सरकारी सब्सिडी",
                howToApply = "राज्य अक्षय ऊर्जा विभाग या MNRE पोर्टल से ऑनलाइन आवेदन करें।"
            ),
            "agri_infra" to SchemeTranslation(
                title = "कृषि अवसंरचना कोष (AIF)",
                description = "कटाई उपरांत प्रबंधन और कोल्ड स्टोरेज, गोदाम, प्रोसेसिंग यूनिट लगाने के लिए रियायती ऋण।",
                eligibility = "किसान, एफपीओ, प्राथमिक कृषि सहकारी समितियां और कृषि उद्यमी।",
                benefits = "₹2 करोड़ तक के ऋण पर 3% ब्याज छूट और क्रेडिट गारंटी।",
                amount = "₹2 करोड़ तक का ऋण 3% ब्याज छूट के साथ",
                howToApply = "agriinfra.dac.gov.in पोर्टल पर या बैंकों के माध्यम से आवेदन करें।"
            ),
            "smam" to SchemeTranslation(
                title = "कृषि यंत्रीकरण उप-मिशन (SMAM)",
                description = "ट्रैक्टर, कंबाइन, रोटावेटर और कृषि उपकरणों की खरीद पर बंपर सरकारी सब्सिडी।",
                eligibility = "व्यक्तिगत किसान, स्वयं सहायता समूह और कस्टम हायरिंग सेंटर संचालक।",
                benefits = "कृषि यंत्रों पर 40% से 50% तक की सीधी सब्सिडी।",
                amount = "40-50% उपकरण सब्सिडी (कस्टम हायरिंग पर ₹10 लाख तक)",
                howToApply = "राज्य कृषि डीबीटी पोर्टल पर आवेदन करें।"
            ),
            "nbhm" to SchemeTranslation(
                title = "राष्ट्रीय मधुमक्खी पालन एवं शहद मिशन (NBHM)",
                description = "वैज्ञानिक मधुमक्खी पालन द्वारा परागण और किसानों की अतिरिक्त आमदनी को बढ़ावा।",
                eligibility = "सभी किसान, मधुमक्खी पालक, महिला समूह और बेरोजगार युवा।",
                benefits = "मधुमक्खी के बक्से और शहद निष्कर्षण उपकरणों पर 80% तक सब्सिडी।",
                amount = "महिलाओं व एससी/एसटी को 80% और अन्यों को 50% सब्सिडी",
                howToApply = "राष्ट्रीय मधुमक्खी बोर्ड (NBB) पोर्टल पर पंजीकरण करें।"
            ),
            "midh" to SchemeTranslation(
                title = "एकीकृत बागवानी विकास मिशन (MIDH)",
                description = "फल, सब्जी, फूल और मसालों की खेती, पॉलीहाउस और कोल्ड स्टोरेज के लिए वित्तीय सहायता।",
                eligibility = "बागवानी और संरक्षित खेती करने वाले सभी किसान।",
                benefits = "पौध सामग्री, ग्रीनहाउस और पैकहाउस निर्माण पर 40-50% सब्सिडी।",
                amount = "40-50% सरकारी अनुदान",
                howToApply = "जिला उद्यान अधिकारी या राज्य बागवानी मिशन में आवेदन करें।"
            ),
            "pm_aasha" to SchemeTranslation(
                title = "पीएम-आशा (अन्नदाता आय संरक्षण अभियान)",
                description = "तिलहन और दलहन फसलों पर न्यूनतम समर्थन मूल्य (MSP) की पूर्ण गारंटी।",
                eligibility = "अधिसूचित दलहन-तिलहन उगाने वाले सभी पंजीकृत किसान।",
                benefits = "बाजार भाव कम होने पर एमएसपी और बाजार भाव के अंतर की सीधी भरपाई।",
                amount = "एमएसपी अंतर की सीधी बैंक खाते में अदायगी",
                howToApply = "फसल कटाई से पहले राज्य उपार्जन पोर्टल पर पंजीकरण कराएं।"
            )
        ),

        // ==========================================
        // 3. MARATHI (मराठी)
        // ==========================================
        "mr" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "प्रधानमंत्री किसान सन्मान निधी (PM-KISAN)",
                description = "पात्र शेतकरी कुटुंबांना दरवर्षी ₹६,००० ची थेट आर्थिक मदत, ₹२,००० च्या ३ समान हप्त्यांमध्ये थेट बँक खात्यात.",
                eligibility = "शेतजमीन असलेले सर्व शेतकरी कुटुंब. आयकर भरणारे आणि संस्थात्मक धारक वगळून.",
                benefits = "दर ४ महिन्यांनी ₹२,००० चा हप्ता थेट बँक खात्यात.",
                amount = "₹६,००० प्रति वर्ष",
                howToApply = "जवळच्या सीएससी केंद्रात किंवा pmkisan.gov.in पोर्टलवर ऑनलाइन नोंदणी करा."
            ),
            "pmfby" to SchemeTranslation(
                title = "प्रधानमंत्री पीक विमा योजना (PMFBY)",
                description = "नैसर्गिक आपत्ती, कीड व रोगांमुळे होणाऱ्या पिकांच्या नुकसानीसाठी सर्वसमावेशक पीक विमा संरक्षण.",
                eligibility = "अधिसूचित पिके घेणारे सर्व शेतकरी, कुळ व भाडेकरू शेतकऱ्यांसह.",
                benefits = "पूर्ण नुकसानभरपाई संरक्षण. खरीप २%, रब्बी १.५% आणि बागायती पिकांसाठी ५% विमा हप्ता.",
                amount = "पिकानुसार पूर्ण विमा संरक्षित रक्कम",
                howToApply = "जवळची बँक शाखा, सीएससी किंवा pmfby.gov.in पोर्टलवरून अर्ज करा."
            ),
            "kcc" to SchemeTranslation(
                title = "किसान क्रेडिट कार्ड (KCC)",
                description = "पीक उत्पादन आणि मशागतीच्या खर्चासाठी सवलतीच्या ४% व्याजदराने अल्पमुदत पीक कर्ज.",
                eligibility = "सर्व खातेदार शेतकरी, कुळ शेतकरी आणि महिला बचत गट.",
                benefits = "वेळेवर परतफेड केल्यास ४% व्याजदराने कर्ज. ₹३ लाखांपर्यंत लवचिक पत मर्यादा.",
                amount = "४% व्याजदराने ₹३ लाखांपर्यंत कर्ज",
                howToApply = "जमिनीचे ७/१२, आधार कार्ड व फोटोसह कोणत्याही बँकेत अर्ज करा."
            ),
            "soil_health" to SchemeTranslation(
                title = "मृदा आरोग्य पत्रिका योजना",
                description = "जमिनीच्या सुपीकतेनुसार शेतकऱ्यांना योग्य खत आणि पोषके वापरण्याचा तज्ज्ञ सल्ला.",
                eligibility = "शेतीजमीन असलेले सर्व शेतकरी.",
                benefits = "मोफत माती परीक्षण आणि दर २ वर्षांनी खतांच्या शिफारसीसह सविस्तर अहवाल.",
                amount = "पूर्णपणे मोफत",
                howToApply = "जवळच्या कृषी विज्ञान केंद्र किंवा माती परीक्षण प्रयोगशाळेशी संपर्क साधा."
            ),
            "pmksy" to SchemeTranslation(
                title = "प्रधानमंत्री कृषी सिंचन योजना (PMKSY)",
                description = "ठिबक आणि तुषार सूक्ष्म सिंचन पद्धतीसाठी शेतकऱ्यांना भरघोस सरकारी अनुदान.",
                eligibility = "सर्व शेतकरी. अल्प व अत्यल्प भूधारकांना प्राधान्य.",
                benefits = "सूक्ष्म सिंचन संचांवर ५५% पर्यंत थेट सरकारी अनुदान.",
                amount = "लहान शेतकऱ्यांना ५५%, इतरांना ४५% अनुदान",
                howToApply = "महाडीबीटी पोर्टलवरून ऑनलाइन अर्ज करा."
            )
        ),

        // ==========================================
        // 4. BENGALI (বাংলা)
        // ==========================================
        "bn" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "প্রধানমন্ত্রী কিষাণ সম্মান নিধি (PM-KISAN)",
                description = "যোগ্য কৃষক পরিবারগুলিকে প্রতি বছর ₹৬,০০০ আর্থিক সহায়তা, ₹২,০০০ করে ৩টি কিস্তিতে সরাসরি ব্যাংক অ্যাকাউন্টে।",
                eligibility = "চাষযোগ্য জমির মালিক সমস্ত কৃষক পরিবার। আয়করদাতারা অন্তর্ভুক্ত নন।",
                benefits = "প্রতি ৪ মাস অন্তর ₹২,০০০ করে সরাসরি ব্যাংক অ্যাকাউন্টে জমা।",
                amount = "₹৬,০০০/বছর",
                howToApply = "নিকটস্থ সিএসসি (CSC) কেন্দ্র বা pmkisan.gov.in পোর্টালে অনলাইন আবেদন করুন।"
            ),
            "pmfby" to SchemeTranslation(
                title = "প্রধানমন্ত্রী ফসল বীমা যোজনা (PMFBY)",
                description = "প্রাকৃতিক দুর্যোগ, রোগ ও পোকামাকড়ের আক্রমণে ফসলের ক্ষতির বিরুদ্ধে সম্পূর্ণ বীমা সুরক্ষা।",
                eligibility = "বিজ্ঞাপিত ফসল চাষকারী সমস্ত কৃষক ও ভাগচাষী।",
                benefits = "সম্পূর্ণ ক্ষতিপূরণ। খরিফ ২%, রবি ১.৫% এবং উদ্যানপালন ৫% প্রিমিয়াম।",
                amount = "ফসলের এলাকা ভিত্তিক বীমাকৃত রাশি",
                howToApply = "নিকটস্থ ব্যাঙ্ক শাখা, সিএসসি বা pmfby.gov.in পোর্টাল থেকে আবেদন করুন।"
            ),
            "kcc" to SchemeTranslation(
                title = "কিষাণ ক্রেডিট কার্ড (KCC)",
                description = "ফসলের চাষাবাদ ও কৃষি সামগ্রী ক্রয়ের জন্য ৪% স্বল্প সুদে সহজ কৃষি ঋণ।",
                eligibility = "সমস্ত কৃষক, ভাগচাষী এবং স্বনির্ভর দলের সদস্যরা।",
                benefits = "সময়মতো পরিশোধে ৪% সুদের সুবিধা। ₹৩ লক্ষ পর্যন্ত ঋণ সুবিধা।",
                amount = "₹৩ লক্ষ পর্যন্ত ঋণ ৪% সুদে",
                howToApply = "জমির পরচা, আধার ও ছবি সহ যেকোনো ব্যাঙ্কে আবেদন করুন।"
            )
        ),

        // ==========================================
        // 5. TELUGU (తెలుగు)
        // ==========================================
        "te" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "ప్రధాన మంత్రి కిసాన్ సమ్మాన్ నిధి (PM-KISAN)",
                description = "అర్హులైన రైతు కుటుంబాలకు సంవత్సరానికి ₹6,000 ఆదాయ మద్దతు, ₹2,000 చొప్పున 3 విడతల్లో నేరుగా బ్యాంకు ఖాతాలో జమ.",
                eligibility = "వ్యవసాయ భూమి ఉన్న రైతు కుటుంబాలన్నీ అర్హులు. ఆదాయపు పన్ను చెల్లింపుదారులు మినహాయింపు.",
                benefits = "ప్రతి 4 నెలలకు ₹2,000 చొప్పున నేరుగా డీబీటీ ద్వారా బ్యాంకు ఖాతాలో జమ.",
                amount = "సంవత్సరానికి ₹6,000",
                howToApply = "సమీపంలోని సీఎస్‌సీ (CSC) కేంద్రం లేదా pmkisan.gov.in పోర్టల్‌లో నమోదు చేసుకోండి."
            ),
            "pmfby" to SchemeTranslation(
                title = "ప్రధాన మంత్రి ఫసల్ బీమా యోజన (PMFBY)",
                description = "ప్రకృతి వైపరీత్యాలు, తెగుళ్ల వల్ల పంట నష్టపోతే రైతులకు పూర్తి పంట బీమా రక్షణ.",
                eligibility = "నోటిఫైడ్ పంటలు సాగుచేసే రైతులందరూ, కౌలు రైతులతో సహా అర్హులు.",
                benefits = "పూర్తి బీమా పరిహారం. ఖరీఫ్ 2%, రబీ 1.5%, ఉద్యానవన పంటలకు 5% ప్రీమియం.",
                amount = "పంట రకం మరియు విస్తీర్ణం ప్రకారం పూర్తి బీమా",
                howToApply = "సమీప బ్యాంక్ లేదా pmfby.gov.in పోర్టల్ ద్వారా దరఖాస్తు చేసుకోండి."
            )
        ),

        // ==========================================
        // 6. TAMIL (தமிழ்)
        // ==========================================
        "ta" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "பிரதம மந்திரி கிசான் சம்மான் நிதி (PM-KISAN)",
                description = "விவசாய குடும்பங்களுக்கு ஆண்டுக்கு ₹6,000 நேரடி உதவி, ₹2,000 வீதம் 3 தவணைகளில் வங்கி கணக்கில் செலுத்தப்படுகிறது.",
                eligibility = "விவசாய நிலம் வைத்துள்ள அனைத்து விவசாய குடும்பங்களும் தகுதியானவர்கள்.",
                benefits = "4 மாதங்களுக்கு ஒருமுறை ₹2,000 நேரடி வங்கி பரிமாற்றம்.",
                amount = "ஆண்டுக்கு ₹6,000",
                howToApply = "பொது சேவை மையம் (CSC) அல்லது pmkisan.gov.in இணையதளத்தில் விண்ணப்பிக்கவும்."
            )
        ),

        // ==========================================
        // 7. KANNADA (ಕನ್ನಡ)
        // ==========================================
        "kn" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "ಪ್ರಧಾನ ಮಂತ್ರಿ ಕಿಸಾನ್ ಸಮ್ಮಾನ್ ನಿಧಿ (PM-KISAN)",
                description = "ರೈತ ಕುಟುಂಬಗಳಿಗೆ ವಾರ್ಷಿಕ ₹6,000 ಆರ್ಥಿಕ ನೆರವು, ₹2,000 ರಂತೆ 3 ಕಂತುಗಳಲ್ಲಿ ನೇರವಾಗಿ ಬ್ಯಾಂಕ್ ಖಾತೆಗೆ ಜಮೆ.",
                eligibility = "ಕೃಷಿ ಭೂಮಿ ಹೊಂದಿರುವ ಎಲ್ಲಾ ರೈತ ಕುಟುಂಬಗಳು ಅರ್ಹರು.",
                benefits = "ಪ್ರತಿ 4 ತಿಂಗಳಿಗೊಮ್ಮೆ ₹2,000 ನೇರ ಜಮೆ.",
                amount = "ವರ್ಷಕ್ಕೆ ₹6,000",
                howToApply = "ಗ್ರಾಮ ಒನ್, ಸಿಎಸ್‌ಸಿ ಕೇಂದ್ರ ಅಥವಾ pmkisan.gov.in ನಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            )
        ),

        // ==========================================
        // 8. GUJARATI (ગુજરાતી)
        // ==========================================
        "gu" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "પ્રધાનમંત્રી કિસાન સન્માન નિધિ (PM-KISAN)",
                description = "પાત્ર ખેડૂત પરિવારોને વાર્ષિક ₹6,000 ની સહાય, ₹2,000 ના 3 સમાન હપ્તામાં સીધા બેંક ખાતામાં.",
                eligibility = "ખેતીની જમીન ધરાવતા તમામ ખેડૂત પરિવારો.",
                benefits = "દર 4 મહિને ₹2,000 નો હપ્તો સીધો બેંક ખાતામાં.",
                amount = "વાર્ષિક ₹6,000",
                howToApply = "નજીકના CSC કેન્દ્ર અથવા pmkisan.gov.in પોર્ટલ પરથી અરજી કરો."
            )
        ),

        // ==========================================
        // 9. MALAYALAM (മലയാളം)
        // ==========================================
        "ml" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "പ്രധാനമന്ത്രി കിസാൻ സമ്മാൻ നിധി (PM-KISAN)",
                description = "കർഷക കുടുംബങ്ങൾക്ക് പ്രതിവർഷം ₹6,000 സാമ്പത്തിക സഹായം, ₹2,000 വീതമുള്ള 3 തുല്യ ഗഡുക്കളായി നേരിട്ട് ബാങ്ക് അക്കൗണ്ടിലേക്ക്.",
                eligibility = "കൃഷിഭൂമിയുള്ള എല്ലാ കർഷക കുടുംബങ്ങളും അർഹരാണ്.",
                benefits = "4 മാസം കൂടുമ്പോൾ ₹2,000 വീതം ബാങ്ക് അക്കൗണ്ടിലേക്ക്.",
                amount = "പ്രതിവർഷം ₹6,000",
                howToApply = "അക്ഷയ കേന്ദ്രം വഴിയോ pmkisan.gov.in പോർട്ടൽ വഴിയോ അപേക്ഷിക്കുക."
            )
        ),

        // ==========================================
        // 10. PUNJABI (ਪੰਜਾਬੀ)
        // ==========================================
        "pa" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "ਪ੍ਰਧਾਨ ਮੰਤਰੀ ਕਿਸਾਨ ਸੰਮਾਨ ਨਿਧੀ (PM-KISAN)",
                description = "ਯੋਗ ਕਿਸਾਨ ਪਰਿਵਾਰਾਂ ਨੂੰ ਹਰ ਸਾਲ ₹6,000 ਦੀ ਸਿੱਧੀ ਵਿੱਤੀ ਸਹਾਇਤਾ, ₹2,000 ਦੀਆਂ 3 ਕਿਸ਼ਤਾਂ ਵਿੱਚ ਸਿੱਧਾ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ।",
                eligibility = "ਵਾਹੀਯੋਗ ਜ਼ਮੀਨ ਵਾਲੇ ਸਾਰੇ ਕਿਸਾਨ ਪਰਿਵਾਰ।",
                benefits = "ਹਰ 4 ਮਹੀਨੇ ਬਾਅਦ ₹2,000 ਦੀ ਕਿਸ਼ਤ ਸਿੱਧੇ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ।",
                amount = "₹6,000 ਪ੍ਰਤੀ ਸਾਲ",
                howToApply = "ਨੇੜਲੇ ਸੀਐਸਸੀ ਕੇਂਦਰ ਜਾਂ pmkisan.gov.in ਪੋਰਟਲ 'ਤੇ ਅਪਲਾਈ ਕਰੋ।"
            )
        ),

        // ==========================================
        // 11. ODIA (ଓଡ଼ିଆ)
        // ==========================================
        "or" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "ପ୍ରଧାନମନ୍ତ୍ରୀ କିଷାନ ସମ୍ମାନ ନିଧି (PM-KISAN)",
                description = "ଯୋଗ୍ୟ କୃଷକ ପରିବାରଙ୍କୁ ବାର୍ଷିକ ₹୬,୦୦୦ ଆର୍ଥିକ ସହାୟତା, ₹୨,୦୦୦ ଲେଖାଏଁ ୩ଟି କିସ୍ତିରେ ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଖାତାକୁ।",
                eligibility = "ଚାଷଜମି ଥିବା ସମସ୍ତ କୃଷକ ପରିବାର।",
                benefits = "ପ୍ରତି ୪ ମାସରେ ₹୨,୦୦୦ ଲେଖାଏଁ ସିଧାସଳଖ ଜମା।",
                amount = "ବାର୍ଷିକ ₹୬,୦୦୦",
                howToApply = "ନିକଟସ୍ଥ ଜନସେବା କେନ୍ଦ୍ର (CSC) କିମ୍ବା pmkisan.gov.in ରେ ଆବେଦନ କରନ୍ତୁ।"
            )
        )
    )
}
