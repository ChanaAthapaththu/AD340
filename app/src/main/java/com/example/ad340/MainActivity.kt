package com.example.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {
        private val foreCastRepository= ForeCastRepository()
    // region Setup Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipcodeEditText: EditText= findViewById(R.id.editTextTextZipcode)
        val submitButton: Button= findViewById(R.id.button)

        submitButton.setOnClickListener {
            val zipcode:String=zipcodeEditText.text.toString()
            if (zipcode.length!=5){
                Toast.makeText(this,R.string.zipcode_entry_error,Toast.LENGTH_SHORT).show()

            }else{

//                Toast.makeText(this,zipcode,Toast.LENGTH_SHORT).show()
                foreCastRepository.loadForecast(zipcode)
            }

            val forecastList: RecyclerView= findViewById(R.id.forecastList)
            forecastList.layoutManager = LinearLayoutManager(this)
            val dailyForecastAdapter = DailyForecastAdapter(){

                val msg=getString(R.string.forecast_clicked_format,it.temp,it.description)
                Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
            }
            forecastList.adapter = dailyForecastAdapter

            val weeklyForecastObserver = Observer<List<DailyForecast>>{
                //update our list adapter
               dailyForecastAdapter.submitList(it)
            }
            foreCastRepository.weeklyForecast.observe(this,weeklyForecastObserver)

        }
    }

}