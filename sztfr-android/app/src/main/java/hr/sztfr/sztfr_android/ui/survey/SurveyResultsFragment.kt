package hr.sztfr.sztfr_android.ui.survey

import android.content.Context
import android.os.Bundle
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.databinding.FragmentSurveyResultsBinding
import hr.sztfr.sztfr_android.ui.GlideApp
import hr.sztfr.sztfr_android.ui.MainFragmentDirections

class SurveyResultsFragment : Fragment() {

    private lateinit var surveyResultsList: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: SurveyResultsFragment.SurveyResultsFirestoreRecyclerAdapter
    private lateinit var surveyViewModel: SurveyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentSurveyResultsBinding>(inflater, R.layout.fragment_survey_results, container, false)
        surveyViewModel = ViewModelProvider(this).get(SurveyViewModel::class.java)
        surveyResultsList = binding.surveyResultsList
        searchView = binding.surveyResultsSearch
        adapter = SurveyResultsFirestoreRecyclerAdapter(surveyViewModel.getPublishedSurveys())
        surveyResultsList.adapter = adapter

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

    private inner class SurveyResultsViewHolder internal constructor(private val view: View) : RecyclerView.ViewHolder(view){

        internal fun setValues(surveyModel: SurveyModel){
            val surveyResultsTitle = view.findViewById<TextView>(R.id.survey_results_title)
            val surveyResultsImage = view.findViewById<ImageView>(R.id.survey_results_img)
            val surveyResultsNavigationButton = view.findViewById<Button>(R.id.survey_results_navigation_button)
            val surveyResultsDescription = view.findViewById<TextView>(R.id.survey_results_description)

            surveyResultsNavigationButton.setOnClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSurveyResultsDetailsFragment(surveyModel))
            }
            surveyResultsTitle.text = surveyModel.title
            surveyResultsDescription.text = surveyModel.shortDescription
            val storageReference = surveyViewModel.getImageReference(surveyModel.image)
            GlideApp.with(this@SurveyResultsFragment).load(storageReference).into(surveyResultsImage)
        }



    }
    private inner class SurveyResultsFirestoreRecyclerAdapter internal constructor(options: FirestoreRecyclerOptions<SurveyModel>) : FirestoreRecyclerAdapter<SurveyModel, SurveyResultsFragment.SurveyResultsViewHolder>(options) {
        private var searchText: String = ""
        private val isSearch: Boolean
            get() = searchText.isNotBlank()

        private var searchItems: MutableList<SurveyModel> = mutableListOf()

        override fun onBindViewHolder(surveyResultsViewHolder: SurveyResultsFragment.SurveyResultsViewHolder, position: Int, surveyModel: SurveyModel) {
            surveyResultsViewHolder.setValues(surveyModel)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyResultsFragment.SurveyResultsViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_card_survey_results, parent, false)
            return SurveyResultsViewHolder(view)
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