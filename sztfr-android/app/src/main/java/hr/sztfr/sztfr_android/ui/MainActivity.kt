package hr.sztfr.sztfr_android.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.etebarian.meowbottomnavigation.MeowBottomNavigation.Model
import com.google.firebase.auth.FirebaseAuth
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.ActivityMainBinding
import hr.sztfr.sztfr_android.ui.login.LoginActivity
import java.util.Stack


class MainActivity : AppCompatActivity() {

    companion object {
        const val HOME = 0
        const val FAVORITES = 1
        const val SURVEY = 2
        const val INFO = 3
    }

    private lateinit var binding: ActivityMainBinding
    private val backStack: Stack<Int> = Stack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.fragmentContainer.adapter = PagerAdapter(supportFragmentManager)
        binding.fragmentContainer.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                if (position == HOME) {
                    backStack.clear()
                    backStack.push(position)
                }
                if (position != backStack.lastElement()) {
                    if (backStack.indexOf(position) > -1) {
                        backStack.removeElement(position)
                    }
                    backStack.push(position)
                }
                binding.meowMenu.show(position)
            }
        })

        binding.meowMenu.apply {
            add(Model(HOME, R.drawable.house))
            add(Model(FAVORITES, R.drawable.favorite))
            add(Model(SURVEY, R.drawable.bar_chart))
            add(Model(INFO, R.drawable.info))
            setOnClickMenuListener { binding.fragmentContainer.currentItem = it.id }
        }

        binding.fragmentContainer.currentItem = HOME
        backStack.push(HOME)
        binding.meowMenu.show(HOME)
    }
  
    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (backStack.size <= 1) {
            super.onBackPressed()
            return
        }
        backStack.pop()
        binding.fragmentContainer.currentItem = backStack.lastElement()
    }
}