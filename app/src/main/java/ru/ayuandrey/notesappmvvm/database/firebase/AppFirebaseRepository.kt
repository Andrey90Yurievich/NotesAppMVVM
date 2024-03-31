package ru.ayuandrey.notesappmvvm.database.firebase

import androidx.lifecycle.LiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import ru.ayuandrey.notesappmvvm.database.DatabaseRepository
import ru.ayuandrey.notesappmvvm.model.Note
import ru.ayuandrey.notesappmvvm.utils.LOGIN
import ru.ayuandrey.notesappmvvm.utils.PASSWORD

class AppFirebaseRepository : DatabaseRepository {


    private val mAuth = FirebaseAuth.getInstance()

    override val readAll: LiveData<List<Note>>
        get() = TODO("Not yet implemented")

    override suspend fun create(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun update(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(note: Note, onSuccess: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun signOut() {
        mAuth.signOut()
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onFail: (String) -> Unit) {
        mAuth.signInWithEmailAndPassword(LOGIN, PASSWORD)
            .addOnCanceledListener { onSuccess() }
            .addOnFailureListener {
                mAuth.createUserWithEmailAndPassword(LOGIN, PASSWORD)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onFail(it.message.toString()) }
            }
    }
}