package com.example.covidtracker.coroutine

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.CovidData
import com.example.covidtracker.MainActivity
import com.example.covidtracker.R
import com.example.covidtracker.coroutine.RetrofitObject.getApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HistoryActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    lateinit var covidHistoryList: ArrayList<CovidData>
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        job = Job()

        // Action bar - Back button enable
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        launch{
            val covidHistoryResponse = getApiService().getCovidHistory()
            covidHistoryList = covidHistoryResponse.body()!!
            Log.d("MYTAG", "History data: $covidHistoryList")

            // Recyclerview object
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)

            // Create adapter passing in the data set
            val adapter = RecyclerViewAdapter(covidHistoryList)

            // Attach the adapter to the recyclerview
            recyclerView.adapter = adapter

            // Set layout manager
            val layoutManager = LinearLayoutManager(this@HistoryActivity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            // Attach the layoutManager to the recyclerview
            recyclerView.layoutManager = layoutManager
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    /**
     * Action bar - Back button Implementation
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}