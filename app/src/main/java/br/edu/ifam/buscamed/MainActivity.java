package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.edu.ifam.buscamed.dto.LoginDTO;
import br.edu.ifam.buscamed.interfaces.LoginAPI;
import br.edu.ifam.buscamed.model.Login;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText etUsuarioEmail;
    private EditText etUsuarioSenha;
    private LoginAPI loginAPI;

    public String tipo;

    private Button ibLogar;

    private Button ibCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ibLogar = findViewById(R.id.ibLogar);
        ibCadastro = findViewById(R.id.ibCadastro);
        etUsuarioEmail = findViewById(R.id.etUsuarioNome);
        etUsuarioSenha = findViewById(R.id.etUsuarioSenha);

        ibCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cadastro(v);
            }
        });

        ibLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });

        acessarAPI();
    }

    public void cadastro(View v){
        Intent i = new Intent(this, Cadastro.class);
        startActivity(i);
    }

    public void acessarAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginAPI = retrofit.create(LoginAPI.class);
    }

    public void login(View v) {
        Login login = new Login();

        login.setEmail(etUsuarioEmail.getText().toString());
        login.setSenha(etUsuarioSenha.getText().toString());

        Call<LoginDTO> loginDTOCall = loginAPI.autenticar(new LoginDTO(login));
        loginDTOCall.enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                if(response.isSuccessful() && response.body()!=null){

                    Login log = response.body().getLogin();

                    Toast.makeText(getApplicationContext(), "Autenticado!", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putString("userType", log.getTipo());
                    editor.putLong("ID", log.getId());
                    editor.putLong("farmacia", 0);
                    editor.apply();

                    if (log.getTipo().equals("farmacia")){
                        Intent i = new Intent(getApplicationContext(), MenuFarmacia.class);
                        startActivity(i);
                    } else if (log.getTipo().equals("user")){
                        Intent i = new Intent(getApplicationContext(), MenuUsuario.class);
                        startActivity(i);
                    }
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });

    }
}