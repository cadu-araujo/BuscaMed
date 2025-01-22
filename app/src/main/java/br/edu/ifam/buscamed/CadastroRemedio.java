package br.edu.ifam.buscamed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

import br.edu.ifam.buscamed.model.Farmacia;
import br.edu.ifam.buscamed.model.Remedio;
import br.edu.ifam.buscamed.repository.FarmaciaDAO;
import br.edu.ifam.buscamed.repository.RemedioDAO;

public class CadastroRemedio extends AppCompatActivity {

    private Long id;
    private ImageButton ibSaveRemedio;
    private ImageButton ibDeleteRemedio;
    private EditText etRemedioNome;
    private EditText etRemedioMarca;
    private EditText etRemedioQuantidade;
    private EditText etRemedioDescricao;
    private EditText etRemedioPreco;
    private EditText etRemedioFarmacia;
    private RemedioDAO remedioDAO;
    private FarmaciaDAO farmaciaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_remedio);

        remedioDAO = new RemedioDAO(this);
        farmaciaDAO = new FarmaciaDAO(this);
        ibSaveRemedio = findViewById(R.id.ibSaveRemedio);
        ibDeleteRemedio = findViewById(R.id.ibExcluirRemedio);
        etRemedioDescricao = findViewById(R.id.etRemedioDescricao);
        etRemedioNome = findViewById(R.id.etRemedioNome);
        etRemedioPreco = findViewById(R.id.etRemedioPreco);
        etRemedioMarca = findViewById(R.id.etRemedioMarca);
        etRemedioQuantidade = findViewById(R.id.etRemedioQuantidade);
        etRemedioFarmacia = findViewById(R.id.etRemedioFarmacia);

        ibDeleteRemedio.setVisibility(View.INVISIBLE);

        //insert
        ibSaveRemedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(farmaciaDAO.farmaciaExiste(etRemedioFarmacia.getText().toString())){
                    remedioDAO.insert(getRemedio());
                    Toast.makeText(getApplicationContext(), "Remédio inserido", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Farmácia não existe", Toast.LENGTH_SHORT).show();
                    setRemedio(new Remedio());
                }
            }
        });

        //alter
        Intent intent = getIntent();

        if(intent.hasExtra("id")){
            ibDeleteRemedio.setVisibility(View.VISIBLE);
            id = intent.getLongExtra("id", 0);
            setRemedio(remedioDAO.getRemedioByID(id));
            ibSaveRemedio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remedioDAO.update(id, getRemedio());
                    Toast.makeText(getApplicationContext(), "Remédio alterado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    private Remedio getRemedio(){
        Remedio remedio = new Remedio();
        remedio.setNome(etRemedioNome.getText().toString());
        remedio.setDescricao(etRemedioDescricao.getText().toString());
        remedio.setMarca(etRemedioMarca.getText().toString());
        remedio.setValor(Float.parseFloat(etRemedioPreco.getText().toString()));
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
        etRemedioFarmacia.setText(remedio.getFarmacia().toUpperCase());
    }

    public void ibClearRemedioOnClick(View v){
        setRemedio(new Remedio());
    }

    public void ibDeleteRemedioOnClick(View v){
        new AlertDialog.Builder(this).setTitle("Confirmação").setMessage("Tem certeza que deseja deletar este remédio?")
                .setPositiveButton("Sim", (dialog, which) -> {
                    remedioDAO.delete(id);
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

}