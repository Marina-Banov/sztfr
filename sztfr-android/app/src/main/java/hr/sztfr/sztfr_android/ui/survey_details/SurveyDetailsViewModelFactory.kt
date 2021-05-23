package hr.sztfr.sztfr_android.ui.survey_details

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.sztfr.sztfr_android.data.model.SurveyModel


@Suppress("UNCHECKED_CAST")
class SurveyDetailsViewModelFactory (private val surveyModel: SurveyModel, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SurveyDetailsViewModel::class.java)){
            return SurveyDetailsViewModel(surveyModel, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
