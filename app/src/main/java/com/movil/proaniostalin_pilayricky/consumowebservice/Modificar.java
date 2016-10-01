package com.movil.proaniostalin_pilayricky.consumowebservice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Modificar extends AppCompatActivity {
    public static final int NUMERO_DE_PROVINCIAS = 24;//22;
    RequestQueue colapeticiones;
    Button btnconsultar,btnmodificar;
    EditText txtbuscar;
    EditText papellidos,pnombres,pcedula,pdireccion,pcelular,pdeuda,pcuotas,pvalor;
    TextView pant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar);
        txtbuscar=(EditText)findViewById(R.id.etBuscar);
        papellidos=(EditText)findViewById(R.id.txtapelidosn);
        pnombres=(EditText)findViewById(R.id.txtnombresn);
        pcedula=(EditText)findViewById(R.id.txtcedulan);
        pcelular=(EditText)findViewById(R.id.txtcelularn);
        pdeuda=(EditText)findViewById(R.id.txtdeudan);
        pcuotas=(EditText)findViewById(R.id.txtcuotasn);
        pvalor=(EditText)findViewById(R.id.txtvalordeudan);
        pdireccion = (EditText)findViewById(R.id.txtdireccionn);



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
                                    pcedula.setText(cedula);
                                    papellidos.setText(apellido);
                                    pnombres.setText(nombre);
                                    pcelular.setText(celular);
                                    pdireccion.setText(direccion);
                                    pdeuda.setText(deuda);
                                    pvalor.setText(valor);
                                    pcuotas.setText(cuotas);
                                    Toast.makeText(Modificar.this, "", Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(Modificar.this, "No existe Persona con el codigo ID de" + txtf, Toast.LENGTH_SHORT).show();

                    }
                });
                colapeticiones.add(peticionindivudal);


            }
        });
        btnmodificar=(Button)findViewById(R.id.btnmodificar);
        btnmodificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String patron = "^[0]{1}[9]{1}[1-9]{2}[0-9]{6}$";


                Pattern p = Pattern.compile(patron);
                //Pattern p = Pattern.compile(patron);

              //Busca patron en el texto
                Matcher matcher = p.matcher(pcelular.getText());

                if (matcher.matches()) {

                    if (CedulaValida(pcedula.getText().toString())==true)
                    {

                        modificarDatos();

                    }
                    else
                    {
                        Toast.makeText(Modificar.this, "Cedula Incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Modificar.this, "celular malo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public  void modificarDatos()
    {
        colapeticiones = Volley.newRequestQueue(this);

        String textoBuscar = ((EditText)findViewById(R.id.etBuscar)).getText().toString();
        try {

            JSONObject jdeudor = new JSONObject();
            jdeudor.put("apellidos", papellidos.getText().toString());
            jdeudor.put("nombres", pnombres.getText().toString());
            jdeudor.put("cedula", pcedula.getText().toString());
            jdeudor.put("celular", pcelular.getText().toString());
            jdeudor.put("direccion", pdireccion.getText().toString());
            jdeudor.put("deuda", pdeuda.getText().toString());
            jdeudor.put("numerocuotas", pcuotas.getText().toString());
            jdeudor.put("valordeuda", pvalor.getText().toString());
            String URL = "http://tareawebservicerest.azurewebsites.net/rest/deudor/edit/"+textoBuscar+"/";
            JsonObjectRequest pDeudor = new JsonObjectRequest(Request.Method.PUT, URL, jdeudor, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.e("Datos modificados correctamente", jsonObject.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Error al modificar datos", volleyError.getMessage());

                }

            });colapeticiones.add(pDeudor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(Modificar.this, "Datos Actualizados Correctamente", Toast.LENGTH_SHORT).show();
    }
    public boolean CedulaValida(String cedula) {
        //verifica que tenga 10 dígitos y que contenga solo valores numéricos
        if (!((cedula.length() == 10) && cedula.matches("^[0-9]{10}$"))) {
            return false;
        }

        //verifica que los dos primeros dígitos correspondan a un valor entre 1 y NUMERO_DE_PROVINCIAS
        int prov = Integer.parseInt(cedula.substring(0, 2));

        if (!((prov > 0) && (prov <= NUMERO_DE_PROVINCIAS))) {
            return false;
        }

        //verifica que el último dígito de la cédula sea válido
        int[] d = new int[10];

        //Asignamos el string a un array
        for (int i = 0; i < d.length; i++) {
            d[i] = Integer.parseInt(cedula.charAt(i) + "");
        }

        int imp = 0;
        int par = 0;

        //sumamos los duplos de posición impar
        for (int i = 0; i < d.length; i += 2) {
            d[i] = ((d[i] * 2) > 9) ? ((d[i] * 2) - 9) : (d[i] * 2);
            imp += d[i];
        }

        //sumamos los digitos de posición par
        for (int i = 1; i < (d.length - 1); i += 2) {
            par += d[i];
        }

        //Sumamos los dos resultados
        int suma = imp + par;

        //Restamos de la decena superior
        int d10 = Integer.parseInt(String.valueOf(suma + 10).substring(0, 1) +
                "0") - suma;

        //Si es diez el décimo dígito es cero
        d10 = (d10 == 10) ? 0 : d10;

        //si el décimo dígito calculado es igual al digitado la cédula es correcta
        return d10 == d[9];
    }



}
