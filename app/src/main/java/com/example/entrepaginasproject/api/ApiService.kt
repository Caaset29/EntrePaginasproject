package com.example.entrepaginasproject.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST



interface ApiService {

    @FormUrlEncoded
    @POST("usuarios/registrar.php")
    fun registrarUsuario(
        @Field("nombre_completo") nombre: String,
        @Field("email") email: String,
        @Field("password") pass: String
    ): Call<LoginResponse> // Usamos la misma respuesta genérica (success/message)

    @FormUrlEncoded
    @POST("usuarios/login.php")
    fun loginUsuario(
        @Field("email") email: String,
        @Field("password") pass: String
    ): Call<LoginResponse>

    @GET("libros/mostrar.php")
    fun obtenerLibros(): Call<List<Libro>>
}