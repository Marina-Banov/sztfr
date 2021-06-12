package hr.sztfr.sztfr_android.ui.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.databinding.FragmentFavoritesBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections
import hr.sztfr.sztfr_android.ui.user_settings.UserSettingsActivity


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private var userRepository = UserRepository.getInstance(FirebaseFirestore.getInstance())

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = this
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