package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.annotation.SuppressLint;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.recycler.FarmaciaAdapter;
import br.edu.ifam.buscamed.recycler.RemedioAdapter;
import br.edu.ifam.buscamed.repository.FarmaciaDAO;
import br.edu.ifam.buscamed.repository.RemedioDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BuscaDeFarmacia extends AppCompatActivity {

    private RecyclerView recyclerBusca;
    private FarmaciaAdapter farmaciaAdapter;
    private FarmaciaDAO farmaciaDAO;
    private EditText buscaFarmacia;
    private ImageButton svBusca;
    private Button btBuscar;
    private FarmaciaAPI farmaciaAPI;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_busca_de_farmacia);

        context = this;
        btBuscar = findViewById(R.id.btPesquisarFarmacia);
        svBusca = findViewById(R.id.ibSearchFarmacia);
        buscaFarmacia= findViewById(R.id.etBuscaFarmacia);
        farmaciaDAO = new FarmaciaDAO(this);
        recyclerBusca = findViewById(R.id.recyclerBuscaFarmacia);


        acessarAPI();

        Intent intent = getIntent();

        if(intent.hasExtra("farmacia")){
            String farmacia = intent.getStringExtra("farmacia");
            farmaciaAdapter = new FarmaciaAdapter( this, farmaciaDAO.buscaNome(farmacia));
            recyclerBusca.setAdapter(farmaciaAdapter);
        }

    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        farmaciaAPI = retrofit.create(FarmaciaAPI.class);
    }

    public void buscar(View v){
        Call<List<FarmaciaDTO>> call = farmaciaAPI.getFarmaciaByNome(buscaFarmacia.getText().toString());

        call.enqueue(new Callback<List<FarmaciaDTO>>() {
            @Override
            public void onResponse(Call<List<FarmaciaDTO>> call, Response<List<FarmaciaDTO>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    List<FarmaciaDTO> farmaciaDTOList = response.body();
                    List<Farmacia> farmaciaList = new ArrayList<>();

                    for (FarmaciaDTO farmaciaDTO : farmaciaDTOList) {
                        farmaciaList.add(farmaciaDTO.getFarmacia());
                    }
                    farmaciaAdapter = new FarmaciaAdapter( getApplicationContext(), farmaciaList);
                    recyclerBusca.setAdapter(farmaciaAdapter);
                    recyclerBusca.setLayoutManager(new LinearLayoutManager(context));
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<FarmaciaDTO>> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void redirecionar(View v){
        buscaFarmacia.requestFocus();
    }

}