/*
 * Viikko 7 tehtävä 1-4
 * MainActivity.java / (MainActivity.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: java version "1.8.0_231"
 * Version history: 1
 * License:
 *
 */

package com.example.week7;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends Activity {

    /* TEHTÄVÄT 1-4 */
    TextView text, inputCopy; // Puhelimen tekstikentän tyyppi.
    EditText input; // Puhelimen syötettävän tekstikentän tyyppi.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = (TextView) findViewById(R.id.textView); // Alustus
        // Muistutus: (TextView) parsimismuoto.
        input = (EditText) findViewById(R.id.textBox);
        inputCopy = (TextView) findViewById(R.id.textBoxView);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputCopy.setText(s);
            }
        }); // TEHTÄVÄ 4 - kopioi syötekentän tekstin.
    }

    public void buttonClick(View v){
        System.out.println("Hello World!"); // TEHTÄVÄ 1 - "Hello world!" tulostus konsoliin.
        text.setText("Hello World!"); // TEHTÄVÄ 2 - "Hello world!" tekstikenttään.
        input.setText("Button inserted this text here."); // TEHTÄVÄ 3 - syöte syötettävään tekstikenttään.
    }


}
