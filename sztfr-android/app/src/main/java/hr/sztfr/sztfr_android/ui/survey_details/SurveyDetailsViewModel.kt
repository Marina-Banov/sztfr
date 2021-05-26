package hr.sztfr.sztfr_android.ui.survey_details

import android.app.Application
import androidx.databinding.BindingAdapter
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.storage.StorageReference
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.data.FirestoreRepository
import hr.sztfr.sztfr_android.util.GlideApp

class SurveyDetailsViewModel(s: SurveyModel, app: Application) : AndroidViewModel(app) {
    var firestoreRepository = FirestoreRepository()

    companion object {
        var firestoreRepository = FirestoreRepository()
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun loadImage(view: ShapeableImageView, url: String) {
            var storageReference = firestoreRepository.getImageReference(url)
            GlideApp.with(view.context).load(storageReference).into(view)
        }
    }
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
