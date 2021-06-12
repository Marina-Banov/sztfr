package hr.sztfr.sztfr_android.ui.survey_list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hr.sztfr.sztfr_android.data.model.SurveyModel
/*
class SurveyListViewModelFactory (
    private val survey: SurveyModel,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SurveyListViewModel::class.java)) {
            return SurveyListViewModel(survey, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/