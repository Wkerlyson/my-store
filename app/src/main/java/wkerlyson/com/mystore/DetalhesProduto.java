package wkerlyson.com.mystore;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.security.PrivateKey;

import butterknife.BindView;
import butterknife.ButterKnife;
import wkerlyson.com.mystore.model.Produto;

public class DetalhesProduto extends AppCompatActivity {

    @BindView(R.id.ivProdutoDetalhe)
    ImageView imagemProduto;
    @BindView(R.id.tvNomeProdutoDetalhe)
    TextView nomeProduto;
    @BindView(R.id.tvDescProdDetalhe)
    TextView descricaoProduto;
    @BindView(R.id.tvValorProdDetalhe)
    TextView valorProduto;

    private Produto produto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_produto);
        ButterKnife.bind(this);

        Bundle dados = getIntent().getExtras();
        produto = (Produto) dados.getSerializable("produto");

        mostrarDados();
    }

    public void mostrarDados(){


        nomeProduto.setText(produto.getNomeProduto());
        descricaoProduto.setText(produto.getDescricaoProduto());
        valorProduto.setText(produto.getValorProduto().toString());

        Picasso.with(this).load(produto.getUrlImagem())
                .placeholder(R.drawable.ic_photo_24dp)
                .into(imagemProduto);

    }

    public void salvarEdicao(){

    }


}
