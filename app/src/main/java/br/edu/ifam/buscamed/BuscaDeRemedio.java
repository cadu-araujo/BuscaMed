package br.edu.ifam.buscamed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifam.buscamed.model.Remedio;
import br.edu.ifam.buscamed.recycler.RemedioAdapter;
import br.edu.ifam.buscamed.repository.RemedioDAO;

public class BuscaDeRemedio extends AppCompatActivity {

    private RecyclerView recyclerBusca;
    private RemedioAdapter remedioAdapter;
    private RemedioDAO remedioDAO;
    private EditText buscaRemedio;
    private ImageButton svBusca;
    private Button btBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_busca_de_remedio);

        btBuscar = findViewById(R.id.btPesquisar);
        svBusca = findViewById(R.id.ibSearch);
        buscaRemedio = findViewById(R.id.etBuscaRemedio);
        remedioDAO = new RemedioDAO(this);
        recyclerBusca = findViewById(R.id.recyclerBusca);
        recyclerBusca.setLayoutManager(new LinearLayoutManager(this));
    }



    public void buscar(View v){
        remedioAdapter = new RemedioAdapter( this, remedioDAO.buscaNome(buscaRemedio.getText().toString()));
        recyclerBusca.setAdapter(remedioAdapter);
    }

    public void redirecionar(View v){
        buscaRemedio.requestFocus();
    }

}