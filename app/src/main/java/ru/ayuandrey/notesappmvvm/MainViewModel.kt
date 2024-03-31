package ru.ayuandrey.notesappmvvm

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ayuandrey.notesappmvvm.database.room.AppRoomDatabase
import ru.ayuandrey.notesappmvvm.database.room.repository.RoomRepository
import ru.ayuandrey.notesappmvvm.model.Note
import ru.ayuandrey.notesappmvvm.navigation.NavRoute
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

    fun readAllNotes() = REPOSITORY.readAll


}

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}