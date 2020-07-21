package com.example.covidtracker.coroutine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.CovidData
import com.example.covidtracker.R
import java.text.NumberFormat

class RecyclerViewAdapter(private val covidData: ArrayList<CovidData>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val codivDate: TextView = itemView.findViewById(R.id.item_date)
        val covidDeath: TextView = itemView.findViewById(R.id.item_death)
        val codivDeath2: TextView = itemView.findViewById(R.id.item_death2)
        val covidPos: TextView = itemView.findViewById(R.id.item_positive)
        val codivPos2: TextView = itemView.findViewById(R.id.item_positive2)
        val covidRecov: TextView = itemView.findViewById(R.id.item_recovered)
    }

    // Create new ViewHolder when there is no ViewHolder available.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return covidData.size
    }

    // Bind data to the ViewHolder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val covid = covidData[position]

        val year = covid.date.toString().substring(0,4)
        val month = covid.date.toString().substring(4,6)
        val day = covid.date.toString().substring(6,8)
        val dateText = "$year/$month/$day"

        holder.codivDate.text = dateText
        holder.covidDeath.text = NumberFormat.getIntegerInstance().format(covid.death)
        holder.codivDeath2.text = NumberFormat.getIntegerInstance().format(covid.deathIncrease)
        holder.covidPos.text = NumberFormat.getIntegerInstance().format(covid.positive)
        holder.codivPos2.text = NumberFormat.getIntegerInstance().format(covid.positiveIncrease)
        holder.covidRecov.text = NumberFormat.getIntegerInstance().format(covid.recovered)
    }
}