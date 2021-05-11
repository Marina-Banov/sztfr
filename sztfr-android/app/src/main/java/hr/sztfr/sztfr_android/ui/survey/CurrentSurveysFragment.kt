package hr.sztfr.sztfr_android.ui.survey


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.databinding.FragmentCurrentSurveysBinding
import hr.sztfr.sztfr_android.ui.GlideApp

class CurrentSurveysFragment : Fragment() {

    private lateinit var currentSurveysList: RecyclerView
    private lateinit var adapter: SurveyFirestoreRecyclerAdapter
    private lateinit var surveyViewModel: SurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentCurrentSurveysBinding>(inflater, R.layout.fragment_current_surveys, container, false)
        surveyViewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        currentSurveysList = binding.currentSurveysList
        adapter = SurveyFirestoreRecyclerAdapter(surveyViewModel.options)
        currentSurveysList.adapter = adapter

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private inner class SurveyViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view){
        internal fun setValues(surveyModel: SurveyModel){
            val surveyTitle = view.findViewById<TextView>(R.id.survey_title)
            val surveyDescription = view.findViewById<TextView>(R.id.survey_description)
            val surveyImage = view.findViewById<ImageView>(R.id.survey_image)
            surveyTitle.text = surveyModel.title
            surveyDescription.text = surveyModel.shortDescription
            val storageReference = surveyViewModel.getImageReference(surveyModel.image)
            GlideApp.with(this@CurrentSurveysFragment).load(storageReference).into(surveyImage)
        }

    }


    private inner class SurveyFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<SurveyModel>) : FirestoreRecyclerAdapter<SurveyModel, SurveyViewHolder>(options) {
        override fun onBindViewHolder(surveyViewHolder: SurveyViewHolder, position: Int, surveyModel: SurveyModel) {
            surveyViewHolder.setValues(surveyModel)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_card_survey_new, parent, false)
            return SurveyViewHolder(view)
        }

    }
}