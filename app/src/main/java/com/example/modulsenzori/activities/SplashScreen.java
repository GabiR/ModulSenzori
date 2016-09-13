package com.example.modulsenzori.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.eftimoff.androipathview.PathView;
import com.example.modulsenzori.R;

/**
 * Created by GabiRotaru on 08/07/16.
 */
public class SplashScreen extends Activity {

    RelativeLayout relativeLayout;
    PathView pathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        pathView = (PathView) findViewById(R.id.pathView);

        pathView.getPathAnimator()
                .delay(100)
                .duration(1500)
                .interpolator(new AccelerateDecelerateInterpolator())
                .listenerEnd(new PathView.AnimatorBuilder.ListenerEnd() {
                    @Override
                    public void onAnimationEnd() {
                        Intent myIntent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                })
                .start();
    }
}
