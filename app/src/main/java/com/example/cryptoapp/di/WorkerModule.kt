package com.example.cryptoapp.di

import androidx.work.ListenableWorker
import com.example.cryptoapp.workers.ChildWorkerFactory
import com.example.cryptoapp.workers.RefreshDataWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RefreshDataWorker::class)
    fun bindRefreshDataWorkerFactory(worker: RefreshDataWorker.Factory): ChildWorkerFactory

}