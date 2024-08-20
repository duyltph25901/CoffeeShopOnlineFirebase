package com.coffee.shop.online.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.coffee.shop.online.base.BaseActivity
import com.coffee.shop.online.databinding.ActivityMainBinding
import com.coffee.shop.online.ui.adapter.CategoryAdapter
import com.coffee.shop.online.ui.adapter.OfferAdapter
import com.coffee.shop.online.ui.adapter.PopularAdapter
import com.coffee.shop.online.ui.vm.MainViewModel

class MainActivity : BaseActivity() {

    companion object {
        internal const val FLAG_LOAD_POPULAR = 0
        internal const val FLAG_LOAD_OFFER = 1
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var popularAdapter: PopularAdapter
    private lateinit var offerAdapter: OfferAdapter
    private val mainViewModel: MainViewModel by viewModels()

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCategory()
        initPopular()
        initOffer()
    }

    override fun clickView() {
        binding.apply {
            cartBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, CartActivity::class.java))
            }
        }
    }

    override fun getDataSrc() {
        mainViewModel.apply {
            loadCategory()
            loadPopularOrOffer(FLAG_LOAD_OFFER)
            loadPopularOrOffer(FLAG_LOAD_POPULAR)
        }
    }

    override fun observerData() {
        mainViewModel.apply {
            category.observe(this@MainActivity) { categories ->
                categoryAdapter.submitData(categories)
                binding.progressBarCategory.visibility = View.GONE
            }

            popular.observe(this@MainActivity) { populars ->
                popularAdapter.submitData(populars)
                binding.progressBarPopular.visibility = View.GONE
            }

            offer.observe(this@MainActivity) { offers ->
                offerAdapter.submitData(offers)
                binding.progressBarOffer.visibility = View.GONE
            }
        }
    }

    private fun initCategory() = binding.apply {
        progressBarCategory.visibility = View.VISIBLE
        recycleViewCategory.apply {
            categoryAdapter = CategoryAdapter(mutableListOf()) { category, indexCategory ->
                val oldValueSelected = categoryAdapter.indexSelected
                val newValueSelected = indexCategory
                categoryAdapter.indexSelected = newValueSelected
                categoryAdapter.notifyItemChanged(oldValueSelected)
            }

            adapter = categoryAdapter
            setItemViewCacheSize(20)
            setHasFixedSize(true)
        }
    }

    private fun initPopular() = binding.apply {
        progressBarPopular.visibility = View.VISIBLE
        recycleViewPopular.apply {
            popularAdapter = PopularAdapter(mutableListOf()) { popular, indexPopular ->
                /*val oldValueSelected = popularAdapter.indexSelected
                val newValueSelected = indexPopular
                popularAdapter.indexSelected = newValueSelected
                popularAdapter.notifyItemChanged(oldValueSelected)*/

                val intent = Intent(this@MainActivity, DetailsActivity::class.java).apply {
                    putExtra("object", popular)
                }
                startActivity(intent)
            }

            adapter = popularAdapter
        }
    }

    private fun initOffer() = binding.apply {
        progressBarOffer.visibility = View.VISIBLE
        recycleViewOffer.apply {
            offerAdapter = OfferAdapter(mutableListOf()) { _, indexOffer ->
                val oldValueSelected = offerAdapter.indexSelected
                val newValueSelected = indexOffer
                offerAdapter.indexSelected = newValueSelected
                offerAdapter.notifyItemChanged(oldValueSelected)
            }

            adapter = offerAdapter
        }
    }
}