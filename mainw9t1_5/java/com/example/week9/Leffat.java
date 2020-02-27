/*
 * Viikko 9 tehtävä 1-5
 * Leffat.java / (MainActivity.java, Leffat.java, Infot.java, Naytos.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */

package com.example.week9;

import java.util.ArrayList;

public class Leffat {

    private static Leffat mv = new Leffat(); // Singleton
    ArrayList<Infot> teatteriLista = new ArrayList();
    public static Leffat getInstance(){ // Tehtävä 1: Singleton
        return mv;
    }

    private Leffat(){

    }

    public void addTheater(String area, String theID){
        Infot teatteri = new Infot(area, theID);
        teatteriLista.add(teatteri);
    }

    public String setList(int i){
        return teatteriLista.get(i).getArea();
    }

    public String searchId(String s){
        for(int i = 0; i < teatteriLista.size(); i++){
            if(s == teatteriLista.get(i).getArea()){
                return teatteriLista.get(i).getID();
            }
        }
        return "1029"; // Kaikki teatterit;
    }
}
