package hr.sztfr.sztfr_android.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.databinding.FragmentEventDetailsBinding


class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val application = requireNotNull(activity).application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false)
        binding.lifecycleOwner = this
        val event = Event(
                "1",
                getDrawable(application, R.drawable.dummy)!!,
                getString(R.string.home_frag_title),
                getString(R.string.home_frag_time),
                getString(R.string.home_frag_location),
                getString(R.string.home_frag_organisation),
                listOf(getString(R.string.tag)),
                getString(R.string.home_frag_description),
        )
        val viewModelFactory = EventDetailsViewModelFactory(event, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(EventDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.event.observe(viewLifecycleOwner, {
            for (tag in (it!!).tags) {
                val chip = layoutInflater.inflate(R.layout.layout_chip, binding.tagGroup, false) as Chip
                chip.text = tag
                chip.isClickable = false
                binding.tagGroup.addView(chip)
            }
        })
        return binding.root
    }

}