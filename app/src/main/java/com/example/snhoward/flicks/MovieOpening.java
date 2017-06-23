package com.example.snhoward.flicks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MovieOpening extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    @BindView(R.id.ivLogo) ImageView ivLogo;
    @BindView(R.id.btnFlicks) Button btnFlicks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_opening);
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.btnFlicks)
    public void launchApp() {
        btnFlicks.setBackgroundColor(Color.WHITE);
        ivLogo.setColorFilter(Color.WHITE);
        Toast.makeText(MovieOpening.this,
                "Launching the FLICKS experience...",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MovieOpening.this , MovieListActivity.class);
        startActivity(intent);
        MovieOpening.this.startActivity(intent);
    }

}
