package com.example.spaceship;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Bullet {
    public RelativeLayout gameScreen;
    public Point bulletCoor;
    public ImageView bullet;
    Bullet(Context context, RelativeLayout gameScreen, Point bulletCoor, int screenHeight)
    {
        bullet = new ImageView(context);
        bullet.setImageResource(R.drawable.bullet);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(20, 40);
        bullet.setLayoutParams(layoutParams);
        gameScreen.addView(bullet);

        this.bulletCoor=bulletCoor;
        bullet.setX(bulletCoor.x);
        bullet.setY(bulletCoor.y);
    }

}
