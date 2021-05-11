package hr.sztfr.sztfr_android.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.databinding.LayoutCardEventBinding

class HomeAdapter(private val showDetailsListener: (event: Event) -> Unit,
                  private val addFavoritesListener: (event: Event) -> Unit) :
        ListAdapter<Event, HomeAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(private var binding: LayoutCardEventBinding,
                     private var showDetailsListener: (event: Event) -> Unit,
                     private var addFavoritesListener: (event: Event) -> Unit):
            RecyclerView.ViewHolder(binding.root) {
        fun bind(event: Event) {
            binding.event = event
            binding.showDetailsButton.setOnClickListener { showDetailsListener(event) }
            binding.addFavoriteButton.setOnClickListener { addFavoritesListener(event) }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutCardEventBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding, showDetailsListener, addFavoritesListener)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val event = getItem(position)
        viewHolder.bind(event)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
