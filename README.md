# KrishiSevak (कृषि सेवक) 🌾🚜

**KrishiSevak** is an AI-powered, voice-first agricultural companion designed specifically for farmers across India. Built with **Modern Android (Jetpack Compose, Kotlin 2.0, Material 3, Clean MVVM)**, the app delivers localized crop intelligence, real-time APMC Mandi market rates, weather alerts, government scheme guidance, and precision smart farming tools across **11 major Indian languages**.

---

## 🌟 Key Features

### 1. 🤖 Conversational Agri-AI Chat
* **Indic-Trained Agricultural LLM**: Powered by **Sarvam AI (105B Indic Conversations)** for deep, contextual, and localized farming advisory.
* **Voice-First Interaction**: Integrated with **Sarvam Saaras v3** for speech-to-text and **Sarvam Bulbul v3** (voice: *Ritu*) with native Edge TTS & Android local TTS fallback.
* **Dialect Awareness**: Authentic Indian language support with Indian English (`en-IN`) and West Bengal Indian Bengali (`bn-IN`).

### 2. 📊 Live Mandi Market Prices (APMC Agmarknet)
* **Real-Time Commodity Rates**: Live integration with the Indian Government's Agmarknet dataset (`data.gov.in`).
* **Offline-First Resilience**: Intelligent fallback dataset covering **40+ major Indian crops** with regional dynamic filtering by commodity category.
* **Search & Favorites**: Instant filtering by state, market, and crop with persistent favorites.

### 3. 🏛️ Government Schemes Portal
* **31 National & State Schemes**: Complete repository of **15 Central/National Schemes** (PM-KISAN, PMFBY, KCC, Soil Health Card, PM-KUSUM, e-NAM, etc.) and **16 State-Specific Schemes** (Maharashtra, Punjab, Uttar Pradesh, West Bengal, Telangana, Tamil Nadu, Karnataka, Gujarat, Bihar, Odisha, Kerala, etc.).
* **100% Fully Localized**: Titles, descriptions, eligibility, benefits, assistance amounts, and step-by-step application instructions in all 11 languages.
* **One-Tap Official Applications**: Direct in-app links to official state and central application portals.

### 4. 🛠️ Smart Agri Tools
* **🩺 Multimodal Crop Doctor**: Visual crop disease and pest diagnosis using camera/gallery photo upload with Kindwise AI and localized weather-based preventive threat management.
* **🧪 Soil Advisory & Fertilizer Calculator**: 
  - Calculates exact bag requirements for Urea, DAP, MOP, and SSP tailored to specific crops, acreage, and soil types.
  - Comprehensive N-P-K soil health assessment, deficiency identification, and organic manure recommendations.
* **🌾 Smart Crop Recommendation Engine**: Ranked crop suitability based on crop season (Kharif, Rabi, Zaid), water availability, and soil type.
* **📅 12-Month Agro Almanac**: Month-by-month agricultural calendar with seasonal activities, weather tips, and planting schedules.
* **☎️ KVK & Kisan Helpline Directory**: GPS-based nearest Krishi Vigyan Kendra locator, one-tap calling to Kisan Call Center (`1800-180-1551`), and emergency helplines.

---

## 🌐 Supported Indian Languages

| Language | Code | Native Name | Script |
|---|---|---|---|
| **English (India)** | `en` | English (Indian) | Latin |
| **Hindi** | `hi` | हिन्दी | Devanagari |
| **Bengali (India)** | `bn` | বাংলা | Bengali |
| **Marathi** | `mr` | मराठी | Devanagari |
| **Telugu** | `te` | తెలుగు | Telugu |
| **Tamil** | `ta` | தமிழ் | Tamil |
| **Kannada** | `kn` | ಕನ್ನಡ | Kannada |
| **Gujarati** | `gu` | ગુજરાતી | Gujarati |
| **Punjabi** | `pa` | ਪੰਜਾਬੀ | Gurmukhi |
| **Malayalam** | `ml` | മലയാളം | Malayalam |
| **Odia** | `or` | ଓଡ଼ିଆ | Odia |

---

## 🏗️ Architecture & Tech Stack

```
com.krishisevak.app/
├── data/
│   ├── engine/       # Core offline domain engines (Schemes, Soil, Crop Doctor, Almanac)
│   ├── local/        # Room Database, DAOs, Entities, DataStore Preferences
│   └── remote/       # Retrofit Services, OkHttp, WebSocket Edge TTS, API DTOs
├── ui/
│   ├── almanac/      # Crop Almanac Screen
│   ├── chat/         # Conversational AI Screen & Chat Bubbles
│   ├── crop/         # Crop Recommendation Screen
│   ├── dashboard/    # Main Dashboard, Mandi, Weather & Quick Actions
│   ├── doctor/       # Crop Doctor Disease Diagnosis Screen
│   ├── kvk/          # Krishi Vigyan Kendra & Helpline Screen
│   ├── onboarding/   # Language, Name & Location Setup Screens
│   ├── schemes/      # Government Schemes Screen & Detail Cards
│   ├── soil/         # Soil Advisory & Fertilizer Calculator
│   └── theme/        # Material 3 Color Schemes, Typography & Shapes
└── utils/            # AppStrings, TtsManager, VoiceRecorder, LocationHelper
```

