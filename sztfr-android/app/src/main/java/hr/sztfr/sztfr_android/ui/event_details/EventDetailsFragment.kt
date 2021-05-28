package hr.sztfr.sztfr_android.ui.event_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.databinding.FragmentEventDetailsBinding
import hr.sztfr.sztfr_android.util.handleClick


class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private var userRepository = UserRepository.getInstance(FirebaseFirestore.getInstance())

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val application = requireActivity().application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false)
        binding.lifecycleOwner = this
        val event = EventDetailsFragmentArgs.fromBundle(requireArguments()).event
        val viewModelFactory = EventDetailsViewModelFactory(event, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(EventDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.updateFavorites(userRepository.user.value!!.favorites)
        userRepository.user.observe(viewLifecycleOwner, {
            viewModel.updateFavorites(it.favorites)
        })

        viewModel.event.observe(viewLifecycleOwner, {
            for (tag in (it!!).tags) {
                val chip = layoutInflater.inflate(R.layout.layout_chip, binding.tagGroup, false) as Chip
                chip.text = tag
                chip.isClickable = false
                binding.tagGroup.addView(chip)
            }
        })

        binding.favoritesButton.setOnClickListener {
            handleClick(viewModel.event.value!!.documentId)
        }
        binding.goBackBtn.setOnClickListener { requireActivity().onBackPressed() }
        return binding.root
    }

}