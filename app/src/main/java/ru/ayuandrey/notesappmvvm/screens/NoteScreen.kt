package ru.ayuandrey.notesappmvvm.screens

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ru.ayuandrey.notesappmvvm.MainViewModel
import ru.ayuandrey.notesappmvvm.MainViewModelFactory
import ru.ayuandrey.notesappmvvm.model.Note
import ru.ayuandrey.notesappmvvm.navigation.NavRoute
import ru.ayuandrey.notesappmvvm.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(navController: NavHostController, viewModel: MainViewModel, noteId: String?) {

    val notes = viewModel.readAllNotes().observeAsState(listOf()).value
    val note = notes.firstOrNull { it.id == noteId?.toInt() } ?: Note(
        title = Constants.Keys.NONE,
        subtitle = Constants.Keys.NONE
    )

    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var title by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var subtitle by remember { mutableStateOf(Constants.Keys.EMPTY) }

    var showBottomSheet by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    //.fillMaxSize()
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        //text = Constants.Keys.TITLE,
                        text = note.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 32.dp)
                    )
                    Text(
                        //text = Constants.Keys.SUBTITLE,
                        text = note.subtitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = {
                    showBottomSheet = true
                    coroutineScope.launch {
                        title = note.title
                        subtitle = note.subtitle
                        bottomSheetState.show()
                    }
                }) {
                    Text(text = Constants.Keys.UPDATE)
                }
                Button(onClick = {
                    coroutineScope.launch {
                        viewModel.deleteNote(note = note) {
                            navController.navigate(NavRoute.Main.route)
                        }
                    }

                }) {
                    Text(text = Constants.Keys.DELETE)
                }
            }
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                onClick = {
                    navController.navigate(NavRoute.Main.route)
                }) {
                Text(text = Constants.Keys.NAV_BACK)
            }
        }
    }


    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = bottomSheetState
        ) {
            Surface {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(all = 32.dp)
                ) {
                    Text(
                        text = Constants.Keys.EDIT_NOTE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },
                        label = { Text(text = Constants.Keys.TITLE) },
                        isError = title.isEmpty()
                    )
                    OutlinedTextField(
                        value = subtitle,
                        onValueChange = {
                            title = it
                        },
                        label = { Text(text = Constants.Keys.SUBTITLE) },
                        isError = subtitle.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            coroutineScope.launch {
                                viewModel.updateNote(note =
                                Note(id = note.id, title = title, subtitle = subtitle)
                            ) {
                                    Log.e( "AAA","ЕБАНОЕ ГОВНО ПРОШЛА ЗАМЕТКАAAA" )
                                navController.navigate(NavRoute.Main.route)
                                }
                            }
                        }
                    ) {
                        Text(text = Constants.Keys.UPDATE_NOTE)
                    }
                }
            }
        }
    }
}





@Preview
@Composable
fun prevNoteScreen() {
    val context = LocalContext.current //Получите доступ к текущему значению a с помощью его свойства.
    val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

    NoteScreen(
        navController = rememberNavController(),
        viewModel = mViewModel,
        noteId = "1"
    )
}