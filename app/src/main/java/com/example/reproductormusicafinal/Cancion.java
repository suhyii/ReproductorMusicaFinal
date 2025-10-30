package com.example.reproductormusicafinal;

public class Cancion {
    private String titulo;
    private int portada;
    private int fondo;
    private int audio;
    private String artista;

    public Cancion(String titulo, int portada, int fondo, int audio, String artista) {
        this.titulo = titulo;
        this.portada = portada;
        this.fondo = fondo;
        this.audio = audio;
        this.artista = artista;
    }

    public String getTitulo() { return titulo; }
    public int getPortada() { return portada; }
    public int getFondo() { return fondo; }
    public int getAudio() { return audio; }
    public String getArtista() { return artista; }
}