* **UI Framework**: Jetpack Compose (100% declarative UI with Material Design 3)
* **Architecture Pattern**: MVVM + Clean Architecture + Repository Pattern
* **Language & Compiler**: Kotlin `2.0.21` with Kotlin Symbol Processing (`KSP`)
* **Asynchronous Streams**: Kotlin Coroutines (`1.9.0`) & `StateFlow`
* **Local Persistence**: Jetpack Room `2.8.4` & Jetpack DataStore Preferences `1.1.1`
* **Networking**: Retrofit `2.9.0`, OkHttp `4.12.0`, Gson
* **Image Loading**: Coil Compose `2.7.0`
* **Audio & Speech**: Android Native `TextToSpeech` & Microsoft Edge TTS WebSocket Client

---

## 🚀 Getting Started (Cloning & Running)

### 📋 Prerequisites
1. **Java Development Kit (JDK)**: **JDK 17** or **JDK 21** installed and configured.
2. **Android Studio**: Android Studio Hedgehog / Iguana / Jellyfish / Koala / Ladybug or newer.
3. **Android SDK**: Android 15.0 SDK Platform (**API 35**) and Build Tools (`35.0.0`).

---

### 📥 1. Clone the Repository
```bash
git clone https://github.com/mehedi-it-is/Krishi-Sevak.git
cd Krishi-Sevak
```

### ⚙️ 2. Configure `local.properties`
Copy the template file to `local.properties`:
```bash
cp local.properties.example local.properties
```

Open `local.properties` and add your API keys:
```properties
## Optional API Keys (All features have offline fallbacks)
SARVAM_API_KEY="your_sarvam_api_key"
OPENWEATHER_API_KEY="your_openweather_api_key"
MANDI_API_KEY="your_data_gov_in_api_key"
KINDWISE_API_KEY="your_kindwise_api_key"
```

> **Note on `sdk.dir`**: If opening the project inside **Android Studio**, Android Studio automatically detects and configures `sdk.dir`. If building from terminal without Android Studio, specify your SDK path (e.g., `sdk.dir=/home/username/Android/Sdk`).

---

### 🔨 3. Build the Project

#### Make Gradle Wrapper Executable (Linux / macOS):
```bash
chmod +x gradlew
```

#### Run Unit Tests & Build Debug APK:
```bash
./gradlew testDebugUnitTest assembleDebug
```
The compiled APK will be located at:
```
app/build/outputs/apk/debug/app-debug.apk
```

---

### 📱 4. Deploy to Device / Emulator

#### Using Android Studio:
1. Open the project in Android Studio.
2. Allow Gradle sync to finish.
3. Select your connected device or emulator in the device dropdown.
4. Click **Run ▶** (`Shift + F10`).

#### Using Command Line (ADB):
```bash
./gradlew installDebug
```
Or stream-install via `adb`:
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.krishisevak.app/.MainActivity
```

---

## 📱 Device Compatibility & Limitations

### ✅ Supported Devices
* **Android OS Range**: **Android 7.0 (Nougat / API 24)** up to **Android 15 (API 35)**.
* **Brands & Form Factors**: All Android smartphones, phablets, and tablets (Samsung, Motorola, Xiaomi/Redmi/Poco, Realme, Vivo, Oppo, OnePlus, Google Pixel, Nothing, Tecno, Infinix, JioPhone Next / Pragati OS, etc.).
* **Coverage**: Compatible with **over 96.5% of all active Android devices globally** (>98% in India).

### ❌ Incompatible Devices (Will NOT Work)
* **Legacy Android Devices**: Android 6.0 (Marshmallow / API 23) or older (`minSdk = 24` requirement; installation will fail with `INSTALL_FAILED_OLDER_SDK`).
* **Non-Android Operating Systems**: Apple iOS (iPhone/iPad), Windows Phone, KaiOS feature phones (JioPhone 1 & 2).
* **Desktops / Browsers**: Cannot run natively without an Android emulator (BlueStacks, Android Studio Emulator, or WSA).

### ⚠️ Special Notes & Partial Limitations
* **Devices without Google Play Services (GMS)**: On Huawei devices (post-2019) or de-Googled custom ROMs without microG, GPS auto-location detection will fallback to manual state/district selection.
* **Low-Memory Devices (< 1GB RAM)**: Phones with less than 1GB RAM on Android 7/8 may experience stutter when taking or loading high-resolution camera photos in the Crop Doctor diagnosis tool.

---

## 🛠️ Common Troubleshooting

| Issue | Cause | Solution |
|---|---|---|
| **`SDK location not found`** | `sdk.dir` missing in `local.properties` or `ANDROID_HOME` not exported. | Open the project in Android Studio to auto-generate `local.properties`, or set `export ANDROID_HOME=/path/to/Sdk`. |
| **`Unsupported class file major version`** | Gradle running on old Java version (Java 8 or 11). | Switch to **JDK 17 or JDK 21** in Android Studio: `Settings` $\to$ `Build, Execution, Deployment` $\to$ `Build Tools` $\to$ `Gradle` $\to$ `Gradle JDK`. |
| **`INSTALL_FAILED_USER_RESTRICTED`** | Phone security blocking USB install (common on Xiaomi/MIUI/Realme/Oppo). | On your phone, go to **Developer Options** and enable **"Install via USB"** and **"USB debugging (Security settings)"**. |
| **`Permission denied: ./gradlew`** | Gradle wrapper lost Unix execution bit. | Run `chmod +x gradlew`. |

---

## 📄 License
This project is developed for empowerment and demonstration purposes. Contributions, issues, and feature requests are welcome!
