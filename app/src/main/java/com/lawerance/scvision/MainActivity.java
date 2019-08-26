package com.lawerance.scvision;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.lawerance.scvision.Adapters.GridViewAdapter;
import com.lawerance.scvision.Widget.UpdateUIWidgetService;
import com.marozzi.roundbutton.RoundButton;

import java.util.Arrays;
import java.util.List;

import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private List<AuthUI.IdpConfig> providers;
    private ImageView start_scanning;
    private ImageView shine;
    private Animation animation;
    private RoundButton view_more_details;
    private Context mContext;
    private GridView gridView;
    private androidx.appcompat.widget.Toolbar toolbar;
    private Intent intentWidget;

    private FirebaseAuth mFirebaseAuth;

    private final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private static final int My_Request_Code = 7177;
    boolean toggle = false;
    public static String[] gridViewStrings = {
            "Gallery",
            "Location",
            "Video",
            "Policy",
    };
    public static int[] gridViewImages = {
            R.drawable.images,
            R.drawable.location,
            R.drawable.medical,
            R.drawable.pill,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentWidget = new Intent(this, UpdateUIWidgetService.class);

        intentWidget.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        startService(intentWidget);
        toolbar = findViewById(R.id.app_bar);
        start_scanning = findViewById(R.id.start_scanning);

        shine = findViewById(R.id.shine);
        view_more_details = findViewById(R.id.view_more_details);
        setSupportActionBar(toolbar);
        mContext = this;
        gridView = (GridView) findViewById(R.id.grid);

        Drawable droid = ContextCompat.getDrawable(this, R.drawable.skin_scanner);

        SharedPreferences prefs = getSharedPreferences("showHint1", MODE_PRIVATE);
        Boolean restoredText = prefs.getBoolean("shown", false);
        if (!restoredText) {

            SharedPreferences.Editor editor = getSharedPreferences("showHint1", MODE_PRIVATE).edit();
            editor.putBoolean("shown", true);
            editor.apply();
            showHint();

        }
        gridView.setAdapter(new GridViewAdapter(this, gridViewStrings, gridViewImages));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0)
                    startActivity(new Intent(MainActivity.this, Cancer_tests_list_Activity.class));

                if (position == 1) {
                    Intent intent=new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("username", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                    startActivity(intent);

                }
                if (position == 2)
                    startActivity(new Intent(MainActivity.this, YoutubeAPI.class));
                if (position == 3)
                    startActivity(new Intent(MainActivity.this, Policy_Activity.class));


            }
        });
        bounce_btn_animation();
        start_scanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(mContext,
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)
                    startActivity(new Intent(MainActivity.this, Custom_Camera_Activity.class));
                else
                    checkForCameraPermission();
            }
        });

        view_more_details.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                toggle = !toggle;


                view_more_details.startAnimation();
                startActivity(new Intent(MainActivity.this, Pop_Activity.class));


                view_more_details.revertAnimation();

            }
        });
        enter_animation();

        //not signed in
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()

        );
        showSignInOptions();
    }

    private void showHint() {
        TapTargetView.showFor(this,                 // `this` is an Activity
                TapTarget.forView(findViewById(R.id.start_scanning), "Press To Start Scanning", "")
                        // All options below are optional
                        .outerCircleColor(R.color.white)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.6f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(10)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.white)  // Specify the color of the description text
                        .textColor(R.color.black)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(true)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                        // Specify a custom drawable to draw as the target
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                    }
                });

    }

    private void bounce_btn_animation() {

        Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.btn_bounce);

        myAnim.setRepeatMode(Animation.REVERSE);
        myAnim.setRepeatCount(Animation.INFINITE);
        // Use bounce interpolator with amplitude 0.2 and frequency 20
        CustomBounceInterpolator interpolator = new CustomBounceInterpolator(0.2, 20);
        myAnim.setInterpolator(interpolator);

        view_more_details.startAnimation(myAnim);

    }


    private void checkForCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {


            {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);


            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    startActivity(new Intent(MainActivity.this, Custom_Camera_Activity.class));
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(mContext, "Please Allow App To Access Camra Device", Toast.LENGTH_SHORT).show();
                }
                return;
            }


        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            startShiningAnimation();

        }
    }


    public void enter_animation() {
        LinearLayout dialog = (LinearLayout) findViewById(R.id.dialog);
        dialog.setVisibility(LinearLayout.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.enter_layout_animation);
        animation.setDuration(1000);
        dialog.setAnimation(animation);
        dialog.animate();
        animation.start();
    }

    private void startShiningAnimation() {
        if (animation == null) {
            animation = new TranslateAnimation(0, start_scanning.getWidth() + shine.getWidth() + 50, 0, 0);
            animation.setDuration(2000);
            animation.setFillAfter(false);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            shine.startAnimation(animation);
        }
    }


    public void showSignInOptions() {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setTheme(R.style.MyTheme)
                    .setLogo(R.drawable.asset_1xxxhdpi)
                    .build(), My_Request_Code);
        }

    }


    @Override
    protected void onActivityResult(int my_Request_Code, int result_Code, @Nullable Intent data) {
        super.onActivityResult(my_Request_Code, result_Code, data);
        if (my_Request_Code == My_Request_Code) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (result_Code == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


            }
        }
    }

}
