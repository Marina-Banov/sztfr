package hr.sztfr.sztfr_android.ui.survey

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SurveyCollectionPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm){

    override fun getCount(): Int  = 2

    override fun getItem(position: Int): Fragment {
        val currentSurveysFragment = CurrentSurveysFragment()
        val surveyResultsFragment = SurveyResultsFragment()

        return if(position == 0){
            currentSurveysFragment
        }else{
            surveyResultsFragment
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if(position == 0){
            "ANKETE"
        }else{
            "REZULTATI ANKETA"
        }
    }

}