package ru.ayuandrey.notesappmvvm

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import ru.ayuandrey.notesappmvvm.navigation.NavRoute
import ru.ayuandrey.notesappmvvm.navigation.NotesNavHost
import ru.ayuandrey.notesappmvvm.ui.theme.NotesAppMVVMTheme
import ru.ayuandrey.notesappmvvm.utils.DB_TYPE


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotesAppMVVMTheme {
                val context = LocalContext.current //Получите доступ к текущему значению a с помощью его свойства.
                val mViewModel: MainViewModel = viewModel(factory = MainViewModelFactory(context.applicationContext as Application))
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                //Text(text = "Notes App")
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text ="Notes App")

                                        if(DB_TYPE.value.isNotEmpty()) {
                                            Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "",
                                                modifier = Modifier.clickable {
                                                    mViewModel.signOut {
                                                        navController.navigate(NavRoute.Start.route) {
                                                            popUpTo(NavRoute.Start.route) {
                                                                inclusive = true
                                                            }
                                                        }
                                                    }
                                                }
                                            )
                                        }

                                    }
                            },
                            colors = topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                        )
                    },
                    content = {
                        Surface (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 60.dp),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            NotesNavHost(mViewModel, navController)
                        }
                    }
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NotesAppMVVMTheme {

    }
}