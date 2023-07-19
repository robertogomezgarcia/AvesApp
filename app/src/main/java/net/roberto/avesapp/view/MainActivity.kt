package net.roberto.avesapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import net.roberto.avesapp.R
import net.roberto.avesapp.databinding.ActivityMainBinding
import net.roberto.avesapp.model.Usuario
import net.roberto.avesapp.model.Zona
import net.roberto.avesapp.viewModel.MainViewModel
import net.roberto.recyclerviewzonas.adapter.AdapterAves

class MainActivity : AppCompatActivity() {
    private var usuario: Usuario? = null
    private lateinit var shareP: SharedPreferences
    private var titulo = String()
    private lateinit var zonas: List<Zona>
    private lateinit var adapter: AdapterAves
    private lateinit var rvzonas: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Localizaciones"
        titulo = "$title"
        shareP = getSharedPreferences("datos", MODE_PRIVATE)
        getUsuarioSH()
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        rvzonas = findViewById<RecyclerView>(R.id.rvzonas)

        initRV()
        sacazonas()


    }

    private fun getUsuarioSH() {
        val usuarioTXT = shareP.getString("usuario", "nosta")
        if (!usuarioTXT.equals("nosta")) {
            usuario = Gson().fromJson(usuarioTXT, Usuario::class.java)
            title = "$titulo - ${usuario!!.nick}"
        }
    }

    private fun sacazonas() {
        viewModel.getZonas().observe(this, Observer { it ->
            it?.let {
                zonas = it
                adapter.setZonas(zonas)
            }
        })
    }

    private fun initRV() {
        adapter = AdapterAves(this, R.layout.rowzonas)
        rvzonas.layoutManager = LinearLayoutManager(this)
        rvzonas.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                dialogoRegister()
                true
            }
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        removeUsuarioSH()
        Toast.makeText(this,"Logout ok..",Toast.LENGTH_SHORT).show()
    }

    private fun removeUsuarioSH() {
        val editor = shareP.edit()
        editor.remove("usuario")
        editor.commit()
        usuario = null
        title = titulo
    }

    private fun dialogoRegister() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login/Register")
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
        etnick.hint = "Nick"
        textInputLayoutNick.addView(etnick)

        val textInputLayoutPass = TextInputLayout(this)
        textInputLayoutPass.layoutParams = lp
        val etpass = EditText(this)
        etpass.setPadding(0, 80, 0, 80)
        etpass.textSize = 20.0F
        etpass.hint = "Pass"
        textInputLayoutPass.addView(etpass)

        ll.addView(textInputLayoutNick)
        ll.addView(textInputLayoutPass)

        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            val usuario = Usuario(0, etnick.text.toString(), etpass.text.toString())
            loginregister(usuario)
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()
    }


    private fun loginregister(usuario: Usuario) {
        viewModel.getUsuario(usuario).observe(this, Observer { it ->
            if (it == null) {
                viewModel.postUsuario(usuario).observe(this, Observer { it ->
                    it?.let {
                        this.usuario = it
                        saveUsuarioSH(it)
                        Toast.makeText(this, "User created..", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                this.usuario = it
                saveUsuarioSH(it)
                Toast.makeText(this, "Login ok ....", Toast.LENGTH_SHORT).show()

            }
        })
    }

    private fun saveUsuarioSH(it: Usuario) {
        val editor = shareP.edit()
        editor.putString("usuario",Gson().toJson(usuario))
        editor.commit()

        title = "$titulo - ${usuario!!.nick}"
    }

   fun onclickZona(v:View){
       val zona = v.tag as Zona
       val intent= Intent(this,DetalleZona::class.java)
       intent.putExtra("usuario",usuario)
       intent.putExtra("zona",zona)
       startActivity(intent)


   }
}