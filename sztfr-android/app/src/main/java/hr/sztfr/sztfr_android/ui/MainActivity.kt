package hr.sztfr.sztfr_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model
import com.google.firebase.auth.FirebaseAuth
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.ActivityMainBinding
import hr.sztfr.sztfr_android.ui.favorites.FavoritesFragment
import hr.sztfr.sztfr_android.ui.home.HomeFragment
import hr.sztfr.sztfr_android.ui.info.InfoFragment
import hr.sztfr.sztfr_android.ui.login.LoginActivity
import hr.sztfr.sztfr_android.ui.survey.SurveyFragment


class MainActivity : AppCompatActivity() {

    companion object {
        private const val HOME = 1
        private const val FAVORITES = 2
        private const val SURVEY = 3
        private const val INFO = 4
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.meowMenu.apply {
            add(Model(HOME, R.drawable.house))
            add(Model(FAVORITES, R.drawable.favorite))
            add(Model(SURVEY, R.drawable.bar_chart))
            add(Model(INFO, R.drawable.info))

            setOnClickMenuListener { replaceFragment(
                when(it.id) {
                    FAVORITES -> FavoritesFragment()
                    SURVEY -> SurveyFragment()
                    INFO -> InfoFragment()
                    else -> HomeFragment()
                }
            )}
        }

        replaceFragment(HomeFragment())
        binding.meowMenu.show(HOME)
    }

    private fun replaceFragment(fragment: Fragment) {
        with(supportFragmentManager) {
            popBackStackImmediate(fragment::class.java.simpleName, POP_BACK_STACK_INCLUSIVE)
            val transaction = beginTransaction().replace(R.id.fragmentContainer, fragment)
            fragments.lastOrNull()?.let {
                if (fragment::class.java.simpleName != it::class.java.simpleName)
                    transaction.addToBackStack(it::class.java.simpleName)
            }
            transaction.commit()
        }
    }
  
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        with(supportFragmentManager) {
            val count = backStackEntryCount
            if (count == 0) {
                super.onBackPressed()
                return
            }

            popBackStackImmediate()
            binding.meowMenu.show(
                when (fragments.lastOrNull()) {
                    is FavoritesFragment -> FAVORITES
                    is SurveyFragment -> SURVEY
                    is InfoFragment -> INFO
                    else -> HOME
                }
            )
        }
    }
}