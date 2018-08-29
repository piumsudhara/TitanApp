package com.titan;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Detail extends AppCompatActivity implements View.OnClickListener {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        btn = (Button) findViewById(R.id.button2);
        btn.setOnClickListener(this);

    }
    @Override
    public void onClick(View view)
    {
        if (view.getId() == R.id.button2)
        {
            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.activity_detail),
                    R.string.Cart, Snackbar.LENGTH_SHORT);
            mySnackbar.show();
        }
    }
}
