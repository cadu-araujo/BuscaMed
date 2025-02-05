package br.edu.ifam.buscamed;

import static br.edu.ifam.buscamed.repository.BDbuscaMed.API_URL;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.edu.ifam.buscamed.dto.RemedioDTO;
import br.edu.ifam.buscamed.dto.UsuarioDTO;
import br.edu.ifam.buscamed.dto.VendaInputDTO;
import br.edu.ifam.buscamed.interfaces.RemedioAPI;
import br.edu.ifam.buscamed.interfaces.UsuarioAPI;
import br.edu.ifam.buscamed.interfaces.VendaAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroPedido extends AppCompatActivity {

    private RemedioAPI remedioAPI;
    private UsuarioAPI usuarioAPI;
    private VendaAPI vendaAPI;
    private EditText nome, telefone, remedio, quantidade;
    private Spinner situacao;
    private Long idUser, idremedio;
    private int quantoTem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_pedido);

        acessarAPI();

        SharedPreferences userI = getSharedPreferences("user", MODE_PRIVATE);

        idremedio = getIntent().getLongExtra("idRemedio", 0);
        idUser = userI.getLong("IDUser", 0);

        nome = findViewById(R.id.etNomePedido);
        telefone = findViewById(R.id.etTelefonePedido);
        remedio = findViewById(R.id.etRemedioPedido);
        quantidade = findViewById(R.id.etQuantidadePedido);
        situacao = findViewById(R.id.spSituacaoPedido);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.situacoes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        situacao.setAdapter(adapter);

        inicia(idremedio, idUser);
    }

    public VendaInputDTO getVendaDTO(){
        VendaInputDTO vendaDTO = new VendaInputDTO();
        vendaDTO.setRemedioId(idremedio);
        vendaDTO.setConcluida("Em andamento");
        vendaDTO.setUsuarioId(idUser);
        return vendaDTO;
    }

    private void acessarAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        remedioAPI = retrofit.create(RemedioAPI.class);
        usuarioAPI =  retrofit.create(UsuarioAPI.class);
        vendaAPI = retrofit.create(VendaAPI.class);
    }

    private void getRemedio(Long id) {
        Call<RemedioDTO> call = remedioAPI.getRemedio(id);

        call.enqueue(new Callback<RemedioDTO>() {
            @Override
            public void onResponse(Call<RemedioDTO> call, Response<RemedioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    remedio.setText(response.body().getNome());
                    quantoTem = response.body().getQuantidade();
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


    private void getUsuario(Long id) {
        Call<UsuarioDTO> call = usuarioAPI.getUsuario(id);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    nome.setText(response.body().getNome());
                    telefone.setText(response.body().getTelefone());
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

    private void inicia(Long idremedio, Long idUser){
        getUsuario(idUser);
        getRemedio(idremedio);
    }

    public void pedir(View v){
        if(quantidade.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Informe a quantidade", Toast.LENGTH_LONG).show();
            quantidade.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(quantidade, InputMethodManager.SHOW_IMPLICIT);
            }
        }else{
            int pedido = Integer.parseInt(quantidade.getText().toString());
            if( pedido <= quantoTem){
                new AlertDialog.Builder(this).setTitle("Confirmação").setMessage("Tem certeza que deseja realizar esse pedido?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            cadastrar();
                            finish();
                            Toast.makeText(this, "Ação confirmada!", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Não", (dialog, which) -> {
                        }).show();
            }else{
                Toast.makeText(getApplicationContext(), "Quantidade excede o limite", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void cadastrar(){
        Call<VendaInputDTO> call = vendaAPI.setVenda(getVendaDTO());
        call.enqueue(new Callback<VendaInputDTO>() {
            @Override
            public void onResponse(Call<VendaInputDTO> call, Response<VendaInputDTO> response) {
                if(response.isSuccessful() && response.body()!=null){
                    Toast.makeText(getApplicationContext(),"Pedido realizado", Toast.LENGTH_SHORT).show();
                }else{
                    String codigo = "Erro: "+response.code();
                    Toast.makeText(getApplicationContext(), codigo, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VendaInputDTO> call, Throwable t) {
                String failureMessage = "Falha de acesso"+t.getMessage();
                Toast.makeText(getApplicationContext(), failureMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

}