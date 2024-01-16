package ru.netology.yandexmaps.activity

import android.graphics.PointF
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.GeoObjectTapEvent
import com.yandex.mapkit.layers.GeoObjectTapListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.TextStyle
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import ru.netology.yandexmaps.dto.Place
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.databinding.FragmentMapsBinding
import ru.netology.yandexmaps.viewmodel.PlaceViewModel

@AndroidEntryPoint
class MapsFragment : Fragment(), GeoObjectTapListener {

    private val viewModel: PlaceViewModel by activityViewModels()

    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentMapsBinding.inflate(inflater, container, false)

        binding.placeInfo.visibility = View.GONE

        lifecycleScope.let {
            MapKitFactory.initialize(requireContext())
            //requireActivity().setContentView(binding.root)  // для activity
            mapView = binding.mapview

            val map = mapView.mapWindow.map
            map.apply {
                //isNightModeEnabled = true
                isScrollGesturesEnabled = true
                isZoomGesturesEnabled = true
            }
            map.move(
                CameraPosition(
                    Point(viewModel.position.latitude, viewModel.position.longitude), //Point(55.752004, 37.617734),
                    /* zoom = */ 17.0f,
                    /* azimuth = */ 150.0f,
                    /* tilt = */ 30.0f
                )
            )

            binding.zoomInButton.setOnClickListener {
                val cameraPosition = map.cameraPosition
                val newZoom = cameraPosition.zoom + 1.0f
                val newCameraPosition = CameraPosition(cameraPosition.target, newZoom, cameraPosition.azimuth, cameraPosition.tilt)
                map.move(newCameraPosition)
            }

            binding.zoomOutButton.setOnClickListener {
                val cameraPosition = map.cameraPosition
                val newZoom = cameraPosition.zoom - 1.0f
                val newCameraPosition = CameraPosition(cameraPosition.target, newZoom, cameraPosition.azimuth, cameraPosition.tilt)
                map.move(newCameraPosition)
            }

            binding.showPlaces.setOnClickListener {
                findNavController().navigate(R.id.placesFragment)
            }


            map.addTapListener (this)

            viewModel.placeName.observe(viewLifecycleOwner) {
                binding.placeInfo.isVisible = (it != null)
                binding.placeInfo.text = "More information about ${it}"
            }


            binding.placeInfo.setOnClickListener {
                it.visibility = View.GONE
                viewModel.placeName.value = null
                findNavController().navigate(R.id.placeDescriptionFragment)
            }

            val imageProvider = ImageProvider.fromResource(requireContext(), R.drawable.ic_map_marker_icon)

            viewModel.data.observe(viewLifecycleOwner) { listPlaces ->
                listPlaces.forEach {place ->
                    map.mapObjects.addPlacemark().apply {
                        geometry = Point(place.latitude, place.longitude)
                        setIcon(imageProvider)
                        setIconStyle(
                            IconStyle().apply {
                                anchor = PointF(0.5f, 1.0f)
                                scale = 0.08f
                                zIndex = 10F
                            }
                        )
                        setText("${place.name}",
                            TextStyle().apply {
                                size = 10f
                                placement = TextStyle.Placement.TOP
                                offset = 5f
                            }
                        )
                    }
                }
            }


        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onObjectTap(geoObjectTapEvent: GeoObjectTapEvent): Boolean {

        val objectGeometry = geoObjectTapEvent.geoObject.geometry.component1().point
        if (objectGeometry != null) {
            geoObjectTapEvent.geoObject.name?.let { name ->
                viewModel.placeView = Place (name, objectGeometry.latitude, objectGeometry.longitude)
                viewModel.placeName.value = name
                viewModel.position= Point(objectGeometry.latitude, objectGeometry.longitude)
            }
        }
        return true
    }

}
