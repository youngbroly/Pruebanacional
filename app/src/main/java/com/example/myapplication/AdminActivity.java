package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

//librerias de MQTT y formulario
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class AdminActivity extends AppCompatActivity {
    // Variables de la conexión a MQTT
    private static String mqtthost = "tcp://sapphirewhale459.cloud.shiftr.io:1883"; // IP del MQTT
    private static String IdUsuario = "AppAndroid"; // Nombre del dispositivo que se conectará
    private static String topico = "Mensaje"; // Tópico al que se suscribirá
    private static String User = "sapphirewhale459"; // Usuario (sin espacios)
    private static String Pass = "m7aFVIkcuamoVAC0"; // Contraseña o token (reemplaza con el token correcto)

    // Variable que se utilizará para imprimir los datos del sensor
    private TextView textView;
    private EditText editTextMessage;
    private Button botonEnvio;

    // Librería MQTT
    private MqttClient mqttClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Enlace de la variable del ID que está en el activity main donde imprimiremos los datos
        textView = findViewById(R.id.textview);
        editTextMessage = findViewById(R.id.txtMensaje);
        botonEnvio = findViewById(R.id.botonEnvioMensaje);

        try {
            // Creación de un cliente MQTT
            mqttClient = new MqttClient(mqtthost, IdUsuario, null);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(User);
            options.setPassword(Pass.toCharArray());
            // Conexión al servidor MQTT
            mqttClient.connect(options);
            // Si se conecta, imprimirá un mensaje de MQTT
            Toast.makeText(this, "Aplicación conectada al servidor MQTT", Toast.LENGTH_SHORT).show();
            // Manejo de entrega de datos y pérdida de conexión
            mqttClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d("MQTT", "Conexión perdida");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String payload = new String(message.getPayload());
                    runOnUiThread(() -> textView.setText(payload));
                }

                // Método para verificar si el envío fue exitoso
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d("MQTT", "Entrega Completa");
                }
            });

        } catch (MqttException e) {
            e.printStackTrace();
        }

        // Al hacer clic en el botón, se enviará un mensaje al tópico
        botonEnvio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener el mensaje ingresado por el usuario
                String mensaje = editTextMessage.getText().toString().trim(); // Usar trim() para eliminar espacios en blanco

                if (mensaje.isEmpty()) {
                    // Validar que el mensaje no esté vacío
                    Toast.makeText(AdminActivity.this, "Por favor ingrese un mensaje.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Verificar si la conexión MQTT está activa
                    if (mqttClient != null && mqttClient.isConnected()) {
                        // Publicar el mensaje en el tópico especificado
                        mqttClient.publish(topico, mensaje.getBytes(), 0, false);
                        // Mostrar el mensaje enviado en el TextView
                        textView.append("\n - " + mensaje);
                        Toast.makeText(AdminActivity.this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminActivity.this, "Error: no se pudo enviar el mensaje. La conexión MQTT no está activa.", Toast.LENGTH_SHORT).show();
                    }
                } catch (MqttException e) {
                    e.printStackTrace();
                    Toast.makeText(AdminActivity.this, "Error al enviar el mensaje: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });};}
