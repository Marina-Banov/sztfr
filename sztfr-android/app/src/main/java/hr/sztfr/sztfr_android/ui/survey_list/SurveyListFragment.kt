package hr.sztfr.sztfr_android.ui.survey_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyListBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections
import hr.sztfr.sztfr_android.ui.survey.SurveyFragment
import hr.sztfr.sztfr_android.util.filterByStatus

class SurveyListFragment(private val isPublished: Boolean): Fragment() {
    private lateinit var binding: FragmentSurveyListBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_list, container, false)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this).get(SurveyListViewModel::class.java)
        binding.viewModel = viewModel
        (parentFragment as SurveyFragment).viewModel.displaySurveys.observe(
            viewLifecycleOwner,
            { viewModel.setSurveys(filterByStatus(it, isPublished)) }
        )

        binding.surveyRecyclerView.adapter = SurveyListAdapter(this) {
            if (it.published) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSurveyResultsDetailsFragment(it)
                )
            } else {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSurveyDetailsFragment(it)
                )
            }
        }

        return binding.root
    }

}