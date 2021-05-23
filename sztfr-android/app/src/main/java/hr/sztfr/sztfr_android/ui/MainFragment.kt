package hr.sztfr.sztfr_android.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentMainBinding
import java.util.Stack


class MainFragment : Fragment() {

    companion object {
        const val HOME = 0
        const val FAVORITES = 1
        const val SURVEY = 2
        const val INFO = 3
    }

    private lateinit var binding: FragmentMainBinding
    private val backStack: Stack<Int> = Stack()

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = this

        binding.fragmentContainer.adapter = PagerAdapter(childFragmentManager)
        binding.fragmentContainer.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                addToStack(position)
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { removeFromStack(this) }
            }
        )

        binding.meowMenu.apply {
            add(MeowBottomNavigation.Model(HOME, R.drawable.house))
            add(MeowBottomNavigation.Model(FAVORITES, R.drawable.favorite))
            add(MeowBottomNavigation.Model(SURVEY, R.drawable.bar_chart))
            add(MeowBottomNavigation.Model(INFO, R.drawable.info))
            setOnClickMenuListener { binding.fragmentContainer.currentItem = it.id }
        }

        binding.fragmentContainer.currentItem = HOME
        addToStack(HOME)
        return binding.root
    }

     private fun addToStack(position: Int) {
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

    private fun removeFromStack(callback: OnBackPressedCallback) {
        if (backStack.size <= 1) {
            callback.isEnabled = false
            requireActivity().onBackPressed()
        } else {
            backStack.pop()
            binding.fragmentContainer.currentItem = backStack.lastElement()
        }
    }
}