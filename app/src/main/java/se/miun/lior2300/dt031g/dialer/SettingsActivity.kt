package se.miun.lior2300.dt031g.dialer

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import se.miun.lior2300.dt031g.dialer.data.viewmodel.CallViewModel

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        private lateinit var callViewModel: CallViewModel

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)

            callViewModel = ViewModelProvider(this)[CallViewModel::class.java]

            val deletePreference = findPreference<androidx.preference.Preference>(getString(R.string.delete_numbers_key))

            deletePreference?.setOnPreferenceClickListener {
                callViewModel.deleteAllCalls()
                true
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }


    //instead of static
    companion object {
        fun shouldStoreNumbers(context: Context): Boolean {
            val preference = PreferenceManager.getDefaultSharedPreferences(context)
            return preference.getBoolean(context.getString(R.string.store_numbers_key), true)
        }
    }

}