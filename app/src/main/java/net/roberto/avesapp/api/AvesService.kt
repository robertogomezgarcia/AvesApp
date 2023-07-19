package net.roberto.avesapp.api

import kotlinx.coroutines.Deferred
import net.roberto.avesapp.model.Comentarioadd
import net.roberto.avesapp.model.Respuesta
import net.roberto.avesapp.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface AvesService {

    @GET("zonas")
    fun getZonas(): Deferred<Response<Respuesta>>

    // idbar en la url
    @GET("zona/{idzona}/recursos")
    fun getRecursos(@Path("idzona") idzona: Long): Deferred<Response<Respuesta>>

    @GET("recurso/{idrecurso}/comentarios")
    fun getComentario(@Path("idrecurso") idrecurso: Long): Deferred<Response<Respuesta>>

    // nick y pass como variables en la url?nick=paco&pass=paco
    @GET("usuario")
    fun getUsuario(
        @Query("nick") nick: String,
        @Query("pass") pass: String): Deferred<Response<Respuesta>>

    // post con objeto en json
    @POST("usuario")
    fun PostUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    // post con variables sueltas
    @FormUrlEncoded
    @POST("recurso/{idrecurso}/comentario")
    fun postComentario(@Path("idrecurso") idrecurso: Long,
                   @Field("comentario") comentario: Comentarioadd): Deferred<Response<Respuesta>>

}
