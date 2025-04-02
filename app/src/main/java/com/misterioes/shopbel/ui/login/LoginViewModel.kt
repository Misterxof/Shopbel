package com.misterioes.shopbel.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.misterioes.shopbel.domain.model.Status
import com.misterioes.shopbel.domain.model.User
import com.misterioes.shopbel.domain.model.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow<Status>(Status.Loading(true))
    val state: StateFlow<Status> = _state

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val auth = FirebaseAuth.getInstance()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = auth.currentUser?.uid ?: ""
                        getUserData(userId)
                    } else {
                        Log.e("Login", "Error: ${task.exception?.message}")
                        _state.value = Status.Error("SignIn error " + task.exception?.message!!)
                    }
                }
        }
    }

    fun getUserData(userId: String) {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()

            db.collection("user")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val id = document.getString("id") ?: ""
                        val login = document.getString("login") ?: ""
                        val password = document.getString("password") ?: ""
                        val fio = document.getString("fio") ?: ""

                        UserInfo.user = User(id, login, password, fio)
                        _state.value = Status.Success("Success")
                        Log.e("UserData", "get Success with ",)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("UserData", "get failed with ", exception)
                    _state.value = Status.Error(exception.message!!)
                }
        }
    }
}