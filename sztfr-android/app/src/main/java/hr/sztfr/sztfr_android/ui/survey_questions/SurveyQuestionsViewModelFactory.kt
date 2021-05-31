package hr.sztfr.sztfr_android.ui.survey_questions

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.sztfr.sztfr_android.ui.event_details.EventDetailsViewModel

class SurveyQuestionsViewModelFactory(private val id: String, private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyQuestionsViewModel::class.java)){
            return SurveyQuestionsViewModel(id, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

