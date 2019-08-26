package com.lawerance.scvision;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comix.overwatch.HiveProgressView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lawerance.scvision.Adapters.RecycleViewImageRecordAdapter;
import com.lawerance.scvision.Adapters.RecyclerItemClickListener;
import com.lawerance.scvision.Models.gallery;


import java.util.ArrayList;

public class Cancer_tests_list_Activity extends Activity {
    private RecyclerView list_users;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<gallery> values = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private HiveProgressView loading_images;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallary_list_layout);
        linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        loading_images = findViewById(R.id.loading_images);

        firebaseDatabase = FirebaseDatabase.getInstance();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference = firebaseDatabase.getReference();
        list_users = findViewById(R.id.list_users);
        databaseReference.child("users/" + id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {

                        gallery post = postSnapShot.getValue(gallery.class);
                        values.add(post);
                        loading_images.setVisibility(View.GONE);
                        findViewById(R.id.fetching_results).setVisibility(View.GONE);

                    }


                }



                RecycleViewImageRecordAdapter adapters = new RecycleViewImageRecordAdapter(Cancer_tests_list_Activity.this, values);
                list_users.setAdapter(adapters);
                list_users.setLayoutManager(linearLayoutManager);
                list_users.hasFixedSize();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
        list_users.addOnItemTouchListener(
                new RecyclerItemClickListener(this, list_users, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        gallery currentUser = values.get(position);
                        ImageView img = view.findViewById(R.id.image);
                        img.buildDrawingCache();

                        Bitmap bitmap = img.getDrawingCache();

                        String url = currentUser.getImageUrl();
                        String date = currentUser.getTime();

                        String type = currentUser.getType();
                        Intent intent = new Intent(Cancer_tests_list_Activity.this, Cancer_Info_Activity.class);
                        intent.putExtra("type", type);
                        intent.putExtra("url", url);
                        intent.putExtra("date", date);
                        intent.putExtra("BitmapImage", bitmap);

                        startActivity(intent);


                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );


    }

}
