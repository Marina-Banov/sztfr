package hr.sztfr.sztfr_android.ui.settings

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
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

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.logout.setOnClickListener {
            (activity as SettingsActivity).signOut()
        }
        
        /* ovo ne radi ako povuces switch, radi samo na klik
        binding.swDarkMode.setOnClickListener {
            val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (isNightTheme) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }*/

        binding.swDarkMode.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isShown) { //ako nema ovog nakon aktiviranja switcha kod se neprestano pokrece
                val isNightTheme =
                    resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
                when (isNightTheme) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                            ?: return@setOnCheckedChangeListener
                        with(sharedPref.edit()) {
                            putString(getString(R.string.theme), "day")
                            apply()
                        }
                    }
                    Configuration.UI_MODE_NIGHT_NO -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                            ?: return@setOnCheckedChangeListener
                        with(sharedPref.edit()) {
                            putString(getString(R.string.theme), "night")
                            apply()
                        }
                    }
                }
            }
        }

        binding.swEventNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            if (buttonView.isShown) { //ako nema ovog nakon aktiviranja switcha kod se neprestano pokrece
                val isNightTheme = R.string.notifications
                if (isNightTheme.equals('Y')) {
                        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                            ?: return@setOnCheckedChangeListener
                        with(sharedPref.edit()) {
                            putString(getString(R.string.theme), "day")
                            apply()
                        }
                } else {
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                            ?: return@setOnCheckedChangeListener
                        with(sharedPref.edit()) {
                            putString(getString(R.string.theme), "night")
                            apply()
                        }
                }
            }
        }

        return binding.root
    }

}