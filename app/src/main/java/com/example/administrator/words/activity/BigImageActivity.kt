package com.example.administrator.words.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.dpdp.base_moudle.base.BaseActivity
import com.dpdp.base_moudle.image.GlideUtils
import com.example.administrator.words.R
import kotlinx.android.synthetic.main.activity_big_image_layout.*

class BigImageActivity : AppCompatActivity() {

    companion object {
        private var translationName=""
        fun startActivity(activity: BaseActivity, view: View, translationName: String): Unit {
            this.translationName=translationName
            val intent = Intent(activity, BigImageActivity::class.java)
            val makeSceneTransitionAnimation = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, translationName)
            ActivityCompat.startActivity(activity, intent, makeSceneTransitionAnimation.toBundle())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_big_image_layout)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            big_image_iv.transitionName = translationName
        }
        // Glide.with(this).load(R.mipmap.ui_default_logo).into(big_image_iv)
        GlideUtils.load(this,R.mipmap.ui_default_logo,big_image_iv)
    }
}