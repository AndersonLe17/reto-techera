package com.techera.reto;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.techera.reto.dao.UsuarioDAO;
import com.techera.reto.dto.Usuario;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterActivity extends AppCompatActivity {
    // Componentes
    private TextInputLayout iNombre, iApellido, iDNI, iEmail, iPassword, iRePassword;
    private ImageButton btnBack;
    private Button btnRegister;
    // DAO
    private UsuarioDAO usuarioDAO;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initUI() {
        iNombre = (TextInputLayout) findViewById(R.id.iNombre);
        iApellido = (TextInputLayout) findViewById(R.id.iApellido);
        iDNI = (TextInputLayout) findViewById(R.id.iDNI);
        iEmail = (TextInputLayout) findViewById(R.id.iEmail);
        iPassword = (TextInputLayout) findViewById(R.id.iPassword);
        iRePassword = (TextInputLayout) findViewById(R.id.iRePassword);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnRegister = (Button) findViewById(R.id.btnRegister);

        usuarioDAO = new UsuarioDAO(this);

        btnBack.setOnClickListener(view -> backLogin(view));
        btnRegister.setOnClickListener(view -> registerUser(view));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void registerUser(View view) {
        if (!verifyInputs()) {return;}
        String nombre = iNombre.getEditText().getText().toString();
        String apellido = iApellido.getEditText().getText().toString();
        String dni = iDNI.getEditText().getText().toString();
        String email = iEmail.getEditText().getText().toString();
        String password = iPassword.getEditText().getText().toString();
        String repassword = iRePassword.getEditText().getText().toString();
        String response;

        if (password.contains(" ") || repassword.contains(" ")){
            Snackbar.make(view,"Las contraseñas no deben contener espacios.", BaseTransientBottomBar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.red))
                    .show();
            return;
        } else if (password.isEmpty() || repassword.isEmpty()) {
            Snackbar.make(view,"Las contraseñas no pueden estar en blanco", BaseTransientBottomBar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.red))
                    .show();
            return;
        }else if (!password.equals(repassword)) {
            Snackbar.make(view,"Contraseñas no coinciden.", BaseTransientBottomBar.LENGTH_SHORT)
                    .setBackgroundTint(getColor(R.color.red))
                    .show();
            return;
        }

        response = usuarioDAO.insert(new Usuario(dni, nombre, apellido, email, password));

        if (response == null) {
            guardarPreferencias(dni);
            Intent intent = new Intent(RegisterActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.i("SQLITE-INFO", response);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean verifyInputs() {
        List<TextInputLayout> inputs = Arrays.asList(iNombre,iApellido,iDNI,iEmail);
        AtomicBoolean verified = new AtomicBoolean(true);

        inputs.forEach(input -> {
            if (TextUtils.isEmpty(input.getEditText().getText().toString())){
                input.getEditText().setError("Completar Campo");
                verified.set(false);
            } else {
                input.getEditText().setError(null);

            }
        });
        return verified.get();
    }

    private void backLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void guardarPreferencias(String dni) {
        SharedPreferences sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("dni", dni);

        editor.commit();
    }

}