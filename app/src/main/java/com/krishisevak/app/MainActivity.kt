package com.krishisevak.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.krishisevak.app.data.local.datastore.DataStoreManager
import com.krishisevak.app.data.local.db.AppDatabase
import com.krishisevak.app.data.local.db.PlantScanDao
import com.krishisevak.app.data.remote.kindwise.KindwiseApi
import com.krishisevak.app.data.remote.sarvam.SarvamApi
import com.krishisevak.app.data.repository.ChatRepository
import com.krishisevak.app.data.remote.mandi.MandiApi
import com.krishisevak.app.ui.almanac.AlmanacScreen
import com.krishisevak.app.ui.almanac.AlmanacViewModel
import com.krishisevak.app.ui.chat.ChatScreen
import com.krishisevak.app.ui.chat.ChatViewModel
import com.krishisevak.app.ui.crop.CropRecommendScreen
import com.krishisevak.app.ui.crop.CropRecommendViewModel
import com.krishisevak.app.ui.dashboard.DashboardScreen
import com.krishisevak.app.ui.dashboard.DashboardViewModel
import com.krishisevak.app.ui.doctor.CropDoctorScreen
import com.krishisevak.app.ui.doctor.CropDoctorViewModel
import com.krishisevak.app.ui.insights.InsightsScreen
import com.krishisevak.app.ui.insights.InsightsViewModel
import com.krishisevak.app.ui.kvk.KvkScreen
import com.krishisevak.app.ui.learn.LearnScreen
import com.krishisevak.app.ui.learn.LearnViewModel
import com.krishisevak.app.ui.onboarding.OnboardingScreen
import com.krishisevak.app.ui.onboarding.OnboardingViewModel
import com.krishisevak.app.ui.schemes.SchemesScreen
import com.krishisevak.app.ui.schemes.SchemesViewModel
import com.krishisevak.app.ui.soil.SoilScreen
import com.krishisevak.app.ui.soil.SoilViewModel
import com.krishisevak.app.ui.theme.KrishiSevakTheme
import com.krishisevak.app.utils.LocationHelper
import com.krishisevak.app.utils.TtsManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {

    private lateinit var dataStoreManager: DataStoreManager
    private lateinit var repository: ChatRepository
    private lateinit var ttsManager: TtsManager
    private lateinit var locationHelper: LocationHelper
    private lateinit var plantScanDao: PlantScanDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dataStoreManager = DataStoreManager(applicationContext)
        ttsManager = TtsManager(applicationContext)
        locationHelper = LocationHelper(applicationContext)

        val db = AppDatabase.getDatabase(applicationContext)
        plantScanDao = db.plantScanDao()

        val kindwiseRetrofit = Retrofit.Builder()
            .baseUrl("https://crop.kindwise.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val kindwiseApi = kindwiseRetrofit.create(KindwiseApi::class.java)

        val sarvamRetrofit = Retrofit.Builder()
            .baseUrl("https://api.sarvam.ai/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val sarvamApi = sarvamRetrofit.create(SarvamApi::class.java)

        repository = ChatRepository(
            kindwiseApi = kindwiseApi,
            sarvamApi = sarvamApi,
            chatDao = db.chatDao()
        )

        val mandiRetrofit = Retrofit.Builder()
            .baseUrl("https://api.data.gov.in/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val mandiApi = mandiRetrofit.create(MandiApi::class.java)

        setContent {
            val isDarkMode by dataStoreManager.isDarkModeFlow.collectAsStateWithLifecycle(initialValue = true)

            KrishiSevakTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val userNameState by dataStoreManager.userNameFlow.collectAsStateWithLifecycle(initialValue = null)

                    when (val userName = userNameState) {
                        null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            }
                        }
                        "" -> {
                            MainAppNavigation(
                                startDestination = "onboarding",
                                dataStoreManager = dataStoreManager,
                                repository = repository,
                                ttsManager = ttsManager,
                                locationHelper = locationHelper,
                                mandiApi = mandiApi,
                                plantScanDao = plantScanDao,
                                kindwiseApi = kindwiseApi
                            )
                        }
                        else -> {
                            MainAppNavigation(
                                startDestination = "dashboard",
                                dataStoreManager = dataStoreManager,
                                repository = repository,
                                ttsManager = ttsManager,
                                locationHelper = locationHelper,
                                mandiApi = mandiApi,
                                plantScanDao = plantScanDao,
                                kindwiseApi = kindwiseApi
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainAppNavigation(
    startDestination: String,
    dataStoreManager: DataStoreManager,
    repository: ChatRepository,
    ttsManager: TtsManager,
    locationHelper: LocationHelper,
    mandiApi: MandiApi,
    plantScanDao: PlantScanDao,
    kindwiseApi: com.krishisevak.app.data.remote.kindwise.KindwiseApi
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("onboarding") {
            val onboardingVm = remember { OnboardingViewModel(dataStoreManager, ttsManager) }
            OnboardingScreen(
                viewModel = onboardingVm,
                onOnboardingComplete = {
                    navController.navigate("dashboard") {
                        popUpTo("onboarding") { inclusive = true }
                    }
                }
            )
        }

        composable("dashboard") {
            val dashboardVm = remember {
                DashboardViewModel(repository, dataStoreManager, locationHelper, ttsManager, mandiApi)
            }
            val schemesVm = remember { SchemesViewModel(dataStoreManager, ttsManager) }
            DashboardScreen(
                viewModel = dashboardVm,
                schemesViewModel = schemesVm,
                onNavigateToChat = { chatId, initialText, initialImageUri ->
                    var route = "chat"
                    val queryParams = mutableListOf<String>()
                    if (chatId != null) queryParams.add("chatId=${android.net.Uri.encode(chatId)}")
                    if (initialText != null && initialText.isNotBlank()) queryParams.add("initialText=${android.net.Uri.encode(initialText)}")
                    if (initialImageUri != null) queryParams.add("initialImageUri=${android.net.Uri.encode(initialImageUri)}")
                    
                    if (queryParams.isNotEmpty()) {
                        route += "?" + queryParams.joinToString("&")
                    }
                    navController.navigate(route)
                },
                onNavigateToSchemes = {
                    navController.navigate("schemes")
                },
                onNavigateToSoil = {
                    navController.navigate("soil")
                },
                onNavigateToCropRecommend = {
                    navController.navigate("crop_recommend")
                },
                onNavigateToCropDoctor = {
                    navController.navigate("crop_doctor")
                },
                onNavigateToAlmanac = {
                    navController.navigate("almanac")
                },
                onNavigateToInsights = {
                    navController.navigate("insights")
                },
                onNavigateToLearn = {
                    navController.navigate("learn")
                },
                onNavigateToKvk = {
                    navController.navigate("kvk")
                },
                onLogoutRestart = {
                    navController.navigate("onboarding") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = "chat?chatId={chatId}&initialText={initialText}&initialImageUri={initialImageUri}",
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("initialText") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                },
                navArgument("initialImageUri") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            val initialText = backStackEntry.arguments?.getString("initialText")
            val initialImageUri = backStackEntry.arguments?.getString("initialImageUri")
            
            val chatVm = remember(chatId) {
                ChatViewModel(repository, dataStoreManager, ttsManager, existingChatId = chatId)
            }
            ChatScreen(
                viewModel = chatVm,
                initialText = initialText,
                initialImageUri = initialImageUri,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("schemes") {
            val schemesVm = remember { SchemesViewModel(dataStoreManager, ttsManager) }
            SchemesScreen(
                viewModel = schemesVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("soil") {
            val soilVm = remember { SoilViewModel(dataStoreManager, ttsManager) }
            SoilScreen(
                viewModel = soilVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("crop_recommend") {
            val cropVm = remember { CropRecommendViewModel(dataStoreManager, ttsManager) }
            CropRecommendScreen(
                viewModel = cropVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("crop_doctor") {
            val doctorVm = remember { CropDoctorViewModel(plantScanDao, dataStoreManager, ttsManager, kindwiseApi) }
            CropDoctorScreen(
                viewModel = doctorVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("almanac") {
            val almanacVm = remember { AlmanacViewModel(dataStoreManager, ttsManager) }
            AlmanacScreen(
                viewModel = almanacVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("insights") {
            val insightsVm = remember { InsightsViewModel(plantScanDao, dataStoreManager, ttsManager) }
            InsightsScreen(
                viewModel = insightsVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("learn") {
            val learnVm = remember { LearnViewModel(dataStoreManager, ttsManager) }
            LearnScreen(
                viewModel = learnVm,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("kvk") {
            val langState by dataStoreManager.userLanguageCodeFlow.collectAsStateWithLifecycle(initialValue = "en")
            val cityState by dataStoreManager.locationCityFlow.collectAsStateWithLifecycle(initialValue = "Nashik")
            val districtState by dataStoreManager.locationDistrictFlow.collectAsStateWithLifecycle(initialValue = "Nashik")
            val stateNameState by dataStoreManager.locationStateFlow.collectAsStateWithLifecycle(initialValue = "Maharashtra")
            val latState by dataStoreManager.locationLatFlow.collectAsStateWithLifecycle(initialValue = 19.9975)
            val lonState by dataStoreManager.locationLonFlow.collectAsStateWithLifecycle(initialValue = 73.7898)

            val userLocation = com.krishisevak.app.utils.UserLocationDetails(
                latitude = latState,
                longitude = lonState,
                cityName = cityState,
                districtName = districtState,
                stateName = stateNameState
            )

            KvkScreen(
                userLanguageCode = langState,
                userLocation = userLocation,
                ttsManager = ttsManager,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
