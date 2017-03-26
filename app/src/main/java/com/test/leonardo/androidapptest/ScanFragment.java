package com.test.leonardo.androidapptest;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanFragment extends Fragment{

    private Button startScan;
    private TextView nameView;
    private TextView descriptionView;
    private Cursor piante;
    private TreeDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_scan, container, false);
        nameView = (TextView)v.findViewById(R.id.name_view);
        descriptionView = (TextView)v.findViewById(R.id.description_view);
        startScan = (Button) v.findViewById(R.id.startScan);
        startScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ScanFragment.this);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this.getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this.getContext(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                db = new TreeDatabase(this.getContext());
                piante = db.getTreeByCode(result.getContents());

                if(piante!=null) {

                    nameView.setText(piante.getString(piante.getColumnIndex("nome")));
                    descriptionView.setText(piante.getString(piante.getColumnIndex("descrizione")));
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
