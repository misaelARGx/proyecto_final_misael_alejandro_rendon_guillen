package com.example.doggallery.model

import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.Sort
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.Dispatchers

/*Esta clase sera utilizada en el viewModel para obtener los datos del WebService*/
class DogRepository {
    private val dogService = RetrofitInstance.service

    suspend fun getDogs(breed: String): Dog {
        return dogService.getDogs(breed)
    }

    suspend fun getDogsBreeds(): ListBreeds {
        return dogService.getBreeds()
    }

    suspend fun getDogsRandom(): Dog {
        return dogService.getDogsRandom()
    }
}


class RealmRespository(var context: Context) {
    private val databaseConfiguration: RealmConfiguration = createRealmConfig(context = context)

    /*Insertar documento*/
    suspend fun insert(favoriteBreed: FavoriteBreed) {
        var realm = Realm.getInstance(databaseConfiguration)// se obtiene instancia de BD
        realm.executeTransactionAwait(Dispatchers.IO) { transaction ->
            realm.insert(FavoriteBreedToRealm(favoriteBreed = favoriteBreed))
        }
    }

    /*Obtener Documentos*/
    suspend fun getAll(): List<FavoriteBreed> {
        var realm = Realm.getInstance(databaseConfiguration)// se obtiene instancia de BD
        var alumnos = mutableListOf<FavoriteBreed>()
        realm.executeTransactionAwait(Dispatchers.IO) { transaction ->
            alumnos.addAll(
                transaction.where(FavoriteBreedRealm::class.java).findAll().sort("_id", Sort.DESCENDING).map { FavoriteBreedRealmToObj(it) }
            )
        }
        return alumnos
    }
}
