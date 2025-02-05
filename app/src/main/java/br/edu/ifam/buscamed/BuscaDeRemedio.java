package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SearchView;
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

public class BuscaDeRemedio extends AppCompatActivity {

    private RecyclerView recyclerBusca;
    private RemedioAdapter remedioAdapter;
    private RemedioDAO remedioDAO;
    private EditText buscaRemedio;
    private ImageButton svBusca;
    private Button btBuscar;

    private Context context;

    private RemedioAPI remedioAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_busca_de_remedio);

        context = getApplicationContext();
        btBuscar = findViewById(R.id.btPesquisar);
        svBusca = findViewById(R.id.ibSearch);
        buscaRemedio = findViewById(R.id.etBuscaRemedio);
        remedioDAO = new RemedioDAO(this);
        recyclerBusca = findViewById(R.id.recyclerBusca);
        recyclerBusca.setLayoutManager(new LinearLayoutManager(this));


        acessarAPI();
    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remedioAPI = retrofit.create(RemedioAPI.class);
    }

    public void buscar(View v){
        Call<List<RemedioDTO>> call = remedioAPI.getRemedioByNome(buscaRemedio.getText().toString());


        call.enqueue(new Callback<List<RemedioDTO>>() {
            @Override
            public void onResponse(Call<List<RemedioDTO>> call, Response<List<RemedioDTO>> response) {
                List<Remedio> remedioList = new ArrayList<>();
                if(response.isSuccessful() && response.body()!=null){
                    List<RemedioDTO> remedioDTOList = response.body();

                    for (RemedioDTO remedioDTO : remedioDTOList) {
                        remedioList.add(remedioDTO.getRemedio());
                    }

                    remedioAdapter = new RemedioAdapter(context, remedioList);
                    recyclerBusca.setAdapter(remedioAdapter);
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RemedioDTO>> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void redirecionar(View v){
        buscaRemedio.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(buscaRemedio, InputMethodManager.SHOW_IMPLICIT);
        }
    }

}