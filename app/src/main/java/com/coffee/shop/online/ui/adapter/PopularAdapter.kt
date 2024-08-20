package com.coffee.shop.online.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coffee.shop.online.databinding.ViewHolderPopularBinding
import com.coffee.shop.online.model.ItemsDTO

class PopularAdapter(
    private val items: MutableList<ItemsDTO>,
    private val onItemClick: (item: ItemsDTO, indexItem: Int) -> Unit
) : RecyclerView.Adapter<PopularAdapter.PopularItemViewHolder>() {

    var indexSelected = -1
        set(value) {
            field = value
            notifyItemChanged(value)
        }

    inner class PopularItemViewHolder(private val binding: ViewHolderPopularBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(item: ItemsDTO, indexItem: Int) =
            binding.apply {
                titleTxt.text = item.title
                priceText.text = "$${item.price}"
                ratingBar.rating = item.rating.toFloat()
                extraTxt.text = item.extra
                Glide.with(root.context).load(item.picUrl[0]).into(pic)

                root.setOnClickListener { _ -> onItemClick.invoke(item, indexItem)  }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder =
        PopularItemViewHolder(
            ViewHolderPopularBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(items: List<ItemsDTO>) {
        this.items.apply {
            clear()
            addAll(items)
            notifyDataSetChanged()
        }
    }
}