package com.techera.reto.sqlite.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.techera.reto.sqlite.constantes.Constantes;
import com.techera.reto.sqlite.dto.Curso;
import com.techera.reto.sqlite.dto.Tecnologia;
import com.techera.reto.sqlite.servicios.ConectaDB;

import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private SQLiteDatabase db = null;

    public CursoDAO(Context context) {
        this.db =new ConectaDB(context, Constantes.BDD, null, Constantes.VERSION).getWritableDatabase();
    }

    public String insert(Curso curso) {
        String resp = null;
        ContentValues registry = new ContentValues();
        registry.put("id_tecnologia",curso.getId_tecnologia());
        registry.put("nombre",curso.getNombre());
        registry.put("descripcion",curso.getDescripcion());
        if (curso.getId_curso() != null){ registry.put("id_curso",curso.getId_curso()); }
        try {
            db.insertOrThrow(Constantes.TBL_CURSO,null,registry);
        } catch (SQLException e) {
            resp = e.getMessage().toString();
        }
        return resp;
    }

    public String update(Curso curso) {
        String resp = null;
        ContentValues registry = new ContentValues();
        registry.put("id_tecnologia",curso.getId_tecnologia());
        registry.put("nombre",curso.getNombre());
        registry.put("descripcion",curso.getDescripcion());
        String[] args = new String []{String.valueOf(curso.getId_curso())};
        try {
            db.update(Constantes.TBL_CURSO,registry,"id_curso=?",args);
        } catch (SQLException e) {
            resp = e.getMessage().toString();
        }
        return resp;
    }
    @SuppressLint("Range")
    public List<Curso> getCursos() {
        List<Curso> cursos = new ArrayList<>();
        String cadSQL = "SELECT c.id_curso, t.id_tecnologia, t.nombre AS tnombre, c.nombre, c.descripcion FROM " + Constantes.TBL_CURSO + " c " +
                "INNER JOIN " + Constantes.TBL_TECNOLOGIA + " t ON t.id_tecnologia = c.id_tecnologia";

        Cursor cursor = db.rawQuery(cadSQL, null);

        while (cursor.moveToNext()){
            cursos.add(new Curso(
                    cursor.getInt(cursor.getColumnIndex("id_curso")),
                    new Tecnologia(cursor.getInt(cursor.getColumnIndex("id_tecnologia")), cursor.getString(cursor.getColumnIndex("tnombre"))),
                    cursor.getString(cursor.getColumnIndex("nombre")),
                    cursor.getString(cursor.getColumnIndex("descripcion"))));
        }
        cursor.close();
        return cursos;
    }

    @SuppressLint("Range")
    public Curso getCursoById(Integer id_curso) {
        Curso curso = null;
        String cadSQL = "SELECT * FROM " + Constantes.TBL_CURSO + " WHERE id_curso=?";
        String parametros[] = {String.valueOf(id_curso)};
        Cursor cursor = db.rawQuery(cadSQL, parametros);

        if (cursor.moveToFirst()){
            curso = new Curso(
                    cursor.getInt(cursor.getColumnIndex("id_curso")),
                    cursor.getInt(cursor.getColumnIndex("id_tecnologia")),
                    cursor.getString(cursor.getColumnIndex("nombre")),
                    cursor.getString(cursor.getColumnIndex("descripcion")));
        }
        cursor.close();
        return curso;
    }

    public String delete(Integer id_curso) {
        String resp = null;
        String[] args = new String []{String.valueOf(id_curso)};
        try {
            db.delete(Constantes.TBL_CURSO,"id_curso=?",args);
        } catch (SQLException e) {
            resp = e.getMessage().toString();
        }
        return resp;
    }
}
