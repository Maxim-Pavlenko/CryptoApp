package com.example.cryptoapp.presentation

import android.app.Application
import androidx.work.Configuration
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.api.ApiFactory
import com.example.cryptoapp.di.DaggerApplicationComponent
import com.example.cryptoapp.workers.RefreshDataWorkerFactory

class CoinApp: Application(), Configuration.Provider {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(application = this)
    }
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(
                RefreshDataWorkerFactory(
                    AppDatabase.getInstance(this).coinPriceInfoDao(),
                    ApiFactory.apiService,
                    CoinMapper()
                )
            )
            .build()
}