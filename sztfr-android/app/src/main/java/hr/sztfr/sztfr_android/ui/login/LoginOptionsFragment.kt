package hr.sztfr.sztfr_android.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentLoginOptionsBinding

class LoginOptionsFragment : Fragment() {

    private lateinit var binding: FragmentLoginOptionsBinding
    private lateinit var activity: LoginActivity

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity = getActivity() as LoginActivity
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_options, container, false)

        binding.emailSignInButton.setOnClickListener {
            val email = binding.email.text
            email?.let {
                binding.email.clearFocus()
                activity.firebaseAuthEmail(email.toString())
            }
        }

        binding.googleSignInButton.setOnClickListener { activity.firebaseAuthGoogle() }
        binding.anonymousSignIn.setOnClickListener { activity.firebaseAuthAnonymous() }

        return binding.root
    }
}