package hr.sztfr.sztfr_android.ui.favorites

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.databinding.FragmentFavoritesBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections
import hr.sztfr.sztfr_android.util.CreateNotification


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private var userRepository = UserRepository.getInstance(FirebaseFirestore.getInstance())
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val RECEIVE_NOTIFICATIONS = "RECEIVE_NOTIFICATIONS"
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = this
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireActivity())

        val viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        binding.viewModel = viewModel

        binding.searchFilter.selectedTags.observe(viewLifecycleOwner, {
            viewModel.updateFavorites(it)
        })

        binding.searchFilter.searchQuery.observe(viewLifecycleOwner, {
            viewModel.updateFavorites(it)
        })

        userRepository.user.observe(viewLifecycleOwner, {
            viewModel.filterFavorites()
            binding.favoritesRecyclerView.adapter!!.notifyDataSetChanged()
        })

        binding.favoritesRecyclerView.adapter = FavoritesAdapter({
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToEventDetailsFragment(it)
            )
        }, {
            if (it.published) {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSurveyResultsDetailsFragment(it)
                )
            } else {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToSurveyDetailsFragment(it)
                )
            }
        }, this::sendNotification)

        return binding.root
    }

    private fun sendNotification() {
        if (sharedPreferences.getBoolean(RECEIVE_NOTIFICATIONS, true)) {
            CreateNotification.createNotificationChannel(activity)
        }
    }
}