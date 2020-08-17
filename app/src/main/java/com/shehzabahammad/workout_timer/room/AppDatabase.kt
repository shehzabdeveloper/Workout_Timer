package com.shehzabahammad.workout_timer.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shehzabahammad.workout_timer.room.model.WorkoutRoutine

@Database(entities = [WorkoutRoutine::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun RoutineDao(): RoutineDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "workoutTimer_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}