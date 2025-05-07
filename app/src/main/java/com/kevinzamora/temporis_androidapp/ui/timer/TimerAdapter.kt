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
    private val remainingTimes = mutableMapOf<String, Long>() // Tiempo restante en ms
    private val pausedStates = mutableMapOf<String, Boolean>() // true = pausado

    inner class TimerViewHolder(val binding: ItemTimerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timer: Timer) {
            binding.timerName.text = timer.name
            binding.timerDuration.text = "${timer.duration} min"
            binding.timerStatus.text = if (timer.isActive) "Activo" else "Inactivo"
            binding.timerCreatedAt.text = "Creado el: ${
                SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(timer.createdAt.toDate())
            }"

            binding.textViewCountdown.text = "00:00"
            binding.btnEdit.setOnClickListener { onEditClick?.invoke(timer) }
            binding.btnDelete.setOnClickListener { onDeleteClick?.invoke(timer) }

            binding.btnPlay.setOnClickListener {
                val isPaused = pausedStates[timer.id] ?: false
                if (activeTimers.containsKey(timer.id)) {
                    if (!isPaused) {
                        // Pausar
                        activeTimers[timer.id]?.cancel()
                        pausedStates[timer.id] = true
                    } else {
                        // Reanudar
                        val remaining = remainingTimes[timer.id] ?: timer.duration * 60 * 1000L
                        startCountdown(timer, this@TimerViewHolder, remaining)
                        pausedStates[timer.id] = false
                    }
                } else {
                    // Iniciar por primera vez
                    startCountdown(timer, this@TimerViewHolder, timer.duration * 60 * 1000L)
                    pausedStates[timer.id] = false
                }

                onPlayClick?.invoke(timer)
            }
        }
    }

    fun startCountdown(timer: Timer, holder: TimerViewHolder, durationInMillis: Long) {
        activeTimers[timer.id]?.cancel()

        val countdown = object : CountDownTimer(durationInMillis, 1000) {
            var millisRemaining = durationInMillis

            override fun onTick(millisUntilFinished: Long) {
                millisRemaining = millisUntilFinished
                remainingTimes[timer.id] = millisRemaining

                val minutes = (millisRemaining / 1000) / 60
                val seconds = (millisRemaining / 1000) % 60
                holder.binding.textViewCountdown.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                holder.binding.textViewCountdown.text = "00:00"
                activeTimers.remove(timer.id)
                pausedStates[timer.id] = false
            }
        }

        activeTimers[timer.id] = countdown
        countdown.start()
    }

    class DiffCallback : DiffUtil.ItemCallback<Timer>() {
        override fun areItemsTheSame(oldItem: Timer, newItem: Timer) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Timer, newItem: Timer) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val binding = ItemTimerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        val timer = getItem(position)
        holder.bind(timer)
    }
}