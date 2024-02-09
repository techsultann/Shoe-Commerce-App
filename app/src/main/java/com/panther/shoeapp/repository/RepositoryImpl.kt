package com.panther.shoeapp.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.panther.shoeapp.models.User
import com.panther.shoeapp.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val auth: FirebaseAuth) : Repository {

    override suspend fun signUp(email: String, password: String, username: String): Resource<User> {

        return try {

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful) {

                        val currentUser = auth.currentUser
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(username)
                            .build()

                        currentUser?.updateProfile(profileUpdates)
                        currentUser?.sendEmailVerification()
                    }
                }

            Resource.Success(User())
        } catch (e: Exception) {

            Log.e("TAG", "Signup: FAILED")
            return Resource.Error(e.localizedMessage ?: "Unknown error occurred")

        }

    }

    override suspend fun login(email: String, password: String): Resource<User> {
        return try {

            val response = auth.signInWithEmailAndPassword(email, password).await()
            Log.d("TAG", "Login: SUCCESS")
            Resource.Success(User())
        } catch (e: Exception) {
            Log.e("TAG", "Login: FAILED")
            Resource.Error("Failed")
        }
    }

    fun logout(): Resource<User> {
        return try {
            auth.signOut()
            Log.d("TAG", "Logout: SUCCESS")
            Resource.Success(User())
        } catch (e: Exception) {
            Log.e("TAG", "Logout: FAILED")
            Resource.Error("Failed")
        }
    }

}