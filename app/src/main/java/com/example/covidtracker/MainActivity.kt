package com.example.covidtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.covidtracker.coroutine.HistoryActivity
import com.example.covidtracker.viewmodel.MainViewModel
import com.example.covidtracker.viewmodel.MainViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import lecho.lib.hellocharts.model.LineChartData
import java.text.NumberFormat
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: MainViewModelFactory

    private val viewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component.inject(this)

        viewModel.death.observe(this, Observer { death ->
            tv_death.text = NumberFormat.getIntegerInstance().format(death)
        })
        viewModel.deathIncrement.observe(this, Observer { deathIncrement ->
            tv_death2.text = NumberFormat.getIntegerInstance().format(deathIncrement)
        })
        viewModel.positive.observe(this, Observer { positive ->
            tv_positive.text = NumberFormat.getIntegerInstance().format(positive)
        })
        viewModel.positiveIncrement.observe(this, Observer { positiveIncrement ->
            tv_positive2.text = NumberFormat.getIntegerInstance().format(positiveIncrement)
        })
        viewModel.recovered.observe(this, Observer { recovered ->
            tv_recovered.text = NumberFormat.getIntegerInstance().format(recovered)
        })
        viewModel.date.observe(this, Observer { date ->
            val year = date.toString().substring(0,4)
            val month = date.toString().substring(4,6)
            val day = date.toString().substring(6,8)
            val dateText = "$year/$month/$day"
            tv_date.text = dateText
        })
    }

    override fun onStart() {
        super.onStart()
        viewModel.searchCovid()
    }

    /**
     * Action bar
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar items
        return when(item.itemId){
            R.id.action_history -> {
                val intent = Intent(this, HistoryActivity::class.java)
                startActivity(intent)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}