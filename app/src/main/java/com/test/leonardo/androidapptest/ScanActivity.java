package com.test.leonardo.androidapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanActivity extends AppCompatActivity{

    private Button startScan;
    private TextView nameView;
    private TextView descriptionView;
    private Cursor piante;
    private TreeDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        startScan = (Button)findViewById(R.id.startScan);
        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ScanActivity.this);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                db = new TreeDatabase(this);
                piante = db.getTreeByCode(result.getContents());

                if(piante!=null) {
                    nameView = (TextView) findViewById(R.id.name_view);
                    nameView.setText(piante.getString(piante.getColumnIndex("nome")));

                    descriptionView = (TextView) findViewById(R.id.description_view);
                    descriptionView.setText(piante.getString(piante.getColumnIndex("descrizione")));
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
