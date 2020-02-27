/*
 * Viikko 7 tehtävä 5
 * MainActivity.java / (MainActivity.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: java version "1.8.0_231"
 * Version history: 1
 * License:
 *
 */

package com.example.week7_t5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    Context context = null;
    EditText input, name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        input = (EditText) findViewById(R.id.inputField);
        name = (EditText) findViewById(R.id.nameField);

        System.out.println("THE LOCATION OF THE FILE " + context.getFilesDir());
    }

    public void readFile(View v){

        String data = "";
        String fileName = name.getText().toString();

        try {
            InputStream inputS = context.openFileInput(fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputS));
            String line = "";

            while((line = br.readLine()) != null){
                data = data.concat(line); // Adds line by line to data
                data = data.concat("\n"); // Adds line seperator which BufferedReader does not do.
            }
            System.out.println("WHAT WE ARE READING FROM THE FILE\n" + data);
            input.setText(data);
            inputS.close();
        } catch (IOException e){
            Log.e("IOException", "Error in input!");
        }

    }

    public void saveToFile(View v){

        String fileName = name.getText().toString();

        try {

            OutputStreamWriter outputS = new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_PRIVATE));

            String data = input.getText().toString();
            System.out.println("WHAT WE ARE WRITING TO THE FILE\n" + data);
            outputS.write(data);
            outputS.close();
        } catch (IOException e){
            Log.e("IOException", "Error in input!");
        }

    }
}
