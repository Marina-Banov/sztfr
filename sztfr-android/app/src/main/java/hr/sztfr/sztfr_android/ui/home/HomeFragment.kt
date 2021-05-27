package hr.sztfr.sztfr_android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.model.Event
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.databinding.FragmentHomeBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var userRepository = UserRepository.getInstance(FirebaseFirestore.getInstance())

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this

        val application = requireNotNull(activity).application
        val list = ArrayList<Event>()
        for (i in 1..5) {
            list.add(Event(
                i.toString(),
                "dummy",
                getString(R.string.event_dummy_title) + " " + i.toString(),
                getString(R.string.event_dummy_time),
                getString(R.string.event_dummy_location),
                getString(R.string.event_dummy_organisation),
                listOf(getString(R.string.tag)),
                getString(R.string.dummy_description)
            ))
        }
        val viewModelFactory = HomeViewModelFactory(list, application)
        val viewModel = ViewModelProvider(this, viewModelFactory)
                .get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        binding.searchFilter.viewModel.selectedTags.observe(viewLifecycleOwner, {
            viewModel.updateEvents(it)
        })

        binding.searchFilter.viewModel.searchQuery.observe(viewLifecycleOwner, {
            viewModel.updateEvents(it)
        })

        binding.homeRecyclerView.adapter = HomeAdapter {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToEventDetailsFragment(it)
            )
        }

        userRepository.user.observe(viewLifecycleOwner, {
            binding.homeRecyclerView.adapter!!.notifyDataSetChanged()
        })

        return binding.root
    }

}



