package com.example.reproductormusicafinal;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ImageView imgPortada, btnPlay, btnNext, btnPrev, btnListas, btnFavorito, btnCerrar;
    private TextView txtTitulo, txtInicio, txtFin;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private ArrayList<Cancion> listaCanciones;
    private int indiceActual = 0;
    private HashMap<Integer, Boolean> favoritos = new HashMap<>(); // guarda favoritos por índice
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgPortada = findViewById(R.id.imgPortada);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtInicio = findViewById(R.id.txtInicio);
        txtFin = findViewById(R.id.txtFin);
        btnPlay = findViewById(R.id.btnPlayPause);
        btnNext = findViewById(R.id.btnSiguiente);
        btnPrev = findViewById(R.id.btnAnterior);
        btnListas = findViewById(R.id.btnPlaylist);
        btnFavorito = findViewById(R.id.btnFavorito);
        btnCerrar = findViewById(R.id.btnCerrar);
        seekBar = findViewById(R.id.seekBar);

        listaCanciones = new ArrayList<>();
        listaCanciones.add(new Cancion("Ivonny Bonita", R.drawable.m1, R.drawable.f1, R.raw.ivonny, "Karol G"));
        listaCanciones.add(new Cancion("But Daddy I Love Him", R.drawable.m2, R.drawable.f2, R.raw.butdaddy, "Taylor Swift"));
        listaCanciones.add(new Cancion("Paranoia", R.drawable.m3, R.drawable.f3, R.raw.paranoia, "The Marias"));
        listaCanciones.add(new Cancion("Vida Normal", R.drawable.m4, R.drawable.f4, R.raw.vida, "Mon Laferte"));
        listaCanciones.add(new Cancion("Linger", R.drawable.m5, R.drawable.f5, R.raw.linger, "The Cranberries"));

        // Recibe los datos desde la lista
        Intent intent = getIntent();
        String titulo = intent.getStringExtra("titulo");
        if (titulo != null) {
            for (int i = 0; i < listaCanciones.size(); i++) {
                if (listaCanciones.get(i).getTitulo().equals(titulo)) {
                    indiceActual = i;
                    break;
                }
            }
        }

        reproducirCancion(indiceActual);

        // Botón play/pause
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setImageResource(R.drawable.play);
            } else {
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                actualizarTiempo(); // reinicia actualización de tiempo
            }
        });

        // Para cambiar canciones
        btnNext.setOnClickListener(v -> cambiarCancion(1));
        btnPrev.setOnClickListener(v -> cambiarCancion(-1));

        // Para la playlist
        btnListas.setOnClickListener(v -> {
            Intent volver = new Intent(MainActivity.this, ListasCanciones.class);
            startActivity(volver);
            finish();
        });

        // Botón de la estrellita (border es la llena y star es la vacía)
        btnFavorito.setOnClickListener(v -> {
            boolean marcado = favoritos.getOrDefault(indiceActual, false);
            if (!marcado) {
                favoritos.put(indiceActual, true);
                btnFavorito.setImageResource(R.drawable.star_border);
                Toast.makeText(MainActivity.this, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            } else {
                favoritos.put(indiceActual, false);
                btnFavorito.setImageResource(R.drawable.star);
                Toast.makeText(MainActivity.this, "Quitado de favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        // Para que se reinice la canción
        btnCerrar.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(0);
                seekBar.setProgress(0);
            }
        });

        // Barrita del  tiempo
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) mediaPlayer.seekTo(progress);
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void reproducirCancion(int indice) {
        Cancion c = listaCanciones.get(indice);
        txtTitulo.setText(c.getTitulo());
        txtFin.setText(formatearTiempo(mediaPlayer != null ? mediaPlayer.getDuration() : 0)); // temporal
        txtInicio.setText("0:00");
        imgPortada.setImageResource(c.getPortada());
        findViewById(R.id.layoutFondo).setBackgroundResource(c.getFondo());

        // que cambie el nombre del artista
        TextView txtArtista = findViewById(R.id.txtArtista);
        txtArtista.setText(c.getArtista());

        // botón de favoritos (YA FUNCIONAAAAAAAA)
        boolean marcado = favoritos.getOrDefault(indice, false);
        btnFavorito.setImageResource(marcado ? R.drawable.star_border : R.drawable.star);

        if (mediaPlayer != null) mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(this, c.getAudio());
        mediaPlayer.start();
        btnPlay.setImageResource(R.drawable.pause);

        seekBar.setMax(mediaPlayer.getDuration());
        actualizarTiempo();
    }

    private void cambiarCancion(int cambio) {
        indiceActual += cambio;
        if (indiceActual < 0) indiceActual = listaCanciones.size() - 1;
        if (indiceActual >= listaCanciones.size()) indiceActual = 0;
        reproducirCancion(indiceActual);
    }

    private void actualizarTiempo() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    txtInicio.setText(formatearTiempo(mediaPlayer.getCurrentPosition()));
                    txtFin.setText(formatearTiempo(mediaPlayer.getDuration()));
                    handler.postDelayed(this, 500);
                }
            }
        }, 500);
    }

    private String formatearTiempo(int milisegundos) {
        int min = milisegundos / 1000 / 60;
        int seg = (milisegundos / 1000) % 60;
        return String.format("%d:%02d", min, seg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) mediaPlayer.release();
        handler.removeCallbacksAndMessages(null);
    }
}

