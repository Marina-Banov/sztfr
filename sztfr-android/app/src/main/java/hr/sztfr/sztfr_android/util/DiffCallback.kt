package hr.sztfr.sztfr_android.util

import androidx.recyclerview.widget.DiffUtil
import hr.sztfr.sztfr_android.data.model.Filterable

class DiffCallback<T: Filterable> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.documentId == newItem.documentId
    }
}