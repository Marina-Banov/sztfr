package hr.sztfr.sztfr_android.ui.survey_questions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyAnswersSubmittedBinding


class SurveyAnswersSubmittedFragment : Fragment() {

    private lateinit var binding: FragmentSurveyAnswersSubmittedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_answers_submitted, container, false)
        var surveyModel = SurveyAnswersSubmittedFragmentArgs.fromBundle(requireArguments()).surveyModel
        var surveyTitle = binding.surveyTitle
        surveyTitle.text = surveyTitle.text.toString() + " " + surveyModel.title

        binding.goBackButton.setOnClickListener {
            val fmManager: FragmentManager = requireActivity().supportFragmentManager
            fmManager.popBackStack()
            fmManager.popBackStack()
        }
        return binding.root
    }


}