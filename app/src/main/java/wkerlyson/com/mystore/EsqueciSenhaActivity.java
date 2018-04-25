package wkerlyson.com.mystore;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EsqueciSenhaActivity extends AppCompatActivity {

    @BindView(R.id.etEmailParaRecuperacao)
    EditText campoEmailParaRecuperacao;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.btnRecuperarSenha)
    public void enviarEmailParaRecuperacao(){
        String email = campoEmailParaRecuperacao.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(EsqueciSenhaActivity.this, "Insira um email", Toast.LENGTH_LONG).show();
        }else{
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(EsqueciSenhaActivity.this, LoginActivity.class));
                        finish();
                        Toast.makeText(EsqueciSenhaActivity.this, "E-mail enviado com sucesso", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(EsqueciSenhaActivity.this, "Erro ao enviar o email. ERRO: " + task.getException()
                                , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
