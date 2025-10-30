package com.example.reproductormusicafinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class ListasCanciones extends AppCompatActivity {

    private ListView listView;
    private ArrayList<Cancion> listaCanciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas_canciones);

        listView = findViewById(R.id.listView);
        listaCanciones = new ArrayList<>();

        listaCanciones.add(new Cancion("Ivonny Bonita", R.drawable.m1, R.drawable.f1, R.raw.ivonny, "Karol G"));
        listaCanciones.add(new Cancion("But Daddy I Love Him", R.drawable.m2, R.drawable.f2, R.raw.butdaddy, "Taylor Swift"));
        listaCanciones.add(new Cancion("Paranoia", R.drawable.m3, R.drawable.f3, R.raw.paranoia, "The Marías"));
        listaCanciones.add(new Cancion("Vida Normal", R.drawable.m4, R.drawable.f4, R.raw.vida, "Mon Laferte"));
        listaCanciones.add(new Cancion("Linger", R.drawable.m5, R.drawable.f5, R.raw.linger, "The Cranberries"));

        AdaptadorCancion adapter = new AdaptadorCancion(this, listaCanciones);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Cancion seleccionada = listaCanciones.get(position);
            Intent intent = new Intent(ListasCanciones.this, MainActivity.class);
            intent.putExtra("titulo", seleccionada.getTitulo());
            intent.putExtra("portada", seleccionada.getPortada());
            intent.putExtra("fondo", seleccionada.getFondo());
            intent.putExtra("audio", seleccionada.getAudio());
            intent.putExtra("artista", seleccionada.getArtista());
            startActivity(intent);
        });
    }
}
