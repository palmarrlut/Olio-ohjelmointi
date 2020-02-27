/*
 * Viikko 8 tehtävä 1-5
 * MainActivity.java / (MainActivity.java, BottleDispenser.java, Bottle.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */

package com.example.week8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Context context = null;
    TextView screen, moneyScreen, amount;
    SeekBar credit;
    Spinner drinks, sizes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        BottleDispenser bd = BottleDispenser.getInstance(); // Alustus;

        screen = (TextView) findViewById(R.id.Screen);
        moneyScreen = (TextView) findViewById(R.id.screen2);
        credit = (SeekBar) findViewById(R.id.seekBar);
        amount = (TextView) findViewById(R.id.creditView);
        drinks = (Spinner) findViewById(R.id.drinkList);
        sizes = (Spinner) findViewById(R.id.sizeList);

        bd.printList(screen); // Tulostaa pullot näytölle.
        moneyScreen.setText("Amount of money in machine: 0.00");

        credit.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    float money = 0.00f;
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        money = progress / 100;
                        amount.setText(Float.toString(money));

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        amount.setText(Float.toString(money)); // SeekBarin arvo tallentuu amountiin.
                    }
                }
        );

        System.out.println(" ******* THE LOCATION OF THE FILE ******: " + context.getFilesDir());
        // Oikea sijainti on: /data/data/com.example.week8/files
    }

    public void insertMoney(View v) {
        BottleDispenser bd_i = BottleDispenser.getInstance();
        bd_i.addMoney(moneyScreen, credit, amount);
    }

    public void spitOutMoney(View v) {
        BottleDispenser bd_s = BottleDispenser.getInstance();
        bd_s.returnMoney(moneyScreen);
    }

    public void myShopping(View v) {
        BottleDispenser bd_1 = BottleDispenser.getInstance();
        String myBottle = drinks.getSelectedItem().toString();
        float mySize = Float.parseFloat(sizes.getSelectedItem().toString());
        bd_1.buyBottle(screen, moneyScreen, myBottle, mySize, context);
    }

}
