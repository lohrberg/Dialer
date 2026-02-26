package se.miun.lior2300.dt031g.dialer

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var aboutPressedBefore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val dialBtn = findViewById<Button>(R.id.btnDial)
        dialBtn.setOnClickListener {
            startActivity(Intent(this, DialActivity::class.java))
        }

        val callListBtn = findViewById<Button>(R.id.btnCallList)
        callListBtn.setOnClickListener {
            startActivity(Intent(this, CallListActivity::class.java))
        }

        val settingsBtn = findViewById<Button>(R.id.btnSettings)
        settingsBtn.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        val mapBtn = findViewById<Button>(R.id.btnMap)
        mapBtn.setOnClickListener {
            startActivity(Intent(this, MapsActivity::class.java))
        }

        val aboutBtn = findViewById<Button>(R.id.btnAbout)

        aboutBtn.setOnClickListener {
            if(!aboutPressedBefore) {
                aboutPressedBefore = true
                val aboutTitle = getString(R.string.title_about)
                val msg = getString(R.string.info_about)
                AlertDialog.Builder(this)
                    .setTitle(aboutTitle)
                    .setMessage(msg)
                    .setPositiveButton("OK", null)
                    .show()
            }
            else {
                Toast.makeText(this, "User has already seen the about dialog", Toast.LENGTH_SHORT).show()
            }
        }

        if(!Util.defaultVoiceExist(this)) {
            Util.copyDefaultVoiceToInternalStorage(this)
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("aboutShown", aboutPressedBefore)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        aboutPressedBefore = savedInstanceState.getBoolean("aboutShown", false)
    }
}