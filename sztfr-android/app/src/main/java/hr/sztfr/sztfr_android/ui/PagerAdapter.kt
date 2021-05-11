package hr.sztfr.sztfr_android.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import hr.sztfr.sztfr_android.ui.favorites.FavoritesFragment
import hr.sztfr.sztfr_android.ui.home.HomeFragment
import hr.sztfr.sztfr_android.ui.info.InfoFragment
import hr.sztfr.sztfr_android.ui.survey.SurveyFragment

class PagerAdapter(fragmentManager: FragmentManager)
    : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            MainFragment.FAVORITES -> FavoritesFragment()
            MainFragment.SURVEY -> SurveyFragment()
            MainFragment.INFO -> InfoFragment()
            else -> HomeFragment()
        }
    }
}