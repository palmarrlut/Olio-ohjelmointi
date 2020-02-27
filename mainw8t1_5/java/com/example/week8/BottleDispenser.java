/*
 * Viikko 8 tehtävä 1-5
 * BottleDispenser.java / (MainActivity.java, BottleDispenser.java, Bottle.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */

package com.example.week8;

import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class BottleDispenser {

    private static BottleDispenser bd = new BottleDispenser(); // Tehtävä 1: Singleton
    private int bottles;
    private float money;
    ArrayList<Bottle> bottle_list = new ArrayList();

    public static BottleDispenser getInstance(){ // Tehtävä 1: Singleton
        return bd;
    }

    private BottleDispenser() {

        bottles = 5;
        money = 0;


        // Initializing

        // Pepsi Max
        Bottle bottle = new Bottle("Pepsi Max", (float) 0.5, (float) 1.8);
        bottle_list.add(bottle);
        // Pepsi Max
        Bottle bottle_2 = new Bottle("Pepsi Max", (float) 1.5, (float) 2.2);
        bottle_list.add(bottle_2);
        // Coca-Cola Zero
        Bottle bottle_3 = new Bottle("Coca-Cola Zero", (float) 0.5, (float) 2.0);
        bottle_list.add(bottle_3);
        // Coca-Cola Zero
        Bottle bottle_4 = new Bottle("Coca-Cola Zero", (float) 1.5, (float) 2.5);
        bottle_list.add(bottle_4);
        // Fanta Zero
        Bottle bottle_5 = new Bottle("Fanta Zero", (float) 0.5, (float) 1.95);
        bottle_list.add(bottle_5);
        System.gc();
    }

    public void addMoney(TextView v, SeekBar c, TextView a) {
        money += Float.parseFloat(a.getText().toString());
        String s = "Amount of money in machine: ";
        s = s.concat(Float.toString(money));
        v.setText(s);
        c.setProgress(0);
    }

    public void buyBottle(TextView v, TextView m, String drink, float size, Context context) {

        int myBottle = 0;
        boolean test = false;

        printList(v); // Print menu
        String s = "";

        /* SEARCHING FOR THE BOTTLE */
        for(int i = 0; i < bottle_list.size(); i++){
            if(drink.equals(bottle_list.get(i).getName()) && size == bottle_list.get(i).getSize()){
                test = true;
                myBottle = i;
            }
        }

        if(!test){
            m.setText("Out of: " + drink + " " + size);
            return;
        }

        if(myBottle > bottle_list.size()){
            m.setText("Out of bottle No.: " + myBottle);
        }
        else if(money <= 0 || money < bottle_list.get(myBottle).getPrice()) {
            m.setText("Add money first!");
        } else if(bottles == 0) {
            m.setText("No more bottles.");
        } else {
            bottles -= 1;
            s = s.concat("KACHUNK! " + bottle_list.get(myBottle).getName() + " came out of the dispenser!");
            money -= bottle_list.get(myBottle).getPrice();
            bottle_list.remove(myBottle);// The bottle that is bought is now removed from list
            m.setText(s);
            printList(v);
            saveToFile(drink, size, context, myBottle);

        }
    }

    public void returnMoney(TextView v) {
        String s = "";
        if(money > 0) {
            s = s.concat("Money came out amount of: ");
            s = s.concat(Float.toString(money));
            s = s.concat("\n");
        }
        money = 0;
        s = s.concat("Amount of money in machine: ");
        s = s.concat(Float.toString(money));
        v.setText(s);
    }

    public void printList(TextView v){
        String list_of_bottles = "";
        for(int i = 0; i < bottles; i++){
            list_of_bottles = list_of_bottles.concat(i+1 + ". Name: " + bottle_list.get(i).getName() + "\n");
            list_of_bottles = list_of_bottles.concat("\tSize: " + bottle_list.get(i).getSize() + "\tPrice: " + bottle_list.get(i).getPrice() + "\n\n");
        }
        v.setText(list_of_bottles);
    }

    public void saveToFile(String drink, float size, Context context, Integer myBottle) {

        String fileName = "receipt.txt";

        try {

            OutputStreamWriter outputS = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            String data = "";
            data = data.concat("*** RECEIPT ***\n\nProduct: " + drink + " " + size + "\nPrice: " + bottle_list.get(myBottle).getPrice());
            System.out.println("WHAT WE ARE WRITING TO THE FILE\n" + data);
            outputS.write(data);
            outputS.close();
        } catch (IOException e) {
            Log.e("IOException", "Error in input!");
        }
    }
}

