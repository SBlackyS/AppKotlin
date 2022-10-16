package com.example.practica01v21

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.practica01v21.databinding.ActivityMainNuevoBinding

class MainActivityNuevo : AppCompatActivity() {

    //Vincular las vistas con MainActivityNuevo
    private lateinit var binding: ActivityMainNuevoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainNuevoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val txtNombre = binding.txtNombre.text
            val txtCue = binding.txtCuenta.text
            val txtCorr = binding.txtCorreo.text
            val txtImg = binding.txtImage.text

            val intento2 = Intent(this,MainActivity::class.java)
            intento2.putExtra("mensaje","nuevo")
            intento2.putExtra("nombre","${txtNombre}")
            intento2.putExtra("cuenta","${txtCue}")
            intento2.putExtra("correo","${txtCorr}")
            intento2.putExtra("imagen","${txtImg}")

            startActivity(intento2)
        }

    }
}