package com.example.doggallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.example.doggallery.model.DogViewModel
import com.example.doggallery.model.Fabrica
import com.example.doggallery.screens.ScreenFavoriteBreeds
import com.example.doggallery.screens.ScreenMain
import com.example.doggallery.ui.theme.DogGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Inicializamos ViewModel*/
        val dogViewModel by viewModels<DogViewModel> {
            Fabrica(this)
        }
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "main") {
                composable("main") { ScreenMain(dogViewModel = dogViewModel, navController = navController) }
                composable(route = "favorite_breed") { ScreenFavoriteBreeds(dogViewModel = dogViewModel, navController = navController) }
            }
        }
    }
}
