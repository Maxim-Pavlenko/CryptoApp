package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.CoinRepository
import javax.inject.Inject

class GetCoinInfoUseCase @Inject constructor(
    private val repository: CoinRepository
) {
    operator fun invoke(fromSymbol: String) = repository.getCoinInfo(fromSymbol)
}