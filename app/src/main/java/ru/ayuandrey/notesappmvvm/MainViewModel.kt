package ru.ayuandrey.notesappmvvm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ayuandrey.notesappmvvm.database.firebase.AppFirebaseRepository
import ru.ayuandrey.notesappmvvm.database.room.AppRoomDatabase
import ru.ayuandrey.notesappmvvm.database.room.repository.RoomRepository
import ru.ayuandrey.notesappmvvm.model.Note
import ru.ayuandrey.notesappmvvm.navigation.NavRoute
import ru.ayuandrey.notesappmvvm.utils.Constants
import ru.ayuandrey.notesappmvvm.utils.DB_TYPE
import ru.ayuandrey.notesappmvvm.utils.REPOSITORY
import ru.ayuandrey.notesappmvvm.utils.TYPE_FIREBASE
import ru.ayuandrey.notesappmvvm.utils.TYPE_ROOM

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    val readTest: MutableLiveData<List<Note>> by lazy {
//        MutableLiveData<List<Note>>()
//    }
//
//    val dbType: MutableLiveData<String> by lazy {
//        MutableLiveData<String>(TYPE_ROOM)
//    }
//
//
//
//
//    init {
//        readTest.value =
//            when(dbType.value) {
//                TYPE_ROOM -> {
//                    listOf<Note>(
//                        Note(title = "Note 1", subtitle = "Subtitle for note 1"),
//                        Note(title = "Note 2", subtitle = "Subtitle for note 2"),
//                        Note(title = "Note 3", subtitle = "Subtitle for note 3"),
//                        Note(title = "Note 4", subtitle = "Subtitle for note 4"),
//                    )
//                }
//                TYPE_FIREBASE -> listOf()
//                else -> listOf()
//            }
//    }

    val context = application
    fun initDatabase(type: String, onSuccess: () -> Unit) {
        //dbType.value = type
        Log.d("checkData", "MainViewModel initDatabase with type: $type")
        when(type) {
            TYPE_ROOM -> {
                val dao = AppRoomDatabase.getInstance(context = context).getRoomDao()
                REPOSITORY =  RoomRepository(dao)
                onSuccess()
            }


            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase(
                    { onSuccess() },
                    { Log.d("checkData", "Error: ${it}") }
                )
//                Log.d("checkData", "Init TYPE_FIREBASE")
//                onSuccess()
            }


        }
    }

    fun addNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.create(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
    }


     suspend fun updateNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(note = note) {
                viewModelScope.launch(Dispatchers.Main) {
                    onSuccess()
                }
            }
        }
     }


    suspend fun deleteNote(note: Note, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
        REPOSITORY.delete(note = note) {
            viewModelScope.launch(Dispatchers.Main) {
                onSuccess()
                }
            }
        }
    }


    fun readAllNotes() = REPOSITORY.readAll


    fun signOut(onSuccess: () -> Unit) {
        when(DB_TYPE.value) {
            TYPE_FIREBASE,
                TYPE_ROOM -> {
                    REPOSITORY.signOut()
                    DB_TYPE.value = Constants.Keys.EMPTY
                onSuccess()

                }
            else -> { Log.d("checkData", "signOut: ELSE: ${DB_TYPE.value}") }
        }
    }


}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}