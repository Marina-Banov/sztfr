package hr.sztfr.sztfr_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.FirestoreUser
import hr.sztfr.sztfr_android.databinding.LayoutCardEventBinding
import hr.sztfr.sztfr_android.util.handleClick

class HomeAdapter(private val showDetailsListener: (event: Event) -> Unit) :
        ListAdapter<Event, HomeAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: LayoutCardEventBinding):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.event = event
            binding.userFavorites = FirestoreUser.value!!.favorites
            binding.favoritesButton.setOnClickListener {
                handleClick(event.documentId)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutCardEventBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val event = getItem(position)
        viewHolder.itemView.setOnClickListener { showDetailsListener(event) }
        viewHolder.bind(event)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.documentId == newItem.documentId
        }
    }
}
