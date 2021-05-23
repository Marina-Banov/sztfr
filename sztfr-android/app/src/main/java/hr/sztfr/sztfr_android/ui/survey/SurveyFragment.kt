package hr.sztfr.sztfr_android.ui.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyBinding

class SurveyFragment : Fragment() {

    private lateinit var binding: FragmentSurveyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey, container, false)
        binding.lifecycleOwner = this
        binding.pager.adapter = SurveyPagerAdapter(childFragmentManager)
        return binding.root
    }

}