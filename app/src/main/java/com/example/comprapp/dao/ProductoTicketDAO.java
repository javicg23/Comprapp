package com.example.comprapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.comprapp.dbhelper.DBHelper;
import com.example.comprapp.modelo.ProductoTicket;

import java.util.ArrayList;

public class ProductoTicketDAO {

    private static DBHelper dbHelper;

    public ProductoTicketDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    private int insert(ProductoTicket productoTicket) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ProductoTicket.KEY_ID_Producto, productoTicket.getIdProducto());
        values.put(ProductoTicket.KEY_ID_Ticket, productoTicket.getIdTicket());
        values.put(ProductoTicket.KEY_Cantidad, productoTicket.getCantidad());

        long idProductoTicket = db.insert(ProductoTicket.TABLE, null, values);
        db.close();
        return (int) idProductoTicket;
    }

    private void delete(int idProductoTicket) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(ProductoTicket.TABLE, ProductoTicket.KEY_ID_Producto + "= ?", new String[]{String.valueOf(idProductoTicket)});
        db.close();
    }

    private void update(ProductoTicket productoTicket) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ProductoTicket.KEY_ID_Producto, productoTicket.getIdProducto());
        values.put(ProductoTicket.KEY_ID_Ticket, productoTicket.getIdTicket());
        values.put(ProductoTicket.KEY_Cantidad, productoTicket.getCantidad());

        db.update(ProductoTicket.TABLE, values, ProductoTicket.KEY_ID_Producto + "= ?", new String[]{String.valueOf(productoTicket.getIdProducto())});
        db.close();
    }

    private ProductoTicket getProductoTicketById(int id){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ProductoTicket.KEY_ID_Producto + "," +
                ProductoTicket.KEY_ID_Ticket + "," +
                ProductoTicket.KEY_Cantidad +
                " FROM " + ProductoTicket.TABLE
                + " WHERE " +
                ProductoTicket.KEY_ID_Producto + "=?";

        ProductoTicket productoTicket = new ProductoTicket();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(id) } );

        if (cursor.moveToFirst()) {
            do {
                productoTicket.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Producto)));
                productoTicket.setIdTicket(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Ticket)));
                productoTicket.setCantidad(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_Cantidad)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productoTicket;
    }

    private ArrayList<ProductoTicket> getProductoTicketList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  " +
                ProductoTicket.KEY_ID_Producto + "," +
                ProductoTicket.KEY_ID_Ticket + "," +
                ProductoTicket.KEY_Cantidad +
                " FROM " + ProductoTicket.TABLE;

        ArrayList<ProductoTicket> productoTicketList = new ArrayList<>();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ProductoTicket productoTicket = new ProductoTicket();
                productoTicket.setIdProducto(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Producto)));
                productoTicket.setIdTicket(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_ID_Ticket)));
                productoTicket.setCantidad(cursor.getInt(cursor.getColumnIndex(ProductoTicket.KEY_Cantidad)));
                productoTicketList.add(productoTicket);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productoTicketList;
    }
}
