package hr.sztfr.sztfr_android.ui.survey_questions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyBinding
import hr.sztfr.sztfr_android.databinding.FragmentSurveyQuestionsBinding
import hr.sztfr.sztfr_android.ui.survey.SurveyPagerAdapter
import hr.sztfr.sztfr_android.ui.survey.SurveyViewModel

class SurveyQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentSurveyQuestionsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val application = requireActivity().application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_questions, container, false)
        binding.lifecycleOwner = this

        val surveyId =SurveyQuestionsFragmentArgs.fromBundle(requireArguments()).surveyId
        val viewModelFactory = SurveyQuestionsViewModelFactory(surveyId, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SurveyQuestionsViewModel::class.java)
        binding.viewModel = viewModel
        binding.surveyQuestionsRecyclerView.adapter = SurveyQuestionsAdapter(childFragmentManager)

        return binding.root
    }


}
