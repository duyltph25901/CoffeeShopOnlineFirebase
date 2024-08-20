package com.coffee.shop.online.base

import android.os.Bundle
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen()

        initView()
        clickView()
        getDataSrc()
        observerData()
    }

    private fun setFullScreen() =
        window.setFlags(
            LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

    open fun initView() {}

    open fun clickView() {}

    open fun getDataSrc() {}

    open fun observerData() {}

}