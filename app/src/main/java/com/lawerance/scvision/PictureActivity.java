package com.lawerance.scvision;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.lawerance.scvision.API.DjangoApi;
import com.marozzi.roundbutton.RoundButton;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class PictureActivity extends AppCompatActivity {

    private ImageView imageView;
    private DatabaseReference mMessagesDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private RoundButton start_processing;
    private static final String IMAGE_DIRECTORY = "/CustomImage";
    String advice_photo_text = "Make sure photo is <font color='#EE0000'>Sharp, Centered</font> and free of hair or other obstructing objects.";
    private TextView tv_advice_photo;
    private ChildEventListener mChildEventListener;
    private int ready = 0;
    private boolean isChildAddedEntered = false;
    ArrayList<String> keys = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_layout);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("users/" + id);
        imageView = findViewById(R.id.img);

        start_processing = findViewById(R.id.start_processing);
        tv_advice_photo = findViewById(R.id.tv_photo_advice);
        tv_advice_photo.setText(Html.fromHtml(advice_photo_text));
        findViewById(R.id.retake_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        imageView.setImageBitmap(Custom_Camera_Activity.bitmap);
        saveImage(Custom_Camera_Activity.bitmap);
        Log.e("xx", "Picture Activity entered");
        mChildEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                   startActivity(new Intent(PictureActivity.this, Cancer_tests_list_Activity.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.e("xx", "child changed");

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };


        start_processing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessagesDatabaseReference.addChildEventListener(mChildEventListener);

                findViewById(R.id.loading_bar).setVisibility(View.VISIBLE);
                start_processing.setClickable(false);
                uploadFoto();

//        doUpoload();
            }
        });
    }

    private void uploadFoto() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DjangoApi.DJANGO_SITE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        File file = new File(this.getCacheDir(), id + ".png");
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Custom_Camera_Activity.bitmap.compress(Bitmap.CompressFormat.JPEG, 70, os);
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        final DjangoApi postApi = retrofit.create(DjangoApi.class);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/data"), file);
        final MultipartBody.Part multiPartBody = MultipartBody.Part
                .createFormData("model_pic", file.getName(), requestBody);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String myid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("users/" + myid);


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Call<RequestBody> call = postApi.uploadFile(multiPartBody);

                call.enqueue(new Callback<RequestBody>() {
                    @Override
                    public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {

                        Log.e("good", "good" + response.code());

                    }

                    @Override
                    public void onFailure(Call<RequestBody> call, Throwable t) {

                        Log.e("fail", "fail" + t.getMessage());
                    }
                });
                return null;
            }
        };

        asyncTask.execute();


    }


    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.

        if (!wallpaperDirectory.exists()) {
            Log.d("dirrrrrr", "" + wallpaperDirectory.mkdirs());
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();   //give read write permission
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";

    }
}