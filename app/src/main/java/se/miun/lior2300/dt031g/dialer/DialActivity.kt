package se.miun.lior2300.dt031g.dialer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import android.location.Location
import se.miun.lior2300.dt031g.dialer.data.viewmodel.CallViewModel
import se.miun.lior2300.dt031g.dialer.data.viewmodel.LocationViewModel

class DialActivity : AppCompatActivity() {

    private var dialedNumber: String? = null
    private var lastLocation: Location? = null
    private lateinit var callViewModel: CallViewModel
    private lateinit var locationViewModel: LocationViewModel

    companion object {
        private const val REQ_CALL_PERMISSION = 1
        private const val REQ_LOCATION_PERMISSION = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        callViewModel = androidx.lifecycle.ViewModelProvider(this)[CallViewModel::class.java]
        locationViewModel = androidx.lifecycle.ViewModelProvider(this)[LocationViewModel::class.java]

        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION),REQ_LOCATION_PERMISSION)

        locationViewModel.location.observe(this) { location ->
            lastLocation = location
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dial_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    fun onCallReq(number: String) {
        dialedNumber = number


        if(SettingsActivity.shouldStoreNumbers(this)) {

            val lat = if(hasLocationPerms()) {
                lastLocation?.latitude
            }
            else {
                null
            }

            val long = if (hasLocationPerms()) {
                lastLocation?.longitude
            }
            else {
                null
            }

            callViewModel.insertCall(number, lat, long)
        }

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startCall(number)
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),REQ_CALL_PERMISSION )
        }
    }

    //https://stackoverflow.com/questions/4275678/how-to-make-a-phone-call-using-intent-in-android
    private fun startCall(number: String) {
        val callIntent = Intent(Intent.ACTION_CALL).apply {
            data = "tel:$number".toUri()
        }
        startActivity(callIntent)
    }

    private fun openDialer(number: String) {
        val dialIntent = Intent(Intent.ACTION_DIAL).apply {
            data = "tel:$number".toUri()
        }
        startActivity(dialIntent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQ_CALL_PERMISSION) {

            val result = grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
            val number = dialedNumber?: return

            if(result) {
                startCall(number)
            }
            else {
                openDialer(number)
            }
        }
    }

    private fun hasLocationPerms(): Boolean {

        return ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onResume() {
        super.onResume()
        if (hasLocationPerms()) {
            locationViewModel.start()
        }
    }

    override fun onPause() {
        super.onPause()
        locationViewModel.stop()
    }
}