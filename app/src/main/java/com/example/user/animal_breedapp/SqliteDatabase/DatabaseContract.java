package com.example.user.animal_breedapp.SqliteDatabase;

import android.provider.BaseColumns;

/**
 * Created by ${Mina} on 17/06/2018.
 */

public class DatabaseContract {


    public static final class Categorie implements BaseColumns {


        public static final String TABLE_NAME = "Categorie";

        public static final String U_COLUMN_NAME = "name";
        public static final String U_COLUMN_image = "id";
    }
}
