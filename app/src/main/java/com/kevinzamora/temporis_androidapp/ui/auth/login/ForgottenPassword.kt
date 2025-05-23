package com.kevinzamora.temporis_androidapp.ui.auth.Login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.kevinzamora.temporis_androidapp.R
import com.google.firebase.auth.FirebaseAuth
import com.kevinzamora.temporis_androidapp.ui.auth.login.LoginActivity
import java.util.regex.Pattern


class ForgottenPassword : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_forgot_password, container, false)
        val ivRegistroAtras = root.findViewById<ImageView>(R.id.ivRegistroAtras2)
        val etContraOlvidadaEmail = root.findViewById<EditText>(R.id.etContraOlvidadaEmail)
        val btnContraOlvidadaEnviar = root.findViewById<Button>(R.id.btnContraOlvidadaEnviar)


        ivRegistroAtras.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(intent)
        })

        btnContraOlvidadaEnviar.setOnClickListener(View.OnClickListener {

            val email = etContraOlvidadaEmail.text.toString()
            //se comprueba que haya introducido un email
            if (!email.isEmpty()) {
                email.trim()

                if (comprobarEmail(email)) {
                    //usamos el metodo del servicio de Firebase para restablecer la contraseña y volvemos al login
                    auth = FirebaseAuth.getInstance()
                    auth.sendPasswordResetEmail(email).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val toast1 = Toast.makeText(
                                context,
                                "se le ha enviado un correo ", Toast.LENGTH_SHORT
                            )
                            toast1.show()
                            val intent = Intent(activity, LoginActivity::class.java)
                            activity?.startActivity(intent)
                        } else {
                            val toast1 = Toast.makeText(
                                context,
                                "ha ocurrido un error ", Toast.LENGTH_SHORT
                            )
                            toast1.show()
                        }
                    }


                } else {
                    val toast1 = Toast.makeText(
                        context,
                        "compruebe el email", Toast.LENGTH_SHORT
                    )
                    toast1.show()
                }
            } else {
                val toast1 = Toast.makeText(
                    context,
                    "rellene el email", Toast.LENGTH_SHORT
                )
                toast1.show()
            }


        })


        return root;
    }

    /**
     * Metodo para comprobar el formato de un email
     */
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