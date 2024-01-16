package ru.netology.yandexmaps.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.adapter.OnInteractionListener
import ru.netology.yandexmaps.adapter.PlaceAdapter
import ru.netology.yandexmaps.databinding.FragmentPlacesBinding
import ru.netology.yandexmaps.dto.Place
import ru.netology.yandexmaps.viewmodel.PlaceViewModel

@AndroidEntryPoint
class PlacesFragment : Fragment() {

    private val viewModel: PlaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlacesBinding.inflate(inflater, container, false)

        val adapter = PlaceAdapter(object : OnInteractionListener {
            override fun remove(place: Place) {
                viewModel.removePlace(place)
            }

            override fun goTo(place: Place) {
                viewModel.position = Point(place.latitude, place.longitude)
                findNavController().navigate(R.id.action_placesFragment_to_mapsFragment)
            }
        })

        binding.recyclerList.adapter = adapter

        viewModel.data.observe(viewLifecycleOwner) { places ->
            adapter.submitList(places)
        }

        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_placesFragment_to_mapsFragment)
        }

        return binding.root
    }


}