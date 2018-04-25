package wkerlyson.com.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProdutosActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Produtos");
    }

    @OnClick(R.id.fabAddProdutos)
    public void chamarCadProdActivity(View view){
        startActivity(new Intent(ProdutosActivity.this, CadastroProdutoActivity.class));
    }

}
