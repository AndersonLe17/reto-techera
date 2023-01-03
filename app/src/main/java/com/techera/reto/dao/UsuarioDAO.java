package com.techera.reto.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.techera.reto.constantes.Constantes;
import com.techera.reto.dto.Usuario;
import com.techera.reto.servicios.ConectaDB;

public class UsuarioDAO {

    private SQLiteDatabase db = null;

    public UsuarioDAO(Context context) {
        this.db =new ConectaDB(context, Constantes.BDD, null, Constantes.VERSION).getWritableDatabase();
    }

    public String insert(Usuario usuario) {
        String resp = null;
        ContentValues registry = new ContentValues();
        registry.put("dni", usuario.getDni());
        registry.put("nombre", usuario.getNombre());
        registry.put("apellido", usuario.getApellido());
        registry.put("email", usuario.getEmail());
        registry.put("password", usuario.getPassword());

        try {
            db.insertOrThrow(Constantes.TBL_USUARIO,null,registry);
        } catch (SQLException e) {
            resp =e.getMessage().toString();
        }
        return resp;
    }

    @SuppressLint("Range")
    public Usuario findUserByEmailAndPassword(String email, String password) {
        Usuario usuario = null;
        String cadSQL = "SELECT * FROM " + Constantes.TBL_USUARIO + " WHERE email=? AND password=?";
        String parametros[] = {email,password};
        Cursor cursor = db.rawQuery(cadSQL, parametros);

        if (cursor.moveToFirst()){
            usuario =new Usuario(cursor.getInt(cursor.getColumnIndex("id_usuario"))
                                ,cursor.getString(cursor.getColumnIndex("dni"))
                                ,cursor.getString(cursor.getColumnIndex("nombre"))
                                ,cursor.getString(cursor.getColumnIndex("apellido"))
                                ,cursor.getString(cursor.getColumnIndex("email"))
                                ,cursor.getString(cursor.getColumnIndex("password")));
        }
        cursor.close();
        return usuario;
    }

    @SuppressLint("Range")
    public Usuario findUserByDNI(String dni) {
        Usuario usuario = null;
        String cadSQL = "SELECT * FROM " + Constantes.TBL_USUARIO + " WHERE dni=?";
        String parametros[] = {dni};
        Cursor cursor = db.rawQuery(cadSQL, parametros);

        if (cursor.moveToFirst()){
            usuario =new Usuario(cursor.getInt(cursor.getColumnIndex("id_usuario"))
                    ,cursor.getString(cursor.getColumnIndex("dni"))
                    ,cursor.getString(cursor.getColumnIndex("nombre"))
                    ,cursor.getString(cursor.getColumnIndex("apellido"))
                    ,cursor.getString(cursor.getColumnIndex("email")));
        }
        cursor.close();
        return usuario;
    }
}