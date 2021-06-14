package hr.sztfr.sztfr_android.ui.event_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.material.chip.Chip
import com.google.firebase.firestore.FirebaseFirestore
import hr.sztfr.sztfr_android.R
import hr.sztfr.sztfr_android.data.repository.UserRepository
import hr.sztfr.sztfr_android.databinding.FragmentEventDetailsBinding
import hr.sztfr.sztfr_android.ui.settings.CreateNotification
import hr.sztfr.sztfr_android.util.handleClick


class EventDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEventDetailsBinding
    private lateinit var viewModel: EventDetailsViewModel
    private var userRepository = UserRepository.getInstance(FirebaseFirestore.getInstance())
    private var googleMap: GoogleMap? = null

    companion object {
        const val MAPS_QUERY = "https://www.google.com/maps/search/?api=1&query="
        const val MAPS_QUERY_PLACE_ID = "&query_place_id="
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val application = requireActivity().application
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_details, container, false)
        binding.lifecycleOwner = this

        val event = EventDetailsFragmentArgs.fromBundle(requireArguments()).event
        val viewModelFactory = EventDetailsViewModelFactory(event, application)
        viewModel = ViewModelProvider(this, viewModelFactory)
                .get(EventDetailsViewModel::class.java)
        binding.viewModel = viewModel

        viewModel.updateFavorites(userRepository.user.value!!.favorites)
        userRepository.user.observe(viewLifecycleOwner, {
            viewModel.updateFavorites(it.favorites)
        })

        if (!event.online) {
            initMapView(savedInstanceState, event.googlePlace!!)
        }

        for (tag in event.tags) {
            val chip = layoutInflater.inflate(R.layout.layout_chip, binding.tagGroup, false) as Chip
            chip.text = tag
            chip.isClickable = false
            chip.textSize = 16f
            binding.tagGroup.addView(chip)
        }

        binding.favoritesButton.setOnClickListener {
            handleClick(viewModel.event.value!!.documentId)

            CreateNotification.createNotificationChannel(activity, viewModel.event.value!!.documentId)
        }

        binding.goBackBtn.setOnClickListener { requireActivity().onBackPressed() }

        return binding.root
    }

    private fun initMapView(savedInstanceState: Bundle?, place: Place) {
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.onResume()
        binding.mapView.getMapAsync {
            googleMap = it
            val pos = LatLng(place.latLng!!.latitude, place.latLng!!.longitude)
            googleMap!!.addMarker(MarkerOptions().position(pos))
            googleMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, 15f))
            googleMap!!.setOnMapClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("$MAPS_QUERY${pos.latitude},${pos.longitude}$MAPS_QUERY_PLACE_ID${place.id}")
                )
                startActivity(intent)
            }
        }
        binding.mapView.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        if (googleMap != null) {
            binding.mapView.onResume()
        }
    }

    override fun onPause() {
        super.onPause()
        if (googleMap != null) {
            binding.mapView.onPause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (googleMap != null) {
            binding.mapView.onDestroy()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (googleMap != null) {
            binding.mapView.onLowMemory()
        }
    }


}