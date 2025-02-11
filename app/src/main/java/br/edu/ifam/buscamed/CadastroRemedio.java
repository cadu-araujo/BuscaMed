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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Locale;

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import br.edu.ifam.buscamed.dto.RemedioDTO;
import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.interfaces.RemedioAPI;
import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.model.Remedio;
import br.edu.ifam.buscamed.repository.FarmaciaDAO;
import br.edu.ifam.buscamed.repository.RemedioDAO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroRemedio extends AppCompatActivity {

    private Long id;
    private ImageButton ibSaveRemedio,ibDeleteRemedio, clear, comprar;

    private TextView titulo;
    private EditText etRemedioNome, etRemedioMarca, etRemedioQuantidade, etRemedioDescricao, etRemedioPreco, etRemedioFarmacia;
    private String nome;
    private long userID;

    private RemedioAPI remedioAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_remedio);


        acessarAPI();
        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        Intent intent = getIntent();
        id = intent.getLongExtra("id", 0);
        getRemedio(id);

        titulo = findViewById(R.id.tvTituloRemedio);
        clear = findViewById(R.id.ibLimparRemedio);
        ibSaveRemedio = findViewById(R.id.ibSaveRemedio);
        ibDeleteRemedio = findViewById(R.id.ibExcluirRemedio);
        etRemedioDescricao = findViewById(R.id.etRemedioDescricao);
        etRemedioNome = findViewById(R.id.etRemedioNome);
        etRemedioPreco = findViewById(R.id.etRemedioPreco);
        etRemedioMarca = findViewById(R.id.etRemedioMarca);
        etRemedioQuantidade = findViewById(R.id.etRemedioQuantidade);
        etRemedioFarmacia = findViewById(R.id.etRemedioFarmacia);
        comprar = findViewById(R.id.ibPEDIR);

        comprar.setVisibility(View.INVISIBLE);
        etRemedioFarmacia.setText(nome);



        if(preferences.getString("userType", "").equals("user")){
            ibSaveRemedio.setVisibility(View.INVISIBLE);
            ibDeleteRemedio.setVisibility(View.INVISIBLE);
            clear.setVisibility(View.INVISIBLE);
            comprar.setVisibility(View.VISIBLE);
            titulo.setText("Detalhes do remédio");
        } else if(!preferences.getString("userType", "").equals("user") && intent.hasExtra("nomeFarmacia")){
            ibSaveRemedio.setVisibility(View.VISIBLE);
            etRemedioFarmacia.setText(intent.getStringExtra("nomeFarmacia"));
            ibSaveRemedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inserirRemedio(new RemedioDTO(getEditRemedio()));
                }
            });
        }else{
            ibDeleteRemedio.setVisibility(View.VISIBLE);
            ibSaveRemedio.setVisibility(View.VISIBLE);
            clear.setVisibility(View.INVISIBLE);
            titulo.setText("Edição de remédio");
            ibSaveRemedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    atualizarRemedio(id, new RemedioDTO(getEditRemedio()));
                }
            });
        }


    }

    private void acessarAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remedioAPI = retrofit.create(RemedioAPI.class);
    }

    private Remedio getEditRemedio(){
        Remedio remedio = new Remedio();
        remedio.setNome(etRemedioNome.getText().toString());
        remedio.setDescricao(etRemedioDescricao.getText().toString());
        remedio.setMarca(etRemedioMarca.getText().toString());
        try {
            remedio.setValor(Float.parseFloat(etRemedioPreco.getText().toString().replace(",", ".")));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }

        remedio.setQuantidade(Integer.parseInt(etRemedioQuantidade.getText().toString()));
        remedio.setFarmacia(etRemedioFarmacia.getText().toString());
        return remedio;
    }

    private void setRemedio(Remedio remedio){
        String preco = String.format(Locale.getDefault(), "%.2f", remedio.getValor());
        String quantidade = String.format(Locale.getDefault(), "%d", remedio.getQuantidade());
        etRemedioNome.setText(remedio.getNome());
        etRemedioDescricao.setText(remedio.getDescricao());
        etRemedioPreco.setText(preco);
        etRemedioQuantidade.setText(quantidade);
        etRemedioMarca.setText(remedio.getMarca());
        etRemedioFarmacia.setText(nome);
    }

    public void ibClearRemedioOnClick(View v){
        setRemedio(new Remedio());
    }

    public void ibDeleteRemedioOnClick(View v){
        new AlertDialog.Builder(this).setTitle("Confirmação").setMessage("Tem certeza que deseja deletar este remédio?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    deleteRemedio(id);
                    finish();
                    Toast.makeText(this, "Ação confirmada!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Não", (dialog, which) -> {
                }).show();
    }

    public void acharFarmacia(View v){
        Intent intent = new Intent(this, BuscaDeFarmacia.class);
        intent.putExtra("farmacia", etRemedioFarmacia.getText().toString());
        startActivity(intent);
    }

    private void getRemedio(Long id) {
        Call<RemedioDTO> call = remedioAPI.getRemedio(id);

        call.enqueue(new Callback<RemedioDTO>() {
            @Override
            public void onResponse(Call<RemedioDTO> call, Response<RemedioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    RemedioDTO remedioDTO = response.body();
                    nome = remedioDTO.getFarmacia();
                    setRemedio(remedioDTO .getRemedio());
                }
            }

            @Override
            public void onFailure(Call<RemedioDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteRemedio(Long id) {
        Call<RemedioDTO> call = remedioAPI.deleteRemedio(id);

        call.enqueue(new Callback<RemedioDTO>() {
            @Override
            public void onResponse(Call<RemedioDTO> call, Response<RemedioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Remédio excluído com sucesso", Toast.LENGTH_SHORT).show();
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemedioDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inserirRemedio(RemedioDTO remedioDTO){
        Call<RemedioDTO> call = remedioAPI.setRemedio(remedioDTO);
        call.enqueue(new Callback<RemedioDTO>() {
            @Override
            public void onResponse(Call<RemedioDTO> call, Response<RemedioDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(getApplicationContext(), "Remédio inserido com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String codigo = "Erro: " + response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemedioDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void atualizarRemedio(Long id, RemedioDTO remedioDTO) {
        Call<RemedioDTO> call = remedioAPI.updateRemedio(id, remedioDTO);

        call.enqueue(new Callback<RemedioDTO>() {
            @Override
            public void onResponse(Call<RemedioDTO> call, Response<RemedioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Remédio atualizado com sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RemedioDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void realizarPedido(View v){
        Intent pedir = new Intent(getApplicationContext(), CadastroPedido.class);
        pedir.putExtra("idRemedio", id);
        startActivity(pedir);
    }

}