package br.edu.ifam.buscamed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifam.buscamed.recycler.FarmaciaAdapter;
import br.edu.ifam.buscamed.recycler.RemedioAdapter;
import br.edu.ifam.buscamed.repository.FarmaciaDAO;

public class Farmacias extends AppCompatActivity {

    private RecyclerView recyclerViewFarmacia;
    private FarmaciaAdapter farmaciaAdapter;
    private FarmaciaDAO farmaciaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_farmacias);

        farmaciaDAO = new FarmaciaDAO(this);
        recyclerViewFarmacia = findViewById(R.id.recyclerViewFarmacia);
        recyclerViewFarmacia.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        farmaciaAdapter = new FarmaciaAdapter(this,  farmaciaDAO.getAll());
        recyclerViewFarmacia.setAdapter(farmaciaAdapter);
    }

    public void cadastro(View v){
        Intent i = new Intent(this, CadastroFarmacia.class);
        startActivity(i);
    }
}