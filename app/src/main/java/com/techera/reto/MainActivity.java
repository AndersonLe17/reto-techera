package com.techera.reto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.techera.reto.dao.UsuarioDAO;
import com.techera.reto.dto.Usuario;

public class MainActivity extends AppCompatActivity {
    // Componentes
    private TextInputLayout iEmail, iPassword;
    private Button btnLogin, btnRegister;
    private CheckBox cSave;
    // DAO
    private UsuarioDAO usuarioDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitUI();
    }

    private void InitUI() {
        iEmail = (TextInputLayout) findViewById(R.id.iEmail);
        iPassword = (TextInputLayout) findViewById(R.id.iPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        cSave = (CheckBox) findViewById(R.id.cSave);
        usuarioDAO = new UsuarioDAO(this);

        btnLogin.setOnClickListener(view -> loginUser(view));
        btnRegister.setOnClickListener(view -> openRegister(view));
        cargarPreferencias();
    }

    private void openRegister(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void loginUser(View view) {
        String email = iEmail.getEditText().getText().toString();
        String password = iPassword.getEditText().getText().toString();
        Usuario usuarioResponse;
        if (!TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            iEmail.getEditText().setError("Ingrese un Email.");
            return;
        }

        usuarioResponse = usuarioDAO.findUserByEmailAndPassword(email, password);

        if (usuarioResponse != null) {
            guardarPreferencias(usuarioResponse);
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            Snackbar.make(view,"Email/Password incorrectos.", BaseTransientBottomBar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.red))
                    .show();
        }
    }

    private void guardarPreferencias(Usuario usuario) {
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dni", usuario.getDni());
        if (cSave.isChecked()) {
            editor.putString("email", usuario.getEmail());
            editor.putString("password", usuario.getPassword());
        }

        editor.commit();
    }

    private void cargarPreferencias() {
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String email = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);

        iEmail.getEditText().setText(email);
        iPassword.getEditText().setText(password);
        if (email != null && password != null) {cSave.setChecked(true);}
    }
}