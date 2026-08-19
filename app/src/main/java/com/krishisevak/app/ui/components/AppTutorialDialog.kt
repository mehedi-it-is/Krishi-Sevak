package com.krishisevak.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.krishisevak.app.utils.TtsManager
import kotlinx.coroutines.delay

data class LocalizedTutorialStep(
    val emoji: String,
    val color: Color,
    val titles: Map<String, String>,
    val descriptions: Map<String, String>,
    val tips: Map<String, String>
)

val localizedTutorialSteps = listOf(
    LocalizedTutorialStep(
        emoji = "🌦️",
        color = Color(0xFF0284C7),
        titles = mapOf(
            "en" to "Agro-Weather & Sowing Window",
            "hi" to "कृषि मौसम एवं बुवाई सलाहकार",
            "bn" to "কৃষি আবহাওয়া ও বপন নির্দেশিকা",
            "mr" to "कृषी हवामान व पेरणी सल्ला",
            "te" to "వ్యవసాయ వాతావరణం & విత్తే సమయం",
            "ta" to "வேளாண் வானிலை & விதைப்பு வழிகாட்டி",
            "kn" to "ಕೃಷಿ ಹವಾಮಾನ ಮತ್ತು ಬಿತ್ತನೆ ಸಲಹೆ",
            "ml" to "കാലാവസ്ഥയും വിതയ്ക്കൽ സമയവും",
            "gu" to "કૃષિ હવામાન અને વાવણી સલાહ",
            "pa" to "ਖੇਤੀ ਮੌਸਮ ਅਤੇ ਬਿਜਾਈ ਸਲਾਹ",
            "or" to "କୃଷି ପାଣିପାଗ ଓ ବୁଣିବା ପରାମର୍ଶ"
        ),
        descriptions = mapOf(
            "en" to "Get 7-day hyper-local weather forecasts, rain probability, humidity alerts, and ideal pesticide spraying windows calibrated for your farm.",
            "hi" to "7-दिवसीय सटीक मौसम पूर्वानुमान, वर्षा की संभावना, आर्द्रता अलर्ट और कीटनाशक छिड़काव के सर्वोत्तम समय की जानकारी प्राप्त करें।",
            "bn" to "৭ দিনের সঠিক স্থানীয় আবহাওয়ার পূর্বাভাস, বৃষ্টির সম্ভাবনা এবং কীটনাশক স্প্রে করার উপযুক্ত সময়ের তথ্য পান।",
            "mr" to "७ दिवसांचा अचूक स्थानिक हवामान अंदाज, पावसाची शक्यता आणि कीटकनाशक फवारणीच्या सर्वोत्तम वेळेची माहिती मिळवा.",
            "te" to "7 రోజుల ఖచ్చితమైన స్థానిక వాతావరణ సమాచారం, వర్ష సూచన మరియు పురుగుమందుల పిచికారీకి అనుకూల సమయం తెలుసుకోండి.",
            "ta" to "7 நாள் துல்லியமான உள்ளூர் வானிலை முன்னறிவிப்பு, மழை வாய்ப்பு மற்றும் பூச்சிக்கொல்லி தெளிப்பதற்கான சிறந்த நேரத்தை அறியலாம்.",
            "kn" to "7 ದಿನಗಳ ನಿಖರವಾದ ಹವಾಮಾನ ಮುನ್ಸೂಚನೆ, ಮಳೆಯ ಸಾಧ್ಯತೆ ಮತ್ತು ಕೀಟನಾಶಕ ಸಿಂಪಡಣೆಗೆ ಸೂಕ್ತ ಸಮಯವನ್ನು ತಿಳಿಯಿರಿ.",
            "ml" to "7 ദിവസത്തെ കൃത്യമായ കാലാവസ്ഥാ പ്രവചനം, മഴ സാധ്യത, കീടനാശിനി തളിക്കാൻ അനുയോജ്യമായ സമയം എന്നിവ അറിയുക.",
            "gu" to "૭ દિવસની સચોટ સ્થાનિક હવામાન આગાહી, વરસાદની સંભાવના અને જંતુનાશક છંટકાવના શ્રેષ્ઠ સમયની માહિતી મેળવો.",
            "pa" to "7-ਦਿਨਾਂ ਸਹੀ ਸਥਾਨਕ ਮੌਸਮ ਭਵਿੱਖਬਾਣੀ, ਮੀਂਹ ਦੀ ਸੰਭਾਵਨਾ ਅਤੇ ਕੀਟਨਾਸ਼ਕ ਸਪਰੇਅ ਦੇ ਸਹੀ ਸਮੇਂ ਦੀ ਜਾਣਕਾਰੀ ਪ੍ਰਾਪਤ ਕਰੋ।",
            "or" to "୭ ଦିନର ସଠିକ୍ ପାଣିପାଗ ପୂର୍ବାନୁମାନ, ବର୍ଷା ସମ୍ଭାବନା ଏବଂ କୀଟନାଶକ ସ୍ପ୍ରେ କରିବାର ଉପଯୁକ୍ତ ସମୟ ଜାଣନ୍ତୁ।"
        ),
        tips = mapOf(
            "en" to "Tip: Tap the speaker icon on the weather card to listen to the audio forecast.",
            "hi" to "सुझाव: मौसम कार्ड पर स्पीकर आइकन दबाकर पूरी मौसम रिपोर्ट सुनें।",
            "bn" to "পরামর্শ: আবহাওয়া কার্ডের স্পিকার আইকনে ট্যাপ করে অডিও শুনুন।",
            "mr" to "टीप: हवामान कार्डावरील स्पीकर आयकॉनवर टॅप करून संपूर्ण अहवाल ऐका.",
            "te" to "చిట్కా: పూర్తి వాతావరణ నివేదిక వినడానికి స్పీకర్ ఐకాన్ నొక్కండి.",
            "ta" to "குறிப்பு: வானிலை அறிக்கையைக் கேட்க ஸ்பீக்கர் ஐகானைத் தட்டவும்.",
            "kn" to "ಸಲಹೆ: ಹವಾಮಾನ ವರದಿಯನ್ನು ಕೇಳಲು ಸ್ಪೀಕರ್ ಐಕಾನ್ ಒತ್ತಿ.",
            "ml" to "സൂചന: കാലാവസ്ഥാ വിവരങ്ങൾ കേൾക്കാൻ സ്പീക്കർ ഐക്കൺ അമർത്തുക.",
            "gu" to "સૂચન: હવામાન અહેવાલ સાંભળવા માટે સ્પીકર આયકન પર ટેપ કરો.",
            "pa" to "ਸੁਝਾਅ: ਪੂਰੀ ਮੌਸਮ ਰਿਪੋਰਟ ਸੁਣਨ ਲਈ ਸਪੀਕਰ ਆਈਕਨ 'ਤੇ ਟੈਪ ਕਰੋ।",
            "or" to "ଟିପ୍: ପାଣିପାଗ ରିପୋର୍ଟ ଶୁଣିବା ପାଇଁ ସ୍ପିକର୍ ଆଇକନ୍ ଦବାନ୍ତୁ।"
        )
    ),
    LocalizedTutorialStep(
        emoji = "🏛️",
        color = Color(0xFF16A34A),
        titles = mapOf(
            "en" to "Real-Time Mandi Bhav",
            "hi" to "ताज़ा मंडी भाव और लाइव कीमतें",
            "bn" to "লাইভ মান্ডির দর ও বাজার দর",
            "mr" to "ताजा बाजार भाव व लाइव दर",
            "te" to "తాజా మార్కెట్ ధరలు & లైవ్ రేట్లు",
            "ta" to "நேரலை சந்தை விலை நிலவரம்",
            "kn" to "ಲೈವ್ ಮಾರುಕಟ್ಟೆ ದರಗಳು",
            "ml" to "തത്സമയ ചന്ത വിലനിലവാരം",
            "gu" to "તાજા બજાર ભાવ અને લાઈવ દરો",
            "pa" to "ਤਾਜ਼ਾ ਮੰਡੀ ਦੇ ਭਾਅ",
            "or" to "ଲାଇଭ୍ ମଣ୍ଡି ଦର"
        ),
        descriptions = mapOf(
            "en" to "Explore live crop prices across 60+ commodities from your nearest APMC Mandi, with distance tracking and price trend indicators.",
            "hi" to "अपने निकटतम APMC मंडी से 60 से अधिक फसलों के ताज़ा भाव, दूरी और मूल्य में तेजी/मंदी का रुझान देखें।",
            "bn" to "আপনার নিকটবর্তী APMC মান্ডি থেকে ৬০টির বেশি ফসলের তাজা দর এবং দূরত্বের তথ্য দেখুন।",
            "mr" to "जवळच्या बाजार समितीमधील ६०+ पिकांचे ताजे भाव, अंतर आणि तेजी-मंदीचा कल तपासा.",
            "te" to "మీ సమీప మార్కెట్ నుండి 60కి పైగా పంటల తాజా ధరలు, దూరం మరియు ధరల ధోరణిని చూడండి.",
            "ta" to "அருகிலுள்ள சந்தையிலிருந்து 60க்கும் மேற்பட்ட பயிர்களின் நேரலை விலை, தூரம் மற்றும் விலை மாற்றங்களை அறியலாம்.",
            "kn" to "ಹತ್ತಿರದ ಮಾರುಕಟ್ಟೆಯಿಂದ 60 ಕ್ಕೂ ಹೆಚ್ಚು ಬೆಳೆಗಳ ನೇರ ದರಗಳು, ದೂರ ಮತ್ತು ದರ ಏರಿಳಿತಗಳನ್ನು ಪರಿಶೀಲಿಸಿ.",
            "ml" to "അടുത്തുള്ള ചന്തയിൽ നിന്നുള്ള 60-ലധികം വിളകളുടെ ഏറ്റവും പുതിയ വിലകളും വില വ്യതിയാനങ്ങളും അറിയുക.",
            "gu" to "નજીકના માર્કેટ યાર્ડમાંથી ૬૦+ પાકોના લાઈવ ભાવ, અંતર અને તેજી-મંદીનો ટ્રેન્ડ જુઓ.",
            "pa" to "ਆਪਣੀ ਨੇੜਲੀ ਮੰਡੀ ਤੋਂ 60+ ਫ਼ਸਲਾਂ ਦੇ ਤਾਜ਼ਾ ਭਾਅ, ਦੂਰੀ ਅਤੇ ਭਾਅ ਦੇ ਰੁਝਾਨ ਦੇਖੋ।",
            "or" to "ଆପଣଙ୍କ ନିକଟସ୍ଥ ମଣ୍ଡିରୁ ୬୦+ ଫସଲର ତାଜା ଦର, ଦୂରତା ଏବଂ ମୂଲ୍ୟ ବୃଦ୍ଧି-ହ୍ରାସ ଦେଖନ୍ତୁ।"
        ),
        tips = mapOf(
            "en" to "Tip: Use category filters (Vegetables, Fruits, Grains) or search bar to find any crop.",
            "hi" to "सुझाव: किसी भी फसल का भाव जानने के लिए सर्च बार या श्रेणी फिल्टर का उपयोग करें।",
            "bn" to "পরামর্শ: যেকোনো ফসল খুঁজতে সার্চ বার বা ক্যাটাগরি ফিল্টার ব্যবহার করুন।",
            "mr" to "टीप: कोणतेही पीक शोधण्यासाठी सर्च बार किंवा श्रेणी फिल्टर वापरा.",
            "te" to "చిట్కా: పంటలను వెతకడానికి సెర్చ్ బార్ లేదా కేటగిరీ ఫిల్టర్‌లను ఉపయోగించండి.",
            "ta" to "குறிப்பு: எந்தப் பயிரையும் தேட தேடல் பட்டி அல்லது வகை வடிப்பான்களைப் பயன்படுத்தவும்.",
            "kn" to "ಸಲಹೆ: ಯಾವುದೇ ಬೆಳೆಯನ್ನು ಹುಡುಕಲು ಹುಡುಕಾಟ ಪಟ್ಟಿ ಅಥವಾ ವರ್ಗ ಫಿಲ್ಟರ್ ಬಳಸಿ.",
            "ml" to "സൂചന: വിളകൾ കണ്ടെത്താൻ തിരയൽ ബാറോ കാറ്റഗറി ഫിൽട്ടറോ ഉപയോഗിക്കുക.",
            "gu" to "સૂચન: કોઈપણ પાક શોધવા માટે સર્ચ બાર અથવા કેટેગરી ફિલ્ટરનો ઉપયોગ કરો.",
            "pa" to "ਸੁਝਾਅ: ਕਿਸੇ ਵੀ ਫ਼ਸਲ ਨੂੰ ਲੱਭਣ ਲਈ ਖੋਜ ਪੱਟੀ ਜਾਂ ਸ਼੍ਰੇਣੀ ਫਿਲਟਰ ਦੀ ਵਰਤੋਂ ਕਰੋ।",
            "or" to "ଟିପ୍: ଯେକୌଣସି ଫସଲ ଖୋଜିବା ପାଇଁ ସର୍ଚ୍ଚ ବାର୍ କିମ୍ବା ବର୍ଗ ଫିଲ୍ଟର୍ ବ୍ୟବହାର କରନ୍ତୁ।"
        )
    ),
    LocalizedTutorialStep(
        emoji = "🩺",
        color = Color(0xFFEF4444),
        titles = mapOf(
            "en" to "Multimodal Crop Doctor",
            "hi" to "एआई फसल डॉक्टर - पत्तों की जांच",
            "bn" to "এআই ফসল ডাক্তার - পাতার রোগ নির্ণয়",
            "mr" to "एआय पीक डॉक्टर - पानांची तपासणी",
            "te" to "ఏఐ క్రాప్ డాక్టర్ - ఆకుల పరీక్ష",
            "ta" to "ஏஐ பயிர் மருத்துவர் - இலை பரிசோதனை",
            "kn" to "ಎಐ ಬೆಳೆ ವೈದ್ಯ - ಎಲೆ ಪರೀಕ್ಷೆ",
            "ml" to "AI ക്രോപ്പ് ഡോക്ടർ - ഇല പരിശോധന",
            "gu" to "AI પાક ડૉક્ટર - પાંદડાની તપાસ",
            "pa" to "AI ਕ੍ਰੌਪ ਡਾਕਟਰ - ਪੱਤਿਆਂ ਦੀ ਜਾਂਚ",
            "or" to "AI କ୍ରପ୍ ଡାକ୍ତର - ପତ୍ର ପରୀକ୍ଷା"
        ),
        descriptions = mapOf(
            "en" to "Snap a photo of diseased leaves or crops to instantly diagnose fungal, pest, or nutrient deficiencies with organic & chemical cure steps.",
            "hi" to "फसल की बीमारी या पत्तों की फोटो लें और एआई द्वारा तुरंत बीमारी की पहचान एवं जैविक व रासायनिक उपचार पाएं।",
            "bn" to "রোগাক্রান্ত পাতার ছবি তুলুন এবং এআই-এর সাহায্যে তাৎক্ষণিক রোগ শনাক্তকরণ ও প্রতিকার পান।",
            "mr" to "आजारी पिकाच्या पाण्याचा फोटो काढा आणि रोगाचे अचूक निदान व सेंद्रिय/रासायनिक उपाय मिळवा.",
            "te" to "తెగులు సోకిన ఆకుల ఫోటో తీయండి మరియు ఏఐ ద్వారా వెంటనే వ్యాధి నిర్ధారణ మరియు నివారణ పొందండి.",
            "ta" to "நோயுற்ற இலைகளின் புகைப்படத்தை எடுத்து உடனடி நோய் கண்டறிதல் மற்றும் தீர்வு முறைகளைப் பெறுங்கள்.",
            "kn" to "ರೋಗಗ್ರಸ್ತ ಎಲೆಗಳ ಫೋಟೋ ತೆಗೆಯಿರಿ ಮತ್ತು ತಕ್ಷಣವೇ ರೋಗ ಪತ್ತೆ ಹಾಗೂ ಸಾವಯವ/ರಾಸಾಯನಿಕ ಚಿಕಿತ್ಸೆ ಪಡೆಯಿರಿ.",
            "ml" to "രോഗബാധയുള്ള ഇലകളുടെ ഫോട്ടോ എടുത്ത് ഉടൻ തന്നെ രോഗനിർണ്ണയവും പ്രതിവിധികളും നേടുക.",
            "gu" to "રોગગ્રસ્ત પાંદડાનો ફોટો લો અને તરત જ રોગની ઓળખ અને જૈવિક/રાસાયણિક ઉપચાર મેળવો.",
            "pa" to "ਬਿਮਾਰ ਪੱਤਿਆਂ ਦੀ ਫੋਟੋ ਖਿੱਚੋ ਅਤੇ ਤੁਰੰਤ ਬਿਮਾਰੀ ਦੀ ਪਛਾਣ ਅਤੇ ਜੈਵਿਕ/ਰਸਾਇਣਕ ਇਲਾਜ ਪ੍ਰਾਪਤ ਕਰੋ।",
            "or" to "ରୋଗାକ୍ରାନ୍ତ ପତ୍ରର ଫଟୋ ଉଠାନ୍ତୁ ଏବଂ ତୁରନ୍ତ ରୋଗ ଚିହ୍ନଟ ସହ ଜୈବିକ ଓ ରାସାୟନିକ ଉପଚାର ପାଆନ୍ତୁ।"
        ),
        tips = mapOf(
            "en" to "Tip: Tap the Camera button in Crop Doctor to diagnose your leaf sample.",
            "hi" to "सुझाव: फसल डॉक्टर में कैमरा बटन दबाकर तुरंत पत्ते की जांच करें।",
            "bn" to "পরামর্শ: ফসল ডাক্তারের ক্যামেরা বোতাম টিপে পাতা পরীক্ষা করুন।",
            "mr" to "टीप: पीक डॉक्टरमध्ये कॅमेरा बटण दाबून पानाची तपासणी करा.",
            "te" to "చిట్కా: ఆకులను పరీక్షించడానికి క్రాప్ డాక్టర్‌లోని కెమెరా బటన్‌ను నొక్కండి.",
            "ta" to "குறிப்பு: பயிர் மருத்துவத்தில் கேமரா பொத்தானைத் தட்டி இலையைச் சரிபார்க்கவும்.",
            "kn" to "ಸಲಹೆ: ಕ್ರಾಪ್ ಡಾಕ್ಟರ್‌ನಲ್ಲಿ ಕ್ಯಾಮೆರಾ ಬಟನ್ ಒತ್ತಿ ಎಲೆ ಪರೀಕ್ಷಿಸಿ.",
            "ml" to "സൂചന: ഇല പരിശോധിക്കാൻ ക്രോപ്പ് ഡോക്ടറിലെ ക്യാമറ ബട്ടൺ അമർത്തുക.",
            "gu" to "સૂચન: પાંદડાની તપાસ કરવા માટે ક્રોપ ડૉક્ટરમાં કેમેરા બટન દબાવો.",
            "pa" to "ਸੁਝਾਅ: ਪੱਤੇ ਦੀ ਜਾਂਚ ਕਰਨ ਲਈ ਕ੍ਰੌਪ ਡਾਕਟਰ ਵਿੱਚ ਕੈਮਰਾ ਬਟਨ ਦਬਾਓ।",
            "or" to "ଟିପ୍: ପତ୍ର ପରୀକ୍ଷା ପାଇଁ କ୍ରପ୍ ଡାକ୍ତରରେ କ୍ୟାମେରା ବଟନ୍ ଦବାନ୍ତୁ।"
        )
    ),
    LocalizedTutorialStep(
        emoji = "📜",
        color = Color(0xFFF59E0B),
        titles = mapOf(
            "en" to "Government Schemes & Subsidies",
            "hi" to "सरकारी योजनाएं एवं प्रत्यक्ष सब्सिडी",
            "bn" to "সরকারি প্রকল্প ও আর্থিক সহায়তা",
            "mr" to "शासकीय योजना व थेट अनुदान",
            "te" to "ప్రభుత్వ పథకాలు & రాయితీలు",
            "ta" to "அரசு திட்டங்கள் & மானியங்கள்",
            "kn" to "ಸರ್ಕಾರಿ ಯೋಜನೆಗಳು ಮತ್ತು ಸಬ್ಸಿಡಿಗಳು",
            "ml" to "സർക്കാർ പദ്ധതികളും സബ്‌സിഡികളും",
            "gu" to "સરકારી યોજનાઓ અને સબસિડી",
            "pa" to "ਸਰਕਾਰੀ ਸਕੀਮਾਂ ਅਤੇ ਸਬਸਿਡੀਆਂ",
            "or" to "ସରକାରୀ ଯୋଜନା ଓ ସବସିଡି"
        ),
        descriptions = mapOf(
            "en" to "Browse central and state-specific agricultural schemes (PM-Kisan, PMFBY, KCC, Solar Pumps) with direct links to official government portals.",
            "hi" to "पीएम-किसान, फसल बीमा, केसीसी और सोलर पंप जैसी केंद्र व राज्य सरकार की योजनाओं की जानकारी और सीधे आधिकारिक वेबसाइट लिंक पाएं।",
            "bn" to "পিএম-কিসান, ফসল বিমা এবং সৌর পাম্প সংক্রান্ত সরকারি প্রকল্প ও সরাসরি আবেদনের লিঙ্ক দেখুন।",
            "mr" to "पीएम-किसान, पीक विमा, केसीसी आणि सौर पंप योजनांची माहिती व थेट अधिकृत अर्ज लिंक मिळवा.",
            "te" to "పీఎం-కిసాన్, పంటల బీమా, కేసీసీ మరియు సోలార్ పంపుల వంటి పథకాల సమాచారం మరియు అధికారిక లింకులు పొందండి.",
            "ta" to "பிஎம்-கிசான், பயிர் காப்பீடு மற்றும் சோலார் பம்ப் போன்ற அரசு திட்டங்களின் விவரங்கள் மற்றும் விண்ணப்ப இணைப்புகள்.",
            "kn" to "ಪಿಎಂ-ಕಿಸಾನ್, ಬೆಳೆ ವಿಮೆ, ಕೆಸಿಸಿ ಮತ್ತು ಸೌರ ಪಂಪ್‌ಗಳಂತಹ ಯೋಜನೆಗಳ ಮಾಹಿತಿ ಮತ್ತು ಅಧಿಕೃತ ಅರ್ಜಿ ಲಿಂಕ್‌ಗಳನ್ನು ಪಡೆಯಿರಿ.",
            "ml" to "പിഎം-കിസാൻ, വിള ഇൻഷുറൻസ് തുടങ്ങിയ പദ്ധതികളുടെ വിവരങ്ങളും ഔദ്യോഗിക വെബ്സൈറ്റ് ലിങ്കുകളും.",
            "gu" to "પીએમ-કિસાન, પાક વીમો, કેસીસી અને સોલર પંપ જેવી યોજનાઓની વિગતો અને સત્તાવાર અરજી લિંક મેળવો.",
            "pa" to "ਪੀਐਮ-ਕਿਸਾਨ, ਫ਼ਸਲ ਬੀਮਾ ਅਤੇ ਸੋਲਰ ਪੰਪ ਵਰਗੀਆਂ ਸਕੀਮਾਂ ਦੀ ਜਾਣਕਾਰੀ ਅਤੇ ਅਧਿਕਾਰਤ ਲਿੰਕ ਪ੍ਰਾਪਤ ਕਰੋ।",
            "or" to "ପିଏମ୍-କିଷାନ, ଫସଲ ବୀମା ଏବଂ ସୋଲାର ପମ୍ପ ଯୋଜନା ବିଷୟରେ ସବିଶେଷ ତଥ୍ୟ ଓ ଆବେଦନ ଲିଙ୍କ୍ ପାଆନ୍ତୁ।"
        ),
        tips = mapOf(
            "en" to "Tip: Tap any scheme card to expand eligibility, benefits, and the official apply link.",
            "hi" to "सुझाव: पात्रता और आवेदन लिंक देखने के लिए किसी भी योजना कार्ड पर टैप करें।",
            "bn" to "পরামর্শ: যোগ্যতা ও আবেদনের লিঙ্ক দেখতে যেকোনো প্রকল্প কার্ডে ট্যাপ করুন।",
            "mr" to "टीप: पात्रता आणि अर्ज लिंक पाहण्यासाठी कोणत्याही योजना कार्डावर टॅप करा.",
            "te" to "చిట్కా: అర్హత మరియు దరఖాస్తు లింక్ చూడటానికి ఏదైనా పథకం కార్డుపై నొక్కండి.",
            "ta" to "குறிப்பு: தகுதி மற்றும் விண்ணப்ப இணைப்பைப் பார்க்க எந்தவொரு திட்ட அட்டையையும் தட்டவும்.",
            "kn" to "ಸಲಹೆ: ಅರ್ಹತೆ ಮತ್ತು ಅರ್ಜಿ ಲಿಂಕ್ ನೋಡಲು ಯಾವುದೇ ಯೋಜನೆ ಕಾರ್ಡ್ ಮೇಲೆ ಟ್ಯಾಪ್ ಮಾಡಿ.",
            "ml" to "സൂചന: യോഗ്യതയും അപേക്ഷാ ലിങ്കും കാണാൻ സ്കീം കാർഡിൽ ടാപ്പ് ചെയ്യുക.",
            "gu" to "સૂચન: પાત્રતા અને અરજી લિંક જોવા માટે કોઈપણ યોજના કાર્ડ પર ટેપ કરો.",
            "pa" to "ਸੁਝਾਅ: ਯੋਗਤਾ ਅਤੇ ਅਰਜ਼ੀ ਲਿੰਕ ਦੇਖਣ ਲਈ ਕਿਸੇ ਵੀ ਸਕੀਮ ਕਾਰਡ 'ਤੇ ਟੈਪ ਕਰੋ।",
            "or" to "ଟିପ୍: ଯୋଗ୍ୟତା ଏବଂ ଆବେଦନ ଲିଙ୍କ୍ ଦେଖିବାକୁ ଯୋଜନା କାର୍ଡ ଉପରେ ଟ୍ୟାପ୍ କରନ୍ତୁ।"
        )
    ),
    LocalizedTutorialStep(
        emoji = "🎙️",
        color = Color(0xFF8B5CF6),
        titles = mapOf(
            "en" to "Voice AI in 11 Indian Languages",
            "hi" to "11 भारतीय भाषाओं में बोलकर पूछें",
            "bn" to "১১টি ভারতীয় ভাষায় কথা বলে পরামর্শ",
            "mr" to "११ भारतीय भाषांमध्ये बोलून विचारा",
            "te" to "11 భారతీయ భాషల్లో మాట్లాడి అడగండి",
            "ta" to "11 இந்திய மொழிகளில் பேசி கேளுங்கள்",
            "kn" to "11 ಭಾರತೀಯ ಭಾಷೆಗಳಲ್ಲಿ ಮಾತನಾಡಿ ಕೇಳಿ",
            "ml" to "11 ഇന്ത്യൻ ഭാഷകളിൽ സംസാരിച്ച് ചോദിക്കാം",
            "gu" to "૧૧ ભારતીય ભાષાઓમાં બોલીને પૂછો",
            "pa" to "11 ਭਾਰਤੀ ਭਾਸ਼ਾਵਾਂ ਵਿੱਚ ਬੋਲ ਕੇ ਪੁੱਛੋ",
            "or" to "୧୧ଟି ଭାରତୀୟ ଭାଷାରେ କଥା ହୋଇ ପଚାରନ୍ତୁ"
        ),
        descriptions = mapOf(
            "en" to "Speak naturally in Hindi, Bengali, Marathi, Telugu, Tamil, Gujarati, Kannada, Punjabi, Malayalam, Odia, or English. The AI responds in your exact language with spoken voice.",
            "hi" to "अपनी मातृभाषा में बोलकर खेती से जुड़ा कोई भी सवाल पूछें। कृषि सेवक आपकी ही भाषा में तुरंत उत्तर और बोलकर सलाह देगा।",
            "bn" to "আপনার মাতৃভাষায় চাষের যেকোনো প্রশ্ন মুখে বলুন। কৃষি সেবক আপনার ভাষাতেই সঙ্গে সঙ্গে মুখে বলে পরামর্শ দেবে।",
            "mr" to "आपल्या भाषेत बोलून शेतीचा कोणताही प्रश्न विचारा. कृषी सेवक आपल्याच भाषेत बोलून उत्तर देईल.",
            "te" to "మీ స్వంత భాషలో మాట్లాడి వ్యవసాయ ప్రశ్నలు అడగండి. కృషి సేవక్ మీ భాషలోనే మాట్లాడి సమాధానం ఇస్తుంది.",
            "ta" to "உங்கள் தாய்மொழியில் பேசி விவசாய கேள்விகளைக் கேளுங்கள். கிருஷி சேவக் உங்கள் மொழியிலேயே குரல் மூலம் பதிலளிக்கும்.",
            "kn" to "ನಿಮ್ಮ ಭಾಷೆಯಲ್ಲಿ ಮಾತನಾಡಿ ಕೃಷಿ ಪ್ರಶ್ನೆಗಳನ್ನು ಕೇಳಿ. ಕೃಷಿ ಸೇವಕ್ ನಿಮ್ಮದೇ ಭಾಷೆಯಲ್ಲಿ ಧ್ವನಿ ಮೂಲಕ ಉತ್ತರಿಸುತ್ತದೆ.",
            "ml" to "നിങ്ങളുടെ മാതൃഭാഷയിൽ സംസാരിച്ച് സംശയങ്ങൾ ചോദിക്കുക. കൃഷി സേവക് വോയ്‌സ് വഴി മറുപടി നൽകും.",
            "gu" to "તમારી માતૃભાષામાં બોલીને ખેતીના પ્રશ્નો પૂછો. કૃષિ સેવક તમારી ભાષામાં જ બોલીને જવાબ આપશે.",
            "pa" to "ਆਪਣੀ ਮਾਂ-ਬੋਲੀ ਵਿੱਚ ਖੇਤੀ ਦੇ ਸਵਾਲ ਪੁੱਛੋ। ਕ੍ਰਿਸ਼ੀ ਸੇਵਕ ਤੁਹਾਡੀ ਭਾਸ਼ਾ ਵਿੱਚ ਹੀ ਬੋਲ ਕੇ ਜਵਾਬ ਦੇਵੇਗਾ।",
            "or" to "ଆପଣଙ୍କ ମାତୃଭାଷାରେ କଥା ହୋଇ ଚାଷ ବିଷୟରେ ପଚାରନ୍ତୁ। କୃଷି ସେବକ ଆପଣଙ୍କ ଭାଷାରେ ହିଁ ଉତ୍ତର ଦେବ।"
        ),
        tips = mapOf(
            "en" to "Tip: Tap the Mic button at the bottom of the dashboard to start talking.",
            "hi" to "सुझाव: बात करने के लिए डैशबोर्ड के नीचे माइक बटन दबाएं।",
            "bn" to "পরামর্শ: কথা বলতে ড্যাশবোর্ডের নিচের মাইক বোতাম টিপুন।",
            "mr" to "टीप: बोलण्यासाठी डॅशबोर्डवरील माइक बटण दाबा.",
            "te" to "చిట్కా: మాట్లాడటానికి డ్యాష్‌బోర్డ్ క్రింద ఉన్న మైక్ బటన్‌ను నొక్కండి.",
            "ta" to "குறிப்பு: பேசத் தொடங்க திரையின் கீழே உள்ள மைக் பொத்தானைத் தட்டவும்.",
            "kn" to "ಸಲಹೆ: ಮಾತನಾಡಲು ಡ್ಯಾಶ್‌ಬೋರ್ಡ್ ಕೆಳಗಿರುವ ಮೈಕ್ ಬಟನ್ ಒತ್ತಿ.",
            "ml" to "സൂചന: സംസാരിക്കാൻ താഴെയുള്ള മൈക്ക് ബട്ടൺ അമർത്തുക.",
            "gu" to "સૂચન: વાત કરવા માટે ડેશબોર્ડ પર માઇક બટન દબાવો.",
            "pa" to "ਸੁਝਾਅ: ਗੱਲ ਕਰਨ ਲਈ ਡੈਸ਼ਬੋਰਡ 'ਤੇ ਮਾਈਕ ਬਟਨ ਦਬਾਓ।",
            "or" to "ଟିପ୍: କଥା ହେବା ପାଇଁ ଡ୍ୟାସବୋର୍ଡ ତଳେ ଥିବା ମାଇକ୍ ବଟନ୍ ଦବାନ୍ତୁ।"
        )
    )
)

