# KrishiSevak (कृषि सेवक)

KrishiSevak is an AI-powered agricultural assistant designed to empower farmers in India with real-time data, localized information, and expert advice. Built with Android and Jetpack Compose, the app bridges the information gap for farmers by providing a simple, voice-first, and multi-lingual interface.

## 🚀 Key Features

*   **Multi-Lingual Support:** Full UI and content support for 11 Indian regional languages (Hindi, English, Bengali, Kannada, Malayalam, Marathi, Odia, Punjabi, Tamil, Telugu, and Gujarati). Users can select their language directly during onboarding (Name and OTP flows).
*   **AI-Powered Chat:** Powered by the Gemini AI Engine to provide intelligent, contextual, and localized agricultural advice, crop disease diagnosis, and farming tips. Features language-specific localized fallbacks.
*   **Voice-First Interface (TTS):** Integrated Text-To-Speech (TTS) that reads out responses, weather forecasts, schemes, and mandi prices in the user's selected language, ensuring accessibility for users with varying literacy levels. Sequenced intelligently to prevent audio overlapping.
*   **Real-Time Mandi Prices:** Fetches live crop prices directly from the Indian Government's Agmarknet dataset (`data.gov.in`). Features an intelligent offline/mock fallback system with over 40 major crops if the API is unreachable, and dynamic UI filtering that only shows categories with available data.
*   **Government Schemes Portal:** A comprehensive list of 15+ Central and 15+ State-specific agricultural schemes (e.g., PM-Kisan, PMFBY). Schemes are dynamically filtered based on the user's GPS location and include direct, clickable intent links to official government application portals.
*   **Weather Forecasts:** Real-time location-based weather updates, including current conditions and actionable agro-weather advisories based on temperature, humidity, and wind speed.

## 🛠 Tech Stack

*   **Platform:** Android (Kotlin)
*   **UI Toolkit:** Jetpack Compose (Material Design 3)
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **Asynchronous Programming:** Kotlin Coroutines & StateFlow
*   **Network:** Retrofit2 & OkHttp3 (for Mandi API)
*   **Local Storage:** Jetpack DataStore (Preferences)
*   **Location:** FusedLocationProviderClient (Google Play Services)

## 📦 Project Structure

*   `app/src/main/java/com/krishisevak/app/ui/onboarding/`: Screens for Name, Location, and Language selection.
*   `app/src/main/java/com/krishisevak/app/ui/dashboard/`: Main hub containing tabs for Mandi Prices, Government Schemes, Weather, and Alerts.
*   `app/src/main/java/com/krishisevak/app/ui/chat/`: The conversational AI interface.
*   `app/src/main/java/com/krishisevak/app/data/remote/`: API interfaces and data models (MandiApi, WeatherApi).
*   `app/src/main/java/com/krishisevak/app/data/local/`: DataStore implementation for persisting user preferences.
*   `app/src/main/java/com/krishisevak/app/utils/`: Helper classes for TTS (`TtsManager`), Location mapping (`LocationHelper`), and AI integration (`LocalSmartAiEngine`).

## ⚙️ How to Build and Run

1.  Clone the repository to your local machine.
2.  Open the project in **Android Studio**.
3.  Sync the project with Gradle files.
4.  Run `./gradlew assembleDebug` in the terminal to build the debug APK.
5.  Click the **Run** button or use `./gradlew installDebug` to deploy the app to an emulator or a connected physical device.

## 🔑 Note on APIs
Currently, the app uses public endpoints and local mock fallbacks to demonstrate functionality. For full production deployment, replace the placeholder API keys in `DashboardViewModel.kt` with your own dedicated keys for Mandi, Weather, and Gemini services.

## 🛡 License
This project is for demonstration and development purposes.
