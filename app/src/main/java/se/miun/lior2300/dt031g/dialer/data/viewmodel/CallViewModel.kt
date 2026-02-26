package se.miun.lior2300.dt031g.dialer.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import se.miun.lior2300.dt031g.dialer.data.local.entity.Call
import se.miun.lior2300.dt031g.dialer.data.repository.CallRepository

class CallViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = CallRepository(application)

    val allCalls: LiveData<List<Call>> = repo.allCalls


    //https://developer.android.com/kotlin/coroutines
    fun insertCall(number: String, lat: Double?, long: Double?) {
        val call = Call(
            number = number,
            timestamp = System.currentTimeMillis(),
            latitude = lat,
            longitude = long
        )

        viewModelScope.launch(Dispatchers.IO) {
            repo.insert(call)
        }
    }

    fun deleteAllCalls() {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAll()
        }
    }

    val callsWithLocation: LiveData<List<Call>> = repo.callsWithLocation
}