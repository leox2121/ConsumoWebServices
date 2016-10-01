package com.movil.proaniostalin_pilayricky.consumowebservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {
    public static int segundos=8;
    public static int milisegundos=segundos*1000;
    private ProgressBar barracargando;
    public static int delay=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        barracargando=(ProgressBar)findViewById(R.id.barracargando);
        barracargando.setMax(MaximoProgress());
        EmpezarAnimacion();

    }
    public void EmpezarAnimacion() {
        new CountDownTimer(milisegundos, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                barracargando.setProgress(EstablecerProgreso(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                Intent formprincipal = new Intent(SplashScreen.this,ActivityPrincipal.class);
                startActivity(formprincipal);
                finish();
            }
        }.start();
    }


    public static int EstablecerProgreso(long miliseconds)
    {
        return (int)(milisegundos-miliseconds)/1000;
    }
    public int MaximoProgress()
    {
        return (segundos-delay);
    }
}
