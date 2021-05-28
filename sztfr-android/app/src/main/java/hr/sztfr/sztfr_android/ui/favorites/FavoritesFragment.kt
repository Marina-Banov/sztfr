package hr.sztfr.sztfr_android.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.databinding.FragmentFavoritesBinding
import hr.sztfr.sztfr_android.ui.MainFragmentDirections


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = this
        val viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        binding.viewModel = viewModel

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
}