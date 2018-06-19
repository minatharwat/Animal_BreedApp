package com.example.user.animal_breedapp;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
    public static int x = 0;
    public Categorie_RecycleAdapter recycle_categorie;
    public RecyclerView recyclerView;

    public static long addcategorie_sqlLite(String name, String image) {
        ContentValues cv = new ContentValues();
        cv.put(U_COLUMN_NAME, name);
        cv.put(DatabaseContract.Categorie.U_COLUMN_image, image);

        return database.insertWithOnConflict(TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

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

        checkConnection();

    }

    public List<CategoreItem> getAllData() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        //database = this.getReadableDatabase();
        Cursor cursorz = database.rawQuery(countQuery, null);
        List<CategoreItem> x = new ArrayList<>();
        if (cursorz.moveToFirst()) {

            Breed_List.clear();
            do {
                CategoreItem r = new CategoreItem();

                r.setName(cursorz.getString(cursorz.getColumnIndex(U_COLUMN_NAME)));
                r.setCategorie_image(cursorz.getString(cursorz.getColumnIndex(DatabaseContract.Categorie.U_COLUMN_image)));

                x.add(r);
            } while (cursorz.moveToNext());
        }
        Breed_List = x;
        return Breed_List;
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = null;
        if (cm != null) {
            netInfo = cm.getActiveNetworkInfo();
        }
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    public void checkConnection() {
        if (isOnline()) {

            LoadFrom_Firebase();

        } else {
            LoadFrom_SqlDatabase();

        }
    }

    public void LoadFrom_Firebase() {

        x = 0;

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference namesRef = rootRef.child("categories");
        namesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String m = dataSnapshot.getKey();
                String i = String.valueOf(dataSnapshot.child("Image").getValue());
                //save in database if it's not dublicated
                check_dublicates(m, i);
                //save in list from firebase
                Breed_List.add(new CategoreItem(m, i));
                recycle_categorie = new Categorie_RecycleAdapter(Breed_List, getApplicationContext());
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

    public void LoadFrom_SqlDatabase() {

        getAllData();
        recycle_categorie = new Categorie_RecycleAdapter(Breed_List, getApplicationContext());
        recyclerView.setAdapter(recycle_categorie);

    }

    public boolean deleteTitle(String name) {
        return database.delete(DatabaseContract.Categorie.TABLE_NAME, U_COLUMN_NAME + "=" + "'" + name + "'", null) > 0;
    }

    public void check_dublicates(String n, String im) {

        Cursor cursor = null;
        String sql = "SELECT name FROM " + TABLE_NAME + " WHERE name = " + "'" + n + "'";
        cursor = database.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            deleteTitle(n);
        } else {
            addcategorie_sqlLite(n, im);
        }
        cursor.close();
    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryRefinementEnabled(true);

        //______________
        // Get SearchView autocomplete object.
        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorWhite));
        searchAutoComplete.setDropDownBackgroundResource(getResources().getColor(R.color.colorAccent));

        // Create a new ArrayAdapter and add data to search auto complete object.
        //get local data to autocomplete
        String[] array = new String[getAllData().size()];
        int index = 0;
        for (CategoreItem value : getAllData()) {
            array[index] = (String) value.getName();
            index++;
        }


        ArrayAdapter<String> breed_names = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, array);
        searchAutoComplete.setAdapter(breed_names);

        // Listen to search view item on click event.
        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
                String queryString = (String) adapterView.getItemAtPosition(itemIndex);
                for (int k = 0; k < Breed_List.size(); k++) {
                    if (queryString.equals(Breed_List.get(k).getName())) {

                        Intent intentQ = new Intent(getApplicationContext(), DetailCategory.class);
                        intentQ.putExtra("name_c", Breed_List.get(k).getName());
                        intentQ.putExtra("image_c", Breed_List.get(k).getCategorie_image());
                        startActivity(intentQ);
                    }

                }
            }
        });

        //______________

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {

                    //TODO: Reset your views
                    return false;
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    for (int k = 0; k < Breed_List.size(); k++) {
                        if (s.equals(Breed_List.get(k).getName())) {

                            Intent intentQ = new Intent(getApplicationContext(), DetailCategory.class);
                            intentQ.putExtra("name_c", Breed_List.get(k).getName());
                            intentQ.putExtra("image_c", Breed_List.get(k).getCategorie_image());
                            startActivity(intentQ);
                        }

                    }
                    return false; //do the default
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //NOTE: doing anything here is optional, onNewIntent is the important bit
                    if (s.length() > 1) {

                        searchView.setQueryRefinementEnabled(true);
                        //2 chars or more
                        //TODO: filter/return results
                    } else if (s.length() == 0) {
                        //TODO: reset the displayed data
                    }
                    return false;
                }

            });
        }
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);


            for (int k = 0; k < Breed_List.size(); k++) {
                if (query.equals(Breed_List.get(k).getName())) {

                    Intent intentQ = new Intent(getApplicationContext(), DetailCategory.class);
                    intentQ.putExtra("name_c", Breed_List.get(k).getName());
                    intentQ.putExtra("image_c", Breed_List.get(k).getCategorie_image());
                    startActivity(intentQ);
                }

            }

        }
    }


}


