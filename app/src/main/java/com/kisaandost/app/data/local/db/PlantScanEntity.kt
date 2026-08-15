package com.kisaandost.app.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "plant_scans")
data class PlantScanEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val plantName: String,
    val disease: String,
    val isHealthy: Boolean,
    val probability: Float = 0.9f,
    val treatment: String = "",
    val imageUri: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)
