package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import br.edu.ifam.buscamed.dto.UsuarioDTO;
import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.interfaces.UsuarioAPI;
import br.edu.ifam.buscamed.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MenuUsuario extends AppCompatActivity {
    private ImageButton ibhome;
    private Long loginID;

    private UsuarioAPI usuarioAPI;
    private TextView nome;
    private Usuario user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_usuario);
        ibhome = findViewById(R.id.ibHomeUser);

        acessarAPI();

        nome = findViewById(R.id.tvNomeUser);

        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        loginID = preferences.getLong("ID", 0);

        getUsuario(loginID);

    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usuarioAPI = retrofit.create(UsuarioAPI.class);
    }

    public void buscaRemedios(View v){
        Intent i = new Intent(this, BuscaDeRemedio.class);
        startActivity(i);
    }

    public void buscaFarmacias(View v){
        Intent i = new Intent(this, BuscaDeFarmacia.class);
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

    private void getUsuario(Long id) {
        Call<UsuarioDTO> call = usuarioAPI.getLogin(id);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    user = response.body().getUsuario();

                    SharedPreferences userI = getSharedPreferences("user", MODE_PRIVATE);
                    SharedPreferences.Editor editor = userI.edit();
                    editor.putLong("IDUser", user.getId());
                    editor.apply();

                    nome.setText("Bem vindo(a),  "+user.getNome()+"!");
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }


    public void mudarUsuario(View v){
        Intent i = new Intent(this, EditaUsuario.class);
        i.putExtra("id", user.getId());
        startActivity(i);
    }

    public void meusPedidos(View v){
        Intent i = new Intent(this, Pedidos.class);
        i.putExtra("id", user.getId());
        startActivity(i);
    }

}
