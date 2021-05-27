package hr.sztfr.sztfr_android.ui.survey_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.StorageReference
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.data.FirestoreRepository

class SurveyDetailsViewModel(s: SurveyModel, app: Application) : AndroidViewModel(app) {
    private var firestoreRepository = FirestoreRepository()

    private val _studyModel = MutableLiveData<SurveyModel>()
    val surveyModel: LiveData<SurveyModel>
        get() = _studyModel

    private val _resultImages = MutableLiveData<List<StorageReference>>()
    val resultsImages : LiveData<List<StorageReference>>
        get() = _resultImages


    init {
        _studyModel.value = s
        _resultImages.value = firestoreRepository.getImageReferences(s.resultImages)
    }



}
