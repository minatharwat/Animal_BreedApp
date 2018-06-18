package com.example.user.animal_breedapp.Utilites;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ${Mina} on 17/06/2018.
 */

public class Logs {


    public static void log(String s) {

        Log.e("tag", s);
    }

    public static void toast(Context context, String s) {

        Toast.makeText(context, s, Toast.LENGTH_LONG).show();

    }
}
