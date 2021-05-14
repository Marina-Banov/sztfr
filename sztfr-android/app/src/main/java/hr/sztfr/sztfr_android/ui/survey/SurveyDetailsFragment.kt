package hr.sztfr.sztfr_android.ui.survey

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBinderMapperImpl
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentEventDetailsBinding
import hr.sztfr.sztfr_android.databinding.FragmentSurveyDetailsBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections
import hr.sztfr.sztfr_android.ui.event_details.EventDetailsFragmentArgs
import hr.sztfr.sztfr_android.ui.event_details.EventDetailsViewModel
import hr.sztfr.sztfr_android.ui.event_details.EventDetailsViewModelFactory


class SurveyDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSurveyDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireActivity().application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_details, container, false)
        binding.lifecycleOwner = this

        val surveyModel = SurveyDetailsFragmentArgs.fromBundle(requireArguments()).surveyModel
        val viewModelFactory = SurveyDetailsViewModelFactory(surveyModel, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SurveyDetailsViewModel::class.java)
        binding.viewModel = viewModel

        binding.surveyDetailsGoBackBtn.setOnClickListener { requireActivity().onBackPressed() }

        binding.filloutSurveyButton.setOnClickListener {
            findNavController().navigate(SurveyDetailsFragmentDirections.
            actionSurveyDetailsFragmentToSurveyWebViewFragment(viewModel.surveyModel.value!!.googleFormsURL))
        }





        return binding.root
    }

}




