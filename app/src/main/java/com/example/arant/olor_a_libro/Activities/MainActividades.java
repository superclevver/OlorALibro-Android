package com.example.arant.olor_a_libro.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.arant.olor_a_libro.Clases.ClaseActivitats;
import com.example.arant.olor_a_libro.R;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActividades extends AppCompatActivity {

    public boolean registrado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_actividades);

        /*agafem si esta resgitrat o no*/
        registrado = getIntent().getExtras().getBoolean("registrado");
        /*Agafem Posisio Usuari*/


        /*Actividades*/
        ArrayList<ClaseActivitats> actividades= new ArrayList<ClaseActivitats>();
        actividades = activitatsJson(actividades);

        ActivitatAdapter adapter = new ActivitatAdapter(this, actividades);
        ListView llista = (ListView) findViewById(R.id.llista);
        llista.setAdapter(adapter);

        /*Spinner*/
        ArrayList<String> categories = new ArrayList<String>();

        categories.add("TODAS");
        categories.add("INFANTIL");
        categories.add("ADULTOS");


        final Spinner mySpinner = (Spinner) findViewById(R.id.mySpinner);

        ArrayAdapter<String> adap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories);

        mySpinner.setAdapter(adap);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ArrayList<ClaseActivitats> actividades1= new ArrayList<ClaseActivitats>();
                actividades1 = activitatsJson(actividades1);
                actividades1 = omplirListView(actividades1, position);

                ActivitatAdapter adapter1 = new ActivitatAdapter(MainActividades.this, actividades1);
                ListView llista = (ListView) findViewById(R.id.llista);
                llista.setAdapter(adapter1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        llista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<ClaseActivitats> actividades1= new ArrayList<ClaseActivitats>();
                actividades1 = activitatsJson(actividades1);
                actividades1 = omplirListView(actividades1, mySpinner.getSelectedItemPosition());

                /*CReacio nou intent*/
                Intent myintent = new Intent (MainActividades.this, PrincipalActivitats.class);

                /*Enviem el nom de l'intent*/
                myintent.putExtra("nombre",actividades1.get(position).getNombre());

                if (registrado){
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    myintent.putExtra("usuario", posicionUsuario);
                }
                myintent.putExtra("apuntado", false);
                myintent.putExtra("registrado", registrado);

                startActivity(myintent);
            }
        });
    }

    /*FILL ACTIVITIES in ListView*/
    public ArrayList<ClaseActivitats> omplirListView(ArrayList<ClaseActivitats> actividades, int position) {

        int i;
        ArrayList<ClaseActivitats> aux = new ArrayList<ClaseActivitats>();

        switch (position){
            case 0:
                aux = actividades;
                break;
            case 1:
                for (i = 0; i < actividades.size(); i++){
                    ClaseActivitats a = actividades.get(i);
                    if (a.isCategorias()){
                        aux.add(a);
                    }
                }
                break;
            case 2:
                for (i = 0; i < actividades.size(); i++){
                    ClaseActivitats a = actividades.get(i);
                    if (!a.isCategorias()){
                        aux.add(a);
                    }
                }
                break;
        }
        return aux;
    }

    /*CUSTOM ADAPTER*/
    public class ActivitatAdapter extends ArrayAdapter<ClaseActivitats> {
        public ActivitatAdapter(Context context, ArrayList<ClaseActivitats> activitats)   //Constrcutor
        {
            super(context, 0, activitats);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position -> obté les dades de l'item en funcio de la position
            ClaseActivitats activitat = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view -> comprova si un vista existent esta sent reutilitzada, en cas que no:
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.rellenoactividades, parent, false);
            }
            // Lookup view for data population
            TextView nomActi = (TextView) convertView.findViewById(R.id.nom);
            TextView temaActi = (TextView) convertView.findViewById(R.id.tema);
            ImageView imatge = (ImageView) convertView.findViewById(R.id.imatge);
            // Populate the data into the template view using the data object
            nomActi.setText(activitat.getNombre());
            temaActi.setText(activitat.getTemas().get(0).toString());
            imatge.setImageResource(R.mipmap.reliquias_muerte_blanc);
            // Return the completed view to render on screen
            return convertView;
        }
    }

    /*JSON*/
    public ArrayList<ClaseActivitats> activitatsJson(ArrayList<ClaseActivitats> activitats){

        JSONParser parser = new JSONParser();
        try{
            Object obj = parser.parse(new FileReader(Environment.getExternalStorageDirectory()
                    + "/JSON_files/actividades.json"));

            JSONArray jsonArray = (JSONArray) obj;

            for (int i = 0; i < jsonArray.size(); i++ ){
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                String nombre = (String)jsonObject.get("nombre");
                String fechaInicio = (String)jsonObject.get("fechaInicio");
                String fechaFinal = (String)jsonObject.get("fechaFinal");
                boolean categorias = (boolean)jsonObject.get("categorias");

                JSONArray jsonArrayT = (JSONArray)jsonObject.get("temas");
                Iterator<String> iterator = jsonArrayT.iterator();
                ArrayList<String> temas = new ArrayList<String>();
                while (iterator.hasNext()){
                    temas.add(iterator.next());
                }

                String descripcion = (String)jsonObject.get("descripcion");

                JSONArray jsonArrayL = (JSONArray)jsonObject.get("librerias");
                Iterator<String> iterator2 = jsonArrayL.iterator();

                ArrayList<String> librerias = new ArrayList<String>();
                while (iterator2.hasNext()){
                    librerias.add(iterator2.next());
                }

                int inscritos = (int)(long)jsonObject.get("inscritos");

                ClaseActivitats a = new ClaseActivitats(nombre, fechaInicio, fechaFinal, categorias, temas, descripcion, librerias, inscritos);
                activitats.add(a);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return activitats;
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
                Intent menu=new Intent(MainActividades.this,olorAlibro.class);
                startActivityForResult(menu,0);
                devolver=true;
                break;
            case R.id.Libreriastoolbar:

                Intent myintent=new Intent(MainActividades.this,Librerias.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    myintent.putExtra("usuario", posicionUsuario);
                }
                myintent.putExtra("registrado", registrado);
                startActivityForResult(myintent,0);
                devolver=true;
                break;
            case R.id.ActividadesToolbar:

                Intent actividades=new Intent(MainActividades.this,MainActividades.class);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    actividades.putExtra("usuario", posicionUsuario);
                }
                actividades.putExtra("registrado", registrado);
                startActivityForResult(actividades,0);
                devolver=true;
                break;
            case R.id.RedToolbar:

                Intent redes=new Intent(MainActividades.this,DatosDeRed.class);
                redes.putExtra("registrado", registrado);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    redes.putExtra("usuario", posicionUsuario);
                }
                startActivityForResult(redes,0);
                devolver=true;
                break;
            case R.id.AtencionToolbar:

                Intent atencion=new Intent(MainActividades.this,AtencionAlCliente.class);
                atencion.putExtra("registrado", registrado);
                if (registrado) {
                    int posicionUsuario = getIntent().getExtras().getInt("usuario");
                    atencion.putExtra("usuario", posicionUsuario);
                }
                startActivityForResult(atencion,0);
                devolver=true;
                break;
            case R.id.UsuarioToolbar:

                Intent Usuario=new Intent(MainActividades.this,Login.class);
                Usuario.putExtra("registrado", registrado);
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




