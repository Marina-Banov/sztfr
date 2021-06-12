package hr.sztfr.sztfr_android.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.databinding.FragmentHomeBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections
import hr.sztfr.sztfr_android.ui.user_settings.UserSettingsActivity


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var userRepository = UserRepository.getInstance(FirebaseFirestore.getInstance())

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this

        val viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel

        binding.searchFilter.selectedTags.observe(viewLifecycleOwner, {
            viewModel.updateEvents(it)
        })

        binding.searchFilter.searchQuery.observe(viewLifecycleOwner, {
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



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn = view?.findViewById<ImageButton>(R.id.profile_button)
        btn?.setOnClickListener {
            var intent = Intent(activity, UserSettingsActivity::class.java)
            startActivity(intent)
        }
    }
}



