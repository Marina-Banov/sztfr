package hr.sztfr.sztfr_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.libraries.places.api.Places
import com.google.firebase.auth.FirebaseAuth
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.PlacesRepository
import hr.sztfr.sztfr_android.ui.login.LoginActivity


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext, getString(R.string.google_maps_key))
        PlacesRepository.client = Places.createClient(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        setContentView(R.layout.activity_main)
    }
  
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}