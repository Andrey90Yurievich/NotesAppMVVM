package ru.ayuandrey.notesappmvvm.database

import androidx.lifecycle.LiveData
import ru.ayuandrey.notesappmvvm.model.Note

interface DatabaseRepository {

    val readAll: LiveData<List<Note>>

    suspend fun create(note: Note, onSuccess: () -> Unit)
    suspend
    fun update(note: Note, onSuccess: () -> Unit)
    suspend
    fun delete(note: Note, onSuccess: () -> Unit)


}