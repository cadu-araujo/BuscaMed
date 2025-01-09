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
import br.edu.ifam.buscamed.CadastroRemedio;
import br.edu.ifam.buscamed.R;
import br.edu.ifam.buscamed.model.Remedio;

public class RemedioAdapter extends RecyclerView.Adapter<RemedioAdapter.RemedioViewHolder>{
    private Context context;
    private List<Remedio> remedioList;

    public RemedioAdapter(Context context, List<Remedio> produtoList) {
        this.context = context;
        this.remedioList = produtoList;
    }

    @NonNull
    @Override
    public RemedioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_remedio, parent, false);
        return new RemedioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemedioViewHolder holder, int position) {
        Remedio remedio = remedioList.get(position);
        holder.tvNome.setText(remedio.getNome());
        holder.tvMarca.setText(remedio.getMarca());
        holder.tvDescricao.setText(remedio.getDescricao());
        holder.tvQuantidade.setText(String.valueOf(remedio.getQuantidade()));
        holder.tvPreco.setText(String.valueOf(remedio.getValor()));
        holder.tvFarmacia.setText(remedio.getFarmacia());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CadastroRemedio.class);
            intent.putExtra("id", remedio.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return remedioList.size();
    }

    public static class RemedioViewHolder extends RecyclerView.ViewHolder {
        TextView tvNome;
        TextView tvMarca;
        TextView tvDescricao;
        TextView tvPreco;
        TextView tvQuantidade;
        TextView tvFarmacia;

        public RemedioViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvMarca = itemView.findViewById(R.id.tvMarca);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvPreco = itemView.findViewById(R.id.tvPreco);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            tvFarmacia = itemView.findViewById(R.id.tvFarmacia);
        }
    }
}
