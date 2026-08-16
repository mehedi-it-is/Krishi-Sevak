package com.krishisevak.app.data.engine

import com.krishisevak.app.utils.AppStrings

data class AgroWeatherInput(
    val temperature: Float = 29f,
    val humidity: Int = 78,
    val windSpeedKmH: Float = 12f,
    val rainProbability: Int = 60,
    val condition: String = "Partly Cloudy"
)

enum class SprayingStatus(val label: String, val badgeColorHex: Long, val icon: String) {
    SAFE("SAFE TO SPRAY", 0xFF16A34A, "✅"),
    CAUTION("USE CAUTION", 0xFFD97706, "⚠️"),
    PROHIBITED("DO NOT SPRAY", 0xFFDC2626, "⛔")
}

enum class IrrigationStatus(val label: String, val badgeColorHex: Long, val icon: String) {
    HOLD("HOLD IRRIGATION", 0xFF0284C7, "🛑"),
    LIGHT("LIGHT IRRIGATION", 0xFF10B981, "💧"),
    FULL("FULL IRRIGATION", 0xFFD97706, "🌊")
}

data class AgroWeatherAdvisory(
    val sprayingStatus: SprayingStatus,
    val sprayingStatusLabel: String,
    val sprayingReason: String,
    val irrigationStatus: IrrigationStatus,
    val irrigationStatusLabel: String,
    val irrigationReason: String,
    val harvestAdvisory: String,
    val pestDiseaseThreat: String,
    val summaryAudioText: String
)

object AgroWeatherAdvisoryEngine {

    fun getSprayingStatusLabel(status: SprayingStatus, langCode: String): String {
        return when (status) {
            SprayingStatus.SAFE -> AppStrings.get("spray_safe", langCode)
            SprayingStatus.CAUTION -> AppStrings.get("spray_caution", langCode)
            SprayingStatus.PROHIBITED -> AppStrings.get("spray_prohibited", langCode)
        }
    }

    fun getIrrigationStatusLabel(status: IrrigationStatus, langCode: String): String {
        return when (status) {
            IrrigationStatus.HOLD -> AppStrings.get("irri_hold", langCode)
            IrrigationStatus.LIGHT -> AppStrings.get("irri_light", langCode)
            IrrigationStatus.FULL -> AppStrings.get("irri_full", langCode)
        }
    }

    fun generateAdvisory(input: AgroWeatherInput, langCode: String = "en"): AgroWeatherAdvisory {
        // 1. Pesticide / Foliar Spraying Window Evaluation
        val sprayingStatus: SprayingStatus
        val sprayingReason: String

        if (input.windSpeedKmH >= 18f) {
            sprayingStatus = SprayingStatus.PROHIBITED
            sprayingReason = AppStrings.get("agro_spray_wind_high", langCode)
                .replace("{WIND}", input.windSpeedKmH.toInt().toString())
        } else if (input.rainProbability >= 50 || input.condition.lowercase().contains("rain")) {
            sprayingStatus = SprayingStatus.PROHIBITED
            sprayingReason = AppStrings.get("agro_spray_rain_high", langCode)
                .replace("{RAIN}", input.rainProbability.toString())
        } else if (input.temperature >= 35f) {
            sprayingStatus = SprayingStatus.CAUTION
            sprayingReason = AppStrings.get("agro_spray_heat", langCode)
                .replace("{TEMP}", input.temperature.toInt().toString())
        } else if (input.windSpeedKmH >= 12f || input.rainProbability >= 30) {
            sprayingStatus = SprayingStatus.CAUTION
            sprayingReason = AppStrings.get("agro_spray_moderate", langCode)
        } else {
            sprayingStatus = SprayingStatus.SAFE
            sprayingReason = AppStrings.get("agro_spray_safe", langCode)
                .replace("{WIND}", input.windSpeedKmH.toInt().toString())
        }

        // 2. Irrigation Decision Evaluation
        val irrigationStatus: IrrigationStatus
        val irrigationReason: String

        if (input.rainProbability >= 55 || input.condition.lowercase().contains("rain")) {
            irrigationStatus = IrrigationStatus.HOLD
            irrigationReason = AppStrings.get("agro_irri_hold", langCode)
                .replace("{RAIN}", input.rainProbability.toString())
        } else if (input.temperature >= 34f && input.humidity <= 50) {
            irrigationStatus = IrrigationStatus.FULL
            irrigationReason = AppStrings.get("agro_irri_full", langCode)
                .replace("{TEMP}", input.temperature.toInt().toString())
        } else if (input.humidity >= 80) {
            irrigationStatus = IrrigationStatus.LIGHT
            irrigationReason = AppStrings.get("agro_irri_humid", langCode)
                .replace("{HUMIDITY}", input.humidity.toString())
        } else {
            irrigationStatus = IrrigationStatus.LIGHT
            irrigationReason = AppStrings.get("agro_irri_routine", langCode)
        }

        // 3. Harvesting & Produce Protection (generic, not crop-specific)
        val harvestAdvisory = if (input.rainProbability >= 40) {
            AppStrings.get("agro_harvest_warn", langCode)
        } else {
            AppStrings.get("agro_harvest_safe", langCode)
        }

        // 4. Pest & Disease Alert Triggered by Microclimate (generic, not crop-specific)
        val pestDiseaseThreat = if (input.humidity >= 78 && input.temperature >= 24f && input.temperature <= 32f) {
            AppStrings.get("agro_pest_fungal", langCode)
                .replace("{HUMIDITY}", input.humidity.toString())
        } else if (input.temperature >= 35f) {
            AppStrings.get("agro_pest_heat", langCode)
        } else if (input.temperature <= 10f) {
            AppStrings.get("agro_pest_frost", langCode)
        } else {
            AppStrings.get("agro_pest_normal", langCode)
        }

        val sprayLabel = getSprayingStatusLabel(sprayingStatus, langCode)
        val irriLabel = getIrrigationStatusLabel(irrigationStatus, langCode)

        val summaryAudioText = "${AppStrings.get("agro_summary_prefix", langCode)} $sprayLabel. $sprayingReason $irriLabel. $irrigationReason $harvestAdvisory $pestDiseaseThreat"

        return AgroWeatherAdvisory(
            sprayingStatus = sprayingStatus,
            sprayingStatusLabel = sprayLabel,
            sprayingReason = sprayingReason,
            irrigationStatus = irrigationStatus,
            irrigationStatusLabel = irriLabel,
            irrigationReason = irrigationReason,
            harvestAdvisory = harvestAdvisory,
            pestDiseaseThreat = pestDiseaseThreat,
            summaryAudioText = summaryAudioText
        )
    }
}
