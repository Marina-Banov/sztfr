package hr.sztfr.sztfr_android.ui

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import hr.sztfr.sztfr_android.databinding.ActivityMainBinding
import hr.sztfr.sztfr_android.ui.login.LoginActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model
import hr.sztfr.sztfr_android.ui.home.HomeFragment
import hr.sztfr.sztfr_android.ui.info.InfoFragment
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.ui.survey.SurveyFragment
import hr.sztfr.sztfr_android.ui.favorites.FavoritesFragment


class MainActivity : AppCompatActivity() {

    enum class MenuItem { HOME, FAVORITES, SURVEY, INFO }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.meowMenu.add(Model(MenuItem.HOME.ordinal, R.drawable.house))
        binding.meowMenu.add(Model(MenuItem.FAVORITES.ordinal, R.drawable.favorite))
        binding.meowMenu.add(Model(MenuItem.SURVEY.ordinal, R.drawable.bar_chart))
        binding.meowMenu.add(Model(MenuItem.INFO.ordinal, R.drawable.info))

        binding.meowMenu.show(MenuItem.HOME.ordinal)
        replaceFragment(HomeFragment.newInstance())

        binding.meowMenu.setOnClickMenuListener {
            when(it.id){
                MenuItem.HOME.ordinal -> replaceFragment(HomeFragment.newInstance())
                MenuItem.FAVORITES.ordinal -> replaceFragment(FavoritesFragment.newInstance())
                MenuItem.SURVEY.ordinal -> replaceFragment(SurveyFragment.newInstance())
                MenuItem.INFO.ordinal -> replaceFragment(InfoFragment.newInstance())
            }
        }
    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }
  
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}