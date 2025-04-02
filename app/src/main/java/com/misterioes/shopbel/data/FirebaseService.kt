package com.misterioes.shopbel.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FirebaseService {
    @GET("v1/projects/{projectId}/databases/(default)/documents/pack")
    suspend fun getPacks(
        @Path("projectId") projectId: String,
        @Query("key") apiKey: String

    ): FirestoreResponse
}