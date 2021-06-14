package hr.sztfr.sztfr_android.ui.settings

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var sharedPreferences: SharedPreferences
    companion object {
        private const val NIGHT_MODE = "NIGHT_MODE"
        private const val RECEIVE_NOTIFICATIONS = "RECEIVE_NOTIFICATIONS"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.logout.setOnClickListener {
            (activity as SettingsActivity).signOut()
        }
        
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())

        binding.swDarkMode.isChecked = getDarkMode() == Configuration.UI_MODE_NIGHT_YES


        binding.swDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isShown) { //ako nema ovog nakon aktiviranja switcha kod se neprestano pokrece
                when (getDarkMode()) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        sharedPreferences.edit()?.putInt(NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO)?.apply()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        sharedPreferences.edit()?.putInt(NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES)?.apply()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                }
            }
        }

        binding.swEventNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isShown) { //ako nema ovog nakon aktiviranja switcha kod se neprestano pokrece
                val receiveNotification = sharedPreferences.getBoolean(RECEIVE_NOTIFICATIONS, true)
                sharedPreferences.edit().putBoolean(RECEIVE_NOTIFICATIONS, !receiveNotification).apply()
            }
        }

        return binding.root
    }

    private fun getDarkMode(): Int {
        return resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    }

}