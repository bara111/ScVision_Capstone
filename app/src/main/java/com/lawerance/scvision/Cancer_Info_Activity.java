package com.lawerance.scvision;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Cancer_Info_Activity extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String type = intent.getStringExtra("type");
        String date = intent.getStringExtra("date");
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");
        String text = "What does this mean? \n" +
                "Our algorithm performed a check on your photo and did not find signs of skin cancer. These signs include irregular patterns, multiple colors, asymmetry, size and uneven borders. \n" +
                "However, you have indicated that your skin spot is either infected, changing or bleeding. These symptoms are not detected by our algorithm but can be a potentially dangerous sign. If at any time you feel uncomfortable about this spot, please visit a doctor.";


        ImageView result_color=findViewById(R.id.result_color);

        imageView = findViewById(R.id.backdrop);
        if(type.equals("High Risk")){
            result_color.setImageDrawable(getDrawable(R.drawable.custom_circle_red));

        }else if(type.equals("Low Risk")){
            result_color.setImageDrawable(getDrawable(R.drawable.camera_circle_center));
        }
        else if(type.equals("Meduim Risk")){
            result_color.setImageDrawable(getDrawable(R.drawable.camera_circle_center3));
        }
        imageView.setImageBitmap(bitmap);
        TextView result=findViewById(R.id.result);
        result.setText(type);

    }
}