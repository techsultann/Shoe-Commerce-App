package com.panther.shoeapp.repository

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.panther.shoeapp.models.User
import com.panther.shoeapp.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) : Repository {

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
                        saveUserDataToFiresStore(username, email)
                    }
                }

            Resource.Success(User())
        } catch (e: Exception) {

            Log.e("TAG", "Signup: FAILED")
            return Resource.Error(e.localizedMessage ?: "Unknown error occurred")

        }

    }

    fun saveUserDataToFiresStore(userName: String, email: String): Resource<User> {

        return try {
            val currentUser = auth.currentUser

            if (currentUser == null) {
                Log.e("USER DATA", "User data saving failed: No authenticated user")
            }
            val userId = currentUser?.uid
            val userDocRef = userId?.let { fireStore.collection("users").document(it) }

            Log.d("USER DATA", "User data: $userId, $userName, $email")
            val userData = User(
                uid = userId,
                email = email,
                displayName = userName,
                photoUrl = null,
                role = "user",
                phoneNumber = ""
            )

            userDocRef?.let { await(it.set(userData)) }

            Resource.Success(User()) // Indicate successful data saving
        } catch (e: Exception) {
            Log.e("USER DATA", "Firestore write failed: ${e.message}", e)
            Resource.Error(e.localizedMessage?.toString())
            //Result.failure(e) // Indicate error with exception object
        }
    }


    override suspend fun login(email: String, password: String): Resource<User> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Log.d("TAG", "Login: SUCCESS")
            Resource.Success(User())
        } catch (e: Exception) {
            Log.e("LOGIN TAG", "Login: FAILED")
            Resource.Error(e.message)
        }
    }

    override suspend fun getUserRole(userId: String): String {

        try {
            val userDocRef = fireStore.collection("users").document(userId)
            val document = userDocRef.get().await()
            val role = document.getString("role") ?: "" // Handle missing "role" field
            Log.d("LOGIN TAG", "Get User Role $role")
            return role
        } catch (e: Exception) {
            Log.e("LOGIN TAG", "Error getting role: $e")
            return "" // Return empty string to prevent navigation errors
        }
    }


    fun logout(): Resource<User> {
        return try {
            auth.signOut()
            Log.d("TAG", "Logout: SUCCESS")
            Resource.Success(User())
        } catch (e: Exception) {
            Log.e("TAG", "Logout: FAILED")
            Resource.Error(e.message)
        }
    }

}