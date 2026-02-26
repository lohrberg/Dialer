package se.miun.lior2300.dt031g.dialer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import se.miun.lior2300.dt031g.dialer.data.local.entity.Call
import se.miun.lior2300.dt031g.dialer.data.viewmodel.CallViewModel
import se.miun.lior2300.dt031g.dialer.databinding.ActivityMapsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private var mapLoaded = false
    private lateinit var binding: ActivityMapsBinding

    private var callsWithLocation: List<Call> = emptyList()

    private lateinit var callViewModel: CallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        callViewModel = ViewModelProvider(this)[CallViewModel::class.java]

        callViewModel.callsWithLocation.observe(this) { calls ->
            callsWithLocation = calls

            if(mapLoaded) {
                addMarkersOnMap()
            }
        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val sweden = LatLngBounds(
            LatLng(55.001099,11.10694),
            LatLng(69.063141, 24.16707)
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(sweden, 100))
        mapLoaded = true
        addMarkersOnMap() //had issue if not in both
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun addMarkersOnMap() {
        mMap.clear()

        for (call in callsWithLocation) {

            val lat = call.latitude ?: continue
            val long = call.longitude ?: continue
            val position = LatLng(lat, long)

            val dateText = SimpleDateFormat("MMM d, yyyy hh:mm:ss a", Locale.getDefault()).format(Date(call.timestamp))

            mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(call.number)
                    .snippet(dateText)
            )
        }

    }
}