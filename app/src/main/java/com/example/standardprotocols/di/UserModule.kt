package com.example.standardprotocols.di

import com.example.standardprotocols.data.AnnouncementRepository
import com.example.standardprotocols.data.FirebaseAnnouncementRepository
import com.example.standardprotocols.data.FirebaseIssueRepository
import com.example.standardprotocols.data.FirebaseLeaveRepository
import com.example.standardprotocols.data.FirebaseUserRepository
import com.example.standardprotocols.data.IssueRepository
import com.example.standardprotocols.data.LeaveRepository
import com.example.standardprotocols.data.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        firebaseUserRepository: FirebaseUserRepository
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindsLeaveRepository(
        firebaseLeaveRepository: FirebaseLeaveRepository
    ): LeaveRepository

    @Binds
    @Singleton
    abstract fun bindsIssueRepository(
        firebaseIssueRepository: FirebaseIssueRepository
    ): IssueRepository

    @Binds
    @Singleton
    abstract fun bindsAnnouncementRepository(
        firebaseAnnouncementRepository: FirebaseAnnouncementRepository
    ) : AnnouncementRepository

    companion object {
        @Provides
        @Singleton
        fun provideDatabase(): FirebaseFirestore {
            return Firebase.firestore
        }
    }
}