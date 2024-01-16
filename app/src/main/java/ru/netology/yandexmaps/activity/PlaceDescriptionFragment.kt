package ru.netology.yandexmaps.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.databinding.FragmentPlaceDescriptionBinding
import ru.netology.yandexmaps.viewmodel.PlaceViewModel

@AndroidEntryPoint
class PlaceDescriptionFragment : Fragment() {

    private val viewModel: PlaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPlaceDescriptionBinding.inflate(inflater, container, false)

        with(binding) {

            val place = viewModel.placeView

            val description = "Name of the place: ${place.name}\n" +
                    "\nits latitude ${place.latitude}\n" +
                    "\nits longitude ${place.longitude}\n"

            placeDescription.text = description

            addToMyPlaces.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.savePlace(place)
                    findNavController().navigate(R.id.mapsFragment)
                }
            }

            backToMap.setOnClickListener {
                findNavController().navigateUp()
            }

        }









        return binding.root
    }
}