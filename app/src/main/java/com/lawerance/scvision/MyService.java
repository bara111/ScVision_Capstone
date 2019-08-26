package com.lawerance.scvision;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyService extends IntentService {
    public MyService() {
        super("MyService");
    }

    public MyService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction().equals("CALL_HOSPITAL")) {
            Bundle bundle = intent.getBundleExtra("bundle");
            String number=bundle.getString("number");
            dialContactPhone(number);
        }
    }
    private void dialContactPhone( String phoneNumber) {
        Intent intent =new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        getApplication().startActivity(intent);
    }
}
