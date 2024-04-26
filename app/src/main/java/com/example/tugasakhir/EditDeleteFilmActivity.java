package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tugasakhir.databinding.ActivityEditDeleteFilmBinding;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditDeleteFilmActivity extends AppCompatActivity {

    private EditText judulFilmField, deskripsiField;
    private Button editButton, deleteButton;
    private int filmId;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete_film);

        // Inisialisasi views
        judulFilmField = findViewById(R.id.judulFilmField);
        deskripsiField = findViewById(R.id.deskripsi_field);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Inisialisasi DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        // Mendapatkan ID film dari intent
        filmId = getIntent().getIntExtra("film_id", -1);

        // Mengambil informasi film dari database berdasarkan ID
        Film film = databaseHelper.getFilmById(filmId);

        // Menampilkan informasi film di EditText
        judulFilmField.setText(film.getJudul());
        deskripsiField.setText(film.getDeskripsi());

        // Set onClickListener untuk tombol Edit Film
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judulBaru = judulFilmField.getText().toString();
                String deskripsiBaru = deskripsiField.getText().toString();

                // Memperbarui data film di database
                boolean isSuccess = databaseHelper.updateFilm(filmId, judulBaru, deskripsiBaru);
                if (isSuccess) {
                    // Data berhasil diperbarui
                    Log.d("FilmDetailActivity", "Data film baru berhasil disimpan");
                } else {
                    Log.e("FilmDetailActivity", "Gagal menyimpan data film baru");
                    // Tambahkan tindakan lain jika diperlukan
                }
                finish();
            }
        });

        // Set onClickListener untuk tombol Hapus Film
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menghapus film dari database
                boolean isSuccess = databaseHelper.deleteFilm(filmId);
                if (isSuccess) {
                    // Film berhasil dihapus
                    Log.d("FilmDetailActivity", "Data film baru berhasil dihapus");
                } else {
                    // Gagal menghapus film
                    Log.e("FilmDetailActivity", "Gagal menghapus data film baru");
                }
                finish();
            }
        });
    }
}
