package br.com.wilker.projeto2.helpers;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.wilker.projeto2.R;
import br.com.wilker.projeto2.models.Contato;

public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.MyViewHolder>{

    private List<Contato> listaContatos;

    public ContatoAdapter(List<Contato> lista) {
        this.listaContatos = lista;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_contatos_adapter, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Contato contato = listaContatos.get(position);
        holder.contato.setText(contato.getNomePessoa());
    }

    @Override
    public int getItemCount() {
        return this.listaContatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView contato;

        public MyViewHolder(View itemView) {
            super(itemView);

            contato = itemView.findViewById(R.id.textContato);
        }
    }

}
