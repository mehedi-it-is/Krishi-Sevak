package com.krishisevak.app.data.engine

data class HelplineContact(
    val title: String,
    val number: String,
    val timing: String,
    val description: String,
    val languages: String,
    val isTollFree: Boolean = true,
    val iconEmoji: String = "📞"
)

data class KvkCenter(
    val state: String,
    val district: String,
    val name: String,
    val headScientist: String,
    val phone: String,
    val email: String,
    val address: String
)

object KvkDirectory {

    val nationalHelplines = listOf(
        HelplineContact(
            title = "Kisan Call Center (KCC)",
            number = "18001801551",
            timing = "06:00 AM – 10:00 PM (All 365 Days)",
            description = "Ministry of Agriculture direct farmer query line. Speaks directly with agricultural graduates and agronomists.",
            languages = "22 Indian Languages (Hindi, Marathi, Bengali, Tamil, Telugu, Kannada, Gujarati, Punjabi, etc.)",
            isTollFree = true,
            iconEmoji = "🏛️"
        ),
        HelplineContact(
            title = "Kisan Suvidha Emergency Helpline",
            number = "1551",
            timing = "24/7 IVR & Live Support",
            description = "Short emergency code for quick farmer assistance, mandi rates, and crop insurance guidance.",
            languages = "Hindi, English & Regional",
            isTollFree = true,
            iconEmoji = "🚨"
        ),
        HelplineContact(
            title = "PM-KISAN Central Helpline",
            number = "155261",
            timing = "09:30 AM – 06:00 PM (Mon-Fri)",
            description = "Inquiries regarding ₹6,000 yearly instalment status, Aadhaar seeding, and e-KYC resolution.",
            languages = "Hindi & English",
            isTollFree = true,
            iconEmoji = "💰"
        ),
        HelplineContact(
            title = "Pradhan Mantri Fasal Bima Yojana (PMFBY)",
            number = "14447",
            timing = "09:00 AM – 07:00 PM",
            description = "Crop loss intimation within 72 hours of localized disaster (flood, unseasonal rain, hailstorm, drought).",
            languages = "Hindi, English & All State Languages",
            isTollFree = true,
            iconEmoji = "🛡️"
        )
    )

