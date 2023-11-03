package com.example.limaapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class doaShow extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bader_show);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button button1 = findViewById(R.id.button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddoadoaData();
            }
        });
    }


    private void loaddoadoaData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://doa-doa-api-ahmadramadhan.fly.dev")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        doaService service = retrofit.create(doaService.class);
        Call<List<doa>> call = service.getdoadoa();
        call.enqueue(new Callback<List<doa>>() {
            @Override
            public void onResponse(Call<List<doa>> call, Response<List<doa>>response) {
                if (response.isSuccessful()) {
                    List<doa> doaList = response.body();
                    adapter = new doaAdapter(doaList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(doaShow.this, "Gagal mengambil data doa-doa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<doa>> call, Throwable t) {
                Toast.makeText(doaShow.this, "Kesalahan jaringan", Toast.LENGTH_SHORT).show();
            }
        });
    }

}