package com.example.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForeCastRepository {
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast:LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String){
        val randomValues = List(7){ Random.nextFloat().rem(100)*100}
        val forecastItems = randomValues.map {
            DailyForecast(it,getTempDescription(it))
        }
        _weeklyForecast.setValue(forecastItems)
    }
    private fun getTempDescription(temp: Float):String{
        return if(temp<75)"Its too cold" else "Its great"
    }

}