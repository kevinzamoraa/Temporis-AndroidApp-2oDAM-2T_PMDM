package com.kevinzamora.temporis_androidapp.ui.dashboard

import android.os.Bundle
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

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
                    binding.etUsuario.setText(it.username)
                    binding.etDisplayName.setText(it.displayName)
                    binding.etEmail.setText(it.email)
                    binding.etDescripcion.setText(it.description)
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
        binding.btnGuardar.setOnClickListener {
            val updatedUser = User(
                userId,
                binding.etUsuario.text.toString(),
                user?.email ?: "",
                binding.etDisplayName.text.toString(),
                binding.etDescripcion.text.toString(),
                binding.etProfileUrl.text.toString()
            )

            lifecycleScope.launch {
                try {
                    FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(userId)
                        .set(updatedUser, SetOptions.merge())
                        .await()

                    Toast.makeText(requireContext(), "Perfil actualizado", Toast.LENGTH_SHORT).show()

                    Glide.with(requireContext())
                        .load(updatedUser.profilePhotoUrl)
                        .placeholder(R.drawable.ic_default_profile)
                        .circleCrop()
                        .into(binding.imgProfilePhoto)

                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error al guardar perfil", Toast.LENGTH_SHORT).show()
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
