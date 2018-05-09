package wkerlyson.com.mystore;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wkerlyson.com.mystore.model.Produto;
import wkerlyson.com.mystore.util.FirebaseUtil;

public class CadastroProdutoActivity extends AppCompatActivity {

    @BindView(R.id.ivCadastroProduto)
    ImageView imagemProduto;

    @BindView(R.id.etNomeProdutoCadastro)
    EditText campoNome;

    @BindView(R.id.etDescricaoProdutoCadastro)
    EditText campoDescricao;

    @BindView(R.id.etValorProdutoCadastro)
    EditText campoValor;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produto);
        storageReference = FirebaseUtil.getInstanceFirebaseStore();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnCadastrarProduto)
    public void cadastarProduto(View view){

        final String textoNome = campoNome.getText().toString();
        final String textoDescricao = campoDescricao.getText().toString();
        final String textoValor = campoValor.getText().toString();

        if (textoNome.isEmpty() || textoDescricao.isEmpty() || textoValor.isEmpty()){
            Toast.makeText(this, "Todos os campos são obrigatórios", Toast.LENGTH_SHORT).show();
        }else{
            StorageReference caminhoImagem = storageReference.child("ImagensProdutos").child(mImageUri.getLastPathSegment());

            caminhoImagem.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Produto produto = new Produto();
                    produto.setNomeProduto(textoNome);
                    produto.setDescricaoProduto(textoDescricao);
                    produto.setValorProduto(Double.parseDouble(textoValor));
                    produto.setUrlImagem(taskSnapshot.getDownloadUrl().toString());
                    produto.cadastrarProduto(CadastroProdutoActivity.this);
                }
            });



        }
    }

    @OnClick(R.id.ivCadastroProduto)
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
