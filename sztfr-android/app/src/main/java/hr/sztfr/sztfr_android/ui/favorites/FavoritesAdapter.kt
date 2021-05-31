package hr.sztfr.sztfr_android.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.databinding.LayoutCardEventBinding
import hr.sztfr.sztfr_android.databinding.LayoutCardSurveyBinding
import hr.sztfr.sztfr_android.ui.home.HomeAdapter
import hr.sztfr.sztfr_android.ui.survey_list.SurveyListAdapter
import hr.sztfr.sztfr_android.util.DiffCallback

class  FavoritesAdapter(private val showEventDetailsListener: (event: Event) -> Unit,
                        private val showSurveyDetailsListener: (survey: SurveyModel) -> Unit) :
    ListAdapter<Filterable, RecyclerView.ViewHolder>(DiffCallback()) {

    private val EVENT = 0
    private val SURVEY = 1

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) is Event) EVENT else SURVEY
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == EVENT) {
            val binding = LayoutCardEventBinding.inflate(LayoutInflater.from(parent.context))
            HomeAdapter.ViewHolder(binding)
        } else {
            val binding = LayoutCardSurveyBinding.inflate(LayoutInflater.from(parent.context))
            SurveyListAdapter.ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.apply {
            if (this is HomeAdapter.ViewHolder) {
                itemView.setOnClickListener { showEventDetailsListener(item as Event) }
                bind(item as Event)
            } else if (this is SurveyListAdapter.ViewHolder) {
                itemView.setOnClickListener { showSurveyDetailsListener(item as SurveyModel) }
                bind(item as SurveyModel)
            }
        }
    }
}
