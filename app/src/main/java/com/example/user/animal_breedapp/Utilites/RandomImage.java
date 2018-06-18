package com.example.user.animal_breedapp.Utilites;

import android.content.Context;

import com.example.user.animal_breedapp.NetworkThreads.Volley;
import com.example.user.animal_breedapp.R;

/**
 * Created by ${Mina} on 17/06/2018.
 */

public class RandomImage implements Volley.API {
    protected Context mcontext;

    public RandomImage(Context mcontext)
    {
        this.mcontext=mcontext;
    }


    public void getImageRequest(String breed_name) {
        Volley volley = new Volley(mcontext, this);
        volley.getapi(mcontext.getString(R.string.base)+breed_name+mcontext.getString(R.string.catgore_image));
        Logs.log(" "+mcontext.getString(R.string.base)+breed_name+mcontext.getString(R.string.catgore_image));
    }

    @Override
    public void ConnectionDone(String msg) {

     //   Logs.log(msg);

      //  Breed_List.get(Counter).setCategorie_image(JasonParser.parse_imagees(msg));

      //  Counter++;
    }
}
