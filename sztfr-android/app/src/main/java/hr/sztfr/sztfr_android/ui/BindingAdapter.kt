package hr.sztfr.sztfr_android.ui

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.ui.home.HomeAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Event>?) {
    val adapter = recyclerView.adapter as HomeAdapter
    for (d in data!!) {
        Log.i("adapting", d.id)
    }
    adapter.submitList(data)
}