package com.kevinzamora.temporis_androidapp.ui.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevinzamora.temporis_androidapp.databinding.ItemTimerBinding
import com.kevinzamora.temporis_androidapp.model.Timer
import java.text.SimpleDateFormat
import java.util.*

class TimerAdapter : ListAdapter<Timer, TimerAdapter.TimerViewHolder>(DiffCallback()) {

    var onPlayClick: ((Timer) -> Unit)? = null
    var onEditClick: ((Timer) -> Unit)? = null
    var onDeleteClick: ((Timer) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val binding = ItemTimerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TimerViewHolder(private val binding: ItemTimerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timer: Timer) {
            binding.timerName.text = timer.name
            binding.timerDuration.text = "${timer.duration} min"

            // Aquí conectamos los botones con las funciones del fragment
            binding.btnEdit.setOnClickListener {
                onEditClick?.invoke(timer)
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClick?.invoke(timer)
            }

            binding.btnPlay.setOnClickListener {
                onPlayClick?.invoke(timer)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Timer>() {
        override fun areItemsTheSame(oldItem: Timer, newItem: Timer) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Timer, newItem: Timer) = oldItem == newItem
    }
}