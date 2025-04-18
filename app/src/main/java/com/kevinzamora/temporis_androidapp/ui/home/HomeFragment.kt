package com.kevinzamora.temporis_androidapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinzamora.temporis_androidapp.databinding.FragmentHomeBinding
import com.kevinzamora.temporis_androidapp.model.Timer
import com.kevinzamora.temporis_androidapp.ui.timer.TimerAdapter
import com.kevinzamora.temporis_androidapp.viewmodel.TimerViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: TimerAdapter
    private val timerViewModel: TimerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Configurar RecyclerView
        adapter = TimerAdapter()
        binding.recyclerViewTimers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTimers.adapter = adapter

        // Crear nuevo temporizador
        binding.buttonCreateTimer.setOnClickListener {
            val name = binding.editTextTimerName.text.toString().trim()
            val duration = binding.editTextTimerDuration.text.toString().toIntOrNull()

            if (name.isNotEmpty() && duration != null) {
                timerViewModel.addTimer(name, duration)
                binding.editTextTimerName.text.clear()
                binding.editTextTimerDuration.text.clear()
                Toast.makeText(requireContext(), "Temporizador creado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Introduce nombre y duración válidos", Toast.LENGTH_SHORT).show()
            }
        }

        // Observar los temporizadores
        timerViewModel.timers.observe(viewLifecycleOwner, Observer { timers ->
            adapter.submitList(timers)
        })

        return binding.root
    }
}
