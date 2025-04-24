package com.kevinzamora.temporis_androidapp.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.kevinzamora.temporis_androidapp.R
import com.kevinzamora.temporis_androidapp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kevinzamora.temporis_androidapp.ui.auth.login.LoginActivity
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        // Para que el teclado no se vuelva loco
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val btnRegistrar = root.findViewById<Button>(R.id.btnRegistroRegistrar)
        val etRegistroEmail = root.findViewById<EditText>(R.id.etRegistroEmail)
        val etRegistroContra = root.findViewById<EditText>(R.id.etRegistroContra)
        val etRegistroConfirmContra = root.findViewById<EditText>(R.id.etRegistroConfirm)
        val etRegistroUserName = root.findViewById<EditText>(R.id.etRegistroUserName)
        /*val ivRegistroAtras = root.findViewById<ImageView>(R.id.ivRegistroAtras)*/

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        btnRegistrar.setOnClickListener {

            val email = etRegistroEmail.text.toString()
            val password = etRegistroContra.text.toString()
            val confirmPassword = etRegistroConfirmContra.text.toString()
            val username = etRegistroUserName.text.toString()

            // Se comprueba que todos los campos estén rellenos
            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && username.isNotEmpty()) {
                // Se comprueba que el email tiene un formato correcto
                if (comprobarEmail(email.trim())) {
                    etRegistroEmail.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                        ContextCompat.getColorStateList(it1, R.color.background_tint_azul)
                    })

                    // Se comprueba que la contraseña es de al menos 6 caracteres
                    if (password.trim().length >= 6) {
                        etRegistroContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                            ContextCompat.getColorStateList(it1, R.color.background_tint_azul)
                        })

                        // Se comprueba que las contraseñas coinciden
                        if (password == confirmPassword) {
                            etRegistroConfirmContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                                ContextCompat.getColorStateList(it1, R.color.background_tint_azul)
                            })

                            // Se han pasado los filtros y se crea la cuenta con el email y la contraseña
                            registrar(username, email, password)

                        } else {
                            Toast.makeText(context, "Revise la contraseña", Toast.LENGTH_SHORT).show()
                            etRegistroConfirmContra.setText("")
                            etRegistroConfirmContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                                ContextCompat.getColorStateList(it1, R.color.rojo_google)
                            })
                        }
                    } else {
                        etRegistroContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                            ContextCompat.getColorStateList(it1, R.color.rojo_google)
                        })
                        Toast.makeText(context, "La contraseña debe ser de al menos 6 caracteres", Toast.LENGTH_LONG).show()
                    }

                } else {
                    etRegistroEmail.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                        ContextCompat.getColorStateList(it1, R.color.rojo_google)
                    })
                    Toast.makeText(context, "Revise el email", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        /*ivRegistroAtras.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
        }*/

        return root
    }

    private fun registrar(username: String, email: String, contra: String) {
        // Verificamos si el nombre de usuario está disponible
        firestore.collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // Si no existe, procedemos con el registro
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, contra)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val uid = auth.uid.toString()
                                val photo = "https://img.freepik.com/premium-vector/gamer-man_961307-25037.jpg?semt=ais_hybrid&w=740"
                                val user = User(uid, username, email, username, photo)

                                // Guardamos el usuario en Firestore
                                firestore.collection("users").document(uid).set(user)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(activity, LoginActivity::class.java)
                                        activity?.startActivity(intent)
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Error al guardar en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            } else {
                                Toast.makeText(context, "Error al crear cuenta: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(context, "Nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al verificar nombre de usuario: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun comprobarEmail(email: String): Boolean {
        val pattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        )
        val matcher = pattern.matcher(email)
        return matcher.find()
    }
}