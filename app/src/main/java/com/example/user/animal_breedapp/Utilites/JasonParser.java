package com.example.user.animal_breedapp.Utilites;

import android.content.Context;

import com.example.user.animal_breedapp.Models.Animal;
import com.example.user.animal_breedapp.Models.CategoreItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by ${Mina} on 17/06/2018.
 */

public class JasonParser {
    Context mc;

    public static ArrayList<CategoreItem> parsebreeds(String response) {
        ArrayList<CategoreItem> all_breed = new ArrayList<CategoreItem>();
        CategoreItem item;
        try {
            JSONObject data_object;

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                JSONObject jsonObject1 = jsonObject.getJSONObject("message");////todo massage
                jsonObject1.names();
                all_breed.clear();

                Iterator iterator = jsonObject1.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();

                    JSONArray jArray = jsonObject1.getJSONArray(key);
                    if (jArray.length() >= 1) {
                        /*for (int j = 0; j < jArray.length(); j++) {
                          //   data_object = jArray.getJSONObject(j);

                            item = new CategoreItem();
                            //item.setName(data_object.toString());
                             item.setName(key);
                            all_breed.add(item);

                        }*/
                        item = new CategoreItem();
                        item.setName(key);
                        all_breed.add(item);


                    }

                }


            }
        }   // TRY

        catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return all_breed;
        }   //  FINALLY
    }


    public static String parse_imagees(String response) {


        CategoreItem item1;
        String s = null;
        try {
            JSONObject data_object;

            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                // JSONObject object=jsonObject.getJSONObject("message");////todo massage

                item1 = new CategoreItem();
                item1.setCategorie_image(jsonObject.getString("message"));
                s = item1.getCategorie_image();

            }
        }   // TRY

        catch (JSONException e) {
            e.printStackTrace();
        } finally {
            return s;
        }   //  FINALLY
    }

    public static ArrayList<Animal> parseAnimals(String r) {

        ArrayList<Animal> all = new ArrayList<Animal>();

        Animal item;
        String s = null;
        try {
            JSONObject data_object;

            JSONObject js = new JSONObject(r);
            JSONArray ja = js.getJSONArray("message");////todo massage

            for (int j = 0; j < ja.length(); j++) {
                String da = ja.getString(j);

                item = new Animal();
                item.setName_an(da);
                all.add(item);
            }

            // }
        }   // TRY

        catch (JSONException e) {
            e.printStackTrace();
        } finally {
        }

        return all;
    }

}
