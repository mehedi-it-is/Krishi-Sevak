package com.kisaandost.app.ui.schemes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kisaandost.app.data.engine.SchemeTranslations
import com.kisaandost.app.data.local.datastore.DataStoreManager
import com.kisaandost.app.utils.TtsManager
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class SchemeStatus { ACTIVE, CLOSED, UPCOMING }

data class SchemeItem(
    val id: String,
    val title: String,
    val description: String,
    val eligibility: String,
    val benefits: String,
    val amount: String,
    val howToApply: String,
    val status: SchemeStatus = SchemeStatus.ACTIVE,
    val openingDate: String? = null,
    val closingDate: String? = null,
    val officialUrl: String,
    val category: String = "NATIONAL", // NATIONAL, STATE, ALERT
    val targetState: String? = null // null = all India
)

class SchemesViewModel(
    private val dataStoreManager: DataStoreManager,
    val ttsManager: TtsManager
) : ViewModel() {

    private val _userState = MutableStateFlow("Maharashtra")
    val userState: StateFlow<String> = _userState.asStateFlow()

    private val _userLanguageCode = MutableStateFlow("hi")
    val userLanguageCode: StateFlow<String> = _userLanguageCode.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.locationStateFlow.collect { _userState.value = it }
        }
        viewModelScope.launch {
            dataStoreManager.userLanguageCodeFlow.collect { _userLanguageCode.value = it }
        }
        refreshSchemes()
    }

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _refreshTrigger = MutableStateFlow(0)

    // All schemes filtered by user's state and translated to user's selected language
    val filteredSchemes: StateFlow<List<SchemeItem>> = combine(
        _userState,
        _userLanguageCode,
        _refreshTrigger
    ) { state, langCode, _ ->
        val national = allNationalSchemes
        val stateSpecific = allStateSchemes.filter { it.targetState.equals(state, ignoreCase = true) }
        (national + stateSpecific).map { scheme ->
            SchemeTranslations.getTranslatedScheme(scheme, langCode)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), allNationalSchemes)

    fun refreshSchemes(force: Boolean = false) {
        viewModelScope.launch {
            val lastFetch = dataStoreManager.lastSchemesFetchTimeFlow.first()
            val now = System.currentTimeMillis()
            val twentyFourHours = 24 * 60 * 60 * 1000L
            
            if (force || now - lastFetch > twentyFourHours) {
                _isRefreshing.value = true
                kotlinx.coroutines.delay(1500) // Simulate real-time API fetch
                _refreshTrigger.value += 1
                dataStoreManager.updateLastSchemesFetchTime(now)
                _isRefreshing.value = false
            }
        }
    }

    fun speakScheme(scheme: SchemeItem) {
        val translated = SchemeTranslations.getTranslatedScheme(scheme, _userLanguageCode.value)
        val text = "${translated.title}. ${translated.description}. ${translated.benefits}."
        ttsManager.speak("scheme_${scheme.id}", text, _userLanguageCode.value)
    }

    companion object {
        val allNationalSchemes = listOf(
            SchemeItem(
                id = "pm_kisan",
                title = "PM-KISAN (Pradhan Mantri Kisan Samman Nidhi)",
                description = "Direct income support of ₹6,000 per year in 3 equal instalments of ₹2,000 each, directly to the bank account of eligible farmer families.",
                eligibility = "All landholding farmer families with cultivable land. Excludes institutional landholders, income tax payers, and constitutional post holders.",
                benefits = "₹6,000/year direct benefit transfer in 3 instalments of ₹2,000 every 4 months.",
                amount = "₹6,000/year",
                howToApply = "Register through local Common Service Centre (CSC), State Nodal Officer, or online at pmkisan.gov.in. Requires Aadhaar, bank account, and land ownership records.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://pmkisan.gov.in/"
            ),
            SchemeItem(
                id = "pmfby",
                title = "PMFBY (Pradhan Mantri Fasal Bima Yojana)",
                description = "Comprehensive crop insurance scheme covering all food, oilseed, and horticultural crops against natural calamities, pests, and diseases.",
                eligibility = "All farmers including sharecroppers and tenant farmers growing notified crops. Both loanee and non-loanee farmers are eligible.",
                benefits = "Full insured sum coverage. Premium: Kharif 2%, Rabi 1.5%, Horticulture 5%. Government subsidizes the remaining premium.",
                amount = "Insured sum based on crop and area. Premium: 2% Kharif, 1.5% Rabi, 5% Horticulture",
                howToApply = "Apply through nearest bank branch, CSC, or PMFBY portal within the enrollment window for each crop season.",
                status = SchemeStatus.ACTIVE,
                openingDate = "Kharif: April-July, Rabi: October-December",
                closingDate = "Season-specific deadlines",
                officialUrl = "https://pmfby.gov.in/"
            ),
            SchemeItem(
                id = "kcc",
                title = "Kisan Credit Card (KCC)",
                description = "Provides short-term credit to farmers for crop production, post-harvest expenses, and consumption needs at subsidized interest rates.",
                eligibility = "All farmers, including individual/joint borrowers, sharecroppers, tenant farmers, and SHGs/JLGs of farmers.",
                benefits = "Credit at 4% interest (with prompt repayment subvention). Insurance cover under PMFBY. Revolving cash credit facility.",
                amount = "Credit limit based on land holding and crop pattern. Up to ₹3 lakh at 4% interest with subvention.",
                howToApply = "Apply at any commercial, cooperative, or regional rural bank. Requires land records, ID proof, and passport-size photos.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://www.pmkisan.gov.in/KCC.aspx"
            ),
            SchemeItem(
                id = "soil_health",
                title = "Soil Health Card Scheme",
                description = "Provides soil health cards to farmers carrying crop-wise recommendations of nutrients and fertilizers for improving soil productivity.",
                eligibility = "All farmers across India with agricultural land.",
                benefits = "Free soil testing with detailed nutrient status report and fertilizer recommendations. Issued every 2 years per farm holding.",
                amount = "Free of cost to farmers. Government bears entire testing and card issuance expense.",
                howToApply = "Contact nearest soil testing lab, Krishi Vigyan Kendra (KVK), or register through the Soil Health Card Portal.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://soilhealth.dac.gov.in/"
            ),
            SchemeItem(
                id = "pmksy",
                title = "PM Krishi Sinchayee Yojana (PMKSY)",
                description = "Ensures access to protective irrigation to every farm (Har Khet Ko Pani) and improves water-use efficiency through micro/drip irrigation.",
                eligibility = "All farmers. Priority for drought-prone areas, rain-fed areas, and tribal areas.",
                benefits = "Subsidy of 55% for small/marginal farmers and 45% for others on micro-irrigation systems. Water source development support.",
                amount = "55% subsidy for small farmers, 45% for others on drip/sprinkler systems",
                howToApply = "Apply through State Agriculture Department or District Agriculture Officer. Online through PMKSY portal in select states.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://pmksy.gov.in/"
            ),
            SchemeItem(
                id = "pkvy",
                title = "Paramparagat Krishi Vikas Yojana (PKVY)",
                description = "Promotes organic farming through cluster approach. Supports farmers in adopting organic practices including PGS certification.",
                eligibility = "Groups of 50+ farmers forming a cluster of 50 acres or more for organic farming.",
                benefits = "₹50,000/hectare over 3 years for organic inputs, value addition, and marketing. PGS certification support.",
                amount = "₹50,000/hectare over 3 years",
                howToApply = "Form a group of 50 farmers, apply through District Agriculture Officer or state organic farming mission.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://pgsindia-ncof.gov.in/"
            ),
            SchemeItem(
                id = "enam",
                title = "e-NAM (National Agriculture Market)",
                description = "Online trading platform for agricultural commodities. Connects existing APMC mandis across India into a unified national market.",
                eligibility = "All farmers, traders, and commission agents registered with APMC mandis. States must reform APMC Act.",
                benefits = "Transparent price discovery, reduced intermediaries, better prices for farmers, online payment facility.",
                amount = "No fee for farmers. Government bears platform cost.",
                howToApply = "Register through the e-NAM portal or visit the nearest integrated APMC mandi with Aadhaar and bank details.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://enam.gov.in/"
            ),
            SchemeItem(
                id = "pm_kusum",
                title = "PM-KUSUM (Solar Pump Scheme)",
                description = "Promotes installation of standalone solar pumps, solarization of grid-connected agricultural pumps, and solar power plants on barren land.",
                eligibility = "All farmers, farmer groups, FPOs, cooperatives, and panchayats for different components.",
                benefits = "Component A: Solar power plants on barren land. Component B: Standalone solar pumps up to 7.5 HP. Component C: Solarize existing pumps.",
                amount = "60% subsidy (30% Central + 30% State). Farmer bears only 40% (or 10% with bank loan).",
                howToApply = "Apply through State Renewable Energy Department or MNRE portal. District-level implementation committees process applications.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://mnre.gov.in/solar/schemes/"
            ),
            SchemeItem(
                id = "nmsa",
                title = "National Mission on Sustainable Agriculture (NMSA)",
                description = "Promotes sustainable agriculture through climate change adaptation measures, soil health management, and rainfed area development.",
                eligibility = "All farmers, with priority for rainfed and drought-prone areas.",
                benefits = "Soil health management, rainfed area development, climate resilient varieties, water management support.",
                amount = "Varies by component. Soil Health: ₹31,000/unit, Rainfed Development: ₹12,500/ha",
                howToApply = "Apply through State Agriculture Department or District Agriculture Officer.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://nmsa.dac.gov.in/"
            ),
            SchemeItem(
                id = "rkvy",
                title = "Rashtriya Krishi Vikas Yojana (RKVY-RAFTAAR)",
                description = "Incentivizes states to increase public investment in agriculture and allied sectors through flexi-fund mechanism.",
                eligibility = "State governments for agricultural infrastructure. Farmers benefit indirectly through improved facilities.",
                benefits = "Agricultural infrastructure, innovation, agri-entrepreneurship. Incubation support for agri-startups.",
                amount = "State-specific allocation. Agri-startup grants up to ₹25 lakh.",
                howToApply = "Projects proposed by State Agriculture Departments. Startups apply through RKVY-RAFTAAR portal.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://rkvy.nic.in/"
            ),
            SchemeItem(
                id = "agri_infra",
                title = "Agriculture Infrastructure Fund (AIF)",
                description = "Financing facility for post-harvest management infrastructure and community farming assets through interest subvention and credit guarantee.",
                eligibility = "Farmers, FPOs, PACS, SHGs, agri-entrepreneurs, startups, and state agencies.",
                benefits = "3% interest subvention on loans up to ₹2 crore. Credit guarantee through CGTMSE/NABARD.",
                amount = "Loans up to ₹2 crore with 3% interest subvention for 7 years",
                howToApply = "Apply through banks or online AIF portal. Project proposals evaluated by concerned bank.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://agriinfra.dac.gov.in/"
            ),
            SchemeItem(
                id = "smam",
                title = "Sub-Mission on Agricultural Mechanization (SMAM)",
                description = "Promotes farm mechanization by providing subsidies on purchase of agricultural machinery and equipment.",
                eligibility = "Individual farmers, FPOs, cooperatives, Self Help Groups, and entrepreneur for custom hiring centres.",
                benefits = "Subsidy of 40-50% on tractors, harvesters, rotavators, threshers, and other farm equipment.",
                amount = "40-50% subsidy on machinery. Custom Hiring Centre: up to ₹10 lakh",
                howToApply = "Apply through DBT Agriculture portal of respective state or District Agriculture Officer.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://agrimachinery.nic.in/"
            ),
            SchemeItem(
                id = "nbhm",
                title = "National Beekeeping & Honey Mission (NBHM)",
                description = "Promotes scientific beekeeping for income generation, pollination support, and honey/bee products development.",
                eligibility = "All farmers, unemployed youth, women SHGs, and FPOs interested in beekeeping.",
                benefits = "Subsidy on bee colonies, bee boxes, honey processing equipment. Training support.",
                amount = "80% subsidy for SC/ST/Women, 50% for others on beekeeping equipment",
                howToApply = "Apply through NBHM portal or contact State Horticulture Department.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://nbb.gov.in/"
            ),
            SchemeItem(
                id = "midh",
                title = "Mission for Integrated Development of Horticulture (MIDH)",
                description = "Holistic development of horticulture sector including fruits, vegetables, mushrooms, spices, flowers, and coconut.",
                eligibility = "Individual farmers, FPOs, cooperatives, companies, and state agencies engaged in horticulture.",
                benefits = "Subsidies for planting material, area expansion, protected cultivation, cold storage, and processing infrastructure.",
                amount = "40-50% subsidy on various horticulture activities. Protected cultivation: up to ₹56 lakh/ha",
                howToApply = "Apply through National Horticulture Board or State Horticulture Mission.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://midh.gov.in/"
            ),
            SchemeItem(
                id = "pm_aasha",
                title = "PM-AASHA (Annadata Aay Sanrakshan Abhiyan)",
                description = "Ensures MSP to farmers through Price Support Scheme (PSS), Price Deficiency Payment Scheme (PDPS), and Private Procurement.",
                eligibility = "All farmers selling notified oilseeds, pulses, and copra at prices below MSP.",
                benefits = "Government procures at MSP if market price falls below. Deficiency payment for the difference.",
                amount = "Procurement at MSP. Deficiency payment = MSP minus selling price.",
                howToApply = "Register on state procurement portal during procurement season. Sell at designated centres.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://dfpd.gov.in/"
            )
        )

        val allStateSchemes = listOf(
            // Maharashtra
            SchemeItem(
                id = "mh_mahatma_jyotirao_phule",
                title = "Mahatma Jyotirao Phule Shetkari Karj Mukti Yojana",
                description = "Farm loan waiver scheme for Maharashtra farmers with outstanding crop loans up to ₹2 lakh.",
                eligibility = "Farmers in Maharashtra with crop loans up to ₹2 lakh from nationalized, cooperative, or rural banks.",
                benefits = "Complete waiver of crop loans up to ₹2 lakh. Incentive of ₹50,000 for regular loan repayers.",
                amount = "Loan waiver up to ₹2 lakh + ₹50,000 incentive",
                howToApply = "Apply through MahaDBT portal or nearest Tahsildar office. Requires 7/12 extract and bank details.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://mahadbt.maharashtra.gov.in/",
                category = "STATE",
                targetState = "Maharashtra"
            ),
            SchemeItem(
                id = "mh_nanaji_deshmukh",
                title = "Nanaji Deshmukh Krishi Sanjivani Yojana",
                description = "Climate-resilient agriculture project for drought-prone areas of Maharashtra with World Bank support.",
                eligibility = "Farmers in 15 drought-prone districts of Maharashtra.",
                benefits = "Soil-water conservation, improved farming practices, crop diversification, market linkages.",
                amount = "Project-specific grants. Total outlay ₹4,000 crore with World Bank assistance.",
                howToApply = "Register through Gram Panchayat or Project Management Unit in eligible districts.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://mahapocra.gov.in/",
                category = "STATE",
                targetState = "Maharashtra"
            ),

            // Punjab
            SchemeItem(
                id = "pb_pani_bachao_paisa_kamao",
                title = "Pani Bachao Paisa Kamao (Save Water Earn Money)",
                description = "Incentivizes farmers to save electricity used for irrigation. Farmers get ₹4/unit for every unit saved below a benchmark.",
                eligibility = "All Punjab farmers with metered tubewells on agricultural feeders.",
                benefits = "₹4/unit saved directly deposited in bank account. Promotes water conservation.",
                amount = "₹4 per electricity unit saved",
                howToApply = "Automatic enrollment for farmers on smart-metered agricultural feeders.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://pbpower.punjab.gov.in/",
                category = "STATE",
                targetState = "Punjab"
            ),

            // Uttar Pradesh
            SchemeItem(
                id = "up_kisan_uday",
                title = "UP Kisan Uday Yojana",
                description = "Free distribution of solar pumps to small and marginal farmers for irrigation purposes.",
                eligibility = "Small and marginal farmers in UP who do not have grid-connected electric pumps.",
                benefits = "Free solar pump (2-5 HP) with installation. Reduces diesel dependency for irrigation.",
                amount = "Free solar pumps worth ₹60,000-₹2,00,000 depending on capacity",
                howToApply = "Apply through UP Agriculture Department portal or District Agriculture Officer.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://upagriculture.com/",
                category = "STATE",
                targetState = "Uttar Pradesh"
            ),

            // Madhya Pradesh
            SchemeItem(
                id = "mp_bhavantar",
                title = "Bhavantar Bhugtan Yojana (Price Deficit Payment)",
                description = "Compensates farmers when market prices fall below MSP. Difference between MSP and modal price is paid to farmers.",
                eligibility = "All registered farmers in MP who sell notified crops through registered mandis.",
                benefits = "Payment of price difference between MSP and average selling price directly to farmer's account.",
                amount = "Variable: MSP minus average market price per quintal",
                howToApply = "Register on MP e-Uparjan portal during crop procurement season.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://mpeuparjan.nic.in/",
                category = "STATE",
                targetState = "Madhya Pradesh"
            ),

            // Telangana
            SchemeItem(
                id = "ts_rythu_bandhu",
                title = "Rythu Bandhu (Farmer Investment Support)",
                description = "Direct investment support of ₹10,000/acre/year to all land-owning farmers for two crop seasons.",
                eligibility = "All farmer families in Telangana owning agricultural land as per land records.",
                benefits = "₹10,000 per acre per year (₹5,000 each for Rabi and Kharif seasons) direct bank transfer.",
                amount = "₹10,000/acre/year",
                howToApply = "Automatic for registered pattadar farmers. Update land records at Mee-Seva or Dharani portal.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://rythubandhu.telangana.gov.in/",
                category = "STATE",
                targetState = "Telangana"
            ),

            // Tamil Nadu
            SchemeItem(
                id = "tn_free_electricity",
                title = "Tamil Nadu Free Farm Electricity Scheme",
                description = "Free electricity for agricultural pumpsets used for irrigation by farmers in Tamil Nadu.",
                eligibility = "All farmers in Tamil Nadu with registered agricultural pumpsets.",
                benefits = "Completely free electricity for irrigation with no metering or billing.",
                amount = "100% free electricity for agricultural use",
                howToApply = "Apply through TNEB (Tamil Nadu Electricity Board) for agricultural service connection.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://www.tneb.in/",
                category = "STATE",
                targetState = "Tamil Nadu"
            ),

            // Karnataka
            SchemeItem(
                id = "ka_raitha_siri",
                title = "Raitha Siri (Farmer Prosperity Scheme)",
                description = "Provides zero-interest crop loans up to ₹3 lakh to farmers in Karnataka through cooperative banks.",
                eligibility = "All farmers in Karnataka with land records and cooperative bank membership.",
                benefits = "Zero-interest short-term crop loans up to ₹3 lakh. Additional interest subvention for timely repayment.",
                amount = "0% interest crop loan up to ₹3 lakh",
                howToApply = "Apply at nearest Primary Agricultural Credit Society (PACS) or cooperative bank.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://raitamitra.karnataka.gov.in/",
                category = "STATE",
                targetState = "Karnataka"
            ),

            // Gujarat
            SchemeItem(
                id = "gj_kisan_suryodaya",
                title = "Kisan Suryodaya Yojana",
                description = "Provides dedicated daytime electricity (5 AM to 9 PM) for agricultural irrigation through new transmission infrastructure.",
                eligibility = "All farmers in Gujarat connected to agricultural power feeders.",
                benefits = "16 hours of daytime electricity for irrigation. Reduces dependence on midnight/odd-hour power supply.",
                amount = "₹3,500 crore state investment for transmission infrastructure",
                howToApply = "Automatic for farmers on agricultural feeders in phased rollout districts.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://guj-nre.gujarat.gov.in/",
                category = "STATE",
                targetState = "Gujarat"
            ),

            // Rajasthan
            SchemeItem(
                id = "rj_kisan_mitra",
                title = "Rajasthan Kisan Mitra Energy Yojana",
                description = "Subsidy on electricity bills for agricultural connections to reduce irrigation costs for farmers.",
                eligibility = "All metered agricultural consumers in Rajasthan.",
                benefits = "₹1,000/month subsidy on electricity bills for agricultural pumpsets up to 25% discount.",
                amount = "Up to ₹12,000/year electricity subsidy",
                howToApply = "Automatic subsidy credited to electricity bill for eligible agricultural connections.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://energy.rajasthan.gov.in/",
                category = "STATE",
                targetState = "Rajasthan"
            ),

            // Bihar
            SchemeItem(
                id = "br_diesel_anudan",
                title = "Bihar Diesel Anudan Yojana",
                description = "Diesel subsidy for irrigation during Kharif and Rabi seasons to reduce farming costs.",
                eligibility = "All farmers in Bihar for irrigation of notified crops.",
                benefits = "₹75/litre diesel subsidy for agricultural irrigation, up to 10 litres/acre for Kharif.",
                amount = "₹75/litre subsidy, max 10 litres/acre",
                howToApply = "Apply through DBT Agriculture Bihar portal with land records and Aadhaar.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://dbtagriculture.bihar.gov.in/",
                category = "STATE",
                targetState = "Bihar"
            ),

            // West Bengal
            SchemeItem(
                id = "wb_krishak_bandhu",
                title = "Krishak Bandhu (Farmer's Friend)",
                description = "Financial assistance of ₹10,000/year to all farmers and ₹2 lakh life insurance for farmer deaths during 18-60 years.",
                eligibility = "All farmers in West Bengal with cultivable land (minimum 1 acre). Life insurance for 18-60 age group.",
                benefits = "₹10,000/year (₹5,000 each for Kharif/Rabi). ₹2 lakh insurance on death.",
                amount = "₹10,000/year + ₹2 lakh life insurance",
                howToApply = "Register through Krishak Bandhu portal or nearest Block Development Office.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://krishakbandhu.net/",
                category = "STATE",
                targetState = "West Bengal"
            ),

            // Andhra Pradesh
            SchemeItem(
                id = "ap_ysr_rythu_bharosa",
                title = "YSR Rythu Bharosa (Farmer Investment Support)",
                description = "Investment support of ₹13,500/year to all farmer families including tenant farmers.",
                eligibility = "All farmer families in AP including landless tenant farmers (Crop Cultivators Rights Cards).",
                benefits = "₹13,500/year: ₹7,500 (Kharif) + ₹4,000 (Rabi) + ₹2,000 PM-KISAN top-up.",
                amount = "₹13,500/year (includes PM-KISAN component)",
                howToApply = "Automatic for registered farmers. New registrations through Village/Ward Secretariats.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://ysrrythubharosa.ap.gov.in/",
                category = "STATE",
                targetState = "Andhra Pradesh"
            ),

            // Haryana
            SchemeItem(
                id = "hr_bhavantar",
                title = "Haryana Bhavantar Bharpai Yojana",
                description = "Price deficiency payment for horticultural crops when market prices fall below government-set protected prices.",
                eligibility = "Horticultural crop farmers in Haryana registered on Meri Fasal Mera Byora portal.",
                benefits = "Difference between protected price and selling price directly transferred to farmer's account.",
                amount = "Variable: Protected price minus market price per quintal",
                howToApply = "Register crops on Meri Fasal Mera Byora portal before each season deadline.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://fasal.haryana.gov.in/",
                category = "STATE",
                targetState = "Haryana"
            ),

            // Odisha
            SchemeItem(
                id = "od_kalia",
                title = "KALIA (Krushak Assistance for Livelihood)",
                description = "Financial assistance to small, marginal, landless farmers and sharecroppers for cultivation and livelihood.",
                eligibility = "Small, marginal, and landless cultivators and agricultural households in Odisha.",
                benefits = "₹12,500/year for cultivation. ₹12,500/year for landless. ₹2 lakh life/accident insurance.",
                amount = "₹12,500/year cultivation support + insurance",
                howToApply = "Apply through KALIA portal or nearest Block/GP office with land records.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://kalia.odisha.gov.in/",
                category = "STATE",
                targetState = "Odisha"
            ),

            // Kerala
            SchemeItem(
                id = "kl_comprehensive_crop",
                title = "Kerala State Crop Insurance Scheme",
                description = "State-funded crop insurance for crops not covered under PMFBY, including perennial crops and spices.",
                eligibility = "All farmers in Kerala growing notified crops including rubber, coconut, pepper, cardamom.",
                benefits = "Coverage for natural calamities, pest attack, and disease. Claims processed within 45 days.",
                amount = "Subsidized premium (State bears major share). Coverage varies by crop.",
                howToApply = "Apply through Krishi Bhavan (Agricultural Office) at panchayat level.",
                status = SchemeStatus.ACTIVE,
                officialUrl = "https://keralaagriculture.gov.in/",
                category = "STATE",
                targetState = "Kerala"
            )
        )
    }
}
