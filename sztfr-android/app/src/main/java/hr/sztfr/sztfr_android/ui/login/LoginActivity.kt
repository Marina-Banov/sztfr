package hr.sztfr.sztfr_android.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import hr.sztfr.sztfr_android.BuildConfig.APPLICATION_ID
import hr.sztfr.sztfr_android.BuildConfig.VERSION_NAME
import hr.sztfr.sztfr_android.MainActivity
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val onAuthComplete = OnCompleteListener<AuthResult> { task ->
        if (task.isSuccessful) {
            navigateToMainActivity()
        } else {
            showLoginFailed()
        }
    }

    companion object {
        private const val GOOGLE_SIGN_IN_REQ_CODE = 123
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        auth = Firebase.auth

        binding.emailSignInButton.setOnClickListener {
            val email = binding.email.text
            email?.let {
                binding.email.clearFocus()
                firebaseAuthSendEmail(email.toString())
            }
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.googleSignInButton.setOnClickListener {
            startActivityForResult(googleSignInClient.signInIntent, GOOGLE_SIGN_IN_REQ_CODE)
        }

        binding.anonymousSignIn.setOnClickListener {
            auth.signInAnonymously().addOnCompleteListener(this, onAuthComplete)
        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null) {
            navigateToMainActivity()
        } else {
            val emailLink = intent.data.toString()
            val email = sharedPreferences.getString("userEmail", "")
            email?.let {
                if (auth.isSignInWithEmailLink(emailLink)) {
                    auth.signInWithEmailLink(email, emailLink).addOnCompleteListener(this, onAuthComplete)
                }
            }
        }
    }

    private fun firebaseAuthSendEmail(email: String) {
        val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setHandleCodeInApp(true)
                .setUrl("https://sztfr.page.link")
                .setAndroidPackageName(APPLICATION_ID, true, VERSION_NAME)
                .build()

        // TODO If you want the dynamic link to redirect to a specific activity, you will need to configure an intent filter in your AndroidManifest.xml file. This can be done by either specifying your dynamic link domain or the email action handler in the intent filter. By default, the email action handler is hosted on a domain like the following example: PROJECT_ID.firebaseapp.com
        auth.sendSignInLinkToEmail(email, actionCodeSettings).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val editor: Editor = sharedPreferences.edit()
                editor.putString("userEmail", email)
                editor.apply()
                Toast.makeText(applicationContext, R.string.email_sent, Toast.LENGTH_LONG).show()
            } else {
                showLoginFailed()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN_REQ_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                auth.signInWithCredential(credential).addOnCompleteListener(this, onAuthComplete)
            } catch (e: ApiException) {
                showLoginFailed()
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoginFailed() {
        Toast.makeText(applicationContext, R.string.login_failed, Toast.LENGTH_SHORT).show()
    }
}