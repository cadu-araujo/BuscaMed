package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.content.SharedPreferences;
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
import br.edu.ifam.buscamed.dto.RemedioDTO;
import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.interfaces.RemedioAPI;
import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.model.Remedio;
import br.edu.ifam.buscamed.recycler.FarmaciaAdapter;
import br.edu.ifam.buscamed.recycler.RemedioAdapter;
import br.edu.ifam.buscamed.repository.RemedioDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Remedios extends AppCompatActivity {
    private RecyclerView recyclerViewRemedio;
    private RemedioAdapter remedioAdapter;
    private ProgressBar pbRemedios;
    private RemedioAPI remedioAPI;

    private FarmaciaAPI farmaciaAPI;
    private Long farmaciaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remedios);

        recyclerViewRemedio = findViewById(R.id.recyclerViewRemedio);
        recyclerViewRemedio.setLayoutManager(new LinearLayoutManager(this));
        pbRemedios = findViewById(R.id.pbRemedios);
        acessarAPI();

        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        farmaciaID = getIntent().getLongExtra("idFarmacia", 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getRemedios();
    }

    public void cadastro(View v){
        Intent i = new Intent(this, CadastroRemedio.class);
        i.putExtra("nomeFarmacia", getIntent().getStringExtra("nome"));
        startActivity(i);
    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remedioAPI = retrofit.create(RemedioAPI.class);
    }

    private void getRemedios() {
        Call<List<RemedioDTO>> call = remedioAPI.getRemedioByFarmacia(farmaciaID);
        pbRemedios.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<RemedioDTO>>() {
            @Override
            public void onResponse(Call<List<RemedioDTO>> call, Response<List<RemedioDTO>> response) {
                List<Remedio> remedios = new ArrayList<>();
                if(response.isSuccessful() && response.body()!=null){
                    List<RemedioDTO> remediosDTO = response.body();
                    for(RemedioDTO remedioDTO:remediosDTO)
                        remedios.add(remedioDTO.getRemedio());
                }else{
                    if(response.code() == 204){
                        Toast.makeText(getApplicationContext(), "Sem remédios Cadastrados", Toast.LENGTH_SHORT).show();
                    }else{
                        String codigo = "Erro: "+response.code();
                        Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                    }
                }
                remedioAdapter = new RemedioAdapter(getApplicationContext(), remedios);
                recyclerViewRemedio.setAdapter(remedioAdapter);
                pbRemedios.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<List<RemedioDTO>> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
                pbRemedios.setVisibility(View.INVISIBLE);
            }
        });
    }


}