package se.miun.lior2300.dt031g.dialer.data.viewmodel

import android.Manifest
import android.app.Application
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import se.miun.lior2300.dt031g.dialer.data.repository.LocationRepository

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = LocationRepository(application)

    val location: LiveData<Location> = repo.locationLiveData

    //Added to remove error/warning
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION])
    fun start() {
        repo.startLocationUpdates()
    }


    fun stop() {
        repo.stopLocationUpdates()
    }
}