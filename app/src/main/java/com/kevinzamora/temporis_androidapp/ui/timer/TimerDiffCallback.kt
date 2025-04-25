package com.kevinzamora.temporis_androidapp.ui.timer

import androidx.recyclerview.widget.DiffUtil
import com.kevinzamora.temporis_androidapp.model.Timer

object TimerDiffCallback : DiffUtil.ItemCallback<Timer>() {
    override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
        return oldItem == newItem
    }
}
