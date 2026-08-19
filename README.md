# KrishiSevak (कृषि सेवक)

KrishiSevak is an AI-powered agricultural assistant designed to empower farmers in India with real-time data, localized information, and expert advice. Built with Android and Jetpack Compose, the app bridges the information gap for farmers by providing a simple, voice-first, and multi-lingual interface.

## 🚀 Key Features

*   **AI-Powered Chat:** Powered by the Sarvam AI (105B Conversations) engine to provide intelligent, contextual, and localized agricultural advice, crop disease diagnosis, and farming tips across all 11 Indic languages.
*   **Voice-First Interface (TTS & STT):** Integrated with Sarvam Saaras v3 for speech recognition and Sarvam Bulbul v3 (speaker Ritu) with offline Android native TTS fallback for responsive voice advisories.
*   **Real-Time Mandi Prices:** Fetches live crop prices directly from the Indian Government's Agmarknet dataset (`data.gov.in`). Features an intelligent offline/mock fallback system with over 40 major crops if the API is unreachable, and dynamic UI filtering that only shows categories with available data.
*   **Government Schemes Portal:** A comprehensive list of 15+ Central and 15+ State-specific agricultural schemes (e.g., PM-Kisan, PMFBY). Schemes are dynamically filtered based on the user's GPS location and include direct, clickable intent links to official government application portals.
*   **Weather Forecasts:** Real-time location-based weather updates, including current conditions and actionable agro-weather advisories based on temperature, humidity, and wind speed.

## 🛠 Tech Stack

*   **Platform:** Android (Kotlin)
*   **UI Toolkit:** Jetpack Compose (Material Design 3)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Asynchronous Programming:** Kotlin Coroutines & StateFlow
*   **Network:** Retrofit2 & OkHttp3 (for Sarvam AI, Kindwise, Weather, Mandi API)
*   **Local Storage:** Jetpack DataStore (Preferences) & Room Database
*   **Location:** FusedLocationProviderClient (Google Play Services)

## 📦 Project Structure

*   `app/src/main/java/com/krishisevak/app/ui/onboarding/`: Screens for Name, Location, and Language selection.
*   `app/src/main/java/com/krishisevak/app/ui/dashboard/`: Main hub containing tabs for Mandi Prices, Government Schemes, Weather, and Alerts.
*   `app/src/main/java/com/krishisevak/app/ui/chat/`: The conversational AI interface.
*   `app/src/main/java/com/krishisevak/app/data/remote/`: API interfaces and data models (SarvamApi, KindwiseApi, MandiApi, WeatherApi).
*   `app/src/main/java/com/krishisevak/app/data/local/`: Room database & DataStore implementation for persisting user preferences.
*   `app/src/main/java/com/krishisevak/app/utils/`: Helper classes for TTS (`TtsManager`), Audio (`VoiceRecorder`), Recommendations (`ChatRecommendations`), and strings (`AppStrings`).

## ⚙️ How to Build and Run

1.  Clone the repository to your local machine.
2.  Open the project in **Android Studio**.
3.  Sync the project with Gradle files.
4.  Run `./gradlew assembleDebug` in the terminal to build the debug APK.
5.  Click the **Run** button or use `./gradlew installDebug` to deploy the app to an emulator or a connected physical device.

## 🔑 Note on APIs
Provide your API keys in `local.properties` (e.g., `SARVAM_API_KEY`, `KINDWISE_API_KEY`, `OPENWEATHER_API_KEY`, `MANDI_API_KEY`). The app includes offline local fallbacks if APIs are unreachable.

## 🛡 License
This project is for demonstration and development purposes.
