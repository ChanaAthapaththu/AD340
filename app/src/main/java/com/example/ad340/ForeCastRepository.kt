package com.example.ad340

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForeCastRepository {
    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast:LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String){
        val randomValues = List(10){ Random.nextFloat().rem(100)*100}
        val forecastItems = randomValues.map { temp->
            DailyForecast(temp,getTempDescription(temp))
        }
        _weeklyForecast.setValue(forecastItems)
    }
    private fun getTempDescription(temp: Float):String{
        return when(temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "Any thing below 0 doesn't make sense "
            in 0f.rangeTo(32f)-> "Way too cold"
            in 32f.rangeTo(55f)-> "Way too cold"
            in 55f.rangeTo(65f)-> "Way too cold"
            in 65f.rangeTo(80f)-> "Way too cold"
            in 80f.rangeTo(90f)-> "Getting little cold"
            in 90f.rangeTo(100f)-> "Where is the AC"
            in 100f.rangeTo(Float.MAX_VALUE)-> "What is this Arizona?"
            else -> "Does not compute"

        }
    }

}