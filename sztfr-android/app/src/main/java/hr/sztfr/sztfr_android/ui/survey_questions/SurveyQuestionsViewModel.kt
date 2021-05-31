package hr.sztfr.sztfr_android.ui.survey_questions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.model.Question
import hr.sztfr.sztfr_android.data.repository.SurveysRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SurveyQuestionsViewModel(id: String, app: Application) : AndroidViewModel(app){

    private val surveysRepository = SurveysRepository()
    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _questions = MutableLiveData<ArrayList<Question>>()
    val questions: LiveData<ArrayList<Question>>
        get() = _questions


    init{
        getQuestions(id)
    }

    private fun getQuestions(id: String) {
        coroutineScope.launch {
            _questions.value = surveysRepository.getQuestions(id)
        }
    }
}



