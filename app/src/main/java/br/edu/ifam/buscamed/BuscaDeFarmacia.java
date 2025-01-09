package br.edu.ifam.buscamed;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifam.buscamed.recycler.FarmaciaAdapter;
import br.edu.ifam.buscamed.recycler.RemedioAdapter;
import br.edu.ifam.buscamed.repository.FarmaciaDAO;
import br.edu.ifam.buscamed.repository.RemedioDAO;

public class BuscaDeFarmacia extends AppCompatActivity {

    private RecyclerView recyclerBusca;
    private FarmaciaAdapter farmaciaAdapter;
    private FarmaciaDAO farmaciaDAO;
    private EditText buscaFarmacia;
    private ImageButton svBusca;
    private Button btBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_busca_de_farmacia);

        btBuscar = findViewById(R.id.btPesquisarFarmacia);
        svBusca = findViewById(R.id.ibSearchFarmacia);
        buscaFarmacia= findViewById(R.id.etBuscaFarmacia);
        farmaciaDAO = new FarmaciaDAO(this);
        recyclerBusca = findViewById(R.id.recyclerBuscaFarmacia);
        recyclerBusca.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();

        if(intent.hasExtra("farmacia")){
            String farmacia = intent.getStringExtra("farmacia");
            farmaciaAdapter = new FarmaciaAdapter( this, farmaciaDAO.buscaNome(farmacia));
            recyclerBusca.setAdapter(farmaciaAdapter);
        }
    }

    public void buscar(View v){
        farmaciaAdapter = new FarmaciaAdapter( this, farmaciaDAO.buscaNome(buscaFarmacia.getText().toString()));
        recyclerBusca.setAdapter(farmaciaAdapter);
    }

    public void redirecionar(View v){
        buscaFarmacia.requestFocus();
    }


}