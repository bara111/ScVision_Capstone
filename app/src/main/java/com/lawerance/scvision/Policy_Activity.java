package com.lawerance.scvision;

import android.app.Activity;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;

public class Policy_Activity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.policy_layout);

        PDFView pdfView=findViewById(R.id.pdfView);
        pdfView.fromAsset("policy.pdf").load();
    }
}
