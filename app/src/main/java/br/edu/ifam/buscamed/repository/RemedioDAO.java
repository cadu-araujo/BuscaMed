package br.edu.ifam.buscamed.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ifam.buscamed.model.Remedio;

public class RemedioDAO {
    private SQLiteDatabase db;

    public RemedioDAO(Context context) {
        BDbuscaMed dbHelper = new BDbuscaMed(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(Remedio remedio) {
        ContentValues values = new ContentValues();
        values.put("nome", remedio.getNome());
        values.put("marca", remedio.getMarca());
        values.put("descricao", remedio.getDescricao());
        values.put("quantidade", remedio.getQuantidade());
        values.put("valor", remedio.getValor());
        values.put("farmacia", remedio.getFarmacia());
        db.insert("remedio", null, values);
    }

    public void update(Long id, Remedio remedio) {
        ContentValues values = new ContentValues();
        values.put("nome", remedio.getNome());
        values.put("marca", remedio.getMarca());
        values.put("descricao", remedio.getDescricao());
        values.put("quantidade", remedio.getQuantidade());
        values.put("valor", remedio.getValor());
        values.put("farmacia", remedio.getFarmacia());
        db.update("remedio", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void delete(Long id) {
        db.delete("remedio", "id = ?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<Remedio> getRemedio() {
        List<Remedio> remedios = new ArrayList<>();
        Cursor cursor = db.query("remedio", null, null, null, null, null, "nome");
        while (cursor.moveToNext()) {
            Remedio remedio = new Remedio();
            remedio.setId(cursor.getLong(cursor.getColumnIndex("id")));
            remedio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            remedio.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
            remedio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            remedio.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
            remedio.setValor(cursor.getFloat(cursor.getColumnIndex("valor")));
            remedio.setFarmacia(cursor.getString(cursor.getColumnIndex("farmacia")));
            remedios.add(remedio);
        }
        cursor.close();
        return remedios;
    }

    @SuppressLint("Range")
    public Remedio getRemedioByID(Long id) {
        Cursor cursor = db.query("remedio", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            Remedio remedio = new Remedio();
            remedio.setId(cursor.getLong(cursor.getColumnIndex("id")));
            remedio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            remedio.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
            remedio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            remedio.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
            remedio.setValor(cursor.getFloat(cursor.getColumnIndex("valor")));
            remedio.setFarmacia(cursor.getString(cursor.getColumnIndex("farmacia")));
            cursor.close();
            return remedio;
        }
        cursor.close();
        return null;
    }

    @SuppressLint("Range")
    public List<Remedio> buscaNome(String nome){
        List<Remedio> remedios = new ArrayList<>();
        String consulta = "SELECT * FROM remedio where Lower(nome) = ?";
        String[] selectionArgs = {nome.toLowerCase(Locale.ROOT)};
        Cursor cursor = db.rawQuery(consulta, selectionArgs);
        while (cursor.moveToNext()) {
            Remedio remedio = new Remedio();
            remedio.setId(cursor.getLong(cursor.getColumnIndex("id")));
            remedio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            remedio.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
            remedio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            remedio.setQuantidade(cursor.getInt(cursor.getColumnIndex("quantidade")));
            remedio.setValor(cursor.getFloat(cursor.getColumnIndex("valor")));
            remedio.setFarmacia(cursor.getString(cursor.getColumnIndex("farmacia")));
            remedios.add(remedio);
        }
        cursor.close();
        return remedios;
    }
}
