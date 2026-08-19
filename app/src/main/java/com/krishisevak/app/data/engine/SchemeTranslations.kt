package com.krishisevak.app.data.engine

import com.krishisevak.app.ui.schemes.SchemeItem

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
     * Guaranteed to provide authentic 11-language content for all 31 national & state schemes.
     */
    fun getTranslatedScheme(scheme: SchemeItem, langCode: String): SchemeItem {
        val code = langCode.lowercase()
        val trans = translations[code]?.get(scheme.id) 
            ?: translations["en"]?.get(scheme.id)

        if (trans != null) {
            return scheme.copy(
                title = trans.title,
                description = trans.description,
                eligibility = trans.eligibility,
                benefits = trans.benefits,
                amount = trans.amount,
                howToApply = trans.howToApply
            )
        }

        return scheme
    }

    fun getLocalizedStateName(state: String, langCode: String): String {
        return stateNamesMap[langCode.lowercase()]?.get(state) ?: state
    }

    private val stateNamesMap = mapOf(
        "hi" to mapOf(
            "Maharashtra" to "महाराष्ट्र",
            "Punjab" to "पंजाब",
            "Uttar Pradesh" to "उत्तर प्रदेश",
            "Madhya Pradesh" to "मध्य प्रदेश",
            "Telangana" to "तेलंगाना",
            "Tamil Nadu" to "तमिलनाडु",
            "Karnataka" to "कर्नाटक",
            "Gujarat" to "गुजरात",
            "Rajasthan" to "राजस्थान",
            "Bihar" to "बिहार",
            "West Bengal" to "पश्चिम बंगाल",
            "Andhra Pradesh" to "आंध्र प्रदेश",
            "Haryana" to "हरियाणा",
            "Odisha" to "ओडिशा",
            "Kerala" to "केरल"
        ),
        "bn" to mapOf(
            "Maharashtra" to "মহারাষ্ট্র",
            "Punjab" to "পাঞ্জাব",
            "Uttar Pradesh" to "উত্তরপ্রদেশ",
            "Madhya Pradesh" to "মধ্যপ্রদেশ",
            "Telangana" to "তেলেঙ্গানা",
            "Tamil Nadu" to "তামিলনাড়ু",
            "Karnataka" to "কর্ণাটক",
            "Gujarat" to "গুজরাট",
            "Rajasthan" to "রাজস্থান",
            "Bihar" to "বিহার",
            "West Bengal" to "পশ্চিমবঙ্গ",
            "Andhra Pradesh" to "অন্ধ্রপ্রদেশ",
            "Haryana" to "হরিয়ানা",
            "Odisha" to "ওড়িশা",
            "Kerala" to "কেরালা"
        ),
        "mr" to mapOf(
            "Maharashtra" to "महाराष्ट्र",
            "Punjab" to "पंजाब",
            "Uttar Pradesh" to "उत्तर प्रदेश",
            "Madhya Pradesh" to "मध्य प्रदेश",
            "Telangana" to "तेलंगणा",
            "Tamil Nadu" to "तमिळनाडू",
            "Karnataka" to "कर्नाटक",
            "Gujarat" to "गुजरात",
            "Rajasthan" to "राजस्थान",
            "Bihar" to "बिहार",
            "West Bengal" to "पश्चिम बंगाल",
            "Andhra Pradesh" to "आंध्र प्रदेश",
            "Haryana" to "हरियाणा",
            "Odisha" to "ओडिशा",
            "Kerala" to "केरळ"
        ),
        "te" to mapOf(
            "Maharashtra" to "మహారాష్ట్ర",
            "Punjab" to "పంజాబ్",
            "Uttar Pradesh" to "ఉత్తర ప్రదేశ్",
            "Madhya Pradesh" to "మధ్య ప్రదేశ్",
            "Telangana" to "తెలంగాణ",
            "Tamil Nadu" to "తమిళనాడు",
            "Karnataka" to "కర్ణాటక",
            "Gujarat" to "గుజరాత్",
            "Rajasthan" to "రాజస్థాన్",
            "Bihar" to "బీహార్",
            "West Bengal" to "పశ్చిమ బెంగాల్",
            "Andhra Pradesh" to "ఆంధ్రప్రదేశ్",
            "Haryana" to "హర్యానా",
            "Odisha" to "ఒడిశా",
            "Kerala" to "కేరళ"
        ),
        "ta" to mapOf(
            "Maharashtra" to "மகாராஷ்டிரா",
            "Punjab" to "பஞ்சாப்",
            "Uttar Pradesh" to "உத்தரப் பிரதேசம்",
            "Madhya Pradesh" to "மத்தியப் பிரதேசம்",
            "Telangana" to "தெலுங்கானா",
            "Tamil Nadu" to "தமிழ்நாடு",
            "Karnataka" to "கர்நாடகா",
            "Gujarat" to "குஜராத்",
            "Rajasthan" to "ராஜஸ்தான்",
            "Bihar" to "பீகார்",
            "West Bengal" to "மேற்கு வங்கம்",
            "Andhra Pradesh" to "ஆந்திரப் பிரதேசம்",
            "Haryana" to "ஹரியானா",
            "Odisha" to "ஒடிசா",
            "Kerala" to "கேரளா"
        ),
        "kn" to mapOf(
            "Maharashtra" to "ಮಹಾರಾಷ್ಟ್ರ",
            "Punjab" to "ಪಂಜಾಬ್",
            "Uttar Pradesh" to "ಉತ್ತರ ಪ್ರದೇಶ",
            "Madhya Pradesh" to "ಮಧ್ಯ ಪ್ರದೇಶ",
            "Telangana" to "ತೆಲಂಗಾಣ",
            "Tamil Nadu" to "ತಮಿಳುನಾಡು",
            "Karnataka" to "ಕರ್ನಾಟಕ",
            "Gujarat" to "ಗುಜರಾತ್",
            "Rajasthan" to "ರಾಜಸ್ಥಾನ",
            "Bihar" to "ಬಿಹಾರ",
            "West Bengal" to "ಪಶ್ಚಿಮ ಬಂಗಾಳ",
            "Andhra Pradesh" to "ಆಂಧ್ರ ಪ್ರದೇಶ",
            "Haryana" to "ಹರಿಯಾಣ",
            "Odisha" to "ಒಡಿಶಾ",
            "Kerala" to "ಕೇರಳ"
        ),
        "gu" to mapOf(
            "Maharashtra" to "મહારાષ્ટ્ર",
            "Punjab" to "પંજાબ",
            "Uttar Pradesh" to "ઉત્તર પ્રદેશ",
            "Madhya Pradesh" to "મધ્ય પ્રદેશ",
            "Telangana" to "તેલંગાણા",
            "Tamil Nadu" to "તમિલનાડુ",
            "Karnataka" to "કર્ણાટક",
            "Gujarat" to "ગુજરાત",
            "Rajasthan" to "રાજસ્થાન",
            "Bihar" to "બિહાર",
            "West Bengal" to "પશ્ચિમ બંગાળ",
            "Andhra Pradesh" to "આંધ્ર પ્રદેશ",
            "Haryana" to "હરિયાણા",
            "Odisha" to "ઓડિશા",
            "Kerala" to "કેરળ"
        ),
        "pa" to mapOf(
            "Maharashtra" to "ਮਹਾਰਾਸ਼ਟਰ",
            "Punjab" to "ਪੰਜਾਬ",
            "Uttar Pradesh" to "ਉੱਤਰ ਪ੍ਰਦੇਸ਼",
            "Madhya Pradesh" to "ਮੱਧ ਪ੍ਰਦੇਸ਼",
            "Telangana" to "ਤੇਲੰਗਾਨਾ",
            "Tamil Nadu" to "ਤਾਮਿਲਨਾਡੂ",
            "Karnataka" to "ਕਰਨਾਟਕ",
            "Gujarat" to "ਗੁਜਰਾਤ",
            "Rajasthan" to "ਰਾਜਸਥਾਨ",
            "Bihar" to "ਬਿਹਾਰ",
            "West Bengal" to "ਪੱਛਮੀ ਬੰਗਾਲ",
            "Andhra Pradesh" to "ਆਂਧਰਾ ਪ੍ਰਦੇਸ਼",
            "Haryana" to "ਹਰਿਆਣਾ",
            "Odisha" to "ਓਡੀਸ਼ਾ",
            "Kerala" to "ਕੇਰਲ"
        ),
        "ml" to mapOf(
            "Maharashtra" to "മഹാരാഷ്ട്ര",
            "Punjab" to "പഞ്ചാബ്",
            "Uttar Pradesh" to "ഉത്തർപ്രദേശ്",
            "Madhya Pradesh" to "മധ്യപ്രദേശ്",
            "Telangana" to "തെലങ്കാന",
            "Tamil Nadu" to "തമിഴ്നാട്",
            "Karnataka" to "കർണാടക",
            "Gujarat" to "ഗുജറാത്ത്",
            "Rajasthan" to "രാജസ്ഥാൻ",
            "Bihar" to "ബീഹാർ",
            "West Bengal" to "പശ്ചിമ ബംഗാൾ",
            "Andhra Pradesh" to "ആന്ധ്രാപ്രദേശ്",
            "Haryana" to "ഹരിയാന",
            "Odisha" to "ഒഡീഷ",
            "Kerala" to "കേരളം"
        ),
        "or" to mapOf(
            "Maharashtra" to "ମହାରାଷ୍ଟ୍ର",
            "Punjab" to "ପଞ୍ଜାବ",
            "Uttar Pradesh" to "ଉତ୍ତର ପ୍ରଦେଶ",
            "Madhya Pradesh" to "ମଧ୍ୟ ପ୍ରଦେଶ",
            "Telangana" to "ତେଲେଙ୍ଗାନା",
            "Tamil Nadu" to "ତାମିଲନାଡୁ",
            "Karnataka" to "କର୍ଣ୍ଣାଟକ",
            "Gujarat" to "ଗୁଜରାଟ",
            "Rajasthan" to "ରାଜସ୍ଥାନ",
            "Bihar" to "ବିହାର",
            "West Bengal" to "ପଶ୍ଚିମବଙ୍ଗ",
            "Andhra Pradesh" to "ଆନ୍ଧ୍ର ପ୍ରଦେଶ",
            "Haryana" to "ହରିୟାଣା",
            "Odisha" to "ଓଡ଼ିଶା",
            "Kerala" to "କେରଳ"
        )
    )

    private val translations: Map<String, Map<String, SchemeTranslation>> = mapOf(

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
                eligibility = "All farmers including sharecroppers and tenant farmers growing notified crops. Both loanee and non-loanee farmers are eligible.",
                benefits = "Full insured sum coverage. Premium: Kharif 2%, Rabi 1.5%, Horticulture 5%. Government subsidizes the remaining premium.",
                amount = "Insured sum based on crop and area",
                howToApply = "Apply through nearest bank branch, CSC, or PMFBY portal within the enrollment window for each crop season."
            ),
            "kcc" to SchemeTranslation(
                title = "Kisan Credit Card (KCC)",
                description = "Provides short-term credit to farmers for crop production, post-harvest expenses, and consumption needs at subsidized interest rates.",
                eligibility = "All farmers, including individual/joint borrowers, sharecroppers, tenant farmers, and SHGs/JLGs of farmers.",
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
                eligibility = "All farmers, with priority for rainfed and drought-prone areas.",
                benefits = "Soil health management, rainfed area development, climate resilient seeds, water conservation support.",
                amount = "Varies by component (₹12,500/ha for rainfed dev)",
                howToApply = "Apply through State Agriculture Department or District Agriculture Officer."
            ),
            "rkvy" to SchemeTranslation(
                title = "Rashtriya Krishi Vikas Yojana (RKVY-RAFTAAR)",
                description = "Incentivizes states to increase public investment in agriculture infrastructure and supports agri-entrepreneurs.",
                eligibility = "Farmers and agri-startups through state agriculture initiatives.",
                benefits = "Infrastructure development, farm mechanization, agri-incubation and startup funding.",
                amount = "Startup grants up to ₹25 lakh",
                howToApply = "Apply through state agriculture department or RKVY portal."
            ),
            "agri_infra" to SchemeTranslation(
                title = "Agriculture Infrastructure Fund (AIF)",
                description = "Medium to long-term debt financing facility for post-harvest management infrastructure and community farming assets.",
                eligibility = "Farmers, FPOs, PACS, Agri-entrepreneurs, and Startups.",
                benefits = "3% interest subvention per annum on loans up to ₹2 crore for a maximum period of 7 years.",
                amount = "Loans up to ₹2 crore with 3% interest discount",
                howToApply = "Submit application on the official AIF portal (agriinfra.dac.gov.in)."
            ),
            "smam" to SchemeTranslation(
                title = "Sub-Mission on Agricultural Mechanization (SMAM)",
                description = "Subsidies on modern agricultural machinery to promote farm mechanization and establish Custom Hiring Centres.",
                eligibility = "Small, marginal, SC/ST, women farmers, and rural youth entrepreneurs.",
                benefits = "40% to 50% subsidy on purchase of tractors, rotavators, harvesters, and seed drills.",
                amount = "40%-50% equipment subsidy",
                howToApply = "Apply online at agrimachinery.nic.in with land records and bank details."
            ),
            "nbhm" to SchemeTranslation(
                title = "National Beekeeping & Honey Mission (NBHM)",
                description = "Promotes scientific beekeeping for income generation, pollination support, and honey production.",
                eligibility = "Farmers, women SHGs, and beekeepers across all states.",
                benefits = "Up to 80% subsidy on bee boxes, colonies, and honey extraction units.",
                amount = "Up to 80% subsidy for SC/ST/Women, 50% for others",
                howToApply = "Apply via National Bee Board portal (nbb.gov.in) or district horticulture office."
            ),
            "midh" to SchemeTranslation(
                title = "Mission for Integrated Development of Horticulture (MIDH)",
                description = "Holistic development of fruits, vegetables, spices, flowers, polyhouses, and cold storage infrastructure.",
                eligibility = "Individual farmers, SHGs, FPOs engaged in horticulture cultivation.",
                benefits = "40-50% financial assistance for orchards, protected shade-net polyhouses, and pack-houses.",
                amount = "40%-50% capital subsidy",
                howToApply = "Contact District Horticulture Officer or apply on state horticulture portal."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "PM-AASHA (Annadata Aay Sanrakshan Abhiyan)",
                description = "Comprehensive price support scheme ensuring remunerative Minimum Support Price (MSP) for oilseeds, pulses, and copra.",
                eligibility = "All registered farmers growing notified pulses and oilseeds.",
                benefits = "Guaranteed procurement at MSP or direct compensation of price deficit in bank accounts.",
                amount = "Full MSP realization",
                howToApply = "Register on the state procurement portal before harvest season."
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "Mahatma Jyotirao Phule Shetkari Karj Mukti Yojana",
                description = "Comprehensive farm loan waiver scheme for Maharashtra farmers with outstanding crop loans up to ₹2 lakh.",
                eligibility = "Farmers in Maharashtra with overdue crop loans up to ₹2 lakh from nationalized, cooperative, or rural banks.",
                benefits = "Complete waiver of crop loans up to ₹2 lakh. Incentive of ₹50,000 for regular loan repayers.",
                amount = "Loan waiver up to ₹2 lakh + ₹50,000 incentive",
                howToApply = "Apply through MahaDBT portal or nearest Tahsildar office with 7/12 extract and Aadhaar."
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "Nanaji Deshmukh Krishi Sanjivani Yojana (PoCRA)",
                description = "Climate-resilient agriculture project for drought-prone and salinity-affected districts of Maharashtra.",
                eligibility = "Small and marginal farmers in 15 drought-prone districts of Marathwada and Vidarbha.",
                benefits = "Subsidies for farm ponds, drip irrigation, shade nets, soil health improvement, and seed production.",
                amount = "Up to 75% subsidy on water conservation & micro-irrigation",
                howToApply = "Register on mahapocra.gov.in portal or via Gram Krishi Vikas Samiti."
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "Pani Bachao Paisa Kamao (Save Water Earn Money)",
                description = "Incentive scheme to conserve groundwater and electricity used in tube-well agricultural irrigation.",
                eligibility = "Farmers in Punjab with agricultural tube-well connections on designated feeders.",
                benefits = "Direct cash transfer of ₹4 per kilowatt-hour unit of electricity saved against sanctioned quota.",
                amount = "₹4 per electricity unit saved",
                howToApply = "Enroll with Punjab State Power Corporation Limited (PSPCL) sub-division."
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "UP Kisan Uday Yojana",
                description = "Distribution of highly energy-efficient solar and smart pumpsets to farmers in Uttar Pradesh.",
                eligibility = "Small and marginal farmers of UP with agricultural land and valid irrigation requirement.",
                benefits = "Free installation of 2 to 5 HP smart solar pump with 5-year free maintenance.",
                amount = "100% free smart pump distribution",
                howToApply = "Apply on the UP Agriculture Department portal (upagriculture.com)."
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "Bhavantar Bhugtan Yojana (Price Deficit Payment)",
                description = "Price compensation scheme paying the difference between MSP and market selling price directly to farmers.",
                eligibility = "Farmers registered on MP e-Uparjan portal selling notified crops in APMC mandis.",
                benefits = "Direct bank transfer of price gap whenever market mandi rates fall below MSP.",
                amount = "Deficit amount per quintal",
                howToApply = "Register on mpeuparjan.nic.in before harvesting."
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "Rythu Bandhu (Farmer Investment Support)",
                description = "Direct agricultural input investment assistance of ₹10,000 per acre per year for two crop seasons.",
                eligibility = "All landholding pattadar farmers in Telangana.",
                benefits = "₹5,000/acre in Kharif and ₹5,000/acre in Rabi deposited directly to bank accounts.",
                amount = "₹10,000/acre/year",
                howToApply = "Automatic enrollment based on Dharani portal land ownership records."
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "Tamil Nadu Free Farm Electricity Scheme",
                description = "Provides 24-hour uninterrupted free electrical power for agricultural pumpsets in Tamil Nadu.",
                eligibility = "All registered agricultural electricity consumers and landowners in Tamil Nadu.",
                benefits = "100% free electricity without bills for agricultural irrigation.",
                amount = "100% free agricultural power",
                howToApply = "Submit application to Tamil Nadu Generation and Distribution Corporation (TANGEDCO)."
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "Raitha Siri (Millet & Crop Prosperity Scheme)",
                description = "Financial assistance to promote minor millet cultivation and zero-interest crop loans up to ₹3 lakh.",
                eligibility = "Millet growers and farming families across Karnataka.",
                benefits = "₹10,000 per hectare incentive for minor millet cultivation; 0% interest crop loans.",
                amount = "₹10,000/hectare + 0% interest loans",
                howToApply = "Register on the Karnataka Raitha Mitra portal (raitamitra.karnataka.gov.in)."
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "Kisan Suryodaya Yojana",
                description = "Dedicated daytime electricity supply (5 AM to 9 PM) for farm irrigation across Gujarat.",
                eligibility = "All farmers in Gujarat connected to agricultural power grid.",
                benefits = "Safe, reliable daytime power eliminating dangerous midnight irrigation duties.",
                amount = "Free state infrastructure provision",
                howToApply = "Automatic coverage under state electricity distribution companies (DISCOMs)."
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "Rajasthan Kisan Mitra Energy Yojana",
                description = "Monthly electricity bill subsidy of up to ₹1,000 for general category agricultural connections.",
                eligibility = "Metered agricultural electricity consumers in Rajasthan.",
                benefits = "₹1,000 per month (up to ₹12,000/year) direct adjustment in electricity bills.",
                amount = "Up to ₹12,000/year electricity subsidy",
                howToApply = "Automatic adjustment by electricity DISCOMs on active metered connections."
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "Bihar Diesel Anudan Yojana",
                description = "Subsidized diesel reimbursement for irrigation during drought and deficient rainfall periods.",
                eligibility = "All farmers in Bihar owning land or cultivating as verified sharecroppers.",
                benefits = "₹75 per litre diesel subsidy, up to ₹750/acre per irrigation (up to 3 irrigations).",
                amount = "₹75/litre (max ₹2,250/acre)",
                howToApply = "Apply online at DBT Agriculture Bihar (dbtagriculture.bihar.gov.in)."
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "Krishak Bandhu Scheme",
                description = "Direct financial aid of ₹10,000/year and ₹2 lakh life insurance coverage for farmers in West Bengal.",
                eligibility = "All farmers and recorded bargadars (sharecroppers) in West Bengal aged 18-60.",
                benefits = "₹10,000/year in two instalments (Kharif and Rabi); ₹2,00,000 death compensation.",
                amount = "₹10,000/year + ₹2,00,000 life insurance",
                howToApply = "Register at nearest BDO office or Krishak Bandhu portal (krishakbandhu.net)."
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "YSR Rythu Bharosa - PM KISAN",
                description = "Annual financial assistance of ₹13,500 per farmer family including tenant farmers.",
                eligibility = "All landholding farmer families and SC/ST/BC/Minority tenant farmers in Andhra Pradesh.",
                benefits = "₹13,500/year credited in 3 seasonal instalments for seed, fertilizer, and farm inputs.",
                amount = "₹13,500/year",
                howToApply = "Enroll at Rythu Bharosa Kendras (RBK) or Village Secretariats."
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "Haryana Bhavantar Bharpai Yojana",
                description = "Risk mitigation scheme compensating vegetable and horticulture farmers for price crashes.",
                eligibility = "Horticulture farmers registered on Meri Fasal Mera Byora portal.",
                benefits = "Deficit payment between base protected cost and mandi selling price.",
                amount = "Difference amount per quintal",
                howToApply = "Register on fasal.haryana.gov.in portal before planting."
            ),
            "od_kalia" to SchemeTranslation(
                title = "KALIA (Krushak Assistance for Livelihood)",
                description = "Comprehensive livelihood and cultivation assistance for small, marginal, and landless agricultural households.",
                eligibility = "Small/marginal farmers and landless agricultural labourers in Odisha.",
                benefits = "₹10,000/year cultivation support, ₹12,500 for landless livelihoods, and ₹2 lakh insurance cover.",
                amount = "₹10,000/year + livelihood support",
                howToApply = "Apply online at kalia.odisha.gov.in or GP office with Aadhaar and ration card."
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "Kerala State Comprehensive Crop Insurance Scheme",
                description = "State-notified crop insurance protecting 25+ major crops against flood, landslide, wildlife damage, and pest outbreaks.",
                eligibility = "All farmers cultivating notified crops (paddy, banana, spices, vegetables, rubber) in Kerala.",
                benefits = "Prompt compensation for weather and wild animal crop damage with nominal farmer premium.",
                amount = "Crop-specific compensation up to ₹35,000/acre",
                howToApply = "Apply via AIMS portal (aims.kerala.gov.in) or visit local Krishi Bhavan."
            ),
        ),
        "hi" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "पीएम-किसान (प्रधानमंत्री किसान सम्मान निधि)",
                description = "पात्र किसान परिवारों को प्रति वर्ष ₹6,000 की प्रत्यक्ष आय सहायता, ₹2,000 की 3 समान किस्तों में सीधे बैंक खाते में दी जाती है।",
                eligibility = "कृषि योग्य भूमि वाले सभी भूमिधारक किसान परिवार। संस्थागत भूधारक, आयकर दाता और संवैधानिक पद धारक अपात्र हैं।",
                benefits = "हर 4 महीने में ₹2,000 की 3 किस्तों में प्रति वर्ष ₹6,000 का सीधा बैंक ट्रांसफर।",
                amount = "₹6,000 प्रति वर्ष",
                howToApply = "नजदीकी सीएससी (CSC) केंद्र, नोडल अधिकारी या pmkisan.gov.in पोर्टल पर ऑनलाइन पंजीकरण करें। आधार कार्ड, बैंक पासबुक और खतौनी जरूरी है।"
            ),
            "pmfby" to SchemeTranslation(
                title = "पीएमएफबीवाई (प्रधानमंत्री फसल बीमा योजना)",
                description = "प्राकृतिक आपदाओं, कीटों और रोगों से फसल नुकसान की स्थिति में सभी खाद्य, तिलहन और बागवानी फसलों को व्यापक बीमा सुरक्षा प्रदान करती है।",
                eligibility = "अधिसूचित फसलें उगाने वाले बटाईदार और काश्तकार सहित सभी ऋणी और गैर-ऋणी किसान।",
                benefits = "पूर्ण बीमित राशि का भुगतान। किसान प्रीमियम: खरीफ 2%, रबी 1.5%, बागवानी 5%। शेष प्रीमियम सरकार देती है।",
                amount = "फसल और क्षेत्रफल के आधार पर बीमित राशि",
                howToApply = "निकटतम बैंक शाखा, सीएससी या pmfby.gov.in पोर्टल पर फसल मौसम की निर्धारित समय-सीमा के भीतर आवेदन करें।"
            ),
            "kcc" to SchemeTranslation(
                title = "किसान क्रेडिट कार्ड (केसीसी)",
                description = "फसल उत्पादन, कटाई के बाद के खर्चों और घरेलू जरूरतों के लिए रियायती ब्याज दर पर अल्पकालिक ऋण सुविधा।",
                eligibility = "व्यक्तिगत/संयुक्त उधारकर्ता, काश्तकार, बटाईदार और स्वयं सहायता समूह (SHG) सहित सभी किसान।",
                benefits = "4% ब्याज दर पर ऋण (समय पर भुगतान करने पर 3% छूट)। पीएमएफबीवाई के तहत बीमा और एटीएम कार्ड सुविधा।",
                amount = "4% ब्याज दर पर ₹3 लाख तक का ऋण",
                howToApply = "जमीन के दस्तावेज, पहचान पत्र और फोटो के साथ किसी भी वाणिज्यिक, ग्रामीण या सहकारी बैंक में आवेदन करें।"
            ),
            "soil_health" to SchemeTranslation(
                title = "मृदा स्वास्थ्य कार्ड योजना (सॉइल हेल्थ कार्ड)",
                description = "किसानों को मिट्टी की उर्वरता और फसलवार पोषक तत्वों व उर्वरकों के संतुलित उपयोग की वैज्ञानिक सिफारिशें प्रदान की जाती हैं।",
                eligibility = "कृषि भूमि वाले देश के सभी किसान।",
                benefits = "निःशुल्क मिट्टी जांच, 12 प्रमुख पोषक तत्वों की स्थिति रिपोर्ट और 2 वर्ष के लिए मान्य सिफारिशें।",
                amount = "पूर्णतः निःशुल्क",
                howToApply = "नजदीकी मृदा परीक्षण प्रयोगशाला, कृषि विज्ञान केंद्र (KVK) से संपर्क करें या soilhealth.dac.gov.in पर पंजीकरण करें।"
            ),
            "pmksy" to SchemeTranslation(
                title = "पीएम कृषि सिंचाई योजना (हर खेत को पानी)",
                description = "प्रत्येक खेत तक सिंचाई की पहुंच सुनिश्चित करना और सूक्ष्म/ड्रिप सिंचाई के माध्यम से जल उपयोग दक्षता को बढ़ाना।",
                eligibility = "सभी किसान। सूखाग्रस्त, असिंचित और जनजातीय क्षेत्रों को विशेष प्राथमिकता।",
                benefits = "ड्रिप और स्प्रिंकलर सिस्टम पर लघु/सीमांत किसानों को 55% और अन्य किसानों को 45% सब्सिडी।",
                amount = "लघु किसानों को 55%, अन्य को 45% सब्सिडी",
                howToApply = "राज्य कृषि/उद्यानिकी विभाग या जिला कृषि अधिकारी के कार्यालय में आवेदन करें।"
            ),
            "pkvy" to SchemeTranslation(
                title = "परंपरागत कृषि विकास योजना (पीकेवीवाई)",
                description = "क्लस्टर पद्धति के माध्यम से जैविक खेती को बढ़ावा देना और किसानों को पीजीएस प्रमाणन में सहायता करना।",
                eligibility = "जैविक खेती के लिए 50 एकड़ का क्लस्टर बनाने वाले 50 या अधिक किसानों का समूह।",
                benefits = "जैविक खाद, बीज, प्रसंस्करण और विपणन के लिए 3 वर्षों में ₹50,000 प्रति हेक्टेयर की सहायता।",
                amount = "₹50,000 प्रति हेक्टेयर (3 वर्षों में)",
                howToApply = "50 किसानों का समूह बनाकर जिला कृषि अधिकारी या राज्य जैविक मिशन में आवेदन करें।"
            ),
            "enam" to SchemeTranslation(
                title = "ई-नाम (राष्ट्रीय कृषि बाजार)",
                description = "कृषि जिंसों के लिए ऑनलाइन व्यापार मंच जो देश भर की एपीएमसी मंडियों को एक साझा बाजार से जोड़ता है।",
                eligibility = "एपीएमसी मंडियों में पंजीकृत सभी किसान, व्यापारी और कमीशन एजेंट।",
                benefits = "पारदर्शी मूल्य निर्धारण, बिचौलियों से मुक्ति, बेहतर मूल्य और ऑनलाइन बैंक भुगतान।",
                amount = "किसानों के लिए पूर्णतः निःशुल्क",
                howToApply = "enam.gov.in पोर्टल पर पंजीकरण करें या नजदीकी ई-नाम मंडी में आधार व बैंक खाते के साथ जाएं।"
            ),
            "pm_kusum" to SchemeTranslation(
                title = "पीएम-कुसुम (सोलर पंप योजना)",
                description = "खेतों में स्टैंडअलोन सोलर पंप लगाने और ग्रिड से जुड़े कृषि पंपों को सौर ऊर्जा से संचालित करने की योजना।",
                eligibility = "सभी व्यक्तिगत किसान, किसान समूह, एफपीओ और पंचायतें।",
                benefits = "सोलर पंप लगाने पर 60% सरकारी सब्सिडी (30% केंद्र + 30% राज्य)। किसान को केवल 40% (या 10% बैंक लोन के साथ) देना होता है।",
                amount = "कुल 60% सरकारी सब्सिडी",
                howToApply = "राज्य अक्षय ऊर्जा विभाग (REDA) या mnre.gov.in पोर्टल पर आवेदन करें।"
            ),
            "nmsa" to SchemeTranslation(
                title = "राष्ट्रीय सतत कृषि मिशन (एनएमएसए)",
                description = "जलवायु अनुकूल कृषि तकनीकों, मृदा स्वास्थ्य प्रबंधन और वर्षा आधारित क्षेत्रों में टिकाऊ खेती को बढ़ावा देना।",
                eligibility = "सभी किसान, विशेष रूप से असिंचित व सूखा प्रवण क्षेत्रों के कृषक।",
                benefits = "मृदा संरक्षण, खेत तालाब निर्माण, वर्मीकम्पोस्ट और जलवायु अनुकूल बीजों पर अनुदान।",
                amount = "वर्षा आधारित विकास पर ₹12,500/हेक्टेयर तक सहायता",
                howToApply = "जिला कृषि अधिकारी या ब्लॉक कृषि कार्यालय के माध्यम से आवेदन करें।"
            ),
            "rkvy" to SchemeTranslation(
                title = "राष्ट्रीय कृषि विकास योजना (आरकेवीवाई-रफ्तार)",
                description = "कृषि अवसंरचना, आधुनिक तकनीक और कृषि स्टार्टअप्स को वित्तीय व इनक्यूबेशन सहायता।",
                eligibility = "किसान, एफपीओ और कृषि-उद्यमी/स्टार्टअप।",
                benefits = "कृषि प्रसंस्करण, फसल कटाई उपरांत प्रबंधन और नए कृषि स्टार्टअप्स को ₹25 लाख तक का अनुदान।",
                amount = "स्टार्टअप अनुदान ₹25 लाख तक",
                howToApply = "rkvy.nic.in पोर्टल पर या राज्य कृषि विभाग के माध्यम से आवेदन करें।"
            ),
            "agri_infra" to SchemeTranslation(
                title = "कृषि अवसंरचना कोष (एआईएफ)",
                description = "फसल कटाई उपरांत प्रबंधन और सामुदायिक कृषि परिसंपत्तियों के निर्माण हेतु रियायती ऋण सहायता।",
                eligibility = "किसान, एफपीओ, प्राथमिक कृषि सहकारी समितियां (PACS), कृषि उद्यमी।",
                benefits = "₹2 करोड़ तक के ऋण पर 7 वर्षों के लिए 3% ब्याज छूट (सबवेंशन) और क्रेडिट गारंटी।",
                amount = "₹2 करोड़ तक ऋण पर 3% ब्याज छूट",
                howToApply = "agriinfra.dac.gov.in पोर्टल पर ऑनलाइन प्रोजेक्ट प्रस्ताव जमा करें।"
            ),
            "smam" to SchemeTranslation(
                title = "कृषि यंत्रीकरण उप-मिशन (एसएमएएम)",
                description = "ट्रैक्टर, कंबाइन हार्वेस्टर और आधुनिक कृषि यंत्रों की खरीद पर भारी सब्सिडी।",
                eligibility = "लघु, सीमांत, महिला, एससी/एसटी किसान और कस्टम हायरिंग सेंटर स्थापित करने वाले ग्रामीण युवा।",
                benefits = "कृषि उपकरणों और मशीनों पर 40% से 50% तक की प्रत्यक्ष सब्सिडी।",
                amount = "उपकरणों पर 40% से 50% सब्सिडी",
                howToApply = "agrimachinery.nic.in पोर्टल पर खतौनी और बैंक विवरण के साथ ऑनलाइन आवेदन करें।"
            ),
            "nbhm" to SchemeTranslation(
                title = "राष्ट्रीय मधुमक्खी पालन एवं शहद मिशन (एनबीएचएम)",
                description = "अतिरिक्त आय, परागण सुधार और शहद उत्पादन के लिए वैज्ञानिक मधुमक्खी पालन को प्रोत्साहन।",
                eligibility = "किसान, महिला स्वयं सहायता समूह और मधुमक्खी पालक।",
                benefits = "मधुमक्खी के बक्से, कॉलोनियों और शहद निष्कर्षण यंत्रों पर 80% तक अनुदान।",
                amount = "महिला/एससी/एसटी को 80%, अन्य को 50% सब्सिडी",
                howToApply = "nbb.gov.in पोर्टल पर या जिला उद्यान अधिकारी से संपर्क करें।"
            ),
            "midh" to SchemeTranslation(
                title = "एकीकृत बागवानी विकास मिशन (एमआईडीएच)",
                description = "फल, सब्जी, मसाले, फूल, पॉलीहाउस और कोल्ड स्टोरेज के समग्र विकास हेतु सहायता।",
                eligibility = "बागवानी की खेती करने वाले सभी व्यक्तिगत किसान और एफपीओ।",
                benefits = "नया बगीचा लगाने, शेड-नेट पॉलीहाउस और पैक-हाउस निर्माण पर 40-50% वित्तीय सहायता।",
                amount = "40% से 50% पूंजीगत सब्सिडी",
                howToApply = "जिला उद्यानिकी अधिकारी से संपर्क करें या राज्य बागवानी पोर्टल पर आवेदन करें।"
            ),
            "pm_aasha" to SchemeTranslation(
                title = "पीएम-आशा (अन्नदाता आय संरक्षण अभियान)",
                description = "दलहन, तिलहन और खोपरा (नारियल) के लिए न्यूनतम समर्थन मूल्य (एमएसपी) की कानूनी गारंटी।",
                eligibility = "अधिसूचित दलहन और तिलहन फसलें उगाने वाले सभी पंजीकृत किसान।",
                benefits = "मंडी भाव एमएसपी से कम होने पर सरकारी खरीद या मूल्य अंतर का सीधा बैंक भुगतान।",
                amount = "पूर्ण एमएसपी मूल्य प्राप्ति",
                howToApply = "फसल कटाई से पहले राज्य उपार्जन पोर्टल पर किसान पंजीकरण कराएं।"
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "महात्मा ज्योतिराव फुले शेतकरी कर्जमुक्ति योजना",
                description = "महाराष्ट्र के किसानों के लिए ₹2 लाख तक का बकाया फसली ऋण माफ करने की योजना।",
                eligibility = "महाराष्ट्र के वे किसान जिनका ₹2 लाख तक का फसली ऋण बकाया है।",
                benefits = "₹2 लाख तक का संपूर्ण फसली ऋण माफ। नियमित ऋण चुकाने वाले किसानों को ₹50,000 की प्रोत्साहन राशि।",
                amount = "₹2 लाख तक कर्जमाफी + ₹50,000 प्रोत्साहन",
                howToApply = "MahaDBT पोर्टल या नजदीकी तहसीलदार कार्यालय में 7/12 और आधार के साथ आवेदन करें।"
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "नानाजी देशमुख कृषि संजीवनी योजना (पोकरा)",
                description = "महाराष्ट्र के सूखाग्रस्त और खारे पानी से प्रभावित जिलों में जलवायु-अनुकूल खेती परियोजना।",
                eligibility = "मराठवाड़ा और विदर्भ के 15 सूखाग्रस्त जिलों के छोटे व सीमांत किसान।",
                benefits = "खेत तालाब, ड्रिप सिंचाई, शेड-नेट, मृदा सुधार और बीज उत्पादन पर 75% तक अनुदान।",
                amount = "जल संरक्षण व ड्रिप पर 75% तक सब्सिडी",
                howToApply = "mahapocra.gov.in पोर्टल पर या ग्राम कृषि विकास समिति के माध्यम से आवेदन करें।"
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "पानी बचाओ पैसा कमाओ योजना",
                description = "कृषि ट्यूबवेलों में बिजली और भूजल बचाने पर किसानों को नकद प्रोत्साहन देने की योजना।",
                eligibility = "पंजाब के वे किसान जिनके पास कृषि फीडर पर मीटरयुक्त ट्यूबवेल कनेक्शन है।",
                benefits = "निर्धारित कोटे से बिजली बचाने पर ₹4 प्रति यूनिट की दर से सीधे बैंक खाते में भुगतान।",
                amount = "₹4 प्रति यूनिट बिजली बचत",
                howToApply = "पंजाब स्टेट पावर कॉर्पोरेशन (PSPCL) के उप-मंडल कार्यालय में नामांकन कराएं।"
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "यूपी किसान उदय योजना",
                description = "उत्तर प्रदेश के किसानों को ऊर्जा-कुशल स्मार्ट और सोलर पंपसेटों का निःशुल्क वितरण।",
                eligibility = "यूपी के लघु और सीमांत किसान जिनके पास कृषि भूमि और सिंचाई की आवश्यकता है।",
                benefits = "2 से 5 हॉर्सपावर (HP) के सोलर पंप का निःशुल्क इंस्टॉलेशन और 5 साल की मुफ्त मेंटेनेंस।",
                amount = "100% निःशुल्क सोलर पंप वितरण",
                howToApply = "यूपी कृषि विभाग के पोर्टल (upagriculture.com) पर ऑनलाइन आवेदन करें।"
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "भावांतर भुगतान योजना",
                description = "मंडी में फसल का भाव एमएसपी से कम रहने पर मूल्य अंतर की भरपाई सीधे किसान के खाते में।",
                eligibility = "एमपी ई-उपार्जन पोर्टल पर पंजीकृत किसान जो अधिसूचित फसलें मंडियों में बेचते हैं।",
                benefits = "एमएसपी और मंडी बिक्री मूल्य के अंतर का सीधा बैंक खाते में भुगतान।",
                amount = "प्रति क्विंटल भावांतर राशि",
                howToApply = "फसल कटाई से पहले mpeuparjan.nic.in पोर्टल पर पंजीकरण कराएं।"
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "रयथु बंधु योजना (किसान निवेश सहायता)",
                description = "खेती की लागत के लिए ₹10,000 प्रति एकड़ प्रति वर्ष की प्रत्यक्ष आर्थिक सहायता।",
                eligibility = "तेलंगाना के सभी पट्टादार भूमिधारक किसान।",
                benefits = "खरीफ में ₹5,000 और रबी में ₹5,000 प्रति एकड़ सीधे बैंक खाते में जमा।",
                amount = "₹10,000 प्रति एकड़ प्रति वर्ष",
                howToApply = "धरणी पोर्टल पर भूमि रिकॉर्ड के आधार पर स्वतः नामांकन।"
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "तमिलनाडु निःशुल्क कृषि बिजली योजना",
                description = "तमिलनाडु में कृषि पंपसेटों के लिए 24 घंटे निर्बाध और पूरी तरह मुफ्त बिजली आपूर्ति।",
                eligibility = "तमिलनाडु के सभी पंजीकृत कृषि विद्युत उपभोक्ता और किसान।",
                benefits = "कृषि सिंचाई के लिए बिना किसी मीटर बिल के 100% मुफ्त बिजली।",
                amount = "100% मुफ्त कृषि बिजली",
                howToApply = "तमिलनाडु बिजली बोर्ड (TANGEDCO) में कृषि कनेक्शन हेतु आवेदन करें।"
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "रैथा सिरी योजना (मिलेट एवं फसल संवर्धन)",
                description = "मोटे अनाज (मिलेट्स) की खेती को बढ़ावा देने हेतु आर्थिक सहायता और ₹3 लाख तक शून्य ब्याज ऋण।",
                eligibility = "कर्नाटक के मोटे अनाज उत्पादक और कृषक परिवार।",
                benefits = "मोटे अनाज की खेती पर ₹10,000 प्रति हेक्टेयर प्रोत्साहन; 0% ब्याज पर फसली ऋण।",
                amount = "₹10,000/हेक्टेयर + 0% ब्याज ऋण",
                howToApply = "कर्नाटक रैथा मित्र पोर्टल (raitamitra.karnataka.gov.in) पर आवेदन करें।"
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "किसान सूर्योदय योजना",
                description = "गुजरात भर के किसानों को दिन के समय (सुबह 5 से रात 9 बजे तक) कृषि सिंचाई हेतु बिजली आपूर्ति।",
                eligibility = "गुजरात के कृषि विद्युत ग्रिड से जुड़े सभी किसान।",
                benefits = "दिन के समय सुरक्षित व विश्वसनीय बिजली जिससे रात में सिंचाई की परेशानी खत्म।",
                amount = "निःशुल्क सरकारी अवसंरचना व्यवस्था",
                howToApply = "राज्य विद्युत वितरण कंपनियों (DISCOMs) के तहत स्वतः आच्छादित।"
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "राजस्थान किसान मित्र ऊर्जा योजना",
                description = "कृषि बिजली उपभोक्ताओं को बिल पर प्रति माह ₹1,000 तक की प्रत्यक्ष सब्सिडी।",
                eligibility = "राजस्थान के मीटरयुक्त कृषि बिजली कनेक्शन धारक।",
                benefits = "बिजली बिल में प्रति माह ₹1,000 (प्रति वर्ष ₹12,000 तक) की सीधी छूट।",
                amount = "₹12,000 प्रति वर्ष तक बिजली सब्सिडी",
                howToApply = "विद्युत वितरण निगम द्वारा सक्रिय बिलों पर स्वतः समायोजन।"
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "बिहार डीजल अनुदान योजना",
                description = "अनियमित बारिश व सूखे की स्थिति में सिंचाई के लिए डीजल खरीद पर सरकारी अनुदान।",
                eligibility = "बिहार के रैयत और गैर-रैयत (बटाईदार) किसान।",
                benefits = "₹75 प्रति लीटर डीजल सब्सिडी (अधिकतम ₹750 प्रति एकड़ प्रति सिंचाई, 3 सिंचाई तक)।",
                amount = "₹75/लीटर (अधिकतम ₹2,250/एकड़)",
                howToApply = "प्रत्यक्ष लाभ अंतरण कृषि पोर्टल (dbtagriculture.bihar.gov.in) पर ऑनलाइन आवेदन करें।"
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "कृषक बंधु योजना",
                description = "पश्चिम बंगाल के किसानों को प्रति वर्ष ₹10,000 की वित्तीय सहायता और ₹2 लाख का जीवन बीमा।",
                eligibility = "पश्चिम बंगाल के सभी किसान और बटाईदार (उम्र 18 से 60 वर्ष)।",
                benefits = "₹10,000 प्रति वर्ष (दो किस्तों में) और मृत्यु होने पर ₹2 लाख की बीमा राशि।",
                amount = "₹10,000 प्रति वर्ष + ₹2 लाख बीमा",
                howToApply = "प्रखंड विकास अधिकारी (BDO) कार्यालय या krishakbandhu.net पोर्टल पर पंजीकरण करें।"
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "वाईएसआर रयथु भरोसा - पीएम किसान",
                description = "आंध्र प्रदेश में काश्तकार किसानों सहित सभी किसान परिवारों को ₹13,500 प्रति वर्ष की आर्थिक सहायता।",
                eligibility = "आंध्र प्रदेश के सभी भूमिधारक और एससी/एसटी/बीसी काश्तकार किसान।",
                benefits = "बीज, खाद और कृषि इनपुट हेतु प्रति वर्ष ₹13,500 की 3 मौसमी किस्तें।",
                amount = "₹13,500 प्रति वर्ष",
                howToApply = "रयथु भरोसा केंद्र (RBK) या ग्राम सचिवालय में पंजीकरण कराएं।"
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "हरियाणा भावांतर भरपाई योजना",
                description = "सब्जियों और बागवानी फसलों के बाजार भाव गिरने पर किसानों को नुकसान से बचाने की योजना।",
                eligibility = "मेरी फसल मेरा ब्योरा पोर्टल पर पंजीकृत बागवानी किसान।",
                benefits = "संरक्षित आधार मूल्य और मंडी बिक्री मूल्य के अंतर का सीधा बैंक भुगतान।",
                amount = "प्रति क्विंटल भरपाई राशि",
                howToApply = "फसल लगाने से पहले fasal.haryana.gov.in पर पंजीकरण करें।"
            ),
            "od_kalia" to SchemeTranslation(
                title = "कालिया योजना (कृषक आजीविका एवं आय संवर्धन)",
                description = "ओडिशा के छोटे, सीमांत और भूमिहीन किसान परिवारों के लिए व्यापक वित्तीय सहायता।",
                eligibility = "ओडिशा के लघु/सीमांत किसान और भूमिहीन कृषि श्रमिक।",
                benefits = "खेती हेतु ₹10,000/वर्ष, भूमिहीनों के लिए ₹12,500 और ₹2 लाख का बीमा कवर।",
                amount = "₹10,000/वर्ष खेती सहायता + बीमा",
                howToApply = "kalia.odisha.gov.in पोर्टल पर या पंचायत कार्यालय में आवेदन करें।"
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "केरल राज्य व्यापक फसल बीमा योजना",
                description = "बाढ़, भूस्खलन और वन्यजीवों से फसल नुकसान की स्थिति में 25+ प्रमुख फसलों को राज्य-स्तरीय बीमा सुरक्षा।",
                eligibility = "केरल में धान, केला, मसाले, सब्जियां और रबर की खेती करने वाले किसान।",
                benefits = "प्राकृतिक आपदा व जंगली जानवरों से नुकसान पर तुरंत मुआवजा और नाममात्र प्रीमियम।",
                amount = "फसलवार मुआवजा ₹35,000 प्रति एकड़ तक",
                howToApply = "AIMS पोर्टल (aims.kerala.gov.in) पर या नजदीकी कृषि भवन में आवेदन करें।"
            ),
        ),
        "bn" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "পিএম-কিসান (প্রধানমন্ত্রী কিষাণ সম্মান নিধি)",
                description = "যোগ্য কৃষক পরিবারকে বছরে ₹৬,০০০ সরাসরি আর্থিক সহায়তা, প্রতি ৪ মাসে ₹২,০০০ করে ৩টি সমান কিস্তিতে সরাসরি ব্যাংক অ্যাকাউন্টে দেওয়া হয়।",
                eligibility = "চাষযোগ্য জমি রয়েছে এমন সকল জমির মালিক কৃষক পরিবার। প্রাতিষ্ঠানিক জমির মালিক ও আয়করদাতারা অন্তর্ভুক্ত নন।",
                benefits = "বছরে ৩টি কিস্তিতে মোট ₹৬,০০০ সরাসরি ব্যাংক ট্রান্সফার।",
                amount = "প্রতি বছর ₹৬,০০০",
                howToApply = "নিকটস্থ সিএসসি (CSC) কেন্দ্র, কৃষি আধিকারিক বা pmkisan.gov.in পোর্টালে আধার ও জমির খতিয়ান সহ আবেদন করুন।"
            ),
            "pmfby" to SchemeTranslation(
                title = "পিএমএফবিওয়াই (প্রধানমন্ত্রী ফসল বীমা যোজনা)",
                description = "প্রাকৃতিক দুর্যোগ, খরা, বন্যা ও পোকার আক্রমণে ফসলের ক্ষতি হলে খাদ্যশস্য, তৈলবীজ ও উদ্যানজাত ফসলের জন্য ব্যাপক বীমা সুরক্ষা।",
                eligibility = "অধিভুক্ত ফসল চাষকারী সকল কৃষক, ভাগচাষী এবং ঋণগ্রহীতা ও অ-ঋণগ্রহীতা কৃষক।",
                benefits = "সম্পূর্ণ বীমাকৃত অর্থ প্রদান। প্রিমিয়াম: খরিফ ২%, রবি ১.৫%, বাণিজ্যিক ফসল ৫%। বাকি ভর্তুকি সরকার দেয়।",
                amount = "ফসল ও জমির পরিমাণের ওপর ভিত্তি করে বীমার অর্থ",
                howToApply = "নিকটস্থ ব্যাংক শাখা, সিএসসি অথবা pmfby.gov.in পোর্টালে সময়সীমার মধ্যে আবেদন করুন।"
            ),
            "kcc" to SchemeTranslation(
                title = "কিষাণ ক্রেডিট কার্ড (কেসিসি)",
                description = "ফসল উৎপাদন, চাষের খরচ ও কাটার পরবর্তী প্রয়োজনে স্বল্প সুদে কৃষকদের জন্য স্বল্পমেয়াদী ঋণ সুবিধা।",
                eligibility = "একক/যৌথ ঋণগ্রহীতা, ভাগচাষী এবং স্বনির্ভর গোষ্ঠীর কৃষক সদস্যগণ।",
                benefits = "৪% সহজ সুদে ঋণ (সময়মতো পরিশোধে ৩% সুদ ছাড়)। পিএমএফবিওয়াই বীমা কভারেজ ও এটিএম কার্ড সুবিধা।",
                amount = "৪% সুদে সর্বোচ্চ ₹৩ লাখ পর্যন্ত ঋণ",
                howToApply = "জমির দলিল, পরিচয়পত্র এবং ছবি সহ যেকোনো বাণিজ্যিক, সমবায় বা গ্রামীণ ব্যাংকে আবেদন করুন।"
            ),
            "soil_health" to SchemeTranslation(
                title = "মৃত্তিকা স্বাস্থ্য কার্ড প্রকল্প (সয়েল হেলথ কার্ড)",
                description = "জমির মাটির উর্বরতা পরীক্ষা করে ফসল অনুযায়ী প্রয়োজনীয় সার ও পুষ্টি উপাদানের সঠিক পরিমাণ নির্ধারণের সুপারিশ।",
                eligibility = "কৃষি জমি রয়েছে এমন সকল কৃষক।",
                benefits = "বিনামূল্যে মাটির গুণমান পরীক্ষা এবং ১২টি পুষ্টি উপাদানের বিশদ রিপোর্ট ও ২ বছরের জন্য সুপারিশ।",
                amount = "সম্পূর্ণ বিনামূল্যে",
                howToApply = "নিকটস্থ মৃত্তিকা পরীক্ষা গবেষণাগার, কৃষি বিজ্ঞান কেন্দ্র (কেভিকে) বা সয়েল হেলথ কার্ড পোর্টালে যোগাযোগ করুন।"
            ),
            "pmksy" to SchemeTranslation(
                title = "প্রধানমন্ত্রী কৃষি সেচ যোজনা (পিএমকেএসওয়াই)",
                description = "প্রতিটি জমিতে সেচের জল নিশ্চিত করা এবং ড্রিপ ও স্প্রিংকলার সেচের মাধ্যমে জলের সঠিক ব্যবহার বৃদ্ধি করা।",
                eligibility = "সকল কৃষক। খরাপ্রবণ ও অনগ্রসর এলাকাকে অগ্রাধিকার।",
                benefits = "ক্ষুদ্র ও প্রান্তিক কৃষকদের ড্রিপ/স্প্রিংকলার সেচ ব্যবস্থায় ৫৫% এবং অন্যান্য কৃষকদের ৪৫% ভর্তুকি।",
                amount = "৫৫% থেকে ৪৫% সরকারি ভর্তুকি",
                howToApply = "জেলা কৃষি দপ্তর বা ব্লক কৃষি আধিকারিকের (ADA) অফিসে আবেদন করুন।"
            ),
            "pkvy" to SchemeTranslation(
                title = "পরম্পরাগত কৃষি বিকাশ যোজনা (পিকেভিওয়াই)",
                description = "ক্লাস্টার পদ্ধতির মাধ্যমে জৈব চাষের প্রচার এবং কৃষকদের পিজিএস সার্টিফিকেশন সহায়তা প্রদান।",
                eligibility = "জৈব চাষের জন্য ৫০ একরের ক্লাস্টার তৈরিকারী ৫০ বা ততোধিক কৃষকের দল।",
                benefits = "জৈব সার, প্রক্রিয়াকরণ ও বিপণনের জন্য ৩ বছরে প্রতি হেক্টরে ₹৫০,০০০ আর্থিক সহায়তা।",
                amount = "৩ বছরে হেক্টর প্রতি ₹৫০,০০০",
                howToApply = "৫০ জন কৃষকের দল গঠন করে জেলা কৃষি আধিকারিকের নিকট আবেদন জমা দিন।"
            ),
            "enam" to SchemeTranslation(
                title = "ই-নাম (জাতীয় কৃষি বাজার)",
                description = "কৃষি পণ্যের অনলাইন ট্রেডিং প্ল্যাটফর্ম যা সারা দেশের এপিএমসি মান্ডিকে একক জাতীয় বাজারে সংযুক্ত করে।",
                eligibility = "এপিএমসি মান্ডিতে নিবন্ধিত সকল কৃষক, ব্যবসায়ী ও কমিশন এজেন্ট।",
                benefits = "স্বচ্ছ মূল্য নির্ধারণ, সঠিক ওজন, দালালের দৌরাত্ম্য হ্রাস এবং সরাসরি ব্যাংক অ্যাকাউন্টে টাকা জমা।",
                amount = "কৃষকদের জন্য সম্পূর্ণ বিনামূল্যে",
                howToApply = "enam.gov.in পোর্টালে নিবন্ধন করুন বা নিকটস্থ ই-নাম মান্ডিতে যান।"
            ),
            "pm_kusum" to SchemeTranslation(
                title = "পিএম-কুসুম (সৌর বিদ্যুৎ চালিত পাম্প প্রকল্প)",
                description = "চাষের জমিতে সৌর পাম্প স্থাপন এবং গ্রিড সংযুক্ত পাম্পকে সৌর শক্তিতে রূপান্তর করার প্রকল্প।",
                eligibility = "সকল কৃষক, কৃষক গোষ্ঠী, এফপিও এবং পঞ্চায়েত।",
                benefits = "সৌর পাম্পে ৬০% সরকারি ভর্তুকি (৩০% কেন্দ্র + ৩০% রাজ্য)। কৃষককে মাত্র ৪০% বহন করতে হয়।",
                amount = "মোট ৬০% সরকারি ভর্তুকি",
                howToApply = "রাজ্য পুনর্নবীকরণযোগ্য শক্তি উন্নয়ন সংস্থা বা mnre.gov.in পোর্টালে আবেদন করুন।"
            ),
            "nmsa" to SchemeTranslation(
                title = "জাতীয় টেকসই কৃষি মিশন (এনএমএসএ)",
                description = "জলবায়ু সহনশীল কৃষি প্রযুক্তি, মাটির উর্বরতা সংরক্ষণ ও বৃষ্টির ওপর নির্ভরশীল এলাকায় টেকসই চাষাবাদ।",
                eligibility = "বৃষ্টির জলে চাষকারী সকল ক্ষুদ্র ও প্রান্তিক কৃষক।",
                benefits = "মাটি সংরক্ষণ, ফার্ম পন্ড খনন, ভার্মিকম্পোস্ট এবং উন্নত বীজের জন্য আর্থিক সহায়তা।",
                amount = "বৃষ্টিভিত্তিক এলাকা উন্নয়নে ₹১২,৫০০/হেক্টর সহায়তা",
                howToApply = "ব্লক বা জেলা কৃষি আধিকারিকের মাধ্যমে আবেদন করুন।"
            ),
            "rkvy" to SchemeTranslation(
                title = "রাষ্ট্রীয় কৃষি বিকাশ যোজনা (আরকেভিওয়াই-রাফতার)",
                description = "কৃষি পরিকাঠামো উন্নয়ন, উদ্ভাবন এবং কৃষি ভিত্তিক স্টার্টআপ ও উদ্যোক্তাদের আর্থিক সহায়তা।",
                eligibility = "কৃষক, এফপিও এবং নতুন এগ্রি-স্টার্টআপ উদ্যোক্তাগণ।",
                benefits = "কৃষি ফসল সংগ্রহোত্তর পরিকাঠামো এবং এগ্রি-স্টার্টআপের জন্য ₹২৫ লাখ পর্যন্ত অনুদান।",
                amount = "স্টার্টআপ অনুদান ₹২৫ লাখ পর্যন্ত",
                howToApply = "rkvy.nic.in পোর্টালে অথবা রাজ্য কৃষি দপ্তরের মাধ্যমে আবেদন করুন।"
            ),
            "agri_infra" to SchemeTranslation(
                title = "কৃষি পরিকাঠামো তহবিল (এআইএফ)",
                description = "ফসল কাটার পরবর্তী গুদাম, কোল্ড স্টোরেজ ও প্রাথমিক প্রক্রিয়াকরণ পরিকাঠামো নির্মাণের জন্য স্বল্প সুদে ঋণ।",
                eligibility = "কৃষক, এফপিও, প্রাথমিক কৃষি সমবায় সমিতি (PACS), এবং কৃষি উদ্যোক্তা।",
                benefits = "₹২ কোটি পর্যন্ত ঋণে ৭ বছরের জন্য ৩% বার্ষিক সুদ ছাড় ও ক্রেডিট গ্যারান্টি।",
                amount = "₹২ কোটি পর্যন্ত ঋণে ৩% সুদ ছাড়",
                howToApply = "agriinfra.dac.gov.in পোর্টালে প্রকল্পের বিশদ বিবরণ জমা দিন।"
            ),
            "smam" to SchemeTranslation(
                title = "কৃষি যান্ত্রিকীকরণ উপ-মিশন (এসএমএএম)",
                description = "ট্র্যাক্টর, পাওয়ার টিলার, হারভেস্টার এবং আধুনিক কৃষি যন্ত্রপাতি কেনার জন্য অনুদান।",
                eligibility = "ক্ষুদ্র, প্রান্তিক, মহিলা ও তফসিলি জাতি/উপজাতির কৃষক এবং গ্রামীণ যুবক।",
                benefits = "কৃষি যন্ত্রপাতিতে ৪০% থেকে ৫০% সরাসরি সরকারি ভর্তুকি।",
                amount = "যন্ত্রপাতিতে ৪০%-৫০% সরকারি ভর্তুকি",
                howToApply = "agrimachinery.nic.in পোর্টালে জমির নথি ও ব্যাংক বিবরণ সহ আবেদন করুন।"
            ),
            "nbhm" to SchemeTranslation(
                title = "জাতীয় মৌমাছি পালন ও মধু মিশন (এনবিএইচএম)",
                description = "অতিরিক্ত আয়, পরাগায়ন বৃদ্ধি এবং মধু উৎপাদনের জন্য বৈজ্ঞানিক মৌমাছি পালনে সহায়তা।",
                eligibility = "কৃষক, মহিলা স্বনির্ভর গোষ্ঠী ও মৌচাষে আগ্রহী ব্যক্তিবর্গ।",
                benefits = "মৌবাক্স, কলোনি এবং মধু নিষ্কাশন যন্ত্রের ওপর ৮০% পর্যন্ত সরকারি অনুদান।",
                amount = "মহিলা/তফসিলিদের ৮০%, অন্যদের ৫০% অনুদান",
                howToApply = "nbb.gov.in পোর্টালে অথবা জেলা উদ্যানপালন অফিসে যোগাযোগ করুন।"
            ),
            "midh" to SchemeTranslation(
                title = "সমন্বিত উদ্যানপালন উন্নয়ন মিশন (এমআইডিএইচ)",
                description = "ফল, সবজি, মশলা, ফুল, পলিহাউস ও কোল্ড স্টোরেজের সার্বিক উন্নয়নে সহায়তা।",
                eligibility = "উদ্যানজাত ফসল চাষকারী ব্যক্তিগত কৃষক ও এফপিও।",
                benefits = "নতুন বাগান তৈরি, শেড-নেট পলিহাউস ও প্যাকিং হাউসের জন্য ৪০-৫০% আর্থিক অনুদান।",
                amount = "৪০% থেকে ৫০% মূলধনী ভর্তুকি",
                howToApply = "জেলা উদ্যানপালন আধিকারিক বা রাজ্য উদ্যানপালন পোর্টালে আবেদন করুন।"
            ),
            "pm_aasha" to SchemeTranslation(
                title = "পিএম-আশা (অন্নদাতা আয় সংরক্ষণ অভিযান)",
                description = "ডাল, তৈলবীজ এবং নারকেলের জন্য ন্যায্য ন্যূনতম সহায়ক মূল্য (এমএসপি) নিশ্চিতকরণ প্রকল্প।",
                eligibility = "অধিভুক্ত তৈলবীজ ও ডাল চাষকারী সকল নিবন্ধিত কৃষক।",
                benefits = "এমএসপিতে সরাসরি সরকারি ক্রয় বা বাজার দরের ঘাটতি মূল্য সরাসরি ব্যাংক অ্যাকাউন্টে জমা।",
                amount = "সম্পূর্ণ এমএসপি মূল্য প্রাপ্তি",
                howToApply = "ফসল কাটার আগে রাজ্য সংগ্রহ পোর্টালে কৃষক নিবন্ধন সম্পন্ন করুন।"
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "মহাত্মা জ্যোতিরাও ফুলে কৃষক ঋণমুক্তি প্রকল্প",
                description = "মহারাষ্ট্রের কৃষকদের ₹২ লাখ পর্যন্ত বকেয়া ফসল ঋণ সম্পূর্ণ মকুব করার প্রকল্প।",
                eligibility = "মহারাষ্ট্রের কৃষক যাদের সমবায় বা বাণিজ্যিক ব্যাংকে ₹২ লাখ পর্যন্ত ফসল ঋণ রয়েছে।",
                benefits = "₹২ লাখ পর্যন্ত সম্পূর্ণ ঋণ মকুব এবং নিয়মিত ঋণ পরিশোধকারীদের ₹৫০,০০০ আর্থিক প্রণোদনা।",
                amount = "₹২ লাখ পর্যন্ত ঋণ মকুব + ₹৫০,০০০ পুরস্কার",
                howToApply = "MahaDBT পোর্টাল বা স্থানীয় তহসিলদার অফিসে আধার সহ আবেদন করুন।"
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "নানাজী দেশমুখ কৃষি সঞ্জীবনী যোজনা (পোক্রা)",
                description = "মহারাষ্ট্রের খরাপ্রবণ ও লবণাক্ত অঞ্চলে জলবায়ু-সহনশীল কৃষি উন্নয়ন প্রকল্প।",
                eligibility = "মারাঠওয়াড়া ও বিদর্ভের ১৫টি খরাপ্রবণ জেলার ক্ষুদ্র ও প্রান্তিক কৃষক।",
                benefits = "ফার্ম পন্ড, ড্রিপ সেচ, শেড-নেট এবং মাটি সংস্কারে ৭৫% পর্যন্ত অনুদান।",
                amount = "জল সংরক্ষণ ও সেচে ৭৫% পর্যন্ত অনুদান",
                howToApply = "mahapocra.gov.in পোর্টালে বা গ্রাম কৃষি বিকাশ সমিতিতে আবেদন করুন।"
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "পানি বাঁচাও পয়সা কামাও যোজনা (জল বাঁচাও প্রকল্প)",
                description = "কৃষি টিউবওয়েলে ভূগর্ভস্থ জল ও বিদ্যুৎ বাঁচানোর জন্য কৃষকদের সরাসরি নগদ প্রণোদনা।",
                eligibility = "মিটারযুক্ত টিউবওয়েল সংযোগধারী পাঞ্জাবের কৃষক।",
                benefits = "বিদ্যুৎ কোটা বাঁচালে প্রতি কিলোওয়াট-ঘণ্টা ইউনিটে ₹৪ সরাসরি ব্যাংক অ্যাকাউন্টে জমা।",
                amount = "বিদ্যুৎ বাঁচালে ₹৪ প্রতি ইউনিট",
                howToApply = "পাঞ্জাব স্টেট পাওয়ার কর্পোরেশন (PSPCL) এর সাব-ডিভিশনে নিবন্ধন করুন।"
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "ইউপি কিষাণ উদয় যোজনা",
                description = "উত্তরপ্রদেশের কৃষকদের বিনামূল্যে বিদ্যুৎ সাশ্রয়ী স্মার্ট ও সোলার পাম্পসেট বিতরণ।",
                eligibility = "উত্তরপ্রদেশের ক্ষুদ্র ও প্রান্তিক কৃষক যাদের সেচের জন্য পাম্প প্রয়োজন।",
                benefits = "২ থেকে ৫ হর্সপাওয়ারের স্মার্ট সোলার পাম্পের বিনামূল্যে ইনস্টলেশন ও ৫ বছরের ফ্রি সার্ভিসিং।",
                amount = "১০০% বিনামূল্যে সোলার পাম্প বিতরণ",
                howToApply = "upagriculture.com পোর্টালে অনলাইনে আবেদন করুন।"
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "ভাবান্তর অর্থপ্রদান যোজনা (ভাবান্তর ভুক্তান)",
                description = "মান্ডির দর এমএসপির চেয়ে কমে গেলে মূল্যের পার্থক্য সরাসরি কৃষকের ব্যাংক অ্যাকাউন্টে প্রদান।",
                eligibility = "এমপি ই-উপার্জন পোর্টালে নিবন্ধিত মধ্যপ্রদেশের কৃষক।",
                benefits = "এমএসপি এবং গড় বিক্রয় মূল্যের পার্থক্যের টাকা সরাসরি অ্যাকাউন্টে জমা।",
                amount = "প্রতি কুইন্টালে ঘাটতি মূল্য",
                howToApply = "mpeuparjan.nic.in পোর্টালে ফসল কাটার আগে নিবন্ধন করুন।"
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "রায়তু বন্ধু যোজনা (কৃষক বিনিয়োগ সহায়তা)",
                description = "চাষের খরচের জন্য বছরে প্রতি একরে ₹১০,০০০ সরাসরি আর্থিক সহায়তা।",
                eligibility = "তেলেঙ্গানার জমির পাট্টাদার সকল কৃষক পরিবার।",
                benefits = "খরিফে ₹৫,০০০ এবং রবিতে ₹৫,০০০ প্রতি একরে সরাসরি ব্যাংক একাউন্টে জমা।",
                amount = "প্রতি বছর একর প্রতি ₹১০,০০০",
                howToApply = "ধরণী পোর্টালে জমির রেকর্ডের ভিত্তিতে স্বয়ংক্রিয় তালিকাভুক্তিকরণ।"
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "তামিলনাড়ু বিনামূল্যে কৃষি বিদ্যুৎ প্রকল্প",
                description = "তামিলনাড়ুর কৃষকদের জন্য সেচের পাম্পসেটে ২৪ ঘণ্টা সম্পূর্ণ বিনামূল্যে বিদ্যুৎ সরবরাহ।",
                eligibility = "তামিলনাড়ুর সকল নিবন্ধিত কৃষি পাম্পসেট গ্রাহক।",
                benefits = "কৃষি কাজের জন্য কোনো বিল বা মিটার চার্জ ছাড়াই ১০০% বিনামূল্যে বিদ্যুৎ।",
                amount = "১০০% বিনামূল্যে কৃষি বিদ্যুৎ",
                howToApply = "তামিলনাড়ু বিদ্যুৎ পর্ষদে (TANGEDCO) কৃষি সংযোগের জন্য আবেদন করুন।"
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "রাইতা সিরি যোজনা (মিলেট ও ফসল সমৃদ্ধি)",
                description = "দানাশস্য বা মিলেট চাষে আর্থিক উৎসাহ এবং সমবায় ব্যাংকের মাধ্যমে ₹৩ লাখ পর্যন্ত শূন্য সুদে ঋণ।",
                eligibility = "কর্ণাটকের মিলেট চাষী ও কৃষক পরিবার।",
                benefits = "মিলেট চাষের জন্য হেক্টর প্রতি ₹১০,০০০ অনুদান এবং ০% সুদে ফসল ঋণ।",
                amount = "₹১০,০০০/হেক্টর অনুদান + ০% সুদে ঋণ",
                howToApply = "কর্ণাটক রাইতা মিত্র পোর্টালে (raitamitra.karnataka.gov.in) আবেদন করুন।"
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "কিষাণ সূর্যোদয় যোজনা",
                description = "গুজরাটের কৃষকদের দিনের বেলায় (সকাল ৫টা থেকে রাত ৯টা) সেচের জন্য নিয়মিত বিদ্যুৎ সরবরাহ।",
                eligibility = "গুজরাটের কৃষি বিদ্যুৎ গ্রিডের অন্তর্ভুক্ত সকল কৃষক।",
                benefits = "দিনের আলোয় নিরাপদ বিদ্যুৎ সংযোগ, যাতে রাতের অন্ধকারে সেচ দেওয়ার ঝুঁকি না থাকে।",
                amount = "বিনামূল্যে সরকারি পরিকাঠামো ব্যবস্থা",
                howToApply = "বিদ্যুৎ বণ্টন কোম্পানির (DISCOM) অধীনে স্বয়ংক্রিয় পরিষেবা।"
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "রাজস্থান কিষাণ মিত্র শক্তি যোজনা",
                description = "কৃষি বিদ্যুৎ বিলে প্রতি মাসে সর্বোচ্চ ₹১,০০০ পর্যন্ত সরাসরি সরকারি ভর্তুকি।",
                eligibility = "রাজস্থানের মিটারযুক্ত কৃষি বিদ্যুৎ সংযোগধারী কৃষকগণ।",
                benefits = "বিদ্যুৎ বিলে মাসে ₹১,০০০ (বছরে ₹১২,০০০ পর্যন্ত) সরাসরি ছাড়।",
                amount = "বছরে সর্বোচ্চ ₹১২,০০০ বিদ্যুৎ ভর্তুকি",
                howToApply = "বিদ্যুৎ বিলের ওপর সরাসরি অটো-অ্যাডজাস্টমেন্ট সুবিধা।"
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "বিহার ডিজেল অনুদান যোজনা",
                description = "অনাবৃষ্টি ও খরার সময় সেচের জন্য ডিজেল ক্রয়ে সরাসরি সরকারি ভর্তুকি প্রদান।",
                eligibility = "বিহারের রায়ত ও অ-রায়ত (ভাগচাষী) কৃষকগণ।",
                benefits = "প্রতি লিটার ডিজেলে ₹৭৫ ভর্তুকি (প্রতি একরে সেচ প্রতি ₹৭৫০, সর্বোচ্চ ৩ বার সেচ)।",
                amount = "₹৭৫/লিটার (সর্বোচ্চ ₹২,২৫০/একর)",
                howToApply = "ডিবিটি এগ্রিকালচার বিহার (dbtagriculture.bihar.gov.in) পোর্টালে অনলাইনে আবেদন করুন।"
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "কৃষক বন্ধু যোজনা",
                description = "পশ্চিমবঙ্গের সকল কৃষকদের বছরে ₹১০,০০০ আর্থিক অনুদান এবং ₹২ লাখ পর্যন্ত মৃত্যু বীমা সুরক্ষা।",
                eligibility = "পশ্চিমবঙ্গের সকল নথিবদ্ধ কৃষক ও নথিভুক্ত বর্গাদার (বয়স ১৮ থেকে ৬০ বছর)।",
                benefits = "বছরে দুই কিস্তিতে ₹১০,০০০ আর্থিক সহায়তা এবং কৃষকের মৃত্যুতে পরিবারকে ₹২ লাখ এককালীন বীমা।",
                amount = "প্রতি বছর ₹১০,০০০ + ₹২,০০,০০০ বীমা",
                howToApply = "নিকটস্থ বিডিও (BDO) অফিস বা krishakbandhu.net পোর্টালে আবেদন করুন।"
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "ওয়াইএসআর রায়তু ভরসা - পিএম কিসান",
                description = "অন্ধ্রপ্রদেশে ভাগচাষী সহ সকল কৃষক পরিবারকে প্রতি বছর ₹১৩,৫০০ আর্থিক বিনিয়োগ সহায়তা।",
                eligibility = "অন্ধ্রপ্রদেশের সকল জমির মালিক ও তফসিলি/অনগ্রসর শ্রেণির ভাগচাষী কৃষক।",
                benefits = "বীজ, সার ও চাষের খরচের জন্য বছরে ৩টি কিস্তিতে মোট ₹১৩,৫০০ সরাসরি ব্যাংকে জমা।",
                amount = "প্রতি বছর ₹১৩,৫০০",
                howToApply = "রায়তু ভরসা কেন্দ্র (RBK) বা গ্রাম সচিবালয়ে নাম নথিভুক্ত করুন।"
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "হরিয়ানা ভাবান্তর ভরপাই যোজনা",
                description = "শাকসবজি ও উদ্যানজাত ফসলের বাজার দর কমে গেলে কৃষকদের ক্ষতিপূরণ দেওয়ার প্রকল্প।",
                eligibility = "মেরি ফসল মেরা ব্যোরা পোর্টালে নিবন্ধিত হরিয়ানার উদ্যানপালন কৃষক।",
                benefits = "সংরক্ষিত ভিত্তি মূল্য এবং মান্ডি বিক্রয় মূল্যের পার্থক্যের টাকা সরাসরি অ্যাকাউন্টে জমা।",
                amount = "প্রতি কুইন্টালে ঘাটতি মূল্য",
                howToApply = "ফসল বোনার আগে fasal.haryana.gov.in পোর্টালে নাম নথিভুক্ত করুন।"
            ),
            "od_kalia" to SchemeTranslation(
                title = "কালিয়া যোজনা (কৃষক সহায়তা ও জীবিকা উন্নয়ন)",
                description = "ওড়িশার ক্ষুদ্র, প্রান্তিক ও ভূমিহীন কৃষক পরিবারের জন্য ব্যাপক আর্থিক সহায়তা ও বীমা প্রকল্প।",
                eligibility = "ওড়িশার ক্ষুদ্র/প্রান্তিক কৃষক এবং ভূমিহীন কৃষি শ্রমিক পরিবার।",
                benefits = "চাষের জন্য ₹১০,০০০/বছর, ভূমিহীনদের জন্য ₹১২,৫০০ এবং ₹২ লাখের জীবন বীমা।",
                amount = "বছরে ₹১০,০০০ + জীবিকা অনুদান",
                howToApply = "kalia.odisha.gov.in পোর্টাল বা পঞ্চায়েত অফিসে আবেদন করুন।"
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "কেরালা রাজ্য সার্বিক ফসল বীমা যোজনা",
                description = "বন্যা, ভূমিধস ও বন্যপ্রাণীর আক্রমণে ক্ষতিপূরণের জন্য ২৫+ প্রধান ফসলের রাজ্য স্তরের বীমা।",
                eligibility = "কেরালার ধান, কলা, মশলা, সবজি ও রাবার চাষকারী সকল কৃষক।",
                benefits = "প্রাকৃতিক দুর্যোগ ও বন্যপ্রাণীর ফসলের ক্ষতিতে দ্রুত ক্ষতিপূরণ ও নামমাত্র প্রিমিয়াম।",
                amount = "ফসল অনুযায়ী প্রতি একরে ₹৩৫,০০০ পর্যন্ত ক্ষতিপূরণ",
                howToApply = "AIMS পোর্টালে (aims.kerala.gov.in) বা স্থানীয় কৃষি ভবনে আবেদন করুন।"
            ),
        ),
        "mr" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "पीएम-किसान (प्रधानमंत्री किसान सन्मान निधी)",
                description = "पात्र शेतकरी कुटुंबांना दरवर्षी ₹६,००० ची थेट आर्थिक मदत, ₹२,००० च्या ३ समान हप्त्यांमध्ये थेट बँक खात्यात जमा केली जाते.",
                eligibility = "शेतीयोग्य जमीन असणारी सर्व शेतकरी कुटुंबे. संस्थात्मक भूधारक, करदाते आणि घटनात्मक पदधारक वगळता.",
                benefits = "दर ४ महिन्यांनी ₹२,००० चे ३ हप्ते, वर्षाला एकूण ₹६,००० थेट बँक खात्यात जमा.",
                amount = "दरवर्षी ₹६,०००",
                howToApply = "जवळचे सीएससी (CSC) केंद्र किंवा pmkisan.gov.in पोर्टलवर ७/१२ आणि आधार कार्डासह नोंदणी करा."
            ),
            "pmfby" to SchemeTranslation(
                title = "पीएमएफबीवाय (प्रधानमंत्री पीक विमा योजना)",
                description = "नैसर्गिक आपत्ती, कीड व रोगांमुळे पिकांचे नुकसान झाल्यास अन्नधान्य, गळीत धान्य आणि फळबागांना सर्वसमावेशक विमा संरक्षण.",
                eligibility = "अधिसूचित पिके घेणारे सर्व खातेदार आणि कुळ शेतकरी (कर्जदार व बिगर-कर्जदार शेतकरी).",
                benefits = "विमा संरक्षित रकमेचे संपूर्ण नुकसानभरपाई. शेतकरी हिस्सा: खरीप २%, रब्बी १.५%, फळबागा ५%. उर्वरित हप्ता सरकार भरते.",
                amount = "पीक आणि क्षेत्रानुसार विमा संरक्षित रक्कम",
                howToApply = "जवळची बँक शाखा, सीएससी किंवा pmfby.gov.in पोर्टलवर विहित मुदतीत अर्ज करा."
            ),
            "kcc" to SchemeTranslation(
                title = "किसान क्रेडिट कार्ड (केसीसी)",
                description = "पीक उत्पादन, कापणीनंतरचा खर्च व घरगुती गरजांसाठी अत्यंत कमी व्याजदरात अल्पमुदत पीक कर्ज सुविधा.",
                eligibility = "सर्व वैयक्तिक व संयुक्त शेतकरी, कुळ शेतकरी आणि बचत गटांचे शेतकरी सदस्य.",
                benefits = "४% सवलतीच्या व्याजदरात कर्ज (नियमित परताव्यावर ३% व्याज परतावा). पीक विमा व एटीएम कार्ड सुविधा.",
                amount = "४% व्याजदरावर ₹३ लाखांपर्यंत पीक कर्ज",
                howToApply = "जमिनीचा ७/१२, ८-अ, आधार कार्ड व फोटोसह कोणत्याही व्यावसायिक, ग्रामीण किंवा जिल्हा सहकारी बँकेत अर्ज करा."
            ),
            "soil_health" to SchemeTranslation(
                title = "मृदा आरोग्य पत्रिका योजना (सॉईल हेल्थ कार्ड)",
                description = "जमिनीची सुपिकता तपासून पिकांच्या गरजेनुसार योग्य खते आणि पोषक तत्वांचा संतुलित वापर करण्यासाठी मार्गदर्शन.",
                eligibility = "शेतीजमीन असणारे राज्यातील सर्व शेतकरी.",
                benefits = "मोफत माती परीक्षण, १२ पोषक घटकांची सविस्तर माहिती आणि २ वर्षांसाठी खत व्यवस्थापनाची शिफारस.",
                amount = "पूर्णपणे मोफत",
                howToApply = "जवळची माती परीक्षण प्रयोगशाळा, कृषी विज्ञान केंद्र (KVK) किंवा soilhealth.dac.gov.in वर संपर्क साधा."
            ),
            "pmksy" to SchemeTranslation(
                title = "पीएम कृषी सिंचन योजना (हर शेताला पाणी)",
                description = "प्रत्येक शेतापर्यंत पाण्याची पोहोच सुनिश्चित करणे आणि ठिबक/तुषार सिंचनाद्वारे पाणी वापर क्षमता वाढवणे.",
                eligibility = "सर्व शेतकरी. दुष्काळग्रस्त, अवर्षणप्रवण आणि आदिवासी भागांना विशेष प्राधान्य.",
                benefits = "ठिबक व तुषार सिंचनासाठी अल्प/अल्पभूधारक शेतकऱ्यांना ५५% आणि इतर शेतकऱ्यांना ४५% शासकीय अनुदान.",
                amount = "लहान शेतकऱ्यांना ५५%, इतरांना ४५% अनुदान",
                howToApply = "महाडीबीटी (MahaDBT) पोर्टलवर किंवा तालुका कृषी अधिकारी कार्यालयात अर्ज करा."
            ),
            "pkvy" to SchemeTranslation(
                title = "परंपरागत कृषी विकास योजना (पीकेव्हीवाय)",
                description = "क्लस्टर पद्धतीद्वारे सेंद्रिय शेतीला प्रोत्साहन देणे आणि शेतकऱ्यांना पीजीएस सेंद्रिय प्रमाणीकरण मिळवून देणे.",
                eligibility = "सेंद्रिय शेतीसाठी ५० एकरचे क्लस्टर तयार करणारे ५० किंवा अधिक शेतकऱ्यांचे गट.",
                benefits = "सेंद्रिय खते, कीटकनाशके व विक्री व्यवस्थेसाठी ३ वर्षांत प्रति हेक्टरी ₹५०,००० ची आर्थिक मदत.",
                amount = "₹५०,००० प्रति हेक्टर (३ वर्षांत)",
                howToApply = "५० शेतकऱ्यांचा गट तयार करून जिल्हा कृषी अधीक्षक कार्यालयात अर्ज करा."
            ),
            "enam" to SchemeTranslation(
                title = "ई-नाम (राष्ट्रीय कृषी बाजार)",
                description = "कृषी मालाच्या खरेदी-विक्रीसाठी ऑनलाइन इलेक्ट्रॉनिक मंच, जो देशभरातील कृषी उत्पन्न बाजार समित्यांना जोडतो.",
                eligibility = "बाजार समित्यांमध्ये नोंदणीकृत सर्व शेतकरी, व्यापारी आणि अडते.",
                benefits = "पारदर्शक भाव, मध्यस्थांशिवाय विक्री, चांगला मोबदला आणि थेट बँक खात्यात ऑनलाइन पेमेंट.",
                amount = "शेतकऱ्यांसाठी पूर्णपणे मोफत",
                howToApply = "enam.gov.in वर नोंदणी करा किंवा जवळच्या ई-नाम बाजार समितीमध्ये आधार व बँक पासबुकसह जा."
            ),
            "pm_kusum" to SchemeTranslation(
                title = "पीएम-कुसूम (सौर कृषी पंप योजना)",
                description = "शेतकऱ्यांच्या शेतात स्वतंत्र सोलर पंप बसवणे आणि वीज पंपांचे सौर ऊर्जेवर रूपांतर करणे.",
                eligibility = "सर्व वैयक्तिक शेतकरी, शेतकरी गट, एफपीओ आणि ग्रामपंचायती.",
                benefits = "सोलर पंपासाठी ६०% शासकीय अनुदान (३०% केंद्र + ३०% राज्य). शेतकऱ्याला फक्त १०% रक्कम भरावी लागते.",
                amount = "एकूण ६०% ते ९०% पर्यंत शासकीय अनुदान",
                howToApply = "महाऊर्जा (MEDA) किंवा महावितरणच्या अधिकृत पोर्टलवरून ऑनलाइन अर्ज करा."
            ),
            "nmsa" to SchemeTranslation(
                title = "राष्ट्रीय शाश्वत शेती अभियान (एनएमएसए)",
                description = "हवामान बदलास अनुकूल शेती पद्धती, जलसंधारण आणि कोरडवाहू शेतीतील जमिनीची उत्पादकता वाढवणे.",
                eligibility = "सर्व शेतकरी, विशेषतः कोरडवाहू व अवर्षणग्रस्त भागातील शेतकरी.",
                benefits = "शेततळे अस्तरीकरण, सेंद्रिय खत निर्मिती, गांडूळ खत आणि जलसंधारण कामांवर अनुदान.",
                amount = "कोरडवाहू विकासासाठी ₹१२,५००/हेक्टर मदत",
                howToApply = "तालुका कृषी अधिकारी किंवा कृषी सहाय्यकांमार्फत अर्ज करा."
            ),
            "rkvy" to SchemeTranslation(
                title = "राष्ट्रीय कृषी विकास योजना (आरकेव्हीवाय-रफ्तार)",
                description = "कृषी पायाभूत सुविधा, कृषी प्रक्रिया आणि नवउद्योजक कृषी स्टार्टअप्सना आर्थिक पाठबळ देणे.",
                eligibility = "शेतकरी, शेतकरी उत्पादक कंपन्या (FPO) आणि कृषी स्टार्टअप्स.",
                benefits = "कापणीनंतरच्या प्रक्रिया प्रकल्पांना आणि कृषी स्टार्टअपना ₹२५ लाखांपर्यंतचे अनुदान.",
                amount = "स्टार्टअप अनुदान ₹२५ लाखांपर्यंत",
                howToApply = "rkvy.nic.in पोर्टलवर किंवा कृषी विभागाच्या योजनांमधून अर्ज करा."
            ),
            "agri_infra" to SchemeTranslation(
                title = "कृषी पायाभूत सुविधा निधी (एआयएफ)",
                description = "गोदामे, शीतगृहे, प्रतवारी व पॅक-हाउस यांसारख्या काढणीपश्चात पायाभूत सुविधांसाठी सवलतीच्या दरात कर्ज.",
                eligibility = "शेतकरी, एफपीओ, प्राथमिक कृषी पतसंस्था (PACS) आणि कृषी उद्योजक.",
                benefits = "₹२ कोटींपर्यंतच्या कर्जावर ७ वर्षांसाठी दरवर्षी ३% व्याज परतावा आणि पत हमी संरक्षण.",
                amount = "₹२ कोटींपर्यंत कर्जावर ३% व्याज सवलत",
                howToApply = "agriinfra.dac.gov.in पोर्टलवर ऑनलाइन प्रकल्प प्रस्ताव दाखल करा."
            ),
            "smam" to SchemeTranslation(
                title = "कृषी यांत्रिकीकरण उप-अभियान (एसएमएएम)",
                description = "ट्रॅक्टर, रोटाव्हेटर, हार्वेस्टर व आधुनिक कृषी अवजारांच्या खरेदीसाठी थेट शासकीय अनुदान.",
                eligibility = "अल्प, अत्यल्प भूधारक, महिला, अनुसूचित जाती/जमातीचे शेतकरी आणि ग्रामीण युवक.",
                benefits = "कृषी यंत्रे व अवजारांवर ४०% ते ५०% थेट बँक खात्यात अनुदान.",
                amount = "यंत्रांवर ४०% ते ५०% अनुदान",
                howToApply = "MahaDBT पोर्टलवर 'कृषी यांत्रिकीकरण' घटकांतर्गत ऑनलाइन अर्ज करा."
            ),
            "nbhm" to SchemeTranslation(
                title = "राष्ट्रीय मधमाशी पालन व मध अभियान (एनबीएचएम)",
                description = "उत्पन्न वाढ, परागीभवन सुधारणा आणि दर्जेदार मध उत्पादनासाठी वैज्ञानिक मधमाशी पालनाला प्रोत्साहन.",
                eligibility = "शेतकरी, महिला बचत गट आणि मधमाशी पालन व्यावसायिक.",
                benefits = "मधमाशी पेट्या, मधमाश्यांचे पोळे व मध काढणी यंत्रांवर ८०% पर्यंत अनुदान.",
                amount = "महिला/मागास प्रवर्गासाठी ८०%, इतरांना ५०% अनुदान",
                howToApply = "nbb.gov.in पोर्टलवर किंवा जिल्हा अधीक्षक कृषी अधिकारी कार्यालयात संपर्क साधा."
            ),
            "midh" to SchemeTranslation(
                title = "एकात्मिक फलोत्पादन विकास अभियान (एमआयडीएच)",
                description = "फळे, भाजीपाला, फुले, मसाले, शेडनेट हाऊस व फळ प्रक्रिया उद्योगांचा सर्वांगीण विकास.",
                eligibility = "फलोत्पादन करणारे सर्व शेतकरी, बचत गट आणि एफपीओ.",
                benefits = "नवीन फळबाग लागवड, हरितगृह, शेडनेट आणि शीतगृहासाठी ४०% ते ५०% आर्थिक अनुदान.",
                amount = "४०% ते ५०% भांडवली अनुदान",
                howToApply = "MahaDBT पोर्टलवर फलोत्पादन विभागाच्या योजनांमध्ये अर्ज करा."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "पीएम-आशा (अन्नदाता उत्पन्न संरक्षण अभियान)",
                description = "कडधान्ये, गळीत धान्य आणि खोबऱ्यासाठी हमीभाव (MSP) मिळवून देणारी संरक्षण योजना.",
                eligibility = "अधिसूचित कडधान्य व तेलबिया पिके घेणारे सर्व नोंदणीकृत शेतकरी.",
                benefits = "हमीभावाने सरकारी खरेदी किंवा बाजारभाव हमीभावापेक्षा कमी असल्यास फरक रक्कम बँक खात्यात.",
                amount = "पूर्ण हमीभाव (MSP) प्राप्ती",
                howToApply = "पीक निघण्यापूर्वी महा-ई-उपार्जन पोर्टलवर शेतकरी नोंदणी करा."
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "महात्मा ज्योतिराव फुले शेतकरी कर्जमुक्ती योजना",
                description = "महाराष्ट्रातील शेतकऱ्यांचे ₹२ लाखांपर्यंतचे थकीत पीक कर्ज माफ करणारी योजना.",
                eligibility = "महाराष्ट्रातील शेतकरी ज्यांचे राष्ट्रीयीकृत, व्यापारी किंवा जिल्हा मध्यवर्ती बँकेत ₹२ लाखांपर्यंत पीक कर्ज थकीत आहे.",
                benefits = "₹२ लाखांपर्यंतचे पीक कर्ज पूर्णपणे माफ आणि नियमित कर्जफेड करणाऱ्या शेतकऱ्यांना ₹५०,००० प्रोत्साहनपर अनुदान.",
                amount = "₹२ लाखांपर्यंत कर्जमाफी + ₹५०,००० प्रोत्साहन",
                howToApply = "MahaDBT पोर्टल किंवा तहसीलदार कार्यालयात आधार प्रमाणीकरणासह लाभ घ्या."
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "नानाजी देशमुख कृषी संजीवनी योजना (पोकरा)",
                description = "विदर्भ व मराठवाड्यातील दुष्काळग्रस्त आणि क्षारपड जमिनीतील शेतकऱ्यांसाठी हवामान-अनुकूल कृषी प्रकल्प.",
                eligibility = "मराठवाडा व विदर्भातील १५ जिल्ह्यांतील अल्प व अत्यल्प भूधारक शेतकरी.",
                benefits = "शेततळे, ठिबक सिंचन, शेडनेट हाऊस, सेंद्रिय खते व गांडूळ खत युनिटवर ७५% पर्यंत अनुदान.",
                amount = "जलसंधारण व ठिबकवर ७५% पर्यंत अनुदान",
                howToApply = "mahapocra.gov.in किंवा डीबीटी पोकरा ॲपद्वारे नोंदणी करा."
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "पाणी वाचवा पैसे कमवा योजना",
                description = "कृषी पंपांवर वीज आणि भूजल वाचवल्याबद्दल शेतकऱ्यांना रोख मोबदला देणारी योजना.",
                eligibility = "पंजाबमधील मीटर असलेल्या कृषी वीज जोडणीधारक शेतकरी.",
                benefits = "विजेची बचत केल्यास प्रति युनिट ₹४ थेट बँक खात्यात जमा.",
                amount = "₹४ प्रति युनिट वीज बचत",
                howToApply = "पंजाब स्टेट पॉवर कॉर्पोरेशनकडे (PSPCL) नोंदणी करा."
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "यूपी किसान उदय योजना",
                description = "उत्तर प्रदेशातील शेतकऱ्यांना ऊर्जा-कार्यक्षम मोफत स्मार्ट सोलर पंपांचे वितरण.",
                eligibility = "उत्तर प्रदेशातील लहान व सीमांत शेतकरी ज्यांना सिंचनाची गरज आहे.",
                benefits = "२ ते ५ एचपी क्षमतेचे स्मार्ट सोलर पंप मोफत बसवणे व ५ वर्षे मोफत देखभाल.",
                amount = "१००% मोफत सोलर पंप वाटप",
                howToApply = "upagriculture.com पोर्टलवर ऑनलाइन अर्ज करा."
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "भावांतर भरपाई योजना (मध्य प्रदेश)",
                description = "बाजारभाव हमीभावापेक्षा कमी झाल्यास फरकाची रक्कम थेट शेतकऱ्याच्या बँक खात्यात जमा.",
                eligibility = "एमपी ई-उपार्जन पोर्टलवर नोंदणीकृत मध्य प्रदेशातील शेतकरी.",
                benefits = "हमीभाव आणि बाजार विक्री भावातील फरकाची रक्कम थेट खात्यात.",
                amount = "प्रति क्विंटल भावांतर रक्कम",
                howToApply = "mpeuparjan.nic.in वर पिकांची पूर्वनोंदणी करा."
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "रयथू बंधू योजना (शेतकरी गुंतवणूक सहाय्य)",
                description = "शेती खर्चासाठी प्रतिवर्षी प्रति एकर ₹१०,००० ची थेट आर्थिक मदत.",
                eligibility = "तेलंगणातील सर्व पट्टेदार शेतकरी कुटुंबे.",
                benefits = "खरीप हंगामासाठी ₹५,००० व रब्बीसाठी ₹५,००० थेट बँक खात्यात जमा.",
                amount = "प्रति एकर प्रति वर्ष ₹१०,०००",
                howToApply = "धरणी पोर्टलवरील नोंदींनुसार थेट बँक खात्यात जमा."
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "तमिळनाडू मोफत शेती वीज योजना",
                description = "तमिळनाडूमध्ये शेती पंपांसाठी २४ तास पूर्णपणे मोफत वीजपुरवठा.",
                eligibility = "तमिळनाडूतील सर्व नोंदणीकृत कृषी वीज ग्राहक.",
                benefits = "कोणत्याही बिलाशिवाय शेती सिंचनासाठी १००% मोफत वीज.",
                amount = "१००% मोफत कृषी वीज",
                howToApply = "तमिळनाडू वीज मंडळाकडे (TANGEDCO) अर्ज करा."
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "रैता सिरी योजना (तृणधान्य समृद्धी)",
                description = "पौष्टिक तृणधान्य (मिलेट्स) लागवडीसाठी प्रोत्साहन आणि ₹३ लाखांपर्यंत शून्य टक्के व्याजदरात पीक कर्ज.",
                eligibility = "कर्नाटकातील तृणधान्य उत्पादक शेतकरी.",
                benefits = "तृणधान्य लागवडीवर हेक्टरी ₹१०,००० अनुदान आणि ०% व्याजाने पीक कर्ज.",
                amount = "₹१०,०००/हेक्टर + ०% व्याजाने कर्ज",
                howToApply = "कर्नाटक रैता मित्र पोर्टलवर (raitamitra.karnataka.gov.in) अर्ज करा."
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "किसान सूर्योदय योजना",
                description = "गुजरातमधील शेतकऱ्यांना दिवसा (सकाळी ५ ते रात्री ९) सिंचनासाठी भरवशाचा वीजपुरवठा.",
                eligibility = "गुजरातमधील शेती वीज जोडणी असलेले सर्व शेतकरी.",
                benefits = "दिवसा सुरक्षित वीज मिळाल्यामुळे रात्रीच्या वेळी शेतात पाणी देण्याचा त्रास टळतो.",
                amount = "मोफत शासकीय पायाभूत सुविधा",
                howToApply = "वीज वितरण कंपन्यांतर्गत (DISCOMs) थेट अंमलबजावणी."
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "राजस्थान किसान मित्र ऊर्जा योजना",
                description = "शेतकऱ्यांच्या कृषी वीज बिलावर दरमहा ₹१,००० पर्यंत थेट शासकीय अनुदान.",
                eligibility = "राजस्थानमधील मीटरयुक्त शेती वीज ग्राहक.",
                benefits = "वीज बिलात दरमहा ₹१,००० (वर्षाला ₹१२,००० पर्यंत) थेट सवलत.",
                amount = "वर्षाला ₹१२,००० पर्यंत वीज सबसिडी",
                howToApply = "वीज वितरण कंपन्यांकडून चालू बिलांमध्ये थेट वजावट."
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "बिहार डिझेल अनुदान योजना",
                description = "दुष्काळ व अपुऱ्या पावसाच्या काळात सिंचनासाठी डिझेल खरेदीवर थेट शासकीय अनुदान.",
                eligibility = "बिहारमधील खातेदार आणि कुळ शेतकरी.",
                benefits = "₹७५ प्रति लिटर डिझेल अनुदान (एका सिंचनासाठी कमाल ₹७५०/एकर, ३ वेळेपर्यंत).",
                amount = "₹७५/लिटर (कमाल ₹२,२५०/एकर)",
                howToApply = "DBT Agriculture Bihar (dbtagriculture.bihar.gov.in) वर ऑनलाइन अर्ज करा."
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "कृषक बंधू योजना",
                description = "पश्चिम बंगालमधील शेतकऱ्यांना दरवर्षी ₹१०,००० ची आर्थिक मदत आणि ₹२ लाखांचे जीवन विमा संरक्षण.",
                eligibility = "पश्चिम बंगालमधील नोंदणीकृत शेतकरी व बटाईदार (वय १८ ते ६० वर्षे).",
                benefits = "दोन हप्त्यांत ₹१०,००० ची मदत आणि मृत्यू झाल्यास कुटुंबास ₹२ लाखांची मदत.",
                amount = "दरवर्षी ₹१०,००० + ₹२ लाख विमा",
                howToApply = "बीडीओ (BDO) कार्यालय किंवा krishakbandhu.net वर अर्ज करा."
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "वायएसआर रयथू भरोसा - पीएम किसान",
                description = "आंध्र प्रदेशात कुळ शेतकऱ्यांसह सर्व शेतकरी कुटुंबांना दरवर्षी ₹१३,५०० ची आर्थिक मदत.",
                eligibility = "आंध्र प्रदेशातील सर्व भूधारक आणि मागासवर्गीय कुळ शेतकरी.",
                benefits = "बियाणे, खते व मशागतीसाठी ३ हप्त्यांत एकूण ₹१३,५०० बँक खात्यात जमा.",
                amount = "दरवर्षी ₹१३,५००",
                howToApply = "रयथू भरोसा केंद्र (RBK) किंवा ग्राम सचिवालयात नोंदणी करा."
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "हरियाणा भावांतर भरपाई योजना",
                description = "भाजीपाला व फलोत्पादन पिकांचे बाजारभाव कोसळल्यास नुकसान भरपाई देणारी योजना.",
                eligibility = "मेरी फसल मेरा ब्योरा पोर्टलवर नोंदणीकृत हरियाणातील फलोत्पादन शेतकरी.",
                benefits = "संरक्षित आधारभूत किंमत आणि प्रत्यक्ष विक्री भावातील फरक थेट खात्यात जमा.",
                amount = "प्रति क्विंटल भावांतर रक्कम",
                howToApply = "fasal.haryana.gov.in वर लागवडीपूर्वी नोंदणी करा."
            ),
            "od_kalia" to SchemeTranslation(
                title = "कालिया योजना (कृषक उपजीविका व उत्पन्न वाढ)",
                description = "ओडिशामधील अल्प, अत्यल्प व भूमिहीन शेतकरी कुटुंबांसाठी सर्वसमावेशक आर्थिक मदत.",
                eligibility = "ओडिशामधील लहान शेतकरी आणि भूमिहीन शेतमजूर कुटुंबे.",
                benefits = "शेतीसाठी ₹१०,०००/वर्ष, भूमिहीनांसाठी ₹१२,५०० आणि ₹२ लाखांचे विमा संरक्षण.",
                amount = "दरवर्षी ₹१०,००० + उपजीविका मदत",
                howToApply = "kalia.odisha.gov.in किंवा ग्रामपंचायत कार्यालयात अर्ज करा."
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "केरळ राज्य सर्वसमावेशक पीक विमा योजना",
                description = "पूर, दरड कोसळणे व वन्यजीवांमुळे पिकांचे नुकसान झाल्यास २५+ प्रमुख पिकांना राज्य विमा संरक्षण.",
                eligibility = "केरळमधील भात, केळी, मसाले, भाजीपाला व रबर उत्पादक शेतकरी.",
                benefits = "नैसर्गिक आपत्ती व वन्यप्राण्यांच्या नुकसानीवर तातडीची भरपाई व नाममात्र विमा हप्ता.",
                amount = "पिकानुसार एकरी ₹३५,০০০ पर्यंत भरपाई",
                howToApply = "AIMS पोर्टल (aims.kerala.gov.in) किंवा स्थानिक कृषी भवनात अर्ज करा."
            ),
        ),
        "te" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "పీఎం-కిసాన్ (ప్రధానమంత్రి కిసాన్ సమ్మాన్ నిధి)",
                description = "అర్హులైన రైతు కుటుంబాలకు సంవత్సరానికి ₹6,000 ప్రత్యక్ష ఆర్థిక సహాయం, ప్రతి 4 నెలలకు ఒకసారి ₹2,000 చొప్పున 3 సమాన వాయిదాలలో నేరుగా బ్యాంక్ ఖాతాలో జమ చేయబడుతుంది.",
                eligibility = "సాగుభూమి ఉన్న రైతు కుటుంబాలన్నీ అర్హులు. సంస్థాగత భూ యజమానులు మరియు ఆదాయపు పన్ను చెల్లింపుదారులు మినహాయింపు.",
                benefits = "సంవత్సరానికి 3 విడతలలో మొత్తం ₹6,000 నేరుగా బ్యాంక్ బదిలీ.",
                amount = "సంవత్సరానికి ₹6,000",
                howToApply = "సమీపంలోని సీఎస్‌సీ (CSC) కేంద్రం లేదా pmkisan.gov.in పోర్టల్ ద్వారా ఆధార్ మరియు పట్టాదారు పాస్‌బుక్ వివరాలతో నమోదు చేసుకోండి."
            ),
            "pmfby" to SchemeTranslation(
                title = "పీఎంఎఫ్‌బీవై (ప్రధానమంత్రి ఫసల్ బీమా యోజన)",
                description = "ప్రకృతి వైపరీత్యాలు, తెగుళ్లు మరియు వ్యాధుల వల్ల పంట నష్టం జరిగినప్పుడు ఆహార, నూనెగింజలు మరియు ఉద్యాన పంటలకు సమగ్ర బీమా రక్షణ.",
                eligibility = "నోటిఫై చేసిన పంటలను సాగు చేసే కౌలు రైతులు మరియు పట్టాదారులు (రుణ గ్రహీతలు మరియు ఇతరులు).",
                benefits = "పూర్తి బీమా మొత్తం పరిహారం. ప్రీమియం: ఖరీఫ్ 2%, రబీ 1.5%, ఉద్యాన పంటలు 5%. మిగిలిన ప్రీమియంను ప్రభుత్వం భరిస్తుంది.",
                amount = "పంట మరియు విస్తీర్ణం ఆధారంగా బీమా మొత్తం",
                howToApply = "సమీప బ్యాంక్ శాఖ, సీఎస్‌సీ కేంద్రం లేదా pmfby.gov.in పోర్టల్ ద్వారా గడువు ముగిసేలోపు దరఖాస్తు చేసుకోండి."
            ),
            "kcc" to SchemeTranslation(
                title = "కిసాన్ క్రెడిట్ కార్డ్ (కేసీసీ)",
                description = "పంట ఉత్పత్తి, సాగు ఖర్చులు మరియు పంట కోత అనంతర అవసరాల కోసం రాయితీ వడ్డీ రేటుతో స్వల్పకాలిక రుణ సదుపాయం.",
                eligibility = "వ్యక్తిగత/ఉమ్మడి రైతులు, కౌలు రైతులు మరియు స్వయం సహాయక సంఘాల రైతు సభ్యులందరూ.",
                benefits = "4% తక్కువ వడ్డీతో రుణం (సకాలంలో చెల్లిస్తే 3% వడ్డీ రాయితీ). బీమా రక్షణ మరియు ఏటీఎం కార్డు సదుపాయం.",
                amount = "4% వడ్డీతో ₹3 లక్షల వరకు రుణం",
                howToApply = "భూమి పత్రాలు, ఆధార్ మరియు ఫోటోలతో సమీపంలోని వాణిజ్య, సహకార లేదా గ్రామీణ బ్యాంకులో దరఖాస్తు చేసుకోండి."
            ),
            "soil_health" to SchemeTranslation(
                title = "భూసార ఆరోగ్య కార్డు పథకం (సాయిల్ హెల్త్ కార్డ్)",
                description = "రైతుల పొలంలోని నేల స్వభావాన్ని పరీక్షించి, పంటల వారీగా పోషకాలు మరియు ఎరువుల సమతుల్య వాడకానికి శాస్త్రీయ సిఫార్సులు.",
                eligibility = "వ్యవసాయ భూమి కలిగిన దేశంలోని రైతులందరూ.",
                benefits = "ఉచిత నేల పరీక్ష, 12 రకాల పోషకాల స్థాయి నివేదిక మరియు 2 సంవత్సరాలకు సరిపడా ఎరువుల యాజమాన్య సలహాలు.",
                amount = "పూర్తిగా ఉచితం",
                howToApply = "సమీపంలోని నేల పరీక్ష ప్రయోగశాల, కృషి విజ్ఞాన కేంద్రం (KVK) లేదా soilhealth.dac.gov.in పోర్టల్ ద్వారా సంప్రదించండి."
            ),
            "pmksy" to SchemeTranslation(
                title = "పీఎం కృషి సించాయి యోజన (హర్ ఖేత్ కో పానీ)",
                description = "ప్రతి పొలానికి సాగునీరు అందించడం మరియు మైక్రో/బిందు సేద్యం ద్వారా నీటి వినియోగ సామర్థ్యాన్ని పెంచడం.",
                eligibility = "రైతులందరూ. వర్షాధార, వెనుకబడిన మరియు గిరిజన ప్రాంతాలకు ప్రత్యేక ప్రాధాన్యత.",
                benefits = "బిందు మరియు తుంపర సేద్య పరికరాలపై చిన్న/సన్నకారు రైతులకు 55%, ఇతర రైతులకు 45% సబ్సిడీ.",
                amount = "చిన్న రైతులకు 55%, ఇతరులకు 45% సబ్సిడీ",
                howToApply = "రాష్ట్ర ఉద్యానవన/వ్యవసాయ శాఖ లేదా జిల్లా వ్యవసాయ అధికారి కార్యాలయంలో దరఖాస్తు చేయండి."
            ),
            "pkvy" to SchemeTranslation(
                title = "పరంపరాగత్ కృషి వికాస్ యోజన (పీకేవీవై)",
                description = "క్లస్టర్ పద్ధతి ద్వారా సేంద్రీయ వ్యవసాయాన్ని ప్రోత్సహించడం మరియు రైతులకు పీజీఎస్ సేంద్రీయ ధృవీకరణ అందించడం.",
                eligibility = "సేంద్రీయ సాగు కోసం 50 ఎకరాల క్లస్టర్‌గా ఏర్పడిన 50 లేదా అంతకంటే ఎక్కువ మంది రైతుల బృందం.",
                benefits = "సేంద్రీయ ఎరువులు, ప్రాసెసింగ్ మరియు మార్కెటింగ్ కోసం 3 సంవత్సరాలలో హెక్టారుకు ₹50,000 ఆర్థిక సహాయం.",
                amount = "హెక్టారుకు ₹50,000 (3 సంవత్సరాలలో)",
                howToApply = "50 మంది రైతులతో గ్రూపుగా ఏర్పడి జిల్లా వ్యవసాయ అధికారికి దరఖాస్తు సమర్పించండి."
            ),
            "enam" to SchemeTranslation(
                title = "ఈ-నామ్ (జాతీయ వ్యవసాయ మార్కెట్)",
                description = "దేశవ్యాప్తంగా ఉన్న వ్యవసాయ మార్కెట్ యార్డులను అనుసంధానించే ఆన్‌లైన్ ట్రేడింగ్ ప్లాట్‌ఫారమ్.",
                eligibility = "ఏపీఎంసీ మార్కెట్లలో నమోదైన రైతులు, వ్యాపారులు మరియు కమీషన్ ఏజెంట్లు.",
                benefits = "పారదర్శక ధరలు, దళారుల బెడద లేకపోవడం, గిట్టుబాటు ధర మరియు నేరుగా బ్యాంక్ ఖాతాలో జమ.",
                amount = "రైతులకు పూర్తిగా ఉచితం",
                howToApply = "enam.gov.in పోర్టల్‌లో నమోదు చేసుకోండి లేదా సమీప ఈ-నామ్ మార్కెట్‌ను సంప్రదించండి."
            ),
            "pm_kusum" to SchemeTranslation(
                title = "పీఎం-కుసుమ్ (రైతు సోలార్ పంపుల పథకం)",
                description = "వ్యవసాయ పంపుసెట్లకు సోలార్ పంపులను అమర్చడం మరియు గ్రిడ్ అనుసంధాన పంపులను సౌరశక్తితో నడపడం.",
                eligibility = "వ్యక్తిగత రైతులు, రైతు గ్రూపులు, ఎఫ్‌పీఓలు మరియు గ్రామ పంచాయతీలు.",
                benefits = "సోలార్ పంపులపై 60% ప్రభుత్వ సబ్సిడీ (30% కేంద్రం + 30% రాష్ట్రం). రైతు కేవలం 10% నుంచి 40% మాత్రమే భరించాలి.",
                amount = "మొత్తం 60% ప్రభుత్వ సబ్సిడీ",
                howToApply = "రాష్ట్ర పునరుత్పాదక ఇంధన అభివృద్ధి సంస్థ (REDA) లేదా mnre.gov.in ద్వారా దరఖాస్తు చేసుకోండి."
            ),
            "nmsa" to SchemeTranslation(
                title = "జాతీయ స్థిరమైన వ్యవసాయ మిషన్ (ఎన్‌ఎంఎస్ఏ)",
                description = "వాతావరణ మార్పులను తట్టుకునే సాగు పద్ధతులు, నేల సంరక్షణ మరియు వర్షాధార ప్రాంతాల అభివృద్ధి.",
                eligibility = "వర్షాధార, మెట్ట భూములు సాగు చేసే రైతులందరూ.",
                benefits = "నేల సంరక్షణ, పంట కుంటల నిర్మాణం, వర్మీ కంపోస్ట్ మరియు విత్తనాల కొనుగోలుపై సబ్సిడీ.",
                amount = "వర్షాధార అభివృద్ధికి ₹12,500/హెక్టారు సహాయం",
                howToApply = "మండల లేదా జిల్లా వ్యవసాయ అధికారి కార్యాలయం ద్వారా దరఖాస్తు చేయండి."
            ),
            "rkvy" to SchemeTranslation(
                title = "రాష్ట్రీయ కృషి వికాస్ యోజన (ఆర్కేవీవై-రఫ్తార్)",
                description = "వ్యవసాయ మౌలిక సదుపాయాలు, సాంకేతికత మరియు అగ్రి-స్టార్టప్‌లకు ఆర్థిక ప్రోత్సాహం.",
                eligibility = "రైతులు, రైతు ఉత్పత్తిదారుల సంఘాలు (FPO) మరియు నూతన అగ్రి-స్టార్టప్‌లు.",
                benefits = "పంట కోత అనంతర మౌలిక సదుపాయాలు మరియు అగ్రి-స్టార్టప్‌లకు ₹25 లక్షల వరకు గ్రాంట్లు.",
                amount = "స్టార్టప్ గ్రాంట్లు ₹25 లక్షల వరకు",
                howToApply = "rkvy.nic.in పోర్టల్ లేదా రాష్ట్ర వ్యవసాయ శాఖ ద్వారా దరఖాస్తు చేయండి."
            ),
            "agri_infra" to SchemeTranslation(
                title = "వ్యవసాయ మౌలిక సదుపాయాల నిధి (ఏఐఎఫ్)",
                description = "గోదాములు, కోల్డ్ స్టోరేజీలు మరియు ప్రాథమిక ప్రాసెసింగ్ యూనిట్ల నిర్మాణానికి రాయితీ రుణాలు.",
                eligibility = "రైతులు, ఎఫ్‌పీఓలు, ప్రాథమిక వ్యవసాయ సహకార సంఘాలు (PACS) మరియు అగ్రి-ఔత్సాహికులు.",
                benefits = "₹2 కోట్ల వరకు రుణాలపై 7 సంవత్సరాల పాటు సంవత్సరానికి 3% వడ్డీ రాయితీ మరియు క్రెడిట్ గ్యారెంటీ.",
                amount = "₹2 కోట్ల వరకు రుణాలపై 3% వడ్డీ రాయితీ",
                howToApply = "agriinfra.dac.gov.in పోర్టల్ ద్వారా ప్రాజెక్ట్ ప్రతిపాదనను సమర్పించండి."
            ),
            "smam" to SchemeTranslation(
                title = "వ్యవసాయ యాంత్రీకరణ ఉప-మిషన్ (ఎస్ఎమ్ఏఎమ్)",
                description = "ట్రాక్టర్లు, పవర్ టిల్లర్లు, హార్వెస్టర్లు మరియు ఆధునిక వ్యవసాయ పరికరాల కొనుగోలుపై రాయితీ.",
                eligibility = "చిన్న, సన్నకారు, మహిళా, ఎస్సీ/ఎస్టీ రైతులు మరియు గ్రామీణ యువత.",
                benefits = "వ్యవసాయ పరికరాలు మరియు యంత్రాలపై 40% నుండి 50% వరకు ప్రత్యక్ష సబ్సిడీ.",
                amount = "పరికరాలపై 40%-50% సబ్సిడీ",
                howToApply = "agrimachinery.nic.in పోర్టల్‌లో భూమి పత్రాలు మరియు బ్యాంక్ వివరాలతో నమోదు చేసుకోండి."
            ),
            "nbhm" to SchemeTranslation(
                title = "జాతీయ తేనెటీగల పెంపకం మిషన్ (ఎన్‌బీహెచ్ఎమ్)",
                description = "అదనపు ఆదాయం, పరాగసంపర్కం మరియు నాణ్యమైన తేనె ఉత్పత్తి కోసం శాస్త్రీయ తేనెటీగల పెంపకం.",
                eligibility = "రైతులు, మహిళా స్వయం సహాయక సంఘాలు మరియు తేనెటీగల పెంపకందారులు.",
                benefits = "తేనెటీగల పెట్టెలు, కాలనీలు మరియు ప్రాసెసింగ్ పరికరాలపై 80% వరకు సబ్సిడీ.",
                amount = "మహిళలు/ఎస్సీ/ఎస్టీలకు 80%, ఇతరులకు 50% సబ్సిడీ",
                howToApply = "nbb.gov.in పోర్టల్ లేదా జిల్లా ఉద్యానవన శాఖ అధికారిని సంప్రదించండి."
            ),
            "midh" to SchemeTranslation(
                title = "సమీకృత ఉద్యానవన అభివృద్ధి మిషన్ (ఎమ్ఐడీహెచ్)",
                description = "పండ్లు, కూరగాయలు, సుగంధ ద్రవ్యాలు, పువ్వులు, పాలీహౌస్‌లు మరియు కోల్డ్ స్టోరేజీల సమగ్ర అభివృద్ధి.",
                eligibility = "ఉద్యాన పంటలు సాగు చేసే రైతులు, స్వయం సహాయక సంఘాలు మరియు ఎఫ్‌పీఓలు.",
                benefits = "కొత్త తోటల పెంపకం, షేడ్‌నెట్ పాలీహౌస్‌లు మరియు ప్యాక్-హౌస్‌ల నిర్మాణానికి 40-50% ఆర్థిక సహాయం.",
                amount = "40% నుండి 50% మూలధన సబ్సిడీ",
                howToApply = "జిల్లా ఉద్యానవన శాఖ అధికారి లేదా రాష్ట్ర ఉద్యాన పోర్టల్ ద్వారా దరఖాస్తు చేయండి."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "పీఎం-ఆశా (అన్నదాత ఆదాయ సంరక్షణ అభియాన్)",
                description = "పప్పుధాన్యాలు, నూనెగింజలు మరియు కొబ్బరి పంటలకు కనీస మద్దతు ధర (MSP) హామీ పథకం.",
                eligibility = "నోటిఫై చేసిన నూనెగింజలు మరియు పప్పుధాన్యాలు సాగు చేసే నమోదిత రైతులందరూ.",
                benefits = "మార్కెట్ ధర ఎంఎస్పీ కంటే తగ్గితే ప్రభుత్వ సేకరణ లేదా ధరల వ్యత్యాసాన్ని నేరుగా ఖాతాలో జమ చేయడం.",
                amount = "పూర్తి కనీస మద్దతు ధర (MSP) లబ్ధి",
                howToApply = "పంట కోతకు ముందే రాష్ట్ర సేకరణ పోర్టల్‌లో రైతు నమోదు చేసుకోండి."
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "మహాత్మా జ్యోతిరావు ఫూలే రైతు రుణమాఫీ పథకం",
                description = "మహారాష్ట్రలో ₹2 లక్షల వరకు బకాయి ఉన్న పంట రుణాలను పూర్తిగా మాఫీ చేసే పథకం.",
                eligibility = "మహారాష్ట్రలోని జాతీయ, సహకార లేదా గ్రామీణ బ్యాంకుల్లో ₹2 లక్షల వరకు పంట రుణం ఉన్న రైతులు.",
                benefits = "₹2 లక్షల వరకు పూర్తి రుణమాఫీ మరియు క్రమం తప్పకుండా రుణం తీర్చే రైతులకు ₹50,000 ప్రోత్సాహకం.",
                amount = "₹2 లక్షల వరకు రుణమాఫీ + ₹50,000 ప్రోత్సాహకం",
                howToApply = "MahaDBT పోర్టల్ లేదా తహసీల్దార్ కార్యాలయంలో ఆధార్ ధృవీకరణతో దరఖాస్తు చేయండి."
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "నానాజీ దేశ్‌ముఖ్ కృషి సంజీవని యోజన (పోక్రా)",
                description = "మహారాష్ట్రలోని కరువు పీడిత మరియు ఉప్పు నేలల ప్రాంతాలలో వాతావరణ-అనుకూల సాగు ప్రాజెక్ట్.",
                eligibility = "మరాఠ్వాడా మరియు విదర్భలోని 15 కరువు జిల్లాల చిన్న, సన్నకారు రైతులు.",
                benefits = "పంట కుంటలు, బిందు సేద్యం, షేడ్‌నెట్ మరియు నేల పునరుద్ధరణ పనులపై 75% వరకు సబ్సిడీ.",
                amount = "నీటి సంరక్షణ మరియు డ్రిప్‌పై 75% వరకు సబ్సిడీ",
                howToApply = "mahapocra.gov.in పోర్టల్ ద్వారా దరఖాస్తు చేసుకోండి."
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "నీటిని ఆదా చేయండి డబ్బు సంపాదించండి పథకం",
                description = "వ్యవసాయ బోరుబావుల్లో విద్యుత్ మరియు భూగర్భ జలాలను ఆదా చేసినందుకు రైతులకు నగదు ప్రోత్సాహం.",
                eligibility = "మీటర్లు ఉన్న వ్యవసాయ విద్యుత్ కనెక్షన్లు కలిగిన పంజాబ్ రైతులు.",
                benefits = "విద్యుత్ ఆదా చేస్తే యూనిట్‌కు ₹4 చొప్పున నేరుగా బ్యాంక్ ఖాతాలో జమ.",
                amount = "ఆదా చేసిన విద్యుత్ యూనిట్‌కు ₹4",
                howToApply = "పంజాబ్ స్టేట్ పవర్ కార్పొరేషన్ (PSPCL) ఉప-విభాగంలో నమోదు చేసుకోండి."
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "యూపీ కిసాన్ ఉదయ్ యోజన",
                description = "ఉత్తరప్రదేశ్‌లోని రైతులకు ఉచిత ఇంధన సామర్థ్యం గల స్మార్ట్ సోలార్ పంపుసెట్ల పంపిణీ.",
                eligibility = "సాగుభూమి మరియు సాగునీటి అవసరం ఉన్న యూపీ చిన్న మరియు సన్నకారు రైతులు.",
                benefits = "2 నుండి 5 హెచ్‌పీ స్మార్ట్ సోలార్ పంపు ఉచిత అమరిక మరియు 5 సంవత్సరాల ఉచిత నిర్వహణ.",
                amount = "100% ఉచిత సోలార్ పంపుల పంపిణీ",
                howToApply = "యూపీ వ్యవసాయ శాఖ పోర్టల్ (upagriculture.com) లో ఆన్‌లైన్‌లో దరఖాస్తు చేసుకోండి."
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "భావంతర్ భుగ్తాన్ యోజన (ధరల వ్యత్యాస చెల్లింపు)",
                description = "మార్కెట్ ధర ఎంఎస్పీ కంటే తగ్గినప్పుడు ఆ తేడా మొత్తాన్ని నేరుగా రైతు ఖాతాలో జమ చేసే పథకం.",
                eligibility = "ఎంపీ ఈ-ఉపార్జన్ పోర్టల్‌లో నమోదైన మధ్యప్రదేశ్ రైతులు.",
                benefits = "ఎంఎస్పీ మరియు మార్కెట్ విక్రయ ధర మధ్య వ్యత్యాసం నేరుగా ఖాతాలో జమ.",
                amount = "క్వింటాల్‌కు వ్యత్యాస మొత్తం",
                howToApply = "mpeuparjan.nic.in లో పంట కోతకు ముందే నమోదు చేసుకోండి."
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "రైతు బంధు పథకం (రైతు పెట్టుబడి సహాయం)",
                description = "వ్యవసాయ పెట్టుబడి కోసం ఎకరానికి సంవత్సరానికి ₹10,000 ప్రత్యక్ష ఆర్థిక సహాయం.",
                eligibility = "తెలంగాణలోని పట్టాదారు పాస్‌బుక్ ఉన్న రైతులందరూ.",
                benefits = "ఖరీఫ్‌కు ₹5,000, రబీకి ₹5,000 చొప్పున ఎకరానికి ₹10,000 నేరుగా బ్యాంక్ ఖాతాలో జమ.",
                amount = "ఎకరానికి సంవత్సరానికి ₹10,000",
                howToApply = "ధరణి పోర్టల్ భూమి రికార్డుల ఆధారంగా ఆటోమేటిక్ నమోదు."
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "తమిళనాడు ఉచిత వ్యవసాయ విద్యుత్ పథకం",
                description = "తమిళనాడులో వ్యవసాయ పంపుసెట్లకు 24 గంటల పాటు పూర్తిగా ఉచిత విద్యుత్ సరఫరా.",
                eligibility = "తమిళనాడులోని నమోదిత వ్యవసాయ విద్యుత్ వినియోగదారులందరూ.",
                benefits = "ఎటువంటి విద్యుత్ బిల్లులు లేకుండా సాగునీటి కోసం 100% ఉచిత విద్యుత్.",
                amount = "100% ఉచిత వ్యవసాయ విద్యుత్",
                howToApply = "తమిళనాడు విద్యుత్ బోర్డు (TANGEDCO) లో వ్యవసాయ కనెక్షన్ కోసం దరఖాస్తు చేసుకోండి."
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "రైత సిరి యోజన (సిరిధాన్యాల ప్రోత్సాహం)",
                description = "సిరిధాన్యాల (మిల్లెట్స్) సాగుకు ప్రోత్సాహం మరియు ₹3 లక్షల వరకు వడ్డీ లేని పంట రుణాలు.",
                eligibility = "కర్ణాటకలోని సిరిధాన్యాల రైతులు మరియు వ్యవసాయ కుటుంబాలు.",
                benefits = "సిరిధాన్యాల సాగుపై హెక్టారుకు ₹10,000 ప్రోత్సాహకం మరియు 0% వడ్డీ పంట రుణాలు.",
                amount = "₹10,000/హెక్టారు + 0% వడ్డీ రుణాలు",
                howToApply = "కర్ణాటక రైత మిత్ర పోర్టల్ (raitamitra.karnataka.gov.in) ద్వారా దరఖాస్తు చేసుకోండి."
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "కిసాన్ సూర్యోదయ యోజన",
                description = "గుజరాత్‌లోని రైతులకు పగటిపూట (ఉదయం 5 నుండి రాత్రి 9 వరకు) వ్యవసాయ విద్యుత్ సరఫరా.",
                eligibility = "గుజరాత్ వ్యవసాయ విద్యుత్ నెట్‌వర్క్‌లో ఉన్న రైతులందరూ.",
                benefits = "పగటిపూట నమ్మకమైన విద్యుత్ సరఫరా వలన రాత్రివేళల్లో పొలాలకు నీరు పెట్టే ప్రమాదం తప్పుతుంది.",
                amount = "ఉచిత ప్రభుత్వ మౌలిక సదుపాయాలు",
                howToApply = "డిస్కంల (DISCOMs) ద్వారా ఆటోమేటిక్ సదుపాయం."
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "రాజస్థాన్ కిసాన్ మిత్ర ఎనర్జీ యోజన",
                description = "వ్యవసాయ విద్యుత్ బిల్లులపై నెలకు ₹1,000 వరకు ప్రత్యక్ష ప్రభుత్వ రాయితీ.",
                eligibility = "రాజస్థాన్‌లోని మీటర్లు ఉన్న వ్యవసాయ విద్యుత్ వినియోగదారులు.",
                benefits = "విద్యుత్ బిల్లులో నెలకు ₹1,000 (సంవత్సరానికి ₹12,000 వరకు) ప్రత్యక్ష మినహాయింపు.",
                amount = "సంవత్సరానికి ₹12,000 వరకు విద్యుత్ రాయితీ",
                howToApply = "విద్యుత్ పంపిణీ సంస్థల ద్వారా బిల్లుల్లో ఆటోమేటిక్ సర్దుబాటు."
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "బీహార్ డీజిల్ సబ్సిడీ పథకం (డీజిల్ అనుదాన్)",
                description = "వర్షాభావ పరిస్థితులలో సాగునీటి కోసం డీజిల్ కొనుగోలుపై ప్రభుత్వ రాయితీ.",
                eligibility = "బీహార్‌లోని భూయజమానులు మరియు కౌలు రైతులు.",
                benefits = "డీజిల్‌పై లీటరుకు ₹75 సబ్సిడీ (ఒక తడికి ఎకరానికి గరిష్టంగా ₹750, మొత్తం 3 తడుల వరకు).",
                amount = "₹75/లీటరు (గరిష్టంగా ₹2,250/ఎకరం)",
                howToApply = "డీబీటీ అగ్రికల్చర్ బీహార్ (dbtagriculture.bihar.gov.in) ద్వారా ఆన్‌లైన్‌లో దరఖాస్తు చేసుకోండి."
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "కృషక్ బంధు పథకం",
                description = "పశ్చిమ బెంగాల్‌లోని రైతులకు సంవత్సరానికి ₹10,000 ఆర్థిక సహాయం మరియు ₹2 లక్షల జీవిత బీమా.",
                eligibility = "పశ్చిమ బెంగాల్‌లోని రైతులు మరియు నమోదిత బర్గాదారులు (వయస్సు 18 నుండి 60 సంవత్సరాలు).",
                benefits = "రెండు విడతలలో ₹10,000 ఆర్థిక సాయం మరియు మరణించిన రైతు కుటుంబానికి ₹2 లక్షల పరిహారం.",
                amount = "సంవత్సరానికి ₹10,000 + ₹2 లక్షల బీమా",
                howToApply = "సమీప బీడీఓ (BDO) కార్యాలయం లేదా krishakbandhu.net పోర్టల్ ద్వారా దరఖాస్తు చేసుకోండి."
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "వైఎస్సార్ రైతు భరోసా - పీఎం కిసాన్",
                description = "ఆంధ్రప్రదేశ్‌లో కౌలు రైతులతో సహా అన్ని రైతు కుటుంబాలకు సంవత్సరానికి ₹13,500 పెట్టుబడి సహాయం.",
                eligibility = "ఆంధ్రప్రదేశ్‌లోని భూయజమానులు మరియు ఎస్సీ/ఎస్టీ/బీసీ కౌలు రైతులు.",
                benefits = "విత్తనాలు, ఎరువుల కోసం 3 విడతలలో మొత్తం ₹13,500 నేరుగా బ్యాంక్ ఖాతాలో జమ.",
                amount = "సంవత్సరానికి ₹13,500",
                howToApply = "రైతు భరోసా కేంద్రాలు (RBK) లేదా గ్రామ సచివాలయాల్లో నమోదు చేసుకోండి."
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "హర్యానా భావాంతర్ భర్పాయ్ యోజన",
                description = "కూరగాయలు మరియు ఉద్యాన పంటల మార్కెట్ ధరలు తగ్గినప్పుడు రైతులకు నష్టపరిహారం అందించే పథకం.",
                eligibility = "మేరీ ఫసల్ మేరా బ్యోరా పోర్టల్‌లో నమోదైన హర్యానా ఉద్యానవన రైతులు.",
                benefits = "రక్షిత కనీస ధర మరియు మార్కెట్ విక్రయ ధర మధ్య వ్యత్యాసం నేరుగా ఖాతాలో జమ.",
                amount = "క్వింటాల్‌కు వ్యత్యాస మొత్తం",
                howToApply = "పంట వేసే ముందు fasal.haryana.gov.in లో నమోదు చేసుకోండి."
            ),
            "od_kalia" to SchemeTranslation(
                title = "కాలియా పథకం (రైతు జీవనోపాధి మరియు ఆదాయ పెంపుదల)",
                description = "ఒడిశాలోని చిన్న, సన్నకారు మరియు భూమిలేని వ్యవసాయ కుటుంబాలకు సమగ్ర ఆర్థిక సహాయం.",
                eligibility = "ఒడిశాలోని చిన్న/సన్నకారు రైతులు మరియు భూమిలేని వ్యవసాయ కూలీలు.",
                benefits = "సాగు కోసం ₹10,000/సంవత్సరం, భూమిలేని వారికి ₹12,500 మరియు ₹2 లక్షల బీమా రక్షణ.",
                amount = "సంవత్సరానికి ₹10,000 + జీవనోపాధి సాయం",
                howToApply = "kalia.odisha.gov.in లేదా గ్రామ పంచాయతీ కార్యాలయంలో దరఖాస్తు చేసుకోండి."
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "కేరళ రాష్ట్ర సమగ్ర పంటల బీమా పథకం",
                description = "వరదలు, కొండచరియలు విరిగిపడటం మరియు అడవి జంతువుల నష్టం నుండి 25+ ప్రధాన పంటలకు రాష్ట్ర బీమా రక్షణ.",
                eligibility = "కేరళలో వరి, అరటి, సుగంధ ద్రవ్యాలు, కూరగాయలు మరియు రబ్బరు సాగు చేసే రైతులందరూ.",
                benefits = "ప్రకృతి వైపరీత్యాలు మరియు వన్యప్రాణుల నష్టానికి తక్షణ పరిహారం.",
                amount = "పంటను బట్టి ఎకరానికి ₹35,000 వరకు పరిహారం",
                howToApply = "AIMS పోర్టల్ (aims.kerala.gov.in) లేదా స్థానిక కృషి భవన్‌లో దరఖాస్తు చేసుకోండి."
            ),
        ),
        "ta" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "பிரதம மந்திரி கிசான் சம்மான் நிதி (PM-KISAN)",
                description = "தகுதியான விவசாய குடும்பங்களுக்கு ஆண்டுதோறும் ₹6,000 நேரடி வருமான ஆதரவு, ₹2,000 வீதம் 3 தவணைகளில் நேரடியாக வங்கி கணக்கில் செலுத்தப்படுகிறது.",
                eligibility = "விவசாய நிலம் வைத்துள்ள அனைத்து விவசாய குடும்பங்களும் தகுதியானவர்கள். அரசு ஊழியர்கள் மற்றும் வருமான வரி செலுத்துவோர் தவிர.",
                benefits = "ஆண்டுக்கு 3 தவணைகளில் ₹6,000 நேரடி வங்கி பணப்பரிமாற்றம்.",
                amount = "ஆண்டுக்கு ₹6,000",
                howToApply = "அருகிலுள்ள இ-சேவை மையம் (CSC) அல்லது pmkisan.gov.in இணையதளத்தில் பட்டா மற்றும் ஆதார் விவரங்களுடன் பதிவு செய்யவும்."
            ),
            "pmfby" to SchemeTranslation(
                title = "பிரதம மந்திரி பயிர் காப்பீட்டுத் திட்டம் (PMFBY)",
                description = "இயற்கை பேரிடர், பூச்சி மற்றும் நோய்த்தாக்குதலால் பயிர் சேதமடையும் போது உணவு, எண்ணெய் வித்துக்கள் மற்றும் தோட்டக்கலை பயிர்களுக்கு முழுமையான காப்பீடு.",
                eligibility = "அறிவிக்கப்பட்ட பயிர்களை பயிரிடும் அனைத்து நில உரிமையாளர்கள் மற்றும் குத்தகை விவசாயிகள்.",
                benefits = "முழு காப்பீட்டுத் தொகை பாதுகாப்பு. விவசாயி பிரீமியம்: காரிஃப் 2%, ரபி 1.5%, தோட்டக்கலை 5%. மீதமுள்ள தொகையை அரசே ஏற்கும்.",
                amount = "பயிர் மற்றும் பரப்பளவின் அடிப்படையில் காப்பீட்டுத் தொகை",
                howToApply = "அருகிலுள்ள தொடக்க வேளாண்மை கூட்டுறவு வங்கி, தேசியமயமாக்கப்பட்ட வங்கி அல்லது pmfby.gov.in இல் விண்ணப்பிக்கவும்."
            ),
            "kcc" to SchemeTranslation(
                title = "கிசான் கிரெடிட் கார்டு (KCC - விவசாய கடன் அட்டை)",
                description = "பயிர் சாகுபடி, அறுவடைக்கு பிந்தைய செலவுகள் மற்றும் விவசாய தேவைகளுக்காக மிகக் குறைந்த வட்டியில் குறுகிய கால கடன் வசதி.",
                eligibility = "தனிநபர், கூட்டுக் கடன் வாங்குவோர், குத்தகை விவசாயிகள் மற்றும் சுய உதவிக் குழு விவசாயிகள்.",
                benefits = "4% மானிய வட்டியில் கடன் (சரியான நேரத்தில் திரும்பச் செலுத்தினால் 3% வட்டி தள்ளுபடி). ஏடிஎம் அட்டை வசதி.",
                amount = "4% வட்டியில் ₹3 லட்சம் வரை பயிர்க்கடன்",
                howToApply = "சிட்டா, அடங்கல், ஆதார் மற்றும் புகைப்படங்களுடன் எந்தவொரு வணிக, கூட்டுறவு அல்லது கிராம வங்கியில் விண்ணப்பிக்கவும்."
            ),
            "soil_health" to SchemeTranslation(
                title = "மண் வள அட்டை திட்டம் (Soil Health Card)",
                description = "விவசாய நிலத்தின் மண் மாதிரிகளை பரிசோதித்து, பயிர்களுக்கு தேவையான ஊட்டச்சத்துக்கள் மற்றும் உரங்களின் சமச்சீர் பயன்பாட்டிற்கான பரிந்துரைகள்.",
                eligibility = "விவசாய நிலம் வைத்துள்ள அனைத்து விவசாயிகளும்.",
                benefits = "இலவச மண் பரிசோதனை, 12 முக்கிய ஊட்டச்சத்துக்களின் நிலை அறிக்கை மற்றும் 2 ஆண்டுகளுக்கான உரப் பரிந்துரை.",
                amount = "முற்றிலும் இலவசம்",
                howToApply = "அருகிலுள்ள மண் பரிசோதனை நிலையம், வேளாண்மை அறிவியல் மையம் (KVK) அல்லது வட்டார வேளாண்மை விரிவாக்க மையத்தை அணுகவும்."
            ),
            "pmksy" to SchemeTranslation(
                title = "பிரதம மந்திரி நுண்ணீர்ப் பாசனத் திட்டம் (PMKSY)",
                description = "ஒவ்வொரு விவசாய நிலத்திற்கும் பாசன நீர் வசதி மற்றும் சொட்டுநீர்/தெளிப்புநீர் பாசனம் மூலம் நீர் பயன்பாட்டுத் திறனை அதிகரித்தல்.",
                eligibility = "அனைத்து விவசாயிகள். வறட்சி மற்றும் மானாவாரி பகுதிகளுக்கு முன்னுரிமை.",
                benefits = "சிறு, குறு விவசாயிகளுக்கு 100% (தமிழகத்தில் கூடுதல் மானியத்துடன்) அல்லது 55% மத்திய மானியம்; பிற விவசாயிகளுக்கு 45% மானியம்.",
                amount = "சிறு விவசாயிகளுக்கு 55% முதல் 100% வரை மானியம்",
                howToApply = "வட்டார தோட்டக்கலை அல்லது வேளாண்மை உதவி இயக்குநர் அலுவலகத்தில் விண்ணப்பிக்கவும்."
            ),
            "pkvy" to SchemeTranslation(
                title = "பாரம்பரிய வேளாண்மை வளர்ச்சித் திட்டம் (PKVY)",
                description = "இயற்கை வேளாண்மையை ஊக்குவித்தல் மற்றும் விவசாயிகளுக்கு PGS இயற்கை சான்றிதழ் பெற உதவுதல்.",
                eligibility = "இயற்கை விவசாயத்திற்காக 50 ஏக்கர் பரப்பளவில் குழுவாக இணையும் 50 அல்லது அதற்கு மேற்பட்ட விவசாயிகள்.",
                benefits = "இயற்கை உரம், பண்ணை இடுபொருட்கள் மற்றும் சந்தைப்படுத்துதலுக்கு 3 ஆண்டுகளில் ஹெக்டேருக்கு ₹50,000 நிதி உதவி.",
                amount = "ஹெக்டேருக்கு ₹50,000 (3 ஆண்டுகளில்)",
                howToApply = "50 விவசாயிகள் குழுவாக இணைந்து மாவட்ட வேளாண்மை அலுவலகத்தில் விண்ணப்பிக்கவும்."
            ),
            "enam" to SchemeTranslation(
                title = "இ-நாம் (தேசிய மின்னணு வேளாண் சந்தை)",
                description = "விவசாய விளைபொருட்களை நாடு தழுவிய அளவில் இடைத்தரகர்கள் இன்றி விற்பனை செய்வதற்கான ஆன்லைன் வர்த்தக தளம்.",
                eligibility = "ஒழுங்குமுறை விற்பனைக்கூடங்களில் பதிவு செய்த அனைத்து விவசாயிகள் மற்றும் வியாபாரிகள்.",
                benefits = "வெளிப்படையான ஏல முறை, சரியான எடை, நியாயமான விலை மற்றும் வங்கி கணக்கில் நேரடி பணப்பரிவர்த்தனை.",
                amount = "விவசாயிகளுக்கு முற்றிலும் இலவசம்",
                howToApply = "enam.gov.in போர்ட்டலில் பதிவு செய்யவும் அல்லது அருகிலுள்ள ஒழுங்குமுறை விற்பனைக்கூடத்திற்கு செல்லவும்."
            ),
            "pm_kusum" to SchemeTranslation(
                title = "பிஎம்-குசும் (சூரிய சக்தி பம்ப் திட்டம்)",
                description = "விவசாய நிலங்களில் தனித்துவமான சோலார் பம்புகள் அமைத்தல் மற்றும் மின்சார பம்புகளை சூரிய சக்தியாக மாற்றுதல்.",
                eligibility = "அனைத்து விவசாயிகள், விவசாய உற்பத்தியாளர் நிறுவனங்கள் (FPO) மற்றும் ஊராட்சிகள்.",
                benefits = "சோலார் பம்புகளுக்கு 60% முதல் 70% வரை அரசு மானியம் (30% மத்திய அரசு + 30% மாநில அரசு).",
                amount = "மொத்தம் 60% அரசு மானியம்",
                howToApply = "தமிழ்நாடு எரிசக்தி மேம்பாட்டு முகமை (TEDA) அல்லது வேளாண்மை பொறியியல் துறை மூலம் விண்ணப்பிக்கவும்."
            ),
            "nmsa" to SchemeTranslation(
                title = "நிலையான விவசாயத்திற்கான தேசிய இயக்கம் (NMSA)",
                description = "பருவநிலை மாற்றத்தை தாங்கும் சாகுபடி முறைகள், மண்வள மேலாண்மை மற்றும் மானாவாரி நில மேம்பாடு.",
                eligibility = "மானாவாரி மற்றும் வறட்சி பாதித்த பகுதி விவசாயிகள்.",
                benefits = "பண்ணைக் குட்டை அமைத்தல், மண்புழு உரம், பசுந்தாள் உரம் மற்றும் விதை மானியம்.",
                amount = "மானாவாரி நில மேம்பாட்டிற்கு ₹12,500/ஹெக்டேர் உதவி",
                howToApply = "வட்டார வேளாண்மை அலுவலர் அலுவலகத்தில் விண்ணப்பிக்கவும்."
            ),
            "rkvy" to SchemeTranslation(
                title = "ராஷ்ட்ரிய கிரிஷி விகாஸ் யோஜனா (RKVY-RAFTAAR)",
                description = "வேளாண் உட்கட்டமைப்பு, நவீன கண்டுபிடிப்புகள் மற்றும் அக்ரி-ஸ்டார்ட்அப் நிறுவனங்களுக்கு நிதி உதவி.",
                eligibility = "விவசாயிகள், எஃப்.பி.ஓ மற்றும் வேளாண் தொழில் முனைவோர்.",
                benefits = "அறுவடைக்கு பிந்தைய பதப்படுத்தும் வசதிகள் மற்றும் வேளாண் புத்தாக்க நிறுவனங்களுக்கு ₹25 லட்சம் வரை மானியம்.",
                amount = "ஸ்டார்ட்அப் மானியம் ₹25 லட்சம் வரை",
                howToApply = "rkvy.nic.in இணையதளம் அல்லது மாநில வேளாண்மை துறை மூலம் விண்ணப்பிக்கவும்."
            ),
            "agri_infra" to SchemeTranslation(
                title = "வேளாண் உள்கட்டமைப்பு நிதி (AIF)",
                description = "சேமிப்புக் கிடங்குகள், குளிர்பதனக் கிடங்குகள் மற்றும் முதன்மை பதப்படுத்தும் மையங்கள் அமைக்க மானிய கடன்.",
                eligibility = "விவசாயிகள், எஃப்.பி.ஓக்கள், தொடக்க வேளாண் கூட்டுறவு சங்கங்கள் (PACS) மற்றும் தொழில்முனைவோர்.",
                benefits = "₹2 கோடி வரையிலான கடன்களுக்கு 7 ஆண்டுகளுக்கு ஆண்டுக்கு 3% வட்டி மானியம் மற்றும் கடன் உத்தரவாதம்.",
                amount = "₹2 கோடி வரை கடனில் 3% வட்டி தள்ளுபடி",
                howToApply = "agriinfra.dac.gov.in போர்ட்டலில் விரிவான திட்ட அறிக்கையை சமர்ப்பிக்கவும்."
            ),
            "smam" to SchemeTranslation(
                title = "வேளாண் இயந்திரமயமாக்கல் துணை இயக்கம் (SMAM)",
                description = "டிராக்டர்கள், பவர் டில்லர்கள், அறுவடை இயந்திரங்கள் மற்றும் விவசாய கருவிகள் வாங்க மானியம்.",
                eligibility = "சிறு, குறு, பெண், ஆதிதிராவிட விவசாயிகள் மற்றும் ஊரக இளைஞர்கள்.",
                benefits = "விவசாய இயந்திரங்கள் மற்றும் உபகரணங்கள் வாங்க 40% முதல் 50% வரை நேரடி மானியம்.",
                amount = "இயந்திரங்களுக்கு 40%-50% வரை மானியம்",
                howToApply = "agrimachinery.nic.in அல்லது உழவன் செயலியில் பட்டா மற்றும் வங்கி ஆவணங்களுடன் விண்ணப்பிக்கவும்."
            ),
            "nbhm" to SchemeTranslation(
                title = "தேசிய தேனீ வளர்ப்பு மற்றும் தேன் இயக்கம் (NBHM)",
                description = "கூடுதல் வருமானம், மகரந்தச் சேர்க்கை மற்றும் தரமான தேன் உற்பத்திக்காக அறிவியல் பூர்வ தேனீ வளர்ப்பு.",
                eligibility = "விவசாயிகள், மகளிர் சுய உதவிக் குழுக்கள் மற்றும் தேனீ வளர்ப்போர்.",
                benefits = "தேனீப் பெட்டிகள், தேனீக் குடும்பங்கள் மற்றும் தேன் பிரித்தெடுக்கும் கருவிகளுக்கு 80% வரை மானியம்.",
                amount = "பெண்கள்/ஆதிதிராவிடருக்கு 80%, மற்றவர்களுக்கு 50% மானியம்",
                howToApply = "nbb.gov.in இணையதளம் அல்லது மாவட்ட தோட்டக்கலை அலுவலகத்தில் விண்ணப்பிக்கவும்."
            ),
            "midh" to SchemeTranslation(
                title = "ஒருங்கிணைந்த தோட்டக்கலை மேம்பாட்டு இயக்கம் (MIDH)",
                description = "பழங்கள், காய்கறிகள், நறுமணப் பயிர்கள், பூக்கள், பாலிஹவுஸ் மற்றும் குளிர்பதனக் கிடங்குகளின் ஒருங்கிணைந்த வளர்ச்சி.",
                eligibility = "தோட்டக்கலை பயிர் செய்யும் விவசாயிகள் மற்றும் விவசாய உற்பத்தியாளர் நிறுவனங்கள்.",
                benefits = "புதிய பழத்தோட்டம் அமைத்தல், நிழல்வலை குடில்கள் மற்றும் சிப்பம் கட்டும் அறை அமைக்க 40-50% மானியம்.",
                amount = "40% முதல் 50% வரை மூலதன மானியம்",
                howToApply = "மாவட்ட தோட்டக்கலை துணை இயக்குநர் அலுவலகத்தை அணுகவும்."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "பிஎம்-ஆஷா (விவசாயிகள் வருமான பாதுகாப்பு திட்டம்)",
                description = "பருப்பு வகைகள், எண்ணெய் வித்துக்கள் மற்றும் கொப்பரைத் தேங்காய்க்கு குறைந்தபட்ச ஆதரவு விலை (MSP) உத்தரவாதம்.",
                eligibility = "அறிவிக்கப்பட்ட எண்ணெய் வித்துக்கள் மற்றும் பருப்பு பயிரிடும் பதிவு செய்த விவசாயிகள்.",
                benefits = "சந்தை விலை MSP-யை விட குறையும் போது அரசே கொள்முதல் செய்தல் அல்லது விலை வித்தியாச தொகையை வங்கியில் செலுத்துதல்.",
                amount = "முழுமையான குறைந்தபட்ச ஆதரவு விலை (MSP) பலன்",
                howToApply = "அறுவடைக்கு முன் ஒழுங்குமுறை விற்பனைக்கூட இ-கொள்முதல் தளத்தில் பதிவு செய்யவும்."
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "மகாத்மா ஜோதிராவ் பூலே விவசாய கடன் தள்ளுபடி திட்டம்",
                description = "மகாராஷ்டிராவில் ₹2 லட்சம் வரையிலான நிலுவையில் உள்ள பயிர்க்கடன்களை முழுமையாக தள்ளுபடி செய்யும் திட்டம்.",
                eligibility = "மகாராஷ்டிராவில் கூட்டுறவு அல்லது வணிக வங்கிகளில் ₹2 லட்சம் வரை பயிர்க்கடன் நிலுவை வைத்துள்ள விவசாயிகள்.",
                benefits = "₹2 லட்சம் வரை முழு கடன் தள்ளுபடி மற்றும் கடனை சரியாக செலுத்திய விவசாயிகளுக்கு ₹50,000 ஊக்கத்தொகை.",
                amount = "₹2 லட்சம் வரை கடன் தள்ளுபடி + ₹50,000 ஊக்கத்தொகை",
                howToApply = "MahaDBT இணையதளம் அல்லது வட்டாட்சியர் அலுவலகத்தில் ஆதார் சரிபார்ப்புடன் பெறலாம்."
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "நானாஜி தேஷ்முக் கிருஷி சஞ்சீவனி யோஜனா (PoCRA)",
                description = "மகாராஷ்டிராவின் வறட்சி மற்றும் உவர் நில பகுதிகளில் காலநிலை தாங்கும் விவசாய திட்டம்.",
                eligibility = "மராத்வாடா மற்றும் விதர்பா பகுதியின் 15 வறட்சி மாவட்ட சிறு, குறு விவசாயிகள்.",
                benefits = "பண்ணைக்குட்டைகள், சொட்டுநீர் பாசனம், நிழல்வலை குடில் மற்றும் மண் சீரமைப்புக்கு 75% வரை மானியம்.",
                amount = "நீர் மேலாண்மை மற்றும் சொட்டுநீருக்கு 75% வரை மானியம்",
                howToApply = "mahapocra.gov.in போர்ட்டலில் விண்ணப்பிக்கவும்."
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "தண்ணீரை சேமியுங்கள் பணம் சம்பாதியுங்கள் திட்டம்",
                description = "விவசாய மோட்டார்களில் மின்சாரம் மற்றும் நிலத்தடி நீரை சேமிக்கும் விவசாயிகளுக்கு நேரடி பண ஊக்கத்தொகை.",
                eligibility = "பஞ்சாபில் மீட்டர் பொருத்தப்பட்ட விவசாய மின் இணைப்பு வைத்துள்ள விவசாயிகள்.",
                benefits = "மின்சாரத்தை சேமித்தால் யூனிட்டுக்கு ₹4 வீதம் நேரடியாக வங்கி கணக்கில் செலுத்தப்படும்.",
                amount = "சேமிக்கப்படும் யூனிட்டுக்கு ₹4",
                howToApply = "பஞ்சாப் மின்சார வாரியத்தில் (PSPCL) பதிவு செய்யவும்."
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "யுபி கிசான் உதய் யோஜனா",
                description = "உத்தரபிரதேச விவசாயிகளுக்கு மின் சிக்கன ஸ்மார்ட் சோலார் பம்புகளை இலவசமாக வழங்குதல்.",
                eligibility = "விவசாய நிலம் மற்றும் பாசன தேவை உள்ள உத்தரபிரதேச சிறு, குறு விவசாயிகள்.",
                benefits = "2 முதல் 5 குதிரைத்திறன் (HP) ஸ்மார்ட் சோலார் பம்ப் இலவச பொருத்துதல் மற்றும் 5 ஆண்டுகள் இலவச பராமரிப்பு.",
                amount = "100% இலவச சோலார் பம்ப் விநியோகம்",
                howToApply = "upagriculture.com போர்ட்டலில் ஆன்லைனில் விண்ணப்பிக்கவும்."
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "பவந்தர் புக்தான் யோஜனா (விலை வித்தியாசம் வழங்கும் திட்டம்)",
                description = "மண்டிகளில் விலை குறையும் போது குறைந்தபட்ச ஆதரவு விலைக்கும் விற்பனை விலைக்கும் உள்ள வித்தியாசத் தொகையை வழங்கும் திட்டம்.",
                eligibility = "மத்தியப் பிரதேச இ-உபார்ஜன் போர்ட்டலில் பதிவு செய்த விவசாயிகள்.",
                benefits = "குறைந்தபட்ச ஆதரவு விலைக்கும் சந்தை விலைக்கும் உள்ள வேறுபாட்டுத் தொகை நேரடியாக வங்கிக் கணக்கில் வரவு.",
                amount = "குவிண்டாலுக்கான வித்தியாசத் தொகை",
                howToApply = "mpeuparjan.nic.in இணையதளத்தில் அறுவடைக்கு முன் பதிவு செய்யவும்."
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "ரைத்து பந்து திட்டம் (விவசாயிகள் முதலீட்டு உதவி)",
                description = "விவசாய சாகுபடி செலவுகளுக்காக ஆண்டுக்கு ஏக்கருக்கு ₹10,000 நேரடி நிதி உதவி.",
                eligibility = "தெலுங்கானாவில் பட்டா நிலம் வைத்துள்ள அனைத்து விவசாய குடும்பங்களும்.",
                benefits = "காரிஃப் பருவத்திற்கு ₹5,000 மற்றும் ரபி பருவத்திற்கு ₹5,000 வீதம் ஏக்கருக்கு ₹10,000 வங்கி கணக்கில் வரவு.",
                amount = "ஆண்டுக்கு ஏக்கருக்கு ₹10,000",
                howToApply = "தரணி போர்ட்டலில் உள்ள நில ஆவணங்களின் அடிப்படையில் தானியங்கி பதிவு."
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "தமிழ்நாடு இலவச விவசாய மின்சாரத் திட்டம்",
                description = "தமிழ்நாட்டில் விவசாய பம்ப் செட்டுகளுக்கு 24 மணி நேரமும் தடையற்ற, 100% இலவச மின்சாரம் வழங்குதல்.",
                eligibility = "தமிழ்நாட்டில் பதிவு செய்த அனைத்து விவசாய மின் இணைப்புதாரர்கள்.",
                benefits = "எந்தவொரு கட்டணமும் இன்றி விவசாய பாசனத்திற்கு 100% இலவச மின்சாரம்.",
                amount = "100% இலவச விவசாய மின்சாரம்",
                howToApply = "தமிழ்நாடு மின் உற்பத்தி மற்றும் பகிர்மானக் கழகத்தில் (TANGEDCO) விண்ணப்பிக்கவும்."
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "ரைத்த சிரி திட்டம் (சிறுதானிய சாகுபடி ஊக்கத்தொகை)",
                description = "சிறுதானியங்கள் சாகுபடியை ஊக்குவிக்க நிதி உதவி மற்றும் ₹3 லட்சம் வரை வட்டி இல்லா பயிர்க்கடன்.",
                eligibility = "கர்நாடகாவின் சிறுதானிய விவசாயிகள் மற்றும் விவசாயக் குடும்பங்கள்.",
                benefits = "சிறுதானிய சாகுபடிக்கு ஹெக்டேருக்கு ₹10,000 ஊக்கத்தொகை மற்றும் 0% வட்டியில் பயிர்க்கடன்.",
                amount = "₹10,000/ஹெக்டேர் + 0% வட்டி கடன்",
                howToApply = "கர்நாடக ரைத்த மித்ரா போர்ட்டலில் (raitamitra.karnataka.gov.in) விண்ணப்பிக்கவும்."
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "கிசான் சூர்யோதயா யோஜனா",
                description = "குஜராத் விவசாயிகளுக்கு பகல் நேரத்தில் (காலை 5 மணி முதல் இரவு 9 மணி வரை) பாசன மின்சாரம் வழங்குதல்.",
                eligibility = "குஜராத்தில் விவசாய மின் இணைப்பு பெற்றுள்ள அனைத்து விவசாயிகள்.",
                benefits = "பகல் நேரத்தில் பாதுகாப்பான மின்சாரம் கிடைப்பதால் இரவு நேர பாசன ஆபத்துகள் தவிர்க்கப்படுகின்றன.",
                amount = "இலவச அரசு உள்கட்டமைப்பு திட்டம்",
                howToApply = "மின் பகிர்மான நிறுவனங்கள் (DISCOMs) மூலம் தானியங்கி சேவை."
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "ராஜஸ்தான் கிசான் மித்ரா எனர்ஜி யோஜனா",
                description = "விவசாய மின் கட்டணத்தில் மாதம் ₹1,000 வரை நேரடி அரசு மானியம்.",
                eligibility = "ராஜஸ்தானில் மீட்டர் பொருத்தப்பட்ட விவசாய மின் இணைப்புதாரர்கள்.",
                benefits = "மின் கட்டணத்தில் மாதம் ₹1,000 (ஆண்டுக்கு ₹12,000 வரை) நேரடி தள்ளுபடி.",
                amount = "ஆண்டுக்கு ₹12,000 வரை மின் மானியம்",
                howToApply = "மின் விநியோக நிறுவனங்களால் பில்களில் தானாகவே கழிக்கப்படும்."
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "பீகார் டீசல் மானியத் திட்டம்",
                description = "வறட்சி காலத்தில் பயிர் பாசனத்திற்காக டீசல் வாங்குவதற்கு அரசு வழங்கும் நேரடி நிதி உதவி.",
                eligibility = "பீகாரின் நில உரிமையாளர்கள் மற்றும் குத்தகை விவசாயிகள்.",
                benefits = "டீசலுக்கு லிட்டருக்கு ₹75 மானியம் (ஒரு முறைக்கு ஏக்கருக்கு ₹750, அதிகபட்சம் 3 பாசனங்கள்).",
                amount = "₹75/லிட்டர் (அதிகபட்சம் ₹2,250/ஏக்கர்)",
                howToApply = "DBT Agriculture Bihar (dbtagriculture.bihar.gov.in) இணையதளத்தில் ஆன்லைனில் விண்ணப்பிக்கவும்."
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "கிருஷக் பந்து திட்டம்",
                description = "மேற்கு வங்க விவசாயிகளுக்கு ஆண்டுக்கு ₹10,000 நிதி உதவி மற்றும் ₹2 லட்சம் ஆயுள் காப்பீடு.",
                eligibility = "மேற்கு வங்காளத்தில் உள்ள விவசாயிகள் மற்றும் பதிவு செய்த குத்தகைதாரர்கள் (18 முதல் 60 வயது).",
                benefits = "ஆண்டுக்கு ₹10,000 (இரு தவணைகளில்) மற்றும் விவசாயி இறந்தால் குடும்பத்திற்கு ₹2 லட்சம் நிதி.",
                amount = "ஆண்டுக்கு ₹10,000 + ₹2 லட்சம் காப்பீடு",
                howToApply = "வட்டார வளர்ச்சி அலுவலகம் (BDO) அல்லது krishakbandhu.net இணையதளத்தில் விண்ணப்பிக்கவும்."
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "ஒய்.எஸ்.ஆர் ரைத்து பரோசா - பிஎம் கிசான்",
                description = "ஆந்திரப் பிரதேசத்தில் குத்தகை விவசாயிகள் உட்பட அனைத்து விவசாய குடும்பங்களுக்கும் ஆண்டுக்கு ₹13,500 நிதி உதவி.",
                eligibility = "ஆந்திராவின் நில உரிமையாளர்கள் மற்றும் ஆதிதிராவிட/பிற்படுத்தப்பட்ட குத்தகை விவசாயிகள்.",
                benefits = "விதை மற்றும் உரங்களுக்காக ஆண்டுக்கு 3 தவணைகளில் ₹13,500 வங்கி கணக்கில் செலுத்தப்படுகிறது.",
                amount = "ஆண்டுக்கு ₹13,500",
                howToApply = "ரைத்து பரோசா கேந்திரா (RBK) அல்லது கிராம செயலகத்தில் பதிவு செய்யவும்."
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "ஹரியானா பவந்தர் பர்பாய் யோஜனா",
                description = "காய்கறிகள் மற்றும் தோட்டக்கலை பயிர்களின் சந்தை விலை குறையும் போது இழப்பீடு வழங்கும் திட்டம்.",
                eligibility = "மேரி ஃபசல் மேரா ப்யோரா போர்ட்டலில் பதிவு செய்த ஹரியானா தோட்டக்கலை விவசாயிகள்.",
                benefits = "பாதுகாக்கப்பட்ட அடிப்படை விலைக்கும் சந்தை விற்பனை விலைக்கும் உள்ள வித்தியாசம் வங்கியில் வரவு வைக்கப்படும்.",
                amount = "குவிண்டாலுக்கான வித்தியாசத் தொகை",
                howToApply = "பயிர் நடும் முன் fasal.haryana.gov.in இல் பதிவு செய்யவும்."
            ),
            "od_kalia" to SchemeTranslation(
                title = "காலியா திட்டம் (விவசாயிகள் வாழ்வாதாரம் மற்றும் வருமான மேம்பாடு)",
                description = "ஒடிசாவின் சிறு, குறு மற்றும் நிலமற்ற விவசாய குடும்பங்களுக்கான விரிவான நிதி உதவி.",
                eligibility = "ஒடிசாவின் சிறு, குறு விவசாயிகள் மற்றும் நிலமற்ற விவசாய கூலி தொழிலாளர்கள்.",
                benefits = "சாகுபடிக்கு ₹10,000/ஆண்டு, நிலமற்றவர்களுக்கு ₹12,500 மற்றும் ₹2 லட்சம் காப்பீட்டு பாதுகாப்பு.",
                amount = "ஆண்டுக்கு ₹10,000 + வாழ்வாதார உதவி",
                howToApply = "kalia.odisha.gov.in போர்டல் அல்லது கிராம பஞ்சாயத்து அலுவலகத்தில் விண்ணப்பிக்கவும்."
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "கேரளா விரிவான பயிர் காப்பீட்டுத் திட்டம்",
                description = "வெள்ளம், நிலச்சரிவு மற்றும் வனவிலங்கு சேதங்களுக்கு எதிராக 25+ முக்கிய பயிர்களுக்கு மாநில காப்பீடு.",
                eligibility = "கேரளாவில் நெல், வாழை, வாசனைப் பொருட்கள், காய்கறிகள் மற்றும் ரப்பர் பயிரிடும் விவசாயிகள்.",
                benefits = "இயற்கை பேரிடர் மற்றும் வனவிலங்குகளால் ஏற்படும் பயிர் சேதங்களுக்கு உடனடி இழப்பீடு.",
                amount = "பயிருக்கு ஏற்ப ஏக்கருக்கு ₹35,000 வரை இழப்பீடு",
                howToApply = "AIMS இணையதளம் (aims.kerala.gov.in) அல்லது உள்ளூர் கிருஷி பவனில் விண்ணப்பிக்கவும்."
            ),
        ),
        "kn" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "ಪ್ರಧಾನ ಮಂತ್ರಿ ಕಿಸಾನ್ ಸಮ್ಮಾನ್ ನಿಧಿ (PM-KISAN)",
                description = "ಅರ್ಹ ರೈತ ಕುಟುಂಬಗಳಿಗೆ ವಾರ್ಷಿಕ ₹6,000 ಆರ್ಥಿಕ ನೆರವು, ₹2,000 ರಂತೆ 3 ಕಂತುಗಳಲ್ಲಿ ನೇರವಾಗಿ ಬ್ಯಾಂಕ್ ಖಾತೆಗೆ ಜಮೆ ಮಾಡಲಾಗುತ್ತದೆ.",
                eligibility = "ಕೃಷಿ ಭೂಮಿ ಹೊಂದಿರುವ ಎಲ್ಲಾ ರೈತ ಕುಟುಂಬಗಳು ಅರ್ಹರು. ಸಾಂಸ್ಥಿಕ ಭೂಮಾಲೀಕರು ಮತ್ತು ಆದಾಯ ತೆರಿಗೆ ಪಾವತಿದಾರರಿಗೆ ಅನ್ವಯಿಸುವುದಿಲ್ಲ.",
                benefits = "ವರ್ಷಕ್ಕೆ 3 ಕಂತುಗಳಲ್ಲಿ ಒಟ್ಟು ₹6,000 ನೇರ ಬ್ಯಾಂಕ್ ವರ್ಗಾವಣೆ.",
                amount = "ವರ್ಷಕ್ಕೆ ₹6,000",
                howToApply = "ಗ್ರಾಮ ಒನ್, ಸಿಎಸ್‌ಸಿ (CSC) ಕೇಂದ್ರ ಅಥವಾ pmkisan.gov.in ನಲ್ಲಿ ಪಹಣಿ (RTC) ಮತ್ತು ಆಧಾರ್ ನೊಂದಿಗೆ ನೋಂದಾಯಿಸಿ."
            ),
            "pmfby" to SchemeTranslation(
                title = "ಪ್ರಧಾನ ಮಂತ್ರಿ ಫಸಲ್ ಬಿಮಾ ಯೋಜನೆ (PMFBY)",
                description = "ನೈಸರ್ಗಿಕ ವಿಕೋಪಗಳು, ಕೀಟ ಮತ್ತು ರೋಗಬಾಧೆಯಿಂದ ಬೆಳೆ ನಷ್ಟವಾದಾಗ ಆಹಾರ, ಎಣ್ಣೆಕಾಳು ಮತ್ತು ತೋಟಗಾರಿಕಾ ಬೆಳೆಗಳಿಗೆ ಸಮಗ್ರ ಬೆಳೆ ವಿಮೆ ರಕ್ಷಣೆ.",
                eligibility = "ಅಧಿಸೂಚಿತ ಬೆಳೆ ಬೆಳೆಯುವ ಎಲ್ಲಾ ರೈತರು ಮತ್ತು ಗೇಣಿದಾರರು (ಸಾಲ ಪಡೆದ ಮತ್ತು ಸಾಲ ಪಡೆಯದ ರೈತರು).",
                benefits = "ಪೂರ್ಣ ವಿಮಾ ಮೊತ್ತ ಪರಿಹಾರ. ಪ್ರೀಮಿಯಂ: ಮುಂಗಾರು 2%, ಹಿಂಗಾರು 1.5%, ತೋಟಗಾರಿಕೆ 5%. ಉಳಿದ ಪ್ರೀಮಿಯಂ ಸರ್ಕಾರವೇ ಭರಿಸುತ್ತದೆ.",
                amount = "ಬೆಳೆ ಮತ್ತು ವಿಸ್ತೀರ್ಣದ ಆಧಾರದ ಮೇಲೆ ವಿಮಾ ಮೊತ್ತ",
                howToApply = "ಸಮೀಪದ ಬ್ಯಾಂಕ್ ಶಾಖೆ, ಸಿಎಸ್‌ಸಿ ಕೇಂದ್ರ ಅಥವಾ ಸಂರಕ್ಷಣೆ (Samrakshane) ಪೋರ್ಟಲ್ ಮೂಲಕ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "kcc" to SchemeTranslation(
                title = "ಕಿಸಾನ್ ಕ್ರೆಡಿಟ್ ಕಾರ್ಡ್ (KCC - ರೈತ ಸಾಲ ಕಾರ್ಡ್)",
                description = "ಬೆಳೆ ಉತ್ಪಾದನೆ, ಕೃಷಿ ವೆಚ್ಚಗಳು ಮತ್ತು ಕೊಯ್ಲೋತ್ತರ ನಿರ್ವಹಣೆಗೆ ಅತ್ಯಂತ ರಿಯಾಯಿತಿ ಬಡ್ಡಿದರದಲ್ಲಿ ಅಲ್ಪಾವಧಿ ಸಾಲ ಸೌಲಭ್ಯ.",
                eligibility = "ವೈಯಕ್ತಿಕ/ಜಂಟಿ ರೈತರು, ಗೇಣಿದಾರರು ಮತ್ತು ಸ್ವಸಹಾಯ ಗುಂಪುಗಳ ರೈತ ಸದಸ್ಯರು.",
                benefits = "4% ರಿಯಾಯಿತಿ ಬಡ್ಡಿದರದಲ್ಲಿ ಸಾಲ (ಸಮಯಕ್ಕೆ ಮರುಪಾವತಿಸಿದರೆ 3% ಬಡ್ಡಿ ಸಹಾಯಧನ). ಎಟಿಎಂ ಕಾರ್ಡ್ ಸೌಲಭ್ಯ.",
                amount = "4% ಬಡ್ಡಿದರದಲ್ಲಿ ₹3 ಲಕ್ಷದವರೆಗೆ ಸಾಲ",
                howToApply = "ಭೂ ದಾಖಲೆಗಳು (RTC), ಆಧಾರ್ ಮತ್ತು ಫೋಟೋಗಳೊಂದಿಗೆ ಯಾವುದೇ ವಾಣಿಜ್ಯ, ಗ್ರಾಮೀಣ ಅಥವಾ ಸಹಕಾರಿ ಬ್ಯಾಂಕಿನಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "soil_health" to SchemeTranslation(
                title = "ಮಣ್ಣು ಆರೋಗ್ಯ ಕಾರ್ಡ್ ಯೋಜನೆ (Soil Health Card)",
                description = "ಜಮೀನಿನ ಮಣ್ಣಿನ ಫಲವತ್ತತೆ ಪರೀಕ್ಷಿಸಿ, ಬೆಳೆವಾರು ಪೋಷಕಾಂಶಗಳು ಮತ್ತು ರಸಗೊಬ್ಬರಗಳ ಸಮತೋಲಿತ ಬಳಕೆಗೆ ವೈಜ್ಞಾನಿಕ ಶಿಫಾರಸುಗಳು.",
                eligibility = "ಕೃಷಿ ಭೂಮಿ ಹೊಂದಿರುವ ರಾಜ್ಯದ ಎಲ್ಲಾ ರೈತರು.",
                benefits = "ಉಚಿತ ಮಣ್ಣು ಪರೀಕ್ಷೆ, 12 ಪ್ರಮುಖ ಪೋಷಕಾಂಶಗಳ ವರದಿ ಮತ್ತು 2 ವರ್ಷಗಳ ಕಾಲ ಗೊಬ್ಬರ ನಿರ್ವಹಣಾ ಸಲಹೆ.",
                amount = "ಸಂಪೂರ್ಣ ಉಚಿತ",
                howToApply = "ರೈತ ಸಂಪರ್ಕ ಕೇಂದ್ರ (RSK), ಕೃಷಿ ವಿಜ್ಞಾನ ಕೇಂದ್ರ (KVK) ಅಥವಾ soilhealth.dac.gov.in ಗೆ ಭೇಟಿ ನೀಡಿ."
            ),
            "pmksy" to SchemeTranslation(
                title = "ಪ್ರಧಾನ ಮಂತ್ರಿ ಕೃಷಿ ಸಿಂಚಾಯಿ ಯೋಜನೆ (ಹನಿ ನೀರಾವರಿ)",
                description = "ಪ್ರತಿಯೊಂದು ಜಮೀನಿಗೂ ನೀರಾವರಿ ಸೌಲಭ್ಯ ಒದಗಿಸುವುದು ಮತ್ತು ಹನಿ/ತುಂತುರು ನೀರಾವರಿ ಮೂಲಕ ನೀರಿನ ಸದ್ಬಳಕೆ ಹೆಚ್ಚಿಸುವುದು.",
                eligibility = "ಎಲ್ಲಾ ರೈತರು. ಮಳೆಯಾಶ್ರಿತ ಮತ್ತು ಹಿಂದುಳಿದ ತಾಲೂಕುಗಳಿಗೆ ಆದ್ಯತೆ.",
                benefits = "ಹನಿ ಮತ್ತು ತುಂತುರು ನೀರಾವರಿಗೆ ಸಣ್ಣ/ಅತಿ ಸಣ್ಣ ರೈತರಿಗೆ 90% (ಕರ್ನಾಟಕ ಹೆಚ್ಚುವರಿ ಸಹಾಯಧನ ಸೇರಿದಂತೆ) ಸಹಾಯಧನ.",
                amount = "ಸಣ್ಣ ರೈತರಿಗೆ 55% ರಿಂದ 90% ರವರೆಗೆ ಸಬ್ಸಿಡಿ",
                howToApply = "ರೈತ ಸಂಪರ್ಕ ಕೇಂದ್ರ ಅಥವಾ ತಾಲೂಕು ತೋಟಗಾರಿಕೆ ಸಹಾಯಕ ನಿರ್ದೇಶಕರ ಕಚೇರಿಯಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "pkvy" to SchemeTranslation(
                title = "ಪರಂಪರಾಗತ್ ಕೃಷಿ ವಿಕಾಸ ಯೋಜನೆ (PKVY)",
                description = "ಕ್ಲಸ್ಟರ್ ಮಾದರಿಯಲ್ಲಿ ಸಾವಯವ ಕೃಷಿ ಉತ್ತೇಜನ ಮತ್ತು ರೈತರಿಗೆ PGS ಸಾವಯವ ಪ್ರಮಾಣೀಕರಣ ನೆರವು.",
                eligibility = "ಸಾವಯವ ಕೃಷಿಗಾಗಿ 50 ಎಕರೆ ವಿಸ್ತೀರ್ಣದಲ್ಲಿ ಗುಂಪು ರಚಿಸುವ 50 ಅಥವಾ ಹೆಚ್ಚಿನ ರೈತರು.",
                benefits = "ಸಾವಯವ ಗೊಬ್ಬರ, ಕೀಟನಾಶಕ ಮತ್ತು ಮಾರುಕಟ್ಟೆಗಾಗಿ 3 ವರ್ಷಗಳಲ್ಲಿ ಹೆಕ್ಟೇರ್‌ಗೆ ₹50,000 ಆರ್ಥಿಕ ನೆರವು.",
                amount = "ಹೆಕ್ಟೇರ್‌ಗೆ ₹50,000 (3 ವರ್ಷಗಳಲ್ಲಿ)",
                howToApply = "50 ರೈತರ ಗುಂಪು ರಚಿಸಿ ತಾಲೂಕು ಕೃಷಿ ಅಧಿಕಾರಿಗೆ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "enam" to SchemeTranslation(
                title = "ಇ-ನ್ಯಾಮ್ (ರಾಷ್ಟ್ರೀಯ ಕೃಷಿ ಮಾರುಕಟ್ಟೆ)",
                description = "ಕೃಷಿ ಉತ್ಪನ್ನಗಳ ಆನ್‌ಲೈನ್ ಮಾರಾಟ ವೇದಿಕೆ, ಇದು ದೇಶಾದ್ಯಂತದ ಎಪಿಎಂಸಿ ಮಾರುಕಟ್ಟೆಗಳನ್ನು ಸಂಯೋಜಿಸುತ್ತದೆ.",
                eligibility = "ಎಪಿಎಂಸಿ ಮಾರುಕಟ್ಟೆಯಲ್ಲಿ ನೋಂದಾಯಿತ ಎಲ್ಲಾ ರೈತರು ಮತ್ತು ವರ್ತಕರು.",
                benefits = "ಪಾರದರ್ಶಕ ಹರಾಜು, ಮಧ್ಯವರ್ತಿಗಳ ಹಾವಳಿ ಇಲ್ಲ, ಉತ್ತಮ ಧಾರಣೆ ಮತ್ತು ನೇರವಾಗಿ ಬ್ಯಾಂಕ್ ಖಾತೆಗೆ ಹಣ ಸಂದಾಯ.",
                amount = "ರೈತರಿಗೆ ಸಂಪೂರ್ಣ ಉಚಿತ",
                howToApply = "enam.gov.in ನಲ್ಲಿ ನೋಂದಾಯಿಸಿ ಅಥವಾ ಸಮೀಪದ ಎಪಿಎಂಸಿ ಮಾರುಕಟ್ಟೆಗೆ ಭೇಟಿ ನೀಡಿ."
            ),
            "pm_kusum" to SchemeTranslation(
                title = "ಪಿಎಂ-ಕುಸುಮ್ (ಸೌರ ಪಂಪ್ ಯೋಜನೆ)",
                description = "ಜಮೀನುಗಳಲ್ಲಿ ಸೋಲಾರ್ ಪಂಪ್ ಸೆಟ್ ಅಳವಡಿಕೆ ಮತ್ತು ಗ್ರಿಡ್ ಸಂಪರ್ಕಿತ ಪಂಪ್‌ಗಳನ್ನು ಸೌರಶಕ್ತಿಗೆ ಪರಿವರ್ತಿಸುವುದು.",
                eligibility = "ಎಲ್ಲಾ ರೈತರು, ರೈತ ಉತ್ಪಾದಕ ಕಂಪನಿಗಳು (FPO) ಮತ್ತು ಗ್ರಾಮ ಪಂಚಾಯಿತಿಗಳು.",
                benefits = "ಸೋಲಾರ್ ಪಂಪ್‌ಗಳಿಗೆ 60% ರಿಂದ 80% ರವರೆಗೆ ಸರ್ಕಾರಿ ಸಹಾಯಧನ (ಕೇಂದ್ರ + ರಾಜ್ಯ).",
                amount = "ಒಟ್ಟು 60% ರಿಂದ 80% ಸಹಾಯಧನ",
                howToApply = "ಕರ್ನಾಟಕ ನವೀಕರಿಸಬಹುದಾದ ಇಂಧನ ಅಭಿವೃದ್ಧಿ ನಿಯಮಿತ (KREDL) ಅಥವಾ ಎಸ್ಕಾಂಗಳ (ESCOMs) ಮೂಲಕ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "nmsa" to SchemeTranslation(
                title = "ಸುಸ್ಥಿರ ಕೃಷಿಗಾಗಿ ರಾಷ್ಟ್ರೀಯ ಮಿಷನ್ (NMSA)",
                description = "ಹವಾಮಾನ ವೈಪರೀತ್ಯ ತಾಳಿಕೊಳ್ಳುವ ಕೃಷಿ ಪದ್ಧತಿಗಳು, ಮಣ್ಣು ಸಂರಕ್ಷಣೆ ಮತ್ತು ಮಳೆಯಾಶ್ರಿತ ಪ್ರದೇಶಾಭಿವೃದ್ಧಿ.",
                eligibility = "ಮಳೆಯಾಶ್ರಿತ ಮತ್ತು ಒಣಭೂಮಿ ಬೇಸಾಯ ಮಾಡುವ ಎಲ್ಲಾ ರೈತರು.",
                benefits = "ಕೃಷಿ ಹೊಂಡ ನಿರ್ಮಾಣ, ಎರೆಹುಳು ಗೊಬ್ಬರ, ಹಸಿರೆಲೆ ಗೊಬ್ಬರ ಮತ್ತು ಬೀಜ ಸಹಾಯಧನ.",
                amount = "ಮಳೆಯಾಶ್ರಿತ ಅಭಿವೃದ್ಧಿಗೆ ₹12,500/ಹೆಕ್ಟೇರ್ ಸಹಾಯ",
                howToApply = "ರೈತ ಸಂಪರ್ಕ ಕೇಂದ್ರ ಅಥವಾ ಕೃಷಿ ಇಲಾಖೆಯ ಮೂಲಕ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "rkvy" to SchemeTranslation(
                title = "ರಾಷ್ಟ್ರೀಯ ಕೃಷಿ ವಿಕಾಸ ಯೋಜನೆ (RKVY-RAFTAAR)",
                description = "ಕೃಷಿ ಮೂಲಸೌಕರ್ಯ, ಆಧುನಿಕ ತಂತ್ರಜ್ಞಾನ ಮತ್ತು ಅಗ್ರಿ-ಸ್ಟಾರ್ಟಪ್‌ಗಳಿಗೆ ಆರ್ಥಿಕ ಪ್ರೋತ್ಸಾಹ.",
                eligibility = "ರೈತರು, ಎಫ್‌ಪಿಒ ಮತ್ತು ನವೋದ್ಯಮಿ ಕೃಷಿ ಸ್ಟಾರ್ಟಪ್‌ಗಳು.",
                benefits = "ಕೊಯ್ಲೋತ್ತರ ಸಂಸ್ಕರಣಾ ಘಟಕಗಳು ಮತ್ತು ಅಗ್ರಿ-ಸ್ಟಾರ್ಟಪ್‌ಗಳಿಗೆ ₹25 ಲಕ್ಷದವರೆಗೆ ಅನುದಾನ.",
                amount = "ಸ್ಟಾರ್ಟಪ್ ಅನುದಾನ ₹25 ಲಕ್ಷದವರೆಗೆ",
                howToApply = "rkvy.nic.in ಪೋರ್ಟಲ್ ಅಥವಾ ಕೃಷಿ ನಿರ್ದೇಶನಾಲಯದ ಮೂಲಕ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "agri_infra" to SchemeTranslation(
                title = "ಕೃಷಿ ಮೂಲಸೌಕರ್ಯ ನಿಧಿ (AIF)",
                description = "ಗೋದಾಮುಗಳು, ಶೀತಲಗೃಹಗಳು ಮತ್ತು ಪ್ರಾಥಮಿಕ ಸಂಸ್ಕರಣಾ ಘಟಕಗಳನ್ನು ನಿರ್ಮಿಸಲು ರಿಯಾಯಿತಿ ಸಾಲ ಸೌಲಭ್ಯ.",
                eligibility = "ರೈತರು, ಎಫ್‌ಪಿಒಗಳು, ಪ್ರಾಥಮಿಕ ಕೃಷಿ ಪತ್ತಿನ ಸಹಕಾರ ಸಂಘಗಳು (PACS) ಮತ್ತು ಉದ್ಯಮಿಗಳು.",
                benefits = "₹2 ಕೋಟಿಯವರೆಗಿನ ಸಾಲಗಳಿಗೆ 7 ವರ್ಷಗಳವರೆಗೆ ವಾರ್ಷಿಕ 3% ಬಡ್ಡಿ ಸಹಾಯಧನ ಮತ್ತು ಸಾಲ ಖಾತರಿ.",
                amount = "₹2 ಕೋಟಿಯವರೆಗೆ ಸಾಲದಲ್ಲಿ 3% ಬಡ್ಡಿ ರಿಯಾಯಿತಿ",
                howToApply = "agriinfra.dac.gov.in ನಲ್ಲಿ ಯೋಜನಾ ಪ್ರಸ್ತಾವನೆಯನ್ನು ಸಲ್ಲಿಸಿ."
            ),
            "smam" to SchemeTranslation(
                title = "ಕೃಷಿ ಯಾಂತ್ರೀಕರಣ ಉಪ-ಮಿಷನ್ (SMAM)",
                description = "ಟ್ರಾಕ್ಟರ್, ಪವರ್ ಟಿಲ್ಲರ್, ಕಟಾವು ಯಂತ್ರ ಮತ್ತು ಆಧುನಿಕ ಕೃಷಿ ಉಪಕರಣಗಳ ಖರೀದಿಗೆ ಸಹಾಯಧನ.",
                eligibility = "ಸಣ್ಣ, ಅತಿ ಸಣ್ಣ, ಮಹಿಳಾ, ಪರಿಶಿಷ್ಟ ಜಾತಿ/ಪಂಗಡದ ರೈತರು ಮತ್ತು ಗ್ರಾಮೀಣ ಯುವಕರು.",
                benefits = "ಕೃಷಿ ಯಂತ್ರೋಪಕರಣಗಳ ಖರೀದಿಯ ಮೇಲೆ 40% ರಿಂದ 50% ನೇರ ಸಹಾಯಧನ.",
                amount = "ಉಪಕರಣಗಳ ಮೇಲೆ 40%-50% ಸಬ್ಸಿಡಿ",
                howToApply = "agrimachinery.nic.in ಅಥವಾ ಕೃಷಿ ಇಲಾಖೆಯ ಪೋರ್ಟಲ್ ನಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "nbhm" to SchemeTranslation(
                title = "ರಾಷ್ಟ್ರೀಯ ಜೇನುಸಾಕಣೆ ಮತ್ತು ಜೇನುತುಪ್ಪ ಮಿಷನ್ (NBHM)",
                description = "ಹೆಚ್ಚುವರಿ ಆದಾಯ, ಪರಾಗಸ್ಪರ್ಶ ಸುಧಾರಣೆ ಮತ್ತು ಗುಣಮಟ್ಟದ ಜೇನುತುಪ್ಪ ಉತ್ಪಾದನೆಗಾಗಿ ವೈಜ್ಞಾನಿಕ ಜೇನುಸಾಕಣೆ.",
                eligibility = "ರೈತರು, ಮಹಿಳಾ ಸ್ವಸಹಾಯ ಸಂಘಗಳು ಮತ್ತು ಜೇನು ಸಾಕಣೆದಾರರು.",
                benefits = "ಜೇನು ಪೆಟ್ಟಿಗೆಗಳು, ಕಾಲೋನಿಗಳು ಮತ್ತು ಜೇನು ಸಂಸ್ಕರಣಾ ಘಟಕಗಳಿಗೆ 80% ವರೆಗೆ ಸಹಾಯಧನ.",
                amount = "ಮಹಿಳೆಯರು/ಎಸ್‌ಸಿಗೆ 80%, ಇತರರಿಗೆ 50% ಸಬ್ಸಿಡಿ",
                howToApply = "nbb.gov.in ಪೋರ್ಟಲ್ ಅಥವಾ ತಾಲೂಕು ತೋಟಗಾರಿಕೆ ಕಚೇರಿಯನ್ನು ಸಂಪರ್ಕಿಸಿ."
            ),
            "midh" to SchemeTranslation(
                title = "ಸಮಗ್ರ ತೋಟಗಾರಿಕಾ ಅಭಿವೃದ್ಧಿ ಮಿಷನ್ (MIDH)",
                description = "ಹಣ್ಣುಗಳು, ತರಕಾರಿಗಳು, ಸಂಬಾರ ಬೆಳೆಗಳು, ಹೂವುಗಳು, ಪಾಲಿಹೌಸ್ ಮತ್ತು ಶೀತಲ ಗೋದಾಮುಗಳ ಸರ್ವತೋಮುಖ ಅಭಿವೃದ್ಧಿ.",
                eligibility = "ತೋಟಗಾರಿಕೆ ಬೆಳೆಗಾರರು, ರೈತ ಸಂಘಗಳು ಮತ್ತು ಎಫ್‌ಪಿಒಗಳು.",
                benefits = "ಹೊಸ ತೋಟ ನಿರ್ಮಾಣ, ನೆರಳುಪರದೆ ಹಸಿರುಮನೆ ಮತ್ತು ಪ್ಯಾಕ್-ಹೌಸ್ ನಿರ್ಮಾಣಕ್ಕೆ 40-50% ಆರ್ಥಿಕ ನೆರವು.",
                amount = "40% ರಿಂದ 50% ರವರೆಗೆ ಬಂಡವಾಳ ಸಬ್ಸಿಡಿ",
                howToApply = "ಜಿಲ್ಲಾ ತೋಟಗಾರಿಕಾ ಉಪನಿರ್ದೇಶಕರ ಕಚೇರಿಯನ್ನು ಸಂಪರ್ಕಿಸಿ."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "ಪಿಎಂ-ಆಶಾ (ರೈತರ ಆದಾಯ ಸಂರಕ್ಷಣಾ ಅಭಿಯಾನ)",
                description = "ದ್ವಿದಳ ಧಾನ್ಯಗಳು, ಎಣ್ಣೆಕಾಳುಗಳು ಮತ್ತು ಕೊಬ್ಬರಿಗೆ ಕನಿಷ್ಠ ಬೆಂಬಲ ಬೆಲೆ (MSP) ಖಾತರಿ ಯೋಜನೆ.",
                eligibility = "ಅಧಿಸೂಚಿತ ಎಣ್ಣೆಕಾಳು ಮತ್ತು ದ್ವಿದಳ ಧಾನ್ಯ ಬೆಳೆಯುವ ನೋಂದಾಯಿತ ರೈತರು.",
                benefits = "ಮಾರುಕಟ್ಟೆ ದರ ಬೆಂಬಲ ಬೆಲೆಗಿಂತ ಕಡಿಮೆಯಾದಾಗ ಸರ್ಕಾರಿ ಖರೀದಿ ಅಥವಾ ಬೆಲೆ ವ್ಯತ್ಯಾಸ ಮೊತ್ತ ನೇರ ಖಾತೆಗೆ ಜಮೆ.",
                amount = "ಸಂಪೂರ್ಣ ಕನಿಷ್ಠ ಬೆಂಬಲ ಬೆಲೆ (MSP) ಲಾಭ",
                howToApply = "ಬೆಳೆ ಕಟಾವಿಗೆ ಮೊದಲು ರಾಜ್ಯ ಖರೀದಿ ಪೋರ್ಟಲ್‌ನಲ್ಲಿ ನೋಂದಾಯಿಸಿ."
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "ಮಹಾತ್ಮಾ ಜ್ಯೋತಿರಾವ್ ಫುಲೆ ರೈತ ಸಾಲಮನ್ನಾ ಯೋಜನೆ",
                description = "ಮಹಾರಾಷ್ಟ್ರದಲ್ಲಿ ₹2 ಲಕ್ಷದವರೆಗಿನ ಬಾಕಿ ಬೆಳೆ ಸಾಲವನ್ನು ಸಂಪೂರ್ಣವಾಗಿ ಮನ್ನಾ ಮಾಡುವ ಯೋಜನೆ.",
                eligibility = "ಮಹಾರಾಷ್ಟ್ರದ ಸಹಕಾರಿ ಅಥವಾ ವಾಣಿಜ್ಯ ಬ್ಯಾಂಕುಗಳಲ್ಲಿ ₹2 ಲಕ್ಷದವರೆಗೆ ಬೆಳೆ ಸಾಲ ಬಾಕಿ ಇರುವ ರೈತರು.",
                benefits = "₹2 ಲಕ್ಷದವರೆಗಿನ ಸಾಲ ಸಂಪೂರ್ಣ ಮನ್ನಾ ಮತ್ತು ನಿಯಮಿತ ಮರುಪಾವತಿದಾರರಿಗೆ ₹50,000 ಪ್ರೋತ್ಸಾಹಧನ.",
                amount = "₹2 ಲಕ್ಷ ಸಾಲಮನ್ನಾ + ₹50,000 ಪ್ರೋತ್ಸಾಹಧನ",
                howToApply = "MahaDBT ಪೋರ್ಟಲ್ ಅಥವಾ ತಹಶೀಲ್ದಾರ್ ಕಚೇರಿಯಲ್ಲಿ ಆಧಾರ್ ನೊಂದಿಗೆ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "ನಾನಾಜಿ ದೇಶ್‌ಮುಖ್ ಕೃಷಿ ಸಂಜೀವಿನಿ ಯೋಜನೆ (PoCRA)",
                description = "ಮಹಾರಾಷ್ಟ್ರದ ಬರಪೀಡಿತ ಮತ್ತು ಲವಣಯುಕ್ತ ಪ್ರದೇಶಗಳಲ್ಲಿ ಹವಾಮಾನ-ಸ್ನೇಹಿ ಕೃಷಿ ಯೋಜನೆ.",
                eligibility = "ಮರಾಠವಾಡ ಮತ್ತು ವಿದರ್ಭದ 15 ಬರಪೀಡಿತ ಜಿಲ್ಲೆಗಳ ಸಣ್ಣ, ಅತಿ ಸಣ್ಣ ರೈತರು.",
                benefits = "ಕೃಷಿ ಹೊಂಡ, ಹನಿ ನೀರಾವರಿ, ನೆರಳುಪರದೆ ಮನೆ ಮತ್ತು ಮಣ್ಣು ಸುಧಾರಣೆಗೆ 75% ವರೆಗೆ ಸಬ್ಸಿಡಿ.",
                amount = "ಜಲ ಸಂರಕ್ಷಣೆ ಮತ್ತು ಹನಿ ನೀರಾವರಿಗೆ 75% ವರೆಗೆ ಸಬ್ಸಿಡಿ",
                howToApply = "mahapocra.gov.in ಪೋರ್ಟಲ್ ಮೂಲಕ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "ನೀರು ಉಳಿಸಿ ಹಣ ಗಳಿಸಿ ಯೋಜನೆ",
                description = "ಕೃಷಿ ಪಂಪ್‌ಸೆಟ್‌ಗಳಲ್ಲಿ ವಿದ್ಯುತ್ ಮತ್ತು ಅಂತರ್ಜಲ ಉಳಿಸಿದ ರೈತರಿಗೆ ನೇರ ನಗದು ಪ್ರೋತ್ಸಾಹಧನ.",
                eligibility = "ಮೀಟರ್ ಇರುವ ಕೃಷಿ ವಿದ್ಯುತ್ ಸಂಪರ್ಕ ಹೊಂದಿರುವ ಪಂಜಾಬ್ ರೈತರು.",
                benefits = "ವಿದ್ಯುತ್ ಉಳಿಸಿದರೆ ಪ್ರತಿ ಯೂನಿಟ್‌ಗೆ ₹4 ರಂತೆ ನೇರವಾಗಿ ಬ್ಯಾಂಕ್ ಖಾತೆಗೆ ಜಮೆ.",
                amount = "ಉಳಿಸಿದ ಪ್ರತಿ ಯೂನಿಟ್ ವಿದ್ಯುತ್‌ಗೆ ₹4",
                howToApply = "ಪಂಜಾಬ್ ಸ್ಟೇಟ್ ಪವರ್ ಕಾರ್ಪೊರೇಷನ್ (PSPCL) ಉಪ-ವಿಭಾಗದಲ್ಲಿ ನೋಂದಾಯಿಸಿ."
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "ಯುಪಿ ಕಿಸಾನ್ ಉದಯ್ ಯೋಜನೆ",
                description = "ಉತ್ತರ ಪ್ರದೇಶದ ರೈತರಿಗೆ ಇಂಧನ ದಕ್ಷತೆಯುಳ್ಳ ಸ್ಮಾರ್ಟ್ ಸೌರ ಪಂಪ್‌ಸೆಟ್‌ಗಳ ಉಚಿತ ವಿತರಣೆ.",
                eligibility = "ಕೃಷಿ ಭೂಮಿ ಮತ್ತು ನೀರಾವರಿ ಅಗತ್ಯವಿರುವ ಯುಪಿಯ ಸಣ್ಣ ಮತ್ತು ಅತಿ ಸಣ್ಣ ರೈತರು.",
                benefits = "2 ರಿಂದ 5 ಹೆಚ್‌ಪಿ ಸ್ಮಾರ್ಟ್ ಸೋಲಾರ್ ಪಂಪ್ ಉಚಿತ ಅಳವಡಿಕೆ ಮತ್ತು 5 ವರ್ಷಗಳ ಉಚಿತ ನಿರ್ವಹಣೆ.",
                amount = "100% ಉಚಿತ ಸೋಲಾರ್ ಪಂಪ್ ವಿತರಣೆ",
                howToApply = "upagriculture.com ಪೋರ್ಟಲ್ ನಲ್ಲಿ ಆನ್‌ಲೈನ್‌ನಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "ಭಾವಾಂತರ್ ಭುಗ್ತಾನ್ ಯೋಜನೆ (ಬೆಲೆ ವ್ಯತ್ಯಾಸ ಪಾವತಿ)",
                description = "ಮಂಡಿಗಳಲ್ಲಿ ಬೆಲೆ ಕುಸಿದಾಗ ಕನಿಷ್ಠ ಬೆಂಬಲ ಬೆಲೆ ಮತ್ತು ಮಾರಾಟ ಬೆಲೆಯ ನಡುವಿನ ವ್ಯತ್ಯಾಸವನ್ನು ರೈತರಿಗೆ ಪಾವತಿಸುವ ಯೋಜನೆ.",
                eligibility = "ಮಧ್ಯಪ್ರದೇಶದ ಇ-ಉಪಾರ್ಜನ್ ಪೋರ್ಟಲ್‌ನಲ್ಲಿ ನೋಂದಾಯಿತ ರೈತರು.",
                benefits = "ಬೆಂಬಲ ಬೆಲೆ ಮತ್ತು ಮಾರುಕಟ್ಟೆ ಮಾರಾಟ ಬೆಲೆಯ ನಡುವಿನ ವ್ಯತ್ಯಾಸದ ಮೊತ್ತ ನೇರ ಖಾತೆಗೆ ಜಮೆ.",
                amount = "ಪ್ರತಿ ಕ್ವಿಂಟಾಲ್‌ಗೆ ಬೆಲೆ ವ್ಯತ್ಯಾಸ ಮೊತ್ತ",
                howToApply = "mpeuparjan.nic.in ನಲ್ಲಿ ಬೆಳೆ ಕಟಾವಿಗೆ ಮುನ್ನ ನೋಂದಾಯಿಸಿ."
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "ರೈತು ಬಂಧು ಯೋಜನೆ (ರೈತ ಹೂಡಿಕೆ ಸಹಾಯಧನ)",
                description = "ಕೃಷಿ ಖರ್ಚುಗಳಿಗಾಗಿ ವರ್ಷಕ್ಕೆ ಎಕರೆಗೆ ₹10,000 ನೇರ ಆರ್ಥಿಕ ನೆರವು.",
                eligibility = "ತೆಲಂಗಾಣದ ಪಟ್ಟಾದಾರ ಭೂಮಾಲೀಕ ರೈತ ಕುಟುಂಬಗಳು.",
                benefits = "ಮುಂಗಾರಿಗೆ ₹5,000 ಮತ್ತು ಹಿಂಗಾರಿಗೆ ₹5,000 ನಂತೆ ಎಕರೆಗೆ ₹10,000 ಬ್ಯಾಂಕ್ ಖಾತೆಗೆ ಜಮೆ.",
                amount = "ಎಕರೆಗೆ ವರ್ಷಕ್ಕೆ ₹10,000",
                howToApply = "ಧರಣಿ ಪೋರ್ಟಲ್ ಭೂ ದಾಖಲೆಗಳ ಆಧಾರದ ಮೇಲೆ ಸ್ವಯಂಚಾಲಿತ ನೋಂದಣಿ."
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "ತಮಿಳುನಾಡು ಉಚಿತ ಕೃಷಿ ವಿದ್ಯುತ್ ಯೋಜನೆ",
                description = "ತಮಿಳುನಾಡಿನಲ್ಲಿ ಕೃಷಿ ಪಂಪ್‌ಸೆಟ್‌ಗಳಿಗೆ 24 ಗಂಟೆಗಳ ಕಾಲ ಸಂಪೂರ್ಣ ಉಚಿತ ವಿದ್ಯುತ್ ಪೂರೈಕೆ.",
                eligibility = "ತಮಿಳುನಾಡಿನ ಎಲ್ಲಾ ನೋಂದಾಯಿತ ಕೃಷಿ ವಿದ್ಯುತ್ ಗ್ರಾಹಕರು.",
                benefits = "ಯಾವುದೇ ಬಿಲ್ ಇಲ್ಲದೆ ಕೃಷಿ ನೀರಾವರಿಗೆ 100% ಉಚಿತ ವಿದ್ಯುತ್.",
                amount = "100% ಉಚಿತ ಕೃಷಿ ವಿದ್ಯುತ್",
                howToApply = "ತಮಿಳುನಾಡು ವಿದ್ಯುತ್ ಮಂಡಳಿಗೆ (TANGEDCO) ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "ರೈತ ಸಿರಿ ಯೋಜನೆ (ಸಿರಿಧಾನ್ಯ ಪ್ರೋತ್ಸಾಹಧನ)",
                description = "ಸಿರಿಧಾನ್ಯಗಳ (ಮಿಲೆಟ್ಸ್) ಬೆಳೆಗಾರರಿಗೆ ಪ್ರೋತ್ಸಾಹಧನ ಮತ್ತು ₹3 ಲಕ್ಷದವರೆಗೆ ಶೂನ್ಯ ಬಡ್ಡಿದರದಲ್ಲಿ ಬೆಳೆ ಸಾಲ.",
                eligibility = "ಕರ್ನಾಟಕದ ಸಿರಿಧಾನ್ಯ ಬೆಳೆಗಾರರು ಮತ್ತು ರೈತ ಕುಟುಂಬಗಳು.",
                benefits = "ಸಿರಿಧಾನ್ಯ ಬೆಳೆಯಲು ಹೆಕ್ಟೇರ್‌ಗೆ ₹10,000 ಪ್ರೋತ್ಸಾಹಧನ ಮತ್ತು 0% ಬಡ್ಡಿದರದಲ್ಲಿ ಬೆಳೆ ಸಾಲ.",
                amount = "₹10,000/ಹೆಕ್ಟೇರ್ + 0% ಬಡ್ಡಿ ಸಾಲ",
                howToApply = "ಕರ್ನಾಟಕ ರೈತ ಮಿತ್ರ ಪೋರ್ಟಲ್ (raitamitra.karnataka.gov.in) ಅಥವಾ RSK ಯಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "ಕಿಸಾನ್ ಸೂರ್ಯೋದಯ ಯೋಜನೆ",
                description = "ಗುಜರಾತ್ ರೈತರಿಗೆ ಹಗಲಿನ ವೇಳೆ (ಬೆಳಿಗ್ಗೆ 5 ರಿಂದ ರಾತ್ರಿ 9 ರವರೆಗೆ) ನೀರಾವರಿಗಾಗಿ ವಿದ್ಯುತ್ ಸರಬರಾಜು.",
                eligibility = "ಗುಜರಾತ್‌ನಲ್ಲಿ ಕೃಷಿ ವಿದ್ಯುತ್ ಸಂಪರ್ಕ ಹೊಂದಿರುವ ಎಲ್ಲಾ ರೈತರು.",
                benefits = "ಹಗಲಿನಲ್ಲಿ ಸುರಕ್ಷಿತ ವಿದ್ಯುತ್ ಲಭ್ಯವಾಗುವುದರಿಂದ ರಾತ್ರಿಯ ನೀರಾವರಿ ತೊಂದರೆಗಳು ತಪ್ಪುತ್ತವೆ.",
                amount = "ಉಚಿತ ಸರ್ಕಾರಿ ಮೂಲಸೌಕರ್ಯ ಯೋಜನೆ",
                howToApply = "ವಿದ್ಯುತ್ ಕಂಪನಿಗಳ (DISCOMs) ಮೂಲಕ ಸ್ವಯಂಚಾಲಿತ ಸೇವೆ."
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "ರಾಜಸ್ಥಾನ ಕಿಸಾನ್ ಮಿತ್ರ ಇಂಧನ ಯೋಜನೆ",
                description = "ಕೃಷಿ ವಿದ್ಯುತ್ ಬಿಲ್‌ನಲ್ಲಿ ತಿಂಗಳಿಗೆ ₹1,000 ವರೆಗೆ ನೇರ ಸರ್ಕಾರಿ ಸಹಾಯಧನ.",
                eligibility = "ರಾಜಸ್ಥಾನದಲ್ಲಿ ಮೀಟರ್ ಇರುವ ಕೃಷಿ ವಿದ್ಯುತ್ ಸಂಪರ್ಕ ಹೊಂದಿರುವ ರೈತರು.",
                benefits = "ವಿದ್ಯುತ್ ಬಿಲ್‌ನಲ್ಲಿ ತಿಂಗಳಿಗೆ ₹1,000 (ವರ್ಷಕ್ಕೆ ₹12,000 ವರೆಗೆ) ನೇರ ರಿಯಾಯಿತಿ.",
                amount = "ವರ್ಷಕ್ಕೆ ₹12,000 ವರೆಗೆ ವಿದ್ಯುತ್ ಸಬ್ಸಿಡಿ",
                howToApply = "ವಿದ್ಯುತ್ ವಿತರಣಾ ನಿಗಮದಿಂದ ಬಿಲ್‌ಗಳಲ್ಲಿ ಸ್ವಯಂ ಕಡಿತ."
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "ಬಿಹಾರ ಡೀಸೆಲ್ ಸಬ್ಸಿಡಿ ಯೋಜನೆ",
                description = "ಬರಗಾಲದ ಸಮಯದಲ್ಲಿ ಕೃಷಿ ನೀರಾವರಿಗಾಗಿ ಡೀಸೆಲ್ ಖರೀದಿಗೆ ಸರ್ಕಾರಿ ಸಹಾಯಧನ.",
                eligibility = "ಬಿಹಾರದ ಭೂಮಾಲೀಕರು ಮತ್ತು ಗೇಣಿದಾರ ರೈತರು.",
                benefits = "ಡೀಸೆಲ್‌ಗೆ ಲೀಟರ್‌ಗೆ ₹75 ಸಹಾಯಧನ (ಒಂದು ಬಾರಿಯ ನೀರಾವರಿಗೆ ಎಕರೆಗೆ ₹750, ಗರಿಷ್ಠ 3 ಬಾರಿ).",
                amount = "₹75/ಲೀಟರ್ (ಗರಿಷ್ಠ ₹2,250/ಎಕರೆ)",
                howToApply = "ಡಿಬಿಟಿ ಅಗ್ರಿಕಲ್ಚರ್ ಬಿಹಾರ (dbtagriculture.bihar.gov.in) ಮೂಲಕ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "ಕೃಷಕ್ ಬಂಧು ಯೋಜನೆ",
                description = "ಪಶ್ಚಿಮ ಬಂಗಾಳದ ರೈತರಿಗೆ ವಾರ್ಷಿಕ ₹10,000 ಆರ್ಥಿಕ ನೆರವು ಮತ್ತು ₹2 ಲಕ್ಷ ಜೀವ ವಿಮಾ ರಕ್ಷಣೆ.",
                eligibility = "ಪಶ್ಚಿಮ ಬಂಗಾಳದ ರೈತರು ಮತ್ತು ನೋಂದಾಯಿತ ಗೇಣಿದಾರರು (18 ರಿಂದ 60 ವರ್ಷ ವಯಸ್ಸು).",
                benefits = "ವರ್ಷಕ್ಕೆ ₹10,000 (ಎರಡು ಕಂತುಗಳಲ್ಲಿ) ಮತ್ತು ರೈತ ಮರಣ ಹೊಂದಿದರೆ ಕುಟುಂಬಕ್ಕೆ ₹2 ಲಕ್ಷ ಪರಿಹಾರ.",
                amount = "ವರ್ಷಕ್ಕೆ ₹10,000 + ₹2 ಲಕ್ಷ ವಿಮೆ",
                howToApply = "ಬಿಡಿಒ (BDO) ಕಚೇರಿ ಅಥವಾ krishakbandhu.net ಪೋರ್ಟಲ್ ಮೂಲಕ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "ವೈಎಸ್‌ಆರ್ ರೈತು ಭರೋಸಾ - ಪಿಎಂ ಕಿಸಾನ್",
                description = "ಆಂಧ್ರಪ್ರದೇಶದಲ್ಲಿ ಗೇಣಿದಾರರು ಸೇರಿದಂತೆ ಎಲ್ಲಾ ರೈತ ಕುಟುಂಬಗಳಿಗೆ ವರ್ಷಕ್ಕೆ ₹13,500 ಹೂಡಿಕೆ ಬೆಂಬಲ.",
                eligibility = "ಆಂಧ್ರದ ಭೂಮಾಲೀಕರು ಮತ್ತು ಹಿಂದುಳಿದ ವರ್ಗಗಳ ಗೇಣಿ ರೈತರು.",
                benefits = "ಬೀಜ, ಗೊಬ್ಬರಕ್ಕಾಗಿ ವರ್ಷಕ್ಕೆ 3 ಕಂತುಗಳಲ್ಲಿ ₹13,500 ಬ್ಯಾಂಕ್ ಖಾತೆಗೆ ಜಮೆ.",
                amount = "ವರ್ಷಕ್ಕೆ ₹13,500",
                howToApply = "ರೈತು ಭರೋಸಾ ಕೇಂದ್ರಗಳು (RBK) ಅಥವಾ ಗ್ರಾಮ ಸಚಿವಾಲಯದಲ್ಲಿ ನೋಂದಾಯಿಸಿ."
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "ಹರಿಯಾಣ ಭಾವಾಂತರ್ ಭರಪಾಯಿ ಯೋಜನೆ",
                description = "ತರಕಾರಿ ಮತ್ತು ತೋಟಗಾರಿಕಾ ಬೆಳೆಗಳ ಮಾರುಕಟ್ಟೆ ಬೆಲೆ ಕುಸಿದಾಗ ನಷ್ಟ ಪರಿಹಾರ ನೀಡುವ ಯೋಜನೆ.",
                eligibility = "ಮೇರಿ ಫಸಲ್ ಮೇರಾ ಬ್ಯೋರಾ ಪೋರ್ಟಲ್‌ನಲ್ಲಿ ನೋಂದಾಯಿತ ಹರಿಯಾಣದ ತೋಟಗಾರಿಕಾ ರೈತರು.",
                benefits = "ರಕ್ಷಿತ ಕನಿಷ್ಠ ಬೆಲೆ ಮತ್ತು ಮಾರುಕಟ್ಟೆ ಮಾರಾಟ ಬೆಲೆಯ ನಡುವಿನ ವ್ಯತ್ಯಾಸದ ಮೊತ್ತ ನೇರ ಖಾತೆಗೆ ಜಮೆ.",
                amount = "ಪ್ರತಿ ಕ್ವಿಂಟಾಲ್‌ಗೆ ಬೆಲೆ ವ್ಯತ್ಯಾಸ ಮೊತ್ತ",
                howToApply = "ಬೆಳೆ ನಾಟಿಗೆ ಮುನ್ನ fasal.haryana.gov.in ನಲ್ಲಿ ನೋಂದಾಯಿಸಿ."
            ),
            "od_kalia" to SchemeTranslation(
                title = "ಕಾಲಿಯಾ ಯೋಜನೆ (ರೈತರ ಜೀವನೋಪಾಯ ಮತ್ತು ಆದಾಯ ಹೆಚ್ಚಳ)",
                description = "ಒಡಿಶಾದ ಸಣ್ಣ, ಅತಿ ಸಣ್ಣ ಮತ್ತು ಭೂಹೀನ ಕೃಷಿ ಕುಟುಂಬಗಳಿಗೆ ಸಮಗ್ರ ಆರ್ಥಿಕ ನೆರವು.",
                eligibility = "ಒಡಿಶಾದ ಸಣ್ಣ/ಅತಿ ಸಣ್ಣ ರೈತರು ಮತ್ತು ಭೂಹೀನ ಕೃಷಿ ಕಾರ್ಮಿಕ ಕುಟುಂಬಗಳು.",
                benefits = "ಕೃಷಿಗೆ ₹10,000/ವರ್ಷ, ಭೂಹೀನರಿಗೆ ₹12,500 ಮತ್ತು ₹2 ಲಕ್ಷ ಜೀವ ವಿಮಾ ರಕ್ಷಣೆ.",
                amount = "ವರ್ಷಕ್ಕೆ ₹10,000 + ಜೀವನೋಪಾಯ ನೆರವು",
                howToApply = "kalia.odisha.gov.in ಪೋರ್ಟಲ್ ಅಥವಾ ಗ್ರಾಮ ಪಂಚಾಯಿತಿ ಕಚೇರಿಯಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "ಕೇರಳ ರಾಜ್ಯ ಸಮಗ್ರ ಬೆಳೆ ವಿಮೆ ಯೋಜನೆ",
                description = "ಪ್ರವಾಹ, ಭೂಕುಸಿತ ಮತ್ತು ವನ್ಯಜೀವಿ ಹಾನಿಯಿಂದ 25+ ಪ್ರಮುಖ ಬೆಳೆಗಳಿಗೆ ರಾಜ್ಯ ಮಟ್ಟದ ವಿಮಾ ರಕ್ಷಣೆ.",
                eligibility = "ಕೇರಳದಲ್ಲಿ ಭತ್ತ, ಬಾಳೆ, ಸಾಂಬಾರ ಪದಾರ್ಥಗಳು, ತರಕಾರಿ ಮತ್ತು ರಬ್ಬರ್ ಬೆಳೆಯುವ ರೈತರು.",
                benefits = "ನೈಸರ್ಗಿಕ ವಿಕೋಪ ಮತ್ತು ಕಾಡು ಪ್ರಾಣಿಗಳಿಂದ ಬೆಳೆ ಹಾನಿಯಾದರೆ ತ್ವರಿತ ಪರಿಹಾರ.",
                amount = "ಬೆಳೆಗೆ ಅನುಗುಣವಾಗಿ ಎಕರೆಗೆ ₹35,000 ವರೆಗೆ ಪರಿಹಾರ",
                howToApply = "AIMS ಪೋರ್ಟಲ್ (aims.kerala.gov.in) ಅಥವಾ ಸ್ಥಳೀಯ ಕೃಷಿ ಭವನದಲ್ಲಿ ಅರ್ಜಿ ಸಲ್ಲಿಸಿ."
            ),
        ),
        "gu" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "પીએમ-કિસાન (પ્રધાનમંત્રી કિસાન સન્માન નિધિ)",
                description = "પાત્ર ખેડૂત પરિવારોને વાર્ષિક ₹6,000 ની સીધી આર્થિક સહાય, ₹2,000 ના 3 સમાન હપ્તામાં સીધા બેંક ખાતામાં જમા કરવામાં આવે છે.",
                eligibility = "ખેતીલાયક જમીન ધરાવતા તમામ ખેડૂત પરિવારો. સંસ્થાકીય જમીનધારકો અને આવકવેરો ભરનારાઓને બાદ કરતાં.",
                benefits = "દર 4 મહિને ₹2,000 ના 3 હપ્તામાં વાર્ષિક ₹6,000 ની સીધી બેંક સહાય.",
                amount = "વાર્ષિક ₹6,000",
                howToApply = "નજીકના ઈ-ગ્રામ/સીએસસી (CSC) કેન્દ્ર અથવા pmkisan.gov.in પર 7/12, 8-અ અને આધાર કાર્ડ સાથે નોંધણી કરાવો."
            ),
            "pmfby" to SchemeTranslation(
                title = "પીએમએફબીવાય (પ્રધાનમંત્રી ફસલ બીમા યોજના)",
                description = "કુદરતી આફતો, કીટક અને રોગોથી પાક નુકસાન સામે તમામ ખાદ્યાન્ન, તેલીબિયાં અને બાગાયતી પાકોને વ્યાપક પાક વીમા રક્ષણ.",
                eligibility = "નોંધાયેલા પાકો વાવતા તમામ ખાતેદાર અને ભાગિયા ખેડૂતો.",
                benefits = "સંપૂર્ણ વીમા રક્ષણ. ખેડૂત પ્રીમિયમ: ખરીફ 2%, રવિ 1.5%, બાગાયત 5%. બાકીનું પ્રીમિયમ સરકાર ભોગવે છે.",
                amount = "પાક અને વિસ્તાર મુજબ વીમાની રકમ",
                howToApply = "નજીકની બેંક શાખા, વીસીઇ (VCE) કેન્દ્ર અથવા pmfby.gov.in પર નિયત સમયમર્યાદામાં અરજી કરો."
            ),
            "kcc" to SchemeTranslation(
                title = "કિસાન ક્રેડિટ કાર્ડ (કેસીસી - પાક ધિરાણ કાર્ડ)",
                description = "પાક ઉત્પાદન, ખેતી ખર્ચ અને લણણી પછીની જરૂરિયાતો માટે અત્યંત રાહત દરે અલ્પમુદતી પાક ધિરાણ.",
                eligibility = "વ્યક્તિગત/સંયુક્ત ખેડૂતો, ભાગિયા અને સ્વસહાય જૂથના ખેડૂત સભ્યો.",
                benefits = "4% રાહત દરે ધિરાણ (સમયસર ભરપાઈ પર 3% વ્યાજ સબસીડી). એટીએમ કાર્ડ અને વીમા સુવિધા.",
                amount = "4% વ્યાજે ₹3 લાખ સુધી પાક ધિરાણ",
                howToApply = "જમીનના 7/12, 8-અ, આધાર કાર્ડ અને ફોટા સાથે કોઈપણ વાણિજ્યિક, સહકારી કે ગ્રામીણ બેંકમાં અરજી કરો."
            ),
            "soil_health" to SchemeTranslation(
                title = "જમીન સ્વાસ્થ્ય કાર્ડ યોજના (સોઇલ હેલ્થ કાર્ડ)",
                description = "જમીનની ફળદ્રુપતા ચકાસીને પાક મુજબ જરૂરી પોષક તત્વો અને રાસાયણિક ખાતરોના સંતુલિત ઉપયોગની વૈજ્ઞાનિક ભલામણો.",
                eligibility = "ખેતીની જમીન ધરાવતા રાજ્યના તમામ ખેડૂતો.",
                benefits = "મફત માટી પરીક્ષણ, 12 મુખ્ય પોષક તત્ત્વોનો અહેવાલ અને 2 વર્ષ માટે ખાતર વ્યવસ્થાપનની ભલામણ.",
                amount = "સંપૂર્ણ મફત",
                howToApply = "નજીકની જમીન ચકાસણી પ્રયોગશાળા, કૃષિ વિજ્ઞાન કેન્દ્ર (KVK) અથવા ગ્રામ સેવકનો સંપર્ક કરો."
            ),
            "pmksy" to SchemeTranslation(
                title = "પીએમ કૃષિ સિંચાઈ યોજના (ટપક/ફુવારા પદ્ધતિ)",
                description = "દરેક ખેતર સુધી સિંચાઈનું પાણી પહોંચાડવું અને સૂક્ષ્મ સિંચાઈ (ટપક/ફુવારા) દ્વારા પાણીની બચત અને ઉત્પાદકતા વધારવી.",
                eligibility = "તમામ ખેડૂતો. સૂકા અને પછાત વિસ્તારોને વિશેષ પ્રાથમિકતા.",
                benefits = "ટપક અને ફુવારા પદ્ધતિ પર નાના/સીમાંત ખેડૂતોને 70% સુધી અને અન્ય ખેડૂતોને 50% સુધી સબસીડી (GGRC મારફત).",
                amount = "નાના ખેડૂતોને 55% થી 70% સુધી સબસીડી",
                howToApply = "ગુજરાત ગ્રીન રિવોલ્યુશન કંપની (GGRC) પોર્ટલ (ggrc.co.in) અથવા આઈ-ખેડૂત પર અરજી કરો."
            ),
            "pkvy" to SchemeTranslation(
                title = "પરંપરાગત કૃષિ વિકાસ યોજના (પીકેવીવાય)",
                description = "ક્લસ્ટર પદ્ધતિથી પ્રાકૃતિક અને ગૌ-આધારિત જૈવિક ખેતીને પ્રોત્સાહન અને પીજીએસ સર્ટિફિકેશન સહાય.",
                eligibility = "જૈવિક ખેતી માટે 50 એકરનું ક્લસ્ટર બનાવતા 50 કે તેથી વધુ ખેડૂતોનું જૂથ.",
                benefits = "દેશી ગાય આધારિત ખાતર, જીવામૃત અને માર્કેટિંગ માટે 3 વર્ષમાં હેક્ટર દીઠ ₹50,000 ની સહાય.",
                amount = "હેક્ટર દીઠ ₹50,000 (3 વર્ષમાં)",
                howToApply = "50 ખેડૂતોનું જૂથ બનાવી જિલ્લા ખેતીવાડી અધિકારીને દરખાસ્ત રજૂ કરો."
            ),
            "enam" to SchemeTranslation(
                title = "ઈ-નામ (રાષ્ટ્રીય કૃષિ બજાર)",
                description = "કૃષિ પેદાશોના વેચાણ માટેનું ઓનલાઇન રાષ્ટ્રીય વેપાર મંચ, જે દેશભરની એપીએમસી મંડીઓને જોડે છે.",
                eligibility = "એપીએમસી માર્કેટ યાર્ડમાં નોંધાયેલા તમામ ખેડૂતો અને વેપારીઓ.",
                benefits = "પારદર્શક હરાજી, વચેટિયા વગર સીધું વેચાણ, સારો ભાવ અને સીધા બેંક ખાતામાં નાણાં જમા.",
                amount = "ખેડૂતો માટે તદ્દન મફત",
                howToApply = "enam.gov.in પર નોંધણી કરાવો અથવા નજીકના ઈ-નામ માર્કેટ યાર્ડમાં આધાર સાથે જાઓ."
            ),
            "pm_kusum" to SchemeTranslation(
                title = "પીએમ-કુસુમ (સૂર્યઊર્જા સોલાર પંપ યોજના)",
                description = "ખેતરોમાં સ્વતંત્ર સોલાર પંપ સ્થાપિત કરવા અને ખેતીના વીજ પંપોને સોલાર ઊર્જામાં રૂપાંતરિત કરવા.",
                eligibility = "તમામ વ્યક્તિગત ખેડૂતો, ખેડૂત જૂથો, એફપીઓ અને પંચાયતો.",
                benefits = "સોલાર પંપ માટે 60% થી 80% સુધી સરકારી સબસીડી (કેન્દ્ર + રાજ્ય સરકાર).",
                amount = "કુલ 60% થી 80% સરકારી સબસીડી",
                howToApply = "આઈ-ખેડૂત પોર્ટલ (ikhedut.gujarat.gov.in) અથવા જીયુવીએનએલ (GUVNL) દ્વારા અરજી કરો."
            ),
            "nmsa" to SchemeTranslation(
                title = "ટકાઉ કૃષિ માટે રાષ્ટ્રીય મિશન (એનએમએસએ)",
                description = "વાતાવરણ પરિવર્તન સામે રક્ષણ, જમીન-જળ સંરક્ષણ અને બિનપિયત/સૂકા વિસ્તારોનો કૃષિ વિકાસ.",
                eligibility = "બિનપિયત અને સૂકા વિસ્તારના તમામ ખેડૂતો.",
                benefits = "ખેત તલાવડી નિર્માણ, વર્મી કમ્પોસ્ટ યુનિટ, લીલો પડવાશ અને બિયારણ પર સહાય.",
                amount = "બિનપિયત વિસ્તાર વિકાસ માટે ₹12,500/હેક્ટર સહાય",
                howToApply = "ગ્રામ સેવક અથવા તાલુકા ખેતીવાડી અધિકારી મારફત આઈ-ખેડૂત પર અરજી કરો."
            ),
            "rkvy" to SchemeTranslation(
                title = "રાષ્ટ્રીય કૃષિ વિકાસ યોજના (આરકેવીવાય-રફતાર)",
                description = "કૃષિ માળખાકીય સુવિધાઓ, આધુનિક ટેકનોલોજી અને એગ્રી-સ્ટાર્ટઅપ્સને આર્થિક અનુદાન.",
                eligibility = "ખેડૂતો, એફપીઓ અને કૃષિ ક્ષેત્રના નવઉદ્યોગ સાહસિકો.",
                benefits = "લણણી પછીની પ્રક્રિયા સુવિધાઓ અને નવા એગ્રી-સ્ટાર્ટઅપ્સને ₹25 લાખ સુધીનું અનુદાન.",
                amount = "સ્ટાર્ટઅપ અનુદાન ₹25 લાખ સુધી",
                howToApply = "rkvy.nic.in પોર્ટલ અથવા રાજ્ય કૃષિ નિયામકની કચેરી દ્વારા અરજી કરો."
            ),
            "agri_infra" to SchemeTranslation(
                title = "કૃષિ ઇન્ફ્રાસ્ટ્રક્ચર ફંડ (એઆઈએફ)",
                description = "ગોડાઉન, કોલ્ડ સ્ટોરેજ, સોર્ટિંગ અને પેક-હાઉસ જેવા પ્રોજેક્ટ્સ માટે રાહત દરે લોન સહાય.",
                eligibility = "ખેડૂતો, એફપીઓ, સેવા સહકારી મંડળીઓ (PACS) અને કૃષિ ઉદ્યોગસાહસિકો.",
                benefits = "₹2 કરોડ સુધીની લોન પર 7 વર્ષ માટે વાર્ષિક 3% વ્યાજ સહાય અને ક્રેડિટ ગેરંટી કવરેજ.",
                amount = "₹2 કરોડ સુધી લોન પર 3% વ્યાજ રાહત",
                howToApply = "agriinfra.dac.gov.in પોર્ટલ પર પ્રોજેક્ટ પ્રપોઝલ સબમિટ કરો."
            ),
            "smam" to SchemeTranslation(
                title = "કૃષિ યાંત્રિકીકરણ પેટા-મિશન (એસએમએએમ)",
                description = "ટ્રેક્ટર, પાવર ટીલર, રોટાવેટર, થ્રેશર અને આધુનિક સાધન-સામગ્રીની ખરીદી પર સબસીડી.",
                eligibility = "નાના, સીમાંત, મહિલા, એસસી/એસટી ખેડૂતો અને કસ્ટમ હાયરિંગ સેન્ટર સ્થાપતા યુવાનો.",
                benefits = "ખેતી ઓજારો અને મશીનરી પર 40% થી 50% સીધી સરકારી સબસીડી.",
                amount = "ઓજારો પર 40%-50% સબસીડી",
                howToApply = "આઈ-ખેડૂત (ikhedut.gujarat.gov.in) પોર્ટલ પર ઓનલાઇન અરજી કરો."
            ),
            "nbhm" to SchemeTranslation(
                title = "રાષ્ટ્રીય મધમાખી પાલન અને મધ મિશન (એનબીએચએમ)",
                description = "વધારાની આવક, પરાગનયન સુધારણા અને શુદ્ધ મધ ઉત્પાદન માટે વૈજ્ઞાનિક મધમાખી પાલન.",
                eligibility = "ખેડૂતો, મહિલા સ્વસહાય જૂથો અને મધમાખી પાલકો.",
                benefits = "મધમાખીની પેટીઓ, કોલોની અને મધ પ્રોસેસિંગ એકમો પર 80% સુધી સબસીડી.",
                amount = "મહિલા/એસસી માટે 80%, અન્ય માટે 50% સબસીડી",
                howToApply = "nbb.gov.in પોર્ટલ અથવા જિલ્લા બાગાયત અધિકારીનો સંપર્ક કરો."
            ),
            "midh" to SchemeTranslation(
                title = "સંકલિત બાગાયત વિકાસ મિશન (એમઆઈડીએચ)",
                description = "ફળો, શાકભાજી, મસાલા, ફૂલો, ગ્રીનહાઉસ, નેટહાઉસ અને કોલ્ડ સ્ટોરેજનો સર્વાંગી વિકાસ.",
                eligibility = "બાગાયતી ખેતી કરતા તમામ ખેડૂતો, મંડળીઓ અને એફપીઓ.",
                benefits = "નવા ફળબગીચા, ગ્રીનહાઉસ અને પેકિંગ હાઉસ માટે 40% થી 50% આર્થિક સહાય.",
                amount = "40% થી 50% મૂડી સબસીડી",
                howToApply = "આઈ-ખેડૂત પોર્ટલ પર બાગાયત વિભાગની યોજનાઓમાં અરજી કરો."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "પીએમ-આશા (અન્નદાતા આવક સંરક્ષણ અભિયાન)",
                description = "કઠોળ, તેલીબિયાં અને કોપરા માટે લઘુત્તમ ટેકાના ભાવ (MSP) ની કાનૂની ખાતરી.",
                eligibility = "નોંધાયેલા કઠોળ અને તેલીબિયાં પકવતા તમામ ખેડૂતો.",
                benefits = "બજાર ભાવ ટેકાના ભાવથી નીચા જાય ત્યારે સરકારી ખરીદી અથવા ભાવ તફાવતની સીધી ચુકવણી.",
                amount = "સંપૂર્ણ ટેકાના ભાવ (MSP) નો લાભ",
                howToApply = "લણણી પહેલા ઈ-સમૃદ્ધિ અથવા પુરવઠા નિગમના પોર્ટલ પર નોંધણી કરાવો."
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "મહાત્મા જ્યોતિરાવ ફૂલે ખેડૂત દેવામાફી યોજના",
                description = "મહારાષ્ટ્રના ખેડૂતો માટે ₹2 લાખ સુધીનું બાકી પાક ધિરાણ માફ કરવાની યોજના.",
                eligibility = "મહારાષ્ટ્રના ખેડૂતો જેમનું રાષ્ટ્રીયકૃત કે સહકારી બેંકમાં ₹2 લાખ સુધીનું પાક ધિરાણ બાકી છે.",
                benefits = "₹2 લાખ સુધીની સંપૂર્ણ લોન માફી અને નિયમિત ધિરાણ ભરતા ખેડૂતોને ₹50,000 પ્રોત્સાહન.",
                amount = "₹2 લાખ સુધી દેવામાફી + ₹50,000 પ્રોત્સાહન",
                howToApply = "MahaDBT પોર્ટલ અથવા મામલતદાર કચેરીમાં આધાર સાથે અરજી કરો."
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "નાનાજી દેશમુખ કૃષિ સંજીવની યોજના (પોકરા)",
                description = "મહારાષ્ટ્રના દુષ્કાળગ્રસ્ત અને ક્ષારવાળા વિસ્તારોમાં ક્લાઈમેટ-રેઝિલિયન્ટ ખેતી પ્રોજેક્ટ.",
                eligibility = "મરાઠવાડા અને વિદર્ભના 15 દુષ્કાળગ્રસ્ત જિલ્લાઓના નાના અને સીમાંત ખેડૂતો.",
                benefits = "ખેત તલાવડી, ટપક પદ્ધતિ, શેડનેટ અને જમીન સુધારણા પર 75% સુધી સબસીડી.",
                amount = "જળ સંરક્ષણ અને ટપક પર 75% સુધી સબસીડી",
                howToApply = "mahapocra.gov.in પોર્ટલ પર નોંધણી કરાવો."
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "પાણી બચાવો પૈસા કમાઓ યોજના",
                description = "ખેતીના ટ્યુબવેલ પર વીજળી અને ભૂગર્ભ જળ બચાવવા બદલ ખેડૂતોને રોકડ પ્રોત્સાહન.",
                eligibility = "પંજાબના મીટરવાળા કૃષિ વીજ જોડાણ ધરાવતા ખેડૂતો.",
                benefits = "વીજળી બચાવવા પર પ્રતિ યુનિટ ₹4 સીધા બેંક ખાતામાં જમા.",
                amount = "બચાવેલી વીજળી પર ₹4 પ્રતિ યુનિટ",
                howToApply = "પંજાબ સ્ટેટ પાવર કોર્પોરેશન (PSPCL) ની પેટા-કચેરીમાં નોંધણી કરાવો."
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "યુપી કિસાન ઉદય યોજના",
                description = "ઉત્તર પ્રદેશના ખેડૂતોને ઊર્જા-કાર્યક્ષમ સ્માર્ટ અને સોલાર પંપસેટનું મફત વિતરણ.",
                eligibility = "ખેતીની જમીન ધરાવતા યુપીના નાના અને સીમાંત ખેડૂતો.",
                benefits = "2 થી 5 એચપી સ્માર્ટ સોલાર પંપનું મફત ઇન્સ્ટોલેશન અને 5 વર્ષ મફત મેન્ટેનન્સ.",
                amount = "100% મફત સોલાર પંપ વિતરણ",
                howToApply = "upagriculture.com પોર્ટલ પર ઓનલાઇન અરજી કરો."
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "ભાવાંતર ચૂકવણી યોજના (ભાવ તફાવત સહાય)",
                description = "મંડીઓમાં પાકના ભાવ ટેકાના ભાવ કરતા ઘટે ત્યારે ભાવ તફાવતની રકમ સીધી ખેડૂતના ખાતામાં.",
                eligibility = "મધ્ય પ્રદેશ ઈ-ઉપાર્જન પોર્ટલ પર નોંધાયેલા ખેડૂતો.",
                benefits = "ટેકાના ભાવ અને બજાર વેચાણ કિંમત વચ્ચેનો તફાવત સીધો ખાતામાં જમા.",
                amount = "ક્વિન્ટલ દીઠ ભાવ તફાવત સહાય",
                howToApply = "mpeuparjan.nic.in પર લણણી પહેલા પાકની નોંધણી કરાવો."
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "રૈયતુ બંધુ યોજના (ખેડૂત રોકાણ સહાય)",
                description = "ખેતી ખર્ચ માટે વાર્ષિક પ્રતિ એકર ₹10,000 ની સીધી આર્થિક સહાય.",
                eligibility = "તેલંગાણાના તમામ પટ્ટાધારક જમીનમાલિક ખેડૂત પરિવારો.",
                benefits = "ખરીફ માટે ₹5,000 અને રવિ માટે ₹5,000 લેખે એકર દીઠ ₹10,000 સીધા બેંક ખાતામાં.",
                amount = "પ્રતિ એકર વાર્ષિક ₹10,000",
                howToApply = "ધરણી પોર્ટલ પર જમીનના રેકોર્ડ મુજબ સ્વચાલિત લાભ."
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "તમિલનાડુ મફત કૃષિ વીજળી યોજના",
                description = "તમિલનાડુમાં ખેતીના પંપસેટ્સ માટે 24 કલાક સંપૂર્ણપણે મફત વીજળી પુરવઠો.",
                eligibility = "તમિલનાડુના તમામ નોંધાયેલા ખેતી વીજ ગ્રાહકો.",
                benefits = "કોઈપણ બિલ કે મીટર ચાર્જ વગર સિંચાઈ માટે 100% મફત વીજળી.",
                amount = "100% મફત કૃષિ વીજળી",
                howToApply = "તમિલનાડુ ઇલેક્ટ્રિસિટી બોર્ડ (TANGEDCO) માં અરજી કરો."
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "રૈતા સિરી યોજના (મિલેટ્સ પ્રોત્સાહન)",
                description = "પૌષ્ટિક ધાન્ય (મિલેટ્સ) વાવવા બદલ પ્રોત્સાહન અને ₹3 લાખ સુધી શૂન્ય ટકા વ્યાજે પાક ધિરાણ.",
                eligibility = "કર્ણાટકના મિલેટ્સ પકવતા ખેડૂત પરિવારો.",
                benefits = "મિલેટ્સની ખેતી પર હેક્ટર દીઠ ₹10,000 પ્રોત્સાહન અને 0% વ્યાજે લોન.",
                amount = "₹10,000/હેક્ટર + 0% વ્યાજે લોન",
                howToApply = "કર્ણાટક રૈતા મિત્ર પોર્ટલ (raitamitra.karnataka.gov.in) પર અરજી કરો."
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "કિસાન સૂર્યોદય યોજના",
                description = "ગુજરાતના ખેડૂતોને દિવસે (સવારે 5 થી રાત્રે 9) ખેતી માટે વિશ્વસનીય વીજળી પુરવઠો.",
                eligibility = "ગુજરાતના ખેતી વીજ જોડાણ ધરાવતા તમામ ખેડૂતો.",
                benefits = "દિવસે સુરક્ષિત વીજળી મળવાથી રાત્રે ખેતરમાં પાણી વાળવાની પરેશાની અને જોખમોથી મુક્તિ.",
                amount = "મફત સરકારી ઇન્ફ્રાસ્ટ્રક્ચર યોજના",
                howToApply = "વીજ વિતરણ કંપનીઓ (DISCOMs) દ્વારા તબક્કાવાર સ્વચાલિત અમલ."
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "રાજસ્થાન કિસાન મિત્ર ઊર્જા યોજના",
                description = "ખેતી વીજ બિલ પર દર મહિને ₹1,000 સુધીની સીધી સરકારી સબસીડી.",
                eligibility = "રાજસ્થાનના મીટરવાળા કૃષિ વીજ જોડાણ ધારકો.",
                benefits = "વીજ બિલમાં દર મહિને ₹1,000 (વાર્ષિક ₹12,000 સુધી) ની સીધી છૂટ.",
                amount = "વાર્ષિક ₹12,000 સુધી વીજ સબસીડી",
                howToApply = "વીજ કંપનીઓ દ્વારા ચાલુ બિલમાં સીધો ઘટાડો."
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "બિહાર ડીઝલ અનુદાન યોજના",
                description = "દુષ્કાળ અને ઓછા વરસાદ વખતે સિંચાઈ માટે ડીઝલ ખરીદી પર સીધી સરકારી સબસીડી.",
                eligibility = "બિહારના ખાતેદાર અને ભાગિયા ખેડૂતો.",
                benefits = "ડીઝલ પર પ્રતિ લિટર ₹75 સબસીડી (એક સિંચાઈ માટે એકર દીઠ ₹750, મહત્તમ 3 સિંચાઈ).",
                amount = "₹75/લિટર (મહત્તમ ₹2,250/એકર)",
                howToApply = "ડીબીટી એગ્રિકલ્ચર બિહાર (dbtagriculture.bihar.gov.in) પર ઓનલાઇન અરજી કરો."
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "કૃષક બંધુ યોજના",
                description = "પશ્ચિમ બંગાળના ખેડૂતોને વાર્ષિક ₹10,000 ની નાણાકીય સહાય અને ₹2 લાખનું જીવન વીમા રક્ષણ.",
                eligibility = "પશ્ચિમ બંગાળના તમામ ખેડૂતો અને નોંધાયેલા ભાગિયાઓ (ઉંમર 18 થી 60 વર્ષ).",
                benefits = "વાર્ષિક ₹10,000 (બે હપ્તામાં) અને ખેડૂતના અવસાન પર વારસદારને ₹2 લાખની સહાય.",
                amount = "વાર્ષિક ₹10,000 + ₹2 લાખ વીમો",
                howToApply = "બીડીઓ (BDO) કચેરી અથવા krishakbandhu.net પોર્ટલ પર અરજી કરો."
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "વાયએસઆર રૈયતુ ભરોસા - પીએમ કિસાન",
                description = "આંધ્ર પ્રદેશમાં ભાગિયા ખેડૂતો સહિત તમામ ખેડૂત પરિવારોને વાર્ષિક ₹13,500 ની સહાય.",
                eligibility = "આંધ્રના જમીનમાલિક અને પછાત વર્ગના ભાગિયા ખેડૂતો.",
                benefits = "બિયારણ, ખાતર માટે 3 હપ્તામાં કુલ ₹13,500 બેંક ખાતામાં જમા.",
                amount = "વાર્ષિક ₹13,500",
                howToApply = "રૈયતુ ભરોસા કેન્દ્ર (RBK) અથવા ગ્રામ સચિવાલયમાં નોંધણી કરાવો."
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "હરિયાણા ભાવાંતર ભરપાઈ યોજના",
                description = "શાકભાજી અને બાગાયતી પાકોના ભાવ ગગડી જાય ત્યારે ખેડૂતોને નુકસાન ભરપાઈ આપતી યોજના.",
                eligibility = "મેરી ફસલ મેરા બ્યોરા પોર્ટલ પર નોંધાયેલા હરિયાણાના બાગાયત ખેડૂતો.",
                benefits = "સંરક્ષિત ભાવ અને વાસ્તવિક બજાર ભાવ વચ્ચેનો તફાવત સીધો બેંક ખાતામાં.",
                amount = "ક્વિન્ટલ દીઠ ભાવ તફાવત સહાય",
                howToApply = "વાવણી પહેલા fasal.haryana.gov.in પર નોંધણી કરાવો."
            ),
            "od_kalia" to SchemeTranslation(
                title = "કાલિયા યોજના (ખેડૂત આજીવિકા અને આવક વૃદ્ધિ)",
                description = "ઓડિશાના નાના, સીમાંત અને ભૂમિહીન ખેડૂત પરિવારો માટે વ્યાપક આર્થિક સહાય.",
                eligibility = "ઓડિશાના નાના/સીમાંત ખેડૂતો અને જમીન વિહોણા ખેત મજૂરો.",
                benefits = "ખેતી માટે ₹10,000/વર્ષ, ભૂમિહીનોને ₹12,500 અને ₹2 લાખનું વીમા કવચ.",
                amount = "વાર્ષિક ₹10,000 + આજીવિકા સહાય",
                howToApply = "kalia.odisha.gov.in પોર્ટલ અથવા ગ્રામ પંચાયતમાં અરજી કરો."
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "કેરળ રાજ્ય વ્યાપક પાક વીમા યોજના",
                description = "પૂર, ભૂસ્ખલન અને જંગલી પ્રાણીઓના ઉપદ્રવ સામે 25+ મુખ્ય પાકોને રાજ્ય સ્તરીય વીમા રક્ષણ.",
                eligibility = "કેરળમાં ડાંગર, કેળા, મસાલા, શાકભાજી અને રબર પકવતા ખેડૂતો.",
                benefits = "કુદરતી આફત અને વન્યજીવ નુકસાન સામે તાત્કાલિક વળતર.",
                amount = "પાક મુજબ એકર દીઠ ₹35,000 સુધી વળતર",
                howToApply = "AIMS પોર્ટલ (aims.kerala.gov.in) અથવા સ્થાનિક કૃષિ ભવનમાં અરજી કરો."
            ),
        ),
        "pa" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "ਪੀਐਮ-ਕਿਸਾਨ (ਪ੍ਰਧਾਨ ਮੰਤਰੀ ਕਿਸਾਨ ਸੰਮਾਨ ਨਿਧੀ)",
                description = "ਯੋਗ ਕਿਸਾਨ ਪਰਿਵਾਰਾਂ ਨੂੰ ਹਰ ਸਾਲ ₹6,000 ਦੀ ਸਿੱਧੀ ਵਿੱਤੀ ਸਹਾਇਤਾ, ₹2,000 ਦੀਆਂ 3 ਬਰਾਬਰ ਕਿਸ਼ਤਾਂ ਵਿੱਚ ਸਿੱਧਾ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ ਦਿੱਤੀ ਜਾਂਦੀ ਹੈ।",
                eligibility = "ਖੇਤੀਯੋਗ ਜ਼ਮੀਨ ਵਾਲੇ ਸਾਰੇ ਕਿਸਾਨ ਪਰਿਵਾਰ। ਸੰਸਥਾਗਤ ਜ਼ਮੀਨ ਮਾਲਕ ਅਤੇ ਇਨਕਮ ਟੈਕਸ ਦਾਤਾ ਬਾਹਰ ਹਨ।",
                benefits = "ਸਾਲ ਵਿੱਚ 3 ਕਿਸ਼ਤਾਂ ਵਿੱਚ ਕੁੱਲ ₹6,000 ਸਿੱਧਾ ਬੈਂਕ ਟ੍ਰਾਂਸਫਰ।",
                amount = "₹6,000 ਪ੍ਰਤੀ ਸਾਲ",
                howToApply = "ਨੇੜਲੇ ਸੀਐਸਸੀ (CSC) ਕੇਂਦਰ ਜਾਂ pmkisan.gov.in ਪੋਰਟਲ 'ਤੇ ਫ਼ਰਦ ਅਤੇ ਆਧਾਰ ਕਾਰਡ ਨਾਲ ਰਜਿਸਟਰ ਕਰੋ।"
            ),
            "pmfby" to SchemeTranslation(
                title = "ਪੀਐਮਐਫਬੀਵਾਈ (ਪ੍ਰਧਾਨ ਮੰਤਰੀ ਫ਼ਸਲ ਬੀਮਾ ਯੋਜਨਾ)",
                description = "ਕੁਦਰਤੀ ਆਫ਼ਤਾਂ, ਕੀੜਿਆਂ ਅਤੇ ਬਿਮਾਰੀਆਂ ਕਾਰਨ ਫ਼ਸਲ ਦੇ ਨੁਕਸਾਨ 'ਤੇ ਅਨਾਜ, ਤੇਲ ਬੀਜ ਅਤੇ ਬਾਗਬਾਨੀ ਫ਼ਸਲਾਂ ਲਈ ਵਿਆਪਕ ਬੀਮਾ ਸੁਰੱਖਿਆ।",
                eligibility = "ਨੋਟੀਫਾਈਡ ਫ਼ਸਲਾਂ ਬੀਜਣ ਵਾਲੇ ਸਾਰੇ ਕਿਸਾਨ, ਹਿੱਸੇਦਾਰ ਅਤੇ ਮੁਜ਼ਾਰੇ (ਕਰਜ਼ਦਾਰ ਅਤੇ ਗ਼ੈਰ-ਕਰਜ਼ਦਾਰ ਕਿਸਾਨ)।",
                benefits = "ਪੂਰਾ ਬੀਮਾ ਰਕਮ ਕਲੇਮ। ਕਿਸਾਨ ਪ੍ਰੀਮੀਅਮ: ਸਾਉਣੀ 2%, ਹਾੜ੍ਹੀ 1.5%, ਬਾਗਬਾਨੀ 5%। ਬਾਕੀ ਪ੍ਰੀਮੀਅਮ ਸਰਕਾਰ ਦਿੰਦੀ ਹੈ।",
                amount = "ਫ਼ਸਲ ਅਤੇ ਰਕਬੇ ਦੇ ਆਧਾਰ 'ਤੇ ਬੀਮਾ ਰਕਮ",
                howToApply = "ਨੇੜਲੀ ਬੈਂਕ ਸ਼ਾਖਾ, ਸੀਐਸਸੀ ਕੇਂਦਰ ਜਾਂ pmfby.gov.in ਪੋਰਟਲ 'ਤੇ ਮਿਥੀ ਤਰੀਕ ਤੋਂ ਪਹਿਲਾਂ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "kcc" to SchemeTranslation(
                title = "ਕਿਸਾਨ ਕ੍ਰੈਡਿਟ ਕਾਰਡ (ਕੇਸੀਸੀ - ਕਿਸਾਨ ਕਰਜ਼ਾ ਕਾਰਡ)",
                description = "ਫ਼ਸਲ ਉਤਪਾਦਨ, ਖੇਤੀ ਖ਼ਰਚਿਆਂ ਅਤੇ ਵਾਢੀ ਤੋਂ ਬਾਅਦ ਦੀਆਂ ਲੋੜਾਂ ਲਈ ਬਹੁਤ ਘੱਟ ਵਿਆਜ ਦਰ 'ਤੇ ਥੋੜ੍ਹੇ ਸਮੇਂ ਦਾ ਕਰਜ਼ਾ।",
                eligibility = "ਨਿੱਜੀ/ਸਾਂਝੇ ਕਿਸਾਨ, ਮੁਜ਼ਾਰੇ ਅਤੇ ਸਵੈ-ਸਹਾਇਤਾ ਸਮੂਹਾਂ ਦੇ ਕਿਸਾਨ ਮੈਂਬਰ।",
                benefits = "4% ਰਿਆਇਤੀ ਵਿਆਜ 'ਤੇ ਕਰਜ਼ਾ (ਸਮੇਂ ਸਿਰ ਮੋੜਨ 'ਤੇ 3% ਛੋਟ)। ਏਟੀਐਮ ਕਾਰਡ ਅਤੇ ਬੀਮਾ ਸਹੂਲਤ।",
                amount = "4% ਵਿਆਜ 'ਤੇ ₹3 ਲੱਖ ਤੱਕ ਦਾ ਕਰਜ਼ਾ",
                howToApply = "ਜ਼ਮੀਨ ਦੀ ਫ਼ਰਦ, ਆਧਾਰ ਕਾਰਡ ਅਤੇ ਫ਼ੋਟੋਆਂ ਨਾਲ ਕਿਸੇ ਵੀ ਵਪਾਰਕ, ਸਹਿਕਾਰੀ ਜਾਂ ਪੇਂਡੂ ਬੈਂਕ ਵਿੱਚ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "soil_health" to SchemeTranslation(
                title = "ਮਿੱਟੀ ਸਿਹਤ ਕਾਰਡ ਸਕੀਮ (ਸੋਇਲ ਹੈਲਥ ਕਾਰਡ)",
                description = "ਜ਼ਮੀਨ ਦੀ ਮਿੱਟੀ ਦੀ ਪਰਖ ਕਰਕੇ ਫ਼ਸਲਾਂ ਅਨੁਸਾਰ ਖ਼ੁਰਾਕੀ ਤੱਤਾਂ ਅਤੇ ਖਾਦਾਂ ਦੀ ਸੰਤੁਲਿਤ ਵਰਤੋਂ ਲਈ ਵਿਗਿਆਨਕ ਸਿਫ਼ਾਰਸ਼ਾਂ।",
                eligibility = "ਖੇਤੀਯੋਗ ਜ਼ਮੀਨ ਵਾਲੇ ਸਾਰੇ ਕਿਸਾਨ।",
                benefits = "ਮੁਫ਼ਤ ਮਿੱਟੀ ਪਰਖ, 12 ਮੁੱਖ ਪੋਸ਼ਕ ਤੱਤਾਂ ਦੀ ਰਿਪੋਰਟ ਅਤੇ 2 ਸਾਲਾਂ ਲਈ ਖਾਦ ਪ੍ਰਬੰਧਨ ਦੀ ਸਲਾਹ।",
                amount = "ਬਿਲਕੁਲ ਮੁਫ਼ਤ",
                howToApply = "ਨੇੜਲੀ ਮਿੱਟੀ ਪਰਖ ਲੈਬਾਰਟਰੀ, ਕ੍ਰਿਸ਼ੀ ਵਿਗਿਆਨ ਕੇਂਦਰ (KVK) ਜਾਂ ਖੇਤੀਬਾੜੀ ਦਫ਼ਤਰ ਨਾਲ ਸੰਪਰਕ ਕਰੋ।"
            ),
            "pmksy" to SchemeTranslation(
                title = "ਪੀਐਮ ਕ੍ਰਿਸ਼ੀ ਸਿੰਚਾਈ ਯੋਜਨਾ (ਹਰ ਖੇਤ ਨੂੰ ਪਾਣੀ)",
                description = "ਹਰ ਖੇਤ ਤੱਕ ਸਿੰਚਾਈ ਦਾ ਪਾਣੀ ਪਹੁੰਚਾਉਣਾ ਅਤੇ ਤੁਪਕਾ/ਫੁਹਾਰਾ ਸਿੰਚਾਈ ਰਾਹੀਂ ਪਾਣੀ ਦੀ ਬੱਚਤ ਕਰਨਾ।",
                eligibility = "ਸਾਰੇ ਕਿਸਾਨ। ਮੀਂਹ 'ਤੇ ਨਿਰਭਰ ਅਤੇ ਕੰਢੀ ਖੇਤਰਾਂ ਨੂੰ ਵਿਸ਼ੇਸ਼ ਤਰਜੀਹ।",
                benefits = "ਤੁਪਕਾ ਅਤੇ ਫੁਹਾਰਾ ਸਿੰਚਾਈ ਪ੍ਰਣਾਲੀ 'ਤੇ ਛੋਟੇ ਕਿਸਾਨਾਂ ਨੂੰ 55% ਅਤੇ ਦੂਜੇ ਕਿਸਾਨਾਂ ਨੂੰ 45% ਸਬਸਿਡੀ।",
                amount = "ਛੋਟੇ ਕਿਸਾਨਾਂ ਨੂੰ 55%, ਦੂਜਿਆਂ ਨੂੰ 45% ਸਬਸਿਡੀ",
                howToApply = "ਜ਼ਿਲ੍ਹਾ ਖੇਤੀਬਾੜੀ ਜਾਂ ਬਾਗਬਾਨੀ ਵਿਭਾਗ ਦੇ ਦਫ਼ਤਰ ਵਿੱਚ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "pkvy" to SchemeTranslation(
                title = "ਪਰੰਪਰਾਗਤ ਕ੍ਰਿਸ਼ੀ ਵਿਕਾਸ ਯੋਜਨਾ (ਪੀਕੇਵੀਵਾਈ)",
                description = "ਕਲੱਸਟਰ ਵਿਧੀ ਰਾਹੀਂ ਕੁਦਰਤੀ/ਜੈਵਿਕ ਖੇਤੀ ਨੂੰ ਹੱਲਾਸ਼ੇਰੀ ਦੇਣਾ ਅਤੇ ਪੀਜੀਐਸ ਜੈਵਿਕ ਸਰਟੀਫਿਕੇਸ਼ਨ ਵਿੱਚ ਮਦਦ।",
                eligibility = "ਜੈਵਿਕ ਖੇਤੀ ਲਈ 50 ਏਕੜ ਦਾ ਕਲੱਸਟਰ ਬਣਾਉਣ ਵਾਲੇ 50 ਜਾਂ ਵੱਧ ਕਿਸਾਨਾਂ ਦਾ ਸਮੂਹ।",
                benefits = "ਜੈਵਿਕ ਖਾਦਾਂ, ਬੀਜ ਅਤੇ ਮੰਡੀਕਰਨ ਲਈ 3 ਸਾਲਾਂ ਵਿੱਚ ਪ੍ਰਤੀ ਹੈਕਟੇਅਰ ₹50,000 ਦੀ ਵਿੱਤੀ ਸਹਾਇਤਾ।",
                amount = "₹50,000 ਪ੍ਰਤੀ ਹੈਕਟੇਅਰ (3 ਸਾਲਾਂ ਵਿੱਚ)",
                howToApply = "50 ਕਿਸਾਨਾਂ ਦਾ ਸਮੂਹ ਬਣਾ ਕੇ ਮੁੱਖ ਖੇਤੀਬਾੜੀ ਅਫ਼ਸਰ ਦੇ ਦਫ਼ਤਰ ਦਰਖ਼ਾਸਤ ਦਿਓ।"
            ),
            "enam" to SchemeTranslation(
                title = "ਈ-ਨਾਮ (ਕੌਮੀ ਖੇਤੀਬਾੜੀ ਮੰਡੀ)",
                description = "ਖੇਤੀ ਜਿਣਸਾਂ ਦੀ ਆਨਲਾਈਨ ਵਿਕਰੀ ਦਾ ਸਾਂਝਾ ਮੰਚ, ਜੋ ਦੇਸ਼ ਭਰ ਦੀਆਂ ਅਨਾਜ ਮੰਡੀਆਂ ਨੂੰ ਜੋੜਦਾ ਹੈ।",
                eligibility = "ਮੰਡੀਆਂ ਵਿੱਚ ਰਜਿਸਟਰਡ ਸਾਰੇ ਕਿਸਾਨ, ਵਪਾਰੀ ਅਤੇ ਆੜ੍ਹਤੀਏ।",
                benefits = "ਪਾਰਦਰਸ਼ੀ ਬੋਲੀ, ਵਿਚੋਲਿਆਂ ਤੋਂ ਛੁਟਕਾਰਾ, ਫ਼ਸਲ ਦਾ ਵਧੀਆ ਭਾਅ ਅਤੇ ਸਿੱਧਾ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ ਭੁਗਤਾਨ।",
                amount = "ਕਿਸਾਨਾਂ ਲਈ ਬਿਲਕੁਲ ਮੁਫ਼ਤ",
                howToApply = "enam.gov.in ਪੋਰਟਲ 'ਤੇ ਰਜਿਸਟਰ ਕਰੋ ਜਾਂ ਨੇੜਲੀ ਈ-ਨਾਮ ਮੰਡੀ ਵਿੱਚ ਆਧਾਰ ਕਾਰਡ ਨਾਲ ਜਾਓ।"
            ),
            "pm_kusum" to SchemeTranslation(
                title = "ਪੀਐਮ-ਕੁਸੁਮ (ਸੋਲਰ ਪੰਪ ਸਕੀਮ)",
                description = "ਖੇਤਾਂ ਵਿੱਚ ਸੋਲਰ ਪੰਪ ਲਗਾਉਣ ਅਤੇ ਬਿਜਲੀ ਵਾਲੇ ਟਿਊਬਵੈੱਲਾਂ ਨੂੰ ਸੌਰ ਊਰਜਾ ਨਾਲ ਚਲਾਉਣ ਦੀ ਸਕੀਮ।",
                eligibility = "ਸਾਰੇ ਨਿੱਜੀ ਕਿਸਾਨ, ਕਿਸਾਨ ਸਮੂਹ, ਐਫਪੀਓ ਅਤੇ ਗ੍ਰਾਮ ਪੰਚਾਇਤਾਂ।",
                benefits = "ਸੋਲਰ ਪੰਪ ਲਗਾਉਣ 'ਤੇ 60% ਤੋਂ 80% ਤੱਕ ਸਰਕਾਰੀ ਸਬਸਿਡੀ (ਕੇਂਦਰ + ਪੰਜਾਬ ਸਰਕਾਰ)।",
                amount = "ਕੁੱਲ 60% ਤੋਂ 80% ਸਰਕਾਰੀ ਸਬਸਿਡੀ",
                howToApply = "ਪੇਡਾ (PEDA) ਦੀ ਵੈੱਬਸਾਈਟ (peda.gov.in) ਰਾਹੀਂ ਆਨਲਾਈਨ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "nmsa" to SchemeTranslation(
                title = "ਕੌਮੀ ਟਿਕਾਊ ਖੇਤੀਬਾੜੀ ਮਿਸ਼ਨ (ਐਨਐਮਐਸਏ)",
                description = "ਮੌਸਮੀ ਤਬਦੀਲੀ ਦਾ ਟਾਕਰਾ ਕਰਨ ਵਾਲੀਆਂ ਖੇਤੀ ਤਕਨੀਕਾਂ, ਮਿੱਟੀ ਦੀ ਸਾਂਭ-ਸੰਭਾਲ ਅਤੇ ਬਰਸਾਤੀ ਖੇਤਰਾਂ ਦਾ ਵਿਕਾਸ।",
                eligibility = "ਬਰਸਾਤੀ ਅਤੇ ਖੁਸ਼ਕ ਇਲਾਕਿਆਂ ਦੇ ਸਾਰੇ ਕਿਸਾਨ।",
                benefits = "ਖੇਤ ਤਲਾਅ, ਗੰਡੋਆ ਖਾਦ ਯੂਨਿਟ, ਹਰੀ ਖਾਦ ਅਤੇ ਸੁਧਰੇ ਬੀਜਾਂ 'ਤੇ ਵਿੱਤੀ ਸਹਾਇਤਾ।",
                amount = "ਬਰਸਾਤੀ ਇਲਾਕਾ ਵਿਕਾਸ ਲਈ ₹12,500/ਹੈਕਟੇਅਰ ਸਹਾਇਤਾ",
                howToApply = "ਬਲਾਕ ਜਾਂ ਜ਼ਿਲ੍ਹਾ ਖੇਤੀਬਾੜੀ ਅਫ਼ਸਰ ਰਾਹੀਂ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "rkvy" to SchemeTranslation(
                title = "ਰਾਸ਼ਟਰੀ ਕ੍ਰਿਸ਼ੀ ਵਿਕਾਸ ਯੋਜਨਾ (ਆਰਕੇਵੀਵਾਈ-ਰਫ਼ਤਾਰ)",
                description = "ਖੇਤੀਬਾੜੀ ਬੁਨਿਆਦੀ ਢਾਂਚੇ, ਨਵੀਆਂ ਤਕਨੀਕਾਂ ਅਤੇ ਐਗਰੀ-ਸਟਾਰਟਅੱਪਸ ਲਈ ਆਰਥਿਕ ਮਦਦ।",
                eligibility = "ਕਿਸਾਨ, ਕਿਸਾਨ ਉਤਪਾਦਕ ਸੰਗਠਨ (FPO) ਅਤੇ ਨਵੇਂ ਖੇਤੀ ਉੱਦਮੀ।",
                benefits = "ਫ਼ਸਲ ਸੰਭਾਲ ਕੇਂਦਰ, ਪ੍ਰੋਸੈਸਿੰਗ ਯੂਨਿਟ ਅਤੇ ਐਗਰੀ-ਸਟਾਰਟਅੱਪਸ ਲਈ ₹25 ਲੱਖ ਤੱਕ ਦੀ ਗ੍ਰਾਂਟ।",
                amount = "ਸਟਾਰਟਅੱਪ ਗ੍ਰਾਂਟ ₹25 ਲੱਖ ਤੱਕ",
                howToApply = "rkvy.nic.in ਪੋਰਟਲ ਜਾਂ ਪੰਜਾਬ ਖੇਤੀਬਾੜੀ ਵਿਭਾਗ ਰਾਹੀਂ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "agri_infra" to SchemeTranslation(
                title = "ਖੇਤੀਬਾੜੀ ਬੁਨਿਆਦੀ ਢਾਂਚਾ ਫੰਡ (ਏਆਈਐਫ)",
                description = "ਗੋਦਾਮ, ਕੋਲਡ ਸਟੋਰੇਜ, ਸਿਲੋਜ਼ ਅਤੇ ਪ੍ਰੋਸੈਸਿੰਗ ਯੂਨਿਟ ਬਣਾਉਣ ਲਈ ਰਿਆਇਤੀ ਵਿਆਜ 'ਤੇ ਕਰਜ਼ਾ।",
                eligibility = "ਕਿਸਾਨ, ਐਫਪੀਓ, ਸਹਿਕਾਰੀ ਸਭਾਵਾਂ (PACS) ਅਤੇ ਖੇਤੀ ਉੱਦਮੀ।",
                benefits = "₹2 ਕਰੋੜ ਤੱਕ ਦੇ ਕਰਜ਼ੇ 'ਤੇ 7 ਸਾਲਾਂ ਲਈ 3% ਸਾਲਾਨਾ ਵਿਆਜ ਛੋਟ ਅਤੇ ਕਰੈਡਿਟ ਗਾਰੰਟੀ।",
                amount = "₹2 ਕਰੋੜ ਤੱਕ ਕਰਜ਼ੇ 'ਤੇ 3% ਵਿਆਜ ਛੋਟ",
                howToApply = "agriinfra.dac.gov.in ਪੋਰਟਲ 'ਤੇ ਪ੍ਰੋਜੈਕਟ ਰਿਪੋਰਟ ਜਮ੍ਹਾਂ ਕਰੋ।"
            ),
            "smam" to SchemeTranslation(
                title = "ਖੇਤੀ ਮਸ਼ੀਨੀਕਰਨ ਸਬ-ਮਿਸ਼ਨ (ਐਸਐਮਏਐਮ)",
                description = "ਟਰੈਕਟਰ, ਸੁਪਰ ਸੀਡਰ, ਹੈਪੀ ਸੀਡਰ, ਬੇਲਰ ਅਤੇ ਆਧੁਨਿਕ ਖੇਤੀ ਮਸ਼ੀਨਰੀ 'ਤੇ ਵੱਡੀ ਸਬਸਿਡੀ।",
                eligibility = "ਛੋਟੇ, ਸੀਮਾਂਤ, ਮਹਿਲਾ, ਐਸਸੀ ਕਿਸਾਨ ਅਤੇ ਕਸਟਮ ਹਾਇਰਿੰਗ ਸੈਂਟਰ ਬਣਾਉਣ ਵਾਲੇ ਪੇਂਡੂ ਨੌਜਵਾਨ।",
                benefits = "ਖੇਤੀ ਮਸ਼ੀਨਰੀ ਅਤੇ ਪਰਾਲੀ ਪ੍ਰਬੰਧਨ ਸੰਦਾਂ 'ਤੇ 40% ਤੋਂ 50% ਸਿੱਧੀ ਸਬਸਿਡੀ।",
                amount = "ਸੰਦਾਂ 'ਤੇ 40%-50% ਸਬਸਿਡੀ",
                howToApply = "agrimachinery.nic.in ਜਾਂ ਪੰਜਾਬ ਖੇਤੀਬਾੜੀ ਪੋਰਟਲ 'ਤੇ ਆਨਲਾਈਨ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "nbhm" to SchemeTranslation(
                title = "ਕੌਮੀ ਸ਼ਹਿਦ ਮੱਖੀ ਪਾਲਣ ਮਿਸ਼ਨ (ਐਨਬੀਐਚਐਮ)",
                description = "ਵਾਧੂ ਆਮਦਨ, ਪਰਾਗਣ ਸੁਧਾਰ ਅਤੇ ਮਿਆਰੀ ਸ਼ਹਿਦ ਉਤਪਾਦਨ ਲਈ ਵਿਗਿਆਨਕ ਮੱਖੀ ਪਾਲਣ।",
                eligibility = "ਕਿਸਾਨ, ਮਹਿਲਾ ਸਵੈ-ਸਹਾਇਤਾ ਸਮੂਹ ਅਤੇ ਮੱਖੀ ਪਾਲਕ।",
                benefits = "ਮੱਖੀ ਦੇ ਬਕਸੇ, ਕਲੋਨੀਆਂ ਅਤੇ ਸ਼ਹਿਦ ਕੱਢਣ ਵਾਲੀਆਂ ਮਸ਼ੀਨਾਂ 'ਤੇ 80% ਤੱਕ ਸਬਸਿਡੀ।",
                amount = "ਮਹਿਲਾ/ਐਸਸੀ ਲਈ 80%, ਹੋਰਾਂ ਲਈ 50% ਸਬਸਿਡੀ",
                howToApply = "nbb.gov.in ਪੋਰਟਲ ਜਾਂ ਜ਼ਿਲ੍ਹਾ ਬਾਗਬਾਨੀ ਦਫ਼ਤਰ ਨਾਲ ਸੰਪਰਕ ਕਰੋ।"
            ),
            "midh" to SchemeTranslation(
                title = "ਸੰਗਠਿਤ ਬਾਗਬਾਨੀ ਵਿਕਾਸ ਮਿਸ਼ਨ (ਐਮਆਈਡੀਐਚ)",
                description = "ਫਲ, ਸਬਜ਼ੀਆਂ, ਮਸਾਲੇ, ਫੁੱਲ, ਪੌਲੀਹਾਊਸ ਅਤੇ ਕੋਲਡ ਸਟੋਰੇਜ ਦੇ ਸਰਬਪੱਖੀ ਵਿਕਾਸ ਲਈ ਸਹਾਇਤਾ।",
                eligibility = "ਬਾਗਬਾਨੀ ਦੀ ਖੇਤੀ ਕਰਨ ਵਾਲੇ ਸਾਰੇ ਕਿਸਾਨ ਅਤੇ ਐਫਪੀਓ।",
                benefits = "ਨਵੇਂ ਬਾਗ ਲਗਾਉਣ, ਸ਼ੈੱਡਨੈੱਟ ਪੌਲੀਹਾਊਸ ਅਤੇ ਪੈਕ-ਹਾਊਸ ਬਣਾਉਣ ਲਈ 40-50% ਵਿੱਤੀ ਸਹਾਇਤਾ।",
                amount = "40% ਤੋਂ 50% ਪੂੰਜੀਗਤ ਸਬਸਿਡੀ",
                howToApply = "ਜ਼ਿਲ੍ਹਾ ਬਾਗਬਾਨੀ ਅਫ਼ਸਰ (Deputy Director Horticulture) ਨਾਲ ਸੰਪਰਕ ਕਰੋ।"
            ),
            "pm_aasha" to SchemeTranslation(
                title = "ਪੀਐਮ-ਆਸ਼ਾ (ਅੰਨਦਾਤਾ ਆਮਦਨ ਸੁਰੱਖਿਆ ਅਭਿਆਨ)",
                description = "ਦਾਲਾਂ, ਤੇਲ ਬੀਜਾਂ ਅਤੇ ਖੋਪਰੇ ਲਈ ਘੱਟੋ-ਘੱਟ ਸਮਰਥਨ ਮੁੱਲ (MSP) ਦੀ ਪੱਕੀ ਗਾਰੰਟੀ।",
                eligibility = "ਅਧਿਸੂਚਿਤ ਤੇਲ ਬੀਜ ਅਤੇ ਦਾਲਾਂ ਬੀਜਣ ਵਾਲੇ ਸਾਰੇ ਰਜਿਸਟਰਡ ਕਿਸਾਨ।",
                benefits = "ਮੰਡੀ ਭਾਅ ਐਮਐਸਪੀ ਤੋਂ ਘੱਟ ਹੋਣ 'ਤੇ ਸਰਕਾਰੀ ਖ਼ਰੀਦ ਜਾਂ ਭਾਅ ਦੇ ਫ਼ਰਕ ਦਾ ਸਿੱਧਾ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ ਭੁਗਤਾਨ।",
                amount = "ਪੂਰਾ ਘੱਟੋ-ਘੱਟ ਸਮਰਥਨ ਮੁੱਲ (MSP) ਲਾਭ",
                howToApply = "ਫ਼ਸਲ ਵੱਢਣ ਤੋਂ ਪਹਿਲਾਂ ਅਨਾਜ ਖ਼ਰੀਦ ਪੋਰਟਲ 'ਤੇ ਰਜਿਸਟ੍ਰੇਸ਼ਨ ਕਰਵਾਓ।"
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "ਮਹਾਤਮਾ ਜੋਤੀਰਾਓ ਫੂਲੇ ਕਿਸਾਨ ਕਰਜ਼ਾ ਮੁਆਫ਼ੀ ਸਕੀਮ",
                description = "ਮਹਾਰਾਸ਼ਟਰ ਦੇ ਕਿਸਾਨਾਂ ਦਾ ₹2 ਲੱਖ ਤੱਕ ਦਾ ਬਕਾਇਆ ਫ਼ਸਲੀ ਕਰਜ਼ਾ ਮੁਆਫ਼ ਕਰਨ ਦੀ ਸਕੀਮ।",
                eligibility = "ਮਹਾਰਾਸ਼ਟਰ ਦੇ ਉਹ ਕਿਸਾਨ ਜਿਨ੍ਹਾਂ ਦਾ ਬੈਂਕਾਂ ਵਿੱਚ ₹2 ਲੱਖ ਤੱਕ ਫ਼ਸਲੀ ਕਰਜ਼ਾ ਬਕਾਇਆ ਹੈ।",
                benefits = "₹2 ਲੱਖ ਤੱਕ ਪੂਰਾ ਕਰਜ਼ਾ ਮੁਆਫ਼ ਅਤੇ ਸਮੇਂ ਸਿਰ ਕਰਜ਼ਾ ਮੋੜਨ ਵਾਲਿਆਂ ਨੂੰ ₹50,000 ਇਨਾਮੀ ਸਹਾਇਤਾ।",
                amount = "₹2 ਲੱਖ ਤੱਕ ਕਰਜ਼ਾ ਮੁਆਫ਼ੀ + ₹50,000 ਇਨਾਮ",
                howToApply = "MahaDBT ਪੋਰਟਲ ਜਾਂ ਤਹਿਸੀਲਦਾਰ ਦਫ਼ਤਰ ਵਿੱਚ ਆਧਾਰ ਕਾਰਡ ਨਾਲ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "ਨਾਨਾਜੀ ਦੇਸ਼ਮੁਖ ਕ੍ਰਿਸ਼ੀ ਸੰਜੀਵਨੀ ਯੋਜਨਾ (ਪੋਕਰਾ)",
                description = "ਮਹਾਰਾਸ਼ਟਰ ਦੇ ਸੋਕਾ ਪ੍ਰਭਾਵਿਤ ਖੇਤਰਾਂ ਵਿੱਚ ਮੌਸਮ-ਅਨੁਕੂਲ ਖੇਤੀਬਾੜੀ ਵਿਕਾਸ ਪ੍ਰੋਜੈਕਟ।",
                eligibility = "ਮਰਾਠਵਾੜਾ ਅਤੇ ਵਿਦਰਭ ਦੇ 15 ਸੋਕਾ ਪ੍ਰਭਾਵਿਤ ਜ਼ਿਲ੍ਹਿਆਂ ਦੇ ਛੋਟੇ ਕਿਸਾਨ।",
                benefits = "ਖੇਤ ਤਲਾਅ, ਤੁਪਕਾ ਸਿੰਚਾਈ, ਸ਼ੈੱਡਨੈੱਟ ਅਤੇ ਜ਼ਮੀਨ ਸੁਧਾਰ 'ਤੇ 75% ਤੱਕ ਸਬਸਿਡੀ।",
                amount = "ਜਲ ਸੰਭਾਲ ਅਤੇ ਤੁਪਕਾ ਸਿੰਚਾਈ 'ਤੇ 75% ਸਬਸਿਡੀ",
                howToApply = "mahapocra.gov.in ਪੋਰਟਲ 'ਤੇ ਰਜਿਸਟਰ ਕਰੋ।"
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "ਪਾਣੀ ਬਚਾਓ ਪੈਸਾ ਕਮਾਓ ਸਕੀਮ (ਪੰਜਾਬ)",
                description = "ਖੇਤੀ ਟਿਊਬਵੈੱਲਾਂ 'ਤੇ ਬਿਜਲੀ ਅਤੇ ਧਰਤੀ ਹੇਠਲਾ ਪਾਣੀ ਬਚਾਉਣ ਬਦਲੇ ਕਿਸਾਨਾਂ ਨੂੰ ਸਿੱਧਾ ਨਕਦ ਇਨਾਮ।",
                eligibility = "ਪੰਜਾਬ ਦੇ ਉਹ ਕਿਸਾਨ ਜਿਨ੍ਹਾਂ ਕੋਲ ਮੀਟਰ ਵਾਲੇ ਖੇਤੀ ਟਿਊਬਵੈੱਲ ਕੁਨੈਕਸ਼ਨ ਹਨ।",
                benefits = "ਬਿਜਲੀ ਬਚਾਉਣ 'ਤੇ ₹4 ਪ੍ਰਤੀ ਯੂਨਿਟ ਦੇ ਹਿਸਾਬ ਨਾਲ ਸਿੱਧਾ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ ਜਮ੍ਹਾਂ।",
                amount = "ਬਚਾਈ ਗਈ ਬਿਜਲੀ 'ਤੇ ₹4 ਪ੍ਰਤੀ ਯੂਨਿਟ",
                howToApply = "ਪੰਜਾਬ ਸਟੇਟ ਪਾਵਰ ਕਾਰਪੋਰੇਸ਼ਨ (PSPCL) ਦੇ ਸਬ-ਡਵੀਜ਼ਨ ਦਫ਼ਤਰ ਵਿੱਚ ਨਾਮ ਦਰਜ ਕਰਵਾਓ।"
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "ਯੂਪੀ ਕਿਸਾਨ ਉਦੈ ਯੋਜਨਾ",
                description = "ਉੱਤਰ ਪ੍ਰਦੇਸ਼ ਦੇ ਕਿਸਾਨਾਂ ਨੂੰ ਊਰਜਾ-ਕੁਸ਼ਲ ਸਮਾਰਟ ਅਤੇ ਸੋਲਰ ਪੰਪ ਸੈੱਟਾਂ ਦੀ ਮੁਫ਼ਤ ਵੰਡ।",
                eligibility = "ਖੇਤੀਯੋਗ ਜ਼ਮੀਨ ਅਤੇ ਸਿੰਚਾਈ ਦੀ ਲੋੜ ਵਾਲੇ ਯੂਪੀ ਦੇ ਛੋਟੇ ਕਿਸਾਨ।",
                benefits = "2 ਤੋਂ 5 ਹਾਰਸਪਾਵਰ ਦੇ ਸਮਾਰਟ ਸੋਲਰ ਪੰਪ ਦੀ ਮੁਫ਼ਤ ਫਿਟਿੰਗ ਅਤੇ 5 ਸਾਲ ਮੁਫ਼ਤ ਮੁਰੰਮਤ।",
                amount = "100% ਮੁਫ਼ਤ ਸੋਲਰ ਪੰਪ ਵੰਡ",
                howToApply = "upagriculture.com ਪੋਰਟਲ 'ਤੇ ਆਨਲਾਈਨ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "ਭਾਵਾਂਤਰ ਭੁਗਤਾਨ ਯੋਜਨਾ (ਮੱਧ ਪ੍ਰਦੇਸ਼)",
                description = "ਮੰਡੀਆਂ ਵਿੱਚ ਫ਼ਸਲ ਦਾ ਭਾਅ ਐਮਐਸਪੀ ਨਾਲੋਂ ਡਿੱਗਣ 'ਤੇ ਭਾਅ ਦੇ ਫ਼ਰਕ ਦੀ ਸਿੱਧੀ ਭਰਪਾਈ।",
                eligibility = "ਮੱਧ ਪ੍ਰਦੇਸ਼ ਈ-ਉਪਾਰਜਨ ਪੋਰਟਲ 'ਤੇ ਰਜਿਸਟਰਡ ਕਿਸਾਨ।",
                benefits = "ਘੱਟੋ-ਘੱਟ ਸਮਰਥਨ ਮੁੱਲ ਅਤੇ ਮੰਡੀ ਵਿਕਰੀ ਮੁੱਲ ਦਾ ਫ਼ਰਕ ਸਿੱਧਾ ਖਾਤੇ ਵਿੱਚ ਜਮ੍ਹਾਂ।",
                amount = "ਪ੍ਰਤੀ ਕੁਇੰਟਲ ਭਾਵਾਂਤਰ ਰਕਮ",
                howToApply = "mpeuparjan.nic.in 'ਤੇ ਫ਼ਸਲ ਕੱਟਣ ਤੋਂ ਪਹਿਲਾਂ ਰਜਿਸਟ੍ਰੇਸ਼ਨ ਕਰਵਾਓ।"
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "ਰੈਥੂ ਬੰਧੂ ਸਕੀਮ (ਕਿਸਾਨ ਨਿਵੇਸ਼ ਸਹਾਇਤਾ)",
                description = "ਖੇਤੀ ਖ਼ਰਚਿਆਂ ਲਈ ਪ੍ਰਤੀ ਸਾਲ ਪ੍ਰਤੀ ਏਕੜ ₹10,000 ਦੀ ਸਿੱਧੀ ਵਿੱਤੀ ਮਦਦ।",
                eligibility = "ਤੇਲੰਗਾਨਾ ਦੇ ਸਾਰੇ ਪੱਟਾਧਾਰਕ ਜ਼ਮੀਨ ਮਾਲਕ ਕਿਸਾਨ ਪਰਿਵਾਰ।",
                benefits = "ਸਾਉਣੀ ਲਈ ₹5,000 ਅਤੇ ਹਾੜ੍ਹੀ ਲਈ ₹5,000 ਪ੍ਰਤੀ ਏਕੜ ਸਿੱਧਾ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ ਜਮ੍ਹਾਂ।",
                amount = "ਪ੍ਰਤੀ ਏਕੜ ਪ੍ਰਤੀ ਸਾਲ ₹10,000",
                howToApply = "ਧਰਣੀ ਪੋਰਟਲ ਜ਼ਮੀਨ ਰਿਕਾਰਡ ਦੇ ਆਧਾਰ 'ਤੇ ਆਟੋਮੈਟਿਕ ਲਾਭ।"
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "ਤਾਮਿਲਨਾਡੂ ਮੁਫ਼ਤ ਖੇਤੀ ਬਿਜਲੀ ਸਕੀਮ",
                description = "ਤਾਮਿਲਨਾਡੂ ਵਿੱਚ ਖੇਤੀ ਮੋਟਰਾਂ ਲਈ 24 ਘੰਟੇ ਬਿਲਕੁਲ ਮੁਫ਼ਤ ਬਿਜਲੀ ਸਪਲਾਈ।",
                eligibility = "ਤਾਮਿਲਨਾਡੂ ਦੇ ਸਾਰੇ ਰਜਿਸਟਰਡ ਖੇਤੀਬਾੜੀ ਬਿਜਲੀ ਖਪਤਕਾਰ।",
                benefits = "ਬਿਨਾਂ ਕਿਸੇ ਬਿੱਲ ਦੇ ਖੇਤੀ ਸਿੰਚਾਈ ਲਈ 100% ਮੁਫ਼ਤ ਬਿਜਲੀ।",
                amount = "100% ਮੁਫ਼ਤ ਖੇਤੀ ਬਿਜਲੀ",
                howToApply = "ਤਾਮਿਲਨਾਡੂ ਬਿਜਲੀ ਬੋਰਡ (TANGEDCO) ਵਿੱਚ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "ਰੈਥਾ ਸਿਰੀ ਸਕੀਮ (ਮਿਲਟਸ ਉਤਸ਼ਾਹਨ)",
                description = "ਮੋਟੇ ਅਨਾਜ (ਮਿਲਟਸ) ਦੀ ਕਾਸ਼ਤ ਲਈ ਵਿੱਤੀ ਮਦਦ ਅਤੇ ₹3 ਲੱਖ ਤੱਕ ਜ਼ੀਰੋ ਫ਼ੀਸਦੀ ਵਿਆਜ 'ਤੇ ਕਰਜ਼ਾ।",
                eligibility = "ਕਰਨਾਟਕ ਦੇ ਮਿਲਟਸ ਉਤਪਾਦਕ ਕਿਸਾਨ ਪਰਿਵਾਰ।",
                benefits = "ਮਿਲਟਸ ਬੀਜਣ 'ਤੇ ₹10,000 ਪ੍ਰਤੀ ਹੈਕਟੇਅਰ ਗ੍ਰਾਂਟ ਅਤੇ 0% ਵਿਆਜ 'ਤੇ ਫ਼ਸਲੀ ਕਰਜ਼ਾ।",
                amount = "₹10,000/ਹੈਕਟੇਅਰ + 0% ਵਿਆਜ ਕਰਜ਼ਾ",
                howToApply = "ਕਰਨਾਟਕ ਰੈਥਾ ਮਿੱਤਰਾ ਪੋਰਟਲ (raitamitra.karnataka.gov.in) 'ਤੇ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "ਕਿਸਾਨ ਸੂਰਯੋਦੈ ਯੋਜਨਾ",
                description = "ਗੁਜਰਾਤ ਦੇ ਕਿਸਾਨਾਂ ਨੂੰ ਦਿਨ ਵੇਲੇ (ਸਵੇਰੇ 5 ਤੋਂ ਰਾਤ 9 ਵਜੇ ਤੱਕ) ਸਿੰਚਾਈ ਲਈ ਭਰੋਸੇਯੋਗ ਬਿਜਲੀ ਸਪਲਾਈ।",
                eligibility = "ਗੁਜਰਾਤ ਦੇ ਖੇਤੀ ਬਿਜਲੀ ਕੁਨੈਕਸ਼ਨ ਵਾਲੇ ਸਾਰੇ ਕਿਸਾਨ।",
                benefits = "ਦਿਨ ਵੇਲੇ ਬਿਜਲੀ ਮਿਲਣ ਨਾਲ ਰਾਤ ਨੂੰ ਖੇਤਾਂ ਵਿੱਚ ਪਾਣੀ ਲਾਉਣ ਦੇ ਖ਼ਤਰੇ ਖ਼ਤਮ।",
                amount = "ਮੁਫ਼ਤ ਸਰਕਾਰੀ ਬੁਨਿਆਦੀ ਢਾਂਚਾ ਪ੍ਰੋਜੈਕਟ",
                howToApply = "ਬਿਜਲੀ ਵੰਡ ਕੰਪਨੀਆਂ (DISCOMs) ਰਾਹੀਂ ਆਟੋਮੈਟਿਕ ਸਹੂਲਤ।"
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "ਰਾਜਸਥਾਨ ਕਿਸਾਨ ਮਿੱਤਰ ਊਰਜਾ ਯੋਜਨਾ",
                description = "ਖੇਤੀ ਬਿਜਲੀ ਬਿੱਲ 'ਤੇ ਹਰ ਮਹੀਨੇ ₹1,000 ਤੱਕ ਦੀ ਸਿੱਧੀ ਸਰਕਾਰੀ ਸਬਸਿਡੀ।",
                eligibility = "ਰਾਜਸਥਾਨ ਦੇ ਮੀਟਰ ਵਾਲੇ ਖੇਤੀ ਬਿਜਲੀ ਕੁਨੈਕਸ਼ਨ ਧਾਰਕ।",
                benefits = "ਬਿਜਲੀ ਬਿੱਲ ਵਿੱਚ ਹਰ ਮਹੀਨੇ ₹1,000 (ਸਾਲਾਨਾ ₹12,000 ਤੱਕ) ਦੀ ਸਿੱਧੀ ਛੋਟ।",
                amount = "ਸਾਲਾਨਾ ₹12,000 ਤੱਕ ਬਿਜਲੀ ਸਬਸਿਡੀ",
                howToApply = "ਬਿਜਲੀ ਕੰਪਨੀਆਂ ਵੱਲੋਂ ਚੱਲਦੇ ਬਿੱਲਾਂ ਵਿੱਚ ਸਿੱਧੀ ਕਟੌਤੀ।"
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "ਬਿਹਾਰ ਡੀਜ਼ਲ ਸਬਸਿਡੀ ਸਕੀਮ (ਡੀਜ਼ਲ ਅਨੁਦਾਨ)",
                description = "ਸੋਕੇ ਅਤੇ ਘੱਟ ਮੀਂਹ ਦੌਰਾਨ ਸਿੰਚਾਈ ਲਈ ਡੀਜ਼ਲ ਖ਼ਰੀਦਣ 'ਤੇ ਸਰਕਾਰੀ ਸਬਸਿਡੀ।",
                eligibility = "ਬਿਹਾਰ ਦੇ ਜ਼ਮੀਨ ਮਾਲਕ ਅਤੇ ਹਿੱਸੇਦਾਰ (ਮੁਜ਼ਾਰੇ) ਕਿਸਾਨ।",
                benefits = "ਡੀਜ਼ਲ 'ਤੇ ₹75 ਪ੍ਰਤੀ ਲੀਟਰ ਸਬਸਿਡੀ (ਇੱਕ ਸਿੰਚਾਈ ਲਈ ₹750/ਏਕੜ, ਵੱਧ ਤੋਂ ਵੱਧ 3 ਵਾਰ)।",
                amount = "₹75/ਲੀਟਰ (ਵੱਧ ਤੋਂ ਵੱਧ ₹2,250/ਏਕੜ)",
                howToApply = "ਡੀਬੀਟੀ ਐਗਰੀਕਲਚਰ ਬਿਹਾਰ (dbtagriculture.bihar.gov.in) 'ਤੇ ਆਨਲਾਈਨ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "ਕ੍ਰਿਸ਼ਕ ਬੰਧੂ ਸਕੀਮ",
                description = "ਪੱਛਮੀ ਬੰਗਾਲ ਦੇ ਕਿਸਾਨਾਂ ਨੂੰ ਸਾਲਾਨਾ ₹10,000 ਦੀ ਵਿੱਤੀ ਸਹਾਇਤਾ ਅਤੇ ₹2 ਲੱਖ ਦਾ ਜੀਵਨ ਬੀਮਾ।",
                eligibility = "ਪੱਛਮੀ ਬੰਗਾਲ ਦੇ ਸਾਰੇ ਕਿਸਾਨ ਅਤੇ ਰਜਿਸਟਰਡ ਬਰਗਾਦਾਰ (ਉਮਰ 18 ਤੋਂ 60 ਸਾਲ)।",
                benefits = "ਸਾਲ ਵਿੱਚ ₹10,000 (ਦੋ ਕਿਸ਼ਤਾਂ ਵਿੱਚ) ਅਤੇ ਕਿਸਾਨ ਦੀ ਮੌਤ 'ਤੇ ਪਰਿਵਾਰ ਨੂੰ ₹2 ਲੱਖ ਦੀ ਸਹਾਇਤਾ।",
                amount = "ਸਾਲਾਨਾ ₹10,000 + ₹2 ਲੱਖ ਬੀਮਾ",
                howToApply = "ਬੀਡੀਓ (BDO) ਦਫ਼ਤਰ ਜਾਂ krishakbandhu.net ਪੋਰਟਲ 'ਤੇ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "ਵਾਈਐਸਆਰ ਰੈਥੂ ਭਰੋਸਾ - ਪੀਐਮ ਕਿਸਾਨ",
                description = "ਆਂਧਰਾ ਪ੍ਰਦੇਸ਼ ਵਿੱਚ ਮੁਜ਼ਾਰਾ ਕਿਸਾਨਾਂ ਸਮੇਤ ਸਾਰੇ ਕਿਸਾਨ ਪਰਿਵਾਰਾਂ ਨੂੰ ਸਾਲਾਨਾ ₹13,500 ਦੀ ਵਿੱਤੀ ਮਦਦ।",
                eligibility = "ਆਂਧਰਾ ਦੇ ਜ਼ਮੀਨ ਮਾਲਕ ਅਤੇ ਪਛੜੀਆਂ ਸ਼੍ਰੇਣੀਆਂ ਦੇ ਮੁਜ਼ਾਰਾ ਕਿਸਾਨ।",
                benefits = "ਬੀਜ, ਖਾਦਾਂ ਲਈ ਸਾਲ ਵਿੱਚ 3 ਕਿਸ਼ਤਾਂ ਵਿੱਚ ਕੁੱਲ ₹13,500 ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ ਜਮ੍ਹਾਂ।",
                amount = "ਸਾਲਾਨਾ ₹13,500",
                howToApply = "ਰੈਥੂ ਭਰੋਸਾ ਕੇਂਦਰ (RBK) ਜਾਂ ਗ੍ਰਾਮ ਸਕੱਤਰੇਤ ਵਿੱਚ ਰਜਿਸਟਰ ਕਰੋ।"
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "ਹਰਿਆਣਾ ਭਾਵਾਂਤਰ ਭਰਪਾਈ ਯੋਜਨਾ",
                description = "ਸਬਜ਼ੀਆਂ ਅਤੇ ਬਾਗਬਾਨੀ ਫ਼ਸਲਾਂ ਦੇ ਮੰਡੀ ਭਾਅ ਡਿੱਗਣ 'ਤੇ ਕਿਸਾਨਾਂ ਨੂੰ ਨੁਕਸਾਨ ਦੀ ਭਰਪਾਈ ਦੇਣ ਵਾਲੀ ਸਕੀਮ।",
                eligibility = "ਮੇਰੀ ਫ਼ਸਲ ਮੇਰਾ ਬਿਓਰਾ ਪੋਰਟਲ 'ਤੇ ਰਜਿਸਟਰਡ ਹਰਿਆਣਾ ਦੇ ਬਾਗਬਾਨੀ ਕਿਸਾਨ।",
                benefits = "ਸੁਰੱਖਿਅਤ ਮੁੱਲ ਅਤੇ ਅਸਲ ਮੰਡੀ ਵਿਕਰੀ ਮੁੱਲ ਦਾ ਫ਼ਰਕ ਸਿੱਧਾ ਬੈਂਕ ਖਾਤੇ ਵਿੱਚ ਜਮ੍ਹਾਂ।",
                amount = "ਪ੍ਰਤੀ ਕੁਇੰਟਲ ਭਾਵਾਂਤਰ ਰਕਮ",
                howToApply = "ਫ਼ਸਲ ਲਗਾਉਣ ਤੋਂ ਪਹਿਲਾਂ fasal.haryana.gov.in 'ਤੇ ਰਜਿਸਟਰ ਕਰੋ।"
            ),
            "od_kalia" to SchemeTranslation(
                title = "ਕਾਲੀਆ ਸਕੀਮ (ਕਿਸਾਨ ਰੋਜ਼ੀ-ਰੋਟੀ ਅਤੇ ਆਮਦਨ ਵਾਧਾ)",
                description = "ਓਡੀਸ਼ਾ ਦੇ ਛੋਟੇ, ਸੀਮਾਂਤ ਅਤੇ ਬੇਜ਼ਮੀਨੇ ਕਿਸਾਨ ਪਰਿਵਾਰਾਂ ਲਈ ਵਿਆਪਕ ਆਰਥਿਕ ਸਹਾਇਤਾ।",
                eligibility = "ਓਡੀਸ਼ਾ ਦੇ ਛੋਟੇ ਕਿਸਾਨ ਅਤੇ ਬੇਜ਼ਮੀਨੇ ਖੇਤ ਮਜ਼ਦੂਰ ਪਰਿਵਾਰ।",
                benefits = "ਖੇਤੀ ਲਈ ₹10,000/ਸਾਲ, ਬੇਜ਼ਮੀਨਿਆਂ ਲਈ ₹12,500 ਅਤੇ ₹2 ਲੱਖ ਦਾ ਜੀਵਨ ਬੀਮਾ।",
                amount = "ਸਾਲਾਨਾ ₹10,000 + ਰੋਜ਼ੀ-ਰੋਟੀ ਸਹਾਇਤਾ",
                howToApply = "kalia.odisha.gov.in ਪੋਰਟਲ ਜਾਂ ਗ੍ਰਾਮ ਪੰਚਾਇਤ ਦਫ਼ਤਰ ਵਿੱਚ ਅਪਲਾਈ ਕਰੋ।"
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "ਕੇਰਲ ਰਾਜ ਵਿਆਪਕ ਫ਼ਸਲ ਬੀਮਾ ਸਕੀਮ",
                description = "ਹੜ੍ਹ, ਜ਼ਮੀਨ ਖਿਸਕਣ ਅਤੇ ਜੰਗਲੀ ਜਾਨਵਰਾਂ ਦੇ ਨੁਕਸਾਨ ਤੋਂ 25+ ਮੁੱਖ ਫ਼ਸਲਾਂ ਨੂੰ ਰਾਜ ਪੱਧਰੀ ਬੀਮਾ ਸੁਰੱਖਿਆ।",
                eligibility = "ਕੇਰਲ ਵਿੱਚ ਝੋਨਾ, ਕੇਲਾ, ਮਸਾਲੇ, ਸਬਜ਼ੀਆਂ ਅਤੇ ਰਬੜ ਬੀਜਣ ਵਾਲੇ ਸਾਰੇ ਕਿਸਾਨ।",
                benefits = "ਕੁਦਰਤੀ ਆਫ਼ਤ ਅਤੇ ਜੰਗਲੀ ਜਾਨਵਰਾਂ ਦੇ ਨੁਕਸਾਨ 'ਤੇ ਤੁਰੰਤ ਮੁਆਵਜ਼ਾ।",
                amount = "ਫ਼ਸਲ ਅਨੁਸਾਰ ਪ੍ਰਤੀ ਏਕੜ ₹35,000 ਤੱਕ ਮੁਆਵਜ਼ਾ",
                howToApply = "AIMS ਪੋਰਟਲ (aims.kerala.gov.in) ਜਾਂ ਸਥਾਨਕ ਕ੍ਰਿਸ਼ੀ ਭਵਨ ਵਿੱਚ ਅਪਲਾਈ ਕਰੋ।"
            ),
        ),
        "ml" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "പിഎം-കിസാൻ (പ്രധാനമന്ത്രി കിസാൻ സമ്മാൻ നിധി)",
                description = "അർഹരായ കർഷക കുടുംബങ്ങൾക്ക് പ്രതിവർഷം ₹6,000 സാമ്പത്തിക സഹായം, ₹2,000 വീതമുള്ള 3 തുല്യ ഗഡുക്കളായി നേരിട്ട് ബാങ്ക് അക്കൗണ്ടിലേക്ക് നൽകുന്നു.",
                eligibility = "കൃഷിഭൂമിയുള്ള എല്ലാ കർഷക കുടുംബങ്ങളും അർഹരാണ്. സ്ഥാപന ഉടമകളും ആദായനികുതി അടയ്ക്കുന്നവരും ഒഴികെ.",
                benefits = "പ്രതിവർഷം ₹6,000 മൂന്ന് ഗഡുക്കളായി നേരിട്ട് ബാങ്ക് അക്കൗണ്ടിലേക്ക്.",
                amount = "പ്രതിവർഷം ₹6,000",
                howToApply = "അക്ഷയ കേന്ദ്രം, സിഎസ്‌സി (CSC) അല്ലെങ്കിൽ pmkisan.gov.in പോർട്ടൽ വഴി ആധാർ, കരമടച്ച രസീത് എന്നിവ സഹിതം അപേക്ഷിക്കുക."
            ),
            "pmfby" to SchemeTranslation(
                title = "പിഎംഎഫ്ബിവൈ (പ്രധാനമന്ത്രി ഫസൽ ബീമാ യോജന)",
                description = "പ്രകൃതിക്ഷോഭം, കീടബാധ, രോഗങ്ങൾ എന്നിവ മൂലമുണ്ടാകുന്ന വിളനാശത്തിന് ഭക്ഷ്യ, എണ്ണക്കുരു, തോട്ടവിളകൾക്ക് സമഗ്ര വിള ഇൻഷുറൻസ് സംരക്ഷണം.",
                eligibility = "വിജ്ഞാപനം ചെയ്ത വിളകൾ കൃഷി ചെയ്യുന്ന ഭൂവുടമകളും പാട്ടക്കർഷകരും (വായ്പ എടുത്തവരും അല്ലാത്തവരും).",
                benefits = "പൂർണ്ണ ഇൻഷുറൻസ് തുക നഷ്ടപരിഹാരം. കർഷക പ്രീമിയം: ഖാരിഫ് 2%, റാബി 1.5%, തോട്ടവിളകൾ 5%. ബാക്കി പ്രീമിയം സർക്കാർ വഹിക്കുന്നു.",
                amount = "വിളയും വിസ്തൃതിയും അടിസ്ഥാനമാക്കിയുള്ള ഇൻഷുറൻസ് തുക",
                howToApply = "സമീപത്തെ ബാങ്ക് ശാഖ, അക്ഷയ കേന്ദ്രം അല്ലെങ്കിൽ pmfby.gov.in പോർട്ടൽ വഴി നിശ്ചിത തീയതിക്കുള്ളിൽ അപേക്ഷിക്കുക."
            ),
            "kcc" to SchemeTranslation(
                title = "കിസാൻ ക്രെഡിറ്റ് കാർഡ് (കെസിസി - കർഷക വായ്പാ കാർഡ്)",
                description = "വിള ഉൽപാദനം, കൃഷി ചെലവുകൾ, വിളവെടുപ്പാനന്തര ആവശ്യങ്ങൾ എന്നിവയ്ക്കായി വളരെ കുറഞ്ഞ പലിശ നിരക്കിൽ ഹ്രസ്വകാല വായ്പാ സൗകര്യം.",
                eligibility = "വ്യക്തിഗത കർഷകർ, പാട്ടക്കർഷകർ, സ്വയംസഹായ സംഘങ്ങളിലെ കർഷക അംഗങ്ങൾ.",
                benefits = "4% സബ്‌സിഡി പലിശ നിരക്കിൽ വായ്പ (കൃത്യസമയത്ത് തിരിച്ചടച്ചാൽ 3% പലിശ ഇളവ്). എടിഎം കാർഡ് സൗകര്യം.",
                amount = "4% പലിശയിൽ ₹3 ലക്ഷം വരെ വായ്പ",
                howToApply = "ഭൂമിയുടെ രേഖകൾ, ആധാർ, ഫോട്ടോ എന്നിവ സഹിതം ഏതെങ്കിലും വാണിജ്യ, സഹകരണ അല്ലെങ്കിൽ ഗ്രാമീണ ബാങ്കിൽ അപേക്ഷിക്കുക."
            ),
            "soil_health" to SchemeTranslation(
                title = "സോയിൽ ഹെൽത്ത് കാർഡ് പദ്ധതി (മണ്ണ് ആരോഗ്യ കാർഡ്)",
                description = "കൃഷിഭൂമിയിലെ മണ്ണ് പരിശോധിച്ച് വിളകൾക്കനുസൃതമായി പോഷകങ്ങളും വളങ്ങളും സന്തുലിതമായി ഉപയോഗിക്കുന്നതിനുള്ള ശാസ്ത്രീയ നിർദ്ദേശങ്ങൾ.",
                eligibility = "കൃഷിഭൂമിയുള്ള സംസ്ഥാനത്തെ എല്ലാ കർഷകരും.",
                benefits = "സൗജന്യ മണ്ണ് പരിശോധന, 12 പ്രധാന പോഷകങ്ങളുടെ വിശദമായ റിപ്പോർട്ട്, 2 വർഷത്തേക്കുള്ള വളപ്രയോഗ ശുപാർശകൾ.",
                amount = "പൂർണ്ണമായും സൗജന്യം",
                howToApply = "സമീപത്തെ മണ്ണ് പരിശോധനാ ലബോറട്ടറി, കൃഷി വിജ്ഞാന കേന്ദ്രം (KVK) അല്ലെങ്കിൽ കൃഷിഭവൻ വഴി ബന്ധപ്പെടുക."
            ),
            "pmksy" to SchemeTranslation(
                title = "പ്രധാനമന്ത്രി കൃഷി സിഞ്ചായി യോജന (സൂക്ഷ്മ ജലസേചനം)",
                description = "എല്ലാ കൃഷിയിടങ്ങളിലും ജലസേചന സൗകര്യം ലഭ്യമാക്കലും തുള്ളിനന/സ്പ്രിങ്ക്ലർ വഴി ജലവിനിയോഗ കാര്യക്ഷമത വർദ്ധിപ്പിക്കലും.",
                eligibility = "എല്ലാ കർഷകരും. മഴയെ ആശ്രയിക്കുന്ന വരൾച്ചാ ബാധിത മേഖലകൾക്ക് മുൻഗണന.",
                benefits = "ചെറുകിട/നാമമാത്ര കർഷകർക്ക് തുള്ളിനനയ്ക്ക് 55% മുതൽ 70% വരെയും മറ്റ് കർഷകർക്ക് 45% സബ്‌സിഡിയും.",
                amount = "ചെറുകിട കർഷകർക്ക് 55% മുതൽ 70% വരെ സബ്സിഡി",
                howToApply = "കൃഷിഭവൻ അല്ലെങ്കിൽ ജില്ലാ കൃഷി ഓഫീസർ വഴി അപേക്ഷ സമർപ്പിക്കുക."
            ),
            "pkvy" to SchemeTranslation(
                title = "പരമ്പരാഗത് കൃഷി വികാസ് യോജന (പികെവിവൈ)",
                description = "ക്ലസ്റ്റർ സമീപനത്തിലൂടെ ജൈവകൃഷി പ്രോത്സാഹിപ്പിക്കലും കർഷകർക്ക് പിജിഎസ് ജൈവ സർട്ടിഫിക്കേഷൻ സഹായവും.",
                eligibility = "ജൈവകൃഷിക്കായി 50 ഏക്കർ വിസ്തൃതിയിൽ കൂട്ടായ്മ രൂപീകരിക്കുന്ന 50 അല്ലെങ്കിൽ അതിൽ കൂടുതൽ കർഷകരുടെ സംഘം.",
                benefits = "ജൈവ വളങ്ങൾ, ഉൽപാദനം, വിപണനം എന്നിവയ്ക്കായി 3 വർഷത്തിനുള്ളിൽ ഹെക്ടറിന് ₹50,000 സാമ്പത്തിക സഹായം.",
                amount = "ഹെക്ടറിന് ₹50,000 (3 വർഷത്തേക്ക്)",
                howToApply = "50 കർഷകരുടെ സംഘം രൂപീകരിച്ച് കൃഷിഭവൻ മുഖേന പ്രോജക്ട് സമർപ്പിക്കുക."
            ),
            "enam" to SchemeTranslation(
                title = "ഇ-നാം (ദേശീയ കാർഷിക വിപണി)",
                description = "കാർഷിക ഉൽപ്പന്നങ്ങൾ ഓൺലൈനായി വിൽക്കുന്നതിനുള്ള ഏകീകൃത ദേശീയ ഇലക്ട്രോണിക് വിപണി.",
                eligibility = "മാർക്കറ്റ് കമ്മിറ്റികളിൽ രജിസ്റ്റർ ചെയ്ത എല്ലാ കർഷകരും വ്യാപാരികളും.",
                benefits = "സുതാര്യമായ ലേലം, ഇടനിലക്കാരില്ലാത്ത വിപണനം, മികച്ച വില, തുക നേരിട്ട് ബാങ്ക് അക്കൗണ്ടിലേക്ക്.",
                amount = "കർഷകർക്ക് പൂർണ്ണമായും സൗജന്യം",
                howToApply = "enam.gov.in പോർട്ടലിൽ രജിസ്റ്റർ ചെയ്യുക അല്ലെങ്കിൽ സമീപത്തെ ഇ-നാം മാർക്കറ്റ് സന്ദർശിക്കുക."
            ),
            "pm_kusum" to SchemeTranslation(
                title = "പിഎം-കുസും (സോളാർ അഗ്രികൾച്ചറൽ പമ്പ് സ്കീം)",
                description = "കൃഷിയിടങ്ങളിൽ സോളാർ പമ്പുകൾ സ്ഥാപിക്കലും ഇലക്ട്രിക് പമ്പുകളെ സൗരോർജ്ജത്തിലേക്ക് മാറ്റലും.",
                eligibility = "എല്ലാ കർഷകരും, കർഷക കൂട്ടായ്മകളും, എഫ്പിഒകളും, പഞ്ചായത്തുകളും.",
                benefits = "സോളാർ പമ്പുകൾക്ക് 60% വരെ സർക്കാർ സബ്‌സിഡി (30% കേന്ദ്രം + 30% സംസ്ഥാനം). കർഷകൻ തുച്ഛമായ തുക മാത്രം നൽകിയാൽ മതി.",
                amount = "ആകെ 60% സർക്കാർ സബ്സിഡി",
                howToApply = "അനെർട്ട് (ANERT) അല്ലെങ്കിൽ mnre.gov.in പോർട്ടൽ വഴി അപേക്ഷിക്കുക."
            ),
            "nmsa" to SchemeTranslation(
                title = "സുസ്ഥിര കൃഷിക്കായുള്ള ദേശീയ ദൗത്യം (എൻഎംഎസ്എ)",
                description = "കാലാവസ്ഥാ വ്യതിയാനത്തെ പ്രതിരോധിക്കുന്ന കൃഷിരീതികൾ, മണ്ണുസംരക്ഷണം, മഴാശ്രിത കൃഷി വികസനം.",
                eligibility = "മഴയെ ആശ്രയിച്ച് കൃഷി ചെയ്യുന്ന എല്ലാ ചെറുകിട കർഷകരും.",
                benefits = "ഫാം പോണ്ട് നിർമ്മാണം, മണ്ണിര കമ്പോസ്റ്റ്, പച്ചിലവളം, വിത്ത് സബ്‌സിഡി എന്നിവയ്ക്ക് സഹായം.",
                amount = "മഴാശ്രിത വികസനത്തിന് ₹12,500/ഹെക്ടർ സഹായം",
                howToApply = "കൃഷിഭവൻ മുഖേന അപേക്ഷ സമർപ്പിക്കുക."
            ),
            "rkvy" to SchemeTranslation(
                title = "രാഷ്ട്രീയ കൃഷി വികാസ് യോജന (ആർകെവിവൈ-റഫ്താർ)",
                description = "കാർഷിക അടിസ്ഥാന സൗകര്യ വികസനം, നൂതന സാങ്കേതികവിദ്യ, അഗ്രി-സ്റ്റാർട്ടപ്പുകൾക്ക് സാമ്പത്തിക സഹായം.",
                eligibility = "കർഷകർ, ഫാർമർ പ്രൊഡ്യൂസർ ഓർഗനൈസേഷനുകൾ (FPO), അഗ്രി-സ്റ്റാർട്ടപ്പുകൾ.",
                benefits = "വിളവെടുപ്പാനന്തര സംസ്കരണ യൂണിറ്റുകൾക്കും അഗ്രി-സ്റ്റാർട്ടപ്പുകൾക്കും ₹25 ലക്ഷം വരെ ഗ്രാന്റ്.",
                amount = "സ്റ്റാർട്ടപ്പ് ഗ്രാന്റ് ₹25 ലക്ഷം വരെ",
                howToApply = "rkvy.nic.in പോർട്ടൽ അല്ലെങ്കിൽ സംസ്ഥാന കൃഷി വകുപ്പ് വഴി അപേക്ഷിക്കുക."
            ),
            "agri_infra" to SchemeTranslation(
                title = "അഗ്രികൾച്ചർ ഇൻഫ്രാസ്ട്രക്ചർ ഫണ്ട് (എഐഎഫ്)",
                description = "സംഭരണശാലകൾ, കോൾഡ് സ്റ്റോറേജുകൾ, പ്രൈമറി പ്രോസസ്സിംഗ് സെന്ററുകൾ എന്നിവ നിർമ്മിക്കുന്നതിന് കുറഞ്ഞ പലിശയിൽ വായ്പ.",
                eligibility = "കർഷകർ, എഫ്പിഒകൾ, പ്രാഥമിക കാർഷിക സഹകരണ സംഘങ്ങൾ (PACS), സംരംഭകർ.",
                benefits = "₹2 കോടി വരെയുള്ള വായ്പകൾക്ക് 7 വർഷത്തേക്ക് പ്രതിവർഷം 3% പലിശ ഇളവും ക്രെഡിറ്റ് ഗ്യാരണ്ടിയും.",
                amount = "₹2 കോടി വരെയുള്ള വായ്പയ്ക്ക് 3% പലിശയിളവ്",
                howToApply = "agriinfra.dac.gov.in പോർട്ടലിൽ പ്രോജക്ട് റിപ്പോർട്ട് സമർപ്പിക്കുക."
            ),
            "smam" to SchemeTranslation(
                title = "കാർഷിക യന്ത്രവൽക്കരണ ഉപദൗത്യം (എസ്എംഎഎം)",
                description = "ട്രാക്ടറുകൾ, പവർ ടില്ലറുകൾ, കൊയ്ത്തുയന്ത്രങ്ങൾ, ആധുനിക കാർഷിക ഉപകരണങ്ങൾ എന്നിവ വാങ്ങുന്നതിന് സബ്‌സിഡി.",
                eligibility = "ചെറുകിട, നാമമാത്ര, വനിതാ, പട്ടികജാതി/പട്ടികവർഗ്ഗ കർഷകരും ഗ്രാമീണ യുവാക്കളും.",
                benefits = "കാർഷിക യന്ത്രങ്ങൾക്കും ഉപകരണങ്ങൾക്കും 40% മുതൽ 50% വരെ നേരിട്ടുള്ള സർക്കാർ സബ്‌സിഡി.",
                amount = "യന്ത്രങ്ങൾക്ക് 40%-50% സബ്സിഡി",
                howToApply = "agrimachinery.nic.in അല്ലെങ്കിൽ കൃഷിഭവൻ വഴി ആധാർ, ഭൂമി രേഖകൾ എന്നിവ സഹിതം അപേക്ഷിക്കുക."
            ),
            "nbhm" to SchemeTranslation(
                title = "ദേശീയ തേനീച്ച വളർത്തൽ ദൗത്യം (എൻബിഎച്ച്എം)",
                description = "അധിക വരുമാനം, പരാഗണം വർദ്ധിപ്പിക്കൽ, ഗുണനിലവാരമുള്ള തേൻ ഉത്പാദനം എന്നിവയ്ക്കായി ശാസ്ത്രീയ തേനീച്ച വളർത്തൽ.",
                eligibility = "കർഷകർ, വനിതാ സ്വയംസഹായ സംഘങ്ങൾ, തേനീച്ച കർഷകർ.",
                benefits = "തേനീച്ച പെട്ടികൾ, കോളനികൾ, തേൻ സംസ്കരണ ഉപകരണങ്ങൾ എന്നിവയ്ക്ക് 80% വരെ സബ്‌സിഡി.",
                amount = "വനിതകൾ/പട്ടികവിഭാഗങ്ങൾക്ക് 80%, മറ്റുള്ളവർക്ക് 50% സബ്സിഡി",
                howToApply = "nbb.gov.in പോർട്ടൽ അല്ലെങ്കിൽ ജില്ലാ ഹോർട്ടികൾച്ചർ ഓഫീസ് വഴി അപേക്ഷിക്കുക."
            ),
            "midh" to SchemeTranslation(
                title = "ഹോർട്ടികൾച്ചർ സംയോജിത വികസന ദൗത്യം (എംഐഡിഎച്ച്)",
                description = "പഴങ്ങൾ, പച്ചക്കറികൾ, സുഗന്ധവ്യഞ്ജനങ്ങൾ, പൂക്കൾ, പോളിഹൗസ്, കോൾഡ് സ്റ്റോറേജ് എന്നിവയുടെ സമഗ്ര വികസനം.",
                eligibility = "തോട്ടവിള കർഷകർ, സ്വയംസഹായ സംഘങ്ങൾ, എഫ്പിഒകൾ.",
                benefits = "പുതിയ തോട്ടങ്ങൾ നിർമ്മിക്കൽ, ഷേഡ്നെറ്റ് പോളിഹൗസ്, പായ്ക്ക്-ഹൗസ് എന്നിവയ്ക്ക് 40-50% സാമ്പത്തിക സഹായം.",
                amount = "40% മുതൽ 50% വരെ സബ്സിഡി",
                howToApply = "കൃഷിഭവൻ അല്ലെങ്കിൽ ജില്ലാ ഹോർട്ടികൾച്ചർ ഡെപ്യൂട്ടി ഡയറക്ടറുടെ ഓഫീസ് വഴി അപേക്ഷിക്കുക."
            ),
            "pm_aasha" to SchemeTranslation(
                title = "പിഎം-ആശ (കർഷക വരുമാന സംരക്ഷണ പദ്ധതി)",
                description = "പയറുവർഗ്ഗങ്ങൾ, എണ്ണക്കുരുക്കൾ, കൊപ്ര എന്നിവയ്ക്ക് താങ്ങുവില (MSP) ഉറപ്പാക്കുന്ന സമഗ്ര പദ്ധതി.",
                eligibility = "വിജ്ഞാപനം ചെയ്ത എണ്ണക്കുരുക്കളും പയറുവർഗ്ഗങ്ങളും കൃഷി ചെയ്യുന്ന രജിസ്റ്റർ ചെയ്ത കർഷകർ.",
                benefits = "വിപണി വില താങ്ങുവിലയേക്കാൾ കുറയുമ്പോൾ സർക്കാർ സംഭരണം അല്ലെങ്കിൽ വിലവ്യത്യാസം നേരിട്ട് ബാങ്കിൽ നൽകുന്നു.",
                amount = "പൂർണ്ണ താങ്ങുവില (MSP) ആനുകൂല്യം",
                howToApply = "വിളവെടുപ്പിന് മുമ്പ് സംസ്ഥാന സംഭരണ പോർട്ടലിൽ രജിസ്റ്റർ ചെയ്യുക."
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "മഹാത്മാ ജ്യോതിറാവു ഫൂലെ കർഷക കടാശ്വാസ പദ്ധതി",
                description = "മഹാരാഷ്ട്രയിലെ കർഷകരുടെ ₹2 ലക്ഷം വരെയുള്ള കാർഷിക വായ്പ പൂർണ്ണമായി എഴുതിത്തള്ളുന്ന പദ്ധതി.",
                eligibility = "ബാങ്കുകളിൽ ₹2 ലക്ഷം വരെ വിള വായ്പ കുടിശ്ശികയുള്ള മഹാരാഷ്ട്രയിലെ കർഷകർ.",
                benefits = "₹2 ലക്ഷം വരെയുള്ള വായ്പ എഴുതിത്തള്ളലും കൃത്യമായി തിരിച്ചടച്ചവർക്ക് ₹50,000 പ്രോത്സാഹന സഹായവും.",
                amount = "₹2 ലക്ഷം കടാശ്വാസം + ₹50,000 ഇൻസെന്റീവ്",
                howToApply = "MahaDBT പോർട്ടൽ അല്ലെങ്കിൽ തഹസിൽദാർ ഓഫീസ് വഴി അപേക്ഷിക്കുക."
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "നാനാജി ദേശ്മുഖ് കൃഷി സഞ്ജീവനി യോജന (PoCRA)",
                description = "മഹാരാഷ്ട്രയിലെ വരൾച്ചാ ബാധിത മേഖലകളിലെ കാലാവസ്ഥാ പ്രതിരോധ കൃഷി പദ്ധതി.",
                eligibility = "മറാത്ത്‌വാഡ, വിദർഭ മേഖലകളിലെ 15 വരൾച്ചാ ബാധിത ജില്ലകളിലെ ചെറുകിട കർഷകർ.",
                benefits = "ഫാം പോണ്ട്, തുള്ളിനന, ഷേഡ്നെറ്റ്, മണ്ണുപരിപാലനം എന്നിവയ്ക്ക് 75% വരെ സബ്‌സിഡി.",
                amount = "ജലസംരക്ഷണത്തിനും തുള്ളിനനയ്ക്കും 75% സബ്സിഡി",
                howToApply = "mahapocra.gov.in പോർട്ടൽ വഴി അപേക്ഷിക്കുക."
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "വെള്ളം സംരക്ഷിക്കൂ പണം നേടൂ പദ്ധതി",
                description = "കൃഷി പമ്പുകളിൽ വൈദ്യുതിയും ഭൂഗർഭജലവും ലാഭിക്കുന്ന കർഷകർക്ക് നേരിട്ട് പണം നൽകുന്ന പദ്ധതി.",
                eligibility = "മീറ്ററുള്ള കാർഷിക കണക്ഷനുകളുള്ള പഞ്ചാബിലെ കർഷകർ.",
                benefits = "വൈദ്യുതി ലാഭിച്ചാൽ യൂണിറ്റിന് ₹4 നിരക്കിൽ നേരിട്ട് ബാങ്ക് അക്കൗണ്ടിലേക്ക്.",
                amount = "ലാഭിക്കുന്ന വൈദ്യുതിക്ക് ₹4/യൂണിറ്റ്",
                howToApply = "പഞ്ചാബ് സ്റ്റേറ്റ് പവർ കോർപ്പറേഷൻ (PSPCL) വഴി രജിസ്റ്റർ ചെയ്യുക."
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "യുപി കിസാൻ ഉദയ് യോജന",
                description = "ഉത്തർപ്രദേശിലെ കർഷകർക്ക് ഊർജ്ജക്ഷമതയുള്ള സ്മാർട്ട് സോളാർ പമ്പുകൾ സൗജന്യമായി വിതരണം ചെയ്യുന്നു.",
                eligibility = "കൃഷിഭൂമിയും ജലസേചന ആവശ്യവുമുള്ള യുപിയിലെ ചെറുകിട കർഷകർ.",
                benefits = "2 മുതൽ 5 എച്ച്പി സ്മാർട്ട് സോളാർ പമ്പ് സൗജന്യമായി സ്ഥാപിക്കലും 5 വർഷത്തെ സൗജന്യ സർവീസും.",
                amount = "100% സൗജന്യ സോളാർ പമ്പ് വിതരണം",
                howToApply = "upagriculture.com പോർട്ടലിൽ ഓൺലൈനായി അപേക്ഷിക്കുക."
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "ഭവാന്തർ ഭുഗ്താൻ യോജന (വില വ്യത്യാസ നികത്തൽ പദ്ധതി)",
                description = "വിപണി വില താങ്ങുവിലയേക്കാൾ കുറയുമ്പോൾ ആ വ്യത്യാസ തുക നേരിട്ട് കർഷകന്റെ അക്കൗണ്ടിൽ നൽകുന്നു.",
                eligibility = "മധ്യപ്രദേശ് ഇ-ഉപാർജൻ പോർട്ടലിൽ രജിസ്റ്റർ ചെയ്ത കർഷകർ.",
                benefits = "താങ്ങുവിലയും വിപണി വിലയും തമ്മിലുള്ള വ്യത്യാസം നേരിട്ട് അക്കൗണ്ടിലേക്ക്.",
                amount = "ക്വിന്റലിന് വിലവ്യത്യാസ തുക",
                howToApply = "mpeuparjan.nic.in പോർട്ടലിൽ വിളവെടുപ്പിന് മുമ്പ് രജിസ്റ്റർ ചെയ്യുക."
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "റൈതു ബന്ധു പദ്ധതി (കർഷക നിക്ഷേപ സഹായം)",
                description = "കൃഷി ചെലവുകൾക്കായി പ്രതിവർഷം ഏക്കറിന് ₹10,000 നേരിട്ടുള്ള സാമ്പത്തിക സഹായം.",
                eligibility = "തെലങ്കാനയിലെ പട്ടയഭൂമിയുള്ള എല്ലാ കർഷക കുടുംബങ്ങളും.",
                benefits = "ഖാരിഫ് സീസണിൽ ₹5,000, റാബിക്ക് ₹5,000 എന്ന ക്രമത്തിൽ ഏക്കറിന് ₹10,000 ബാങ്കിലേക്ക്.",
                amount = "പ്രതിവർഷം ഏക്കറിന് ₹10,000",
                howToApply = "ധരണി പോർട്ടലിലെ ഭൂമി രേഖകൾ പ്രകാരം ഓട്ടോമാറ്റിക് രജിസ്ട്രേഷൻ."
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "തമിഴ്‌നാട് സൗജന്യ കാർഷിക വൈദ്യുതി പദ്ധതി",
                description = "തമിഴ്‌നാട്ടിൽ കാർഷിക പമ്പ് സെറ്റുകൾക്ക് 24 മണിക്കൂറും തടസ്സമില്ലാത്ത സൗജന്യ വൈദ്യുതി നൽകുന്നു.",
                eligibility = "തമിഴ്‌നാട്ടിലെ രജിസ്റ്റർ ചെയ്ത എല്ലാ കാർഷിക വൈദ്യുതി ഉപഭോക്താക്കളും.",
                benefits = "ബില്ലുകളോ മീറ്റർ ചാർജോ ഇല്ലാതെ ജലസേചനത്തിന് 100% സൗജന്യ വൈദ്യുതി.",
                amount = "100% സൗജന്യ കാർഷിക വൈദ്യുതി",
                howToApply = "തമിഴ്‌നാട് ഇലക്ട്രിസിറ്റി ബോർഡിൽ (TANGEDCO) അപേക്ഷിക്കുക."
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "റൈത സിരി പദ്ധതി (മില്ലറ്റ് പ്രോത്സാഹന ധനസഹായം)",
                description = "ചെറുധാന്യങ്ങൾ (മില്ലറ്റ്സ്) കൃഷി ചെയ്യുന്നതിന് ധനസഹായവും ₹3 ലക്ഷം വരെ പലിശരഹിത വായ്പയും.",
                eligibility = "കർണാടകയിലെ ചെറുധാന്യ കർഷക കുടുംബങ്ങൾ.",
                benefits = "മില്ലറ്റ് കൃഷിക്ക് ഹെക്ടറിന് ₹10,000 പ്രോത്സാഹന തുകയും 0% പലിശയിൽ വിള വായ്പയും.",
                amount = "₹10,000/ഹെക്ടർ + 0% പലിശ വായ്പ",
                howToApply = "കർണാടക റൈത മിത്ര പോർട്ടൽ (raitamitra.karnataka.gov.in) വഴി അപേക്ഷിക്കുക."
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "കിസാൻ സൂര്യോദയ യോജന",
                description = "ഗുജറാത്തിലെ കർഷകർക്ക് പകൽ സമയത്ത് (രാവിലെ 5 മുതൽ രാത്രി 9 വരെ) ജലസേചനത്തിനുള്ള വൈദ്യുതി ഉറപ്പാക്കുന്നു.",
                eligibility = "ഗുജറാത്തിൽ കാർഷിക വൈദ്യുതി കണക്ഷനുള്ള എല്ലാ കർഷകരും.",
                benefits = "പകൽ സമയത്ത് സുരക്ഷിതമായ വൈദ്യുതി ലഭിക്കുന്നതിനാൽ രാത്രിയിലെ അപകടങ്ങൾ ഒഴിവാകുന്നു.",
                amount = "സൗജന്യ സർക്കാർ പദ്ധതി",
                howToApply = "വൈദ്യുതി വിതരണ കമ്പനികൾ (DISCOMs) വഴി ലഭ്യമാക്കുന്നു."
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "രാജസ്ഥാൻ കിസാൻ മിത്ര ഊർജ്ജ യോജന",
                description = "കാർഷിക വൈദ്യുതി ബില്ലിൽ പ്രതിമാസം ₹1,000 വരെ നേരിട്ടുള്ള സർക്കാർ സബ്‌സിഡി.",
                eligibility = "മീറ്ററുള്ള കാർഷിക കണക്ഷനുകളുള്ള രാജസ്ഥാനിലെ കർഷകർ.",
                benefits = "വൈദ്യുതി ബില്ലിൽ പ്രതിമാസം ₹1,000 (പ്രതിവർഷം ₹12,000 വരെ) നേരിട്ടുള്ള ഇളവ്.",
                amount = "പ്രതിവർഷം ₹12,000 വരെ വൈദ്യുതി സബ്സിഡി",
                howToApply = "വൈദ്യുതി ബില്ലിൽ ഓട്ടോമാറ്റിക് ആയി കുറവുചെയ്യുന്നു."
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "ബിഹാർ ഡീസൽ സബ്‌സിഡി പദ്ധതി",
                description = "വരൾച്ചാ കാലത്ത് ജലസേചനത്തിനായി ഡീസൽ വാങ്ങുന്നതിന് സർക്കാർ നൽകുന്ന സബ്‌സിഡി.",
                eligibility = "ബിഹാറിലെ ഭൂവുടമകളും പാട്ടക്കർഷകരും.",
                benefits = "ഡീസലിന് ലിറ്ററിന് ₹75 സബ്‌സിഡി (ഒരു നനയ്ക്ക് ഏക്കറിന് ₹750, പരമാവധി 3 നനകൾക്ക്).",
                amount = "₹75/ലിറ്റർ (പരമാവധി ₹2,250/ഏക്കർ)",
                howToApply = "ഡിബിടി അഗ്രികൾച്ചർ ബിഹാർ (dbtagriculture.bihar.gov.in) വഴി അപേക്ഷിക്കുക."
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "കൃഷക് ബന്ധു പദ്ധതി",
                description = "പശ്ചിമ ബംഗാളിലെ കർഷകർക്ക് പ്രതിവർഷം ₹10,000 സാമ്പത്തിക സഹായവും ₹2 ലക്ഷം ജീവൻ രക്ഷാ ഇൻഷുറൻസും.",
                eligibility = "പശ്ചിമ ബംഗാളിലെ എല്ലാ കർഷകരും രജിസ്റ്റർ ചെയ്ത ബർഗാദാർമാരും (18 മുതൽ 60 വയസ്സ് വരെ).",
                benefits = "പ്രതിവർഷം ₹10,000 (രണ്ട് ഗഡുക്കളായി) കർഷകൻ മരണപ്പെട്ടാൽ കുടുംബത്തിന് ₹2 ലക്ഷം ധനസഹായം.",
                amount = "പ്രതിവർഷം ₹10,000 + ₹2 ലക്ഷം ഇൻഷുറൻസ്",
                howToApply = "ബിഡിഒ (BDO) ഓഫീസ് അല്ലെങ്കിൽ krishakbandhu.net പോർട്ടൽ വഴി അപേക്ഷിക്കുക."
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "വൈഎസ്ആർ റൈതു ഭരോസ - പിഎം കിസാൻ",
                description = "ആന്ധ്രാപ്രദേശിൽ പാട്ടക്കർഷകർ ഉൾപ്പെടെയുള്ള എല്ലാ കർഷക കുടുംബങ്ങൾക്കും പ്രതിവർഷം ₹13,500 നിക്ഷേപ സഹായം.",
                eligibility = "ആന്ധ്രയിലെ ഭൂവുടമകളും പിന്നോക്ക വിഭാഗങ്ങളിലെ പാട്ടക്കർഷകരും.",
                benefits = "വിത്ത്, വളം എന്നിവയ്ക്കായി 3 ഗഡുക്കളായി ആകെ ₹13,500 ബാങ്ക് അക്കൗണ്ടിലേക്ക്.",
                amount = "പ്രതിവർഷം ₹13,500",
                howToApply = "റൈതു ഭരോസ കേന്ദ്രം (RBK) അല്ലെങ്കിൽ വില്ലേജ് സെക്രട്ടേറിയറ്റ് വഴി രജിസ്റ്റർ ചെയ്യുക."
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "ഹരിയാന ഭവാന്തർ ഭർപായ് യോജന",
                description = "പച്ചക്കറികൾക്കും തോട്ടവിളകൾക്കും വിപണി വില ഇടിയുമ്പോൾ നഷ്ടപരിഹാരം നൽകുന്ന പദ്ധതി.",
                eligibility = "മേരി ഫസൽ മേരാ ബ്യോറ പോർട്ടലിൽ രജിസ്റ്റർ ചെയ്ത ഹരിയാനയിലെ ഹോർട്ടികൾച്ചർ കർഷകർ.",
                benefits = "സംരക്ഷിത തറവിലയും വിപണി വിൽപന വിലയും തമ്മിലുള്ള വ്യത്യാസം നേരിട്ട് അക്കൗണ്ടിലേക്ക്.",
                amount = "ക്വിന്റലിന് വിലവ്യത്യാസ തുക",
                howToApply = "വിള നടുന്നതിന് മുമ്പ് fasal.haryana.gov.in പോർട്ടലിൽ രജിസ്റ്റർ ചെയ്യുക."
            ),
            "od_kalia" to SchemeTranslation(
                title = "കാലിയ പദ്ധതി (കർഷക ഉപജീവനവും വരുമാന വർദ്ധനവും)",
                description = "ഒഡീഷയിലെ ചെറുകിട, നാമമാത്ര, ഭൂരഹിത കർഷക കുടുംബങ്ങൾക്കുള്ള സമഗ്ര സാമ്പത്തിക സഹായം.",
                eligibility = "ഒഡീഷയിലെ ചെറുകിട കർഷകരും ഭൂരഹിത കാർഷിക തൊഴിലാളി കുടുംബങ്ങളും.",
                benefits = "കൃഷിക്ക് ₹10,000/വർഷം, ഭൂരഹിതർക്ക് ₹12,500, ₹2 ലക്ഷത്തിന്റെ ഇൻഷുറൻസ് പരിരക്ഷ.",
                amount = "പ്രതിവർഷം ₹10,000 + ഉപജീവന ധനസഹായം",
                howToApply = "kalia.odisha.gov.in പോർട്ടൽ അല്ലെങ്കിൽ ഗ്രാമപഞ്ചായത്ത് വഴി അപേക്ഷിക്കുക."
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "കേരള സംസ്ഥാന സമഗ്ര വിള ഇൻഷുറൻസ് പദ്ധതി",
                description = "പ്രളയം, ഉരുൾപൊട്ടൽ, വന്യജീവി ആക്രമണം എന്നിവയിൽ നിന്ന് 25+ പ്രധാന വിളകൾക്ക് സംസ്ഥാന ഇൻഷുറൻസ് സംരക്ഷണം.",
                eligibility = "കേരളത്തിൽ നെല്ല്, വാഴ, സുഗന്ധവ്യഞ്ജനങ്ങൾ, പച്ചക്കറികൾ, റബ്ബർ എന്നിവ കൃഷി ചെയ്യുന്ന എല്ലാ കർഷകരും.",
                benefits = "പ്രകൃതിക്ഷോഭത്തിനും വന്യജീവി ആക്രമണത്തിനും പെട്ടെന്നുള്ള നഷ്ടപരിഹാരം.",
                amount = "വിളയനുസരിച്ച് ഏക്കറിന് ₹35,000 വരെ നഷ്ടപരിഹാരം",
                howToApply = "AIMS പോർട്ടൽ (aims.kerala.gov.in) അല്ലെങ്കിൽ പ്രാദേശിക കൃഷിഭവൻ വഴി അപേക്ഷിക്കുക."
            ),
        ),
        "or" to mapOf(
            "pm_kisan" to SchemeTranslation(
                title = "ପିଏମ୍-କିଷାନ (ପ୍ରଧାନମନ୍ତ୍ରୀ କିଷାନ ସମ୍ମାନ ନିଧି)",
                description = "ଯୋଗ୍ୟ କୃଷକ ପରିବାରଙ୍କୁ ବାର୍ଷିକ ₹୬,୦୦୦ ଆର୍ଥିକ ସହାୟତା, ₹୨,୦୦୦ ଲେଖାଏଁ ୩ଟି କିସ୍ତିରେ ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଖାତାକୁ ପ୍ରଦାନ କରାଯାଏ।",
                eligibility = "ଚାଷଜମି ଥିବା ସମସ୍ତ କୃଷକ ପରିବାର। ସାଂସ୍ଥାନିକ ଜମି ମାଲିକ ଓ ଆୟକରଦାତା ବାଦ୍।",
                benefits = "ବର୍ଷକୁ ୩ଟି କିସ୍ତିରେ ମୋଟ ₹୬,୦୦୦ ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଜମା।",
                amount = "ବାର୍ଷିକ ₹୬,୦୦୦",
                howToApply = "ନିକଟସ୍ଥ ଜନସେବା କେନ୍ଦ୍ର (CSC) କିମ୍ବା pmkisan.gov.in ରେ ଜମି ପଟ୍ଟା ଓ ଆଧାର ସହିତ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "pmfby" to SchemeTranslation(
                title = "ପିଏମ୍ଏଫ୍ବିୱାଇ (ପ୍ରଧାନମନ୍ତ୍ରୀ ଫସଲ ବୀମା ଯୋଜନା)",
                description = "ପ୍ରାକୃତିକ ବିପର୍ଯ୍ୟୟ, ରୋଗ ପୋକ ଆକ୍ରମଣରେ ଫସଲ ନଷ୍ଟ ହେଲେ ଖାଦ୍ୟଶସ୍ୟ, ତୈଳବୀଜ ଓ ଉଦ୍ୟାନ କୃଷି ଫସଲକୁ ସମ୍ପୂର୍ଣ୍ଣ ବୀମା ସୁରକ୍ଷା।",
                eligibility = "ଅଧିସୂଚିତ ଫସଲ ଚାଷ କରୁଥିବା ସମସ୍ତ ଚାଷୀ ଓ ଭାଗଚାଷୀ (ଋଣୀ ଏବଂ ଅଣଋଣୀ ଚାଷୀ)।",
                benefits = "ସମ୍ପୂର୍ଣ୍ଣ ବୀମା ରାଶି କ୍ଷତିପୂରଣ। ପ୍ରିମିୟମ: ଖରିଫ ୨%, ରବି ୧.୫%, ବ୍ୟବସାୟିକ ଫସଲ ୫%। ବାକି ପ୍ରିମିୟମ ସରକାର ଦିଅନ୍ତି।",
                amount = "ଫସଲ ଓ ଜମି ଅନୁଯାୟୀ ବୀମା ରାଶି",
                howToApply = "ନିକଟସ୍ଥ ବ୍ୟାଙ୍କ ଶାଖା, ସିଏସସି କେନ୍ଦ୍ର କିମ୍ବା pmfby.gov.in ରେ ସମୟସୀମା ମଧ୍ୟରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "kcc" to SchemeTranslation(
                title = "କିଷାନ କ୍ରେଡିଟ କାର୍ଡ (କେସିସି - କୃଷି ଋଣ କାର୍ଡ)",
                description = "ଫସଲ ଉତ୍ପାଦନ, ଚାଷ ଖର୍ଚ୍ଚ ଏବଂ ଅମଳ ପରବର୍ତ୍ତୀ ଆବଶ୍ୟକତା ପାଇଁ ରିହାତି ସୁଧ ହାରରେ ସ୍ୱଳ୍ପମିଆଦୀ ଋଣ ସୁବିଧା।",
                eligibility = "ବ୍ୟକ୍ତିଗତ/ଯୁଗ୍ମ ଚାଷୀ, ଭାଗଚାଷୀ ଏବଂ ସ୍ୱୟଂ ସହାୟକ ଗୋଷ୍ଠୀର ସଦସ୍ୟ।",
                benefits = "୪% କମ ସୁଧରେ ଋଣ (ନିୟମିତ ପରିଶୋଧ କଲେ ୩% ରିହାତି)। ଏଟିଏମ କାର୍ଡ ଏବଂ ବୀମା ସୁବିଧା।",
                amount = "୪% ସୁଧରେ ₹୩ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ ଋଣ",
                howToApply = "ଜମି ପଟ୍ଟା, ଆଧାର କାର୍ଡ ଏବଂ ଫଟୋ ସହିତ ଯେକୌଣସି ବାଣିଜ୍ୟିକ, ସମବାୟ କିମ୍ବା ଗ୍ରାମ୍ୟ ବ୍ୟାଙ୍କରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "soil_health" to SchemeTranslation(
                title = "ମୃତ୍ତିକା ସ୍ୱାସ୍ଥ୍ୟ କାର୍ଡ ଯୋଜନା (ସଏଲ ହେଲଥ କାର୍ଡ)",
                description = "ଜମିର ମାଟି ପରୀକ୍ଷା କରି ଫସଲ ଅନୁସାରେ ଆବଶ୍ୟକୀୟ ପୋଷକ ତତ୍ତ୍ୱ ଏବଂ ସାରର ସନ୍ତୁଳିତ ବ୍ୟବହାର ପାଇଁ ବୈଜ୍ଞାନିକ ପରାମର୍ଶ।",
                eligibility = "ଚାଷଜମି ଥିବା ରାଜ୍ୟର ସମସ୍ତ ଚାଷୀ।",
                benefits = "ମାଗଣା ମାଟି ପରୀକ୍ଷା, ୧୨ଟି ମୁଖ୍ୟ ପୋଷକ ତତ୍ତ୍ୱ ବିବରଣୀ ଏବଂ ୨ ବର୍ଷ ପାଇଁ ସାର ପରିଚାଳନା ପରାମର୍ଶ।",
                amount = "ସମ୍ପୂର୍ଣ୍ଣ ମାଗଣା",
                howToApply = "ନିକଟସ୍ଥ ମୃତ୍ତିକା ପରୀକ୍ଷାଗାର, କୃଷି ବିଜ୍ଞାନ କେନ୍ଦ୍ର (KVK) କିମ୍ବା soilhealth.dac.gov.in ରେ ଯୋଗାଯୋଗ କରନ୍ତୁ।"
            ),
            "pmksy" to SchemeTranslation(
                title = "ପ୍ରଧାନମନ୍ତ୍ରୀ କୃଷି ସିଞ୍ଚାଇ ଯୋଜନା (ବୁନ୍ଦା/ସ୍ପ୍ରିଙ୍କଲର ଜଳସେଚନ)",
                description = "ପ୍ରତ୍ୟେକ ଚାଷଜମିକୁ ଜଳସେଚନ ସୁନିଶ୍ଚିତ କରିବା ଏବଂ ବୁନ୍ଦା/ଫୁଆରା ସେଚନ ମାଧ୍ୟମରେ ଜଳର ଉପଯୁକ୍ତ ବ୍ୟବହାର ବୃଦ୍ଧି।",
                eligibility = "ସମସ୍ତ ଚାଷୀ। ବର୍ଷାନିର୍ଭର ଓ ଖରାପ୍ରବଣ ଅଞ୍ଚଳକୁ ପ୍ରାଥମିକତା।",
                benefits = "କ୍ଷୁଦ୍ର ଓ ନାମମାତ୍ର ଚାଷୀଙ୍କୁ ବୁନ୍ଦା/ସ୍ପ୍ରିଙ୍କଲର ସେଚନ ଉପକରଣ ଉପରେ ୫୫% ଏବଂ ଅନ୍ୟ ଚାଷୀଙ୍କୁ ୪୫% ସବସିଡି।",
                amount = "କ୍ଷୁଦ୍ର ଚାଷୀଙ୍କୁ ୫୫% ଓ ଅନ୍ୟମାନଙ୍କୁ ୪୫% ସବସିଡି",
                howToApply = "ଜିଲ୍ଲା କୃଷି ଅଧିକାରୀ କିମ୍ବା ଉଦ୍ୟାନ କୃଷି ସହକାରୀ ନିର୍ଦ୍ଦେଶକଙ୍କ କାର୍ଯ୍ୟାଳୟରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "pkvy" to SchemeTranslation(
                title = "ପରମ୍ପରାଗତ କୃଷି ବିକାଶ ଯୋଜନା (ପିକେଭିୱାଇ)",
                description = "କ୍ଲଷ୍ଟର ପଦ୍ଧତିରେ ଜୈବିକ ଚାଷକୁ ପ୍ରୋତ୍ସାହନ ଏବଂ ଚାଷୀଙ୍କୁ ପିଜିଏସ ଜୈବିକ ପ୍ରମାଣପତ୍ର ସହାୟତା।",
                eligibility = "ଜୈବିକ ଚାଷ ପାଇଁ ୫୦ ଏକର ଜମିରେ ଗୋଷ୍ଠୀ ଗଠନ କରୁଥିବା ୫୦ ବା ତଦୁର୍ଦ୍ଧ୍ୱ ଚାଷୀ।",
                benefits = "ଜୈବିକ ଖତ, ପ୍ରକ୍ରିୟାକରଣ ଓ ବିକ୍ରି ପାଇଁ ୩ ବର୍ଷରେ ହେକ୍ଟର ପିଛା ₹୫୦,୦୦୦ ଆର୍ଥିକ ଅନୁଦାନ।",
                amount = "ହେକ୍ଟର ପିଛା ₹୫୦,୦୦୦ (୩ ବର୍ଷରେ)",
                howToApply = "୫୦ ଜଣ ଚାଷୀଙ୍କ ଦଳ ଗଠନ କରି ବ୍ଲକ କୃଷି ଅଧିକାରୀଙ୍କୁ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "enam" to SchemeTranslation(
                title = "ଇ-ନାମ (ଜାତୀୟ କୃଷି ବଜାର)",
                description = "କୃଷିଜାତ ଦ୍ରବ୍ୟ ବିକ୍ରି ପାଇଁ ଅନଲାଇନ୍ ଇଲେକ୍ଟ୍ରୋନିକ୍ ବଜାର, ଯାହା ଦେଶର ସମସ୍ତ ମଣ୍ଡିକୁ ସଂଯୋଗ କରେ।",
                eligibility = "ନିୟନ୍ତ୍ରିତ ବଜାର କମିଟି (RMC) ରେ ପଞ୍ଜୀକୃତ ସମସ୍ତ ଚାଷୀ ଏବଂ ବ୍ୟବସାୟୀ।",
                benefits = "ସ୍ୱଚ୍ଛ ନିଲାମ, ମଧ୍ୟସ୍ଥିମୁକ୍ତ ବ୍ୟବସ୍ଥା, ଉଚିତ ମୂଲ୍ୟ ଏବଂ ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଖାତାରେ ଟଙ୍କା ଜମା।",
                amount = "ଚାଷୀଙ୍କ ପାଇଁ ସମ୍ପୂର୍ଣ୍ଣ ମାଗଣା",
                howToApply = "enam.gov.in ରେ ପଞ୍ଜୀକରଣ କରନ୍ତୁ କିମ୍ବା ନିକଟସ୍ଥ ଇ-ନାମ ମଣ୍ଡିକୁ ଯାଆନ୍ତୁ।"
            ),
            "pm_kusum" to SchemeTranslation(
                title = "ପିଏମ୍-କୁସୁମ (ସୌର ପମ୍ପ ଯୋଜନା)",
                description = "ଚାଷଜମିରେ ସୌରଚାଳିତ ପମ୍ପ ସେଟ୍ ସ୍ଥାପନ ଏବଂ ବିଦ୍ୟୁତ୍ ପମ୍ପକୁ ସୌର ଶକ୍ତିରେ ରୂପାନ୍ତର।",
                eligibility = "ସମସ୍ତ ଚାଷୀ, କୃଷକ ଉତ୍ପାଦକ ଗୋଷ୍ଠୀ (FPO) ଏବଂ ଗ୍ରାମ ପଞ୍ଚାୟତ।",
                benefits = "ସୋଲାର ପମ୍ପ ଉପରେ ୬୦% ସରକାରୀ ସବସିଡି (୩୦% କେନ୍ଦ୍ର + ୩୦% ରାଜ୍ୟ)। ଚାଷୀଙ୍କୁ ମାତ୍ର ୧୦% ରୁ ୪୦% ଦେବାକୁ ପଡ଼େ।",
                amount = "ମୋଟ ୬୦% ସରକାରୀ ସବସିଡି",
                howToApply = "ଓରେଡା (OREDA) କିମ୍ବା mnre.gov.in ପୋର୍ଟାଲ ମାଧ୍ୟମରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "nmsa" to SchemeTranslation(
                title = "ଜାତୀୟ ନିରନ୍ତର କୃଷି ମିଶନ (ଏନଏମଏସଏ)",
                description = "ଜଳବାୟୁ ପରିବର୍ତ୍ତନ ସହନଶୀଳ କୃଷି ପଦ୍ଧତି, ମୃତ୍ତିକା ସଂରକ୍ଷଣ ଏବଂ ବର୍ଷାଜଳ ଭିତ୍ତିକ ଅଞ୍ଚଳର ବିକାଶ।",
                eligibility = "ବର୍ଷାନିର୍ଭର ଚାଷ କରୁଥିବା ସମସ୍ତ ଚାଷୀ।",
                benefits = "କୃଷି ପୋଖରୀ ଖନନ, ଜିଆ ଖତ ୟୁନିଟ୍, ସବୁଜ ସାର ଏବଂ ଉନ୍ନତ ବିହନ ଉପରେ ରିହାତି।",
                amount = "ଅଣଜଳସେଚିତ ଅଞ୍ଚଳ ବିକାଶ ପାଇଁ ₹୧୨,୫୦୦/ହେକ୍ଟର ସହାୟତା",
                howToApply = "ବ୍ଲକ କୃଷି ଅଧିକାରୀ କିମ୍ବା ଗ୍ରାମ୍ୟ କୃଷି କର୍ମଚାରୀଙ୍କ ମାଧ୍ୟମରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "rkvy" to SchemeTranslation(
                title = "ରାଷ୍ଟ୍ରୀୟ କୃଷି ବିକାଶ ଯୋଜନା (ଆରକେଭିୱାଇ-ରଫତାର)",
                description = "କୃଷି ଭିତ୍ତିଭୂମି ବିକାଶ, ନୂତନ ଜ୍ଞାନକୌଶଳ ଏବଂ କୃଷି ଷ୍ଟାର୍ଟଅପ୍ ପାଇଁ ଆର୍ଥିକ ଅନୁଦାନ।",
                eligibility = "ଚାଷୀ, ଏଫପିଓ ଏବଂ କୃଷି ଉଦ୍ୟୋଗୀ।",
                benefits = "ଅମଳ ପରବର୍ତ୍ତୀ ପ୍ରକ୍ରିୟାକରଣ କେନ୍ଦ୍ର ଏବଂ କୃଷି ଷ୍ଟାର୍ଟଅପ୍ ପାଇଁ ₹୨୫ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ ଅନୁଦାନ।",
                amount = "ଷ୍ଟାର୍ଟଅପ୍ ଅନୁଦାନ ₹୨୫ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ",
                howToApply = "rkvy.nic.in ପୋର୍ଟାଲ କିମ୍ବା ରାଜ୍ୟ କୃଷି ବିଭାଗ ମାଧ୍ୟମରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "agri_infra" to SchemeTranslation(
                title = "କୃଷି ଭିତ୍ତିଭୂମି ପାଣ୍ଠି (ଏଆଇଏଫ)",
                description = "ଗୋଦାମ ଘର, ଶୀତଳ ଭଣ୍ଡାର, ପ୍ୟାକ୍-ହାଉସ୍ ଏବଂ ପ୍ରାଥମିକ ପ୍ରକ୍ରିୟାକରଣ ୟୁନିଟ୍ ନିର୍ମାଣ ପାଇଁ ରିହାତି ଋଣ।",
                eligibility = "ଚାଷୀ, ଏଫପିଓ, ପ୍ରାଥମିକ କୃଷି ସମବାୟ ସମିତି (PACS) ଏବଂ କୃଷି ଉଦ୍ୟୋଗୀ।",
                benefits = "₹୨ କୋଟି ପର୍ଯ୍ୟନ୍ତ ଋଣ ଉପରେ ୭ ବର୍ଷ ପାଇଁ ବାର୍ଷିକ ୩% ସୁଧ ଛାଡ଼ ଏବଂ କ୍ରେଡିଟ୍ ଗ୍ୟାରେଣ୍ଟି।",
                amount = "₹୨ କୋଟି ଋଣ ଉପରେ ୩% ସୁଧ ରିହାତି",
                howToApply = "agriinfra.dac.gov.in ପୋର୍ଟାଲରେ ପ୍ରକଳ୍ପ ରିପୋର୍ଟ ଦାଖଲ କରନ୍ତୁ।"
            ),
            "smam" to SchemeTranslation(
                title = "କୃଷି ଯାନ୍ତ୍ରିକୀକରଣ ଉପ-ମିଶନ (ଏସଏମଏଏମ)",
                description = "ଟ୍ରାକ୍ଟର, ପାୱାର ଟିଲର, ଧାନ କଟା ମେସିନ ଓ ଆଧୁନିକ କୃଷି ଯନ୍ତ୍ରପାତି କିଣିବା ପାଇଁ ସବସିଡି।",
                eligibility = "କ୍ଷୁଦ୍ର, ନାମମାତ୍ର, ମହିଳା, ଏସସି/ଏସଟି ଚାଷୀ ଏବଂ ଗ୍ରାମୀଣ ଯୁବକ।",
                benefits = "କୃଷି ଯନ୍ତ୍ରପାତି ଉପରେ ୪୦% ରୁ ୫୦% ସିଧାସଳଖ ସରକାରୀ ସବସିଡି।",
                amount = "ଯନ୍ତ୍ରପାତି ଉପରେ ୪୦%-୫୦% ସବସିଡି",
                howToApply = "agrimachinery.nic.in କିମ୍ବା DBT Agriculture ପୋର୍ଟାଲରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "nbhm" to SchemeTranslation(
                title = "ଜାତୀୟ ମହୁମାଛି ପାଳନ ମିଶନ (ଏନବିଏଚଏମ)",
                description = "ଅତିରିକ୍ତ ଆୟ, ପରାଗସଙ୍ଗମ ବୃଦ୍ଧି ଏବଂ ଶୁଦ୍ଧ ମହୁ ଉତ୍ପାଦନ ପାଇଁ ବୈଜ୍ଞାନିକ ମହୁଚାଷ।",
                eligibility = "ଚାଷୀ, ମହିଳା ସ୍ୱୟଂ ସହାୟକ ଗୋଷ୍ଠୀ ଏବଂ ମହୁଚାଷୀ।",
                benefits = "ମହୁ ବାକ୍ସ, କଲୋନୀ ଏବଂ ମହୁ ନିଷ୍କାସନ ଯନ୍ତ୍ର ଉପରେ ୮୦% ପର୍ଯ୍ୟନ୍ତ ସବସିଡି।",
                amount = "ମହିଳା/ଏସସି ପାଇଁ ୮୦%, ଅନ୍ୟମାନଙ୍କୁ ୫୦% ସବସିଡି",
                howToApply = "nbb.gov.in ପୋର୍ଟାଲ କିମ୍ବା ଜିଲ୍ଲା ଉଦ୍ୟାନ କୃଷି କାର୍ଯ୍ୟାଳୟରେ ଯୋଗାଯୋଗ କରନ୍ତୁ।"
            ),
            "midh" to SchemeTranslation(
                title = "ସମନ୍ୱିତ ଉଦ୍ୟାନ କୃଷି ବିକାଶ ମିଶନ (ଏମଆଇଡିଏଚ)",
                description = "ଫଳ, ପନିପରିବା, ମସଲା, ଫୁଲ, ପଲିହାଉସ୍ ଏବଂ ଶୀତଳ ଭଣ୍ଡାରର ସାମଗ୍ରିକ ଉନ୍ନତି।",
                eligibility = "ଉଦ୍ୟାନ କୃଷି କରୁଥିବା ସମସ୍ତ ଚାଷୀ ଏବଂ ଏଫପିଓ।",
                benefits = "ନୂତନ ବଗିଚା ସ୍ଥାପନ, ସେଡନେଟ୍ ପଲିହାଉସ୍ ଏବଂ ପ୍ୟାକିଂ ହାଉସ୍ ପାଇଁ ୪୦-୫୦% ଆର୍ଥିକ ଅନୁଦାନ।",
                amount = "୪୦% ରୁ ୫୦% ପୁଞ୍ଜି ସବସିଡି",
                howToApply = "ଜିଲ୍ଲା ଉଦ୍ୟାନ କୃଷି ସହକାରୀ ନିର୍ଦ୍ଦେଶକଙ୍କୁ ସମ୍ପର୍କ କରନ୍ତୁ।"
            ),
            "pm_aasha" to SchemeTranslation(
                title = "ପିଏମ୍-ଆଶା (ଚାଷୀ ଆୟ ସଂରକ୍ଷଣ ଅଭିଯାନ)",
                description = "ଡାଲି, ତୈଳବୀଜ ଏବଂ ନଡ଼ିଆ କୋପ୍ରା ପାଇଁ ସର୍ବନିମ୍ନ ସହାୟକ ମୂଲ୍ୟ (MSP) ର ସୁନିଶ୍ଚିତତା।",
                eligibility = "ଅଧିସୂଚିତ ତୈଳବୀଜ ଓ ଡାଲି ଜାତୀୟ ଫସଲ ଚାଷ କରୁଥିବା ପଞ୍ଜୀକୃତ ଚାଷୀ।",
                benefits = "ବଜାର ଦର ଏମଏସପି ଠାରୁ କମିଲେ ସରକାରୀ କ୍ରୟ କିମ୍ବା ମୂଲ୍ୟ ପାର୍ଥକ୍ୟ ରାଶି ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଖାତାରେ ଜମା।",
                amount = "ସମ୍ପୂର୍ଣ୍ଣ ସର୍ବନିମ୍ନ ସହାୟକ ମୂଲ୍ୟ (MSP) ଲାଭ",
                howToApply = "ଫସଲ ଅମଳ ପୂର୍ବରୁ ରାଜ୍ୟ କ୍ରୟ ପୋର୍ଟାଲରେ ପଞ୍ଜୀକରଣ କରନ୍ତୁ।"
            ),
            "mh_mahatma_jyotirao_phule" to SchemeTranslation(
                title = "ମହାତ୍ମା ଜ୍ୟୋତିରାଓ ଫୁଲେ କୃଷକ ଋଣମୁକ୍ତି ଯୋଜନା",
                description = "ମହାରାଷ୍ଟ୍ରର ଚାଷୀଙ୍କ ପାଇଁ ₹୨ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ ବକେୟା ଫସଲ ଋଣ ସମ୍ପୂର୍ଣ୍ଣ ଛାଡ଼ କରିବା ଯୋଜନା।",
                eligibility = "ମହାରାଷ୍ଟ୍ରର ସମବାୟ ବା ବ୍ୟାଙ୍କରେ ₹୨ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ ଋଣ ଥିବା ଚାଷୀ।",
                benefits = "₹୨ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ ସମ୍ପୂର୍ଣ୍ଣ ଋଣ ଛାଡ଼ ଏବଂ ନିୟମିତ ପରିଶୋଧ କରୁଥିବା ଚାଷୀଙ୍କୁ ₹୫୦,୦୦୦ ପୁରସ୍କାର।",
                amount = "₹୨ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ ଋଣ ଛାଡ଼ + ₹୫୦,୦୦୦ ରିହାତି",
                howToApply = "MahaDBT ପୋର୍ଟାଲ କିମ୍ବା ତହସିଲଦାର କାର୍ଯ୍ୟାଳୟରେ ଆଧାର ସହିତ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "mh_nanaji_deshmukh" to SchemeTranslation(
                title = "ନାନାଜୀ ଦେଶମୁଖ କୃଷି ସଞ୍ଜୀବନୀ ଯୋଜନା (ପୋକ୍ରା)",
                description = "ମହାରାଷ୍ଟ୍ରର ମରୁଡ଼ି ପ୍ରଭାବିତ ଅଞ୍ଚଳରେ ଜଳବାୟୁ ସହନଶୀଳ କୃଷି ବିକାଶ ପ୍ରକଳ୍ପ।",
                eligibility = "ମରାଠୱାଡା ଏବଂ ବିଦର୍ଭର ୧୫ଟି ମରୁଡ଼ି ପ୍ରଭାବିତ ଜିଲ୍ଲାର କ୍ଷୁଦ୍ର ଚାଷୀ।",
                benefits = "କୃଷି ପୋଖରୀ, ବୁନ୍ଦା ସେଚନ, ସେଡନେଟ୍ ଏବଂ ଜମି ଉନ୍ନତିକରଣ ପାଇଁ ୭୫% ପର୍ଯ୍ୟନ୍ତ ଅନୁଦାନ।",
                amount = "ଜଳ ସଂରକ୍ଷଣ ଓ ବୁନ୍ଦା ସେଚନ ଉପରେ ୭୫% ସବସିଡି",
                howToApply = "mahapocra.gov.in ପୋର୍ଟାଲରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "pb_pani_bachao_paisa_kamao" to SchemeTranslation(
                title = "ପାଣି ବଞ୍ଚାଅ ପଇସା ରୋଜଗାର କର ଯୋଜନା",
                description = "କୃଷି ନଳକୂପରେ ବିଦ୍ୟୁତ୍ ଏବଂ ଭୂତଳ ଜଳ ବଞ୍ଚାଇବା ବଦଳରେ ଚାଷୀଙ୍କୁ ନଗଦ ପ୍ରୋତ୍ସାହନ।",
                eligibility = "ପଞ୍ଜାବର ମିଟର ଥିବା କୃଷି ବିଦ୍ୟୁତ ସଂଯୋଗ ଥିବା ଚାଷୀ।",
                benefits = "ବିଦ୍ୟୁତ ବଞ୍ଚାଇଲେ ୟୁନିଟ୍ ପିଛା ₹୪ ହିସାବରେ ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଖାତାରେ ଜମା।",
                amount = "ବଞ୍ଚାଯାଇଥିବା ବିଦ୍ୟୁତ ଉପରେ ₹୪ ପ୍ରତି ୟୁନିଟ୍",
                howToApply = "ପଞ୍ଜାବ ରାଜ୍ୟ ବିଦ୍ୟୁତ ନିଗମ (PSPCL) କାର୍ଯ୍ୟାଳୟରେ ନାମ ଲେଖାନ୍ତୁ।"
            ),
            "up_kisan_uday" to SchemeTranslation(
                title = "ୟୁପି କିଷାନ ଉଦୟ ଯୋଜନା",
                description = "ଉତ୍ତର ପ୍ରଦେଶର ଚାଷୀଙ୍କୁ ଶକ୍ତି-ଦକ୍ଷ ସ୍ମାର୍ଟ ସୌର ପମ୍ପ ସେଟ୍ ମାଗଣାରେ ବଣ୍ଟନ।",
                eligibility = "ଚାଷଜମି ଓ ଜଳସେଚନ ଆବଶ୍ୟକତା ଥିବା ୟୁପିର କ୍ଷୁଦ୍ର ଓ ନାମମାତ୍ର ଚାଷୀ।",
                benefits = "୨ ରୁ ୫ ଏଚପି ସ୍ମାର୍ଟ ସୋଲାର ପମ୍ପର ମାଗଣା ଫିଟିଂ ଏବଂ ୫ ବର୍ଷ ମାଗଣା ମରାମତି।",
                amount = "୧୦୦% ମାଗଣା ସୋଲାର ପମ୍ପ ବଣ୍ଟନ",
                howToApply = "upagriculture.com ପୋର୍ଟାଲରେ ଅନଲାଇନ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "mp_bhavantar" to SchemeTranslation(
                title = "ଭାୱାନ୍ତର ଭୁଗତାନ ଯୋଜନା (ମୂଲ୍ୟ ପାର୍ଥକ୍ୟ ଭରଣା)",
                description = "ମଣ୍ଡିରେ ଫସଲ ଦର ଏମଏସପି ଠାରୁ କମିଲେ ମୂଲ୍ୟ ପାର୍ଥକ୍ୟ ରାଶି ସିଧାସଳଖ ଚାଷୀଙ୍କ ଖାତାରେ ଜମା।",
                eligibility = "ମଧ୍ୟପ୍ରଦେଶ ଇ-ଉପାର୍ଜନ ପୋର୍ଟାଲରେ ପଞ୍ଜୀକୃତ ଚାଷୀ।",
                benefits = "ଏମଏସପି ଏବଂ ମଣ୍ଡି ବିକ୍ରି ମୂଲ୍ୟର ପାର୍ଥକ୍ୟ ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଖାତାକୁ ପ୍ରଦାନ।",
                amount = "କ୍ୱିଣ୍ଟାଲ ପିଛା ମୂଲ୍ୟ ପାର୍ଥକ୍ୟ ରାଶି",
                howToApply = "mpeuparjan.nic.in ରେ ଫସଲ ଅମଳ ପୂର୍ବରୁ ପଞ୍ଜୀକରଣ କରନ୍ତୁ।"
            ),
            "ts_rythu_bandhu" to SchemeTranslation(
                title = "ରାଇତୁ ବନ୍ଧୁ ଯୋଜନା (ଚାଷୀ ନିବେଶ ସହାୟତା)",
                description = "ଚାଷ ଖର୍ଚ୍ଚ ପାଇଁ ବାର୍ଷିକ ଏକର ପିଛା ₹୧୦,୦୦୦ ସିଧାସଳଖ ଆର୍ଥିକ ସହାୟତା।",
                eligibility = "ତେଲେଙ୍ଗାନାର ସମସ୍ତ ପଟ୍ଟାଧାରୀ ଚାଷୀ ପରିବାର।",
                benefits = "ଖରିଫ ପାଇଁ ₹୫,୦୦୦ ଏବଂ ରବି ପାଇଁ ₹୫,୦୦୦ ଏକର ପିଛା ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଜମା।",
                amount = "ଏକର ପିଛା ବାର୍ଷିକ ₹୧୦,୦୦୦",
                howToApply = "ଧରଣୀ ପୋର୍ଟାଲ ରେକର୍ଡ ଆଧାରରେ ସ୍ୱତଃ ପଞ୍ଜୀକରଣ।"
            ),
            "tn_free_electricity" to SchemeTranslation(
                title = "ତାମିଲନାଡୁ ମାଗଣା କୃଷି ବିଦ୍ୟୁତ ଯୋଜନା",
                description = "ତାମିଲନାଡୁରେ କୃଷି ପମ୍ପ ସେଟ୍ ପାଇଁ ୨୪ ଘଣ୍ଟା ସମ୍ପୂର୍ଣ୍ଣ ମାଗଣା ବିଦ୍ୟୁତ୍ ଯୋଗାଣ।",
                eligibility = "ତାମିଲନାଡୁର ସମସ୍ତ ପଞ୍ଜୀକୃତ କୃଷି ବିଦ୍ୟୁତ ଉପଭୋକ୍ତା।",
                benefits = "କୌଣସି ବିଲ୍ କିମ୍ବା ମିଟର ଚାର୍ଜ ବିନା ଚାଷ ପାଇଁ ୧୦୦% ମାଗଣା ବିଦ୍ୟୁତ୍।",
                amount = "୧୦୦% ମାଗଣା କୃଷି ବିଦ୍ୟୁତ୍",
                howToApply = "ତାମିଲନାଡୁ ବିଦ୍ୟୁତ୍ ବୋର୍ଡ (TANGEDCO) ରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "ka_raitha_siri" to SchemeTranslation(
                title = "ରାଇତା ସିରି ଯୋଜନା (କ୍ଷୁଦ୍ର ଶସ୍ୟ ପ୍ରୋତ୍ସାହନ)",
                description = "ମାଣ୍ଡିଆ ଓ ମିଲେଟ୍ସ ଚାଷ ପାଇଁ ପ୍ରୋତ୍ସାହନ ଏବଂ ₹୩ ଲକ୍ଷ ପର୍ଯ୍ୟନ୍ତ ଶୂନ ପ୍ରତିଶତ ସୁଧରେ ଫସଲ ଋଣ।",
                eligibility = "କର୍ଣ୍ଣାଟକର ମିଲେଟ୍ସ ଚାଷୀ ପରିବାର।",
                benefits = "ମିଲେଟ୍ସ ଚାଷ ପାଇଁ ହେକ୍ଟର ପିଛା ₹୧୦,୦୦୦ ଅନୁଦାନ ଏବଂ ୦% ସୁଧରେ ଋଣ।",
                amount = "₹୧୦,୦୦୦/ହେକ୍ଟର + ୦% ସୁଧ ଋଣ",
                howToApply = "କର୍ଣ୍ଣାଟକ ରାଇତା ମିତ୍ର ପୋର୍ଟାଲ (raitamitra.karnataka.gov.in) ରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "gj_kisan_suryodaya" to SchemeTranslation(
                title = "କିଷାନ ସୂର୍ଯ୍ୟୋଦୟ ଯୋଜନା",
                description = "ଗୁଜରାଟର ଚାଷୀଙ୍କୁ ଦିନ ବେଳେ (ସକାଳ ୫ ରୁ ରାତି ୯) ଜଳସେଚନ ପାଇଁ ବିଶ୍ୱସନୀୟ ବିଦ୍ୟୁତ ଯୋଗାଣ।",
                eligibility = "ଗୁଜରାଟର କୃଷି ବିଦ୍ୟୁତ ସଂଯୋଗ ଥିବା ସମସ୍ତ ଚାଷୀ।",
                benefits = "ଦିନ ବେଳେ ସୁରକ୍ଷିତ ବିଦ୍ୟୁତ ମିଳିବା ଦ୍ୱାରା ରାତିରେ ଜଳସେଚନର ବିପଦ ଦୂର ହୁଏ।",
                amount = "ମାଗଣା ସରକାରୀ ଭିତ୍ତିଭୂମି ଯୋଜନା",
                howToApply = "ବିଦ୍ୟୁତ ବିତରଣ କମ୍ପାନୀ (DISCOMs) ମାଧ୍ୟମରେ ସ୍ୱତଃ ସୁବିଧା।"
            ),
            "rj_kisan_mitra" to SchemeTranslation(
                title = "ରାଜସ୍ଥାନ କିଷାନ ମିତ୍ର ଶକ୍ତି ଯୋଜନା",
                description = "କୃଷି ବିଦ୍ୟୁତ ବିଲ୍ ଉପରେ ମାସିକ ₹୧,୦୦୦ ପର୍ଯ୍ୟନ୍ତ ସିଧାସଳଖ ସରକାରୀ ସବସିଡି।",
                eligibility = "ରାଜସ୍ଥାନର ମିଟର ଥିବା କୃଷି ବିଦ୍ୟୁତ ଉପଭୋକ୍ତା।",
                benefits = "ବିଦ୍ୟୁତ ବିଲରେ ପ୍ରତି ମାସ ₹୧,୦୦୦ (ବାର୍ଷିକ ₹୧୨,୦୦୦ ପର୍ଯ୍ୟନ୍ତ) ସିଧାସଳଖ ରିହାତି।",
                amount = "ବାର୍ଷିକ ₹୧୨,୦୦୦ ପର୍ଯ୍ୟନ୍ତ ବିଦ୍ୟୁତ ସବସିଡି",
                howToApply = "ବିଦ୍ୟୁତ ବିତରଣ ନିଗମ ଦ୍ୱାରା ବିଲରେ ସିଧାସଳଖ ଛାଡ଼।"
            ),
            "br_diesel_anudan" to SchemeTranslation(
                title = "ବିହାର ଡିଜେଲ ସବସିଡି ଯୋଜନା (ଡିଜେଲ ଅନୁଦାନ)",
                description = "ମରୁଡ଼ି ସମୟରେ ଫସଲ ଜଳସେଚନ ପାଇଁ ଡିଜେଲ କିଣିବା ଉପରେ ସରକାରୀ ରିହାତି।",
                eligibility = "ବିହାରର ଜମି ମାଲିକ ଏବଂ ଭାଗଚାଷୀ।",
                benefits = "ଡିଜେଲ ଉପରେ ଲିଟର ପିଛା ₹୭୫ ସବସିଡି (ଗୋଟିଏ ସେଚନ ପାଇଁ ଏକର ପିଛା ₹୭୫୦, ସର୍ବାଧିକ ୩ ଥର)।",
                amount = "₹୭୫/ଲିଟର (ସର୍ବାଧିକ ₹୨,୨୫୦/ଏକର)",
                howToApply = "ଡିବିଟି ଏଗ୍ରିକଲଚର ବିହାର (dbtagriculture.bihar.gov.in) ରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "wb_krishak_bandhu" to SchemeTranslation(
                title = "କୃଷକ ବନ୍ଧୁ ଯୋଜନା",
                description = "ପଶ୍ଚିମବଙ୍ଗର ଚାଷୀଙ୍କୁ ବାର୍ଷିକ ₹୧୦,୦୦୦ ଆର୍ଥିକ ସହାୟତା ଏବଂ ₹୨ ଲକ୍ଷର ଜୀବନ ବୀମା।",
                eligibility = "ପଶ୍ଚିମବଙ୍ଗର ସମସ୍ତ ଚାଷୀ ଓ ପଞ୍ଜୀକୃତ ବର୍ଗାଦାର (ବୟସ ୧୮ ରୁ ୬୦ ବର୍ଷ)।",
                benefits = "ବର୍ଷକୁ ₹୧୦,୦୦୦ (ଦୁଇଟି କିସ୍ତିରେ) ଏବଂ ଚାଷୀଙ୍କ ମୃତ୍ୟୁରେ ପରିବାରକୁ ₹୨ ଲକ୍ଷର ସହାୟତା।",
                amount = "ବାର୍ଷିକ ₹୧୦,୦୦୦ + ₹୨ ଲକ୍ଷ ବୀମା",
                howToApply = "ବିଡିଓ (BDO) କାର୍ଯ୍ୟାଳୟ କିମ୍ବା krishakbandhu.net ପୋର୍ଟାଲରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "ap_ysr_rythu_bharosa" to SchemeTranslation(
                title = "ୱାଇଏସଆର ରାଇତୁ ଭରସା - ପିଏମ କିଷାନ",
                description = "ଆନ୍ଧ୍ରପ୍ରଦେଶରେ ଭାଗଚାଷୀଙ୍କ ସମେତ ସମସ୍ତ ଚାଷୀ ପରିବାରକୁ ବାର୍ଷିକ ₹୧୩,୫୦୦ ନିବେଶ ସହାୟତା।",
                eligibility = "ଆନ୍ଧ୍ରର ଜମି ମାଲିକ ଏବଂ ପଛୁଆ ବର୍ଗର ଭାଗଚାଷୀ।",
                benefits = "ବିହନ, ସାର ପାଇଁ ୩ଟି କିସ୍ତିରେ ବାର୍ଷିକ ମୋଟ ₹୧୩,୫୦୦ ବ୍ୟାଙ୍କ ଖାତାରେ ଜମା।",
                amount = "ବାର୍ଷିକ ₹୧୩,୫୦୦",
                howToApply = "ରାଇତୁ ଭରସା କେନ୍ଦ୍ର (RBK) କିମ୍ବା ଗ୍ରାମ ସଚିବାଳୟରେ ନାମ ଲେଖାନ୍ତୁ।"
            ),
            "hr_bhavantar" to SchemeTranslation(
                title = "ହରିୟାଣା ଭାୱାନ୍ତର ଭରପାୟୀ ଯୋଜନା",
                description = "ପନିପରିବା ଓ ଉଦ୍ୟାନ ଫସଲର ବଜାର ଦର କମିଗଲେ କ୍ଷତିପୂରଣ ପ୍ରଦାନ କରୁଥିବା ଯୋଜନା।",
                eligibility = "ମେରୀ ଫସଲ ମେରା ବ୍ୟୋରା ପୋର୍ଟାଲରେ ପଞ୍ଜୀକୃତ ହରିୟାଣାର ଉଦ୍ୟାନ କୃଷି ଚାଷୀ।",
                benefits = "ସୁରକ୍ଷିତ ମୂଲ୍ୟ ଏବଂ ମଣ୍ଡି ବିକ୍ରି ମୂଲ୍ୟର ପାର୍ଥକ୍ୟ ସିଧାସଳଖ ବ୍ୟାଙ୍କ ଖାତାକୁ ପ୍ରଦାନ।",
                amount = "କ୍ୱିଣ୍ଟାଲ ପିଛା ମୂଲ୍ୟ ପାର୍ଥକ୍ୟ ରାଶି",
                howToApply = "ଫସଲ ଲଗାଇବା ପୂର୍ବରୁ fasal.haryana.gov.in ରେ ପଞ୍ଜୀକରଣ କରନ୍ତୁ।"
            ),
            "od_kalia" to SchemeTranslation(
                title = "କାଳିଆ ଯୋଜନା (କୃଷକ ସହାୟତା ଓ ଆୟ ବୃଦ୍ଧି)",
                description = "ଓଡ଼ିଶାର କ୍ଷୁଦ୍ର, ନାମମାତ୍ର ଓ ଭୂମିହୀନ କୃଷି ପରିବାର ପାଇଁ ସାମଗ୍ରିକ ଆର୍ଥିକ ସହାୟତା।",
                eligibility = "ଓଡ଼ିଶାର କ୍ଷୁଦ୍ର/ନାମମାତ୍ର ଚାଷୀ ଏବଂ ଭୂମିହୀନ କୃଷି ଶ୍ରମିକ ପରିବାର।",
                benefits = "ଚାଷ ପାଇଁ ₹୧୦,୦୦୦/ବର୍ଷ, ଭୂମିହୀନଙ୍କ ପାଇଁ ₹୧୨,୫୦୦ ଏବଂ ₹୨ ଲକ୍ଷର ବୀମା ସୁରକ୍ଷା।",
                amount = "ବାର୍ଷିକ ₹୧୦,୦୦୦ + ଜୀବିକା ସହାୟତା",
                howToApply = "kalia.odisha.gov.in ପୋର୍ଟାଲ କିମ୍ବା ଗ୍ରାମ ପଞ୍ଚାୟତରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
            "kl_comprehensive_crop" to SchemeTranslation(
                title = "କେରଳ ରାଜ୍ୟ ସାମଗ୍ରିକ ଫସଲ ବୀମା ଯୋଜନା",
                description = "ବନ୍ୟା, ଭୂସ୍ଖଳନ ଏବଂ ବନ୍ୟଜନ୍ତୁ ଆକ୍ରମଣରୁ ୨୫+ ମୁଖ୍ୟ ଫସଲକୁ ରାଜ୍ୟ ସ୍ତରୀୟ ବୀମା ସୁରକ୍ଷା।",
                eligibility = "କେରଳରେ ଧାନ, କଦଳୀ, ମସଲା, ପନିପରିବା ଓ ରବର ଚାଷ କରୁଥିବା ସମସ୍ତ ଚାଷୀ।",
                benefits = "ପ୍ରାକୃତିକ ବିପର୍ଯ୍ୟୟ ଏବଂ ବନ୍ୟପ୍ରାଣୀ କ୍ଷତି ପାଇଁ ତୁରନ୍ତ କ୍ଷତିପୂରଣ।",
                amount = "ଫସଲ ଅନୁଯାୟୀ ଏକର ପିଛା ₹୩୫,୦୦୦ ପର୍ଯ୍ୟନ୍ତ କ୍ଷତିପୂରଣ",
                howToApply = "AIMS ପୋର୍ଟାଲ (aims.kerala.gov.in) କିମ୍ବା କୃଷି ଭବନରେ ଆବେଦନ କରନ୍ତୁ।"
            ),
        ),
    )
}
