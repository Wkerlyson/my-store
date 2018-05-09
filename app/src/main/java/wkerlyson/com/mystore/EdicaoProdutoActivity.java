package wkerlyson.com.mystore;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wkerlyson.com.mystore.model.Produto;
import wkerlyson.com.mystore.util.FirebaseUtil;

public class EdicaoProdutoActivity extends AppCompatActivity {

    @BindView(R.id.ivProdutoEdicao)
    ImageView imagemProduto;
    @BindView(R.id.etNomeProdutoEdicao)
    EditText campoNome;
    @BindView(R.id.etDescricaoProdutoEdicao)
    EditText campoDescricao;
    @BindView(R.id.etValorProdutoEdicao)
    EditText campoValor;

    private Produto produto;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edicao_produto);
        ButterKnife.bind(this);
        storageReference = FirebaseUtil.getInstanceFirebaseStore();
        mostrarDados();
    }

    public void mostrarDados(){
        Bundle dados = getIntent().getExtras();
        produto = (Produto) dados.getSerializable("produto");

        campoNome.setText(produto.getNomeProduto());
        campoDescricao.setText(produto.getDescricaoProduto());
        campoValor.setText(produto.getValorProduto().toString());

        Picasso.with(this).load(produto.getUrlImagem())
                .resize(400,400)
                .placeholder(R.drawable.ic_photo_24dp)
                .centerCrop()
                .into(imagemProduto);

    }

    @OnClick(R.id.btnEditarProduto)
    public void editarProduto(View view){

        final String textoNome = campoNome.getText().toString();
        final String textoDescricao = campoDescricao.getText().toString();
        final String textoValor = campoValor.getText().toString();

        if (textoNome.isEmpty() || textoDescricao.isEmpty() || textoValor.isEmpty()){
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
        }else{

            if (mImageUri != null){
                StorageReference caminhoImagem = storageReference.child("ImagensProdutos").child(mImageUri.getLastPathSegment());

                caminhoImagem.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        produto.setNomeProduto(textoNome);
                        produto.setDescricaoProduto(textoDescricao);
                        produto.setValorProduto(Double.parseDouble(textoValor));
                        produto.setUrlImagem(taskSnapshot.getDownloadUrl().toString());
                        produto.editarProduto(EdicaoProdutoActivity.this, produto);
                    }
                });
            }else {
                produto.setNomeProduto(textoNome);
                produto.setDescricaoProduto(textoDescricao);
                produto.setValorProduto(Double.parseDouble(textoValor));
                produto.editarProduto(EdicaoProdutoActivity.this, produto);
            }
        }
    }

    @OnClick(R.id.ivProdutoEdicao)
    public void selecionarImagemProduto(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri)
                    .into(imagemProduto);
        }
    }
}
