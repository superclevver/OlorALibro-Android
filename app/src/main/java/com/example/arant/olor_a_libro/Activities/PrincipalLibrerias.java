package com.example.arant.olor_a_libro.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.arant.olor_a_libro.Clases.ClaseLibreria;
import com.example.arant.olor_a_libro.R;

import org.w3c.dom.Text;

import java.io.File;

public class PrincipalLibrerias extends AppCompatActivity {
    String name;
    String file;
    int img;
    private ListView lista;
    public boolean registrado;
    private TextView NombreContacto;
    private TextView DescripcionLiberia;
    private TextView DireccionLibreria;
    private TextView TelefonoLibrerias;
    private TextView TituloLibrerias;
    private ImageView ImagenLibreria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principallibrerias);

        /*Intent de registrado*/
        registrado = getIntent().getExtras().getBoolean("registrado");



        final Intent intent =getIntent();
        //Estamos recogiendo la posicion del intent de libreria
        final ClaseLibreria libreria1 =(ClaseLibreria) getIntent().getSerializableExtra("sitio");
        ImagenLibreria=(ImageView)findViewById(R.id.ImagenLibrerias);
      //Le indicamos la foto que queremos coger por el nombre y la posicion
        String dir =Environment.getExternalStorageDirectory()+ File.separator+libreria1.getFoto();
        File file =new File(dir);
        ImagenLibreria.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        /*
        Le indicamos a que TextView nos referimos y  a que variable de la claseLibreria .
         */

        TituloLibrerias= (TextView) findViewById(R.id.TituloLibrerias);
        TituloLibrerias.setText(libreria1.getTitulo());

        TelefonoLibrerias= (TextView) findViewById(R.id.TelefonoLibrerias);
        TelefonoLibrerias.setText(libreria1.getTelefono()+"");
        DireccionLibreria=(TextView)findViewById(R.id.DireccionLibrerias);
        DireccionLibreria.setText(libreria1.getUbicacion());

        NombreContacto= (TextView) findViewById(R.id.NombreContacto);
        NombreContacto.setText(libreria1.getNombreContacto() );
        DescripcionLiberia= (TextView) findViewById(R.id.DescripcionLibrerias);
        DescripcionLiberia.setText(libreria1.getDescripcion() );



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
                Intent menu=new Intent(PrincipalLibrerias.this,olorAlibro.class);
                startActivityForResult(menu,0);
                devolver=true;
                break;
            case R.id.Libreriastoolbar:

                Intent myintent=new Intent(PrincipalLibrerias.this,Librerias.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    myintent.putExtra("usuario", posicionUsuario);
                }
                myintent.putExtra("registrado", registrado);
                startActivityForResult(myintent,0);
                devolver=true;
                break;
            case R.id.ActividadesToolbar:

                Intent actividades=new Intent(PrincipalLibrerias.this,MainActividades.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    actividades.putExtra("usuario", posicionUsuario);
                }
                actividades.putExtra("registrado", registrado);
                startActivityForResult(actividades,0);
                devolver=true;
                break;

            case R.id.RedToolbar:
                Intent redes=new Intent(PrincipalLibrerias.this,DatosDeRed.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    redes.putExtra("usuario", posicionUsuario);
                }
                startActivityForResult(redes,0);
                redes.putExtra("registrado", registrado);
                devolver=true;
                break;

            case R.id.AtencionToolbar:
                Intent atencion=new Intent(PrincipalLibrerias.this,AtencionAlCliente.class);
                startActivityForResult(atencion,0);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    atencion.putExtra("usuario", posicionUsuario);
                }
                atencion.putExtra("registrado", registrado);
                devolver=true;
                break;

            case R.id.UsuarioToolbar:
                Intent Usuario=new Intent(PrincipalLibrerias.this,Login.class);
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
