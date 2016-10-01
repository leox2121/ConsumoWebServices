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

public class Registro extends AppCompatActivity {
    public static final int NUMERO_DE_PROVINCIAS = 24;//22;
    EditText tnombres, tapellido, tcedula, tcelular, tdireccion, tdescripciondeuda, tcuotadeuda, tvalordeuda;
    RequestQueue colapeticion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        tnombres = ((EditText) findViewById(R.id.txtnombres));
        tapellido = ((EditText) findViewById(R.id.txtapellidos));
        tcedula = ((EditText) findViewById(R.id.txtcedula));
        tcelular = ((EditText) findViewById(R.id.txtcelular));
        tdireccion = ((EditText) findViewById(R.id.txtdireccion));
        tdescripciondeuda = ((EditText) findViewById(R.id.txtdescripciondeuda));
        tcuotadeuda = ((EditText) findViewById(R.id.txtncuotadeuda));
        tvalordeuda = ((EditText) findViewById(R.id.txtvalordeuda));
        Button oswaldoingresa = (Button) findViewById(R.id.oswaldoregistrar);
        oswaldoingresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String patron = "^[0]{1}[9]{1}[1-9]{2}[0-9]{6}$";


                Pattern p = Pattern.compile(patron);
                //Pattern p = Pattern.compile(patron);

//Busca patron en el texto
                Matcher matcher = p.matcher(tcelular.getText());

                if (matcher.matches()) {

                    if (CedulaValida(tcedula.getText().toString())==true)
                    {

                        GuardarDatos();

                    }
                    else
                    {
                        Toast.makeText(Registro.this, "Cedula Incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Registro.this, "celular malo", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void GuardarDatos() {
        colapeticion = Volley.newRequestQueue(this);

        try {
            JSONObject jdeudor = new JSONObject();
            jdeudor.put("apellidos", tapellido.getText().toString());
            jdeudor.put("nombres", tnombres.getText().toString());
            jdeudor.put("cedula", tcedula.getText().toString());
            jdeudor.put("celular", tcelular.getText().toString());
            jdeudor.put("direccion", tdireccion.getText().toString());
            jdeudor.put("deuda", tdescripciondeuda.getText().toString());
            jdeudor.put("numerocuotas", tcuotadeuda.getText().toString());
            jdeudor.put("valordeuda", tvalordeuda.getText().toString());
            String URL = "http://tareawebservicerest.azurewebsites.net/rest/deudor/create";
            JsonObjectRequest pDeudor = new JsonObjectRequest(Request.Method.POST, URL, jdeudor, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.e("Datos guardados correctamente", jsonObject.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.e("Error al Ingresar datos", volleyError.getMessage());

                }

            });colapeticion.add(pDeudor);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(Registro.this, "Deudor GUardado Correctamente", Toast.LENGTH_SHORT).show();
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
