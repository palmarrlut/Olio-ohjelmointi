/*
 * Viikko 9 tehtävä 1-5
 * Naytos.java / (MainActivity.java, Leffat.java, Infot.java, Naytos.java)
 * ----------------------
 * Author: Marcus Palmu
 * Description:
 * Development environment: Android Studio
 * Version history: 1
 * License:
 *
 */

package com.example.week9;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Naytos extends RecyclerView.Adapter<Naytos.MovieViewHolder> {

    ArrayList<String> naytosLista;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public MovieViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.movie_row);
        }
    }

    public Naytos(ArrayList<String> naytosLista) {
        this.naytosLista = naytosLista;
    }

    @Override
    public Naytos.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.text.setText(naytosLista.get(position));
    }

    @Override
    public int getItemCount() {
        return naytosLista.size();
    }

}
