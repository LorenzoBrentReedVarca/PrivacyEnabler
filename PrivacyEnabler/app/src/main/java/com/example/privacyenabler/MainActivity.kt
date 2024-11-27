package com.example.privacyenabler

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.pm.PackageManager

class MainActivity : AppCompatActivity() {

    private lateinit var wifiManager: WifiManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var locationManager: LocationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val btnToggleLocation: Button = findViewById(R.id.btnToggleLocation)
        val btnToggleWifi: Button = findViewById(R.id.btnToggleWifi)
        val btnToggleBluetooth: Button = findViewById(R.id.btnToggleBluetooth)
        val btnDisableAll: Button = findViewById(R.id.btnDisableAll)

        btnToggleLocation.setOnClickListener {
            toggleLocation()
        }
        btnToggleWifi.setOnClickListener {
            toggleWifi()
        }
        btnToggleBluetooth.setOnClickListener {
            toggleBluetooth()
        }
        btnDisableAll.setOnClickListener {
            disableAllPrivacyFeatures()
        }
    }

    private fun toggleLocation() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            val isLocationEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            if (isLocationEnabled) {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))  // Opens Location Settings
            } else {
                startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))  // Opens Location Settings
            }
        }
    }

    private fun toggleWifi() {
        if (wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = false  // Disable Wi-Fi
            Toast.makeText(this, "Wi-Fi Disabled", Toast.LENGTH_SHORT).show()
        } else {
            wifiManager.isWifiEnabled = true   // Enable Wi-Fi
            Toast.makeText(this, "Wi-Fi Enabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleBluetooth() {
        if (bluetoothAdapter.isEnabled) {
            bluetoothAdapter.disable()  // Disable Bluetooth
            Toast.makeText(this, "Bluetooth Disabled", Toast.LENGTH_SHORT).show()
        } else {
            bluetoothAdapter.enable()   // Enable Bluetooth
            Toast.makeText(this, "Bluetooth Enabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun disableAllPrivacyFeatures() {
        toggleLocation()
        toggleWifi()
        toggleBluetooth()
    }
}
