package com.movil.proaniostalin_pilayricky.consumowebservice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Consulta extends AppCompatActivity implements Response.Listener<JSONArray>,Response.ErrorListener{

    RequestQueue colapeticiones;
    String URL1 = "http://tareawebservicerest.azurewebsites.net/rest/deudor/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        colapeticiones = Volley.newRequestQueue(this);
        String URL3="http://tareawebservicerest.azurewebsites.net/rest/deudor/";
        JsonArrayRequest peticion = new JsonArrayRequest(Request.Method.GET, URL3, null, this, this);
        colapeticiones.add(peticion);

    }



    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e("Error",volleyError.getMessage());
    }

    @Override
    public void onResponse(JSONArray jsonObject) {
        Log.d("Respuesta,",jsonObject.toString());
        JSONArray objetos;
        objetos=jsonObject;
        try {

            String[] Campos = new String[objetos.length()];
            for (int i=0;objetos.length()>i;i++)
            {
                JSONObject objeto2=objetos.getJSONObject(i);
                //"cedula":null,"apellidos":null,"nombres":null,"celular":null,"direccion":null,"deuda":null,"valordeuda":null,"numerocuotas":null

                String Cedula=objeto2.getString("cedula");
                String Apellidos=objeto2.getString("apellidos");
                String Nombres=objeto2.getString("nombres");
                String celular=objeto2.getString("celular");
                String direccion=objeto2.getString("direccion");
                String deuda=objeto2.getString("deuda");
                String valordeuda=objeto2.getString("valordeuda");
                String numerocuota=objeto2.getString("numerocuotas");

                String Union = "Cédula= "+Cedula+"\n Apellidos : "+Apellidos+"\n Nombres : "+Nombres+"\n N° de celular: "+celular
                        +"\n Dirección : "+direccion+"\n Descripción de la deuda : "+deuda+"\n Deuda : "+valordeuda
                        +"\n N° de cuotas : "+numerocuota;

                Campos[i]=Union;


                // String[] Campos = new String[objetos.length()];




                //Toast.makeText(ActivityMain.this, id+nombre+apellido+edad, Toast.LENGTH_SHORT).show();

            }
            ArrayAdapter<String> lista = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, Campos);
            ListView listaPunto = (ListView) findViewById(R.id.ListaDeudor);
            listaPunto.setAdapter(null);
            listaPunto.setAdapter(lista);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
