package com.example.cryptoapp.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.cryptoapp.databinding.ActivityCoinPrceListBinding
import com.example.cryptoapp.presentation.adapters.CoinInfoAdapter
import com.example.cryptoapp.domain.CoinInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinPriceListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCoinPrceListBinding
    private val viewModel: CoinViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinPrceListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CoinInfoAdapter(this)
        adapter.onCoinClickListener = object : CoinInfoAdapter.OnCoinClickListener {
            override fun onCoinClick(coinPriceInfo: CoinInfo) {
                val intent = CoinDetailActivity.newIntent(
                    this@CoinPriceListActivity,
                    coinPriceInfo.fromSymbol
                )
                startActivity(intent)
            }
        }
        binding.rvCoinPriceList.adapter = adapter
        viewModel.coinInfoList.observe(this) {
            adapter.coinInfoList = it
        }
    }
}
