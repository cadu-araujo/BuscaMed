package br.edu.ifam.buscamed.repository;

import static java.lang.Boolean.TRUE;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.model.Remedio;

public class FarmaciaDAO {

    private SQLiteDatabase db;

    public FarmaciaDAO(Context context) {
        BDbuscaMed dbHelper = new BDbuscaMed(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(Farmacia farmacia) {
         ContentValues values = new ContentValues();
         values.put("nome", farmacia.getNome());
         values.put("cnpj", farmacia.getCnpj());
         values.put("endereco", farmacia.getEndereco());
         db.insert("farmacia", null, values);
    }

    public void update(Long id, Farmacia farmacia) {
        ContentValues values = new ContentValues();
        values.put("nome", farmacia.getNome());
        values.put("cnpj", farmacia.getCnpj());
        values.put("endereco", farmacia.getEndereco());
        db.update("farmacia", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public void delete(Long id) {
        db.delete("farmacia", "id = ?", new String[]{String.valueOf(id)});
    }

    @SuppressLint("Range")
    public List<Farmacia> getAll() {
        List<Farmacia> farmacias = new ArrayList<>();
        Cursor cursor = db.query("farmacia", null, null, null, null, null, "nome");
        while (cursor.moveToNext()) {
            Farmacia farmacia = new Farmacia();
            farmacia.setId(cursor.getLong(cursor.getColumnIndex("id")));
            farmacia.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            farmacia.setCnpj(cursor.getString(cursor.getColumnIndex("cnpj")));
            farmacia.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            farmacias.add(farmacia);
        }
        cursor.close();
        return farmacias;
    }

    @SuppressLint("Range")
    public Farmacia getById(Long id) {
        Cursor cursor = db.query("farmacia", null, "id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            Farmacia farmacia = new Farmacia();
            farmacia.setId(cursor.getLong(cursor.getColumnIndex("id")));
            farmacia.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            farmacia.setCnpj(cursor.getString(cursor.getColumnIndex("cnpj")));
            farmacia.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            cursor.close();
            return farmacia;
        }
        cursor.close();
        return null;
    }

    public Boolean farmaciaExiste(String nome){
        String consulta = "SELECT nome FROM farmacia where Lower(nome) = ?";
        String[] selectionArgs = {nome.toLowerCase(Locale.ROOT)};
        Cursor cursor = db.rawQuery(consulta, selectionArgs);

        if(cursor.moveToFirst()) return true;

        return false;
    }

    @SuppressLint("Range")
    public List<Farmacia> buscaNome(String nome){
        List<Farmacia> farmacias = new ArrayList<>();
        String consulta = "SELECT * FROM farmacia where Lower(nome) = ?";
        String[] selectionArgs = {nome.toLowerCase(Locale.ROOT)};
        Cursor cursor = db.rawQuery(consulta, selectionArgs);
        while (cursor.moveToNext()) {
            Farmacia farmacia = new Farmacia();
            farmacia.setId(cursor.getLong(cursor.getColumnIndex("id")));
            farmacia.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            farmacia.setCnpj(cursor.getString(cursor.getColumnIndex("cnpj")));
            farmacia.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            farmacias.add(farmacia);
        }
        cursor.close();
        return farmacias;
    }

}