@Composable
fun AppTutorialDialog(
    userLanguageCode: String,
    ttsManager: TtsManager,
    onDismiss: () -> Unit
) {
    var currentStepIndex by remember { mutableIntStateOf(0) }
    val step = localizedTutorialSteps[currentStepIndex]

    val title = step.titles[userLanguageCode] ?: step.titles["hi"] ?: step.titles["en"].orEmpty()
    val desc = step.descriptions[userLanguageCode] ?: step.descriptions["hi"] ?: step.descriptions["en"].orEmpty()
    val tip = step.tips[userLanguageCode] ?: step.tips["hi"] ?: step.tips["en"].orEmpty()

    val currentlySpeakingId by ttsManager.currentlySpeakingId.collectAsStateWithLifecycle()
    val isSpeaking = currentlySpeakingId == "tutorial_step_${currentStepIndex}"

    // Automatically speak the current tutorial step when dialog opens or when step changes
    LaunchedEffect(currentStepIndex) {
        delay(400)
        val textToSpeak = "$title. $desc. $tip"
        ttsManager.speak("tutorial_step_${currentStepIndex}", textToSpeak, userLanguageCode)
    }

    DisposableEffect(Unit) {
        onDispose {
            ttsManager.stop()
        }
    }

    Dialog(
        onDismissRequest = {
            ttsManager.stop()
            onDismiss()
        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .wrapContentHeight()
                .padding(vertical = 16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            border = androidx.compose.foundation.BorderStroke(1.5.dp, step.color.copy(alpha = 0.6f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header with Step Indicator & Close Button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = step.color.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = "Step ${currentStepIndex + 1} of ${localizedTutorialSteps.size}",
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = step.color
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (isSpeaking) {
                                    ttsManager.stop()
                                } else {
                                    val textToSpeak = "$title. $desc. $tip"
                                    ttsManager.speak("tutorial_step_${currentStepIndex}", textToSpeak, userLanguageCode)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isSpeaking) Icons.Default.Pause else Icons.AutoMirrored.Filled.VolumeUp,
                                contentDescription = "Listen",
                                tint = step.color
                            )
                        }

                        IconButton(
                            onClick = {
                                ttsManager.stop()
                                onDismiss()
                            }
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }

                // Step Emoji Badge
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .background(step.color.copy(alpha = 0.15f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = step.emoji, fontSize = 40.sp)
                }

                // Step Title
                Text(
                    text = title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                // Step Description
                Text(
                    text = desc,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )

                // Tip Box
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("💡", fontSize = 16.sp)
                        Text(
                            text = tip,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 16.sp
                        )
                    }
                }

                // Step Dots Progress Indicator
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    localizedTutorialSteps.indices.forEach { index ->
                        val isCurrent = index == currentStepIndex
                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(if (isCurrent) 24.dp else 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (isCurrent) step.color else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                        )
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                // Navigation Buttons (Back / Next / Finish)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (currentStepIndex > 0) {
                        OutlinedButton(
                            onClick = {
                                ttsManager.stop()
                                currentStepIndex--
                            },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Back")
                        }
                    } else {
                        TextButton(
                            onClick = {
                                ttsManager.stop()
                                onDismiss()
                            }
                        ) {
                            Text("Skip Tour", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }

                    Button(
                        onClick = {
                            ttsManager.stop()
                            if (currentStepIndex < localizedTutorialSteps.size - 1) {
                                currentStepIndex++
                            } else {
                                onDismiss()
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = step.color)
                    ) {
                        Text(if (currentStepIndex < localizedTutorialSteps.size - 1) "Next" else "Get Started 🎉", fontWeight = FontWeight.Bold)
                        if (currentStepIndex < localizedTutorialSteps.size - 1) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}
