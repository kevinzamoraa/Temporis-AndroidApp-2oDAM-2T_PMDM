package com.kevinzamora.temporis_androidapp.ui.timer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kevinzamora.temporis_androidapp.R
import com.kevinzamora.temporis_androidapp.model.Timer
import com.kevinzamora.temporis_androidapp.repository.TimerRepository

class TimerActivity {
    /*private lateinit var timerAdapter: Timer
    private lateinit var repo: TimerRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.activity_timer, container, false)
        val etName = view.findViewById<EditText>(R.id.etTimerName)
        val btnCreate = view.findViewById<Button>(R.id.btnCreateTimer)
        val rv = view.findViewById<RecyclerView>(R.id.rvTimers)

        repo = TimerRepository() // Ajusta si usas DI
        timerAdapter = Any(listOf())

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = Timer

        // Crear nuevo temporizador
        btnCreate.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isNotEmpty()) {
                repo.createTimer(name) { success ->
                    if (success) {
                        Toast.makeText(context, "Temporizador creado", Toast.LENGTH_SHORT).show()
                        etName.text.clear()
                        loadTimers()
                    }
                }
            }
        }

        loadTimers()

        return view
    }

    private fun loadTimers() {
        repo.getTimers { timers ->
            timer.updateTimers(timers)
        }
    }*/
}
