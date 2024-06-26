package ru.ayuandrey.notesappmvvm.utils

import androidx.compose.runtime.mutableStateOf
import ru.ayuandrey.notesappmvvm.database.DatabaseRepository

const val TYPE_DATABASE = "type_database"
const val TYPE_ROOM = "type_room"
const val TYPE_FIREBASE = "type_firebase"
const val FIREBASE_ID = "firebaseId"

lateinit var REPOSITORY: DatabaseRepository
lateinit var LOGIN: String
lateinit var PASSWORD: String
//lateinit var DB_TYPE: String
var DB_TYPE = mutableStateOf("")


object Constants {
    object Keys {
        const val NOTE_DATABASE = "notes_database"
        const val NOTE_TABLE = "notes_table"
        const val ADD_NEW_NOTE = "Add new note"
        const val NOTE_TITLE = "Note title"
        const val NOTE_SUBTITLE = "Note subtitle"
        const val ADD_NOTE = "Add Note"
        const val TITLE = "title"
        const val SUBTITLE = "subTitle"
        const val WHAT_WILL_WE_USE = "Что мы будем использовать?"
        const val ROOM_DATABASE = "Room database"
        const val FIREBASE_DATABASE = "Firebase database"
        const val ID = "Id"
        const val NONE = "None"
        const val UPDATE = "UPDATE"
        const val DELETE = "DELETE"
        const val NAV_BACK = "NAV_BACK"
        const val EDIT_NOTE = "Edit_note"
        const val LOG_IN = "Log In"
        const val EMPTY = ""
        const val UPDATE_NOTE = "Update note"
        const val SIGN_IN = "Sign In"
        const val LOGIN_TEXT = "Login"
        const val PASSWORD_TEXT = "Password"
    }

    object Screens {
        const val START_SCREEN = "start_screen"
        const val MAIN_SCREEN = "main_screen"
        const val ADD_SCREEN = "add_screen"
        const val NOTE_SCREEN = "note_screen"

    }
}