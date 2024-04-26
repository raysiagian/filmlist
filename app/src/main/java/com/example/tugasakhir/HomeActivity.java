package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private ListView filmListView;
    private FilmAdapter filmAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initWidgets();
        setFilmAdapter();

        databaseHelper = new DatabaseHelper(this);

        FloatingActionButton fab = findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, FilmDetailActivity.class);
                startActivity(intent);
            }
        });

        // Memanggil method getFilmAdapter saat Activity pertama kali dibuat
        getFilmAdapter();
    }

    private void initWidgets(){
        filmListView = findViewById(R.id.filmListView);
    }

    private void setFilmAdapter() {
        filmAdapter = new FilmAdapter(getApplicationContext(), Film.filmArrayList);
        filmListView.setAdapter(filmAdapter);
    }

    private void updateFilmAdapter() {
        filmAdapter.notifyDataSetChanged();
    }

    // Method untuk mendapatkan data film dari database
    private void getFilmAdapter() {
        // Mengambil data film dari database
        ArrayList<Film> filmList = databaseHelper.getAllFilms();

        // Memperbarui ArrayList filmArrayList di kelas Film
        Film.filmArrayList.clear();
        Film.filmArrayList.addAll(filmList);

        // Memperbarui adapter
        updateFilmAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Memanggil kembali getFilmAdapter saat Activity dilanjutkan (misalnya setelah menambahkan film baru)
        getFilmAdapter();
    }
}
