package com.coffee.shop.online.ui

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.coffee.shop.online.base.BaseActivity
import com.coffee.shop.online.databinding.ActivityDetailsBinding
import com.coffee.shop.online.model.ItemsDTO
import com.coffee.shop.online.ui.adapter.SizeAdapter
import com.coffee.shop.online.utils.ManagementCart

class DetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var itemDetails: ItemsDTO
    private lateinit var managementCart: ManagementCart
    private lateinit var sizeAdapter: SizeAdapter

    override fun initView() {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDataBundleAndShowDataToView()
        initSizeList()
        initManagementCart()
    }

    @SuppressLint("SetTextI18n")
    override fun clickView() {
        binding.apply {
            addToCartBtn.setOnClickListener { view ->
                itemDetails.numberInCart = numberItemTxt.text.toString().trim().toInt()
                managementCart.insertItems(itemDetails)
            }

            backBtn.setOnClickListener { finish() }

            plusCart.setOnClickListener {
                numberItemTxt.text = (itemDetails.numberInCart + 1).toString()
                itemDetails.numberInCart++
            }

            minusCart.setOnClickListener {
                if (itemDetails.numberInCart > 1) {
                    numberItemTxt.text = (itemDetails.numberInCart - 1).toString()
                    itemDetails.numberInCart--
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDataBundleAndShowDataToView() = binding.apply {
        itemDetails = intent.getParcelableExtra("object")!!
        titleTxt.text = itemDetails.title
        descriptionTxt.text = itemDetails.description
        priceTxt.text = "$${itemDetails.price}"
        ratingBar.rating = itemDetails.rating.toFloat()
    }

    private fun initSizeList() {
        val sizeList = ArrayList<String>()
        sizeList.add("1")
        sizeList.add("2")
        sizeList.add("3")
        sizeList.add("4")
        val colorList = ArrayList<String>()
        colorList.addAll(itemDetails.picUrl)

        binding.sizeList.apply {
            sizeAdapter = SizeAdapter(sizeList) { size, index ->
                val oldValueSelected = sizeAdapter.indexSelected
                val newValueSelected = index
                sizeAdapter.indexSelected = newValueSelected
                sizeAdapter.notifyItemChanged(oldValueSelected)
            }

            adapter = sizeAdapter
        }
        Glide.with(this@DetailsActivity).load(colorList[0])
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
            .into(binding.picMain)
    }

    private fun initManagementCart() {
        managementCart = ManagementCart(this)
    }

}