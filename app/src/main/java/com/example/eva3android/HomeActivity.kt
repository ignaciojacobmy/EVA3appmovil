package com.example.eva3android

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.eva3android.databinding.ActivityHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)

        // Configurar DrawerLayout con Toolbar
        val toggle = ActionBarDrawerToggle(
            this,
            binding.root as DrawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        (binding.root as DrawerLayout).addDrawerListener(toggle)
        toggle.syncState()

        // Manejo de clics en el menú
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Home -> {
                    // Acción para la opción "Inicio"
                    showToast("Inicio seleccionado")
                }

                R.id.add -> {
                    // Redirigir a CrearProductos
                    val intent = Intent(this, VerProductosActivity::class.java)
                    startActivity(intent)
                }

                R.id.MyList -> {
                    // Redirigir a ListaProducto
                    val intent = Intent(this, ListaProducto::class.java)
                    startActivity(intent)
                }

                R.id.Logout -> {
                    // Acción para cerrar sesión
                    auth.signOut()
                    showToast("Sesión cerrada")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }

                else -> {
                    showToast("Opción no implementada")
                }
            }
            (binding.root as DrawerLayout).closeDrawers() // Cierra el Drawer
            true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}


