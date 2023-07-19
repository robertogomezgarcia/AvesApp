package net.roberto.avesapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import net.roberto.avesapp.R
import net.roberto.avesapp.model.Comentario
import net.roberto.avesapp.model.Comentarioadd
import net.roberto.avesapp.model.Recurso
import net.roberto.avesapp.model.Usuario
import net.roberto.avesapp.viewModel.MainViewModel
import net.roberto.recyclerviewcomentarios.adapter.AdapterComentario
import java.text.SimpleDateFormat
import java.util.*

class DetalleRecurso : AppCompatActivity() {

    private lateinit var comentario: Comentarioadd
    private lateinit var fabcomentario: FloatingActionButton
    private lateinit var tvtitulorowdetallerecurso: TextView
    private lateinit var ivrowdetallerecurso: ImageView
    private lateinit var comentarios: List<Comentario>
    private lateinit var adapter: AdapterComentario
    private lateinit var rvcomentarios: RecyclerView
    private lateinit var clcomentario: ConstraintLayout
    private lateinit var viewModel: MainViewModel
    private lateinit var recurso: Recurso
    private var usuario: Usuario?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_recurso)
        usuario= intent.getSerializableExtra("usuario")as Usuario?
        recurso =intent.getSerializableExtra("recurso")as Recurso
        viewModel = ViewModelProvider(this,).get(MainViewModel::class.java)
        clcomentario= findViewById<ConstraintLayout>(R.id.clcomentario)

        rvcomentarios = findViewById<RecyclerView>(R.id.rvcomentarios)
        ivrowdetallerecurso = findViewById<ImageView>(R.id.ivrowdetallerecurso)
        tvtitulorowdetallerecurso= findViewById<TextView>(R.id.tvtitulorowdetallerecurso)
        fabcomentario = findViewById<FloatingActionButton>(R.id.fabcomentario)
        initRV()
        tvtitulorowdetallerecurso.text = recurso.titulo
        Picasso.get().load("${recurso.url}").into(ivrowdetallerecurso)
        sacacomentarios()

        fabcomentario.setOnClickListener { view ->
            onclickfabcomentario()
        }

    }

    private fun onclickfabcomentario() {

            if (usuario!=null){
                dialogocomentario()

            }else{
                Toast.makeText(this, "NO LOGIN ZOQUETE .....   :)", Toast.LENGTH_SHORT).show()
            }
    }

    private fun dialogocomentario() {
        val fecha = Date()
        val sdf = SimpleDateFormat("2022-03-04 13:37:05")
        val fechaTXT = sdf.format(fecha)



        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add comentario")
        val ll = LinearLayout(this)
        ll.setPadding(30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        lp.setMargins(0, 50, 0, 50)

        val textInputLayoutNick = TextInputLayout(this)
        textInputLayoutNick.layoutParams = lp
        val etnick = EditText(this)
        etnick.setPadding(0, 80, 0, 80)
        etnick.textSize = 20.0F
        etnick.hint = "Comentario"
        textInputLayoutNick.addView(etnick)

        ll.addView(textInputLayoutNick)

        builder.setView(ll)
        builder.setPositiveButton("Aceptar") { dialog, which ->

//            comentario.comentario = etnick.text.toString()
            val comentario = Comentarioadd(0, recurso.id, usuario!!.id, fechaTXT, etnick.text.toString())
//            comentario.fecha = fechaTXT
//            comentario.usuario = usuario!!.id
//            comentario.recurso = recurso.id
//            comentario.id = 0
            addcomentario(comentario)
            

        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }

    private fun addcomentario(comentario: Comentarioadd) {
        viewModel.postComentario(recurso.id, comentario).observe(this, Observer { it ->
            it?.let {
                Toast.makeText(this, "Has comentado", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sacacomentarios() {
        viewModel.getComentario(recurso.id).observe(this, Observer { it ->
            it?.let{
                comentarios = it
                adapter.setComentarios(comentarios)
            }
        })
    }



    private fun initRV() {
        adapter = AdapterComentario(this, R.layout.rowcomentario)
        rvcomentarios.layoutManager = LinearLayoutManager(this)
        rvcomentarios.adapter = adapter
    }
}