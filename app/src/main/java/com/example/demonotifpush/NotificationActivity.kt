package com.example.demonotifpush

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.demonotifpush.databinding.ActivityMainBinding
import com.example.demonotifpush.databinding.ActivityNotificationBinding

class NotificationActivity : AppCompatActivity() {

    lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val extras= intent.extras

        if(extras!= null){
            val titulo= extras.get("titulo").toString()
            val contenido = extras.get("contenido").toString()
            val nombre = extras.get("nombre").toString()
            val dinero = extras.get("dinero").toString()

            binding.txtTitulo.text      = titulo
            binding.txtContenido.text   = contenido
            binding.txtNombre.text      = nombre
            binding.txtDinero.text      = dinero

        }
    }
}