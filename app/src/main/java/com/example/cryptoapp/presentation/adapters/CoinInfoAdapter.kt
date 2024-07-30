package com.example.cryptoapp.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptoapp.R
import com.example.cryptoapp.databinding.ItemCoinInfoBinding
import com.example.cryptoapp.domain.CoinInfo
import com.squareup.picasso.Picasso

class CoinInfoAdapter(private val context: Context) :
    RecyclerView.Adapter<CoinInfoAdapter.CoinInfoViewHolder>() {

    var coinInfoList: List<CoinInfo> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onCoinClickListener: OnCoinClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinInfoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_coin_info, parent, false)
        return CoinInfoViewHolder(view)
    }

    override fun getItemCount() = coinInfoList.size

    override fun onBindViewHolder(holder: CoinInfoViewHolder, position: Int) {
        val coin = coinInfoList[position]
        val binding = ItemCoinInfoBinding.bind(holder.itemView)
        binding.apply {
            with(coin) {
                val symbolsTemplate = context.resources.getString(R.string.symbols_template)
                val lastUpdateTemplate = context.resources.getString(R.string.last_update_template)
                tvSymbols.text = String.format(symbolsTemplate, fromSymbol, toSymbol)
                tvPrice.text = price
                tvLastUpdate.text = String.format(lastUpdateTemplate, lastUpdate)
                Picasso.get().load(imageUrl).into(ivLogoCoin)
                holder.itemView.setOnClickListener {
                    onCoinClickListener?.onCoinClick(this)
                }
            }
        }
    }

    inner class CoinInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnCoinClickListener {
        fun onCoinClick(coinPriceInfo: CoinInfo)
    }
}