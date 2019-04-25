package com.example.comprapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.comprapp.dbhelper.DBHelper;
import com.example.comprapp.modelo.ProductoLista;

import java.util.ArrayList;

public class ProductoListaDAO {

    private static DBHelper dbHelper;

    public ProductoListaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    private int insert(ProductoLista productoLista) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductoLista.KEY_ID_Producto, productoLista.getIdProducto());
        values.put(ProductoLista.KEY_ID_Lista, productoLista.getIdLista());

        long idProductoLista = db.insert(ProductoLista.TABLE, null, values);
        db.close();
        return (int) idProductoLista;
    }

    private void delete(int idProductoLista) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ProductoLista.TABLE, ProductoLista.KEY_ID_Producto + "= ?", new String[]{String.valueOf(idProductoLista)});
        db.close();
    }

    private void update(ProductoLista productoLista) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProductoLista.KEY_ID_Producto, productoLista.getIdProducto());
        values.put(ProductoLista.KEY_ID_Lista, productoLista.getIdLista());

        db.update(ProductoLista.TABLE, values, ProductoLista.KEY_ID_Producto + "= ?", new String[]{String.valueOf(productoLista.getIdProducto())});
        db.close();
    }

    private ProductoLista getProductoListaById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ProductoLista.KEY_ID_Producto + "," +
                ProductoLista.KEY_ID_Lista +
                " FROM " + ProductoLista.TABLE
                + " WHERE " +
                ProductoLista.KEY_ID_Producto + "=?";

        ProductoLista productoLista = new ProductoLista();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                productoLista.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Producto)));
                productoLista.setIdLista(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Lista)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productoLista;
    }

    private ArrayList<ProductoLista> getProductoListaList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ProductoLista.KEY_ID_Producto + "," +
                ProductoLista.KEY_ID_Lista +
                " FROM " + ProductoLista.TABLE;

        ArrayList<ProductoLista> productoListaList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductoLista productoLista = new ProductoLista();
                productoLista.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Producto)));
                productoLista.setIdLista(cursor.getInt(cursor.getColumnIndex(ProductoLista.KEY_ID_Lista)));
                productoListaList.add(productoLista);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productoListaList;
    }
}
