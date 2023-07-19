package net.roberto.avesapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.roberto.avesapp.R
import net.roberto.avesapp.model.Recurso
import net.roberto.avesapp.model.Usuario
import net.roberto.avesapp.model.Zona
import net.roberto.avesapp.viewModel.MainViewModel
import net.roberto.recyclerviewrecursos.adapter.AdapterRecursos

class RecursosMain : AppCompatActivity() {
    private lateinit var recursos: List<Recurso>
    private lateinit var rvrecursos: RecyclerView
    private lateinit var adapter: AdapterRecursos
    private lateinit var viewModel: MainViewModel
    private lateinit var zona: Zona
    private var usuario: Usuario? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recursos_main)
        usuario =intent.getSerializableExtra("usuario")as Usuario?
        zona = intent.getSerializableExtra("zona")as Zona
        rvrecursos = findViewById<RecyclerView>(R.id.rvrecursos)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initRV()
        sacarecursos()
    }

    private fun sacarecursos() {
        viewModel.getRecursos(zona.id).observe(this, Observer { it->
            it?.let{
                recursos = it
                adapter.setRecursos(recursos)
            }
        })

    }

    private fun initRV() {
        adapter = AdapterRecursos(this, R.layout.rowrecurso)
        rvrecursos.layoutManager = LinearLayoutManager(this)
        rvrecursos.adapter = adapter
    }
    fun onclickRecurso(v: View){
        val recurso = v.tag as Recurso
        val intent = Intent(this,DetalleRecurso::class.java)
        intent.putExtra("recurso",recurso)

        intent.putExtra("usuario",usuario)
        startActivity(intent)
    }
}