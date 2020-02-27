/*
 * Viikko 8 tehtävä 1-5
 * Bottle.java / (MainActivity.java, BottleDispenser.java, Bottle.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */

package com.example.week8;

public class Bottle {

    // Default values
    private String name = "Pepsi Max";
    private float size = (float) 0.5;
    private float price = (float) 1.80;


    public Bottle(String x, float y, float z){
        name = x;
        size = y;
        price = z;
    }

    public String getName(){
        return name;
    }

    public float getSize(){
        return size;
    }

    public float getPrice(){
        return price;
    }
}
