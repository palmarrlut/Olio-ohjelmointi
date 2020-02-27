/*
 * Viikko 9 tehtävä 1-5
 * MainActivity.java / (MainActivity.java, Leffat.java, Infot.java, Naytos.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */
package com.example.week9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {

    Spinner area;
    EditText date, timeb, timea, movie;
    RecyclerView screen;
    RecyclerView.Adapter adapteri;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<String> spinnerList = new ArrayList<>();
    ArrayList<String> naytosLista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        screen = (RecyclerView) findViewById(R.id.recyclerView);
        screen.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        screen.setLayoutManager(layoutManager);
        area = (Spinner) findViewById(R.id.spinner);
        date = (EditText) findViewById(R.id.editText);
        timeb = (EditText) findViewById(R.id.editText2);
        timea = (EditText) findViewById(R.id.editText3);
        movie = (EditText) findViewById(R.id.editText4);

        adapteri = new Naytos(naytosLista);
        screen.setAdapter(adapteri);

        /* XML LUKU VAIHE */
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/TheatreAreas/"; // Finnkinon xml sivu.
            Document xmlDoc = builder.parse(urlString);
            System.out.println(builder.parse(urlString));
            xmlDoc.getDocumentElement().normalize(); // Normalistointi

            // Tallennetaan muuttujat listaan, myöhemmäksi luettavaksi applikaatiossa.
            NodeList nList = xmlDoc.getDocumentElement().getElementsByTagName("TheatreArea");

            for(int i = 0; i < nList.getLength(); i++){
                Node node = nList.item(i); // Syötetään i. solmu.

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node; // Tyyppi muunnos
                    // Talletetaan listaan luokassa Leffat.
                    Leffat mv = Leffat.getInstance(); // Alustus;
                    try{
                        mv.addTheater(element.getElementsByTagName("Name").item(0).getTextContent(), element.getElementsByTagName("ID").item(0).getTextContent());
                    } catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }

            for(int i = 0; i < nList.getLength(); i++){
                Leffat mv = Leffat.getInstance(); // Alustus;
                String s = mv.setList(i);
                spinnerList.add(s);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,spinnerList);
            area.setAdapter(adapter);
            area.setSelection(0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void searchMovies(View v){

        naytosLista.removeAll(naytosLista);

                /*Theatre Area List:
        id: 1014 (Pääkaupunkiseutu)
        id: 1015 (Jyväskylä: FANTASIA)
        id: 1016 (Kuopio: SCALA)
        id: 1017 (Lahti: KUVAPALATSI)
        id: 1041 (Lappeenranta: STRAND)
        id: 1018 (Oulu: PLAZA)
        id: 1019 (Pori: PROMENADI)
        id: 1021 (Tampere)
        id: 1022 (Turku: KINOPALATSI)*/
        ArrayList<String> kaikkiTeatterit = new ArrayList<>();
        kaikkiTeatterit.add("1014");
        kaikkiTeatterit.add("1015");
        kaikkiTeatterit.add("1016");
        kaikkiTeatterit.add("1017");
        kaikkiTeatterit.add("1041");
        kaikkiTeatterit.add("1018");
        kaikkiTeatterit.add("1019");
        kaikkiTeatterit.add("1021");
        kaikkiTeatterit.add("1022");


        String s = area.getSelectedItem().toString();
        String n = movie.getText().toString();
        Leffat mv = Leffat.getInstance(); // ETSITÄÄN ID TEATTERILLE.
        s = mv.searchId(s);
        String d = date.getText().toString();
        String tS = timeb.getText().toString();
        String tE = timea.getText().toString();
        String x = "&dt=";

        /* Tarkistetaan onko syötetty päivämäärä vai valitaanko kaikki päivämäärät. */
        Date dateTest = null;
        try {
            dateTest = new SimpleDateFormat("dd.MM.yyyy").parse(d); // Pakollinen formaatti.
            x = x.concat(d);
        } catch (Exception ex) {
            x = "&nrOfDays=31";
        }

        /* Tarkistetaan onko syötetty ajat vai valitaanko kaikki ajat. */
        Date DtS = null;
        Date DtE = null;
        DateFormat hourmin = new SimpleDateFormat("hh:mm");
        try {
            DtS = hourmin.parse(tS); // Pakollinen formaatti.
            DtE = hourmin.parse(tE); // Pakollinen formaatti.
        } catch (Exception ex) {
            tS = "00:00";
            tE = "23:59";
        }

        if(!s.equals("1029")) {
            try {
                DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                String urlString = "http://www.finnkino.fi/xml/Schedule/?area=" + s + x; // Finnkinon xml sivu aikatauluille.
                Document xmlDoc = builder.parse(urlString);
                xmlDoc.getDocumentElement().normalize(); // Normalistointi

                // Tallennetaan muuttujat listaan, myöhemmäksi luettavaksi applikaatiossa.
                NodeList nList = xmlDoc.getDocumentElement().getElementsByTagName("Show");

                for (int i = 0; i < nList.getLength(); i++) {
                    //if(naytosLista.size() < 20) {
                        Node node = nList.item(i); // Syötetään i. solmu.

                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) node; // Tyyppi muunnos
                            // Talletetaan listaan luokassa Leffat.
                            try {
                                String elokuva = element.getElementsByTagName("Title").item(0).getTextContent();
                                String aikaA = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                                String paikka = element.getElementsByTagName("TheatreAndAuditorium").item(0).getTextContent();
                                String naytosaika = (aikaA.substring(8, 10) + "." + aikaA.substring(5, 7) + "." + aikaA.substring(0, 4) + " " + aikaA.substring(11, 16));
                                aikaA = aikaA.substring(11, 16); // Formaatti on tyyliä: 2020-03-03T00:00:00

                                Date d1 = null;
                                /* Aika vertailu */
                                try {
                                    d1 = hourmin.parse(aikaA);
                                    DtS = hourmin.parse(tS);
                                    DtE = hourmin.parse(tE);
                                } catch (Exception ex) {
                                    return;
                                }
                                try {
                                    if (DtS.compareTo(d1) < 0 && DtE.compareTo(d1) > 0) {
                                        if (n.equals("")) {
                                            naytosLista.add("Elokuva: " + elokuva + "\n\tTeatteri: " + paikka + "\n\tNäytösaika: " + naytosaika);
                                        } else {
                                            if (n.equals(elokuva)) {
                                                naytosLista.add("Elokuva: " + elokuva + "\n\tTeatteri: " + paikka + "\n\tNäytösaika: " + naytosaika);
                                            }
                                        }
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }

                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            }
                        }
                   // }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        } else {
            for(int j = 0; j < kaikkiTeatterit.size(); j++){
                try {
                    s = kaikkiTeatterit.get(j);
                    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    String urlString = "http://www.finnkino.fi/xml/Schedule/?area=" + s + x; // Finnkinon xml sivu aikatauluille.
                    Document xmlDoc = builder.parse(urlString);
                    System.out.println(" 3 ############");
                    xmlDoc.getDocumentElement().normalize(); // Normalistointi
                    System.out.println(" 4 ############");
                    // Tallennetaan muuttujat listaan, myöhemmäksi luettavaksi applikaatiossa.
                    NodeList nList = xmlDoc.getDocumentElement().getElementsByTagName("Show");


                    for (int i = 0; i < nList.getLength(); i++) {
                        Node node = nList.item(i); // Syötetään i. solmu.
                        //if(naytosLista.size() < 20) {
                            if (node.getNodeType() == Node.ELEMENT_NODE) {
                                Element element = (Element) node; // Tyyppi muunnos
                                // Talletetaan listaan luokassa Leffat.
                                try {
                                    String elokuva = element.getElementsByTagName("Title").item(0).getTextContent();
                                    String aikaA = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                                    String paikka = element.getElementsByTagName("TheatreAndAuditorium").item(0).getTextContent();
                                    String naytosaika = (aikaA.substring(8, 10) + "." + aikaA.substring(5, 7) + "." + aikaA.substring(0, 4) + " " + aikaA.substring(11, 16));
                                    aikaA = aikaA.substring(11, 16); // Formaatti on tyyliä: 2020-03-03T00:00:00

                                    Date d1 = null;
                                    /* Aika vertailu */
                                    try {
                                        d1 = hourmin.parse(aikaA);
                                        DtS = hourmin.parse(tS);
                                        DtE = hourmin.parse(tE);
                                    } catch (Exception ex) {
                                        return;
                                    }
                                    try {
                                        if (DtS.compareTo(d1) < 0 && DtE.compareTo(d1) > 0) {
                                            if (n.equals("")) {
                                                naytosLista.add("Elokuva: " + elokuva + "\n\tTeatteri: " + paikka + "\n\tNäytösaika: " + naytosaika);
                                            } else {
                                                if (n.equals(elokuva)) {
                                                    naytosLista.add("Elokuva: " + elokuva + "\n\tTeatteri: " + paikka + "\n\tNäytösaika: " + naytosaika);
                                                }
                                            }
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }

                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }
                        //}
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }
        adapteri = new Naytos(naytosLista);
        screen.setAdapter(adapteri);
    }
}