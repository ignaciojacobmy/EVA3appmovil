package com.example.eva3android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.eva3android.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usar try-catch para inicializar la vista y FirebaseAuth
        try {
            // Binding para usar ViewBinding
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Inicializar FirebaseAuth
            auth = FirebaseAuth.getInstance()

            // Listener para el botón de Login
            binding.btnLogin.setOnClickListener {
                try {
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()

                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        loginUser(email, password)
                    } else {
                        Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error en el proceso de login: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            // Listener para el botón de Registro
            binding.tvRegistrar.setOnClickListener {
                try {
                    startActivity(Intent(this, RegisterActivity::class.java))
                } catch (e: Exception) {
                    Toast.makeText(this, "Error al intentar abrir el registro: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Error al iniciar la actividad: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loginUser(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                        // Redirigir a la pantalla principal o home
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(this, "Error al intentar iniciar sesión: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}




