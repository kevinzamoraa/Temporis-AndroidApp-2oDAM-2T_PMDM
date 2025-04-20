package com.example.tenisclubdroid.ui.Login

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
import com.google.firebase.database.*
import com.kevinzamora.temporis_androidapp.ui.auth.login.LoginActivity
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var takenNames: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_register, container, false)

        //para que el teclado no se vuelva loco
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        val btnRegistrar = root.findViewById<Button>(R.id.btnRegistroRegistrar)
        val etRegistroEmail = root.findViewById<EditText>(R.id.etRegistroEmail)
        val etRegistroContra = root.findViewById<EditText>(R.id.etRegistroContra)
        val etRegistroConfirmContra = root.findViewById<EditText>(R.id.etRegistroConfirm)
        val etRegistroUserName = root.findViewById<EditText>(R.id.etRegistroUserName)
        val ivRegistroAtras = root.findViewById<ImageView>(R.id.ivRegistroAtras)

        auth = FirebaseAuth.getInstance()
        database =
            FirebaseDatabase.getInstance("https://tenisclubdroid-default-rtdb.europe-west1.firebasedatabase.app/")


        databaseReference = database.reference.child("users")
        takenNames = database.reference.child("NombresCogidos")



        btnRegistrar.setOnClickListener(View.OnClickListener {

            var email = etRegistroEmail.text.toString()
            var password = etRegistroContra.text.toString()
            var confirm_password = etRegistroConfirmContra.text.toString()
            var username = etRegistroUserName.text.toString()


            //se comprueba que todos los campos esten rellenos
            if (!email.isEmpty() && !password.isEmpty() && !confirm_password.isEmpty() && !username.isEmpty()) {
                //se comprueba que el email tiene un formato correcto
                if (comprobarEmail(email.trim())) {
                    etRegistroEmail.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                        ContextCompat.getColorStateList(
                            it1, R.color.background_tint_azul
                        )
                    })
                    //se comprueba que la contraseña es de al menos 6 caracteres
                    if (password.trim().length >= 6) {
                        etRegistroContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                            ContextCompat.getColorStateList(
                                it1, R.color.background_tint_azul
                            )
                        })

                        //se comprueba que la contraseña es correcta
                        if (password.trim().equals(confirm_password)) {

                            etRegistroConfirmContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                                ContextCompat.getColorStateList(
                                    it1, R.color.background_tint_azul
                                )
                            })


                            //se han pasado los filtros y se crea la cuenta con el email y la contraseña
                            registrar(username, email, password)

                        } else {
                            val toast1 = Toast.makeText(
                                context,
                                "revise la contraseña", Toast.LENGTH_SHORT
                            )
                            toast1.show()
                            etRegistroConfirmContra.setText("")
                            etRegistroConfirmContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                                ContextCompat.getColorStateList(
                                    it1, R.color.rojo_google
                                )
                            })
                        }
                    } else {
                        etRegistroContra.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                            ContextCompat.getColorStateList(
                                it1, R.color.rojo_google
                            )
                        })
                        val toast1 = Toast.makeText(
                            context,
                            "La contraseña debe de ser de al menos 6  caracteres",
                            Toast.LENGTH_LONG
                        )
                        toast1.show()
                    }

                } else {
                    etRegistroEmail.setBackgroundTintList(activity?.applicationContext?.let { it1 ->
                        ContextCompat.getColorStateList(
                            it1, R.color.rojo_google
                        )
                    })
                    val toast1 = Toast.makeText(
                        context,
                        "revise el email", Toast.LENGTH_SHORT
                    )
                    toast1.show()

                }
            } else {
                val toast1 = Toast.makeText(
                    context,
                    "rellene todos los campos", Toast.LENGTH_SHORT
                )
                toast1.show()
            }


        })

        ivRegistroAtras.setOnClickListener(View.OnClickListener
        {
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
        })

        return root
    }

    private fun registrar(username: String, email: String, contra: String) {
        //primero cogeremos los nombres que ya estan registrados para comprobar que el nuevo usuario no lo repite
        takenNames.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.e("repetido", "" + dataSnapshot.child(username).key)
                //si da false significa que no existe ningun nombre asi registrado
                if (!dataSnapshot.hasChild(username)) {
                    // si no existe creamos al usuario con los metodos que da Firebase
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        email,
                        contra
                    ).addOnCompleteListener {
                        //si el registro ha sido correcto lo guardaremos tambien en la base de datos de real time
                        if (it.isSuccessful) {
                            //guardamos el  usuario en la bbdd con su id, una foto predeterminada, y su nickname
                            //la id será el id que Firebase le ha dado al hacer el registro
                            val uid = auth.uid.toString()
                            val photo= "https://img.freepik.com/premium-vector/gamer-man_961307-25037.jpg?semt=ais_hybrid&w=740"
                            //public Usuario(String nickName, String fotoPerfil, String descripcion, int rol)
                            val u = User(uid, username, email, username/*, "Tu descripcion"*/, photo)

                            //lo guardamos
                            FirebaseAuth.getInstance().currentUser?.let { it1 ->
                                FirebaseDatabase.getInstance("https://tenisclubdroid-default-rtdb.europe-west1.firebasedatabase.app/")
                                    .getReference("users").child(
                                        it1.uid
                                    ).setValue(u).addOnCompleteListener {
                                        //si ha salido bien agregamos su nickame a los nombres cogidos
                                        if (it.isSuccessful) {
                                            takenNames.child(username).setValue(id)
                                            val toast1 = Toast.makeText(
                                                context,
                                                "guardado", Toast.LENGTH_SHORT
                                            )
                                            toast1.show()
                                        } else {
                                            val toast1 = Toast.makeText(
                                                context,
                                                it.result.toString(), Toast.LENGTH_SHORT
                                            )
                                            toast1.show()
                                        }

                                    }
                            }
                            val intent = Intent(activity, LoginActivity::class.java)
                            activity?.startActivity(intent)
                        } else {
                            val toast1 = Toast.makeText(
                                context,
                                "MAL", Toast.LENGTH_SHORT
                            )
                            toast1.show()
                        }

                    }
                }else{
                    Toast.makeText(
                        context,
                        "Usuario repetido ,escoja otro",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("repetido2", dataSnapshot.child(username).key.toString())
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    context,
                    "ERROR",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun comprobarEmail(email: String): Boolean {

        // Patrón para validar el email
        val pattern = Pattern
            .compile(
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            )
        val mather = pattern.matcher(email)
        return if (mather.find() == true) {
            true
        } else {
            false
        }
    }



}