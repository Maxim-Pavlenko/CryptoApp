package com.example.cryptoapp.domain.di

import android.app.Application
import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.domain.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CoinModule {
    @Provides
    fun provideCoinRepository(application: Application): CoinRepository =
        CoinRepositoryImpl(application)
}