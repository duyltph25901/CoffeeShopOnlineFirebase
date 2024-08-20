package com.coffee.shop.online.ui

import android.annotation.SuppressLint
import com.coffee.shop.online.base.BaseActivity
import com.coffee.shop.online.base.callback.ChangeNumberItemsListener
import com.coffee.shop.online.databinding.ActivityCartBinding
import com.coffee.shop.online.model.ItemsDTO
import com.coffee.shop.online.ui.adapter.CartAdapter
import com.coffee.shop.online.utils.ManagementCart

class CartActivity : BaseActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var managementCart: ManagementCart
    private lateinit var cartAdapter: CartAdapter
    private var tax: Double = 0.0

    override fun initView() {
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initManagementCart()
        calculateCart()
        initListCart()
    }

    override fun clickView() {
        binding.apply {
            backBtn.setOnClickListener { finish() }
        }
    }

    private fun initManagementCart() {
        managementCart = ManagementCart(this)
    }

    @SuppressLint("SetTextI18n")
    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 15.0
        tax = Math.round(managementCart.getTotalFee() * percentTax * 100) / 100.0
        val total = Math.round((managementCart.getTotalFee() + tax + delivery) * 100) / 100
        val itemTotal = Math.round(managementCart.getTotalFee() * 100)

        binding.apply {
            totalFeeTxt.text = "$$itemTotal"
            taxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }

    private fun initListCart() = binding.cartView.apply {
        val callBackChangeNumberItemListener = object : ChangeNumberItemsListener {
            override fun onChanged() = calculateCart()
        }
        cartAdapter =
            CartAdapter(
                items = managementCart.getListCart(), incrementItem = { item, index ->
                    managementCart.plusItem(
                        cartAdapter.getListItemCurrent() as ArrayList<ItemsDTO>,
                        index, callBackChangeNumberItemListener
                    )
                    cartAdapter.notifyItemChanged(index)
                }, reduceItem = { item, index ->
                    managementCart.minusItem(
                        cartAdapter.getListItemCurrent() as ArrayList<ItemsDTO>,
                        index, callBackChangeNumberItemListener
                    )
                    cartAdapter.notifyItemChanged(index)
                }
            )

        adapter = cartAdapter
        setHasFixedSize(true)
        setItemViewCacheSize(20)
    }
}