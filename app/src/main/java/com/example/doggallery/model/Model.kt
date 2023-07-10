package com.example.doggallery.model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.RuntimeException

/*en este archivo crearemos el Viewmodel que nos ayude a mandar los datos del WebService a la vista*/
data class DataState(
    /*Estado inicial lista de perros*/
    val dog: Dog = Dog(
        message = "https://images.dog.ceo/breeds/terrier-dandie/n02096437_2019.jpg",
        status = "success"
    ),
    val breedSearch: String = "",
    val favoriteBreeds: List<FavoriteBreed> = emptyList()
)

/*en este archivo crearemos el Viewmodel que nos ayude a mandar los datos del WebService a la vista*/
data class DataStateBreed(
    /*Estado inicial lista de perros*/
    val listBreed: ListBreeds = ListBreeds(
        message = emptyList(),
        status = "success"
    )

)

/*Esta clase permite que el viewmodel reciba argumentos en su constructor*/
class Fabrica(private val contexto: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DogViewModel(context = contexto) as T
    }
}

/*ViewModel para la app*/
class DogViewModel(context: Context) : ViewModel() {
    private val repository = DogRepository()
    private var repositoryRealm: RealmRespository? = null
    private val scope: CoroutineScope = viewModelScope
    var state by mutableStateOf<DataState>(DataState())
        private set
    var stateBreed by mutableStateOf<DataStateBreed>(DataStateBreed())
        private set

    init {
        repositoryRealm = RealmRespository(context = context)
    }

    /*Este metodo modifica el estado de la lista de dogs usando los datos del repositorio*/
    fun fetchRandomBreedDog() {
        try {
            scope.launch(Dispatchers.IO) {
                if (state.breedSearch.isNotEmpty()) {
                    var perro = repository.getDogs(state.breedSearch)/*En este momento se detiene hasta que obtenga la respuesta del WebService*/
                    state = state.copy(
                        dog = perro
                    )
                }
            }
        } catch (ex: Exception) {
            Log.e("ERRROR", ex.message.toString())
        }
    }

    fun changeStateBreedSearch(breedSearch: String) {
        state = state.copy(
            breedSearch = breedSearch
        )
    }

    /*Este metodo modifica el estado de la lista de dogs usando los datos del repositorio*/
    fun changeStateDogs() {
        try {
            scope.launch(Dispatchers.IO) {
                if (state.breedSearch.isNotEmpty()) {
                    fetchRandomBreedDog()
                } else {
                    var perro = repository.getDogsRandom()/*En este momento se detiene hasta que obtenga la respuesta del WebService*/
                    state = state.copy(
                        dog = perro
                    )
                }
            }
        } catch (ex: Exception) {
            Log.e("ERRROR", ex.message.toString())
        }
    }

    /*Este metodo modifica el estado de la lista de dogs usando los datos del repositorio*/
    fun fetchBreeds() {
        try {
            scope.launch(Dispatchers.IO) {
                var breeds = repository.getDogsBreeds()/*En este momento se detiene hasta que obtenga la respuesta del WebService*/
                stateBreed = stateBreed.copy(
                    listBreed = breeds
                )
            }
        } catch (ex: Exception) {
            Log.e("ERRROR", ex.message.toString())
        }
    }

    /*Favorite Breed*/
    fun fetchFavoriteBreedsList(favoriteBreeds: List<FavoriteBreed>) {
        state = state.copy(
            favoriteBreeds = favoriteBreeds
        )
    }

    fun getFavoriteBreeds() {
        scope.launch(Dispatchers.IO) {
            fetchFavoriteBreedsList(repositoryRealm?.getAll() ?: emptyList())
        }
    }

    fun markAsFav() {
        if (state.dog.message.isNotEmpty()) {
            scope.launch(Dispatchers.IO) {
                repositoryRealm?.insert(FavoriteBreed(breed = state.breedSearch ?: "Sin raza", img = state.dog.message))
            }
            getFavoriteBreeds()
        } else {
            throw RuntimeException("Elementos Vacios")
        }
    }

}
