package hr.sztfr.sztfr_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.FirestoreUser
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            // TODO good reason to put splash screen
            FirestoreUser.value = UserRepository().get()
            setContentView(R.layout.activity_main)
        }
    }
  
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}