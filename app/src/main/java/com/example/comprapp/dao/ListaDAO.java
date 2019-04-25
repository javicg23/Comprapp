package com.example.comprapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.comprapp.dbhelper.DBHelper;
import com.example.comprapp.modelo.Lista;

import java.util.ArrayList;

public class ListaDAO {

    private static DBHelper dbHelper;

    public ListaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    private int insert(Lista lista) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Lista.KEY_Nombre, lista.getNombre());

        long idLista = db.insert(Lista.TABLE, null, values);
        db.close();
        return (int) idLista;
    }

    private void delete(int idLista) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Lista.TABLE, Lista.KEY_ID + "= ?", new String[]{String.valueOf(idLista)});
        db.close();
    }

    private void update(Lista lista) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Lista.KEY_Nombre, lista.getNombre());

        db.update(Lista.TABLE, values, Lista.KEY_ID + "= ?", new String[]{String.valueOf(lista.getId())});
        db.close();
    }

    private Lista getListaById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Lista.KEY_ID + "," +
                Lista.KEY_Nombre +
                " FROM " + Lista.TABLE
                + " WHERE " +
                Lista.KEY_ID + "=?";

        Lista lista = new Lista();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                lista.setId(cursor.getInt(cursor.getColumnIndex(Lista.KEY_ID)));
                lista.setNombre(cursor.getString(cursor.getColumnIndex(Lista.KEY_Nombre)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return lista;
    }

    private ArrayList<Lista> getListaList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                Lista.KEY_ID + "," +
                Lista.KEY_Nombre +
                " FROM " + Lista.TABLE;

        ArrayList<Lista> listaList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Lista lista = new Lista();
                lista.setId(cursor.getInt(cursor.getColumnIndex(Lista.KEY_ID)));
                lista.setNombre(cursor.getString(cursor.getColumnIndex(Lista.KEY_Nombre)));
                listaList.add(lista);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaList;
    }
}
