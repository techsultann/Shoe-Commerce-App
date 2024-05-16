package com.panther.shoeapp.di

import android.app.Application
import com.google.android.gms.wallet.PaymentsClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.panther.shoeapp.api.FlutterWaveApi
import com.panther.shoeapp.repository.Repository
import com.panther.shoeapp.repository.RepositoryImpl
import com.panther.shoeapp.utils.Constants.BASE_URL
import com.panther.shoeapp.utils.PaymentsUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
//    @Provides
//    @Singleton
//    fun providePayStackPayment() : PaymentSheet {
//    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideFlutterWaveApi(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
           // .create(TravelTideApi::class.java)
    }

    @Provides
    @Singleton
    fun ProvideApiService(retrofit: Retrofit): FlutterWaveApi {
        return retrofit.create(FlutterWaveApi::class.java)
    }
}