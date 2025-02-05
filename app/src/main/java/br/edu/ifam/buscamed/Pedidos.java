package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ifam.buscamed.dto.VendaOutputDTO;
import br.edu.ifam.buscamed.interfaces.RemedioAPI;
import br.edu.ifam.buscamed.interfaces.UsuarioAPI;
import br.edu.ifam.buscamed.interfaces.VendaAPI;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pedidos);
        recyclerView = findViewById(R.id.recyclerPedidos);
        titulo = findViewById(R.id.tvTituloPedidos);

        acessarAPI();

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
        Call<VendaOutputDTO> call = vendaAPI.getVendaFarmacia(id);

        call.enqueue(new Callback<VendaOutputDTO>() {
            @Override
            public void onResponse(Call<VendaOutputDTO> call, Response<VendaOutputDTO> response) {

            }

            @Override
            public void onFailure(Call<VendaOutputDTO> call, Throwable t) {

            }
        });
    }

    private void getVendaUser(Long id){
        Call<VendaOutputDTO> call = vendaAPI.getVendaUser(id);

        call.enqueue(new Callback<VendaOutputDTO>() {
            @Override
            public void onResponse(Call<VendaOutputDTO> call, Response<VendaOutputDTO> response) {

            }

            @Override
            public void onFailure(Call<VendaOutputDTO> call, Throwable t) {

            }
        });
    }

}