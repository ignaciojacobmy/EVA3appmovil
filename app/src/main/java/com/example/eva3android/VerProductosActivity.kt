package com.example.eva3android

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.eva3android.Models.Producto
import com.example.eva3android.databinding.ActivityVerProductosBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class VerProductosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerProductosBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityVerProductosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Referencia a la base de datos
        database = FirebaseDatabase.getInstance().getReference("Productos")

        // Botón para guardar producto
        binding.btnGuardar.setOnClickListener {
            val nombre = binding.etNombreProducto.text.toString()
            val precio = binding.etPrecioProducto.text.toString()
            val description = binding.etDescripcionProducto.text.toString()

            val id = database.push().key
            if (id == null) {
                Snackbar.make(binding.root, "Error generando ID para el producto", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (nombre.isEmpty()) {
                binding.etNombreProducto.error = "Por favor ingresar nombre"
                return@setOnClickListener
            }
            if (precio.isEmpty()) {
                binding.etPrecioProducto.error = "Por favor ingresar precio"
                return@setOnClickListener
            }
            if (description.isEmpty()) {
                binding.etDescripcionProducto.error = "Por favor ingresar descripción"
                return@setOnClickListener
            }

            val producto = Producto(id, nombre, precio, description)

            database.child(id).setValue(producto)
                .addOnSuccessListener {
                    resetForm()
                    Snackbar.make(binding.root, "Producto Agregado", Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root, "Error al guardar producto", Snackbar.LENGTH_SHORT).show()
                }
        }

        // Botón para ver lista de productos
        binding.btnVer.setOnClickListener {
            val intent = Intent(this, ListaProducto::class.java)
            startActivity(intent)
        }
    }

    // Método para limpiar el formulario
    private fun resetForm() {
        binding.etNombreProducto.text?.clear()
        binding.etPrecioProducto.text?.clear()
        binding.etDescripcionProducto.text?.clear()
    }
}
