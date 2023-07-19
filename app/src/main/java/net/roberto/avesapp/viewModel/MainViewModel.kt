package net.roberto.avesapp.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.roberto.avesapp.api.MainRepository
import net.roberto.avesapp.model.*

// ……

/**
 * Created by pacopulido on 23/02/2021.
 */
class MainViewModel : ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getZonas(): MutableLiveData<List<Zona>> {
        val zonas = MutableLiveData<List<Zona>>()
        GlobalScope.launch(Main) {
            zonas.value = repository.getZonas()
        }
        return zonas
    }

    fun getRecursos(idzona:Long): MutableLiveData<List<Recurso>> {
        val zonas = MutableLiveData<List<Recurso>>()
        GlobalScope.launch(Main) {
            zonas.value = repository.getRecursos(idzona)
        }
        return zonas
    }

    fun getComentario(idrecurso:Long): MutableLiveData<List<Comentario>> {
        val comentarios = MutableLiveData<List<Comentario>>()
        GlobalScope.launch(Main) {
            comentarios.value = repository.getComentario(idrecurso)
        }
        return comentarios
    }

    fun getUsuario(usuario: Usuario): MutableLiveData<Usuario> {
        val usuarioresponse = MutableLiveData<Usuario>()
        GlobalScope.launch(Main){
            usuarioresponse.value = repository.getUsuario(usuario)
        }
        return usuarioresponse
    }

    fun postUsuario(usuario: Usuario): MutableLiveData<Usuario> {
        val usuarioresponse = MutableLiveData<Usuario>()
        GlobalScope.launch(Main){
            usuarioresponse.value = repository.postUsuario(usuario)
        }
        return usuarioresponse
    }

    fun postComentario(idrecurso: Long,comentario: Comentarioadd):MutableLiveData<Comentarioadd> {
        val comentarioresponse= MutableLiveData<Comentarioadd>()
        GlobalScope.launch(Main) {
            comentarioresponse.value = repository.postComentario(idrecurso,comentario)
        }
        return comentarioresponse
    }
// ……..   resto de métodos del ViewModel ………..
}
