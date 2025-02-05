package com.example.currenthub

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.currenthub.databinding.ActivityMainBinding
import com.example.currenthub.network.WeatherApi
import com.example.currenthub.network.WeatherApiResponse
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private val handler = Handler(Looper.getMainLooper())
    var cityName: String? = null
    private val apiKey = "3a08afc0d8e09c12d18ad61080365958"
    private var datatoPass: WeatherApiResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            handlePermissionsResult(permissions)
        }

        handler.post(updateWeather)
    }

    private val updateWeather = object : Runnable {
        override fun run() {
            requestWeatherUpdate()
            handler.postDelayed(this, 10 * 60 * 1000)
        }
    }

    private fun handlePermissionsResult(permissions: Map<String, Boolean>) {
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            if (!isLocationEnabled()) {
                Toast.makeText(this, "Location is disabled", Toast.LENGTH_SHORT).show()
                createLocationRequest()
            } else {
                retrieveLocation()
            }
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return LocationManagerCompat.isLocationEnabled(locationManager)
    }

    private fun requestWeatherUpdate() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun retrieveLocation() {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_BALANCED_POWER_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            if (location != null) {
                updateUIWithLocation(location)
            } else {
                binding.textView.text = "Unable to retrieve location"
            }
        }.addOnFailureListener {
            binding.textView.text = "Failed to get location: ${it.message}"
        }
    }

    private fun createLocationRequest() {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 10000
        ).setMinUpdateIntervalMillis(5000).build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)

        client.checkLocationSettings(builder.build())
            .addOnFailureListener { e ->
                if (e is ResolvableApiException) {
                    try {
                        e.startResolutionForResult(this, 100)
                    } catch (sendEx: Exception) {
                        sendEx.printStackTrace()
                    }
                }
            }
    }

    private fun updateUIWithLocation(location: Location) {
        lifecycleScope.launch {
            val weatherInfo = withContext(Dispatchers.IO) {
                getWeatherUpdate(location.latitude, location.longitude)
            }
            binding.textView.text = cityName ?: "Location not available"
            binding.textView2.text = weatherInfo
        }
    }

    private fun kelvinToCelsius(kelvin: Double): String {
        return String.format("%.1f", kelvin - 273.15)
    }

    private suspend fun getWeatherUpdate(latitude: Double, longitude: Double): String {
        return try {
            val weatherResponse = WeatherApi.weatherretrofitService.getWeather(
                latitude.toString(),
                longitude.toString(),
                apiKey
            ).await()

            datatoPass = weatherResponse
            cityName = weatherResponse.name
            binding.dataBinding = weatherResponse
            val temperature = kelvinToCelsius(weatherResponse.main.temp)
            "$temperature°C"
        } catch (e: Exception) {
            Log.e("WeatherError", "Error fetching weather: ${e.message}")
            "Error: ${e.message}"
        }
    }
}
