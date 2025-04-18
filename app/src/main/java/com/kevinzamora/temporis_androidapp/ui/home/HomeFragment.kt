package com.kevinzamora.temporis_androidapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.*
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinzamora.temporis_androidapp.databinding.FragmentHomeBinding
import com.kevinzamora.temporis_androidapp.repository.TimerRepository
import com.kevinzamora.temporis_androidapp.ui.timer.TimerAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TimerAdapter
    private val repo = TimerRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        adapter = TimerAdapter()
        binding.recyclerViewTimers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTimers.adapter = adapter

        binding.buttonCreateTimer.setOnClickListener {
            val name = binding.editTextTimerName.text.toString().trim()
            val duration = binding.editTextTimerDuration.text.toString().toIntOrNull()

            if (name.isNotEmpty() && duration != null) {
                repo.createTimer(name, duration) {
                    loadTimers()
                    Toast.makeText(requireContext(), "Temporizador creado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Introduce nombre y duración válidos", Toast.LENGTH_SHORT).show()
            }
        }

        loadTimers()

        return binding.root
    }

    private fun loadTimers() {
        repo.getTimers { timers ->
            adapter.submitList(timers)
        }
    }
}