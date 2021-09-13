package com.example.ad340

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340.details.ForecastDetailsActivity
import java.util.*

class MainActivity : AppCompatActivity() {
        private val foreCastRepository= ForeCastRepository()
        private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    // region Setup Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tempDisplaySettingManager = TempDisplaySettingManager(this)

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

            val dailyForecastList: RecyclerView= findViewById(R.id.forecastList)
            dailyForecastList.layoutManager = LinearLayoutManager(this)
            val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){
            showForecastDetails(it)

            }
            dailyForecastList.adapter = dailyForecastAdapter

            val weeklyForecastObserver = Observer<List<DailyForecast>>{
                //update our list adapter
               dailyForecastAdapter.submitList(it)
            }
            foreCastRepository.weeklyForecast.observe(this,weeklyForecastObserver)

        }
    }

    private fun showForecastDetails(forecast:DailyForecast){
        val forecastDetailsIntent = Intent(this,ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp",forecast.temp)
        forecastDetailsIntent.putExtra("key_description",forecast.description)
        startActivity(forecastDetailsIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //Handle Item Selction
        return  when(item.itemId){
            R.id.tempDisplaySetting->{
                showDisplaySettingsDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private  fun showDisplaySettingsDialog(){
        val dialogBuilder= AlertDialog.Builder(this)
            .setTitle("Choose Display Units")
            .setMessage("Choose which temperature unit to use for temperature display")
            .setPositiveButton("F°"){_,_->
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)

            }
            .setNeutralButton("C°"){_,_->
                tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
            }

            .setOnDismissListener(){
                Toast.makeText(this,"Setting will show affect on app restart",Toast.LENGTH_SHORT).show()
            }
        dialogBuilder.show()
    }
}