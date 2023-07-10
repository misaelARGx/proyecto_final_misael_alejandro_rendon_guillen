package com.example.doggallery.model

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.net.URL

/*URL base de  servicios*/
val URL_BASE = "https://dog.ceo"

/*Interface usada para mapear los metodos que usaremos del servicio web */
@JsonClass(generateAdapter = true)
interface DogService {
    @GET("api/breed/{breed}/images/random")
    suspend fun getDogs(@Path("breed") breed: String): Dog

    @GET("api/breeds/list")
    suspend fun getBreeds(): ListBreeds

    @GET("api/breeds/image/random")
    suspend fun getDogsRandom(): Dog
}

object RetrofitInstance {
    /*Declaremos a Moshi (Convertor JSON -> Class)*/
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    /*Conexion con Retrofit*/
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(URL_BASE).addConverterFactory(MoshiConverterFactory.create(moshi)).build()
    }

    /*servicio Externo de Retrofit*/
    val service: DogService by lazy {
        retrofit.create(DogService::class.java)
    }
}
