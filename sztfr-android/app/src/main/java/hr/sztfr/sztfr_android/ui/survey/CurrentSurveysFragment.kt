package hr.sztfr.sztfr_android.ui.survey

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.GlideApp
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.databinding.FragmentCurrentSurveysBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections

class CurrentSurveysFragment : Fragment() {

    private lateinit var currentSurveysList: RecyclerView
    private lateinit var adapter: SurveyFirestoreRecyclerAdapter
    private lateinit var surveyViewModel: SurveyViewModelSub

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentCurrentSurveysBinding>(inflater, R.layout.fragment_current_surveys, container, false)
        surveyViewModel = ViewModelProvider(this).get(SurveyViewModelSub::class.java)
        currentSurveysList = binding.currentSurveysList
        adapter = SurveyFirestoreRecyclerAdapter(surveyViewModel.getUnpublishedSurveys())
        currentSurveysList.adapter = adapter

        (parentFragment as SurveyFragment).viewModel.displaySurveys.observe(
            viewLifecycleOwner,
            { }
        )
        return binding.root
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private inner class SurveyViewHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {
        fun setValues(surveyModel: SurveyModel){
            val surveyTitle = view.findViewById<TextView>(R.id.survey_title)
            val surveyDescription = view.findViewById<TextView>(R.id.survey_description)
            val surveyImage = view.findViewById<ImageView>(R.id.survey_image)
            val surveyNavigationButton = view.findViewById<Button>(R.id.survey_navigation_button)


            surveyNavigationButton.setOnClickListener {
                findNavController().navigate(
                        MainFragmentDirections.actionMainFragmentToSurveyDetailsFragment(surveyModel))
            }
            surveyTitle.text = surveyModel.title
            surveyDescription.text = surveyModel.shortDescription
            val storageReference = surveyViewModel.getImageReference(surveyModel.image)
            Log.i("SurveyViewHolder", storageReference.toString())
            GlideApp.with(this@CurrentSurveysFragment).load(storageReference).into(surveyImage)
        }
    }

    private inner class SurveyFirestoreRecyclerAdapter(options: FirestoreRecyclerOptions<SurveyModel>):
        FirestoreRecyclerAdapter<SurveyModel, SurveyViewHolder>(options) {
        override fun onBindViewHolder(surveyViewHolder: SurveyViewHolder, position: Int, surveyModel: SurveyModel) {
            surveyViewHolder.setValues(surveyModel)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_card_survey_new, parent, false)
            return SurveyViewHolder(view)
        }
    }
}