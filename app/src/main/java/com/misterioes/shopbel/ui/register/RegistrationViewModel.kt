package com.misterioes.shopbel.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.misterioes.shopbel.domain.model.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<Status>(Status.Loading(true))
    val state: StateFlow<Status> = _state

    fun registerUser(email: String, password: String, fio: String) {
        viewModelScope.launch {
            val auth = FirebaseAuth.getInstance()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val firebaseUser = auth.currentUser
                        val userId = firebaseUser?.uid ?: ""

                        createUserInFirestore(userId, email, password, fio)
                    } else {
                        _state.value = Status.Error("Registration error " + task.exception?.message!!)
                    }
                }
        }
    }

    private fun createUserInFirestore(userId: String, email: String, password: String, fio: String) {
        val db = FirebaseFirestore.getInstance()

        val user = hashMapOf(
            "id" to userId,
            "email" to email,
            "password" to password,
            "fio" to fio
        )

        db.collection("user")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                _state.value = Status.Success("Success")
            }
            .addOnFailureListener { e ->
                Log.w("Firestore", "Error adding user document", e)
                _state.value = Status.Error(e.message!!)
            }
    }
}