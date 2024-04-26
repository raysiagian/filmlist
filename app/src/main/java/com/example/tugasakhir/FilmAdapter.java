package com.example.tugasakhir;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FilmAdapter extends ArrayAdapter <Film>{

    public FilmAdapter(Context context, List<Film> films){
        super(context, 0, films);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Film film = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.film_cell, parent, false);

        TextView judul = convertView.findViewById(R.id.cellTitle);
        TextView deskripsi = convertView.findViewById(R.id.cellDesc);

        judul.setText(film.getJudul());
        deskripsi.setText(film.getDeskripsi());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mengambil ID film dari daftar film
                int filmId = film.getId();

                // Mengirim ID film ke halaman FilmDetailActivity
                Intent intent = new Intent(getContext(), EditDeleteFilmActivity.class);
                intent.putExtra("film_id", filmId);

                // Menambahkan flag FLAG_ACTIVITY_NEW_TASK sebelum memanggil startActivity()
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getContext().startActivity(intent);
            }
        });

        return convertView;
    }
}
