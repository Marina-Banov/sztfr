package hr.sztfr.sztfr_android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.ui.home.HomeAdapter
import hr.sztfr.sztfr_android.ui.survey_list.SurveyListAdapter

@JvmName("eventListData")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Event>?) {
    val adapter = recyclerView.adapter as HomeAdapter
    adapter.submitList(data)
}

@JvmName("surveyListData")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<SurveyModel>?) {
    val adapter = recyclerView.adapter as SurveyListAdapter
    adapter.submitList(data)
}

@BindingAdapter("surveyStatusIcon")
fun ImageView.setStatusIcon(item: SurveyModel?) {
    item?.let {
        setImageResource(when (item.published) {
            true -> R.drawable.ic_check
            false -> R.drawable.ic_help
        })
    }
}

@BindingAdapter(value=["item", "userFavorites"])
fun MaterialButton.setDynamicIcon(item: Filterable?, userFavorites: List<String>?) {
    this.setIconResource(getFavoritesDrawable(item!!.documentId, userFavorites))
}

@BindingAdapter(value=["item", "userFavorites"])
fun ImageView.setDynamicIcon(item: Filterable?, userFavorites: List<String>?) {
    this.setImageResource(getFavoritesDrawable(item!!.documentId, userFavorites))
}

fun getFavoritesDrawable(id: String, userFavorites: List<String>?): Int {
    return if (userFavorites!!.contains(id)) {
        R.drawable.favorite_filled
    } else {
        R.drawable.favorite
    }
}