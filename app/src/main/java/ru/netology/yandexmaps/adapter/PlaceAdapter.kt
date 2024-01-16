package ru.netology.yandexmaps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.yandexmaps.R
import ru.netology.yandexmaps.databinding.FragmentOnePlaceBinding
import ru.netology.yandexmaps.dto.Place

interface OnInteractionListener {
    fun remove (place: Place)
    fun goTo (place: Place)
}

class PlaceAdapter (private val onInteractionListener: OnInteractionListener):
    ListAdapter<Place, PlaceViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding =
            FragmentOnePlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        holder.bind(place)
    }
}

class PlaceViewHolder(
    private val binding: FragmentOnePlaceBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(place: Place) {
        binding.apply {
            placeName.text = place.name
            remove.setImageResource(R.drawable.ic_cancel_24)
            remove.setOnClickListener {
                onInteractionListener.remove(place)
            }
            placeName.setOnClickListener {
                onInteractionListener.goTo(place)
            }
        }
    }
}

class PlaceDiffCallback : DiffUtil.ItemCallback<Place>() {
    override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
        return oldItem == newItem
    }
}