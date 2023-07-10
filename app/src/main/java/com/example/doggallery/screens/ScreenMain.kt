package com.example.doggallery.screens

import android.content.Context
import android.graphics.drawable.shapes.Shape
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.doggallery.R
import com.example.doggallery.model.Dog
import com.example.doggallery.model.DogViewModel
import com.example.doggallery.model.FavoriteBreed
import com.example.doggallery.model.ListBreeds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(dogViewModel: DogViewModel, navController: NavController) {
    var state = dogViewModel.state
    var stateBreed = dogViewModel.stateBreed
    var context = LocalContext.current

    dogViewModel.fetchBreeds()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DropdownDemo(stateBreed.listBreed, dogViewModel)
        }
        TarjetaPerro(dog = state.dog, dogViewModel = dogViewModel, context = context)
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    dogViewModel.changeStateDogs()
                }, modifier = Modifier
                    .weight(1f, fill = false)
                    .width(150.dp)
            ) {
                Text(text = "Buscar")
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate("favorite_breed")
                    Toast.makeText(context, "Ver favoritos", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier
                    .weight(1f, fill = false)
                    .width(190.dp)
            ) {
                Text(text = "Ver mis favoritos")
            }
        }
    }


}

@Composable
fun TarjetaPerro(dog: Dog, dogViewModel: DogViewModel, context: Context) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(350.dp)
                .padding(10.dp)

        ) {
            AsyncImage(
                model = dog.message,
                contentDescription = "perro",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .border(2.dp, Color.LightGray, CircleShape),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = {
                        try {
                            dogViewModel.markAsFav()
                            Toast.makeText(context, "Agregado a mis favoritos", Toast.LENGTH_SHORT).show()
                        } catch (ex: Exception) {
                            Toast.makeText(context, "Error ${ex.message.toString()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "add to fav"
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownDemo(breed: ListBreeds, dogViewModel: DogViewModel) {
    var isExpanded by remember { mutableStateOf(false) }
    val items = breed.message
    var selectedoptionText by remember { mutableStateOf("") }

    Row(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(modifier = Modifier.width(280.dp), expanded = isExpanded, onExpandedChange = {
            isExpanded = it
        }) {
            TextField(
                value = selectedoptionText,
                onValueChange = {

                },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }, modifier = Modifier.fillMaxWidth()) {
                items.forEach { selectionOption ->
                    DropdownMenuItem(text = { Text(text = selectionOption) }, onClick = {
                        selectedoptionText = selectionOption
                        isExpanded = false
                        dogViewModel.changeStateBreedSearch(selectionOption)
                    })
                }
            }
        }
        Button(
            onClick = {
                dogViewModel.changeStateBreedSearch("")
                selectedoptionText = ""
            }, shape = RectangleShape, modifier = Modifier
                .width(120.dp)
                .height(55.dp)
        ) {
            Text(text = "Limpiar")
        }
    }
}


@Preview
@Composable
fun PreviewScreenMain() {
    ScreenMain(navController = rememberNavController(), dogViewModel = DogViewModel(LocalContext.current))
}
