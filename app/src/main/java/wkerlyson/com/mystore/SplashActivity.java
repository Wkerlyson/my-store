package wkerlyson.com.mystore;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity implements Runnable{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView imgSplah = findViewById(R.id.imageView2);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_splash);
        imgSplah.setAnimation(animation);

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
