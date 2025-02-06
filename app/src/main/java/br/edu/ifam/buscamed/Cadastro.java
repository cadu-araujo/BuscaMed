package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifam.buscamed.dto.FarmaciaDTO;
import br.edu.ifam.buscamed.dto.LoginDTO;
import br.edu.ifam.buscamed.dto.UsuarioDTO;
import br.edu.ifam.buscamed.interfaces.FarmaciaAPI;
import br.edu.ifam.buscamed.interfaces.LoginAPI;
import br.edu.ifam.buscamed.interfaces.UsuarioAPI;
import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.model.Login;
import br.edu.ifam.buscamed.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Cadastro extends AppCompatActivity {
    private Long id;
    private ImageButton ibSaveUsuario;
    private ImageButton ibClearUsuario;
    private EditText etUsuarioNome; //id = nomeUsuario
    private EditText etUsuarioSenha; //id = senhaUsuario
    private EditText etUsuarioTelefone; //id = telefoneUsuario
    private EditText etUsarioEmail; //id = emailUsuario
    private EditText etUsarioEndereco; //id = enderecoUsuario

    private EditText etFarmaciaNome;
    private EditText etFarmaciaSenha;
    private EditText etFarmaciaEmail;
    private EditText etFarmaciaEndereco;
    private EditText etFarmaciaCNPJ;
    private EditText etFarmaciaTelefone;

    private LoginAPI loginAPI;
    private UsuarioAPI usuarioAPI;
    private FarmaciaAPI farmaciaAPI;

    private LinearLayout formFarmacia;
    private LinearLayout formUsuario;

    private String tipoSelecionado = "usuario";
    private RadioGroup rgTipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        ibSaveUsuario = findViewById(R.id.ibSaveUsuario);
        ibClearUsuario = findViewById(R.id.ibLimparUsuario);


        formUsuario = findViewById(R.id.form_usuario);
        formFarmacia = findViewById(R.id.form_farmacia);

        etUsuarioNome = findViewById(R.id.nomeUsuario);
        etUsuarioSenha = findViewById(R.id.senhaUsuario);
        etUsuarioTelefone = findViewById(R.id.telefoneUsuario);
        etUsarioEmail = findViewById(R.id.emailUsuario);
        etUsarioEndereco = findViewById(R.id.enderecoUsuario);

        etFarmaciaNome = findViewById(R.id.nomeFarmacia);
        etFarmaciaSenha = findViewById(R.id.senhaFarmacia);
        etFarmaciaEmail = findViewById(R.id.emailFarmacia);
        etFarmaciaEndereco = findViewById(R.id.enderecoFarmacia);
        etFarmaciaCNPJ = findViewById(R.id.cnpjFarmacia);
        etFarmaciaTelefone = findViewById(R.id.telefoneFarmacia);

        rgTipoUsuario = findViewById(R.id.rgTipoUsuario);

        rgTipoUsuario.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbUsuario) {
                tipoSelecionado = "user";
                formFarmacia.setVisibility(View.GONE);
                formUsuario.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rbFarmacia) {
                tipoSelecionado = "farmacia";
                formUsuario.setVisibility(View.GONE);
                formFarmacia.setVisibility(View.VISIBLE);
            }
        });

        acessarAPI();

    }

    public void acessarAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        loginAPI = retrofit.create(LoginAPI.class);
        usuarioAPI = retrofit.create(UsuarioAPI.class);
        farmaciaAPI = retrofit.create(FarmaciaAPI.class);
    }

    private Usuario getViewUsuario(Long loginId){
        Usuario usuario = new Usuario();
        usuario.setNome(etUsuarioNome.getText().toString());
        usuario.setEndereco(etUsarioEndereco.getText().toString());
        usuario.setTelefone(etUsuarioTelefone.getText().toString());
        usuario.setEmail(etUsarioEmail.getText().toString());
        usuario.setLogin(loginId);
        return usuario;
    }

    private Farmacia getViewFarmacia(Long loginId){
        Farmacia farmacia = new Farmacia();
        farmacia.setNome(etFarmaciaNome.getText().toString());
        farmacia.setEndereco(etFarmaciaEndereco.getText().toString());
        farmacia.setEmail(etFarmaciaEmail.getText().toString());
        farmacia.setCnpj(etFarmaciaCNPJ.getText().toString());
        farmacia.setTelefone(etFarmaciaTelefone.getText().toString());
        farmacia.setLogin(loginId);
        return farmacia;
    }

    private Login getViewLogin() {
        Login login = new Login();
        login.setTipo(tipoSelecionado);
        if (tipoSelecionado.equals("user")) {
            login.setEmail(etUsarioEmail.getText().toString());
            login.setSenha(etUsuarioSenha.getText().toString());
        } else if (tipoSelecionado.equals("farmacia")) {
            login.setEmail(etFarmaciaEmail.getText().toString());
            login.setSenha(etFarmaciaSenha.getText().toString());
        }

        return login;
    }


    public void cadastrarLogin(View v) {
        Call<LoginDTO> loginDTOCall = loginAPI.setLogin(new LoginDTO(getViewLogin()));
        loginDTOCall.enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    LoginDTO loginDTO = response.body();
                    if (tipoSelecionado.equals("user")) {
                        cadastrarUsuario(loginDTO.getId());
                    } else if (tipoSelecionado.equals("farmacia")) {
                        cadastrarFarmacia(loginDTO.getId());
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
    public void cadastrarUsuario(Long loginId) {
        Call<UsuarioDTO> usuarioDTOCall = usuarioAPI.setUsuario(new UsuarioDTO(getViewUsuario(loginId)));
        usuarioDTOCall.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Usuário foi cadastrado!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
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

    public void cadastrarFarmacia(Long loginId) {
        Call<FarmaciaDTO> farmaciaDTOCall = farmaciaAPI.setFarmacia(new FarmaciaDTO(getViewFarmacia(loginId)));
        farmaciaDTOCall.enqueue(new Callback<FarmaciaDTO>() {
            @Override
            public void onResponse(Call<FarmaciaDTO> call, Response<FarmaciaDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(), "Farmácia foi cadastrada!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
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
