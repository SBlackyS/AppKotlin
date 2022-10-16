package com.example.practica01v21

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica01v21.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //Vincular las vistas con MainActivity
    private lateinit var binding: ActivityMainBinding
    // ArrayList of class ItemsViewModel
    val data = ArrayList<Alumno>()

    private lateinit var adapter : AlumnoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)



        //Agregar elementos a la lista
        data.add(
            Alumno(
                "Celia Claire",
                "20189987",
                "celiaclaire@hotmail.com",
                "https://i.pinimg.com/736x/31/50/79/3150796708769332bdaddd16b4416e86.jpg"
            )
        )
        data.add(
            Alumno(
                "Naruto Uzumaki",
                "20998765",
                "naru@ucol.mx",
                "https://i.pinimg.com/736x/ea/59/8a/ea598a8f6b6a74016a5a776202ced042.jpg"
            )
        )
        data.add(
            Alumno(
                "Sasuke Uchiha",
                "20229876",
                "sasuuchi@ucol.mx",
                "https://i.pinimg.com/564x/4a/50/e9/4a50e93c205e7f14ea5385fb792d1e49.jpg"
            )
        )
        data.add(
            Alumno(
                "Nagisa Shiota",
                "20121495",
                "nagisa@gmail.com",
                "https://i.pinimg.com/736x/9e/8a/db/9e8adb86d64297bd0a2264bf9dd84511.jpg"
            )
        )


        // This will pass the ArrayList to our Adapter
        val adapter = AlumnoAdapter(this, data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        //Accion de 3 puntos
        adapter.setOnItemClickListener(object : AlumnoAdapter.ClickListener {
            override fun onItemClick(view: View, position: Int) {
                Toast.makeText(
                    this@MainActivity,
                    "Click en el item: ${position}",
                    Toast.LENGTH_LONG
                ).show()
                itemOptionsMenu(position)
            }
        })

        //Recibiendo información del MainActivityNuevo
        val parExtras = intent.extras
        val msg = parExtras?.getString("mensaje")
        val nombre = parExtras?.getString("nombre")
        val cuenta = parExtras?.getString("cuenta")
        val correo = parExtras?.getString("correo")
        val imagen = parExtras?.getString("imagen")

        if (msg == "nuevo") {
            val insertIndex: Int = data.count()
            data.add(insertIndex, Alumno("${nombre}", "${cuenta}", "${correo}", "${imagen}"))
            adapter.notifyItemInserted(insertIndex)
        }

        //Click en boton de agregar
        binding.faButton.setOnClickListener {
            val intento1 = Intent(this, MainActivityNuevo::class.java)
            //  Pasar valores por parámetro
            // intento1.putExtra("valor1","Holis")
            startActivity(intento1)
        }


    }

    private fun itemOptionsMenu(position: Int) {
        val popupMenu = PopupMenu(this,binding.recyclerview[position].findViewById(R.id.textViewOptions))
        popupMenu.inflate(R.menu.options_menu)
//Para cambiarnos de activity
        val intento2 = Intent(this,MainActivityNuevo::class.java)
//Implementar el click en el item
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.borrar -> {
                        val tmpAlum = data[position]
                        data.remove(tmpAlum)
                        adapter.notifyDataSetChanged()
                        return true
                    }
                    R.id.editar ->{
                        //Tomamos los datos del alumno, en la posición de la lista donde hicieron click
                        val nombre = data[position].nombre
                        val cuenta = data[position].cuenta
                        val correo = data[position].correo
                        val image = data[position].imagen
                        //En position tengo el indice del elemento en la lista
                        val idAlum: Int = position
                        intento2.putExtra("mensaje","edit")
                        intento2.putExtra("nombre","${nombre}")
                        intento2.putExtra("cuenta","${cuenta}")
                        intento2.putExtra("correo","${correo}")
                        intento2.putExtra("image","${image}")
                        //Pasamos por extras el idAlum para poder saber cual editar de la lista (ArrayList)
                        intento2.putExtra("idA",idAlum)
                        startActivity(intento2)
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }


}