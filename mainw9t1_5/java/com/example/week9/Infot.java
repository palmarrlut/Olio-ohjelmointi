/*
 * Viikko 9 tehtävä 1-5
 * Infot.java / (MainActivity.java, Leffat.java, Infot.java, Naytos.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */

package com.example.week9;

public class Infot {

    private String alue = "";
    private String id = "";

    public Infot(String area, String theID){
        alue = area;
        id = theID;
    }

    public String getArea(){
        return alue;
    }

    public String getID(){
        return id;
    }
}
