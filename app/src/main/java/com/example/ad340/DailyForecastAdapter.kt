package com.example.ad340

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.InvocationHandler

class DailyForeCastViewHolder(
    view: View,
    private val tempDisplaySettingManager: TempDisplaySettingManager

): RecyclerView.ViewHolder(view){
         private val tempText:TextView= view.findViewById(R.id.tempText)
        private val descriptionText:TextView= view.findViewById(R.id.descriptionText)

        fun bind(dailyForecast: DailyForecast){
            tempText.text= FormatTempDisplay(dailyForecast.temp,tempDisplaySettingManager.getTempDisplaySetting())
            descriptionText.text=dailyForecast.description
        }


    }

class DailyForecastAdapter(
    private val tempDisplaySettingManager: TempDisplaySettingManager,
    private  val clickHandler: (DailyForecast)->Unit

):androidx.recyclerview.widget.ListAdapter<DailyForecast,DailyForeCastViewHolder>(DIFF_CONFIG){

    companion object{
        val DIFF_CONFIG = object:DiffUtil.ItemCallback<DailyForecast>(){
            override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(
                oldItem: DailyForecast,
                newItem: DailyForecast
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForeCastViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.iteam_daily_forecast,parent,false)
        return  DailyForeCastViewHolder(itemView,tempDisplaySettingManager)
    }

    override fun onBindViewHolder(holder: DailyForeCastViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener{
            clickHandler(getItem(position))
        }
    }
}