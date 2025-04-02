package com.misterioes.shopbel.data

data class FirestoreResponse(
    val documents: List<FirestoreDocument>
)

data class FirestoreDocument(
    val name: String,
    val fields: Map<String, FirestoreField>
)

data class FirestoreField(
    val stringValue: String? = null,
    val integerValue: Int? = null,
    val mapValue: Map<String, FirestoreField>? = null
)