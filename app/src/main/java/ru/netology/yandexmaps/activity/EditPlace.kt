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
import ru.netology.yandexmaps.databinding.FragmentEditPlaceBinding
import ru.netology.yandexmaps.viewmodel.PlaceViewModel

@AndroidEntryPoint
class EditPlaceFragment : Fragment() {

    private val viewModel: PlaceViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditPlaceBinding.inflate(inflater, container, false)

        with(binding) {

            val place = viewModel.placeView

            placeName.setText(place.name)


            saveChanges.setImageResource(R.drawable.ic_done_24)
            cancel.setImageResource(R.drawable.ic_cancel_24)

            saveChanges.setOnClickListener {
                viewLifecycleOwner.lifecycleScope.launch {
                    val placeEdited = place.copy(name = binding.placeName.text.toString())
                    viewModel.editPlace(placeEdited)
                    findNavController().navigateUp()
                }
            }

           cancel.setOnClickListener {
                findNavController().navigateUp()
            }

        }

        return binding.root
    }
}