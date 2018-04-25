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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import wkerlyson.com.mystore.util.FirebaseUtil;

public class RegistroActivity extends AppCompatActivity {

    @BindView(R.id.etNome)
    EditText campoNome;

    @BindView(R.id.etEmail)
    EditText campoEmail;

    @BindView(R.id.etSenha)
    EditText campoSenha;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);
        auth = FirebaseUtil.getInstanceFirebaseAuth();
    }

    @OnClick(R.id.btRegistrar)
    public void registrarUsuario(){

        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();
        final String nome = campoNome.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(senha) || TextUtils.isEmpty(nome)){
            Toast.makeText(RegistroActivity.this, "Todos os campos são obrigatórios", Toast.LENGTH_LONG).show();
        }else{
            auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();

                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(nome)
                                        .build();

                                user.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Log.d(TAG, "User profile updated.");
                                                }
                                            }
                                        });
                                Toast.makeText(RegistroActivity.this, "Registrado com sucesso", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(RegistroActivity.this, "Erro ao registrar. Erro: " + task.getException(), Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }
}
