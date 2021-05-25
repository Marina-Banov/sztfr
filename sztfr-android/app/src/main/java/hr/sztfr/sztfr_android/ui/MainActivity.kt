package hr.sztfr.sztfr_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.FirestoreUser
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.ui.login.LoginActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
        coroutineScope.launch {
            FirestoreUser.value = UserRepository().get(Firebase.auth.currentUser!!.uid)
        }
    }
  
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}