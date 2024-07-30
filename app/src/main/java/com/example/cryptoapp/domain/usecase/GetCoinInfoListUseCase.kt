package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.CoinRepository

class GetCoinInfoListUseCase(
    private val repository: CoinRepository
) {
    operator fun invoke() = repository.getCoinInfoList()
}