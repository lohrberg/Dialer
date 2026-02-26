package se.miun.lior2300.dt031g.dialer.data.repository

import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationRepository (context: Context){

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val _locationLiveData = MutableLiveData<Location>()
    val locationLiveData: LiveData<Location> get() = _locationLiveData

    private val locationRequest: LocationRequest =
        LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5_000L)
            .build()

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {

            result.lastLocation?.let { location ->
                _locationLiveData.postValue(location)
            }
        }
    }

    //To remove error/warning
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION])
    fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

}