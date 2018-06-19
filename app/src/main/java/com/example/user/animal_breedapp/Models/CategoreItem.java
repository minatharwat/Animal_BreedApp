package com.example.user.animal_breedapp.Models;

/**
 * Created by ${Mina} on 17/06/2018.
 */

public class CategoreItem {
    private String name;
    private String Categorie_image;

    public CategoreItem() {
    }

    public CategoreItem(String name, String image) {
        this.name = name;
        this.Categorie_image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategorie_image() {
        return Categorie_image;
    }

    public void setCategorie_image(String categorie_image) {
        Categorie_image = categorie_image;
    }
}
