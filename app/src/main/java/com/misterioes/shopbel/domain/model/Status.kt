package com.misterioes.shopbel.domain.model

sealed class Status {
    data class Success(val data: String): Status()
    data class Loading(val loading: Boolean): Status()
    data class Error(val error: String): Status()
}