package com.example.spaceship;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.style.BulletSpan;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Scene scene1, scene2, scene3, scene4;
    Point startingPosition;
    ImageView spaceShip;
    RelativeLayout gameScreen;
    int screenWidth, screenHeight;
    List<Meteor> meteors;
    int Score;
    int type=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Score=0;

        //Setup menu
        ViewGroup menuScene=findViewById(R.id.menuContainer);
        scene1=Scene.getSceneForLayout(menuScene,R.layout.start_menu, this);
        scene2=Scene.getSceneForLayout(menuScene, R.layout.choose_type,this);
        scene3=Scene.getSceneForLayout(menuScene, R.layout.blank_menu, this);
        scene4=Scene.getSceneForLayout(menuScene,R.layout.game_over, this);
        scene1.enter();
        StartMenu();

        // Move Left right
        spaceShip=findViewById(R.id.spaceShip);
        gameScreen=findViewById(R.id.gameScreen);

        screenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeight=Resources.getSystem().getDisplayMetrics().heightPixels;

        startingPosition=new Point(screenWidth/2-50, 0);
        spaceShip.setX(startingPosition.x);
        spaceShip.setY(startingPosition.y);

        Button btMoveLeft=findViewById(R.id.btMoveLeft);
        Button btMoveRight=findViewById(R.id.btMoveRight);

        btMoveLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startingPosition=new Point(startingPosition.x+50,startingPosition.y);
                spaceShip.setX(startingPosition.x);
            }
        });
        btMoveRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startingPosition=new Point(startingPosition.x-50,startingPosition.y);
                spaceShip.setX(startingPosition.x);
            }
        });
        // Xoay cái layout
        gameScreen.setRotation(180.f);

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
                        type=1;
                        StartGame();
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
                        type=2;
                        StartGame();
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
                        type=3;
                        StartGame();
                    }
                }, 2000);
            }
        });
    }
    void StartGame() {
        meteors=new ArrayList<Meteor>();
        RandomMeteor();

        Button btShoot=findViewById(R.id.btShoot);
        btShoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Point bulletCoor=new Point((int)spaceShip.getX()+60,100);
                Bullet b= new Bullet(getApplicationContext(),gameScreen,bulletCoor,screenHeight);
                ValueAnimator animation2 = ValueAnimator.ofFloat( 100.f, (float)(screenHeight*0.7));
                animation2.setDuration(13000);
                animation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                        float animatedValue = (float)updatedAnimation.getAnimatedValue();
                        b.bullet.setY(animatedValue);
                        int bulletX=(int)(b.bullet.getX());
                        int bulletY=(int)(b.bullet.getY());
                        List<Meteor> destroyMeteor=new ArrayList<Meteor>();
                        for(int i=0;i<meteors.size();i++)
                        {
                            int meteorX=(int)(meteors.get(i).meteor.getX());
                            int meteorY=(int)(meteors.get(i).meteor.getY());
                            if(meteorX+64>bulletX&&meteorX<bulletX+10&&bulletY+20>=meteorY)
                            {
                                destroyMeteor.add(meteors.get(i));
                                Score++;
                                TextView tvScore=findViewById(R.id.tvScore);
                                tvScore.setText("Score: "+String.valueOf(Score));
                            }
                        }
                        for(int i=0;i<destroyMeteor.size();i++)
                        {
                            Random r= new Random();
                            Meteor k= destroyMeteor.get(i);
                            meteors.remove(k);
                            gameScreen.removeView(k.meteor);
                            //Xoá bỏ thiên thạch trong list
                            int x=Math.abs(r.nextInt())%(screenWidth-128) + 64;
                            Point meteorCoord=new Point(x,(int)(screenHeight*0.7));
                            Meteor m=new Meteor(getApplicationContext(),meteorCoord,screenHeight,gameScreen);
                            meteors.add(m);

                            ValueAnimator animation = ValueAnimator.ofFloat( (float)(screenHeight*0.7) ,0.f);
                            animation.setDuration(20000);
                            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                                    float animatedValue = (float)updatedAnimation.getAnimatedValue();
                                    m.meteor.setY(animatedValue);
                                    if(m.meteor.getY()<=10&&m.meteor.getParent()==gameScreen)
                                    {
                                        for(int i=0;i<meteors.size();i++)
                                        {
                                            gameScreen.removeView(meteors.get(i).meteor);
                                        }
                                        meteors.clear();
                                        Transition Fade=new Fade();
                                        TransitionManager.go(scene4, Fade);

                                        Button btPlayAgain=findViewById(R.id.btPlayAgain);
                                        btPlayAgain.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Transition Fade=new Fade();
                                                TransitionManager.go(scene2, Fade);
                                                ChooseType();
                                            }
                                        });
                                        Button btExitAgain=findViewById(R.id.btExitAgain);
                                        btExitAgain.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                finishAffinity();
                                            }
                                        });
                                    }
                                }

                            });
                            animation.start();
                        }

                    }

                });
                animation2.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        gameScreen.removeView(b.bullet);

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                animation2.start();
            }
        });
    }
    void RandomMeteor() {
        Random r= new Random();
        for(int i=0;i<type;i++)
        {
            int x=Math.abs(r.nextInt())%(screenWidth-128) + 64;
            Point meteorCoord=new Point(x,(int)(screenHeight*0.7));
            Meteor m=new Meteor(getApplicationContext(),meteorCoord,screenHeight,gameScreen);
            meteors.add(m);

            ValueAnimator animation = ValueAnimator.ofFloat( (float)(screenHeight*0.7) ,0.f);
            animation.setDuration(20000);
            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator updatedAnimation) {
                    float animatedValue = (float)updatedAnimation.getAnimatedValue();
                    m.meteor.setY(animatedValue);

                    if(m.meteor.getY()<=10&&m.meteor.getParent()==gameScreen)
                    {
                        for(int i=0;i<meteors.size();i++)
                        {
                            gameScreen.removeView(meteors.get(i).meteor);
                        }
                        meteors.clear();
                        Score=0;
                        TextView tvScore=findViewById(R.id.tvScore);
                        tvScore.setText("Score: "+String.valueOf(Score));
                        Transition Fade=new Fade();
                        TransitionManager.go(scene4, Fade);

                        Button btPlayAgain=findViewById(R.id.btPlayAgain);
                        btPlayAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Transition Fade=new Fade();
                                TransitionManager.go(scene2, Fade);
                                ChooseType();
                            }
                        });
                        Button btExitAgain=findViewById(R.id.btExitAgain);
                        btExitAgain.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finishAffinity();
                            }
                        });
                    }
                }

            });
            animation.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if(!meteors.contains(m))
                    {
                        return;
                    }

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            m.setAnimator(animation);
            animation.start();

        }
    }
}