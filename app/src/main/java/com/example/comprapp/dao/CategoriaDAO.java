package com.example.comprapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.comprapp.dbhelper.DBHelper;
import com.example.comprapp.modelo.Categoria;

import java.util.ArrayList;

public class CategoriaDAO {

    private static DBHelper dbHelper;

    public CategoriaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    private int insert(Categoria categoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Categoria.KEY_Nombre, categoria.getNombre());

        long idCategoria = db.insert(Categoria.TABLE, null, values);
        db.close();
        return (int) idCategoria;
    }

    private void delete(int idCategoria) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Categoria.TABLE, Categoria.KEY_ID + "= ?", new String[]{String.valueOf(idCategoria)});
        db.close();
    }

    private void update(Categoria categoria) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Categoria.KEY_Nombre, categoria.getNombre());

        db.update(Categoria.TABLE, values, Categoria.KEY_ID + "= ?", new String[]{String.valueOf(categoria.getId())});
        db.close();
    }

    private Categoria getCategoriaById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Categoria.KEY_ID + "," +
                Categoria.KEY_Nombre +
                " FROM " + Categoria.TABLE
                + " WHERE " +
                Categoria.KEY_ID + "=?";

        Categoria categoria = new Categoria();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                categoria.setId(cursor.getInt(cursor.getColumnIndex(Categoria.KEY_ID)));
                categoria.setNombre(cursor.getString(cursor.getColumnIndex(Categoria.KEY_Nombre)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categoria;
    }

    private ArrayList<Categoria> getCategoriaList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Categoria.KEY_ID + "," +
                Categoria.KEY_Nombre +
                " FROM " + Categoria.TABLE;

        ArrayList<Categoria> categoriaList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Categoria categoria = new Categoria();
                categoria.setId(cursor.getInt(cursor.getColumnIndex(Categoria.KEY_ID)));
                categoria.setNombre(cursor.getString(cursor.getColumnIndex(Categoria.KEY_Nombre)));
                categoriaList.add(categoria);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return categoriaList;
    }
}
