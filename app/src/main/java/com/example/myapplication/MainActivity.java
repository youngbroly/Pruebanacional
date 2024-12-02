package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usuarioEditText;
    private EditText contrasenaEditText;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazo las variables con los id correspondientes del activity_main
        usuarioEditText = findViewById(R.id.usuario);
        contrasenaEditText = findViewById(R.id.contrasena);
        spinner = findViewById(R.id.spinnerRoles);

        // Arreglo de elementos que aparecerán en mi Spinner
        String[] roles = {"Administrador", "Usuario"};

        // Creamos un ArrayAdapter para poblar el spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Asignar el ArrayAdapter al Spinner
        spinner.setAdapter(adapter);
    }

    // Este método será llamado cuando se haga clic en el botón de login
    public void onClickAcceder(View view) {
        // Declaramos variables para obtener los valores ingresados en los campos
        String user = usuarioEditText.getText().toString().trim();
        String pass = contrasenaEditText.getText().toString().trim();
        String rol = spinner.getSelectedItem().toString();

        // Si está vacío saltará un Toast
        if (user.isEmpty()) {
            Toast.makeText(this, "El campo de usuario está vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        // Si está vacío saltará un Toast
        if (pass.isEmpty()) {
            Toast.makeText(this, "El campo de contraseña está vacío", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifico las credenciales y dependiendo del usuario me enviará a diferentes activities
        if (user.equals("usuario1") && pass.equals("1234") && rol.equals("Usuario")) {
            // Inicio una actividad que se realizará en la clase AccesoActivity
            Intent intent = new Intent(this, AccesoActivity.class);
            startActivity(intent);
        } else {
            // Si nada es válido, saltará una alerta
            Toast.makeText(this, "Credenciales Incorrectas", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para registrar
    public void onClickRegistrar(View view) {
        // Inicia la actividad VideoActivity
        Intent intent = new Intent(this, VideoActivity.class);
        startActivity(intent);
    }
}