package br.edu.ifam.buscamed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

    public void mudarRemedios(View v){
        Intent i = new Intent(this, Remedios.class);
        startActivity(i);
    }

    public void mudarFarmacias(View v){
        Intent i = new Intent(this, Farmacias.class);
        startActivity(i);
    }

    public void buscaRemedios(View v){
        Intent i = new Intent(this, BuscaDeRemedio.class);
        startActivity(i);
    }

    public void buscaFarmacias(View v){
        Intent i = new Intent(this, BuscaDeFarmacia.class);
        startActivity(i);
    }


}