package hr.sztfr.sztfr_android.util

import androidx.recyclerview.widget.DiffUtil
import hr.sztfr.sztfr_android.data.model.Filterable
import hr.sztfr.sztfr_android.data.model.Question

class QuestionsDiffCallBack<T: Question> : DiffUtil.ItemCallback<T>(){

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.documentId == newItem.documentId
    }
}
