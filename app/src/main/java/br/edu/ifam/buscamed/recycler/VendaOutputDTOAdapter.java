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

import br.edu.ifam.buscamed.CadastroPedido;
import br.edu.ifam.buscamed.CadastroRemedio;
import br.edu.ifam.buscamed.R;
import br.edu.ifam.buscamed.dto.VendaOutputDTO;
import br.edu.ifam.buscamed.model.Remedio;

public class VendaOutputDTOAdapter extends RecyclerView.Adapter<VendaOutputDTOAdapter.VendaViewHolder>{
    private Context context;
    private List<VendaOutputDTO> vendasList;

    public VendaOutputDTOAdapter(Context context, List<VendaOutputDTO> vendasList) {
        this.context = context;
        this.vendasList = vendasList;
    }

    @NonNull
    @Override
    public VendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_venda, parent, false);
        return new VendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VendaViewHolder holder, int position) {
        VendaOutputDTO vendaOutputDTO = vendasList.get(position);
        holder.tvSituacao.setText(vendaOutputDTO.getSituacao());
        holder.tvRemedio.setText(vendaOutputDTO.getRemedio());
        holder.tvCliente.setText(vendaOutputDTO.getNome());
        holder.tvQuantidade.setText(String.valueOf(vendaOutputDTO.getQuantidade()));
        holder.tvTelefone.setText(vendaOutputDTO.getTelefone());
        holder.tvFarmacia.setText(vendaOutputDTO.getFarmacia());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CadastroPedido.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return vendasList.size();
    }

    public static class VendaViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente;
        TextView tvTelefone;
        TextView tvRemedio;
        TextView tvFarmacia;
        TextView tvQuantidade;
        TextView tvSituacao;

        public VendaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tvItemCliente);
            tvFarmacia = itemView.findViewById(R.id.tvItemFarmacia);
            tvTelefone = itemView.findViewById(R.id.tvItemTelefone);
            tvRemedio = itemView.findViewById(R.id.tvItemRemedio);
            tvSituacao = itemView.findViewById(R.id.tvItemSituacao);
            tvQuantidade = itemView.findViewById(R.id.tvItemQuantidade);
        }
    }
}
