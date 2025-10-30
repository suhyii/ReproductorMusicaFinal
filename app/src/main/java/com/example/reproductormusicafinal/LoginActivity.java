package com.example.reproductormusicafinal;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usuario = findViewById(R.id.etUsuario);
        EditText contrasena = findViewById(R.id.etContrasena);
        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = usuario.getText().toString();
            String pass = contrasena.getText().toString();

            if(user.equals("4323") && pass.equals("1313")){
                Intent i = new Intent(LoginActivity.this, ListasCanciones.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}