package hr.sztfr.sztfr_android.ui.survey_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.sztfr.sztfr_android.data.model.SurveyModel
import java.util.ArrayList

class SurveyListViewModel: ViewModel() {

    private val _surveys = MutableLiveData<ArrayList<SurveyModel>>()
    val surveys: LiveData<ArrayList<SurveyModel>>
        get() = _surveys

    fun setSurveys(s: ArrayList<SurveyModel>) {
        _surveys.value = s
    }
}