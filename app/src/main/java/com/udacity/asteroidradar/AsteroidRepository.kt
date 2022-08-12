package com.udacity.asteroidradar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AsteroidRepository (private val database: AsteroidDatabase) {


    val date: Date = Calendar.getInstance().getTime()
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd hh:mm:ss")
    val strDate: String = dateFormat.format(date)

    var asteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.dao.getAllAsteroid(strDate)) {
            it.asDomainModel()
        }


    suspend fun refreshList() {

        withContext(Dispatchers.IO) {

            val asteroid = parseAsteroidsJsonResult(
                JSONObject(
                    AsteroidApi.retrofitService.getAsteroids(
                        "",
                        "",
                        "api-key"
                    )
                )
            )
             Log.d("crortine1",asteroid.asDatabaseModel().toString())
            database.dao.insert(AsteroidForDatabase(123, "","2022-7-28",0.0,0.0,0.0,0.0,false))
                database.dao.insertAll(asteroid.asDatabaseModel())

            asteroids= Transformations.map(database.dao.getAllAsteroid(strDate)) {
                it.asDomainModel()
            }
        }
    }

    suspend fun refreshPhoto(): PictureOfDay? {
        return AsteroidApi.retrofitService.getPhotoOfDay("pkDohOYTGiHux3Pv7b8f74I1cqhoEZssH671eerI")

    }
}