package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import br.edu.ifam.buscamed.dto.UsuarioDTO;
import br.edu.ifam.buscamed.interfaces.UsuarioAPI;
import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditaUsuario extends AppCompatActivity {

    private EditText nome, endereco, telefone, email;
    private UsuarioAPI usuarioAPI;
    private Long userId, loginId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edita_usuario);

        acessarAPI();

        email = findViewById(R.id.etUserEmail);
        telefone = findViewById(R.id.etUserFone);
        nome = findViewById(R.id.etUserNome);
        endereco = findViewById(R.id.etUserEndereco);

        SharedPreferences preferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
        loginId = preferences.getLong("ID", 0);

        userId = getIntent().getLongExtra("id", 0);
        getUsuario(loginId);
    }

    private void setUsuario(Usuario usuario){
        telefone.setText(usuario.getTelefone());
        nome.setText(usuario.getNome());
        endereco.setText(usuario.getEndereco());
        email.setText(usuario.getEmail());
    }

    private Usuario getUsuario(){
        Usuario user = new Usuario();
        user.setEndereco(endereco.getText().toString());
        user.setNome(nome.getText().toString());
        user.setTelefone(telefone.getText().toString());
        user.setLogin(loginId);
        user.setEmail(email.getText().toString());
        return  user;
    }

    private void acessarAPI(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        usuarioAPI = retrofit.create(UsuarioAPI.class);
    }

    private void getUsuario(Long id) {
        Call<UsuarioDTO> call = usuarioAPI.getLogin(id);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    setUsuario(response.body().getUsuario());
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

    public void limpar(View V){
        setUsuario(new Usuario());
    }


    public void deletarUsuario(View V){
        Call<UsuarioDTO> call = usuarioAPI.deleteUsuario(userId);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Usuário(a) deletado!", Toast.LENGTH_SHORT).show();
                    finish();
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

    public void alterarUsuario(View V){
        Call<UsuarioDTO> call = usuarioAPI.updateUsuario(userId, new UsuarioDTO(getUsuario()));

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Usuário(a) alterado!", Toast.LENGTH_SHORT).show();
                    finish();
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

}