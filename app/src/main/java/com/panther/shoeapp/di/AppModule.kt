package com.panther.shoeapp.di

import android.app.Application
import com.google.android.gms.wallet.PaymentsClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.panther.shoeapp.repository.Repository
import com.panther.shoeapp.repository.RepositoryImpl
import com.panther.shoeapp.utils.PaymentsUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseFireStore() : FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun providePaymentsClient(application: Application) : PaymentsClient {
        return PaymentsUtil.createPaymentsClient(application)
    }


    @Provides
    @Singleton
    fun provideRepository(auth: FirebaseAuth, fireStore: FirebaseFirestore) : Repository {
        return RepositoryImpl(auth, fireStore)
    }
}