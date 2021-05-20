package hr.sztfr.sztfr_android.ui.survey

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.StorageReference
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentSurveyResultsDetailsBinding
import hr.sztfr.sztfr_android.data.GlideApp


class SurveyResultsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentSurveyResultsDetailsBinding
    private lateinit var galery : LinearLayout
    private lateinit var dialog: Dialog

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val application = requireActivity().application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey_results_details, container, false)
        binding.lifecycleOwner = this


        val surveyModel = SurveyResultsDetailsFragmentArgs.fromBundle(requireArguments()).surveyModel
        val viewModelFactory = SurveyDetailsViewModelFactory(surveyModel, application)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(SurveyDetailsViewModel::class.java)
        binding.viewModel = viewModel


        dialog = Dialog(this.requireContext())
        galery = binding.gallery

        viewModel.resultsImages.value?.forEach { ref ->
            Log.i("Survey", ref.toString())
            val imageView = ImageView(this.context)
            imageView.setPadding(20, 2, 20, 2)
            imageView.setScaleType(ScaleType.FIT_XY)
            GlideApp.with(this).load(ref).into(imageView)
            galery.addView(imageView)
            imageView.setOnClickListener {
                Toast.makeText(context, ref.toString(), Toast.LENGTH_SHORT).show()
                dialog.setContentView(R.layout.image_popup)
                var popup_image = dialog.findViewById<ImageView>(R.id.popup_image)
                dialog.show()
                GlideApp.with(popup_image).load(ref).into(popup_image)
            }

            imageView.requestLayout()
            imageView.layoutParams.height = 500
            imageView.layoutParams.width = 500


        }

        binding.surveyResultsDetailsGoBackBtn.setOnClickListener { requireActivity().onBackPressed() }



        return binding.root
    }


}