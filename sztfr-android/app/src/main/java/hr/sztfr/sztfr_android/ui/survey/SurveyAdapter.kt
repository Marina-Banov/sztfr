package hr.sztfr.sztfr_android.ui.survey

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.R

class SurveyAdapter(private val items: List<SurveyNew>) : RecyclerView.Adapter<SurveyAdapter.ViewHolder>() {
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

    }

    override fun onBindViewHolder(viewHolder: SurveyAdapter.ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SurveyAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val homeView = inflater.inflate(R.layout.layout_card_survey_new, parent, false)
        return ViewHolder(homeView)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}