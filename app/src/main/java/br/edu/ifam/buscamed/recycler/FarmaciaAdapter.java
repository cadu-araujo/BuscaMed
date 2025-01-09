package br.edu.ifam.buscamed.recycler;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.ifam.buscamed.CadastroFarmacia;
import br.edu.ifam.buscamed.R;
import br.edu.ifam.buscamed.model.Farmacia;

public class FarmaciaAdapter extends RecyclerView.Adapter<FarmaciaAdapter.FarmaciaViewHolder>{
    private Context context;
    private List<Farmacia> farmaciaList;

    public FarmaciaAdapter(Context context, List<Farmacia> farmaciaList) {
        this.context = context;
        this.farmaciaList = farmaciaList;
    }

    @NonNull
    @Override
    public FarmaciaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_farmacia, parent, false);
        return new FarmaciaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmaciaViewHolder holder, int position) {
        Farmacia farmacia = farmaciaList.get(position);
        holder.tvNomeF.setText(farmacia.getNome());
        holder.tvCNPJ.setText(farmacia.getCnpj());
        holder.tvEndereco.setText(farmacia.getEndereco());

        //View que representa o fornecedorviewholder
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CadastroFarmacia.class);
            intent.putExtra("id", farmacia.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return farmaciaList.size();
    }

    public static class FarmaciaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeF;
        TextView tvCNPJ;
        TextView tvEndereco;

        public FarmaciaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeF = itemView.findViewById(R.id.tvNomeF);
            tvCNPJ = itemView.findViewById(R.id.tvCNPJ);
            tvEndereco = itemView.findViewById(R.id.tvEndereco);
        }
    }
}
