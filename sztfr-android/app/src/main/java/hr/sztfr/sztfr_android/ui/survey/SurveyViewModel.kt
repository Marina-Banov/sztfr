package hr.sztfr.sztfr_android.ui.survey

import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.StorageReference
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.ui.FirestoreRepository

class SurveyViewModel: ViewModel() {

    val TAG = "SURVEY_VIEW_MODEL"
    private var firestoreRepository = FirestoreRepository()
    var options : FirestoreRecyclerOptions<SurveyModel>
    private lateinit var storageReference : StorageReference


    init {
        var query = firestoreRepository.getSurveys()
        options = FirestoreRecyclerOptions.Builder<SurveyModel>()
            .setQuery(query, SurveyModel::class.java).build()
    }

    fun getImageReference(imageURL: String) : StorageReference {
        return firestoreRepository.getImageReference(imageURL)
    }
}