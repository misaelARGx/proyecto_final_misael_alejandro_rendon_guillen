package com.example.doggallery.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.realm.RealmModel
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId

@JsonClass(generateAdapter = true)
data class Dog(
    @field:Json(name = "message") val message: String,
    @field:Json(name = "status") val status: String
)

@JsonClass(generateAdapter = true)
data class ListBreeds(
    @field:Json(name = "message") val message: List<String>,
    @field:Json(name = "status") val status: String
)


/*Clase que ayudara a RealmDB a convertir un Documento a un Object*/
@RealmClass
open class FavoriteBreedRealm : RealmModel {
    var _id: ObjectId = ObjectId.get()
    var img: String = ""
    var breed: String = ""
}

data class FavoriteBreed(
    var _id: ObjectId = ObjectId.get(),
    var img: String = "",
    var breed: String = ""
)

/*Funciones de conversion*/
fun FavoriteBreedToRealm(favoriteBreed: FavoriteBreed): FavoriteBreedRealm {
    var favBreedRealm = FavoriteBreedRealm()
    favBreedRealm._id = favoriteBreed._id
    favBreedRealm.img = favoriteBreed.img
    favBreedRealm.breed = favoriteBreed.breed
    return favBreedRealm
}

/*Funciones de conversion*/
fun FavoriteBreedRealmToObj(favoriteBreedRealm: FavoriteBreedRealm): FavoriteBreed {
    var favBreed = FavoriteBreed()
    favBreed._id = favoriteBreedRealm._id
    favBreed.img = favoriteBreedRealm.img
    favBreed.breed = favoriteBreedRealm.breed
    return favBreed
}
