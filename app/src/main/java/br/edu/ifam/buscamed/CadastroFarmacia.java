package br.edu.ifam.buscamed;


import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Locale;

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.interfaces.LoginAPI;
import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.recycler.FarmaciaAdapter;
import br.edu.ifam.buscamed.repository.FarmaciaDAO;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroFarmacia extends AppCompatActivity {

    private EditText etFarmaciaNome, etFarmaciaCNPJ, etFarmaciaEndereco;
    private long id, idFarmacia;
    private FarmaciaDAO farmaciaDAO;
    private ImageButton ibSaveFarmacia, ibDeleteFarmacia;
    private FarmaciaAPI farmaciaAPI;
    private TextView titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_farmacia);

        acessarAPI();
        titulo = findViewById(R.id.tvCadastroFarmacia);
        farmaciaDAO = new FarmaciaDAO(this);
        etFarmaciaNome = findViewById(R.id.etFarmaciaNome);
        etFarmaciaCNPJ = findViewById(R.id.etFarmaciaCNPJ);
        etFarmaciaEndereco = findViewById(R.id.etFarmaciaEndereco);
        ibSaveFarmacia = findViewById(R.id.ibSaveFarmacia);
        ibDeleteFarmacia = findViewById(R.id.ibExcluirFarmacia);

        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            titulo.setText("Edição de Farmácia");
            id = intent.getLongExtra("id", 0);
            getFarmacia(id);
        }

    }

    private Farmacia getFarmaciaV(){
        Farmacia farmacia = new Farmacia();
        farmacia.setNome(etFarmaciaNome.getText().toString());
        farmacia.setCnpj(etFarmaciaCNPJ.getText().toString());
        farmacia.setEndereco(etFarmaciaEndereco.getText().toString());
        return farmacia;
    }

    private void setFarmacia(Farmacia farmacia){
        etFarmaciaNome.setText(farmacia.getNome());
        etFarmaciaCNPJ.setText(farmacia.getCnpj());
        etFarmaciaEndereco.setText(farmacia.getEndereco());
    }

    public void ibDeleteFarmaciaOnClick(View v){
        new AlertDialog.Builder(this).setTitle("Confirmação").setMessage("Tem certeza que deseja deletar esta farmácia?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    deleteFarmacia(id);
                    finish();
                })
                .setNegativeButton("Não", (dialog, which) -> {
                }).show();
    }

    public void ibUpdateFarmaciaOnClick(View v){
        new AlertDialog.Builder(this).setTitle("Confirmação").setMessage("Tem certeza que deseja alterar esta farmácia?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    atualizarFarmacia(id, new FarmaciaDTO(getFarmaciaV()));
                    finish();
                })
                .setNegativeButton("Não", (dialog, which) -> {
                }).show();
    }

    public void ibClearFarmaciaOnClick(View v){
        setFarmacia(new Farmacia());
    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        farmaciaAPI = retrofit.create(FarmaciaAPI.class);
    }

    private void getFarmacia(Long id) {
        Call<FarmaciaDTO> call = farmaciaAPI.getFarmaciaLogin(id);
        call.enqueue(new Callback<FarmaciaDTO>() {
            @Override
            public void onResponse(Call<FarmaciaDTO> call, Response<FarmaciaDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                   setFarmacia(response.body().getFarmacia());
                   idFarmacia = response.body().getFarmacia().getId();
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmaciaDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }


    private void deleteFarmacia(Long id) {

        Call<FarmaciaDTO> call = farmaciaAPI.deleteFarmacia(idFarmacia);

        call.enqueue(new Callback<FarmaciaDTO>() {
            @Override
            public void onResponse(Call<FarmaciaDTO> call, Response<FarmaciaDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Farmacia excluída com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    String codigo = "Erro: "+response.code()+" Descrição: "+response.message();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmaciaDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }


   private void atualizarFarmacia(Long id, FarmaciaDTO farmaciaDTO){
        Call<FarmaciaDTO> farmaciaDTOCall = farmaciaAPI.updateFarmacia(idFarmacia, new FarmaciaDTO(getFarmaciaV()));
        farmaciaDTOCall.enqueue(new Callback<FarmaciaDTO>() {
            @Override
            public void onResponse(Call<FarmaciaDTO> call, Response<FarmaciaDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Farmacia atualizada com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FarmaciaDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }


}