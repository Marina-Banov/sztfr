package hr.sztfr.sztfr_android.ui.search_filter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.google.android.material.chip.Chip
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.LayoutSearchFilterBinding

class SearchFilter(ctx: Context, attributeSet: AttributeSet? = null):
    LinearLayout(ctx, attributeSet) {
    private var binding: LayoutSearchFilterBinding
    private var viewModel: SearchFilterViewModel

    init {
        this.orientation = VERTICAL
        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_search_filter, this, true)
        viewModel = ViewModelProvider(ctx as ViewModelStoreOwner).get(SearchFilterViewModel::class.java)
        viewModel.tags.observe(ctx as LifecycleOwner, Observer {
            for (tag in it) {
                val chip = inflater.inflate(R.layout.layout_chip, binding.filter, false) as Chip
                chip.text = tag
                binding.filter.addView(chip)
            }
        })
    }
}