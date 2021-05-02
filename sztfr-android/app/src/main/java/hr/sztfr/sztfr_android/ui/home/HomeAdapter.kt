package hr.sztfr.sztfr_android.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hr.sztfr.sztfr_android.R

class HomeAdapter (private val items: List<HomeRow>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    var onBtnClick: ((HomeRow) -> Unit)? = null

    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

    }

    override fun onBindViewHolder(viewHolder: HomeAdapter.ViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val homeView = inflater.inflate(R.layout.layout_card_event, parent, false)
        return ViewHolder(homeView)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
