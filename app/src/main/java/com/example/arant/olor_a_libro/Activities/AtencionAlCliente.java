package com.example.arant.olor_a_libro.Activities;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class  AtencionAlCliente extends AppCompatActivity {
    Button enviar;
    EditText nombre, correo, duda;
    public boolean registrado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        registrado = getIntent().getExtras().getBoolean("registrado");

        setContentView(R.layout.atencionalcliente);
        enviar = findViewById(R.id.buttonEnviar);
        nombre = findViewById(R.id.editTextNombre);
        correo = findViewById(R.id.editTextCorreo);
        duda = findViewById(R.id.editTextDuda);

        /**
         * Al clicar en el botón 'ENVIAR' creamos un JSON con los datos que el usuario
         * introduzca en los EditText (nombre, correo y duda).
         */
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                try  {
                    jsonObject.put("nombre", nombre.getText().toString());
                    jsonObject.put("correo", correo.getText().toString());
                    jsonObject.put("duda", duda.getText().toString());
                    jsonArray.add(jsonObject);
                    FileWriter file = new FileWriter(Environment.getExternalStorageDirectory() + "/JSON_files/atencion_al_cliente.json");
                    file.write(jsonArray.toJSONString());
                    file.flush();


                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }
                Toast.makeText(getApplicationContext(), "Enviado", Toast.LENGTH_LONG).show();
                Intent myintent=new Intent(AtencionAlCliente.this,olorAlibro.class);
                startActivityForResult(myintent,0);
            }
        });
    }
    //Creamos un booleano del menu del action bar

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    // Creamos otro boolean con las opciones del menu.
    //En cada caso le decimos a donde queremos que nos mande cuando clickemos
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean devolver;
        switch (item.getItemId()) {
            case R.id.MenuOlorAlibro:
                Intent menu=new Intent(AtencionAlCliente.this,olorAlibro.class);
                startActivityForResult(menu,0);
                devolver=true;
                break;
            case R.id.Libreriastoolbar:

                Intent myintent=new Intent(AtencionAlCliente.this,Librerias.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    myintent.putExtra("usuario", posicionUsuario);
                }
                myintent.putExtra("registrado", registrado);
                startActivityForResult(myintent,0);
                devolver=true;
                break;
            case R.id.ActividadesToolbar:

                Intent actividades=new Intent(AtencionAlCliente.this,MainActividades.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    actividades.putExtra("usuario", posicionUsuario);
                }
                actividades.putExtra("registrado", registrado);
                startActivityForResult(actividades,0);
                devolver=true;
                break;
            case R.id.RedToolbar:

                Intent redes=new Intent(AtencionAlCliente.this,DatosDeRed.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    redes.putExtra("usuario", posicionUsuario);
                }
                redes.putExtra("registrado", registrado);
                startActivityForResult(redes,0);
                devolver=true;
                break;
            case R.id.AtencionToolbar:

                Intent atencion=new Intent(AtencionAlCliente.this,AtencionAlCliente.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    atencion.putExtra("usuario", posicionUsuario);
                }
                atencion.putExtra("registrado", registrado);
                startActivityForResult(atencion,0);
                devolver=true;
                break;
            case R.id.UsuarioToolbar:

                Intent Usuario=new Intent(AtencionAlCliente.this,Login.class);

                startActivityForResult(Usuario,0);
                devolver=true;
                break;
            default:
                devolver=super.onOptionsItemSelected(item);
                break;

        }
        return devolver;
    }
}

