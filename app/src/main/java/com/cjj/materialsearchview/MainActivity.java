package com.cjj.materialsearchview;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.nineoldandroids.animation.ObjectAnimator;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;


public class MainActivity extends AppCompatActivity {
    private SupportAnimator mAnimator;
    private ImageView iv_bottom_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CardView cardView = (CardView) this.findViewById(R.id.card_search);
        cardView.setVisibility(View.INVISIBLE);
        final FloatingActionButton floatingActionButton = (FloatingActionButton) this.findViewById(R.id.floatingActionButton);
        iv_bottom_search = (ImageView) this.findViewById(R.id.iv_bottom_search);
        iv_bottom_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnimator != null && !mAnimator.isRunning()) {
                    mAnimator = mAnimator.reverse();
                    int cx = cardView.getRight();
                    float curTranslationX = iv_bottom_search.getTranslationX();
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_search, "translationX", curTranslationX, 0);
                    animator.setDuration(1000);
                    mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                            animator.start();
                        }

                        @Override
                        public void onAnimationEnd() {
                            mAnimator = null;
                            floatingActionButton.setVisibility(View.VISIBLE);
                            cardView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationCancel() {

                        }

                        @Override
                        public void onAnimationRepeat() {

                        }
                    });
                } else if (mAnimator != null) {
                    mAnimator.cancel();
                    return;
                } else {
                    int cx = cardView.getRight();
                    int cy = cardView.getBottom();
                    float curTranslationX = iv_bottom_search.getTranslationX();
                    final ObjectAnimator animator = ObjectAnimator.ofFloat(iv_bottom_search, "translationX", curTranslationX, cx / 2 - DensityUtil.dip2px(MainActivity.this, 24));
                    animator.setDuration(1000);
                    float radius = r(cardView.getWidth(), cardView.getHeight());
                    mAnimator = ViewAnimationUtils.createCircularReveal(cardView, cx / 2, cy - DensityUtil.dip2px(MainActivity.this, 32), DensityUtil.dip2px(MainActivity.this, 20), radius);
                    mAnimator.addListener(new SupportAnimator.AnimatorListener() {
                        @Override
                        public void onAnimationStart() {
                            animator.start();


                        }

                        @Override
                        public void onAnimationEnd() {

                        }

                        @Override
                        public void onAnimationCancel() {

                        }

                        @Override
                        public void onAnimationRepeat() {

                        }
                    });
                }

                mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                mAnimator.setDuration(1000);
                mAnimator.start();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardView.setVisibility(View.VISIBLE);
                floatingActionButton.setVisibility(View.GONE);
                iv_bottom_search.performClick();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    static float r(int a, int b) {
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

}
