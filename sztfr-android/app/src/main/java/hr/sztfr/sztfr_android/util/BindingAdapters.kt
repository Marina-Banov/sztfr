package hr.sztfr.sztfr_android.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.FirestoreRepository
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.ui.home.HomeAdapter
import hr.sztfr.sztfr_android.ui.survey_list.SurveyListAdapter

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    val storageReference = FirestoreRepository().getImageReference(url)
    GlideApp.with(view.context).load(storageReference).into(view)
}

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
fun setStatusIcon(view: ImageView, item: SurveyModel?) {
    view.setImageResource(when (item!!.published) {
        true -> R.drawable.ic_check
        false -> R.drawable.ic_help
    })
}

@BindingAdapter(value=["item", "userFavorites"])
fun setDynamicIcon(view: MaterialButton, item: Filterable?, userFavorites: List<String>?) {
    view.setIconResource(getFavoritesDrawable(item!!.documentId, userFavorites))
}

@BindingAdapter(value=["item", "userFavorites"])
fun setDynamicIcon(view: ImageView, item: Filterable?, userFavorites: List<String>?) {
    view.setImageResource(getFavoritesDrawable(item!!.documentId, userFavorites))
}

fun getFavoritesDrawable(id: String, userFavorites: List<String>?): Int {
    return if (userFavorites!!.contains(id)) {
        R.drawable.favorite_filled
    } else {
        R.drawable.favorite
    }
}