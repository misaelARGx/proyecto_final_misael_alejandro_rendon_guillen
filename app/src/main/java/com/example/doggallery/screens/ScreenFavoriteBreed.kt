package com.example.doggallery.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.doggallery.model.DogViewModel
import com.example.doggallery.model.FavoriteBreed

@Composable
fun ScreenFavoriteBreeds(dogViewModel: DogViewModel, navController: NavController) {
    var state = dogViewModel.state
    dogViewModel.getFavoriteBreeds()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Mis razas favoritas <3",
            style = MaterialTheme.typography.headlineMedium,
            fontFamily = FontFamily.Cursive,
            modifier = Modifier.offset(x = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp)
        ) {
            items(state.favoriteBreeds) { photo ->
                TarjetaFavoriteBreed(photo)
            }
        }
    }
}


@Composable
fun TarjetaFavoriteBreed(favBreed: FavoriteBreed) {
    Row() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RectangleShape),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = favBreed.img,
                contentDescription = "perro",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RectangleShape),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .width(80.dp)
                    .background(Color.White)
            ) {
                Text(
                    text = favBreed.breed,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewScreenFavoriteBreeds() {
    ScreenFavoriteBreeds(navController = rememberNavController(), dogViewModel = DogViewModel(LocalContext.current))
}
