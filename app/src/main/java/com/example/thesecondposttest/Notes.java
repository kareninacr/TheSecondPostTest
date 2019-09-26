package com.example.thesecondposttest;

public class Notes {
    String id, judul, deskripsi;

    public Notes(String id, String judul, String desk) {

    }

    public Notes(String judul, String deskripsi) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    public String getId() {
        return id;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
