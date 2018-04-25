package wkerlyson.com.mystore;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity implements Runnable{

    @BindView(R.id.ivSplash)
    ImageView imgSplash;

    @BindView(R.id.tvMyStore)
    TextView textoMyStore;

    @BindView(R.id.tvMelhorLoja)
    TextView textoMelhorLoja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        Animation animation2 = AnimationUtils.makeInAnimation(this, true);

        imgSplash.setAnimation(animation);
        textoMyStore.setAnimation(animation2);
        textoMelhorLoja.setAnimation(animation2);

        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
    }


    @Override
    public void run() {
        Intent telaLogin = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(telaLogin);
        finish();
    }
}
