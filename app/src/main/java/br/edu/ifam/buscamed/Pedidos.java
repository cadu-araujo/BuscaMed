package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifam.buscamed.dto.RemedioDTO;
import br.edu.ifam.buscamed.dto.VendaOutputDTO;
import br.edu.ifam.buscamed.interfaces.RemedioAPI;
import br.edu.ifam.buscamed.interfaces.UsuarioAPI;
import br.edu.ifam.buscamed.interfaces.VendaAPI;
import br.edu.ifam.buscamed.recycler.RemedioAdapter;
import br.edu.ifam.buscamed.recycler.VendaOutputDTOAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Pedidos extends AppCompatActivity {

    private RemedioAPI remedioAPI;
    private UsuarioAPI usuarioAPI;
    private VendaAPI vendaAPI;
    private TextView titulo;
    private RecyclerView recyclerView;
    private Long id;
    private VendaOutputDTOAdapter vendaOutputDTOAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedidos);
        recyclerView = findViewById(R.id.recyclerPedidos);
        titulo = findViewById(R.id.tvTituloPedidos);

        acessarAPI();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent i = getIntent();
        id = i.getLongExtra("id", 0);
        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        if(preferences.getString("userType", "").equals("user")){
            getVendaUser(id);
        }else{
            getVendaFarmacia(id);
        }
    }

    private void acessarAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        vendaAPI = retrofit.create(VendaAPI.class);
    }

    private void getVendaFarmacia(Long id){
        Call<List<VendaOutputDTO>> call = vendaAPI.getVendaFarmacia(id);

        call.enqueue(new Callback<List<VendaOutputDTO>>() {
            @Override
            public void onResponse(Call<List<VendaOutputDTO>> call, Response<List<VendaOutputDTO>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    vendaOutputDTOAdapter = new VendaOutputDTOAdapter(getApplicationContext(), response.body());
                    recyclerView.setAdapter(vendaOutputDTOAdapter);
                }else{
                    if(response.code() == 204){
                        Toast.makeText(getApplicationContext(), "Sem pedidos cadastrados", Toast.LENGTH_SHORT).show();
                    }else{
                        String codigo = "Erro: "+response.code();
                        Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VendaOutputDTO>> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getVendaUser(Long id){
        Call<List<VendaOutputDTO>> call = vendaAPI.getVendaUser(id);

        call.enqueue(new Callback<List<VendaOutputDTO>>() {
            @Override
            public void onResponse(Call<List<VendaOutputDTO>> call, Response<List<VendaOutputDTO>> response) {
                if(response.isSuccessful() && response.body()!=null){
                    vendaOutputDTOAdapter = new VendaOutputDTOAdapter(getApplicationContext(), response.body());
                    recyclerView.setAdapter(vendaOutputDTOAdapter);
                }else{
                    if(response.code() == 204){
                        Toast.makeText(getApplicationContext(), "Sem pedidos realizados", Toast.LENGTH_SHORT).show();
                    }else{
                        String codigo = "Erro: "+response.code();
                        Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VendaOutputDTO>> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

}