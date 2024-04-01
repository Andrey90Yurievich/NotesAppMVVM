package ru.ayuandrey.notesappmvvm.screens

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import ru.ayuandrey.notesappmvvm.navigation.NavRoute
import ru.ayuandrey.notesappmvvm.ui.theme.NotesAppMVVMTheme
import ru.ayuandrey.notesappmvvm.utils.Constants
import ru.ayuandrey.notesappmvvm.utils.Constants.Keys.FIREBASE_DATABASE
import ru.ayuandrey.notesappmvvm.utils.Constants.Keys.ROOM_DATABASE
import ru.ayuandrey.notesappmvvm.utils.Constants.Keys.WHAT_WILL_WE_USE
import ru.ayuandrey.notesappmvvm.utils.DB_TYPE
import ru.ayuandrey.notesappmvvm.utils.LOGIN
import ru.ayuandrey.notesappmvvm.utils.PASSWORD
import ru.ayuandrey.notesappmvvm.utils.TYPE_DATABASE
import ru.ayuandrey.notesappmvvm.utils.TYPE_FIREBASE
import ru.ayuandrey.notesappmvvm.utils.TYPE_ROOM


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun StartScreen(navController: NavHostController, viewModel: MainViewModel) {

//    val context = LocalContext.current //Получите доступ к текущему значению a с помощью его свойства.
//    val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))



    val bottomSheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()

    var login by remember { mutableStateOf(Constants.Keys.EMPTY) }
    var password by remember { mutableStateOf(Constants.Keys.EMPTY) }

    var showBottomSheet by remember {
        mutableStateOf(false)
    }





    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = WHAT_WILL_WE_USE)
            Button(
                onClick = {

                    viewModel.initDatabase(TYPE_ROOM) {
                        DB_TYPE.value = TYPE_FIREBASE
                        navController.navigate(route = NavRoute.Main.route)
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = ROOM_DATABASE)
            }
            Button(
                onClick = {
                    showBottomSheet = true
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }

//                    viewModel.initDatabase(TYPE_FIREBASE) {
//                        navController.navigate(route = NavRoute.Main.route)
//                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp)
            ) {
                Text(text = FIREBASE_DATABASE)
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
                        text = Constants.Keys.LOG_IN,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    OutlinedTextField(
                        value = login,
                        onValueChange = {
                            login = it
                        },
                        label = { Text(text = Constants.Keys.LOGIN_TEXT) },
                        isError = login.isEmpty()
                    )
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                        },
                        label = { Text(text = Constants.Keys.PASSWORD_TEXT) },
                        isError = password.isEmpty()
                    )
                    Button(
                        modifier = Modifier.padding(top = 16.dp),
                        onClick = {
                            LOGIN = login
                            PASSWORD = password
                            viewModel.initDatabase(TYPE_FIREBASE) {
                                DB_TYPE.value = TYPE_FIREBASE
                                navController.navigate(NavRoute.Main.route)
                                Log.d("checkData", "Auth success")
                            }
//                            coroutineScope.launch {
//                                viewModel.updateNote(note =
//                                Note(id = note.id, title = title, subtitle = subtitle)
//                                ) {
//                                    Log.e( "AAA"," ПРОШЛА ЗАМЕТКАAAA" )
//                                    navController.navigate(NavRoute.Main.route)
//                                }
//                            }
                        },
                        enabled = login.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text(text = Constants.Keys.SIGN_IN)
                    }
                }
            }
        }
    }



















}

@Preview(showBackground = true)
@Composable
fun prevStartScreen() {
    NotesAppMVVMTheme {
        val context = LocalContext.current //Получите доступ к текущему значению a с помощью его свойства.
        val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))

        StartScreen(navController = rememberNavController(), viewModel = mViewModel)
    }
}