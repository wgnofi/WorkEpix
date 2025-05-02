package com.example.standardprotocols.di

import com.example.standardprotocols.data.AuthRepository
import com.example.standardprotocols.data.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    abstract fun bindAuthRepository(
        firebaseAuthAuthRepository: FirebaseAuthRepository
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideAuth(): FirebaseAuth {
            return Firebase.auth
        }
    }
}