package org.tensorflow.lite.examples.objectdetection;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {

    EditText username, Email;
    Button login, cancel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        String uname = username.getText().toString();
        String eml = Email.getText().toString();

        login = findViewById(R.id.loginBTN);
        cancel = findViewById(R.id.cancelBTN);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SQLiteDatabase db = openOrCreateDatabase("GEM_LAB", Context.MODE_PRIVATE, null); //create database if doesn't exist

        ///Creating the tables

        //Table for each user
        db.execSQL("CREATE TABLE IF NOT EXISTS 'log' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'uname' TEXT,'mail' TEXT, 'image' BLOB, 'name' TEXT, 'Av.price' FLOAT, 'price' FLOAT, 'weight' FLOAT)");


        //*****************
        String mail1 = "roxsalmanulfaris7@gmail.com";
        String uname1 = "Faris";

        String query= "INSERT INTO 'log' ('uname','mail') VALUES (?,?)";
        //query to insert
        SQLiteStatement statement = db.compileStatement(query);
        //enter query to the sqlite

        statement.bindString(1,uname1); //bind the values to be in the given "?" place
        statement.bindString(2,mail1); //bind the values to be in the given "?" place
        statement.execute(); //execute the query

        //*****************

        username.setText("Faris");
        Email.setText("roxsalmanulfaris7@gmail.com");
        //end of creating the tables

    }

    public void login()
    {
        String uname = username.getText().toString();
        String eml = Email.getText().toString();

        SQLiteDatabase db = openOrCreateDatabase("GEM_LAB", Context.MODE_PRIVATE, null);


        ///Creating the tables

        //Table for each user
        db.execSQL("CREATE TABLE IF NOT EXISTS 'log' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'uname' TEXT,'mail' TEXT PRIMARY KEY, 'image' BLOB, 'name' TEXT, 'Av.price' FLOAT, 'price' FLOAT, 'weight' FLOAT)");



        final Cursor Umail = db.rawQuery("SELECT mail FROM log WHERE uname=?", new String[] {uname});


        if(Umail.moveToFirst())
        {

            String mail2 = Umail.getString(0);
            if(mail2.equals(eml)){
                Intent intent= new Intent(login.this, Home.class);
                intent.putExtra("Email", eml);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Enter the correct username and/or the email", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Sighnup to the application", Toast.LENGTH_SHORT).show();
        }

    }
}
