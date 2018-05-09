package wkerlyson.com.mystore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import wkerlyson.com.mystore.util.RecyclerItemClickListener;

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

        DividerItemDecoration itemDecor = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.HORIZONTAL);


        databaseReference = FirebaseUtil.getInstanceDatabaseReference();

        //configurar adapter
        adapterListProdutos = new AdapterListProdutos(produtos, this);

        //configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterListProdutos);

        recuperarProdutos();
        configuracoesClick();
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
                    produto.setKey(dados.getKey());
                    produtos.add(produto);
                }

                adapterListProdutos.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void configuracoesClick(){
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Produto produto = produtos.get(position);

                Bundle bundle = new Bundle();
                bundle.putSerializable("produto", produto);

                Intent intent = new Intent(getApplicationContext(), DetalhesProduto.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
                opcoesRecycler(position);
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));
    }




    public void opcoesRecycler(final int posicao){

        AlertDialog.Builder alerttDialog = new AlertDialog.Builder(this);
        alerttDialog.setTitle("O que deseja fazer ?");
        alerttDialog.setItems(R.array.opcoes_dialog, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(i == 0){
                    //editar
                    Produto produto = produtos.get(posicao);

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("produto", produto);

                    Intent intent = new Intent(getApplicationContext(), EdicaoProdutoActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    //remover
                    removerProduto(posicao);
                }
            }
        });

        AlertDialog alert = alerttDialog.create();
        alert.show();
    }

    public void removerProduto(int posicao){
        Produto produto = produtos.get(posicao);

        databaseReference.child("produtos").child(produto.getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(getApplicationContext(), "Produto removido com sucesso", Toast.LENGTH_LONG).show();
            }
        });
    }

}
