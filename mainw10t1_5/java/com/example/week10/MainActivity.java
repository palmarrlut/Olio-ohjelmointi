/*
 * Viikko 10 tehtävä 1-5
 * MainActivity.java / (MainActivity.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: java version "1.8.0_231"
 * Version history: 1
 * License:
 *
 */

package com.example.week10;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {

    WebView web;
    EditText urlBar;
    ImageButton search, refresh, left, right;
    Button goJS, goOG;

    String currentLink = ""; // Osoite tällä hetkellä.
    ArrayList<String> addressList = new ArrayList<>();

    ListIterator <String> itR = addressList.listIterator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        web = (WebView) findViewById(R.id.WebView);
        urlBar = (EditText) findViewById(R.id.urlBar);
        search = (ImageButton) findViewById(R.id.searchButton);
        refresh = (ImageButton) findViewById(R.id.refreshButton);
        left = (ImageButton) findViewById(R.id.returnButton);
        right = (ImageButton) findViewById(R.id.forwardButton);
        goJS = (Button) findViewById(R.id.JSButton);
        goOG = (Button) findViewById(R.id.ReJSButton);

        web.setWebViewClient(new WebViewClient(){ // Varmistaa, että "googlettamisen" osoitteetkin tallentuvat historiaan.
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                urlBar.setText(url);
                if(!(currentLink.equals(url))) {
                    currentLink = url;
                    manageListSize(currentLink);
                }
            }
        }); // Tekee ohjelman selaimesta operoivan selaimen.
        web.getSettings().setJavaScriptEnabled(true); // Sallii selaimen suorittaa JavaScript koodia.

        currentLink = "http://www.google.fi"; // Osoite ohjelman käynnistyessä.
        urlBar.setText(currentLink); // Autofill tekstille osoitekenttään.
        web.loadUrl(currentLink);

        urlBar.setOnClickListener(new View.OnClickListener() { // Osoitekentän klikkauksella toteutuu...
            @Override public void onClick(View v) {
                    urlBar.setText(""); // Nopeuttaakseen testaamista ja tehtävän toiminnan esittämistä.
                    urlBar.setSelection(urlBar.getText().length());
            }
        });  // Mahdollisesti turha toiminto.

        urlBar.setOnEditorActionListener(new TextView.OnEditorActionListener() { // Osoitekentässä Enteriä painamalla toteutuu...
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( (actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN ))){
                    searchPage(v); // Hakutoiminto.
                    return true;
                }
                else{
                    return false;
                }
            }
        });

    }

    public void searchPage(View v){

        String check = "http://";
        String compare = urlBar.getText().toString().substring(0, 7);

        if(!(check.equals(compare))){
            check = check.concat(urlBar.getText().toString());
        }

        if(check.equals("http://index.html")){ // Jos syöte on "index.html" suoritetaan resursseissa oleva tiedosto.
            currentLink = "file:///android_asset/index.html";
            urlBar.setText(currentLink);
            web.loadUrl(currentLink);
        } else {
            currentLink = check;
            web.loadUrl(currentLink);
        }
    }

    public void refreshPage(View v){

            urlBar.setText(currentLink); // Mikäli kentässä lukee muuta kuin päivitetty osoite, laitetaan kenttään nykyisen osoitteen tiedot.
            web.loadUrl(currentLink);
    }

    public void goBack(View v){

        if(itR.previousIndex() != -1){
            currentLink = itR.previous();
            System.out.println(currentLink);
            urlBar.setText(currentLink);
            web.loadUrl(currentLink);
        }
    }

    public void goForward(View v){

        if(itR.nextIndex() != addressList.size()){
            currentLink = itR.next();
            urlBar.setText(currentLink);
            web.loadUrl(currentLink);
        }
    }

    public void goJavaScript(View v){
        web.evaluateJavascript("javascript:shoutOut()", null);
    }

    public void goOriginal(View v){
        web.evaluateJavascript("javascript:initialize()", null);
    }

    private void manageListSize(String address){

        if(itR.hasNext()) {
            Integer kerroin = 0;

            while(itR.hasNext()){
                itR.next();
                kerroin++;
            }
            for(int i = 0; i < kerroin-1; i++){
                itR.previous();
                itR.remove();
            }// Poistaa listasta kaiken nykyisen sijainnin jälkeen.
        }

        if(addressList.size() <= 10){ // Niin kauan kuin listaan mahtuu niin lisätään suoraan listalle osoitteita.
            itR.add(address); // Listaan mahtuu siis vielä 11. alkio eli 10 askelta.
        } else {
            while(itR.hasPrevious()){
                itR.previous();
            }
            itR.remove();
            while(itR.hasNext()){
                itR.next();
            }
            itR.add(address);
        }
    }
}
