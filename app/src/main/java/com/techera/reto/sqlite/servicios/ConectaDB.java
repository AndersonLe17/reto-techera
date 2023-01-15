package com.techera.reto.sqlite.servicios;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.techera.reto.sqlite.constantes.Constantes;

public class ConectaDB extends SQLiteOpenHelper {

    public ConectaDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constantes.TABLA_USUARIO);
        sqLiteDatabase.execSQL(Constantes.TABLA_TECNOLOGIA);
        sqLiteDatabase.execSQL(Constantes.TABLA_CURSO);
        sqLiteDatabase.execSQL(Constantes.REG_TECNOLOGIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {}
}
