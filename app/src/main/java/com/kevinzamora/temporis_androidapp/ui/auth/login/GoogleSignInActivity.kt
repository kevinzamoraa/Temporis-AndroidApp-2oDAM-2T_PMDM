/*
package com.kevinzamora.temporis_androidapp.ui.auth.login

import com.kevinzamora.temporis_androidapp.model.User
import androidx.core.app.ActivityCompat.startActivityForResult

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseUser
import com.kevinzamora.temporis_androidapp.R

class GoogleSignInActivity : AppCompatActivity(), OAuth2Operations {
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private lateinit var btnGoogleSignIn: Button

    companion object {
        private const val RC_SIGN_IN = 1001
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        btnGoogleSignIn.setOnClickListener {
            signInWithGoogle()
        }

        auth = FirebaseAuth.getInstance()
        btnGoogleSignIn = findViewById(R.id.btnGoogleSignIn)

    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                showMessage("Error en la autenticación de Google")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveGoogleUserToFirestore(auth.currentUser!!)
                } else {
                    showMessage("Error al autenticar con Firebase")
                }
            }
    }

    private fun saveGoogleUserToFirestore(user: FirebaseUser) {
        val userData = hashMapOf(
            "uid" to user.uid,
            "email" to user.email ?: "",
            "name" to user.name ?: "",
            "accessibleMode" to false,
            "googleSignIn" to true
        )

        FirebaseFirestore.getInstance().collection("users")
            .document(user.uid)
            .set(userData)
            .addOnSuccessListener {
                startMainActivity()
            }
            .addOnFailureListener { e ->
                showMessage("Error al guardar datos: ${e.message}")
            }
    }
}
*/
