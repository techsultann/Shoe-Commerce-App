package com.panther.shoeapp.repository

import com.google.firebase.auth.AuthResult
import com.panther.shoeapp.models.User
import com.panther.shoeapp.utils.Resource

interface Repository {

    suspend fun signUp(email: String, password: String, username: String) : Resource<AuthResult>

    suspend fun login(email: String, password: String) : Resource<User>
}