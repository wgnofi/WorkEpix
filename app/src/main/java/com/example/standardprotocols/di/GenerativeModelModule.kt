package com.example.standardprotocols.di

import com.google.ai.client.generativeai.GenerativeModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object GenerativeModelModule {
    @Provides
    @Singleton
    fun provideGenerativeModel(): GenerativeModel {
        val apiKey = "YOUR_API_KEY"
        return GenerativeModel(
            modelName = "gemini-2.0-flash",
            apiKey = apiKey
        )
    }
}