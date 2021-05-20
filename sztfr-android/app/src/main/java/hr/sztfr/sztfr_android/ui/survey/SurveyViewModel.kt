package hr.sztfr.sztfr_android.ui.survey

import androidx.lifecycle.ViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.StorageReference
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.data.FirestoreRepository

class SurveyViewModel: ViewModel() {

    val TAG = "SURVEY_VIEW_MODEL"
    private var firestoreRepository = FirestoreRepository()
    private lateinit var storageReference : StorageReference


    fun getPublishedSurveys() : FirestoreRecyclerOptions<SurveyModel>{
        var query = firestoreRepository.getPublishedSurveys()
        var options = FirestoreRecyclerOptions.Builder<SurveyModel>()
                .setQuery(query, SurveyModel::class.java).build()

        return options
    }

    fun getUnpublishedSurveys() : FirestoreRecyclerOptions<SurveyModel>{
        var query = firestoreRepository.getUnpublishedSurveys()
        var options = FirestoreRecyclerOptions.Builder<SurveyModel>()
                .setQuery(query, SurveyModel::class.java).build()

        return options
    }

    fun getImageReference(imageURL: String) : StorageReference {
        return firestoreRepository.getImageReference(imageURL)
    }
}