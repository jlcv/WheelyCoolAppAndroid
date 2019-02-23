package com.jchavez.wheelycoolapp.models

import android.arch.persistence.room.*
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class WheelOption(@PrimaryKey @ColumnInfo(name = "name") val name: String): Parcelable

@Dao
interface WheelOptionDao {
    @Query("SELECT * FROM wheeloption")
    fun getAll(): List<WheelOption>

    @Insert
    fun insert(wheelOption: WheelOption)

    @Delete
    fun delete(user: WheelOption)
}

