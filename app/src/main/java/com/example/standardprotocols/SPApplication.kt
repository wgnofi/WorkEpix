package com.example.standardprotocols

import android.app.Application
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.Firebase
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.appCheck
import com.google.firebase.initialize
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SPApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(context = this)
    }
}