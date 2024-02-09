package com.panther.shoeapp.repository

import com.panther.shoeapp.models.User
import com.panther.shoeapp.utils.Resource

interface Repository {

    suspend fun signUp(email: String, password: String, username: String) : Resource<User>

    suspend fun login(email: String, password: String) : Resource<User>
}