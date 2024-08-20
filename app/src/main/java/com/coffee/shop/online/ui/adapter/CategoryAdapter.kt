package com.coffee.shop.online.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.coffee.shop.online.R
import com.coffee.shop.online.databinding.ViewHolderCategoryBinding
import com.coffee.shop.online.model.CategoryDTO

class CategoryAdapter(
    private val categories: MutableList<CategoryDTO>,
    private val clickItem: (obj: CategoryDTO, indexObj: Int) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var indexSelected = -1
        set(value) {
            field = value
            notifyItemChanged(value)
        }

    inner class CategoryViewHolder(private val binding: ViewHolderCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(category: CategoryDTO, index: Int) {
            binding.titleCate.apply {
                text = category.title
                setBackgroundResource(if (index == indexSelected) R.drawable.orange_bg else R.drawable.editext_bg)
                setOnClickListener { _ -> clickItem.invoke(category, index) }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            ViewHolderCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.onBind(category, position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(categories: List<CategoryDTO>) {
        this.categories.apply {
            clear()
            addAll(categories)
            notifyDataSetChanged()
        }
    }

}