package com.example.spaceship;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Meteor {
    public ImageView meteor;
    public Point meteorCoord;
    public ValueAnimator animator;
    Meteor(Context context, Point meteorCoord, int screenHeight, RelativeLayout gameScreen)
    {
        meteor= new ImageView(context);
        LinearLayout.LayoutParams layoutParams= new LinearLayout.LayoutParams(100, 100);
        meteor.setLayoutParams(layoutParams);
        meteor.setBackgroundResource(R.drawable.meteor);
        AnimationDrawable rotateMeteor=(AnimationDrawable)meteor.getBackground();
        rotateMeteor.start();
        gameScreen.addView(meteor);

        this.meteorCoord=meteorCoord;
        meteor.setX(meteorCoord.x);
        meteor.setY(meteorCoord.y);

    }
    public void setAnimator(ValueAnimator animator)
    {
        this.animator=animator;
    }
}
