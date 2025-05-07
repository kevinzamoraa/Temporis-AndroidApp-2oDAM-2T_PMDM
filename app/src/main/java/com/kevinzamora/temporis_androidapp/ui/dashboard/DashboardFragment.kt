package com.kevinzamora.temporis_androidapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.kevinzamora.temporis_androidapp.R
import com.kevinzamora.temporis_androidapp.databinding.FragmentDashboardBinding
import com.kevinzamora.temporis_androidapp.model.User
import com.kevinzamora.temporis_androidapp.repository.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private val userRepository = UserRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val userId = user?.uid ?: return root

        binding.etEmail.setText(user.email)
        binding.etUsername.setText(user.displayName?.replace("\\s+".toRegex(), ""))
        binding.etDisplayName.setText(user.displayName)
        binding.etProfileUrl.setText(user.photoUrl.toString())
        Glide.with(requireContext())
            .load(user.photoUrl)
            .placeholder(R.drawable.ic_default_profile)
            .circleCrop()
            .into(binding.imgProfilePhoto)

        // Cargar datos desde Firestore
        lifecycleScope.launch {
            try {
                val snapshot = FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(userId)
                    .get()
                    .await()

                val userData = snapshot.toObject(User::class.java)
                userData?.let {
                    binding.etUsername.setText(it.username)
                    binding.etDisplayName.setText(it.displayName)
                    binding.etEmail.setText(it.email)
                    binding.etProfileUrl.setText(it.profilePhotoUrl)

                    Glide.with(requireContext())
                        .load(it.profilePhotoUrl)
                        .placeholder(R.drawable.ic_default_profile)
                        .circleCrop()
                        .into(binding.imgProfilePhoto)
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error al cargar perfil", Toast.LENGTH_SHORT).show()
            }
        }

        // Guardar cambios en Firestore
        binding.btnSafe.setOnClickListener {
            val updatedUser = User()
            updatedUser.uid = userId
            updatedUser.username = binding.etUsername.text.toString()
            updatedUser.email = binding.etEmail.text.toString()
            updatedUser.displayName = binding.etDisplayName.text.toString()
            updatedUser.profilePhotoUrl = binding.etProfileUrl.text.toString()

            lifecycleScope.launch {
                userRepository.updateUser(updatedUser).collect { result ->
                    result.onSuccess {
                        Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()

                        Glide.with(requireContext())
                            .load(updatedUser.profilePhotoUrl)
                            .placeholder(R.drawable.ic_default_profile)
                            .circleCrop()
                            .into(binding.imgProfilePhoto)
                    }.onFailure { e ->
                        Toast.makeText(requireContext(), "Error al guardar perfil", Toast.LENGTH_SHORT).show()
                        Log.e("DashboardFragment", "Error al actualizar usuario", e)
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
