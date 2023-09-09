package com.example.batteryinfo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.batteryinfo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var chargeStatus: TextView
    lateinit var batteryLevel: TextView
    lateinit var voltage: TextView
    lateinit var batteryHealth: TextView
    lateinit var temperature: TextView

    private lateinit var binding: ActivityMainBinding

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            if (Intent.ACTION_BATTERY_CHANGED == intent?.action) {

                chargeStatus.text =
                    when (intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
                        1 -> "Unknown"
                        2 -> "Charging"
                        3 -> "Discharging"
                        4 -> "Not charging"
                        5 -> "Full"
                        else -> "-1"
                    }

                batteryLevel.text =
                    intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1).toString()

                voltage.text =
                    ((intent.getIntExtra(
                        BatteryManager.EXTRA_VOLTAGE,
                        -1
                    )).toDouble() / 1000).toString()

                batteryHealth.text =
                    when (intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)) {
                        1 -> "Unknown"
                        2 -> "Good"
                        3 -> "Overheated"
                        4 -> "Dead"
                        5 -> "Overvoltage"
                        6 -> "Failed"
                        else -> "-1"
                    }

                temperature.text =
                    ((intent.getIntExtra(
                        BatteryManager.EXTRA_TEMPERATURE,
                        -1
                    )).toDouble() / 10).toString()

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        registerReceiver(receiver, filter)

        chargeStatus = binding.BatteryChargeStatusValueTextView
        batteryLevel = binding.BatteryLevelValueTextView
        voltage = binding.BatteryVoltageValueTextView
        batteryHealth = binding.BatteryHealthValueTextView
        temperature = binding.BatteryTemperatureValueTextView
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }
}