package br.edu.ifam.buscamed.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDbuscaMed extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "buscamed.db";
    private static final int DATABASE_VERSION = 1;

    public BDbuscaMed(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

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
    }
}
