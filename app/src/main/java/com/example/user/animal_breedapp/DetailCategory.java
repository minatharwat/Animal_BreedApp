package com.example.user.animal_breedapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.animal_breedapp.Adapters.Animal_Adapter;
import com.example.user.animal_breedapp.Models.Animal;
import com.example.user.animal_breedapp.NetworkThreads.Volley;
import com.example.user.animal_breedapp.Utilites.JasonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${Mina} on 18/06/2018.
 */

public class DetailCategory extends AppCompatActivity implements Volley.API{

    public static String name_re;
    public static String image_re;
    TextView title;
    ImageView img;
    RecyclerView recyclerView0;
    Animal_Adapter adapter;
    List<Animal> q;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_categorie);


        name_re=getIntent().getExtras().getString("name_c");
        image_re=getIntent().getExtras().getString("image_c");

        title=findViewById(R.id.title);
        title.setText(name_re);
        img=findViewById(R.id.img_de_cat);
        Picasso.with(this).load(image_re).error(R.drawable.no_img).placeholder(R.drawable.no_img).into(img);


        Volley volley = new Volley(this, this);
        volley.getapi(getString(R.string.base)+name_re+"/list");

    }

    @Override
    public void ConnectionDone(String msg)  {

         q= new ArrayList<Animal>();
         q.clear();
         q= JasonParser.parseAnimals(msg);

        recyclerView0 = findViewById(R.id.recyc_cat_de);
        recyclerView0.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        adapter=new Animal_Adapter(q,getApplicationContext());
        recyclerView0.setAdapter(adapter);


    }
}
