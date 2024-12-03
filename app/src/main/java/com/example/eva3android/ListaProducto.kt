package com.example.eva3android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eva3android.Adapter.AdapterProducto
import com.example.eva3android.Models.Producto
import com.example.eva3android.databinding.ActivityListaProductoBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListaProducto : AppCompatActivity() {

    private lateinit var binding: ActivityListaProductoBinding

    private lateinit var database: DatabaseReference

    private lateinit var productosList: ArrayList<Producto>

    private lateinit var adapterProducto: AdapterProducto

    private lateinit var productoRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        productoRecyclerView = binding.rvProductos
        productoRecyclerView.layoutManager = LinearLayoutManager(this)
        productoRecyclerView.hasFixedSize()

        productosList = arrayListOf<Producto>()

        getProductos()

        // Configurar el botón para regresar al HomeActivity
        binding.btnBackToHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish() // Cierra la actividad actual
        }
    }

    private fun getProductos() {
        database = FirebaseDatabase.getInstance().getReference("Productos")

        database.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (productosSnapshot in snapshot.children){
                        val producto = productosSnapshot.getValue(Producto::class.java)
                        productosList.add(producto!!)
                    }
                    adapterProducto = AdapterProducto(productosList)
                    productoRecyclerView.adapter = adapterProducto
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}