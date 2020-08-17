package com.shehzabahammad.workout_timer.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shehzabahammad.workout_timer.room.model.WorkoutRoutine

@Dao
interface RoutineDao {

    @Insert
    fun insertRoutine(routine: WorkoutRoutine)

    @Query("SELECT * FROM workoutRoutine")
    fun getAllRoutine(): List<WorkoutRoutine>

    @Query("SELECT * FROM workoutRoutine WHERE id=:id")
    fun getRoutine(id: Int): WorkoutRoutine

}