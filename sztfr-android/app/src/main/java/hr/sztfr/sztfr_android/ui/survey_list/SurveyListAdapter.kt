package hr.sztfr.sztfr_android.ui.survey_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.data.FirestoreRepository
import hr.sztfr.sztfr_android.data.GlideApp
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.data.repository.FirestoreUser
import hr.sztfr.sztfr_android.databinding.LayoutCardSurveyBinding
import hr.sztfr.sztfr_android.util.handleClick

class SurveyListAdapter(private val fragment: Fragment,
                        private val showDetailsListener: (survey: SurveyModel) -> Unit) :
    ListAdapter<SurveyModel, SurveyListAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private val fragment: Fragment,
                     private var binding: LayoutCardSurveyBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(survey: SurveyModel) {
            binding.survey = survey
            binding.user = FirestoreUser.value
            binding.favoritesButton.setOnClickListener { handleClick(survey) }
            val storageReference = FirestoreRepository().getImageReference(survey.image)
            GlideApp.with(fragment).load(storageReference).into(binding.surveyImage)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutCardSurveyBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(fragment, binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val survey = getItem(position)
        viewHolder.itemView.setOnClickListener { showDetailsListener(survey) }
        viewHolder.bind(survey)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<SurveyModel>() {
        override fun areItemsTheSame(oldItem: SurveyModel, newItem: SurveyModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: SurveyModel, newItem: SurveyModel): Boolean {
            return oldItem.documentId == newItem.documentId
        }
    }
}
