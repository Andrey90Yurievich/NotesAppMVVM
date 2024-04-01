package ru.ayuandrey.notesappmvvm.screens


import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.ayuandrey.notesappmvvm.MainViewModel
import ru.ayuandrey.notesappmvvm.MainViewModelFactory
import ru.ayuandrey.notesappmvvm.model.Note
import ru.ayuandrey.notesappmvvm.navigation.NavRoute
import ru.ayuandrey.notesappmvvm.ui.theme.NotesAppMVVMTheme
import ru.ayuandrey.notesappmvvm.utils.Constants
import ru.ayuandrey.notesappmvvm.utils.DB_TYPE
import ru.ayuandrey.notesappmvvm.utils.TYPE_FIREBASE
import ru.ayuandrey.notesappmvvm.utils.TYPE_ROOM


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavHostController, viewModel: MainViewModel) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value


    //val notes = mViewModel.readTest.observeAsState(listOf()).value

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavRoute.Add.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add, contentDescription = "Add Icons",
                    tint = Color.White
                    )
            }
        }
    ) {
        //Column {
            //NoteItem(title = "Note 1", subtitle = "Subtitle for note 1", navController = navController)
            //NoteItem(title = "Note 2", subtitle = "Subtitle for note 2", navController = navController)
            //NoteItem(title = "Note 3", subtitle = "Subtitle for note 3", navController = navController)
            //NoteItem(title = "Note 4", subtitle = "Subtitle for note 4", navController = navController)
       // }

        LazyColumn {
            items (notes) {note ->
                NoteItem(note = note, navController = navController)
            }
        }

    }
}

@Composable
fun NoteItem(note: Note, navController: NavHostController) {
    val noteId = when(DB_TYPE.value) {
        TYPE_FIREBASE -> note.firebaseId
        TYPE_ROOM -> note.id
        else -> Constants.Keys.EMPTY
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 24.dp)
            .clickable {
                //navController.navigate(NavRoute.Note.route + "/${note.id}")
                navController.navigate(NavRoute.Note.route + "/${noteId}")
            },
        elevation  = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = note.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = note.subtitle
            )
        }
    }
}


@Preview
@Composable
fun prevMAinScreen() {
    NotesAppMVVMTheme {
        val context = LocalContext.current //Получите доступ к текущему значению a с помощью его свойства.
        val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

        MainScreen(navController = rememberNavController(), viewModel = mViewModel)
    }
}