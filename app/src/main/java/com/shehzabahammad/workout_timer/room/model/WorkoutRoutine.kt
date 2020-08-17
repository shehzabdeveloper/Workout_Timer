package com.shehzabahammad.workout_timer.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workoutRoutine")
data class WorkoutRoutine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "planeName") val planeName: String,
    @ColumnInfo(name = "routine") val routine: String
)