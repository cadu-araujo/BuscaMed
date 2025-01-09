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
import br.edu.ifam.buscamed.repository.FarmaciaDAO;

public class CadastroFarmacia extends AppCompatActivity {

    private EditText etFarmaciaNome;
    private EditText etFarmaciaCNPJ;
    private EditText etFarmaciaEndereco;

    private long id;
    private FarmaciaDAO farmaciaDAO;
    private ImageButton ibSaveFarmacia;
    private ImageButton ibDeleteFarmacia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_farmacia);

        farmaciaDAO = new FarmaciaDAO(this);
        etFarmaciaNome = findViewById(R.id.etFarmaciaNome);
        etFarmaciaCNPJ = findViewById(R.id.etFarmaciaCNPJ);
        etFarmaciaEndereco = findViewById(R.id.etFarmaciaEndereco);
        ibSaveFarmacia = findViewById(R.id.ibSaveFarmacia);
        ibDeleteFarmacia = findViewById(R.id.ibExcluirFarmacia);

        ibDeleteFarmacia.setVisibility(View.INVISIBLE);
        //Insert

        ibSaveFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                farmaciaDAO.insert(getFarmacia());
                Toast.makeText(getApplicationContext(), "Farmacia inserida", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        //Delete e alter
        Intent intent = getIntent();

        if(intent.hasExtra("id")){
            ibDeleteFarmacia.setVisibility(View.VISIBLE);
            id = intent.getLongExtra("id", 0);
            setFarmacia(farmaciaDAO.getById(id));
            ibSaveFarmacia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    farmaciaDAO.update(id, getFarmacia());
                    Toast.makeText(getApplicationContext(), "Farmacia alterada", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    private Farmacia getFarmacia(){
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
                    farmaciaDAO.delete(id);
                    finish();
                    Toast.makeText(this, "Ação confirmada!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Não", (dialog, which) -> {
                }).show();
    }

    public void ibClearFarmaciaOnClick(View v){
        setFarmacia(new Farmacia());
    }


}