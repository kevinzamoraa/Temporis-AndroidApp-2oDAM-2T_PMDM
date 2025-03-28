package com.kevinzamora.temporis_androidapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kevinzamora.temporis_androidapp.databinding.FragmentDashboardBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DashboardViewModel
    private lateinit var adapter: TimerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViews()
        observeData()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
    }

    private fun setupViews() {
        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        adapter = TimerAdapter { timer ->
            viewModel.onTimerClick(timer)
        }
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@DashboardFragment.adapter
            addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))
        }
    }

    private fun setupFab() {
        binding.fab.setOnClickListener {
            viewModel.onCreateTimerClick()
        }
    }

    private fun observeData() {
        viewModel.timers.observe(viewLifecycleOwner) { timers ->
            adapter.submitList(timers)
        }

        viewModel.navigationEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is DashboardViewModel.Navigation.CreateTimer -> {
                        findNavController().navigate(R.id.action_dashboardFragment_to_createTimerFragment)
                    }
                    is DashboardViewModel.Navigation.EditTimer -> {
                        findNavController().navigate(
                            R.id.action_dashboardFragment_to_editTimerFragment,
                            Bundle().apply { putParcelable("timer", action.timer) }
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}