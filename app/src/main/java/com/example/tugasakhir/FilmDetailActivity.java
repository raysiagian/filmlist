package com.example.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FilmDetailActivity extends AppCompatActivity {

    private EditText judulEditText, descriptionEditText;
    private Button saveButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_detail);
        initWidgets();

        databaseHelper = new DatabaseHelper(this);

        getSupportActionBar().setTitle("Tambah Film");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = judulEditText.getText().toString();
                String deskripsi = descriptionEditText.getText().toString();

                // Menyimpan data ke database
                boolean isSuccess = databaseHelper.insertFilm(judul, deskripsi);
                if (isSuccess) {
                    Log.d("FilmDetailActivity", "Data film baru berhasil disimpan");
                    // Tambahkan tindakan lain yang perlu dilakukan setelah menyimpan data
                } else {
                    Log.e("FilmDetailActivity", "Gagal menyimpan data film baru");
                    // Tambahkan tindakan lain jika penyimpanan gagal
                }

                finish();
            }
        });
    }

    private void initWidgets() {
        judulEditText = findViewById(R.id.judulEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        saveButton = findViewById(R.id.saveButton);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
