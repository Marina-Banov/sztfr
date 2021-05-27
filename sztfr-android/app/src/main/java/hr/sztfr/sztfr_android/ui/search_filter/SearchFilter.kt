package hr.sztfr.sztfr_android.ui.search_filter

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.EnumsRepository
import hr.sztfr.sztfr_android.databinding.LayoutSearchFilterBinding
import java.util.ArrayList

class SearchFilter(ctx: Context, attributeSet: AttributeSet? = null):
    LinearLayout(ctx, attributeSet) {

    private var binding: LayoutSearchFilterBinding
    private var enumsRepository = EnumsRepository.getInstance(FirebaseFirestore.getInstance())

    private val _selectedTags = MutableLiveData<ArrayList<String>>()
    val selectedTags: LiveData<ArrayList<String>>
        get() = _selectedTags

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    init {
        this.orientation = VERTICAL

        val inflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = DataBindingUtil.inflate(inflater, R.layout.layout_search_filter, this, true)
        binding.lifecycleOwner = ctx as LifecycleOwner

        _selectedTags.value = ArrayList()

        for (tag in enumsRepository.tags.value!!) {
            val chip = inflater.inflate(R.layout.layout_chip, binding.filter, false) as Chip
            chip.text = tag
            chip.setOnClickListener {
                _selectedTags.value?.apply {
                    if (chip.isChecked) { add(tag) } else { remove(tag) }
                }
                // Live data is not updated simply by updating the ArrayList
                // Must also update the reference
                _selectedTags.value = _selectedTags.value
            }
            binding.filter.addView(chip)
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(query: String?): Boolean {
                _searchQuery.value = query!!
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                _searchQuery.value = query!!
                binding.search.clearFocus()
                return true
            }
        })
    }
}