package br.edu.ifam.buscamed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifam.buscamed.recycler.RemedioAdapter;
import br.edu.ifam.buscamed.repository.RemedioDAO;

public class Remedios extends AppCompatActivity {
    private RecyclerView recyclerViewRemedio;
    private RemedioAdapter remedioAdapter;
    private RemedioDAO remedioDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remedios);

        remedioDAO = new RemedioDAO(this);
        recyclerViewRemedio = findViewById(R.id.recyclerViewRemedios);
        recyclerViewRemedio.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        remedioAdapter = new RemedioAdapter( this, remedioDAO.getRemedio());
        recyclerViewRemedio.setAdapter(remedioAdapter);
    }

    public void cadastro(View v){
        Intent i = new Intent(this, CadastroRemedio.class);
        startActivity(i);
    }

}