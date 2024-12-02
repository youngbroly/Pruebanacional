package com.example.myapplication;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class VentanaActvity extends AppCompatActivity {

    //declaramos variables para enlazar con los ids
    private TextView textView;
    private ImageView imageView;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana);
//enlazamos con los ids
        textView = findViewById(R.id.text4);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar1);
    }
//crear y ejecutar el threads para simular la carga de imagen y texto
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        //simular una operacion que toma un tiempo 5 segundos
        try {
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //actulizar la interfaz de usuario (ui) desde el hilo principal cuando pasen 5 segundos
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //ocultar la barra de progreso
                progressBar.setVisibility(View.GONE);
                //actualizar el texto
                textView.setText("imagen cargada correctamente👍✔");
                //Hacer visible la imagen
                imageView.setVisibility(View.VISIBLE);
                //Actualizar la imagen si es necesario , aunque ya eeste en el src del xml
                imageView.setImageResource(R.drawable.approved);
            }
        });
    }
});

    public void onClickMap(View view) {
        Intent intent = new Intent(this, MapaActivity.class);
        startActivity(intent);
    }

}
