package hr.sztfr.sztfr_android.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.data.model.User
import hr.sztfr.sztfr_android.ui.home.HomeAdapter
import hr.sztfr.sztfr_android.ui.survey_list.SurveyListAdapter

@JvmName("bindRecyclerView1")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Event>?) {
    val adapter = recyclerView.adapter as HomeAdapter
    adapter.submitList(data)
}

@JvmName("bindRecyclerView2")
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

@BindingAdapter(value=["item", "user"])
fun MaterialButton.setDynamicIcon(item: Filterable?, user: User?) {
    this.setIconResource(getFavoritesDrawable(item, user))
}

@BindingAdapter(value=["item", "user"])
fun ImageView.setFavoriteIcon(item: Filterable?, user: User?) {
    this.setImageResource(getFavoritesDrawable(item, user))
}

fun getFavoritesDrawable(item: Filterable?, user: User?): Int {
    return when {
        user!!.favorites.contains(item) -> R.drawable.favorite_filled
        else -> R.drawable.favorite
    }
}