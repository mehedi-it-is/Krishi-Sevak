package com.kisaandost.app.data.local.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantScanDao {

    @Query("SELECT * FROM plant_scans ORDER BY timestamp DESC")
    fun getAllScans(): Flow<List<PlantScanEntity>>

    @Query("SELECT * FROM plant_scans ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentScans(limit: Int): List<PlantScanEntity>

    @Query("SELECT COUNT(*) FROM plant_scans")
    fun getTotalScansCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM plant_scans WHERE isHealthy = 1")
    fun getHealthyScansCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM plant_scans WHERE isHealthy = 0")
    fun getDiseasedScansCount(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertScan(scan: PlantScanEntity)

    @Query("DELETE FROM plant_scans WHERE id = :scanId")
    fun deleteScan(scanId: String): Int

    @Query("DELETE FROM plant_scans")
    fun deleteAllScans(): Int
}
