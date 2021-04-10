package hr.sztfr.sztfr_android

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import hr.sztfr.sztfr_android.databinding.ActivityMainBinding
import hr.sztfr.sztfr_android.ui.login.LoginActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.meowMenu.add(MeowBottomNavigation.Model(1, R.drawable.house))
        binding.meowMenu.add(MeowBottomNavigation.Model(2, R.drawable.favorite))
        binding.meowMenu.add(MeowBottomNavigation.Model(3, R.drawable.bar_chart))
        binding.meowMenu.add(MeowBottomNavigation.Model(4, R.drawable.info))

        binding.meowMenu.setOnClickMenuListener {
            when(it.id){
                1 -> replaceFragment(HomeFragment.newInstance())
                2 -> replaceFragment(FavoritesFragment.newInstance())
                3 -> replaceFragment(SurveyFragment.newInstance())
                4 -> replaceFragment(InfoFragment.newInstance())
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