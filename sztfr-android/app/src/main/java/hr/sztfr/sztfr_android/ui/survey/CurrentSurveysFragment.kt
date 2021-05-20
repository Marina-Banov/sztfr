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
import android.widget.SearchView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.databinding.FragmentCurrentSurveysBinding
import hr.sztfr.sztfr_android.data.GlideApp
import hr.sztfr.sztfr_android.ui.MainFragmentDirections

class CurrentSurveysFragment : Fragment() {

    private lateinit var currentSurveysList: RecyclerView
    private lateinit var searchView: SearchView
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
        searchView = binding.currentSurveySearch
        adapter = SurveyFirestoreRecyclerAdapter(surveyViewModel.getUnpublishedSurveys())
        currentSurveysList.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                val query = searchView.query.toString()
                adapter.search(query)

                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val query = searchView.query.toString()
                searchView.hideKeyboard()
                adapter.search(query)
                return true
            }
        })


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

    private inner class SurveyViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view){
        internal fun setValues(surveyModel: SurveyModel){
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


    private inner class SurveyFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<SurveyModel>) : FirestoreRecyclerAdapter<SurveyModel, SurveyViewHolder>(options) {
        private var searchText: String = ""
        private val isSearch: Boolean
            get() = searchText.isNotBlank()

        private var searchItems: MutableList<SurveyModel> = mutableListOf()

        override fun onBindViewHolder(surveyViewHolder: SurveyViewHolder, position: Int, surveyModel: SurveyModel) {
            surveyViewHolder.setValues(surveyModel)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_card_survey_new, parent, false)
            return SurveyViewHolder(view)
        }

        override fun getItemCount(): Int {
            return if (isSearch){
                searchItems = mutableListOf()
                for (position in 0 until super.getItemCount()){
                    var item = super.getItem(position)
                    if (item.title?.toLowerCase()?.contains(searchText) == true){
                        searchItems.add(item)
                    }
                }
                searchItems.size
            }else{
                super.getItemCount()
            }
        }

        override fun getItem(position: Int): SurveyModel {
            return if (!isSearch){
                super.getItem(position)
            }else{
                searchItems[position]
            }

        }

        fun search(text: String){
            searchText = text.toLowerCase()
            notifyDataSetChanged()

        }

    }
}