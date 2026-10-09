package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CalculationHistoryDao {
    @Query("SELECT * FROM calculation_history WHERE mode = :mode ORDER BY id DESC")
    fun getHistoryByMode(mode: String): Flow<List<CalculationHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: CalculationHistoryEntity): Long

    @Query("DELETE FROM calculation_history WHERE mode = :mode")
    suspend fun clearHistoryByMode(mode: String): Int

    @Query("DELETE FROM calculation_history")
    suspend fun clearAllHistory(): Int
}
