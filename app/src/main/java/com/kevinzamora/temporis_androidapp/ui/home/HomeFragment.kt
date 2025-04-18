package com.kevinzamora.temporis_androidapp.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
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
    private var editingTimerId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Configurar RecyclerView
        adapter = TimerAdapter()
        binding.recyclerViewTimers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTimers.adapter = adapter

        // Crear nuevo temporizador o editar existente
        binding.buttonCreateTimer.setOnClickListener {
            val name = binding.editTextTimerName.text.toString().trim()
            val duration = binding.editTextTimerDuration.text.toString().toIntOrNull()

            if (name.isNotEmpty() && duration != null) {
                val id = editingTimerId
                if (id == null) {
                    // Crear nuevo temporizador
                    timerViewModel.addTimer(name, duration)
                    Toast.makeText(requireContext(), "Temporizador creado", Toast.LENGTH_SHORT).show()
                } else {
                    // Editar: buscamos el timer original y conservamos sus campos
                    val originalTimer = timerViewModel.timers.value?.find { it.id == id }

                    if (originalTimer != null) {
                        val updatedTimer = Timer().apply {
                            this.id = originalTimer.id
                            this.name = name
                            this.duration = duration
                            this.isActive = originalTimer.isActive
                            this.createdAt = originalTimer.createdAt
                            this.uid = originalTimer.uid
                        }

                        timerViewModel.updateTimer(updatedTimer)
                        Toast.makeText(requireContext(), "Temporizador actualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "Error: temporizador no encontrado", Toast.LENGTH_SHORT).show()
                    }

                    editingTimerId = null
                    binding.buttonCreateTimer.text = "Crear temporizador"
                }

                binding.editTextTimerName.text.clear()
                binding.editTextTimerDuration.text.clear()
            } else {
                Toast.makeText(requireContext(), "Introduce nombre y duración válidos", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.onDeleteClick = { timer ->
            AlertDialog.Builder(requireContext())
                .setTitle("Eliminar temporizador")
                .setMessage("¿Estás seguro de que quieres eliminar este temporizador?")
                .setPositiveButton("Sí") { _, _ ->
                    timerViewModel.deleteTimer(timer.id)
                    Toast.makeText(requireContext(), "Temporizador eliminado", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        adapter.onPlayClick = { timer ->
            val durationInMillis = timer.duration * 60 * 1000L

            Toast.makeText(requireContext(), "Temporizador iniciado: ${timer.name}", Toast.LENGTH_SHORT).show()

            object : CountDownTimer(durationInMillis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = (millisUntilFinished / 1000) % 60
                    val minutes = (millisUntilFinished / 1000) / 60
                    println("Tiempo restante: $minutes:${"%02d".format(seconds)}")
                }

                override fun onFinish() {
                    Toast.makeText(requireContext(), "¡Temporizador ${timer.name} finalizado!", Toast.LENGTH_SHORT).show()
                }
            }.start()
        }

        // Observar los temporizadores
        timerViewModel.timers.observe(viewLifecycleOwner, Observer { timers ->
            adapter.submitList(timers)
        })

        return binding.root
    }
}
