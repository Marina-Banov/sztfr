package hr.sztfr.sztfr_android.util

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.FirestoreRepository
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.ui.favorites.FavoritesAdapter
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

@JvmName("favoritesListData")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Filterable>?) {
    val adapter = recyclerView.adapter as FavoritesAdapter
    adapter.submitList(data)
}

@BindingAdapter("surveyStatusIcon")
fun setStatusIcon(view: ImageView, item: SurveyModel?) {
    view.setImageResource(when (item!!.published) {
        true -> R.drawable.ic_check
        false -> R.drawable.ic_help
    })
}

@BindingAdapter("favoriteIcon")
fun setDynamicIcon(view: View, isFavorite: Boolean) {
    val icon = if (isFavorite) { R.drawable.favorite_filled }
               else { R.drawable.favorite }
    if (view is ImageButton) {
        view.setImageResource(icon)
    } else if (view is MaterialButton) {
        view.setIconResource(icon)
    }
}

@BindingAdapter("textLocation")
fun setLocationText(view: TextView, event: Event) {
    view.text = if (event.online) {
        view.context.getString(R.string.event_online)
    } else {
        "@ " + event.googlePlace!!.name
    }
}

@BindingAdapter("textLocationDetails")
fun setLocationDetails(view: TextView, event: Event) {
    view.text = if (event.online) event.location else event.googlePlace!!.address
}
