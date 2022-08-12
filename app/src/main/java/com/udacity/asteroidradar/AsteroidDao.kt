package com.udacity.asteroidradar

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroid: AsteroidForDatabase)

    @Query("select * from asteroid_table where closeApproachDate  >= :date order by closeApproachDate")
    fun getAllAsteroid(date:String):LiveData<List<AsteroidForDatabase>>

    @Query("select * from asteroid_table where closeApproachDate  between :date1 and :date2 order by closeApproachDate")
    fun getWeekAsteroid(date1:String,date2:String):LiveData<List<AsteroidForDatabase>>


    @Query("select * from asteroid_table where closeApproachDate = :date order by closeApproachDate")
    fun getTodayAsteroid(date:String):LiveData<List<AsteroidForDatabase>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(asteroid: List<AsteroidForDatabase>)

}

