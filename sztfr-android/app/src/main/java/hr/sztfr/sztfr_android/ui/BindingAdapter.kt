package hr.sztfr.sztfr_android.ui

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.model.SurveyModel
import hr.sztfr.sztfr_android.ui.home.HomeAdapter

@JvmName("bindRecyclerView1")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Event>?) {
    val adapter = recyclerView.adapter as HomeAdapter
    adapter.submitList(data)
}