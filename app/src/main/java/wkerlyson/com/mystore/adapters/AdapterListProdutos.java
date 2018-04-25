package wkerlyson.com.mystore.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import wkerlyson.com.mystore.R;
import wkerlyson.com.mystore.model.Produto;

public class AdapterListProdutos extends RecyclerView.Adapter<AdapterListProdutos.MyViewHolder>{

    List<Produto> produtos;
    Context context;

    public AdapterListProdutos(List<Produto> produtos, Context context) {
        this.produtos = produtos;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_produtos, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Produto produto = produtos.get(position);
        holder.nome.setText(produto.getNomeProduto());
        holder.valor.setText( String.valueOf(produto.getValorProduto()));
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvNomeProdutoList)
        TextView nome;
        @BindView(R.id.tvValorProduto)
        TextView valor;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
