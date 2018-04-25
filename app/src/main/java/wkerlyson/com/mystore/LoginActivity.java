package wkerlyson.com.mystore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wkerlyson.com.mystore.util.FirebaseUtil;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.etEmail)
    EditText campoEmail;

    @BindView(R.id.etSenha)
    EditText campoSenha;

    @BindView(R.id.btLogar)
    Button btnLogar;

    @BindView(R.id.tvEsqSenha)
    TextView esqSenha;

    @BindView(R.id.tvRegistre)
    TextView registre;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        auth = FirebaseUtil.getInstanceFirebaseAuth();
    }

    @OnClick(R.id.btLogar)
    public void logar(View view){
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Informe o e-mail", Toast.LENGTH_LONG).show();
        }else if(TextUtils.isEmpty(senha)){
            Toast.makeText(this, "Informe a senha", Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                startActivity(new Intent(LoginActivity.this, ProdutosActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Falha na autenticação. ERRO: " + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }

    @OnClick(R.id.tvEsqSenha)
    public void chamarActivityEsqSenha(View view){
        Intent intent = new Intent(LoginActivity.this, EsqueciSenhaActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.tvRegistre)
    public void chamarActivityRegistre(View view){
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(intent);
    }


}
