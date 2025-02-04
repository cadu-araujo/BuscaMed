package br.edu.ifam.buscamed.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDbuscaMed extends SQLiteOpenHelper {
    public static final String API_URL = "http://192.168.100.10:8080/";
    private static final String DATABASE_NAME = "buscamed.db";
    private static final int DATABASE_VERSION = 2;

    public BDbuscaMed(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsuarioTable = "CREATE TABLE usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL, " +
                "tipo TEXT NOT NULL)";
        db.execSQL(createUsuarioTable);

        String createFarmaciaTable = "CREATE TABLE farmacia (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cnpj TEXT NOT NULL, " +
                "endereco TEXT NOT NULL)";
        db.execSQL(createFarmaciaTable);

        String createRemedioTable = "CREATE TABLE remedio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "marca TEXT, " +
                "descricao TEXT, " +
                "quantidade INTEGER, " +
                "valor REAL, " +
                "farmacia TEXT NOT NULL)";
        db.execSQL(createRemedioTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        String createUsuarioTable = "CREATE TABLE usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL UNIQUE, " +
                "senha TEXT NOT NULL, " +
                "tipo TEXT NOT NULL)";
        db.execSQL(createUsuarioTable);

        db.execSQL("DROP TABLE IF EXISTS farmacia");
        String createFarmaciaTable = "CREATE TABLE farmacia (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "cnpj TEXT NOT NULL, " +
                "endereco TEXT NOT NULL)";
        db.execSQL(createFarmaciaTable);

        db.execSQL("DROP TABLE IF EXISTS remedio");
        String createRemedioTable = "CREATE TABLE remedio (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nome TEXT NOT NULL, " +
                "marca TEXT, " +
                "descricao TEXT, " +
                "quantidade INTEGER, " +
                "valor REAL, " +
                "farmacia TEXT NOT NULL)";
        db.execSQL(createRemedioTable);
    }
}
