package com.example.myclock

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var start: ImageButton
    lateinit var pause: ImageButton
    lateinit var reset: ImageButton
    lateinit var tvStopwatch: TextView

    private var milisecondTime: Long = 0
    private var startTime: Long = 0
    private var updateTime: Long = 0
    private var timeBuff: Long = 0
    private var seconds: Int = 0
    private var minutes: Int = 0
    private var millisecond: Int = 0

    lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvStopwatch = findViewById(R.id.tv_stopwatch)

        start = findViewById(R.id.actionStart)
        pause = findViewById(R.id.actionPause)
        reset = findViewById(R.id.actionReset)
        handler = Handler()

        start.setOnClickListener {
            startTime = SystemClock.uptimeMillis()
            handler.postDelayed(runnable, 0)
            pause.isEnabled = true
            start.isEnabled = false
        }

        pause.setOnClickListener {
            handler.removeCallbacks(runnable)
            timeBuff += milisecondTime
            reset.isEnabled = true
            start.isEnabled = true
        }

        reset.setOnClickListener {
            milisecondTime = 0L
            startTime = 0L
            timeBuff = 0L
            updateTime = 0L
            millisecond = 0
            minutes = 0
            tvStopwatch.text = "00:00:00"
            start.isEnabled = true

        }
        runnable = object : Runnable {
            override fun run() {

                milisecondTime = SystemClock.uptimeMillis() - startTime

                updateTime = timeBuff + milisecondTime

                seconds = (updateTime / 1000).toInt()

                minutes = seconds / 60

                seconds = seconds % 60

                millisecond = (updateTime % 1000).toInt()

                tvStopwatch.text = String.format(
                        "%02d:%02d:%03d",
                        minutes,
                        seconds,
                        millisecond
                )

                handler.postDelayed(runnable, 0)
            }
        }
    }
}