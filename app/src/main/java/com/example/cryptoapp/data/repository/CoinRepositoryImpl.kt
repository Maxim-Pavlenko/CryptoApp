package com.example.cryptoapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.cryptoapp.data.database.AppDatabase
import com.example.cryptoapp.data.mapper.CoinMapper
import com.example.cryptoapp.data.network.api.ApiFactory
import com.example.cryptoapp.data.network.model.CoinNameListDto
import com.example.cryptoapp.domain.CoinInfo
import com.example.cryptoapp.domain.CoinRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    application: Application
) : CoinRepository {
    private val coinInfoDao = AppDatabase.getInstance(application).coinPriceInfoDao()
    private val mapper = CoinMapper()
    private val apiService = ApiFactory.apiService
    override fun getCoinInfoList(): LiveData<List<CoinInfo>> =
        MediatorLiveData<List<CoinInfo>>().apply {
            addSource(coinInfoDao.getPriceList()) {
                val list = it.map {
                    mapper.mapDbModelToEntity(it)
                }
                value = list
            }
        }

    override fun getCoinInfo(fromSymbol: String): LiveData<CoinInfo> =
        MediatorLiveData<CoinInfo>().apply {
            addSource(coinInfoDao.getPriceInfoAboutCoin(fromSymbol)) {
                value = mapper.mapDbModelToEntity(it)
            }
        }

    override suspend fun loadData() {
        while (true) {
            try {
                val topCoins = apiService.getTopCoinsInfo(limit = 50)
                val fromSymbols = mapper.mapNamesListToString(topCoins)
                val jsonContainer = apiService.getFullPriceList(fSyms = fromSymbols)
                val coinInfoDtoList = mapper.mapJsonContainerToListCoinInfo(jsonContainer)
                val dbModelList = coinInfoDtoList.map { mapper.mapDtoToDbModel(it) }
                coinInfoDao.insertPriceList(dbModelList)
            } catch (e: Exception) {}
            delay(10000)
        }
    }
}