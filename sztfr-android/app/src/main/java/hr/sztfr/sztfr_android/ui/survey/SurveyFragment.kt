package hr.sztfr.sztfr_android.ui.survey

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyBinding
import hr.sztfr.sztfr_android.ui.home.HomeViewModel

class SurveyFragment : Fragment() {

    private lateinit var binding: FragmentSurveyBinding
    lateinit var viewModel: SurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)

        binding.searchFilter.viewModel.selectedTags.observe(viewLifecycleOwner, {
            viewModel.updateSurveys(it)
        })

        binding.searchFilter.viewModel.searchQuery.observe(viewLifecycleOwner, {
            viewModel.updateSurveys(it)
        })

        binding.pager.adapter = SurveyPagerAdapter(childFragmentManager)
        return binding.root
    }

}