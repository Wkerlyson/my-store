package wkerlyson.com.mystore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wkerlyson.com.mystore.adapters.AdapterListProdutos;
import wkerlyson.com.mystore.model.Produto;
import wkerlyson.com.mystore.util.FirebaseUtil;

public class ProdutosActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerProdutos)
    RecyclerView recyclerView;

    DatabaseReference databaseReference;
    DatabaseReference produtosRef;

    ValueEventListener addValueEventListener;

    private AdapterListProdutos adapterListProdutos;

    private List<Produto> produtos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Produtos");

        databaseReference = FirebaseUtil.getInstanceDatabaseReference();

        //configurar adapter
        adapterListProdutos = new AdapterListProdutos(produtos, this);

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListProdutos);

        recuperarProdutos();
    }

    @OnClick(R.id.fabAddProdutos)
    public void chamarCadProdActivity(View view){
        startActivity(new Intent(ProdutosActivity.this, CadastroProdutoActivity.class));
    }

    public void recuperarProdutos(){


        produtosRef = databaseReference.child("produtos");

        addValueEventListener = produtosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                produtos.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Produto produto = dados.getValue(Produto.class);
                    produtos.add(produto);
                }

                adapterListProdutos.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
