package com.coffee.shop.online.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coffee.shop.online.R
import com.coffee.shop.online.databinding.ViewHolderSizeBinding
import com.coffee.shop.online.utils.ext.dpToPx

class SizeAdapter(
    private val items: MutableList<String>,
    private val onSizeClick: (String, Int) -> Unit,
) : RecyclerView.Adapter<SizeAdapter.SizeViewHolder>() {

    var indexSelected = -1
        set(value) {
            field = value
            notifyItemChanged(value)
        }

    inner class SizeViewHolder(private val binding: ViewHolderSizeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: String, index: Int) =
            binding.apply {
                val imageSize = when (index) {
                    0 -> 45.dpToPx(root.context)
                    1 -> 50.dpToPx(root.context)
                    2 -> 55.dpToPx(root.context)
                    3 -> 65.dpToPx(root.context)
                    else -> 70.dpToPx(root.context)
                }
                val layoutParamsCustom = img.layoutParams
                layoutParamsCustom.apply {
                    width = imageSize
                    height = imageSize
                }

                img.apply {
                    setBackgroundResource(if (indexSelected == index) R.drawable.orange_bg else R.drawable.size_bg)
                    layoutParams = layoutParamsCustom
                }

                root.setOnClickListener { onSizeClick.invoke(item, index) }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder =
        SizeViewHolder(
            ViewHolderSizeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        val item = items[position]
        holder.onBind(item, position)
    }
}