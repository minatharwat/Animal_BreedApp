package com.example.user.animal_breedapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.user.animal_breedapp.Adapters.Categorie_RecycleAdapter;
import com.example.user.animal_breedapp.Models.CategoreItem;
import com.example.user.animal_breedapp.NetworkThreads.Volley;
import com.example.user.animal_breedapp.SqliteDatabase.DatabaseContract;
import com.example.user.animal_breedapp.SqliteDatabase.Db_Helper;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.user.animal_breedapp.SqliteDatabase.DatabaseContract.Categorie.TABLE_NAME;
import static com.example.user.animal_breedapp.SqliteDatabase.DatabaseContract.Categorie.U_COLUMN_NAME;

/**
 * Created by ${Mina} on 16/06/2018.
 */

public class HomeActivity extends AppCompatActivity implements Volley.API {
    public static List<CategoreItem> Breed_List;
    public static int Counter = 0;
    public static SQLiteDatabase database;
    public Categorie_RecycleAdapter recycle_categorie;
    public RecyclerView recyclerView;

    public static int x = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init_data();
        //database.delete(TABLE_NAME, null, null);
        checkConnection();

        Volley volley = new Volley(this, this);
        volley.getapi(getString(R.string.base_url));

    }

    public void init_data() {
        recyclerView = findViewById(R.id.recyle_cat);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        Db_Helper dbHelper = new Db_Helper(this);
        database = dbHelper.getWritableDatabase();
        Breed_List = new ArrayList<CategoreItem>();
    }

    @Override
    public void ConnectionDone(String msg) {


    }


    public static long addcategorie_sqlLite(String name, String image) {
        ContentValues cv = new ContentValues();
        cv.put(U_COLUMN_NAME, name);
        cv.put(DatabaseContract.Categorie.U_COLUMN_image, image);

       return database.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public  List<CategoreItem> getAllData() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        //database = this.getReadableDatabase();
        Cursor cursorz = database.rawQuery(countQuery, null);
        List<CategoreItem> x=new ArrayList<>();
        if (cursorz.moveToFirst()) {

            Breed_List.clear();
            do {
                CategoreItem r = new CategoreItem();

                r.setName(cursorz.getString(cursorz.getColumnIndex(U_COLUMN_NAME)));
                r.setCategorie_image(cursorz.getString(cursorz.getColumnIndex(DatabaseContract.Categorie.U_COLUMN_image)));

                x.add(r);
            } while (cursorz.moveToNext());
        }
        Breed_List=x;
        return Breed_List;
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
    public void checkConnection(){
        if(isOnline()){

            LoadFrom_Firebase();

        }else{
            LoadFrom_SqlDatabase();
        }
    }

    public void LoadFrom_Firebase(){

        x=0;

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference namesRef = rootRef.child("categories");
        namesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String m = dataSnapshot.getKey();
                String i= String.valueOf(dataSnapshot.child("Image").getValue());
                //save in database if it's not dublicated
                check_dublicates(m,i);
                //save in list from firebase
                Breed_List.add(new CategoreItem(m,i));
                recycle_categorie=new Categorie_RecycleAdapter(Breed_List,getApplicationContext());
                recyclerView.setAdapter(recycle_categorie);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void LoadFrom_SqlDatabase(){

        getAllData();
        recycle_categorie=new Categorie_RecycleAdapter(Breed_List,this);
        recyclerView.setAdapter(recycle_categorie);

    }

    public boolean deleteTitle(String name)
    {
        return database.delete(DatabaseContract.Categorie.TABLE_NAME, U_COLUMN_NAME + "="+"'"+name+"'", null) > 0;
    }
    public void check_dublicates(String n,String im){

        Cursor cursor = null;
        String sql ="SELECT name FROM "+TABLE_NAME+" WHERE name = "+"'"+n+"'";
        cursor= database.rawQuery(sql,null);
        if(cursor.getCount()>0){
           deleteTitle(n);
        }else{
            addcategorie_sqlLite(n,im);
        }
        cursor.close();
    }

}
