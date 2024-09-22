package org.tensorflow.lite.examples.objectdetection;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.tensorflow.lite.examples.objectdetection.fragments.CameraFragment;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class View_Inventory extends AppCompatActivity {

    RecyclerView brand_list;
    RelativeLayout go_back;
    LinearLayout addBTN;

    String email;

    ArrayList<gem> gemList = new ArrayList<gem>();
    private RecyclerView.Adapter mAdapter;//view adapter
    private RecyclerView.LayoutManager layoutManager; //view layout manager


    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_inventory);

        Intent i = getIntent();

        email = i.getStringExtra("Email").toString();

        brand_list = (RecyclerView) findViewById(R.id.brand_list);
        brand_list.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);//assign layout manager
        mAdapter = new adapterBrand(gemList, View_Inventory.this); //updateBTN brand_list data to adapter class

        go_back = findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_Inventory.this, Home.class);
                startActivity(intent);
            }
        });

        addBTN = findViewById(R.id.addBTN);

        addBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(View_Inventory.this, Camera.class);
                startActivity(intent);
            }
        });

        SQLiteDatabase db1 = openOrCreateDatabase("GEM_LAB", Context.MODE_PRIVATE, null);
        final Cursor d = db1.rawQuery("SELECT * FROM inventory4", null);

        String test = "";

            if (true) {
                int email1 = d.getColumnIndex("email");
                int gemName = d.getColumnIndex("gem_Name");
                int gemWeight = d.getColumnIndex("gem_Weight");
                int gemShape = d.getColumnIndex("gem_Shape");
                int perCarat = d.getColumnIndex("per_Carat");
                int gemPrice = d.getColumnIndex("gem_Price");
                int imagePath = d.getColumnIndex("imagePath");

                if (d.moveToFirst()) {
                    do {
                        gem ba = new gem();
                        test = d.getString(email1);
                        ba.email = test;
                        ba.gemName = d.getString(gemName);
                        ba.gemWeight = d.getString(gemWeight);
                        ba.gemShape = d.getString(gemShape);
                        ba.perCarat = d.getString(perCarat);
                        ba.gemPrice = d.getString(gemPrice);
                        ba.imagePath = d.getString(imagePath);

                        if(test.equals(email)) {

                            gemList.add(ba);
                        }

                    } while (d.moveToNext());
                } else {
                    Toast.makeText(getApplicationContext(), "database error", Toast.LENGTH_LONG).show();
                }

                brand_list.setLayoutManager(layoutManager);
                brand_list.setAdapter(mAdapter);
            }
        }

}