package com.example.administrator.words.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.bumptech.glide.Glide
import com.dpdp.base_moudle.base.BaseActivity
import com.example.administrator.words.R
import kotlinx.android.synthetic.main.activity_big_image_layout.*

class BigImageActivity : AppCompatActivity() {

    companion object {
        fun startActivity(activity: BaseActivity, view: View, translationName: String): Unit {
            val intent = Intent(activity, BigImageActivity::class.java)
            val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, translationName)
            ActivityCompat.startActivity(activity, intent, makeSceneTransitionAnimation.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_image_layout)
        Glide.with(this).load(R.mipmap.ui_default_logo).into(big_image_iv)
    }
}