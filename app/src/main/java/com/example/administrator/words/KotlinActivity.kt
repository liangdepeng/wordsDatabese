package com.example.administrator.words

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_kotlin.*

@Deprecated("测试用")
class KotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        initView()
    }

    private fun initView() {

        text_01_tv.text = "textView_01"

        text_02_tv.text = "textView_02"

        text_03_tv?.text = "textView_03"

        text_04_tv?.text = "textView_04"

        text_05_tv?.text = "textView_05"

        button_01_btn.text = "button_01"

        button_02_btn.text = "button_02"

        button_03_btn?.text = "button_03"

        button_04_btn?.text = "button_04"

        button_05_btn?.text = "button_05"

    }
}