package com.techera.reto.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.techera.reto.sqlite.constantes.Constantes;
import com.techera.reto.sqlite.dto.Tecnologia;
import com.techera.reto.sqlite.servicios.ConectaDB;

import java.util.ArrayList;
import java.util.List;

public class TecnologiaDAO {

    private SQLiteDatabase db = null;

    public TecnologiaDAO(Context context) {
        this.db =new ConectaDB(context, Constantes.BDD, null, Constantes.VERSION).getWritableDatabase();
    }

    @SuppressLint("Range")
    public List<Tecnologia> getTecnologias() {
        List<Tecnologia> tecnologias = new ArrayList<>();
        String cadSQL = "SELECT * FROM " + Constantes.TBL_TECNOLOGIA;

        Cursor cursor = db.rawQuery(cadSQL, null);

        while (cursor.moveToNext()){
            tecnologias.add(new Tecnologia(
                    cursor.getInt(cursor.getColumnIndex("id_tecnologia")),
                    cursor.getString(cursor.getColumnIndex("nombre")),
                    cursor.getString(cursor.getColumnIndex("descripcion"))));
        }
        cursor.close();
        return tecnologias;
    }

}
