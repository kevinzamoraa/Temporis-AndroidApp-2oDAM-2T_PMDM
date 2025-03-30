package layout.login

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kevinzamora.temporis_androidapp.databinding.FragmentLoginBinding
import androidx.navigation.fragment.findNavController

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// Autenticación con Google
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

// View binding
import androidx.viewbinding.ViewBinding
import com.temporis.databinding.FragmentLoginBinding
import com.temporis.databinding.FragmentDashboardBinding
import com.temporis.databinding.FragmentTimerBinding

// Componentes de UI y diálogos
import android.app.Dialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialTextInputEditText
import com.google.android.material.button.MaterialButton
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast

class LoginFragment : Fragment() {
    
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        setupGoogleSignIn()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        binding.btnLoginEmail.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            loginWithEmail(email, password)
        }

        binding.btnLoginGoogle.setOnClickListener {
            signInWithGoogle()
        }

        binding.btnLoginAnonymous.setOnClickListener {
            loginAnonymous()
        }

        binding.tvForgotPassword.setOnClickListener {
            showForgotPasswordDialog()
        }
    }

    private fun loginWithEmail(email: String, password: String) {
        if (isValidInput(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        navigateToDashboard()
                    } else {
                        showMessage("Error al iniciar sesión: ${task.exception?.message}")
                    }
                }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, Companion.RC_SIGN_IN)
    }

    private fun loginAnonymous() {
        auth.signInAnonymously()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    navigateToDashboard()
                } else {
                    showMessage("Error al iniciar sesión anónima: ${task.exception?.message}")
                }
            }
    }

    private fun showForgotPasswordDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_forgot_password)

        val etEmail = dialog.findViewById<MaterialTextInputEditText>(R.id.etEmail)
        val btnSend = dialog.findViewById<MaterialButton>(R.id.btnSend)

        btnSend.setOnClickListener {
            val email = etEmail.text.toString()
            if (!email.isNullOrEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showMessage("Se ha enviado un correo para restablecer tu contraseña")
                            dialog.dismiss()
                        } else {
                            showMessage("Error al enviar el correo: ${task.exception?.message}")
                        }
                    }
            }
        }

        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Companion.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                showMessage("Error en la autenticación de Google: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    navigateToDashboard()
                } else {
                    showMessage("Error al autenticar con Google: ${task.exception?.message}")
                }
            }
    }

    private fun isValidInput(email: String, password: String): Boolean {
        return !email.isNullOrEmpty() &&
                !password.isNullOrEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                password.length >= 6
    }

    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val RC_SIGN_IN = 123 // Cualquier número constante para el requestCode
    }
}