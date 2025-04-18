package com.kevinzamora.temporis_androidapp.ui.timer

import android.os.CountDownTimer
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
    private val activeTimers = mutableMapOf<String, CountDownTimer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val binding = ItemTimerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun startCountdown(timer: Timer, holder: TimerViewHolder) {
        val durationInMillis = timer.duration * 60 * 1000L

        activeTimers[timer.id]?.cancel()

        val countdown = object : CountDownTimer(durationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                holder.binding.textViewCountdown.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                holder.binding.textViewCountdown.text = "00:00"
            }
        }

        activeTimers[timer.id] = countdown
        countdown.start()
    }

    inner class TimerViewHolder(val binding: ItemTimerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timer: Timer) {
            binding.timerName.text = timer.name
            binding.timerDuration.text = "${timer.duration} min"

            // Nuevo: mostrar estado y fecha de creación
            binding.timerStatus.text = if (timer.isActive) "Activo" else "Inactivo"
            binding.timerCreatedAt.text = "Creado el: ${
                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(timer.createdAt.toDate())
            }"

            // Reinicia el texto del contador
            binding.textViewCountdown.text = "00:00"

            binding.btnEdit.setOnClickListener { onEditClick?.invoke(timer) }
            binding.btnDelete.setOnClickListener { onDeleteClick?.invoke(timer) }
            binding.btnPlay.setOnClickListener {
                startCountdown(timer, this@TimerViewHolder) // Pasamos el ViewHolder actual
                onPlayClick?.invoke(timer)
            }
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Timer>() {
        override fun areItemsTheSame(oldItem: Timer, newItem: Timer) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Timer, newItem: Timer) = oldItem == newItem
    }
}