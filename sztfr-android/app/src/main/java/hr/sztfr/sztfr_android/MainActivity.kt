package hr.sztfr.sztfr_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Transition
import androidx.fragment.app.Fragment
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class MainActivity : AppCompatActivity() {

    private lateinit var meow_nav: MeowBottomNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         meow_nav = findViewById(R.id.meow_menu)
         meow_nav.add(MeowBottomNavigation.Model(1, R.drawable.house))
         meow_nav.add(MeowBottomNavigation.Model(2, R.drawable.favorite))
         meow_nav.add(MeowBottomNavigation.Model(3, R.drawable.bar_chart))
         meow_nav.add(MeowBottomNavigation.Model(4, R.drawable.info))

        meow_nav.setOnClickMenuListener {
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

}