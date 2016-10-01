package com.movil.proaniostalin_pilayricky.consumowebservice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Eliminar extends AppCompatActivity {

    RequestQueue colapeticiones;
    Button btnconsultar,btneliminar;
    EditText txtbuscar;
    TextView pant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar);
        txtbuscar=(EditText)findViewById(R.id.etBuscar);

        colapeticiones= Volley.newRequestQueue(this);


        btnconsultar=(Button)findViewById(R.id.btnConsul);
        btnconsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txtf = txtbuscar.getText().toString();
                String URL = "http://tareawebservicerest.azurewebsites.net/rest/deudor/details/" + txtf + "/";
                //String URL = "http://tareawebservicerest.azurewebsites.net/rest/deudor/details/0802268615";
                pant = (TextView) findViewById(R.id.pantallaind);
                JsonObjectRequest peticionindivudal = new JsonObjectRequest(Request.Method
                        .GET, URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                try {
                                    String cedula = jsonObject.getString("cedula");
                                    String nombre = jsonObject.getString("nombres");
                                    String celular = jsonObject.getString("celular");
                                    String apellido = jsonObject.getString("apellidos");
                                    String direccion = jsonObject.getString("direccion");
                                    String deuda = jsonObject.getString("deuda");
                                    String valor = jsonObject.getString("valordeuda");
                                    String cuotas = jsonObject.getString("numerocuotas");

                                    String Union = "CEDULA: " + cedula + "\n NOMBRES: " + nombre + "\n APELLIDOS: " + apellido + "\n NUMERO DE TELEFONO: " + celular+ "\n DIRECION: " + direccion+ "\n DEUDA: " + deuda+ "\n VALOR DE DEUDA: " + valor+ "\n NUMERO DE CUOTAS: " + cuotas;


                                    pant.setText(Union);
                                    Toast.makeText(Eliminar.this, Union, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Eliminar.this, "No existe Persona con el codigo ID de" + txtf, Toast.LENGTH_SHORT).show();

                    }
                });
                colapeticiones.add(peticionindivudal);


            }
        });
        btneliminar=(Button)findViewById(R.id.btneliminar);
        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txtf=txtbuscar.getText().toString();
                String URL = "http://tareawebservicerest.azurewebsites.net/rest/deudor/delete/" + txtf + "/";
                JsonObjectRequest peticionindivudal=new JsonObjectRequest(Request.Method
                        .DELETE, URL, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                pant.setText("");
                                Toast.makeText(Eliminar.this,"Se elimino el deudor con cedula:"+txtf, Toast.LENGTH_SHORT).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Eliminar.this, "No existe el deudor que desea eliminar", Toast.LENGTH_SHORT).show();

                    }
                });colapeticiones.add(peticionindivudal);


            }
        });

    }

}
