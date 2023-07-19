package net.roberto.avesapp.api

import net.roberto.avesapp.model.*

class MainRepository() {
    val service = WebAccess.avesService

    suspend fun getZonas(): List<Zona> {
        val webResponse = service.getZonas().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.zonas
        }
        return emptyList()
    }
    suspend fun getRecursos(idlugar:Long): List<Recurso> {
        val webResponse = service.getRecursos(idlugar).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.recursos
        }
        return emptyList()
    }
    suspend fun getComentario(idrecurso:Long): List<Comentario> {
        val webResponse = service.getComentario(idrecurso).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentarios
        }
        return emptyList()
    }

    suspend fun getUsuario(usuario: Usuario): Usuario?{
        var usuarioresponse:Usuario? = null
        val  webresponse = service.getUsuario(usuario.nick,usuario.pass).await()
        if (webresponse.isSuccessful){
            return webresponse.body()!!.usuario
        }
        return usuarioresponse

    }
    suspend fun postUsuario(usuario: Usuario): Usuario?{
        var usuarioresponse:Usuario? = null
        val  webresponse = service.PostUsuario(usuario).await()
        if (webresponse.isSuccessful){
            return webresponse.body()!!.usuario
        }
        return usuarioresponse

    }
    suspend fun postComentario(idrecurso: Long ,comentario: Comentarioadd): Comentarioadd? {
        var comentarioaddresponse:Comentarioadd? = null
        val webResponse = service.postComentario(idrecurso,comentario).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comentario
        }
        return comentarioaddresponse
    }

}
