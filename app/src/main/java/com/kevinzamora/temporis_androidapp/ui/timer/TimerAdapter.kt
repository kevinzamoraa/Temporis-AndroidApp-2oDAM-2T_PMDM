package com.kevinzamora.temporis_androidapp.ui.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kevinzamora.temporis_androidapp.databinding.ItemTimerBinding
import com.kevinzamora.temporis_androidapp.model.Timer
import java.text.SimpleDateFormat
import java.util.*

class TimerAdapter : ListAdapter<Timer, TimerAdapter.TimerViewHolder>(TimerDiffCallback) {

    inner class TimerViewHolder(private val binding: ItemTimerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(timer: Timer) {
            binding.timerName.text = timer.name
            binding.timerDuration.text = "${timer.duration} min"

            val statusText = if (timer.isActive) "Activo" else "Inactivo"
            binding.timerStatus.text = "Estado: $statusText"

            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val date = timer.createdAt?.toDate()
            binding.timerCreatedAt.text = "Creado el: ${date?.let { sdf.format(it) } ?: "Desconocido"}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val binding = ItemTimerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}