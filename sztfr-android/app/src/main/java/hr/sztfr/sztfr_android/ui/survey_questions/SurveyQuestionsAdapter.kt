package hr.sztfr.sztfr_android.ui.survey_questions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.data.model.Question
import hr.sztfr.sztfr_android.databinding.LayoutCardSurveyQuestionBinding
import hr.sztfr.sztfr_android.util.QuestionsDiffCallBack


class SurveyQuestionsAdapter(private val showDetailsListener: FragmentManager) :
    androidx.recyclerview.widget.ListAdapter<Question, SurveyQuestionsAdapter.ViewHolder>(
        QuestionsDiffCallBack()
    ) {

    class ViewHolder(private var binding: LayoutCardSurveyQuestionBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(question: Question){
            binding.question = question
            binding.executePendingBindings()
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val question = getItem(position)
        holder.bind(question)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutCardSurveyQuestionBinding.inflate(LayoutInflater.from(parent.context))
        return SurveyQuestionsAdapter.ViewHolder(binding)
    }


}




