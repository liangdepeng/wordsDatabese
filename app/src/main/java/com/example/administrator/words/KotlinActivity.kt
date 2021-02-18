package com.example.administrator.words

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dpdp.base_moudle.utils.LogUtils
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

        val intArray = intArrayOf(1, 2, 4, 5)
        val map = intArray.map { i: Int ->
            i * 3
        }

        val iterator = map.iterator()
        while (iterator.hasNext())
            LogUtils.e("kotlin", "new Array111 ${iterator.next()}")

        val newArray = intArray.map {
            it * 2
        }

        newArray.forEach {
            LogUtils.e("kotlin", "new Array222 $it")
        }

        arrayOf("1","2","3").map {  }

    }
}