package com.misterioes.shopbel.domain.model

data class User(
    val id: String,
    val login: String,
    val password: String,
    val fio: String
) {
    constructor() : this("", "", "", "")
}