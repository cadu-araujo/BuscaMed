package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Farmacias extends AppCompatActivity {

    private RecyclerView recyclerViewFarmacia;
    private FarmaciaAdapter farmaciaAdapter;
    private FarmaciaDAO farmaciaDAO;

    private FarmaciaAPI farmaciaAPI;
    private ProgressBar pbFarmacias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_farmacias);

        farmaciaDAO = new FarmaciaDAO(this);
        recyclerViewFarmacia = findViewById(R.id.recyclerViewFarmacia);
        recyclerViewFarmacia.setLayoutManager(new LinearLayoutManager(this));
        pbFarmacias = findViewById(R.id.pbFarmacia);
        acessarAPI();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFarmacias();
    }

    public void cadastro(View v){
        Intent i = new Intent(this, CadastroFarmacia.class);
        startActivity(i);
    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        farmaciaAPI = retrofit.create(FarmaciaAPI.class);
    }

    private void getFarmacias(){
        Call<List<FarmaciaDTO>> call = farmaciaAPI.getFarmacia();
        pbFarmacias.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<FarmaciaDTO>>() {
            @Override
            public void onResponse(Call<List<FarmaciaDTO>> call, Response<List<FarmaciaDTO>> response) {
                List<Farmacia> farmacias = new ArrayList<>();
                if(response.isSuccessful() && response.body()!=null){
                    List<FarmaciaDTO> farmaciasDTO = response.body();
                    for(FarmaciaDTO farmaciaDTO:farmaciasDTO)
                        farmacias.add(farmaciaDTO.getFarmacia());
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
                farmaciaAdapter = new FarmaciaAdapter(getApplicationContext(), farmacias);
                recyclerViewFarmacia.setAdapter(farmaciaAdapter);
                pbFarmacias.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<FarmaciaDTO>> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                System.out.println("Falha de acesso"+t.getMessage());
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
                pbFarmacias.setVisibility(View.INVISIBLE);
            }
        });

    }
}