package com.techera.reto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.techera.reto.dao.CursoDAO;
import com.techera.reto.dao.TecnologiaDAO;
import com.techera.reto.dao.UsuarioDAO;
import com.techera.reto.dto.Curso;
import com.techera.reto.dto.Tecnologia;

import java.util.List;

public class CursosActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnFocusChangeListener {
    // Componentes
    private TextInputLayout iIdCurso, iNombre, iDescripcion;
    private EditText editIdCurso;
    private AutoCompleteTextView iTecnologia;
    private Button btnRegister, btnEliminar;
    private ListView lvCursos;
    // DAO
    private TecnologiaDAO tecnologiaDAO;
    private CursoDAO cursoDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        initUI();
    }

    private void initUI() {
        iIdCurso = (TextInputLayout) findViewById(R.id.iIdCurso);
        editIdCurso = (EditText) findViewById(R.id.editIdCurso);
        iNombre = (TextInputLayout) findViewById(R.id.iNombre);
        iDescripcion = (TextInputLayout) findViewById(R.id.iDescripcion);
        iTecnologia = findViewById(R.id.iTecnologia);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);
        lvCursos = (ListView) findViewById(R.id.lvCursos);
        tecnologiaDAO = new TecnologiaDAO(this);
        cursoDAO = new CursoDAO(this);

        cargarTecnologias();
        cargarItems();

        btnRegister.setOnClickListener(view -> registrarCurso(view));
        btnEliminar.setOnClickListener(view -> eliminarCurso(view));
        lvCursos.setOnItemClickListener(this);
        editIdCurso.setOnFocusChangeListener(this);
    }

    private void registrarCurso(View view) {
        String nombre = iNombre.getEditText().getText().toString();
        String descripcion = iDescripcion.getEditText().getText().toString();
        Integer id_tecnologia = Integer.parseInt(iTecnologia.getText().toString().split("-")[0]);
        String response;

        if (btnRegister.getText().toString().equals("Registrar")){
            response = cursoDAO.insert(new Curso(id_tecnologia,nombre,descripcion));
        } else {
            Integer id_curso = Integer.parseInt(iIdCurso.getEditText().getText().toString());
            response = cursoDAO.update(new Curso(id_curso,id_tecnologia,nombre,descripcion));
        }
        String estado = (btnRegister.getText().toString().equals("Registrar"))?"Registrado":"Modificado";
        if (response == null) {
            Snackbar.make(view,"Curso " + estado, BaseTransientBottomBar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.green))
                    .show();
            cargarItems();
        } else {
            Log.i("INFOX",response);
        }
    }

    private void eliminarCurso(View view) {
        Integer id_curso = Integer.parseInt(iIdCurso.getEditText().getText().toString());
        String response = cursoDAO.delete(id_curso);
        if (response == null) {
            Toast.makeText(this, "Curso N°"+id_curso+" Eliminado", Toast.LENGTH_LONG).show();
            cargarItems();
            btnRegister.setText("Registrar");
            btnEliminar.setEnabled(false);
        } else {
            Log.i("INFOX",response);
        }
    }

    private void cargarTecnologias() {
        List<Tecnologia> tecnologias = tecnologiaDAO.getTecnologias();
        String[] type = new String[tecnologias.size()];
        for (int i = 0; i < type.length; i++) {
            type[i] = tecnologias.get(i).getId_tecnologia() + "- " + tecnologias.get(i).getNombre();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_down_item,type);
        iTecnologia.setAdapter(adapter);
    }

    private void cargarItems() {
        List<Curso> cursos = cursoDAO.getCursos();

        ArrayAdapter<Curso> adapter = new ArrayAdapter<Curso>(this, android.R.layout.simple_list_item_1, cursos);

        lvCursos.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Curso curso = (Curso) adapterView.getAdapter().getItem(i);
        mostrarCurso(curso);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()){
            case R.id.editIdCurso:
                if (!hasFocus){
                    try {
                        Integer id_curso = Integer.parseInt(editIdCurso.getText().toString());
                        Curso curso = cursoDAO.getCursoById(id_curso);
                        mostrarCurso(curso);
                    } catch (NumberFormatException e) {
                        //Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
                        mostrarCurso(null);
                    }
                }
                break;
        }
    }

    private void mostrarCurso(Curso curso) {
        if (curso != null){
            iIdCurso.getEditText().setText(String.valueOf(curso.getId_curso()));
            iTecnologia.setText(iTecnologia.getAdapter().getItem((curso.getId_tecnologia() != null)? curso.getId_tecnologia()-1 : curso.getTecnologia().getId_tecnologia()-1).toString(), false);
            iNombre.getEditText().setText(String.valueOf(curso.getNombre()));
            iDescripcion.getEditText().setText(String.valueOf(curso.getDescripcion()));
            btnRegister.setText("Modificar");
            btnEliminar.setEnabled(true);
        } else {
            iNombre.getEditText().setText("");
            iTecnologia.setText(null,false);
            iDescripcion.getEditText().setText("");
            btnRegister.setText("Registrar");
            btnEliminar.setEnabled(false);
        }
    }

}