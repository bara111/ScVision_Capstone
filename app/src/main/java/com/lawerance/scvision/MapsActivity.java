package com.lawerance.scvision;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private final String najah_hospital_number = "+97092341501";
    private final String arabic_hospital_number = "+97092353000";
    private final String national_hospital_number = "+97092383599";
    private final String bible_hosptial_number = "092383818";
    private GoogleMap mMap;
    private Marker myMarker_najah, myMarker_arabic, myMarker_national, myMarker_bible;
    private Intent intent;
    private Bundle bundle;
    private TextView NameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        intent = new Intent(this, MyService.class);
        intent.setAction("CALL_HOSPITAL");
        bundle = new Bundle();
        Intent nameIntent=getIntent();

        NameTextView=findViewById(R.id.user_name);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        NameTextView.setText(nameIntent.getStringExtra("username"));
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        String najah_hospital_number = "+97092341501";
        String arabic_hospital_number = "+97092353000";
        String national_hospital_number = "+97092383599";
        String bible_hosptial_number = "092383818";
        LatLng nablus_location = new LatLng(32.223577, 35.262271);
        LatLng najah_hospital_location = new LatLng(32.239231, 35.245670);
        LatLng arabic_hospital_location = new LatLng(32.223743, 35.240013);
        LatLng bible_hospital_location = new LatLng(32.222960, 35.254687);
        LatLng national_hospital_location = new LatLng(32.224799, 35.263592);


        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.auricular_phone_symbol_in_a_circle);

        myMarker_najah = googleMap.addMarker(new MarkerOptions()
                .position(najah_hospital_location)
                .title("Najah")
                .snippet("Najah hospital")
                .icon(icon));
        myMarker_national = googleMap.addMarker(new MarkerOptions()
                .position(national_hospital_location)
                .title("National")
                .snippet("National hospital")
                .icon(icon));
        myMarker_arabic = googleMap.addMarker(new MarkerOptions()
                .position(arabic_hospital_location)
                .title("Arabic")
                .snippet("Arabic hospital")
                .icon(icon));
        myMarker_bible = googleMap.addMarker(new MarkerOptions()
                .position(bible_hospital_location)
                .title("Arabic")
                .snippet("Arabic hospital")
                .icon(icon));


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nablus_location, 13));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(myMarker_najah)) {
            bundle.putString("number",najah_hospital_number);
            intent.putExtra("bundle",bundle);
            startService(intent);
            return true;
        }
        if (marker.equals(myMarker_national)) {
            bundle.putString("number",national_hospital_number);
            intent.putExtra("bundle",bundle);
            startService(intent);
            return true;
        }
        if (marker.equals(myMarker_arabic)) {
            bundle.putString("number",arabic_hospital_number);
            intent.putExtra("bundle",bundle);
            startService(intent);

            return true;
        }
        if (marker.equals(myMarker_bible)) {
            bundle.putString("number",bible_hosptial_number);
            intent.putExtra("bundle",bundle);
            startService(intent);

            return true;
        }
        return true;
    }


}
