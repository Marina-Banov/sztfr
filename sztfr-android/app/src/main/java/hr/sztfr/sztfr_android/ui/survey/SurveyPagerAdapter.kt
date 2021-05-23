package hr.sztfr.sztfr_android.ui.survey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class SurveyPagerAdapter(fm: FragmentManager)
    : FragmentPagerAdapter(fm,  BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int  = 2

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            CurrentSurveysFragment()
        } else {
            SurveyResultsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return if (position == 0) {
            "NOVE ANKETE"
        } else {
            "REZULTATI ANKETA"
        }
    }
}