package ru.ayuandrey.notesappmvvm.database.room.repository

import androidx.lifecycle.LiveData
import ru.ayuandrey.notesappmvvm.database.DatabaseRepository
import ru.ayuandrey.notesappmvvm.database.room.dao.NoteRoomDao
import ru.ayuandrey.notesappmvvm.model.Note

class RoomRepository(private val noteRoomDao: NoteRoomDao) : DatabaseRepository {
    override val readAll: LiveData<List<Note>>
        get() = noteRoomDao.getAllNotes()

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.addNote(note = note)
        onSuccess()
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.updateNote(note = note)
        onSuccess()
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        noteRoomDao.deleteNote(note = note)
        onSuccess()
    }

    override fun signOut() {

    }
}