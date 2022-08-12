package com.udacity.asteroidradar.main

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(val context: Context) : ViewModel() {

    val list: MutableLiveData<List<Asteroid>>
        get() = privateList
    private var privateList = MutableLiveData<List<Asteroid>>()

    val photo: LiveData<PictureOfDay>
        get() = privatePhoto
    private val privatePhoto = MutableLiveData<PictureOfDay>()
    //////////////////////////
    val date: Date = Calendar.getInstance().getTime()
    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    val strDate: String = dateFormat.format(date)

    private val database = AsteroidDatabase.getInstance(context)
    val repository=AsteroidRepository(database)

    init {
        viewModelScope.launch {

            Log.d("crortine3", strDate)

            privateList = Transformations.map(database.dao.getAllAsteroid(strDate)) {
                it.asDomainModel()
            } as MutableLiveData<List<Asteroid>>

            refreshList()
            try{
                privatePhoto.value=repository.refreshPhoto()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    fun getTodayList()
    {
        privateList = Transformations.map(database.dao.getTodayAsteroid(strDate)) {
            it.asDomainModel()
        } as MutableLiveData<List<Asteroid>>
    }

    fun getWeekList(date1:String,date2:String)
    {
        privateList = Transformations.map(database.dao.getWeekAsteroid(date1,date2)) {
            it.asDomainModel()
        } as MutableLiveData<List<Asteroid>>
    }
    fun getSavedList()
    {
        privateList = Transformations.map(database.dao.getAllAsteroid(strDate)) {
            it.asDomainModel()
        } as MutableLiveData<List<Asteroid>>
    }
    suspend fun refreshList() {
        viewModelScope.launch {
            try {
                repository.refreshList()
                Log.d("crortine3", database.dao.getAllAsteroid(strDate).value.toString())

                privateList = Transformations.map(database.dao.getAllAsteroid(strDate)) {
                    it.asDomainModel()
                } as MutableLiveData<List<Asteroid>>

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
        class Factory(val context: Context) : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return MainViewModel(context) as T
                }
                throw IllegalArgumentException("Unable to construct viewmodel")
            }

    }
}
