package wkerlyson.com.mystore.model;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;

import wkerlyson.com.mystore.CadastroProdutoActivity;
import wkerlyson.com.mystore.ProdutosActivity;
import wkerlyson.com.mystore.util.FirebaseUtil;

public class Produto implements Serializable{
    private String nomeProduto;
    private String descricaoProduto;
    private Double valorProduto = 00.00;
    private String urlImagem;
    private String key;

    public Produto() {
    }

    public void cadastrarProduto(final Activity activity){
        DatabaseReference reference = FirebaseUtil.getInstanceDatabaseReference();
        reference.child("produtos").push().setValue(this, new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    Toast.makeText(activity.getApplicationContext(), "Cadastrado com sucesso", Toast.LENGTH_LONG).show();
                    activity.startActivity(new Intent(activity.getApplicationContext(), ProdutosActivity.class));
                    activity.finish();
                }else{
                    Toast.makeText(activity.getApplicationContext(), "Erro ao cadastrar. Erro: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void editarProduto(final Activity activity, Produto produto){
        DatabaseReference reference = FirebaseUtil.getInstanceDatabaseReference();
        reference.child("produtos").child(produto.getKey()).setValue(this, new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError == null){
                    Toast.makeText(activity.getApplicationContext(), "Editado com sucesso", Toast.LENGTH_LONG).show();
                    activity.startActivity(new Intent(activity.getApplicationContext(), ProdutosActivity.class));
                    activity.finish();
                }else{
                    Toast.makeText(activity.getApplicationContext(), "Erro ao editar. Erro: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Double getValorProduto() {
        return valorProduto;
    }

    public void setValorProduto(Double valorProduto) {
        this.valorProduto = valorProduto;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
