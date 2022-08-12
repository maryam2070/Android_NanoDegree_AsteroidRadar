package com.udacity.asteroidradar

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.RecyclerItemBinding

class AsteroidAdapter(val clickListener: AsteroidClickListener) : ListAdapter<Asteroid,AsteroidAdapter.AsteroidViewHolder>(AsteroidDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        currentList.get(position)

        holder.bind(clickListener,getItem(position)!!)

    }

    class AsteroidViewHolder private  constructor(val binding: RecyclerItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(clickListener: AsteroidClickListener,item: Asteroid) {

            binding.asteroid=item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): AsteroidViewHolder {

                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecyclerItemBinding.inflate(layoutInflater, parent, false)

                return AsteroidViewHolder(binding)
            }
        }
    }
    class AsteroidDiffCallback :
        DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem==newItem
        }
    }

    class AsteroidClickListener(val clickListener: (asteroid:Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}