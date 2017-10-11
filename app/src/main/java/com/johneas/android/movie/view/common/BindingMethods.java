package com.johneas.android.movie.view.common;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class BindingMethods {

    @BindingAdapter("android:onShow")
    public static void setOnShow(View view, boolean isShown) {
        if (isShown)
            view.setVisibility(View.VISIBLE);
        else
            view.setVisibility(View.GONE);
    }


    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        if(imageUrl.length() > 0){
            Picasso.with(view.getContext())
                    .load(imageUrl)
                    .into(view);
        }
    }

}