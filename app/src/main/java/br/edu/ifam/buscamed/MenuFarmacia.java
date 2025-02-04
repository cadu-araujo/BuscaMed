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

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.model.Farmacia;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuFarmacia extends AppCompatActivity {
    private Long loginID;

    private TextView nome;

    private FarmaciaAPI farmaciaAPI;

    private ImageButton ibhome;

    private Farmacia farmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_farmacia);

        nome = findViewById(R.id.tvNomeFarmacia);

        ibhome = findViewById(R.id.ibHomeFarmacia);

        acessarAPI();

        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        loginID = preferences.getLong("ID", 0);

        getFarmacia(loginID);

    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        farmaciaAPI = retrofit.create(FarmaciaAPI.class);
    }

    public void mudarFarmacia(View v){
        Intent i = new Intent(this, CadastroFarmacia.class);
        i.putExtra("id", loginID);
        startActivity(i);
    }

    public void cadastrarRemedio(View v){
        Intent i = new Intent(this, Remedios.class);
        i.putExtra("idFarmacia", farmacia.getId());
        i.putExtra("nome", farmacia.getNome());
        startActivity(i);
    }

    public void home(View v){
        new AlertDialog.Builder(this).setTitle("Confirmação").setMessage("Tem certeza que deseja voltar ao Login?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(this, "De volta ao login!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Não", (dialog, which) -> {
                }).show();
    }


    private void getFarmacia(Long id) {
        Call<FarmaciaDTO> call = farmaciaAPI.getFarmaciaLogin(id);
        call.enqueue(new Callback<FarmaciaDTO>() {
            @Override
            public void onResponse(Call<FarmaciaDTO> call, Response<FarmaciaDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    farmacia = response.body().getFarmacia();
                    nome.setText("Bem vindo(a),  "+farmacia.getNome()+"!");
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
