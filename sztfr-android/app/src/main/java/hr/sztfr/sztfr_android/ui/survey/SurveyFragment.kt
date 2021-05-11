package hr.sztfr.sztfr_android.ui.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyBinding

class SurveyFragment : Fragment() {

    private lateinit var surveyCollectionPagerAdapter: SurveyCollectionPagerAdapter
    private lateinit var viewPager: ViewPager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentSurveyBinding>(inflater, R.layout.fragment_survey, container, false)
        viewPager = binding.pager
        surveyCollectionPagerAdapter = SurveyCollectionPagerAdapter(childFragmentManager)
        viewPager.adapter = surveyCollectionPagerAdapter
        return binding.root

    }

}