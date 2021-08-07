package com.onething.asteroidradar.screens.list


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.onething.asteroidradar.databinding.ListItemAsteroidBinding
import com.onething.asteroidradar.domain.model.Asteroid

class AsteroidListAdapter(
    private val callback: AsteroidItemClickListener
) : ListAdapter<Asteroid, AsteroidListAdapter.ViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: ListItemAsteroidBinding,
        private val callback: AsteroidItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Asteroid) {
            binding.root.setOnClickListener { callback.onClick(data) }
            binding.asteroid = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup, callback: AsteroidItemClickListener) : ViewHolder {
                return ViewHolder(
                    ListItemAsteroidBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ), callback
                )
            }
        }

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Asteroid>() {
            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

}

interface AsteroidItemClickListener {
    fun onClick(item: Asteroid)
}