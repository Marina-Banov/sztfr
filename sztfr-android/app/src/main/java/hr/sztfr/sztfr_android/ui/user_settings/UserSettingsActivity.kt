package hr.sztfr.sztfr_android.ui.user_settings

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.ui.MainActivity
import hr.sztfr.sztfr_android.ui.login.LoginActivity

class UserSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        var btn_logout = findViewById<ImageButton>(R.id.btn_logout)
        btn_logout?.setOnClickListener {
            signOut()
        }
        
        var sw_dark_mode = findViewById<Switch>(R.id.sw_dark_mode)
        /*sw_dark_mode.setOnCheckedChangeListener { buttonView, isChecked -> true
            Toast.makeText(this, "klik", Toast.LENGTH_SHORT).show()
            Configuration.UI_MODE_NIGHT_NO
        }*/

        sw_dark_mode.setOnClickListener {
            val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (isNightTheme) {
                Configuration.UI_MODE_NIGHT_YES ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Configuration.UI_MODE_NIGHT_NO ->
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }



    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}