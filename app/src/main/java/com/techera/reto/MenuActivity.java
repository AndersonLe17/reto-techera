package com.techera.reto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.techera.reto.sqlite.dao.UsuarioDAO;
import com.techera.reto.sqlite.dto.Usuario;

public class MenuActivity extends AppCompatActivity {
    // Componentes
    private TextView tUsuario;
    private Button btnCursos, btnPosts;
    // DAO
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    // BackPressed
    private static final int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initUI();
    }

    private void initUI() {
        setTitle("");
        tUsuario = (TextView) findViewById(R.id.tUsuario);
        btnCursos = (Button) findViewById(R.id.btnCursos);
        btnPosts = (Button) findViewById(R.id.btnPosts);
        usuarioDAO = new UsuarioDAO(this);

        cargarPreferencias();
        tUsuario.setText(usuario.getNombre());

        btnCursos.setOnClickListener(view -> openCourses());
        btnPosts.setOnClickListener(view -> openPosts());
    }

    private void openCourses() {
        Intent intent = new Intent(MenuActivity.this, CursosActivity.class);
        startActivity(intent);
    }

    private void openPosts() {
        Intent intent = new Intent(MenuActivity.this, PostsActivity.class);
        startActivity(intent);
    }

    private void cargarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String dni = sharedPreferences.getString("dni", null);

        usuario = usuarioDAO.findUserByDNI(dni);
    }

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            finish();
        }else {
            Toast.makeText(this, "Vuelve a presionar para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mCursos:
                openCourses();
                break;
            case R.id.mPosts:
                openPosts();
                break;
            case R.id.mSalir:
                closeSession();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeSession() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("dni");

        editor.commit();

        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}