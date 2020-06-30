package unis.edu.crudalunos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    static final int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final TextView textView = findViewById(R.id.tvProgress);

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i = 1; i <= 100; i++) {
                    final int value = i;
                    delay();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(value);
                            textView.setText(value + "%");
                        }
                    });
                }
                mostrarLoginActivity();
            }
        };
        new Thread(runnable).start();
    }

    private void delay() {
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void mostrarLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}