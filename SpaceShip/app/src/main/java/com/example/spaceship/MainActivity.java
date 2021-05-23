package com.example.spaceship;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Scene scene1, scene2, scene3;
    Point startingPosition;
    ImageView spaceShip;
    LinearLayout gameScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup menu
        ViewGroup menuScene=findViewById(R.id.menuContainer);
        scene1=Scene.getSceneForLayout(menuScene,R.layout.start_menu, this);
        scene2=Scene.getSceneForLayout(menuScene, R.layout.choose_type,this);
        scene3=Scene.getSceneForLayout(menuScene, R.layout.blank_menu, this);
        scene1.enter();
        StartMenu();

        // Move Left right
        spaceShip=findViewById(R.id.spaceShip);
        gameScreen=findViewById(R.id.gameScreen);

        startingPosition=new Point((int)gameScreen.getWidth()/2-50, 0);
        spaceShip.setX(startingPosition.x);
        spaceShip.setY(startingPosition.y);

        Button btMoveLeft=findViewById(R.id.btMoveLeft);
        Button btMoveRight=findViewById(R.id.btMoveRight);

        btMoveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startingPosition=new Point(startingPosition.x-10,startingPosition.y);
                spaceShip.setX(startingPosition.x);
            }
        });
        btMoveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startingPosition=new Point(startingPosition.x+10,startingPosition.y);
                spaceShip.setX(startingPosition.x);
            }
        });

    }


    void StartMenu() {
        Button btPlay, btExit;
        btPlay=findViewById(R.id.btPlay);
        btExit=findViewById(R.id.btExit);

        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transition Fade=new Fade();
                TransitionManager.go(scene2, Fade);
                ChooseType();
            }
        });
        btExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
    void ChooseType() {
        LinearLayout ll;
        Button btEasy, btNormal, btHard;

        ll=(LinearLayout)findViewById(R.id.choose_type);
        btEasy=findViewById(R.id.btEasy);
        btNormal=findViewById(R.id.btNormal);
        btHard=findViewById(R.id.btHard);

        Animation moveUp=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);
        Animation moveDown=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
        Animation moveDownStronger=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down_stronger);
        Animation moveUpStronger=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up_stronger);


        btEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btEasy.startAnimation(moveDown);
                btHard.startAnimation(moveDown);
                btNormal.startAnimation(moveDownStronger);
                btEasy.setOnClickListener(null);
                btNormal.setOnClickListener(null);
                btHard.setOnClickListener(null);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Transition Explode=new Explode();
                        TransitionManager.go(scene3, Explode);
                    }
                }, 2000);
            }
        });

        btNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btEasy.startAnimation(moveUp);
                btHard.startAnimation(moveDown);
                btEasy.setOnClickListener(null);
                btNormal.setOnClickListener(null);
                btHard.setOnClickListener(null);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Transition Explode=new Explode();
                        TransitionManager.go(scene3, Explode);
                    }
                }, 2000);
            }
        });

        btHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btEasy.startAnimation(moveUp);
                btNormal.startAnimation(moveUpStronger);
                btHard.startAnimation(moveUp);
                btEasy.setOnClickListener(null);
                btNormal.setOnClickListener(null);
                btHard.setOnClickListener(null);
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Transition Explode=new Explode();
                        TransitionManager.go(scene3, Explode);
                    }
                }, 2000);
            }
        });


    }
}