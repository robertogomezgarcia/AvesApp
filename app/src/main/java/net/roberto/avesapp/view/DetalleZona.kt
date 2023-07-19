package net.roberto.avesapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import net.roberto.avesapp.R
import net.roberto.avesapp.model.Usuario
import net.roberto.avesapp.model.Zona

class DetalleZona : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var tvpresentacion: TextView
    private lateinit var tvformaciones: TextView
    private lateinit var tvlocalizaciondetalle: TextView
    private lateinit var tvnombredetalle: TextView
    private lateinit var clzona: ConstraintLayout
    private lateinit var zona: Zona
    private var usuario: Usuario? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_zona)


        usuario = intent.getSerializableExtra("usuario")as Usuario?
       zona= intent.getSerializableExtra("zona")as Zona
        clzona= findViewById<ConstraintLayout>(R.id.clzona)
        tvpresentacion= findViewById<TextView>(R.id.tvpresentacion)
        tvformaciones= findViewById<TextView>(R.id.tvformaciones)
        tvlocalizaciondetalle=findViewById<TextView>(R.id.tvlocalizaciondetalle)
        tvnombredetalle= findViewById<TextView>(R.id.tvnombredetalle)
        fab = findViewById<FloatingActionButton>(R.id.fabcomentario)

        title="Zona"
        tvnombredetalle.text=zona.nombre
        tvlocalizaciondetalle.text="${zona.localizacion}(${zona.geom_lat},${zona.geom_lon}"
        tvformaciones.text = zona.formaciones_principales
        tvpresentacion.text=zona.presentacion

        fab.setOnClickListener { view ->
            onclickfab()
        }


    }

    private fun onclickfab() {

            intent = Intent(this, RecursosMain::class.java)
            intent.putExtra("zona", zona)
            intent.putExtra("usuario", usuario)
            startActivity(intent)

    }


}