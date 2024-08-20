package com.coffee.shop.online.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coffee.shop.online.databinding.ViewHolderOfferBinding
import com.coffee.shop.online.model.ItemsDTO

class OfferAdapter(
    private val items: MutableList<ItemsDTO>,
    private val onItemClick: (ItemsDTO, Int) -> Unit,
) : RecyclerView.Adapter<OfferAdapter.OfferItemViewHolder>() {

    var indexSelected = -1
        set(value) {
            field = value
            notifyItemChanged(value)
        }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(items: MutableList<ItemsDTO>) {
        this.items.apply {
            clear()
            addAll(items)
            notifyDataSetChanged()
        }
    }

    inner class OfferItemViewHolder(private val binding: ViewHolderOfferBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: ItemsDTO, index: Int) =
            binding.apply {
                titleTxt.text = item.title
                priceTxt.text = "$${item.price}"
                Glide.with(root.context).load(item.picUrl[0]).into(pic)

                root.setOnClickListener { _ -> onItemClick.invoke(item, index) }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OfferItemViewHolder =
        OfferItemViewHolder(
            ViewHolderOfferBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: OfferItemViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item, position)
    }

}