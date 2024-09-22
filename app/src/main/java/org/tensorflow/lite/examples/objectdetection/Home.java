package org.tensorflow.lite.examples.objectdetection;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.util.Map;

public class Home extends AppCompatActivity {

    ImageButton open_camera, inventory_btn;
    Button Market_btn, save_btn;
    TextView Email, weight;
    EditText average_price, Name, carat_price, gem_Shape, gem_Price;
    FirebaseFirestore firestore;

    String email="";
    String imagePath = "";
    String mail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Intent i =getIntent();

        //Values from login page on click
        email = i.getStringExtra("Email").toString();

        Name = findViewById(R.id.Name_txt);
        weight = findViewById(R.id.weight_txt);
        average_price = findViewById(R.id.avg_price_txt);
        carat_price = findViewById(R.id.per_carat_txt);
        gem_Shape = findViewById(R.id.gemShape_txt);
        save_btn = findViewById(R.id.save_btn);
        Market_btn = findViewById(R.id.return_price);
        inventory_btn = findViewById(R.id.inventory);

        SQLiteDatabase db = openOrCreateDatabase("GEM_LAB", Context.MODE_PRIVATE, null);





        final Cursor c = db.rawQuery("SELECT * FROM inventory1", null);
        if (c.moveToLast()) {
            do {
                mail = c.getString(1);
                imagePath = c.getString(2);

                Log.e("TAG", imagePath);
                // do something with the product and quantity
            } while (c.moveToNext());
        }

        File imageFile = new File(imagePath.toString());
        if (imageFile.exists() && imageFile.isFile() && imageFile.getName().endsWith(".jpg")) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            ImageView imageView = findViewById(R.id.image_frame);
            imageView.setImageBitmap(imageBitmap);
        }

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = openOrCreateDatabase("GEM_LAB", Context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS 'inventory4' ('gemID' INTEGER PRIMARY KEY AUTOINCREMENT, 'email' TEXT, 'gem_Name' TEXT,'gem_Weight' TEXT,'gem_Shape' TEXT,'per_Carat' TEXT, 'gem_Price' TEXT, 'imagePath' TEXT)");
                //create table
                String query= "INSERT INTO 'inventory4' ('email','gem_Name','gem_Weight','gem_Shape','per_Carat', 'gem_Price', 'imagePath') VALUES (?,?,?,?,?,?,?)";
                //query to insert
                SQLiteStatement statement = db.compileStatement(query);
                //enter query to the sqlite

                statement.bindString(1,email); //bind the values to be in the given "?" place
                statement.bindString(2,Name.getText().toString()); //bind the values to be in the given "?" place
                statement.bindString(3,weight.getText().toString());
                statement.bindString(4,gem_Shape.getText().toString()); //bind the values to be in the given "?" place
                statement.bindString(5,carat_price.getText().toString()); //bind the values to be in the given "?" place
                statement.bindString(6,imagePath);
                statement.execute(); //execute the query
                Toast.makeText(getApplicationContext(), "Gem added to the inventory4", Toast.LENGTH_LONG).show();
            }
        });

        Market_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((Name.getText().toString().toLowerCase()).equals("blue sapphire")){
                    blueSapphire(Float.parseFloat(weight.getText().toString()));

                }else if(Name.getText().toString().equals("ruby")){
                    ruby(Float.parseFloat(weight.getText().toString()));
                }else if(Name.getText().toString().equals("amethyst")) {
                    amethyst(Float.parseFloat(weight.getText().toString()));
                }
            }
        });

        inventory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Home.this, View_Inventory.class);

                startActivity(intent);
            }
        });

        open_camera = findViewById(R.id.camera_btn);
        open_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(Home.this, Camera.class);
                startActivity(intent);
            }
        });
        inventory_btn = findViewById(R.id.inventory);
        inventory_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, View_Inventory.class);
                intent.putExtra("Email", email);
                startActivity(intent);
            }
        });
    }
    public void blueSapphire(float weight){
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("bluesaphire").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            if (documentSnapshot.exists()) {
                                float price = 0;
                                float total = 0;
                                Map<String, Object> user = documentSnapshot.getData();
                                if(weight<1) {
                                    price = Float.parseFloat(user.get("less 1").toString());
                                } else if(weight<2) {
                                    price = Float.parseFloat(user.get("less 2").toString());
                                }else if(weight<3) {
                                    price = Float.parseFloat(user.get("less 3").toString());
                                }else if(weight<5) {
                                    price = Float.parseFloat(user.get("less 4").toString());

                                }else if(weight<7) {
                                    price = Float.parseFloat(user.get("less 7").toString());
                                }else  {
                                    Toast.makeText(getApplicationContext(), "Maximum weight range has been surpassed", Toast.LENGTH_LONG).show();
                                }
                                total = price * weight;
                                average_price.setText(Float.toString(total));
                                carat_price.setText(Float.toString(price));
                                Toast.makeText(getApplicationContext(), "Average price of the gem: $" + total, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error retrieving documents: ", e);
                    }
                });
    }

    public void ruby(float weight){
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("ruby").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            if (documentSnapshot.exists()) {
                                float price = 0;
                                float total = 0;
                                Map<String, Object> user = documentSnapshot.getData();
                                if(weight<1) {
                                    price = Float.parseFloat(user.get("less 1").toString());
                                } else if(weight<2) {
                                    price = Float.parseFloat(user.get("less 2").toString());
                                }else if(weight<3) {
                                    price = Float.parseFloat(user.get("less 3").toString());
                                }else if(weight<5) {
                                    price = Float.parseFloat(user.get("less 4").toString());
                                }else if(weight<7) {
                                    price = Float.parseFloat(user.get("less 6").toString());
                                }else  {
                                    Toast.makeText(getApplicationContext(), "Maximum weight range has been surpassed", Toast.LENGTH_LONG).show();
                                }
                                total = price * weight;
                                average_price.setText(Float.toString(total));
                                carat_price.setText(Float.toString(price));
                                Toast.makeText(getApplicationContext(), "Average price of the gem: $" + total, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error retrieving documents: ", e);
                    }
                });
    }

    public void amethyst(float weight){
        firestore = FirebaseFirestore.getInstance();

        firestore.collection("amethyst").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            if (documentSnapshot.exists()) {
                                float price = 0;
                                float total = 0;
                                Map<String, Object> user = documentSnapshot.getData();
                                    price = Float.parseFloat(user.get("price").toString());
                                    total = price * weight;
                                average_price.setText(Float.toString(total));
                                carat_price.setText(Float.toString(price));
                                Toast.makeText(getApplicationContext(), "Average price of the gem: $" + total, Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error retrieving documents: ", e);
                    }
                });
    }
}