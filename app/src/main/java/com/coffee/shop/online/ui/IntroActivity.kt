package com.coffee.shop.online.ui

import android.content.Intent
import com.coffee.shop.online.base.BaseActivity
import com.coffee.shop.online.databinding.ActivityIntroBinding

class IntroActivity : BaseActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun initView() {
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun clickView() {
        binding.apply {
            btnStart.setOnClickListener { _  ->
                startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}