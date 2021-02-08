package com.dpdp.base_moudle.image;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-08
 * <p>
 * Summary: GlideUtils 图片加载
 */
public class GlideUtils {

    /**
     * 加载图片
     */
    public static void load(Context context, Object imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl).into(imageView);
    }

    public static void load(Activity activity, Object imageUrl, ImageView imageView) {
        Glide.with(activity).load(imageUrl).into(imageView);
    }

    public static void load(Fragment fragment, Object imageUrl, ImageView imageView) {
        Glide.with(fragment).load(imageUrl).into(imageView);
    }

    /**
     * 加载圆形图片
     */
    public static void loadAsRound(Context context, Object imageUrl, ImageView imageView) {
        Glide.with(context).load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }

    /**
     * 加载圆角图片
     */
    public static void loadAsCorner(Context context, Object imageUrl, int corner, ImageView imageView) {
        Glide.with(context).load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(corner)))
                .into(imageView);
    }


}
