package com.lawerance.scvision;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Pop_Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_layout);

        ImageView imageView1 = findViewById(R.id.image1);
        ImageView imageView2 = findViewById(R.id.image2);
        Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        imageView1.startAnimation(myFadeInAnimation);
        imageView2.startAnimation(myFadeInAnimation);

    }


}
