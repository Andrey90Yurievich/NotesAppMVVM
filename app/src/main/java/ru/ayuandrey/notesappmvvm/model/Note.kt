package ru.ayuandrey.notesappmvvm.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.ayuandrey.notesappmvvm.utils.Constants.Keys.NOTE_TABLE

@Entity(tableName = NOTE_TABLE)
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo
    var title: String = "",
    @ColumnInfo
    var subtitle: String = "",
    var firebaseId: String = ""


)
