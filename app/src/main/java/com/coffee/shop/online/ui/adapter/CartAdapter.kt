package com.coffee.shop.online.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.coffee.shop.online.databinding.ViewHolderCartBinding
import com.coffee.shop.online.model.ItemsDTO

class CartAdapter(
    private val items: List<ItemsDTO>,
    private val incrementItem: (item: ItemsDTO, index: Int) -> Unit,
    private val reduceItem: (item: ItemsDTO, index: Int) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(private val binding: ViewHolderCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: ItemsDTO, index: Int) = binding.apply {
            titleTxt.text = item.title
            feeEachItem.text = "$${item.price}"
            totalEachItem.text = "$${Math.round(item.numberInCart * item.price)}"
            numberItemTxt.text = item.numberInCart.toString()
            Glide.with(root.context).load(item.picUrl[0])
                .apply(RequestOptions().transform(CenterCrop())).into(picCart)

            minusCartBtn.setOnClickListener { reduceItem.invoke(item, index) }
            plusCartBtn.setOnClickListener { incrementItem.invoke(item, index) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            ViewHolderCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item, position)
    }

    fun getListItemCurrent() = this.items

}