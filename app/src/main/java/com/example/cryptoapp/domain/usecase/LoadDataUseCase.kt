package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.CoinRepository

class LoadDataUseCase(
    private val repository: CoinRepository
) {
    operator suspend fun invoke() = repository.loadData()
}