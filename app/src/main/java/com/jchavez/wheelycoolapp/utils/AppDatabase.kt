package com.jchavez.wheelycoolapp.utils

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jchavez.wheelycoolapp.models.WheelOption
import com.jchavez.wheelycoolapp.models.WheelOptionDao

@Database(entities = [WheelOption::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wheelOptionDao(): WheelOptionDao
}
