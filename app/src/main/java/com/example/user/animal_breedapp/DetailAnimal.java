package com.example.user.animal_breedapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailAnimal extends AppCompatActivity {

    String name_categorie;
    String image_categorie;
    String animal_name;

    TextView n;
    TextView c;
    ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_animal);

       //get intent data
        name_categorie=getIntent().getExtras().getString("cat_na");
        image_categorie=getIntent().getExtras().getString("cat_im");
        animal_name=getIntent().getExtras().getString("name_ani");
       //intilize views
        n=findViewById(R.id.Animal_namee);
        c=findViewById(R.id.Categore_namee);
        i=findViewById(R.id.Animal_imagee);
        //set data
        Picasso.with(this).load(image_categorie).error(R.drawable.no_img).placeholder(R.drawable.no_img).into(i);
        n.setText(animal_name);
        c.setText(name_categorie);




    }
}
