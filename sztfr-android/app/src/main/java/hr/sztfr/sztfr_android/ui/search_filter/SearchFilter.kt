package hr.sztfr.sztfr_android.ui.search_filter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.google.android.material.chip.Chip
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.LayoutSearchFilterBinding

class SearchFilter(ctx: Context, attributeSet: AttributeSet? = null):
    LinearLayout(ctx, attributeSet) {
    private var binding: LayoutSearchFilterBinding
    var viewModel: SearchFilterViewModel

    init {
        this.orientation = VERTICAL

        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_search_filter, this, true)
        binding.lifecycleOwner = ctx as LifecycleOwner

        viewModel = ViewModelProvider(ctx as ViewModelStoreOwner).get(SearchFilterViewModel::class.java)
        viewModel.tags.observe(ctx as LifecycleOwner, Observer {
            for (tag in it) {
                val chip = inflater.inflate(R.layout.layout_chip, binding.filter, false) as Chip
                chip.text = tag
                chip.setOnClickListener {
                    viewModel.updateSelectedTags(tag, chip.isChecked)
                }
                binding.filter.addView(chip)
            }
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                viewModel.setSearchQuery(query!!)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.setSearchQuery(query!!)
                binding.search.clearFocus()
                return true
            }
        })
    }
}