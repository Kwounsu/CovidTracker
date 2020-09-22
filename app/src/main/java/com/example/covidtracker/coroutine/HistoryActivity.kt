package com.example.covidtracker.coroutine

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.covidtracker.CovidData
import com.example.covidtracker.R
import com.example.covidtracker.coroutine.RetrofitObject.getApiService
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import lecho.lib.hellocharts.model.Axis
import lecho.lib.hellocharts.model.Line
import lecho.lib.hellocharts.model.LineChartData
import lecho.lib.hellocharts.model.PointValue
import java.text.DateFormatSymbols
import java.text.NumberFormat
import kotlin.coroutines.CoroutineContext

/**
 * This Activity contains a chart and recyclerview of COVID-19 data.
 * For the Chart, user can choose the range with three options: 1, 3 and 6 months.
 */
class HistoryActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job
    private lateinit var covidHistoryList: ArrayList<CovidData>
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        job = Job()

        // Action bar - Back button enable
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        launch {
            setItemsData()
            // Recyclerview object
            val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
            // Create adapter passing in the data set
            // Attach the adapter to the recyclerview
            recyclerView.adapter = RecyclerViewAdapter(covidHistoryList)
            // Set layout manager
            val layoutManager = LinearLayoutManager(this@HistoryActivity)
            layoutManager.orientation = LinearLayoutManager.VERTICAL
            // Attach the layoutManager to the recyclerview
            recyclerView.layoutManager = layoutManager

            // Swipe to refresh
            swipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this@HistoryActivity, R.color.colorPrimary))
            swipeRefreshLayout.setColorSchemeColors(Color.WHITE)
            swipeRefreshLayout.setOnRefreshListener {
                launch {
                    covidHistoryList.clear()
                    setItemsData()
                    recyclerView.adapter = RecyclerViewAdapter(covidHistoryList)
                    // Attach the layoutManager to the recyclerview
                    recyclerView.layoutManager = layoutManager
                }
                swipeRefreshLayout.isRefreshing = false
            }

            /**
             * CHART from here
             */
            var chartRange: Int = 30
            createChart(chartRange, covidHistoryList)
            // 0: One Month, 1: Three Month, 2: Six Month
            var rangeSelected = 0
            seekBar.max = 30

            oneMonth.setOnClickListener {
                if (rangeSelected != 0) {
                    chartRange = 30
                    oneMonth.setTextColor(Color.BLACK)
                    threeMonth.setTextColor(Color.GRAY)
                    sixMonth.setTextColor(Color.GRAY)
                    createChart(chartRange, covidHistoryList)
                    rangeSelected = 0
                    seekBar.max = 30
                }
            }
            threeMonth.setOnClickListener {
                if (rangeSelected != 1) {
                    chartRange = 90
                    oneMonth.setTextColor(Color.GRAY)
                    threeMonth.setTextColor(Color.BLACK)
                    sixMonth.setTextColor(Color.GRAY)
                    createChart(chartRange, covidHistoryList)
                    rangeSelected = 1
                    seekBar.max = 90
                }
            }
            sixMonth.setOnClickListener {
                if (rangeSelected != 2) {
                    chartRange = 180
                    oneMonth.setTextColor(Color.GRAY)
                    threeMonth.setTextColor(Color.GRAY)
                    sixMonth.setTextColor(Color.BLACK)
                    createChart(chartRange, covidHistoryList)
                    rangeSelected = 2
                    seekBar.max = 180
                }
            }
        }
    }

    suspend fun setItemsData() {
        val covidHistoryResponse = getApiService().getCovidHistory()
        covidHistoryList = covidHistoryResponse.body()!!
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

    private fun createChart(chartRange:Int, covidDataArray:ArrayList<CovidData>){
        val values: MutableList<PointValue> = ArrayList()
        for (i in 0..chartRange){
            val covid = covidDataArray[i]
            val posNum = NumberFormat.getIntegerInstance().format(covid.positiveIncrease)
            val month = covid.date.toString().substring(4,6).toInt()
            val monthText = DateFormatSymbols().months[month-1].toString()
            val day = covid.date.toString().substring(6,8)
            val date = "$monthText $day"

            if (i == 0) {
                val detail = "$date - $posNum"
                labelDetail.text = detail
            }

            values
                .add(PointValue((chartRange-i).toFloat(), covid.positiveIncrease.toFloat())
                    .setLabel("$date\n$posNum"))
        }
        val line = Line(values)
            .setColor(Color.LTGRAY)
            .setHasPoints(true)
            .setHasLabels(true)
            .setHasLabelsOnlyForSelected(true)
            .setPointRadius(1)
        val lines: MutableList<Line> = ArrayList()
        lines.add(line)
        val data = LineChartData(lines)
        chart.lineChartData = data
        chart.isZoomEnabled

        val axisX = Axis()
        axisX.setHasLines(true)

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val covid = covidDataArray[chartRange-progress]
                val posNum = NumberFormat.getIntegerInstance().format(covid.positiveIncrease)
                val month = covid.date.toString().substring(4,6).toInt()
                val monthText = DateFormatSymbols().months[month-1].toString()
                val day = covid.date.toString().substring(6,8)
                val date = "$monthText - $day"
                val detail = "$date $posNum"
                labelDetail.text = detail
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

}