    val districtKvkList = listOf(
        // Maharashtra
        KvkCenter("Maharashtra", "Nashik", "KVK Nashik (YCMOU)", "Dr. Nitin Thorat", "02532230554", "kvknashik@gmail.com", "Yashwantrao Chavan Open University Campus, Gangapur Road, Nashik - 422222"),
        KvkCenter("Maharashtra", "Pune", "KVK Baramati (Pune)", "Dr. Ratan Jadhav", "02112255207", "kvkbaramati@yahoo.com", "Agricultural Development Trust, Malegaon Khurd, Baramati, Pune - 413115"),
        KvkCenter("Maharashtra", "Aurangabad", "KVK Aurangabad (VNMKV)", "Dr. S. K. Patil", "02402376288", "kvkaurangabad@rediffmail.com", "National Highway 211, Jalna Road, Aurangabad - 431005"),
        KvkCenter("Maharashtra", "Nagpur", "KVK Nagpur (CICR)", "Dr. U. V. Galkate", "07103275536", "kvknagpur@gmail.com", "Central Institute for Cotton Research, Shankarnagar, Nagpur - 440010"),
        KvkCenter("Maharashtra", "Kolhapur", "KVK Kolhapur (Talsande)", "Dr. A. S. Jagtap", "02302477382", "kvkkolhapur@gmail.com", "D.Y. Patil Education Complex, Talsande, Kolhapur - 416112"),

        // Punjab
        KvkCenter("Punjab", "Ludhiana", "KVK Ludhiana (PAU)", "Dr. Gurmeet Singh", "01612401960", "kvkludhiana@pau.edu", "Punjab Agricultural University Campus, Ferozepur Road, Ludhiana - 141004"),
        KvkCenter("Punjab", "Amritsar", "KVK Amritsar (Nag Kalan)", "Dr. Bikramjit Singh", "01832783850", "kvkamritsar@pau.edu", "Majitha Road, Nag Kalan, Amritsar - 143601"),
        KvkCenter("Punjab", "Bathinda", "KVK Bathinda", "Dr. Jaspal Singh", "01642212159", "kvkbathinda@pau.edu", "Regional Research Station, Dabwali Road, Bathinda - 151001"),

        // Uttar Pradesh
        KvkCenter("Uttar Pradesh", "Kanpur", "KVK Kanpur Nagar (CSAUAT)", "Dr. Rajiv Kumar", "05122534157", "kvkkanpur@csauk.ac.in", "Chandra Shekhar Azad University of Agriculture & Technology, Kanpur - 208002"),
        KvkCenter("Uttar Pradesh", "Varanasi", "KVK Varanasi (IIVR)", "Dr. Narendra Singh", "05422635236", "kvkvaranasi@iivr.org.in", "ICAR-Indian Institute of Vegetable Research, Shahanshapur, Varanasi - 221305"),
        KvkCenter("Uttar Pradesh", "Agra", "KVK Bichpuri (Agra)", "Dr. B. S. Rajput", "05622636442", "kvkagra@gmail.com", "RBS College Research Farm, Bichpuri, Agra - 283105"),
        KvkCenter("Uttar Pradesh", "Lucknow", "KVK Lucknow (IISR)", "Dr. A. K. Dubey", "05222480726", "kvklucknow@iisr.nic.in", "Indian Institute of Sugarcane Research, Telibagh, Lucknow - 226002"),

        // Madhya Pradesh
        KvkCenter("Madhya Pradesh", "Indore", "KVK Kasturbagram (Indore)", "Dr. Alok Deshwal", "07312851410", "kvkindore@rediffmail.com", "Kasturbagram Rural Institute, Khandwa Road, Indore - 452020"),
        KvkCenter("Madhya Pradesh", "Ujjain", "KVK Ujjain (RVSKVV)", "Dr. R. P. Sharma", "07342514800", "kvkujjain@rvskvv.net", "Dewas Road, Near Vikram University, Ujjain - 456010"),
        KvkCenter("Madhya Pradesh", "Bhopal", "KVK Bhopal (CIAE)", "Dr. Sanjay Kumar", "07552521080", "kvkbhopal@ciae.res.in", "Central Institute of Agricultural Engineering, Nabi Bagh, Bhopal - 462038"),

        // Rajasthan
        KvkCenter("Rajasthan", "Jaipur", "KVK Jaipur (SKNAU)", "Dr. Mahesh Kothari", "01425254022", "kvkjaipur@sknau.ac.in", "Durgapura Research Station, Tonk Road, Jaipur - 302018"),
        KvkCenter("Rajasthan", "Jodhpur", "KVK Jodhpur (CAZRI)", "Dr. Bhagwan Singh", "02912786534", "kvkjodhpur@cazri.res.in", "Central Arid Zone Research Institute Campus, Jodhpur - 342003"),
        KvkCenter("Rajasthan", "Kota", "KVK Kota (AU Kota)", "Dr. K. M. Sharma", "07442321204", "kvkkota@aukota.org", "Borkhera Agricultural Farm, Baran Road, Kota - 324001"),

        // Gujarat
        KvkCenter("Gujarat", "Anand", "KVK Anand (AAU)", "Dr. G. G. Patel", "02692261310", "kvkanand@aau.in", "Anand Agricultural University Campus, Anand - 388110"),
        KvkCenter("Gujarat", "Junagadh", "KVK Junagadh (JAU)", "Dr. H. M. Gajipara", "02852670131", "kvkjunagadh@jau.in", "Junagadh Agricultural University, Moti Baug, Junagadh - 362001"),

        // Karnataka
        KvkCenter("Karnataka", "Bengaluru Rural", "KVK Hadonahalli (UASB)", "Dr. K. C. Narayanaswamy", "08027651005", "kvkbengaluru@uasbangalore.edu.in", "Hadonahalli, Doddaballapura Taluk, Bengaluru Rural - 561203"),
        KvkCenter("Karnataka", "Mysuru", "KVK Suttur (JSS)", "Dr. Arun Balamatti", "08221232218", "kvk_suttur@jssonline.org", "JSS Rural Development Trust, Suttur, Nanjangud, Mysuru - 571129"),
        KvkCenter("Karnataka", "Dharwad", "KVK Dharwad (UASD)", "Dr. V. I. Benagi", "08362214436", "kvkdharwad@uasd.in", "University of Agricultural Sciences, Dharwad - 580005"),

        // Tamil Nadu
        KvkCenter("Tamil Nadu", "Coimbatore", "KVK Coimbatore (TNAU)", "Dr. M. Jawaharlal", "04226611200", "kvkcbe@tnau.ac.in", "Tamil Nadu Agricultural University Campus, Coimbatore - 641003"),
        KvkCenter("Tamil Nadu", "Madurai", "KVK Madurai (AC&RI)", "Dr. V. Swaminathan", "04522422955", "kvkmadurai@tnau.ac.in", "Agricultural College & Research Institute, Othakadai, Madurai - 625104"),

        // Andhra Pradesh & Telangana
        KvkCenter("Andhra Pradesh", "Guntur", "KVK Lam (ANGRAU)", "Dr. P. Rambabu", "08632524017", "kvk.lam@angrau.ac.in", "Regional Agricultural Research Station, Lam, Guntur - 522034"),
        KvkCenter("Telangana", "Hyderabad / Ranga Reddy", "KVK CRIDA (Hayathnagar)", "Dr. K. S. Reddy", "04024530161", "kvk.crida@icar.gov.in", "Hayathnagar Research Farm, Saheb Nagar, Hyderabad - 500059"),

        // West Bengal & Bihar
        KvkCenter("West Bengal", "Nadia", "KVK Nadia (BCKV)", "Dr. S. K. Roy", "03473222277", "kvknadia@bckv.edu.in", "Bidhan Chandra Krishi Viswavidyalaya, Mohanpur, Nadia - 741252"),
        KvkCenter("Bihar", "Patna", "KVK Barh (ICAR-RCER)", "Dr. Ujjwal Kumar", "06122223962", "kvkbarh@icar.gov.in", "ICAR Research Complex for Eastern Region, Parisar, Patna - 800014"),

        // Odisha & Haryana
        KvkCenter("Odisha", "Khurda", "KVK Khurda (CIFA)", "Dr. S. S. Mishra", "06742465446", "kvk.cifa@icar.gov.in", "Central Institute of Freshwater Aquaculture, Kausalyaganga, Bhubaneswar - 751002"),
        KvkCenter("Haryana", "Karnal", "KVK Karnal (NDRI)", "Dr. M. S. Chauhan", "01842259023", "kvkkarnal@ndri.res.in", "National Dairy Research Institute, Karnal - 132001")
    )
}
